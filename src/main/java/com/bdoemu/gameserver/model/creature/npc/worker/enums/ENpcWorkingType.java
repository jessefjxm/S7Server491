package com.bdoemu.gameserver.model.creature.npc.worker.enums;

import com.bdoemu.gameserver.model.creature.npc.worker.works.*;

public enum ENpcWorkingType {
    PlantZone {
        @Override
        ANpcWork newInstance() {
            return new PlantZoneWork(this);
        }
    },
    Upgrade {
        @Override
        ANpcWork newInstance() {
            return new UpgradeWork(this);
        }
    },
    PlantRentHouse {
        @Override
        ANpcWork newInstance() {
            return new PlantRentHouseWork(this);
        }
    },
    ChangeTown {
        @Override
        ANpcWork newInstance() {
            return new ChangeTownWork(this);
        }
    },
    PlantBuliding {
        @Override
        ANpcWork newInstance() {
            return new PlantBulidingWork(this);
        }
    },
    RegionManaging {
        @Override
        ANpcWork newInstance() {
            return new RegionManagingWork(this);
        }
    },
    PlantRentHouseLargeCraft {
        @Override
        ANpcWork newInstance() {
            return new PlantRentHouseLargeCraftWork(this);
        }
    },
    HouseParty {
        @Override
        ANpcWork newInstance() {
            return new HousePartyWork(this);
        }
    },
    GuildHouseLargeCraft {
        @Override
        ANpcWork newInstance() {
            return new GuildHouseLargeCraftWork(this);
        }
    },
    HarvestWorking {
        @Override
        ANpcWork newInstance() {
            return new HarvestWorkingWork(this);
        }
    };

    public int getId() {
        return this.ordinal();
    }

    abstract ANpcWork newInstance();

    public ANpcWork newNpcWorkInstance() {
        return this.newInstance();
    }
}
