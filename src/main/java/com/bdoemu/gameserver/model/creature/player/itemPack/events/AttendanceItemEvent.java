// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMAttendanceReward;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.events.AttendancePlayerEvent;
import com.bdoemu.gameserver.model.items.Item;

public class AttendanceItemEvent extends AItemEvent {
    private int eventId;
    private AttendancePlayerEvent attendancePlayerEvent;

    public AttendanceItemEvent(final Player player, final int eventId) {
        super(player, player, player, EStringTable.eErrNoItemTakeFromQuest, EStringTable.eErrNoItemTakeFromQuest, 0);
        this.eventId = eventId;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.attendancePlayerEvent.setAttendanceRewardedCount(this.attendancePlayerEvent.getAttendanceRewardedCount() + 1);
        this.player.sendPacket(new SMAttendanceReward(this.attendancePlayerEvent));
    }

    @Override
    public boolean canAct() {
        this.attendancePlayerEvent = this.player.getEventStorage().getAttendanceEvent(this.eventId);
        if (this.attendancePlayerEvent == null) {
            return false;
        }
        if (this.attendancePlayerEvent.getAttendanceRewardedCount() >= this.attendancePlayerEvent.getAttendanceRewardCount()) {
            return false;
        }
        final int[] reward = this.attendancePlayerEvent.getAttendanceT().getRewards()[this.attendancePlayerEvent.getAttendanceRewardedCount()];
        if (reward != null && reward[0] > 0 && reward[1] > 0) {
            this.addItem(new Item(reward[0], reward[1], 0));
        }
        return super.canAct();
    }
}
