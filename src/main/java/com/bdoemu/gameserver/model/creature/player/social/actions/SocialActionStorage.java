package com.bdoemu.gameserver.model.creature.player.social.actions;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMLoadSocialActionConfig;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SocialActionStorage extends JSONable {
    private final HashMap<Integer, SocialActionConfig> socialConfigs;
    private final Player player;

    public SocialActionStorage(final Player player, final BasicDBObject basicDBObject) {
        this.socialConfigs = new HashMap<>();
        this.player = player;
        final BasicDBList socialConfigDBList = (BasicDBList) basicDBObject.get("socialConfigs");
        for (final Object aSocialConfigDBList : socialConfigDBList) {
            final SocialActionConfig socialActionConfig = new SocialActionConfig((BasicDBObject) aSocialConfigDBList);
            this.socialConfigs.put(socialActionConfig.getActionIndex(), socialActionConfig);
        }
    }

    public SocialActionStorage(final Player player) {
        this.socialConfigs = new HashMap<>();
        this.player = player;
    }

    public void update(final List<SocialActionConfig> socialConfigList) {
        for (final SocialActionConfig socialConfig : socialConfigList) {
            this.socialConfigs.put(socialConfig.getActionIndex(), socialConfig);
        }
    }

    public void onLogin() {
        if (!this.socialConfigs.isEmpty()) {
            this.player.sendPacket(new SMLoadSocialActionConfig(this.getSocialConfigs()));
        }
    }

    public Collection<SocialActionConfig> getSocialConfigs() {
        return this.socialConfigs.values();
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList socialConfigDBList = new BasicDBList();
        for (final SocialActionConfig actionConfig : this.socialConfigs.values()) {
            socialConfigDBList.add(actionConfig.toDBObject());
        }
        builder.append("socialConfigs", socialConfigDBList);
        return builder.get();
    }
}
