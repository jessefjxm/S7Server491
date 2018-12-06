package com.bdoemu.gameserver.scripts.ai;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.model.ai.deprecated.*;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.actions.enums.*;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.misc.enums.ETradeCommerceType;
import com.bdoemu.gameserver.model.weather.enums.EWeatherFactorType;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author H1X4
 */

@SuppressWarnings("all")
@IAIName("m0056_opinhunter")
public class Ai_m0056_opinhunter extends CreatureAI {
	public Ai_m0056_opinhunter(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && getTargetCharacterKey(object) == 23759 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && (getTargetCharacterKey(object) == 23755 || getTargetCharacterKey(object) == 23756 || getTargetCharacterKey(object) == 23757 || getTargetCharacterKey(object) == 23758) && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target2(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && getTargetCharacterKey(object) == 23751 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target2(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && getTargetCharacterKey(object) == 23751 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait2(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && (getTargetCharacterKey(object) == 23755 || getTargetCharacterKey(object) == 23756 || getTargetCharacterKey(object) == 23757 || getTargetCharacterKey(object) == 23758) && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait2(0.3)))
				return;
		}
		changeState(state -> Wait_End(blendTime));
	}

	protected void Wait_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x77AF5655L /*Wait_End*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Weapon_In(0.8)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_End(blendTime), 3000));
	}

	protected void Weapon_In(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x626F781FL /*Weapon_In*/);
		doAction(1891792052L /*WEAPON_IN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && getTargetCharacterKey(object) == 23759 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && (getTargetCharacterKey(object) == 23755 || getTargetCharacterKey(object) == 23756 || getTargetCharacterKey(object) == 23757 || getTargetCharacterKey(object) == 23758) && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target2(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && getTargetCharacterKey(object) == 23751 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target2(0.3)))
				return;
		}
		if(Rnd.getChance(20)) {
			if (changeState(state -> Wait_Talk(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000 + Rnd.get(-500,500)));
	}

	protected void Wait_Talk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x808413F5L /*Wait_Talk*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && getTargetCharacterKey(object) == 23759 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && (getTargetCharacterKey(object) == 23755 || getTargetCharacterKey(object) == 23756 || getTargetCharacterKey(object) == 23757 || getTargetCharacterKey(object) == 23758) && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target2(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && getTargetCharacterKey(object) == 23751 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target2(0.3)))
				return;
		}
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000 + Rnd.get(-500,500)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Detect_Target2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x765A52E7L /*Detect_Target2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait2(blendTime), 1000));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		clearAggro(true);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && (getTargetCharacterKey(object) == 23755 || getTargetCharacterKey(object) == 23756 || getTargetCharacterKey(object) == 23757 || getTargetCharacterKey(object) == 23758) && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait2(0.3)))
				return;
		}
		doAction(1142352784L /*BATTLE_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 500));
	}

	protected void Battle_Logic1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8EFF36F1L /*Battle_Logic1*/);
		clearAggro(true);
		changeState(state -> Battle_Logic2(blendTime));
	}

	protected void Battle_Logic2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB8191EA0L /*Battle_Logic2*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && getTargetCharacterKey(object) == 23759 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Rangeattack1(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && getTargetCharacterKey(object) == 23759 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Rangeattack1(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Battle_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCC2226D5L /*Battle_Wait2*/);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && getTargetCharacterKey(object) == 23759 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Rangeattack1(0.3)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Battle_Wait2_Talk(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait2(blendTime), 3000));
	}

	protected void Battle_Wait2_Talk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x624051C8L /*Battle_Wait2_Talk*/);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && getTargetCharacterKey(object) == 23759 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Rangeattack1(0.3)))
				return;
		}
		doAction(1142352784L /*BATTLE_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait2(blendTime), 3000));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	protected void Battle_Rangeattack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC349CD1FL /*Battle_Rangeattack1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 30000 && target != null && getTargetHp(target) > 0) {
				if(Rnd.getChance(40)) {
					if (changeState(state -> Battle_Rangeattack1_Talk(0.3)))
						return;
				}
			}
			changeState(state -> Battle_Logic1(blendTime));
		});
	}

	protected void Battle_Rangeattack1_Talk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEF97160L /*Battle_Rangeattack1_Talk*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Logic1(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
