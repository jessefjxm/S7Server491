package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMAddItemToWarehouse;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.GuildWarehouse;
import com.bdoemu.gameserver.model.creature.player.itemPack.Warehouse;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.team.guild.Guild;

public class CMListWarehouseItem extends ReceivablePacket<GameClient> {
    private byte _storageType;
    private short _townNodeId;
    private long _guildId;

    public CMListWarehouseItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        _storageType = readC();
        _townNodeId = readH();
        readH();
        readD();
        _guildId = readQ();
        readQ();
    }

    public void runImpl() {
        if (getClient() == null)
            return;

        final Player player = ((GameClient) getClient()).getPlayer();

        // Guild Storage
        if (_storageType == 14) {
            final Guild guild = player.getGuild();
            if (_guildId > 0 && guild != null && _guildId == guild.getObjectId()) {
                final GuildWarehouse warehouse = guild.getGuildWarehouse();
                if (warehouse != null) {
                    final ListSplitter<Item> itemsSplitter = new ListSplitter(warehouse.getItemList(), 100);
                    while (itemsSplitter.hasNext()) {
                        player.sendPacket(new SMAddItemToWarehouse(itemsSplitter.getNext(), EItemStorageLocation.GuildWarehouse, _townNodeId, itemsSplitter.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update, 0, 0L));
                    }
                }
            }
        } else { // Others that are not implemented.
            if (player != null) {
                final Warehouse warehouse = player.getPlayerBag().getWarehouse(_townNodeId);
                if (warehouse != null) {
                    final ListSplitter<Item> itemsSplitter = new ListSplitter(warehouse.getItemList(), 100);
                    while (itemsSplitter.hasNext()) {
                        player.sendPacket(new SMAddItemToWarehouse(itemsSplitter.getNext(), EItemStorageLocation.Warehouse, _townNodeId, itemsSplitter.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update, 0, 0L));
                    }
                }
            }
        }
    }
}
