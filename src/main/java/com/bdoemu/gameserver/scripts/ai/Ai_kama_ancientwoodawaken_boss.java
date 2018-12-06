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
@IAIName("kama_ancientwoodawaken_boss")
public class Ai_kama_ancientwoodawaken_boss extends CreatureAI {
	public Ai_kama_ancientwoodawaken_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x1142E147L /*_SummonLeg_Count*/, 1);
		setVariable(0xCF959574L /*_SummonShield_Count*/, 1);
		setVariable(0x9BAF2A6DL /*_SpiderAttackCount*/, 3);
		setVariable(0xAA78D096L /*_SpecialAttack_Count*/, 0);
		setVariable(0x307DB559L /*_Destroy_Shield_Count*/, 0);
		setVariable(0xD9492841L /*_Destroy_Leg_Count*/, 0);
		setVariable(0x17241B06L /*_Destroy_Leg_FL_Count*/, 0);
		setVariable(0xD24BF3ECL /*_Destroy_Leg_FR_Count*/, 0);
		setVariable(0xE98FC0CBL /*_Destroy_Leg_BL_Count*/, 0);
		setVariable(0x617F8E5CL /*_Destroy_Leg_BR_Count*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x714FD77AL /*_Parent_Die*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Talk_Wait(blendTime), 5000));
	}

	protected void Talk_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x109D3080L /*Talk_Wait*/);
		doAction(764562823L /*TALK_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Talk_Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 1000));
	}

	protected void Summon_Opin(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4F8D8AD1L /*Summon_Opin*/);
		if (isTargetLost()) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		doAction(534523460L /*ROTATE*/, blendTime, onDoActionEnd -> changeState(state -> Start_Action(blendTime)));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_OPIN_START");
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Leg(blendTime), 3000));
	}

	protected void Summon_Leg(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x208F7502L /*Summon_Leg*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().TimeAttack(getActor(), null));
		setVariable(0x1142E147L /*_SummonLeg_Count*/, 0);
		doAction(2747633155L /*SUMMON_LEG*/, blendTime, onDoActionEnd -> changeState(state -> Shield_Battle_Wait(blendTime)));
	}

	protected void Summon_Shield(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCA35F66CL /*Summon_Shield*/);
		setVariable(0xCF959574L /*_SummonShield_Count*/, 0);
		doAction(3985585216L /*SUMMON_SHIELD*/, blendTime, onDoActionEnd -> changeState(state -> Destroy_Logic(blendTime)));
	}

	protected void Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x84794EB4L /*Wait2*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		if (getVariable(0x1142E147L /*_SummonLeg_Count*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 5000)) {
				if (changeState(state -> Summon_Leg(0.2)))
					return;
			}
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 5000)) {
			if (changeState(state -> Shield_Battle_Wait(0.2)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait2(blendTime), 1000));
	}

	protected void Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x34E5AE02L /*Summon*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_OPINTET_APPEAR");
		doAction(3635031213L /*SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Wait(blendTime)));
	}

	protected void Avoid_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE712FF1EL /*Avoid_Wait*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x52154D23L /*AI_AT_Swing_B*/) > 0 && getVariable(0xE98FC0CBL /*_Destroy_Leg_BL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= -90 && target != null && getAngleToTarget(target) >= -179 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x52154D23L /*AI_AT_Swing_B*/))) {
				if (changeState(state -> Avoid_Attack_Swing_BL(0.2)))
					return;
			}
		}
		if (getVariable(0x52154D23L /*AI_AT_Swing_B*/) > 0 && getVariable(0x617F8E5CL /*_Destroy_Leg_BR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= 180 && target != null && getAngleToTarget(target) >= 90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x52154D23L /*AI_AT_Swing_B*/))) {
				if (changeState(state -> Avoid_Attack_Swing_BR(0.2)))
					return;
			}
		}
		if (getVariable(0xD9C3A034L /*AI_AT_B*/) > 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && getVariable(0xE98FC0CBL /*_Destroy_Leg_BL_Count*/) == 0 && getVariable(0x617F8E5CL /*_Destroy_Leg_BR_Count*/) == 0 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= -90 && target != null && getAngleToTarget(target) >= 90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xD9C3A034L /*AI_AT_B*/))) {
				if (changeState(state -> Avoid_Attack_BLR(0.2)))
					return;
			}
		}
		if (getVariable(0xD9C3A034L /*AI_AT_B*/) > 0 && getVariable(0xE98FC0CBL /*_Destroy_Leg_BL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= -90 && target != null && getAngleToTarget(target) >= -179 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xD9C3A034L /*AI_AT_B*/))) {
				if (changeState(state -> Avoid_Attack_BL(0.2)))
					return;
			}
		}
		if (getVariable(0xD9C3A034L /*AI_AT_B*/) > 0 && getVariable(0x617F8E5CL /*_Destroy_Leg_BR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= 180 && target != null && getAngleToTarget(target) >= 90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xD9C3A034L /*AI_AT_B*/))) {
				if (changeState(state -> Avoid_Attack_BR(0.2)))
					return;
			}
		}
		if (getVariable(0xA971ACB7L /*AI_AT_Swing_F*/) > 0 && getVariable(0x17241B06L /*_Destroy_Leg_FL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 0 && target != null && getAngleToTarget(target) >= -90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xA971ACB7L /*AI_AT_Swing_F*/))) {
				if (changeState(state -> Avoid_Attack_Swing_FL(0.2)))
					return;
			}
		}
		if (getVariable(0xA971ACB7L /*AI_AT_Swing_F*/) > 0 && getVariable(0xD24BF3ECL /*_Destroy_Leg_FR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 90 && target != null && getAngleToTarget(target) >= 0 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xA971ACB7L /*AI_AT_Swing_F*/))) {
				if (changeState(state -> Avoid_Attack_Swing_FR(0.2)))
					return;
			}
		}
		if (getVariable(0x64FBE53FL /*AI_AT_F*/) > 0 && getVariable(0x17241B06L /*_Destroy_Leg_FL_Count*/) == 0 && getVariable(0xD24BF3ECL /*_Destroy_Leg_FR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 90 && target != null && getAngleToTarget(target) >= -90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x64FBE53FL /*AI_AT_F*/))) {
				if (changeState(state -> Avoid_Attack_FLR(0.2)))
					return;
			}
		}
		if (getVariable(0x64FBE53FL /*AI_AT_F*/) > 0 && getVariable(0x17241B06L /*_Destroy_Leg_FL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 0 && target != null && getAngleToTarget(target) >= -90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x64FBE53FL /*AI_AT_F*/))) {
				if (changeState(state -> Avoid_Attack_FL(0.2)))
					return;
			}
		}
		if (getVariable(0x64FBE53FL /*AI_AT_F*/) > 0 && getVariable(0xD24BF3ECL /*_Destroy_Leg_FR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 90 && target != null && getAngleToTarget(target) >= 0 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x64FBE53FL /*AI_AT_F*/))) {
				if (changeState(state -> Avoid_Attack_FR(0.2)))
					return;
			}
		}
		doAction(1333086569L /*AVOID_BATTLE_WAIT2*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Wait(blendTime)));
	}

	protected void Avoid_Wait3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x269C8B86L /*Avoid_Wait3*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(3804072644L /*AVOID_BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Wait(blendTime), 1000));
	}

	protected void Revival(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x59ADD975L /*Revival*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_OPINSHIELD_BROKEN_1");
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
		if (getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 2) {
			if (changeState(state -> Shield_Battle_Wait(0.2)))
				return;
		}
		if (getVariable(0xD9492841L /*_Destroy_Leg_Count*/) >= 2) {
			if (changeState(state -> Battle_Wait(0.2)))
				return;
		}
		doAction(1727911018L /*REVIVAL*/, blendTime, onDoActionEnd -> scheduleState(state -> Revival(blendTime), 5000));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(600051306L /*SHIELD_BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Shield_Battle_Wait(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		clearAggro(true);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(3763948041L /*RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Delete_Die(blendTime), 1000)));
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		clearAggro(true);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 2) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 5000)) {
				if (changeState(state -> Shield_Battle_Wait(0.2)))
					return;
			}
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) >= 2) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 5000)) {
				if (changeState(state -> Battle_Wait(0.2)))
					return;
			}
		}
		scheduleState(state -> TargetLost(blendTime), 1000);
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.2)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void TargetChange_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A6D56A5L /*TargetChange_Logic*/);
		if(Rnd.getChance(50)) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 7000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Avoid_TargetChange_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8AFBB1C1L /*Avoid_TargetChange_Logic*/);
		if(Rnd.getChance(50)) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Avoid_Wait(0.3)))
					return;
			}
		}
		changeState(state -> Avoid_Wait(blendTime));
	}

	protected void Shield_TargetChange_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCAE6A85AL /*Shield_TargetChange_Logic*/);
		if(Rnd.getChance(50)) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 7000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Shield_Battle_Wait(0.3)))
					return;
			}
		}
		changeState(state -> Shield_Battle_Wait(blendTime));
	}

	protected void Attack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x50B0953FL /*Attack_Logic*/);
		setVariable(0xAA78D096L /*_SpecialAttack_Count*/, getVariable(0xAA78D096L /*_SpecialAttack_Count*/) + 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x714FD77AL /*_Parent_Die*/) == 0 && getVariable(0x3F487035L /*_HP*/) < 3) {
			if (changeState(state -> Parent_Die(0.2)))
				return;
		}
		if (getVariable(0xAA78D096L /*_SpecialAttack_Count*/) >= 15 && target != null && getDistanceToTarget(target) >= 1500 && target != null && getDistanceToTarget(target) <= 5000 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Attack_Energy(0.2)))
				return;
		}
		if (getVariable(0x14467319L /*AI_AT_Breath*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x61A9BDB9L /*AI_AT_Breath_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x7EACAE6AL /*AI_AT_Breath_MaxDistance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x14467319L /*AI_AT_Breath*/))) {
				if (changeState(state -> Attack_Breath(0.2)))
					return;
			}
		}
		if (getVariable(0xED075C83L /*AI_AT_Earthquake*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x418AA3BAL /*AI_AT_Earthquake_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x5381190BL /*AI_AT_Earthquake_MaxDistance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xED075C83L /*AI_AT_Earthquake*/))) {
				if (changeState(state -> Attack_Earthquake(0.2)))
					return;
			}
		}
		if (getVariable(0x138D160L /*AI_AT_Shockwave*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xD5843DC5L /*AI_AT_Shockwave_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x879A92D5L /*AI_AT_Shockwave_MaxDistance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x138D160L /*AI_AT_Shockwave*/))) {
				if (changeState(state -> Attack_Shockwave(0.2)))
					return;
			}
		}
		if (getVariable(0x880C3A26L /*AI_AT_Spear*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x29C342DBL /*AI_AT_Spear_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x4156CF2CL /*AI_AT_Spear_MaxDistance*/) && target != null && getAngleToTarget(target) <= 45 && target != null && getAngleToTarget(target) >= -45 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x880C3A26L /*AI_AT_Spear*/))) {
				if (changeState(state -> Attack_Spear_F(0.2)))
					return;
			}
		}
		if (getVariable(0x880C3A26L /*AI_AT_Spear*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x29C342DBL /*AI_AT_Spear_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x4156CF2CL /*AI_AT_Spear_MaxDistance*/) && target != null && getAngleToTarget(target) <= -135 && target != null && getAngleToTarget(target) >= -179 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Spear_B(0.2)))
					return;
			}
		}
		if (getVariable(0x880C3A26L /*AI_AT_Spear*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x29C342DBL /*AI_AT_Spear_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x4156CF2CL /*AI_AT_Spear_MaxDistance*/) && target != null && getAngleToTarget(target) <= 180 && target != null && getAngleToTarget(target) >= 135 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Spear_B(0.2)))
					return;
			}
		}
		if (getVariable(0x880C3A26L /*AI_AT_Spear*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x29C342DBL /*AI_AT_Spear_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x4156CF2CL /*AI_AT_Spear_MaxDistance*/) && target != null && getAngleToTarget(target) <= -45 && target != null && getAngleToTarget(target) >= -135 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x880C3A26L /*AI_AT_Spear*/))) {
				if (changeState(state -> Attack_Spear_L(0.2)))
					return;
			}
		}
		if (getVariable(0x880C3A26L /*AI_AT_Spear*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x29C342DBL /*AI_AT_Spear_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x4156CF2CL /*AI_AT_Spear_MaxDistance*/) && target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 45 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x880C3A26L /*AI_AT_Spear*/))) {
				if (changeState(state -> Attack_Spear_R(0.2)))
					return;
			}
		}
		if (getVariable(0x52154D23L /*AI_AT_Swing_B*/) > 0 && getVariable(0xE98FC0CBL /*_Destroy_Leg_BL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= -90 && target != null && getAngleToTarget(target) >= -179 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x52154D23L /*AI_AT_Swing_B*/))) {
				if (changeState(state -> Attack_Swing_BL(0.2)))
					return;
			}
		}
		if (getVariable(0x52154D23L /*AI_AT_Swing_B*/) > 0 && getVariable(0x617F8E5CL /*_Destroy_Leg_BR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= 180 && target != null && getAngleToTarget(target) >= 90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x52154D23L /*AI_AT_Swing_B*/))) {
				if (changeState(state -> Attack_Swing_BR(0.2)))
					return;
			}
		}
		if (getVariable(0xD9C3A034L /*AI_AT_B*/) > 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && getVariable(0xE98FC0CBL /*_Destroy_Leg_BL_Count*/) == 0 && getVariable(0x617F8E5CL /*_Destroy_Leg_BR_Count*/) == 0 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= -90 && target != null && getAngleToTarget(target) >= 90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xD9C3A034L /*AI_AT_B*/))) {
				if (changeState(state -> Attack_BLR(0.2)))
					return;
			}
		}
		if (getVariable(0xD9C3A034L /*AI_AT_B*/) > 0 && getVariable(0xE98FC0CBL /*_Destroy_Leg_BL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= -90 && target != null && getAngleToTarget(target) >= -179 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xD9C3A034L /*AI_AT_B*/))) {
				if (changeState(state -> Attack_BL(0.2)))
					return;
			}
		}
		if (getVariable(0xD9C3A034L /*AI_AT_B*/) > 0 && getVariable(0x617F8E5CL /*_Destroy_Leg_BR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= 180 && target != null && getAngleToTarget(target) >= 90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xD9C3A034L /*AI_AT_B*/))) {
				if (changeState(state -> Attack_BR(0.2)))
					return;
			}
		}
		if (getVariable(0xA971ACB7L /*AI_AT_Swing_F*/) > 0 && getVariable(0x17241B06L /*_Destroy_Leg_FL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 0 && target != null && getAngleToTarget(target) >= -90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xA971ACB7L /*AI_AT_Swing_F*/))) {
				if (changeState(state -> Attack_Swing_FL(0.2)))
					return;
			}
		}
		if (getVariable(0xA971ACB7L /*AI_AT_Swing_F*/) > 0 && getVariable(0xD24BF3ECL /*_Destroy_Leg_FR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 90 && target != null && getAngleToTarget(target) >= 0 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xA971ACB7L /*AI_AT_Swing_F*/))) {
				if (changeState(state -> Attack_Swing_FR(0.2)))
					return;
			}
		}
		if (getVariable(0x64FBE53FL /*AI_AT_F*/) > 0 && getVariable(0x17241B06L /*_Destroy_Leg_FL_Count*/) == 0 && getVariable(0xD24BF3ECL /*_Destroy_Leg_FR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 90 && target != null && getAngleToTarget(target) >= -90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x64FBE53FL /*AI_AT_F*/))) {
				if (changeState(state -> Attack_FLR(0.2)))
					return;
			}
		}
		if (getVariable(0x64FBE53FL /*AI_AT_F*/) > 0 && getVariable(0x17241B06L /*_Destroy_Leg_FL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 0 && target != null && getAngleToTarget(target) >= -90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x64FBE53FL /*AI_AT_F*/))) {
				if (changeState(state -> Attack_FL(0.2)))
					return;
			}
		}
		if (getVariable(0x64FBE53FL /*AI_AT_F*/) > 0 && getVariable(0xD24BF3ECL /*_Destroy_Leg_FR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 90 && target != null && getAngleToTarget(target) >= 0 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x64FBE53FL /*AI_AT_F*/))) {
				if (changeState(state -> Attack_FR(0.2)))
					return;
			}
		}
		if (getVariable(0x4BCE222EL /*AI_AT_Meteo*/) > 0 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Attack_Meteo(0.2)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Shield_Attack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xFF5ADD5CL /*Shield_Attack_Logic*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0xAA78D096L /*_SpecialAttack_Count*/, getVariable(0xAA78D096L /*_SpecialAttack_Count*/) + 1);
		if (getVariable(0xAA78D096L /*_SpecialAttack_Count*/) >= 10 && target != null && getDistanceToTarget(target) >= 1500 && target != null && getDistanceToTarget(target) <= 3000 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Shield_Attack_Energy(0.2)))
				return;
		}
		if (getVariable(0x14467319L /*AI_AT_Breath*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x61A9BDB9L /*AI_AT_Breath_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x7EACAE6AL /*AI_AT_Breath_MaxDistance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x14467319L /*AI_AT_Breath*/))) {
				if (changeState(state -> Shield_Attack_Breath(0.2)))
					return;
			}
		}
		if (getVariable(0xED075C83L /*AI_AT_Earthquake*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x418AA3BAL /*AI_AT_Earthquake_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x5381190BL /*AI_AT_Earthquake_MaxDistance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xED075C83L /*AI_AT_Earthquake*/))) {
				if (changeState(state -> Shield_Attack_Earthquake(0.2)))
					return;
			}
		}
		if (getVariable(0x138D160L /*AI_AT_Shockwave*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0xD5843DC5L /*AI_AT_Shockwave_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x879A92D5L /*AI_AT_Shockwave_MaxDistance*/) && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x138D160L /*AI_AT_Shockwave*/))) {
				if (changeState(state -> Shield_Attack_Shockwave(0.2)))
					return;
			}
		}
		if (getVariable(0x880C3A26L /*AI_AT_Spear*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x29C342DBL /*AI_AT_Spear_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x4156CF2CL /*AI_AT_Spear_MaxDistance*/) && target != null && getAngleToTarget(target) <= 45 && target != null && getAngleToTarget(target) >= -45 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x880C3A26L /*AI_AT_Spear*/))) {
				if (changeState(state -> Shield_Attack_Spear_F(0.2)))
					return;
			}
		}
		if (getVariable(0x880C3A26L /*AI_AT_Spear*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x29C342DBL /*AI_AT_Spear_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x4156CF2CL /*AI_AT_Spear_MaxDistance*/) && target != null && getAngleToTarget(target) <= -135 && target != null && getAngleToTarget(target) >= -179 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Shield_Attack_Spear_B(0.2)))
					return;
			}
		}
		if (getVariable(0x880C3A26L /*AI_AT_Spear*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x29C342DBL /*AI_AT_Spear_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x4156CF2CL /*AI_AT_Spear_MaxDistance*/) && target != null && getAngleToTarget(target) <= 180 && target != null && getAngleToTarget(target) >= 135 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Shield_Attack_Spear_B(0.2)))
					return;
			}
		}
		if (getVariable(0x880C3A26L /*AI_AT_Spear*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x29C342DBL /*AI_AT_Spear_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x4156CF2CL /*AI_AT_Spear_MaxDistance*/) && target != null && getAngleToTarget(target) <= -45 && target != null && getAngleToTarget(target) >= -135 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x880C3A26L /*AI_AT_Spear*/))) {
				if (changeState(state -> Shield_Attack_Spear_L(0.2)))
					return;
			}
		}
		if (getVariable(0x880C3A26L /*AI_AT_Spear*/) > 0 && target != null && getDistanceToTarget(target) >= getVariable(0x29C342DBL /*AI_AT_Spear_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x4156CF2CL /*AI_AT_Spear_MaxDistance*/) && target != null && getAngleToTarget(target) <= 135 && target != null && getAngleToTarget(target) >= 45 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x880C3A26L /*AI_AT_Spear*/))) {
				if (changeState(state -> Shield_Attack_Spear_R(0.2)))
					return;
			}
		}
		if (getVariable(0x52154D23L /*AI_AT_Swing_B*/) > 0 && getVariable(0xE98FC0CBL /*_Destroy_Leg_BL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= -90 && target != null && getAngleToTarget(target) >= -179 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x52154D23L /*AI_AT_Swing_B*/))) {
				if (changeState(state -> Shield_Attack_Swing_BL(0.2)))
					return;
			}
		}
		if (getVariable(0x52154D23L /*AI_AT_Swing_B*/) > 0 && getVariable(0x617F8E5CL /*_Destroy_Leg_BR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= 180 && target != null && getAngleToTarget(target) >= 90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x52154D23L /*AI_AT_Swing_B*/))) {
				if (changeState(state -> Shield_Attack_Swing_BR(0.2)))
					return;
			}
		}
		if (getVariable(0xD9C3A034L /*AI_AT_B*/) > 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && getVariable(0xE98FC0CBL /*_Destroy_Leg_BL_Count*/) == 0 && getVariable(0x617F8E5CL /*_Destroy_Leg_BR_Count*/) == 0 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= -90 && target != null && getAngleToTarget(target) >= 90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xD9C3A034L /*AI_AT_B*/))) {
				if (changeState(state -> Shield_Attack_BLR(0.2)))
					return;
			}
		}
		if (getVariable(0xD9C3A034L /*AI_AT_B*/) > 0 && getVariable(0xE98FC0CBL /*_Destroy_Leg_BL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= -90 && target != null && getAngleToTarget(target) >= -179 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xD9C3A034L /*AI_AT_B*/))) {
				if (changeState(state -> Shield_Attack_BL(0.2)))
					return;
			}
		}
		if (getVariable(0xD9C3A034L /*AI_AT_B*/) > 0 && getVariable(0x617F8E5CL /*_Destroy_Leg_BR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0x2F82561FL /*AI_AT_B_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0x86811C0BL /*AI_AT_B_MaxDistance*/) && target != null && getAngleToTarget(target) <= 180 && target != null && getAngleToTarget(target) >= 90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xD9C3A034L /*AI_AT_B*/))) {
				if (changeState(state -> Shield_Attack_BR(0.2)))
					return;
			}
		}
		if (getVariable(0xA971ACB7L /*AI_AT_Swing_F*/) > 0 && getVariable(0x17241B06L /*_Destroy_Leg_FL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 0 && target != null && getAngleToTarget(target) >= -90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xA971ACB7L /*AI_AT_Swing_F*/))) {
				if (changeState(state -> Shield_Attack_Swing_FL(0.2)))
					return;
			}
		}
		if (getVariable(0xA971ACB7L /*AI_AT_Swing_F*/) > 0 && getVariable(0xD24BF3ECL /*_Destroy_Leg_FR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 90 && target != null && getAngleToTarget(target) >= 0 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0xA971ACB7L /*AI_AT_Swing_F*/))) {
				if (changeState(state -> Shield_Attack_Swing_FR(0.2)))
					return;
			}
		}
		if (getVariable(0x64FBE53FL /*AI_AT_F*/) > 0 && getVariable(0x17241B06L /*_Destroy_Leg_FL_Count*/) == 0 && getVariable(0xD24BF3ECL /*_Destroy_Leg_FR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 90 && target != null && getAngleToTarget(target) >= -90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x64FBE53FL /*AI_AT_F*/))) {
				if (changeState(state -> Shield_Attack_FLR(0.2)))
					return;
			}
		}
		if (getVariable(0x64FBE53FL /*AI_AT_F*/) > 0 && getVariable(0x17241B06L /*_Destroy_Leg_FL_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 0 && target != null && getAngleToTarget(target) >= -90 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x64FBE53FL /*AI_AT_F*/))) {
				if (changeState(state -> Shield_Attack_FL(0.2)))
					return;
			}
		}
		if (getVariable(0x64FBE53FL /*AI_AT_F*/) > 0 && getVariable(0xD24BF3ECL /*_Destroy_Leg_FR_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3 && target != null && getDistanceToTarget(target) >= getVariable(0xDB78721CL /*AI_AT_F_MinDistance*/) && target != null && getDistanceToTarget(target) <= getVariable(0xC6EABA97L /*AI_AT_F_MaxDistance*/) && target != null && getAngleToTarget(target) <= 90 && target != null && getAngleToTarget(target) >= 0 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(getVariable(0x64FBE53FL /*AI_AT_F*/))) {
				if (changeState(state -> Shield_Attack_FR(0.2)))
					return;
			}
		}
		if (getVariable(0x4BCE222EL /*AI_AT_Meteo*/) > 0 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Shield_Attack_Meteo(0.2)))
				return;
		}
		changeState(state -> Shield_Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(5)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 5000) {
			if (changeState(state -> TargetLost(5)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> TargetChange_Logic(0.2)))
				return;
		}
		if (changeState(state -> Attack_Logic(0.2)))
			return;
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Shield_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x26A8AA20L /*Shield_Battle_Wait*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(5)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 5000) {
			if (changeState(state -> TargetLost(5)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Shield_TargetChange_Logic(0.2)))
				return;
		}
		if (changeState(state -> Shield_Attack_Logic(0.2)))
			return;
		doAction(600051306L /*SHIELD_BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Shield_Battle_Wait(blendTime), 2000));
	}

	protected void Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE70D4D89L /*Die_Logic*/);
		getObjects(EAIFindTargetType.Sibling, object -> getTargetCharacterKey(object) == 23769).forEach(consumer -> consumer.getAi().Die(getActor(), null));
		changeState(state -> Before_Die(blendTime));
	}

	protected void Before_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB1B32239L /*Before_Die*/);
		doAction(1174134199L /*BEFORE_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Before_Die(blendTime), 10000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD43BC680L /*Delete_Die*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_OPIN_TIMEOUT");
		scheduleState(state -> Delete_Die_End(blendTime), 1000);
	}

	protected void Delete_Die_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x687CDE97L /*Delete_Die_End*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die_End(blendTime), 1000));
	}

	protected void Attack_FLR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF6767E56L /*Attack_FLR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == 23770 || getTargetCharacterKey(object) == 23771)).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(2908231930L /*ATTACK_FLR*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> (getTargetCharacterKey(object) == 23770 || getTargetCharacterKey(object) == 23771)).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> TargetChange_Logic(blendTime));
		});
	}

	protected void Attack_FL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6D0B68F7L /*Attack_FL*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23770).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(506926446L /*ATTACK_FL*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23770).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> TargetChange_Logic(blendTime));
		});
	}

	protected void Attack_FR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x43894663L /*Attack_FR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23771).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(1288820204L /*ATTACK_FR*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23771).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> TargetChange_Logic(blendTime));
		});
	}

	protected void Attack_Swing_FL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xED5826B6L /*Attack_Swing_FL*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23770).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(2962492673L /*ATTACK_SWING_FL*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Swing_FR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF6382CE6L /*Attack_Swing_FR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23771).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(3944142103L /*ATTACK_SWING_FR*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_BLR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9681B877L /*Attack_BLR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == 23772 || getTargetCharacterKey(object) == 23773)).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(2117244285L /*ATTACK_BLR*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> (getTargetCharacterKey(object) == 23772 || getTargetCharacterKey(object) == 23773)).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> TargetChange_Logic(blendTime));
		});
	}

	protected void Attack_BL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x726EB3F0L /*Attack_BL*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23773).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(2002561103L /*ATTACK_BL*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23773).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> TargetChange_Logic(blendTime));
		});
	}

	protected void Attack_BR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFF54A07FL /*Attack_BR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23772).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(3575252428L /*ATTACK_BR*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23772).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> TargetChange_Logic(blendTime));
		});
	}

	protected void Attack_Swing_BL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x16BE8F42L /*Attack_Swing_BL*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23773).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(1468029054L /*ATTACK_SWING_BL*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Swing_BR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF7A021D5L /*Attack_Swing_BR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23772).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(3789339111L /*ATTACK_SWING_BR*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Breath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9D9470D7L /*Attack_Breath*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3779359920L /*ATTACK_BREATH*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Meteo(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBBBADD74L /*Attack_Meteo*/);
		clearAggro(true);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) > getVariable(0x57840EA7L /*AI_AT_Meteo_MinDistance*/) && getDistanceToTarget(object) < getVariable(0x1C611EC7L /*AI_AT_Meteo_MaxDistance*/))) {
			if (changeState(state -> Attack_Meteo_Ing(0.2)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Attack_Meteo_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD24D56ABL /*Attack_Meteo_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3026686317L /*ATTACK_METEO*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Meteo_F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x82BC081DL /*Attack_Meteo_F*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1023566379L /*ATTACK_METEO_F*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Meteo_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x339C8399L /*Attack_Meteo_B*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1935348943L /*ATTACK_METEO_B*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Meteo_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6C29FCD0L /*Attack_Meteo_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3116857117L /*ATTACK_METEO_L*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Meteo_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC34DD7A3L /*Attack_Meteo_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3090923401L /*ATTACK_METEO_R*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4A9D8D69L /*Attack_Summon*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4064546250L /*ATTACK_SUMMON_START*/, blendTime, onDoActionEnd -> {
			setVariable(0x9BAF2A6DL /*_SpiderAttackCount*/, getVariable(0x9BAF2A6DL /*_SpiderAttackCount*/) - 1);
			changeState(state -> TargetChange_Logic(blendTime));
		});
	}

	protected void Parent_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8E40A930L /*Parent_Die*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0x714FD77AL /*_Parent_Die*/, 1);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().Parent_Die(getActor(), null));
		doAction(4064546250L /*ATTACK_SUMMON_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Earthquake(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x61BA060L /*Attack_Earthquake*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(855488562L /*ATTACK_EARTHQUAKE_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Shockwave(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB470A88L /*Attack_Shockwave*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4154892165L /*ATTACK_SHOCKWAVE*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Energy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA0C190B2L /*Attack_Energy*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23780).forEach(consumer -> consumer.getAi().HiddenTet_On(getActor(), null));
		setVariable(0xAA78D096L /*_SpecialAttack_Count*/, 0);
		doAction(3291181177L /*ATTACK_ENEERGY_START*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23780).forEach(consumer -> consumer.getAi().HiddenTet_Off(getActor(), null));
			changeState(state -> TargetChange_Logic(blendTime));
		});
	}

	protected void Attack_Spear_F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x711259DDL /*Attack_Spear_F*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3860869198L /*ATTACK_SPEAR_F*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Spear_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6AADD519L /*Attack_Spear_B*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(172509615L /*ATTACK_SPEAR_B*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Spear_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDD89952CL /*Attack_Spear_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3335721559L /*ATTACK_SPEAR_L*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Spear_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFBEA9048L /*Attack_Spear_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4059767312L /*ATTACK_SPEAR_R*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_FLR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAF17F5DBL /*Shield_Attack_FLR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == 23770 || getTargetCharacterKey(object) == 23771)).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(827109385L /*SHIELD_ATTACK_FLR*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> (getTargetCharacterKey(object) == 23770 || getTargetCharacterKey(object) == 23771)).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> Shield_TargetChange_Logic(blendTime));
		});
	}

	protected void Shield_Attack_FL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE47AA484L /*Shield_Attack_FL*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23770).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(1838120870L /*SHIELD_ATTACK_FL*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23770).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> Shield_TargetChange_Logic(blendTime));
		});
	}

	protected void Shield_Attack_FR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFC970E5FL /*Shield_Attack_FR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23771).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(2175995108L /*SHIELD_ATTACK_FR*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23771).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> Shield_TargetChange_Logic(blendTime));
		});
	}

	protected void Shield_Attack_Swing_FL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5112B77FL /*Shield_Attack_Swing_FL*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23770).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(3321178633L /*SHIELD_ATTACK_SWING_FL*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Swing_FR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x33B1109AL /*Shield_Attack_Swing_FR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23771).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(160989162L /*SHIELD_ATTACK_SWING_FR*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_BLR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCFB13A42L /*Shield_Attack_BLR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == 23772 || getTargetCharacterKey(object) == 23773)).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(3166372881L /*SHIELD_ATTACK_BLR*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> (getTargetCharacterKey(object) == 23772 || getTargetCharacterKey(object) == 23773)).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> Shield_TargetChange_Logic(blendTime));
		});
	}

	protected void Shield_Attack_BL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7E98DB4CL /*Shield_Attack_BL*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23773).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(1540781820L /*SHIELD_ATTACK_BL*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23773).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> Shield_TargetChange_Logic(blendTime));
		});
	}

	protected void Shield_Attack_BR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1AF340FCL /*Shield_Attack_BR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23772).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(3061329221L /*SHIELD_ATTACK_BR*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23772).forEach(consumer -> consumer.getAi().Avoid_Off(getActor(), null));
			changeState(state -> Shield_TargetChange_Logic(blendTime));
		});
	}

	protected void Shield_Attack_Swing_BL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBAC818DAL /*Shield_Attack_Swing_BL*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23773).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(2990351747L /*SHIELD_ATTACK_SWING_BL*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Swing_BR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCA2ED059L /*Shield_Attack_Swing_BR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Monster, object -> getTargetCharacterKey(object) == 23772).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(923599723L /*SHIELD_ATTACK_SWING_BR*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Breath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xACB015ACL /*Shield_Attack_Breath*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(119086906L /*SHIELD_ATTACK_BREATH*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Meteo(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x232048FDL /*Shield_Attack_Meteo*/);
		clearAggro(true);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) > getVariable(0x57840EA7L /*AI_AT_Meteo_MinDistance*/) && getDistanceToTarget(object) < getVariable(0x1C611EC7L /*AI_AT_Meteo_MaxDistance*/))) {
			if (changeState(state -> Shield_Attack_Meteo_Ing(0.2)))
				return;
		}
		changeState(state -> Shield_Battle_Wait(blendTime));
	}

	protected void Shield_Attack_Meteo_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6BE5A43DL /*Shield_Attack_Meteo_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(110544128L /*SHIELD_ATTACK_METEO*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Meteo_F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB74A21CCL /*Shield_Attack_Meteo_F*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3626373950L /*SHIELD_ATTACK_METEO_F*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Meteo_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9AC5F16BL /*Shield_Attack_Meteo_B*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2337956105L /*SHIELD_ATTACK_METEO_B*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Meteo_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x919F2D6CL /*Shield_Attack_Meteo_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1220105898L /*SHIELD_ATTACK_METEO_L*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Meteo_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3BB31DE7L /*Shield_Attack_Meteo_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4252125470L /*SHIELD_ATTACK_METEO_R*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFBA56ED3L /*Shield_Attack_Summon*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1323585509L /*SHIELD_ATTACK_SUMMON_START*/, blendTime, onDoActionEnd -> {
			setVariable(0x9BAF2A6DL /*_SpiderAttackCount*/, getVariable(0x9BAF2A6DL /*_SpiderAttackCount*/) - 1);
			changeState(state -> Shield_TargetChange_Logic(blendTime));
		});
	}

	protected void Shield_Attack_Earthquake(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDC1DE135L /*Shield_Attack_Earthquake*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3394494391L /*SHIELD_ATTACK_EARTHQUAKE_START*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Shockwave(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9919CE7FL /*Shield_Attack_Shockwave*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3765705481L /*SHIELD_ATTACK_SHOCKWAVE*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Energy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x395BB74L /*Shield_Attack_Energy*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23780).forEach(consumer -> consumer.getAi().HiddenTet_On(getActor(), null));
		setVariable(0xAA78D096L /*_SpecialAttack_Count*/, 0);
		doAction(2636218769L /*SHIELD_ATTACK_ENEERGY_START*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Child, object -> getTargetCharacterKey(object) == 23780).forEach(consumer -> consumer.getAi().HiddenTet_Off(getActor(), null));
			changeState(state -> Shield_TargetChange_Logic(blendTime));
		});
	}

	protected void Shield_Attack_Spear_F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6A0E2C40L /*Shield_Attack_Spear_F*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3364068127L /*SHIELD_ATTACK_SPEAR_F*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Spear_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x487FA6E6L /*Shield_Attack_Spear_B*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4181285436L /*SHIELD_ATTACK_SPEAR_B*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Spear_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x22E4468FL /*Shield_Attack_Spear_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3490909331L /*SHIELD_ATTACK_SPEAR_L*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Shield_Attack_Spear_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x12B44CF2L /*Shield_Attack_Spear_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2206881337L /*SHIELD_ATTACK_SPEAR_R*/, blendTime, onDoActionEnd -> changeState(state -> Shield_TargetChange_Logic(blendTime)));
	}

	protected void Combination_Attack_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFF21E037L /*Combination_Attack_Summon*/);
		doAction(1323585509L /*SHIELD_ATTACK_SUMMON_START*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Wait(blendTime)));
	}

	protected void Avoid_Attack_FLR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBAEC2DF1L /*Avoid_Attack_FLR*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		doAction(827109385L /*SHIELD_ATTACK_FLR*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_TargetChange_Logic(blendTime)));
	}

	protected void Avoid_Attack_FL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x320CBF73L /*Avoid_Attack_FL*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		doAction(1838120870L /*SHIELD_ATTACK_FL*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_TargetChange_Logic(blendTime)));
	}

	protected void Avoid_Attack_FR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1B271953L /*Avoid_Attack_FR*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		doAction(2175995108L /*SHIELD_ATTACK_FR*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_TargetChange_Logic(blendTime)));
	}

	protected void Avoid_Attack_Swing_FL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB7583947L /*Avoid_Attack_Swing_FL*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		doAction(3321178633L /*SHIELD_ATTACK_SWING_FL*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_TargetChange_Logic(blendTime)));
	}

	protected void Avoid_Attack_Swing_FR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x58289058L /*Avoid_Attack_Swing_FR*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		doAction(160989162L /*SHIELD_ATTACK_SWING_FR*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_TargetChange_Logic(blendTime)));
	}

	protected void Avoid_Attack_BLR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5B760091L /*Avoid_Attack_BLR*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		doAction(3166372881L /*SHIELD_ATTACK_BLR*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_TargetChange_Logic(blendTime)));
	}

	protected void Avoid_Attack_BL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x74300EF5L /*Avoid_Attack_BL*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		doAction(1540781820L /*SHIELD_ATTACK_BL*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_TargetChange_Logic(blendTime)));
	}

	protected void Avoid_Attack_BR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xED270B0DL /*Avoid_Attack_BR*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		doAction(3061329221L /*SHIELD_ATTACK_BR*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_TargetChange_Logic(blendTime)));
	}

	protected void Avoid_Attack_Swing_BL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7CD05739L /*Avoid_Attack_Swing_BL*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		doAction(2990351747L /*SHIELD_ATTACK_SWING_BL*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_TargetChange_Logic(blendTime)));
	}

	protected void Avoid_Attack_Swing_BR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x17D14E97L /*Avoid_Attack_Swing_BR*/);
		if (isTargetLost()) {
			if (changeState(state -> Avoid_Wait(blendTime)))
				return;
		}
		doAction(923599723L /*SHIELD_ATTACK_SWING_BR*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_TargetChange_Logic(blendTime)));
	}

	protected void Destroy_Leg_FL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD86EF6F1L /*Destroy_Leg_FL*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().Broken_Leg(getActor(), null));
		getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == 23771 || getTargetCharacterKey(object) == 23772 || getTargetCharacterKey(object) == 23773)).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(55109930L /*DESTROY_LEG_FL*/, blendTime, onDoActionEnd -> {
			setVariable(0xD9492841L /*_Destroy_Leg_Count*/, getVariable(0xD9492841L /*_Destroy_Leg_Count*/) + 1);
			setVariable(0x17241B06L /*_Destroy_Leg_FL_Count*/, 1);
			scheduleState(state -> Destroy_Logic(blendTime), 3000);
		});
	}

	protected void Destroy_Leg_FR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5E23DE13L /*Destroy_Leg_FR*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().Broken_Leg(getActor(), null));
		getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == 23770 || getTargetCharacterKey(object) == 23772 || getTargetCharacterKey(object) == 23773)).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(3655425039L /*DESTROY_LEG_FR*/, blendTime, onDoActionEnd -> {
			setVariable(0xD9492841L /*_Destroy_Leg_Count*/, getVariable(0xD9492841L /*_Destroy_Leg_Count*/) + 1);
			setVariable(0xD24BF3ECL /*_Destroy_Leg_FR_Count*/, 1);
			scheduleState(state -> Destroy_Logic(blendTime), 3000);
		});
	}

	protected void Destroy_Leg_BL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7588F8ACL /*Destroy_Leg_BL*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().Broken_Leg(getActor(), null));
		getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == 23770 || getTargetCharacterKey(object) == 23771 || getTargetCharacterKey(object) == 23773)).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(325444721L /*DESTROY_LEG_BL*/, blendTime, onDoActionEnd -> {
			setVariable(0xD9492841L /*_Destroy_Leg_Count*/, getVariable(0xD9492841L /*_Destroy_Leg_Count*/) + 1);
			setVariable(0x617F8E5CL /*_Destroy_Leg_BR_Count*/, 1);
			scheduleState(state -> Destroy_Logic(blendTime), 3000);
		});
	}

	protected void Destroy_Leg_BR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD75B656CL /*Destroy_Leg_BR*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().Broken_Leg(getActor(), null));
		getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == 23770 || getTargetCharacterKey(object) == 23771 || getTargetCharacterKey(object) == 23772)).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(674820112L /*DESTROY_LEG_BR*/, blendTime, onDoActionEnd -> {
			setVariable(0xD9492841L /*_Destroy_Leg_Count*/, getVariable(0xD9492841L /*_Destroy_Leg_Count*/) + 1);
			setVariable(0xE98FC0CBL /*_Destroy_Leg_BL_Count*/, 1);
			scheduleState(state -> Destroy_Logic(blendTime), 3000);
		});
	}

	protected void Destroy_3Leg_F_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2BCBD1A8L /*Destroy_3Leg_F_Start*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().Broken_Leg(getActor(), null));
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(1978306144L /*DESTROY_3LEG_F*/, blendTime, onDoActionEnd -> scheduleState(state -> Destroy_3Leg_F(blendTime), 3000));
	}

	protected void Destroy_3Leg_F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x37932FA7L /*Destroy_3Leg_F*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().Broken_Leg(getActor(), null));
		doAction(4082480723L /*DESTROY_3LEG_F_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Destroy_3Leg_F(blendTime), 3000));
	}

	protected void Destroy_3Leg_B_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4C58FF6CL /*Destroy_3Leg_B_Start*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().Broken_Leg(getActor(), null));
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(4240899037L /*DESTROY_3LEG_B*/, blendTime, onDoActionEnd -> scheduleState(state -> Destroy_3Leg_B(blendTime), 3000));
	}

	protected void Destroy_3Leg_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x13A64C77L /*Destroy_3Leg_B*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().Broken_Leg(getActor(), null));
		doAction(784792064L /*DESTROY_3LEG_B_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Destroy_3Leg_B(blendTime), 3000));
	}

	protected void Destroy_Shield(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x123D7B0EL /*Destroy_Shield*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().Broken_Shield(getActor(), null));
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Avoid_On(getActor(), null));
		doAction(3488969188L /*DESTROY_SHIELD*/, blendTime, onDoActionEnd -> {
			setVariable(0x307DB559L /*_Destroy_Shield_Count*/, 1);
			scheduleState(state -> Destroy_Logic(blendTime), 3000);
		});
	}

	protected void Destroy_HiddenPart(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE0BE280DL /*Destroy_HiddenPart*/);
		doAction(482854026L /*DESTROY_HIDDENTET*/, blendTime, onDoActionEnd -> scheduleState(state -> TargetLost(blendTime), 3000));
	}

	protected void Destroy_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x35AF6D9BL /*Destroy_Logic*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0xCF959574L /*_SummonShield_Count*/) == 1 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) == 1) {
			if (changeState(state -> Summon_Shield(0.3)))
				return;
		}
		if (getVariable(0x307DB559L /*_Destroy_Shield_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) == 1) {
			if (changeState(state -> Summon(0.3)))
				return;
		}
		if (getVariable(0x307DB559L /*_Destroy_Shield_Count*/) == 1 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) == 0) {
			if (changeState(state -> Summon(0.3)))
				return;
		}
		if (getVariable(0x307DB559L /*_Destroy_Shield_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) == 2) {
			if (changeState(state -> Avoid_Wait(0.3)))
				return;
		}
		if (getVariable(0x307DB559L /*_Destroy_Shield_Count*/) == 0 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) == 3) {
			if (changeState(state -> Avoid_Wait(0.3)))
				return;
		}
		if (getVariable(0x307DB559L /*_Destroy_Shield_Count*/) == 1 && getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3) {
			if (changeState(state -> Avoid_Wait(0.3)))
				return;
		}
		changeState(state -> Destroy_Logic(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x84794EB4L /*Wait2*/) {
			if (changeState(state -> Search_Enemy(0.2)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Revival(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Revival(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Delete_Die(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Delete_Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Die(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult DestroyLeg1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xD9492841L /*_Destroy_Leg_Count*/) == 2) {
			if (changeState(state -> Destroy_3Leg_F_Start(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (changeState(state -> Destroy_Leg_FL(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult DestroyLeg2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xD9492841L /*_Destroy_Leg_Count*/) == 2) {
			if (changeState(state -> Destroy_3Leg_F_Start(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (changeState(state -> Destroy_Leg_FR(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult DestroyLeg3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xD9492841L /*_Destroy_Leg_Count*/) == 2) {
			if (changeState(state -> Destroy_3Leg_B_Start(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (changeState(state -> Destroy_Leg_BL(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult DestroyLeg4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xD9492841L /*_Destroy_Leg_Count*/) == 2) {
			if (changeState(state -> Destroy_3Leg_B_Start(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (changeState(state -> Destroy_Leg_BR(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult DestroyShield(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Destroy_Shield(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Order(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xD9492841L /*_Destroy_Leg_Count*/) < 3) {
			if (changeState(state -> Combination_Attack_Summon(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Damage_Tet(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if(Rnd.getChance(30)) {
			if (changeState(state -> Destroy_HiddenPart(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleAwakeGuildBoss(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Start_Action(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTalkToDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x109D3080L /*Talk_Wait*/) {
			if (changeState(state -> Start_Action(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
