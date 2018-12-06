package com.bdoemu.gameserver.model.actions.templates;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.commons.utils.HashUtil;

import java.util.HashMap;
import java.util.Map;

public class ActionChartPackageMapT {
    private final String packageName;
    private final Map<Integer, Map<Long, Integer>> aiParams;
    private Map<Long, ActionChartActionT> actions;

    public ActionChartPackageMapT(final FileBinaryReader reader) {
        this.aiParams = new HashMap<>();
        this.actions = new HashMap<>();
        this.packageName = reader.readS();
        for (int aiParamFormCount = reader.readD(), aiParamFormIndex = 0; aiParamFormIndex < aiParamFormCount; ++aiParamFormIndex) {
            final int formIndex = reader.readD();
            if (!this.aiParams.containsKey(formIndex)) {
                this.aiParams.put(formIndex, new HashMap<>());
            }
            for (int aiParamCount = reader.readD(), aiParamIndex = 0; aiParamIndex < aiParamCount; ++aiParamIndex) {
                this.aiParams.get(formIndex).put(HashUtil.generateHashA(reader.readS().toLowerCase()), (int) reader.readQ());
            }
        }
        for (int actionCount = reader.readD(), actionIndex = 0; actionIndex < actionCount; ++actionIndex) {
            final ActionChartActionT actionChartAction = new ActionChartActionT(reader);
            this.actions.put(actionChartAction.getActionHash(), actionChartAction);
        }
    }

    public ActionChartActionT getActionChartTemplate(final long actionHash) {
        return this.actions.get(actionHash);
    }

    public String getPackageName() {
        return this.packageName;
    }

    public Map<Integer, Map<Long, Integer>> getAiParams() {
        return this.aiParams;
    }

    public Map<Long, ActionChartActionT> getActions() {
        return this.actions;
    }

    public void setActions(final Map<Long, ActionChartActionT> actions) {
        this.actions = actions;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ActionChartPackageMapT)) {
            return false;
        }
        final ActionChartPackageMapT other = (ActionChartPackageMapT) o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$packageName = this.getPackageName();
        final Object other$packageName = other.getPackageName();
        Label_0065:
        {
            if (this$packageName == null) {
                if (other$packageName == null) {
                    break Label_0065;
                }
            } else if (this$packageName.equals(other$packageName)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$aiParams = this.getAiParams();
        final Object other$aiParams = other.getAiParams();
        Label_0102:
        {
            if (this$aiParams == null) {
                if (other$aiParams == null) {
                    break Label_0102;
                }
            } else if (this$aiParams.equals(other$aiParams)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$actions = this.getActions();
        final Object other$actions = other.getActions();
        if (this$actions == null) {
            if (other$actions == null) {
                return true;
            }
        } else if (this$actions.equals(other$actions)) {
            return true;
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ActionChartPackageMapT;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $packageName = this.getPackageName();
        result = result * 59 + (($packageName == null) ? 43 : $packageName.hashCode());
        final Object $aiParams = this.getAiParams();
        result = result * 59 + (($aiParams == null) ? 43 : $aiParams.hashCode());
        final Object $actions = this.getActions();
        result = result * 59 + (($actions == null) ? 43 : $actions.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ActionChartPackageMapT(packageName=" + this.getPackageName() + ", aiParams=" + this.getAiParams() + ", actions=" + this.getActions() + ")";
    }
}
