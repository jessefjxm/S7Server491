package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ClientState;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMEnterPlayerCharacterToField extends ReceivablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(CMEnterPlayerCharacterToField.class);
    }

    private long characterId;
    private long clientTimeMillis;

    public CMEnterPlayerCharacterToField(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.characterId = this.readQ();
        this.clientTimeMillis = this.readQ();
    }

    public void runImpl() {
        final Player player = PlayersDBCollection.getInstance().load(this.characterId, this.getClient());
        if (player == null || player.getDeletionDate() > 0L) {
            return;
        }
        if (this.getClient().compareAndSetState(ClientState.AUTHED, ClientState.ENTERED)) {
            CMEnterPlayerCharacterToField.log.debug("Player {} trying to enter world!", player.getName());
            player.setClient(this.getClient());
            this.getClient().setPlayer(player);
            player.onLogin();
        }
    }
}
