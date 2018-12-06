package com.bdoemu.gameserver.model.skills.services;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.dataholders.BuffData;
import com.bdoemu.gameserver.dataholders.SkillData;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.creature.player.enums.EquipType;
import com.bdoemu.gameserver.model.creature.player.itemPack.ADBItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RemoveItemEvent;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.VaryEquipItemEnduranceEvent;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.team.party.IParty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SkillService {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(SkillService.class);
    }

    public static List<ActiveBuff> useSkill(final Creature owner, final int skillId, final List<Integer> buffIndexList, final Collection<Creature> targets) {
        return useSkill(owner, SkillData.getInstance().getTemplate(skillId), buffIndexList, targets);
    }

    private static boolean hasCoolTime(final Creature owner, final SkillT skillT) {
        if (!owner.isPlayer() || !skillT.getSkillOwnerType().isCharacter()) {
            return false;
        }
        final Player player = (Player) owner;
        final int reuseCycle = skillT.getReuseCycle();
        if (reuseCycle > 0) {
            if (!skillT.isUsableInCoolTime() && player.getCoolTimeList().hasSkillCoolTime(skillT.getSkillId())) {
                return true;
            }
            player.getCoolTimeList().addSkillCoolTime(skillT.getSkillId(), reuseCycle);
            final int reuseGroup = skillT.getReuseGroup();
            if (reuseGroup > 0 && !player.getCoolTimeList().addItemCoolTime(reuseGroup, reuseCycle)) {
                return true;
            }
        }
        return false;
    }

    private static boolean canUse(final Creature owner, final SkillT skillT) {
        if (!owner.isPlayable()) {
            return true;
        }
        final Playable playable = (Playable) owner;
        final EquipType equipType = skillT.getRequireEquipType();
        if (!equipType.isNone()) {
            final EEquipSlot auxSlot = equipType.getAuxSlot();
            if (auxSlot != null) {
                final ADBItemPack equipments = playable.getEquipments();
                final Item item = equipments.getItem(auxSlot.getId());
                if (item == null || item.getTemplate().getEquipType() != equipType) {
                    return false;
                }
            }
        }
        if (skillT.getNeedItemId() > 0) {
            final Item item2 = playable.getInventory().getItemById(skillT.getNeedItemId());
            if (item2 == null || item2.getCount() < skillT.getNeedItemCount()) {
                return false;
            }
        }
        return true;
    }

    public static List<ActiveBuff> useSkill(Creature owner, SkillT skillT, List<Integer> buffIndexList, Collection<Creature> targets) {
        //if (owner.isPlayer())
        //	System.out.println("useSkill#1 => " + owner.getName() + ", SkillID: " + skillT.getSkillId());
        if (skillT == null)
            return null;

        if (targets.size() > skillT.getApplyNumber())
            targets = targets.stream().limit(skillT.getApplyNumber()).collect(Collectors.toList());

        if (owner.isPlayer()) {
            final Player player = (Player) owner;
            if (!skillT.canUse(player.getClassType())) {
                return null;
            }
            if (skillT.getWeaponEnduranceDecreaseRate() > 0 && owner.getRegion() != null && !owner.getRegion().getRegionType().isArena()) {
                int enduranceDecreaseRate = skillT.getWeaponEnduranceDecreaseRate() / 10000;
                final int enduranceReduceStatValue = player.getGameStats().getEnduranceReduceStat().getIntValue() / 10000;
                if (enduranceReduceStatValue > 0) {
                    enduranceDecreaseRate -= enduranceDecreaseRate * enduranceReduceStatValue / 100;
                }
                if (Rnd.getChance(enduranceDecreaseRate)) {
                    player.getPlayerBag().onEvent(new VaryEquipItemEnduranceEvent(player, EEquipSlot.rightHand.getId(), -1));
                }
            }
        }
        final List<Integer> buffs = skillT.getBuffs();
        final List<ActiveBuff> applyBuffs = new ArrayList<ActiveBuff>();
        if (buffIndexList != null) {
            for (final int buffIndex : buffIndexList) {
                if (buffIndex > buffs.size() - 1) { // 1574, buf index 6, exists, but throws error? lol
                    SkillService.log.warn("Wrong buffIndex: {} for skillId: {} ", buffIndex, skillT.getSkillId());
                } else {
                    final Collection<ActiveBuff> _applyBuffs = applyBuff(owner, targets, skillT, buffs.get(buffIndex));
                    if (_applyBuffs == null) {
                        continue;
                    }
                    applyBuffs.addAll(_applyBuffs);
                }
            }
        } else {
            for (final int buffId : buffs) {
                final Collection<ActiveBuff> _applyBuffs = applyBuff(owner, targets, skillT, buffId);
                if (_applyBuffs != null) {
                    applyBuffs.addAll(_applyBuffs);
                }
            }
        }
        return applyBuffs;
    }

    public static void applySkill(final Creature owner, final SkillT skillT, final Collection<? extends Creature> targets) {
        //if (owner.isPlayer())
        //	System.out.println("useSkill#2 => " + owner.getName() + ", SkillID: " + skillT.getSkillId());

        final List<Integer> buffs = skillT.getBuffs();
        for (final int buffId : buffs) {
            final BuffTemplate buffTemplate = BuffData.getInstance().getTemplate(buffId);
            if (buffTemplate != null) {
                final ABuffEffect effect = buffTemplate.getModuleType().getEffect();
                if (effect == null) {
                    continue;
                }
                final Collection<ActiveBuff> activeBuffs = effect.initEffect(owner, targets, skillT, buffTemplate);
                if (activeBuffs == null) {
                    continue;
                }
                activeBuffs.forEach(ActiveBuff::startEffect);
            }
        }
    }

    private static Collection<ActiveBuff> applyBuff(final Creature owner, Collection<Creature> targets, final SkillT skillT, final int buffId) {
        //if (owner.isPlayer())
        //	System.out.println("useSkill#3 => " + owner.getName() + ", SkillID: " + skillT.getSkillId());
        final BuffTemplate buffTemplate = BuffData.getInstance().getTemplate(buffId);
        Collection<ActiveBuff> activeBuffs = null;
        if (buffTemplate != null && Rnd.getChance(buffTemplate.getApplyRate(), 10000)) {
            //if (owner.isPlayer())
            //    System.out.println("Activating buff " + buffTemplate.getBuffId());
            final ABuffEffect effect = buffTemplate.getModuleType().getEffect();
            if (effect != null) {
                if (buffTemplate.isApplyToGroup() && owner.isPlayer()) {
                    final IParty<Player> party = ((Player) owner).getParty();
                    if (party != null) {
                        targets = party.getMembers().stream().filter(p -> KnowList.knowObject(owner, p)).collect(Collectors.toList());
                    }
                }
                if (targets.isEmpty()) {
                    ActiveBuff alreadyActiveBuff = owner.getBuffList().getBuff(buffTemplate.getBuffId());
                    if (alreadyActiveBuff != null) { // test cash coupons with extension and cc.
                        alreadyActiveBuff.unapplyEffect();
                        owner.getBuffList().removeActiveBuff(alreadyActiveBuff);
                    }
                    targets = Collections.singletonList(owner);
                }
                activeBuffs = effect.initEffect(owner, targets, skillT, buffTemplate);
                if (activeBuffs != null) {
                    activeBuffs.forEach(ActiveBuff::startEffect);
                }
            }
        }
        return activeBuffs;
    }

    public static boolean executeSkill(final Player player, final int skillId) {
        //if (player.isPlayer())
        //	System.out.println("useSkill#4 => " + player.getName() + ", SkillID: " + skillId);

        final SkillT skillT = SkillData.getInstance().getTemplate(skillId);
        if (!canUse(player, skillT)) {
            player.getActionStorage().onActionError(2524986171L, EStringTable.NONE);
            return false;
        }
        if (hasCoolTime(player, skillT)) {
            player.getActionStorage().onActionError(2524986171L, EStringTable.eErrNoSkillIsSelfReuseCycle);
            return false;
        }
        final int reqMp = skillT.getRequireMP();
        if (reqMp != 0 && !player.getGameStats().getMp().addMP(-reqMp)) {
            player.getActionStorage().onActionError(2524986171L, EStringTable.eErrNoActorMpIsLack);
            return false;
        }
        if (skillT.getNeedItemId() > 0 && !player.getPlayerBag().onEvent(new RemoveItemEvent(player, skillT.getNeedItemId(), 0, skillT.getNeedItemCount()))) {
            player.getActionStorage().onActionError(2524986171L, EStringTable.NONE);
            return false;
        }
        return true;
    }
}
