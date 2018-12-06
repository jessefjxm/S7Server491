// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.ItemMarketConfig;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.receivable.CMBuyItemAtItemMarketByParty;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyDiceEndItemMarketByParty;
import com.bdoemu.core.network.sendable.SMNotifyItemMarketByPartyInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.mail.services.MailService;
import com.bdoemu.gameserver.model.items.enums.ENotifyItemMarketPartyInfoType;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.team.PartyItemWinner;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.team.party.service.PartyService;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.List;

public class PartyItemMarket {
    private final PartyItemWinner[] partyItemWinners;
    private Item item;
    private long marketObjectId;
    private long registredDate;
    private long partyId;
    private int territoryKey;

    public PartyItemMarket(final long marketObjectId, final Item item, final int territoryKey, final long partyId, final List<Long> winners) {
        this.partyItemWinners = new PartyItemWinner[5];
        int index = 0;
        for (final Long winner : winners) {
            this.partyItemWinners[index++] = new PartyItemWinner(World.getInstance().getPlayer(winner).getAccountId());
        }
        this.partyId = partyId;
        this.marketObjectId = marketObjectId;
        this.item = item;
        this.territoryKey = territoryKey;
        this.registredDate = GameTimeService.getServerTimeInMillis();
    }

    public static PartyItemMarket newPartyItemMarket(final Item item, final int territoryKey, final long partyId, final List<Long> winners) {
        return new PartyItemMarket(GameServerIDFactory.getInstance().nextId(GSIDStorageType.ItemMarket), item, territoryKey, partyId, winners);
    }

    public PartyItemWinner[] getPartyItemWinners() {
        return this.partyItemWinners;
    }

    public int getPartyItemWinnerSize() {
        int size = 0;
        for (final PartyItemWinner partyItemWinner : this.partyItemWinners) {
            if (partyItemWinner != null) {
                ++size;
            }
        }
        return size;
    }

    public PartyItemWinner getPartyItemWinner(final long accountId) {
        for (final PartyItemWinner partyItemWinner : this.partyItemWinners) {
            if (partyItemWinner != null && partyItemWinner.getAccountId() == accountId) {
                return partyItemWinner;
            }
        }
        return null;
    }

    public boolean containWinner(final long accountId) {
        return this.getPartyItemWinner(accountId) != null;
    }

    public boolean hasNoVoting(final Player player) {
        final IParty<Player> party = player.getParty();
        if (party == null || party.getPartyId() != this.partyId) {
            return true;
        }
        final PartyItemWinner partyItemWinner = this.getPartyItemWinner(player.getAccountId());
        return partyItemWinner == null || partyItemWinner.getDice() == 0;
    }

    public boolean buy(final Player player, final long itemPrice) {
        if (itemPrice != this.getItemPrice()) {
            player.sendPacket(new SMNak(EStringTable.eErrNoPriceIsInvalid, CMBuyItemAtItemMarketByParty.class));
            return false;
        }
        if (!ItemMarketService.getInstance().removeWaitingPartyItem(this) && ItemMarketService.getInstance().removePartyItem(this.getMarketObjectId()) == null) {
            player.sendPacket(new SMNotifyItemMarketByPartyInfo(this.getItemId(), this.getEnchantLevel(), this.getMarketObjectId(), this.getCount(), ENotifyItemMarketPartyInfoType.Clear));
            player.sendPacket(new SMNak(EStringTable.eErrNoAlreadySoldOutItemAtItemMarket, CMBuyItemAtItemMarketByParty.class));
            return false;
        }
        final IParty<Player> party = PartyService.getInstance().getParty(this.partyId);
        if (party != null) {
            party.getPartyInventory().values().remove(this);
        }
        final long pricePerWinner = this.getItemPrice() / this.getPartyItemWinnerSize();
        for (final PartyItemWinner partyItemWinner : this.partyItemWinners) {
            if (partyItemWinner != null) {
                final Player winner = World.getInstance().getPlayerByAccount(partyItemWinner.getAccountId());
                if (winner != null && winner != player) {
                    winner.sendPacket(new SMNotifyItemMarketByPartyInfo(this.getItemId(), this.getEnchantLevel(), this.getMarketObjectId(), this.getCount(), ENotifyItemMarketPartyInfoType.Clear));
                }
                MailService.getInstance().sendMail(partyItemWinner.getAccountId(), -1L, "{3183609639|3119282326}", "{3183609639|4289634830}", "{3183609639|1145746718|643213191|" + this.getItem().getTemplate().getItemName() + "}", new Item(1, pricePerWinner, 0));
            }
        }
        player.sendPacket(new SMNotifyItemMarketByPartyInfo(this.getItemId(), this.getEnchantLevel(), this.getMarketObjectId(), this.getCount(), ENotifyItemMarketPartyInfoType.Buy));
        return true;
    }

