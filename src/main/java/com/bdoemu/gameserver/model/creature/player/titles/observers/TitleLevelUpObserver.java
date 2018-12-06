package com.bdoemu.gameserver.model.creature.player.titles.observers;

public class TitleLevelUpObserver extends ATitleObserver {
    @Override
    public boolean canReward(final Object... params) {
        return (int) params[0] == this.template.getParameter1();
    }

    @Override
    public Object getKey() {
        return this.template.getParameter1();
    }
}
