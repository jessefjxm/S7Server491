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
@IAIName("npc_creature_camaspirit")
public class Ai_npc_creature_camaspirit extends CreatureAI {
	public Ai_npc_creature_camaspirit(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x1995ACA8L /*_isMove*/, getVariable(0xAA621BBFL /*AI_Move*/));
		setVariable(0xCD2D5B7CL /*_MoveCount*/, 0);
		if (getVariable(0x1995ACA8L /*_isMove*/) == 0) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 1) {
			if (changeState(state -> Wait2(blendTime)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 2) {
			if (changeState(state -> Wait3(blendTime)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 3) {
			if (changeState(state -> Wait4(blendTime)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 4) {
			if (changeState(state -> Wait8(blendTime)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 5) {
			if (changeState(state -> Wait9(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_a(blendTime), 2000));
	}

	protected void Wait_a(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC84F53D8L /*Wait_a*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_a(blendTime), 2000));
	}

	protected void Wait3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x75E9DA83L /*Wait3*/);
		if(getCallCount() == 4) {
			if (changeState(state -> Move_Up(0.5)))
				return;
		}
		doAction(463565147L /*START_ACTION2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait3(blendTime), 2000));
	}

	protected void Wait3_a(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDEB121A8L /*Wait3_a*/);
		if(getCallCount() == 4) {
			if (changeState(state -> Move_Up(0.5)))
				return;
		}
		doAction(3018685055L /*UPDOWN_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait3_a(blendTime), 2000));
	}

	protected void Move_Up(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7BA69A15L /*Move_Up*/);
		doAction(3682754745L /*MOVE_UP*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Down(blendTime), 2000));
	}

	protected void Move_Down(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB12FA748L /*Move_Down*/);
		doAction(1147992529L /*MOVE_DOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait3_a(blendTime), 2000));
	}

	protected void Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x84794EB4L /*Wait2*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random(0.5)))
				return;
		}
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait2(blendTime), 2000));
	}

	protected void Wait2_a(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFA8761DAL /*Wait2_a*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random(0.5)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait2_a(blendTime), 2000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if(Rnd.getChance(20)) {
			if (changeState(state -> Wait2_a(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 200, 400, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Random(blendTime), 5000)));
	}

	protected void Wait4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5F62B8E4L /*Wait4*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random2(0.5)))
				return;
		}
		doAction(3230953129L /*START_ACTION200*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait4(blendTime), 2000));
	}

	protected void Wait4_a(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC130D074L /*Wait4_a*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random2(0.5)))
				return;
		}
		doAction(2537463766L /*WAIT_200*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait4_a(blendTime), 2000));
	}

	protected void Wait5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC63443BL /*Wait5*/);
		if(getCallCount() == 3) {
			if (changeState(state -> Move_Up2(0.5)))
				return;
		}
		doAction(2537463766L /*WAIT_200*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait5(blendTime), 2000));
	}

	protected void Wait6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF6F634ABL /*Wait6*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random3(0.5)))
				return;
		}
		doAction(825294070L /*WAIT_250*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait6(blendTime), 2000));
	}

	protected void Wait7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2C0D5F5DL /*Wait7*/);
		if(getCallCount() == 3) {
			if (changeState(state -> Move_Down2(0.5)))
				return;
		}
		doAction(825294070L /*WAIT_250*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait7(blendTime), 2000));
	}

	protected void Move_Random2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x78DDAB8BL /*Move_Random2*/);
		if(Rnd.getChance(20)) {
			if (changeState(state -> Wait5(blendTime)))
				return;
		}
		doAction(2333960651L /*WALK_200*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 200, 400, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Random2(blendTime), 5000)));
	}

	protected void Move_Random3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7FF1BA46L /*Move_Random3*/);
		if(Rnd.getChance(20)) {
			if (changeState(state -> Wait7(blendTime)))
				return;
		}
		doAction(1774184833L /*WALK_250*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 200, 400, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Random3(blendTime), 5000)));
	}

	protected void Move_Up2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEEB91AB4L /*Move_Up2*/);
		doAction(805028078L /*MOVE_UP2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait6(blendTime), 2000));
	}

	protected void Move_Down2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x89DE61A4L /*Move_Down2*/);
		doAction(432975229L /*MOVE_DOWN2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait4_a(blendTime), 2000));
	}

	protected void Wait8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF623F5EDL /*Wait8*/);
		doAction(1508296755L /*START_ACTION3*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Up_100(blendTime), 1000));
	}

	protected void Wait8_a(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x31052378L /*Wait8_a*/);
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Up_100(blendTime), 1000));
	}

	protected void Move_Up_100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9589FA81L /*Move_Up_100*/);
		doAction(3015749040L /*MOVE_UP_100*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Up_100250(blendTime), 1000));
	}

	protected void Move_Up_100250(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x828573CDL /*Move_Up_100250*/);
		doAction(4003567342L /*MOVE_UP_100250*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Down_250100(blendTime), 1000));
	}

	protected void Move_Down_250100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8BB08C95L /*Move_Down_250100*/);
		doAction(1329969778L /*MOVE_DOWN_250100*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Up2_100200(blendTime), 1000));
	}

	protected void Move_Up2_100200(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9AC0EAD1L /*Move_Up2_100200*/);
		doAction(2343305028L /*MOVE_UP2_100200*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Up2_200250(blendTime), 1000));
	}

	protected void Move_Up2_200250(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD74CB71BL /*Move_Up2_200250*/);
		doAction(3863540865L /*MOVE_UP2_200250*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Down2_250100(blendTime), 1000));
	}

	protected void Move_Down2_250100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDEBA9532L /*Move_Down2_250100*/);
		doAction(217047747L /*MOVE_DOWN2_250100*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Down2_100(blendTime), 1000));
	}

	protected void Move_Down2_100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA0C7E7EDL /*Move_Down2_100*/);
		doAction(2580465913L /*MOVE_DOWN2_100*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait8_a(blendTime), 1000));
	}

	protected void Wait9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF61D4FFFL /*Wait9*/);
		doAction(1508296755L /*START_ACTION3*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Up3_100(blendTime), 1000));
	}

	protected void Wait9_a(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA356BA80L /*Wait9_a*/);
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Up3_100(blendTime), 1000));
	}

	protected void Move_Up3_100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEBF630A1L /*Move_Up3_100*/);
		doAction(3015749040L /*MOVE_UP_100*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Up3_100250(blendTime), 1000));
	}

	protected void Move_Up3_100250(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA80529C7L /*Move_Up3_100250*/);
		doAction(2671271522L /*MOVE_RANDOM_100250*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait10(blendTime), 1000));
	}

	protected void Wait10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8467636FL /*Wait10*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random4(0.5)))
				return;
		}
		doAction(825294070L /*WAIT_250*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait10(blendTime), 2000));
	}

	protected void Move_Random4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9AB2AF8CL /*Move_Random4*/);
		if(Rnd.getChance(15)) {
			if (changeState(state -> Move_Down3_250100(blendTime)))
				return;
		}
		doAction(3059346469L /*WALK2_250*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 200, 500, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Random4(blendTime), 5000)));
	}

	protected void Move_Down3_250100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD80160ADL /*Move_Down3_250100*/);
		doAction(1329969778L /*MOVE_DOWN_250100*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Up4_100200(blendTime), 1000));
	}

	protected void Move_Up4_100200(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2C2CA284L /*Move_Up4_100200*/);
		doAction(2343305028L /*MOVE_UP2_100200*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Up4_200250(blendTime), 1000));
	}

	protected void Move_Up4_200250(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x711E37E4L /*Move_Up4_200250*/);
		doAction(3863540865L /*MOVE_UP2_200250*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Down4_250100(blendTime), 1000));
	}

	protected void Move_Down4_250100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x33781D5BL /*Move_Down4_250100*/);
		doAction(217047747L /*MOVE_DOWN2_250100*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Down4_100(blendTime), 1000));
	}

	protected void Move_Down4_100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x53EB1387L /*Move_Down4_100*/);
		doAction(2580465913L /*MOVE_DOWN2_100*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait9_a(blendTime), 1000));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait2(blendTime), 1000));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait2(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
