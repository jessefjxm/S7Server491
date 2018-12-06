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
@IAIName("oceanfish")
public class Ai_oceanfish extends CreatureAI {
	public Ai_oceanfish(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x1995ACA8L /*_isMove*/, getVariable(0xAA621BBFL /*AI_Move*/));
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		if (getVariable(0x1995ACA8L /*_isMove*/) == 5) {
			if (changeState(state -> Turn_Logic2(0.1)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 6) {
			if (changeState(state -> Turn_Logic3(0.1)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 3) {
			if (changeState(state -> Turn_Side(0.1)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 4) {
			if (changeState(state -> Turn_Side1(0.1)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 7) {
			if (changeState(state -> Wait_Wall(0.1)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Logic(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Test(blendTime), 1000));
	}

	protected void Wait_Wall(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x57832FA8L /*Wait_Wall*/);
		doAction(2649792340L /*WAIT_WALL*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Wall(blendTime), 1000));
	}

	protected void Move_Test(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6F3DF364L /*Move_Test*/);
		doAction(3965339082L /*MOVE_WALK_TEST*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 1300, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Test(blendTime), 500)));
	}

	protected void Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x31999313L /*Turn_Logic*/);
		if (getVariable(0x1995ACA8L /*_isMove*/) == 0) {
			if (changeState(state -> Turn(0.1)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 1) {
			if (changeState(state -> Turn1(0.1)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 2) {
			if (changeState(state -> Turn2(0.1)))
				return;
		}
		changeState(state -> Turn_Logic(blendTime));
	}

	protected void Turn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x6B3D2433L /*Turn*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Turn1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x82FC64EL /*Turn1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 30, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Turn2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x65EA08C9L /*Turn2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -30, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(553029183L /*START_ACTION1*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Logic(blendTime), 5000));
	}

	protected void Start_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x615A198AL /*Start_Logic*/);
		if(Rnd.getChance(45)) {
			if (changeState(state -> Move_Left_Start(0.2)))
				return;
		}
		if (changeState(state -> Move_Right_Start(0.2)))
			return;
		changeState(state -> Start_Logic(blendTime));
	}

	protected void Move_Left_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7DF04E12L /*Move_Left_Start*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4277708041L /*MOVE_WALK4*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.FloatingRelative, 0, 20, 20, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Foward_Start(blendTime), 500)));
	}

	protected void Move_Foward_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x752C6A36L /*Move_Foward_Start*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4271683418L /*MOVE_WALK2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 1300, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Right(blendTime), 500)));
	}

	protected void Move_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x21307BE3L /*Move_Left*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4277708041L /*MOVE_WALK4*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.FloatingRelative, 0, 20, 10, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Foward2(blendTime), 500)));
	}

	protected void Move_Foward(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF6FD49CDL /*Move_Foward*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) >= 40000) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4271683418L /*MOVE_WALK2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 2500, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Left(blendTime), 500)));
	}

	protected void Move_Foward2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3178C527L /*Move_Foward2*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) >= 40000) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4271683418L /*MOVE_WALK2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 2500, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Right(blendTime), 500)));
	}

	protected void Move_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x182F8BA0L /*Move_Right*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4277708041L /*MOVE_WALK4*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.FloatingRelative, 0, -20, 10, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Foward(blendTime), 500)));
	}

	protected void Move_Right_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAA1AA7B1L /*Move_Right_Start*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4277708041L /*MOVE_WALK4*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.FloatingRelative, 0, -20, 20, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Foward_Start2(blendTime), 500)));
	}

	protected void Move_Foward_Start2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6C42B129L /*Move_Foward_Start2*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4271683418L /*MOVE_WALK2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 1300, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Left2(blendTime), 500)));
	}

	protected void Move_Left2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1508B730L /*Move_Left2*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4277708041L /*MOVE_WALK4*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.FloatingRelative, 0, 20, 10, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Foward_A(blendTime), 500)));
	}

	protected void Move_Foward_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x217ADD53L /*Move_Foward_A*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) >= 40000) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4271683418L /*MOVE_WALK2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 2500, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Right2(blendTime), 500)));
	}

	protected void Move_Foward2_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6DC9C407L /*Move_Foward2_A*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) >= 40000) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4271683418L /*MOVE_WALK2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 2500, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Left2(blendTime), 500)));
	}

	protected void Move_Right2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x64305468L /*Move_Right2*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(4277708041L /*MOVE_WALK4*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.FloatingRelative, 0, -20, 10, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Foward2_A(blendTime), 500)));
	}

	protected void Turn_Side(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xD25A9467L /*Turn_Side*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 80, () -> {
			return false;
		}, onExit -> scheduleState(state -> Start1(blendTime), 3000 + Rnd.get(-2000,2000))));
	}

	protected void Turn_Side1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xEBD80685L /*Turn_Side1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -80, () -> {
			return false;
		}, onExit -> scheduleState(state -> Start2(blendTime), 3000 + Rnd.get(-2000,2000))));
	}

	protected void Start1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE23A8668L /*Start1*/);
		doAction(553029183L /*START_ACTION1*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Walk_Logic(blendTime), 5000));
	}

	protected void Start2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6F4B100DL /*Start2*/);
		doAction(553029183L /*START_ACTION1*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Walk2_Logic(blendTime), 5000));
	}

	protected void Move_Walk_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x738891CBL /*Move_Walk_Logic*/);
		if(Rnd.getChance(40)) {
			if (changeState(state -> Move_Walk_A(0.1)))
				return;
		}
		if (changeState(state -> Move_Walk(0.1)))
			return;
		changeState(state -> Move_Walk_Logic(blendTime));
	}

	protected void Move_Walk2_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2A6AB7B2L /*Move_Walk2_Logic*/);
		if(Rnd.getChance(40)) {
			if (changeState(state -> Move_Walk2_A(0.1)))
				return;
		}
		if (changeState(state -> Move_Walk2(0.1)))
			return;
		changeState(state -> Turn_Logic(blendTime));
	}

	protected void Move_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCF372FA4L /*Move_Walk*/);
		if(getCallCount() == 25) {
			if (changeState(state -> Attack1(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(631821213L /*MOVE_WALK_SIDE1*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 200, 1500, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Walk(blendTime), 500)));
	}

	protected void Move_Walk_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2BA83CF5L /*Move_Walk_A*/);
		if(getCallCount() == 60) {
			if (changeState(state -> Attack1(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(1684117581L /*MOVE_WALK_SIDE2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 150, 1200, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Walk_A(blendTime), 500)));
	}

	protected void Move_Walk2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xACD08DE3L /*Move_Walk2*/);
		if(getCallCount() == 25) {
			if (changeState(state -> Attack1(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(631821213L /*MOVE_WALK_SIDE1*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, -200, 1500, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Walk2(blendTime), 500)));
	}

	protected void Move_Walk2_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5C5F2CL /*Move_Walk2_A*/);
		if(getCallCount() == 60) {
			if (changeState(state -> Attack1(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack2(0.2)))
				return;
		}
		doAction(1684117581L /*MOVE_WALK_SIDE2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, -200, 1500, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Walk2_A(blendTime), 500)));
	}

	protected void Turn_Logic2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEFCDF89FL /*Turn_Logic2*/);
		if(getCallCount() == 2 && Rnd.getChance(20)) {
			if (changeState(state -> Turn_Jump(0.1)))
				return;
		}
		if(getCallCount() == 2 && Rnd.getChance(20)) {
			if (changeState(state -> Turn_Jump1(0.1)))
				return;
		}
		if(getCallCount() == 2 && Rnd.getChance(20)) {
			if (changeState(state -> Turn_Jump2(0.1)))
				return;
		}
		if(getCallCount() == 2 && Rnd.getChance(20)) {
			if (changeState(state -> Turn_Jump3(0.1)))
				return;
		}
		if(getCallCount() == 2) {
			if (changeState(state -> Turn_Jump4(0.1)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Logic2(blendTime), 3000 + Rnd.get(-2000,2000)));
	}

	protected void Turn_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x9F570402L /*Turn_Jump*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 30, () -> {
			return false;
		}, onExit -> scheduleState(state -> Jump_Logic(blendTime), 3000 + Rnd.get(-2000,2000))));
	}

	protected void Turn_Jump1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xA690CE43L /*Turn_Jump1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 90, () -> {
			return false;
		}, onExit -> scheduleState(state -> Jump_Logic(blendTime), 3000 + Rnd.get(-2000,2000))));
	}

	protected void Turn_Jump2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xD5B32559L /*Turn_Jump2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -90, () -> {
			return false;
		}, onExit -> scheduleState(state -> Jump_Logic(blendTime), 3000 + Rnd.get(-2000,2000))));
	}

	protected void Turn_Jump3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xF653A9ECL /*Turn_Jump3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -30, () -> {
			return false;
		}, onExit -> scheduleState(state -> Jump_Logic(blendTime), 3000 + Rnd.get(-2000,2000))));
	}

	protected void Turn_Jump4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x8786420AL /*Turn_Jump4*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Jump_Logic(blendTime), 3000 + Rnd.get(-2000,2000))));
	}

	protected void Jump_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x56E60BCBL /*Jump_Logic*/);
		if(Rnd.getChance(40)) {
			if (changeState(state -> Jump_Attack1(0.1)))
				return;
		}
		if (changeState(state -> Jump_Attack2(0.1)))
			return;
		changeState(state -> Jump_Logic(blendTime));
	}

	protected void Jump_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x25307B9EL /*Jump_Attack1*/);
		doAction(3575646796L /*JUMP_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Die2(blendTime)));
	}

	protected void Jump_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1B5971F6L /*Jump_Attack2*/);
		doAction(3878077329L /*JUMP_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Die2(blendTime)));
	}

	protected void Turn_Logic3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5E0FF675L /*Turn_Logic3*/);
		if (getVariable(0x1995ACA8L /*_isMove*/) == 6) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Turn_Straight1(0.1)))
					return;
			}
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 6) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Turn_Straight3(0.1)))
					return;
			}
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 6) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Turn_Straight2(0.1)))
					return;
			}
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 6) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Turn_Straight4(0.1)))
					return;
			}
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 6) {
			if (changeState(state -> Turn_Straight(0.1)))
				return;
		}
		changeState(state -> Turn_Logic3(blendTime));
	}

	protected void Turn_Straight(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xED7B4C63L /*Turn_Straight*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Straight(blendTime), 3000)));
	}

	protected void Turn_Straight1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xA69DE426L /*Turn_Straight1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 30, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Straight(blendTime), 3000)));
	}

	protected void Turn_Straight2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xA68F5ED9L /*Turn_Straight2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -30, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Straight(blendTime), 3000)));
	}

	protected void Turn_Straight3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x483DE45FL /*Turn_Straight3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 15, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Straight(blendTime), 3000)));
	}

	protected void Turn_Straight4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xF865EC6DL /*Turn_Straight4*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -15, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Straight(blendTime), 3000)));
	}

	protected void Wait_Straight(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3DABC629L /*Wait_Straight*/);
		doAction(553029183L /*START_ACTION1*/, blendTime, onDoActionEnd -> scheduleState(state -> Straight_Speed_Logic(blendTime), 5000));
	}

	protected void Straight_Speed_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD2B6407AL /*Straight_Speed_Logic*/);
		if(Rnd.getChance(40)) {
			if (changeState(state -> Move_Walk_Straight2(0.1)))
				return;
		}
		if (changeState(state -> Move_Walk_Straight(0.1)))
			return;
		changeState(state -> Straight_Speed_Logic(blendTime));
	}

	protected void Move_Walk_Straight(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x484CFEDL /*Move_Walk_Straight*/);
		if(getCallCount() == 80) {
			if (changeState(state -> Attack_Straight(0.2)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 1000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack_Straight(0.2)))
				return;
		}
		doAction(3046957996L /*MOVE_WALK_STRAIGHT*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 2000, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Walk_Straight(blendTime), 500)));
	}

	protected void Move_Walk_Straight2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9D21F478L /*Move_Walk_Straight2*/);
		if(getCallCount() == 80) {
			if (changeState(state -> Attack_Straight(0.2)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getAngleToTarget(object) >= -30 && getAngleToTarget(object) < 30 && getDistanceToTarget(object) <= 1000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack_Straight(0.2)))
				return;
		}
		doAction(770856934L /*MOVE_WALK_STRAIGHT2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 2000, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Walk_Straight2(blendTime), 500)));
	}

	protected void Attack_Straight(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3B8DE082L /*Attack_Straight*/);
		doAction(2362681632L /*ATTACK_BOMB*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
			if (changeState(state -> FailFindPath(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Bomb_Die(blendTime), 1500));
	}

	protected void Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x566D5E96L /*Attack1*/);
		doAction(1616805723L /*ATTACK_NORMAL1*/, blendTime, onDoActionEnd -> changeState(state -> Die2(blendTime)));
	}

	protected void Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1CF46EC2L /*Attack2*/);
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Die2(blendTime)));
	}

	protected void Bomb_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7570BAE5L /*Bomb_Die*/);
		doAction(4292079804L /*BOMB_DIE*/, blendTime, onDoActionEnd -> changeState(state -> Die2(blendTime)));
	}

	protected void Die2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x6E60EEB7L /*Die2*/);
		clearAggro(true);
		doAction(4260439228L /*DIE2*/, blendTime, onDoActionEnd -> scheduleState(state -> Die2(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		clearAggro(true);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x7570BAE5L /*Bomb_Die*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Bomb_Die(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Attack1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x7570BAE5L /*Bomb_Die*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Attack1(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult ByeEverybody(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x1995ACA8L /*_isMove*/) != 5 && getState() != 0x7570BAE5L /*Bomb_Die*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Bomb_Die(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
