package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.core.network.sendable.SMMovePlayer;
import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.world.Location;

public class RideOnDoAction extends ADefaultAction {
    private Servant servant;

    public RideOnDoAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void init() {
        final Player player = (Player) this.owner;
        final Location loc = player.getLocation();
        player.sendBroadcastItSelfPacket(new SMMovePlayer(player, this.newX, this.newY, this.newZ, this.cos, this.sin, this.getCarrierX(), this.getCarrierY(), this.getCarrierZ(), this.getCarrierGameObjId(), loc.getVehicleX(), loc.getVehicleY(), loc.getVehicleZ()));
        super.init();
    }

    @Override
    public boolean canAct() {
        final Player player = (Player) this.owner;
        this.servant = player.getCurrentVehicle();
        return this.servant != null && super.canAct() && this.servant.getGameObjectId() == this.targetGameObjId;
    }
}
