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
@IAIName("goblin_escape_m")
public class Ai_goblin_escape_m extends CreatureAI {
	public Ai_goblin_escape_m(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x95AC12F8L /*_triggerkey*/, getVariable(0x60D03638L /*AI_triggerkey*/));
		setVariable(0x4D347D06L /*_isUnderWater*/, 0);
		setVariable(0x21C76BFFL /*_isWater*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x71135324L /*_IsHaveTarget*/, 0);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x71135324L /*_IsHaveTarget*/, 1);
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9F40743BL /*AI_ES_MinDistance*/))) {
			if (changeState(state -> Escape(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Walk_Random(0.3)))
				return;
		}
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Walk_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9FE5C5L /*Walk_Random*/);
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9F40743BL /*AI_ES_MinDistance*/))) {
			if (changeState(state -> Escape(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 200, true, ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x71135324L /*_IsHaveTarget*/, 0);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3 && getVariable(0x71135324L /*_IsHaveTarget*/) == 0) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3 && getVariable(0x71135324L /*_IsHaveTarget*/) == 1) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Stop(blendTime), 2000));
	}

	protected void FailFindPath_ToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC53FD679L /*FailFindPath_ToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Stop(blendTime), 2000));
	}

	protected void Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xDCE8DF7DL /*Escape*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x9F40743BL /*AI_ES_MinDistance*/)) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> escape(getVariable(0x8FA1E7B6L /*AI_ES_MaxDistance*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Escape(blendTime), 1000)));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		clearAggro(true);
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x84794EB4L /*Wait2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		setVariable(0x71135324L /*_IsHaveTarget*/, 1);
		setVariable(0x4D347D06L /*_isUnderWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("under_water_ground") ? 1 : 0));
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(getCallCount() == 60) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 1000) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait2(blendTime)));
	}

	protected void Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x29A56175L /*Stop*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		setVariable(0x71135324L /*_IsHaveTarget*/, 1);
		setVariable(0x4D347D06L /*_isUnderWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("under_water_ground") ? 1 : 0));
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(getCallCount() == 60) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 900000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Stop(blendTime)));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		setVariable(0x4D347D06L /*_isUnderWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("under_water_ground") ? 1 : 0));
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(getCallCount() == 60) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Stop(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 900000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(468841119L /*MOVE_WALK_1*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void HighSpeed_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x4C327D19L /*HighSpeed_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		setVariable(0x4D347D06L /*_isUnderWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("under_water_ground") ? 1 : 0));
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(getCallCount() == 60) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Stop(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 900000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(3286102901L /*MOVE_CHASER_1*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> HighSpeed_Chaser(blendTime), 100)));
	}

	protected void End_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xB27633BDL /*End_Die*/);
		doAction(1426790432L /*END_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> End_Die(blendTime), 3000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x9FE5C5L /*Walk_Random*/) && target != null && getTargetHp(target) > 0) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Escape(0.3)))
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
	public EAiHandlerResult HandlerTalkToDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (changeState(state -> Wait2(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait2(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
