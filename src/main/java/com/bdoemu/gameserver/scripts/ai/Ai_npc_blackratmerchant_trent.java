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
@IAIName("npc_blackratmerchant_trent")
public class Ai_npc_blackratmerchant_trent extends CreatureAI {
	public Ai_npc_blackratmerchant_trent(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x1EA1B513L /*_isFindPathHomeCompleted*/, 0);
		setVariable(0x6A229FDCL /*_FailFindPathHomeCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> GoBackPosition(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> GoToHomePosition(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 10000));
	}

	protected void WaitHome(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE65762BFL /*WaitHome*/);
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> GoBackPosition(blendTime)))
				return;
		}
		doAction(408565085L /*HOME*/, blendTime, onDoActionEnd -> scheduleState(state -> WaitHome(blendTime), 10000));
	}

	protected void GoToHomePosition(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6800E6DEL /*GoToHomePosition*/);
		doAction(2731866410L /*GOHOME_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "BlackRatMerchant_42163_7", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> WaitHome(blendTime), 5000)));
	}

	protected void GoBackPosition(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5D28D0FL /*GoBackPosition*/);
		doAction(1339848291L /*GOBACK_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "BlackRatMerchant_42163_1", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> Turn_Orign(blendTime), 5000)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Orign(blendTime), 1500));
	}

	protected void Turn_Orign(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xB69F39BAL /*Turn_Orign*/);
		doAction(2738613969L /*STOP2*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 30000)));
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
}
