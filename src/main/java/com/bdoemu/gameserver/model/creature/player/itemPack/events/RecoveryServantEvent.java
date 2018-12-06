// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.core.network.receivable.CMRecoveryServant;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.utils.MathUtils;

public class RecoveryServantEvent extends AItemEvent {
    private Servant servant;
    private Npc npc;
    private int type;
    private EItemStorageLocation storageLocation;

    public RecoveryServantEvent(final Player player, final Npc npc, final Servant servant, final int type, final EItemStorageLocation storageLocation) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, npc.getRegionId());
        this.npc = npc;
        this.servant = servant;
        this.type = type;
        this.storageLocation = storageLocation;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.servant.recovery(this.player, this.npc.getRegionId());
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isInventory() && !this.storageLocation.isWarehouse()) {
            return false;
        }
        final int recoveryPercentage = 100 - this.servant.getGameStats().getHp().getHpPercentage() + (100 - this.servant.getGameStats().getMp().getMpPercentage());
        int recoveryPrice = 0;
        switch (this.type) {
            case 0: {
                if (!this.servant.getServantState().isField()) {
                    return false;
                }
                if (MathUtils.getDistance(this.npc.getLocation(), this.servant.getLocation()) > ServantConfig.VEHICLE_SEAL_DISTANCE) {
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoDistanceIsFar, CMRecoveryServant.class));
                    return false;
                }
                recoveryPrice = recoveryPercentage * ServantConfig.VEHICLE_RECOVERY_COST;
                if (this.servant.isImprint()) {
                    recoveryPrice = recoveryPrice * (100 - ServantConfig.DISCOUNT_PERCENT_FOR_SERVANT_IMPRINT / 10000) / 100;
                    break;
                }
                break;
            }
            case 1: {
                if (!this.servant.getServantState().isStable()) {
                    return false;
                }
                recoveryPrice = recoveryPercentage * ServantConfig.VEHICLE_RECOVERY_COST;
                if (this.servant.isImprint()) {
                    recoveryPrice = recoveryPrice * (100 - ServantConfig.DISCOUNT_PERCENT_FOR_SERVANT_IMPRINT / 10000) / 100;
                    break;
                }
                break;
            }
            case 2: {
                if (!this.servant.getServantState().isComa()) {
                    return false;
                }
                recoveryPrice = ServantConfig.REVIVE_COST[this.servant.getServantSetTemplate().getTier()];
                if (this.servant.isImprint()) {
                    recoveryPrice = ServantConfig.REVIVE_PRICE_FOR_SERVANT_IMPRINT;
                    break;
                }
                break;
            }
        }
        this.decreaseItem(0, recoveryPrice, this.storageLocation);
        return super.canAct();
    }
}
