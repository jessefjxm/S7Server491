// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.events;

import com.bdoemu.core.network.sendable.SMSetHouseLargeCraftExchange;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseLargeCraft;
import com.bdoemu.gameserver.model.houses.HouseStorage;

public class SetHouseLargeCraftExchangeEvent implements IHouseEvent {
    private Player player;
    private int houseId;
    private int recipeId;
    private HouseStorage houseStorage;

    public SetHouseLargeCraftExchangeEvent(final Player player, final int houseId, final int recipeId) {
        this.player = player;
        this.houseId = houseId;
        this.recipeId = recipeId;
        this.houseStorage = player.getHouseStorage();
    }

    @Override
    public void onEvent() {
        if (this.recipeId != 0) {
            final HouseLargeCraft largerCraft = new HouseLargeCraft(this.houseId, this.recipeId);
            this.houseStorage.getHouseLargeCrafts().put(this.houseId, largerCraft);
        }
        this.player.sendPacket(new SMSetHouseLargeCraftExchange(this.houseId, this.recipeId));
    }

    @Override
    public boolean canAct() {
        if (this.recipeId == 0) {
            if (this.houseStorage.removeHouseLargeCraft(this.houseId) == null) {
                return false;
            }
        } else if (this.houseStorage.getHouseLargeCrafts().containsKey(this.houseId)) {
            return false;
        }
        return true;
    }
}
