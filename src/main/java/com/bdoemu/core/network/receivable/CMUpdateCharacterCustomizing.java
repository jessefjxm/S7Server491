// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.receivable.utils.ReadCharacterInfoPacket;
import com.bdoemu.core.network.sendable.SMRefreshPcBasicCache;
import com.bdoemu.core.network.sendable.SMRefreshPcCustomizationCache;
import com.bdoemu.core.network.sendable.SMUpdateCharacterCustomizing;
import com.bdoemu.core.network.sendable.SMUpdateCharacterCustomizingNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.appearance.PlayerAppearanceStorage;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ChangeAppearanceForItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;

public class CMUpdateCharacterCustomizing extends ReadCharacterInfoPacket {
    private PlayerAppearanceStorage playerAppearance;
    private short type;
    private int slot;
    private EItemStorageLocation storageLocationType;

    public CMUpdateCharacterCustomizing(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.type = this.readC();
        this.slot = this.readC();
        this.storageLocationType = EItemStorageLocation.valueOf(this.readC());
        this.readD();
        this.playerAppearance = this.readPlayerAppearance(((GameClient) this.getClient()).getPlayer().getClassType());
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (this.type == 0) {
            if (!player.getPlayerBag().onEvent(new ChangeAppearanceForItemEvent(player, this.slot, this.storageLocationType))) {
                player.sendPacket(new SMUpdateCharacterCustomizingNak());
                return;
            }
        } else {
            if (this.type != 1) {
                return;
            }
            if (!player.getAccountData().getChargeUserStorage().isActiveChargeUserEffect(EChargeUserType.CustomizationPackage)) {
                player.sendPacket(new SMUpdateCharacterCustomizingNak());
                return;
            }
        }
        player.setPlayerAppearance(this.playerAppearance);
        player.sendPacket(new SMRefreshPcBasicCache(player));
        player.sendPacket(new SMRefreshPcCustomizationCache(player));
        player.sendPacket(new SMUpdateCharacterCustomizing());
    }
}
