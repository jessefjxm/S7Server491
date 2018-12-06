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
@IAIName("typhoon_thunder")
public class Ai_typhoon_thunder extends CreatureAI {
	public Ai_typhoon_thunder(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xB7528D4CL /*_DiceValue*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0x73A51E5DL /*_UnSummonEndTime*/, getVariable(0x427A6FBDL /*AI_EndTime*/));
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Logic(blendTime), 100));
	}

	protected void Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x31999313L /*Turn_Logic*/);
		setVariable(0xB7528D4CL /*_DiceValue*/, getRandom(80));
		if (getVariable(0xB7528D4CL /*_DiceValue*/) <= 10) {
			if (changeState(state -> Turn_R_45(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 10 && getVariable(0xB7528D4CL /*_DiceValue*/) <= 20) {
			if (changeState(state -> Turn_R_90(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 20 && getVariable(0xB7528D4CL /*_DiceValue*/) <= 30) {
			if (changeState(state -> Turn_R_135(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 30 && getVariable(0xB7528D4CL /*_DiceValue*/) <= 40) {
			if (changeState(state -> Turn_L_45(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 40 && getVariable(0xB7528D4CL /*_DiceValue*/) <= 50) {
			if (changeState(state -> Turn_L_90(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 50 && getVariable(0xB7528D4CL /*_DiceValue*/) <= 60) {
			if (changeState(state -> Turn_L_135(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 60 && getVariable(0xB7528D4CL /*_DiceValue*/) <= 70) {
			if (changeState(state -> Turn_L_180(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > 70) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		changeState(state -> Move_Random(blendTime));
	}

	protected void Turn_R_45(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4AE988C9L /*Turn_R_45*/);
		doAction(2583672082L /*TURN_R_45*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Turn_R_90(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x62B7962EL /*Turn_R_90*/);
		doAction(1336687L /*TURN_R_90*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Turn_R_135(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA6E79B0BL /*Turn_R_135*/);
		doAction(945390307L /*TURN_R_135*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Turn_L_45(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x390E497EL /*Turn_L_45*/);
		doAction(3360892639L /*TURN_L_45*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Turn_L_90(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1567DA51L /*Turn_L_90*/);
		doAction(1604179339L /*TURN_L_90*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Turn_L_135(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x11A760EEL /*Turn_L_135*/);
		doAction(349425981L /*TURN_L_135*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Turn_L_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5455EE0L /*Turn_L_180*/);
		doAction(518614956L /*TURN_L_180*/, blendTime, onDoActionEnd -> changeState(state -> Move_Random(blendTime)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) >= getVariable(0x73A51E5DL /*_UnSummonEndTime*/)) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 100, 0, 1, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Random(blendTime), 3000)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) >= getVariable(0x73A51E5DL /*_UnSummonEndTime*/)) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 3000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
