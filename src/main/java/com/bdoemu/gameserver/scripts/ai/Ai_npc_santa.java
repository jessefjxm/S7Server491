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
@IAIName("npc_santa")
public class Ai_npc_santa extends CreatureAI {
	public Ai_npc_santa(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariableArray(0x2C4960E5L /*_WaypointValue1*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x3DFFB456L /*AI_WayPointKey1*/) });
		setVariableArray(0x11AADB03L /*_WaypointValue2*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xCD7084F0L /*AI_WayPointKey2*/) });
		setVariable(0xFD0EAB80L /*_RouteCount*/, 0);
		setVariable(0xADB50864L /*_ChrismasGift_Count*/, 0);
		setVariable(0x1302166FL /*_Moving_Count*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0x7486A6F8L /*_AiCount*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(544489780L /*INVISIBLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Start_Snow(blendTime)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
			if (changeState(state -> Die_Wait(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 5000));
	}

	protected void Die_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DC3CFB8L /*Die_Wait*/);
		doAction(1926787974L /*HIDEMESH*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 3000));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	protected void Start_Snow(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x438604D5L /*Start_Snow*/);
		doAction(3235671119L /*SUMMON_SNOW*/, blendTime, onDoActionEnd -> changeState(state -> Start_Wait(blendTime)));
	}

	protected void Start_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9D7E92CDL /*Start_Wait*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 300000) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(544489780L /*INVISIBLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Wait(blendTime), 10000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x7486A6F8L /*_AiCount*/, 0);
		if (getVariable(0xFD0EAB80L /*_RouteCount*/) == 2) {
			if (changeState(state -> Move_1(blendTime)))
				return;
		}
		if (getVariable(0xFD0EAB80L /*_RouteCount*/) == 1) {
			if (changeState(state -> Move(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move(blendTime), 10000));
	}

	protected void Move(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x93D65E00L /*Move*/);
		setVariable(0xFD0EAB80L /*_RouteCount*/, 1);
		setVariable(0x7486A6F8L /*_AiCount*/, getVariable(0x7486A6F8L /*_AiCount*/) + 1);
		setVariable(0x1302166FL /*_Moving_Count*/, getVariable(0x1302166FL /*_Moving_Count*/) + 1);
		if (getVariable(0xADB50864L /*_ChrismasGift_Count*/) < 56 && getVariable(0x1302166FL /*_Moving_Count*/) > 15) {
			if(Rnd.getChance(12)) {
				if (changeState(state -> Gift_Drop(0.3)))
					return;
			}
		}
		if (getVariable(0xADB50864L /*_ChrismasGift_Count*/) >= 55) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Die_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x7486A6F8L /*_AiCount*/) > 5) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> Die_Wait(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_1(blendTime), 4000)));
	}

	protected void Move_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3790864EL /*Move_1*/);
		setVariable(0xFD0EAB80L /*_RouteCount*/, 2);
		setVariable(0x7486A6F8L /*_AiCount*/, getVariable(0x7486A6F8L /*_AiCount*/) + 1);
		setVariable(0x1302166FL /*_Moving_Count*/, getVariable(0x1302166FL /*_Moving_Count*/) + 1);
		if (getVariable(0xADB50864L /*_ChrismasGift_Count*/) < 56 && getVariable(0x1302166FL /*_Moving_Count*/) > 15) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Gift_Drop(0.3)))
					return;
			}
		}
		if (getVariable(0xADB50864L /*_ChrismasGift_Count*/) >= 55) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Die_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x7486A6F8L /*_AiCount*/) > 5) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> Die_Wait(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move(blendTime), 4000)));
	}

	protected void Gift_Drop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC1C7A7CDL /*Gift_Drop*/);
		setVariable(0xADB50864L /*_ChrismasGift_Count*/, getVariable(0xADB50864L /*_ChrismasGift_Count*/) + 1);
		setVariable(0x1302166FL /*_Moving_Count*/, 0);
		doAction(857695109L /*GIFTDROP*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
