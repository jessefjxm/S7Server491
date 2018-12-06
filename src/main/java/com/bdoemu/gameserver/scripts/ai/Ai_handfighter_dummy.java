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
@IAIName("handfighter_dummy")
public class Ai_handfighter_dummy extends CreatureAI {
	public Ai_handfighter_dummy(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		if (getVariable(0x392FB67DL /*_Grab1*/) == 1) {
			if (changeState(state -> Grab1(0.0)))
				return;
		}
		if (getVariable(0xA3A75864L /*_Grab2*/) == 1) {
			if (changeState(state -> Grab2(0.0)))
				return;
		}
		if (getVariable(0x4A4ACF41L /*_Grab3*/) == 1) {
			if (changeState(state -> Grab3(0.0)))
				return;
		}
		if (getVariable(0x58C7AF65L /*_Grab4*/) == 1) {
			if (changeState(state -> Grab4(0.0)))
				return;
		}
		if (getVariable(0xC33510A6L /*_Dodge*/) == 1) {
			if (changeState(state -> Dodge(0.0)))
				return;
		}
		if (getVariable(0x4D2E6DE4L /*_EvadeBackAttack*/) == 1) {
			if (changeState(state -> EvadeBackAttack_Start(0.0)))
				return;
		}
		if (getVariable(0x73897017L /*_HitDashAttack*/) == 1) {
			if (changeState(state -> HitDashAttack(0.0)))
				return;
		}
		if (getVariable(0xB7B4FAE8L /*_DashAttackBomb*/) == 1) {
			if (changeState(state -> DashAttackBomb(0.0)))
				return;
		}
		if (getVariable(0x76F9F3EBL /*_Extermination*/) == 1) {
			if (changeState(state -> Extermination(0.0)))
				return;
		}
		if (getVariable(0x3C610952L /*_Rampage1*/) == 1) {
			if (changeState(state -> Rampage1_Start(0.0)))
				return;
		}
		if (getVariable(0xD1A8AC2AL /*_Rampage2*/) == 1) {
			if (changeState(state -> Rampage2_Start(0.0)))
				return;
		}
		if (getVariable(0x99E20B20L /*_Rampage3*/) == 1) {
			if (changeState(state -> Rampage3_Start(0.0)))
				return;
		}
		if (getVariable(0x8355A200L /*_Rampage4*/) == 1) {
			if (changeState(state -> Rampage4_Start(0.0)))
				return;
		}
		if (getVariable(0xE5B712A7L /*_Rampage5*/) == 1) {
			if (changeState(state -> Rampage5_Start(0.0)))
				return;
		}
		if (getVariable(0xD4019F52L /*_Rampage6*/) == 1) {
			if (changeState(state -> Rampage6(0.0)))
				return;
		}
		if (getVariable(0x47FBE565L /*_RisingDragon_R*/) == 1) {
			if (changeState(state -> RisingDragon_R_Start(0.0)))
				return;
		}
		if (getVariable(0x55F29D43L /*_RisingDragon_L*/) == 1) {
			if (changeState(state -> RisingDragon_L_Start(0.0)))
				return;
		}
		if (getVariable(0xD4675FF6L /*_Noname1*/) == 1) {
			if (changeState(state -> Noname1_Start(0.0)))
				return;
		}
		if (getVariable(0x3A2D9727L /*_Noname2*/) == 1) {
			if (changeState(state -> Noname2_Start(0.0)))
				return;
		}
		if (getVariable(0x91FF83A3L /*_Noname3*/) == 1) {
			if (changeState(state -> Noname3_Start(0.0)))
				return;
		}
		if (getVariable(0xA26EBCD9L /*_Noname4*/) == 1) {
			if (changeState(state -> Noname4_Start(0.0)))
				return;
		}
		if (getVariable(0x58EB34A5L /*_Noname5*/) == 1) {
			if (changeState(state -> Noname5_Start(0.0)))
				return;
		}
		if (getVariable(0x56D0F991L /*_Noname6*/) == 1) {
			if (changeState(state -> Noname6(0.0)))
				return;
		}
		if (getVariable(0x2FBCD6DBL /*_Noname7*/) == 1) {
			if (changeState(state -> Noname7(0.0)))
				return;
		}
		if (getVariable(0x3AFB8D58L /*_Noname8*/) == 1) {
			if (changeState(state -> Noname8(0.0)))
				return;
		}
		if (getVariable(0xA03BEB8BL /*_Noname9*/) == 1) {
			if (changeState(state -> Noname9(0.0)))
				return;
		}
		if (getVariable(0x77C984B6L /*_BlackSpirit200*/) == 1) {
			if (changeState(state -> BlackSpirit200_Start(0.0)))
				return;
		}
		setVariable(0x392FB67DL /*_Grab1*/, getVariable(0xEF6F12C1L /*AI_Grab1*/));
		setVariable(0xA3A75864L /*_Grab2*/, getVariable(0xB4A5DB2L /*AI_Grab2*/));
		setVariable(0x4A4ACF41L /*_Grab3*/, getVariable(0xE2422082L /*AI_Grab3*/));
		setVariable(0x58C7AF65L /*_Grab4*/, getVariable(0x87B8C4F2L /*AI_Grab4*/));
		setVariable(0xC33510A6L /*_Dodge*/, getVariable(0xABC11C2FL /*AI_Dodge*/));
		setVariable(0x73897017L /*_HitDashAttack*/, getVariable(0x1A5CB926L /*AI_HitDashAttack*/));
		setVariable(0xB7B4FAE8L /*_DashAttackBomb*/, getVariable(0xBDC82CD2L /*AI_DashAttackBomb*/));
		setVariable(0x76F9F3EBL /*_Extermination*/, getVariable(0x5D765436L /*AI_Extermination*/));
		setVariable(0x3C610952L /*_Rampage1*/, getVariable(0x973D0A10L /*AI_Rampage1*/));
		setVariable(0xD1A8AC2AL /*_Rampage2*/, getVariable(0x3CABB560L /*AI_Rampage2*/));
		setVariable(0x99E20B20L /*_Rampage3*/, getVariable(0x8B69F00L /*AI_Rampage3*/));
		setVariable(0x8355A200L /*_Rampage4*/, getVariable(0xAA2AF892L /*AI_Rampage4*/));
		setVariable(0xE5B712A7L /*_Rampage5*/, getVariable(0x3A4FD545L /*AI_Rampage5*/));
		setVariable(0xD4019F52L /*_Rampage6*/, getVariable(0xECE6E182L /*AI_Rampage6*/));
		setVariable(0x47FBE565L /*_RisingDragon_R*/, getVariable(0x2B64C73BL /*AI_RisingDragon_R*/));
		setVariable(0x55F29D43L /*_RisingDragon_L*/, getVariable(0xB6F7749CL /*AI_RisingDragon_L*/));
		setVariable(0xD4675FF6L /*_Noname1*/, getVariable(0x376F52ADL /*AI_Noname1*/));
		setVariable(0x3A2D9727L /*_Noname2*/, getVariable(0x2B3EE85CL /*AI_Noname2*/));
		setVariable(0x91FF83A3L /*_Noname3*/, getVariable(0x4B170F9CL /*AI_Noname3*/));
		setVariable(0xA26EBCD9L /*_Noname4*/, getVariable(0xFD27DA92L /*AI_Noname4*/));
		setVariable(0x58EB34A5L /*_Noname5*/, getVariable(0xBFE3FDFDL /*AI_Noname5*/));
		setVariable(0x4D2E6DE4L /*_EvadeBackAttack*/, getVariable(0x868CC715L /*AI_EvadeBackAttack*/));
		setVariable(0x56D0F991L /*_Noname6*/, getVariable(0x80F2E7DDL /*AI_Noname6*/));
		setVariable(0x2FBCD6DBL /*_Noname7*/, getVariable(0x8C5378BL /*AI_Noname7*/));
		setVariable(0x3AFB8D58L /*_Noname8*/, getVariable(0x465E6DF4L /*AI_Noname8*/));
		setVariable(0xA03BEB8BL /*_Noname9*/, getVariable(0xEDB8561BL /*AI_Noname9*/));
		setVariable(0x77C984B6L /*_BlackSpirit200*/, getVariable(0xC390AB8CL /*AI_BlackSpirit200*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void Grab1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1F0D46C3L /*Grab1*/);
		doAction(1427142468L /*ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Grab2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7081A528L /*Grab2*/);
		doAction(1297441637L /*ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Grab3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE24A9EB3L /*Grab3*/);
		doAction(2079495046L /*ATTACK4*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Grab4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC4D07BAAL /*Grab4*/);
		doAction(1358583901L /*ATTACK5*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Dodge(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x41762F57L /*Dodge*/);
		doAction(713554258L /*ATTACK6*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void EvadeBackAttack_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDE269755L /*EvadeBackAttack_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> EvadeBackAttack_Teleport(blendTime), 300));
	}

	protected void EvadeBackAttack_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9082E48CL /*EvadeBackAttack_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, -1000, 1, 1);
		if (getVariable(0x4D2E6DE4L /*_EvadeBackAttack*/) == 1) {
			if (changeState(state -> EvadeBackAttack(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void EvadeBackAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF9B528L /*EvadeBackAttack*/);
		doAction(1677977533L /*ATTACK7*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Noname4_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x986C596FL /*Noname4_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Noname4_Teleport(blendTime), 300));
	}

	protected void Noname4_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8BC64DCAL /*Noname4_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, -1000, 1, 1);
		if (getVariable(0xA26EBCD9L /*_Noname4*/) == 1) {
			if (changeState(state -> Noname4(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Noname4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x940A295BL /*Noname4*/);
		doAction(1722511378L /*ATTACK23*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Noname5_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x99FEFFD1L /*Noname5_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Noname5_Teleport(blendTime), 300));
	}

	protected void Noname5_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8E3C5237L /*Noname5_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, -700, 1, 1);
		if (getVariable(0x58EB34A5L /*_Noname5*/) == 1) {
			if (changeState(state -> Noname5(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Noname5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC7DB2735L /*Noname5*/);
		doAction(2363286314L /*ATTACK24*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void HitDashAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8B2D1FD9L /*HitDashAttack*/);
		doAction(2960472833L /*ATTACK8*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Extermination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB15C3331L /*Extermination*/);
		doAction(1241660493L /*ATTACK18*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Rampage1_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x78ED86AEL /*Rampage1_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Rampage1_Teleport(blendTime), 300));
	}

	protected void Rampage1_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x36F42CC9L /*Rampage1_Teleport*/);
		doTeleport(EAIMoveDestType.Random, -300, -700, 1, 1);
		if (getVariable(0x3C610952L /*_Rampage1*/) == 1) {
			if (changeState(state -> Rampage1(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Rampage1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC7C5C395L /*Rampage1*/);
		doAction(1120560969L /*ATTACK10*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Rampage2_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6609C1C5L /*Rampage2_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Rampage2_Teleport(blendTime), 300));
	}

	protected void Rampage2_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x58D9CF81L /*Rampage2_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, 600, 1, 1);
		if (getVariable(0xD1A8AC2AL /*_Rampage2*/) == 1) {
			if (changeState(state -> Rampage2(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Rampage2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD7C09535L /*Rampage2*/);
		doAction(3165781643L /*ATTACK12*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Rampage3_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x19E75CADL /*Rampage3_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Rampage3_Teleport(blendTime), 300));
	}

	protected void Rampage3_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x87955CA9L /*Rampage3_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, -450, 1, 1);
		if (getVariable(0x99E20B20L /*_Rampage3*/) == 1) {
			if (changeState(state -> Rampage3(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Rampage3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCC8CF9A3L /*Rampage3*/);
		doAction(3962024853L /*ATTACK11*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Rampage4_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x45985815L /*Rampage4_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Rampage4_Teleport(blendTime), 300));
	}

	protected void Rampage4_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1F1C988L /*Rampage4_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, -700, 1, 1);
		if (getVariable(0x8355A200L /*_Rampage4*/) == 1) {
			if (changeState(state -> Rampage4(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Rampage4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEF3B2E3FL /*Rampage4*/);
		doAction(3776201112L /*ATTACK13*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Rampage5_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF674AAC3L /*Rampage5_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Rampage5_Teleport(blendTime), 300));
	}

	protected void Rampage5_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF67CCCE7L /*Rampage5_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, -700, 1, 1);
		if (getVariable(0xE5B712A7L /*_Rampage5*/) == 1) {
			if (changeState(state -> Rampage5(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Rampage5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEC3E2A69L /*Rampage5*/);
		doAction(975922116L /*ATTACK14*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Rampage6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4E8A7FE6L /*Rampage6*/);
		doAction(891174514L /*ATTACK15*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void RisingDragon_R_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x21373313L /*RisingDragon_R_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> RisingDragon_R_Teleport(blendTime), 300));
	}

	protected void RisingDragon_R_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7E649B0CL /*RisingDragon_R_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, -300, 1, 1);
		if (getVariable(0x47FBE565L /*_RisingDragon_R*/) == 1) {
			if (changeState(state -> RisingDragon_R(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void RisingDragon_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x26640158L /*RisingDragon_R*/);
		doAction(1708521206L /*ATTACK16*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void RisingDragon_L_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x79B6EB8DL /*RisingDragon_L_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> RisingDragon_L_Teleport(blendTime), 300));
	}

	protected void RisingDragon_L_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x15DA555EL /*RisingDragon_L_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, -300, 1, 1);
		if (getVariable(0x55F29D43L /*_RisingDragon_L*/) == 1) {
			if (changeState(state -> RisingDragon_L(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void RisingDragon_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE214D104L /*RisingDragon_L*/);
		doAction(4061497842L /*ATTACK17*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Noname1_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x27692337L /*Noname1_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Noname1_Teleport(blendTime), 300));
	}

	protected void Noname1_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6A8BCF5CL /*Noname1_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, -200, 1, 1);
		if (getVariable(0xD4675FF6L /*_Noname1*/) == 1) {
			if (changeState(state -> Noname1(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Noname1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4FE9761FL /*Noname1*/);
		doAction(2159483868L /*ATTACK19*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Noname2_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x205D8901L /*Noname2_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Noname2_Teleport(blendTime), 300));
	}

	protected void Noname2_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA9992E0BL /*Noname2_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, -400, 1, 1);
		if (getVariable(0x3A2D9727L /*_Noname2*/) == 1) {
			if (changeState(state -> Noname2(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Noname2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE5A7DB52L /*Noname2*/);
		doAction(2109705675L /*ATTACK20*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Noname3_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCD684E30L /*Noname3_Start*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Noname3_Teleport(blendTime), 300));
	}

	protected void Noname3_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBAEE9253L /*Noname3_Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, -300, 1, 1);
		if (getVariable(0x91FF83A3L /*_Noname3*/) == 1) {
			if (changeState(state -> Noname3(0.0)))
				return;
		}
		changeState(state -> Die(blendTime));
	}

	protected void Noname3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x465D1042L /*Noname3*/);
		doAction(2630157094L /*ATTACK21*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void DashAttackBomb(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x18D01A2BL /*DashAttackBomb*/);
		doAction(3142254017L /*ATTACK9*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Noname6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDF7BC073L /*Noname6*/);
		doAction(798939345L /*ATTACK25*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Noname7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4AD1AACFL /*Noname7*/);
		doAction(2991358977L /*ATTACK26*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Noname8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x37BD9D1CL /*Noname8*/);
		doAction(3913127678L /*ATTACK27*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Noname9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEDD5AB02L /*Noname9*/);
		doAction(1943279547L /*ATTACK28*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void BlackSpirit200_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8F37A8F7L /*BlackSpirit200_Start*/);
		doAction(1259766710L /*ATTACK22_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> BlackSpirit200_Start(blendTime), 1000));
	}

	protected void BlackSpirit200(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5677A52FL /*BlackSpirit200*/);
		doAction(565340321L /*ATTACK22*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 500));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnRifleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult TargetInfo(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _200GO(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x77C984B6L /*_BlackSpirit200*/) == 1) {
			if (changeState(state -> BlackSpirit200(0.2)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
