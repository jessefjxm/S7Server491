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
@IAIName("npc_shepherd_serendia")
public class Ai_npc_shepherd_serendia extends CreatureAI {
	public Ai_npc_shepherd_serendia(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xB3AF1929L /*_Dice*/, 0);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		setVariable(0xC50373B9L /*_PartyMoveCount*/, 0);
		setVariable(0xBE2FF277L /*_haveLostSheep*/, 0);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(8));
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		if (getVariable(0xC50373B9L /*_PartyMoveCount*/) > 5 && getVariable(0xBE2FF277L /*_haveLostSheep*/) == 1) {
			setVariable(0xBE2FF277L /*_haveLostSheep*/, 0);
		}
		if (getVariable(0xC50373B9L /*_PartyMoveCount*/) > 10 && getVariable(0xBE2FF277L /*_haveLostSheep*/) == 0) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleCanRunAway(getActor(), null));
		}
		if (getVariable(0xBE2FF277L /*_haveLostSheep*/) == 1) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleDonRunAway(getActor(), null));
		}
		setVariable(0xC50373B9L /*_PartyMoveCount*/, getVariable(0xC50373B9L /*_PartyMoveCount*/) + 1);
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			if (changeState(state -> Party_MovePoint_Start_1(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			if (changeState(state -> Party_MovePoint_Start_2(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			if (changeState(state -> Party_MovePoint_Start_3(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			if (changeState(state -> Party_MovePoint_Start_4(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			if (changeState(state -> Party_MovePoint_Start_5(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			if (changeState(state -> Party_MovePoint_Start_6(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			if (changeState(state -> Party_MovePoint_Start_7(blendTime)))
				return;
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			if (changeState(state -> Party_MovePoint_Start_8(blendTime)))
				return;
		}
		changeState(state -> Party_Wait(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (target != null && getDistanceToTarget(target) < 3000 && target != null && (getTargetCharacterKey(target) == 20305 || getTargetCharacterKey(target) == 20306 || getTargetCharacterKey(target) == 212)) {
			createParty(10, 10);
		}
		if (getPartyMembersCount()>= 5) {
			if (changeState(state -> Party_Wait(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Party_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x677C6416L /*Party_Wait*/);
		if (target != null && getDistanceToTarget(target) < 3000 && target != null && (getTargetCharacterKey(target) == 20305 || getTargetCharacterKey(target) == 20306 || getTargetCharacterKey(target) == 212)) {
			createParty(10, 10);
		}
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) <= 2) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Party_WalkRandom(blendTime)))
					return;
			}
		}
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) > 2) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getPartyMembersCount()== 0) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 1000));
	}

	protected void Party_WalkRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x11984335L /*Party_WalkRandom*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, getVariable(0x22A52166L /*_RandomMoveCount*/) + 1);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleStop(getActor(), null));
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 200, 400, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7C0D1FB5L /*Party_MovePoint_Start_1*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_1(blendTime)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE0E93AE6L /*Party_MovePoint_Wait_1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_1(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDB0DE4DFL /*Party_MovePoint_Go_1*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_1", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2DF7CA6L /*Party_MovePoint_Start_2*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_2(blendTime)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_2", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9DDDAF81L /*Party_MovePoint_Wait_2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_2(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x39C62023L /*Party_MovePoint_Go_2*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_2", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6C843A51L /*Party_MovePoint_Start_3*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_3(blendTime)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_3", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2A3E4FC7L /*Party_MovePoint_Wait_3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_3(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5A7B6E66L /*Party_MovePoint_Go_3*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_3", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9C17D706L /*Party_MovePoint_Start_4*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_4(blendTime)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_4", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE9C2866FL /*Party_MovePoint_Wait_4*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_4(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3DC0383DL /*Party_MovePoint_Go_4*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_4", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA2417838L /*Party_MovePoint_Start_5*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_5(blendTime)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_5", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7ACF9CA4L /*Party_MovePoint_Wait_5*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_5(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDCBDEEF3L /*Party_MovePoint_Go_5*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_5", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD172F3D8L /*Party_MovePoint_Start_6*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_6(blendTime)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_6", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD5EBC468L /*Party_MovePoint_Wait_6*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_7(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8E80E001L /*Party_MovePoint_Go_6*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_6", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA04F1E0BL /*Party_MovePoint_Start_7*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_7(blendTime)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_7", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFC91413EL /*Party_MovePoint_Wait_7*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_7(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCFCB0FE7L /*Party_MovePoint_Go_7*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_7", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Start_8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEF863015L /*Party_MovePoint_Start_8*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (changeState(state -> Party_MovePoint_Wait_8(blendTime)))
			return;
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_8", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MovePoint_Wait_8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBE70CB81L /*Party_MovePoint_Wait_8*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_MovePoint_Go_8(blendTime), 10000));
	}

	protected void Party_MovePoint_Go_8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE95A9D6EL /*Party_MovePoint_Go_8*/);
		setVariable(0xB3AF1929L /*_Dice*/, getRandom());
		if (getVariable(0xB3AF1929L /*_Dice*/) <= 8) {
			getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleGoPatrol(getActor(), null));
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "ram_8", ENaviType.ground, () -> {
			setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyCheck(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getDistanceToTarget(target) < 3000 && target != null && (getTargetCharacterKey(target) == 20305 || getTargetCharacterKey(target) == 20306 || getTargetCharacterKey(target) == 212)) {
			createParty(10, 10);
		}
		if (isPartyLeader()) {
			if (changeState(state -> Party_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleLostWay(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xBE2FF277L /*_haveLostSheep*/, 1);
		if (getVariable(0xBE2FF277L /*_haveLostSheep*/) == 0) {
			setVariable(0xC50373B9L /*_PartyMoveCount*/, 0);
		}
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleDonRunAway(getActor(), null));
		return EAiHandlerResult.BYPASS;
	}
}
