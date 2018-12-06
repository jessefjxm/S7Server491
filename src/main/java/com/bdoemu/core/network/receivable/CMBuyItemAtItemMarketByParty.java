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
import com.bdoemu.core.network.sendable.SMNotifyItemMarketByPartyInfo;
import com.bdoemu.gameserver.dataholders.RegionData;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.BuyPartyItemAtItemMarketEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.enums.ENotifyItemMarketPartyInfoType;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.world.region.templates.RegionTemplate;

public class CMBuyItemAtItemMarketByParty extends ReceivablePacket<GameClient> {
    private long itemMarketObjId;
    private long price;
    private long objId;
    private int itemId;
    private int enchantLevel;
    private int npcGameObjId;
    private boolean isBuyFromParty;
    private EItemStorageLocation storageLocation;

    public CMBuyItemAtItemMarketByParty(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.itemMarketObjId = this.readQ();
        this.itemId = this.readHD();
        this.enchantLevel = this.readHD();
        this.npcGameObjId = this.readD();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.objId = this.readQ();
        this.price = this.readQ();
        this.isBuyFromParty = this.readCB();
        this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            int regionId = 0;
            if (!ItemMarketConfig.ITEM_MARKET_BUY_ENABLED) {
                this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoAuctionNotBuy, this.opCode));
                return;
            }
            PartyItemMarket partyItemMarket = null;
            if (this.isBuyFromParty) {
                final IParty<Player> party = player.getParty();
                if (party != null) {
                    partyItemMarket = party.getPartyInventory().get(this.itemMarketObjId);
                    if (partyItemMarket != null && partyItemMarket.getPartyId() != party.getPartyId()) {
                        return;
                    }
                }
                regionId = player.getRegionId();
                if (!player.getRegion().getRegionType().isMinorTown() && !player.getRegion().getRegionType().isMainTown()) {
                    for (final RegionTemplate template : RegionData.getInstance().getTemplateByTerritoryKey(player.getRegion().getTemplate().getTerritoryKey())) {
                        if (template.getRegionType().isMainTown()) {
                            regionId = template.getRegionId();
                            break;
                        }
                    }
                }
            } else {
                final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
                if (npc == null) {
                    return;
                }
                final CreatureFunctionT function = npc.getTemplate().getCreatureFunctionT();
                if (function == null || function.getTerritoryKeyForItemMarket() == null) {
                    return;
                }
                regionId = npc.getRegionId();
                partyItemMarket = ItemMarketService.getInstance().getPartyItem(this.itemMarketObjId);
            }
            if (partyItemMarket == null) {
                player.sendPacket(new SMNotifyItemMarketByPartyInfo(this.itemId, this.enchantLevel, this.itemMarketObjId, 1L, ENotifyItemMarketPartyInfoType.Clear));
                player.sendPacket(new SMNak(EStringTable.eErrNoAlreadySoldOutItemAtItemMarket, this.opCode));
                return;
            }
            player.getPlayerBag().onEvent(new BuyPartyItemAtItemMarketEvent(player, partyItemMarket, this.price, this.storageLocation, regionId));
        }
    }
}
