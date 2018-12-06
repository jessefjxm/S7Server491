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
@IAIName("turret")
public class Ai_turret extends CreatureAI {
	public Ai_turret(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xF3B4EFA1L /*_buildingRate*/, 0);
		setVariable(0x3F487035L /*_Hp*/, 0);
		useSkill(40381, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		useSkill(40403, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		doAction(3701389406L /*INIT*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckBuildingRate(blendTime), 1000));
	}

	protected void CheckBuildingRate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8E7B6353L /*CheckBuildingRate*/);
		useSkill(40377, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		setVariable(0xF3B4EFA1L /*_buildingRate*/, getVariable(0xF3B4EFA1L /*_buildingRate*/) + 1);
		useSkill(40404, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0xF3B4EFA1L /*_buildingRate*/) > 99) {
			if (changeState(state -> CompleteBuild(blendTime)))
				return;
		}
		if (getVariable(0xF3B4EFA1L /*_buildingRate*/) > 0) {
			if (changeState(state -> BuildingState_1(blendTime)))
				return;
		}
		scheduleState(state -> CheckBuildingRate(blendTime), 1000);
	}

	protected void BuildingState_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6F7FD0A3L /*BuildingState_1*/);
		doAction(3999688434L /*BUILDING_START*/, blendTime, onDoActionEnd -> scheduleState(state -> CheckBuildingRate(blendTime), 1000));
	}

	protected void CompleteBuild(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDB0BF386L /*CompleteBuild*/);
		useSkill(40381, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		useSkill(40408, 1, false, EAIFindTargetType.Self, object -> getDistanceToTarget(object) < 500);
		setVariable(0xF3B4EFA1L /*_buildingRate*/, 100);
		doAction(287969247L /*BUILDING_COMPLETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9048C900L /*Wait_Logic*/);
		clearAggro(true);
		if (findTarget(EAIFindTargetType.AllyLordOrKingTent, EAIFindType.normal, true, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 4000)) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (isRegionSiegeBeing()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) <= 45 && getAngleToTarget(object) >= -45 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Attack_Normal_F(blendTime)))
					return;
			}
		}
		if (isRegionSiegeBeing()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= 45 && getAngleToTarget(object) <= 135 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Attack_Normal_R(blendTime)))
					return;
			}
		}
		if (isRegionSiegeBeing()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) <= -45 && getAngleToTarget(object) >= -135 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Attack_Normal_L(blendTime)))
					return;
			}
		}
		if (isRegionSiegeBeing()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) <= -135 && getAngleToTarget(object) >= -180 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Attack_Normal_B(blendTime)))
					return;
			}
		}
		if (isRegionSiegeBeing()) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= 135 && getAngleToTarget(object) <= 180 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Attack_Normal_B(blendTime)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Logic(blendTime), 1000));
	}

	protected void Attack_Normal_F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x921AFE83L /*Attack_Normal_F*/);
		doAction(545912066L /*ATTACK_NORMAL_F*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Logic(blendTime)));
	}

	protected void Attack_Normal_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFE1457EBL /*Attack_Normal_R*/);
		doAction(677153502L /*ATTACK_NORMAL_R*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Logic(blendTime)));
	}

	protected void Attack_Normal_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x53A7FF8EL /*Attack_Normal_L*/);
		doAction(1156785166L /*ATTACK_NORMAL_L*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Logic(blendTime)));
	}

	protected void Attack_Normal_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCE892E45L /*Attack_Normal_B*/);
		doAction(1732603888L /*ATTACK_NORMAL_B*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Logic(blendTime)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyInvited(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _DestroyedBase(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}