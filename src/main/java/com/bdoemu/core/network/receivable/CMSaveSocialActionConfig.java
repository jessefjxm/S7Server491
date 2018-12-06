// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.dataholders.SocialActionData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.actions.SocialActionConfig;

import java.util.ArrayList;
import java.util.List;

public class CMSaveSocialActionConfig extends ReceivablePacket<GameClient> {
    private List<SocialActionConfig> sosialConfigs;

    public CMSaveSocialActionConfig(final short opcode) {
        super(opcode);
    }

    protected void read() {
        final int size = this.readH();
        if (size > 0 && size <= SocialActionData.getInstance().size()) {
            this.sosialConfigs = new ArrayList<SocialActionConfig>();
            for (int i = 0; i < size; ++i) {
                this.sosialConfigs.add(new SocialActionConfig(this.readH(), this.readC(), this.readS(202)));
            }
        }
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getSocialActionStorage().update(this.sosialConfigs);
            this.sosialConfigs = null;
        }
    }
}
