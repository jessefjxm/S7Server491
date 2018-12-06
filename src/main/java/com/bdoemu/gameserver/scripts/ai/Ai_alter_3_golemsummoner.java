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
@IAIName("alter_3_golemsummoner")
public class Ai_alter_3_golemsummoner extends CreatureAI {
	public Ai_alter_3_golemsummoner(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x7B513297L /*_Count*/, 0);
		setVariable(0xA03782F4L /*_Count1*/, 0);
		setVariable(0x99703876L /*_SummonCount*/, 0);
		setVariable(0x4B5B7DBBL /*_isNumber*/, getVariable(0x56DDABCCL /*AI_Number*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TurningPoint(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void TurningPoint(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xC5D5028EL /*TurningPoint*/);
		doAction(2910378664L /*WAIT_ROTATE*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -400982, -306419, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> TypeSelect_Logic(blendTime), 1000)));
	}

	protected void TypeSelect_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x98664107L /*TypeSelect_Logic*/);
		if (getVariable(0x4B5B7DBBL /*_isNumber*/) == 0) {
			if (changeState(state -> Detect_Target_Red(blendTime)))
				return;
		}
		if (getVariable(0x4B5B7DBBL /*_isNumber*/) == 1) {
			if (changeState(state -> Detect_Target_Green(blendTime)))
				return;
		}
		changeState(state -> TypeSelect_Logic(blendTime));
	}

	protected void Detect_Target_Red(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF7CE6137L /*Detect_Target_Red*/);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Detect_Target_Green(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCEFD2E90L /*Detect_Target_Green*/);
		doAction(2531377426L /*SEARCH_ENEMY2*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x71F28994L /*Battle_Wait*/);
		if (checkInstanceTeamNo() && target != null && getTargetCharacterKey(target) == 27282) {
			setVariable(0x7B513297L /*_Count*/, findCharacterCount(EAIFindTargetType.Monster, true, object -> getDistanceTo(object) >= 0 && getDistanceTo(object) <= 250));
		}
		if (getVariable(0x7B513297L /*_Count*/) > 0) {
			if (changeState(state -> Paralysis_Logic(blendTime)))
				return;
		}
		if (getPartyMembersCount()>= 30) {
			if (changeState(state -> Pause(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> SummonLogic(blendTime)));
	}

	protected void SummonLogic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA2B9CD94L /*SummonLogic*/);
		setVariable(0x99703876L /*_SummonCount*/, getVariable(0x99703876L /*_SummonCount*/) + 1);
							if (getVariable(0x4B5B7DBBL /*_isNumber*/) == 0 && getVariable(0x99703876L /*_SummonCount*/) == 3) {
				if (changeState(state -> Junk_Notifier_Logic(blendTime)))
					return;
			}
			if (getVariable(0x4B5B7DBBL /*_isNumber*/) == 0 && getVariable(0x99703876L /*_SummonCount*/) > 3) {
				if (changeState(state -> Battle_Wait_Junk(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
	}

	protected void Junk_Notifier_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDA349B93L /*Junk_Notifier_Logic*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_3STAGE_JUNKGOLEM");
		changeState(state -> Battle_Wait_Junk(blendTime));
	}

	protected void Battle_Wait1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD649EC78L /*Battle_Wait1*/);
		if (checkInstanceTeamNo() && target != null && getTargetCharacterKey(target) == 27282) {
			setVariable(0xA03782F4L /*_Count1*/, findCharacterCount(EAIFindTargetType.Monster, true, object -> getDistanceTo(object) >= 0 && getDistanceTo(object) <= 200));
		}
		if (getVariable(0xA03782F4L /*_Count1*/) > 0) {
			if (changeState(state -> Paralysis_Logic(blendTime)))
				return;
		}
		if (getPartyMembersCount()>= 30) {
			if (changeState(state -> Pause(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Junk2(blendTime)));
	}

	protected void Battle_Wait_Junk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9F24B7EBL /*Battle_Wait_Junk*/);
		if (checkInstanceTeamNo() && target != null && getTargetCharacterKey(target) == 27282) {
			setVariable(0x7B513297L /*_Count*/, findCharacterCount(EAIFindTargetType.Monster, true, object -> getDistanceTo(object) >= 0 && getDistanceTo(object) <= 200));
		}
		if (getVariable(0x7B513297L /*_Count*/) > 0) {
			if (changeState(state -> Paralysis_Logic(blendTime)))
				return;
		}
		doAction(4211860683L /*BATTLE_WAIT_REDJUNK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Wait_Junk2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBC31049EL /*Battle_Wait_Junk2*/);
		if (checkInstanceTeamNo() && target != null && getTargetCharacterKey(target) == 27282) {
			setVariable(0xA03782F4L /*_Count1*/, findCharacterCount(EAIFindTargetType.Monster, true, object -> getDistanceTo(object) >= 0 && getDistanceTo(object) <= 200));
		}
		if (getVariable(0xA03782F4L /*_Count1*/) > 0) {
			if (changeState(state -> Paralysis_Logic(blendTime)))
				return;
		}
		doAction(4211860683L /*BATTLE_WAIT_REDJUNK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait1(blendTime)));
	}

	protected void Paralysis_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x57F5EFACL /*Paralysis_Logic*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 500 && getTargetCharacterKey(object) == 27282).forEach(consumer -> consumer.getAi().Golem_Eater(getActor(), null));
		}
		if (getVariable(0x7B513297L /*_Count*/) > 0 && getVariable(0xA03782F4L /*_Count1*/) == 0) {
			if (changeState(state -> Paralysis(blendTime)))
				return;
		}
		if (getVariable(0xA03782F4L /*_Count1*/) > 0) {
			if (changeState(state -> Die_Logic(blendTime)))
				return;
		}
		changeState(state -> Paralysis_Logic(blendTime));
	}

	protected void Paralysis(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEBAEE48CL /*Paralysis*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_3STAGE_SUMMONBREAK_RED");
		doAction(1575110744L /*PARALYSIS*/, blendTime, onDoActionEnd -> scheduleState(state -> RecoveryNotifier(blendTime), 1000));
	}

	protected void RecoveryNotifier(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5A92547CL /*RecoveryNotifier*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_3STAGE_GOLEMSUMMONER_RED");
		changeState(state -> Battle_Wait1(blendTime));
	}

	protected void Pause(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE87A8FBCL /*Pause*/);
		if (getPartyMembersCount()<= 10 && getVariable(0x7B513297L /*_Count*/) > 0) {
			if (changeState(state -> Battle_Wait1(blendTime)))
				return;
		}
		if (getPartyMembersCount()<= 10) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(1575110744L /*PARALYSIS*/, blendTime, onDoActionEnd -> scheduleState(state -> Pause(blendTime), 1000));
	}

	protected void Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE70D4D89L /*Die_Logic*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_3STAGE_SUMMONBREAK_RED2");
		changeState(state -> Die(blendTime));
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
		doAction(2737950888L /*DELETE_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult ChildAllDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
