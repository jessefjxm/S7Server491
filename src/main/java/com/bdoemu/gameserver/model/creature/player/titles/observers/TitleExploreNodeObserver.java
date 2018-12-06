package com.bdoemu.gameserver.model.creature.player.titles.observers;

import com.mongodb.DBObject;

public class TitleExploreNodeObserver extends ATitleObserver {
    @Override
    public boolean canReward(final Object... params) {
        return (int) params[1] == this.template.getParameter2();
    }

    @Override
    public Object getKey() {
        return this.template.getParameter1();
    }

    public DBObject toDBObject() {
        return null;
    }
}
