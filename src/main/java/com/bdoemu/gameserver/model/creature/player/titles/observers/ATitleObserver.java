package com.bdoemu.gameserver.model.creature.player.titles.observers;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.player.titles.templates.TitleTemplate;
import com.mongodb.BasicDBObject;

public abstract class ATitleObserver extends JSONable implements ITitleObserver {
    protected TitleTemplate template;

    public void load(final TitleTemplate template) {
        this.template = template;
    }

    public void load(final BasicDBObject dbObject, final TitleTemplate template) {
        this.template = template;
    }

    public TitleTemplate getTemplate() {
        return this.template;
    }

    public abstract boolean canReward(final Object... p0);

    public abstract Object getKey();
}
