package com.bdoemu.gameserver.model.creature.player.titles.observers;

import com.bdoemu.gameserver.model.creature.player.titles.templates.TitleTemplate;
import com.mongodb.BasicDBObject;

public interface ITitleObserver {
    void load(final TitleTemplate p0);

    void load(final BasicDBObject p0, final TitleTemplate p1);

    boolean canReward(final Object... p0);

    TitleTemplate getTemplate();

    Object getKey();
}
