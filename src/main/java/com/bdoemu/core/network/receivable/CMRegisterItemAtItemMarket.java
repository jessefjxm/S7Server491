package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ItemMarketConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RegisterItemAtItemMarketEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMRegisterItemAtItemMarket extends ReceivablePacket<GameClient> {
    private EItemStorageLocation inventoryType;
    private int slotIndex;
    private int sessionId;
    private long count;
    private long price;
    private long itemObjId;
    private int index;
    private int zeroNumbers;
    private int territoryKey;
    private long pin;

    public CMRegisterItemAtItemMarket(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.inventoryType = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.readC();
        this.readH();
        this.readD();
        this.itemObjId = this.readQ();
        this.readH();
        this.readH();
        this.readH();
        this.count = this.readQ();
        this.readH();
        this.price = this.readQ();
        this.sessionId = this.readD();
        this.territoryKey = this.readHD();
        this.index = this.readC();
        this.zeroNumbers = this.readC();
        final int skip = this.index * 8;
        this.skip(skip);
        this.pin = this.readQ();
        this.skip(160 - (skip + 8));
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            if (!ItemMarketConfig.ITEM_MARKET_REGISTRATION_ENABLED) {
                this.sendPacket(new SMNak(EStringTable.eErrAuctionRegisterItem, this.opCode));
                return;
            }
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.sessionId);
            if (npc == null) {
                return;
            }
            final CreatureFunctionT function = npc.getTemplate().getCreatureFunctionT();
            if (function == null) {
                return;
            }
            Integer territoryKey = function.getTerritoryKeyForItemMarket();
            if (function.isWarehouse()) {
                territoryKey = npc.getRegion().getTemplate().getTerritoryKey().ordinal();
            }
            if (territoryKey == null) {
                return;
            }
            final StringBuilder builder = new StringBuilder();
            if (this.index > 0) {
                final char[] pinTable = player.getAccountData().getPaymentPinTable().toCharArray();
                final String pinToLong = Long.toString(this.pin);
                final char[] pinCharArray = pinToLong.toCharArray();
                while (this.zeroNumbers > 0) {
                    --this.zeroNumbers;
                    builder.append(pinTable[0]);
                }
                for (int i = 0; i < pinCharArray.length; ++i) {
                    final char pinCharIndex = pinCharArray[i];
                    final int pinIndex = Integer.parseInt(String.valueOf(pinCharIndex));
                    builder.append(pinTable[pinIndex]);
                }
            }
            player.getPlayerBag().onEvent(new RegisterItemAtItemMarketEvent(player, this.inventoryType, this.slotIndex, this.count, this.price, territoryKey, builder.toString(), this.itemObjId, npc.getRegionId()));
        }
    }
}
