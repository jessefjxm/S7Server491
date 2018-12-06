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
@IAIName("hugecrocodile_boss")
public class Ai_hugecrocodile_boss extends CreatureAI {
	public Ai_hugecrocodile_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x795FB552L /*_Special_Stun_StartTime*/, 0);
		setVariable(0x8807FEFBL /*_Special_Stun_IngTime*/, 0);
		setVariable(0xE8587A5L /*_Special_Stun_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x1E2360D6L /*_StunDelay_StartTime*/, 0);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, 0);
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 100));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 10000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 10000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		if(getCallCount() == 20) {
			if (changeState(state -> Move_Random(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 3500, 4500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 10000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 5) {
			if (changeState(state -> FailFindPathToTarget(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		if (getDistanceToSpawn() > 30000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 504) {
			if (changeState(state -> Move_Back(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if (changeState(state -> Attack_Normal1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 504) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.trace);
		setState(0xE2DFC297L /*Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 30000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal5(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal4(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if (changeState(state -> Attack_Normal1(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			scheduleState(state -> Chaser_Run(blendTime), 100);
		});
	}

	protected void Chaser_Run_No_Curve(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.trace);
		setState(0xDFBE7435L /*Chaser_Run_No_Curve*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 30000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal5(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal4(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 960) {
			if (changeState(state -> Attack_Normal1(0.3)))
				return;
		}
		doAction(4165568205L /*CHASER_RUN_NO_CURVE*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			scheduleState(state -> Chaser_Run_No_Curve(blendTime), 100);
		});
	}

	protected void Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFFAAB1AFL /*Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD662C07EL /*Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	protected void Special_Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEF3EFF55L /*Special_Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Special_Damage_Stun_Ing(blendTime), 1000));
	}

	protected void Special_Damage_Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB97BF3E2L /*Special_Damage_Stun_Ing*/);
		doAction(1531277180L /*DAMAGE_STUN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Special_Damage_Stun_End(blendTime), 9000));
	}

	protected void Special_Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC21C1C3DL /*Special_Damage_Stun_End*/);
		setVariable(0x795FB552L /*_Special_Stun_StartTime*/, getTime());
		setVariable(0x1E2360D6L /*_StunDelay_StartTime*/, getTime());
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_End_Logic(blendTime), 1000));
	}

	protected void Special_Damage_Stun_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x698D06BDL /*Special_Damage_Stun_Logic*/);
		changeState(state -> Special_Damage_Stun_End(blendTime));
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		doAction(1616805723L /*ATTACK_NORMAL1*/, blendTime, onDoActionEnd -> changeState(state -> Move_Back(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Move_Back(blendTime)));
	}

	protected void Attack_Normal3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5F2C1966L /*Attack_Normal3*/);
		doAction(2957630933L /*ATTACK_NORMAL3*/, blendTime, onDoActionEnd -> changeState(state -> Move_Back(blendTime)));
	}

	protected void Attack_Normal4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5CA8868L /*Attack_Normal4*/);
		doAction(1678640124L /*ATTACK_NORMAL4*/, blendTime, onDoActionEnd -> changeState(state -> Move_Back(blendTime)));
	}

	protected void Attack_Normal5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x68BB22F8L /*Attack_Normal5*/);
		doAction(4242286761L /*ATTACK_NORMAL5*/, blendTime, onDoActionEnd -> changeState(state -> Move_Back(blendTime)));
	}

	protected void Move_RunAway(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x9180DC90L /*Move_RunAway*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> escape(5000, () -> {
			return false;
		}, onExit -> scheduleState(state -> Turn_Logic(blendTime), 3000)));
	}

	protected void Move_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xDD3BDB77L /*Move_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1516377807L /*MOVE_BACK*/, blendTime, onDoActionEnd -> escape(6000, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Move_Random(blendTime), 3000)));
	}

	protected void Move_Back_No_Curve(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xDF911E68L /*Move_Back_No_Curve*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1578035056L /*MOVE_BACK_NO_CURVE*/, blendTime, onDoActionEnd -> escape(6000, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Move_Random(blendTime), 3000)));
	}

	protected void Battle_Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7F26F4FAL /*Battle_Move_Random*/);
		doAction(1522267122L /*MOVE_RANDOM*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 2250, 3750, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 1000)));
	}

	protected void Delay_Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC7226459L /*Delay_Turn_Logic*/);
		if (target != null && getAngleToTarget(target) <= -20) {
			if (changeState(state -> Delay_Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 20) {
			if (changeState(state -> Delay_Turn_Right(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Delay(blendTime)));
	}

	protected void Delay_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x13A6DFBAL /*Delay_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Delay(blendTime)));
	}

	protected void Delay_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE35BBF98L /*Delay_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Delay(blendTime)));
	}

	protected void Battle_Wait_Delay(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8DD9DA51L /*Battle_Wait_Delay*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Move_Random(blendTime), 5000));
	}

	protected void Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x31999313L /*Turn_Logic*/);
		if (target != null && getAngleToTarget(target) <= -20 && target != null && getDistanceToTarget(target) < 378) {
			if (changeState(state -> Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 20 && target != null && getDistanceToTarget(target) < 378) {
			if (changeState(state -> Turn_Right(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x355E057L /*Attack_End_Logic*/);
		if(Rnd.getChance(70)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x7F26F4FAL /*Battle_Move_Random*/) {
			if (changeState(state -> Chaser_Run(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleUpdateCombineWave(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
