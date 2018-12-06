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
@IAIName("sylphid")
public class Ai_sylphid extends CreatureAI {
	public Ai_sylphid(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0x457C70ECL /*_DistanceToTarget*/, 0);
		setVariable(0x5B4A863FL /*_OwnerHPrate*/, 0);
		setVariable(0xB669ED2L /*_OwnerEPrate*/, 0);
		setVariable(0x467456F3L /*_DeathTimer*/, 0);
		setVariable(0xF06310B7L /*_Stance*/, 0);
		doAction(3635031213L /*SUMMON*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-0,0)));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10) {
			if (changeState(state -> ChaseOwner(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 50));
	}

	protected void Heal_HP(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x224D2786L /*Heal_HP*/);
		doAction(871740164L /*SKILL_HEAL_HP*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Heal_EP(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7E1934FEL /*Heal_EP*/);
		doAction(3607517943L /*SKILL_HEAL_EP*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ChaseOwner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFDE062E8L /*ChaseOwner*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 5) {
			if (changeState(state -> ChaseOwner_Stop(blendTime)))
				return;
		}
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> ChaseOwner(blendTime), 50)));
	}

	protected void ChaseOwner_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC795B6F3L /*ChaseOwner_Stop*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void ArrowAttack_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7EF9D42L /*ArrowAttack_1*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(1333086927L /*ARROW_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowAttack_Fast(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBDDCD9A6L /*ArrowAttack_Fast*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(2812853529L /*ARROW_ATTACK_FAST*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowAttack_Double(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x545612DEL /*ArrowAttack_Double*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(2519579702L /*ARROW_ATTACK_DOUBLESHOT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_Explosion(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2B0D2D95L /*ArrowSkill_Explosion*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(1135717570L /*ARROW_SKILL_EXPLOSION*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_ChargeShot_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBBF76332L /*ArrowSkill_ChargeShot_1*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(1059731795L /*ARROW_SKILL_CHARGESHOT_1*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_ChargeShot_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9E9E6DA6L /*ArrowSkill_ChargeShot_2*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(144395065L /*ARROW_SKILL_CHARGESHOT_2*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_ChargeShot_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF0B2BAC8L /*ArrowSkill_ChargeShot_3*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(2195070426L /*ARROW_SKILL_CHARGESHOT_3*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_ChargeShot_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB9A8755CL /*ArrowSkill_ChargeShot_4*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(1717856213L /*ARROW_SKILL_CHARGESHOT_4*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_MultipleArrow_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC9DCCB24L /*ArrowSkill_MultipleArrow_3*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(1244225846L /*ARROW_SKILL_MULTIPLEARROW_3*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_MultipleArrow_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x27E9E02L /*ArrowSkill_MultipleArrow_5*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(2573793855L /*ARROW_SKILL_MULTIPLEARROW_5*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_MultipleArrow_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC0F16297L /*ArrowSkill_MultipleArrow_7*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(3373761493L /*ARROW_SKILL_MULTIPLEARROW_7*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_MultipleArrow_9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x114CA936L /*ArrowSkill_MultipleArrow_9*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(2491766274L /*ARROW_SKILL_MULTIPLEARROW_9*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_MultipleArrow_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x974ECA08L /*ArrowSkill_MultipleArrow_10*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(1178827992L /*ARROW_SKILL_MULTIPLEARROW_10*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_EnergyShot(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5A65569AL /*ArrowSkill_EnergyShot*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(981716229L /*ARROW_SKILL_ENERGYSHOT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void ArrowSkill_AimShot(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD352A7C5L /*ArrowSkill_AimShot*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(183845439L /*ARROW_SKILL_AIMSHOT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void UnSummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xCC02DCCFL /*UnSummon*/);
		doAction(1340753835L /*UNSUMMON*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 100));
	}

	@Override
	public EAiHandlerResult HandleStance10(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		setVariable(0xF06310B7L /*_Stance*/, 10);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance11(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowAttack_1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance12(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_Explosion(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance13(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowAttack_Fast(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance14(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowAttack_Double(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance15(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_ChargeShot_1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance16(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_ChargeShot_2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance17(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_ChargeShot_3(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance18(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_ChargeShot_4(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance19(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_MultipleArrow_3(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance20(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_MultipleArrow_5(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance21(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_MultipleArrow_7(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance22(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_MultipleArrow_9(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance23(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_MultipleArrow_10(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance24(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_EnergyShot(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance25(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (target != null && getDistanceToTarget(target) < 3000) {
			if (changeState(state -> ArrowSkill_AimShot(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance99(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (changeState(state -> UnSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
