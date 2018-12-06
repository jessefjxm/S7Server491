package com.bdoemu.gameserver.model.creature.player.titles.observers;

public class TitleItemEnchantObserver extends ATitleObserver {
    @Override
    public boolean canReward(final Object... params) {
        return (int) params[1] <= this.template.getParameter1() && (int) params[1] == this.template.getParameter2() && (int) params[0] == this.template.getParameter1();
    }

    @Override
    public Object getKey() {
        return this.template.getParameter1();
    }
}
