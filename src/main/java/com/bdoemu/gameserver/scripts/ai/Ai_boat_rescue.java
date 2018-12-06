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
@IAIName("boat_rescue")
public class Ai_boat_rescue extends CreatureAI {
	public Ai_boat_rescue(Creature actor, Map<Long, Integer> aiVariables) {
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
		setVariable(0x57D12EBAL /*_PositionValune*/, 0);
		setVariable(0xE74C0946L /*_Teleport_Start*/, 0);
		setVariable(0xCFC1508BL /*_Teleport_Ing*/, 0);
		setVariable(0xE951CD0BL /*_Teleport_End*/, 0);
		setVariable(0x9F475C95L /*_DeadLine_Distance*/, 0);
		setVariable(0xDF89561BL /*_Wait_Distance*/, 0);
		setVariable(0x51C05073L /*_Move_Distance*/, 0);
		setVariable(0x7C66BE44L /*_TargetDistance*/, 0);
		setVariable(0xE74C0946L /*_Teleport_Start*/, getTime());
		setVariable(0x26243C4BL /*_Damage_KnockBack*/, getVariable(0xCA081A50L /*AI_Damage_KnockBack*/));
		setVariable(0x65B16C16L /*_Damage_KnockDown*/, getVariable(0xF9798513L /*AI_Damage_KnockDown*/));
		setVariable(0xB0B44BDAL /*_Damage_Bound*/, getVariable(0x9B63B813L /*AI_Damage_Bound*/));
		setVariable(0x71B20CF2L /*_Damage_Stun*/, getVariable(0x7EBC0F53L /*AI_Damage_Stun*/));
		setVariable(0xA09E148EL /*_Damage_Capture*/, getVariable(0x4A7D3EF6L /*AI_Damage_Capture*/));
		setVariable(0xB9CFA843L /*_Damage_Rigid*/, getVariable(0xA558FAB7L /*AI_Damage_Rigid*/));
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Hide(blendTime), 500));
	}

	protected void Wait_Hide(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC34C44EDL /*Wait_Hide*/);
		setVariable(0xCFC1508BL /*_Teleport_Ing*/, getTime());
		setVariable(0xE951CD0BL /*_Teleport_End*/, getVariable(0xCFC1508BL /*_Teleport_Ing*/) - getVariable(0xE74C0946L /*_Teleport_Start*/));
		if (getVariable(0xE951CD0BL /*_Teleport_End*/) > 4000 && (getActor().getRegionId() == 836 || getActor().getRegionId() == 837 || getActor().getRegionId() == 838 || getActor().getRegionId() == 839 || getActor().getRegionId() == 840 || getActor().getRegionId() == 841 || getActor().getRegionId() == 842 || getActor().getRegionId() == 843 || getActor().getRegionId() == 844 || getActor().getRegionId() == 845 || getActor().getRegionId() == 846 || getActor().getRegionId() == 847 || getActor().getRegionId() == 848 || getActor().getRegionId() == 849)) {
			if (changeState(state -> GoTo_Rot(blendTime)))
				return;
		}
		if (getVariable(0xE951CD0BL /*_Teleport_End*/) > 4000 && (getActor().getRegionId() == 822 || getActor().getRegionId() == 823 || getActor().getRegionId() == 824 || getActor().getRegionId() == 825 || getActor().getRegionId() == 826 || getActor().getRegionId() == 827 || getActor().getRegionId() == 828 || getActor().getRegionId() == 829 || getActor().getRegionId() == 830 || getActor().getRegionId() == 831 || getActor().getRegionId() == 832 || getActor().getRegionId() == 833 || getActor().getRegionId() == 834 || getActor().getRegionId() == 835 || getActor().getRegionId() == 864 || getActor().getRegionId() == 865 || getActor().getRegionId() == 866 || getActor().getRegionId() == 867 || getActor().getRegionId() == 868 || getActor().getRegionId() == 869 || getActor().getRegionId() == 870 || getActor().getRegionId() == 353 || getActor().getRegionId() == 359 || getActor().getRegionId() == 360 || getActor().getRegionId() == 361 || getActor().getRegionId() == 569 || getActor().getRegionId() == 701)) {
			if (changeState(state -> GoTo_Lema(blendTime)))
				return;
		}
		doAction(2877983296L /*WAIT_HIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Hide(blendTime), 1000));
	}

	protected void Wait_Fail(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x32231DCFL /*Wait_Fail*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Hide(blendTime), 1000));
	}

	protected void GoTo_Rot(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x82F18D1L /*GoTo_Rot*/);
		doTeleportWithOwnerToWaypoint("rescue_rot_0050", "carriage", -3000, 0, 100, 200);
		doAction(2877983296L /*WAIT_HIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Coming_Rot(blendTime), 500));
	}

	protected void GoTo_Lema(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x79FDCD42L /*GoTo_Lema*/);
		doTeleportWithOwnerToWaypoint("rescue_lema_0050", "carriage", -3000, 0, 100, 200);
		doAction(2877983296L /*WAIT_HIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Coming_Lema(blendTime), 500));
	}

	protected void Wait_Coming_Rot(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x79CD5935L /*Wait_Coming_Rot*/);
		setVariable(0xDF89561BL /*_Wait_Distance*/, getDistanceToOwner());
		if (getVariable(0xDF89561BL /*_Wait_Distance*/) < 1200) {
			if (changeState(state -> Wait_Ready_Rot(blendTime)))
				return;
		}
		doAction(1842393899L /*MOVE_APPROCH*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 100, 120, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Coming_Rot(blendTime), 1000)));
	}

	protected void Wait_Coming_Lema(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC27362C2L /*Wait_Coming_Lema*/);
		setVariable(0xDF89561BL /*_Wait_Distance*/, getDistanceToOwner());
		if (getVariable(0xDF89561BL /*_Wait_Distance*/) < 1200) {
			if (changeState(state -> Wait_Ready_Lema(blendTime)))
				return;
		}
		doAction(1842393899L /*MOVE_APPROCH*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 1200, 1500, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Coming_Lema(blendTime), 1000)));
	}

	protected void Wait_Ready_Rot(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA1DEF94CL /*Wait_Ready_Rot*/);
		setVariable(0x57D12EBAL /*_PositionValune*/, 1);
		doAction(2036513131L /*WAIT_READY_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Ready(blendTime), 1000));
	}

	protected void Wait_Ready_Lema(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF4D383B2L /*Wait_Ready_Lema*/);
		setVariable(0x57D12EBAL /*_PositionValune*/, 2);
		doAction(2036513131L /*WAIT_READY_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Ready(blendTime), 1000));
	}

	protected void Wait_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DF7F521L /*Wait_Ready*/);
		setVariable(0x9F475C95L /*_DeadLine_Distance*/, getDistanceToOwner());
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 250) {
			if (changeState(state -> Suicide_Die(blendTime)))
				return;
		}
		if (getVariable(0x9F475C95L /*_DeadLine_Distance*/) >= 10000) {
			if (changeState(state -> Wait_Suicide(blendTime)))
				return;
		}
		doAction(1610588688L /*WAIT_READY*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Ready(blendTime), 1000));
	}

	protected void Wait_Suicide(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE9D8BD7EL /*Wait_Suicide*/);
		setVariable(0x9F475C95L /*_DeadLine_Distance*/, getDistanceToOwner());
		if (getVariable(0x9F475C95L /*_DeadLine_Distance*/) < 10000) {
			if (changeState(state -> Wait_Ready(blendTime)))
				return;
		}
		if(getCallCount() == 600) {
			if (changeState(state -> Suicide_Die(blendTime)))
				return;
		}
		doAction(1610588688L /*WAIT_READY*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Suicide(blendTime), 1000));
	}

	protected void Departure_Rot(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1AC9AFD4L /*Departure_Rot*/);
		doAction(4060078600L /*MOVING_START*/, blendTime, onDoActionEnd -> scheduleState(state -> Departure_Rot_Ing(blendTime), 1000));
	}

	protected void Departure_Rot_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEB2683C0L /*Departure_Rot_Ing*/);
		setVariable(0x51C05073L /*_Move_Distance*/, getDistanceToOwner());
		if (getVariable(0x51C05073L /*_Move_Distance*/) > 3000 && getVariable(0x51C05073L /*_Move_Distance*/) < 10000) {
			if (changeState(state -> Wait_Ready_Departure(blendTime)))
				return;
		}
		if(Rnd.getChance(1)) {
			if (changeState(state -> Departure_Rot_Song_Ready(0)))
				return;
		}
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "rescue_rot_0001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Unsummon(blendTime), 1000)));
	}

	protected void Departure_Rot_Song_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC252913L /*Departure_Rot_Song_Ready*/);
		setVariable(0x51C05073L /*_Move_Distance*/, getDistanceToOwner());
		if (getVariable(0x51C05073L /*_Move_Distance*/) > 3000 && getVariable(0x51C05073L /*_Move_Distance*/) < 10000) {
			if (changeState(state -> Wait_Ready_Departure(blendTime)))
				return;
		}
		if(Rnd.getChance(1)) {
			if (changeState(state -> Departure_Rot_Song(0)))
				return;
		}
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "rescue_rot_0001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Unsummon(blendTime), 1000)));
	}

	protected void Departure_Rot_Song(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8DA9F3F4L /*Departure_Rot_Song*/);
		setVariable(0x51C05073L /*_Move_Distance*/, getDistanceToOwner());
		if (getVariable(0x51C05073L /*_Move_Distance*/) > 3000 && getVariable(0x51C05073L /*_Move_Distance*/) < 10000) {
			if (changeState(state -> Wait_Ready_Departure(blendTime)))
				return;
		}
		doAction(1701890858L /*MOVE_SONG*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "rescue_rot_0001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Unsummon(blendTime), 1000)));
	}

	protected void Departure_Lema(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC5A51D74L /*Departure_Lema*/);
		doAction(4060078600L /*MOVING_START*/, blendTime, onDoActionEnd -> scheduleState(state -> Departure_Lema_Ing(blendTime), 1000));
	}

	protected void Departure_Lema_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB8E766ABL /*Departure_Lema_Ing*/);
		setVariable(0x51C05073L /*_Move_Distance*/, getDistanceToOwner());
		if (getVariable(0x51C05073L /*_Move_Distance*/) > 3000 && getVariable(0x51C05073L /*_Move_Distance*/) < 10000) {
			if (changeState(state -> Wait_Ready_Departure(blendTime)))
				return;
		}
		if(Rnd.getChance(1)) {
			if (changeState(state -> Departure_Lema_Song_Ready(0)))
				return;
		}
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "rescue_lema_0001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Unsummon(blendTime), 1000)));
	}

	protected void Departure_Lema_Song_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x39AC6CBCL /*Departure_Lema_Song_Ready*/);
		setVariable(0x51C05073L /*_Move_Distance*/, getDistanceToOwner());
		if (getVariable(0x51C05073L /*_Move_Distance*/) > 3000 && getVariable(0x51C05073L /*_Move_Distance*/) < 10000) {
			if (changeState(state -> Wait_Ready_Departure(blendTime)))
				return;
		}
		if(Rnd.getChance(1)) {
			if (changeState(state -> Departure_Lema_Song(0)))
				return;
		}
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "rescue_lema_0001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Unsummon(blendTime), 1000)));
	}

	protected void Departure_Lema_Song(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x549CFE11L /*Departure_Lema_Song*/);
		setVariable(0x51C05073L /*_Move_Distance*/, getDistanceToOwner());
		if (getVariable(0x51C05073L /*_Move_Distance*/) > 3000 && getVariable(0x51C05073L /*_Move_Distance*/) < 10000) {
			if (changeState(state -> Wait_Ready_Departure(blendTime)))
				return;
		}
		doAction(1701890858L /*MOVE_SONG*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "rescue_lema_0001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Unsummon(blendTime), 1000)));
	}

	protected void Wait_Ready_Departure(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFC6BDFE4L /*Wait_Ready_Departure*/);
		setVariable(0x9F475C95L /*_DeadLine_Distance*/, getDistanceToOwner());
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 250) {
			if (changeState(state -> Suicide_Die(blendTime)))
				return;
		}
		if (getVariable(0x9F475C95L /*_DeadLine_Distance*/) >= 10000) {
			if (changeState(state -> Wait_Suicide_Departure(blendTime)))
				return;
		}
		doAction(4162015127L /*WAIT_READY_2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Ready_Departure(blendTime), 1000));
	}

	protected void Wait_Suicide_Departure(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4E089BDBL /*Wait_Suicide_Departure*/);
		setVariable(0x9F475C95L /*_DeadLine_Distance*/, getDistanceToOwner());
		if (getVariable(0x9F475C95L /*_DeadLine_Distance*/) < 10000) {
			if (changeState(state -> Wait_Ready_Departure(blendTime)))
				return;
		}
		if(getCallCount() == 600) {
			if (changeState(state -> Suicide_Die(blendTime)))
				return;
		}
		doAction(1610588688L /*WAIT_READY*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Suicide_Departure(blendTime), 1000));
	}

	protected void Wait_Unsummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x876CED81L /*Wait_Unsummon*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Unsummon_Teleport(blendTime), 100000));
	}

	protected void Wait_Unsummon_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBB14B3E0L /*Wait_Unsummon_Teleport*/);
		if (getVariable(0x57D12EBAL /*_PositionValune*/) == 1) {
			if (changeState(state -> Wait_Unsummon_Rot(0.3)))
				return;
		}
		if (getVariable(0x57D12EBAL /*_PositionValune*/) == 2) {
			if (changeState(state -> Wait_Unsummon_Lema(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Unsummon_Teleport(blendTime), 1000));
	}

	protected void Wait_Unsummon_Rot(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x40656BBCL /*Wait_Unsummon_Rot*/);
		doTeleportPassengerToWaypoint("rescue_rot_end", "teleport", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Ready_Unsummon(blendTime), 1000));
	}

	protected void Wait_Unsummon_Lema(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1FF85EE6L /*Wait_Unsummon_Lema*/);
		doTeleportPassengerToWaypoint("rescue_lema_end", "teleport", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Ready_Unsummon(blendTime), 1000));
	}

	protected void Ready_Unsummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x55BE00AFL /*Ready_Unsummon*/);
		setVariable(0x9F475C95L /*_DeadLine_Distance*/, getDistanceToOwner());
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 250) {
			if (changeState(state -> Suicide_Die(blendTime)))
				return;
		}
		if (getVariable(0x9F475C95L /*_DeadLine_Distance*/) >= 10000) {
			if(getCallCount() == 2) {
				if (changeState(state -> Suicide_Die(blendTime)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Ready_Unsummon(blendTime), 100000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x9F475C95L /*_DeadLine_Distance*/, getDistanceToOwner());
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 250) {
			if (changeState(state -> Suicide_Die(blendTime)))
				return;
		}
		if (getVariable(0x9F475C95L /*_DeadLine_Distance*/) >= 100000) {
			if (changeState(state -> Suicide_Die(blendTime)))
				return;
		}
		if(getCallCount() == 1200) {
			if (changeState(state -> Suicide_Die(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Ready_Departure(blendTime), 1000));
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
		if (changeState(state -> Wait(0.1)))
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

	@Override
	public EAiHandlerResult HandleWorkingStart(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x57D12EBAL /*_PositionValune*/) == 0) {
			if (changeState(state -> Wait_Fail(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x57D12EBAL /*_PositionValune*/) == 1) {
			if (changeState(state -> Departure_Rot(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x57D12EBAL /*_PositionValune*/) == 2) {
			if (changeState(state -> Departure_Lema(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
