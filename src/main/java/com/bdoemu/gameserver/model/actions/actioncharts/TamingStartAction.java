package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.commons.utils.HashUtil;
import com.bdoemu.core.network.sendable.SMTurnNonPlayer;
import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.utils.MathUtils;

public class TamingStartAction extends ADefaultAction {
    public TamingStartAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void init() {
        super.init();
        final Player player = (Player) this.getOwner();
        final Servant vehicle = KnowList.getObject(this.getOwner(), ECharKind.Vehicle, this.targetGameObjId);
        if (this.targetGameObjId < 0 || vehicle == null || player.getServantController().getTameServant() != null) {
            player.getAi().HandleFailTaming(vehicle, null);
            return;
        }
        if (MathUtils.getDistance(vehicle.getLocation(), this.getOwner().getLocation()) > 1500.0 || (vehicle.getAi().getState() != HashUtil.generateHashA("Wait_wild".toLowerCase()) && vehicle.getAi().getState() != HashUtil.generateHashA("Move_Random".toLowerCase()))) {
            player.getAi().HandleFailTaming(vehicle, null);
            return;
        }
        if (vehicle.getOwner() == null) {
            player.getServantController().setTameServant(vehicle);
            vehicle.getMovementController().cancelMoveTask();
            vehicle.getAggroList().addCreature(this.getOwner());
            final Location actorLoc = vehicle.getLocation();
            final Location destination = this.getOwner().getLocation();
            if (destination != null) {
                final double distance = MathUtils.getDistance(actorLoc, destination);
                final double angle = Math.atan2((destination.getY() - actorLoc.getY()) / distance, (destination.getX() - actorLoc.getX()) / distance);
                final double cos = Math.cos(angle);
                final double sin = Math.sin(angle);
                if (actorLoc.getCos() != cos || actorLoc.getSin() != sin) {
                    actorLoc.setLocation(cos, sin);
                    vehicle.sendBroadcastPacket(new SMTurnNonPlayer(vehicle));
                }
            }
            vehicle.getAi().HandleTryTaming(this.getOwner(), null);
        }
    }
}
