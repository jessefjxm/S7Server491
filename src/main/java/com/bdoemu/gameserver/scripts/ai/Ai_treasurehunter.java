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
@IAIName("treasurehunter")
public class Ai_treasurehunter extends CreatureAI {
	public Ai_treasurehunter(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xC9C6B377L /*_Es_Distance*/, getVariable(0x5E4F73ADL /*AI_Es_Distance*/));
		setVariable(0x9064715EL /*_Es_RunDistance*/, getVariable(0xBA482B0EL /*AI_Es_RunDistance*/));
		setVariable(0x646D0419L /*_Limited_StartTime*/, 0);
		setVariable(0x1CF7D86BL /*_Limited_IngTime*/, 0);
		setVariable(0xC267F07AL /*_Limited_EndTime*/, 0);
		setVariable(0xD6EBA350L /*_MeshOff_StartTime*/, 0);
		setVariable(0x9DBE9B97L /*_MeshOff_EndTime*/, 0);
		setVariable(0x1FB1A0DL /*_MeshOff_IngTime*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Logic(blendTime), 1000));
	}

	protected void Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9048C900L /*Wait_Logic*/);
		clearAggro(true);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0) {
			setVariable(0xD6EBA350L /*_MeshOff_StartTime*/, getTime());
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
			if (changeState(state -> MeshOn_Wait(blendTime)))
				return;
		}
		changeState(state -> MeshOff_Wait(blendTime));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.recovery);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		clearAggro(true);
		doAction(1713207886L /*MESHOFF_WAIT*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return;
			}
			scheduleState(state -> Wait_Logic(blendTime), 10000);
		});
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 5) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		changeState(state -> Wait_Logic(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(1713207886L /*MESHOFF_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 2000));
	}

	protected void MeshOff_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB69D0DF2L /*MeshOff_Wait*/);
		setVariable(0x1FB1A0DL /*_MeshOff_IngTime*/, getTime());
		setVariable(0x9DBE9B97L /*_MeshOff_EndTime*/, getVariable(0x1FB1A0DL /*_MeshOff_IngTime*/) - getVariable(0xD6EBA350L /*_MeshOff_StartTime*/));
		if (getVariable(0x9DBE9B97L /*_MeshOff_EndTime*/) >= 60000) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2500 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		if(Rnd.getChance(getVariable(0x64490D98L /*AI_RandomMove*/))) {
			if (changeState(state -> MeshOff_Move_Random(blendTime)))
				return;
		}
		doAction(1713207886L /*MESHOFF_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> MeshOff_Wait(blendTime), 3000));
	}

	protected void MeshOff_Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5F59D80BL /*MeshOff_Move_Random*/);
		setVariable(0x1FB1A0DL /*_MeshOff_IngTime*/, getTime());
		setVariable(0x9DBE9B97L /*_MeshOff_EndTime*/, getVariable(0x1FB1A0DL /*_MeshOff_IngTime*/) - getVariable(0xD6EBA350L /*_MeshOff_StartTime*/));
		if (getVariable(0x9DBE9B97L /*_MeshOff_EndTime*/) >= 60000) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2500 && isCreatureVisible(object, false))) {
				if (changeState(state -> Search_Enemy(0.3)))
					return;
			}
		}
		doAction(145993149L /*MESHOFF_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> MeshOff_Wait(blendTime), 4000)));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		setVariable(0x646D0419L /*_Limited_StartTime*/, getTime());
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(4220947136L /*MESHON_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Logic(blendTime), 3000));
	}

	protected void MeshOn_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB9D5C086L /*MeshOn_Wait*/);
		setVariable(0x1CF7D86BL /*_Limited_IngTime*/, getTime());
		setVariable(0xC267F07AL /*_Limited_EndTime*/, getVariable(0x1CF7D86BL /*_Limited_IngTime*/) - getVariable(0x646D0419L /*_Limited_StartTime*/));
		if (getVariable(0xC267F07AL /*_Limited_EndTime*/) >= 180000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Escape(0.3)))
				return;
		}
		if(Rnd.getChance(getVariable(0x64490D98L /*AI_RandomMove*/))) {
			if (changeState(state -> MeshOn_Move_Random(blendTime)))
				return;
		}
		doAction(4220947136L /*MESHON_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Logic(blendTime), 3000));
	}

	protected void MeshOn_Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5CCDEA2BL /*MeshOn_Move_Random*/);
		setVariable(0x1CF7D86BL /*_Limited_IngTime*/, getTime());
		setVariable(0xC267F07AL /*_Limited_EndTime*/, getVariable(0x1CF7D86BL /*_Limited_IngTime*/) - getVariable(0x646D0419L /*_Limited_StartTime*/));
		if (getVariable(0xC267F07AL /*_Limited_EndTime*/) >= 180000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Escape(0.3)))
				return;
		}
		doAction(2778595066L /*MESHON_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Logic(blendTime), 4000)));
	}

	protected void Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xDCE8DF7DL /*Escape*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Logic(blendTime)))
				return;
		}
		setVariable(0x1CF7D86BL /*_Limited_IngTime*/, getTime());
		setVariable(0xC267F07AL /*_Limited_EndTime*/, getVariable(0x1CF7D86BL /*_Limited_IngTime*/) - getVariable(0x646D0419L /*_Limited_StartTime*/));
		if (getVariable(0xC267F07AL /*_Limited_EndTime*/) >= 180000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x9064715EL /*_Es_RunDistance*/)) {
			if (changeState(state -> Escape_Run(0.3)))
				return;
		}
		doAction(2778595066L /*MESHON_WALK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Logic(blendTime), 2000)));
	}

	protected void Escape_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xA5DC3AFDL /*Escape_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait_Logic(blendTime)))
				return;
		}
		setVariable(0x1CF7D86BL /*_Limited_IngTime*/, getTime());
		setVariable(0xC267F07AL /*_Limited_EndTime*/, getVariable(0x1CF7D86BL /*_Limited_IngTime*/) - getVariable(0x646D0419L /*_Limited_StartTime*/));
		if (getVariable(0xC267F07AL /*_Limited_EndTime*/) >= 180000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(864309740L /*MESHON_RUN*/, blendTime, onDoActionEnd -> escape(3000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Logic(blendTime), 2000)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Escape_Run(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
