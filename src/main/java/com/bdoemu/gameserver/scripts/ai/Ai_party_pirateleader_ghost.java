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
@IAIName("party_pirateleader_ghost")
public class Ai_party_pirateleader_ghost extends CreatureAI {
	public Ai_party_pirateleader_ghost(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		setVariableArray(0x2C4960E5L /*_WaypointValue1*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x3DFFB456L /*AI_WayPointKey1*/) });
		setVariableArray(0x11AADB03L /*_WaypointValue2*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xCD7084F0L /*AI_WayPointKey2*/) });
		setVariableArray(0x9425998CL /*_WaypointValue3*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x73DB50B5L /*AI_WayPointKey3*/) });
		setVariableArray(0x58ADA3CL /*_WaypointValue4*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x8385DA8EL /*AI_WayPointKey4*/) });
		setVariableArray(0x9FFA920EL /*_WaypointValue5*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xF9FBC5A4L /*AI_WayPointKey5*/) });
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait(blendTime), 1000));
	}

	protected void Summon_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE4A6F0FL /*Summon_Wait*/);
		doAction(3602854557L /*SUMMON_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Loop_Logic(blendTime)));
	}

	protected void Order_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x89C2F090L /*Order_Wait*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleBattleMode(getActor(), null));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getPartyMembersCount()<= 1) {
			if (changeState(state -> Summon_Wait(blendTime)))
				return;
		}
		if (getPartyMembersCount()> 1 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 1) {
			if(getCallCount() == 2) {
				if (changeState(state -> Loop_Logic(blendTime)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Loop_Logic(blendTime), 500));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (getPartyMembersCount()== 0) {
			if (changeState(state -> Loop_Logic(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_End_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA68A63E5L /*Battle_End_Wait*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Loop_Logic(blendTime)))
				return;
		}
		if (getPartyMembersCount()== 0) {
			if (changeState(state -> Loop_Logic(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_End_Wait(blendTime), 1000));
	}

	protected void Loop_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x72F3DC2EL /*Loop_Logic*/);
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 0 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 1) {
			if (changeState(state -> MovePoint(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 1 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 2) {
			if (changeState(state -> MovePoint2(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 2 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 3) {
			if (changeState(state -> MovePoint3(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 3 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 4) {
			if (changeState(state -> MovePoint4(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 4 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 5) {
			if (changeState(state -> MovePoint5(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void MovePoint(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE03B724EL /*MovePoint*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 1) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE8D86D5BL /*MovePoint2*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 2) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBB5DA63EL /*MovePoint3*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 3) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC4B7E7FAL /*MovePoint4*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 4) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6F460A97L /*MovePoint5*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBattleMode(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x71F28994L /*Battle_Wait*/ && getState() != 0x89C2F090L /*Order_Wait*/) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Order_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeTeamDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x71F28994L /*Battle_Wait*/) {
			if (changeState(state -> Battle_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBattleEnd(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Battle_End_Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyCheck(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
