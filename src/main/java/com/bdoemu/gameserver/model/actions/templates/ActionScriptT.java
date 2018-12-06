package com.bdoemu.gameserver.model.actions.templates;

import com.bdoemu.commons.io.FileBinaryReader;

public class ActionScriptT {
    private final String name;
    private final ActionScriptActionChartT actionChart;
    private final ActionScriptHitPartT hitPart;

    public ActionScriptT(final FileBinaryReader reader) {
        this.hitPart = new ActionScriptHitPartT();
        this.name = reader.readS();
        this.actionChart = new ActionScriptActionChartT(reader);
    }

    public String getName() {
        return this.name;
    }

    public ActionScriptActionChartT getActionChart() {
        return this.actionChart;
    }

    public ActionScriptHitPartT getHitPart() {
        return this.hitPart;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ActionScriptT)) {
            return false;
        }
        final ActionScriptT other = (ActionScriptT) o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        Label_0065:
        {
            if (this$name == null) {
                if (other$name == null) {
                    break Label_0065;
                }
            } else if (this$name.equals(other$name)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$actionChart = this.getActionChart();
        final Object other$actionChart = other.getActionChart();
        Label_0102:
        {
            if (this$actionChart == null) {
                if (other$actionChart == null) {
                    break Label_0102;
                }
            } else if (this$actionChart.equals(other$actionChart)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$hitPart = this.getHitPart();
        final Object other$hitPart = other.getHitPart();
        if (this$hitPart == null) {
            if (other$hitPart == null) {
                return true;
            }
        } else if (this$hitPart.equals(other$hitPart)) {
            return true;
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ActionScriptT;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * 59 + (($name == null) ? 43 : $name.hashCode());
        final Object $actionChart = this.getActionChart();
        result = result * 59 + (($actionChart == null) ? 43 : $actionChart.hashCode());
        final Object $hitPart = this.getHitPart();
        result = result * 59 + (($hitPart == null) ? 43 : $hitPart.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ActionScriptT(name=" + this.getName() + ", actionChart=" + this.getActionChart() + ", hitPart=" + this.getHitPart() + ")";
    }
}
