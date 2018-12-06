package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.core.network.sendable.SMChargeUser;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.ChargeUserStorage;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.packageeffects.AChargeUserEffect;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PremiumChargeUserEffect extends ABuffEffect {
    private static final Logger log = LoggerFactory.getLogger(PremiumChargeUserEffect.class);

    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        if (owner.isPlayer()) {
            final Player player = (Player) owner;
            final ChargeUserStorage chargeUserStorage = player.getAccountData().getChargeUserStorage();
            final ConcurrentLinkedQueue<Integer> addItems = new ConcurrentLinkedQueue<Integer>();
            final Integer[] params = buffTemplate.getParams();

            if (chargeUserStorage == null)
                return null;

            // Fetch the buff and check if it has already been activated.
            try {
                EChargeUserType value = EChargeUserType.values()[params[0]];
                AChargeUserEffect effect = chargeUserStorage.getChargeUserEffect(value);
                Calendar calendar = Calendar.getInstance();

                if (effect == null) {    // Check if we have effect
                    calendar.add(Calendar.MINUTE, params[1]);                                // Add database time to current time
                    effect = value.newChargeUserEffectInstance(calendar.getTimeInMillis());    // Initialize base effect
                    chargeUserStorage.addChargeUserEffect(effect);                            // Add effect to database
                    chargeUserStorage.initPackageEffects(player);                            // Initialize effect and update client
                } else {                // Effect already, exists so we will extend it
                    if (effect.getEndTime() > System.currentTimeMillis())                    // Check if our time is not expired.
                        calendar.setTimeInMillis(effect.getEndTime());                        // Set the effect start time, so we could extend it
                    calendar.add(Calendar.MINUTE, params[1]);                                // Add database time to current time
                    effect.setEndTime(calendar.getTimeInMillis());                            // Apply new time to calendar
                    player.sendPacket(new SMChargeUser(player.getAccountData()));            // Update client data
                }
            } catch (Exception eh) {
                log.warn("EChargeUserType [{}] is not implemented. {}", params[0], eh);
            }
        }
        return null;
    }

    @Override
    public void endEffect(final ActiveBuff activeBuff) {
        final Creature target = activeBuff.getTarget();
        if (target.isPlayer()) {
            final Player player = (Player) target;
            final ChargeUserStorage chargeUserStorage = player.getAccountData().getChargeUserStorage();
            final Integer[] params = activeBuff.getTemplate().getParams();

            if (chargeUserStorage == null)
                return;

            try {
                EChargeUserType value = EChargeUserType.values()[params[0]];
                AChargeUserEffect effect = chargeUserStorage.getChargeUserEffect(value);

                if (effect != null) {
                    effect.endEffect(player);
                    player.sendPacket(new SMChargeUser(player.getAccountData()));
                }
            } catch (Exception eh) {
                log.warn("EChargeUserType [{}] failed to endEffect. {}", params[0], eh);
            }
        }
    }
}
