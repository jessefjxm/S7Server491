package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.core.network.sendable.SMBreathGaugeStateChange;
import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.enums.ENaviType;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.worldInstance.World;

public class MoveAction extends ADefaultAction {
    private boolean isUnderWater;

    public MoveAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
        this.isUnderWater = false;
    }

    @Override
    public void init() {
        super.init();
        if (this.owner.isPlayer()) {
            final Player player = (Player) this.owner;
            final Location loc = this.owner.getLocation();
            if (loc.getGameSector() != null && World.getInstance().getWorldMap().updateLocation(this.owner, this.newX, this.newY)) {
                loc.setXYZ(this.newX, this.newY, this.newZ);
                final Servant servant = player.getCurrentVehicle();
                if (servant != null) {
                    if (servant.getOwner() == player) {
                        if (!servant.getTemplate().isFixed() && World.getInstance().getWorldMap().updateLocation(servant, this.newX, this.newY)) {
                            servant.getLocation().setLocation(this.newX, this.newY, this.newZ, this.cos, this.sin);
                        }
                    } else {
                        servant.unMount(player);
                    }
                }
            }
            if (ENaviType.hasAir(this.getActionChartActionT().getNavigationTypes())) {
                final double _fallDistance = player.getOz() - this.newZ;
                if (_fallDistance > 0.0) {
                    player.addFallDistance(_fallDistance);
                }
            }
            player.setOz(this.newZ);
            if (!this.isUnderWater) {
                if (this.actionChartActionT.isSwimmingAction() && this.actionChartActionT.getAttachTerrainType().isUnderWater()) {
                    this.isUnderWater = true;
                    player.sendBroadcastItSelfPacket(new SMBreathGaugeStateChange(player.getGameObjectId(), 1));
                }
            } else if (!this.actionChartActionT.getAttachTerrainType().isUnderWater()) {
                this.isUnderWater = false;
                player.sendBroadcastItSelfPacket(new SMBreathGaugeStateChange(player.getGameObjectId(), 0));
            }
        }
    }
}
