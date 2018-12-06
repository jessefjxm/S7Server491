// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMRefreshGuildMarkCache;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.worldInstance.World;

public class SetGuildMarkItemEvent extends AItemEvent {
    private int slotIndex;
    private EItemStorageLocation storageLocation;
    private byte[] markData;
    private Guild guild;

    public SetGuildMarkItemEvent(final Guild guild, final Player player, final int slotIndex, final byte[] markData) {
        super(player, player, player, EStringTable.eErrNoGuildMarkIsSetted, EStringTable.eErrNoGuildMarkIsSetted, 0);
        this.guild = guild;
        this.storageLocation = EItemStorageLocation.Inventory;
        this.slotIndex = slotIndex;
        this.markData = markData;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.guild.setMarkBytes(this.markData);
        World.getInstance().broadcastWorldPacket(new SMRefreshGuildMarkCache(this.guild));
    }

    @Override
    public boolean canAct() {
        if (!GuildService.getInstance().containsGuild(this.guild) || this.player.getGuild() != this.guild || this.guild.getLeaderAccountId() != this.player.getAccountId()) {
            return false;
        }
        final Item item = this.playerInventory.getItem(this.slotIndex);
        if (item == null) {
            return false;
        }
        final EContentsEventType contentsEventType = item.getTemplate().getContentsEventType();
        if (contentsEventType == null || !contentsEventType.isGuildMark()) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        return super.canAct();
    }
}
