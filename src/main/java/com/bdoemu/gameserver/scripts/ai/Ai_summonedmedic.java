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
@IAIName("summonedmedic")
public class Ai_summonedmedic extends CreatureAI {
	public Ai_summonedmedic(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x77802473L /*_TargetHP*/, 0);
		setVariable(0x13CF182BL /*_CureCount*/, 0);
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, 0);
		setVariable(0xF06310B7L /*_stance*/, 0);
		doAction(2621686016L /*INITIALIZE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2621686016L /*INITIALIZE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Stance(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9A34695AL /*Stance*/);
		if (getVariable(0xF06310B7L /*_stance*/) == 0) {
			if (changeState(state -> Follow_Wait(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 1) {
			if (changeState(state -> GoToTarget(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Follow_Parent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5B2AAEA5L /*Follow_Parent*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 150, 250, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Follow_Wait(blendTime), 500)));
	}

	protected void Follow_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD1CE82C4L /*Follow_Wait*/);
		setVariable(0xF06310B7L /*_stance*/, 0);
		setVariable(0x77802473L /*_TargetHP*/, getTargetHpRate());
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 300) {
			if (changeState(state -> Follow_Parent(blendTime)))
				return;
		}
		if (getVariable(0x77802473L /*_TargetHP*/) < 95) {
			if (changeState(state -> Cure_Owner(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Follow_Wait(blendTime), 1000));
	}

	protected void GoToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAD5B74B6L /*GoToTarget*/);
		setVariable(0xF06310B7L /*_stance*/, 1);
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderDestination, 0, 0, 0, 150, 250, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> BattleWait(blendTime), 500)));
	}

	protected void ChaseTarget_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xBD53C7CAL /*ChaseTarget_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> BattleWait(blendTime)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> BattleWait(blendTime), 500)));
	}

	protected void ChaseTarget_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xECD82809L /*ChaseTarget_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> BattleWait(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> ChaseTarget_Run(blendTime)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> BattleWait(blendTime), 500)));
	}

	protected void BattleWait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x18AB093BL /*BattleWait*/);
		setVariable(0x77802473L /*_TargetHP*/, getTargetHpRate());
		if(getCallCount() == 10) {
			if (changeState(state -> Follow_Wait(blendTime)))
				return;
		}
		if (getVariable(0x13CF182BL /*_CureCount*/) >= 25) {
			if (changeState(state -> Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> ChaseTarget_Walk(blendTime)))
				return;
		}
		if (getVariable(0x77802473L /*_TargetHP*/) < 95) {
			if (changeState(state -> Cure(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> BattleWait(blendTime), 1000));
	}

	protected void Cure(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x14F0A9C9L /*Cure*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		setVariable(0x13CF182BL /*_CureCount*/, getVariable(0x13CF182BL /*_CureCount*/) + 1);
		doAction(1123990951L /*CURE*/, blendTime, onDoActionEnd -> changeState(state -> BattleWait(blendTime)));
	}

	protected void Cure_Owner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x94118347L /*Cure_Owner*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(1123990951L /*CURE*/, blendTime, onDoActionEnd -> changeState(state -> BattleWait(blendTime)));
	}

	protected void Resurrection(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEF0B54C9L /*Resurrection*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		setVariable(0x13CF182BL /*_CureCount*/, getVariable(0x13CF182BL /*_CureCount*/) + 5);
		doAction(1627643347L /*RESURRECTION*/, blendTime, onDoActionEnd -> changeState(state -> BattleWait(blendTime)));
	}

	protected void Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0x10DF0E84L /*Return*/);
		setVariable(0x13CF182BL /*_CureCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Stance(blendTime), 1000)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Damage_StandUp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3891BF54L /*Damage_StandUp*/);
		doAction(927041621L /*DAMAGE_STANDUP*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 2000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_StandUp(blendTime), 2000));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 5000));
	}

	@Override
	public EAiHandlerResult HandlerTargetMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (changeState(state -> GoToTarget(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTargetFastMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (changeState(state -> GoToTarget(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTargetAttackMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (changeState(state -> GoToTarget(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTargetAttackFastMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (changeState(state -> GoToTarget(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockBack(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockDown(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvade(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStuned(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Stun(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Capture(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyInvited2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
