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
@IAIName("spirit_earth")
public class Ai_spirit_earth extends CreatureAI {
	public Ai_spirit_earth(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x9458693L /*_isWaitMode*/, 1);
		setVariable(0xA41A87B8L /*_isComeonMode*/, 0);
		setVariable(0x9326AD79L /*_SummonStartTime*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, 0);
		setVariable(0x51EDA18AL /*_SummonEndTime*/, 0);
		setVariable(0x9326AD79L /*_SummonStartTime*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_TimerSet_Logic(blendTime), 500));
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		clearAggro(true);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.EnemyLordOrKingTent, EAIFindType.normal, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.EnemyBarricade, EAIFindType.normal, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		changeState(state -> Chase_Owner(blendTime));
	}

	protected void TargetLost2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBC668DA5L /*TargetLost2*/);
		clearAggro(true);
		changeState(state -> Chase_Owner(blendTime));
	}

	protected void Summon_TimerSet_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x24B3BDEL /*Summon_TimerSet_Logic*/);
		changeState(state -> Summon(blendTime));
	}

	protected void Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x34E5AE02L /*Summon*/);
		doAction(3635031213L /*SUMMON*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getOwnerCharacterKey() != 32) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000 && getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1200) {
			if (changeState(state -> Chase_Owner_Run(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250) {
			if (changeState(state -> Chase_Owner(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Skill_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE2111862L /*Skill_Wait*/);
		doAction(968466254L /*SKILL_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Skill_Wait_Ing(blendTime), 300));
	}

	protected void Skill_Wait_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC70DB965L /*Skill_Wait_Ing*/);
		doAction(722208483L /*SKILL_WAIT_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Skill_Wait_Ing(blendTime), 1000));
	}

	protected void Skill_Wait_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x98522149L /*Skill_Wait_End*/);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 250, 0, 0);
		doAction(1330291177L /*SKILL_WAIT_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void TeleportToOwner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF5D87A28L /*TeleportToOwner*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 250, 0, 0);
		doAction(519999600L /*APPEAR*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Battle_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD0A32C0L /*Battle_Teleport*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x9458693L /*_isWaitMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 250, 0, 0);
		doAction(519999600L /*APPEAR*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Chase_Owner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEB681DFAL /*Chase_Owner*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000 && getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> TeleportToOwner(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 400) {
			if (changeState(state -> Chase_Owner_Run(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, -200, 250, 0, 1, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 300)));
	}

	protected void Chase_Owner_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB3DD8609L /*Chase_Owner_Run*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000 && getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> TeleportToOwner(0.3)))
				return;
		}
		doAction(1022931502L /*MOVE_RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, -200, 250, 0, 1, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 300)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
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
		if (!checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Teleport(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500));
	}

	protected void Move_Chase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x21564578L /*Move_Chase*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000 && getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> UnSummon(0.3)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> TeleportToOwner(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Chase_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_LR(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Attack_R(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chase(blendTime), 1000)));
	}

	protected void Move_Chase2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x9C2AEB6FL /*Move_Chase2*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost2(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000 && getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> UnSummon(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> TeleportToOwner(0.3)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Chase_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_LR(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Attack_R(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chase2(blendTime), 1000)));
	}

	protected void Chase_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xD4573013L /*Chase_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x3F487035L /*_Hp*/) == 0 && getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> UnSummon(0.3)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> TeleportToOwner(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_LR(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Attack_R(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Chase_Run(blendTime), 1000)));
	}

	protected void UnSummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCC02DCCFL /*UnSummon*/);
		doAction(1340753835L /*UNSUMMON*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 500));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 500));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEC3F34D2L /*Detect_Target*/);
		doAction(3687825854L /*DETECT*/, blendTime, onDoActionEnd -> changeState(state -> Move_Chase2(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x9458693L /*_isWaitMode*/, 0);
		setVariable(0xA41A87B8L /*_isComeonMode*/, 0);
		if (getOwnerCharacterKey() != 32) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000 && getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> UnSummon(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Battle_Teleport(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && target != null && getDistanceToTarget(target) <= 500 && target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Chase_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 200) {
			if (changeState(state -> Move_Chase(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_LR(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Attack_R(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
			if (changeState(state -> Move_Chase(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.EnemyLordOrKingTent, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
			if (changeState(state -> Move_Chase(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.EnemyBarricade, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
			if (changeState(state -> Move_Chase(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 200 && !checkOwnerEvadeEmergency()) {
			if (changeState(state -> Chase_Owner(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x40F853F4L /*Attack_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(857945710L /*ATTACK_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x61CFF41EL /*Attack_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2562336179L /*ATTACK_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_LR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE5D2171FL /*Attack_LR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2106356938L /*ATTACK_LR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_LR_STR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6DF24D27L /*Attack_LR_STR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost2(blendTime)))
				return;
		}
		doAction(3881795681L /*ATTACK_LR_STR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_LR_STR_Delay(blendTime)));
	}

	protected void Attack_LR_STR_Delay(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x512543C8L /*Attack_LR_STR_Delay*/);
		doAction(1931661088L /*ATTACK_LR_STR_DELAY*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_LR_ING(blendTime), 500));
	}

	protected void Attack_LR_ING(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x992241F1L /*Attack_LR_ING*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 400) {
			if (changeState(state -> Attack_LR_END(0.2)))
				return;
		}
		doAction(384166347L /*ATTACK_LR_ING*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Attack_LR_ING(blendTime), 500)));
	}

	protected void Attack_LR_END(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x505C1C20L /*Attack_LR_END*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2164647232L /*ATTACK_LR_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/ && getState() != 0x34E5AE02L /*Summon*/ && getVariable(0xA41A87B8L /*_isComeonMode*/) != 1 && getVariable(0xFA9DA674L /*_isBattleMode*/) != 1) {
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeTeamDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/ && getState() != 0x34E5AE02L /*Summon*/ && getVariable(0xA41A87B8L /*_isComeonMode*/) != 1 && getVariable(0xFA9DA674L /*_isBattleMode*/) != 1) {
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Arrow_1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		setVariable(0xA41A87B8L /*_isComeonMode*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x9458693L /*_isWaitMode*/, 0);
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Attack_LR_STR(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wall_1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Skill_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wall_2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Skill_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Lightning_1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Skill_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Lightning_2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Skill_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Lightning_3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Skill_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBrotherCall(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Skill_Wait_End(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Summon_1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> UnSummon(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Summon_2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> UnSummon(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Die_1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> UnSummon(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Attack_Stop(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		setVariable(0xA41A87B8L /*_isComeonMode*/, 1);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x9458693L /*_isWaitMode*/, 0);
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Chase_Owner(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Attack_Go(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		setVariable(0xA41A87B8L /*_isComeonMode*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x9458693L /*_isWaitMode*/, 0);
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnOwnerDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> UnSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleMoveInWater(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> UnSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
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
