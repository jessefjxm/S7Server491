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
@IAIName("spirit_fire")
public class Ai_spirit_fire extends CreatureAI {
	public Ai_spirit_fire(Creature actor, Map<Long, Integer> aiVariables) {
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
		setVariable(0x925166FDL /*_FirstAttack*/, 0);
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
			if (findTarget(EAIFindTargetType.EnemyLordOrKingTent, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTarget(EAIFindTargetType.EnemyBarricade, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
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
		setVariable(0x9458693L /*_isWaitMode*/, 1);
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
		if (getOwnerCharacterKey() != 29) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(0.3)))
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
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Battle_Teleport(0.3)))
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
		if (getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> UnSummon(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Battle_Teleport(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 800) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Battle_Attack2(0.3)))
				return;
		}
		doAction(1022931502L /*MOVE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chase(blendTime), 500)));
	}

	protected void UnSummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCC02DCCFL /*UnSummon*/);
		doAction(1340753835L /*UNSUMMON*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 2000));
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
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(3687825854L /*DETECT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x9458693L /*_isWaitMode*/, 0);
		if (getOwnerCharacterKey() != 29) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> UnSummon(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Battle_Teleport(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 200) {
			if (changeState(state -> Move_Chase(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 800) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Battle_Attack2(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTarget(EAIFindTargetType.EnemyLordOrKingTent, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTarget(EAIFindTargetType.EnemyBarricade, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void UnderWater_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xBF7E038EL /*UnderWater_KnockDown*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Battle_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5FDC949L /*Battle_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD72BCC90L /*Battle_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x94302B26L /*Battle_Attack3*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Skill_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x816801C7L /*Skill_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4166519709L /*SKILL_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) != 1 && getVariable(0xA41A87B8L /*_isComeonMode*/) != 1) {
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
	public EAiHandlerResult Arrow_3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		setVariable(0xA41A87B8L /*_isComeonMode*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x9458693L /*_isWaitMode*/, 0);
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Skill_Attack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Water_1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Skill_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Water_2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Skill_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Fire_1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Skill_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Fire_2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
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
	public EAiHandlerResult Summon_3(Creature sender, Integer[] eventData) {
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
	public EAiHandlerResult Summon_4(Creature sender, Integer[] eventData) {
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
	public EAiHandlerResult Die_3(Creature sender, Integer[] eventData) {
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
	public EAiHandlerResult Attack_Stop_3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		setVariable(0xA41A87B8L /*_isComeonMode*/, 1);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x925166FDL /*_FirstAttack*/, 0);
		if (getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Chase_Owner(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Attack_Go_3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		setVariable(0x925166FDL /*_FirstAttack*/, 1);
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
	public EAiHandlerResult handleTimeout(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
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
