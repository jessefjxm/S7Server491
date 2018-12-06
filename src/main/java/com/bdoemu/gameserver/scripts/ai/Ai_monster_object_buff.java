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
@IAIName("monster_object_buff")
public class Ai_monster_object_buff extends CreatureAI {
	public Ai_monster_object_buff(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
		setVariable(0x1CF5EE87L /*_Damage_Stop*/, getVariable(0x1A7BA6F4L /*AI_Damage_Stop*/));
		setVariable(0xDCED026BL /*_AI_Kamasylvia*/, getVariable(0xDBC9C356L /*AI_Kamasylvia*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getVariable(0xDCED026BL /*_AI_Kamasylvia*/) == 1) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), getVariable(0x87E37180L /*AI_Wait_CallCycleTime*/) + Rnd.get(-500,500)));
	}

	protected void Battle_Redy_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5FB94CEBL /*Battle_Redy_Wait*/);
		if (target == null) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 3000) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Redy_Wait(blendTime), 6000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		if (target == null) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 3000) {
			if (changeState(state -> Lost_Target(0.3)))
				return;
		}
		if(getCallCount() == getVariable(0xC7B34EEEL /*AI_CallCount*/)) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (changeState(state -> Battle_RangeAttack1(0.3)))
			return;
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 10000));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x67695F37L /*Lost_Target*/);
		clearAggro(true);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
		if ((!((getTargetCollectorEquipmentGrade(target) > 2 && getTargetBattleAimedActionType(target, EBattleAimedActionType.Creep)) || (getTargetCollectorEquipmentGrade(target) > 2 && getTargetActionType(target, EActionType.Collect))))) {
			if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Battle_Wait(0.3)))
					return;
			}
		}
		changeState(state -> Move_Return_NotMove(blendTime));
	}

	protected void Move_Return_NotMove(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.recovery);
		setState(0x59168341L /*Move_Return_NotMove*/);
		clearAggro(true);
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0xB667AD0BL /*AI_HelpMe_Count*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 10000));
	}

	protected void Battle_RangeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC349CD1FL /*Battle_RangeAttack1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Redy_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x46A5E1BAL /*_HelpMe_Count*/, getVariable(0x46A5E1BAL /*_HelpMe_Count*/) - 1);
		if (getVariable(0x46A5E1BAL /*_HelpMe_Count*/) >= 0) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0xBB938CD4L /*AI_HelpMe_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x41A04D7CL /*AI_HelpMe_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0x806F52BAL /*AI_HelpMe_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0xACA0D159L /*AI_HelpMe_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC68B63EDL /*AI_HelpMe_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0x22A45860L /*AI_HelpMe_Distance*/)).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x1CF5EE87L /*_Damage_Stop*/) == 1) {
			if (changeState(state -> Damage_Die_Handler(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	protected void Damage_Die_Handler(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xB0B373DBL /*Damage_Die_Handler*/);
		if (getVariable(0x1CF5EE87L /*_Damage_Stop*/) == 1) {
			getObjects(EAIFindTargetType.Monster, object -> (getTargetCharacterKey(object) == getVariable(0x4E36E74L /*AI_Stop_CharacterKey1*/) || getTargetCharacterKey(object) == getVariable(0x5FE07E6L /*AI_Stop_CharacterKey2*/) || getTargetCharacterKey(object) == getVariable(0xBE8B8DBBL /*AI_Stop_CharacterKey3*/) || getTargetCharacterKey(object) == getVariable(0x54D4B658L /*AI_Stop_CharacterKey4*/) || getTargetCharacterKey(object) == getVariable(0xC996B53L /*AI_Stop_CharacterKey5*/)) && getDistanceToTarget(object) < getVariable(0xB2CB7E71L /*AI_Stop_Distance*/)).forEach(consumer -> consumer.getAi().HandleStop(getActor(), null));
		}
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die_Handler(blendTime), 20000));
	}
}
