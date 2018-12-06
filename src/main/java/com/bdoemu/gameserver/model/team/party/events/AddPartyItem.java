package com.bdoemu.gameserver.model.team.party.events;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.core.network.sendable.SMListSellInfoAtItemMarketByParty;
import com.bdoemu.core.network.sendable.SMNotifyItemMarketByPartyInfo;
import com.bdoemu.core.network.sendable.SMPickupDroppedItem;
import com.bdoemu.gameserver.model.creature.DropBag;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.items.enums.EItemGradeType;
import com.bdoemu.gameserver.model.items.enums.ENotifyItemMarketPartyInfoType;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.service.PartyService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AddPartyItem implements IPartyEvent {
    private IParty<Player> party;
    private Player player;
    private Item dropItem;
    private List<Long> winners;
    private DropBag dropBag;
    private long count;
    private int slotIndex;

    public AddPartyItem(final IParty<Player> party, final Player player, final Item dropItem, final DropBag dropBag, final long count, final int slotIndex, final List<Long> winners) {
        this.party = party;
        this.player = player;
        this.dropItem = dropItem;
        this.winners = winners;
        this.dropBag = dropBag;
        this.count = count;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        this.player.sendBroadcastItSelfPacket(new SMPickupDroppedItem(this.player.getGameObjectId(), this.dropBag.getSourceGameObjectId(), this.slotIndex, this.dropBag.isEmpty(), 0, this.dropItem.getCount()));
        while (this.count > 0L) {
            final long price = this.dropItem.getTemplate().getBasePriceForItemMarket() * this.dropItem.getTemplate().getMaxestPercentForItemMarket() / 1000000L;
            final PartyItemMarket itemMarket = PartyItemMarket.newPartyItemMarket(new Item(this.dropItem, 1L, price), 1, this.party.getPartyId(), this.winners);
            ItemMarketService.getInstance().putPartyItem(itemMarket);
            this.party.getPartyInventory().put(itemMarket.getMarketObjectId(), itemMarket);
            for (final Player partyMember : this.party.getMembers()) {
                partyMember.sendPacket(new SMNotifyItemMarketByPartyInfo(itemMarket.getItemId(), itemMarket.getEnchantLevel(), itemMarket.getMarketObjectId(), itemMarket.getCount(), ENotifyItemMarketPartyInfoType.Register));
                final Collection<PartyItemMarket> partyItems = this.party.getPartyInventory().values().stream().filter(partyItemMarket -> partyItemMarket.containWinner(partyMember.getAccountId())).collect(Collectors.toList());
                if (!partyItems.isEmpty()) {
                    final ListSplitter<PartyItemMarket> splitterItems = new ListSplitter<>(partyItems, 739);
                    while (splitterItems.hasNext()) {
                        partyMember.sendPacket(new SMListSellInfoAtItemMarketByParty(splitterItems.getNext(), splitterItems.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update, 1, partyMember));
                    }
                } else {
                    this.player.sendPacket(new SMListSellInfoAtItemMarketByParty(partyItems, EPacketTaskType.Add, 0, this.player));
                }
            }
            --this.count;
        }
    }

    @Override
    public boolean canAct() {
        final EItemGradeType itemGradeType = this.party.getDistributionItemGrade().getItemGradeType();
        return (!itemGradeType.isWhite() || this.party.getDistributionPrice() >= 1000000L) && (itemGradeType.isWhite() || this.dropItem.getTemplate().getGradeType().ordinal() >= itemGradeType.ordinal()) && (this.party.getDistributionPrice() < 1000000L || this.dropItem.getTemplate().getOriginalPrice() >= this.party.getDistributionPrice()) && PartyService.getInstance().contains(this.party) && this.player.getParty() == this.party && this.dropBag.decrease(this.slotIndex, this.count);
    }
}