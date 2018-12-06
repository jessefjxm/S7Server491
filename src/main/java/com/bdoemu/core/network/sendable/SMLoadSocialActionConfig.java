// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.social.actions.SocialActionConfig;

import java.util.Collection;

public class SMLoadSocialActionConfig extends SendablePacket<GameClient> {
    private Collection<SocialActionConfig> socialActionConfigs;

    public SMLoadSocialActionConfig(final Collection<SocialActionConfig> socialActionConfigs) {
        this.socialActionConfigs = socialActionConfigs;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.socialActionConfigs.size());
        for (final SocialActionConfig socialActionConfig : this.socialActionConfigs) {
            buffer.writeH(socialActionConfig.getActionIndex());
            buffer.writeC((int) socialActionConfig.getType());
            buffer.writeS((CharSequence) socialActionConfig.getChattingKeyword(), 202);
        }
    }
}
