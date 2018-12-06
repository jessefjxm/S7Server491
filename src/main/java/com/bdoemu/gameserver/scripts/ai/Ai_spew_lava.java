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
@IAIName("spew_lava")
public class Ai_spew_lava extends CreatureAI {
	public Ai_spew_lava(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x334ACDCL /*_WayPointNo*/, getVariable(0x3E98C119L /*AI_WayPointNo*/));
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Logic(blendTime), 100 + Rnd.get(-0,0)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if(getCallCount() == 1) {
			if (changeState(state -> SpewLava(0.3)))
				return;
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 120000) {
			if (changeState(state -> Die(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2000));
	}

	protected void SpewLava(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x954B79F7L /*SpewLava*/);
		doAction(3037383091L /*FALL*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 100000));
	}

	protected void Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA5BDA01CL /*Teleport_Logic*/);
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 1) {
			if (changeState(state -> Teleport1(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 2) {
			if (changeState(state -> Teleport2(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 3) {
			if (changeState(state -> Teleport3(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 4) {
			if (changeState(state -> Teleport4(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 5) {
			if (changeState(state -> Teleport5(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 6) {
			if (changeState(state -> Teleport6(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 7) {
			if (changeState(state -> Teleport7(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 8) {
			if (changeState(state -> Teleport8(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 9) {
			if (changeState(state -> Teleport9(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 10) {
			if (changeState(state -> Teleport10(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 11) {
			if (changeState(state -> Teleport11(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 12) {
			if (changeState(state -> Teleport12(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 13) {
			if (changeState(state -> Teleport13(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 14) {
			if (changeState(state -> Teleport14(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 15) {
			if (changeState(state -> Teleport15(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 16) {
			if (changeState(state -> Teleport16(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 17) {
			if (changeState(state -> Teleport17(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 18) {
			if (changeState(state -> Teleport18(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 19) {
			if (changeState(state -> Teleport19(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 20) {
			if (changeState(state -> Teleport20(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 21) {
			if (changeState(state -> Teleport21(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 22) {
			if (changeState(state -> Teleport22(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 23) {
			if (changeState(state -> Teleport23(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 24) {
			if (changeState(state -> Teleport24(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 25) {
			if (changeState(state -> Teleport25(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 26) {
			if (changeState(state -> Teleport26(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 27) {
			if (changeState(state -> Teleport27(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 28) {
			if (changeState(state -> Teleport28(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 29) {
			if (changeState(state -> Teleport29(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 30) {
			if (changeState(state -> Teleport30(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 31) {
			if (changeState(state -> Teleport31(0.3)))
				return;
		}
		if (getVariable(0x334ACDCL /*_WayPointNo*/) == 32) {
			if (changeState(state -> Teleport32(0.3)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE9440C24L /*Teleport1*/);
		doTeleport(EAIMoveDestType.Absolute, 274304, -30192, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD2C8F9C7L /*Teleport2*/);
		doTeleport(EAIMoveDestType.Absolute, 275110, -29186, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4B8A84D5L /*Teleport3*/);
		doTeleport(EAIMoveDestType.Absolute, 274614, -27957, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7D0D3AF9L /*Teleport4*/);
		doTeleport(EAIMoveDestType.Absolute, 276226, -29009, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x744787D6L /*Teleport5*/);
		doTeleport(EAIMoveDestType.Absolute, 275478, -30613, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF347A7EL /*Teleport6*/);
		doTeleport(EAIMoveDestType.Absolute, 274099, -28922, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF06708EL /*Teleport7*/);
		doTeleport(EAIMoveDestType.Absolute, 273507, -27065, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x53EC05A5L /*Teleport8*/);
		doTeleport(EAIMoveDestType.Absolute, 273788, -25731, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x523B10B6L /*Teleport9*/);
		doTeleport(EAIMoveDestType.Absolute, 272028, -27694, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3264286AL /*Teleport10*/);
		doTeleport(EAIMoveDestType.Absolute, 272437, -29657, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport11(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x47F2AC3FL /*Teleport11*/);
		doTeleport(EAIMoveDestType.Absolute, 273020, -31789, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport12(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBC877C76L /*Teleport12*/);
		doTeleport(EAIMoveDestType.Absolute, 274495, -33235, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport13(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE772BF87L /*Teleport13*/);
		doTeleport(EAIMoveDestType.Absolute, 276796, -33107, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport14(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x723667A8L /*Teleport14*/);
		doTeleport(EAIMoveDestType.Absolute, 278250, -32290, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD2BCBE2FL /*Teleport15*/);
		doTeleport(EAIMoveDestType.Absolute, 277168, -31429, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport16(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD298EA49L /*Teleport16*/);
		doTeleport(EAIMoveDestType.Absolute, 279559, -30256, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport17(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8EC2F1FBL /*Teleport17*/);
		doTeleport(EAIMoveDestType.Absolute, 278914, -28080, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport18(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8B6695FBL /*Teleport18*/);
		doTeleport(EAIMoveDestType.Absolute, 276915, -28077, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport19(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF937B51CL /*Teleport19*/);
		doTeleport(EAIMoveDestType.Absolute, 276536, -26095, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport20(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2C3CD9F0L /*Teleport20*/);
		doTeleport(EAIMoveDestType.Absolute, 271533, -31152, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport21(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8ABCEB41L /*Teleport21*/);
		doTeleport(EAIMoveDestType.Absolute, 271637, -28727, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport22(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xAC518F1L /*Teleport22*/);
		doTeleport(EAIMoveDestType.Absolute, 277705, -26613, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport23(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE1477AF3L /*Teleport23*/);
		doTeleport(EAIMoveDestType.Absolute, 275434, -25312, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport24(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCDB7A5A9L /*Teleport24*/);
		doTeleport(EAIMoveDestType.Absolute, 274677, -26780, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB4EEDFCCL /*Teleport25*/);
		doTeleport(EAIMoveDestType.Absolute, 272501, -26503, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport26(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x83C8E802L /*Teleport26*/);
		doTeleport(EAIMoveDestType.Absolute, 272986, -25311, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport27(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA9E19E68L /*Teleport27*/);
		doTeleport(EAIMoveDestType.Absolute, 275687, -27749, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport28(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3B195CD6L /*Teleport28*/);
		doTeleport(EAIMoveDestType.Absolute, 278029, -30884, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport29(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x79FB554DL /*Teleport29*/);
		doTeleport(EAIMoveDestType.Absolute, 277598, -29330, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9951DB61L /*Teleport30*/);
		doTeleport(EAIMoveDestType.Absolute, 276571, -29955, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport31(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD20238D5L /*Teleport31*/);
		doTeleport(EAIMoveDestType.Absolute, 273001, -30680, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	protected void Teleport32(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xEAB5BD21L /*Teleport32*/);
		doTeleport(EAIMoveDestType.Absolute, 274722, -31640, 100, 1000);
		changeState(state -> Wait(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleSpewLava(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> SpewLava(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
