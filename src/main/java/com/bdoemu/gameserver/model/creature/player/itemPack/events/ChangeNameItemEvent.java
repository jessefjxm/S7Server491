package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMChangeName;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.services.PlayerSaveService;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EChangeNameType;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.service.FamilyService;

public class ChangeNameItemEvent extends AItemEvent {
    private EItemStorageLocation storageLocation;
    private int slotIndex;
    private String newName;
    private EChangeNameType changeNameType;
    private long objectId;
    private int cacheCount;

    public ChangeNameItemEvent(final Player player, final EItemStorageLocation storageLocation, final int slotIndex, final String newName, final EChangeNameType changeNameType) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.objectId = 0L;
        this.cacheCount = 0;
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
        this.newName = newName;
        this.changeNameType = changeNameType;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacketNoFlush(new SMChangeName(this.changeNameType, this.player, this.newName, this.objectId, this.cacheCount));
    }

    @Override
    public boolean canAct() {
        this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        if (!super.canAct()) {
            return false;
        }
        switch (this.changeNameType) {
            case FamilyName: {
                FamilyService.getInstance().putFamily(this.player.getAccountId(), this.newName);
                this.objectId = this.player.getAccountId();
                this.player.getAccountData().recalculateUserBasicCacheCount();
                this.cacheCount = this.player.getAccountData().getUserBasicCacheCount();
                this.player.getServantController().getServants(EServantState.Field, EServantType.Vehicle, EServantType.Ship).forEach(Playable::recalculateBasicCacheCount);
                break;
            }
            case PlayerName: {
                if (!PlayerSaveService.getInstance().updateName(this.player, this.newName)) {
                    return false;
                }
                this.objectId = this.player.getAccountId();
                this.player.setName(this.newName);
                this.player.recalculateBasicCacheCount();
                this.cacheCount = this.player.getBasicCacheCount();
                this.player.getServantController().getServants(EServantState.Field, EServantType.Vehicle, EServantType.Ship).forEach(Playable::recalculateBasicCacheCount);
                break;
            }
            case GuildName: {
                final Guild guild = this.player.getGuild();
                if (guild == null || !this.player.getGuildMemberRankType().isMaster()) {
                    return false;
                }
                if (!GuildService.getInstance().updateGuildName(this.player, this.player.getGuild(), this.newName)) {
                    return false;
                }
                this.cacheCount = guild.getBasicCacheCount();
                this.objectId = guild.getObjectId();
                break;
            }
        }
        return true;
    }
}
