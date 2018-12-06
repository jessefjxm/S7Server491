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
@IAIName("alter_novicegargoyle_boss")
public class Ai_alter_novicegargoyle_boss extends CreatureAI {
	public Ai_alter_novicegargoyle_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0x2F6E584FL /*_BattleCount*/, 0);
		setVariable(0x7398A4FL /*_IsRoar*/, 0);
		setVariable(0xE73206BFL /*_JumpAttackCount*/, 0);
		setVariable(0xBEE22793L /*_CombinationCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Rotate_Logic(blendTime), 1000));
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

	protected void Rotate_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x65942A11L /*Rotate_Logic*/);
		if (checkParentInstanceTeamNo()) {
			if (findTarget(EAIFindTargetType.Monster, EAIFindType.nearest, false, object -> getDistanceToTarget(object) < 4000 && getTargetCharacterKey(object) == 27135)) {
				if (changeState(state -> Wait_Rotate(blendTime)))
					return;
			}
		}
		changeState(state -> Rotate_Logic(blendTime));
	}

	protected void Wait_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC6855029L /*Wait_Rotate*/);
		doAction(2910378664L /*WAIT_ROTATE*/, blendTime, onDoActionEnd -> changeState(state -> Detect_Target(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500 + Rnd.get(-500,500)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD61E465EL /*Move_Return*/);
		clearAggro(true);
		if (checkParentInstanceTeamNo()) {
			if (findTarget(EAIFindTargetType.Monster, EAIFindType.nearest, false, object -> getDistanceToTarget(object) < 8000 && getTargetCharacterKey(object) == 27135)) {
				if (changeState(state -> Return_Shrine(blendTime)))
					return;
			}
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Return_Shrine(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x2E0CB188L /*Return_Shrine*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 100 && getDistanceToTarget(target, false) <= 700)) {
			if (changeState(state -> Attack_Roar(0.3)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Return_Shrine(blendTime), 1000)));
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
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if(Rnd.getChance(60)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 75 && target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Combination(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) < 790 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Roar(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 500 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Flying(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) > 350) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 75 && target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Combination(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) < 790 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Roar(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 500 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Flying(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE2DFC297L /*Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 75 && target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Combination(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) < 790 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Roar(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 500 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Flying(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 100)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Combination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB2905D73L /*Attack_Combination*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(1058081582L /*ATTACK_COMBINATION*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_Flying(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDAEAE32EL /*Attack_Flying*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(225868946L /*ATTACK_FLYING_STR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5ABF220L /*Attack_Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(93247687L /*ATTACK_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x355E057L /*Attack_End_Logic*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Summon_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x14EAD959L /*Summon_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Die(blendTime), 1000));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 1000));
	}

	protected void Die_Send(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2D16CC9BL /*Die_Send*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi()._StageBossDie(getActor(), null));
		changeState(state -> Summon_Die(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && getState() == 0x866C7489L /*Wait*/ && target != null && isCreatureVisible(target, false)) {
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
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.4)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die_Send(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult ChildAllDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
