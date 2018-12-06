package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMBroadcastGetItem;
import com.bdoemu.core.network.sendable.SMListServantInfo;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMResultServantMating;
import com.bdoemu.gameserver.dataholders.ServantData;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.model.ServantTemplate;
import com.bdoemu.gameserver.model.items.enums.EItemGetType;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.worldInstance.World;

public class CMResultServantMating extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private int servantId;
    private long servantObjId;
    private String servantName;

    public CMResultServantMating(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.servantObjId = this.readQ();
        this.servantId = this.readHD();
        this.servantName = this.readS(62);
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
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
            if (npc == null) {
                return;
            }
            final Servant femaleServant = player.getServantController().getServant(this.servantObjId);
            if (femaleServant != null && !femaleServant.getServantSetTemplate().isMale() && femaleServant.getServantState().isMating() && femaleServant.getRegionId() == npc.getRegionId() && femaleServant.getMatingTime() < 0L) {
                femaleServant.setServantState(EServantState.Stable);
                final ServantTemplate template = ServantData.getInstance().getTemplate(femaleServant.getMatingChildId(), 1);
                if (template != null) {
                    final Servant servant = new Servant(template, player, this.servantName);
                    servant.setRegionId(npc.getRegionId());
                    servant.setAccountId(player.getAccountId());
                    player.getServantController().add(servant);
                    if (servant.getServantSetTemplate().getTier() > 7)
                        World.getInstance().broadcastWorldPacket(new SMBroadcastGetItem(EItemGetType.HORSE, player.getName(), servant.getCreatureId(), 0, 0));
                    player.sendPacket(new SMListServantInfo(servant, EPacketTaskType.Update));
                }
                femaleServant.setMatingChildId(0);
                player.sendPacket(new SMResultServantMating(this.servantObjId));
            }
        }
    }
}
