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
@IAIName("eavesdrop_quest")
public class Ai_eavesdrop_quest extends CreatureAI {
	public Ai_eavesdrop_quest(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x9477DA4DL /*_Eavesdrop_Count*/, 0);
		setVariable(0x45129E6AL /*_SpeakInterval1*/, getVariable(0xBB1D48D7L /*AI_SpeakInterval1*/));
		setVariable(0xCE86896BL /*_SpeakInterval2*/, getVariable(0x67DA74CEL /*AI_SpeakInterval2*/));
		setVariable(0x7A581E18L /*_SpeakInterval3*/, getVariable(0x9457D834L /*AI_SpeakInterval3*/));
		setVariable(0x9B52F7B4L /*_SpeakInterval4*/, getVariable(0xDF9861DL /*AI_SpeakInterval4*/));
		setVariable(0x2EEC97E8L /*_SpeakInterval5*/, getVariable(0xEAEF9ECAL /*AI_SpeakInterval5*/));
		setVariable(0xBD7CA4ECL /*_SpeakInterval6*/, getVariable(0xC192969CL /*AI_SpeakInterval6*/));
		setVariable(0xA3C784DBL /*GoHomeCount*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x1EA1B513L /*_isFindPathHomeCompleted*/, 0);
		setVariable(0x6A229FDCL /*_FailFindPathHomeCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x9477DA4DL /*_Eavesdrop_Count*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2500 + Rnd.get(-500,500)));
	}

	protected void Eavesdrop1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x21855E0DL /*Eavesdrop1*/);
		setVariable(0x9477DA4DL /*_Eavesdrop_Count*/, 1);
		doAction(1613481347L /*EAVESDROP1*/, blendTime, onDoActionEnd -> scheduleState(state -> Eavesdrop_Logic(blendTime), getVariable(0xBB1D48D7L /*AI_SpeakInterval1*/)));
	}

	protected void Eavesdrop2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x21BFD68FL /*Eavesdrop2*/);
		setVariable(0x9477DA4DL /*_Eavesdrop_Count*/, 2);
		doAction(1041996631L /*EAVESDROP2*/, blendTime, onDoActionEnd -> scheduleState(state -> Eavesdrop_Logic(blendTime), getVariable(0x67DA74CEL /*AI_SpeakInterval2*/)));
	}

	protected void Eavesdrop3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF6584B04L /*Eavesdrop3*/);
		setVariable(0x9477DA4DL /*_Eavesdrop_Count*/, 3);
		doAction(4025844125L /*EAVESDROP3*/, blendTime, onDoActionEnd -> scheduleState(state -> Eavesdrop_Logic(blendTime), getVariable(0x9457D834L /*AI_SpeakInterval3*/)));
	}

	protected void Eavesdrop4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9971F3F5L /*Eavesdrop4*/);
		setVariable(0x9477DA4DL /*_Eavesdrop_Count*/, 4);
		doAction(983566350L /*EAVESDROP4*/, blendTime, onDoActionEnd -> scheduleState(state -> Eavesdrop_Logic(blendTime), getVariable(0xDF9861DL /*AI_SpeakInterval4*/)));
	}

	protected void Eavesdrop5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x75F1A31AL /*Eavesdrop5*/);
		setVariable(0x9477DA4DL /*_Eavesdrop_Count*/, 5);
		doAction(2172815773L /*EAVESDROP5*/, blendTime, onDoActionEnd -> scheduleState(state -> Eavesdrop_Logic(blendTime), getVariable(0xEAEF9ECAL /*AI_SpeakInterval5*/)));
	}

	protected void Eavesdrop6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAB144623L /*Eavesdrop6*/);
		setVariable(0x9477DA4DL /*_Eavesdrop_Count*/, 6);
		doAction(1407943131L /*EAVESDROP6*/, blendTime, onDoActionEnd -> scheduleState(state -> Eavesdrop_Logic(blendTime), getVariable(0xC192969CL /*AI_SpeakInterval6*/)));
	}

	protected void Eavesdrop_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD542D3AL /*Eavesdrop_Logic*/);
		if (getVariable(0x9477DA4DL /*_Eavesdrop_Count*/) == 1 && getVariable(0xCE86896BL /*_SpeakInterval2*/) != 0) {
			if (changeState(state -> Eavesdrop2(0.3)))
				return;
		}
		if (getVariable(0x9477DA4DL /*_Eavesdrop_Count*/) == 2 && getVariable(0x7A581E18L /*_SpeakInterval3*/) != 0) {
			if (changeState(state -> Eavesdrop3(0.3)))
				return;
		}
		if (getVariable(0x9477DA4DL /*_Eavesdrop_Count*/) == 3 && getVariable(0x9B52F7B4L /*_SpeakInterval4*/) != 0) {
			if (changeState(state -> Eavesdrop4(0.3)))
				return;
		}
		if (getVariable(0x9477DA4DL /*_Eavesdrop_Count*/) == 4 && getVariable(0x2EEC97E8L /*_SpeakInterval5*/) != 0) {
			if (changeState(state -> Eavesdrop5(0.3)))
				return;
		}
		if (getVariable(0x9477DA4DL /*_Eavesdrop_Count*/) == 5 && getVariable(0xBD7CA4ECL /*_SpeakInterval6*/) != 0) {
			if (changeState(state -> Eavesdrop6(0.3)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Handler_Eavesdrop(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x9477DA4DL /*_Eavesdrop_Count*/) == 0) {
			if (changeState(state -> Eavesdrop1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	protected void GoBackPosition(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5D28D0FL /*GoBackPosition*/);
		if (getVariable(0x464A6F5AL /*AI_Npc_Home*/) == 1) {
			if (changeState(state -> GoBack_Home_Position(blendTime)))
				return;
		}
		if (getVariable(0x464A6F5AL /*AI_Npc_Home*/) == 2) {
			if (changeState(state -> GoBack_Waypoint_Route(blendTime)))
				return;
		}
		if (getVariable(0x464A6F5AL /*AI_Npc_Home*/) != 1 && getVariable(0x464A6F5AL /*AI_Npc_Home*/) != 2) {
			if (changeState(state -> Day_Wait(blendTime)))
				return;
		}
		doAction(1569894142L /*WAIT_1*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBackPosition(blendTime), 20000 + Rnd.get(-5000,5000)));
	}

	protected void GoBack_Home_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8EDD2BBL /*GoBack_Home_Position*/);
		doAction(105455338L /*GOBACK_1*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Turn_Orign(blendTime), 1000)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Orign(blendTime), 1500));
	}

	protected void GoBack_Waypoint_Route(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2E3718DDL /*GoBack_Waypoint_Route*/);
		doAction(1339848291L /*GOBACK_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc_route", "waypoint_route", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Turn_Orign(blendTime), 1000)));
	}

	protected void Turn_Orign(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xB69F39BAL /*Turn_Orign*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Day_Wait(blendTime), 100)));
	}

	protected void Day_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA9E0A4C0L /*Day_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 10000));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void GoHomePosition(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBB6B4D51L /*GoHomePosition*/);
		if (getVariable(0x464A6F5AL /*AI_Npc_Home*/) == 1) {
			if (changeState(state -> GoToHome_Position(blendTime)))
				return;
		}
		if (getVariable(0x464A6F5AL /*AI_Npc_Home*/) == 2) {
			if (changeState(state -> GoToHome_Waypoint_Route(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> GoHomePosition(blendTime), 20000 + Rnd.get(-5000,5000)));
	}

	protected void GoToHome_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x96F3B465L /*GoToHome_Position*/);
		if (getVariable(0xA3C784DBL /*GoHomeCount*/) == 0) {
			getObjects(EAIFindTargetType.Npc, object -> getDistanceToTarget(object) < 80).forEach(consumer -> consumer.getAi().HandleGoHome(getActor(), null));
		}
		doAction(931603276L /*GOHOME_1*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.House, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x1EA1B513L /*_isFindPathHomeCompleted*/, isFindPathCompleted());
			if (getVariable(0x1EA1B513L /*_isFindPathHomeCompleted*/) == 0 && getVariable(0x6A229FDCL /*_FailFindPathHomeCount*/) >= 3) {
				if (changeState(state -> WaitHome(0.3)))
					return true;
			}
			if (getVariable(0x1EA1B513L /*_isFindPathHomeCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Home(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> WaitHome(blendTime), 100)));
	}

	protected void GoToHome_Waypoint_Route(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x448B0BD3L /*GoToHome_Waypoint_Route*/);
		if (getVariable(0xA3C784DBL /*GoHomeCount*/) == 0) {
			getObjects(EAIFindTargetType.Npc, object -> getDistanceToTarget(object) < 80).forEach(consumer -> consumer.getAi().HandleGoHome(getActor(), null));
		}
		doAction(2731866410L /*GOHOME_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc_route", "waypoint_route", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> WaitHome(blendTime), 100)));
	}

	protected void WaitHome(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE65762BFL /*WaitHome*/);
		doAction(408565085L /*HOME*/, blendTime, onDoActionEnd -> scheduleState(state -> HomeDead(blendTime), 100));
	}

	protected void HomeDead(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x6C6077C1L /*HomeDead*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> HomeDead(blendTime), 100));
	}

	protected void Fright(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x654BC56AL /*Fright*/);
		doAction(32379601L /*FRIGHT*/, blendTime, onDoActionEnd -> scheduleState(state -> Day_Wait(blendTime), 1000));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> GoBackPosition(blendTime)));
	}

	protected void FailFindPath_Home(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8FBEE09EL /*FailFindPath_Home*/);
		setVariable(0x6A229FDCL /*_FailFindPathHomeCount*/, getVariable(0x6A229FDCL /*_FailFindPathHomeCount*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> WaitHome(blendTime)));
	}

	protected void MeshOffState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE73CD5DEL /*MeshOffState*/);
		doAction(1926787974L /*HIDEMESH*/, blendTime, onDoActionEnd -> scheduleState(state -> DeadNPC(blendTime), 100));
	}

	protected void DeadNPC(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xA215D08AL /*DeadNPC*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> DeadNPC(blendTime), 100));
	}

	@Override
	public EAiHandlerResult HandleAtSpawnStartTime(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x464A6F5AL /*AI_Npc_Home*/) != 0) {
			if (changeState(state -> GoBackPosition(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleAtSpawnEndTime(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x464A6F5AL /*AI_Npc_Home*/) != 0) {
			if (changeState(state -> GoHomePosition(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleAtNight(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x464A6F5AL /*AI_Npc_Home*/) != 0) {
			if (changeState(state -> GoHomePosition(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleGoHome(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xA3C784DBL /*GoHomeCount*/, 1);
		if (getVariable(0x464A6F5AL /*AI_Npc_Home*/) == 1) {
			if (changeState(state -> GoToHome_Position(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x464A6F5AL /*AI_Npc_Home*/) == 2) {
			if (changeState(state -> GoToHome_Waypoint_Route(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFailSteal(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender);
		if (getVariable(0xA3C784DBL /*GoHomeCount*/) == 0) {
			getObjects(EAIFindTargetType.Npc, object -> getDistanceToTarget(object) < 2000).forEach(consumer -> consumer.getAi().HandleFailSteal(getActor(), null));
		}
		if (changeState(state -> Fright(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
