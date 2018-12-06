// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.dialogs.templates;

import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;
import com.bdoemu.gameserver.model.functions.FunctionService;
import com.bdoemu.gameserver.model.functions.IFunctionHandler;
import com.bdoemu.gameserver.model.misc.enums.EDialogButtonType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DialogTemplate {
    private int npcId;
    private int dialogIndex;
    private EDialogButtonType buttonType;
    private List<IFunctionHandler> functions;
    private IAcceptConditionHandler[][] conditions;

    public DialogTemplate(final ResultSet rs) throws SQLException {
        this.dialogIndex = rs.getInt("DialogIndex");
        this.npcId = rs.getInt("Npc");
        final String condition = rs.getString("Condition");
        if (condition != null) {
            this.conditions = ConditionService.getAcceptConditions(condition);
        }
        final String function = rs.getString("Function");
        if (function != null) {
            this.functions = FunctionService.getFunctions(this.dialogIndex, function);
        }
        final String buttonTypeString = rs.getString("ButtonType");
        if (buttonTypeString != null && !buttonTypeString.trim().isEmpty()) {
            this.buttonType = EDialogButtonType.valueOf(rs.getInt("ButtonType"));
        }
    }

    public boolean isDisabledByContentsGroup() {
        if (this.functions != null) {
            for (final IFunctionHandler functionHandler : this.functions) {
                if (functionHandler.isDisabledByContentsGroup()) {
                    return true;
                }
            }
        }
        return false;
    }

    public IAcceptConditionHandler[][] getConditions() {
        return this.conditions;
    }

    public List<IFunctionHandler> getFunctions() {
        return this.functions;
    }

    public int getNpcId() {
        return this.npcId;
    }

    public int getDialogIndex() {
        return this.dialogIndex;
    }

    public EDialogButtonType getButtonType() {
        return this.buttonType;
    }
}
