package com.bdoemu.gameserver.model.creature.player.titles.observers;

import com.bdoemu.gameserver.model.creature.player.titles.templates.TitleTemplate;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class TitleKillMonsterObserver extends ATitleObserver {
    private int killedMonsterCount;

    @Override
    public void load(final BasicDBObject dbObject, final TitleTemplate template) {
        super.load(template);
        this.killedMonsterCount = dbObject.getInt("killedMonsterCount");
    }

    @Override
    public boolean canReward(final Object... params) {
        ++this.killedMonsterCount;
        return (int) params[0] == this.template.getParameter1() && this.killedMonsterCount == this.template.getParameter2();
    }

    @Override
    public Object getKey() {
        return this.template.getParameter1();
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("titleId", this.template.getTitleId());
        builder.append("killedMonsterCount", this.killedMonsterCount);
        return builder.get();
    }
}
