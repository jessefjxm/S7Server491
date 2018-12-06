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
@IAIName("pc_bot")
public class Ai_pc_bot extends CreatureAI {
	public Ai_pc_bot(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x9C1A0E76L /*_fear*/, 0);
		setVariable(0x13B80BEAL /*Stress_Enemy_Chase*/, 1);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0xC9E6548BL /*_AI_AttackerType*/, 0);
		setVariable(0x2C297080L /*_Stress_AI_Attack1_Distance*/, 150);
		setVariable(0x47478B48L /*AI_BT_Attack1_CoolCountN*/, 0);
		setVariable(0x44ABD67BL /*AI_BT_Attack2_CoolCountN*/, 0);
		setVariable(0x8DB8E719L /*AI_BT_Attack1_CoolCount*/, 0);
		setVariable(0x6241F31L /*AI_BT_Attack2_CoolCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void ReleaseState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCBE083E6L /*ReleaseState*/);
		doAction(2524986171L /*BT_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void StanceCheck(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC7700F3AL /*StanceCheck*/);
		if (getVariable(0x9C1A0E76L /*_fear*/) == 0) {
			if (changeState(state -> ReleaseState(blendTime)))
				return;
		}
		if (getVariable(0x9C1A0E76L /*_fear*/) == 1) {
			if (changeState(state -> Damage_Fear(blendTime)))
				return;
		}
		changeState(state -> TerminateState(blendTime));
	}

	protected void KnockBack_State(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x3E51A4D1L /*KnockBack_State*/);
		doAction(4059115659L /*KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 100));
	}

	protected void KnockDown_State(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xAB2D9772L /*KnockDown_State*/);
		doAction(1872990688L /*KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 100));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 1000));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 10000));
	}

	protected void Damage_Release(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x82D0AC8EL /*Damage_Release*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 3000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 3000));
	}

	protected void Damage_Fear(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBF1D8728L /*Damage_Fear*/);
		doAction(1002473142L /*DAMAGE_FEAR*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1000, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Damage_Fear(blendTime), 100)));
	}

	protected void Play_Flute_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF8E6D1AAL /*Play_Flute_Go*/);
		doAction(380055388L /*PLAY_FLUTE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("instance_waypoint", "w133_30", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Play_Flute_End(blendTime), 1000)));
	}

	protected void Play_Flute_Go_Short(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2BC7ACB0L /*Play_Flute_Go_Short*/);
		doAction(380055388L /*PLAY_FLUTE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("instance_waypoint", "w133_50", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Play_Flute_End(blendTime), 1000)));
	}

	protected void Play_Flute_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E73BC37L /*Play_Flute_End*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 700).forEach(consumer -> consumer.getAi().Suicide(getActor(), null));
		doAction(1075905676L /*PLAY_FLUTE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Play_Flute_Go_234(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x18362A2EL /*Play_Flute_Go_234*/);
		doAction(380055388L /*PLAY_FLUTE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("instance_waypoint", "w234_30", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Play_Flute_End_234(blendTime), 1000)));
	}

	protected void Play_Flute_End_234(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x134EC0AEL /*Play_Flute_End_234*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 700).forEach(consumer -> consumer.getAi().Suicide(getActor(), null));
		doAction(1075905676L /*PLAY_FLUTE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Play_Flute_Go_301(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAB5C9AF7L /*Play_Flute_Go_301*/);
		doAction(4193103538L /*SOCIAL_FLUTE_WALK_LOW*/, blendTime, onDoActionEnd -> scheduleState(state -> Play_Flute_Go_301(blendTime), 1000));
	}

	protected void Play_Flute_End_301(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5E273D7L /*Play_Flute_End_301*/);
		doAction(1075905676L /*PLAY_FLUTE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void GunFire_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x96A7CEFBL /*GunFire_Ready*/);
		doAction(3411802288L /*ID_GUN_START*/, blendTime, onDoActionEnd -> scheduleState(state -> GunFire_Wait(blendTime), 3000));
	}

	protected void GunFire_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA05DD58DL /*GunFire_Wait*/);
		doAction(2106596449L /*ID_GUN_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 9000));
	}

	protected void GunFire_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881FFA8FL /*GunFire_End*/);
		doAction(4271580473L /*ID_GUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Stress_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCA1645E3L /*Stress_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stress_Wait(blendTime), 1000));
	}

	protected void Stress_Wait_Test(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE32F1DCL /*Stress_Wait_Test*/);
		if (target != null && isTargetRidable(target)) {
			if (changeState(state -> Stress_Move_Random(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stress_Wait_Test(blendTime), 3000 + Rnd.get(-500,500)));
	}

	protected void Stress_Wait_Move(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCA7DBDFCL /*Stress_Wait_Move*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Stress_Monster_Chase(0.3)))
				return;
		}
		if (Rnd.get(100) < 25) {
			if (changeState(state -> Stress_Move_Random(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stress_Wait_Move(blendTime), 3000 + Rnd.get(-500,500)));
	}

	protected void Stress_Find_Corpse(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC2762F0L /*Stress_Find_Corpse*/);
		if (findTarget(EAIFindTargetType.Corpse, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000)) {
			if (changeState(state -> Stress_Move_Corpse(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stress_Wait_Move(blendTime), 3000 + Rnd.get(-500,500)));
	}

	protected void Stress_Move_Corpse(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x6FE6C8F2L /*Stress_Move_Corpse*/);
		if (isTargetLost()) {
			if (changeState(state -> Stress_Wait(blendTime)))
				return;
		}
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		if (getVariable(0xF630F33AL /*_Distance*/) < 100) {
			if (changeState(state -> Stress_Pick_Item0(0.3)))
				return;
		}
		doAction(189132370L /*STRESS_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stress_Move_Corpse(blendTime), 2000)));
	}

	protected void Stress_Pick_Item0(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x24983BA1L /*Stress_Pick_Item0*/);
		doAction(233426249L /*ITEM_PICK_START*/, blendTime, onDoActionEnd -> scheduleState(state -> Stress_Pick_Item1(blendTime), 2000));
	}

	protected void Stress_Pick_Item1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC1FEA77CL /*Stress_Pick_Item1*/);
		doAction(3970496220L /*ITEM_PICK_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Stress_Wait_Move(blendTime), 2000));
	}

	protected void Stress_Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD26EE472L /*Stress_Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Stress_Monster_Chase(0.3)))
				return;
		}
		doAction(2617603032L /*STRESS_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 1500, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stress_Wait_Move(blendTime), 5000)));
	}

	protected void Stress_Monster_Chase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x576C18EAL /*Stress_Monster_Chase*/);
		if (isTargetLost()) {
			if (changeState(state -> Stress_Wait(blendTime)))
				return;
		}
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		if (getVariable(0xF630F33AL /*_Distance*/) < 100) {
			if (changeState(state -> Stress_Battle_Wait(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Stress_Move_Random(0.3)))
				return;
		}
		if (getVariable(0xF630F33AL /*_Distance*/) > 1000) {
			if (changeState(state -> Stress_Move_Random(0.3)))
				return;
		}
		doAction(189132370L /*STRESS_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stress_Monster_Chase(blendTime), 5000)));
	}

	protected void Stress_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x44119D9FL /*Stress_Battle_Wait*/);
		setVariable(0x44ABD67BL /*AI_BT_Attack2_CoolCountN*/, getVariable(0x44ABD67BL /*AI_BT_Attack2_CoolCountN*/) - 1);
		setVariable(0x47478B48L /*AI_BT_Attack1_CoolCountN*/, getVariable(0x47478B48L /*AI_BT_Attack1_CoolCountN*/) - 1);
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Stress_Find_Corpse(0.3)))
				return;
		}
		if (getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x2C297080L /*_Stress_AI_Attack1_Distance*/) && getVariable(0x44ABD67BL /*AI_BT_Attack2_CoolCountN*/) <= 0) {
			if (changeState(state -> Stress_Attack2(0.3)))
				return;
		}
		if (getVariable(0xF630F33AL /*_Distance*/) < getVariable(0x2C297080L /*_Stress_AI_Attack1_Distance*/) && getVariable(0x47478B48L /*AI_BT_Attack1_CoolCountN*/) <= 0) {
			if (changeState(state -> Stress_Attack1(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Stress_Monster_Chase(0.3)))
				return;
		}
		doAction(2524986171L /*BT_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stress_Battle_Wait(blendTime), 200));
	}

	protected void Stress_Follow(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5AE44A79L /*Stress_Follow*/);
		doAction(189132370L /*STRESS_RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 150, 350, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stress_Follow_Wait(blendTime), 2000)));
	}

	protected void Stress_Follow_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD748C8B7L /*Stress_Follow_Wait*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 500) {
			if (changeState(state -> Stress_Follow(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stress_Follow_Wait(blendTime), 1000));
	}

	protected void Stress_Attack_Fist(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x62FBD1FFL /*Stress_Attack_Fist*/);
		if (isTargetLost()) {
			if (changeState(state -> Stress_Wait(blendTime)))
				return;
		}
		doAction(522001871L /*BT_ATTACK_FIST_1*/, blendTime, onDoActionEnd -> changeState(state -> Stress_Battle_Wait(blendTime)));
	}

	protected void Stress_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1460A883L /*Stress_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Stress_Wait(blendTime)))
				return;
		}
		setVariable(0x47478B48L /*AI_BT_Attack1_CoolCountN*/, getVariable(0x8DB8E719L /*AI_BT_Attack1_CoolCount*/));
		doAction(4123583833L /*BT_ATTACK_B1*/, blendTime, onDoActionEnd -> changeState(state -> Stress_Battle_Wait(blendTime)));
	}

	protected void Stress_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8AE36F32L /*Stress_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Stress_Wait(blendTime)))
				return;
		}
		setVariable(0x44ABD67BL /*AI_BT_Attack2_CoolCountN*/, getVariable(0x6241F31L /*AI_BT_Attack2_CoolCount*/));
		doAction(1031147838L /*BT_ATTACK_B2*/, blendTime, onDoActionEnd -> changeState(state -> Stress_Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Stress_Battle_Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> KnockBack_State(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> KnockDown_State(0.1)))
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
	public EAiHandlerResult HandleRigid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Rigid(0.1)))
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
	public EAiHandlerResult HandleFeared(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x9C1A0E76L /*_fear*/, 1);
		if (changeState(state -> StanceCheck(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFearReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x9C1A0E76L /*_fear*/, 0);
		if (changeState(state -> StanceCheck(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRestrictReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> TerminateState(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleInitialize(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> InitialState(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Play_Flute_Go_Short(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Play_Flute_Go_234(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> GunFire_Ready(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> GunFire_End(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOrderSkill1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Play_Flute_Go_301(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOrderSkill2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Play_Flute_End_301(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent_Stress(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Stress_Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent_Follow(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Stress_Follow(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent_Battle(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Stress_Move_Random(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent_Corpse(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Stress_Find_Corpse(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
