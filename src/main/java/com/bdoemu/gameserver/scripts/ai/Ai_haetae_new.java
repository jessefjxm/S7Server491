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
@IAIName("haetae_new")
public class Ai_haetae_new extends CreatureAI {
	public Ai_haetae_new(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0x2E9C3CCFL /*_Stun_Time*/, 0);
		setVariable(0xE79A8AF0L /*_isHaetaeLv*/, getVariable(0x488A5BF8L /*AI_Haetae_LV*/));
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0x925166FDL /*_FirstAttack*/, 1);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xEDA55D2EL /*_StayStartTime*/, 0);
		setVariable(0x2627E3EFL /*_StayIngTime*/, 0);
		setVariable(0x6063F122L /*_StayEndTime*/, 0);
		setVariable(0x3EF14DB4L /*_StayMode*/, 0);
		setVariable(0xA41A87B8L /*_isComeonMode*/, 0);
		setVariable(0x7398A4FL /*_isRoar*/, 0);
		setVariable(0x6867E292L /*_RoarStartTime*/, 0);
		setVariable(0x1CF683E2L /*_RoarIngTime*/, 0);
		setVariable(0x8F5FAFFBL /*_RoarEndTime*/, 0);
		setVariable(0x9326AD79L /*_SummonStartTime*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, 0);
		setVariable(0x51EDA18AL /*_SummonEndTime*/, 0);
		setVariable(0x9326AD79L /*_SummonStartTime*/, getTime());
		setVariable(0xB6B14FDCL /*_AvoidStartTime*/, 0);
		setVariable(0xF121B182L /*_AvoidIngTime*/, 0);
		setVariable(0xBFC6E999L /*_AvoidEndTime*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) < 1) {
			if (changeState(state -> Kill(blendTime)))
				return;
		}
		if (getPartyMembersCount()> 1) {
			if (changeState(state -> Kill(blendTime)))
				return;
		}
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_TimerSet_Logic(blendTime), 1000));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		clearAggro(true);
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0x925166FDL /*_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0x925166FDL /*_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.EnemyLordOrKingTent, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0x925166FDL /*_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.EnemyBarricade, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0x925166FDL /*_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Avoid_Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0x925166FDL /*_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.EnemyLordOrKingTent, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Avoid_Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0x925166FDL /*_FirstAttack*/) == 1) {
			if (findTarget(EAIFindTargetType.EnemyBarricade, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Avoid_Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1) {
			if (changeState(state -> Avoid_Chase_Owner(blendTime)))
				return;
		}
		changeState(state -> Chase_Owner(blendTime));
	}

	protected void Summon_TimerSet_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x24B3BDEL /*Summon_TimerSet_Logic*/);
		changeState(state -> Summon(blendTime));
	}

	protected void Teleport_Scratch(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x84D10C6DL /*Teleport_Scratch*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Scratch(blendTime)));
	}

	protected void Avoid_Teleport_Scratch(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF8D0560EL /*Avoid_Teleport_Scratch*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Attack_Scratch(blendTime)));
	}

	protected void Teleport_Thunder2_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x18853926L /*Teleport_Thunder2_St*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 2 && target != null && getTargetHp(target) > 0) {
				if (changeState(state -> Attack_Thunder2_St(0.3)))
					return;
			}
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) <= 1 && target != null && getTargetHp(target) > 0) {
				if (changeState(state -> Attack_Scratch(0.3)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Avoid_Teleport_Thunder2_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x43418133L /*Avoid_Teleport_Thunder2_St*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(587257120L /*AVOID_ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 2 && target != null && getTargetHp(target) > 0) {
				if (changeState(state -> Avoid_Attack_Thunder2_St(0.3)))
					return;
			}
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) <= 1 && target != null && getTargetHp(target) > 0) {
				if (changeState(state -> Avoid_Attack_Scratch(0.3)))
					return;
			}
			changeState(state -> Avoid_Battle_Wait(blendTime));
		});
	}

	protected void Teleport_Spear_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC9E93F89L /*Teleport_Spear_St*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 2) {
				if (changeState(state -> Attack_StormScratch_St(0.3)))
					return;
			}
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) <= 1) {
				if (changeState(state -> Attack_Scratch(0.3)))
					return;
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Avoid_Teleport_Spear_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2B2AB257L /*Avoid_Teleport_Spear_St*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(587257120L /*AVOID_ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 2) {
				if (changeState(state -> Avoid_Attack_StormScratch_St(0.3)))
					return;
			}
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) <= 1) {
				if (changeState(state -> Avoid_Attack_Scratch(0.3)))
					return;
			}
			changeState(state -> Avoid_Battle_Wait(blendTime));
		});
	}

	protected void Teleport_ThunderZone(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8C47727EL /*Teleport_ThunderZone*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
				if (changeState(state -> Attack_ThunderZone2_St(0.3)))
					return;
			}
			changeState(state -> Teleport_ThunderZone(blendTime));
		});
	}

	protected void Avoid_Teleport_ThunderZone(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8ECFCF07L /*Avoid_Teleport_ThunderZone*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(587257120L /*AVOID_ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
				if (changeState(state -> Avoid_Attack_ThunderZone2_St(0.3)))
					return;
			}
			changeState(state -> Avoid_Teleport_ThunderZone(blendTime));
		});
	}

	protected void Teleport_ThunderZone_Cool(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x66452272L /*Teleport_ThunderZone_Cool*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
				if (changeState(state -> Attack_ThunderZone2_St_Cool(0.3)))
					return;
			}
			changeState(state -> Teleport_ThunderZone_Cool(blendTime));
		});
	}

	protected void Avoid_Teleport_ThunderZone_Cool(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1AB6E9C2L /*Avoid_Teleport_ThunderZone_Cool*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(587257120L /*AVOID_ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
				if (changeState(state -> Avoid_Attack_ThunderZone2_St_Cool(0.3)))
					return;
			}
			changeState(state -> Avoid_Teleport_ThunderZone_Cool(blendTime));
		});
	}

	protected void Teleport_Dash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8CC6DFBL /*Teleport_Dash*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Attack_Awakening_Dash_Str(0.3)))
				return;
			changeState(state -> Teleport_Dash(blendTime));
		});
	}

	protected void Avoid_Teleport_Dash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3F50E6DAL /*Avoid_Teleport_Dash*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(587257120L /*AVOID_ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Avoid_Attack_Awakening_Dash_Str(0.3)))
				return;
			changeState(state -> Avoid_Teleport_Dash(blendTime));
		});
	}

	protected void Teleport_Bash_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5E4336ACL /*Teleport_Bash_A*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Attack_Awakening_Bash_A(0.3)))
				return;
			changeState(state -> Teleport_Bash_A(blendTime));
		});
	}

	protected void Avoid_Teleport_Bash_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9982766EL /*Avoid_Teleport_Bash_A*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(587257120L /*AVOID_ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Avoid_Attack_Awakening_Bash_A(0.3)))
				return;
			changeState(state -> Avoid_Teleport_Bash_A(blendTime));
		});
	}

	protected void Teleport_Bash_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8FA266F8L /*Teleport_Bash_B*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Attack_Awakening_Bash_B(0.3)))
				return;
			changeState(state -> Teleport_Dash(blendTime));
		});
	}

	protected void Avoid_Teleport_Bash_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDF2BA6FFL /*Avoid_Teleport_Bash_B*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(587257120L /*AVOID_ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Avoid_Attack_Awakening_Bash_B(0.3)))
				return;
			changeState(state -> Avoid_Teleport_Dash(blendTime));
		});
	}

	protected void Teleport_Bash_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF95D9C87L /*Teleport_Bash_C*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Attack_Awakening_Bash_C(0.3)))
				return;
			changeState(state -> Teleport_Dash(blendTime));
		});
	}

	protected void Avoid_Teleport_Bash_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4972F1F4L /*Avoid_Teleport_Bash_C*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(587257120L /*AVOID_ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Avoid_Attack_Awakening_Bash_C(0.3)))
				return;
			changeState(state -> Avoid_Teleport_Dash(blendTime));
		});
	}

	protected void Teleport_Bash_D(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8F2C818CL /*Teleport_Bash_D*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Attack_Awakening_Bash_D(0.3)))
				return;
			changeState(state -> Teleport_Dash(blendTime));
		});
	}

	protected void Avoid_Teleport_Bash_D(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDC8A4F68L /*Avoid_Teleport_Bash_D*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(587257120L /*AVOID_ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Avoid_Attack_Awakening_Bash_D(0.3)))
				return;
			changeState(state -> Avoid_Teleport_Dash(blendTime));
		});
	}

	protected void Teleport_NANMOO(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB4913915L /*Teleport_NANMOO*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Attack_Awakening_NANMOO(0.3)))
				return;
			changeState(state -> Teleport_NANMOO(blendTime));
		});
	}

	protected void Avoid_Teleport_NANMOO(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x29A2B36BL /*Avoid_Teleport_NANMOO*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(587257120L /*AVOID_ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> {
			if (changeState(state -> Avoid_Attack_Awakening_NANMOO(0.3)))
				return;
			changeState(state -> Avoid_Teleport_NANMOO(blendTime));
		});
	}

	protected void Wait_Stand(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4C415DCCL /*Wait_Stand*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getOwnerCharacterKey() != 5) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0) {
			if (findTarget(EAIFindTargetType.EnemyLordOrKingTent, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0) {
			if (findTarget(EAIFindTargetType.EnemyBarricade, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1200) {
			if (changeState(state -> Chase_Owner_Run(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250) {
			if (changeState(state -> Chase_Owner(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Stand(blendTime), 500));
	}

	protected void Teleport_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1C9A6846L /*Teleport_Ready*/);
		clearAggro(true);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1272171962L /*ORDER_TELEPORT_ON*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_TurnToOwner(blendTime), 500));
	}

	protected void Battle_Teleport_Ready_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD25C6582L /*Battle_Teleport_Ready_3*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.OwnerPosition, -200, 0, 0, 0);
		doAction(1629735896L /*ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Teleport_TurnToOwner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD0F53445L /*Teleport_TurnToOwner*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon(blendTime), 1000));
	}

	protected void Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x34E5AE02L /*Summon*/);
		doAction(3635031213L /*SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Wait_Stand(blendTime)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Stand(blendTime), 5000)));
	}

	protected void StandUp_OwnerChase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6C5E514DL /*StandUp_OwnerChase*/);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(1411692351L /*STAND_UP*/, blendTime, onDoActionEnd -> scheduleState(state -> Chase_Owner(blendTime), 1000));
	}

	protected void Chase_Owner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEB681DFAL /*Chase_Owner*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1200) {
			if (changeState(state -> Chase_Owner_Run(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Battle_Teleport_Ready_3(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, -200, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Stand(blendTime), 100)));
	}

	protected void Chase_Owner_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB3DD8609L /*Chase_Owner_Run*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Battle_Teleport_Ready_3(0.3)))
				return;
		}
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, -200, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_Stand(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
			if (changeState(state -> FailFindPath(blendTime)))
				return;
		}
		changeState(state -> Wait_Stand(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 100, 300);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Stand(blendTime), 1500));
	}

	protected void StandUp_EnemyChase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6B8EF0E9L /*StandUp_EnemyChase*/);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		doAction(989954818L /*SIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Chase(blendTime), 1000));
	}

	protected void Move_Chase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x21564578L /*Move_Chase*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> DeSummon(0.3)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Battle_Teleport_Ready_3(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Chase_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xC79235C5L /*AI_BT_WAIT_Attack_Combo_R_Dice*/))) {
				if (changeState(state -> Attack_Normal_Combo_RL(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x2A76B060L /*AI_BT_WAIT_Attack_Combo_L_Dice*/))) {
				if (changeState(state -> Attack_Normal_Combo_LR(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xCDD31E13L /*AI_BT_WAIT_AttackNormal_L_Dice*/))) {
				if (changeState(state -> Attack_Normal_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x93D13E75L /*AI_BT_WAIT_AttackNormal_R_Dice*/))) {
				if (changeState(state -> Attack_Normal_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x5B920566L /*AI_BT_WAIT_AttackBite_Dice*/))) {
				if (changeState(state -> Attack_Bite(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(getVariable(0xB62D07FL /*AI_BT_CHASE_WALK_BloodStorm_Dice*/))) {
				if (changeState(state -> Attack_BloodStorm(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chase(blendTime), 100)));
	}

	protected void Chase_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xD4573013L /*Chase_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> DeSummon(0.3)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Battle_Teleport_Ready_3(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Chase_Run(blendTime), 100)));
	}

	protected void Run_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x81666AB5L /*Run_Stop*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> DeSummon(0.3)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Battle_Teleport_Ready_3(0.3)))
				return;
		}
		doAction(881069330L /*RUN_STOP*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void DeSummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x672D31E4L /*DeSummon*/);
		doAction(2476866129L /*UNSUMMON_HAETAE*/, blendTime, onDoActionEnd -> scheduleState(state -> Kill(blendTime), 1000));
	}

	protected void Kill(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x9BA5FB3BL /*Kill*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Kill(blendTime), 1000));
	}

	protected void Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFFAAB1AFL /*Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1664053560L /*BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 200) {
				if (changeState(state -> Move_Chase(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(80)) {
					if (changeState(state -> Attack_Normal_R(0.3)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD662C07EL /*Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 200) {
				if (changeState(state -> Move_Chase(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(80)) {
					if (changeState(state -> Attack_Normal_L(0.3)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Turn_Left_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE46D8DFCL /*Turn_Left_180*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1972822814L /*BATTLE_TURN_LEFT_180*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 200) {
				if (changeState(state -> Move_Chase(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0x5B920566L /*AI_BT_WAIT_AttackBite_Dice*/))) {
					if (changeState(state -> Attack_Bite(0.4)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0xC79235C5L /*AI_BT_WAIT_Attack_Combo_R_Dice*/))) {
					if (changeState(state -> Attack_Normal_Combo_RL(0.4)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0x2A76B060L /*AI_BT_WAIT_Attack_Combo_L_Dice*/))) {
					if (changeState(state -> Attack_Normal_Combo_LR(0.4)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Turn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3DED39BEL /*Turn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2084317812L /*BATTLE_TURN_RIGHT_180*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 200) {
				if (changeState(state -> Move_Chase(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0x5B920566L /*AI_BT_WAIT_AttackBite_Dice*/))) {
					if (changeState(state -> Attack_Bite(0.4)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0xC79235C5L /*AI_BT_WAIT_Attack_Combo_R_Dice*/))) {
					if (changeState(state -> Attack_Normal_Combo_RL(0.4)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0x2A76B060L /*AI_BT_WAIT_Attack_Combo_L_Dice*/))) {
					if (changeState(state -> Attack_Normal_Combo_LR(0.4)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Wait_Sit(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6F36606BL /*Wait_Sit*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x6466322EL /*_isSitMode*/, 1);
		setVariable(0x3EF14DB4L /*_StayMode*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(989954818L /*SIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Sit_Ing(blendTime), 1000));
	}

	protected void Sit_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8B42197BL /*Sit_Ing*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1200) {
			if (changeState(state -> Chase_Owner_Run(0.3)))
				return;
		}
		doAction(2583990611L /*SIT_ING*/, blendTime, onDoActionEnd -> changeState(state -> Sit_Ing(blendTime)));
	}

	protected void StandUp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x11D37032L /*StandUp*/);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(1411692351L /*STAND_UP*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Stand(blendTime), 1000));
	}

	protected void Wait_Stay(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x983F8B2L /*Wait_Stay*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0x3EF14DB4L /*_StayMode*/, 1);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x2627E3EFL /*_StayIngTime*/, getTime());
		setVariable(0x6063F122L /*_StayEndTime*/, getVariable(0x2627E3EFL /*_StayIngTime*/) - getVariable(0xEDA55D2EL /*_StayStartTime*/));
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 1800000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0x3EF14DB4L /*_StayMode*/) == 0 && setVariable(0x3EF14DB4L /*_StayMode*/, 0)) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0x3EF14DB4L /*_StayMode*/) == 0 && setVariable(0x3EF14DB4L /*_StayMode*/, 0)) {
			if (findTarget(EAIFindTargetType.EnemyLordOrKingTent, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0x3EF14DB4L /*_StayMode*/) == 0 && setVariable(0x3EF14DB4L /*_StayMode*/, 0)) {
			if (findTarget(EAIFindTargetType.EnemyBarricade, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Move_Chase(0.3)))
					return;
			}
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0x6063F122L /*_StayEndTime*/) > 15000 && setVariable(0x3EF14DB4L /*_StayMode*/, 0)) {
			if (changeState(state -> Chase_Owner(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1200 && setVariable(0x3EF14DB4L /*_StayMode*/, 0)) {
			if (changeState(state -> Chase_Owner_Run(0.3)))
				return;
		}
		doAction(2106046913L /*ORDER_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Stay(blendTime), 500));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(3687825854L /*DETECT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		if (getOwnerCharacterKey() != 5) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> DeSummon(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xA41A87B8L /*_isComeonMode*/) == 1) {
			if (changeState(state -> Chase_Owner(0.4)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Battle_Teleport_Ready_3(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -135 && target != null && getAngleToTarget(target) >= -179) {
			if (changeState(state -> Turn_Left_180(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 135 && target != null && getAngleToTarget(target) <= 180) {
			if (changeState(state -> Turn_Right_180(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -25) {
			if (changeState(state -> Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 25) {
			if (changeState(state -> Turn_Right(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 200) {
			if (changeState(state -> Move_Chase(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xCDD31E13L /*AI_BT_WAIT_AttackNormal_L_Dice*/))) {
				if (changeState(state -> Attack_Normal_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x93D13E75L /*AI_BT_WAIT_AttackNormal_R_Dice*/))) {
				if (changeState(state -> Attack_Normal_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xC79235C5L /*AI_BT_WAIT_Attack_Combo_R_Dice*/))) {
				if (changeState(state -> Attack_Normal_Combo_RL(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x2A76B060L /*AI_BT_WAIT_Attack_Combo_L_Dice*/))) {
				if (changeState(state -> Attack_Normal_Combo_LR(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x5B920566L /*AI_BT_WAIT_AttackBite_Dice*/))) {
				if (changeState(state -> Attack_Bite(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x107F0B25L /*AI_BT_Scratch_L_Dice*/))) {
				if (changeState(state -> Attack_Scratch_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xB031CA9DL /*AI_BT_Scratch_R_Dice*/))) {
				if (changeState(state -> Attack_Scratch_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xA9B5CF99L /*AI_BT_JumpScratch_L_Dice*/))) {
				if (changeState(state -> Attack_JumpScratch_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xDA48069FL /*AI_BT_JumpScratch_R_Dice*/))) {
				if (changeState(state -> Attack_JumpScratch_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 2) {
			if(Rnd.getChance(getVariable(0x11BAAD2FL /*AI_BT_Upper_Dice*/))) {
				if (changeState(state -> Attack_Upper(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 3) {
			if(Rnd.getChance(getVariable(0x56FF4050L /*AI_BT_Thunder_Dice*/))) {
				if (changeState(state -> Attack_Thunder1_St(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 350 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 4) {
			if(Rnd.getChance(getVariable(0xBB8526ABL /*AI_BT_StormScratch_Dice*/))) {
				if (changeState(state -> Attack_StormScratch_St(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250) {
			if(Rnd.getChance(getVariable(0x18162988L /*AI_BT_ThunderZone_Dice*/))) {
				if (changeState(state -> Attack_ThunderZone_St(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(getVariable(0x9F00F04BL /*AI_BT_BloodStorm_Dice*/))) {
				if (changeState(state -> Attack_BloodStorm(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Scratch(0.3)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), getVariable(0x2E9C3CCFL /*_Stun_Time*/)));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void UnderWater_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF7E038EL /*UnderWater_KnockDown*/);
		doAction(1872990688L /*KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> DeSummon(blendTime), 1000));
	}

	protected void Attack_Normal_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x53A7FF8EL /*Attack_Normal_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(857945710L /*ATTACK_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFE1457EBL /*Attack_Normal_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2562336179L /*ATTACK_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal_Combo_RL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD9A39491L /*Attack_Normal_Combo_RL*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1770462929L /*COMBO_ATTACK_RL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal_Combo_LR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x86D6303L /*Attack_Normal_Combo_LR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1296080089L /*COMBO_ATTACK_LR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Bite(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x46333691L /*Attack_Bite*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1620422645L /*ATTACK_BITE*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_BloodStorm(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3FEB2792L /*Attack_BloodStorm*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2382675758L /*ATTACK_THUNDER_DASH_START*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Teleport_Ready_3(blendTime)));
	}

	protected void Attack_Upper(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1554BBB1L /*Attack_Upper*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1449011620L /*ATTACK_UPPER*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_ThunderZone_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD607C220L /*Attack_ThunderZone_St*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2980659435L /*ATTACK_AGYOPOSU_START*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Thunder1_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x55E1767EL /*Attack_Thunder1_St*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1175827119L /*ATTACK_THUNDER_1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Scratch(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x81F55D03L /*Attack_Scratch*/);
							if(Rnd.getChance(40)) {
				if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 500)) {
					if (changeState(state -> Attack_Scratch_R(0.3)))
						return;
				}
			}
			if(Rnd.getChance(70)) {
				if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 500)) {
					if (changeState(state -> Attack_Scratch_L(0.3)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
	}

	protected void Attack_Scratch_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDD0F09A7L /*Attack_Scratch_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3468823981L /*ATTACK_SCRATCH_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Scratch_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3422BAE3L /*Attack_Scratch_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(4067393763L /*ATTACK_SCRATCH_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_JumpScratch_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEA06A8DEL /*Attack_JumpScratch_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2627118883L /*ATTACK_JUMP_SCRATCH_L*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_JumpScratch_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x76222BDDL /*Attack_JumpScratch_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(610300862L /*ATTACK_JUMP_SCRATCH_R*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_StormScratch_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x824E4668L /*Attack_StormScratch_St*/);
		doAction(1549566633L /*ATTACK_STORM_SCRATCH_2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_ThunderZone2_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB138756AL /*Attack_ThunderZone2_St*/);
		doAction(2566719774L /*ATTACK_AGYOPOSU_START_2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_ThunderZone2_St_Cool(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD6097655L /*Attack_ThunderZone2_St_Cool*/);
		doAction(1294371689L /*ATTACK_AGYOPOSU_START_2_COOLTIME*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Roaring(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x94E43251L /*Attack_Roaring*/);
		doAction(2481152156L /*ATTACK_ROARING_START*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Thunder2_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD61B7448L /*Attack_Thunder2_St*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(689147694L /*ATTACK_THUNDER_2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Awakening_Dash_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xA6896102L /*Attack_Awakening_Dash_Str*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Formation, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Attack_Awakening_Dash(blendTime), 500)));
	}

	protected void Attack_Awakening_Dash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFD41B4FFL /*Attack_Awakening_Dash*/);
		doAction(361862633L /*ATTACK_AWAKENING_DASH_START*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Awakening_Dash_Logic(blendTime)));
	}

	protected void Attack_Awakening_Dash_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x44693589L /*Attack_Awakening_Dash_Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 900 && getAngleToTarget(object) <= -135)) {
			if (changeState(state -> Attack_Awakening_Dash_Attack(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 900 && getAngleToTarget(object) >= 135)) {
			if (changeState(state -> Attack_Awakening_Dash_Attack(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 900)) {
			if (changeState(state -> Attack_Awakening_Dash_Attack(blendTime)))
				return;
		}
		scheduleState(state -> Battle_Wait(blendTime), 1000);
	}

	protected void Attack_Awakening_Dash_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6334DB02L /*Attack_Awakening_Dash_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(778261895L /*ATTACK_AWAKENING_DASH_SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Awakening_Bash_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8CF45491L /*Attack_Awakening_Bash_A*/);
		doAction(1786365793L /*ATTACK_AWAKENING_BASH_A*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Awakening_Bash_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5995997L /*Attack_Awakening_Bash_B*/);
		doAction(2779540817L /*ATTACK_AWAKENING_BASH_B*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Awakening_Bash_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAC388DE3L /*Attack_Awakening_Bash_C*/);
		doAction(2195894903L /*ATTACK_AWAKENING_BASH_C*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Awakening_Bash_D(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBE8514E1L /*Attack_Awakening_Bash_D*/);
		doAction(4062676166L /*ATTACK_AWAKENING_BASH_D*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Awakening_SummonHaetae_AGGRO(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x35E03ACFL /*Attack_Awakening_SummonHaetae_AGGRO*/);
		doAction(3279576573L /*ATTACK_AWAKENING_SKILL_AGGRO*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Awakening_Crush(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x371AD41AL /*Attack_Awakening_Crush*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(466735072L /*ATTACK_AWAKENING_SKILL_AGGRO_CRUSH*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void RideOffWalk_Walking(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x92DD0DBCL /*RideOffWalk_Walking*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> RideOffWalk_Wait(blendTime), 1000));
	}

	protected void RideOffWalk_Running_Faster(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4115F6C5L /*RideOffWalk_Running_Faster*/);
		doAction(3337850389L /*RIDE_RUN_STOP_FASTER*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void RideOffWalk_Running_Fastest(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4D97A9AFL /*RideOffWalk_Running_Fastest*/);
		doAction(2723083914L /*RIDEOFF_ATTACK_THUNDER_DASH*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Teleport_Ready_3(blendTime)));
	}

	protected void RideOffWalk_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x82F09D7FL /*RideOffWalk_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Chase_Owner(blendTime), 1000));
	}

	protected void RideOffRun_Wait_Faster(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4D1C479DL /*RideOffRun_Wait_Faster*/);
		doAction(3337850389L /*RIDE_RUN_STOP_FASTER*/, blendTime, onDoActionEnd -> changeState(state -> Chase_Owner(blendTime)));
	}

	protected void RideOffRun_Wait_Fastest(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x853D867L /*RideOffRun_Wait_Fastest*/);
		doAction(238949962L /*RIDE_RUN_STOP_FASTEST*/, blendTime, onDoActionEnd -> changeState(state -> Chase_Owner(blendTime)));
	}

	protected void Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x60186BFFL /*Roar*/);
		setVariable(0x6867E292L /*_RoarStartTime*/, getTime());
		setVariable(0x7398A4FL /*_isRoar*/, 1);
		doAction(3801428772L /*AVOID_ROAR_STR*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1) {
				if (changeState(state -> Avoid_Battle_Wait(blendTime)))
					return;
			}
			changeState(state -> Avoid_Wait_Stand(blendTime));
		});
	}

	protected void Avoid_Wait_Stand(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9EA2A78BL /*Avoid_Wait_Stand*/);
		setVariable(0x7398A4FL /*_isRoar*/, 1);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x1CF683E2L /*_RoarIngTime*/, getTime());
		setVariable(0x8F5FAFFBL /*_RoarEndTime*/, getVariable(0x1CF683E2L /*_RoarIngTime*/) - getVariable(0x6867E292L /*_RoarStartTime*/));
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Avoid_Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0x8F5FAFFBL /*_RoarEndTime*/) > 60000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Avoid_Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0) {
			if (findTarget(EAIFindTargetType.EnemyLordOrKingTent, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Avoid_Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0) {
			if (findTarget(EAIFindTargetType.EnemyBarricade, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Avoid_Move_Chase(0.3)))
					return;
			}
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1200) {
			if (changeState(state -> Avoid_Chase_Owner_Run(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250) {
			if (changeState(state -> Avoid_Chase_Owner(0.3)))
				return;
		}
		doAction(1102012989L /*AVOID_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Wait_Stand(blendTime), 500));
	}

	protected void Avoid_Wait_Sit(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1AE2E34FL /*Avoid_Wait_Sit*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x6466322EL /*_isSitMode*/, 1);
		setVariable(0x1CF683E2L /*_RoarIngTime*/, getTime());
		setVariable(0x8F5FAFFBL /*_RoarEndTime*/, getVariable(0x1CF683E2L /*_RoarIngTime*/) - getVariable(0x6867E292L /*_RoarStartTime*/));
		if (getVariable(0x8F5FAFFBL /*_RoarEndTime*/) > 60000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		doAction(3873133549L /*AVOID_SIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Sit_Ing(blendTime), 1000));
	}

	protected void Avoid_Sit_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x104B47L /*Avoid_Sit_Ing*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x1CF683E2L /*_RoarIngTime*/, getTime());
		setVariable(0x8F5FAFFBL /*_RoarEndTime*/, getVariable(0x1CF683E2L /*_RoarIngTime*/) - getVariable(0x6867E292L /*_RoarStartTime*/));
		if (getVariable(0x8F5FAFFBL /*_RoarEndTime*/) > 60000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Avoid_Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1200) {
			if (changeState(state -> Avoid_Chase_Owner_Run(0.3)))
				return;
		}
		doAction(2980188240L /*AVOID_SIT_ING*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Sit_Ing(blendTime)));
	}

	protected void Avoid_StandUp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9D5F582EL /*Avoid_StandUp*/);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0x1CF683E2L /*_RoarIngTime*/, getTime());
		setVariable(0x8F5FAFFBL /*_RoarEndTime*/, getVariable(0x1CF683E2L /*_RoarIngTime*/) - getVariable(0x6867E292L /*_RoarStartTime*/));
		if (getVariable(0x8F5FAFFBL /*_RoarEndTime*/) > 60000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Avoid_Battle_Wait(blendTime)))
				return;
		}
		doAction(739808366L /*AVOID_STAND_UP*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Stand(blendTime), 1000));
	}

	protected void Avoid_Wait_Stay(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE28BC175L /*Avoid_Wait_Stay*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0x3EF14DB4L /*_StayMode*/, 1);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x2627E3EFL /*_StayIngTime*/, getTime());
		setVariable(0x6063F122L /*_StayEndTime*/, getVariable(0x2627E3EFL /*_StayIngTime*/) - getVariable(0xEDA55D2EL /*_StayStartTime*/));
		setVariable(0x1CF683E2L /*_RoarIngTime*/, getTime());
		setVariable(0x8F5FAFFBL /*_RoarEndTime*/, getVariable(0x1CF683E2L /*_RoarIngTime*/) - getVariable(0x6867E292L /*_RoarStartTime*/));
		if (getVariable(0x8F5FAFFBL /*_RoarEndTime*/) > 60000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && setVariable(0x3EF14DB4L /*_StayMode*/, 0)) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Avoid_Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && setVariable(0x3EF14DB4L /*_StayMode*/, 0)) {
			if (findTarget(EAIFindTargetType.EnemyLordOrKingTent, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Avoid_Move_Chase(0.3)))
					return;
			}
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && setVariable(0x3EF14DB4L /*_StayMode*/, 0)) {
			if (findTarget(EAIFindTargetType.EnemyBarricade, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) <= 500)) {
				if (changeState(state -> Avoid_Move_Chase(0.3)))
					return;
			}
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Avoid_Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0x6063F122L /*_StayEndTime*/) > 15000 && setVariable(0x3EF14DB4L /*_StayMode*/, 0)) {
			if (changeState(state -> Avoid_Chase_Owner(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1200 && setVariable(0x3EF14DB4L /*_StayMode*/, 0)) {
			if (changeState(state -> Avoid_Chase_Owner_Run(0.3)))
				return;
		}
		doAction(3013055097L /*AVOID_ORDER_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Wait_Stay(blendTime), 500));
	}

	protected void Avoid_Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x802CDD2FL /*Avoid_Move_Random*/);
		setVariable(0x1CF683E2L /*_RoarIngTime*/, getTime());
		setVariable(0x8F5FAFFBL /*_RoarEndTime*/, getVariable(0x1CF683E2L /*_RoarIngTime*/) - getVariable(0x6867E292L /*_RoarStartTime*/));
		if (getVariable(0x8F5FAFFBL /*_RoarEndTime*/) > 60000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Avoid_Battle_Wait(blendTime)))
				return;
		}
		doAction(259510122L /*AVOID_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> Avoid_FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Avoid_Wait_Stand(blendTime), 5000)));
	}

	protected void Avoid_StandUp_OwnerChase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x801194BDL /*Avoid_StandUp_OwnerChase*/);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0x1CF683E2L /*_RoarIngTime*/, getTime());
		setVariable(0x8F5FAFFBL /*_RoarEndTime*/, getVariable(0x1CF683E2L /*_RoarIngTime*/) - getVariable(0x6867E292L /*_RoarStartTime*/));
		if (getVariable(0x8F5FAFFBL /*_RoarEndTime*/) > 60000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		doAction(739808366L /*AVOID_STAND_UP*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Chase_Owner(blendTime), 1000));
	}

	protected void Avoid_Chase_Owner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1846C859L /*Avoid_Chase_Owner*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x1CF683E2L /*_RoarIngTime*/, getTime());
		setVariable(0x8F5FAFFBL /*_RoarEndTime*/, getVariable(0x1CF683E2L /*_RoarIngTime*/) - getVariable(0x6867E292L /*_RoarStartTime*/));
		if (getVariable(0x8F5FAFFBL /*_RoarEndTime*/) > 60000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Avoid_Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 1200) {
			if (changeState(state -> Avoid_Chase_Owner_Run(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Avoid_Battle_Teleport_Ready_3(0.3)))
				return;
		}
		doAction(259510122L /*AVOID_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, -200, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> Avoid_FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Avoid_Wait_Stand(blendTime), 100)));
	}

	protected void Avoid_Chase_Owner_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE325D42FL /*Avoid_Chase_Owner_Run*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x1CF683E2L /*_RoarIngTime*/, getTime());
		setVariable(0x8F5FAFFBL /*_RoarEndTime*/, getVariable(0x1CF683E2L /*_RoarIngTime*/) - getVariable(0x6867E292L /*_RoarStartTime*/));
		if (getVariable(0x8F5FAFFBL /*_RoarEndTime*/) > 60000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Avoid_Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Avoid_Battle_Teleport_Ready_3(0.3)))
				return;
		}
		doAction(3178769201L /*AVOID_RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, -200, 0, 0, 0, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> Avoid_FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Avoid_Wait_Stand(blendTime), 1000)));
	}

	protected void Avoid_FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x828ED5FFL /*Avoid_FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
			if (changeState(state -> Avoid_FailFindPath(blendTime)))
				return;
		}
		changeState(state -> Avoid_Wait_Stand(blendTime));
	}

	protected void Avoid_FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCEEE1EB5L /*Avoid_FailFindPath*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 100, 300);
		doAction(1102012989L /*AVOID_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Wait_Stand(blendTime), 1500));
	}

	protected void Avoid_Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB084DB2DL /*Avoid_Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(530523080L /*AVOID_DETECT*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_StandUp_EnemyChase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x41C5F270L /*Avoid_StandUp_EnemyChase*/);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		doAction(3873133549L /*AVOID_SIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Move_Chase(blendTime), 1000));
	}

	protected void Avoid_Move_Chase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x69B8E342L /*Avoid_Move_Chase*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> DeSummon(0.3)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Avoid_Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Avoid_Battle_Teleport_Ready_3(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 600) {
			if (changeState(state -> Avoid_Chase_Run(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xC79235C5L /*AI_BT_WAIT_Attack_Combo_R_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Normal_Combo_RL(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x2A76B060L /*AI_BT_WAIT_Attack_Combo_L_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Normal_Combo_LR(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xCDD31E13L /*AI_BT_WAIT_AttackNormal_L_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Normal_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x93D13E75L /*AI_BT_WAIT_AttackNormal_R_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Normal_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x5B920566L /*AI_BT_WAIT_AttackBite_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Bite(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(getVariable(0xB62D07FL /*AI_BT_CHASE_WALK_BloodStorm_Dice*/))) {
				if (changeState(state -> Avoid_Attack_BloodStorm(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Avoid_Battle_Wait(0.4)))
				return;
		}
		doAction(3881987050L /*AVOID_BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> Avoid_FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Avoid_Move_Chase(blendTime), 100)));
	}

	protected void Avoid_Chase_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xDC37C958L /*Avoid_Chase_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> DeSummon(0.3)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Avoid_Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Avoid_Battle_Teleport_Ready_3(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if (changeState(state -> Avoid_Battle_Wait(0.3)))
				return;
		}
		doAction(2797493878L /*AVOID_BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> Avoid_FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Avoid_Chase_Run(blendTime), 100)));
	}

	protected void Avoid_Run_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9EF00E60L /*Avoid_Run_Stop*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> DeSummon(0.3)))
				return;
		}
		if (checkOwnerEvadeEmergency()) {
			if (changeState(state -> Avoid_Battle_Wait(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Avoid_Battle_Teleport_Ready_3(0.3)))
				return;
		}
		doAction(2304074675L /*AVOID_RUN_STOP*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Teleport_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x58324EF4L /*Avoid_Teleport_Ready*/);
		clearAggro(true);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 100, 300);
		doAction(768895660L /*AVOID_ORDER_TELEPORT_ON*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Teleport_TurnToOwner(blendTime), 500));
	}

	protected void Avoid_Battle_Teleport_Ready_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA263E1F6L /*Avoid_Battle_Teleport_Ready_3*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doTeleport(EAIMoveDestType.OwnerPosition, 150, 0, 150, 300);
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1) {
			if (changeState(state -> Avoid_Wait_Stand(0.3)))
				return;
		}
		doAction(587257120L /*AVOID_ORDER_TELEPORT_SKILL*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Teleport_Ready_3(blendTime)));
	}

	protected void Avoid_Teleport_TurnToOwner(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3BDA5995L /*Avoid_Teleport_TurnToOwner*/);
		doAction(1102012989L /*AVOID_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Summon(blendTime), 100));
	}

	protected void Avoid_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x10E36497L /*Avoid_Summon*/);
		doAction(4252101869L /*AVOID_ROAR_ING2*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Wait_Stand(blendTime)));
	}

	protected void Avoid_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xECC6CABFL /*Avoid_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1139058517L /*AVOID_BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 200) {
				if (changeState(state -> Avoid_Move_Chase(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(80)) {
					if (changeState(state -> Avoid_Attack_Normal_R(0.3)))
						return;
				}
			}
			changeState(state -> Avoid_Battle_Wait(blendTime));
		});
	}

	protected void Avoid_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8E44540CL /*Avoid_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1088033097L /*AVOID_BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 200) {
				if (changeState(state -> Avoid_Move_Chase(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(80)) {
					if (changeState(state -> Avoid_Attack_Normal_L(0.3)))
						return;
				}
			}
			changeState(state -> Avoid_Battle_Wait(blendTime));
		});
	}

	protected void Avoid_Turn_Left_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEE7CC1BBL /*Avoid_Turn_Left_180*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(428375263L /*AVOID_BATTLE_TURN_LEFT_180*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 200) {
				if (changeState(state -> Avoid_Move_Chase(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0x5B920566L /*AI_BT_WAIT_AttackBite_Dice*/))) {
					if (changeState(state -> Avoid_Attack_Bite(0.4)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0xC79235C5L /*AI_BT_WAIT_Attack_Combo_R_Dice*/))) {
					if (changeState(state -> Avoid_Attack_Normal_Combo_RL(0.4)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0x2A76B060L /*AI_BT_WAIT_Attack_Combo_L_Dice*/))) {
					if (changeState(state -> Avoid_Attack_Normal_Combo_LR(0.4)))
						return;
				}
			}
			changeState(state -> Avoid_Battle_Wait(blendTime));
		});
	}

	protected void Avoid_Turn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBA4FDD7EL /*Avoid_Turn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2919009596L /*AVOID_BATTLE_TURN_RIGHT_180*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 200) {
				if (changeState(state -> Avoid_Move_Chase(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0x5B920566L /*AI_BT_WAIT_AttackBite_Dice*/))) {
					if (changeState(state -> Avoid_Attack_Bite(0.4)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0xC79235C5L /*AI_BT_WAIT_Attack_Combo_R_Dice*/))) {
					if (changeState(state -> Avoid_Attack_Normal_Combo_RL(0.4)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 200) {
				if(Rnd.getChance(getVariable(0x2A76B060L /*AI_BT_WAIT_Attack_Combo_L_Dice*/))) {
					if (changeState(state -> Avoid_Attack_Normal_Combo_LR(0.4)))
						return;
				}
			}
			changeState(state -> Avoid_Battle_Wait(blendTime));
		});
	}

	protected void Avoid_Dash_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB6F7F483L /*Avoid_Dash_B*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Avoid_Battle_Teleport_Ready_3(blendTime)))
				return;
		}
		if(Rnd.getChance(getVariable(0x287F8E6L /*AI_BT_WALK_BACK_BloodStorm_Dice*/))) {
			if (changeState(state -> Avoid_Attack_BloodStorm(0.3)))
				return;
		}
		doAction(1415006095L /*AVOID_DASH_BACK*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x159F7D5BL /*Avoid_Battle_Wait*/);
		setVariable(0x7398A4FL /*_isRoar*/, 1);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x6466322EL /*_isSitMode*/, 0);
		setVariable(0x1CF683E2L /*_RoarIngTime*/, getTime());
		setVariable(0x8F5FAFFBL /*_RoarEndTime*/, getVariable(0x1CF683E2L /*_RoarIngTime*/) - getVariable(0x6867E292L /*_RoarStartTime*/));
		if (getVariable(0x8F5FAFFBL /*_RoarEndTime*/) > 60000) {
			if (changeState(state -> DeSummon(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> DeSummon(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xA41A87B8L /*_isComeonMode*/) == 1) {
			if (changeState(state -> Avoid_Chase_Owner(blendTime)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 2000) {
			if (changeState(state -> Avoid_Battle_Teleport_Ready_3(0.3)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -135 && target != null && getAngleToTarget(target) >= -179) {
			if (changeState(state -> Avoid_Turn_Left_180(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 135 && target != null && getAngleToTarget(target) <= 180) {
			if (changeState(state -> Avoid_Turn_Right_180(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -25) {
			if (changeState(state -> Avoid_Turn_Left(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 25) {
			if (changeState(state -> Avoid_Turn_Right(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 200) {
			if (changeState(state -> Avoid_Move_Chase(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xCDD31E13L /*AI_BT_WAIT_AttackNormal_L_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Normal_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x93D13E75L /*AI_BT_WAIT_AttackNormal_R_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Normal_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xC79235C5L /*AI_BT_WAIT_Attack_Combo_R_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Normal_Combo_RL(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x2A76B060L /*AI_BT_WAIT_Attack_Combo_L_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Normal_Combo_LR(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x5B920566L /*AI_BT_WAIT_AttackBite_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Bite(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0x107F0B25L /*AI_BT_Scratch_L_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Scratch_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xB031CA9DL /*AI_BT_Scratch_R_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Scratch_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xA9B5CF99L /*AI_BT_JumpScratch_L_Dice*/))) {
				if (changeState(state -> Avoid_Attack_JumpScratch_L(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 200) {
			if(Rnd.getChance(getVariable(0xDA48069FL /*AI_BT_JumpScratch_R_Dice*/))) {
				if (changeState(state -> Avoid_Attack_JumpScratch_R(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 2) {
			if(Rnd.getChance(getVariable(0x11BAAD2FL /*AI_BT_Upper_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Upper(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 3) {
			if(Rnd.getChance(getVariable(0x56FF4050L /*AI_BT_Thunder_Dice*/))) {
				if (changeState(state -> Avoid_Attack_Thunder1_St(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 4) {
			if(Rnd.getChance(getVariable(0xBB8526ABL /*AI_BT_StormScratch_Dice*/))) {
				if (changeState(state -> Avoid_Attack_StormScratch_St(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 250) {
			if(Rnd.getChance(getVariable(0x18162988L /*AI_BT_ThunderZone_Dice*/))) {
				if (changeState(state -> Avoid_Attack_ThunderZone_St(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(getVariable(0x9F00F04BL /*AI_BT_BloodStorm_Dice*/))) {
				if (changeState(state -> Avoid_Attack_BloodStorm(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 500) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Avoid_Attack_Scratch(0.3)))
					return;
			}
		}
		doAction(3804072644L /*AVOID_BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Battle_Wait(blendTime), 500));
	}

	protected void Avoid_Attack_Normal_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCDFF77FBL /*Avoid_Attack_Normal_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1566877249L /*AVOID_ATTACK_L*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Normal_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x943F466CL /*Avoid_Attack_Normal_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1361119289L /*AVOID_ATTACK_R*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Normal_Combo_RL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6D34D024L /*Avoid_Attack_Normal_Combo_RL*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2114315263L /*AVOID_COMBO_ATTACK_RL*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Normal_Combo_LR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6F8FCCA2L /*Avoid_Attack_Normal_Combo_LR*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1438326262L /*AVOID_COMBO_ATTACK_LR*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Bite(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x54B52D96L /*Avoid_Attack_Bite*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1017134624L /*AVOID_ATTACK_BITE*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_BloodStorm(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4709E95DL /*Avoid_Attack_BloodStorm*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(25678892L /*AVOID_ATTACK_THUNDER_DASH_START*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Teleport_Ready_3(blendTime)));
	}

	protected void Avoid_Attack_Upper(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE8077C06L /*Avoid_Attack_Upper*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3386810769L /*AVOID_ATTACK_UPPER*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_ThunderZone_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2E3980C6L /*Avoid_Attack_ThunderZone_St*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(858275585L /*AVOID_ATTACK_AGYOPOSU_START*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Thunder1_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFBC0A7EL /*Avoid_Attack_Thunder1_St*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(837816384L /*AVOID_ATTACK_THUNDER_1*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Scratch(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF4D8E470L /*Avoid_Attack_Scratch*/);
							if (target != null && getDistanceToTarget(target) <= 500) {
				if(Rnd.getChance(40)) {
					if (changeState(state -> Avoid_Attack_Scratch_R(0.3)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) <= 500) {
				if(Rnd.getChance(70)) {
					if (changeState(state -> Avoid_Attack_Scratch_L(0.3)))
						return;
				}
			}
			changeState(state -> Avoid_Attack_Normal_R(blendTime));
	}

	protected void Avoid_Attack_Scratch_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9730B88FL /*Avoid_Attack_Scratch_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(154065946L /*AVOID_ATTACK_SCRATCH_R*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Scratch_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x443C177DL /*Avoid_Attack_Scratch_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2399618078L /*AVOID_ATTACK_SCRATCH_L*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_JumpScratch_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x99F0C175L /*Avoid_Attack_JumpScratch_L*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3142985925L /*AVOID_ATTACK_JUMP_SCRATCH_L*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_JumpScratch_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x53FBF173L /*Avoid_Attack_JumpScratch_R*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(268347071L /*AVOID_ATTACK_JUMP_SCRATCH_R*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_StormScratch_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBFE9083CL /*Avoid_Attack_StormScratch_St*/);
		doAction(1999565852L /*AVOID_ATTACK_STORM_SCRATCH_2*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_ThunderZone2_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5D2AE92DL /*Avoid_Attack_ThunderZone2_St*/);
		doAction(3045164788L /*AVOID_ATTACK_AGYOPOSU_START_2*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_ThunderZone2_St_Cool(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD886A94BL /*Avoid_Attack_ThunderZone2_St_Cool*/);
		doAction(3653886190L /*AVOID_ATTACK_AGYOPOSU_START_2_COOLTIME*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Roaring(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC7A533B2L /*Avoid_Attack_Roaring*/);
		doAction(2468170746L /*AVOID_ATTACK_ROARING_START*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Thunder2_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA46E7DEAL /*Avoid_Attack_Thunder2_St*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1194123285L /*AVOID_ATTACK_THUNDER_2*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Awakening_Dash_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xFEE1EF1L /*Avoid_Attack_Awakening_Dash_Str*/);
		doAction(3804072644L /*AVOID_BATTLE_WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Formation, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Avoid_Attack_Awakening_Dash(blendTime), 500)));
	}

	protected void Avoid_Attack_Awakening_Dash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE27E1915L /*Avoid_Attack_Awakening_Dash*/);
		doAction(3251305681L /*AVOID_ATTACK_AWAKENING_DASH_START*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Attack_Awakening_Dash_Logic(blendTime)));
	}

	protected void Avoid_Attack_Awakening_Dash_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3DD74049L /*Avoid_Attack_Awakening_Dash_Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 900 && getAngleToTarget(object) <= -135)) {
			if (changeState(state -> Avoid_Attack_Awakening_Dash_Attack(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 900 && getAngleToTarget(object) >= 135)) {
			if (changeState(state -> Avoid_Attack_Awakening_Dash_Attack(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 900)) {
			if (changeState(state -> Avoid_Attack_Awakening_Dash_Attack(blendTime)))
				return;
		}
		scheduleState(state -> Avoid_Battle_Wait(blendTime), 1000);
	}

	protected void Avoid_Attack_Awakening_Dash_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x20947672L /*Avoid_Attack_Awakening_Dash_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1436352176L /*AVOID_ATTACK_AWAKENING_DASH_SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Awakening_Bash_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x888F5466L /*Avoid_Attack_Awakening_Bash_A*/);
		doAction(3058263578L /*AVOID_ATTACK_AWAKENING_BASH_A*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Awakening_Bash_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x64B5897FL /*Avoid_Attack_Awakening_Bash_B*/);
		doAction(1952359283L /*AVOID_ATTACK_AWAKENING_BASH_B*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Awakening_Bash_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFA070E2DL /*Avoid_Attack_Awakening_Bash_C*/);
		doAction(3255705017L /*AVOID_ATTACK_AWAKENING_BASH_C*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Awakening_Bash_D(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x98E691F0L /*Avoid_Attack_Awakening_Bash_D*/);
		doAction(3051543846L /*AVOID_ATTACK_AWAKENING_BASH_D*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Attack_Awakening_NANMOO(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x52A225AAL /*Attack_Awakening_NANMOO*/);
		doAction(4164719958L /*ATTACK_AWAKENING_NANMOO_A*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Awakening_NANMOO(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFE8AE656L /*Avoid_Attack_Awakening_NANMOO*/);
		doAction(3650337436L /*AVOID_ATTACK_AWAKENING_NANMOO_A*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Attack_Awakening_Skill_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x13375552L /*Attack_Awakening_Skill_Str*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(1112659165L /*ATTACK_AWAKENING_SPECIAL*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Awakening_Skill_Ing(blendTime), 500));
	}

	protected void Attack_Awakening_Skill_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xD728A147L /*Attack_Awakening_Skill_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 500) {
			if (changeState(state -> Attack_Awakening_Skill_End(0.2)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 3000) {
			if (changeState(state -> Battle_Teleport_Ready_3(0.3)))
				return;
		}
		doAction(2646041357L /*ATTACK_AWAKENING_SPECIAL_A*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Attack_Awakening_Skill_Ing(blendTime), 500)));
	}

	protected void Attack_Awakening_Skill_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5ECC19AAL /*Attack_Awakening_Skill_End*/);
		doAction(1278813934L /*ATTACK_AWAKENING_SPECIAL_B*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Awakening_Skill_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2D62879EL /*Avoid_Attack_Awakening_Skill_Str*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(575654107L /*AVOID_ATTACK_AWAKENING_SPECIAL*/, blendTime, onDoActionEnd -> scheduleState(state -> Avoid_Attack_Awakening_Skill_Ing(blendTime), 500));
	}

	protected void Avoid_Attack_Awakening_Skill_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x2BC3046AL /*Avoid_Attack_Awakening_Skill_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 500) {
			if (changeState(state -> Avoid_Attack_Awakening_Skill_End(0.2)))
				return;
		}
		if (!checkOwnerEvadeEmergency() && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 3000) {
			if (changeState(state -> Battle_Teleport_Ready_3(0.3)))
				return;
		}
		doAction(1089507906L /*AVOID_ATTACK_AWAKENING_SPECIAL_A*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Avoid_Attack_Awakening_Skill_Ing(blendTime), 500)));
	}

	protected void Avoid_Attack_Awakening_Skill_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB572EBB8L /*Avoid_Attack_Awakening_Skill_End*/);
		doAction(516483080L /*AVOID_ATTACK_AWAKENING_SPECIAL_B*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Awakening_SummonHaetae_AGGRO(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2BA8C94FL /*Avoid_Attack_Awakening_SummonHaetae_AGGRO*/);
		doAction(3994803742L /*AVOID_ATTACK_AWAKENING_SKILL_AGGRO*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	protected void Avoid_Attack_Awakening_Crush(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x52BC6190L /*Avoid_Attack_Awakening_Crush*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3485129187L /*AVOID_ATTACK_AWAKENING_SKILL_AGGRO_CRUSH*/, blendTime, onDoActionEnd -> changeState(state -> Avoid_Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0x6466322EL /*_isSitMode*/) == 0 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0 && getVariable(0x3EF14DB4L /*_StayMode*/) == 0 && !isRiderExist()) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0x6466322EL /*_isSitMode*/) == 1 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0 && getVariable(0x3EF14DB4L /*_StayMode*/) == 0 && !isRiderExist()) {
			if (changeState(state -> StandUp_EnemyChase(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0x6466322EL /*_isSitMode*/) == 0 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0 && getVariable(0x3EF14DB4L /*_StayMode*/) == 0 && !isRiderExist()) {
			if (changeState(state -> Avoid_Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0x6466322EL /*_isSitMode*/) == 1 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0 && getVariable(0x3EF14DB4L /*_StayMode*/) == 0 && !isRiderExist()) {
			if (changeState(state -> Avoid_StandUp_EnemyChase(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeTeamDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0x6466322EL /*_isSitMode*/) == 0 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0 && getVariable(0x3EF14DB4L /*_StayMode*/) == 0 && !isRiderExist()) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0x6466322EL /*_isSitMode*/) == 1 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0 && getVariable(0x3EF14DB4L /*_StayMode*/) == 0 && !isRiderExist()) {
			if (changeState(state -> StandUp_EnemyChase(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0x6466322EL /*_isSitMode*/) == 0 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0 && getVariable(0x3EF14DB4L /*_StayMode*/) == 0 && !isRiderExist()) {
			if (changeState(state -> Avoid_Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0x6466322EL /*_isSitMode*/) == 1 && getVariable(0xA41A87B8L /*_isComeonMode*/) == 0 && getVariable(0x3EF14DB4L /*_StayMode*/) == 0 && !isRiderExist()) {
			if (changeState(state -> Avoid_StandUp_EnemyChase(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x925166FDL /*_FirstAttack*/, 1);
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0xFA9DA674L /*_isBattleMode*/) != 1 && !isRiderExist()) {
			if (changeState(state -> Wait_Sit(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0x6466322EL /*_isSitMode*/) == 1 && !isRiderExist()) {
			if (changeState(state -> StandUp(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0xFA9DA674L /*_isBattleMode*/) != 1 && !isRiderExist()) {
			if (changeState(state -> Avoid_Wait_Sit(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0x6466322EL /*_isSitMode*/) == 1 && !isRiderExist()) {
			if (changeState(state -> Avoid_StandUp(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_STAY(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x925166FDL /*_FirstAttack*/, 1);
		setVariable(0xEDA55D2EL /*_StayStartTime*/, getTime());
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0xFA9DA674L /*_isBattleMode*/) != 1 && !isRiderExist()) {
			if (changeState(state -> Wait_Stay(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0xFA9DA674L /*_isBattleMode*/) != 1 && !isRiderExist()) {
			if (changeState(state -> Avoid_Wait_Stay(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		setVariable(0xA41A87B8L /*_isComeonMode*/, 1);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x3EF14DB4L /*_StayMode*/, 0);
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0x6466322EL /*_isSitMode*/) == 1 && !isRiderExist()) {
			if (changeState(state -> StandUp_OwnerChase(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0x6466322EL /*_isSitMode*/) == 0 && !isRiderExist()) {
			if (changeState(state -> Chase_Owner(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0x6466322EL /*_isSitMode*/) == 1 && !isRiderExist()) {
			if (changeState(state -> Avoid_StandUp_OwnerChase(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0x6466322EL /*_isSitMode*/) == 0 && !isRiderExist()) {
			if (changeState(state -> Avoid_Chase_Owner(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		setVariable(0x3EF14DB4L /*_StayMode*/, 0);
		setVariable(0x925166FDL /*_FirstAttack*/, 1);
		setVariable(0xA41A87B8L /*_isComeonMode*/, 0);
		if (getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getState() != 0xD25C6582L /*Battle_Teleport_Ready_3*/ && getVariable(0x6466322EL /*_isSitMode*/) == 0 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && !isRiderExist()) {
			if (changeState(state -> Attack_Awakening_Skill_Str(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getState() != 0xD25C6582L /*Battle_Teleport_Ready_3*/ && getVariable(0x6466322EL /*_isSitMode*/) == 0 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && !isRiderExist()) {
			if (changeState(state -> Avoid_Attack_Awakening_Skill_Str(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_FrontSractch(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 350 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x4C415DCCL /*Wait_Stand*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0xB3DD8609L /*Chase_Owner_Run*/ || getState() == 0xEB681DFAL /*Chase_Owner*/) && !isRiderExist()) {
			if (changeState(state -> Teleport_Scratch(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 350 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x9EA2A78BL /*Avoid_Wait_Stand*/ || getState() == 0x159F7D5BL /*Avoid_Battle_Wait*/ || getState() == 0x802CDD2FL /*Avoid_Move_Random*/ || getState() == 0xE325D42FL /*Avoid_Chase_Owner_Run*/ || getState() == 0x1846C859L /*Avoid_Chase_Owner*/) && !isRiderExist()) {
			if (changeState(state -> Avoid_Teleport_Scratch(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x4C415DCCL /*Wait_Stand*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0xB3DD8609L /*Chase_Owner_Run*/ || getState() == 0xEB681DFAL /*Chase_Owner*/) && !isRiderExist()) {
			if (changeState(state -> Attack_Scratch(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x9EA2A78BL /*Avoid_Wait_Stand*/ || getState() == 0x159F7D5BL /*Avoid_Battle_Wait*/ || getState() == 0x802CDD2FL /*Avoid_Move_Random*/ || getState() == 0xE325D42FL /*Avoid_Chase_Owner_Run*/ || getState() == 0x1846C859L /*Avoid_Chase_Owner*/) && !isRiderExist()) {
			if (changeState(state -> Avoid_Attack_Scratch(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_ThunderStorm(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x4C415DCCL /*Wait_Stand*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0xB3DD8609L /*Chase_Owner_Run*/ || getState() == 0xEB681DFAL /*Chase_Owner*/) && !isRiderExist()) {
			if (changeState(state -> Teleport_Thunder2_St(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 350 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x9EA2A78BL /*Avoid_Wait_Stand*/ || getState() == 0x159F7D5BL /*Avoid_Battle_Wait*/ || getState() == 0x802CDD2FL /*Avoid_Move_Random*/ || getState() == 0xE325D42FL /*Avoid_Chase_Owner_Run*/ || getState() == 0x1846C859L /*Avoid_Chase_Owner*/) && !isRiderExist()) {
			if (changeState(state -> Avoid_Teleport_Thunder2_St(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 250 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x4C415DCCL /*Wait_Stand*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0xB3DD8609L /*Chase_Owner_Run*/ || getState() == 0xEB681DFAL /*Chase_Owner*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Attack_Thunder2_St(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 250 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) <= 1 && !isRiderExist() && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x4C415DCCL /*Wait_Stand*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0xB3DD8609L /*Chase_Owner_Run*/ || getState() == 0xEB681DFAL /*Chase_Owner*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Attack_Scratch(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 350 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x9EA2A78BL /*Avoid_Wait_Stand*/ || getState() == 0x159F7D5BL /*Avoid_Battle_Wait*/ || getState() == 0x802CDD2FL /*Avoid_Move_Random*/ || getState() == 0xE325D42FL /*Avoid_Chase_Owner_Run*/ || getState() == 0x1846C859L /*Avoid_Chase_Owner*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Avoid_Attack_Thunder2_St(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 350 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) <= 1 && !isRiderExist() && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x9EA2A78BL /*Avoid_Wait_Stand*/ || getState() == 0x159F7D5BL /*Avoid_Battle_Wait*/ || getState() == 0x802CDD2FL /*Avoid_Move_Random*/ || getState() == 0xE325D42FL /*Avoid_Chase_Owner_Run*/ || getState() == 0x1846C859L /*Avoid_Chase_Owner*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Avoid_Attack_Scratch(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_HaetaeSpear(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x4C415DCCL /*Wait_Stand*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0xB3DD8609L /*Chase_Owner_Run*/ || getState() == 0xEB681DFAL /*Chase_Owner*/) && !isRiderExist()) {
			if (changeState(state -> Teleport_Spear_St(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) >= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x9EA2A78BL /*Avoid_Wait_Stand*/ || getState() == 0x159F7D5BL /*Avoid_Battle_Wait*/ || getState() == 0x802CDD2FL /*Avoid_Move_Random*/ || getState() == 0xE325D42FL /*Avoid_Chase_Owner_Run*/ || getState() == 0x1846C859L /*Avoid_Chase_Owner*/) && !isRiderExist()) {
			if (changeState(state -> Avoid_Teleport_Spear_St(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 250 && !isRiderExist() && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x4C415DCCL /*Wait_Stand*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0xB3DD8609L /*Chase_Owner_Run*/ || getState() == 0xEB681DFAL /*Chase_Owner*/)) {
			if (changeState(state -> Attack_StormScratch_St(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 250 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) <= 1 && !isRiderExist() && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x4C415DCCL /*Wait_Stand*/ || getState() == 0x71F28994L /*Battle_Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0xB3DD8609L /*Chase_Owner_Run*/ || getState() == 0xEB681DFAL /*Chase_Owner*/)) {
			if (changeState(state -> Attack_Scratch(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 250 && !isRiderExist() && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x9EA2A78BL /*Avoid_Wait_Stand*/ || getState() == 0x159F7D5BL /*Avoid_Battle_Wait*/ || getState() == 0x802CDD2FL /*Avoid_Move_Random*/ || getState() == 0xE325D42FL /*Avoid_Chase_Owner_Run*/ || getState() == 0x1846C859L /*Avoid_Chase_Owner*/)) {
			if (changeState(state -> Avoid_Attack_StormScratch_St(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 250 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) <= 1 && !isRiderExist() && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x9EA2A78BL /*Avoid_Wait_Stand*/ || getState() == 0x159F7D5BL /*Avoid_Battle_Wait*/ || getState() == 0x802CDD2FL /*Avoid_Move_Random*/ || getState() == 0xE325D42FL /*Avoid_Chase_Owner_Run*/ || getState() == 0x1846C859L /*Avoid_Chase_Owner*/)) {
			if (changeState(state -> Avoid_Attack_Scratch(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_ThunderZone(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
			if (changeState(state -> Teleport_ThunderZone(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
			if (changeState(state -> Avoid_Teleport_ThunderZone(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 10 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
			if (changeState(state -> Attack_ThunderZone2_St(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 10 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
			if (changeState(state -> Avoid_Attack_ThunderZone2_St(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_ThunderZone_Cool(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
			if (changeState(state -> Teleport_ThunderZone_Cool(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 10 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
			if (changeState(state -> Avoid_Teleport_ThunderZone_Cool(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 10 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
			if (changeState(state -> Attack_ThunderZone2_St_Cool(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 10 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 6) {
			if (changeState(state -> Avoid_Attack_ThunderZone2_St_Cool(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Roaring(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 8 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Attack_Roaring(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Attack_Bite(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Attack_Bite(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xE79A8AF0L /*_isHaetaeLv*/) > 8 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Attack_Roaring(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Awakening_SummonHaetae(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x3EF14DB4L /*_StayMode*/, 0);
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0) {
			if (changeState(state -> Roar(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Awakening_Dash(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Teleport_Dash(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Teleport_Dash(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Attack_Awakening_Dash_Str(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Attack_Awakening_Dash_Str(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Awakening_Bash_A(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Teleport_Bash_A(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Teleport_Bash_A(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Attack_Awakening_Bash_A(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Attack_Awakening_Bash_A(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Awakening_Bash_B(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Teleport_Bash_B(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Teleport_Bash_B(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Attack_Awakening_Bash_B(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Attack_Awakening_Bash_B(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Awakening_Bash_C(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Teleport_Bash_C(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Teleport_Bash_C(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Attack_Awakening_Bash_C(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Attack_Awakening_Bash_C(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Awakening_Bash_D(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Teleport_Bash_D(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Teleport_Bash_D(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Attack_Awakening_Bash_D(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Attack_Awakening_Bash_D(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Awakening_Crush_A(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Teleport_NANMOO(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Teleport_NANMOO(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Attack_Awakening_NANMOO(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 250 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Attack_Awakening_NANMOO(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Awakening_SummonHaetae_AGGRO(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Attack_Awakening_SummonHaetae_AGGRO(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x3EF14DB4L /*_StayMode*/) != 1 && getVariable(0x6466322EL /*_isSitMode*/) != 1 && getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Avoid_Attack_Awakening_SummonHaetae_AGGRO(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Awakening_Crush(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x35E03ACFL /*Attack_Awakening_SummonHaetae_AGGRO*/) {
			if (changeState(state -> Attack_Awakening_Crush(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x2BA8C94FL /*Avoid_Attack_Awakening_SummonHaetae_AGGRO*/) {
			if (changeState(state -> Avoid_Attack_Awakening_Crush(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_BattleWait(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getVariable(0x7398A4FL /*_isRoar*/) == 0 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x4C415DCCL /*Wait_Stand*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0xB3DD8609L /*Chase_Owner_Run*/ || getState() == 0xEB681DFAL /*Chase_Owner*/)) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x7398A4FL /*_isRoar*/) == 1 && getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/ && (getState() == 0x4C415DCCL /*Wait_Stand*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0xB3DD8609L /*Chase_Owner_Run*/ || getState() == 0xEB681DFAL /*Chase_Owner*/)) {
			if (changeState(state -> Avoid_Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Teleport(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if (changeState(state -> Teleport_Ready(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnOwnerDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> DeSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_GoodDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (changeState(state -> DeSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handleTimeout(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (changeState(state -> DeSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnRifleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> DeSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRideOff_Wait(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> RideOffWalk_Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRideOffWalk(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> RideOffWalk_Walking(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRideOffRun_Faster(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> RideOffWalk_Running_Faster(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRideOffRun_Fastest(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> RideOffWalk_Running_Fastest(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleMoveInWater(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> UnderWater_KnockDown(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Damage_KnockBack(0.1)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Damage_KnockDown(0.1)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStuned(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x2E9C3CCFL /*_Stun_Time*/, eventData[0]);
		if (getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if(Rnd.getChance(12)) {
				if (changeState(state -> Damage_Stun(0.1)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if(Rnd.getChance(12)) {
				if (changeState(state -> Damage_Bound(0.1)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if(Rnd.getChance(12)) {
				if (changeState(state -> Damage_Capture(0.1)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x672D31E4L /*DeSummon*/ && getState() != 0x9BA5FB3BL /*Kill*/) {
			if(Rnd.getChance(12)) {
				if (changeState(state -> Damage_Bound(0.1)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Handle_RideOn(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> TerminateState(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRideEnd(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x3EF14DB4L /*_StayMode*/, 0);
		setVariable(0xEDA55D2EL /*_StayStartTime*/, 0);
		setVariable(0x2627E3EFL /*_StayIngTime*/, 0);
		setVariable(0x6063F122L /*_StayEndTime*/, 0);
		return EAiHandlerResult.BYPASS;
	}
}
