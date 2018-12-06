package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListFixedHouseInstallations;
import com.bdoemu.core.network.sendable.SMVisitHouse;
import com.bdoemu.gameserver.databaseCollections.HouseHoldDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class CMGetInstallationList extends ReceivablePacket<GameClient> {
    private static final Logger log = LoggerFactory.getLogger(CMGetInstallationList.class);
    private long objectId;
    private long accountId;
    private int houseId;
    private byte type;

    public CMGetInstallationList(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.objectId = this.readQ();
        this.houseId = this.readH();
        this.accountId = this.readQ();
        this.type = this.readC();
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null && player.isSpawned()) {
            switch (this.type) {
                case 5: {
                    HouseHold houseFixed = null;
                    final Player houseOwner = World.getInstance().getPlayerByAccount(this.accountId);
                    if (houseOwner != null) {
                        houseFixed = houseOwner.getHouseholdController().getHouseHold(this.objectId);
                    } else {
                        houseFixed = HouseHoldDBCollection.getInstance().load(this.objectId);
                    }
                    if (houseFixed != null) {
                        if (!houseFixed.getInstallations().isEmpty()) {
                            final ListSplitter<HouseInstallation> houseInstallationSplitter = new ListSplitter<>(houseFixed.getInstallations(), SMListFixedHouseInstallations.MAX_COUNT);
                            while (houseInstallationSplitter.hasNext()) {
                                player.sendPacket(new SMListFixedHouseInstallations(houseFixed, houseInstallationSplitter.getNext(), houseInstallationSplitter.isFirst() ? EPacketTaskType.Update : EPacketTaskType.Add));
                            }
                        }
                        player.getHouseVisit().setHouseId(this.houseId);
                        player.getHouseVisit().setHouseObjectId(this.objectId);
                        player.sendBroadcastItSelfPacket(new SMVisitHouse(player.getGameObjectId(), this.houseId, this.objectId));
                        break;
                    }
                    break;
                }
                default: {
                    player.getHouseVisit().setHouseId(0);
                    player.getHouseVisit().setHouseObjectId(0L);
                    break;
                }
            }
        }
    }
}
