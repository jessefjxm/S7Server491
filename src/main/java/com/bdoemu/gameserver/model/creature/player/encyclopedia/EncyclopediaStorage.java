package com.bdoemu.gameserver.model.creature.player.encyclopedia;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMListEncyclopedia;
import com.bdoemu.core.network.sendable.SMUpdateEncyclopedia;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.encyclopedia.enums.EEncyclopediaType;
import com.bdoemu.gameserver.model.creature.player.encyclopedia.templates.EncyclopediaT;
import com.bdoemu.gameserver.model.creature.player.fishing.services.FishingService;
import com.bdoemu.gameserver.utils.DiceUtils;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.HashMap;

public class EncyclopediaStorage extends JSONable {
    private final HashMap<Integer, Encyclopedia> encyclopedies;
    private final Player player;

    public EncyclopediaStorage(final Player player) {
        this.encyclopedies = new HashMap<>();
        this.player = player;
    }

    public EncyclopediaStorage(final Player player, final BasicDBObject basicDBObject) {
        this.encyclopedies = new HashMap<>();
        this.player = player;
        final BasicDBList basicDBList = (BasicDBList) basicDBObject.get("encyclopedies");
        for (final Object aBasicDBList : basicDBList) {
            final Encyclopedia encyclopedia = new Encyclopedia((BasicDBObject) aBasicDBList);
            this.encyclopedies.put(encyclopedia.getKey(), encyclopedia);
        }
    }

    public synchronized void updateEncyclopedia(final Player player, final EncyclopediaT template) {
        final int rand = Rnd.get(1000000);
        int size = template.getBaseSize();
        int varyIndex = -1;
        if (rand <= 10000) {
            varyIndex = 4;
        } else if (rand <= 50000) {
            varyIndex = 3;
        } else if (rand <= 100000) {
            varyIndex = 2;
        } else if (rand <= 300000) {
            varyIndex = 1;
        } else if (rand <= 500000) {
            varyIndex = 0;
        }
        if (varyIndex >= 0) {
            final DiceUtils.DiceValue vary = template.getVarySize()[varyIndex];
            size += Rnd.get(vary.getMinValue(), vary.getMaxModValue());
        }
        if (!this.encyclopedies.containsKey(template.getKey())) {
            this.encyclopedies.put(template.getKey(), new Encyclopedia(template, size));
        }
        final Encyclopedia encyclopedia = this.encyclopedies.get(template.getKey());
        encyclopedia.setCount(encyclopedia.getCount() + 1);
        encyclopedia.setMaxItemSize(Math.max(encyclopedia.getMaxItemSize(), size));
        player.sendPacket(new SMUpdateEncyclopedia(encyclopedia, size));
        if (template.getEncyclopediaType() == EEncyclopediaType.Fishing) {
            FishingService.getInstance().updateToTop(player, template.getKey(), size);
        }
    }

    public boolean hasEncyclopedia(final int key) {
        return this.encyclopedies.containsKey(key);
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList basicDBList = new BasicDBList();
        for (final Encyclopedia encyclopedia : this.encyclopedies.values()) {
            basicDBList.add(encyclopedia.toDBObject());
        }
        builder.append("encyclopedies", basicDBList);
        return builder.get();
    }

    public void onLogin() {
        this.player.sendPacket(new SMListEncyclopedia(this.encyclopedies.values()));
    }
}
