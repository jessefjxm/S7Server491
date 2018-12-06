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
@IAIName("npc_shepherd_dog")
public class Ai_npc_shepherd_dog extends CreatureAI {
	public Ai_npc_shepherd_dog(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		setVariable(0xBB86C5A3L /*_BattleMode*/, 0);
		setVariable(0xD4E69EAEL /*_PatrolOder*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Random(blendTime), 10000));
	}

	protected void Wait_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x96AB8779L /*Wait_1*/);
		doAction(2022255091L /*WAIT02*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Random_1(blendTime), 10000));
	}

	protected void Wait_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD6E9C46CL /*Wait_2*/);
		doAction(3179239796L /*WAIT03*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Random_2(blendTime), 10000));
	}

	protected void Wait_Idle1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFD76F44DL /*Wait_Idle1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_1(blendTime), 10000));
	}

	protected void Wait_Idle2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x637E7B28L /*Wait_Idle2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_2(blendTime), 10000));
	}

	protected void Wait_Idle3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE7C1385L /*Wait_Idle3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 10000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 700, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle1(blendTime), 10000)));
	}

	protected void Move_Random_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF315D8BBL /*Move_Random_1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 700, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle2(blendTime), 10000)));
	}

	protected void Move_Random_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3277C3A5L /*Move_Random_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 700, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle3(blendTime), 10000)));
	}

	protected void Party_MoveToOwner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3675B059L /*Party_MoveToOwner*/);
		if (findTarget(EAIFindTargetType.Character, EAIFindType.nearest, false, object -> (getTargetCharacterKey(object) == 20306 || getTargetCharacterKey(object) == 20305))) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 100, 100, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Logic(blendTime), 1000)));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0xE5BD13F2L /*_Degree*/) != 0) {
			if (changeState(state -> Party_Wait(blendTime)))
				return;
		}
		changeState(state -> Party_MoveToOwner(blendTime));
	}

	protected void Party_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x677C6416L /*Party_Wait*/);
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		if (getVariable(0xF630F33AL /*_Distance*/) >= 1000) {
			if (changeState(state -> Party_MoveToTarget(blendTime)))
				return;
		}
		if (getVariable(0xD4E69EAEL /*_PatrolOder*/) == 1) {
			if (changeState(state -> Party_MoveAround(blendTime)))
				return;
		}
		if (getVariable(0x6F05E9AFL /*_FollowMe*/) == 1) {
			if (changeState(state -> Party_MoveToParent(blendTime)))
				return;
		}
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) <= 2) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Party_WalkRandom(blendTime)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 1000 + Rnd.get(-200,200)));
	}

	protected void Party_WalkRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x11984335L /*Party_WalkRandom*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, getVariable(0x22A52166L /*_RandomMoveCount*/) + 1);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 300, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MoveToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x133E56C1L /*Party_MoveToParent*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0xF630F33AL /*_Distance*/) >= 1500) {
			if (changeState(state -> Party_MoveToTarget(blendTime)))
				return;
		}
		if (getVariable(0xD4E69EAEL /*_PatrolOder*/) == 1) {
			if (changeState(state -> Party_MoveAround(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPath, 0, 0, 0, 350, 550, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MoveToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x9E63FB0EL /*Party_MoveToTarget*/);
		if (isTargetLost()) {
			if (changeState(state -> Party_MoveToOwner(blendTime)))
				return;
		}
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		if (getVariable(0xF630F33AL /*_Distance*/) <= 1000) {
			if (changeState(state -> Party_Wait(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_MoveAround(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x6325FBBEL /*Party_MoveAround*/);
		if (isTargetLost()) {
			if (changeState(state -> Party_MoveToOwner(blendTime)))
				return;
		}
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 45) {
			if (changeState(state -> Party_Wait(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -45) {
			if (changeState(state -> Party_Wait(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveAround(800 + Rnd.get(100, 0), ENaviType.ground, () -> {
			setVariable(0xD4E69EAEL /*_PatrolOder*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_PushSheep(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6CDD273EL /*Party_PushSheep*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderPosition, 0, 0, 150, 0, 0, false, ENaviType.ground, () -> {
			getObjects(EAIFindTargetType.Npc, object -> getDistanceToTarget(object) < 200).forEach(consumer -> consumer.getAi().HandlePushsheep(getActor(), null));
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && (getTargetCharacterKey(target) == 20306 || getTargetCharacterKey(target) == 20305)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Logic(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFollowMe(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x6F05E9AFL /*_FollowMe*/, 1);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStop(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyInvited(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Party_MoveToOwner(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleGoPatrol(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xD4E69EAEL /*_PatrolOder*/, 1);
		return EAiHandlerResult.BYPASS;
	}
}
