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
@IAIName("party_leader_ghost")
public class Ai_party_leader_ghost extends CreatureAI {
	public Ai_party_leader_ghost(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x19EB970L /*_WayPointLoopCount*/, getVariable(0xABA96D52L /*AI_WayPointLoopCount*/));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		setVariable(0x692F3C5CL /*_WayPointKey1*/, getVariable(0x3DFFB456L /*AI_WayPointKey1*/));
		setVariable(0x9231A1DL /*_WayPointKey2*/, getVariable(0xCD7084F0L /*AI_WayPointKey2*/));
		setVariable(0x7F4A9F05L /*_WayPointKey3*/, getVariable(0x73DB50B5L /*AI_WayPointKey3*/));
		setVariable(0x3ECDBD02L /*_WayPointKey4*/, getVariable(0x8385DA8EL /*AI_WayPointKey4*/));
		setVariable(0x6013B50FL /*_WayPointKey5*/, getVariable(0xF9FBC5A4L /*AI_WayPointKey5*/));
		setVariable(0x45B1F5C9L /*_WayPointType1*/, getVariable(0xD098A8C0L /*AI_WayPointType1*/));
		setVariable(0x2D64FAF0L /*_WayPointType2*/, getVariable(0xB85CDA53L /*AI_WayPointType2*/));
		setVariable(0xBAA7C9BBL /*_WayPointType3*/, getVariable(0x683BA92CL /*AI_WayPointType3*/));
		setVariable(0xFDCF0CB0L /*_WayPointType4*/, getVariable(0x492931E9L /*AI_WayPointType4*/));
		setVariable(0xD81A9135L /*_WayPointType5*/, getVariable(0x67CAEEAEL /*AI_WayPointType5*/));
		setVariableArray(0x2C4960E5L /*_WaypointValue1*/, new Integer[] { getVariable(0x45B1F5C9L /*_WayPointType1*/), getVariable(0x692F3C5CL /*_WayPointKey1*/) });
		setVariableArray(0x11AADB03L /*_WaypointValue2*/, new Integer[] { getVariable(0x2D64FAF0L /*_WayPointType2*/), getVariable(0x9231A1DL /*_WayPointKey2*/) });
		setVariableArray(0x9425998CL /*_WaypointValue3*/, new Integer[] { getVariable(0xBAA7C9BBL /*_WayPointType3*/), getVariable(0x7F4A9F05L /*_WayPointKey3*/) });
		setVariableArray(0x58ADA3CL /*_WaypointValue4*/, new Integer[] { getVariable(0xFDCF0CB0L /*_WayPointType4*/), getVariable(0x3ECDBD02L /*_WayPointKey4*/) });
		setVariableArray(0x9FFA920EL /*_WaypointValue5*/, new Integer[] { getVariable(0xD81A9135L /*_WayPointType5*/), getVariable(0x6013B50FL /*_WayPointKey5*/) });
		setVariable(0x5B4DA50AL /*_PatyDistance*/, getVariable(0x2D0AAE65L /*AI_PatyDistance*/));
		if (target != null && getDistanceToTarget(target) < getVariable(0x5B4DA50AL /*_PatyDistance*/) && target != null && (getTargetCharacterKey(target) == getVariable(0x3139600L /*Party_CharacterKey1*/) || getTargetCharacterKey(target) == getVariable(0xDCC70F2EL /*Party_CharacterKey2*/) || getTargetCharacterKey(target) == getVariable(0x300021EEL /*Party_CharacterKey3*/) || getTargetCharacterKey(target) == getVariable(0xBDB5CCA8L /*Party_CharacterKey4*/) || getTargetCharacterKey(target) == getVariable(0x942ED749L /*Party_CharacterKey5*/))) {
			createParty(1, 1);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getPartyMembersCount()>= getVariable(0x71A0B28DL /*AI_PatyMemberCount_Min*/) && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if(getCallCount() == 20) {
				if (changeState(state -> Loop_Logic(blendTime)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < getVariable(0x5B4DA50AL /*_PatyDistance*/) && getPartyMembersCount()<= getVariable(0x71A0B28DL /*AI_PatyMemberCount_Min*/) && target != null && (getTargetCharacterKey(target) == getVariable(0x3139600L /*Party_CharacterKey1*/) || getTargetCharacterKey(target) == getVariable(0xDCC70F2EL /*Party_CharacterKey2*/) || getTargetCharacterKey(target) == getVariable(0x300021EEL /*Party_CharacterKey3*/) || getTargetCharacterKey(target) == getVariable(0xBDB5CCA8L /*Party_CharacterKey4*/) || getTargetCharacterKey(target) == getVariable(0x942ED749L /*Party_CharacterKey5*/))) {
				createParty(1, 1);
			}
			scheduleState(state -> Wait(blendTime), 1000);
		});
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (getPartyMembersCount()<= 0) {
			if (changeState(state -> Damage_Die(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_End_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA68A63E5L /*Battle_End_Wait*/);
		if(getCallCount() == 15) {
			if (changeState(state -> Loop_Logic(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_End_Wait(blendTime), 1000));
	}

	protected void Loop_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x72F3DC2EL /*Loop_Logic*/);
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 0 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if (changeState(state -> MovePoint(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 1 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 2) {
			if (changeState(state -> MovePoint2(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 2 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 3) {
			if (changeState(state -> MovePoint3(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 3 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 4) {
			if (changeState(state -> MovePoint4(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 4 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 5) {
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
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 1) {
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
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 2) {
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
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 3) {
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
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 4) {
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

	protected void MovePoint_Fast(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6962EF53L /*MovePoint_Fast*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint2_Fast(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD62B64D0L /*MovePoint2_Fast*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint3_Fast(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE24940F8L /*MovePoint3_Fast*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint4_Fast(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6857E8F6L /*MovePoint4_Fast*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint5_Fast(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA9DF03E5L /*MovePoint5_Fast*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
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
		if (target != null && getDistanceToTarget(target) < getVariable(0x5B4DA50AL /*_PatyDistance*/) && target != null && (getTargetCharacterKey(target) == getVariable(0x3139600L /*Party_CharacterKey1*/) || getTargetCharacterKey(target) == getVariable(0xDCC70F2EL /*Party_CharacterKey2*/) || getTargetCharacterKey(target) == getVariable(0x300021EEL /*Party_CharacterKey3*/) || getTargetCharacterKey(target) == getVariable(0xBDB5CCA8L /*Party_CharacterKey4*/) || getTargetCharacterKey(target) == getVariable(0x942ED749L /*Party_CharacterKey5*/))) {
			createParty(1, 1);
		}
		return EAiHandlerResult.BYPASS;
	}
}
