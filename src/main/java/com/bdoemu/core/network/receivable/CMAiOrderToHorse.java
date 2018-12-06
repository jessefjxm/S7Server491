// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMAiOrderToHorse extends ReceivablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) CMAiOrderToHorse.class);
    }

    private int servantObjectId;
    private int orderType;
    private int slotIndex;
    private EItemStorageLocation storageLocation;

    public CMAiOrderToHorse(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.servantObjectId = this.readD();
        this.orderType = this.readC();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Servant servant = player.getServantController().getServant(this.servantObjectId);
            if (servant != null) {
                switch (this.orderType) {
                    case 43: {
                        if (servant.getActionStorage().getAction().getActionChartActionT().isParkingAction()) {
                            player.sendPacket(new SMNak(EStringTable.eErrNoServantIsParking, this.opCode));
                            return;
                        }
                        servant.getAi().HandleParkingHorse(player, null);
                        break;
                    }
                    case 55: {
                        int distance = 7500;
                        if (this.storageLocation.isPlayerInventories()) {
                            final Item item = player.getPlayerBag().getItemPack(this.storageLocation).getItem(this.slotIndex);
                            if (item != null && item.canUse()) {
                                final EContentsEventType contentsEventType = item.getTemplate().getContentsEventType();
                                if (contentsEventType != null && contentsEventType.isOrderToServant()) {
                                    distance = item.getTemplate().getContentsEventParam2();
                                }
                            }
                        }
                        if (!MathUtils.isInRange(servant.getLocation(), player.getLocation(), distance)) {
                            player.sendPacket(new SMNak(EStringTable.eErrNoServantIsNoCall, this.opCode));
                            return;
                        }
                        if (!servant.getCurrentRiders().isEmpty()) {
                            return;
                        }
                        servant.getAi().HandleWhistle(player, null);
                        break;
                    }
                    default: {
                        CMAiOrderToHorse.log.warn("Unknown CMAiOrderToHorse type detected (orderType={})", (Object) this.orderType);
                        break;
                    }
                }
            }
        }
    }
}
