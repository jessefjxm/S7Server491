// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.theme;

import com.bdoemu.core.network.sendable.SMCreateCard;
import com.bdoemu.gameserver.dataholders.ThemeData;
import com.bdoemu.gameserver.model.creature.npc.card.Card;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.npc.card.templates.CardTemplate;
import com.bdoemu.gameserver.model.creature.npc.theme.templates.ThemeTemplate;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.concurrent.ConcurrentHashMap;

public class Theme {
    private final ConcurrentHashMap<Integer, Card> cards;
    private final ThemeTemplate template;

    public Theme(final ThemeTemplate template) {
        this.cards = new ConcurrentHashMap<Integer, Card>();
        this.template = template;
    }

    public static Theme newTheme(final int themeId) {
        final ThemeTemplate template = ThemeData.getInstance().getTemplate(themeId);
        if (template == null) {
            return null;
        }
        return new Theme(template);
    }

    public void addCard(final Card card) {
        this.cards.put(card.getCardId(), card);
    }

    public Card updateCard(final Player player, final CardTemplate cardTemplate, final ECardGradeType cardGradeType) {
        final Card card = new Card(cardTemplate, cardGradeType);
        if (this.cards.putIfAbsent(card.getCardId(), card) != null) {
            return null;
        }
        player.getObserveController().notifyObserver(EObserveType.collectKnowledge, card.getCardId(), player.getMentalCardHandler().getCardsCount());
        player.sendPacket(new SMCreateCard(card));
        return card;
    }

    public int getWpBonus() {
        final int cardSize = this.cards.size();
        int wpBonus = 0;
        if (cardSize >= this.template.getNeedCount2()) {
            wpBonus = this.template.getIncreaseWP2();
        } else if (cardSize >= this.template.getNeedCount()) {
            wpBonus = this.template.getIncreaseWP();
        }
        return wpBonus;
    }

    public boolean isFull() {
        return this.cards.size() >= this.template.getNeedCount2();
    }

    public ConcurrentHashMap<Integer, Card> getCards() {
        return this.cards;
    }

    public boolean containsCard(final int cardId) {
        return this.cards.containsKey(cardId);
    }

    public boolean removeCard(final int cardId) {
        return this.cards.remove(cardId) != null;
    }

    public ThemeTemplate getTemplate() {
        return this.template;
    }

    public int getThemeId() {
        return this.template.getThemeId();
    }
}
