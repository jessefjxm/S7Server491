package com.bdoemu.gameserver.model.houses.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMListFixedHouseInstallations;
import com.bdoemu.core.network.sendable.SMStartAlchemy;
import com.bdoemu.gameserver.model.alchemy.enums.EAlchemyType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.StartAlchemyItemEvent;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.houses.HouseStorage;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collections;

public class StartAlchemyHouseEvent implements IHouseEvent {
    private Player player;
    private long houseObjId;
    private long installationObjId;
    private int size;
    private long[][] sts;
    private HouseStorage houseStorage;
    private HouseHold houseHold;
    private HouseInstallation houseInstallation;
    private EStringTable messageId;
    private int type;
    private EAlchemyType alchemyType;

    public StartAlchemyHouseEvent(final Player player, final long houseObjId, final long installationObjId, final int size, final long[]... sts) {
        this.messageId = EStringTable.NONE;
        this.type = 0;
        this.player = player;
        this.houseObjId = houseObjId;
        this.installationObjId = installationObjId;
        this.size = size;
        this.sts = sts;
        this.houseStorage = player.getHouseStorage();
    }

    @Override
    public void onEvent() {
        this.houseInstallation.setEndurance(this.houseInstallation.getEndurance() - 1);
        this.player.sendBroadcastItSelfPacket(new SMListFixedHouseInstallations(this.houseHold, Collections.singleton(this.houseInstallation), EPacketTaskType.Add));
        this.player.sendPacket(new SMStartAlchemy(this.alchemyType, this.messageId, this.type));
    }

    @Override
    public boolean canAct() {
        this.houseHold = this.player.getHouseholdController().getHouseHold(this.houseObjId);
        if (this.houseHold == null) {
            return false;
        }
        this.houseInstallation = this.houseHold.getHouseInstallation(this.installationObjId);
        if (this.houseInstallation == null) {
            return false;
        }
        switch (this.houseInstallation.getObjectTemplate().getInstallationType()) {
            case Alchemy: {
                this.alchemyType = EAlchemyType.Alchemy;
                break;
            }
            case Cooking: {
                this.alchemyType = EAlchemyType.Coocking;
                break;
            }
            default: {
                return false;
            }
        }
        if (this.houseInstallation.getEndurance() <= 0) {
            this.player.sendPacket(new SMStartAlchemy(this.alchemyType, EStringTable.eErrNoInstallationEnduranceIsLack, 4));
            return false;
        }
        final StartAlchemyItemEvent event = new StartAlchemyItemEvent(this.player, this.alchemyType, this.size, this.sts);
        if (!this.player.getPlayerBag().onEvent(event)) {
            return false;
        }
        if (event.getAlchemyT() == null) {
            this.messageId = EStringTable.eErrNoCookingIsFailed;
            this.type = 1;
        }
        return true;
    }
}
