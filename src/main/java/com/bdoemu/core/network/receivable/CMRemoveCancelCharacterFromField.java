// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMRemoveCancelCharacterFromField;
import com.bdoemu.core.network.sendable.SMRemoveCancelCharacterFromFieldNak;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBObject;

public class CMRemoveCancelCharacterFromField extends ReceivablePacket<GameClient> {
    private long objectId;

    public CMRemoveCancelCharacterFromField(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.objectId = this.readQ();
    }

    public void runImpl() {
        final BasicDBObject player = ((GameClient) this.getClient()).getLoginAccountInfo().getPlayer(this.objectId);
        if (player != null) {
            long deletionDate = player.getLong("deletionDate");
            if (deletionDate < 0L) {
                return;
            }
            if (deletionDate < GameTimeService.getServerTimeInSecond()) {
                ((GameClient) this.getClient()).sendPacket((SendablePacket) new SMRemoveCancelCharacterFromFieldNak(this.objectId, EStringTable.eErrNoDbInternalError));
                return;
            }
            deletionDate = -1L;
            player.put("deletionDate", (Object) deletionDate);
            PlayersDBCollection.getInstance().updateDeletionDate(this.objectId, deletionDate);
            ((GameClient) this.getClient()).sendPacket((SendablePacket) new SMRemoveCancelCharacterFromField(this.objectId));
        }
    }
}
