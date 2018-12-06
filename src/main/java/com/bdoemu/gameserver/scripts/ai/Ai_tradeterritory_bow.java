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
@IAIName("tradeterritory_bow")
public class Ai_tradeterritory_bow extends CreatureAI {
	public Ai_tradeterritory_bow(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0xDA86981BL /*_EventTime*/, getVariable(0xC72F2D31L /*AI_EventTime*/));
		setVariable(0x8BE2723CL /*_EventOn_StartTime*/, 0);
		setVariable(0x13413249L /*_EventOn_IngTime*/, 0);
		setVariable(0xB61E93A4L /*_EventOn_EndTime*/, 0);
		setVariable(0x49668984L /*_TerritoryKey*/, getVariable(0xE0B5609FL /*AI_TerritoryKey*/));
		setVariable(0x8B407ECCL /*_NPCKey*/, getVariable(0x39BCDDF1L /*AI_NPCKey*/));
		setVariable(0xAC81906EL /*_UseRainFall_Event1*/, getVariable(0x6404F211L /*AI_UseRainFall_Event1*/));
		setVariable(0xA346DD6EL /*_UseRainFall_Event2*/, getVariable(0xCFFA9857L /*AI_UseRainFall_Event2*/));
		setVariable(0x891F4FFL /*_UseRainFall_Event3*/, getVariable(0x5C57F462L /*AI_UseRainFall_Event3*/));
		setVariable(0x7907DA41L /*_UseRainFall_Event4*/, getVariable(0x30B89A84L /*AI_UseRainFall_Event4*/));
		setVariable(0x8CEA867BL /*_UseRainFall_Event5*/, getVariable(0x70D6CD6EL /*AI_UseRainFall_Event5*/));
		setVariable(0xE708A395L /*_UseRainFall_Event6*/, getVariable(0x4566465CL /*AI_UseRainFall_Event6*/));
		setVariable(0x6A0D76C0L /*_UseRainFall_Event7*/, getVariable(0x831C7D01L /*AI_UseRainFall_Event7*/));
		setVariable(0xBB910C60L /*_UseRainFall_Event8*/, getVariable(0x31CC607DL /*AI_UseRainFall_Event8*/));
		setVariable(0x56BE5401L /*_UseRainFall_Event9*/, getVariable(0x31F05533L /*AI_UseRainFall_Event9*/));
		setVariable(0x6340B055L /*_UseRainFall_Event10*/, getVariable(0xBDE2D2DFL /*AI_UseRainFall_Event10*/));
		setVariable(0xB7528D4CL /*_DiceValue*/, 0);
		setVariable(0x38BA46AAL /*_EventPercent1*/, getVariable(0x52B5692EL /*AI_EventPercent1*/));
		setVariable(0xF70163CFL /*_EventPercent1Min*/, 0);
		setVariable(0x99474C9CL /*_EventPercent1Max*/, 0);
		setVariable(0x83A9940AL /*_EventPercent2*/, getVariable(0x4577DD01L /*AI_EventPercent2*/));
		setVariable(0xBE9B81E0L /*_EventPercent2Min*/, 0);
		setVariable(0x83851516L /*_EventPercent2Max*/, 0);
		setVariable(0xF2856E5CL /*_EventPercent3*/, getVariable(0x35ECDE9AL /*AI_EventPercent3*/));
		setVariable(0xCE9F01BBL /*_EventPercent3Min*/, 0);
		setVariable(0x46EC134BL /*_EventPercent3Max*/, 0);
		setVariable(0x50657294L /*_EventPercent4*/, getVariable(0xD1C4C9CAL /*AI_EventPercent4*/));
		setVariable(0xB03F8389L /*_EventPercent4Min*/, 0);
		setVariable(0x1CDCBEFFL /*_EventPercent4Max*/, 0);
		setVariable(0x1622CAB0L /*_EventPercent5*/, getVariable(0x2B0F2B0EL /*AI_EventPercent5*/));
		setVariable(0xC41692EAL /*_EventPercent5Min*/, 0);
		setVariable(0x740BB5ACL /*_EventPercent5Max*/, 0);
		setVariable(0xC89C643BL /*_EventPercent6*/, getVariable(0x2BC5DB7DL /*AI_EventPercent6*/));
		setVariable(0xEEE07352L /*_EventPercent6Min*/, 0);
		setVariable(0x46A844D7L /*_EventPercent6Max*/, 0);
		setVariable(0xE3D7B4EBL /*_EventPercent7*/, getVariable(0x202A513DL /*AI_EventPercent7*/));
		setVariable(0x6072C82AL /*_EventPercent7Min*/, 0);
		setVariable(0xC465EC7CL /*_EventPercent7Max*/, 0);
		setVariable(0xEA4AACCL /*_EventPercent8*/, getVariable(0x6848093FL /*AI_EventPercent8*/));
		setVariable(0x3C6D7409L /*_EventPercent8Min*/, 0);
		setVariable(0xE886248CL /*_EventPercent8Max*/, 0);
		setVariable(0x709F0F8L /*_EventPercent9*/, getVariable(0xA58F08ACL /*AI_EventPercent9*/));
		setVariable(0x7B618F58L /*_EventPercent9Min*/, 0);
		setVariable(0x379353CEL /*_EventPercent9Max*/, 0);
		setVariable(0x44AD201BL /*_EventPercent10*/, getVariable(0x5F2D36BCL /*AI_EventPercent10*/));
		setVariable(0xFC56EDC6L /*_EventPercent10Min*/, 0);
		setVariable(0x4CE1B67FL /*_EventPercent10Max*/, 0);
		setVariable(0x38BA46AAL /*_EventPercent1*/, getVariable(0x52B5692EL /*AI_EventPercent1*/) / 10000);
		setVariable(0x83A9940AL /*_EventPercent2*/, getVariable(0x4577DD01L /*AI_EventPercent2*/) / 10000);
		setVariable(0xF2856E5CL /*_EventPercent3*/, getVariable(0x35ECDE9AL /*AI_EventPercent3*/) / 10000);
		setVariable(0x50657294L /*_EventPercent4*/, getVariable(0xD1C4C9CAL /*AI_EventPercent4*/) / 10000);
		setVariable(0x1622CAB0L /*_EventPercent5*/, getVariable(0x2B0F2B0EL /*AI_EventPercent5*/) / 10000);
		setVariable(0xC89C643BL /*_EventPercent6*/, getVariable(0x2BC5DB7DL /*AI_EventPercent6*/) / 10000);
		setVariable(0xE3D7B4EBL /*_EventPercent7*/, getVariable(0x202A513DL /*AI_EventPercent7*/) / 10000);
		setVariable(0xEA4AACCL /*_EventPercent8*/, getVariable(0x6848093FL /*AI_EventPercent8*/) / 10000);
		setVariable(0x709F0F8L /*_EventPercent9*/, getVariable(0xA58F08ACL /*AI_EventPercent9*/) / 10000);
		setVariable(0x44AD201BL /*_EventPercent10*/, getVariable(0x5F2D36BCL /*AI_EventPercent10*/) / 10000);
		setVariable(0xF70163CFL /*_EventPercent1Min*/, 0);
		setVariable(0x99474C9CL /*_EventPercent1Max*/, getVariable(0x38BA46AAL /*_EventPercent1*/));
		setVariable(0xBE9B81E0L /*_EventPercent2Min*/, getVariable(0x99474C9CL /*_EventPercent1Max*/));
		setVariable(0x83851516L /*_EventPercent2Max*/, getVariable(0x99474C9CL /*_EventPercent1Max*/) + getVariable(0x83A9940AL /*_EventPercent2*/));
		setVariable(0xCE9F01BBL /*_EventPercent3Min*/, getVariable(0x83851516L /*_EventPercent2Max*/));
		setVariable(0x46EC134BL /*_EventPercent3Max*/, getVariable(0x83851516L /*_EventPercent2Max*/) + getVariable(0xF2856E5CL /*_EventPercent3*/));
		setVariable(0xB03F8389L /*_EventPercent4Min*/, getVariable(0x46EC134BL /*_EventPercent3Max*/));
		setVariable(0x1CDCBEFFL /*_EventPercent4Max*/, getVariable(0x46EC134BL /*_EventPercent3Max*/) + getVariable(0x50657294L /*_EventPercent4*/));
		setVariable(0xC41692EAL /*_EventPercent5Min*/, getVariable(0x1CDCBEFFL /*_EventPercent4Max*/));
		setVariable(0x740BB5ACL /*_EventPercent5Max*/, getVariable(0x1CDCBEFFL /*_EventPercent4Max*/) + getVariable(0x1622CAB0L /*_EventPercent5*/));
		setVariable(0xEEE07352L /*_EventPercent6Min*/, getVariable(0x740BB5ACL /*_EventPercent5Max*/));
		setVariable(0x46A844D7L /*_EventPercent6Max*/, getVariable(0x740BB5ACL /*_EventPercent5Max*/) + getVariable(0xC89C643BL /*_EventPercent6*/));
		setVariable(0x6072C82AL /*_EventPercent7Min*/, getVariable(0x46A844D7L /*_EventPercent6Max*/));
		setVariable(0xC465EC7CL /*_EventPercent7Max*/, getVariable(0x46A844D7L /*_EventPercent6Max*/) + getVariable(0xE3D7B4EBL /*_EventPercent7*/));
		setVariable(0x3C6D7409L /*_EventPercent8Min*/, getVariable(0xC465EC7CL /*_EventPercent7Max*/));
		setVariable(0xE886248CL /*_EventPercent8Max*/, getVariable(0xC465EC7CL /*_EventPercent7Max*/) + getVariable(0xEA4AACCL /*_EventPercent8*/));
		setVariable(0x7B618F58L /*_EventPercent9Min*/, getVariable(0xE886248CL /*_EventPercent8Max*/));
		setVariable(0x379353CEL /*_EventPercent9Max*/, getVariable(0xE886248CL /*_EventPercent8Max*/) + getVariable(0x709F0F8L /*_EventPercent9*/));
		setVariable(0xFC56EDC6L /*_EventPercent10Min*/, getVariable(0x379353CEL /*_EventPercent9Max*/));
		setVariable(0x4CE1B67FL /*_EventPercent10Max*/, getVariable(0x379353CEL /*_EventPercent9Max*/) + getVariable(0x44AD201BL /*_EventPercent10*/));
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0x9225781EL /*_GreetingTime*/, 0);
		setVariable(0x78843D57L /*_GreetingElapsed*/, 0);
		setVariable(0x6C8A49C5L /*_GreetingCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Logic(blendTime), 1000));
	}

	protected void Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9048C900L /*Wait_Logic*/);
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		changeState(state -> Wait(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x78843D57L /*_GreetingElapsed*/, getTime() - getVariable(0x9225781EL /*_GreetingTime*/));
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) >= getVariable(0xC72F2D31L /*AI_EventTime*/)) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Event_Logic(blendTime)))
					return;
			}
		}
		if (getVariable(0x6C8A49C5L /*_GreetingCount*/) < 1) {
			if (findTarget(EAIFindTargetType.LordOrKingPlayer, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000)) {
				if (changeState(state -> Greeting(0.3)))
					return;
			}
		}
		if (getVariable(0x6C8A49C5L /*_GreetingCount*/) >= 1 && getVariable(0x78843D57L /*_GreetingElapsed*/) >= 60000) {
			if (findTarget(EAIFindTargetType.LordOrKingPlayer, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000)) {
				if (changeState(state -> Greeting_Second(0.3)))
					return;
			}
		}
		if (getVariable(0x78843D57L /*_GreetingElapsed*/) >= 60000 && getVariable(0xF630F33AL /*_Distance*/) > 1000) {
			if (changeState(state -> Turn_Spawn(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2500));
	}

	protected void Greeting(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x57CB1F04L /*Greeting*/);
		doAction(1029767713L /*BOW*/, blendTime, onDoActionEnd -> {
			setVariable(0x9225781EL /*_GreetingTime*/, getTime());
			setVariable(0x6C8A49C5L /*_GreetingCount*/, getVariable(0x6C8A49C5L /*_GreetingCount*/) + 1);
			scheduleState(state -> Wait(blendTime), 1000);
		});
	}

	protected void Greeting_Second(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFDB968D4L /*Greeting_Second*/);
		doAction(1029767713L /*BOW*/, blendTime, onDoActionEnd -> {
			setVariable(0x6C8A49C5L /*_GreetingCount*/, getVariable(0x6C8A49C5L /*_GreetingCount*/) + 1);
			setVariable(0x9225781EL /*_GreetingTime*/, getTime());
			scheduleState(state -> Wait(blendTime), 1000);
		});
	}

	protected void Turn_Spawn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xE67FFF90L /*Turn_Spawn*/);
		clearAggro(true);
		setVariable(0x6C8A49C5L /*_GreetingCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Event_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8134DA0EL /*Event_Logic*/);
		setVariable(0xB7528D4CL /*_DiceValue*/, getRandom(100));
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xF70163CFL /*_EventPercent1Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x99474C9CL /*_EventPercent1Max*/) && getVariable(0x38BA46AAL /*_EventPercent1*/) != 0 && getVariable(0xAC81906EL /*_UseRainFall_Event1*/) == 0) {
			if (changeState(state -> Event1(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xF70163CFL /*_EventPercent1Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x99474C9CL /*_EventPercent1Max*/) && getVariable(0x38BA46AAL /*_EventPercent1*/) != 0 && getVariable(0xAC81906EL /*_UseRainFall_Event1*/) == 1 && checkWeather(EWeatherFactorType.Water) >= getVariable(0x938385BEL /*AI_RainFall_Event1*/)) {
			if (changeState(state -> Event1(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xBE9B81E0L /*_EventPercent2Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x83851516L /*_EventPercent2Max*/) && getVariable(0x83A9940AL /*_EventPercent2*/) != 0 && getVariable(0xA346DD6EL /*_UseRainFall_Event2*/) == 0) {
			if (changeState(state -> Event2(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xBE9B81E0L /*_EventPercent2Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x83851516L /*_EventPercent2Max*/) && getVariable(0x83A9940AL /*_EventPercent2*/) != 0 && getVariable(0xA346DD6EL /*_UseRainFall_Event2*/) == 1 && checkWeather(EWeatherFactorType.Water) >= getVariable(0x2F563D71L /*AI_RainFall_Event2*/)) {
			if (changeState(state -> Event2(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xCE9F01BBL /*_EventPercent3Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x46EC134BL /*_EventPercent3Max*/) && getVariable(0xF2856E5CL /*_EventPercent3*/) != 0 && getVariable(0x891F4FFL /*_UseRainFall_Event3*/) == 0) {
			if (changeState(state -> Event3(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xCE9F01BBL /*_EventPercent3Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x46EC134BL /*_EventPercent3Max*/) && getVariable(0xF2856E5CL /*_EventPercent3*/) != 0 && getVariable(0x891F4FFL /*_UseRainFall_Event3*/) == 1 && checkWeather(EWeatherFactorType.Water) >= getVariable(0xB03121C3L /*AI_RainFall_Event3*/)) {
			if (changeState(state -> Event3(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xB03F8389L /*_EventPercent4Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x1CDCBEFFL /*_EventPercent4Max*/) && getVariable(0x50657294L /*_EventPercent4*/) != 0 && getVariable(0x7907DA41L /*_UseRainFall_Event4*/) == 0) {
			if (changeState(state -> Event4(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xB03F8389L /*_EventPercent4Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x1CDCBEFFL /*_EventPercent4Max*/) && getVariable(0x50657294L /*_EventPercent4*/) != 0 && getVariable(0x7907DA41L /*_UseRainFall_Event4*/) == 1 && checkWeather(EWeatherFactorType.Water) >= getVariable(0x9C6860CCL /*AI_RainFall_Event4*/)) {
			if (changeState(state -> Event4(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xC41692EAL /*_EventPercent5Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x740BB5ACL /*_EventPercent5Max*/) && getVariable(0x1622CAB0L /*_EventPercent5*/) != 0 && getVariable(0x8CEA867BL /*_UseRainFall_Event5*/) == 0) {
			if (changeState(state -> Event5(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xC41692EAL /*_EventPercent5Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x740BB5ACL /*_EventPercent5Max*/) && getVariable(0x1622CAB0L /*_EventPercent5*/) != 0 && getVariable(0x8CEA867BL /*_UseRainFall_Event5*/) == 1 && checkWeather(EWeatherFactorType.Water) >= getVariable(0xDECF19B9L /*AI_RainFall_Event5*/)) {
			if (changeState(state -> Event5(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xEEE07352L /*_EventPercent6Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x46A844D7L /*_EventPercent6Max*/) && getVariable(0xC89C643BL /*_EventPercent6*/) != 0 && getVariable(0xE708A395L /*_UseRainFall_Event6*/) == 0) {
			if (changeState(state -> Event6(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xEEE07352L /*_EventPercent6Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x46A844D7L /*_EventPercent6Max*/) && getVariable(0xC89C643BL /*_EventPercent6*/) != 0 && getVariable(0xE708A395L /*_UseRainFall_Event6*/) == 1 && checkWeather(EWeatherFactorType.Water) <= getVariable(0x571093DCL /*AI_RainFall_Event6*/)) {
			if (changeState(state -> Event6(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0x6072C82AL /*_EventPercent7Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0xC465EC7CL /*_EventPercent7Max*/) && getVariable(0xE3D7B4EBL /*_EventPercent7*/) != 0 && getVariable(0x6A0D76C0L /*_UseRainFall_Event7*/) == 0) {
			if (changeState(state -> Event7(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0x6072C82AL /*_EventPercent7Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0xC465EC7CL /*_EventPercent7Max*/) && getVariable(0xE3D7B4EBL /*_EventPercent7*/) != 0 && getVariable(0x6A0D76C0L /*_UseRainFall_Event7*/) == 1 && checkWeather(EWeatherFactorType.Water) <= getVariable(0x29F0AF77L /*AI_RainFall_Event7*/)) {
			if (changeState(state -> Event7(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0x3C6D7409L /*_EventPercent8Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0xE886248CL /*_EventPercent8Max*/) && getVariable(0xEA4AACCL /*_EventPercent8*/) != 0 && getVariable(0xBB910C60L /*_UseRainFall_Event8*/) == 0) {
			if (changeState(state -> Event8(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0x3C6D7409L /*_EventPercent8Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0xE886248CL /*_EventPercent8Max*/) && getVariable(0xEA4AACCL /*_EventPercent8*/) != 0 && getVariable(0xBB910C60L /*_UseRainFall_Event8*/) == 1 && checkWeather(EWeatherFactorType.Water) <= getVariable(0x6077C999L /*AI_RainFall_Event8*/)) {
			if (changeState(state -> Event8(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0x379353CEL /*_EventPercent9Max*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x379353CEL /*_EventPercent9Max*/) && getVariable(0x709F0F8L /*_EventPercent9*/) != 0 && getVariable(0x56BE5401L /*_UseRainFall_Event9*/) == 0) {
			if (changeState(state -> Event9(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0x7B618F58L /*_EventPercent9Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0x7B618F58L /*_EventPercent9Min*/) && getVariable(0x709F0F8L /*_EventPercent9*/) != 0 && getVariable(0x56BE5401L /*_UseRainFall_Event9*/) == 1 && checkWeather(EWeatherFactorType.Water) <= getVariable(0xCCF09FB4L /*AI_RainFall_Event9*/)) {
			if (changeState(state -> Event9(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xFC56EDC6L /*_EventPercent10Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0xFC56EDC6L /*_EventPercent10Min*/) && getVariable(0x44AD201BL /*_EventPercent10*/) != 0 && getVariable(0x6340B055L /*_UseRainFall_Event10*/) == 0) {
			if (changeState(state -> Event10(blendTime)))
				return;
		}
		if (getVariable(0xB7528D4CL /*_DiceValue*/) > getVariable(0xFC56EDC6L /*_EventPercent10Min*/) && getVariable(0xB7528D4CL /*_DiceValue*/) <= getVariable(0xFC56EDC6L /*_EventPercent10Min*/) && getVariable(0x44AD201BL /*_EventPercent10*/) != 0 && getVariable(0x6340B055L /*_UseRainFall_Event10*/) == 1 && checkWeather(EWeatherFactorType.Water) <= getVariable(0x5B724686L /*AI_RainFall_Event10*/)) {
			if (changeState(state -> Event10(blendTime)))
				return;
		}
		changeState(state -> Wait_Logic(blendTime));
	}

	protected void Event1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2F2F26A7L /*Event1*/);
		if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(getVariable(0xE0B5609FL /*AI_TerritoryKey*/), -1, categoryPercentData, 1);
		}
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(-1, getVariable(0xC539BD1L /*npcKey*/), categoryPercentData, 1);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
				getObjects(EAIFindTargetType.Character, object -> true).forEach(consumer -> consumer.getAi().HandlerTypeHappenEvent(getActor(), null));
			}
			scheduleState(state -> Wait_Logic(blendTime), 2500 + Rnd.get(-500,500));
		});
	}

	protected void Event2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE16F7D05L /*Event2*/);
		if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(getVariable(0xE0B5609FL /*AI_TerritoryKey*/), -1, categoryPercentData, 2);
		}
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(-1, getVariable(0xC539BD1L /*npcKey*/), categoryPercentData, 2);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
				getObjects(EAIFindTargetType.Character, object -> true).forEach(consumer -> consumer.getAi().HandlerTypeHappenEvent(getActor(), null));
			}
			scheduleState(state -> Wait_Logic(blendTime), 2500 + Rnd.get(-500,500));
		});
	}

	protected void Event3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC890E93AL /*Event3*/);
		if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(getVariable(0xE0B5609FL /*AI_TerritoryKey*/), -1, categoryPercentData, 3);
		}
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(-1, getVariable(0xC539BD1L /*npcKey*/), categoryPercentData, 3);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
				getObjects(EAIFindTargetType.Character, object -> true).forEach(consumer -> consumer.getAi().HandlerTypeHappenEvent(getActor(), null));
			}
			scheduleState(state -> Wait_Logic(blendTime), 2500 + Rnd.get(-500,500));
		});
	}

	protected void Event4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD853D3B4L /*Event4*/);
		if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(getVariable(0xE0B5609FL /*AI_TerritoryKey*/), -1, categoryPercentData, 4);
		}
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(-1, getVariable(0xC539BD1L /*npcKey*/), categoryPercentData, 4);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
				getObjects(EAIFindTargetType.Character, object -> true).forEach(consumer -> consumer.getAi().HandlerTypeHappenEvent(getActor(), null));
			}
			scheduleState(state -> Wait_Logic(blendTime), 2500 + Rnd.get(-500,500));
		});
	}

	protected void Event5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD8AF92EFL /*Event5*/);
		if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(getVariable(0xE0B5609FL /*AI_TerritoryKey*/), -1, categoryPercentData, 5);
		}
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(-1, getVariable(0xC539BD1L /*npcKey*/), categoryPercentData, 5);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
				getObjects(EAIFindTargetType.Character, object -> true).forEach(consumer -> consumer.getAi().HandlerTypeHappenEvent(getActor(), null));
			}
			scheduleState(state -> Wait_Logic(blendTime), 2500 + Rnd.get(-500,500));
		});
	}

	protected void Event6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4F712F77L /*Event6*/);
		if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(getVariable(0xE0B5609FL /*AI_TerritoryKey*/), -1, categoryPercentData, 6);
		}
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(-1, getVariable(0xC539BD1L /*npcKey*/), categoryPercentData, 6);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
				getObjects(EAIFindTargetType.Character, object -> true).forEach(consumer -> consumer.getAi().HandlerTypeHappenEvent(getActor(), null));
			}
			scheduleState(state -> Wait_Logic(blendTime), 2500 + Rnd.get(-500,500));
		});
	}

	protected void Event7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC4CC3C4FL /*Event7*/);
		if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(getVariable(0xE0B5609FL /*AI_TerritoryKey*/), -1, categoryPercentData, 7);
		}
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(-1, getVariable(0xC539BD1L /*npcKey*/), categoryPercentData, 7);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
				getObjects(EAIFindTargetType.Character, object -> true).forEach(consumer -> consumer.getAi().HandlerTypeHappenEvent(getActor(), null));
			}
			scheduleState(state -> Wait_Logic(blendTime), 2500 + Rnd.get(-500,500));
		});
	}

	protected void Event8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAB69D858L /*Event8*/);
		if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(getVariable(0xE0B5609FL /*AI_TerritoryKey*/), -1, categoryPercentData, 8);
		}
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(-1, getVariable(0xC539BD1L /*npcKey*/), categoryPercentData, 8);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
				getObjects(EAIFindTargetType.Character, object -> true).forEach(consumer -> consumer.getAi().HandlerTypeHappenEvent(getActor(), null));
			}
			scheduleState(state -> Wait_Logic(blendTime), 2500 + Rnd.get(-500,500));
		});
	}

	protected void Event9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCC85DBF0L /*Event9*/);
		if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(getVariable(0xE0B5609FL /*AI_TerritoryKey*/), -1, categoryPercentData, 9);
		}
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(-1, getVariable(0xC539BD1L /*npcKey*/), categoryPercentData, 9);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
				getObjects(EAIFindTargetType.Character, object -> true).forEach(consumer -> consumer.getAi().HandlerTypeHappenEvent(getActor(), null));
			}
			scheduleState(state -> Wait_Logic(blendTime), 2500 + Rnd.get(-500,500));
		});
	}

	protected void Event10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFAB08CF7L /*Event10*/);
		if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(getVariable(0xE0B5609FL /*AI_TerritoryKey*/), -1, categoryPercentData, 10);
		}
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			HashMap<ETradeCommerceType, Long> categoryPercentData = new HashMap<ETradeCommerceType, Long>();
			setTradePrice(-1, getVariable(0xC539BD1L /*npcKey*/), categoryPercentData, 10);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x8B407ECCL /*_NPCKey*/) == 0) {
				getObjects(EAIFindTargetType.Character, object -> true).forEach(consumer -> consumer.getAi().HandlerTypeHappenEvent(getActor(), null));
			}
			scheduleState(state -> Wait_Logic(blendTime), 2500 + Rnd.get(-500,500));
		});
	}

	protected void Event_Off(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB03C455AL /*Event_Off*/);
		setVariable(0x13413249L /*_EventOn_IngTime*/, getTime());
		setVariable(0xB61E93A4L /*_EventOn_EndTime*/, getVariable(0x13413249L /*_EventOn_IngTime*/) - getVariable(0x8BE2723CL /*_EventOn_StartTime*/));
		if (getVariable(0xB61E93A4L /*_EventOn_EndTime*/) >= getVariable(0x1C87A369L /*AI_EventOn_Time*/)) {
			if (changeState(state -> Wait_Logic(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Event_Off(blendTime), 1000));
	}

	protected void Fright(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x654BC56AL /*Fright*/);
		doAction(32379601L /*FRIGHT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFailSteal(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender);
		getObjects(EAIFindTargetType.Npc, object -> getDistanceToTarget(object) < 2000).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		if (changeState(state -> Fright(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTypeHappenEvent(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			setVariable(0x8BE2723CL /*_EventOn_StartTime*/, getTime());
		}
		if (getVariable(0x8B407ECCL /*_NPCKey*/) != 0) {
			if (changeState(state -> Event_Off(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
