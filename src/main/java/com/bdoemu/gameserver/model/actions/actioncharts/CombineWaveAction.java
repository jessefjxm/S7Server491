package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.core.configs.BattleOptionConfig;
import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.team.party.PlayerParty;

public class CombineWaveAction extends ADefaultAction {
    private Player playerTarget;

    public CombineWaveAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void init() {
        final Player player = (Player) this.owner;
        final PlayerParty party = (PlayerParty) player.getParty();
        this.playerTarget = KnowList.getObject(this.owner, ECharKind.Player, this.targetGameObjId);
        final int currentAdrenalin = this.owner.getGameStats().getAdrenalin().getIntValue();
        if (party != null && this.playerTarget != null && party.isMember(this.playerTarget) && this.playerTarget.getLevel() >= BattleOptionConfig.ADRENALIN_SUPER_SKILL_MIN_LEVEL && this.owner.getGameStats().getAdrenalin().addAdrenalin(-currentAdrenalin, false)) {
            this.playerTarget.getGameStats().getAdrenalin().addAdrenalin(currentAdrenalin / 2, true);
        }
        super.init();
    }
}
