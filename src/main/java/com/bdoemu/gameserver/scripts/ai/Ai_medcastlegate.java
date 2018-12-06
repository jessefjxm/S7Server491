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
@IAIName("medcastlegate")
public class Ai_medcastlegate extends CreatureAI {
	public Ai_medcastlegate(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xF06310B7L /*_stance*/, 6);
		setVariable(0xDF434D85L /*_ISOpening*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Stance(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A34695AL /*Stance*/);
		setVariable(0xDF434D85L /*_ISOpening*/, 0);
		if (getVariable(0xF06310B7L /*_stance*/) == 0) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 1) {
			if (changeState(state -> Set_100f(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 2) {
			if (changeState(state -> Set_200f(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 3) {
			if (changeState(state -> Set_300f(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 4) {
			if (changeState(state -> Set_400f(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 5) {
			if (changeState(state -> Set_500f(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) >= 6) {
			if (changeState(state -> Wait_Open(blendTime)))
				return;
		}
		changeState(state -> Stance(blendTime));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void Open_Lv0(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB3883BL /*Open_Lv0*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(3451686694L /*OPEN_0to100*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4F10847CL /*Open_Lv1*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(2870496958L /*OPEN_101to200*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6B727B1DL /*Open_Lv2*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(710740751L /*OPEN_201to300*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE5A54D88L /*Open_Lv3*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(2344335666L /*OPEN_301to400*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_Lv4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2BEF4A79L /*Open_Lv4*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(3134951674L /*OPEN_401to500*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Open_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5B1CD474L /*Open_End*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) + 1);
		doAction(3028268495L /*OPEN_501to600*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv0(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE50D4519L /*Close_Lv0*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(3863010481L /*CLOSE_0to100*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8F4780B4L /*Close_Lv1*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(4196277468L /*CLOSE_101to200*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x666198DBL /*Close_Lv2*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(4189450035L /*CLOSE_201to300*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5BB5981L /*Close_Lv3*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(3685193890L /*CLOSE_301to400*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_Lv4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x74A62F5L /*Close_Lv4*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(3661423851L /*CLOSE_401to500*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Close_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB7751B73L /*Close_End*/);
		setVariable(0xF06310B7L /*_stance*/, getVariable(0xF06310B7L /*_stance*/) - 1);
		doAction(4124804089L /*CLOSE_501to600*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			setVariable(0xDF434D85L /*_ISOpening*/, 0);
			scheduleState(state -> TerminateState(blendTime), 1000);
		});
	}

	protected void Set_100f(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x56E4EF9AL /*Set_100f*/);
		doAction(1461885018L /*SET_100f*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Set_200f(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x33CE10F9L /*Set_200f*/);
		doAction(4116952922L /*SET_200f*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Set_300f(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x31807890L /*Set_300f*/);
		doAction(3322899830L /*SET_300f*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Set_400f(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEAEBCE07L /*Set_400f*/);
		doAction(4138933984L /*SET_400f*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Set_500f(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5F29C6C3L /*Set_500f*/);
		doAction(3538251205L /*SET_500f*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Wait_Open(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB1B22E56L /*Wait_Open*/);
		doAction(3081895922L /*WAIT_OPEN*/, blendTime, onDoActionEnd -> {
			setVariable(0xDF434D85L /*_ISOpening*/, 0);
			scheduleState(state -> TerminateState(blendTime), 1000);
		});
	}

	protected void OneStep_Open(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC2A6B769L /*OneStep_Open*/);
		setVariable(0xF06310B7L /*_stance*/, 6);
		doAction(3451686694L /*OPEN_0to100*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv1(blendTime), 1000));
	}

	protected void OneStep_Open_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x418F1EE4L /*OneStep_Open_Lv1*/);
		setVariable(0xF06310B7L /*_stance*/, 6);
		doAction(2870496958L /*OPEN_101to200*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv2(blendTime), 1000));
	}

	protected void OneStep_Open_Lv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6B1D3E59L /*OneStep_Open_Lv2*/);
		setVariable(0xF06310B7L /*_stance*/, 6);
		doAction(710740751L /*OPEN_201to300*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv3(blendTime), 1000));
	}

	protected void OneStep_Open_Lv3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF5C572BFL /*OneStep_Open_Lv3*/);
		setVariable(0xF06310B7L /*_stance*/, 6);
		doAction(2344335666L /*OPEN_301to400*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_Lv4(blendTime), 1000));
	}

	protected void OneStep_Open_Lv4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF67AD0BL /*OneStep_Open_Lv4*/);
		setVariable(0xF06310B7L /*_stance*/, 6);
		doAction(3134951674L /*OPEN_401to500*/, blendTime, onDoActionEnd -> scheduleState(state -> OneStep_Open_End(blendTime), 1000));
	}

	protected void OneStep_Open_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8B9354A7L /*OneStep_Open_End*/);
		setVariable(0xF06310B7L /*_stance*/, 6);
		doAction(3028268495L /*OPEN_501to600*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 1000));
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
		if (getVariable(0xF06310B7L /*_stance*/) == 0 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Open_Lv0(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 1 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Open_Lv1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 2 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Open_Lv2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 3 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Open_Lv3(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 4 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Open_Lv4(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 5 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Open_End(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleClose(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xF06310B7L /*_stance*/) == 1 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Close_End(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 2 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Close_Lv4(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 3 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Close_Lv3(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 4 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Close_Lv2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 5 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Close_Lv1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 6 && getVariable(0xDF434D85L /*_ISOpening*/) == 0 && (getState() == 0xF74207F6L /*TerminateState*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x56E4EF9AL /*Set_100f*/ || getState() == 0x33CE10F9L /*Set_200f*/ || getState() == 0x31807890L /*Set_300f*/ || getState() == 0xEAEBCE07L /*Set_400f*/ || getState() == 0x5F29C6C3L /*Set_500f*/ || getState() == 0xB1B22E56L /*Wait_Open*/)) {
			if (changeState(state -> Close_Lv0(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
