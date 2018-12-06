package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.enums.EVehicleType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.duel.PVPController;
import com.bdoemu.gameserver.model.creature.player.duel.PvpMatch;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerEquipments;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.ServantEquipOnOff;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;
import com.bdoemu.gameserver.model.world.Location;

import java.util.Collection;

public class SMAddPlayers extends SendablePacket<GameClient> {
    private static final int maximum = 13;
    private final Collection<Player> players;
    private final boolean isNewSpawn;
    private final boolean isRespawn;
    private final Player receiver;

    public SMAddPlayers(final Collection<Player> players, final Player receiver, final boolean isNewSpawn, final boolean isRespawn) {
        if (players.size() > 13) {
            throw new IllegalArgumentException("Maximum size 13");
        }
        this.receiver = receiver;
        this.players = players;
        this.isNewSpawn = isNewSpawn;
        this.isRespawn = isRespawn;
    }

    public static int getMaximum() {
        return 13;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(1);
        buffer.writeC(this.isRespawn);
        buffer.writeC(this.isNewSpawn);
        buffer.writeH(this.players.size());
        for (final Player player : this.players) {
            final Location location = player.getLocation();
            buffer.writeD(player.getGameObjectId());
            buffer.writeD(-1024);
            buffer.writeF(location.getX());
            buffer.writeF(location.getZ());
            buffer.writeF(location.getY());
            buffer.writeF(location.getX());
            buffer.writeF(location.getZ());
            buffer.writeF(location.getY());
            buffer.writeF(location.getCos());
            buffer.writeD(0);
            buffer.writeF(location.getSin());
            buffer.writeD(player.getCreatureId());
            buffer.writeF(player.getGameStats().getHp().getCurrentHp());
            buffer.writeF(player.getGameStats().getHp().getMaxHp());
            buffer.writeD(player.getGameStats().getSwimmingStat().getIntMaxValue());
            buffer.writeQ(player.getGuildCache());
            buffer.writeQ(0L);
            buffer.writeH(0);
            buffer.writeQ(player.getPartyCache());
            buffer.writeC(2);
            buffer.writeD(player.getActionStorage().getActionHash());
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeH(0);
            buffer.writeC(player.getActionStorage().getApplySpeedBuffType().ordinal());
            buffer.writeD(player.getActionStorage().getSpeedRate());
            buffer.writeD(player.getActionStorage().getSlowSpeedRate());
            buffer.writeD(0);
            buffer.writeH(-1); // attempt to fix militant issue
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeB(new byte[600]);
            buffer.writeQ(player.getAccountData().getChargeUserStorage().getChargeUserEffectEndTime(EChargeUserType.DyeingPackage));
            buffer.writeQ(player.getAccountId());
            buffer.writeD(player.getAccountData().getUserBasicCacheCount());
            buffer.writeQ(player.getObjectId());
            buffer.writeD(player.getBasicCacheCount());
            buffer.writeD(player.getPcCustomizationCacheCount());
            buffer.writeD(player.getEquipSlotCacheCount());
            buffer.writeC(player.getPlayerRenderStorage().getRenderBits());
            final ServantEquipOnOff servantEquipOnOff = player.getServantController().getServantEquipOnOff();
            buffer.writeD(servantEquipOnOff.getVehicleEquipOnOff());
            buffer.writeD(servantEquipOnOff.getCamelEquipOnOff());
            buffer.writeD(servantEquipOnOff.getUnk1EquipOnOff());
            buffer.writeD(servantEquipOnOff.getUnk2EquipOnOff());
            buffer.writeD(servantEquipOnOff.getShipEquipOnOff());
            buffer.writeD(player.getAvatarEquipOnOff());
            buffer.writeD(player.getPcNonSavedCacheCount());
            buffer.writeD(0); // unk cache
            buffer.writeH(0);
            buffer.writeC(player.canPvP());
            buffer.writeC(player.isPvPEnable());
            final PlayerEquipments equipment = player.getPlayerBag().getEquipments();
            final Item mainItem = equipment.getItem(0);
            buffer.writeH((mainItem != null) ? mainItem.getItemId() : 0);
            buffer.writeH((mainItem != null) ? mainItem.getEnchantLevel() : 0);
            final Servant servant = player.getCurrentVehicle();
            buffer.writeD((servant != null) ? servant.getGameObjectId() : -1024);
            buffer.writeH((servant != null) ? servant.getCreatureId() : 0);
            buffer.writeD((servant != null) ? servant.getActionIndex() : 0);
            buffer.writeC((servant != null) ? servant.getTemplate().getVehicleType().ordinal() : EVehicleType.None.ordinal());
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeC(player.getRidingSlotType().ordinal());
            buffer.writeD(player.getTendency());
            buffer.writeD(0);
            buffer.writeQ(player.getPreemptiveStrikeTime());
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeD(-1024);
            buffer.writeC(player == this.receiver || player.isVisible());
            buffer.writeD(player.getLevel());
            buffer.writeD(player.getTitleHandler().getTitleId());
            buffer.writeC(player.getGuildMemberRankType().ordinal());
            buffer.writeC(0);
            buffer.writeQ((servant != null) ? servant.getObjectId() : 0L);
            buffer.writeH(player.getHouseVisit().getHouseId());
            buffer.writeQ(player.getHouseVisit().getHouseObjectId());
            buffer.writeD(player.getActivityPoints());
            buffer.writeD(player.getLearnSkillCacheCount());
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeD(-1);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(-1);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            final PVPController pvpController = player.getPVPController();
            buffer.writeC(pvpController.getPvpMatchState().getId());
            final PvpMatch pvpMatch = player.getPVPController().getPvpMatch();
            buffer.writeD((pvpMatch != null) ? pvpMatch.getMatchObjectId() : 0);
            buffer.writeQ(pvpController.getObjectId());
            buffer.writeC((pvpMatch != null) ? pvpMatch.getPvpType().getId() : 0);
            buffer.writeD(player.getPVPController().getLocalWarTeamType().ordinal());
            buffer.writeD(player.getPVPController().getLocalWarPoints());
            buffer.writeD(-2);
            buffer.writeC(4); // this was 4 on sniff, but 0 on not showing one?

            buffer.writeC(0); // 432
            buffer.writeC(0); // 471
            buffer.writeC(0); // 478
        }
    }
}
