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
@IAIName("bigghostship_c_normal")
public class Ai_bigghostship_c_normal extends CreatureAI {
	public Ai_bigghostship_c_normal(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x8AF0B5C9L /*_LostCount*/, 0);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Wait(blendTime), 1000));
	}

	protected void Teleport_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCAF8E69L /*Teleport_Wait*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Handler_Logic(0.4)))
				return;
		}
		doAction(4070086861L /*WAIT_TELEPORT*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Wait(blendTime), 1000));
	}

	protected void Handler_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x63EE0130L /*Handler_Logic*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().Mami(getActor(), null));
		doAction(4070086861L /*WAIT_TELEPORT*/, blendTime, onDoActionEnd -> scheduleState(state -> Handler_Logic(blendTime), 1000));
	}

	protected void Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x88C3A72EL /*Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(4271552272L /*WAIT_TELEPORT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Logic(blendTime), 5000));
	}

	protected void Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA5BDA01CL /*Teleport_Logic*/);
		changeState(state -> Detect_Target(blendTime));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 500 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 500 && getTargetHp(object) > 0 && isTargetVehicle(object))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(2531377426L /*SEARCH_ENEMY2*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Detect_Target2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x765A52E7L /*Detect_Target2*/);
		setVariable(0x8AF0B5C9L /*_LostCount*/, getVariable(0x8AF0B5C9L /*_LostCount*/) + 1);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 500 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 500 && getTargetHp(object) > 0 && isTargetVehicle(object))) {
			if (changeState(state -> Attack_2(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(1142352784L /*BATTLE_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 500)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Delete_Die(0.4)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Detect_Target2(0.3)))
				return;
		}
		if (getVariable(0x8AF0B5C9L /*_LostCount*/) >= 10) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Delete_Die(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 400) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_4(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_5(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if (changeState(state -> Attack_1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 500) {
			if (changeState(state -> Move_Chaser(1.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Detect_Target2(1.3)))
				return;
		}
		doAction(1142352784L /*BATTLE_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Delete_Die(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 250) {
			if (changeState(state -> Battle_Wait(1.3)))
				return;
		}
		doAction(1029977119L /*MOVE_CHASER2*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void Attack_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4EC3F138L /*Attack_1*/);
		doAction(434909106L /*AROUND_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x84016AECL /*Attack_2*/);
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7177AB7EL /*Attack_3*/);
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBA01DB80L /*Attack_4*/);
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x73FD6BE7L /*Attack_5*/);
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x355E057L /*Attack_End_Logic*/);
		setVariable(0x8AF0B5C9L /*_LostCount*/, 0);
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Lost_Target(blendTime), 1000)));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 800 && getTargetHp(object) > 0 && isTargetVehicle(object))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Delete_Die(0.4)))
				return;
		}
		doAction(1142352784L /*BATTLE_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Lost_Target(blendTime), 1000));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0x8AF0B5C9L /*_LostCount*/, getVariable(0x8AF0B5C9L /*_LostCount*/) + 1);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		changeState(state -> FailFindPathToTarget_Logic(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 500 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(1142352784L /*BATTLE_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void FailFindPathToTarget_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3C75D394L /*FailFindPathToTarget_Logic*/);
		if(getCallCount() == 20) {
			if (changeState(state -> Delete_Die(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 500 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		scheduleState(state -> FailFindPathToTarget_Logic(blendTime), 500);
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
		if ((getState() == 0xCF465EDCL /*Search_Enemy*/ || getState() == 0x866C7489L /*Wait*/)) {
			if (changeState(state -> Battle_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getObjects(EAIFindTargetType.Parent, object -> getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 8000).forEach(consumer -> consumer.getAi().Mamibye(getActor(), null));
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getPartyMembersCount()<= 0) {
			if (changeState(state -> Delete_Die(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult YouDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Delete_Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Send_Target(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (changeState(state -> Teleport(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
