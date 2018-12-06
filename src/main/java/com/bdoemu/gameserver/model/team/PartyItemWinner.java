package com.bdoemu.gameserver.model.team;

import com.bdoemu.gameserver.service.FamilyService;

public class PartyItemWinner {
    private final long accountId;
    private int dice;

    public PartyItemWinner(final long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public String getFamily() {
        return FamilyService.getInstance().getFamily(this.accountId);
    }

    public int getDice() {
        return this.dice;
    }

    public void setDice(final int dice) {
        this.dice = dice;
    }
}
