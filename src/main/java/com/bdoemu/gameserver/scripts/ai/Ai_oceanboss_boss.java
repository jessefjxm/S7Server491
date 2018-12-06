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
@IAIName("oceanboss_boss")
public class Ai_oceanboss_boss extends CreatureAI {
	public Ai_oceanboss_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		setVariable(0x5749908DL /*_JumpCount*/, 0);
		setVariable(0xADEBDC44L /*_ThunderCount*/, 0);
		setVariable(0x8386A296L /*_WaterballCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		doAction(3546093118L /*START*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 1000));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> changeState(state -> Start_Action2(blendTime)));
	}

	protected void Start_Action2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC141FFFAL /*Start_Action2*/);
		doAction(3451186354L /*START_ACTION_A*/, blendTime, onDoActionEnd -> changeState(state -> Start_Action3(blendTime)));
	}

	protected void Start_Action3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3F73F0B3L /*Start_Action3*/);
		summonCharacter(27143, 0, 7000, 0, 0, true);
		summonCharacter(27143, 0, 5000, 0, 5000, true);
		summonCharacter(27143, 0, 5000, 0, -5000, true);
		summonCharacter(27143, 0, -7000, 0, 0, true);
		summonCharacter(27143, 0, -5000, 0, 5000, true);
		summonCharacter(27143, 0, -5000, 0, -5000, true);
		summonCharacter(27143, 0, 0, 0, 7000, true);
		doAction(4050609673L /*START_ACTION_B*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Start(blendTime)));
	}

	protected void Wait_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x606517EL /*Wait_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Start(blendTime), 1000));
	}

	protected void Roar_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCC2B11FEL /*Roar_Wait*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(1648198093L /*ROAR_WAIT*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._1Phase(getActor(), null));
			scheduleState(state -> Wait(blendTime), 1000);
		});
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 7200000) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 8000)) {
			if (changeState(state -> Attack_Jump_Low(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 50) {
			if (changeState(state -> Attack_Jump_70(0.8)))
				return;
		}
		if(getCallCount() == 2) {
			if (changeState(state -> Roar_Wait4(0.8)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Roar_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA92F2D5DL /*Roar_Wait2*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(3556972960L /*ROAR_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Roar_Logic(blendTime), 5000));
	}

	protected void Roar_Wait3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCD89E314L /*Roar_Wait3*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(133347576L /*ROAR*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 10000));
	}

	protected void Roar_Wait4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x57BB785EL /*Roar_Wait4*/);
		doAction(1380091494L /*WAIT3*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 10000));
	}

	protected void Roar_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD5B553CL /*Roar_Logic*/);
		if (getVariable(0x8386A296L /*_WaterballCount*/) == 0) {
			if (changeState(state -> Roar(0.8)))
				return;
		}
		if (getVariable(0x8386A296L /*_WaterballCount*/) == 1) {
			if (changeState(state -> Roar2(0.8)))
				return;
		}
		if (getVariable(0x8386A296L /*_WaterballCount*/) == 2) {
			if (changeState(state -> Roar3(0.8)))
				return;
		}
		changeState(state -> Roar_Logic(blendTime));
	}

	protected void Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x60186BFFL /*Roar*/);
		setVariable(0x8386A296L /*_WaterballCount*/, getVariable(0x8386A296L /*_WaterballCount*/) + 1);
		doAction(2839259316L /*ROAR2*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Roar2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x26A0A928L /*Roar2*/);
		setVariable(0x8386A296L /*_WaterballCount*/, getVariable(0x8386A296L /*_WaterballCount*/) + 1);
		doAction(2839259316L /*ROAR2*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Roar3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3F975DD3L /*Roar3*/);
		setVariable(0x8386A296L /*_WaterballCount*/, getVariable(0x8386A296L /*_WaterballCount*/) + 1);
		doAction(2839259316L /*ROAR2*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 7200000) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 50) {
			if (changeState(state -> Attack_Jump_70(0.8)))
				return;
		}
		if (getVariable(0x8386A296L /*_WaterballCount*/) >= 3) {
			if (changeState(state -> Attack_WaterBall(0.8)))
				return;
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) == 2) {
			if (changeState(state -> Roar_Wait2(0.8)))
				return;
		}
		if(Rnd.getChance(20)) {
			if (changeState(state -> Breath_Logic(0.8)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_Jump_Low(0.8)))
				return;
		}
		if(Rnd.getChance(50)) {
			if (changeState(state -> Roar_Attack(0.8)))
				return;
		}
		if (changeState(state -> Roar_Logic(0.8)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_WaterBall(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8F324FF4L /*Attack_WaterBall*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().WaterFall(getActor(), null));
		setVariable(0x8386A296L /*_WaterballCount*/, 0);
		doAction(2898019721L /*ROAR_SLOW*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 6000));
	}

	protected void Attack_Jump_Low(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFC9522B5L /*Attack_Jump_Low*/);
		doAction(1240895934L /*ATTACK_JUMP_LOW*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Jump_Low_End_Logic(blendTime)));
	}

	protected void Attack_Jump_Low_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8B5F07CDL /*Attack_Jump_Low_End_Logic*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		changeState(state -> Wait(blendTime));
	}

	protected void Breath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x652E0F59L /*Breath_Logic*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_Breath_F_Left(0.8)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_Breath_F_Right(0.8)))
				return;
		}
		if(Rnd.getChance(100)) {
			if (changeState(state -> Attack_Breath_F(0.8)))
				return;
		}
		changeState(state -> Breath_Logic(blendTime));
	}

	protected void Attack_Breath_F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x65ADC451L /*Attack_Breath_F*/);
		doAction(1944589819L /*ATTACK_BREATH_FOWARD*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Breath_F_End_Logic(blendTime)));
	}

	protected void Attack_Breath_F_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9B1F74D4L /*Attack_Breath_F_Left*/);
		doAction(2682141315L /*ATTACK_BREATH_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Turn_Right(blendTime)));
	}

	protected void Attack_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA9B11BF9L /*Attack_Turn_Right*/);
		doAction(1597155294L /*ATTACK_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Breath_F_End_Logic(blendTime)));
	}

	protected void Attack_Breath_F_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5AC83731L /*Attack_Breath_F_Right*/);
		doAction(3487194965L /*ATTACK_BREATH_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Turn_Left(blendTime)));
	}

	protected void Attack_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3E8D0930L /*Attack_Turn_Left*/);
		doAction(3567810684L /*ATTACK_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Breath_F_End_Logic(blendTime)));
	}

	protected void Attack_Breath_F_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x15DAF356L /*Attack_Breath_F_End_Logic*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		changeState(state -> Wait(blendTime));
	}

	protected void Roar_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7A64B264L /*Roar_Attack*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Trap(getActor(), null));
		summonCharacter(23100, 0, 5000, 0, 7000, false);
		summonCharacter(23100, 0, -5000, 0, 7000, false);
		summonCharacter(23100, 0, -5000, 0, 10000, false);
		doAction(4006574634L /*ROAR_TURN*/, blendTime, onDoActionEnd -> changeState(state -> Roar_Attack_End_Logic(blendTime)));
	}

	protected void Roar_Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD5FAB230L /*Roar_Attack_End_Logic*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		changeState(state -> Wait(blendTime));
	}

	protected void Battle_Wait_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x17C87EDDL /*Battle_Wait_70*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 7200000) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if (getVariable(0x8386A296L /*_WaterballCount*/) >= 3) {
			if (changeState(state -> Attack_WaterBall_70(0.8)))
				return;
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) == 2) {
			if (changeState(state -> Roar_Wait2_70(0.8)))
				return;
		}
		if(Rnd.getChance(20)) {
			if (changeState(state -> Attack_Breath_Turn_70(0.8)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_Breath_F_70(0.8)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_Jump_Low_70(0.8)))
				return;
		}
		if(Rnd.getChance(40)) {
			if (changeState(state -> Roar_Attack_70(0.8)))
				return;
		}
		if (changeState(state -> Roar_Logic_70(0.8)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_70(blendTime), 1000));
	}

	protected void Wait_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5AB41E51L /*Wait_70*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		doAction(1648198093L /*ROAR_WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 7200000) {
				if (changeState(state -> Die(blendTime)))
					return;
			}
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 8000)) {
				if (changeState(state -> Attack_Jump_Low_70(blendTime)))
					return;
			}
			scheduleState(state -> Battle_Wait_70(blendTime), 1000);
		});
	}

	protected void Roar_Wait2_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEB310197L /*Roar_Wait2_70*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(3556972960L /*ROAR_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Roar_Logic_70(blendTime), 4000));
	}

	protected void Roar_Logic_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2B451C57L /*Roar_Logic_70*/);
		if (getVariable(0x8386A296L /*_WaterballCount*/) == 0) {
			if (changeState(state -> Roar_70(0.8)))
				return;
		}
		if (getVariable(0x8386A296L /*_WaterballCount*/) == 1) {
			if (changeState(state -> Roar_70(0.8)))
				return;
		}
		if (getVariable(0x8386A296L /*_WaterballCount*/) == 2) {
			if (changeState(state -> Roar3_70(0.8)))
				return;
		}
		changeState(state -> Roar_Logic_70(blendTime));
	}

	protected void Roar_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x46A78486L /*Roar_70*/);
		setVariable(0x8386A296L /*_WaterballCount*/, getVariable(0x8386A296L /*_WaterballCount*/) + 1);
		doAction(2839259316L /*ROAR2*/, blendTime, onDoActionEnd -> changeState(state -> Wait_70(blendTime)));
	}

	protected void Roar2_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA90A29E5L /*Roar2_70*/);
		setVariable(0x8386A296L /*_WaterballCount*/, getVariable(0x8386A296L /*_WaterballCount*/) + 1);
		doAction(2839259316L /*ROAR2*/, blendTime, onDoActionEnd -> changeState(state -> Wait_70(blendTime)));
	}

	protected void Roar3_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8A5954BBL /*Roar3_70*/);
		setVariable(0x8386A296L /*_WaterballCount*/, getVariable(0x8386A296L /*_WaterballCount*/) + 1);
		doAction(2839259316L /*ROAR2*/, blendTime, onDoActionEnd -> changeState(state -> Wait_70(blendTime)));
	}

	protected void Attack_WaterBall_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFD281A83L /*Attack_WaterBall_70*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().WaterFall(getActor(), null));
		setVariable(0x8386A296L /*_WaterballCount*/, 0);
		doAction(2898019721L /*ROAR_SLOW*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_70(blendTime), 4000));
	}

	protected void Attack_Jump_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x59D3A2A1L /*Attack_Jump_70*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Stop_Summon(getActor(), null));
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		setVariable(0x5749908DL /*_JumpCount*/, getVariable(0x5749908DL /*_JumpCount*/) + 1);
		useSkill(40436, 1, true, EAIFindTargetType.Player, object -> getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0);
		useSkill(40435, 1, true, EAIFindTargetType.Enemy, object -> getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && isTargetVehicle(object));
		doAction(2705621134L /*ATTACK_JUMP*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Jump_70_A(blendTime)));
	}

	protected void Attack_Jump_70_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF2629057L /*Attack_Jump_70_A*/);
		useSkill(40435, 2, true, EAIFindTargetType.Enemy, object -> getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 30000 && getTargetHp(object) > 0 && isTargetVehicle(object));
		doAction(3414615823L /*ATTACK_JUMP_B*/, blendTime, onDoActionEnd -> changeState(state -> Change_Phase_Logic(blendTime)));
	}

	protected void Change_Phase_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE47C40EBL /*Change_Phase_Logic*/);
		// Missing AI Handler: 2Phase : send_command()
		changeState(state -> Wait_70(blendTime));
	}

	protected void Attack_Jump_Low_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x71BA135FL /*Attack_Jump_Low_70*/);
		doAction(1240895934L /*ATTACK_JUMP_LOW*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic_70(blendTime)));
	}

	protected void Attack_Breath_Turn_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5F71687L /*Attack_Breath_Turn_70*/);
		doAction(3937755100L /*ATTACK_BREATH_TURN*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic_70(blendTime)));
	}

	protected void Attack_Breath_F_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC7203038L /*Attack_Breath_F_70*/);
		doAction(1944589819L /*ATTACK_BREATH_FOWARD*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic_70(blendTime)));
	}

	protected void Roar_Attack_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7FBD28AEL /*Roar_Attack_70*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Trap2(getActor(), null));
		summonCharacter(23100, 0, 0, 0, 12000, false);
		summonCharacter(23100, 0, 0, 0, 8000, false);
		summonCharacter(23100, 0, -8000, 0, 10000, false);
		summonCharacter(23100, 0, 8000, 0, 10000, false);
		doAction(4006574634L /*ROAR_TURN*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic_70(blendTime)));
	}

	protected void Attack_End_Logic_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB1E96EE1L /*Attack_End_Logic_70*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		changeState(state -> Wait_70(blendTime));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 25000)) {
			if (changeState(state -> Roar_Wait(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 4) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2241895867L /*MOVE_WALK_ING*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 10000)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().Gotohell(getActor(), null));
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Gotohell(getActor(), null));
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Stop_Summon(getActor(), null));
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().ByeWall(getActor(), null));
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 5000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult JumpLow(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Attack_Jump_Low(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Breath(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Breath_Logic(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult BreathTurn(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Attack_Breath_Turn_70(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult JumpHigh(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Attack_Jump_70(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wait(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait_Start(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Roar(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Roar_Wait2(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Roarattack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Roar_Attack(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult BattleWait70(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Attack_Jump_70(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
