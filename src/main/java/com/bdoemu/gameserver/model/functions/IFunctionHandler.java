package com.bdoemu.gameserver.model.functions;

import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public interface IFunctionHandler {
    void load(final int p0, final String p1);

    void doFunction(final Player p0, final Npc p1, final long p2, final CreatureTemplate p3, final int p4);

    boolean isDisabledByContentsGroup();
}
