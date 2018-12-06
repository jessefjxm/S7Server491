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
@IAIName("flying_creature_main")
public class Ai_flying_creature_main extends CreatureAI {
	public Ai_flying_creature_main(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xB01283E1L /*_spawnType*/, getVariable(0x282E9A83L /*FlyingSpawnType*/));
		setVariable(0x5640DAE5L /*_isAttacker*/, getVariable(0x1A1AC7C6L /*AI_Attacker*/));
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xDD6BDE1AL /*_isFly*/, 0);
		if (getVariable(0xB01283E1L /*_spawnType*/) == 1) {
			if (changeState(state -> FlyingWait(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1800)) {
			if (changeState(state -> Take_Off(0.4)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void FlyingWait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD54A281BL /*FlyingWait*/);
		setVariable(0xDD6BDE1AL /*_isFly*/, 1);
		if (target != null && getTargetHp(target) == 0 && getVariable(0xB01283E1L /*_spawnType*/) == 0) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 3500 && getVariable(0xB01283E1L /*_spawnType*/) == 0) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if(Rnd.getChance(70)) {
			if (changeState(state -> FlyingRandom(blendTime)))
				return;
		}
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> FlyingWait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void FlyingRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA7442DBDL /*FlyingRandom*/);
		doAction(1059120586L /*FLYING_FLAP*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 1500, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> FlyingWait(blendTime), 300)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		clearAggro(true);
		doAction(1059120586L /*FLYING_FLAP*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			if (getVariable(0xB01283E1L /*_spawnType*/) == 1) {
				if (changeState(state -> FlyingWait(blendTime)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Landing(blendTime), 1000)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void Take_Off_Oder(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x29DFBA3L /*Take_Off_Oder*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 2000).forEach(consumer -> consumer.getAi().fly_near(getActor(), null));
		changeState(state -> Take_Off(blendTime));
	}

	protected void Take_Off(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x212C6C11L /*Take_Off*/);
		setVariable(0xDD6BDE1AL /*_isFly*/, 1);
		doAction(2291128889L /*TAKE_OFF*/, blendTime, onDoActionEnd -> changeState(state -> FlyingFlap(blendTime)));
	}

	protected void Landing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBE797C29L /*Landing*/);
		setVariable(0xDD6BDE1AL /*_isFly*/, 0);
		doAction(116698182L /*LANDING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Search_Enemy_Oder(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC611C998L /*Search_Enemy_Oder*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 2000).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		changeState(state -> Search_Enemy(blendTime));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 500));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
	}

	protected void FlyingFlap(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x54CA9FC8L /*FlyingFlap*/);
		doAction(1059120586L /*FLYING_FLAP*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1000, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> FlyingWait(blendTime), 1000)));
	}

	protected void Flying_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9526A4B9L /*Flying_Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 2000) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 300) {
			if (changeState(state -> Move_Chaser(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Attack_Normal(0.4)))
				return;
		}
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Battle_Wait(blendTime), 500));
	}

	protected void Battle_Take_Off_Oder(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x81A0FB3DL /*Battle_Take_Off_Oder*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 1000).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		changeState(state -> Battle_Take_Off(blendTime));
	}

	protected void Battle_Take_Off(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFE317C16L /*Battle_Take_Off*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0xDD6BDE1AL /*_isFly*/, 1);
		doAction(2291128889L /*TAKE_OFF*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 2000) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Flying_Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 220) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		doAction(1059120586L /*FLYING_FLAP*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Flying_Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Take_Off(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xDD6BDE1AL /*_isFly*/) == 0 && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0x5640DAE5L /*_isAttacker*/) == 1 && (getState() != 0xFE317C16L /*Battle_Take_Off*/ || getState() != 0xCF465EDCL /*Search_Enemy*/)) {
			if (changeState(state -> Battle_Take_Off_Oder(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xDD6BDE1AL /*_isFly*/) == 1 && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0x5640DAE5L /*_isAttacker*/) == 1 && (getState() != 0xFE317C16L /*Battle_Take_Off*/ || getState() != 0xCF465EDCL /*Search_Enemy*/)) {
			if (changeState(state -> Search_Enemy_Oder(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult fly_near(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xB01283E1L /*_spawnType*/) == 0 && getVariable(0xDD6BDE1AL /*_isFly*/) == 0) {
			if (changeState(state -> Take_Off(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _helpme(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xDD6BDE1AL /*_isFly*/) == 1 && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && target != null && getTargetCharacterKey(target) == 20338 && (getState() != 0xFE317C16L /*Battle_Take_Off*/ || getState() != 0xCF465EDCL /*Search_Enemy*/)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Search_Enemy(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xDD6BDE1AL /*_isFly*/) == 0 && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && target != null && getTargetCharacterKey(target) == 20338 && (getState() != 0xFE317C16L /*Battle_Take_Off*/ || getState() != 0xCF465EDCL /*Search_Enemy*/)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Battle_Take_Off(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
