// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card;

import com.bdoemu.commons.concurrent.CloseableReentrantLock;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.dataholders.ZodiacSignData;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGameState;
import com.bdoemu.gameserver.model.creature.npc.card.events.ICardEvent;
import com.bdoemu.gameserver.model.creature.npc.card.templates.ZodiacSignT;
import com.bdoemu.gameserver.model.creature.npc.templates.PersonalityT;
import com.bdoemu.gameserver.model.creature.npc.theme.Theme;
import com.bdoemu.gameserver.model.creature.npc.theme.templates.ThemeTemplate;

public class CardGame {
    protected final CloseableReentrantLock lock;
    private final PersonalityT personalityT;
    private final Theme theme;
    private final int needCount;
    private final int creatureId;
    private final int sessionId;
    private int gameCount;
    private int npcInterest;
    private int npcSympathy;
    private int gameId;
    private Card[] cards;
    private int tryCardIndex;
    private int totalSimpathy;
    private int currentSimpathy;
    private int intimacy;
    private int totalIntimacy;
    private int currentReqValue;
    private int reqValue;
    private int successReqValue;
    private int failReqValue;
    private int hit;
    private ZodiacSignT zodiacSignT;
    private ECardGameState state;

    public CardGame(final PersonalityT personalityT, final Theme theme, final int needCount, final int creatureId, final int sessionId) {
        this.gameCount = 1;
        this.cards = new Card[8];
        this.lock = new CloseableReentrantLock();
        this.state = ECardGameState.SelectCards;
        this.personalityT = personalityT;
        this.theme = theme;
        this.needCount = needCount;
        this.creatureId = creatureId;
        this.sessionId = sessionId;
        this.npcInterest = Rnd.get(personalityT.getMinPv(), personalityT.getMaxPv());
        this.npcSympathy = Rnd.get(personalityT.getMinDv(), personalityT.getMaxDv());
        this.zodiacSignT = ZodiacSignData.getInstance().getTemplate(personalityT.getZodiacIndex());
        this.initNewGame();
    }

    public final void initNewGame() {
        switch (this.gameId = Rnd.get(0, 4)) {
            case 0: {
                this.currentReqValue = 1;
                this.reqValue = 1;
                break;
            }
            case 1: {
                this.reqValue = Rnd.get(1, 2);
                break;
            }
            case 2: {
                this.reqValue = Rnd.get(20, 109);
                break;
            }
            case 3: {
                this.reqValue = Rnd.get(20, 109);
                break;
            }
            case 4: {
                this.reqValue = Rnd.get(1, 3);
                break;
            }
        }
    }

    public boolean onEvent(final ICardEvent event) {
        boolean result = false;
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            if (event.canAct()) {
                result = true;
                event.onEvent();
            }
        }
        return result;
    }

    public ECardGameState getState() {
        return this.state;
    }

    public void setState(final ECardGameState state) {
        this.state = state;
    }

    public ZodiacSignT getZodiacSignT() {
        return this.zodiacSignT;
    }

    public int getSessionId() {
        return this.sessionId;
    }

    public int getCreatureId() {
        return this.creatureId;
    }

    public int getHit() {
        return this.hit;
    }

    public void setHit(final int hit) {
        this.hit = hit;
    }

    public int getSuccessReqValue() {
        return this.successReqValue;
    }

    public void setSuccessReqValue(final int successReqValue) {
        this.successReqValue = successReqValue;
    }

    public int getFailReqValue() {
        return this.failReqValue;
    }

    public void setFailReqValue(final int failReqValue) {
        this.failReqValue = failReqValue;
    }

    public int getReqValue() {
        return this.reqValue;
    }

    public int getCurrentReqValue() {
        return this.currentReqValue;
    }

    public void setCurrentReqValue(final int currentReqValue) {
        this.currentReqValue = currentReqValue;
    }

    public int getNpcInterest() {
        return this.npcInterest;
    }

    public int getNpcSympathy() {
        return this.npcSympathy;
    }

    public int getGameId() {
        return this.gameId;
    }

    public int getGameCount() {
        return this.gameCount;
    }

    public int getTotalIntimacy() {
        return this.totalIntimacy;
    }

    public void setTotalIntimacy(final int totalIntimacy) {
        this.totalIntimacy = totalIntimacy;
    }

    public void addIntimacy(final int intimacy) {
        this.intimacy += intimacy;
        this.totalIntimacy += intimacy;
    }

    public int getIntimacy() {
        return this.intimacy;
    }

    public void setIntimacy(final int intimacy) {
        this.intimacy = intimacy;
    }

    public void addSimpathy(final int value) {
        this.totalSimpathy += value;
        this.currentSimpathy = value;
    }

    public int getCurrentSimpathy() {
        return this.currentSimpathy;
    }

    public int getTotalSimpathy() {
        return this.totalSimpathy;
    }

    public void restartGame() {
        this.state = ECardGameState.SelectCards;
        this.intimacy = 0;
        this.tryCardIndex = 0;
        this.currentSimpathy = 0;
        this.totalSimpathy = 0;
        this.currentReqValue = 0;
        this.reqValue = 0;
        this.successReqValue = 0;
        this.failReqValue = 0;
        this.cards = new Card[8];
        ++this.gameCount;
        this.initNewGame();
    }

    public int getTryCardIndex() {
        return this.tryCardIndex;
    }

    public void setTryCardIndex(final int tryCardIndex) {
        this.tryCardIndex = tryCardIndex;
    }

    public Theme getTheme() {
        return this.theme;
    }

    public ThemeTemplate getThemeTemplate() {
        return this.theme.getTemplate();
    }

    public int getNeedCount() {
        return this.needCount;
    }

    public int getZodiacIndex() {
        return this.personalityT.getZodiacIndex();
    }

    public Card[] getCards() {
        return this.cards;
    }
}
