package com.bdoemu.gameserver.model.creature.player.answer;

import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;

public class SkillAnswer {
    private ActiveBuff activeBuff;

    public SkillAnswer(final ActiveBuff activeBuff) {
        this.activeBuff = activeBuff;
    }

    public ActiveBuff getActiveBuff() {
        return this.activeBuff;
    }
}
