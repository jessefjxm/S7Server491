// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMQuestSearchResult;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMQuestSearchResult extends ReceivablePacket<GameClient> {
    public CMQuestSearchResult(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final int searchCharacterId = player.getPlayerQuestHandler().getSearchQuestCharacterId();
            if (searchCharacterId > 0) {
                player.getPlayerQuestHandler().setSearchQuestCharacterId(0);
                player.getObserveController().notifyObserver(EObserveType.detection, searchCharacterId);
                player.sendPacket(new SMQuestSearchResult(searchCharacterId));
            }
        }
    }
}
