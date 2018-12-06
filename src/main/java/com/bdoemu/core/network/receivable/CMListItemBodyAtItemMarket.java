// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListItemBodyAtItemMarket;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.service.GameTimeService;

public class CMListItemBodyAtItemMarket extends ReceivablePacket<GameClient> {
    private int itemId;
    private int enchantLevel;
    private int sessionId;

    public CMListItemBodyAtItemMarket(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.readH();
        this.itemId = this.readHD();
        this.enchantLevel = this.readHD();
        this.readQ();
        this.sessionId = this.readD();
        this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.sessionId);
            if (npc == null) {
                return;
            }
            final CreatureFunctionT function = npc.getTemplate().getCreatureFunctionT();
            if (function == null || function.getTerritoryKeyForItemMarket() == null) {
                return;
            }
            player.sendPacket(new SMListItemBodyAtItemMarket(ItemMarketService.getInstance().getItems(this.itemId, this.enchantLevel), this.itemId, this.enchantLevel, GameTimeService.getServerTimeInSecond()));
        }
    }
}
