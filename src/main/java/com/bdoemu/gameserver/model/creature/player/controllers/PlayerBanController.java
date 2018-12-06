package com.bdoemu.gameserver.model.creature.player.controllers;

import com.bdoemu.MainServer;
import com.bdoemu.commons.model.enums.EBanCriteriaType;
import com.bdoemu.commons.rmi.model.BanInfo;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.configs.ChatConfig;
import com.bdoemu.core.configs.SecurityConfig;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.sendable.SMChat;
import com.bdoemu.core.network.sendable.SMLoadField;
import com.bdoemu.core.network.sendable.SMLoadFieldComplete;
import com.bdoemu.gameserver.dataholders.RegionData;
import com.bdoemu.gameserver.dataholders.TeleportData;
import com.bdoemu.gameserver.model.chat.ChatChannelController;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.ETeleportType;
import com.bdoemu.gameserver.model.skills.buffs.ModuleBuffType;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.model.world.region.templates.RegionTemplate;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PlayerBanController {
    private static int _penalty = 150;
    private final Player _player;
    private int _score;
    private long _lastActivity;
    private int _muteExpireTime;
    private ScheduledFuture<?> _chatChannelCheckTask;
    private ScheduledFuture<?> banJailEndTask;
    private long banJailEndTime;

    public PlayerBanController(final Player player) {
        _score = 0;
        _lastActivity = System.currentTimeMillis();
        _player = player;
        _muteExpireTime = 0;
        banJailEndTime = 0L;
        _chatChannelCheckTask = null;
        banJailEndTask = null;
    }

    public boolean checkChat() {
        if (!ChatConfig.FLOOD_CONTROL_ENABLE)
            return true;

        long currentActivity = System.currentTimeMillis();
        _score -= (currentActivity - _lastActivity) * ChatConfig.FLOOD_CONTROL_INCREASE_PER_SECOND;
        //_player.sendMessage("Current score: " + _score + ", FCIPS: " + ChatConfig.FLOOD_CONTROL_INCREASE_PER_SECOND + ", Threshold: " + ChatConfig.FLOOD_CONTROL_THRESHOLD + ", Last Update: " + _lastActivity + ", Now: " + currentActivity, true);
        _score = _score < 0 ? 0 : _score;
        _lastActivity = currentActivity;

        if ((_score += _penalty) > ChatConfig.FLOOD_CONTROL_THRESHOLD)
            return false;
        return true;
    }


    public boolean isMuted() {
        return _muteExpireTime > (int) (System.currentTimeMillis() / 1000L);
    }

    public String getMutedUntilString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(_muteExpireTime * 1000L);
        return calendar.getTime().toString();
    }

    public void setChatMute(int expireTime) {
        _muteExpireTime = expireTime;
    }

    public void onLogin() {
        if (_player.getJoinedChatChannelId() == -1) {
            _chatChannelCheckTask = ThreadPool.getInstance().scheduleGeneral(() -> {
                if (_player.getJoinedChatChannelId() == -1) {
                    _player.sendPacket(new SMChat(_player.getName(), _player.getFamily(), _player.getGameObjectId(), EChatType.Notice, EChatNoticeType.Emergency, "You have been joined ENGLISH channel after 15 minutes of not joining a channel."));
                    ChatChannelController.reassignPlayer(_player, 1);
                }
            }, 15, TimeUnit.MINUTES);
        }

        if (SecurityConfig.BAN_SYSTEM_ENABLE) {
            // Temporary workaround for ru shit..
            final List<BanInfo> banInfos = MainServer.getRmi().getBans(ServerConfig.SERVER_ID, EBanCriteriaType.ACCOUNT_ID, String.valueOf(_player.getAccountId()));
            if (!banInfos.isEmpty()) {
                for (final BanInfo banInfo : banInfos) {
                    switch (banInfo.getBanType()) {
                        case CHAT:
                            _muteExpireTime = (int) (banInfo.getBanEnd() / 1000);
                            break;
                        //case JAIL: setBanJailEndTime(banInfo.getBanEnd()); 		 break;
                    }
                }
            }
        } else {
            System.out.println("ERROR! Ban system is disabled!!!!!");
        }
    }


    ////////////////////////////////////////////// OLD RU STUFF
    public void setBanJailEndTime(final long banEnd) {
        this.banJailEndTime = banEnd;
        if (this.banJailEndTask != null) {
            this.banJailEndTask.cancel(true);
            this.banJailEndTask = null;
        }
        final Location jaiLoc = TeleportData.getInstance().getTeleportLocation(ETeleportType.JAIL, 1);
        final Location location = new Location(jaiLoc.getX(), jaiLoc.getY(), jaiLoc.getZ(), _player.getLocation().getCos(), _player.getLocation().getSin());
        _player.sendPacket(new SMLoadField());
        _player.setReadyToPlay(false);
        World.getInstance().teleport(_player, location);
        _player.sendPacket(new SMLoadFieldComplete());
        this.banJailEndTask = ThreadPool.getInstance().scheduleGeneral(new BanJailEndTask(), banEnd, TimeUnit.MILLISECONDS);
    }

    public boolean isInJail() {
        return this.banJailEndTime > System.currentTimeMillis() || _player.getBuffList().hasBuff(ModuleBuffType.NOT_INTERACTION, 2);
    }

    protected class BanJailEndTask implements Runnable {
        @Override
        public void run() {
            PlayerBanController.this.banJailEndTime = 0L;
            final RegionTemplate currentRegionTemplate = PlayerBanController.this._player.getRegion().getTemplate();
            RegionTemplate newRegion = null;
            final Location loc = PlayerBanController.this._player.getLocation();
            if (currentRegionTemplate.getReturnX() == 0.0f && currentRegionTemplate.getReturnY() == 0.0f) {
                newRegion = RegionData.getInstance().getNearestReturnTemplate(loc.getX(), loc.getY(), loc.getZ(), currentRegionTemplate.getTerritoryKey());
            }
            if (newRegion == null) {
                return;
            }
            final Location location = new Location(newRegion.getReturnX(), newRegion.getReturnY(), newRegion.getReturnZ(), loc.getCos(), loc.getSin());
            PlayerBanController.this._player.sendPacket(new SMLoadField());
            PlayerBanController.this._player.setReadyToPlay(false);
            World.getInstance().teleport(PlayerBanController.this._player, location);
            PlayerBanController.this._player.sendPacket(new SMLoadFieldComplete());
        }
    }
}