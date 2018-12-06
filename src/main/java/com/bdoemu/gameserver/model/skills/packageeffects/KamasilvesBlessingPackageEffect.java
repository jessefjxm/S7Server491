package com.bdoemu.gameserver.model.skills.packageeffects;

import com.bdoemu.core.network.sendable.SMSetCharacterStatPoint;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;
import com.bdoemu.gameserver.model.stats.elements.BuffElement;

public class KamasilvesBlessingPackageEffect extends AChargeUserEffect {
    private BuffElement wp;
    private BuffElement dropRate;

    public KamasilvesBlessingPackageEffect(final long effectEndTime, final EChargeUserType chargeUserType) {
        super(effectEndTime, chargeUserType);
    }

    @Override
    public void initEffect(final Player owner) {
        this.dropRate = new BuffElement(200000.0f);
        this.wp = new BuffElement(2.0f);
        //owner.getGameStats().getDropItemLuck().addRateElement(this.dropRate);
        owner.getGameStats().getDropItemLuck().addElement(dropRate);
        owner.getGameStats().getWPRegenStat().addElement(this.wp);
        owner.sendBroadcastItSelfPacket(new SMSetCharacterStatPoint(owner));
    }

    @Override
    public void endEffect(final Player owner) {
        //owner.getGameStats().getDropItemLuck().removeRateElement(this.dropRate);
        owner.getGameStats().getDropItemLuck().removeElement(this.dropRate);
        owner.getGameStats().getWPRegenStat().removeElement(this.wp);
        owner.sendBroadcastItSelfPacket(new SMSetCharacterStatPoint(owner));
    }
}
