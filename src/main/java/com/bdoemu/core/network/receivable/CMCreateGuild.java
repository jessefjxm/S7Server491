// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.dataholders.xml.ObsceneFilterData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildType;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.service.GameTimeService;

public class CMCreateGuild extends ReceivablePacket<GameClient> {
    private String name;
    private long money;
    private EGuildType guildType;

    public CMCreateGuild(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.name = this.readS(62);
        this.guildType = EGuildType.values()[this.readC()];
        this.money = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            if (ObsceneFilterData.getInstance().isObsceneWord(this.name)) {
                this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoGuildNameHaveSwearWord, this.opCode));
                return;
            }
            if (this.name.contains(" ")) {
                player.sendPacket(new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
                return;
            }
            if (player.getAccountData().getGuildCoolTime() > GameTimeService.getServerTimeInMillis()) {
                this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoGuildCreatableTimeDoNotGoes, this.opCode));
                return;
            }
            if (this.name.length() < 2) {
                this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoGuildNameLengthIsTooShort, this.opCode));
                return;
            }
            if (this.name.length() > 16) {
                this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoGuildNameLengthIsTooLong, this.opCode));
                return;
            }
            final String regex = "[0-9]+";
            if (this.name.matches(regex)) {
                this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoGuildNameDontHaveNumberOnly, this.opCode));
                return;
            }
            GuildService.getInstance().createGuild(player, this.guildType, this.name);
        }
    }
}
