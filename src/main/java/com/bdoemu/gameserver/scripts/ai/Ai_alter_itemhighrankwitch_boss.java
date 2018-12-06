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
@IAIName("alter_itemhighrankwitch_boss")
public class Ai_alter_itemhighrankwitch_boss extends CreatureAI {
	public Ai_alter_itemhighrankwitch_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x323A51B5L /*_NormalAttackCount*/, 0);
		setVariable(0x46A5E1BAL /*_helpme_count*/, 1);
		setVariable(0xED5E4041L /*_RangeAttackCount*/, 0);
		setVariable(0x7B4DEA05L /*_CrazyCount*/, 0);
		setVariable(0x41A0D3B8L /*_MirrorPlace*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0x331BABF0L /*_HideCount*/, 0);
		setVariable(0x6698B40CL /*_isCrazyMode*/, 0);
		setVariable(0xDBA53A2L /*_isFaker*/, getVariable(0x8D0BA7FL /*AI_Faker*/));
		setVariable(0x1E2360D6L /*_StunDelay_StartTime*/, 0);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, 0);
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Rotate(blendTime), 1000));
	}

	protected void Summon_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB8F870L /*Summon_Start*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action_Logic(blendTime), 1000));
	}

	protected void Start_Action_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF1ED1C65L /*Start_Action_Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3000)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 5000));
	}

	protected void Wait_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xC6855029L /*Wait_Rotate*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -391814, -300138, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Start_Action_Logic(blendTime), 1000)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1700)) {
			if (changeState(state -> Detect_Target(0.2)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 300, 700, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 5000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Chaser_Teleport1(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Logic_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA9683766L /*Logic_50*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 5000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Chaser_Teleport1_50(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
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
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void FailFindPath_Logic_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8DEAF736L /*FailFindPath_Logic_50*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 5) {
			if (changeState(state -> FailFindPathToTarget_50(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait2(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void FailFindPathToTarget_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4457619EL /*FailFindPathToTarget_50*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait2(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD61E465EL /*Move_Return*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.OwnerPosition, 500, 500, 1, 1);
		changeState(state -> FindTarget_Logic(blendTime));
	}

	protected void FindTarget_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB0531BCDL /*FindTarget_Logic*/);
		if (checkParentInstanceTeamNo()) {
			if (findTarget(EAIFindTargetType.Monster, EAIFindType.nearest, false, object -> getDistanceToTarget(object) < 4000 && getTargetCharacterKey(object) == 27137)) {
				if (changeState(state -> Battle_Wait(blendTime)))
					return;
			}
		}
		changeState(state -> FindTarget_Logic(blendTime));
	}

	protected void Anger_Clear(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x197E999CL /*Anger_Clear*/);
		doAction(4188110314L /*CLEAR_BUFF*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0x6698B40CL /*_isCrazyMode*/) == 1) {
			if (changeState(state -> Battle_Wait2(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 4000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && getVariable(0x7B4DEA05L /*_CrazyCount*/) == 0) {
			if (changeState(state -> Attack_Roar(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 400 && getDistanceToTarget(target, false) <= 1600) && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Skill2A(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Evading_Teleport_Str(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) > 300 && target != null && getDistanceToTarget(target) <= 1200) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Range_Attack3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 300 && target != null && getDistanceToTarget(target) < 1200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Range_Attack2(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) <= 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Normal3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Chaser_Teleport1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCC2226D5L /*Battle_Wait2*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		if (getVariable(0x8AC22F24L /*_StunDelay_EndTime*/) >= 15000 && getVariable(0x331BABF0L /*_HideCount*/) == 0) {
			if (changeState(state -> HideAndSeek(0.3)))
				return;
		}
		if (getVariable(0x8AC22F24L /*_StunDelay_EndTime*/) >= 30000 && getVariable(0x331BABF0L /*_HideCount*/) > 0) {
			if (changeState(state -> HideAndSeek(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic_50(0.3)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 4000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic_50(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 400 && getDistanceToTarget(target, false) <= 1600) && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Skill2A_50(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) > 300 && target != null && getDistanceToTarget(target) <= 1200) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Range_Attack3_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 300 && target != null && getDistanceToTarget(target) < 1200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Range_Attack2_50(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) <= 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Normal3_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal2_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal1_50(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1000) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Chaser_Teleport1_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Chaser_50(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait2(blendTime), 1000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1200) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Chaser_Teleport1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 1200) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Range_Attack2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 1200) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Range_Attack1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 280 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void Move_Chaser_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x9AE91373L /*Move_Chaser_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic_50(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic_50(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1200) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Chaser_Teleport1_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 1200) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Range_Attack2_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 1200) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Range_Attack1_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 280 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal3_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal2_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(70)) {
				if (changeState(state -> Attack_Normal1_50(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Wait2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Move_Chaser_50(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic_50(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser_50(blendTime), 1000)));
	}

	protected void Chaser_Teleport1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x69E32139L /*Chaser_Teleport1*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2559848810L /*BATTLE_ATTACK_TELEPORT*/, blendTime, onDoActionEnd -> changeState(state -> Chaser_Teleport2(blendTime)));
	}

	protected void Chaser_Teleport1_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF55EDFF5L /*Chaser_Teleport1_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic_50(blendTime)))
				return;
		}
		doAction(2559848810L /*BATTLE_ATTACK_TELEPORT*/, blendTime, onDoActionEnd -> changeState(state -> Chaser_Teleport2_50(blendTime)));
	}

	protected void Chaser_Teleport2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x651756C1L /*Chaser_Teleport2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 1000) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Chaser_Teleport3(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if (changeState(state -> Chaser_Teleport3(0.4)))
				return;
		}
		doAction(2242154303L /*BATTLE_ATTACK_TELEPORT_ING*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Chaser_Teleport2(blendTime), 1000)));
	}

	protected void Chaser_Teleport2_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x16A10CB2L /*Chaser_Teleport2_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic_50(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 3000 && getVariable(0x870CD143L /*_IsPartyMember*/) == 1) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 1000) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Chaser_Teleport3_50(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if (changeState(state -> Chaser_Teleport3_50(0.4)))
				return;
		}
		doAction(2242154303L /*BATTLE_ATTACK_TELEPORT_ING*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Chaser_Teleport2_50(blendTime), 1000)));
	}

	protected void Chaser_Teleport3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x40707E48L /*Chaser_Teleport3*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(2422289259L /*BATTLE_ATTACK_TELEPORT_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Chaser_Teleport3_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF2177043L /*Chaser_Teleport3_50*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic_50(blendTime)))
				return;
		}
		doAction(2422289259L /*BATTLE_ATTACK_TELEPORT_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait2(blendTime)));
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		setVariable(0x323A51B5L /*_NormalAttackCount*/, getVariable(0x323A51B5L /*_NormalAttackCount*/) + 1);
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Normal1_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x56EF7B49L /*Attack_Normal1_50*/);
		setVariable(0x323A51B5L /*_NormalAttackCount*/, getVariable(0x323A51B5L /*_NormalAttackCount*/) + 1);
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Logic_50(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		setVariable(0x323A51B5L /*_NormalAttackCount*/, getVariable(0x323A51B5L /*_NormalAttackCount*/) + 1);
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Normal2_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x995571EFL /*Attack_Normal2_50*/);
		setVariable(0x323A51B5L /*_NormalAttackCount*/, getVariable(0x323A51B5L /*_NormalAttackCount*/) + 1);
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Logic_50(blendTime)));
	}

	protected void Attack_Normal3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5F2C1966L /*Attack_Normal3*/);
		setVariable(0x323A51B5L /*_NormalAttackCount*/, 0);
		doAction(1616697113L /*BATTLE_ATTACK_VEGITA_1*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Normal3_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x391A1921L /*Attack_Normal3_50*/);
		setVariable(0x323A51B5L /*_NormalAttackCount*/, 0);
		doAction(1616697113L /*BATTLE_ATTACK_VEGITA_1*/, blendTime, onDoActionEnd -> changeState(state -> Logic_50(blendTime)));
	}

	protected void Range_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3F78FC52L /*Range_Attack1*/);
		setVariable(0xED5E4041L /*_RangeAttackCount*/, getVariable(0xED5E4041L /*_RangeAttackCount*/) + 1);
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Range_Attack1_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1F086869L /*Range_Attack1_50*/);
		setVariable(0xED5E4041L /*_RangeAttackCount*/, getVariable(0xED5E4041L /*_RangeAttackCount*/) + 1);
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Logic_50(blendTime)));
	}

	protected void Range_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFCBDD50EL /*Range_Attack2*/);
		setVariable(0xED5E4041L /*_RangeAttackCount*/, getVariable(0xED5E4041L /*_RangeAttackCount*/) + 1);
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Range_Attack2_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF403430CL /*Range_Attack2_50*/);
		setVariable(0xED5E4041L /*_RangeAttackCount*/, getVariable(0xED5E4041L /*_RangeAttackCount*/) + 1);
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Logic_50(blendTime)));
	}

	protected void Range_Attack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC222DA43L /*Range_Attack3*/);
		setVariable(0xED5E4041L /*_RangeAttackCount*/, getVariable(0xED5E4041L /*_RangeAttackCount*/) + 2);
		doAction(760602734L /*BATTLE_RANGEATTACK1_VEGITA*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Range_Attack3_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDCB80616L /*Range_Attack3_50*/);
		setVariable(0xED5E4041L /*_RangeAttackCount*/, getVariable(0xED5E4041L /*_RangeAttackCount*/) + 2);
		doAction(760602734L /*BATTLE_RANGEATTACK1_VEGITA*/, blendTime, onDoActionEnd -> changeState(state -> Logic_50(blendTime)));
	}

	protected void Attack_Skill1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE0ED2EE9L /*Attack_Skill1*/);
		doAction(341282757L /*BATTLE_ATTACK3_READY*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Skill2A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A8AF5FCL /*Attack_Skill2A*/);
		doAction(4215910613L /*BATTLE_ATTACK_FRY*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Skill2B(blendTime), 1000));
	}

	protected void Attack_Skill2A_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x50B385AAL /*Attack_Skill2A_50*/);
		doAction(4215910613L /*BATTLE_ATTACK_FRY*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Skill2B_50(blendTime), 1000));
	}

	protected void Attack_Skill2B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x73C72D29L /*Attack_Skill2B*/);
		doAction(86324951L /*BATTLE_ATTACK_FRY_B*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Skill2C(blendTime)));
	}

	protected void Attack_Skill2B_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE88BE2EAL /*Attack_Skill2B_50*/);
		doAction(86324951L /*BATTLE_ATTACK_FRY_B*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Skill2C_50(blendTime)));
	}

	protected void Attack_Skill2C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA1DDB93AL /*Attack_Skill2C*/);
		setVariable(0xED5E4041L /*_RangeAttackCount*/, 0);
		doAction(2263031318L /*BATTLE_ATTACK_FRY_C*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 1000));
	}

	protected void Attack_Skill2C_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x343567DBL /*Attack_Skill2C_50*/);
		setVariable(0xED5E4041L /*_RangeAttackCount*/, 0);
		doAction(2263031318L /*BATTLE_ATTACK_FRY_C*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic_50(blendTime), 1000));
	}

	protected void Attack_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5ABF220L /*Attack_Roar*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_4STAGE_SKELETONKING");
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_4STAGE_SKELETONKING2");
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().SkeletonKing(getActor(), null));
		doAction(741286185L /*SKILL_LETS_CRAZY*/, blendTime, onDoActionEnd -> {
			setVariable(0x1E2360D6L /*_StunDelay_StartTime*/, getTime());
			setVariable(0x6698B40CL /*_isCrazyMode*/, 1);
			changeState(state -> Battle_Wait2(blendTime));
		});
	}

	protected void Skill_Teleport1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC2BB42E9L /*Skill_Teleport1*/);
		doAction(2559848810L /*BATTLE_ATTACK_TELEPORT*/, blendTime, onDoActionEnd -> changeState(state -> Skill_Teleport2(blendTime)));
	}

	protected void Skill_Teleport2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x23529AB0L /*Skill_Teleport2*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Skill_Teleport3(0.4)))
				return;
		}
		doAction(2242154303L /*BATTLE_ATTACK_TELEPORT_ING*/, blendTime, onDoActionEnd -> escape(1000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Skill_Teleport3(blendTime), 500)));
	}

	protected void Skill_Teleport3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4A821032L /*Skill_Teleport3*/);
		doAction(2422289259L /*BATTLE_ATTACK_TELEPORT_END*/, blendTime, onDoActionEnd -> changeState(state -> Skill_Teleport_Logic(blendTime)));
	}

	protected void Skill_Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF1CD5814L /*Skill_Teleport_Logic*/);
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) <= 1200) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Range_Attack3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Range_Attack2(0.3)))
					return;
			}
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Evading_Teleport_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF7716D08L /*Evading_Teleport_Str*/);
		doAction(1823561817L /*BATTLE_EVADING*/, blendTime, onDoActionEnd -> changeState(state -> Evading_Move_Logic(blendTime)));
	}

	protected void Evading_Move_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x623DEA02L /*Evading_Move_Logic*/);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getAngleToTarget(target) > -25 && target != null && getAngleToTarget(target) < 25 && target != null && getDistanceToTarget(target) < 400 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Evading_Teleport_Front(0.3)))
					return;
			}
		}
		if(Rnd.getChance(35)) {
			if (changeState(state -> Evading_Teleport_Right(0.3)))
				return;
		}
		if(Rnd.getChance(35)) {
			if (changeState(state -> Evading_Teleport_Left(0.3)))
				return;
		}
		changeState(state -> Evading_Teleport_Front(blendTime));
	}

	protected void Evading_Teleport_Front(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x96074FB2L /*Evading_Teleport_Front*/);
		doAction(1800767642L /*BATTLE_EVADING_FRONT*/, blendTime, onDoActionEnd -> changeState(state -> Evading_Teleport_End(blendTime)));
	}

	protected void Evading_Teleport_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEC291413L /*Evading_Teleport_Right*/);
		doAction(3578530980L /*BATTLE_EVADING_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Evading_Teleport_End(blendTime)));
	}

	protected void Evading_Teleport_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x74B90982L /*Evading_Teleport_Left*/);
		doAction(3647199323L /*BATTLE_EVADING_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Evading_Teleport_End(blendTime)));
	}

	protected void Evading_Teleport_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9F53D359L /*Evading_Teleport_End*/);
		doAction(2362607638L /*BATTLE_EVADING_END*/, blendTime, onDoActionEnd -> changeState(state -> Evading_Attack_Logic(blendTime)));
	}

	protected void Evading_Attack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xEA2B7235L /*Evading_Attack_Logic*/);
		if (getVariable(0x3F487035L /*_HP*/) <= 50 && target != null && getDistanceToTarget(target) <= 1200) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Range_Attack3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Range_Attack2(0.3)))
					return;
			}
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void HideAndSeek(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDECDA9B1L /*HideAndSeek*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_4STAGE_BOSS_HIDE");
		doAction(82464780L /*HIDE_START*/, blendTime, onDoActionEnd -> {
			setVariable(0x331BABF0L /*_HideCount*/, 1);
			scheduleState(state -> Hide(blendTime), 1000);
		});
	}

	protected void Hide(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6D61FF50L /*Hide*/);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0);
		doAction(1096688925L /*HIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> MirrorNotifier_Logic(blendTime), 1000));
	}

	protected void MirrorNotifier_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4F868CCBL /*MirrorNotifier_Logic*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_4STAGE_BOSS_HIDE2");
		changeState(state -> MirrorImage_Logic(blendTime));
	}

	protected void MirrorImage_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3E46E00L /*MirrorImage_Logic*/);
		if(Rnd.getChance(12)) {
			if (changeState(state -> MirrorImage1(blendTime)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> MirrorImage2(blendTime)))
				return;
		}
		if(Rnd.getChance(18)) {
			if (changeState(state -> MirrorImage3(blendTime)))
				return;
		}
		if(Rnd.getChance(21)) {
			if (changeState(state -> MirrorImage4(blendTime)))
				return;
		}
		if(Rnd.getChance(24)) {
			if (changeState(state -> MirrorImage5(blendTime)))
				return;
		}
		if(Rnd.getChance(27)) {
			if (changeState(state -> MirrorImage6(blendTime)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> MirrorImage7(blendTime)))
				return;
		}
		if (changeState(state -> MirrorImage8(blendTime)))
			return;
		changeState(state -> MirrorImage_Logic(blendTime));
	}

	protected void MirrorImage1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x18A41E70L /*MirrorImage1*/);
		doAction(3889494241L /*MIRRORIMAGE1*/, blendTime, onDoActionEnd -> changeState(state -> Hide2(blendTime)));
	}

	protected void MirrorImage2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x80E9F586L /*MirrorImage2*/);
		doAction(1076115246L /*MIRRORIMAGE2*/, blendTime, onDoActionEnd -> changeState(state -> Hide2(blendTime)));
	}

	protected void MirrorImage3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9A07190L /*MirrorImage3*/);
		doAction(3826157512L /*MIRRORIMAGE3*/, blendTime, onDoActionEnd -> changeState(state -> Hide2(blendTime)));
	}

	protected void MirrorImage4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4CD91346L /*MirrorImage4*/);
		doAction(2248767963L /*MIRRORIMAGE4*/, blendTime, onDoActionEnd -> changeState(state -> Hide2(blendTime)));
	}

	protected void MirrorImage5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x212C7724L /*MirrorImage5*/);
		doAction(587907049L /*MIRRORIMAGE5*/, blendTime, onDoActionEnd -> changeState(state -> Hide2(blendTime)));
	}

	protected void MirrorImage6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF4082471L /*MirrorImage6*/);
		doAction(1178703879L /*MIRRORIMAGE6*/, blendTime, onDoActionEnd -> changeState(state -> Hide2(blendTime)));
	}

	protected void MirrorImage7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA0D3968EL /*MirrorImage7*/);
		doAction(3581773983L /*MIRRORIMAGE7*/, blendTime, onDoActionEnd -> changeState(state -> Hide2(blendTime)));
	}

	protected void MirrorImage8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x58A0D1D1L /*MirrorImage8*/);
		doAction(2427610611L /*MIRRORIMAGE8*/, blendTime, onDoActionEnd -> changeState(state -> Hide2(blendTime)));
	}

	protected void Hide2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCDE3905FL /*Hide2*/);
		doAction(1096688925L /*HIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> Hide2(blendTime), 1000));
	}

	protected void Seek_Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x17C35EA1L /*Seek_Teleport_Logic*/);
		doTeleport(EAIMoveDestType.SenderPosition, 0, 0, 0, 1);
		changeState(state -> TurningPoint2(blendTime));
	}

	protected void TurningPoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x66893464L /*TurningPoint2*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_4STAGE_BOSS_SEEK");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -391810, -300138, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Damage_KnockDown(blendTime), 1000)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		doAction(1664053560L /*BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> {
			setVariable(0x1E2360D6L /*_StunDelay_StartTime*/, getTime());
			scheduleState(state -> Battle_Wait2(blendTime), 1000);
		});
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_Ing(blendTime), 1000));
	}

	protected void Damage_Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E79F126L /*Damage_Stun_Ing*/);
		doAction(1531277180L /*DAMAGE_STUN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_End(blendTime), 10000));
	}

	protected void Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA432B7EDL /*Damage_Stun_End*/);
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Cancel_Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAA5F59E0L /*Damage_Cancel_Stun_Ing*/);
		doAction(3152228051L /*DAMAGE_CANCEL*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_End(blendTime), 5000));
	}

	protected void Special_Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEF3EFF55L /*Special_Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Special_Damage_Stun_Ing(blendTime), 1000));
	}

	protected void Special_Damage_Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB97BF3E2L /*Special_Damage_Stun_Ing*/);
		doAction(1531277180L /*DAMAGE_STUN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Special_Damage_Stun_End(blendTime), 10000));
	}

	protected void Special_Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC21C1C3DL /*Special_Damage_Stun_End*/);
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Skill1(blendTime), 1000));
	}

	protected void Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE70D4D89L /*Die_Logic*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().Turn_Undead(getActor(), null));
		changeState(state -> Die(blendTime));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Seek(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xCDE3905FL /*Hide2*/) {
			if (changeState(state -> Seek_Teleport_Logic(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
