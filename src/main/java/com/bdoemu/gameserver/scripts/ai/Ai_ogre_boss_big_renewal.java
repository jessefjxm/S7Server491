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
@IAIName("ogre_boss_big_renewal")
public class Ai_ogre_boss_big_renewal extends CreatureAI {
	public Ai_ogre_boss_big_renewal(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xE4C9AD93L /*_HeadStunCount*/, 0);
		setVariable(0x244677B1L /*_DownAttackCount*/, 0);
		setVariable(0x76D19707L /*_DownLeg_L_Count*/, 0);
		setVariable(0x17ADE38BL /*_DownLeg_R_Count*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0x457D0F1EL /*_Awaken*/, 0);
		setVariable(0x3BDC5BFCL /*_StandUpCount*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x28019FF7L /*_Summon_StartTime2*/, 0);
		setVariable(0x7442A4F5L /*_Summon_IngTime2*/, 0);
		setVariable(0xDA1437CBL /*_Summon_EndTime2*/, 0);
		setVariable(0xB5B81E5EL /*_CannonCount*/, 0);
		setVariable(0x1E2360D6L /*_StunDelay_StartTime*/, 0);
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, 0);
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, 0);
		doAction(3086018745L /*GHOST_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Spawn_Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		setVariable(0x7442A4F5L /*_Summon_IngTime2*/, getTime());
		setVariable(0xDA1437CBL /*_Summon_EndTime2*/, getVariable(0x7442A4F5L /*_Summon_IngTime2*/) - getVariable(0x28019FF7L /*_Summon_StartTime2*/));
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3000)) {
				if (changeState(state -> Detect_Target(0.3)))
					return;
			}
		}
		if (getVariable(0x457D0F1EL /*_Awaken*/) > 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3000)) {
				if (changeState(state -> Detect_Awaken_Target(0.3)))
					return;
			}
		}
		if (getVariable(0xDA1437CBL /*_Summon_EndTime2*/) >= 1800000) {
			if (changeState(state -> Time_Out(0.3)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 3600000 && getVariable(0x3F487035L /*_Hp*/) > 50) {
			if (changeState(state -> Time_Out(0.3)))
				return;
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Move_Random(0.4)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Spawn_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD4B89BD8L /*Spawn_Wait*/);
		doAction(3086018745L /*GHOST_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Spawn_Wait(blendTime), 1000));
	}

	protected void Start_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x615A198AL /*Start_Logic*/);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x28019FF7L /*_Summon_StartTime2*/, getTime());
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
		doAction(3086018745L /*GHOST_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Wait(blendTime), 1000));
	}

	protected void Start_Logic_Test(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9C5A451EL /*Start_Logic_Test*/);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0x28019FF7L /*_Summon_StartTime2*/, getTime());
		changeState(state -> Start_Wait_Test(blendTime));
	}

	protected void Start_Wait_Test(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA32045D3L /*Start_Wait_Test*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 5000) {
			if (changeState(state -> Start_Action(0.3)))
				return;
		}
		doAction(3086018745L /*GHOST_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Wait_Test(blendTime), 1000));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(2944862601L /*STARTACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 3000)) {
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
		setBehavior(EAIBehavior.action);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Logic(blendTime)));
	}

	protected void Detect_Awaken_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6BB6950L /*Detect_Awaken_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(2247142837L /*SEARCH_AWAKEN_ENEMY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Logic(blendTime)));
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		clearAggro(true);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait_L(0.2)))
					return;
			}
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 3000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait_R(0.2)))
					return;
			}
		}
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 0 && getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 3000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.2)))
					return;
			}
		}
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 1 && getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 3000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Awaken_Wait(0.2)))
					return;
			}
		}
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 2 && getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 3000 && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_SuperAwaken_Wait(0.2)))
					return;
			}
		}
		scheduleState(state -> Move_Return(blendTime), 1000);
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		clearAggro(true);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xB5B81E5EL /*_CannonCount*/, 0);
		doAction(3763948041L /*RETURN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			setVariable(0x28019FF7L /*_Summon_StartTime2*/, getTime());
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
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 5) {
			if (changeState(state -> FailFindPathToTarget(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Logic(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Check_Battle_Wait_Logic(blendTime), 1000));
	}

	protected void Check_Battle_Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8977CF70L /*Check_Battle_Wait_Logic*/);
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 1) {
			if (changeState(state -> Battle_Awaken_Wait(0.3)))
				return;
		}
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 2) {
			if (changeState(state -> Battle_SuperAwaken_Wait(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void TargetChange_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A6D56A5L /*TargetChange_Logic*/);
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 0) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 1) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_Awaken_Wait(0.3)))
					return;
			}
		}
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 2) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && getTargetHp(object) > 0 && isCreatureVisible(object, false))) {
				if (changeState(state -> Battle_SuperAwaken_Wait(0.3)))
					return;
			}
		}
		changeState(state -> TargetLost(blendTime));
	}

	protected void Turn_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA55D3525L /*Turn_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4240056895L /*TURN_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1700C253L /*Turn_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1642243093L /*TURN_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x828FBC91L /*Turn_180*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1676409899L /*TURN_180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Turn_L_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6A65A5CBL /*Turn_L_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(748625070L /*ATTACK_TURN_L*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_LA(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_RA(0.2)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Turn_R_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x11A3009AL /*Turn_R_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3533086974L /*ATTACK_TURN_R*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_LA(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_RA(0.2)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Turn_Awaken_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE2F3474BL /*Turn_Awaken_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3296360432L /*TURN_AWAKEN_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Awaken_Wait(blendTime)));
	}

	protected void Turn_Awaken_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7FA4CE1BL /*Turn_Awaken_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(10536537L /*TURN_AWAKEN_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Awaken_Wait(blendTime)));
	}

	protected void Turn_Awaken_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA7EA8F8DL /*Turn_Awaken_180*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3033810479L /*TURN_AWAKEN_180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Awaken_Wait(blendTime)));
	}

	protected void Turn_Awaken_L_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4F3B35L /*Turn_Awaken_L_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2738164196L /*ATTACK_AWAKEN_TURN_L*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 3000) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Awaken_Special(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_Awaken_LA(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_Awaken_RA(0.2)))
						return;
				}
			}
			changeState(state -> Battle_Awaken_Wait(blendTime));
		});
	}

	protected void Turn_Awaken_R_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD3C0B9DAL /*Turn_Awaken_R_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1933301072L /*ATTACK_AWAKEN_TURN_R*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 3000) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Awaken_Special(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_Awaken_LA(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_Awaken_RA(0.2)))
						return;
				}
			}
			changeState(state -> Battle_Awaken_Wait(blendTime));
		});
	}

	protected void Turn_SuperAwaken_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x627AFBDEL /*Turn_SuperAwaken_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1770083508L /*TURN_SUPERAWAKEN_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_SuperAwaken_Wait(blendTime)));
	}

	protected void Turn_SuperAwaken_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x34639178L /*Turn_SuperAwaken_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2241611882L /*TURN_SUPERAWAKEN_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_SuperAwaken_Wait(blendTime)));
	}

	protected void Turn_SuperAwaken_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDEDEE977L /*Turn_SuperAwaken_180*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1512568169L /*TURN_SUPERAWAKEN_180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_SuperAwaken_Wait(blendTime)));
	}

	protected void Turn_SuperAwaken_L_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEAC8C515L /*Turn_SuperAwaken_L_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3667337681L /*ATTACK_SUPERAWAKEN_TURN_L*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 3000) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_SuperAwaken_Special(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_SuperAwaken_LA(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_SuperAwaken_RA(0.2)))
						return;
				}
			}
			changeState(state -> Battle_SuperAwaken_Wait(blendTime));
		});
	}

	protected void Turn_SuperAwaken_R_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7BFCEB84L /*Turn_SuperAwaken_R_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1122159576L /*ATTACK_SUPERAWAKEN_TURN_R*/, blendTime, onDoActionEnd -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 3000) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_SuperAwaken_Special(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_SuperAwaken_LA(0.2)))
						return;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 500) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_SuperAwaken_RA(0.2)))
						return;
				}
			}
			changeState(state -> Battle_SuperAwaken_Wait(blendTime));
		});
	}

	protected void Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x31999313L /*Turn_Logic*/);
		if (target != null && getAngleToTarget(target) <= -70 && target != null && getDistanceToTarget(target) <= 600) {
			if (changeState(state -> Turn_L_Attack(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70 && target != null && getDistanceToTarget(target) <= 600) {
			if (changeState(state -> Turn_R_Attack(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -140 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 140 && target != null && getAngleToTarget(target) <= 180 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -70 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_L(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_R(0.2)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Turn_Awaken_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x598ECD2CL /*Turn_Awaken_Logic*/);
		if (target != null && getAngleToTarget(target) <= -70 && target != null && getDistanceToTarget(target) <= 600) {
			if (changeState(state -> Turn_Awaken_L_Attack(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70 && target != null && getDistanceToTarget(target) <= 600) {
			if (changeState(state -> Turn_Awaken_R_Attack(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -140 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_Awaken_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 140 && target != null && getAngleToTarget(target) <= 180 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_Awaken_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -70 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_Awaken_L(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_Awaken_R(0.2)))
				return;
		}
		changeState(state -> Battle_Awaken_Wait(blendTime));
	}

	protected void Turn_SuperAwaken_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x86ECA556L /*Turn_SuperAwaken_Logic*/);
		if (target != null && getAngleToTarget(target) <= -70 && target != null && getDistanceToTarget(target) <= 600) {
			if (changeState(state -> Turn_SuperAwaken_L_Attack(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70 && target != null && getDistanceToTarget(target) <= 600) {
			if (changeState(state -> Turn_SuperAwaken_R_Attack(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -140 && target != null && getAngleToTarget(target) >= -179 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_SuperAwaken_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 140 && target != null && getAngleToTarget(target) <= 180 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_SuperAwaken_180(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -70 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_SuperAwaken_L(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70 && target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Turn_SuperAwaken_R(0.2)))
				return;
		}
		changeState(state -> Battle_SuperAwaken_Wait(blendTime));
	}

	protected void Move_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDA342E1BL /*Move_Logic*/);
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Move_Back(0.2)))
				return;
		}
		if (changeState(state -> Move_Around(0.2)))
			return;
		changeState(state -> Move_Logic(blendTime));
	}

	protected void Move_Awaken_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7080AFEFL /*Move_Awaken_Logic*/);
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Move_Awaken_Back(0.2)))
				return;
		}
		if (changeState(state -> Move_Awaken_Around(0.2)))
			return;
		changeState(state -> Move_Awaken_Logic(blendTime));
	}

	protected void Move_SuperAwaken_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5BBFA145L /*Move_SuperAwaken_Logic*/);
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Move_SuperAwaken_Around(0.2)))
				return;
		}
		if (changeState(state -> Move_SuperAwaken_Back(0.2)))
			return;
		changeState(state -> Move_SuperAwaken_Logic(blendTime));
	}

	protected void Jump_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x56E60BCBL /*Jump_Logic*/);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getAngleToTarget(target) <= 30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= -30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= 30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump_L(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= -30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Jump_L(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= 30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Jump_R(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= -30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Jump_R(0.2)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Jump_Awaken_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBC39C1A8L /*Jump_Awaken_Logic*/);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getAngleToTarget(target) <= 30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Awaken_Jump(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= -30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Awaken_Jump(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= 30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Awaken_Jump_L(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= -30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Awaken_Jump_L(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= 30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Awaken_Jump_R(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= -30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Awaken_Jump_R(0.2)))
				return;
		}
		changeState(state -> Battle_Awaken_Wait(blendTime));
	}

	protected void Jump_SuperAwaken_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBD931BABL /*Jump_SuperAwaken_Logic*/);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getAngleToTarget(target) <= 30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_SuperAwaken_Jump(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= -30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_SuperAwaken_Jump(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= 30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_SuperAwaken_Jump_L(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) >= -30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_SuperAwaken_Jump_L(0.2)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) <= 30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_SuperAwaken_Jump_R(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= -30 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_SuperAwaken_Jump_R(0.2)))
				return;
		}
		changeState(state -> Battle_SuperAwaken_Wait(blendTime));
	}

	protected void Attack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x50B0953FL /*Attack_Logic*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Jump_Logic(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= -30 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Jump_Logic(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Roar(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Kick(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_LK(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Swing_L(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_RK(0.2)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Swing_R(0.2)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Attack_Awaken_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDABD6C7CL /*Attack_Awaken_Logic*/);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Jump_Awaken_Logic(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= -30 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Jump_Awaken_Logic(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Awaken_Special(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Awaken_Roar(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Awaken_Kick(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Awaken_LK(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Awaken_Swing_L(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Awaken_RK(0.2)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Awaken_Swing_R(0.2)))
				return;
		}
		changeState(state -> Battle_Awaken_Wait(blendTime));
	}

	protected void Attack_SuperAwaken_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x980FA836L /*Attack_SuperAwaken_Logic*/);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) <= 30 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Jump_SuperAwaken_Logic(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getAngleToTarget(target) >= -30 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Jump_SuperAwaken_Logic(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_SuperAwaken_Special(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_SuperAwaken_Roar(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_SuperAwaken_Kick(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_SuperAwaken_LK(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_SuperAwaken_Swing_L(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_SuperAwaken_RK(0.2)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) < 1200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_SuperAwaken_Swing_R(0.2)))
				return;
		}
		changeState(state -> Battle_SuperAwaken_Wait(blendTime));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x17ADE38BL /*_DownLeg_R_Count*/, 0);
		setVariable(0x76D19707L /*_DownLeg_L_Count*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 3600000 && getVariable(0x3F487035L /*_Hp*/) > 50) {
			if (changeState(state -> Time_Out(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 70 && getVariable(0x457D0F1EL /*_Awaken*/) == 0) {
			if (changeState(state -> Attack_Special(0.2)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70) {
			if (changeState(state -> Turn_Logic(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -70) {
			if (changeState(state -> Turn_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Move_Logic(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Logic(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1000) {
			if (changeState(state -> Move_Chaser(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Move_Chaser(0.4)))
				return;
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Awaken_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA2B7A10L /*Battle_Awaken_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x17ADE38BL /*_DownLeg_R_Count*/, 0);
		setVariable(0x76D19707L /*_DownLeg_L_Count*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 3600000 && getVariable(0x3F487035L /*_Hp*/) > 50) {
			if (changeState(state -> Time_Out(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0x457D0F1EL /*_Awaken*/) == 1) {
			if (changeState(state -> Attack_Special(0.2)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70) {
			if (changeState(state -> Turn_Awaken_Logic(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -70) {
			if (changeState(state -> Turn_Awaken_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Move_Awaken_Logic(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Awaken_Logic(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1000) {
			if (changeState(state -> Move_Awaken_Chaser(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_Awaken_Chaser(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Move_Awaken_Chaser(0.4)))
				return;
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(2626724008L /*BATTLE_AWAKEN_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Awaken_Wait(blendTime), 100));
	}

	protected void Battle_SuperAwaken_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x608569B0L /*Battle_SuperAwaken_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x17ADE38BL /*_DownLeg_R_Count*/, 0);
		setVariable(0x76D19707L /*_DownLeg_L_Count*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 3600000 && getVariable(0x3F487035L /*_Hp*/) > 50) {
			if (changeState(state -> Time_Out(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70) {
			if (changeState(state -> Turn_SuperAwaken_Logic(0.2)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -70) {
			if (changeState(state -> Turn_SuperAwaken_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Move_SuperAwaken_Logic(0.2)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 1000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_SuperAwaken_Logic(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1000) {
			if (changeState(state -> Move_SuperAwaken_Chaser(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Move_SuperAwaken_Chaser(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Move_SuperAwaken_Chaser(0.4)))
				return;
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(2626724008L /*BATTLE_AWAKEN_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_SuperAwaken_Wait(blendTime), 100));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70 && target != null && getAngleToTarget(target) <= -70) {
			if (changeState(state -> Turn_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1500 && target != null && getDistanceToTarget(target) < 4000) {
			if (changeState(state -> Run_Start(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Run_Start(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Run_Start(0.4)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void Move_Awaken_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x859C2A3BL /*Move_Awaken_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70 && target != null && getAngleToTarget(target) <= -70) {
			if (changeState(state -> Turn_Awaken_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Awaken_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1500 && target != null && getDistanceToTarget(target) < 4000) {
			if (changeState(state -> Run_Awaken_Start(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Run_Awaken_Start(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Run_Awaken_Start(0.4)))
				return;
		}
		doAction(293903693L /*BATTLE_AWAKEN_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Awaken_Chaser(blendTime), 1000)));
	}

	protected void Move_SuperAwaken_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x260AA4BFL /*Move_SuperAwaken_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 70 && target != null && getAngleToTarget(target) <= -70) {
			if (changeState(state -> Turn_SuperAwaken_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_SuperAwaken_Logic(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 1500 && target != null && getDistanceToTarget(target) < 4000) {
			if (changeState(state -> Run_SuperAwaken_Start(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Run_SuperAwaken_Start(0.4)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Run_SuperAwaken_Start(0.4)))
				return;
		}
		doAction(4100098651L /*BATTLE_SUPERAWAKEN_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_SuperAwaken_Chaser(blendTime), 1000)));
	}

	protected void Run_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x58BAF51EL /*Run_Start*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2897757226L /*RUN_START*/, blendTime, onDoActionEnd -> scheduleState(state -> Run(blendTime), 500));
	}

	protected void Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x6D77C592L /*Run*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Jump_Logic(0.2)))
				return;
		}
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Run(blendTime), 2000)));
	}

	protected void Run_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x81666AB5L /*Run_Stop*/);
		doAction(881069330L /*RUN_STOP*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Logic(blendTime)));
	}

	protected void Run_Awaken_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAB57AA0FL /*Run_Awaken_Start*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1055317977L /*RUN_AWAKEN_START*/, blendTime, onDoActionEnd -> scheduleState(state -> Run(blendTime), 500));
	}

	protected void Run_Awaken(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x310FDF22L /*Run_Awaken*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Jump_Awaken_Logic(0.2)))
				return;
		}
		doAction(415169954L /*RUN_AWAKEN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Run(blendTime), 2000)));
	}

	protected void Run_Awaken_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA93E950AL /*Run_Awaken_Stop*/);
		doAction(4170978744L /*RUN_AWAKEN_STOP*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Awaken_Logic(blendTime)));
	}

	protected void Run_SuperAwaken_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x109E8228L /*Run_SuperAwaken_Start*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2562797788L /*RUN_SUPERAWAKEN_START*/, blendTime, onDoActionEnd -> scheduleState(state -> Run(blendTime), 500));
	}

	protected void Run_SuperAwaken(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x66FC86BAL /*Run_SuperAwaken*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Jump_SuperAwaken_Logic(0.2)))
				return;
		}
		doAction(2679403290L /*RUN_SUPERAWAKEN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Run(blendTime), 2000)));
	}

	protected void Run_SuperAwaken_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x327983BL /*Run_SuperAwaken_Stop*/);
		doAction(2639481409L /*RUN_SUPERAWAKEN_STOP*/, blendTime, onDoActionEnd -> changeState(state -> Attack_SuperAwaken_Logic(blendTime)));
	}

	protected void Move_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x55F437ADL /*Move_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> moveAround(800 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_RA(0.2)))
						return true;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if (changeState(state -> Attack_RK(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 2000 + Rnd.get(-2000,2000))));
	}

	protected void Move_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xDD3BDB77L /*Move_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(662185133L /*BATTLE_WALK_BACK*/, blendTime, onDoActionEnd -> escape(800, () -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1200) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_Kick(0.3)))
						return true;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1000) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_Swing_L(0.3)))
						return true;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1000) {
				if (changeState(state -> Attack_Swing_R(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 2000 + Rnd.get(-2000,2000))));
	}

	protected void Move_Awaken_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0xE859E190L /*Move_Awaken_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(293903693L /*BATTLE_AWAKEN_WALK*/, blendTime, onDoActionEnd -> moveAround(800 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_Awaken_RA(0.2)))
						return true;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if (changeState(state -> Attack_Awaken_RK(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Awaken_Wait(blendTime), 2000 + Rnd.get(-2000,2000))));
	}

	protected void Move_Awaken_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x4D408107L /*Move_Awaken_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(839498423L /*BATTLE_AWAKEN_WALK_BACK*/, blendTime, onDoActionEnd -> escape(800, () -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1200) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_Awaken_Kick(0.3)))
						return true;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1000) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_Awaken_Swing_L(0.3)))
						return true;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1000) {
				if (changeState(state -> Attack_Awaken_Swing_R(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Awaken_Wait(blendTime), 2000 + Rnd.get(-2000,2000))));
	}

	protected void Move_SuperAwaken_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0xFCAC783AL /*Move_SuperAwaken_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4100098651L /*BATTLE_SUPERAWAKEN_WALK*/, blendTime, onDoActionEnd -> moveAround(800 + Rnd.get(600, 1000), ENaviType.ground, () -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if(Rnd.getChance(50)) {
					if (changeState(state -> Attack_SuperAwaken_RA(0.2)))
						return true;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 600) {
				if (changeState(state -> Attack_Awaken_RK(0.2)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_SuperAwaken_Wait(blendTime), 2000 + Rnd.get(-2000,2000))));
	}

	protected void Move_SuperAwaken_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x8A417D40L /*Move_SuperAwaken_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(104453225L /*BATTLE_SUPERAWAKEN_WALK_BACK*/, blendTime, onDoActionEnd -> escape(800, () -> {
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1200) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_SuperAwaken_Kick(0.3)))
						return true;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1000) {
				if(Rnd.getChance(30)) {
					if (changeState(state -> Attack_SuperAwaken_Swing_L(0.3)))
						return true;
				}
			}
			if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1000) {
				if (changeState(state -> Attack_SuperAwaken_Swing_R(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_SuperAwaken_Wait(blendTime), 2000 + Rnd.get(-2000,2000))));
	}

	protected void Time_Out(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC7437E2BL /*Time_Out*/);
		worldNotify(EChatNoticeType.Nuberu, "GAME", "LUA_OGREBOSS_TIMEOUT");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2737950888L /*DELETE_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 5000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Attack_Kick(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC369511CL /*Attack_Kick*/);
		doAction(2028143634L /*ATTACK_KICK*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_LA(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6BB75C4L /*Attack_LA*/);
		doAction(4161783082L /*ATTACK_LA*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_RA(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8E159DDAL /*Attack_RA*/);
		doAction(3168864375L /*ATTACK_RA*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_LK(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x92BFF6AEL /*Attack_LK*/);
		doAction(1897199426L /*ATTACK_LK*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_RK(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x76B393E3L /*Attack_RK*/);
		doAction(37172972L /*ATTACK_RK*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Swing_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1E76385FL /*Attack_Swing_L*/);
		doAction(3548371962L /*ATTACK_SWING_L*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Swing_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x24DBCBF3L /*Attack_Swing_R*/);
		doAction(2462351423L /*ATTACK_SWING_R*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5ABF220L /*Attack_Roar*/);
		doAction(93247687L /*ATTACK_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Special(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xECC10BCL /*Attack_Special*/);
		setVariable(0x457D0F1EL /*_Awaken*/, getVariable(0x457D0F1EL /*_Awaken*/) + 1);
		doAction(1546274521L /*ATTACK_SPECIAL*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_Kick(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDEF2E664L /*Attack_Awaken_Kick*/);
		doAction(1581660525L /*ATTACK_AWAKEN_KICK*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_LA(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2A31583L /*Attack_Awaken_LA*/);
		doAction(1256510192L /*ATTACK_AWAKEN_LA*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_RA(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF8AB2B1FL /*Attack_Awaken_RA*/);
		doAction(215448223L /*ATTACK_AWAKEN_RA*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_LK(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1944F5CL /*Attack_Awaken_LK*/);
		doAction(1288557413L /*ATTACK_AWAKEN_LK*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_RK(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9D3D5EFBL /*Attack_Awaken_RK*/);
		doAction(2897093997L /*ATTACK_AWAKEN_RK*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_Swing_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x262DFFDCL /*Attack_Awaken_Swing_L*/);
		doAction(1180981918L /*ATTACK_AWAKEN_SWING_L*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_Swing_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x91105150L /*Attack_Awaken_Swing_R*/);
		doAction(3243365034L /*ATTACK_AWAKEN_SWING_R*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4ED38B1BL /*Attack_Awaken_Roar*/);
		doAction(2777263671L /*ATTACK_AWAKEN_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_Special(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBAB5BE21L /*Attack_Awaken_Special*/);
		doAction(2950848591L /*ATTACK_AWAKEN_SPECIAL*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_Kick(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3277DB30L /*Attack_SuperAwaken_Kick*/);
		doAction(2956204030L /*ATTACK_SUPERAWAKEN_KICK*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_LA(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEF879DD3L /*Attack_SuperAwaken_LA*/);
		doAction(930046085L /*ATTACK_SUPERAWAKEN_LA*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_RA(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x33E373F9L /*Attack_SuperAwaken_RA*/);
		doAction(678656953L /*ATTACK_SUPERAWAKEN_RA*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_LK(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x42492264L /*Attack_SuperAwaken_LK*/);
		doAction(4128544727L /*ATTACK_SUPERAWAKEN_LK*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_RK(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x76F48F7FL /*Attack_SuperAwaken_RK*/);
		doAction(1031636544L /*ATTACK_SUPERAWAKEN_RK*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_Swing_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDB47C059L /*Attack_SuperAwaken_Swing_L*/);
		doAction(2905325814L /*ATTACK_SUPERAWAKEN_SWING_L*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_Swing_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4190681L /*Attack_SuperAwaken_Swing_R*/);
		doAction(1629457196L /*ATTACK_SUPERAWAKEN_SWING_R*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x88ADB1E1L /*Attack_SuperAwaken_Roar*/);
		doAction(1209778134L /*ATTACK_SUPERAWAKEN_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_Special(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8697B7D5L /*Attack_SuperAwaken_Special*/);
		doAction(4061662307L /*ATTACK_SUPERAWAKEN_SPECIAL*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC8BC3ABDL /*Attack_Jump*/);
		doAction(522473655L /*ATTACK_JUMP_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Jump_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD0F781DDL /*Attack_Jump_L*/);
		doAction(1521313702L /*ATTACK_JUMP_L_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Jump_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD33E54E7L /*Attack_Jump_R*/);
		doAction(2778609938L /*ATTACK_JUMP_R_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x754F6237L /*Attack_Awaken_Jump*/);
		doAction(4183713853L /*ATTACK_AWAKEN_JUMP_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_Jump_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF7014F00L /*Attack_Awaken_Jump_L*/);
		doAction(808440833L /*ATTACK_AWAKEN_JUMP_L_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_Awaken_Jump_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3C6A1183L /*Attack_Awaken_Jump_R*/);
		doAction(3987450898L /*ATTACK_AWAKEN_JUMP_R_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_Jump(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x19629689L /*Attack_SuperAwaken_Jump*/);
		doAction(3583775820L /*ATTACK_SUPERAWAKEN_JUMP_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_Jump_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1F636290L /*Attack_SuperAwaken_Jump_L*/);
		doAction(2013194080L /*ATTACK_SUPERAWAKEN_JUMP_L_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Attack_SuperAwaken_Jump_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x265BD6A4L /*Attack_SuperAwaken_Jump_R*/);
		doAction(2905178823L /*ATTACK_SUPERAWAKEN_JUMP_R_START*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void Stun_Head(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD02F4B6AL /*Stun_Head*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xE4C9AD93L /*_HeadStunCount*/, 1);
		doAction(3269588683L /*STUN_HEAD*/, blendTime, onDoActionEnd -> changeState(state -> Stun_Head_End(blendTime)));
	}

	protected void Stun_Head_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFFF980D0L /*Stun_Head_End*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xE4C9AD93L /*_HeadStunCount*/, 0);
		doAction(33463453L /*STUN_HEAD_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Logic(blendTime)));
	}

	protected void Damage_Leg_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBFAAE59EL /*Damage_Leg_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xB5B81E5EL /*_CannonCount*/, 0);
		setVariable(0x76D19707L /*_DownLeg_L_Count*/, 1);
		doAction(3085944822L /*DOWN_LEG_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_L(blendTime)));
	}

	protected void Down_Leg_L_StandUp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD832C0D5L /*Down_Leg_L_StandUp*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0x76D19707L /*_DownLeg_L_Count*/, 0);
		setVariable(0x244677B1L /*_DownAttackCount*/, 0);
		setVariable(0x3BDC5BFCL /*_StandUpCount*/, getVariable(0x3BDC5BFCL /*_StandUpCount*/) + 1);
		doAction(872223492L /*DOWN_LEG_L_B*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Logic(blendTime)));
	}

	protected void Damage_Leg_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB819C33L /*Damage_Leg_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0x17ADE38BL /*_DownLeg_R_Count*/, 1);
		setVariable(0xB5B81E5EL /*_CannonCount*/, 0);
		doAction(4066199635L /*DOWN_LEG_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_R(blendTime)));
	}

	protected void Down_Leg_R_StandUp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCFDFD27DL /*Down_Leg_R_StandUp*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0x17ADE38BL /*_DownLeg_R_Count*/, 0);
		setVariable(0x244677B1L /*_DownAttackCount*/, 0);
		setVariable(0x3BDC5BFCL /*_StandUpCount*/, getVariable(0x3BDC5BFCL /*_StandUpCount*/) + 1);
		doAction(3119195984L /*DOWN_LEG_R_B*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_Logic(blendTime)));
	}

	protected void Battle_Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8A9BEB3L /*Battle_Wait_Logic*/);
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.2)))
					return;
			}
		}
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Awaken_Wait(0.2)))
					return;
			}
		}
		if (getVariable(0x457D0F1EL /*_Awaken*/) == 2) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_SuperAwaken_Wait(0.2)))
					return;
			}
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		scheduleState(state -> Battle_Wait_Logic(blendTime), 1000);
	}

	protected void Battle_Wait_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x17A4B6AFL /*Battle_Wait_L*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x244677B1L /*_DownAttackCount*/) > 5) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Down_Leg_L_StandUp(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 70 && getVariable(0x3BDC5BFCL /*_StandUpCount*/) == 0) {
			if (changeState(state -> Down_Leg_L_StandUp(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0x3BDC5BFCL /*_StandUpCount*/) == 1) {
			if (changeState(state -> Down_Leg_L_StandUp(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Down_Leg_L(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Down_Leg_L2(0.2)))
					return;
			}
		}
		doAction(701989706L /*BATTLE_WAIT_L*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_L(blendTime), 1000));
	}

	protected void Battle_Wait_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCA1A191AL /*Battle_Wait_R*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x244677B1L /*_DownAttackCount*/) > 5) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Down_Leg_R_StandUp(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 70 && getVariable(0x3BDC5BFCL /*_StandUpCount*/) == 0) {
			if (changeState(state -> Down_Leg_R_StandUp(0.3)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) < 30 && getVariable(0x3BDC5BFCL /*_StandUpCount*/) == 1) {
			if (changeState(state -> Down_Leg_R_StandUp(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Down_Leg_R(0.2)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Down_Leg_R2(0.2)))
					return;
			}
		}
		doAction(627768267L /*BATTLE_WAIT_R*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait_R(blendTime), 100));
	}

	protected void Attack_Down_Leg_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x690EBCAFL /*Attack_Down_Leg_L*/);
		setVariable(0x244677B1L /*_DownAttackCount*/, getVariable(0x244677B1L /*_DownAttackCount*/) + 1);
		doAction(969718423L /*ATTACK_DOWN_LEG_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_L(blendTime)));
	}

	protected void Attack_Down_Leg_L2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5749583FL /*Attack_Down_Leg_L2*/);
		setVariable(0x244677B1L /*_DownAttackCount*/, getVariable(0x244677B1L /*_DownAttackCount*/) + 1);
		doAction(1567966728L /*ATTACK_DOWN_LEG_L2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_L(blendTime)));
	}

	protected void Attack_Down_Leg_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD5903826L /*Attack_Down_Leg_R*/);
		setVariable(0x244677B1L /*_DownAttackCount*/, getVariable(0x244677B1L /*_DownAttackCount*/) + 1);
		doAction(580423279L /*ATTACK_DOWN_LEG_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_R(blendTime)));
	}

	protected void Attack_Down_Leg_R2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x21DCA66CL /*Attack_Down_Leg_R2*/);
		setVariable(0x244677B1L /*_DownAttackCount*/, getVariable(0x244677B1L /*_DownAttackCount*/) + 1);
		doAction(4044715941L /*ATTACK_DOWN_LEG_R2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait_R(blendTime)));
	}

	protected void CannonLogic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCCAB57A0L /*CannonLogic*/);
		setVariable(0x1E2360D6L /*_StunDelay_StartTime*/, getTime());
		if (getVariable(0xB5B81E5EL /*_CannonCount*/) >= 12) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Damage_Leg_L(0.1)))
					return;
			}
		}
		if (getVariable(0xB5B81E5EL /*_CannonCount*/) >= 12) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Damage_Leg_R(0.1)))
					return;
			}
		}
		changeState(state -> Damage_Leg_R(blendTime));
	}

	protected void Hit_Cannon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x44A10749L /*Hit_Cannon*/);
		setVariable(0xB5B81E5EL /*_CannonCount*/, getVariable(0xB5B81E5EL /*_CannonCount*/) + 1);
		if (target != null && checkBuff(target, 139) && getVariable(0xB5B81E5EL /*_CannonCount*/) == 4) {
			if (changeState(state -> Stun_Head(0.3)))
				return;
		}
		if (target != null && checkBuff(target, 139) && getVariable(0xB5B81E5EL /*_CannonCount*/) == 8) {
			if (changeState(state -> Stun_Head(0.3)))
				return;
		}
		if (target != null && checkBuff(target, 139) && getVariable(0xB5B81E5EL /*_CannonCount*/) >= 12) {
			if (changeState(state -> CannonLogic(0.3)))
				return;
		}
		changeState(state -> Hit_Cannon_2(blendTime));
	}

	protected void Hit_Cannon_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB804A1C3L /*Hit_Cannon_2*/);
		if(getCallCount() == 40) {
			if (changeState(state -> Battle_Wait_Logic(0.3)))
				return;
		}
		changeState(state -> Hit_Cannon_2(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xD69E34BBL /*_StunDelay_IngTime*/, getTime());
		setVariable(0x8AC22F24L /*_StunDelay_EndTime*/, getVariable(0xD69E34BBL /*_StunDelay_IngTime*/) - getVariable(0x1E2360D6L /*_StunDelay_StartTime*/));
		if (target != null && checkBuff(target, 139) && getState() != 0x17A4B6AFL /*Battle_Wait_L*/ && getState() != 0x44A10749L /*Hit_Cannon*/ && getState() != 0xB804A1C3L /*Hit_Cannon_2*/ && getState() != 0xCA1A191AL /*Battle_Wait_R*/ && getState() != 0xD02F4B6AL /*Stun_Head*/ && getState() != 0xFFF980D0L /*Stun_Head_End*/ && getState() != 0x690EBCAFL /*Attack_Down_Leg_L*/ && getState() != 0x5749583FL /*Attack_Down_Leg_L2*/ && getState() != 0xD5903826L /*Attack_Down_Leg_R*/ && getState() != 0x21DCA66CL /*Attack_Down_Leg_R2*/ && getVariable(0x8AC22F24L /*_StunDelay_EndTime*/) >= 45000) {
			if (changeState(state -> Hit_Cannon(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.2)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfPartHp(3) <= 0 && getVariable(0xE4C9AD93L /*_HeadStunCount*/) == 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
			if (changeState(state -> Stun_Head(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfPartHp(2) <= 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
			if (changeState(state -> Damage_Leg_L(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfPartHp(1) <= 0 && getVariable(0x17ADE38BL /*_DownLeg_R_Count*/) == 0 && getVariable(0x76D19707L /*_DownLeg_L_Count*/) == 0) {
			if (changeState(state -> Damage_Leg_R(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 1200) && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult WakeUp(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xD4B89BD8L /*Spawn_Wait*/) {
			if (changeState(state -> Start_Logic(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult WakeUp_Test(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xD4B89BD8L /*Spawn_Wait*/) {
			if (changeState(state -> Start_Logic_Test(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
