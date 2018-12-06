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
@IAIName("deserthurricane")
public class Ai_deserthurricane extends CreatureAI {
	public Ai_deserthurricane(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0xB7528D4CL /*_DiceValue*/, 0);
		setVariable(0x680BEDFBL /*isGround*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		doAction(2235988577L /*START_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Logic(blendTime), 1000));
	}

	protected void Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x31999313L /*Turn_Logic*/);
		setVariable(0xB7528D4CL /*_DiceValue*/, getRandom(70));
		if (getVariable(0xB7528D4CL /*_DiceValue*/) <= 10) {
			if (changeState(state -> Turn_R_5(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 10 && getVariable(0xB7528D4CL /*_DiceValue*/) <= 20) {
			if (changeState(state -> Turn_R_10(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 20 && getVariable(0xB7528D4CL /*_DiceValue*/) <= 30) {
			if (changeState(state -> Turn_R_15(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 30 && getVariable(0xB7528D4CL /*_DiceValue*/) <= 40) {
			if (changeState(state -> Turn_L_5(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 40 && getVariable(0xB7528D4CL /*_DiceValue*/) <= 50) {
			if (changeState(state -> Turn_L_10(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 50 && getVariable(0xB7528D4CL /*_DiceValue*/) <= 60) {
			if (changeState(state -> Turn_L_15(blendTime)))
				return;
		}
		changeState(state -> Move_Random(blendTime));
	}

	protected void Turn_R_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x555A015CL /*Turn_R_5*/);
		doAction(2684842417L /*TURN_R_5*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Turn_R_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x292D10B5L /*Turn_R_10*/);
		doAction(620102569L /*TURN_R_10*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Turn_R_15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4CED4383L /*Turn_R_15*/);
		doAction(4066209630L /*TURN_R_15*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Turn_L_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1F128DA9L /*Turn_L_5*/);
		doAction(2611155810L /*TURN_L_5*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Turn_L_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA070F0AAL /*Turn_L_10*/);
		doAction(2726321949L /*TURN_L_10*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Turn_L_15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x853C7317L /*Turn_L_15*/);
		doAction(1875647211L /*TURN_L_15*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) >= 300000) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Random(blendTime), 1000));
	}

	protected void Die_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DC3CFB8L /*Die_Wait*/);
		doAction(425277756L /*DIE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 2000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 3000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) >= 300000) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 100, 0, 1, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Random(blendTime), 3000)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
