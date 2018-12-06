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
@IAIName("oceanboss_boss_test")
public class Ai_oceanboss_boss_test extends CreatureAI {
	public Ai_oceanboss_boss_test(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		setVariable(0x5749908DL /*_JumpCount*/, 0);
		setVariable(0xADEBDC44L /*_ThunderCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		doAction(3546093118L /*START*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		changeState(state -> Move_Return(blendTime));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> changeState(state -> Start_Action2(blendTime)));
	}

	protected void Start_Action2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC141FFFAL /*Start_Action2*/);
		doAction(3451186354L /*START_ACTION_A*/, blendTime, onDoActionEnd -> changeState(state -> Start_Action3(blendTime)));
	}

	protected void Start_Action3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3F73F0B3L /*Start_Action3*/);
		doAction(4050609673L /*START_ACTION_B*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Roar_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCC2B11FEL /*Roar_Wait*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(1648198093L /*ROAR_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Roar_Logic(blendTime), 1000));
	}

	protected void Roar_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA92F2D5DL /*Roar_Wait2*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, 0);
		doAction(3556972960L /*ROAR_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Roar_Logic(blendTime), 1000));
	}

	protected void Roar_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD5B553CL /*Roar_Logic*/);
		if(Rnd.getChance(45)) {
			if (changeState(state -> Roar(0.8)))
				return;
		}
		if (changeState(state -> Roar2(0.8)))
			return;
		changeState(state -> Roar_Logic(blendTime));
	}

	protected void Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x60186BFFL /*Roar*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Summon(getActor(), null));
		doAction(133347576L /*ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Roar2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x26A0A928L /*Roar2*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Summon(getActor(), null));
		doAction(2334705426L /*ROAR3*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Missile(getActor(), null));
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 7200000) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 70 && getVariable(0x5749908DL /*_JumpCount*/) == 0) {
			if (changeState(state -> Attack_Jump(0.8)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 50 && getVariable(0x5749908DL /*_JumpCount*/) == 1) {
			if (changeState(state -> Attack_Jump(0.8)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0x5749908DL /*_JumpCount*/) == 2) {
			if (changeState(state -> Attack_Jump(0.8)))
				return;
		}
		if (getVariable(0xADEBDC44L /*_ThunderCount*/) == 0 && getVariable(0x3F487035L /*_Hp*/) < 60) {
			if (changeState(state -> Attack_Thunder(0.8)))
				return;
		}
		if (getVariable(0xADEBDC44L /*_ThunderCount*/) == 1 && getVariable(0x3F487035L /*_Hp*/) < 20) {
			if (changeState(state -> Attack_Thunder(0.8)))
				return;
		}
		if (getVariable(0x6E0E85B9L /*_AttackCount*/) == 2) {
			if (changeState(state -> Roar_Wait2(0.8)))
				return;
		}
		if(Rnd.getChance(20)) {
			if (changeState(state -> Attack_Breath_F(0.8)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_Jump_Low(0.8)))
				return;
		}
		if(Rnd.getChance(40)) {
			if (changeState(state -> Roar_Attack(0.8)))
				return;
		}
		if (changeState(state -> Roar_Logic(0.8)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8BC3ABDL /*Attack_Jump*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		setVariable(0x5749908DL /*_JumpCount*/, getVariable(0x5749908DL /*_JumpCount*/) + 1);
		doAction(2705621134L /*ATTACK_JUMP*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Attack_Jump_Low(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFC9522B5L /*Attack_Jump_Low*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Summon(getActor(), null));
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(1240895934L /*ATTACK_JUMP_LOW*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Attack_Breath_F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x65ADC451L /*Attack_Breath_F*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(1944589819L /*ATTACK_BREATH_FOWARD*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Attack_Breath_Turn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5D9D7DAEL /*Attack_Breath_Turn*/);
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(3937755100L /*ATTACK_BREATH_TURN*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Attack_Thunder(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8FE3FA51L /*Attack_Thunder*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Tornado(getActor(), null));
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		setVariable(0xADEBDC44L /*_ThunderCount*/, getVariable(0xADEBDC44L /*_ThunderCount*/) + 1);
		doAction(2859529053L /*ATTACK_THUNDER*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Roar_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7A64B264L /*Roar_Attack*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Summon(getActor(), null));
		setVariable(0x6E0E85B9L /*_AttackCount*/, getVariable(0x6E0E85B9L /*_AttackCount*/) + 1);
		doAction(4006574634L /*ROAR_TURN*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 4) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2241895867L /*MOVE_WALK_ING*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 10000)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().Gotohell(getActor(), null));
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Gotohell(getActor(), null));
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 2000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult JumpLow(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Attack_Jump_Low(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Breath(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Attack_Breath_F(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult JumpHigh(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Attack_Jump(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wait(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Roar(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Roar(0.8)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
