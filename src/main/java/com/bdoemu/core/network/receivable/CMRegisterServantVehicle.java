// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.dataholders.ActionEXPData;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.action.ActionEXPT;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantSealType;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import com.bdoemu.gameserver.worldInstance.World;

public class CMRegisterServantVehicle extends ReceivablePacket<GameClient> {
    private String servantName;
    private int gameObjectId;

    public CMRegisterServantVehicle(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.servantName = this.readS(62);
        this.gameObjectId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            if (this.servantName.length() < 2 || this.servantName.length() > 10) {
                player.sendPacket(new SMNak(EStringTable.eErrNoNameLengthIsTooShort, this.opCode));
                return;
            }
            String regex = "[0-9]+";
            if (this.servantName.matches(regex)) {
                player.sendPacket(new SMNak(EStringTable.eErrNoNameDontHaveNumberOnly, this.opCode));
                return;
            }
            regex = "(.)\\1\\1";
            if (this.servantName.matches(regex) || Character.isLowerCase(this.servantName.charAt(0))) {
                player.sendPacket(new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
                return;
            }
            regex = "[a-zA-Z0-9]+";
            if (!this.servantName.matches(regex)) {
                player.sendPacket(new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
                return;
            }
            final Creature sealNpcObject = World.getInstance().getObjectById(this.gameObjectId);
            if (sealNpcObject != null && sealNpcObject.isNpc()) {
                final Npc sealNpc = (Npc) sealNpcObject;
                final Servant servant = player.getServantController().getTameServant();
                if (servant != null) {
                    if (player.getServantController().isStableFull(sealNpc.getRegionId(), servant.getServantType())) {
                        player.sendPacket(new SMNak(EStringTable.eErrNoStableIsFull, this.opCode));
                        return;
                    }
                    if (player.getServantController().getServants(EServantType.Vehicle).contains(servant)) {
                        return;
                    }
                    servant.setRegionId(sealNpc.getRegionId());
                    servant.setAccountId(player.getAccountId());
                    servant.setName(this.servantName);
                    servant.seal(EServantSealType.TAMING);
                    player.getServantController().add(servant);
                    final ActionEXPT actionEXPT = ActionEXPData.getInstance().getExpTemplate(player, player.getLifeExperienceStorage().getLifeExperience(ELifeExpType.Taming).getLevel());
                    if (actionEXPT != null) {
                        player.addExp(actionEXPT.getHorseTaming());
                    }
                }
            }
        }
    }
}
