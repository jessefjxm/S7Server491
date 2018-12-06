package com.bdoemu.gameserver.model.functions;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMDetectCharacter;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMOpenDetectPlayer;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public class DetectPlayerFunction implements IFunctionHandler {
    private int wp;

    @Override
    public void load(final int dialogIndex, final String functionData) {
        this.wp = Integer.parseInt(functionData.toLowerCase().replace("wp", "").trim());
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return false;
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        if (player.addWp(-this.wp)) {
            player.sendPacket(new SMOpenDetectPlayer());
        } else {
            player.sendPacket(new SMNak(EStringTable.eErrNoMentalNotEnoughWp, CMDetectCharacter.class));
        }
    }
}
