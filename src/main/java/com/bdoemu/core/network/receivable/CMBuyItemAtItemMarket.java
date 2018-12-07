// 
// Decompiled by Procyon v0.5.30
// 

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
import com.bdoemu.gameserver.model.creature.player.itemPack.events.BuyItemAtItemMarketEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMBuyItemAtItemMarket extends ReceivablePacket<GameClient> {
	private long itemMarketObjectId;
	private long count;
	private long itemObj;
	private int sessionId;
	private int itemId;
	private int enchant;
	private EItemStorageLocation srcStorageType;

	public CMBuyItemAtItemMarket(final short opcode) {
		super(opcode);
	}

	protected void read() {
		this.itemMarketObjectId = this.readQ();
		this.count = this.readQ();
		this.sessionId = this.readD();
		this.srcStorageType = EItemStorageLocation.valueOf(this.readC());
		this.itemObj = this.readQ();
		this.itemId = this.readH();
		this.enchant = this.readH();
		this.readH();
	}

	public void runImpl() {
		final Player player = ((GameClient) this.getClient()).getPlayer();
		if (player != null) {
			if (!ItemMarketConfig.ITEM_MARKET_BUY_ENABLED) {
				this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoAuctionNotBuy, this.opCode));
				return;
			}
			final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.sessionId);
			if (npc == null) {
				return;
			}
			final CreatureFunctionT function = npc.getTemplate().getCreatureFunctionT();
			if (function == null || function.getTerritoryKeyForItemMarket() == null) {
				return;
			}
			player.getPlayerBag().onEvent(new BuyItemAtItemMarketEvent(player, this.srcStorageType,
					this.itemMarketObjectId, this.count, this.itemId, this.enchant, npc.getRegionId()));
		}
	}
}
