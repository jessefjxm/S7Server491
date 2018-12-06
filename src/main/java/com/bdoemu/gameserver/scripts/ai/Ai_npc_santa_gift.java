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
@IAIName("npc_santa_gift")
public class Ai_npc_santa_gift extends CreatureAI {
	public Ai_npc_santa_gift(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0x2594941FL /*_isAir*/, 0);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Fall(blendTime), 1000));
	}

	protected void Fall(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x433E1948L /*Fall*/);
		setVariable(0x2594941FL /*_isAir*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("air") ? 1 : 0));
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 10000 && getVariable(0x2594941FL /*_isAir*/) == 1) {
			if (changeState(state -> Box_Hide(blendTime)))
				return;
		}
		doAction(2369942272L /*FALLING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ground_Wait(blendTime), 1000)));
	}

	protected void Fall_ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD5CC3C50L /*Fall_ing*/);
		setVariable(0x2594941FL /*_isAir*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("air") ? 1 : 0));
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 10000 && getVariable(0x2594941FL /*_isAir*/) == 1) {
			if (changeState(state -> Box_Hide(blendTime)))
				return;
		}
		doAction(2369942272L /*FALLING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Ground_Wait(blendTime), 1000)));
	}

	protected void Ground_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB78DF991L /*Ground_Wait*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 180000) {
			if (changeState(state -> Box_Hide(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Ground_Wait(blendTime), 1000));
	}

	protected void Chest_open(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE6D8715FL /*Chest_open*/);
		doAction(639015291L /*OPEN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Box_Hide(blendTime), 1000));
	}

	protected void Box_Hide(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9DCC6910L /*Box_Hide*/);
		doAction(1604323777L /*HIDE_BOX*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 1000));
	}

	protected void Suicide_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x2BD8C797L /*Suicide_Die*/);
		doAction(2707805806L /*SUICIDE_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTalkToDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xB78DF991L /*Ground_Wait*/) {
			if (changeState(state -> Chest_open(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
