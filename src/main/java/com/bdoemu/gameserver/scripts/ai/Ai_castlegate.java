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
@IAIName("castlegate")
public class Ai_castlegate extends CreatureAI {
	public Ai_castlegate(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		setVariable(0xDF434D85L /*_ISOpening*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Stance(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A34695AL /*Stance*/);
		setVariable(0xDF434D85L /*_ISOpening*/, 0);
		if (getVariable(0xF06310B7L /*_stance*/) == 0) {
			if (changeState(state -> Wait_Close(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 1) {
			if (changeState(state -> Set_50(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 2) {
			if (changeState(state -> Set_100(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 3) {
			if (changeState(state -> Set_150(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 4) {
			if (changeState(state -> Set_200(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 5) {
			if (changeState(state -> Set_250(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 6) {
			if (changeState(state -> Set_300(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 7) {
			if (changeState(state -> Set_350(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 8) {
			if (changeState(state -> Set_400(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 9) {
			if (changeState(state -> Set_450(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 10) {
			if (changeState(state -> Set_500(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 11) {
			if (changeState(state -> Set_550(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) >= 12) {
			if (changeState(state -> Wait_Open(blendTime)))
				return;
		}
		changeState(state -> Stance(blendTime));
	}

	protected void Open_Lv0(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB3883BL /*Open_Lv0*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(1501413640L /*OPEN_0to50*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4F10847CL /*Open_Lv1*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(3031733724L /*OPEN_51to100*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6B727B1DL /*Open_Lv2*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(1805256532L /*OPEN_101to150*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE5A54D88L /*Open_Lv3*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(2017020096L /*OPEN_151to200*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2BEF4A79L /*Open_Lv4*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(3210076640L /*OPEN_201to250*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6AF42C4AL /*Open_Lv5*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(268716030L /*OPEN_251to300*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3D142588L /*Open_Lv6*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(403142083L /*OPEN_301to350*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDA96019AL /*Open_Lv7*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(2898903557L /*OPEN_351to400*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFB0CDD5AL /*Open_Lv8*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(28740086L /*OPEN_401to450*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD98A5E97L /*Open_Lv9*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(2649324539L /*OPEN_451to500*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD740802BL /*Open_Lv10*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(464175305L /*OPEN_501to550*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5B1CD474L /*Open_End*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(2966115324L /*OPEN_551to600*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv0(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE50D4519L /*Close_Lv0*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(1214255103L /*CLOSE_0to50*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8F4780B4L /*Close_Lv1*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(3572762353L /*CLOSE_51to100*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x666198DBL /*Close_Lv2*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(3746515268L /*CLOSE_101to150*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5BB5981L /*Close_Lv3*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(966327333L /*CLOSE_151to200*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x74A62F5L /*Close_Lv4*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(444541541L /*CLOSE_201to250*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD05D7AABL /*Close_Lv5*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(1235530788L /*CLOSE_251to300*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1D7F29BAL /*Close_Lv6*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(3312391174L /*CLOSE_301to350*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8BD3E6CL /*Close_Lv7*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(2858901617L /*CLOSE_351to400*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD99C9B55L /*Close_Lv8*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(3437796332L /*CLOSE_401to450*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9384928CL /*Close_Lv9*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(2501560687L /*CLOSE_451to500*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7E920AD6L /*Close_Lv10*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(328916568L /*CLOSE_501to550*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB7751B73L /*Close_End*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(1320363683L /*CLOSE_551to600*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Wait_Close(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBA6E4261L /*Wait_Close*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Close(blendTime), 10000));
	}

	protected void Set_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x278E8F9AL /*Set_50*/);
		doAction(556815975L /*SET_50f*/, blendTime, onDoActionEnd -> scheduleState(state -> Set_50(blendTime), 10000));
	}

	protected void Set_100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4DF7171CL /*Set_100*/);
		doAction(1461885018L /*SET_100f*/, blendTime, onDoActionEnd -> scheduleState(state -> Set_100(blendTime), 10000));
	}

	protected void Set_150(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4E504403L /*Set_150*/);
		doAction(171655668L /*SET_150f*/, blendTime, onDoActionEnd -> scheduleState(state -> Set_150(blendTime), 10000));
	}

	protected void Set_200(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x434A048FL /*Set_200*/);
		doAction(4116952922L /*SET_200f*/, blendTime, onDoActionEnd -> scheduleState(state -> Set_200(blendTime), 10000));
	}

	protected void Set_250(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x15F03EE5L /*Set_250*/);
		doAction(2610613715L /*SET_250f*/, blendTime, onDoActionEnd -> scheduleState(state -> Set_250(blendTime), 10000));
	}

	protected void Set_300(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFB5FB6B9L /*Set_300*/);
		doAction(3322899830L /*SET_300f*/, blendTime, onDoActionEnd -> scheduleState(state -> Set_300(blendTime), 10000));
	}

	protected void Set_350(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB89057BAL /*Set_350*/);
		doAction(290337572L /*SET_350f*/, blendTime, onDoActionEnd -> scheduleState(state -> Set_350(blendTime), 10000));
	}

	protected void Set_400(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x45FEB23DL /*Set_400*/);
		doAction(4138933984L /*SET_400f*/, blendTime, onDoActionEnd -> scheduleState(state -> Set_400(blendTime), 10000));
	}

	protected void Set_450(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1EA79EC8L /*Set_450*/);
		doAction(848410756L /*SET_450f*/, blendTime, onDoActionEnd -> scheduleState(state -> Set_450(blendTime), 10000));
	}

	protected void Set_500(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA9A30EBL /*Set_500*/);
		doAction(3538251205L /*SET_500f*/, blendTime, onDoActionEnd -> scheduleState(state -> Set_500(blendTime), 10000));
	}

	protected void Set_550(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB625457L /*Set_550*/);
		doAction(2919830726L /*SET_550f*/, blendTime, onDoActionEnd -> scheduleState(state -> Set_550(blendTime), 10000));
	}

	protected void Wait_Open(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB1B22E56L /*Wait_Open*/);
		doAction(3081895922L /*WAIT_OPEN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Open(blendTime), 10000));
	}

	protected void OneStep_Open(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC2A6B769L /*OneStep_Open*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(275159270L /*OPEN*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void OneStep_Open_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x418F1EE4L /*OneStep_Open_Lv1*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(3031733724L /*OPEN_51to100*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv2(blendTime), 1000));
	}

	protected void OneStep_Open_Lv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6B1D3E59L /*OneStep_Open_Lv2*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(1805256532L /*OPEN_101to150*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv3(blendTime), 1000));
	}

	protected void OneStep_Open_Lv3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF5C572BFL /*OneStep_Open_Lv3*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(2017020096L /*OPEN_151to200*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv4(blendTime), 1000));
	}

	protected void OneStep_Open_Lv4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF67AD0BL /*OneStep_Open_Lv4*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(3210076640L /*OPEN_201to250*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv5(blendTime), 1000));
	}

	protected void OneStep_Open_Lv5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x21D1F453L /*OneStep_Open_Lv5*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(268716030L /*OPEN_251to300*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv6(blendTime), 1000));
	}

	protected void OneStep_Open_Lv6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x14CAC16CL /*OneStep_Open_Lv6*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(403142083L /*OPEN_301to350*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv7(blendTime), 1000));
	}

	protected void OneStep_Open_Lv7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x89EE228FL /*OneStep_Open_Lv7*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(2898903557L /*OPEN_351to400*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv8(blendTime), 1000));
	}

	protected void OneStep_Open_Lv8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x719B8B55L /*OneStep_Open_Lv8*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(28740086L /*OPEN_401to450*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv9(blendTime), 1000));
	}

	protected void OneStep_Open_Lv9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1DFAB1FBL /*OneStep_Open_Lv9*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(2649324539L /*OPEN_451to500*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv10(blendTime), 1000));
	}

	protected void OneStep_Open_Lv10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x82454EA3L /*OneStep_Open_Lv10*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(464175305L /*OPEN_501to550*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_End(blendTime), 1000));
	}

	protected void OneStep_Open_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8B9354A7L /*OneStep_Open_End*/);
		setVariable(0xF06310B7L /*_stance*/, 12);
		doAction(2966115324L /*OPEN_551to600*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xDF434D85L /*_ISOpening*/, 1);
		if (getVariable(0xF06310B7L /*_stance*/) == 0) {
			if (changeState(state -> OneStep_Open(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 1) {
			if (changeState(state -> OneStep_Open_Lv1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 2) {
			if (changeState(state -> OneStep_Open_Lv2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 3) {
			if (changeState(state -> OneStep_Open_Lv3(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 4) {
			if (changeState(state -> OneStep_Open_Lv4(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 5) {
			if (changeState(state -> OneStep_Open_Lv5(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 6) {
			if (changeState(state -> OneStep_Open_Lv6(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 7) {
			if (changeState(state -> OneStep_Open_Lv7(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 8) {
			if (changeState(state -> OneStep_Open_Lv8(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 9) {
			if (changeState(state -> OneStep_Open_Lv9(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 10) {
			if (changeState(state -> OneStep_Open_Lv10(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 11) {
			if (changeState(state -> OneStep_Open_End(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xF06310B7L /*_stance*/, 0);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOpen(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xF06310B7L /*_stance*/) == 0 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_Lv0(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 1 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_Lv1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 2 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_Lv2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 3 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_Lv3(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 4 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_Lv4(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 5 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_Lv5(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 6 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_Lv6(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 7 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_Lv7(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 8 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_Lv8(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 9 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_Lv9(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 10 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_Lv10(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 11 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Open_End(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleClose(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xF06310B7L /*_stance*/) == 1 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_End(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 2 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_Lv10(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 3 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_Lv9(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 4 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_Lv8(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 5 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_Lv7(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 6 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_Lv6(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 7 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_Lv5(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 8 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_Lv4(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 9 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_Lv3(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 10 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_Lv2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 11 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_Lv1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 12 && getVariable(0xDF434D85L /*_ISOpening*/) == 0) {
			if (changeState(state -> Close_Lv0(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
