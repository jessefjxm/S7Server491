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
@IAIName("trolla_boss")
public class Ai_trolla_boss extends CreatureAI {
	public Ai_trolla_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 100);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x5AFA508EL /*_LegStunCount*/, 0);
		setVariable(0xE4C9AD93L /*_HeadStunCount*/, 0);
		setVariable(0x76D19707L /*_DownLeg_L_Count*/, 0);
		setVariable(0x17ADE38BL /*_DownLeg_R_Count*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0xEF7AE808L /*_RoarCount*/, 0);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, 0);
		setVariable(0xB5B81E5EL /*_CannonCount*/, 0);
		setVariable(0xADE8C2ADL /*_NotRecovery*/, 0);
		setVariable(0x22FE74AL /*_AttackBerserk*/, 0);
		setVariable(0xCC380C54L /*_BerserkCount*/, 3);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x3BDC5BFCL /*_StandUpCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, 0);
		setVariable(0xF75130C5L /*_UnSummon_IngTime*/, 0);
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, 0);
		setVariable(0x1E2360D6L /*_StunDelay_StartTime*/, 0);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, 0);
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, 0);
		doAction(539193115L /*STONE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Statue(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait_L2(blendTime)))
					return;
			}
		}
		if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait_R2(blendTime)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait_30(blendTime)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 70 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait_70(blendTime)))
					return;
			}
		}
		if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(blendTime)))
					return;
			}
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Statue(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x59ACE9D4L /*Statue*/);
		doAction(539193115L /*STONE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Statue(blendTime), 1000));
	}

	protected void Notifier_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2635639AL /*Notifier_Logic*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_TROLLBOSS_SPAWN");
		changeState(state -> Start_Action(blendTime));
	}

	protected void Notifier_Logic_Test(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x18C0233FL /*Notifier_Logic_Test*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_TROLLBOSS_SPAWN");
		changeState(state -> Start_Action(blendTime));
	}

	protected void Start_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9D7E92CDL /*Start_Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 900000) {
			if (changeState(state -> Start_Action(0.3)))
				return;
		}
		doAction(539193115L /*STONE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Wait(blendTime), 1000));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x5B77CE65L /*_UnSummon_StartTime*/, getTime());
		doAction(2822613715L /*STONE_WAIT1*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Attack(blendTime), 1000));
	}

	protected void Start_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x80DF632L /*Start_Attack*/);
		doAction(3627573659L /*STONE_WAIT2*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0xF75130C5L /*_UnSummon_IngTime*/, getTime());
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0xF75130C5L /*_UnSummon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Stone_Curse(0.3)))
				return;
		}
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 3600000 && getVariable(0x3F487035L /*_Hp*/) > 50) {
			if (changeState(state -> Stone_Curse(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3000)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000 + Rnd.get(-500,500)));
	}

	protected void Wait1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x17E06D10L /*Wait1*/);
		doAction(1569894142L /*WAIT_1*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3000)) {
			if (changeState(state -> Detect_Target(blendTime)))
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
		}, onExit -> scheduleState(state -> Move_Random_Stop(blendTime), 1000)));
	}

	protected void Move_Random_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5048BF2FL /*Move_Random_Stop*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3000)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		doAction(2741208001L /*WALK_STOP*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF06CDECAL /*LostTarget*/);
		changeState(state -> Logic(blendTime));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Recovery(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x190B95ACL /*Recovery*/);
		doAction(2705309442L /*RECOVERY*/, blendTime, onDoActionEnd -> changeState(state -> Weapon_In(blendTime)));
	}

	protected void Weapon_In(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x626F781FL /*Weapon_In*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 5) {
			if (changeState(state -> FailFindPathToTarget(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Stone_Curse(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7619256L /*Stone_Curse*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_TROLLBOSS_TIMEOUT");
		doAction(3044684171L /*STONE_CURSE*/, blendTime, onDoActionEnd -> changeState(state -> Delete_Die(blendTime)));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 5000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x5AFA508EL /*_LegStunCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0xF75130C5L /*_UnSummon_IngTime*/, getTime());
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0xF75130C5L /*_UnSummon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 3600000 && getVariable(0x3F487035L /*_Hp*/) > 50) {
			if (changeState(state -> Stone_Curse(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0xEF7AE808L /*_RoarCount*/) == 2) {
			if (changeState(state -> Battle_Wait_30(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 70 && getVariable(0xEF7AE808L /*_RoarCount*/) == 1) {
			if (changeState(state -> Battle_Wait_70(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 80 && getVariable(0xCC380C54L /*_BerserkCount*/) == 3) {
			if (changeState(state -> Attack_Berserk(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 70 && getVariable(0xEF7AE808L /*_RoarCount*/) == 0) {
			if (changeState(state -> Attack_Roar_70(0.1)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Left_180(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 160 && target != null && getAngleToTarget(target) <= 179 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Right_180(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= -45 && target != null && getAngleToTarget(target) <= -90 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Left(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 45 && target != null && getAngleToTarget(target) <= 90 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Right(0.3)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Move_Random_Logic(0.1)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 2000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Dash(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) <= 75 && target != null && getDistanceToTarget(target) < 1000 && getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) >= 15 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Broad(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Battle_Wait_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x17C87EDDL /*Battle_Wait_70*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x5AFA508EL /*_LegStunCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0xF75130C5L /*_UnSummon_IngTime*/, getTime());
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0xF75130C5L /*_UnSummon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 3600000 && getVariable(0x3F487035L /*_Hp*/) > 50) {
			if (changeState(state -> Stone_Curse(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0xEF7AE808L /*_RoarCount*/) == 2) {
			if (changeState(state -> Battle_Wait_30(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 50 && getVariable(0xCC380C54L /*_BerserkCount*/) == 2) {
			if (changeState(state -> Attack_Berserk_70(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0xEF7AE808L /*_RoarCount*/) == 1) {
			if (changeState(state -> Attack_Roar_30(0.1)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Left_180_70(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 160 && target != null && getAngleToTarget(target) <= 179 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Right_180_70(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= -45 && target != null && getAngleToTarget(target) <= -90 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Left_70(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 45 && target != null && getAngleToTarget(target) <= 90 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Right_70(0.3)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Move_Random_Logic_70(0.1)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Roll_Str(0.1)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 2000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Dash_70(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) <= 75 && target != null && getDistanceToTarget(target) < 1000 && getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) >= 15 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Broad_70(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal_70(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1_70(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_70(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Run_70(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Run_70(0.3)))
				return;
		}
		doAction(2098656258L /*BATTLE_WAIT_70*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_70(blendTime), 100));
	}

	protected void Battle_Wait_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x23328C00L /*Battle_Wait_30*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x5AFA508EL /*_LegStunCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0xF75130C5L /*_UnSummon_IngTime*/, getTime());
		setVariable(0x5BAFAC82L /*_UnSummon_EndTime*/, getVariable(0xF75130C5L /*_UnSummon_IngTime*/) - getVariable(0x5B77CE65L /*_UnSummon_StartTime*/));
		if (getVariable(0x5BAFAC82L /*_UnSummon_EndTime*/) >= 3600000 && getVariable(0x3F487035L /*_Hp*/) > 50) {
			if (changeState(state -> Stone_Curse(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0xEF7AE808L /*_RoarCount*/) == 2) {
			if (changeState(state -> Battle_Wait_30(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 15 && getVariable(0xCC380C54L /*_BerserkCount*/) == 1) {
			if (changeState(state -> Attack_Berserk_30(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -160 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Left_180_30(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 160 && target != null && getAngleToTarget(target) <= 179 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Right_180_30(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= -45 && target != null && getAngleToTarget(target) <= -90 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Left_30(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= 45 && target != null && getAngleToTarget(target) <= 90 && target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Turn_Right_30(0.3)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Move_Random_Logic_30(0.1)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Roll_Str_30(0.1)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 2000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Dash_30(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) <= 75 && target != null && getDistanceToTarget(target) < 1000 && getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) >= 15 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Broad_30(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal_30(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1_30(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_30(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Run_30(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Run_30(0.3)))
				return;
		}
		doAction(134469941L /*BATTLE_WAIT_30*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_30(blendTime), 100));
	}

	protected void Battle_Wait_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCA1A191AL /*Battle_Wait_R*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(627768267L /*BATTLE_WAIT_R*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Walk_R(blendTime), 1000));
	}

	protected void Battle_Wait_R2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF147DF92L /*Battle_Wait_R2*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if(Rnd.getChance(20)) {
			if (changeState(state -> Move_Random_Logic_R(0.1)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Attack_Normal1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_R(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_R2(blendTime), 100));
	}

	protected void Battle_Wait_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x17A4B6AFL /*Battle_Wait_L*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(701989706L /*BATTLE_WAIT_L*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Walk_L(blendTime), 1000));
	}

	protected void Battle_Wait_L2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7A3D5A63L /*Battle_Wait_L2*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Move_Random_Logic_L(0.1)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal_Logic(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1_Logic(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_L(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Run_L(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Run_L(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_L2(blendTime), 100));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal_Logic(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1_Logic(0.3)))
				return;
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
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Battle_Run_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xCB0461D6L /*Battle_Run_70*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal_Logic(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1_Logic(0.3)))
				return;
		}
		doAction(1562444452L /*BATTLE_RUN_70*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> Battle_Run_70(blendTime), 100)));
	}

	protected void Battle_Run_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xDB02295DL /*Battle_Run_30*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal_Logic(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1_Logic(0.3)))
				return;
		}
		doAction(475961999L /*BATTLE_RUN_30*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> Battle_Run_30(blendTime), 100)));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Attack_Normal1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(4272993707L /*BATTLE_MOVE*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Damage_Head_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA02312D4L /*Damage_Head_Stun*/);
		setVariable(0xE4C9AD93L /*_HeadStunCount*/, 1);
		doAction(205918434L /*DAMAGE_STUN_HEAD_A*/, blendTime, onDoActionEnd -> changeState(state -> Damage_Head_Stun_1(blendTime)));
	}

	protected void Damage_Head_Stun_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x30EFD8AFL /*Damage_Head_Stun_1*/);
		doAction(3600553463L /*DAMAGE_STUN_HEAD_C*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0) {
				if (changeState(state -> Battle_Wait_30(0.3)))
					return;
			}
			if (getVariable(0x3F487035L /*_Hp*/) < 70 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0) {
				if (changeState(state -> Battle_Wait_70(0.3)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0) {
				if (changeState(state -> Battle_Wait_L2(0.3)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_R2(0.3)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Damage_Leg_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB3B2B64EL /*Damage_Leg_Stun*/);
		setVariable(0x5AFA508EL /*_LegStunCount*/, 1);
		setVariable(0x76D19707L /*_DownLeg_L_Count*/, 1);
		setVariable(0x17ADE38BL /*_DownLeg_R_Count*/, 1);
		setVariable(0xB5B81E5EL /*_CannonCount*/, 0);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		doAction(3143853990L /*DAMAGE_STUN_LEG_A*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x3F487035L /*_Hp*/) < 70 && getVariable(0x3BDC5BFCL /*_StandUpCount*/) == 0) {
				if (changeState(state -> Damage_Leg_Stun_1(0.3)))
					return;
			}
			if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0x3BDC5BFCL /*_StandUpCount*/) == 1) {
				if (changeState(state -> Damage_Leg_Stun_1(0.3)))
					return;
			}
			changeState(state -> Attack_Stun_L(blendTime));
		});
	}

	protected void Damage_Leg_Stun_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x92FCEAABL /*Damage_Leg_Stun_1*/);
		setVariable(0x76D19707L /*_DownLeg_L_Count*/, 0);
		setVariable(0x17ADE38BL /*_DownLeg_R_Count*/, 0);
		setVariable(0xE4C9AD93L /*_HeadStunCount*/, 0);
		setVariable(0x3BDC5BFCL /*_StandUpCount*/, getVariable(0x3BDC5BFCL /*_StandUpCount*/) + 1);
		doAction(314985309L /*DAMAGE_STUN_LEG_C*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x3F487035L /*_Hp*/) < 30) {
				if (changeState(state -> Attack_Roar2_30(0.3)))
					return;
			}
			if (getVariable(0x3F487035L /*_Hp*/) < 70) {
				if (changeState(state -> Attack_Roar2_70(0.3)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Damage_Leg_Stun_1_SP(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1B16308EL /*Damage_Leg_Stun_1_SP*/);
		setVariable(0x76D19707L /*_DownLeg_L_Count*/, 0);
		setVariable(0x17ADE38BL /*_DownLeg_R_Count*/, 0);
		doAction(1662815927L /*DAMAGE_STUN_LEG_C_SP*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x3F487035L /*_Hp*/) < 30) {
				if (changeState(state -> Attack_Roar2_30(0.3)))
					return;
			}
			if (getVariable(0x3F487035L /*_Hp*/) < 70) {
				if (changeState(state -> Attack_Roar2_70(0.3)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Damage_Leg_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBFAAE59EL /*Damage_Leg_L*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x76D19707L /*_DownLeg_L_Count*/, 1);
		doAction(2229769531L /*DAMAGE_LEG_L*/, blendTime, onDoActionEnd -> changeState(state -> Down_Leg_L(blendTime)));
	}

	protected void Down_Leg_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB21A4366L /*Down_Leg_L*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3085944822L /*DOWN_LEG_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_L(blendTime)));
	}

	protected void Damage_Leg_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB819C33L /*Damage_Leg_R*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x17ADE38BL /*_DownLeg_R_Count*/, 1);
		doAction(3224818618L /*DAMAGE_LEG_R*/, blendTime, onDoActionEnd -> changeState(state -> Down_Leg_R(blendTime)));
	}

	protected void Down_Leg_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x433A09EAL /*Down_Leg_R*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(4066199635L /*DOWN_LEG_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_R(blendTime)));
	}

	protected void Battle_Run_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xDB53DF41L /*Battle_Run_L*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Battle_Wait_L2(0.3)))
				return;
		}
		doAction(2822890825L /*BATTLE_MOVE_L*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> Battle_Run_L(blendTime), 100)));
	}

	protected void Battle_Run_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xD151883DL /*Battle_Run_R*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Battle_Wait_R2(0.3)))
				return;
		}
		doAction(4211092007L /*BATTLE_MOVE_R*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
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
		}, onExit -> scheduleState(state -> Battle_Run_R(blendTime), 100)));
	}

	protected void Battle_Walk_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x8BF84A4EL /*Battle_Walk_L*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Battle_Wait_L2(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(2822890825L /*BATTLE_MOVE_L*/, blendTime, onDoActionEnd -> escape(2000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_L2(blendTime), 2000)));
	}

	protected void Battle_Walk_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x9226761EL /*Battle_Walk_R*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Battle_Wait_R2(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 10000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(4211092007L /*BATTLE_MOVE_R*/, blendTime, onDoActionEnd -> escape(2000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_R2(blendTime), 2000)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1664053560L /*BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x177CAD44L /*Battle_Turn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1915812192L /*BATTLE_TURN_180_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4FD5675DL /*Battle_Turn_Left_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(991936547L /*BATTLE_TURN_180_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9731EB15L /*Battle_Turn_Left_70*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1861616798L /*BATTLE_TURN_LEFT_70*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB2B3BE77L /*Battle_Turn_Right_70*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1870139560L /*BATTLE_TURN_RIGHT_70*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_180_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA65888CCL /*Battle_Turn_Right_180_70*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2438264659L /*BATTLE_TURN_180_RIGHT_70*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_180_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2D26D438L /*Battle_Turn_Left_180_70*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2351606736L /*BATTLE_TURN_180_LEFT_70*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAF6040BAL /*Battle_Turn_Left_30*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3633526350L /*BATTLE_TURN_LEFT_30*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8DAAB427L /*Battle_Turn_Right_30*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1994016919L /*BATTLE_TURN_RIGHT_30*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Right_180_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x26F3DBCL /*Battle_Turn_Right_180_30*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1408847503L /*BATTLE_TURN_180_RIGHT_30*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Battle_Turn_Left_180_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA7F84DDAL /*Battle_Turn_Left_180_30*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1076388328L /*BATTLE_TURN_180_LEFT_30*/, blendTime, onDoActionEnd -> changeState(state -> Turn_Logic(blendTime)));
	}

	protected void Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x31999313L /*Turn_Logic*/);
		if (getVariable(0x3F487035L /*_Hp*/) < 30) {
			if (changeState(state -> Battle_Wait_30(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 70) {
			if (changeState(state -> Battle_Wait_70(0.3)))
				return;
		}
		if (changeState(state -> Battle_Wait(0.3)))
			return;
		changeState(state -> Turn_Logic(blendTime));
	}

	protected void Move_Random_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2400E763L /*Move_Random_Logic*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Left(0.3)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Right(0.3)))
				return;
		}
		if (changeState(state -> Move_Back(0.3)))
			return;
		changeState(state -> Move_Random_Logic(blendTime));
	}

	protected void Move_Random_Logic_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4C83F911L /*Move_Random_Logic_70*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Left_70(0.3)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Right_70(0.3)))
				return;
		}
		if (changeState(state -> Move_Back_70(0.3)))
			return;
		changeState(state -> Move_Random_Logic_70(blendTime));
	}

	protected void Move_Random_Logic_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x459E119CL /*Move_Random_Logic_30*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Left_30(0.3)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Right_30(0.3)))
				return;
		}
		if (changeState(state -> Move_Back_30(0.3)))
			return;
		changeState(state -> Move_Random_Logic_30(blendTime));
	}

	protected void Move_Random_Logic_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB83E08C8L /*Move_Random_Logic_R*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Left_R(0.3)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Right_R(0.3)))
				return;
		}
		if (changeState(state -> Move_Back_R(0.3)))
			return;
		changeState(state -> Move_Random_Logic_R(blendTime));
	}

	protected void Move_Random_Logic_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE0D18020L /*Move_Random_Logic_L*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Left_L(0.3)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Right_L(0.3)))
				return;
		}
		if (changeState(state -> Move_Back_L(0.3)))
			return;
		changeState(state -> Move_Random_Logic_L(blendTime));
	}

	protected void Move_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x21307BE3L /*Move_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		doAction(4272993707L /*BATTLE_MOVE*/, blendTime, onDoActionEnd -> moveAround(600 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			if (target != null && getDistanceToTarget(target) < 1000) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Normal(0.3)))
						return true;
				}
			}
			if (target != null && getDistanceToTarget(target) < 1000) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 3000)));
	}

	protected void Move_Left_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0xC514D3EAL /*Move_Left_70*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_70(0.3)))
				return;
		}
		doAction(1288020593L /*BATTLE_MOVE_70*/, blendTime, onDoActionEnd -> moveAround(600 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			if (target != null && getDistanceToTarget(target) < 1000) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Normal_Logic(0.3)))
						return true;
				}
			}
			if (target != null && getDistanceToTarget(target) < 1000) {
				if (changeState(state -> Attack_Normal_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_70(blendTime), 3000)));
	}

	protected void Move_Left_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x3B4D8C4L /*Move_Left_30*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_30(0.3)))
				return;
		}
		doAction(1791887746L /*BATTLE_MOVE_30*/, blendTime, onDoActionEnd -> moveAround(600 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			if (target != null && getDistanceToTarget(target) < 1000) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Normal_30(0.3)))
						return true;
				}
			}
			if (target != null && getDistanceToTarget(target) < 1000) {
				if (changeState(state -> Attack_Normal_30(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_30(blendTime), 3000)));
	}

	protected void Move_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x182F8BA0L /*Move_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		doAction(4272993707L /*BATTLE_MOVE*/, blendTime, onDoActionEnd -> moveAround(600 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			if (target != null && getDistanceToTarget(target) < 1000) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Normal(0.3)))
						return true;
				}
			}
			if (target != null && getDistanceToTarget(target) < 1000) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 3000)));
	}

	protected void Move_Right_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x8253D4E0L /*Move_Right_70*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_70(0.3)))
				return;
		}
		doAction(1288020593L /*BATTLE_MOVE_70*/, blendTime, onDoActionEnd -> moveAround(600 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			if (target != null && getDistanceToTarget(target) < 1000) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Normal_Logic(0.3)))
						return true;
				}
			}
			if (target != null && getDistanceToTarget(target) < 1000) {
				if (changeState(state -> Attack_Normal1_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_70(blendTime), 3000)));
	}

	protected void Move_Right_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x8DDD3C63L /*Move_Right_30*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_30(0.3)))
				return;
		}
		doAction(1791887746L /*BATTLE_MOVE_30*/, blendTime, onDoActionEnd -> moveAround(600 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			if (target != null && getDistanceToTarget(target) < 1000) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Normal_30(0.3)))
						return true;
				}
			}
			if (target != null && getDistanceToTarget(target) < 1000) {
				if (changeState(state -> Attack_Normal1_30(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_30(blendTime), 3000)));
	}

	protected void Move_Left_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x1F53DD10L /*Move_Left_L*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_L(0.3)))
				return;
		}
		doAction(2822890825L /*BATTLE_MOVE_L*/, blendTime, onDoActionEnd -> moveAround(600 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_L2(blendTime), 3000)));
	}

	protected void Move_Right_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0xA1109F26L /*Move_Right_L*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_L(0.3)))
				return;
		}
		doAction(2822890825L /*BATTLE_MOVE_L*/, blendTime, onDoActionEnd -> moveAround(600 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_L2(blendTime), 3000)));
	}

	protected void Move_Left_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0xF19BD453L /*Move_Left_R*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_R(0.3)))
				return;
		}
		doAction(4211092007L /*BATTLE_MOVE_R*/, blendTime, onDoActionEnd -> moveAround(600 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_R2(blendTime), 3000)));
	}

	protected void Move_Right_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x1527D182L /*Move_Right_R*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if (changeState(state -> Battle_Run_R(0.3)))
				return;
		}
		doAction(4211092007L /*BATTLE_MOVE_R*/, blendTime, onDoActionEnd -> moveAround(600 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_R2(blendTime), 3000)));
	}

	protected void Move_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xDD3BDB77L /*Move_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1509390199L /*BATTLE_MOVE_BACK*/, blendTime, onDoActionEnd -> escape(1600, () -> {
			if (changeState(state -> Attack_Roar2(0.3)))
				return true;
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 1000)));
	}

	protected void Move_Back_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x21A5D5F1L /*Move_Back_70*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1903647021L /*BATTLE_MOVE_BACK_70*/, blendTime, onDoActionEnd -> escape(1600, () -> {
			if (changeState(state -> Attack_Roar2_70(0.3)))
				return true;
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_70(blendTime), 1000)));
	}

	protected void Move_Back_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x56DD88A7L /*Move_Back_30*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3988388229L /*BATTLE_MOVE_BACK_30*/, blendTime, onDoActionEnd -> escape(1600, () -> {
			if (changeState(state -> Attack_Roar2_30(0.3)))
				return true;
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_30(blendTime), 1000)));
	}

	protected void Move_Back_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xD326C649L /*Move_Back_L*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(4291490981L /*BATTLE_MOVE_BACK_L*/, blendTime, onDoActionEnd -> escape(1600, () -> {
			if (changeState(state -> Attack_Roar2(0.3)))
				return true;
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_L2(blendTime), 1000)));
	}

	protected void Move_Back_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xFBB1B943L /*Move_Back_R*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3974077845L /*BATTLE_MOVE_BACK_R*/, blendTime, onDoActionEnd -> escape(1600, () -> {
			if (changeState(state -> Attack_Roar2(0.3)))
				return true;
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait_R2(blendTime), 1000)));
	}

	protected void Attack_Normal_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF7F3CFB0L /*Attack_Normal_Logic*/);
		if (getVariable(0x3F487035L /*_Hp*/) < 70) {
			if (changeState(state -> Attack_Normal_30(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 30) {
			if (changeState(state -> Attack_Normal_70(blendTime)))
				return;
		}
		if (changeState(state -> Attack_Normal(blendTime)))
			return;
		changeState(state -> Attack_Normal_Logic(blendTime));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) + 1);
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
				if (changeState(state -> Battle_Wait(blendTime)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_L2(blendTime)))
					return;
			}
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_R2(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Attack_Normal_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF005B495L /*Attack_Normal_70*/);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) + 1);
		doAction(744854123L /*ATTACK_NORMAL_70*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
				if (changeState(state -> Battle_Wait_70(blendTime)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_L2(blendTime)))
					return;
			}
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_R2(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait_70(blendTime));
		});
	}

	protected void Attack_Normal_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFDFEACE0L /*Attack_Normal_30*/);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) + 1);
		doAction(685037959L /*ATTACK_NORMAL_30*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
				if (changeState(state -> Battle_Wait_30(blendTime)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_L2(blendTime)))
					return;
			}
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_R2(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait_30(blendTime));
		});
	}

	protected void Attack_Normal1_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x71496550L /*Attack_Normal1_Logic*/);
		if (getVariable(0x3F487035L /*_Hp*/) < 70) {
			if (changeState(state -> Attack_Normal1_30(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 30) {
			if (changeState(state -> Attack_Normal1_70(blendTime)))
				return;
		}
		if (changeState(state -> Attack_Normal1(blendTime)))
			return;
		changeState(state -> Attack_Normal1_Logic(blendTime));
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) + 1);
		doAction(319900647L /*ATTACK_NORMAL_1*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
				if (changeState(state -> Battle_Wait(blendTime)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_L2(blendTime)))
					return;
			}
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_R2(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Attack_Normal1_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1ADC35B8L /*Attack_Normal1_70*/);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) + 1);
		doAction(2284978898L /*ATTACK_NORMAL_1_70*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
				if (changeState(state -> Battle_Wait_70(blendTime)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_L2(blendTime)))
					return;
			}
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_R2(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait_70(blendTime));
		});
	}

	protected void Attack_Normal1_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x88F986F7L /*Attack_Normal1_30*/);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) + 1);
		doAction(1094166569L /*ATTACK_NORMAL_1_30*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
				if (changeState(state -> Battle_Wait_30(blendTime)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_L2(blendTime)))
					return;
			}
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_R2(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait_30(blendTime));
		});
	}

	protected void Attack_Dash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4AA1775EL /*Attack_Dash*/);
		doAction(415291795L /*ATTACK_DASH*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Dash_Broad(blendTime)));
	}

	protected void Attack_Dash_Broad(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF7CC8990L /*Attack_Dash_Broad*/);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) + 1);
		doAction(100867313L /*ATTACK_DASH_BROAD*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Broad(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7F8B5D6EL /*Attack_Broad*/);
		doAction(476458140L /*ATTACK_STAND_BROAD*/, blendTime, onDoActionEnd -> {
			setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, 0);
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Attack_Dash_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4977F10FL /*Attack_Dash_70*/);
		doAction(1261645778L /*ATTACK_DASH_70*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Dash_Broad_70(blendTime)));
	}

	protected void Attack_Dash_Broad_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x33D7BC9BL /*Attack_Dash_Broad_70*/);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) + 1);
		doAction(762564938L /*ATTACK_DASH_BROAD_70*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_70(blendTime)));
	}

	protected void Attack_Broad_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3103FCD7L /*Attack_Broad_70*/);
		doAction(2812525414L /*ATTACK_STAND_BROAD_70*/, blendTime, onDoActionEnd -> {
			setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, 0);
			changeState(state -> Battle_Wait_70(blendTime));
		});
	}

	protected void Attack_Dash_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD0EF0495L /*Attack_Dash_30*/);
		doAction(3068764141L /*ATTACK_DASH_30*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Dash_Broad_30(blendTime)));
	}

	protected void Attack_Dash_Broad_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x52BF9FF3L /*Attack_Dash_Broad_30*/);
		setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, getVariable(0xBE38FAE6L /*_AttackBoardCountDown*/) + 1);
		doAction(3940838551L /*ATTACK_DASH_BROAD_30*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_30(blendTime)));
	}

	protected void Attack_Broad_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3A180DEDL /*Attack_Broad_30*/);
		doAction(2201904095L /*ATTACK_STAND_BROAD_30*/, blendTime, onDoActionEnd -> {
			setVariable(0xBE38FAE6L /*_AttackBoardCountDown*/, 0);
			changeState(state -> Battle_Wait_30(blendTime));
		});
	}

	protected void Attack_Roll_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCFEE121L /*Attack_Roll_Str*/);
		doAction(1871928288L /*ATTACK_ROLLING_STR_70*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Roll_Ing(blendTime), 2000));
	}

	protected void Attack_Roll_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFE56436L /*Attack_Roll_Ing*/);
		doAction(1711662246L /*ATTACK_ROLLING_70*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Roll_End(blendTime)));
	}

	protected void Attack_Roll_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCB2DAB49L /*Attack_Roll_End*/);
		doAction(2325227003L /*ATTACK_ROLLING_2_70*/, blendTime, onDoActionEnd -> changeState(state -> Weapon_Out(blendTime)));
	}

	protected void Weapon_Out(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x169F23DBL /*Weapon_Out*/);
		doAction(4142397618L /*WEAPON_OUT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_70(blendTime), 5000));
	}

	protected void Attack_Roll_Str_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC53D52AL /*Attack_Roll_Str_30*/);
		doAction(2589144925L /*ATTACK_ROLLING_STR_30*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Roll_Ing_30(blendTime), 2000));
	}

	protected void Attack_Roll_Ing_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5115A2C3L /*Attack_Roll_Ing_30*/);
		doAction(2150190214L /*ATTACK_ROLLING_30*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Roll_End_30(blendTime)));
	}

	protected void Attack_Roll_End_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9F0B6FFEL /*Attack_Roll_End_30*/);
		doAction(3801997870L /*ATTACK_ROLLING_2_30*/, blendTime, onDoActionEnd -> changeState(state -> Weapon_Out_30(blendTime)));
	}

	protected void Weapon_Out_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9F4DE4D3L /*Weapon_Out_30*/);
		doAction(4267242725L /*WEAPON_OUT_30*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_30(blendTime), 5000));
	}

	protected void Attack_Stun_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x84211C5DL /*Attack_Stun_L*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		doAction(1953561485L /*DAMAGE_STUN_ATTACK_L*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x3F487035L /*_Hp*/) < 70 && getVariable(0x3BDC5BFCL /*_StandUpCount*/) == 0) {
				if (changeState(state -> Damage_Leg_Stun_1(0.3)))
					return;
			}
			if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0x3BDC5BFCL /*_StandUpCount*/) == 1) {
				if (changeState(state -> Damage_Leg_Stun_1(0.3)))
					return;
			}
			changeState(state -> Attack_Stun_R(blendTime));
		});
	}

	protected void Attack_Stun_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x98BA4DCAL /*Attack_Stun_R*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getVariable(0xADE8C2ADL /*_NotRecovery*/) == 1) {
			if (changeState(state -> Damage_Leg_Stun_1_SP(blendTime)))
				return;
		}
		doAction(2773242188L /*DAMAGE_STUN_ATTACK_R*/, blendTime, onDoActionEnd -> changeState(state -> Damage_Leg_Stun_1(blendTime)));
	}

	protected void Attack_Roar_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x502B8DBCL /*Attack_Roar_70*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2220569436L /*ATTACK_ROAR_70*/, blendTime, onDoActionEnd -> {
			setVariable(0xEF7AE808L /*_RoarCount*/, getVariable(0xEF7AE808L /*_RoarCount*/) + 1);
			changeState(state -> Logic(blendTime));
		});
	}

	protected void Attack_Roar_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7C7CB370L /*Attack_Roar_30*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(956121243L /*ATTACK_ROAR_30*/, blendTime, onDoActionEnd -> {
			setVariable(0xEF7AE808L /*_RoarCount*/, getVariable(0xEF7AE808L /*_RoarCount*/) + 1);
			changeState(state -> Logic(blendTime));
		});
	}

	protected void Attack_Roar2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1D47B777L /*Attack_Roar2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(93247687L /*ATTACK_ROAR*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
				if (changeState(state -> Battle_Wait(blendTime)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_L2(blendTime)))
					return;
			}
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_R2(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Attack_Roar2_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4BC0D4F0L /*Attack_Roar2_70*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2220569436L /*ATTACK_ROAR_70*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
				if (changeState(state -> Battle_Wait_70(blendTime)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_L2(blendTime)))
					return;
			}
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_R2(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait_70(blendTime));
		});
	}

	protected void Attack_Roar2_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE8A273E7L /*Attack_Roar2_30*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(956121243L /*ATTACK_ROAR_30*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
				if (changeState(state -> Battle_Wait_30(blendTime)))
					return;
			}
			if (getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_L2(blendTime)))
					return;
			}
			if (getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
				if (changeState(state -> Battle_Wait_R2(blendTime)))
					return;
			}
			changeState(state -> Battle_Wait_30(blendTime));
		});
	}

	protected void Attack_Berserk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBF15FCA8L /*Attack_Berserk*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0xCC380C54L /*_BerserkCount*/, getVariable(0xCC380C54L /*_BerserkCount*/) - 1);
		doAction(1660979473L /*ATTACK_BERSERK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Berserk_ING(blendTime)));
	}

	protected void Attack_Berserk_ING(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x88A3DBB2L /*Attack_Berserk_ING*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x22FE74AL /*_AttackBerserk*/, getVariable(0x22FE74AL /*_AttackBerserk*/) + 1);
		if (getVariable(0x22FE74AL /*_AttackBerserk*/) >= 120) {
			if (changeState(state -> Attack_Berserk_END(blendTime)))
				return;
		}
		doAction(1058530058L /*ATTACK_BERSERK_ING*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Berserk_ING(blendTime)));
	}

	protected void Attack_Berserk_END(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xED21E763L /*Attack_Berserk_END*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x22FE74AL /*_AttackBerserk*/, 0);
		doAction(586933102L /*ATTACK_BERSERK_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Berserk_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8CE44E6EL /*Attack_Berserk_70*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0xCC380C54L /*_BerserkCount*/, getVariable(0xCC380C54L /*_BerserkCount*/) - 1);
		doAction(3976214222L /*ATTACK_BERSERK_70*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Berserk_ING_70(blendTime)));
	}

	protected void Attack_Berserk_ING_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x61E98513L /*Attack_Berserk_ING_70*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x22FE74AL /*_AttackBerserk*/, getVariable(0x22FE74AL /*_AttackBerserk*/) + 1);
		if (getVariable(0x22FE74AL /*_AttackBerserk*/) >= 150) {
			if (changeState(state -> Attack_Berserk_END_70(blendTime)))
				return;
		}
		doAction(784230910L /*ATTACK_BERSERK_ING_70*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Berserk_ING_70(blendTime)));
	}

	protected void Attack_Berserk_END_70(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3390A49L /*Attack_Berserk_END_70*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x22FE74AL /*_AttackBerserk*/, 0);
		doAction(3696745012L /*ATTACK_BERSERK_END_70*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_70(blendTime)));
	}

	protected void Attack_Berserk_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF9C8218EL /*Attack_Berserk_30*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0xCC380C54L /*_BerserkCount*/, getVariable(0xCC380C54L /*_BerserkCount*/) - 1);
		doAction(2328416137L /*ATTACK_BERSERK_30*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Berserk_ING_30(blendTime)));
	}

	protected void Attack_Berserk_ING_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x20DF1088L /*Attack_Berserk_ING_30*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x22FE74AL /*_AttackBerserk*/, getVariable(0x22FE74AL /*_AttackBerserk*/) + 1);
		if (getVariable(0x22FE74AL /*_AttackBerserk*/) >= 180) {
			if (changeState(state -> Attack_Berserk_END_30(blendTime)))
				return;
		}
		doAction(21064378L /*ATTACK_BERSERK_ING_30*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Berserk_ING_30(blendTime)));
	}

	protected void Attack_Berserk_END_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x86F0A166L /*Attack_Berserk_END_30*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x22FE74AL /*_AttackBerserk*/, 0);
		doAction(3858020162L /*ATTACK_BERSERK_END_30*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_30(blendTime)));
	}

	protected void CannonLogic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCCAB57A0L /*CannonLogic*/);
		setVariable(0x1E2360D6L /*_StunDelay_StartTime*/, getTime());
		changeState(state -> Damage_Leg_Stun(blendTime));
	}

	protected void Hit_Cannon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x44A10749L /*Hit_Cannon*/);
		setVariable(0xB5B81E5EL /*_CannonCount*/, getVariable(0xB5B81E5EL /*_CannonCount*/) + 1);
		if (target != null && checkBuff(target, 139) && getVariable(0xB5B81E5EL /*_CannonCount*/) >= 10 && getState() != 0xB3B2B64EL /*Damage_Leg_Stun*/ && getState() != 0x84211C5DL /*Attack_Stun_L*/ && getState() != 0x98BA4DCAL /*Attack_Stun_R*/ && getState() != 0x92FCEAABL /*Damage_Leg_Stun_1*/ && getState() != 0x866C7489L /*Wait*/ && getState() != 0x8377635AL /*Move_Random*/ && getState() != 0xBF15FCA8L /*Attack_Berserk*/ && getState() != 0x88A3DBB2L /*Attack_Berserk_ING*/ && getState() != 0xED21E763L /*Attack_Berserk_END*/ && getState() != 0x8CE44E6EL /*Attack_Berserk_70*/ && getState() != 0x61E98513L /*Attack_Berserk_ING_70*/ && getState() != 0x3390A49L /*Attack_Berserk_END_70*/ && getState() != 0xF9C8218EL /*Attack_Berserk_30*/ && getState() != 0x20DF1088L /*Attack_Berserk_ING_30*/ && getState() != 0x86F0A166L /*Attack_Berserk_END_30*/ && getState() != 0xD61E465EL /*Move_Return*/ && getState() != 0x190B95ACL /*Recovery*/) {
			if (changeState(state -> CannonLogic(0.3)))
				return;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0x88A3DBB2L /*Attack_Berserk_ING*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0xED21E763L /*Attack_Berserk_END*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0x61E98513L /*Attack_Berserk_ING_70*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0x3390A49L /*Attack_Berserk_END_70*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0x20DF1088L /*Attack_Berserk_ING_30*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0x86F0A166L /*Attack_Berserk_END_30*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return;
		}
		changeState(state -> Hit_Cannon_2(blendTime));
	}

	protected void Hit_Cannon_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB804A1C3L /*Hit_Cannon_2*/);
		if(getCallCount() == 40) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Hit_Cannon_2(blendTime));
	}

	protected void NotRecoveryLogic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCC6FB44AL /*NotRecoveryLogic*/);
		setVariable(0xADE8C2ADL /*_NotRecovery*/, getVariable(0xADE8C2ADL /*_NotRecovery*/) + 1);
		changeState(state -> Damage_Leg_Stun(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		if (target != null && checkBuff(target, 139) && getState() != 0xB3B2B64EL /*Damage_Leg_Stun*/ && getState() != 0x44A10749L /*Hit_Cannon*/ && getState() != 0xB804A1C3L /*Hit_Cannon_2*/ && getState() != 0x84211C5DL /*Attack_Stun_L*/ && getState() != 0x98BA4DCAL /*Attack_Stun_R*/ && getState() != 0x92FCEAABL /*Damage_Leg_Stun_1*/ && getState() != 0x866C7489L /*Wait*/ && getState() != 0x8377635AL /*Move_Random*/ && getState() != 0xBF15FCA8L /*Attack_Berserk*/ && getState() != 0x88A3DBB2L /*Attack_Berserk_ING*/ && getState() != 0xED21E763L /*Attack_Berserk_END*/ && getState() != 0x8CE44E6EL /*Attack_Berserk_70*/ && getState() != 0x61E98513L /*Attack_Berserk_ING_70*/ && getState() != 0x3390A49L /*Attack_Berserk_END_70*/ && getState() != 0xF9C8218EL /*Attack_Berserk_30*/ && getState() != 0x20DF1088L /*Attack_Berserk_ING_30*/ && getState() != 0x86F0A166L /*Attack_Berserk_END_30*/ && getState() != 0xD61E465EL /*Move_Return*/ && getState() != 0x190B95ACL /*Recovery*/ && getVariable(0x8AC22F24L /*_StunDelay_EndTime*/) >= 90000) {
			if (changeState(state -> Hit_Cannon(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0x88A3DBB2L /*Attack_Berserk_ING*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0xED21E763L /*Attack_Berserk_END*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0x61E98513L /*Attack_Berserk_ING_70*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0x3390A49L /*Attack_Berserk_END_70*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0x20DF1088L /*Attack_Berserk_ING_30*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && checkBuff(target, 139) && getState() == 0x86F0A166L /*Attack_Berserk_END_30*/) {
			if (changeState(state -> NotRecoveryLogic(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfPartHp(3) <= 0 && getVariable(0xE4C9AD93L /*_HeadStunCount*/) == 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0 && getVariable(0x5AFA508EL /*_LegStunCount*/) == 0 && getState() != 0xB3B2B64EL /*Damage_Leg_Stun*/ && getState() != 0x84211C5DL /*Attack_Stun_L*/ && getState() != 0x98BA4DCAL /*Attack_Stun_R*/ && getState() != 0x92FCEAABL /*Damage_Leg_Stun_1*/ && getState() != 0xA02312D4L /*Damage_Head_Stun*/ && getState() != 0x30EFD8AFL /*Damage_Head_Stun_1*/ && getState() != 0xD61E465EL /*Move_Return*/ && getState() != 0x190B95ACL /*Recovery*/) {
			if (changeState(state -> Damage_Head_Stun(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfPartHp(1) <= 0 && getSelfPartHp(2) <= 0 && getVariable(0x5AFA508EL /*_LegStunCount*/) == 0 && getState() != 0xB3B2B64EL /*Damage_Leg_Stun*/ && getState() != 0x84211C5DL /*Attack_Stun_L*/ && getState() != 0x98BA4DCAL /*Attack_Stun_R*/ && getState() != 0x92FCEAABL /*Damage_Leg_Stun_1*/ && getState() != 0xD61E465EL /*Move_Return*/ && getState() != 0x190B95ACL /*Recovery*/) {
			if (changeState(state -> Damage_Leg_Stun(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfPartHp(2) <= 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0 && getState() != 0xB3B2B64EL /*Damage_Leg_Stun*/ && getState() != 0x84211C5DL /*Attack_Stun_L*/ && getState() != 0x98BA4DCAL /*Attack_Stun_R*/ && getState() != 0x92FCEAABL /*Damage_Leg_Stun_1*/ && getState() != 0xD61E465EL /*Move_Return*/ && getState() != 0x190B95ACL /*Recovery*/) {
			if (changeState(state -> Damage_Leg_L(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfPartHp(1) <= 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0 && getState() != 0xB3B2B64EL /*Damage_Leg_Stun*/ && getState() != 0x84211C5DL /*Attack_Stun_L*/ && getState() != 0x98BA4DCAL /*Attack_Stun_R*/ && getState() != 0x92FCEAABL /*Damage_Leg_Stun_1*/ && getState() != 0xD61E465EL /*Move_Return*/ && getState() != 0x190B95ACL /*Recovery*/) {
			if (changeState(state -> Damage_Leg_R(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult WakeUp(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x59ACE9D4L /*Statue*/) {
			if (changeState(state -> Notifier_Logic(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult WakeUp_Test(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x59ACE9D4L /*Statue*/) {
			if (changeState(state -> Notifier_Logic_Test(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
