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
@IAIName("holycrystal")
public class Ai_holycrystal extends CreatureAI {
	public Ai_holycrystal(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x762AB710L /*_UnSummonTime*/, getVariable(0xCC757928L /*AI_UnSummonTime*/));
		setVariable(0x9326AD79L /*_SummonStartTime*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, 0);
		setVariable(0x51EDA18AL /*_SummonEndTime*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x9326AD79L /*_SummonStartTime*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack(blendTime), 500));
	}

	protected void Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x34E5AE02L /*Summon*/);
		doAction(3635031213L /*SUMMON*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 300));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > getVariable(0xCC757928L /*AI_UnSummonTime*/)) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 150) {
			if (changeState(state -> Chase_Owner(0.05)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1000) {
			if (changeState(state -> Chase_Owner_Run(0.05)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1500) {
			if (changeState(state -> Teleport_Ready(0.1)))
				return;
		}
		scheduleState(state -> Attack(blendTime), 300);
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > getVariable(0xCC757928L /*AI_UnSummonTime*/)) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 150) {
			if (changeState(state -> Chase_Owner(0.05)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1000) {
			if (changeState(state -> Chase_Owner_Run(0.05)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1500) {
			if (changeState(state -> Teleport_Ready(0.1)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 300));
	}

	protected void Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2417AD84L /*Attack*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > getVariable(0xCC757928L /*AI_UnSummonTime*/)) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 150) {
			if (changeState(state -> Chase_Owner(0.05)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1000) {
			if (changeState(state -> Chase_Owner_Run(0.05)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1500) {
			if (changeState(state -> Teleport_Ready(0.1)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Attack(blendTime)));
	}

	protected void Temp_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1AF4FCDDL /*Temp_Wait*/);
		doAction(3144319281L /*TEMP_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> UnSummon(blendTime), 500));
	}

	protected void UnSummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xCC02DCCFL /*UnSummon*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> UnSummon(blendTime), 500));
	}

	protected void Chase_Owner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEB681DFAL /*Chase_Owner*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > getVariable(0xCC757928L /*AI_UnSummonTime*/)) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 150) {
			if (changeState(state -> Chase_Owner(0.05)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1000) {
			if (changeState(state -> Chase_Owner_Run(0.05)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1500) {
			if (changeState(state -> Teleport_Ready(0.1)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.1)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Attack(blendTime), 100)));
	}

	protected void Chase_Owner_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB3DD8609L /*Chase_Owner_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > getVariable(0xCC757928L /*AI_UnSummonTime*/)) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 150) {
			if (changeState(state -> Chase_Owner(0.05)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1000) {
			if (changeState(state -> Chase_Owner_Run(0.05)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1500) {
			if (changeState(state -> Teleport_Ready(0.1)))
				return;
		}
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Attack(blendTime), 100)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 2) {
			if (changeState(state -> FailFindPath(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 100, 300);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Teleport_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1C9A6846L /*Teleport_Ready*/);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 100, 300);
		doAction(1545875970L /*ORDER_TELEPORT*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Owner(blendTime), 500));
	}

	protected void Teleport_Owner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2EC38528L /*Teleport_Owner*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon(blendTime), 500));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnRifleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> UnSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
