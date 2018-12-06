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
@IAIName("blackjinny_boss")
public class Ai_blackjinny_boss extends CreatureAI {
	public Ai_blackjinny_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x5CE1B16EL /*_GeneralCount*/, 2);
		setVariable(0x5749908DL /*_JumpCount*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, 0);
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, 0);
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 1000));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action_Logic(blendTime), 1500));
	}

	protected void Start_Action_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF1ED1C65L /*Start_Action_Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1700 && isCreatureVisible(object, false))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		scheduleState(state -> Wait(blendTime), 100);
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random(0.4)))
				return;
		}
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 120000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 100));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x5CE1B16EL /*_GeneralCount*/, 2);
		setVariable(0x5749908DL /*_JumpCount*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getPartyMembersCount()== 0 && target != null && getDistanceToTarget(target) < 1000 && getVariable(0x3F487035L /*_Hp*/) <= 75 && getVariable(0x5CE1B16EL /*_GeneralCount*/) == 2) {
			if (changeState(state -> Attack_General(0.3)))
				return;
		}
		if (getPartyMembersCount()== 0 && target != null && getDistanceToTarget(target) < 1000 && getVariable(0x3F487035L /*_Hp*/) <= 50 && getVariable(0x5CE1B16EL /*_GeneralCount*/) == 1) {
			if (changeState(state -> Attack_General(0.3)))
				return;
		}
		if (getPartyMembersCount()== 0 && target != null && getDistanceToTarget(target) < 1000 && getVariable(0x3F487035L /*_Hp*/) <= 25 && getPartyMembersCount()< 1) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_General(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= -35 && target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Turn_L(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 35 && target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Turn_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 1200 && target != null && getAngleToTarget(target) >= -45 && target != null && getAngleToTarget(target) <= 45) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_StoneWave(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 400 && target != null && getDistanceToTarget(target) < 1200) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Pstamp(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Stamp_LK(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Stamp_RK(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Kick(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Swing_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Swing_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Attack_Stamp_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Attack_Stamp_L(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800) {
			if (changeState(state -> HighSpeed_Chaser(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 900) {
			if (changeState(state -> HighSpeed_Chaser(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 250) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Kick(0.3)))
					return;
			}
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
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
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> HighSpeed_Chaser_Stop(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 700) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump_A(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Kick(0.3)))
					return;
			}
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> HighSpeed_Chaser(blendTime), 100)));
	}

	protected void HighSpeed_Chaser_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD4BB14E7L /*HighSpeed_Chaser_Stop*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3949145239L /*BATTLE_RUN_STOP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Move_BackStep(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x257A4497L /*Move_BackStep*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 250) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(1984320902L /*MOVE_WALK_BACK*/, blendTime, onDoActionEnd -> escape(1500, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_BackStep(blendTime), 100)));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2737950888L /*DELETE_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	protected void Attack_Turn_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5404099FL /*Attack_Turn_L*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(748625070L /*ATTACK_TURN_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9E7DE5BDL /*Attack_Turn_R*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3533086974L /*ATTACK_TURN_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Swing_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1E76385FL /*Attack_Swing_L*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getVariable(0x5749908DL /*_JumpCount*/) < 6) {
			setVariable(0x5749908DL /*_JumpCount*/, getVariable(0x5749908DL /*_JumpCount*/) + 1);
		}
		doAction(3548371962L /*ATTACK_SWING_L*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Swing_L_Logic(blendTime)));
	}

	protected void Attack_Swing_L_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xFA0217B1L /*Attack_Swing_L_Logic*/);
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Swing_R(0.3)))
					return;
			}
		}
		if (changeState(state -> Battle_Wait(0.3)))
			return;
		scheduleState(state -> Battle_Wait(blendTime), 500);
	}

	protected void Attack_Swing_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x24DBCBF3L /*Attack_Swing_R*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getVariable(0x5749908DL /*_JumpCount*/) < 6) {
			setVariable(0x5749908DL /*_JumpCount*/, getVariable(0x5749908DL /*_JumpCount*/) + 1);
		}
		doAction(2462351423L /*ATTACK_SWING_R*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Swing_R_Logic(blendTime)));
	}

	protected void Attack_Swing_R_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x660963FEL /*Attack_Swing_R_Logic*/);
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Swing_L(0.3)))
					return;
			}
		}
		if (changeState(state -> Battle_Wait(0.3)))
			return;
		scheduleState(state -> Battle_Wait(blendTime), 500);
	}

	protected void Attack_Stamp_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD757DFDCL /*Attack_Stamp_L*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getVariable(0x5749908DL /*_JumpCount*/) < 6) {
			setVariable(0x5749908DL /*_JumpCount*/, getVariable(0x5749908DL /*_JumpCount*/) + 1);
		}
		doAction(630668866L /*ATTACK_STAMP_LA*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Stamp_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6AB4A774L /*Attack_Stamp_R*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getVariable(0x5749908DL /*_JumpCount*/) < 6) {
			setVariable(0x5749908DL /*_JumpCount*/, getVariable(0x5749908DL /*_JumpCount*/) + 1);
		}
		doAction(2378483406L /*ATTACK_STAMP_RA*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Stamp_LK(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB311A72BL /*Attack_Stamp_LK*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2700809327L /*ATTACK_STAMP_LK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Stamp_LK_Logic(blendTime)));
	}

	protected void Attack_Stamp_LK_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x30D960FL /*Attack_Stamp_LK_Logic*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x3F487035L /*_Hp*/) <= 50 && target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Attack_Kick(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Stamp_R(0.3)))
					return;
			}
		}
		scheduleState(state -> Battle_Wait(blendTime), 500);
	}

	protected void Attack_Stamp_RK(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE25D5BE2L /*Attack_Stamp_RK*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(50284844L /*ATTACK_STAMP_RK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Stamp_RK_Logic(blendTime)));
	}

	protected void Attack_Stamp_RK_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x77431A3L /*Attack_Stamp_RK_Logic*/);
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Stamp_L(0.3)))
					return;
			}
		}
		scheduleState(state -> Battle_Wait(blendTime), 500);
	}

	protected void Attack_Kick(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC369511CL /*Attack_Kick*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2028143634L /*ATTACK_KICK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Kick_Logic(blendTime)));
	}

	protected void Attack_Kick_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x72F4F10EL /*Attack_Kick_Logic*/);
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Stamp_LK(0.3)))
					return;
			}
		}
		scheduleState(state -> Battle_Wait(blendTime), 500);
	}

	protected void Attack_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC8BC3ABDL /*Attack_Jump*/);
		doAction(2705621134L /*ATTACK_JUMP*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_Jump_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE3F16BAFL /*Attack_Jump_A*/);
		setVariable(0x5749908DL /*_JumpCount*/, 0);
		doAction(1056951149L /*ATTACK_JUMP_A*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Charge(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xADCCD7F7L /*Attack_Charge*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2219424680L /*ATTACK_CHARGE_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Charge1(blendTime)));
	}

	protected void Attack_Charge1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x29A3AF09L /*Attack_Charge1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2076125257L /*ATTACK_CHARGE_B*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Charge2(blendTime)));
	}

	protected void Attack_Charge2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x19FAB99CL /*Attack_Charge2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1426502847L /*ATTACK_CHARGE_C*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Charge_Logic(blendTime)));
	}

	protected void Attack_Charge_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE31CAAA2L /*Attack_Charge_Logic*/);
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump(0.3)))
					return;
			}
		}
		scheduleState(state -> Battle_Wait(blendTime), 500);
	}

	protected void Attack_StoneWave(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2A078E8AL /*Attack_StoneWave*/);
		doAction(2804644807L /*ATTACK_STONEWAVE*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Pstamp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA6188348L /*Attack_Pstamp*/);
		doAction(3377977745L /*ATTACK_PSTAMP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_General(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x88DD2B64L /*Attack_General*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(901362947L /*ATTACK_GENERAL_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_General_Chose_Logic(blendTime)));
	}

	protected void Attack_General_Chose_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6277526EL /*Attack_General_Chose_Logic*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_General1_B(0.3)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_General1_C(0.3)))
				return;
		}
		scheduleState(state -> Attack_General1_A(blendTime), 500);
	}

	protected void Attack_General1_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC101CC53L /*Attack_General1_A*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(989846155L /*ATTACK_GENERAL_B_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_General2(blendTime)));
	}

	protected void Attack_General1_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x106CF070L /*Attack_General1_B*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3845171175L /*ATTACK_GENERAL_B_2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_General2(blendTime)));
	}

	protected void Attack_General1_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2E658D3EL /*Attack_General1_C*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(4133664558L /*ATTACK_GENERAL_B_3*/, blendTime, onDoActionEnd -> changeState(state -> Attack_General2(blendTime)));
	}

	protected void Attack_General2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD70732B3L /*Attack_General2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2409930781L /*ATTACK_GENERAL_C*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_General_Logic(blendTime), 1000));
	}

	protected void Attack_General_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4D70DFD1L /*Attack_General_Logic*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Attack_Enemy(getActor(), null));
							if (getVariable(0x5CE1B16EL /*_GeneralCount*/) > 0) {
				setVariable(0x5CE1B16EL /*_GeneralCount*/, getVariable(0x5CE1B16EL /*_GeneralCount*/) - 1);
			}
			scheduleState(state -> Battle_Wait(blendTime), 500);
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0xD5712181L /*WalkRandom*/)) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
