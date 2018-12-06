// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMCreateCharacterToFieldNak extends SendablePacket<GameClient> {
    public static final SMCreateCharacterToFieldNak RESTRICTED_NAME;
    public static final SMCreateCharacterToFieldNak TOO_SHORT_NAME;
    public static final SMCreateCharacterToFieldNak TOO_LONG_NAME;
    public static final SMCreateCharacterToFieldNak ONLY_NUMBERS_RESTRICTED;
    public static final SMCreateCharacterToFieldNak DONT_MATCH_PATTERN;
    public static final SMCreateCharacterToFieldNak NAME_ALREADY_EXIST;

    static {
        RESTRICTED_NAME = new SMCreateCharacterToFieldNak(EStringTable.eErrNoCharacterNameHaveSwearWord);
        TOO_SHORT_NAME = new SMCreateCharacterToFieldNak(EStringTable.eErrNoCharacterNameLengthIsTooShort);
        TOO_LONG_NAME = new SMCreateCharacterToFieldNak(EStringTable.eErrNoNickNameLengthIsTooLong);
        ONLY_NUMBERS_RESTRICTED = new SMCreateCharacterToFieldNak(EStringTable.eErrNoCharacterNameDontHaveNumberOnly);
        DONT_MATCH_PATTERN = new SMCreateCharacterToFieldNak(EStringTable.eErrNoNameCharacterIsInvalid);
        NAME_ALREADY_EXIST = new SMCreateCharacterToFieldNak(EStringTable.eErrNoNameCharacterIsBusy);
    }

    private final EStringTable stringTableMessage;

    private SMCreateCharacterToFieldNak(final EStringTable stringTableMessage) {
        this.stringTableMessage = stringTableMessage;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.stringTableMessage.getHash());
    }
}
