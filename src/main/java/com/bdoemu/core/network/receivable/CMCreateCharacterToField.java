package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.FilteringConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.receivable.utils.ReadCharacterInfoPacket;
import com.bdoemu.core.network.sendable.SMCreateCharacterToField;
import com.bdoemu.core.network.sendable.SMCreateCharacterToFieldNak;
import com.bdoemu.gameserver.dataholders.PCSetData;
import com.bdoemu.gameserver.dataholders.xml.ObsceneFilterData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.appearance.PlayerAppearanceStorage;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.model.creature.player.enums.EZodiacType;
import com.bdoemu.gameserver.model.creature.player.services.PlayerSaveService;
import com.bdoemu.gameserver.model.creature.player.templates.PCSetTemplate;

public class CMCreateCharacterToField extends ReadCharacterInfoPacket {
    private EZodiacType zodiac;
    private EClassType classType;
    private String name;
    private byte slot;
    private PlayerAppearanceStorage playerAppearance;

    public CMCreateCharacterToField(final short opcode) {
        super(opcode);
    }

    public void read() {
        this.classType = EClassType.valueOf(this.readC());
        this.slot = this.readC();
        this.name = this.readS(62);
        this.zodiac = EZodiacType.values()[this.readC()];
        this.playerAppearance = this.readPlayerAppearance(this.classType);
        readC();
    }

    public void runImpl() {
        if (this.classType == null || !this.classType.isEnabled() || this.playerAppearance == null) {
            return;
        }
        if (FilteringConfig.USERNAME_ENABLE) {
            if (ObsceneFilterData.getInstance().isObsceneWord(this.name)) {
                this.sendPacket(SMCreateCharacterToFieldNak.RESTRICTED_NAME);
                return;
            }
            if (FilteringConfig.RESTRICTED_USER_NAMES.contains(this.name.toLowerCase())) {
                this.sendPacket(SMCreateCharacterToFieldNak.RESTRICTED_NAME);
                return;
            }
        }
        if (this.name.length() < 2) {
            this.sendPacket(SMCreateCharacterToFieldNak.TOO_SHORT_NAME);
            return;
        }
        if (this.name.length() > 16) {
            this.sendPacket(SMCreateCharacterToFieldNak.TOO_LONG_NAME);
            return;
        }
        String regex = "[0-9]+";
        if (this.name.matches(regex)) {
            this.sendPacket(SMCreateCharacterToFieldNak.ONLY_NUMBERS_RESTRICTED);
            return;
        }
        regex = "(.)\\1\\1";
        if (this.name.matches(regex) || Character.isLowerCase(this.name.charAt(0))) {
            this.sendPacket(SMCreateCharacterToFieldNak.DONT_MATCH_PATTERN);
            return;
        }
        regex = "[a-zA-Z0-9]+";
        if (!this.name.matches(regex)) {
            this.sendPacket(SMCreateCharacterToFieldNak.DONT_MATCH_PATTERN);
            return;
        }
        final PCSetTemplate playerTemplate = PCSetData.getInstance().getTemplate(this.classType.getId());
        if (playerTemplate == null) {
            return;
        }
        final Player player = PlayerSaveService.getInstance().createPlayer(this.name, this.slot, this.zodiac, playerTemplate, this.playerAppearance, this.getClient());
        if (player != null) {
            this.sendPacket(new SMCreateCharacterToField(player));
        }
    }
}
