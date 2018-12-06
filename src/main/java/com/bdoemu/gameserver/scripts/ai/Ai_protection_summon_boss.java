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
@IAIName("protection_summon_boss")
public class Ai_protection_summon_boss extends CreatureAI {
	public Ai_protection_summon_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xD3505174L /*_Summon_Count*/, 0);
		setVariable(0xB4AF8A2FL /*_Stage_Count*/, 1);
		setVariable(0x2C934EF0L /*_Stage_MessageCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x4B71C94FL /*_Ready_StartTime*/, 0);
		setVariable(0x518E9EB3L /*_Ready_IngTime*/, 0);
		setVariable(0x9660FC4DL /*_Ready_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariable(0xBD8064ACL /*_isSummonReady*/, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 1000));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1700)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 120000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500 + Rnd.get(-500,500)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage1_Start_Message(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> TargetLost_Wait(blendTime)));
	}

	protected void TargetLost_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8BF45292L /*TargetLost_Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0xB4AF8A2FL /*_Stage_Count*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 2000)) {
				if (changeState(state -> Stage1_Wait(blendTime)))
					return;
			}
		}
		if (getVariable(0xB4AF8A2FL /*_Stage_Count*/) == 2) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 2000)) {
				if (changeState(state -> Stage2_Wait(blendTime)))
					return;
			}
		}
		if (getVariable(0xB4AF8A2FL /*_Stage_Count*/) == 3) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 2000)) {
				if (changeState(state -> Stage3_Wait(blendTime)))
					return;
			}
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 120000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TargetLost_Wait(blendTime), 1000));
	}

	protected void Stage1_Start_10SEC_Ago_Message(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x68C7DE17L /*Stage1_Start_10SEC_Ago_Message*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_BOSS_MESSAGE_TIME10SEC");
		setVariable(0x2C934EF0L /*_Stage_MessageCount*/, 1);
		changeState(state -> Stage1_Start_10SEC_Ago_Ready(blendTime));
	}

	protected void Stage1_Start_10SEC_Ago_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3491791L /*Stage1_Start_10SEC_Ago_Ready*/);
		if(getCallCount() == 10) {
			if (changeState(state -> Stage1_Start_Message(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage1_Start_10SEC_Ago_Ready(blendTime), 1000));
	}

	protected void Stage1_Start_Message(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4852B98FL /*Stage1_Start_Message*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_BOSS_MESSAGE_1STAGE");
		setVariable(0x2C934EF0L /*_Stage_MessageCount*/, 1);
		changeState(state -> Summon_Stage1_Boss(blendTime));
	}

	protected void Summon_Stage1_Boss(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDFEA9393L /*Summon_Stage1_Boss*/);
		setVariable(0xB4AF8A2FL /*_Stage_Count*/, 1);
		doAction(2581063739L /*BATTLE_SUMMON_STAGE1_BOSS*/, blendTime, onDoActionEnd -> changeState(state -> Stage1_Wait(blendTime)));
	}

	protected void Stage1_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA9F0AFL /*Stage1_Wait*/);
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 5000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getPartyMembersCount()<= 2) {
			if(getCallCount() == 10) {
				if (changeState(state -> Summon_Stage1(blendTime)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage1_Wait(blendTime), 1000));
	}

	protected void Summon_Stage1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x85E4547BL /*Summon_Stage1*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_BOSS_MESSAGE_SUMMON_MONSTER");
		doAction(633711333L /*BATTLE_SUMMON_STAGE1*/, blendTime, onDoActionEnd -> changeState(state -> Stage1_Wait(blendTime)));
	}

	protected void Stage1_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E95FA72L /*Stage1_End*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._AllChildDie(getActor(), null));
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage2_Start_5SEC_Ago_Message(blendTime), 500));
	}

	protected void Stage2_Start_5SEC_Ago_Message(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD2503C43L /*Stage2_Start_5SEC_Ago_Message*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_BOSS_MESSAGE_TIME5SEC");
		setVariable(0x2C934EF0L /*_Stage_MessageCount*/, 2);
		changeState(state -> Stage2_Start_5SEC_Ago_Ready(blendTime));
	}

	protected void Stage2_Start_5SEC_Ago_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD354F82BL /*Stage2_Start_5SEC_Ago_Ready*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Stage2_Start_Message(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage2_Start_5SEC_Ago_Ready(blendTime), 1000));
	}

	protected void Stage2_Start_Message(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x53B60165L /*Stage2_Start_Message*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_BOSS_MESSAGE_2STAGE");
		setVariable(0x2C934EF0L /*_Stage_MessageCount*/, 2);
		changeState(state -> Summon_Stage2_Boss(blendTime));
	}

	protected void Summon_Stage2_Boss(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x79EC4937L /*Summon_Stage2_Boss*/);
		setVariable(0xB4AF8A2FL /*_Stage_Count*/, 2);
		doAction(4275741446L /*BATTLE_SUMMON_STAGE2_BOSS*/, blendTime, onDoActionEnd -> changeState(state -> Stage2_Wait(blendTime)));
	}

	protected void Stage2_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x904CE6E1L /*Stage2_Wait*/);
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 5000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getPartyMembersCount()<= 2) {
			if(getCallCount() == 10) {
				if (changeState(state -> Summon_Stage2(blendTime)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage2_Wait(blendTime), 1000));
	}

	protected void Summon_Stage2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCAE80D6DL /*Summon_Stage2*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_BOSS_MESSAGE_SUMMON_MONSTER");
		doAction(696130823L /*BATTLE_SUMMON_STAGE2*/, blendTime, onDoActionEnd -> changeState(state -> Stage2_Wait(blendTime)));
	}

	protected void Stage2_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3DCEC9B0L /*Stage2_End*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._AllChildDie(getActor(), null));
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage3_Start_5SEC_Ago_Message(blendTime), 500));
	}

	protected void Stage3_Start_5SEC_Ago_Message(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x48C500FDL /*Stage3_Start_5SEC_Ago_Message*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_BOSS_MESSAGE_TIME5SEC");
		setVariable(0x2C934EF0L /*_Stage_MessageCount*/, 3);
		changeState(state -> Stage3_Start_5SEC_Ago_Ready(blendTime));
	}

	protected void Stage3_Start_5SEC_Ago_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3DC51CCDL /*Stage3_Start_5SEC_Ago_Ready*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Stage3_Start_Message(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage3_Start_5SEC_Ago_Ready(blendTime), 1000));
	}

	protected void Stage3_Start_Message(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE9714F40L /*Stage3_Start_Message*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_BOSS_MESSAGE_3STAGE");
		setVariable(0x2C934EF0L /*_Stage_MessageCount*/, 3);
		changeState(state -> Summon_Stage3_Boss(blendTime));
	}

	protected void Summon_Stage3_Boss(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAC40F7A5L /*Summon_Stage3_Boss*/);
		setVariable(0xB4AF8A2FL /*_Stage_Count*/, 3);
		doAction(627722084L /*BATTLE_SUMMON_STAGE3_BOSS*/, blendTime, onDoActionEnd -> changeState(state -> Stage3_Wait(blendTime)));
	}

	protected void Stage3_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAC0F1FD4L /*Stage3_Wait*/);
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 5000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getPartyMembersCount()<= 2) {
			if (changeState(state -> Summon_Stage3(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage3_Wait(blendTime), 5000));
	}

	protected void Summon_Stage3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x42B3A126L /*Summon_Stage3*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_BOSS_MESSAGE_SUMMON_MONSTER");
		doAction(2588232575L /*BATTLE_SUMMON_STAGE3*/, blendTime, onDoActionEnd -> changeState(state -> Stage3_Wait(blendTime)));
	}

	protected void Stage3_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1BF84E2FL /*Stage3_End*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._AllChildDie(getActor(), null));
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 500));
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
	public EAiHandlerResult _StageBossDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xB4AF8A2FL /*_Stage_Count*/) == 1) {
			if (changeState(state -> Stage1_End(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xB4AF8A2FL /*_Stage_Count*/) == 2) {
			if (changeState(state -> Stage2_End(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xB4AF8A2FL /*_Stage_Count*/) == 3) {
			if (changeState(state -> Stage3_End(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
