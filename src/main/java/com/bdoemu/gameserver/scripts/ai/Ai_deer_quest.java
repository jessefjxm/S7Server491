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
@IAIName("deer_quest")
public class Ai_deer_quest extends CreatureAI {
	public Ai_deer_quest(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0xB06049AFL /*_Detection*/, 0);
		setVariable(0xF792E187L /*_MovingKind*/, getVariable(0x16D76012L /*AI_MovingKind*/));
		setVariable(0x7492188CL /*_FailFindPath*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x7C66BE44L /*_TargetDistance*/, 0);
		setVariable(0x26243C4BL /*_Damage_KnockBack*/, getVariable(0xCA081A50L /*AI_Damage_KnockBack*/));
		setVariable(0x65B16C16L /*_Damage_KnockDown*/, getVariable(0xF9798513L /*AI_Damage_KnockDown*/));
		setVariable(0xB0B44BDAL /*_Damage_Bound*/, getVariable(0x9B63B813L /*AI_Damage_Bound*/));
		setVariable(0x71B20CF2L /*_Damage_Stun*/, getVariable(0x7EBC0F53L /*AI_Damage_Stun*/));
		setVariable(0xA09E148EL /*_Damage_Capture*/, getVariable(0x4A7D3EF6L /*AI_Damage_Capture*/));
		setVariable(0xB9CFA843L /*_Damage_Rigid*/, getVariable(0xA558FAB7L /*AI_Damage_Rigid*/));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		setVariableArray(0x2C4960E5L /*_WaypointValue1*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x3DFFB456L /*AI_WayPointKey1*/) });
		setVariableArray(0x11AADB03L /*_WaypointValue2*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xCD7084F0L /*AI_WayPointKey2*/) });
		setVariableArray(0x9425998CL /*_WaypointValue3*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x73DB50B5L /*AI_WayPointKey3*/) });
		setVariableArray(0x58ADA3CL /*_WaypointValue4*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x8385DA8EL /*AI_WayPointKey4*/) });
		setVariableArray(0x9FFA920EL /*_WaypointValue5*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xF9FBC5A4L /*AI_WayPointKey5*/) });
		setVariableArray(0x95688E57L /*_WaypointValue6*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xD1342C91L /*AI_WayPointKey6*/) });
		setVariableArray(0x62D7BE48L /*_WaypointValue7*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x488AA4CFL /*AI_WayPointKey7*/) });
		setVariableArray(0x5C40916BL /*_WaypointValue8*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x180FE931L /*AI_WayPointKey8*/) });
		setVariableArray(0x61317658L /*_WaypointValue9*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xD1B3C6F5L /*AI_WayPointKey9*/) });
		setVariableArray(0x178B3B3BL /*_WaypointValue10*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xB0F128A8L /*AI_WayPointKey10*/) });
		setVariable(0xF6F07A28L /*_DeadLine_Start*/, 0);
		setVariable(0x669AEA2FL /*_DeadLine_Ing*/, 0);
		setVariable(0xAA21B648L /*_DeadLine_End*/, 0);
		setVariable(0xF6F07A28L /*_DeadLine_Start*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 500));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x881B0A76L /*Start_Action*/);
		doTeleportToWaypoint("bal_deer_quest_117", "monster_patrol", 0, 0, 0, 0);
		changeState(state -> Start_Action2(blendTime));
	}

	protected void Start_Action2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC141FFFAL /*Start_Action2*/);
		doAction(3112710415L /*MOVE_RUNAWAY2*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "bal_deer_quest_001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 5000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Detect_Enemy(0.3)))
				return;
		}
		setVariable(0x669AEA2FL /*_DeadLine_Ing*/, getTime());
		setVariable(0xAA21B648L /*_DeadLine_End*/, getVariable(0x669AEA2FL /*_DeadLine_Ing*/) - getVariable(0xF6F07A28L /*_DeadLine_Start*/));
		if (getVariable(0xAA21B648L /*_DeadLine_End*/) > 1800000) {
			if (changeState(state -> Suicide_Die(blendTime)))
				return;
		}
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 250) {
			if (changeState(state -> Suicide_Die(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void Detect_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x97099137L /*Detect_Enemy*/);
		setVariable(0x7C66BE44L /*_TargetDistance*/, getDistanceToTarget());
		if (getVariable(0x7C66BE44L /*_TargetDistance*/) < 2900) {
			if (changeState(state -> Loop_Logic_Detect(blendTime)))
				return;
		}
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Detect_Enemy(blendTime), 500));
	}

	protected void Loop_Logic_Detect(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCCB296C2L /*Loop_Logic_Detect*/);
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 10) {
			if (changeState(state -> Move_Destination01(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 0) {
			if (changeState(state -> Move_Destination02(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 1) {
			if (changeState(state -> Move_Destination02(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 2) {
			if (changeState(state -> Move_Destination03(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 3) {
			if (changeState(state -> Move_Destination04(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 4) {
			if (changeState(state -> Move_Destination05(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 5) {
			if (changeState(state -> Move_Destination06(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 6) {
			if (changeState(state -> Move_Destination07(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 7) {
			if (changeState(state -> Move_Destination08(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 8) {
			if (changeState(state -> Move_Destination09(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 9) {
			if (changeState(state -> Move_Destination10(blendTime)))
				return;
		}
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Loop_Logic_Detect(blendTime), 500));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Suicide_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x2BD8C797L /*Suicide_Die*/);
		doAction(122194675L /*SUISIDE_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Move_Destination01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2B522FD3L /*Move_Destination01*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Destination02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA1B3446AL /*Move_Destination02*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Destination03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6CB1CDB3L /*Move_Destination03*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Destination04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDEB2B370L /*Move_Destination04*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Destination05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF7FE7C08L /*Move_Destination05*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 5);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Destination06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA3F27E88L /*Move_Destination06*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 6);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x95688E57L /*_WaypointValue6*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Destination07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x30F34246L /*Move_Destination07*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 7);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x62D7BE48L /*_WaypointValue7*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Destination08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x12C2841EL /*Move_Destination08*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 8);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x5C40916BL /*_WaypointValue8*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Destination09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x41FE592DL /*Move_Destination09*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 9);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x61317658L /*_WaypointValue9*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Destination10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCA12979DL /*Move_Destination10*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 10);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x178B3B3BL /*_WaypointValue10*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 1000);
		});
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 3000);
		});
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 1000);
		});
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 1000);
		});
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 5000);
		});
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 2000);
		});
	}

	protected void Damage_Fear(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBF1D8728L /*Damage_Fear*/);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 400, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Damage_Fear(blendTime), 400)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Loop_Logic_Detect(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x26243C4BL /*_Damage_KnockBack*/) == 1) {
			if (changeState(state -> Damage_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x65B16C16L /*_Damage_KnockDown*/) == 1) {
			if (changeState(state -> Damage_KnockDown(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xB0B44BDAL /*_Damage_Bound*/) == 1) {
			if (changeState(state -> Damage_Bound(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x71B20CF2L /*_Damage_Stun*/) == 1) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xA09E148EL /*_Damage_Capture*/) == 1) {
			if (changeState(state -> Damage_Capture(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRigid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xB9CFA843L /*_Damage_Rigid*/) == 1) {
			if (changeState(state -> Damage_Rigid(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
