// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card;

import com.bdoemu.gameserver.dataholders.CardData;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.npc.card.templates.CardTemplate;

public class Card {
    private final CardTemplate template;
    private ECardGradeType cardGradeType;

    public Card(final CardTemplate template, final ECardGradeType cardGradeType) {
        this.template = template;
        this.cardGradeType = cardGradeType;
    }

    public static Card newCard(final int cardId, final ECardGradeType cardGradeType) {
        final CardTemplate template = CardData.getInstance().getTemplate(cardId);
        if (template == null) {
            return null;
        }
        return new Card(template, cardGradeType);
    }

    public int getMainTheme() {
        return this.template.getMainTheme();
    }

    public int getCardId() {
        return this.template.getCardId();
    }

    public ECardGradeType getCardGradeType() {
        return this.cardGradeType;
    }

    public CardTemplate getTemplate() {
        return this.template;
    }
}
