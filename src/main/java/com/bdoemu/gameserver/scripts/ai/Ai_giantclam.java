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
@IAIName("giantclam")
public class Ai_giantclam extends CreatureAI {
	public Ai_giantclam(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5E8EBAL /*Wait_75*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		doAction(624644351L /*WAIT_75*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_75(blendTime), 1000));
	}

	protected void Wait_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x42D479D0L /*Wait_50*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		doAction(1687297596L /*WAIT_50*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_50(blendTime), 1000));
	}

	protected void Wait_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x210809BL /*Wait_25*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		doAction(714400461L /*WAIT_25*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_25(blendTime), 1000));
	}

	protected void Damage_75(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x72132ECDL /*Damage_75*/);
		doAction(4075634643L /*DAMAGE_75*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_75(blendTime), 1000));
	}

	protected void Damage_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC6E66A1BL /*Damage_50*/);
		doAction(4158814158L /*DAMAGE_50*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_50(blendTime), 1000));
	}

	protected void Damage_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2B7613DAL /*Damage_25*/);
		doAction(1904061850L /*DAMAGE_25*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_25(blendTime), 1000));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2737950888L /*DELETE_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getState() == 0x866C7489L /*Wait*/ && getVariable(0x3F487035L /*_HP*/) <= 75 && getVariable(0x3F487035L /*_HP*/) > 50) {
			if (changeState(state -> Damage_75(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x5E8EBAL /*Wait_75*/ && getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0x3F487035L /*_HP*/) > 25) {
			if (changeState(state -> Damage_50(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x42D479D0L /*Wait_50*/ && getVariable(0x3F487035L /*_HP*/) <= 25 && getVariable(0x3F487035L /*_HP*/) > 0) {
			if (changeState(state -> Damage_25(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