    public void dice(final Player player, final boolean isRefuse) {
        final PartyItemWinner myPartyItemWinner = this.getPartyItemWinner(player.getAccountId());
        if (myPartyItemWinner == null || myPartyItemWinner.getDice() != 0) {
            return;
        }
        myPartyItemWinner.setDice(isRefuse ? -1 : Rnd.get(1, 100));
        player.sendPacket(new SMNotifyItemMarketByPartyInfo(this.getItemId(), this.getEnchantLevel(), this.getMarketObjectId(), this.getCount(), ENotifyItemMarketPartyInfoType.Dice));
        PartyItemWinner dicePartyItemWinner = null;
        for (final PartyItemWinner partyItemWinner : this.partyItemWinners) {
            if (partyItemWinner != null) {
                if (partyItemWinner.getDice() == 0) {
                    return;
                }
                if (dicePartyItemWinner == null || partyItemWinner.getDice() > dicePartyItemWinner.getDice()) {
                    dicePartyItemWinner = partyItemWinner;
                }
            }
        }
        if (dicePartyItemWinner != null && dicePartyItemWinner.getDice() > 0 && (ItemMarketService.getInstance().removeWaitingPartyItem(this) || ItemMarketService.getInstance().removePartyItem(this.getMarketObjectId()) != null)) {
            final IParty<Player> party = PartyService.getInstance().getParty(this.partyId);
            if (party != null) {
                party.getPartyInventory().values().remove(this);
            }
            for (final PartyItemWinner partyItemWinner2 : this.partyItemWinners) {
                if (partyItemWinner2 != null) {
                    final Player winner = World.getInstance().getPlayerByAccount(partyItemWinner2.getAccountId());
                    if (winner != null) {
                        winner.sendPacket(new SMNotifyDiceEndItemMarketByParty(this));
                    }
                }
            }
            MailService.getInstance().sendMail(dicePartyItemWinner.getAccountId(), -1L, "{3183609639|235719291}", "{3183609639|2046880306}", "{3183609639|310517342}", new Item(this.getItem(), this.getItem().getCount()));
        }
    }

    public boolean isWaiting() {
        return this.registredDate + ItemMarketConfig.REGISTER_PARTY_ITEM_WAITING_TICK > System.currentTimeMillis();
    }

    public synchronized boolean addCount(final long count) {
        return this.item.addCount(count);
    }

    public int getTerritoryKey() {
        return this.territoryKey;
    }

    public long getPartyId() {
        return this.partyId;
    }

    public long getRegisteredDate() {
        return this.registredDate;
    }

    public long getItemPrice() {
        long percentage = (System.currentTimeMillis() - this.registredDate) / 600000L * 3L;
        if (percentage > 50L) {
            percentage = 50L;
        }
        return this.item.getItemPrice() * (100L - percentage) / 100L;
    }

    public long getMarketObjectId() {
        return this.marketObjectId;
    }

    public Item getItem() {
        return this.item;
    }

    public int getItemId() {
        return this.item.getItemId();
    }

    public int getEnchantLevel() {
        return this.item.getEnchantLevel();
    }

    public long getCount() {
        return this.item.getCount();
    }

    public ItemTemplate getTemplate() {
        return this.item.getTemplate();
    }
}
