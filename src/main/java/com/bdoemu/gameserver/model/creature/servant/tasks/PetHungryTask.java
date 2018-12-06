package com.bdoemu.gameserver.model.creature.servant.tasks;

import com.bdoemu.core.network.sendable.SMUpdatePetHungry;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class PetHungryTask implements Runnable {
    private Servant servant;

    public PetHungryTask(final Servant servant) {
        this.servant = servant;
    }

    @Override
    public void run() {
        this.servant.setHunger(Math.max(this.servant.getHunger() - 2, 0));
        this.servant.getOwner().sendPacket(new SMUpdatePetHungry());
    }
}
