// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.MainServer;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.FilteringConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMCreateCharacterToFieldNak;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.dataholders.xml.ObsceneFilterData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ChangeNameItemEvent;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EChangeNameType;
import com.bdoemu.gameserver.service.GameTimeService;

public class CMChangeName extends ReceivablePacket<GameClient> {
    private EItemStorageLocation storageLocation;
    private int slotIndex;
    private int type;
    private String newName;

    public CMChangeName(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.type = this.readC();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.newName = this.readS(62);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            if (!this.storageLocation.isPlayerInventories()) {
                return;
            }
            final ItemPack itemPack = player.getPlayerBag().getItemPack(this.storageLocation);
            if (itemPack == null) {
                return;
            }
            final Item item = itemPack.getItem(this.slotIndex);
            if (item == null) {
                return;
            }
            final EContentsEventType contentsEventType = item.getTemplate().getContentsEventType();
            if (contentsEventType == null || !contentsEventType.isChangeNickName()) {
                return;
            }
            final EChangeNameType changeNameType = EChangeNameType.values()[item.getTemplate().getContentsEventParam1()];
            switch (changeNameType) {
                case FamilyName: {
                    if (this.newName.length() < 3 || this.newName.length() > 16) {
                        this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
                        return;
                    }
                    String regex = "[0-9]+";
                    if (this.newName.matches(regex)) {
                        this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
                        return;
                    }
                    regex = "(.)\\1\\1";
                    if (this.newName.matches(regex) || Character.isLowerCase(this.newName.charAt(0))) {
                        this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
                        return;
                    }
                    regex = "[a-zA-Z0-9]+";
                    if (!this.newName.matches(regex)) {
                        this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
                        return;
                    }
                    break;
                }
                case PlayerName: {
                    if (FilteringConfig.USERNAME_ENABLE) {
                        if (ObsceneFilterData.getInstance().isObsceneWord(this.newName)) {
                            this.sendPacket((SendablePacket) SMCreateCharacterToFieldNak.RESTRICTED_NAME);
                            return;
                        }
                        if (FilteringConfig.RESTRICTED_USER_NAMES.contains(this.newName.toLowerCase())) {
                            this.sendPacket((SendablePacket) SMCreateCharacterToFieldNak.RESTRICTED_NAME);
                            return;
                        }
                    }
                    if (this.newName.length() < 3) {
                        this.sendPacket((SendablePacket) SMCreateCharacterToFieldNak.TOO_SHORT_NAME);
                        return;
                    }
                    if (this.newName.length() > 16) {
                        this.sendPacket((SendablePacket) SMCreateCharacterToFieldNak.TOO_LONG_NAME);
                        return;
                    }
                    String regex = "[0-9]+";
                    if (this.newName.matches(regex)) {
                        this.sendPacket((SendablePacket) SMCreateCharacterToFieldNak.ONLY_NUMBERS_RESTRICTED);
                        return;
                    }
                    regex = "(.)\\1\\1";
                    if (this.newName.matches(regex) || Character.isLowerCase(this.newName.charAt(0))) {
                        this.sendPacket((SendablePacket) SMCreateCharacterToFieldNak.DONT_MATCH_PATTERN);
                        return;
                    }
                    regex = "[a-zA-Z0-9]+";
                    if (!this.newName.matches(regex)) {
                        this.sendPacket((SendablePacket) SMCreateCharacterToFieldNak.DONT_MATCH_PATTERN);
                        return;
                    }
                    break;
                }
                case GuildName: {
                    if (ObsceneFilterData.getInstance().isObsceneWord(this.newName)) {
                        this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoGuildNameHaveSwearWord, this.opCode));
                        return;
                    }
                    if (this.newName.contains(" ")) {
                        player.sendPacket(new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
                        return;
                    }
                    if (player.getAccountData().getGuildCoolTime() > GameTimeService.getServerTimeInMillis()) {
                        this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoGuildCreatableTimeDoNotGoes, this.opCode));
                        return;
                    }
                    if (this.newName.length() < 2) {
                        this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoGuildNameLengthIsTooShort, this.opCode));
                        return;
                    }
                    if (this.newName.length() > 16) {
                        this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoGuildNameLengthIsTooLong, this.opCode));
                        return;
                    }
                    final String regex = "[0-9]+";
                    if (this.newName.matches(regex)) {
                        this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoGuildNameDontHaveNumberOnly, this.opCode));
                        return;
                    }
                    break;
                }
            }
            if (changeNameType.isFamilyName()) {
                final boolean result = MainServer.getRmi().changeFamilyName(player.getAccountId(), this.newName);
                if (!result) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoNameCharacterIsBusy, this.opCode));
                    return;
                }
            }
            player.getPlayerBag().onEvent(new ChangeNameItemEvent(player, this.storageLocation, this.slotIndex, this.newName, changeNameType));
        }
    }
}
