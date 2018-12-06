package com.bdoemu.gameserver.model.creature.player.mentalcard;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMReadyMentalGame;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMReadyMentalGame;
import com.bdoemu.core.network.sendable.SMRemoveCard;
import com.bdoemu.core.network.sendable.SMUpdateCurrentState;
import com.bdoemu.gameserver.dataholders.CardData;
import com.bdoemu.gameserver.model.creature.npc.card.Card;
import com.bdoemu.gameserver.model.creature.npc.card.CardGame;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.npc.card.templates.CardTemplate;
import com.bdoemu.gameserver.model.creature.npc.templates.PersonalityT;
import com.bdoemu.gameserver.model.creature.npc.theme.Theme;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.EGamePointType;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.*;

public class MentalCardHandler extends JSONable {
    private CardGame game;
    private Map<Integer, Theme> themes;
    private Player player;

    public MentalCardHandler(final Player player) {
        this.themes = new HashMap<>();
        this.player = player;
    }

    public MentalCardHandler(final Player player, final BasicDBObject dbObject) {
        this.themes = new HashMap<>();
        this.player = player;
        final BasicDBList intimacyListDB = (BasicDBList) dbObject.get("mentalCardList");
        for (final Object anIntimacyListDB : intimacyListDB) {
            final BasicDBObject intimacyDB = (BasicDBObject) anIntimacyListDB;
            final Card card = Card.newCard(intimacyDB.getInt("cardId"), ECardGradeType.valueOf(intimacyDB.getString("cardGradeType")));
            if (card != null) {
                if (!this.themes.containsKey(card.getMainTheme())) {
                    this.themes.put(card.getMainTheme(), Theme.newTheme(card.getMainTheme()));
                }
                final Theme theme = this.themes.get(card.getMainTheme());
                theme.addCard(card);
            }
        }
    }

    private static int getWpForIntimacy(final int intimacy) {
        if (intimacy > 1000) {
            return 15;
        }
        if (intimacy > 700) {
            return 10;
        }
        if (intimacy > 500) {
            return 8;
        }
        if (intimacy > 300) {
            return 6;
        }
        if (intimacy > 100) {
            return 4;
        }
        if (intimacy > 0) {
            return 2;
        }
        return 0;
    }

    public synchronized void newGame(final PersonalityT personalityT, final int npcId, final int sessionId) {
        if (this.game != null) {
            return;
        }
        final Integer intimacy = this.player.getIntimacyHandler().getIntimacyMap().get(npcId);
        if (intimacy == null) {
            return;
        }
        final int wp = getWpForIntimacy(intimacy);
        if (wp == 0) {
            return;
        }
        final Theme theme = this.themes.get(personalityT.getTheme1());
        final int count = personalityT.getCount1();
        if (theme.getCards().size() < count) {
            return;
        }
        if (this.player.addWp(-wp)) {
            final CardGame newGame = new CardGame(personalityT, theme, count, npcId, sessionId);
            this.player.sendPacket(new SMReadyMentalGame(theme.getThemeId(), count));
            this.player.sendPacket(new SMUpdateCurrentState(newGame));
            this.game = newGame;
        } else {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoMentalNotEnoughWp, CMReadyMentalGame.class));
        }
    }

    public CardGame getGame() {
        return this.game;
    }

    public void setGame(final CardGame game) {
        this.game = game;
    }

    public Map<Integer, Theme> getThemes() {
        return this.themes;
    }

    public Collection<Card> getCards() {
        final List<Card> cards = new ArrayList<Card>();
        for (final Theme theme : this.themes.values()) {
            cards.addAll(theme.getCards().values());
        }
        return cards;
    }

    public int getCardsCount() {
        return this.themes.values().stream().mapToInt(item -> item.getCards().values().size()).sum();
    }

    public Theme getTheme(final int themeId) {
        return this.themes.get(themeId);
    }

    public boolean removeMentalCard(final int cardId) {
        final CardTemplate cardTemplate = CardData.getInstance().getTemplate(cardId);
        if (cardTemplate == null) {
            return false;
        }
        final int themeId = cardTemplate.getMainTheme();
        final Theme theme = this.themes.get(themeId);
        if (theme == null || !theme.removeCard(cardId)) {
            return false;
        }
        this.player.sendPacket(new SMRemoveCard(cardId));
        this.player.getObserveController().notifyObserver(EObserveType.acquirePoint, EGamePointType.WP.ordinal(), this.player.getMaxWp());
        return true;
    }

    public void updateMentalCard(final int cardId, final ECardGradeType cardGradeType) {
        final CardTemplate cardTemplate = CardData.getInstance().getTemplate(cardId);
        if (cardTemplate == null) {
            return;
        }
        final int themeId = cardTemplate.getMainTheme();
        synchronized (this.themes) {
            if (!this.themes.containsKey(themeId)) {
                this.themes.put(themeId, Theme.newTheme(themeId));
            }
            final Theme theme = this.themes.get(themeId);
            final Card card = theme.updateCard(this.player, cardTemplate, cardGradeType);
            if (card != null) {
                this.player.getObserveController().notifyObserver(EObserveType.acquirePoint, EGamePointType.WP.ordinal(), this.player.getMaxWp());
            }
        }
    }

    public int getWpBonus() {
        int bonus = 0;
        for (final Theme theme : this.themes.values()) {
            bonus += theme.getWpBonus();
        }
        return bonus;
    }

    public boolean containsCard(final int cardId) {
        final CardTemplate cardTemplate = CardData.getInstance().getTemplate(cardId);
        if (cardTemplate == null) {
            return false;
        }
        final Theme theme = this.themes.get(cardTemplate.getMainTheme());
        return theme != null && theme.containsCard(cardId);
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList mentalCardListDB = new BasicDBList();
        for (final Theme theme : this.themes.values()) {
            for (final Card card : theme.getCards().values()) {
                final BasicDBObject obj = new BasicDBObject();
                obj.append("cardId", card.getCardId());
                obj.append("cardGradeType", card.getCardGradeType().name());
                mentalCardListDB.add(obj);
            }
        }
        builder.append("mentalCardList", mentalCardListDB);
        return builder.get();
    }
}
