// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card.events;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMResultMentalGame;
import com.bdoemu.core.network.sendable.SMTryCard;
import com.bdoemu.core.network.sendable.SMUpdateCurrentState;
import com.bdoemu.gameserver.model.creature.npc.card.Card;
import com.bdoemu.gameserver.model.creature.npc.card.CardGame;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGameState;
import com.bdoemu.gameserver.model.creature.npc.card.templates.CardTemplate;
import com.bdoemu.gameserver.model.creature.player.Player;

public class TryCardEvent implements ICardEvent {
    private Player player;
    private CardGame game;

    public TryCardEvent(final Player player, final CardGame game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void onEvent() {
        final Integer step = this.game.getZodiacSignT().getSteps()[this.game.getTryCardIndex()];
        if (step == null) {
            int intimacy = 0;
            if (this.game.getCurrentReqValue() >= this.game.getReqValue()) {
                if (this.game.getIntimacy() == 0) {
                    switch (this.game.getGameCount()) {
                        case 1: {
                            intimacy = Rnd.get(13, 14);
                            break;
                        }
                        case 2: {
                            intimacy = Rnd.get(33, 44);
                            break;
                        }
                        case 3: {
                            intimacy = Rnd.get(37, 57);
                            break;
                        }
                    }
                    final int value = (int) (intimacy * ((100.0f + this.player.getGameStats().getIntimacyRate().getIntMaxValue() / 10000.0f) / 100.0f));
                    this.game.addIntimacy(value);
                }
            } else {
                this.game.setIntimacy(intimacy);
                this.game.setTotalIntimacy(intimacy);
            }
            this.game.setState(ECardGameState.ReadyToReward);
            this.player.sendPacket(new SMResultMentalGame(this.game));
        } else {
            final Card card = this.game.getCards()[step];
            final CardTemplate template = card.getTemplate();
            int simpathy = Rnd.get(template.getMinDd(), template.getMaxDd());
            int interest = template.getHit();
            for (int i = 0; i < this.game.getNeedCount(); ++i) {
                final Card bonusCard = this.game.getCards()[step];
                final CardTemplate bonusTemplate = bonusCard.getTemplate();
                final int startIndex = i + bonusTemplate.getApplyTurn();
                if (this.game.getTryCardIndex() >= startIndex && this.game.getTryCardIndex() < startIndex + bonusTemplate.getValidTurn()) {
                    final Integer buffType = bonusTemplate.getBuffType();
                    if (buffType != null) {
                        switch (buffType) {
                            case 0: {
                                interest += bonusTemplate.getVariedValue();
                                break;
                            }
                            case 1: {
                                simpathy += bonusTemplate.getVariedValue();
                                break;
                            }
                        }
                    }
                }
            }
            simpathy -= this.game.getNpcSympathy();
            if (simpathy < 0) {
                simpathy = 0;
            }
            if (interest < 0) {
                interest = 0;
            }
            final int chance = interest * 100 / this.game.getNpcInterest();
            boolean isSuccess = false;
            if (Rnd.getChance(chance)) {
                this.game.setHit(template.getHit());
                this.game.addSimpathy(simpathy);
                isSuccess = true;
            }
            if (isSuccess) {
                this.game.setSuccessReqValue(this.game.getSuccessReqValue() + 1);
            } else {
                this.game.setSuccessReqValue(0);
                this.game.setFailReqValue(this.game.getFailReqValue() + 1);
            }
            switch (this.game.getGameId()) {
                case 1: {
                    if (isSuccess && this.game.getCurrentReqValue() < this.game.getReqValue()) {
                        this.game.setCurrentReqValue(this.game.getCurrentReqValue() + 1);
                        break;
                    }
                    break;
                }
                case 2: {
                    if (isSuccess && this.game.getCurrentReqValue() < this.game.getReqValue()) {
                        this.game.setCurrentReqValue(this.game.getCurrentReqValue() + simpathy);
                        break;
                    }
                    break;
                }
                case 3: {
                    if (isSuccess && this.game.getCurrentReqValue() < this.game.getReqValue() && simpathy > this.game.getCurrentReqValue()) {
                        if (simpathy > this.game.getReqValue()) {
                            simpathy = this.game.getReqValue();
                        }
                        this.game.setCurrentReqValue(simpathy);
                        break;
                    }
                    break;
                }
                case 4: {
                    if (!isSuccess && this.game.getCurrentReqValue() < this.game.getReqValue()) {
                        this.game.setCurrentReqValue(this.game.getCurrentReqValue() + 1);
                        break;
                    }
                    break;
                }
            }
            this.game.setTryCardIndex(this.game.getTryCardIndex() + 1);
            this.player.sendPacket(new SMUpdateCurrentState(this.game));
            this.player.sendPacket(new SMTryCard());
        }
    }

    @Override
    public boolean canAct() {
        return this.player.getMentalCardHandler().getGame() == this.game && this.game.getState().isTryCards();
    }
}
