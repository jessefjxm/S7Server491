package com.bdoemu.gameserver.model.functions;

import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PushItemFunction implements IFunctionHandler {
    private int itemId;
    private int enchantLevel;
    private int wp;
    private long count;

    @Override
    public void load(final int dialogIndex, final String functionData) {
        String pattern = "(.*)\\,(.*)\\,(.*)";
        final boolean hasWp = functionData.contains("wp");
        if (hasWp) {
            pattern += "(\\,|\\.)(.*)";
        }
        final Pattern r = Pattern.compile(pattern);
        final Matcher m = r.matcher(functionData);
        if (m.find()) {
            this.itemId = Integer.parseInt(m.group(1).toLowerCase());
            this.enchantLevel = Integer.parseInt(m.group(2).toLowerCase());
            this.count = Long.parseLong(m.group(3).toLowerCase());
            if (hasWp) {
                this.wp = Integer.parseInt(m.group(5).toLowerCase().replace("wp", "").trim());
            }
            return;
        }
        throw new IllegalArgumentException("PushItemFunction not found matcher: " + functionData);
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return ItemData.getInstance().getItemTemplate(this.itemId) == null;
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        if (this.wp > 0 && !player.addWp(-this.wp)) {
            return;
        }
        player.getPlayerBag().onEvent(new AddItemEvent(player, this.itemId, this.enchantLevel, this.count));
    }
}
