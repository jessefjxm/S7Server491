// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.knowlist.KnowList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CMDoAction extends ReceivablePacket<GameClient> {
    private int size;
    private int subType;
    private int frameEventIndex;
    private float _x;
    private float _y;
    private float _z;
    private float x;
    private float y;
    private float z;
    private float cos;
    private float sin;
    private float x1;
    private float y1;
    private float z1;
    private float x2;
    private float y2;
    private float z2;
    private float x3;
    private float y3;
    private float z3;
    private float x4;
    private float y4;
    private float z4;
    private float x5;
    private float y5;
    private float z5;
    private float x6;
    private float y6;
    private float z6;
    private float x7;
    private float y7;
    private float z7;
    private float x8;
    private float y8;
    private float z8;
    private float x9;
    private float y9;
    private float z9;
    private byte type;
    private byte type2;
    private int npcGameObjId;
    private long staticObjectId;
    private long clientTimeMillis;
    private List<Creature> targets;
    private List<Creature> playerTargets;

    public CMDoAction(final short opcode) {
        super(opcode);
        this.targets = Collections.emptyList();
        this.playerTargets = Collections.emptyList();
    }

    protected void read() {
        this.size = this.readH();
        if (this.size > 0 && this.size < 10) {
            for (int i = 0; i < this.size; ++i) {
                this.subType = this.readC();
                this.frameEventIndex = this.readH();
                this.type = this.readC();
                this._x = this.readF();
                this._z = this.readF();
                this._y = this.readF();
                this.clientTimeMillis = this.readQ();
                this.x = this.readF();
                this.z = this.readF();
                this.y = this.readF();
                this.cos = this.readF();
                this.readD();
                this.sin = this.readF();
                this.readD();
                this.x1 = this.readF();
                this.z1 = this.readF();
                this.y1 = this.readF();
                this.x2 = this.readF();
                this.z2 = this.readF();
                this.y2 = this.readF();
                this.x3 = this.readF();
                this.z3 = this.readF();
                this.y3 = this.readF();
                this.x4 = this.readF();
                this.z4 = this.readF();
                this.y4 = this.readF();
                this.x5 = this.readF();
                this.z5 = this.readF();
                this.y5 = this.readF();
                this.x6 = this.readF();
                this.z6 = this.readF();
                this.y6 = this.readF();
                this.x7 = this.readF();
                this.z7 = this.readF();
                this.y7 = this.readF();
                this.x8 = this.readF();
                this.z8 = this.readF();
                this.y8 = this.readF();
                this.x9 = this.readF();
                this.z9 = this.readF();
                this.y9 = this.readF();
                this.readQ();
                this.readQ();
                this.readQ();
                this.readQ();
                this.readQ();
                this.readQ();
                this.npcGameObjId = this.readD();
                this.staticObjectId = this.readQ();
                this.type2 = this.readC();
                final int targetCount = this.readCD();
                final Player player = ((GameClient) this.getClient()).getPlayer();
                for (int _i = 0; _i < 10; ++_i) {
                    final int gameObjId = this.readD();
                    if (_i < targetCount) {
                        final Creature spawnTarget = KnowList.getObject(player, gameObjId);
                        if (spawnTarget != null) {
                            if (spawnTarget.isPlayer()) {
                                if (this.playerTargets.isEmpty()) {
                                    this.playerTargets = new ArrayList<Creature>();
                                }
                                this.playerTargets.add(spawnTarget);
                            } else {
                                if (this.targets.isEmpty()) {
                                    this.targets = new ArrayList<Creature>();
                                }
                                this.targets.add(spawnTarget);
                            }
                        }
                    }
                    this.readD();
                }
                if (player != null) {
                    player.getActionStorage().getAction().doAction(this.frameEventIndex, this.npcGameObjId, this.staticObjectId, this.targets, this.playerTargets);
                }
                this.targets = Collections.emptyList();
                this.playerTargets = Collections.emptyList();
            }
        }
        this.skipAll();
    }

    public void runImpl() {
    }
}
