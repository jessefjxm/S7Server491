// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.dataholders.ClassBalanceData;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.HpStat;
import com.bdoemu.gameserver.model.stats.MpStat;

import java.util.Collection;

public class HPAddPercentageEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final Integer[] params = buffTemplate.getParams();
        final int ownerType = params[0];
        final Integer ownerHpPercentage = params[1];
        final Integer ownerMpPercentage = params[2];
        this.applyEffect(owner, owner, ownerType, ownerHpPercentage, ownerMpPercentage);
        final int type = params[3];
        final Integer hpPercentage = params[4];
        final Integer mpPercentage = params[5];
        for (final Creature target : targets) {
            this.applyEffect(owner, target, type, hpPercentage, mpPercentage);
        }
        return null;
    }

    private void applyEffect(final Creature owner, final Creature target, final int type, final Integer hpPercentage, final Integer mpPercentage) {
        final HpStat hpStat = owner.getGameStats().getHp();
        final MpStat mpStat = owner.getGameStats().getMp();
        float hp = 0.0f;
        int mp = 0;
        switch (type) {
            case 0: {
                if (hpPercentage != null) {
                    hp = target.getGameStats().getMp().getMaxMp() * (hpPercentage / 10000.0f) / 100.0f;
                    final float ownerHp = (int) (hpStat.getMaxHp() * (-hpPercentage / 10000.0f) / 100.0f);
                    owner.getGameStats().getHp().updateHp(ownerHp, null);
                }
                if (mpPercentage != null) {
                    mp = (int) (target.getGameStats().getMp().getMaxMp() * (mpPercentage / 10000.0f) / 100.0f);
                    final int ownerMp = (int) (mpStat.getMaxMp() * (-mpPercentage / 10000.0f) / 100.0f);
                    owner.getGameStats().getMp().updateMp(ownerMp);
                    break;
                }
                break;
            }
            case 2: {
                if (hpPercentage != null) {
                    hp = hpStat.getMaxHp() * (hpPercentage / 10000.0f) / 100.0f;
                }
                if (mpPercentage != null) {
                    mp = (int) (hpStat.getMaxHp() * (mpPercentage / 10000.0f) / 100.0f);
                    break;
                }
                break;
            }
            case 3: {
                if (hpPercentage != null)
                    hp = mpStat.getMaxMp() * (hpPercentage / 10000.0f) / 100.0f;

                if (mpPercentage != null) {
                    mp = (int) (mpStat.getMaxMp() * (mpPercentage / 10000.0f) / 100.0f);
                    break;
                }
                break;
            }
            case 6: {
                if (hpPercentage != null) {
                    hp = target.getGameStats().getHp().getMaxHp() * (hpPercentage / 10000.0f) / 100.0f;

                    if (target.isPlayer())
                        hp *= 0.25;
                }

                if (mpPercentage != null) {
                    mp = (int) (target.getGameStats().getMp().getMaxMp() * (mpPercentage / 10000.0f) / 100.0f);

                    if (target.isPlayer())
                        mp *= 0.25;
                }
                break;
            }
        }
        if (hpPercentage != null) {
            if (owner.isPlayer() && target.isPlayer()) {
                float classBalanceRate = ClassBalanceData.getInstance().getClassBalance(((Player) owner).getClassType(), ((Player) target).getClassType());
                if (classBalanceRate <= 0)
                    classBalanceRate = 0.25f;

                hp *= classBalanceRate;
            }

            target.getGameStats().getHp().addHP(hp, owner);
        }
        if (mpPercentage != null) {
            target.getGameStats().getMp().addMP(mp);
        }
    }
}
