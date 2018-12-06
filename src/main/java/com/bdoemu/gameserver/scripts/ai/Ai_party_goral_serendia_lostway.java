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
@IAIName("party_goral_serendia_lostway")
public class Ai_party_goral_serendia_lostway extends CreatureAI {
	public Ai_party_goral_serendia_lostway(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x6F05E9AFL /*_FollowMe*/, 0);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		setVariable(0xBB86C5A3L /*_BattleMode*/, 0);
		setVariable(0x40CC9D51L /*_LostWayDice*/, 0);
		setVariable(0xA425A34EL /*_LostWayEnd*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Riding_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5C23B8A0L /*Riding_Wait*/);
		changeState(state -> TerminateState(blendTime));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		if (!isRiderExist()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		// Prevent recursion;
	}

	protected void RidingOff_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9784B374L /*RidingOff_Wait*/);
		if(getCallCount() == 13) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(1569894142L /*WAIT_1*/, blendTime, onDoActionEnd -> scheduleState(state -> RidingOff_Wait(blendTime), 3000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		setVariable(0x40CC9D51L /*_LostWayDice*/, getRandom(8));
		changeState(state -> Logic_1(blendTime));
	}

	protected void Logic_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x13E1A398L /*Logic_1*/);
		if (getVariable(0x40CC9D51L /*_LostWayDice*/) <= 1) {
			if (changeState(state -> Lost_MovePoint_Start_1(blendTime)))
				return;
		}
		if (getVariable(0x40CC9D51L /*_LostWayDice*/) <= 2) {
			if (changeState(state -> Lost_MovePoint_Start_2(blendTime)))
				return;
		}
		if (getVariable(0x40CC9D51L /*_LostWayDice*/) <= 3) {
			if (changeState(state -> Lost_MovePoint_Start_3(blendTime)))
				return;
		}
		if (getVariable(0x40CC9D51L /*_LostWayDice*/) <= 4) {
			if (changeState(state -> Lost_MovePoint_Start_4(blendTime)))
				return;
		}
		if (getVariable(0x40CC9D51L /*_LostWayDice*/) <= 5) {
			if (changeState(state -> Lost_MovePoint_Start_5(blendTime)))
				return;
		}
		if (getVariable(0x40CC9D51L /*_LostWayDice*/) <= 6) {
			if (changeState(state -> Lost_MovePoint_Start_6(blendTime)))
				return;
		}
		if (getVariable(0x40CC9D51L /*_LostWayDice*/) <= 7) {
			if (changeState(state -> Lost_MovePoint_Start_7(blendTime)))
				return;
		}
		if (getVariable(0x40CC9D51L /*_LostWayDice*/) <= 8) {
			if (changeState(state -> Lost_MovePoint_Start_8(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		clearAggro(true);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xBB86C5A3L /*_BattleMode*/, 0);
		if (!isRiderExist() && checkTrigger(202)) {
			if (changeState(state -> Suicide_Die(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 700)) {
			if (changeState(state -> Escape(blendTime)))
				return;
		}
		doAction(3315571939L /*WAIT_LOSTSHEEP*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2500 + Rnd.get(-500,500)));
	}

	protected void WalkRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD5712181L /*WalkRandom*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, getVariable(0x22A52166L /*_RandomMoveCount*/) + 1);
		doAction(2834477951L /*WALK_LOSTSHEEP*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 300, 800, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 4000)));
	}

	protected void SearchEnemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x43FC3DF7L /*SearchEnemy*/);
		setVariable(0xBB86C5A3L /*_BattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> BattleReady(blendTime), 1000));
	}

	protected void BattleReady(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x83D74061L /*BattleReady*/);
		if (target != null && getDistanceToTarget(target) < 700) {
			if (changeState(state -> Escape(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 700)) {
			if (changeState(state -> Escape(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 2300) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> BattleReady(blendTime), 500 + Rnd.get(-300,300)));
	}

	protected void Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xDCE8DF7DL /*Escape*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1500) {
			if (changeState(state -> BattleReady(blendTime)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> escape(2500, () -> {
			return false;
		}, onExit -> scheduleState(state -> Escape(blendTime), 1000)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> ChaseEnemy(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal(blendTime)))
					return;
			}
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void ChaseEnemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x75E647ACL /*ChaseEnemy*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal(blendTime)))
					return;
			}
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> ChaseEnemy(blendTime), 100)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Lost_MovePoint_Start_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3B06ED21L /*Lost_MovePoint_Start_1*/);
		if(Rnd.getChance(8)) {
			if (changeState(state -> WalkRandom(blendTime)))
				return;
		}
		doAction(2834477951L /*WALK_LOSTSHEEP*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Lost_Sheep_1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Lost_MovePoint_End(blendTime), 1000)));
	}

	protected void Lost_MovePoint_Start_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE20C54CCL /*Lost_MovePoint_Start_2*/);
		if(Rnd.getChance(8)) {
			if (changeState(state -> WalkRandom(blendTime)))
				return;
		}
		doAction(2834477951L /*WALK_LOSTSHEEP*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Lost_Sheep_2", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Lost_MovePoint_End(blendTime), 1000)));
	}

	protected void Lost_MovePoint_Start_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x4851D97L /*Lost_MovePoint_Start_3*/);
		if(Rnd.getChance(8)) {
			if (changeState(state -> WalkRandom(blendTime)))
				return;
		}
		doAction(2834477951L /*WALK_LOSTSHEEP*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Lost_Sheep_3", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Lost_MovePoint_End(blendTime), 1000)));
	}

	protected void Lost_MovePoint_Start_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE51035DCL /*Lost_MovePoint_Start_4*/);
		if(Rnd.getChance(8)) {
			if (changeState(state -> WalkRandom(blendTime)))
				return;
		}
		doAction(2834477951L /*WALK_LOSTSHEEP*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Lost_Sheep_4", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Lost_MovePoint_End(blendTime), 1000)));
	}

	protected void Lost_MovePoint_Start_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x775251C4L /*Lost_MovePoint_Start_5*/);
		if(Rnd.getChance(8)) {
			if (changeState(state -> WalkRandom(blendTime)))
				return;
		}
		doAction(2834477951L /*WALK_LOSTSHEEP*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Lost_Sheep_5", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Lost_MovePoint_End(blendTime), 1000)));
	}

	protected void Lost_MovePoint_Start_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAB486522L /*Lost_MovePoint_Start_6*/);
		if(Rnd.getChance(8)) {
			if (changeState(state -> WalkRandom(blendTime)))
				return;
		}
		doAction(2834477951L /*WALK_LOSTSHEEP*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Lost_Sheep_6", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Lost_MovePoint_End(blendTime), 1000)));
	}

	protected void Lost_MovePoint_Start_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x924F4C44L /*Lost_MovePoint_Start_7*/);
		if(Rnd.getChance(8)) {
			if (changeState(state -> WalkRandom(blendTime)))
				return;
		}
		doAction(2834477951L /*WALK_LOSTSHEEP*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Lost_Sheep_7", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Lost_MovePoint_End(blendTime), 1000)));
	}

	protected void Lost_MovePoint_Start_8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCD12141CL /*Lost_MovePoint_Start_8*/);
		if(Rnd.getChance(8)) {
			if (changeState(state -> WalkRandom(blendTime)))
				return;
		}
		doAction(2834477951L /*WALK_LOSTSHEEP*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Lost_Sheep_8", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Lost_MovePoint_End(blendTime), 1000)));
	}

	protected void Lost_MovePoint_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7D5F69C0L /*Lost_MovePoint_End*/);
		setVariable(0xA425A34EL /*_LostWayEnd*/, 1);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		changeState(state -> Wait(blendTime));
	}

	protected void Suicide_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x2BD8C797L /*Suicide_Die*/);
		if (getActor().isVehicle() && getActor().getOwner() != null) {
			if (changeState(state -> End_Die(0.3)))
				return;
		}
		doAction(2310636251L /*DIE_LOSTSHEEP*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 1000));
	}

	protected void End_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xB27633BDL /*End_Die*/);
		doAction(1426790432L /*END_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> End_Die(blendTime), 3000));
	}

	protected void LostWay_Dead(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xAE2A4D01L /*LostWay_Dead*/);
		doAction(2310636251L /*DIE_LOSTSHEEP*/, blendTime, onDoActionEnd -> scheduleState(state -> LostWay_Dead(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0) {
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
		if (getVariable(0xBB86C5A3L /*_BattleMode*/) == 0) {
			if (changeState(state -> SearchEnemy(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xBB86C5A3L /*_BattleMode*/) == 1) {
			if (changeState(state -> ChaseEnemy(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnResetAI(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Riding_Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRideEnd(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> RidingOff_Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
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
	public EAiHandlerResult HandleStuned(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Stun(0.1)))
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
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Stun(0.1)))
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
}
