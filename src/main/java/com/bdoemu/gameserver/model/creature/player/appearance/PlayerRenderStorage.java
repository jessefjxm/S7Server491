package com.bdoemu.gameserver.model.creature.player.appearance;

import com.bdoemu.gameserver.model.creature.player.enums.ERenderType;

public class PlayerRenderStorage {
    private int defaultBits;

    public PlayerRenderStorage() {
        this.setRender(ERenderType.HELM, true);
        this.setRender(ERenderType.CLOAK, true);
    }

    public void setRender(final ERenderType renderType, final boolean isRender) {
        if (isRender) {
            this.defaultBits |= renderType.getMask();
        } else {
            this.defaultBits &= ~renderType.getMask();
        }
    }

    public boolean isRender(final ERenderType renderType) {
        return (this.defaultBits & renderType.getMask()) == renderType.getMask();
    }

    public int getRenderBits() {
        return this.defaultBits;
    }
}
