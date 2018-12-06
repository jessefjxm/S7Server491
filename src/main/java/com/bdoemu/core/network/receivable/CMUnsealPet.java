// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.configs.LocalizingOptionConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;

public class CMUnsealPet extends ReceivablePacket<GameClient> {
    private long petObjId;

    public CMUnsealPet(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.petObjId = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && player.getServantController().getServants(EServantState.Field, EServantType.Pet).size() < LocalizingOptionConfig.PET_USE_COUNT) {
            final Servant pet = player.getServantController().getServant(this.petObjId);
            if (pet != null) {
                pet.unSeal(null, true, player);
            }
        }
    }
}
