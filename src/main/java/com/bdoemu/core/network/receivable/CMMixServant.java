package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.ServantEquipments;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.MixServantItemEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMMixServant extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private long mixServantObjId1;
    private long mixServantObjId2;
    private long moneyObjId;
    private EItemStorageLocation storageLocation;
    private String servantName;

    public CMMixServant(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.mixServantObjId1 = this.readQ();
        this.mixServantObjId2 = this.readQ();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.moneyObjId = this.readQ();
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
            final Servant mixServant1 = player.getServantController().getServant(this.mixServantObjId1);
            if (mixServant1 == null || !mixServant1.getServantState().isStable() || mixServant1.getRegionId() != npc.getRegionId() || !canAct(player, mixServant1)) {
                return;
            }
            final Servant mixServant2 = player.getServantController().getServant(this.mixServantObjId2);
            if (mixServant2 == null || !mixServant2.getServantState().isStable() || mixServant2.getRegionId() != npc.getRegionId() || !canAct(player, mixServant2)) {
                return;
            }
            if (mixServant1.getServantSetTemplate().isMale() != mixServant2.getServantSetTemplate().isMale()) {
                player.getPlayerBag().onEvent(new MixServantItemEvent(player, mixServant1, mixServant2, this.servantName, this.storageLocation, npc.getRegionId()));
            }
        }
    }

    private boolean canAct(Player player, Servant servant) {
        final ServantEquipments equipments = servant.getEquipments();
        final ItemPack inventory = servant.getInventory();
        if ((equipments != null && !equipments.getItemMap().isEmpty())
                || (inventory != null && !inventory.getItemMap().isEmpty())) {
            player.sendPacket(new SMNak(EStringTable.eErrNoRegisterServantFailedByItem, CMMixServant.class));
            return false;
        }
        return true;
    }
}