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
@IAIName("pet_owl_4er")
public class Ai_pet_owl_4er extends CreatureAI {
	public Ai_pet_owl_4er(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xB01283E1L /*_spawnType*/, 1);
		setVariable(0xDD6BDE1AL /*_isFly*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x940229E0L /*_isState*/, 1);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0x83C2E18EL /*_UpCount*/, 0);
		setVariable(0x69C27CA9L /*_Attachable_Type*/, 0);
		setVariable(0x42A6EC2DL /*_isRunning*/, 0);
		setVariable(0xBA414EEEL /*_isChaseMode*/, 1);
		setVariable(0xB4AEEB95L /*_isGetItemMode*/, 1);
		setVariable(0xC3671716L /*_isFindThatMode*/, 1);
		setVariable(0x3E491457L /*_Skill0_UsableDistance*/, 0);
		setVariable(0x6AD923A2L /*_Skill1_UsableDistance*/, 0);
		setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, 0);
		setVariable(0x18B37A64L /*_Skill1_DetectDistance*/, 0);
		setVariable(0x10042F0L /*_Skill0_TargetType0*/, 0);
		setVariable(0x30CD4CFAL /*_Skill1_TargetType1*/, 0);
		setVariable(0x6EA9665AL /*_FindDungeon_StartTime*/, 0);
		setVariable(0xDEB1169FL /*_FindDungeon_IngTime*/, 0);
		setVariable(0x57F8A701L /*_FindDungeon_EndTime*/, 0);
		setVariable(0x6EA9665AL /*_FindDungeon_StartTime*/, getTime());
		setVariable(0x940229E0L /*_isState*/, 1);
		setVariable(0x940229E0L /*_isState*/, 2);
		setVariable(0x3E491457L /*_Skill0_UsableDistance*/, getPetSkillUsableDistance(0));
		setVariable(0x6AD923A2L /*_Skill1_UsableDistance*/, getPetSkillUsableDistance(1));
		setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, getPetSkillDetectDistance(0));
		if (getVariable(0xF4E090DL /*_Skill0_DetectDistance*/) == -1) {
			setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, 1200);
		}
		setVariable(0x18B37A64L /*_Skill1_DetectDistance*/, getPetSkillDetectDistance(1));
		setVariable(0x10042F0L /*_Skill0_TargetType0*/, getPetSkillTargetType(0));
		setVariable(0x30CD4CFAL /*_Skill1_TargetType1*/, getPetSkillTargetType(1));
		doAction(960059183L /*WAIT_CHASE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Logic(blendTime), 500));
	}

	protected void Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9048C900L /*Wait_Logic*/);
		if (getVariable(0x940229E0L /*_isState*/) == 1) {
			if (changeState(state -> Wait_House(blendTime)))
				return;
		}
		if (getVariable(0x940229E0L /*_isState*/) == 2) {
			if (changeState(state -> Summon_Start(blendTime)))
				return;
		}
		changeState(state -> Wait_Logic(blendTime));
	}

	protected void Wait_House(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCAB2CF45L /*Wait_House*/);
		if(getCallCount() == 4) {
			if (changeState(state -> Walk_Random_House(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_House(blendTime), 500));
	}

	protected void Walk_Random_House(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x26903310L /*Walk_Random_House*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 200, true, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_House(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_House_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Wait_House(blendTime), 500)));
	}

	protected void Move_Return_House(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0x1ED7CB14L /*Move_Return_House*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_House(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_House_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Wait_House(blendTime), 500)));
	}

	protected void FailFindPath_House_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB5462542L /*FailFindPath_House_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 3 && getVariable(0x870CD143L /*_IsPartyMember*/) == 0) {
			if (changeState(state -> FailFindPath_House(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_House(blendTime), 500));
	}

	protected void FailFindPath_House(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF6FA9865L /*FailFindPath_House*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(3467964157L /*TELEPORT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_House(blendTime), 500));
	}

	protected void Summon_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3FB8F870L /*Summon_Start*/);
		doAction(2291128889L /*TAKE_OFF*/, blendTime, onDoActionEnd -> changeState(state -> Summon_Start_A(blendTime)));
	}

	protected void Summon_Start_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDA11E9BDL /*Summon_Start_A*/);
		doAction(2808109224L /*TAKE_OFF_FLYING*/, blendTime, onDoActionEnd -> changeState(state -> Summon_Start_B(blendTime)));
	}

	protected void Summon_Start_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF99E118EL /*Summon_Start_B*/);
		doAction(2086992886L /*TAKE_OFF_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Summon_Start_C(blendTime)));
	}

	protected void Summon_Start_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3C840E7EL /*Summon_Start_C*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		setVariable(0x83C2E18EL /*_UpCount*/, getVariable(0x83C2E18EL /*_UpCount*/) + 1);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0x3E491457L /*_Skill0_UsableDistance*/, getPetSkillUsableDistance(0));
		setVariable(0x6AD923A2L /*_Skill1_UsableDistance*/, getPetSkillUsableDistance(1));
		setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, getPetSkillDetectDistance(0));
		if (getVariable(0xF4E090DL /*_Skill0_DetectDistance*/) == -1) {
			setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, 1500);
		}
		setVariable(0x18B37A64L /*_Skill1_DetectDistance*/, getPetSkillDetectDistance(1));
		setVariable(0x10042F0L /*_Skill0_TargetType0*/, getPetSkillTargetType(0));
		setVariable(0x30CD4CFAL /*_Skill1_TargetType1*/, getPetSkillTargetType(1));
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x3E491457L /*_Skill0_UsableDistance*/))) {
				if (changeState(state -> GetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0xF4E090DL /*_Skill0_DetectDistance*/))) {
				if (changeState(state -> ChaseGetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 200)) {
				if (changeState(state -> FindThat(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 4000)) {
				if (changeState(state -> ChaseFindThat(blendTime)))
					return;
			}
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 1000 && getVariable(0x83C2E18EL /*_UpCount*/) >= 1) {
			if (changeState(state -> Random_ChaseOwner_MoveLv2(blendTime)))
				return;
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 600 && getVariable(0x83C2E18EL /*_UpCount*/) < 1) {
			if (changeState(state -> Random_ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10000) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 10, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Summon(blendTime), 500)));
	}

	protected void Summon_Start_C_RetryCurve(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC3E93BB8L /*Summon_Start_C_RetryCurve*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		setVariable(0x83C2E18EL /*_UpCount*/, getVariable(0x83C2E18EL /*_UpCount*/) + 1);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0x3E491457L /*_Skill0_UsableDistance*/, getPetSkillUsableDistance(0));
		setVariable(0x6AD923A2L /*_Skill1_UsableDistance*/, getPetSkillUsableDistance(1));
		setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, getPetSkillDetectDistance(0));
		if (getVariable(0xF4E090DL /*_Skill0_DetectDistance*/) == -1) {
			setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, 1500);
		}
		setVariable(0x18B37A64L /*_Skill1_DetectDistance*/, getPetSkillDetectDistance(1));
		setVariable(0x10042F0L /*_Skill0_TargetType0*/, getPetSkillTargetType(0));
		setVariable(0x30CD4CFAL /*_Skill1_TargetType1*/, getPetSkillTargetType(1));
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x3E491457L /*_Skill0_UsableDistance*/))) {
				if (changeState(state -> GetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0xF4E090DL /*_Skill0_DetectDistance*/))) {
				if (changeState(state -> ChaseGetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 200)) {
				if (changeState(state -> FindThat(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 4000)) {
				if (changeState(state -> ChaseFindThat(blendTime)))
					return;
			}
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 1000 && getVariable(0x83C2E18EL /*_UpCount*/) >= 1) {
			if (changeState(state -> Random_ChaseOwner_MoveLv2(blendTime)))
				return;
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 600 && getVariable(0x83C2E18EL /*_UpCount*/) < 1) {
			if (changeState(state -> Random_ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10000) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.RetryCurve, 0, 0, 0, 0, 10, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_C(blendTime), 500)));
	}

	protected void Summon_Start_C_RetryCurveTurn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x4F61E11L /*Summon_Start_C_RetryCurveTurn*/);
		setVariable(0x83C2E18EL /*_UpCount*/, getVariable(0x83C2E18EL /*_UpCount*/) + 1);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0x3E491457L /*_Skill0_UsableDistance*/, getPetSkillUsableDistance(0));
		setVariable(0x6AD923A2L /*_Skill1_UsableDistance*/, getPetSkillUsableDistance(1));
		setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, getPetSkillDetectDistance(0));
		if (getVariable(0xF4E090DL /*_Skill0_DetectDistance*/) == -1) {
			setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, 1500);
		}
		setVariable(0x18B37A64L /*_Skill1_DetectDistance*/, getPetSkillDetectDistance(1));
		setVariable(0x10042F0L /*_Skill0_TargetType0*/, getPetSkillTargetType(0));
		setVariable(0x30CD4CFAL /*_Skill1_TargetType1*/, getPetSkillTargetType(1));
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x3E491457L /*_Skill0_UsableDistance*/))) {
				if (changeState(state -> GetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0xF4E090DL /*_Skill0_DetectDistance*/))) {
				if (changeState(state -> ChaseGetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 200)) {
				if (changeState(state -> FindThat(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 4000)) {
				if (changeState(state -> ChaseFindThat(blendTime)))
					return;
			}
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 1000 && getVariable(0x83C2E18EL /*_UpCount*/) >= 1) {
			if (changeState(state -> Random_ChaseOwner_MoveLv2(blendTime)))
				return;
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 600 && getVariable(0x83C2E18EL /*_UpCount*/) < 1) {
			if (changeState(state -> Random_ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10000) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(1596299921L /*FLING_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToPathCurve, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_C_RetryCurve(blendTime), 500)));
	}

	protected void Owner_Landing_Chase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9EA2BBB2L /*Owner_Landing_Chase*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 10, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Owner_Landing_Ing(blendTime), 500)));
	}

	protected void Owner_Landing_RetryCurve(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC191321EL /*Owner_Landing_RetryCurve*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.RetryCurve, 0, 0, 0, 0, 10, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_C(blendTime), 500)));
	}

	protected void Owner_Landing_RetryCurveTurn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xAEF7EED4L /*Owner_Landing_RetryCurveTurn*/);
		doAction(1596299921L /*FLING_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToPathCurve, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Owner_Landing_RetryCurve(blendTime), 500)));
	}

	protected void Owner_Landing_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x322B5416L /*Owner_Landing_Ing*/);
		doAction(2267039204L /*OWNER_LANDING*/, blendTime, onDoActionEnd -> scheduleState(state -> Owner_Landing_Ing(blendTime), 500));
	}

	protected void Owner_Landing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB6555A40L /*Owner_Landing*/);
		doAction(917493806L /*OWNER_LANDING_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Owner_Landing(blendTime), 500));
	}

	protected void Owner_Landing_Chase_PBW(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF1F1CCAFL /*Owner_Landing_Chase_PBW*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 10, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Owner_Landing_Ing_PBW(blendTime), 500)));
	}

	protected void Owner_Landing_RetryCurve_PBW(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8F995746L /*Owner_Landing_RetryCurve_PBW*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.RetryCurve, 0, 0, 0, 0, 10, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Start_C(blendTime), 500)));
	}

	protected void Owner_Landing_RetryCurveTurn_PBW(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xBC87AB08L /*Owner_Landing_RetryCurveTurn_PBW*/);
		doAction(1596299921L /*FLING_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToPathCurve, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Owner_Landing_RetryCurve_PBW(blendTime), 500)));
	}

	protected void Owner_Landing_Ing_PBW(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x87F3D920L /*Owner_Landing_Ing_PBW*/);
		doAction(554937847L /*OWNER_LANDING_PBW*/, blendTime, onDoActionEnd -> scheduleState(state -> Owner_Landing_Ing_PBW(blendTime), 500));
	}

	protected void Owner_Landing_PBW(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xACC03E15L /*Owner_Landing_PBW*/);
		doAction(1447360144L /*OWNER_LANDING_END_PBW*/, blendTime, onDoActionEnd -> scheduleState(state -> Owner_Landing_PBW(blendTime), 500));
	}

	protected void Flying_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x67926D3AL /*Flying_Random*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0x3E491457L /*_Skill0_UsableDistance*/, getPetSkillUsableDistance(0));
		setVariable(0x6AD923A2L /*_Skill1_UsableDistance*/, getPetSkillUsableDistance(1));
		setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, getPetSkillDetectDistance(0));
		if (getVariable(0xF4E090DL /*_Skill0_DetectDistance*/) == -1) {
			setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, 1500);
		}
		setVariable(0x18B37A64L /*_Skill1_DetectDistance*/, getPetSkillDetectDistance(1));
		setVariable(0x10042F0L /*_Skill0_TargetType0*/, getPetSkillTargetType(0));
		setVariable(0x30CD4CFAL /*_Skill1_TargetType1*/, getPetSkillTargetType(1));
		setVariable(0xDEB1169FL /*_FindDungeon_IngTime*/, getTime());
		setVariable(0x57F8A701L /*_FindDungeon_EndTime*/, getVariable(0xDEB1169FL /*_FindDungeon_IngTime*/) - getVariable(0x6EA9665AL /*_FindDungeon_StartTime*/));
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		if(Rnd.getChance(40)) {
			if (changeState(state -> Flying_Random_Up(blendTime)))
				return;
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x3E491457L /*_Skill0_UsableDistance*/))) {
				if (changeState(state -> GetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0xF4E090DL /*_Skill0_DetectDistance*/))) {
				if (changeState(state -> ChaseGetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 200)) {
				if (changeState(state -> FindThat(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 4000)) {
				if (changeState(state -> ChaseFindThat(blendTime)))
					return;
			}
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 1000 && getVariable(0x83C2E18EL /*_UpCount*/) >= 1) {
			if (changeState(state -> Random_ChaseOwner_MoveLv2(blendTime)))
				return;
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 600 && getVariable(0x83C2E18EL /*_UpCount*/) < 1) {
			if (changeState(state -> Random_ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10000) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 200, 50, 80, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_Random(blendTime), 500)));
	}

	protected void Flying_Random_Up(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBDD8214FL /*Flying_Random_Up*/);
		setVariable(0x83C2E18EL /*_UpCount*/, getVariable(0x83C2E18EL /*_UpCount*/) + 1);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0x3E491457L /*_Skill0_UsableDistance*/, getPetSkillUsableDistance(0));
		setVariable(0x6AD923A2L /*_Skill1_UsableDistance*/, getPetSkillUsableDistance(1));
		setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, getPetSkillDetectDistance(0));
		if (getVariable(0xF4E090DL /*_Skill0_DetectDistance*/) == -1) {
			setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, 1500);
		}
		setVariable(0x18B37A64L /*_Skill1_DetectDistance*/, getPetSkillDetectDistance(1));
		setVariable(0x10042F0L /*_Skill0_TargetType0*/, getPetSkillTargetType(0));
		setVariable(0x30CD4CFAL /*_Skill1_TargetType1*/, getPetSkillTargetType(1));
		setVariable(0xDEB1169FL /*_FindDungeon_IngTime*/, getTime());
		setVariable(0x57F8A701L /*_FindDungeon_EndTime*/, getVariable(0xDEB1169FL /*_FindDungeon_IngTime*/) - getVariable(0x6EA9665AL /*_FindDungeon_StartTime*/));
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x3E491457L /*_Skill0_UsableDistance*/))) {
				if (changeState(state -> GetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0xF4E090DL /*_Skill0_DetectDistance*/))) {
				if (changeState(state -> ChaseGetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 200)) {
				if (changeState(state -> FindThat(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 4000)) {
				if (changeState(state -> ChaseFindThat(blendTime)))
					return;
			}
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 1000 && getVariable(0x83C2E18EL /*_UpCount*/) >= 1) {
			if (changeState(state -> Random_ChaseOwner_MoveLv2(blendTime)))
				return;
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 600 && getVariable(0x83C2E18EL /*_UpCount*/) < 1) {
			if (changeState(state -> Random_ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10000) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(3748920357L /*MOVE_FLYING_UP*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 400, 80, 150, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_Random(blendTime), 500)));
	}

	protected void Flying_Random_Down(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x128BF435L /*Flying_Random_Down*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0x3E491457L /*_Skill0_UsableDistance*/, getPetSkillUsableDistance(0));
		setVariable(0x6AD923A2L /*_Skill1_UsableDistance*/, getPetSkillUsableDistance(1));
		setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, getPetSkillDetectDistance(0));
		if (getVariable(0xF4E090DL /*_Skill0_DetectDistance*/) == -1) {
			setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, 1500);
		}
		setVariable(0x18B37A64L /*_Skill1_DetectDistance*/, getPetSkillDetectDistance(1));
		setVariable(0x10042F0L /*_Skill0_TargetType0*/, getPetSkillTargetType(0));
		setVariable(0x30CD4CFAL /*_Skill1_TargetType1*/, getPetSkillTargetType(1));
		setVariable(0xDEB1169FL /*_FindDungeon_IngTime*/, getTime());
		setVariable(0x57F8A701L /*_FindDungeon_EndTime*/, getVariable(0xDEB1169FL /*_FindDungeon_IngTime*/) - getVariable(0x6EA9665AL /*_FindDungeon_StartTime*/));
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x3E491457L /*_Skill0_UsableDistance*/))) {
				if (changeState(state -> GetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0xF4E090DL /*_Skill0_DetectDistance*/))) {
				if (changeState(state -> ChaseGetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 200)) {
				if (changeState(state -> FindThat(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 4000)) {
				if (changeState(state -> ChaseFindThat(blendTime)))
					return;
			}
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 600) {
			if (changeState(state -> ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10000) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 400, 80, 150, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_Random(blendTime), 500)));
	}

	protected void Random_ChaseOwner_MoveLv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3530622BL /*Random_ChaseOwner_MoveLv1*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		setVariable(0x83C2E18EL /*_UpCount*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0xDEB1169FL /*_FindDungeon_IngTime*/, getTime());
		setVariable(0x57F8A701L /*_FindDungeon_EndTime*/, getVariable(0xDEB1169FL /*_FindDungeon_IngTime*/) - getVariable(0x6EA9665AL /*_FindDungeon_StartTime*/));
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 600) {
			if (changeState(state -> Flying_Random(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> ChaseOwner_MoveLv2(blendTime)))
				return;
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x3E491457L /*_Skill0_UsableDistance*/))) {
				if (changeState(state -> GetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0xF4E090DL /*_Skill0_DetectDistance*/))) {
				if (changeState(state -> ChaseGetItem(blendTime)))
					return;
			}
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 50, 200, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_Summon(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Summon_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Random_ChaseOwner_MoveLv1(blendTime), 500)));
	}

	protected void Random_ChaseOwner_MoveLv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3F0785CCL /*Random_ChaseOwner_MoveLv2*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		setVariable(0x83C2E18EL /*_UpCount*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> ChaseOwner_MoveLv2(blendTime)))
				return;
		}
		setVariable(0xDEB1169FL /*_FindDungeon_IngTime*/, getTime());
		setVariable(0x57F8A701L /*_FindDungeon_EndTime*/, getVariable(0xDEB1169FL /*_FindDungeon_IngTime*/) - getVariable(0x6EA9665AL /*_FindDungeon_StartTime*/));
		if(Rnd.getChance(15)) {
			if (changeState(state -> Random_ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 600) {
			if (changeState(state -> Flying_Random(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x3E491457L /*_Skill0_UsableDistance*/))) {
				if (changeState(state -> GetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0xF4E090DL /*_Skill0_DetectDistance*/))) {
				if (changeState(state -> ChaseGetItem(blendTime)))
					return;
			}
		}
		doAction(3145567765L /*MOVE_FLYING2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 50, 200, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_Summon(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Summon_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Random_ChaseOwner_MoveLv2(blendTime), 500)));
	}

	protected void FlyingFlap(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x54CA9FC8L /*FlyingFlap*/);
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 600, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Summon(blendTime), 000)));
	}

	protected void Wait_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x87DC6143L /*Wait_Summon*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0x3E491457L /*_Skill0_UsableDistance*/, getPetSkillUsableDistance(0));
		setVariable(0x6AD923A2L /*_Skill1_UsableDistance*/, getPetSkillUsableDistance(1));
		setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, getPetSkillDetectDistance(0));
		if (getVariable(0xF4E090DL /*_Skill0_DetectDistance*/) == -1) {
			setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, 1500);
		}
		setVariable(0x18B37A64L /*_Skill1_DetectDistance*/, getPetSkillDetectDistance(1));
		setVariable(0x10042F0L /*_Skill0_TargetType0*/, getPetSkillTargetType(0));
		setVariable(0x30CD4CFAL /*_Skill1_TargetType1*/, getPetSkillTargetType(1));
		setVariable(0xDEB1169FL /*_FindDungeon_IngTime*/, getTime());
		setVariable(0x57F8A701L /*_FindDungeon_EndTime*/, getVariable(0xDEB1169FL /*_FindDungeon_IngTime*/) - getVariable(0x6EA9665AL /*_FindDungeon_StartTime*/));
		if(Rnd.getChance(10)) {
			if (changeState(state -> Wait_Summon1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x3E491457L /*_Skill0_UsableDistance*/))) {
				if (changeState(state -> GetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0xF4E090DL /*_Skill0_DetectDistance*/))) {
				if (changeState(state -> ChaseGetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 200)) {
				if (changeState(state -> FindThat(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 4000)) {
				if (changeState(state -> ChaseFindThat(blendTime)))
					return;
			}
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 750) {
			if (changeState(state -> ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10000) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(3473554812L /*FLING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Random(blendTime), 500));
	}

	protected void Wait_Summon1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x17263E70L /*Wait_Summon1*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0x3E491457L /*_Skill0_UsableDistance*/, getPetSkillUsableDistance(0));
		setVariable(0x6AD923A2L /*_Skill1_UsableDistance*/, getPetSkillUsableDistance(1));
		setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, getPetSkillDetectDistance(0));
		if (getVariable(0xF4E090DL /*_Skill0_DetectDistance*/) == -1) {
			setVariable(0xF4E090DL /*_Skill0_DetectDistance*/, 1500);
		}
		setVariable(0x18B37A64L /*_Skill1_DetectDistance*/, getPetSkillDetectDistance(1));
		setVariable(0x10042F0L /*_Skill0_TargetType0*/, getPetSkillTargetType(0));
		setVariable(0x30CD4CFAL /*_Skill1_TargetType1*/, getPetSkillTargetType(1));
		setVariable(0xDEB1169FL /*_FindDungeon_IngTime*/, getTime());
		setVariable(0x57F8A701L /*_FindDungeon_EndTime*/, getVariable(0xDEB1169FL /*_FindDungeon_IngTime*/) - getVariable(0x6EA9665AL /*_FindDungeon_StartTime*/));
		if(Rnd.getChance(55)) {
			if (changeState(state -> Wait_Summon1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x3E491457L /*_Skill0_UsableDistance*/))) {
				if (changeState(state -> GetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xB4AEEB95L /*_isGetItemMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType0, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0xF4E090DL /*_Skill0_DetectDistance*/))) {
				if (changeState(state -> ChaseGetItem(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 200)) {
				if (changeState(state -> FindThat(blendTime)))
					return;
			}
		}
		if (false && getVariable(0xC3671716L /*_isFindThatMode*/) == 1) {
			if (findTarget(EAIFindTargetType._Skill0_TargetType1, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 4000)) {
				if (changeState(state -> ChaseFindThat(blendTime)))
					return;
			}
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 1000) {
			if (changeState(state -> ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10000) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(1007965783L /*FLING_SWING_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Summon1(blendTime)));
	}

	protected void FindDungeon_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x3A5C9708L /*FindDungeon_Str*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> FindDungeon(blendTime)))
				return;
		}
		doAction(3004255870L /*MOVE_FAST2_FLYING*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_Summon(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Summon_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> FindDungeon(blendTime), 500)));
	}

	protected void FindDungeon_RetryCurve(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x32675242L /*FindDungeon_RetryCurve*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.RetryCurve, 0, 0, 0, 0, 10, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> FindDungeon_Str(blendTime), 500)));
	}

	protected void FindDungeon_RetryCurveTurn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xE9904522L /*FindDungeon_RetryCurveTurn*/);
		doAction(1596299921L /*FLING_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToPathCurve, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> FindDungeon_RetryCurve(blendTime), 500)));
	}

	protected void FindDungeon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x50517440L /*FindDungeon*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		if(getCallCount() == 20) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		if (getVariable(0xBA414EEEL /*_isChaseMode*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 4500) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		doAction(1007965783L /*FLING_SWING_WAIT*/, blendTime, onDoActionEnd -> {
			setVariable(0x6EA9665AL /*_FindDungeon_StartTime*/, getTime());
			changeState(state -> FindDungeon(blendTime));
		});
	}

	protected void ChaseOwner_MoveLv1_RetryCurve(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xECC08E64L /*ChaseOwner_MoveLv1_RetryCurve*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.RetryCurve, 0, 0, 0, 0, 10, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> ChaseOwner_MoveLv1(blendTime), 500)));
	}

	protected void ChaseOwner_MoveLv1_RetryCurveTurn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xB5AC9EDL /*ChaseOwner_MoveLv1_RetryCurveTurn*/);
		doAction(1596299921L /*FLING_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToPathCurve, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> ChaseOwner_MoveLv1_RetryCurve(blendTime), 500)));
	}

	protected void ChaseOwner_MoveLv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x643D5C4CL /*ChaseOwner_MoveLv1*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0xDEB1169FL /*_FindDungeon_IngTime*/, getTime());
		setVariable(0x57F8A701L /*_FindDungeon_EndTime*/, getVariable(0xDEB1169FL /*_FindDungeon_IngTime*/) - getVariable(0x6EA9665AL /*_FindDungeon_StartTime*/));
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 400) {
			if (changeState(state -> Flying_Random(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 800) {
			if (changeState(state -> ChaseOwner_MoveLv2(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(135998679L /*MOVE_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 50, 200, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_Summon(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Summon_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> ChaseOwner_MoveLv1(blendTime), 500)));
	}

	protected void ChaseOwner_MoveLv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF1003A81L /*ChaseOwner_MoveLv2*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0xDEB1169FL /*_FindDungeon_IngTime*/, getTime());
		setVariable(0x57F8A701L /*_FindDungeon_EndTime*/, getVariable(0xDEB1169FL /*_FindDungeon_IngTime*/) - getVariable(0x6EA9665AL /*_FindDungeon_StartTime*/));
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 6000) {
			if (changeState(state -> OwnerTeleport(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 400) {
			if (changeState(state -> Flying_Random(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 800) {
			if (changeState(state -> ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 1500) {
			if (changeState(state -> ChaseOwner_MoveLv3(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(2217774653L /*MOVE_FAST_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 50, 200, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_Summon(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Summon_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> ChaseOwner_MoveLv2(blendTime), 500)));
	}

	protected void ChaseOwner_MoveLv3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD22A0FD1L /*ChaseOwner_MoveLv3*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		setVariable(0xDEB1169FL /*_FindDungeon_IngTime*/, getTime());
		setVariable(0x57F8A701L /*_FindDungeon_EndTime*/, getVariable(0xDEB1169FL /*_FindDungeon_IngTime*/) - getVariable(0x6EA9665AL /*_FindDungeon_StartTime*/));
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 6000) {
			if (changeState(state -> OwnerTeleport(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 400) {
			if (changeState(state -> Flying_Random(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 500) {
			if (changeState(state -> ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(3004255870L /*MOVE_FAST2_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 50, 200, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_Summon(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Summon_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> ChaseOwner_MoveLv3(blendTime), 500)));
	}

	protected void ChaseOwner_Item(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x99340D03L /*ChaseOwner_Item*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 6000) {
			if (changeState(state -> OwnerTeleport(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 400) {
			if (changeState(state -> ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 0) {
			if (changeState(state -> FailFindPath_Summon(blendTime)))
				return;
		}
		doAction(3004255870L /*MOVE_FAST2_FLYING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 50, 200, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_Summon(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Summon_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> ChaseOwner_Item(blendTime), 500)));
	}

	protected void OwnerTeleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCB263523L /*OwnerTeleport*/);
		doTeleport(EAIMoveDestType.OwnerPosition, 100, 100, 1, 1);
		doAction(3473554812L /*FLING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Random(blendTime), 500));
	}

	protected void FailFindPath_Summon_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4992DCF6L /*FailFindPath_Summon_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 3) {
			if (changeState(state -> FailFindPath_Summon(0.3)))
				return;
		}
		changeState(state -> Flying_Random(blendTime));
	}

	protected void FailFindPath_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4FB597EBL /*FailFindPath_Summon*/);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 1, 1);
		doAction(3473554812L /*FLING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Flying_Random(blendTime), 500));
	}

	protected void FailFindPath_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x50CE1833L /*FailFindPath_Target*/);
		doTeleport(EAIMoveDestType.Random, 0, 50, 20, 30);
		changeState(state -> GetItem(blendTime));
	}

	protected void ChaseGetItem(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x102DF441L /*ChaseGetItem*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		if(getCallCount() == 10) {
			if (changeState(state -> FailFindPath_Target(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x3E491457L /*_Skill0_UsableDistance*/)) {
			if (changeState(state -> GetItem(blendTime)))
				return;
		}
		doAction(3279328567L /*MOVE_GETITEM_FLYING*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> ChaseGetItem(blendTime), 500)));
	}

	protected void GetItem(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3E8C153BL /*GetItem*/);
		doAction(3473554812L /*FLING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> ChaseOwner_Item(blendTime), 500));
	}

	protected void ChaseFindThatCollect(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xDBBFC18EL /*ChaseFindThatCollect*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 100) {
			if (changeState(state -> FindThatCollect(blendTime)))
				return;
		}
		doAction(2217774653L /*MOVE_FAST_FLYING*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_Summon(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Summon_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> ChaseFindThatCollect(blendTime), 500)));
	}

	protected void FindThatCollect(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD5DBE232L /*FindThatCollect*/);
		if(getCallCount() == 3) {
			if (changeState(state -> ChaseOwner_MoveLv2(0.3)))
				return;
		}
		doAction(3473554812L /*FLING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> FindThatCollect(blendTime), 500));
	}

	protected void ChaseFindThat(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x340C9EB7L /*ChaseFindThat*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Summon(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 220) {
			if (changeState(state -> FindThat(blendTime)))
				return;
		}
		doAction(2217774653L /*MOVE_FAST_FLYING*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_Summon(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Summon_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> ChaseFindThat(blendTime), 500)));
	}

	protected void FindThat(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC4921917L /*FindThat*/);
		doAction(3473554812L /*FLING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> FindThat_Up(blendTime), 500));
	}

	protected void FindThat_Up(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x996CD603L /*FindThat_Up*/);
		doAction(3748920357L /*MOVE_FLYING_UP*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 600, 100, 200, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> FindThat2(blendTime), 500)));
	}

	protected void FindThat2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x24B33327L /*FindThat2*/);
		doAction(1007965783L /*FLING_SWING_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> FindThat3(blendTime)));
	}

	protected void FindThat3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3E81D9F2L /*FindThat3*/);
		doAction(1007965783L /*FLING_SWING_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> FindThat4(blendTime)));
	}

	protected void FindThat4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9CB863F4L /*FindThat4*/);
		doAction(1007965783L /*FLING_SWING_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> ChaseOwner_MoveLv2(blendTime)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 5000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePetAttachBoned(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (0 == 0 && getSummonerClass() != 16 && getVariable(0x69C27CA9L /*_Attachable_Type*/) == 2) {
			if (changeState(state -> Owner_Landing(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (0 == 0 && getSummonerClass() == 16 && getVariable(0x69C27CA9L /*_Attachable_Type*/) == 2) {
			if (changeState(state -> Owner_Landing_PBW(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePetAttachable(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x69C27CA9L /*_Attachable_Type*/, eventData[0]);
		if (0 == 0 && getSummonerClass() != 16 && getVariable(0x69C27CA9L /*_Attachable_Type*/) == 2 && getState() != 0x7301136AL /*Owner_Take_Off*/ && getState() != 0x9BF9820AL /*Take_Off_Fall*/ && getState() != 0xC47D3E68L /*Take_Off_Falling_Wait*/ && getState() != 0x348C730L /*Take_Off_Fall_Ing*/ && getState() != 0x53449918L /*Take_Off_Fall_End*/) {
			if (changeState(state -> Owner_Landing_Chase(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (0 == 0 && getSummonerClass() == 16 && getVariable(0x69C27CA9L /*_Attachable_Type*/) == 2 && getState() != 0x7301136AL /*Owner_Take_Off*/ && getState() != 0x9BF9820AL /*Take_Off_Fall*/ && getState() != 0xC47D3E68L /*Take_Off_Falling_Wait*/ && getState() != 0x348C730L /*Take_Off_Fall_Ing*/ && getState() != 0x53449918L /*Take_Off_Fall_End*/) {
			if (changeState(state -> Owner_Landing_Chase_PBW(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePetNotAttachable(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Start_A(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handlePetFollowMaster(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xBA414EEEL /*_isChaseMode*/, 1);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handlePetWaitMaster(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xBA414EEEL /*_isChaseMode*/, 0);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handlePetGetItemOn(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xB4AEEB95L /*_isGetItemMode*/, 1);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handlePetGetItemOff(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xB4AEEB95L /*_isGetItemMode*/, 0);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handlePetFindThatOn(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xC3671716L /*_isFindThatMode*/, 1);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handlePetFindThatOff(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xC3671716L /*_isFindThatMode*/, 0);
		return EAiHandlerResult.BYPASS;
	}
}
