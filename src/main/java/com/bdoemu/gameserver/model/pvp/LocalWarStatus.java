package com.bdoemu.gameserver.model.pvp;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.configs.RedBattlefieldConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class LocalWarStatus {
    private AtomicReference<ELocalWarStatusType> _localWarStatusType;
    private ConcurrentHashMap<Long, LocalWarMember> _yellowTeam;
    private ConcurrentHashMap<Long, LocalWarMember> _redTeam;
    private int _serverChannel;
    private long _endLocalWarTime;
    private boolean _isLimited;
    private int _redTeamPoints;
    private int _yellowTeamPoints;
    private int _warTime;

    public LocalWarStatus(final int serverChannel) {
        _isLimited = false;
        _localWarStatusType = new AtomicReference<>(ELocalWarStatusType.Waiting);
        _yellowTeam = new ConcurrentHashMap<>();
        _redTeam = new ConcurrentHashMap<>();
        _redTeamPoints = 0;
        _yellowTeamPoints = 0;
        _warTime = 0;
        _serverChannel = serverChannel;
    }

    public int getServerChannel() {
        return _serverChannel;
    }

    public int getPlayerCount() {
        return _yellowTeam.size() + _redTeam.size();
    }

    public boolean isLimited() {
        return _isLimited;
    }

    public ELocalWarStatusType getLocalWarStatusType() {
        return _localWarStatusType.get();
    }

    public int getYellowTeamPoints() {
        return _yellowTeamPoints;
    }

    public int getRedTeamPoints() {
        return _redTeamPoints;
    }

    public long getEndLocalWarDate() {
        return _endLocalWarTime;
    }

    public int getTeamScore(ELocalWarTeamType teamType) {
        switch (teamType) {
            case YellowTeam:
                return _yellowTeam.values().stream().mapToInt(LocalWarMember::getGearScore).sum();
            case RedTeam:
                return _redTeam.values().stream().mapToInt(LocalWarMember::getGearScore).sum();
        }
        return 0;
    }

    public void enterOnLocalWar(final Player player) {
        final ELocalWarTeamType localWarTeamType = (getTeamScore(ELocalWarTeamType.YellowTeam) <= getTeamScore(ELocalWarTeamType.RedTeam)) ? ELocalWarTeamType.YellowTeam : ELocalWarTeamType.RedTeam;

        player.getPVPController().setLocalWarTeamType(localWarTeamType);
        player.getPVPController().setLocalWarPoints(10);

        switch (localWarTeamType) {
            case YellowTeam:
                _yellowTeam.put(player.getObjectId(), new LocalWarMember(player));
                break;
            case RedTeam:
                _redTeam.put(player.getObjectId(), new LocalWarMember(player));
                break;
        }

        changeState(); // TODO: when new player enters, it constantly creates new loops.....
    }

    public void changeState() {
        if (getPlayerCount() >= 6 && _localWarStatusType.compareAndSet(ELocalWarStatusType.Waiting, ELocalWarStatusType.Started)) {
            _warTime = 1200;
            _endLocalWarTime = GameTimeService.getServerTimeInSecond() + _warTime;
            sendBroadcastPacket(new SMUpdateLocalwarState(this));
            ThreadPool.getInstance().scheduleGeneral(() -> {
                LocalWarStatus.this._warTime = 120;
                LocalWarStatus.this._endLocalWarTime += 120L;
                ThreadPool.getInstance().scheduleGeneral(() -> {
                    LocalWarStatus.this._warTime = 120;
                    LocalWarStatus.this._endLocalWarTime += 120L;
                    if (LocalWarStatus.this._localWarStatusType.compareAndSet(ELocalWarStatusType.Started, ELocalWarStatusType.Ending)) {
                        LocalWarStatus.this.sendBroadcastPacket(new SMUpdateLocalwarState(LocalWarStatus.this));
                        long yellowRewardCount = RedBattlefieldConfig.REWARD_SEALS_COUNT_LOSER;
                        long redRewardCount = RedBattlefieldConfig.REWARD_SEALS_COUNT_LOSER;
                        long yellowRewardSilverCount = RedBattlefieldConfig.REWARD_SILVER_COUNT_LOSER;
                        long redRewardSilverCount = RedBattlefieldConfig.REWARD_SILVER_COUNT_LOSER;
                        if (LocalWarStatus.this._yellowTeamPoints > LocalWarStatus.this._redTeamPoints) {
                            yellowRewardCount = RedBattlefieldConfig.REWARD_SEALS_COUNT_WINNER;
                            yellowRewardSilverCount = RedBattlefieldConfig.REWARD_SILVER_COUNT_WINNER;
                        } else if (LocalWarStatus.this._redTeamPoints > LocalWarStatus.this._yellowTeamPoints) {
                            redRewardCount = RedBattlefieldConfig.REWARD_SEALS_COUNT_WINNER;
                            redRewardSilverCount = RedBattlefieldConfig.REWARD_SILVER_COUNT_WINNER;
                        }
                        for (final LocalWarMember yellowMember : LocalWarStatus.this._yellowTeam.values()) {
                            if (yellowMember.getPlayer().getPlayerBag().onEvent(new AddItemEvent(yellowMember.getPlayer(), 450, 0, yellowRewardCount, EStringTable.eErrNoItemCreateByAdministrator))) {
                                yellowMember.getPlayer().getObserveController().notifyObserver(EObserveType.gatherItem, 450, 0, yellowRewardCount, yellowMember.getPlayer().getObjectId());
                            }
                            if (yellowMember.getPlayer().getPlayerBag().onEvent(new AddItemEvent(yellowMember.getPlayer(), 1, 0, yellowRewardSilverCount, EStringTable.eErrNoItemCreateByAdministrator))) {
                                yellowMember.getPlayer().getObserveController().notifyObserver(EObserveType.gatherItem, 1, 0, yellowRewardSilverCount, yellowMember.getPlayer().getObjectId());
                            }
                        }
                        for (final LocalWarMember redMember : LocalWarStatus.this._redTeam.values()) {
                            if (redMember.getPlayer().getPlayerBag().onEvent(new AddItemEvent(redMember.getPlayer(), 450, 0, redRewardCount, EStringTable.eErrNoItemCreateByAdministrator))) {
                                redMember.getPlayer().getObserveController().notifyObserver(EObserveType.gatherItem, 450, 0, redRewardCount, redMember.getPlayer().getObjectId());
                            }
                            if (redMember.getPlayer().getPlayerBag().onEvent(new AddItemEvent(redMember.getPlayer(), 1, 0, redRewardSilverCount, EStringTable.eErrNoItemCreateByAdministrator))) {
                                redMember.getPlayer().getObserveController().notifyObserver(EObserveType.gatherItem, 1, 0, redRewardSilverCount, redMember.getPlayer().getObjectId());
                            }
                        }
                    }
                    ThreadPool.getInstance().scheduleGeneral(() -> {
                        if (LocalWarStatus.this.getRemainingLocalWarTime() <= 0L && LocalWarStatus.this._localWarStatusType.compareAndSet(ELocalWarStatusType.Ending, ELocalWarStatusType.Waiting)) {
                            for (final LocalWarMember yellowMember : LocalWarStatus.this._yellowTeam.values())
                                LocalWarStatus.this.exitFromLocalWar(yellowMember.getPlayer(), true);
                            for (final LocalWarMember redMember : LocalWarStatus.this._redTeam.values())
                                LocalWarStatus.this.exitFromLocalWar(redMember.getPlayer(), true);
                            LocalWarStatus.this._endLocalWarTime = 0L;
                            LocalWarStatus.this._warTime = 0;
                            LocalWarStatus.this._yellowTeamPoints = 0;
                            LocalWarStatus.this._redTeamPoints = 0;
                        }
                    }, (long) LocalWarStatus.this._warTime, TimeUnit.SECONDS);
                }, (long) LocalWarStatus.this._warTime, TimeUnit.SECONDS);
            }, (long) this._warTime, TimeUnit.SECONDS);
        }
        this.sendBroadcastPacket(new SMUpdateLocalwarState(this));
    }

    public LocalWarMember removeFromLocalWar(final Player player) {
        LocalWarMember localWarMember = null;
        switch (player.getPVPController().getLocalWarTeamType()) {
            case YellowTeam:
                localWarMember = _yellowTeam.remove(player.getObjectId());
                break;
            case RedTeam:
                localWarMember = _redTeam.remove(player.getObjectId());
                break;
        }
        return localWarMember;
    }

    public LocalWarMember getMember(final Player player) {
        LocalWarMember localWarMember = null;
        switch (player.getPVPController().getLocalWarTeamType()) {
            case YellowTeam:
                localWarMember = _yellowTeam.get(player.getObjectId());
                break;
            case RedTeam:
                localWarMember = _redTeam.get(player.getObjectId());
                break;
        }
        return localWarMember;
    }

    public boolean canExitFromLocalWar(final Player player) {
        final LocalWarMember member = getMember(player);
        if (member != null && member.getJoinTime() + 60 > ((int) (System.currentTimeMillis() / 1000L)))
            return false;
        return true;
    }

    public void exitFromLocalWar(final Player player, boolean updateRanking) {
        final LocalWarMember localWarMember = this.removeFromLocalWar(player);
        if (localWarMember != null) {
            player.addLocalWarPoints(player.getPVPController().getLocalWarPoints());
            player.getPVPController().setLocalWarTeamType(ELocalWarTeamType.None);
            player.getPVPController().setLocalWarPoints(0);
            player.sendPacketNoFlush(new SMUpdateLocalWar(player));
            player.sendPacketNoFlush(new SMUnjoinLocalWar());
            player.sendPacket(new SMLoadField());
            player.setReadyToPlay(false);
            final Location returnLoc = localWarMember.getReturnLoc();
            World.getInstance().teleport(player, new Location(returnLoc.getX(), returnLoc.getY(), returnLoc.getZ()));
            player.sendPacket(new SMLoadFieldComplete());
        }
    }

    public long getRemainingLocalWarTime() {
        final long diffTime = _endLocalWarTime - GameTimeService.getServerTimeInSecond();
        return (diffTime >= 0L && diffTime <= _warTime) ? diffTime : 0L;
    }

    public void sendBroadcastPacket(final SendablePacket<GameClient> sp) {
        _yellowTeam.values().forEach(member -> member.getPlayer().sendPacketNoFlush(sp));
        _redTeam.values().forEach(member -> member.getPlayer().sendPacketNoFlush(sp));
    }

    public void onDie(final Player killer, final Player player) {
        final int killerPoints = Math.max((int) (player.getPVPController().getLocalWarPoints() * 50.0f / 100.0f), 1);
        final int loserPoints = (int) (player.getPVPController().getLocalWarPoints() * 25.0f / 100.0f);
        switch (killer.getPVPController().getLocalWarTeamType()) {
            case YellowTeam:
                _yellowTeamPoints += killerPoints;
                break;
            case RedTeam:
                _redTeamPoints += killerPoints;
                break;
        }
        killer.getPVPController().addLocalWarPoints(killerPoints);
        player.getPVPController().addLocalWarPoints(-loserPoints);
        sendBroadcastPacket(new SMUpdateLocalWar(killer));
        sendBroadcastPacket(new SMUpdateLocalWar(player));
        sendBroadcastPacket(new SMNotifyLocalWarInfo(killer, player, _redTeamPoints, _yellowTeamPoints, killerPoints));
    }

    public boolean hasParticipant(final Player player) {
        return _yellowTeam.containsKey(player.getObjectId()) || _redTeam.containsKey(player.getObjectId());
    }
}
