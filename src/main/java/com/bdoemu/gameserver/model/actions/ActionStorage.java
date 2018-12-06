package com.bdoemu.gameserver.model.actions;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.dataholders.binary.BinaryActionChartData;
import com.bdoemu.gameserver.model.actions.enums.EActionEffectType;
import com.bdoemu.gameserver.model.actions.enums.EActionType;
import com.bdoemu.gameserver.model.actions.enums.EApplySpeedBuffType;
import com.bdoemu.gameserver.model.actions.enums.EWeaponType;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.actions.templates.ActionChartPackageMapT;
import com.bdoemu.gameserver.model.actions.templates.ActionScriptT;
import com.bdoemu.gameserver.model.creature.Creature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ActionStorage {
    public static final long DIE_ACTION_HASH = 2544805566L;
    public static final long WAIT_ACTION_HASH = 2514775444L;
    public static final long BATTLE_WAIT_ACTION_HASH = 2524986171L;
    private static final Logger log = LoggerFactory.getLogger(ActionStorage.class);
    private Creature owner;
    private ActionScriptT actionScript;
    private String actionChartPackage;
    private IAction action;
    private int callCount;
    private int actionEffects;
    private EnumMap<EActionType, IAction> actions;
    private float moveSpeed;
    private EWeaponType weaponType;
    private ActionTask actionTask;
    private ActionChartActionT actionChartActionT;

    public ActionStorage(final Creature owner) {
        this.callCount = 0;
        this.weaponType = EWeaponType.None;
        this.owner = owner;
        this.actionChartPackage = owner.getTemplate().getAiScriptActionScriptPath();
        this.actionScript = BinaryActionChartData.getInstance().getActionScript(owner.getTemplate().getAiScriptActionScriptPath());
        if (this.getCurrentPackageMap() != null) {
            this.actionChartActionT = this.getCurrentPackageMap().getActionChartTemplate(2514775444L);
            (this.action = this.actionChartActionT.getActionType().getNewActionInstance(this.actionChartActionT)).setOwner(owner);
        } else {
            ActionStorage.log.warn("Action script path {} didn't exist in BinaryActionChartData!", (Object) owner.getTemplate().getAiScriptActionScriptPath());
        }
    }

    public void setActionEffect(final EActionEffectType state) {
        this.actionEffects |= state.getState();
    }

    public void unsetActionEffect(final EActionEffectType state) {
        this.actionEffects &= ~state.getState();
    }

    public boolean hasActionEffect(final EActionEffectType state) {
        return (this.actionEffects & state.getState()) == state.getState();
    }

    public long getActionHash() {
        return this.actionChartActionT.getActionHash();
    }

    public IAction getAction() {
        return this.action;
    }

    public void setAction(final IAction action) {
        if (this.action != null && this.action.getActionHash() == action.getActionHash()) {
            ++this.callCount;
        } else {
            this.callCount = 1;
        }
        this.action = action;
        this.actionChartActionT = action.getActionChartActionT();
        this.moveSpeed = action.getActionChartActionT().getMaxMoveSpeed();
    }

    public void onActionError(final long action, final EStringTable stringTable) {
        final ActionChartActionT actionChartActionT = this.getCurrentPackageMap().getActionChartTemplate(action);
        if (actionChartActionT != null) {
            this.actionChartActionT = actionChartActionT;
            (this.action = actionChartActionT.getActionType().getNewActionInstance(actionChartActionT)).setOwner(this.owner);
            this.action.setOldLocation(this.owner.getLocation());
            this.action.setNewLocation(this.owner.getLocation());
        }
        this.action.setMessage(stringTable);
        this.action.onError();
    }

    public EApplySpeedBuffType getApplySpeedBuffType() {
        return this.actionChartActionT.getApplySpeedBuffType();
    }

    public EWeaponType getWeaponType() {
        return this.weaponType;
    }

    public void setWeaponType(final EWeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public void cancelActionTask() {
        if (this.actionTask != null) {
            this.actionTask.cancelActionTask();
        }
    }

    public void setActionTask(final ActionTask actionTask) {
        this.actionTask = actionTask;
    }

    public float getMoveSpeed() {
        return this.moveSpeed;
    }

    public void setMoveSpeed(final float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public ActionScriptT getActionScript() {
        return this.actionScript;
    }

    public Map<Long, Integer> getAiParameters() {
        final Map<Long, Integer> aiParams = new HashMap<>();
        if (this.actionScript == null) {
            return aiParams;
        }
        if (this.actionScript.getActionChart().getActionChartPackageMap(this.actionChartPackage) == null) {
            return aiParams;
        }
        final ActionChartPackageMapT packageMap = this.actionScript.getActionChart().getActionChartPackageMap(this.actionChartPackage);
        if (packageMap.getAiParams().containsKey(0)) {
            aiParams.putAll(packageMap.getAiParams().get(0));
        }
        if (packageMap.getAiParams().containsKey(this.owner.getActionIndex())) {
            aiParams.putAll(packageMap.getAiParams().get(this.owner.getActionIndex()));
        }
        return aiParams;
    }

    protected int getCallCount() {
        return this.callCount;
    }

    public ActionChartPackageMapT getCurrentPackageMap() {
        if (this.actionScript != null) {
            return this.actionScript.getActionChart().getActionChartPackageMap(this.actionChartPackage);
        }
        return null;
    }

    public ActionChartActionT getActionChartActionT(final long actionHash) {
        return this.getCurrentPackageMap().getActionChartTemplate(actionHash);
    }

    public int getSpeedRate() {
        return this.actionChartActionT.getSpeedRate(this.owner);
    }

    public int getSlowSpeedRate() {
        return this.actionChartActionT.getSlowSpeedRate(this.owner);
    }

    public IAction getNewAction(final long actionHash) {
        if (this.actions == null) {
            this.actions = new EnumMap<>(EActionType.class);
        }
        IAction action = null;
        final ActionChartActionT actionChartActionT = this.getCurrentPackageMap().getActionChartTemplate(actionHash);
        if (actionChartActionT != null) {
            action = this.actions.get(actionChartActionT.getActionType());
            if (action != null) {
                action.setActionChartActionT(actionChartActionT);
            } else {
                action = actionChartActionT.getActionType().getNewActionInstance(actionChartActionT);
                if (action != null) {
                    this.actions.put(actionChartActionT.getActionType(), action);
                }
            }
        }
        return action;
    }

    public ActionChartActionT getActionChartActionT() {
        return this.getAction().getActionChartActionT();
    }

    public void setActionChartActionT(final ActionChartActionT actionChartActionT) {
        this.actionChartActionT = actionChartActionT;
    }

    public EActionType getActionType() {
        return this.getActionChartActionT().getActionType();
    }

    public String getActionChartPackage() {
        return this.actionChartPackage;
    }

    public void setActionChartPackage(final String actionChartPackage) {
        this.actionChartPackage = actionChartPackage;
    }
}
