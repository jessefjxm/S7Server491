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
@IAIName("npc_labor_delphe_to_outpost")
public class Ai_npc_labor_delphe_to_outpost extends CreatureAI {
	public Ai_npc_labor_delphe_to_outpost(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> FirstGo(blendTime)));
	}

	protected void FirstGo(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBE33C101L /*FirstGo*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "DelpheKnight_Supply_Waypoint_001", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Load_Stuff(blendTime), 1000)));
	}

	protected void Load_Stuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA55244FEL /*Load_Stuff*/);
		doAction(2453627260L /*LOAD_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Go(blendTime), 1000));
	}

	protected void Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCDE20F0FL /*Go*/);
		doAction(629689558L /*WALK_WITH_STUFF*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "DelpheKnight_Supply_Waypoint_025", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_Go(blendTime), 1000)));
	}

	protected void Rest_In_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA7151865L /*Rest_In_Go*/);
		doAction(720668828L /*REST_WITH_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Go1(blendTime), 1000));
	}

	protected void Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD9082EF7L /*Go1*/);
		doAction(629689558L /*WALK_WITH_STUFF*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "DelpheKnight_Supply_Waypoint_047", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Clear_Stuff(blendTime), 1000)));
	}

	protected void Clear_Stuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE25D5C49L /*Clear_Stuff*/);
		doAction(2849376549L /*CLEAR_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack(blendTime), 1000));
	}

	protected void GoBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDB128473L /*GoBack*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "DelpheKnight_Supply_Waypoint_077", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack(blendTime), 1000)));
	}

	protected void Rest_In_GoBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC5911E5L /*Rest_In_GoBack*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> GoBack1(blendTime), 1000));
	}

	protected void GoBack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5F2560DCL /*GoBack1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "DelpheKnight_Supply_Waypoint_094", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> MeshOffState(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Rest_In_GoBack1(blendTime), 1000)));
	}

	protected void Rest_In_GoBack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1FC0D3C2L /*Rest_In_GoBack1*/);
		doAction(3172275756L /*REST*/, blendTime, onDoActionEnd -> scheduleState(state -> FirstGo(blendTime), 1000));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> FirstGo(blendTime)));
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
}
