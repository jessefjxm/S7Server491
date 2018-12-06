// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.configs.DebugConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.actions.ActionStorage;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.creature.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMStartAction extends ReceivablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) CMStartAction.class);
    }

    private IAction action;

    public CMStartAction(final short opcode) {
        super(opcode);
        this.action = null;
    }

    protected void read() {
        final long actionHash = this.readDQ();
        final ActionStorage actionStorage = ((GameClient) this.getClient()).getPlayer().getActionStorage();
        if (actionStorage.getCurrentPackageMap() != null) {
            this.action = actionStorage.getNewAction(actionHash);
            if (this.action != null) {
                this.action.setType((byte) 0);
                this.action.setSkillId(0);
                this.action.setSkillLevel(0);
                this.action.setCallAction(false);
                this.action.setMessage(EStringTable.NONE);
                this.action.read(this.getBuffer(), ((GameClient) this.getClient()).getPlayer());
                if (this.action.getActionChartActionT().isForceMove()) {
                    this.action.setTargetX(this.action.getNewX());
                    this.action.setTargetY(this.action.getNewY());
                    this.action.setTargetZ(this.action.getNewZ());
                } else {
                    this.action.setTargetX(0.0);
                    this.action.setTargetY(0.0);
                    this.action.setTargetZ(0.0);
                }
                if (DebugConfig.ENABLE_ACTIONS_DEBUG) {
                    CMStartAction.log.debug("Action actionHash " + actionHash + " actionName " + this.action.getActionName() + " package " + actionStorage.getActionChartPackage() + " branchIndex " + this.action.getBranchIndex());
                }
            } else {
                this.skipAll();
            }
        } else {
            this.skipAll();
        }
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            if (this.action != null && !this.action.getActionChartActionT().getActionType().isMove()) {
                player.doFallDamage();
            }
            player.setAction(this.action);
            this.action = null;
        }
    }
}
