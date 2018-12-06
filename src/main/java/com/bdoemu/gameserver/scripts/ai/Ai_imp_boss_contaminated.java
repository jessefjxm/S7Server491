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
@IAIName("imp_boss_contaminated")
public class Ai_imp_boss_contaminated extends CreatureAI {
	public Ai_imp_boss_contaminated(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0x9BF46A0EL /*_isTargetAvailable*/, 0);
		setVariable(0x5683F4E7L /*_talkInterval*/, 0);
		setVariable(0x33F46DE3L /*_talkInterval1*/, 0);
		setVariable(0x9EFA735FL /*_intervalSwitch*/, 0);
		setVariable(0x99703876L /*_SummonCount*/, 3);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(3376454098L /*START_SITDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> StartState(blendTime), 1000));
	}

	protected void Initialize_variable(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9F2024E1L /*Initialize_variable*/);
		setVariable(0x9BF46A0EL /*_isTargetAvailable*/, 0);
		setVariable(0x5683F4E7L /*_talkInterval*/, 0);
		setVariable(0x33F46DE3L /*_talkInterval1*/, 0);
		setVariable(0x9EFA735FL /*_intervalSwitch*/, 0);
		changeState(state -> StartState(blendTime));
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

	protected void StartState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF8AD4A11L /*StartState*/);
		if (getVariable(0x9EFA735FL /*_intervalSwitch*/) == 1 && getVariable(0x5683F4E7L /*_talkInterval*/) < 5) {
			setVariable(0x5683F4E7L /*_talkInterval*/, getVariable(0x5683F4E7L /*_talkInterval*/) + 1);
		}
		if (getVariable(0x9EFA735FL /*_intervalSwitch*/) == 1 && getVariable(0x5683F4E7L /*_talkInterval*/) >= 5) {
			setVariable(0x5683F4E7L /*_talkInterval*/, 0);
		}
		if (getVariable(0x9EFA735FL /*_intervalSwitch*/) == 2 && getVariable(0x33F46DE3L /*_talkInterval1*/) < 5) {
			setVariable(0x33F46DE3L /*_talkInterval1*/, getVariable(0x33F46DE3L /*_talkInterval1*/) + 1);
		}
		if (getVariable(0x9EFA735FL /*_intervalSwitch*/) == 2 && getVariable(0x33F46DE3L /*_talkInterval1*/) >= 5) {
			setVariable(0x33F46DE3L /*_talkInterval1*/, 0);
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Initialize_variable(0.3)))
				return;
		}
		if (getVariable(0x9BF46A0EL /*_isTargetAvailable*/) == 1 && target != null && getTargetLevel(target) > -5 && target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> StandUp_overLV30(0.2)))
				return;
		}
		if (getVariable(0x9BF46A0EL /*_isTargetAvailable*/) == 1 && target != null && getTargetLevel(target) < -5 && target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> StandUp_underLV30(0.2)))
				return;
		}
		if (getVariable(0x9BF46A0EL /*_isTargetAvailable*/) == 1 && getVariable(0x33F46DE3L /*_talkInterval1*/) == 0 && target != null && getTargetLevel(target) > -5 && target != null && getDistanceToTarget(target) < 1200) {
			if (changeState(state -> Talk_warning_overLV30(0.2)))
				return;
		}
		if (getVariable(0x9BF46A0EL /*_isTargetAvailable*/) == 1 && getVariable(0x33F46DE3L /*_talkInterval1*/) == 0 && target != null && getTargetLevel(target) < -5 && target != null && getDistanceToTarget(target) < 1200) {
			if (changeState(state -> Talk_warning_underLV30(0.2)))
				return;
		}
		if (getVariable(0x5683F4E7L /*_talkInterval*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
				if (changeState(state -> Talk_discover(0.2)))
					return;
			}
		}
		doAction(989954818L /*SIT*/, blendTime, onDoActionEnd -> scheduleState(state -> StartState(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Talk_discover(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA3001239L /*Talk_discover*/);
		setVariable(0x9BF46A0EL /*_isTargetAvailable*/, 1);
		setVariable(0x9EFA735FL /*_intervalSwitch*/, 1);
		doAction(2398044343L /*SIT_TALK_discover*/, blendTime, onDoActionEnd -> scheduleState(state -> StartState(blendTime), 500));
	}

	protected void Talk_warning_overLV30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8B0C1128L /*Talk_warning_overLV30*/);
		setVariable(0x9EFA735FL /*_intervalSwitch*/, 2);
		doAction(2146556748L /*SIT_TALK_overLV30*/, blendTime, onDoActionEnd -> scheduleState(state -> StartState(blendTime), 500));
	}

	protected void Talk_warning_underLV30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8F0D8F0BL /*Talk_warning_underLV30*/);
		setVariable(0x9EFA735FL /*_intervalSwitch*/, 2);
		doAction(1002591500L /*SIT_TALK_underLV30*/, blendTime, onDoActionEnd -> scheduleState(state -> StartState(blendTime), 500));
	}

	protected void StandUp_overLV30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x392166AEL /*StandUp_overLV30*/);
		doAction(1425945882L /*STANDUP_overLV30*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Battle(blendTime), 1200));
	}

	protected void StandUp_underLV30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA11C9E28L /*StandUp_underLV30*/);
		doAction(2759707866L /*STANDUP_underLV30*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Battle(blendTime), 1200));
	}

	protected void Start_Battle(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x21DF123AL /*Start_Battle*/);
		doAction(896823261L /*START_BATTLE*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x99703876L /*_SummonCount*/, 3);
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Return_Wait(blendTime), 1000)));
	}

	protected void Move_Return_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x47391F8FL /*Move_Return_Wait*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> StartState(blendTime), 1000)));
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
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x99703876L /*_SummonCount*/, 3);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 135 && getVariable(0xE5BD13F2L /*_Degree*/) <= 180) {
			if (changeState(state -> Attack_Turn_Right_180(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -25 && target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Battle_Turn_Left(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 25 && target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Battle_Turn_Right(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Battle_Walk_Back(0.3)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -25) {
			if (changeState(state -> Battle_Turn_Left(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 25) {
			if (changeState(state -> Battle_Turn_Right(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 75 && getVariable(0xEE19DBAEL /*Summon_Return*/) >= 3) {
			if (changeState(state -> Summon_Return(0.4)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 55 && getVariable(0xEE19DBAEL /*Summon_Return*/) >= 2) {
			if (changeState(state -> Summon_Return(0.4)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 35 && getVariable(0xEE19DBAEL /*Summon_Return*/) >= 1) {
			if (changeState(state -> Summon_Return(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Swing(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if (changeState(state -> Attack_Normal3(0.4)))
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
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (getSelfCombinePoint() == 13 && target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Chaser_Run_Slow(0.4)))
				return;
		}
		if (getSelfCombinePoint() != 13) {
			if(getCallCount() == 10) {
				if (changeState(state -> Chaser_Run(0.4)))
					return;
			}
		}
		if (getSelfCombinePoint() == 13) {
			if(getCallCount() == 10) {
				if (changeState(state -> Chaser_Run_Slow(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) >= 350 && getSelfCombinePoint() == 13) {
			if (changeState(state -> Move_Chaser_Slow(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Swing(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if (changeState(state -> Attack_Normal3(0.4)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void Move_Chaser_Slow(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x647AEB9AL /*Move_Chaser_Slow*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (getSelfCombinePoint() == 13 && target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Chaser_Run_Slow(0.4)))
				return;
		}
		if (getSelfCombinePoint() != 13) {
			if(getCallCount() == 10) {
				if (changeState(state -> Chaser_Run(0.4)))
					return;
			}
		}
		if (getSelfCombinePoint() == 13) {
			if(getCallCount() == 10) {
				if (changeState(state -> Chaser_Run_Slow(0.4)))
					return;
			}
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) >= 350) {
			if (changeState(state -> Move_Chaser(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if (changeState(state -> Attack_Normal3(0.4)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser_Slow(blendTime), 100)));
	}

	protected void Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE2DFC297L /*Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getSelfCombinePoint() == 13 && target != null && getDistanceToTarget(target) >= 350) {
			if (changeState(state -> Chaser_Run_Slow(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Swing(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if (changeState(state -> Attack_Normal3(0.4)))
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
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 100)));
	}

	protected void Chaser_Run_Slow(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x15BA24B4L /*Chaser_Run_Slow*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 7000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getSelfCombinePoint() != 13 && target != null && getDistanceToTarget(target) >= 350) {
			if (changeState(state -> Chaser_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if (changeState(state -> Attack_Normal3(0.4)))
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
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run_Slow(blendTime), 100)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 350) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal2(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Normal3(0.4)))
					return;
			}
		}
		doAction(662185133L /*BATTLE_WALK_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 100)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1664053560L /*BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Turn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE5006100L /*Attack_Turn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1915812192L /*BATTLE_TURN_180_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Dash_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBE799415L /*Dash_Ready*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3487632696L /*DASH*/, blendTime, onDoActionEnd -> changeState(state -> Dash(blendTime)));
	}

	protected void Dash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xD719C96L /*Dash*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Attack_Jump(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> Dash_End(0.3)))
				return;
		}
		doAction(3753560097L /*DASH_A*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Dash(blendTime), 100)));
	}

	protected void Dash_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC201A136L /*Dash_End*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if(Rnd.getChance(20)) {
			if (changeState(state -> Attack_Normal2(0.2)))
				return;
		}
		doAction(452034032L /*DASH_B*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 200));
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
		doAction(1531277180L /*DAMAGE_STUN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_End(blendTime), 5000));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5F2C1966L /*Attack_Normal3*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2957630933L /*ATTACK_NORMAL3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8BC3ABDL /*Attack_Jump*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2705621134L /*ATTACK_JUMP*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Jump_End(blendTime)));
	}

	protected void Attack_Jump_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEFCBB00FL /*Attack_Jump_End*/);
		doAction(1056951149L /*ATTACK_JUMP_A*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_Run_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x32A6DB53L /*Attack_Run_Jump*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1336135617L /*ATTACK_RUN_JUMP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Swing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDC0D3EEL /*Attack_Swing*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(219042788L /*ATTACKSKILL_SWING_A1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Swing_ing(blendTime)));
	}

	protected void Attack_Swing_ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD97E2752L /*Attack_Swing_ing*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3528707147L /*ATTACKSKILL_SWING_A2*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Swing_end(blendTime)));
	}

	protected void Attack_Swing_end(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x76BD5033L /*Attack_Swing_end*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2968694518L /*ATTACKSKILL_SWING_B1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Summon_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEE19DBAEL /*Summon_Return*/);
		setVariable(0x99703876L /*_SummonCount*/, getVariable(0x99703876L /*_SummonCount*/) - 1);
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 10, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Attack_Summon(blendTime), 1000)));
	}

	protected void Attack_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4A9D8D69L /*Attack_Summon*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(4003178407L /*ATTACK_SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Summon2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8598B162L /*Attack_Summon2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x99703876L /*_SummonCount*/, getVariable(0x99703876L /*_SummonCount*/) - 1);
		doAction(1441273400L /*ATTACK_SUMMON_2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x5683F4E7L /*_talkInterval*/) == 0 && target != null && getTargetHp(target) > 0 && getState() == 0xF8AD4A11L /*StartState*/ && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Talk_discover(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetLevel(target) > -5 && getState() == 0xF8AD4A11L /*StartState*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> StandUp_overLV30(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && getTargetLevel(target) < -5 && getState() == 0xF8AD4A11L /*StartState*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> StandUp_underLV30(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && getTargetLevel(target) > -5 && getState() == 0xA3001239L /*Talk_discover*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> StandUp_overLV30(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && getTargetLevel(target) < -5 && getState() == 0xA3001239L /*Talk_discover*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> StandUp_underLV30(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && getTargetLevel(target) > -5 && getState() == 0x8B0C1128L /*Talk_warning_overLV30*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> StandUp_overLV30(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && getTargetLevel(target) < -5 && getState() == 0x8F0D8F0BL /*Talk_warning_underLV30*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			if (changeState(state -> StandUp_underLV30(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x47391F8FL /*Move_Return_Wait*/) {
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleUpdateCombineWave(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target == null) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		}
		if ((getState() == 0xEE19DBAEL /*Summon_Return*/ || getState() == 0xF8AD4A11L /*StartState*/) && getSelfCombinePoint() == 14) {
			if (changeState(state -> Damage_Cancel_Stun_Ing(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfCombinePoint() == 59 && (getState() != 0x3FB3341CL /*Damage_Stun*/ || getState() != 0x2E79F126L /*Damage_Stun_Ing*/ || getState() != 0xA432B7EDL /*Damage_Stun_End*/ || getState() != 0xC8BC3ABDL /*Attack_Jump*/ || getState() != 0x32A6DB53L /*Attack_Run_Jump*/)) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
