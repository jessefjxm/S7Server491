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
@IAIName("w9000_0_2_minion")
public class Ai_w9000_0_2_minion extends CreatureAI {
	public Ai_w9000_0_2_minion(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xF06310B7L /*_stance*/, 0);
		setVariable(0x9A84DB6EL /*random_stance*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x9B821662L /*_findTargetDistance*/, 600);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> stance_wait(blendTime), 3000));
	}

	protected void stance_wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF6228056L /*stance_wait*/);
		setVariable(0x9A84DB6EL /*random_stance*/, getRandom(2));
		setVariable(0xF06310B7L /*_stance*/, 2);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> set_Stance(blendTime), 3000));
	}

	protected void set_Stance(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x427DCEB7L /*set_Stance*/);
		if (getVariable(0xF06310B7L /*_stance*/) == 0) {
			if (changeState(state -> Move_Line0(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 1) {
			if (changeState(state -> Move_Line1(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 2) {
			if (changeState(state -> Move_Line2(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 3) {
			if (changeState(state -> Move_Line3(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 4) {
			if (changeState(state -> Move_Line4(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 5) {
			if (changeState(state -> Move_Line5(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9B821662L /*_findTargetDistance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2000));
	}

	protected void Move_Line0(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC5C6A508L /*Move_Line0*/);
		if (isTargetLost()) {
			if (changeState(state -> set_Stance(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9B821662L /*_findTargetDistance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("battlefield", "w10000_team0_route0_8", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 1000)));
	}

	protected void Move_Line1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA92112AEL /*Move_Line1*/);
		if (isTargetLost()) {
			if (changeState(state -> set_Stance(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9B821662L /*_findTargetDistance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("battlefield", "w10000_team0_route1_6", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 1000)));
	}

	protected void Move_Line2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB4423B19L /*Move_Line2*/);
		if (isTargetLost()) {
			if (changeState(state -> set_Stance(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9B821662L /*_findTargetDistance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("battlefield", "w10000_team0_route2_7", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 1000)));
	}

	protected void Move_Line3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC36B4A87L /*Move_Line3*/);
		if (isTargetLost()) {
			if (changeState(state -> set_Stance(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9B821662L /*_findTargetDistance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("battlefield", "w10000_team1_route0_7", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 1000)));
	}

	protected void Move_Line4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC424F15AL /*Move_Line4*/);
		if (isTargetLost()) {
			if (changeState(state -> set_Stance(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9B821662L /*_findTargetDistance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("battlefield", "w10000_team1_route1_6", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 1000)));
	}

	protected void Move_Line5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB4232435L /*Move_Line5*/);
		if (isTargetLost()) {
			if (changeState(state -> set_Stance(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9B821662L /*_findTargetDistance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("battlefield", "w10000_team1_route2_8", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 1000)));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0xE5BD13F2L /*_Degree*/) > 155) {
			if(Rnd.getChance(getVariable(0x85CE5989L /*AI_Turn180*/))) {
				if (changeState(state -> Turn_180(0.3)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) < -155) {
			if(Rnd.getChance(getVariable(0x85CE5989L /*AI_Turn180*/))) {
				if (changeState(state -> Turn_180(0.3)))
					return;
			}
		}
		if (getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x714E207L /*AI_BT_Attack2_Distance*/)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0xF630F33AL /*_Distance*/) > getVariable(0x5103FB80L /*AI_EC_Distance*/) && getVariable(0x3F487035L /*_HP*/) > 0) {
			if (changeState(state -> Move_Chaser(0.1)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 200));
	}

	protected void Battle_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5FDC949L /*Battle_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> set_Stance(blendTime)))
				return;
		}
		if (getVariable(0x7DDBA7E1L /*AI_BattleAttack1_RotateOff*/) == 1) {
			if (changeState(state -> Battle_Attack1_NoRotate(0.3)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Attack1_Rotate(blendTime)));
	}

	protected void Battle_Attack1_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x33FF8DD5L /*Battle_Attack1_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack1_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC37AE191L /*Battle_Attack1_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD72BCC90L /*Battle_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> set_Stance(blendTime)))
				return;
		}
		if (getVariable(0x11E69B7FL /*AI_BattleAttack2_RotateOff*/) == 1) {
			if (changeState(state -> Battle_Attack2_NoRotate(0.3)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Attack2_Rotate(blendTime)));
	}

	protected void Battle_Attack2_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF62732EDL /*Battle_Attack2_Rotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack2_NoRotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCA4082FDL /*Battle_Attack2_NoRotate*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chaser(0.1)))
				return;
		}
		if (getVariable(0x64490D98L /*AI_RandomMove*/) == 0 && getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		if (getVariable(0xE97DEBF8L /*AI_EnemyChase*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (changeState(state -> set_Stance(0.3)))
			return;
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Lost_Target(blendTime), 500));
	}

	protected void Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x828FBC91L /*Turn_180*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1676409899L /*TURN_180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> set_Stance(0.3)))
				return;
		}
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x44ABD67BL /*AI_BT_Attack2_CoolCountN*/) <= 0 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x714E207L /*AI_BT_Attack2_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x4DA023BCL /*AI_BT_Attack2_HP*/)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0x2D70D5BBL /*AI_AttackerType*/) == 1 && getVariable(0x47478B48L /*AI_BT_Attack1_CoolCountN*/) <= 0 && getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && getVariable(0x3F487035L /*_HP*/) <= getVariable(0x88498267L /*AI_BT_Attack1_HP*/)) {
			if(Rnd.getChance(getVariable(0xAC1AF8EAL /*AI_BT_Attack1*/))) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (getVariable(0xF630F33AL /*_Distance*/) > 1000) {
			if (changeState(state -> set_Stance(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 500)));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> set_Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Capture(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Move_Line0(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
