// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMStartSympathetic;
import com.bdoemu.gameserver.dataholders.ServantBuffData;
import com.bdoemu.gameserver.dataholders.ServantInteractionData;
import com.bdoemu.gameserver.model.actions.ActionStorage;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantBuffT;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantInteractionT;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collections;

public class CMStartSympathetic extends ReceivablePacket<GameClient> {
    private int servantGameObjId;

    public CMStartSympathetic(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.servantGameObjId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Servant servant = player.getServantController().getServant(this.servantGameObjId);
            if (servant != null && servant.getInteractedCoolTime() == 0L) {
                final ServantInteractionT interactionT = ServantInteractionData.getInstance().getTemplate(servant.getCreatureId());
                if (interactionT == null) {
                    return;
                }
                servant.setInteractionCoolTime(GameTimeService.getServerTimeInMillis() + interactionT.getCoolTime());
                servant.setLastInteractedDate(GameTimeService.getServerTimeInMillis());
                final Integer index = interactionT.getBuffList().get(Rnd.get(0, interactionT.getBuffList().size() - 1));
                final ServantBuffT buffT = ServantBuffData.getInstance().getTemplate(index);
                final ActionStorage playerActionStorage = player.getActionStorage();
                if (playerActionStorage.getCurrentPackageMap() != null) {
                    final IAction action = playerActionStorage.getNewAction(buffT.getActionHash());
                    if (action != null) {
                        action.setOwner(player);
                        action.setNewLocation(player.getLocation());
                        action.setType((byte) 1);
                        action.setCallAction(false);
                        action.setSkillLevel(0);
                        action.setSkillId(0);
                        player.setAction(action);
                    }
                }
                final ActionStorage servantActionStorage = servant.getActionStorage();
                if (servantActionStorage.getCurrentPackageMap() != null) {
                    final IAction action = servantActionStorage.getNewAction(buffT.getServantActionHash());
                    if (action != null) {
                        action.setOwner(servant);
                        action.setNewLocation(servant.getLocation());
                        action.setType((byte) 1);
                        action.setCallAction(false);
                        action.setSkillLevel(0);
                        action.setSkillId(0);
                        servant.setAction(action);
                    }
                }
                SkillService.useSkill(player, buffT.getSkillId(), null, Collections.singletonList(servant));
                player.sendPacket(new SMStartSympathetic(this.servantGameObjId));
            }
        }
    }
}
