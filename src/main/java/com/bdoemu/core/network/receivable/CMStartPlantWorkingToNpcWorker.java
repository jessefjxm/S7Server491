package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.utils.BuffReader;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkingType;
import com.bdoemu.gameserver.model.creature.npc.worker.events.StartPlantWorkingToNpcWorkerEvent;
import com.bdoemu.gameserver.model.creature.npc.worker.works.ANpcWork;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMStartPlantWorkingToNpcWorker extends ReceivablePacket<GameClient> {
    private long objectId;
    private int count;
    private ENpcWorkingType npcWorkingType;
    private ANpcWork npcWork;

    public CMStartPlantWorkingToNpcWorker(final short opcode) {
        super(opcode);
    }

    protected void read() {
        objectId = readQ();
        readHD(); // Character Key
        readD(); // WaypointKey
        readD(); // Worker Level
        readD(); // unk
        readD(); // unk
        readD(); // unk
        readHD(); // skill 1
        readHD(); // skill 2
        readHD(); // skill 3
        readHD(); // skill 4
        readHD(); // skill 5
        readHD(); // skill 6
        readHD(); // skill 7
        this.npcWorkingType = ENpcWorkingType.values()[this.readCD()];
        this.count = this.readHD();
        if (this.npcWorkingType != null) {
            (this.npcWork = this.npcWorkingType.newNpcWorkInstance()).read(BuffReader.init(this.getBuffer()));
        } else {
            this.skipAll();
        }
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null && this.npcWorkingType != null) {
            player.getNpcWorkerController().onEvent(new StartPlantWorkingToNpcWorkerEvent(player, this.objectId, this.count, this.npcWork));
        }
    }
}
