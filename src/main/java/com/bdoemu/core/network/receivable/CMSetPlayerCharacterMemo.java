// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMSetPlayerCharacterMemo;
import com.bdoemu.gameserver.model.creature.player.Memo;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMSetPlayerCharacterMemo extends ReceivablePacket<GameClient> {
    private String memoText;
    private int unk;
    private int group1;
    private int group2;
    private int questId1;
    private int questId2;
    private float x;
    private float y;
    private float z;
    private byte[] unkData;

    public CMSetPlayerCharacterMemo(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.unk = this.readD();
        this.x = this.readF();
        this.z = this.readF();
        this.y = this.readF();
        this.group1 = this.readHD();
        this.questId1 = this.readHD();
        this.group2 = this.readHD();
        this.questId2 = this.readHD();
        this.unkData = this.readB(17);
        this.memoText = this.readS(602);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Memo memo = new Memo(player.getObjectId(), this.memoText, this.unk, this.x, this.y, this.z, this.group1, this.group2, this.questId1, this.questId2, this.unkData);
            player.setMemo(memo);
            ((GameClient) this.getClient()).sendPacket((SendablePacket) new SMSetPlayerCharacterMemo(memo));
        }
    }
}
