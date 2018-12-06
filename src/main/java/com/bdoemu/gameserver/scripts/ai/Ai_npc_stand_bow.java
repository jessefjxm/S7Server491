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
@IAIName("npc_stand_bow")
public class Ai_npc_stand_bow extends CreatureAI {
	public Ai_npc_stand_bow(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xA3C784DBL /*GoHomeCount*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x1EA1B513L /*_isFindPathHomeCompleted*/, 0);
		setVariable(0x6A229FDCL /*_FailFindPathHomeCount*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0x9225781EL /*_GreetingTime*/, 0);
		setVariable(0x78843D57L /*_GreetingElapsed*/, 0);
		setVariable(0x6C8A49C5L /*_GreetingCount*/, 0);
		if (getVariable(0x12C225A5L /*AI_StartRandom*/) == 1) {
			if (changeState(state -> Start_Random(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> GoBackPosition(blendTime)));
	}

	protected void Start_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDD11C2B4L /*Start_Random*/);
		doAction(2720551771L /*START_RANDOM*/, blendTime, onDoActionEnd -> scheduleState(state -> Day_Wait(blendTime), 5000 + Rnd.get(-500,500)));
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
		setVariable(0x78843D57L /*_GreetingElapsed*/, getTime() - getVariable(0x9225781EL /*_GreetingTime*/));
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
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
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Day_Wait(blendTime), 10000));
	}

	protected void Greeting(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x57CB1F04L /*Greeting*/);
		doAction(1029767713L /*BOW*/, blendTime, onDoActionEnd -> {
			setVariable(0x9225781EL /*_GreetingTime*/, getTime());
			setVariable(0x6C8A49C5L /*_GreetingCount*/, getVariable(0x6C8A49C5L /*_GreetingCount*/) + 1);
			scheduleState(state -> Day_Wait(blendTime), 1000);
		});
	}

	protected void Greeting_Second(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFDB968D4L /*Greeting_Second*/);
		doAction(1029767713L /*BOW*/, blendTime, onDoActionEnd -> {
			setVariable(0x6C8A49C5L /*_GreetingCount*/, getVariable(0x6C8A49C5L /*_GreetingCount*/) + 1);
			setVariable(0x9225781EL /*_GreetingTime*/, getTime());
			scheduleState(state -> Day_Wait(blendTime), 1000);
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
		}, onExit -> scheduleState(state -> Day_Wait(blendTime), 3000)));
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
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
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
			// Missing AI Handler: HandleCallGuard : send_command()
		}
		if (changeState(state -> Fright(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
