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
@IAIName("harpy_normal")
public class Ai_harpy_normal extends CreatureAI {
	public Ai_harpy_normal(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1800)) {
			if (changeState(state -> Take_Off(0.4)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Take_Off(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x212C6C11L /*Take_Off*/);
		doAction(2291128889L /*TAKE_OFF*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> FlyingFlap(0.4)))
				return;
			scheduleState(state -> Flying_Battle_Wait(blendTime), 1000);
		});
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Landing(blendTime), 1000)));
	}

	protected void Landing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBE797C29L /*Landing*/);
		doAction(116698182L /*LANDING*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 1000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 200));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 5000));
	}

	protected void FlyingFlap(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x54CA9FC8L /*FlyingFlap*/);
		doAction(1059120586L /*FLYING_FLAP*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_Battle_Wait(blendTime), 1000)));
	}

	protected void Flying_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9526A4B9L /*Flying_Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -135 && getVariable(0xE5BD13F2L /*_Degree*/) >= -179) {
			if (changeState(state -> Flying_BattleTurn_Left_180(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 135 && getVariable(0xE5BD13F2L /*_Degree*/) <= 180) {
			if (changeState(state -> Flying_BattleTurn_Right_180(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -25) {
			if (changeState(state -> Flying_BattleTurn_Left(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 25) {
			if (changeState(state -> Flying_BattleTurn_Right(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Flying_BattleAround(0.4)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_Normal(0.4)))
				return;
		}
		if(Rnd.getChance(20)) {
			if (changeState(state -> Attack_Move(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_AirRush(0.4)))
				return;
		}
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 500));
	}

	protected void Flying_BattleMove_Faster(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x7826CDC8L /*Flying_BattleMove_Faster*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Flying_Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 220) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Move(0.4)))
					return;
			}
		}
		if(Rnd.getChance(7)) {
			if (changeState(state -> Attack_Range(0.4)))
				return;
		}
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_BattleMove_Faster(blendTime), 100)));
	}

	protected void Flying_BattleMove(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x5E29CD02L /*Flying_BattleMove*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Flying_Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 550) {
			if (changeState(state -> Flying_BattleMove_Faster(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 220) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Move(0.4)))
					return;
			}
		}
		doAction(1468881993L /*FLYING_BATTLEMOVE*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_BattleMove(blendTime), 100)));
	}

	protected void Flying_BattleMove_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x73D555B5L /*Flying_BattleMove_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 200) {
			if (changeState(state -> Flying_Battle_Wait(0.4)))
				return;
		}
		if(Rnd.getChance(13)) {
			if (changeState(state -> Attack_Range(0.4)))
				return;
		}
		if(Rnd.getChance(8)) {
			if (changeState(state -> Attack_AirRush(0.4)))
				return;
		}
		doAction(3202605488L /*FLYING_BATTLEMOVE_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_BattleMove_Back(blendTime), 100)));
	}

	protected void Flying_BattleAround(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x43C5D4E6L /*Flying_BattleAround*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Normal(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Move(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Range(0.4)))
				return;
		}
		doAction(948381961L /*FLYING_BATTLEAROUND*/, blendTime, onDoActionEnd -> moveAround(150 + Rnd.get(100, 250), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_Battle_Wait(blendTime), 100)));
	}

	protected void Flying_BattleTurn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9188E3DL /*Flying_BattleTurn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		doAction(2985732940L /*FLYING_BATTLETURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Flying_BattleTurn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD4FA4B26L /*Flying_BattleTurn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		doAction(792653298L /*FLYING_BATTLETURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Flying_BattleTurn_Left_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5B613920L /*Flying_BattleTurn_Left_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		doAction(177101538L /*FLYING_BATTLETURN_180_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Flying_BattleTurn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x383DA6E9L /*Flying_BattleTurn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Flying_BattleMove(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> Flying_BattleMove_Back(0.4)))
				return;
		}
		doAction(3419311486L /*FLYING_BATTLETURN_180_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Attack_Move(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x460B4E3AL /*Attack_Move*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2547671729L /*ATTACK_MOVE*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Move_FlyingAway(blendTime)));
	}

	protected void Attack_Move_FlyingAway(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x4B01B9A0L /*Attack_Move_FlyingAway*/);
		if(getCallCount() == 3) {
			if (changeState(state -> Flying_Battle_Wait(0.4)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3488308574L /*FLYING_BATTLEMOVE_FASTER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 400, 500, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Attack_Move_FlyingAway(blendTime), 1000)));
	}

	protected void Attack_Range(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93D918A4L /*Attack_Range*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3674084903L /*ATTACK_RANGE*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Attack_AirRush(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x94A873ABL /*Attack_AirRush*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(530541300L /*ATTACK_AIR_RUSH*/, blendTime, onDoActionEnd -> changeState(state -> Attack_AirRush_GoTarget(blendTime)));
	}

	protected void Attack_AirRush_GoTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x5995B300L /*Attack_AirRush_GoTarget*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Attack_AirRush_Attack(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Attack_AirRush_Attack(0.3)))
				return;
		}
		doAction(3552497786L /*ATTACK_AIR_RUSH_ING*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Attack_AirRush_GoTarget(blendTime), 1000)));
	}

	protected void Attack_AirRush_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x83545B9AL /*Attack_AirRush_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(856317372L /*ATTACK_AIR_RUSH_END*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Take_Off(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Take_Off(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockBack(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockDown(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Stun(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRigid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Rigid(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Capture(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.2)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
