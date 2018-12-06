package com.bdoemu.gameserver.model.creature.observers.enums;

import com.bdoemu.gameserver.model.creature.observers.map.IntegerKeyObserveMap;
import com.bdoemu.gameserver.model.creature.observers.map.NoKeyObserveMap;
import com.bdoemu.gameserver.model.creature.observers.map.ObserveMap;

public enum EObserveType {
    explore {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    meet {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    useItem {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    acquireItem {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    enchantItem {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    learnSkill {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    killMonster {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    killMonsterGroup {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    killPlayer {
        @Override
        ObserveMap newInstance() {
            return new NoKeyObserveMap();
        }
    },
    collectKnowledge {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    exchangeItem {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    clearMiniGame {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    levelUp {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    lifeExp {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    contribute {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    craftItem {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    trade {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    acquirePoint {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    gatherItem {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    detection {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    eavesdrop {
        @Override
        ObserveMap newInstance() {
            return new NoKeyObserveMap();
        }
    },
    questComplete {
        @Override
        ObserveMap newInstance() {
            return new NoKeyObserveMap();
        }
    },
    tamingServant {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    },
    npcworkermaking {
        @Override
        ObserveMap newInstance() {
            return new IntegerKeyObserveMap();
        }
    };

    abstract ObserveMap newInstance();

    public ObserveMap newMapObserveInstance() {
        return this.newInstance();
    }
}
