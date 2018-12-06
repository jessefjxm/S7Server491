package com.bdoemu.gameserver.model.creature.player.titles.enums;

import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.titles.observers.*;

public enum ETitleType {
    killMonsterByGroupId(0, EObserveType.killMonsterGroup) {
        @Override
        ATitleObserver newInstance() {
            return new TitleKillMonsterGroupObserver();
        }
    },
    killMonsterByCreatureId(1, EObserveType.killMonster) {
        @Override
        ATitleObserver newInstance() {
            return new TitleKillMonsterObserver();
        }
    },
    knowledge(2, EObserveType.collectKnowledge) {
        @Override
        ATitleObserver newInstance() {
            return new TitleKnowledgeObserver();
        }
    },
    itemAcquire(3, EObserveType.acquireItem) {
        @Override
        ATitleObserver newInstance() {
            return new TitleItemAcquireObserver();
        }
    },
    enchantItem(4, EObserveType.enchantItem) {
        @Override
        ATitleObserver newInstance() {
            return new TitleItemEnchantObserver();
        }
    },
    explore(5, EObserveType.explore) {
        @Override
        ATitleObserver newInstance() {
            return new TitleExploreNodeObserver();
        }
    },
    lifeExp(6, EObserveType.lifeExp) {
        @Override
        ATitleObserver newInstance() {
            return new TitleLifeExpObserver();
        }
    },
    craftItem(7, EObserveType.craftItem) {
        @Override
        ATitleObserver newInstance() {
            return new TitleCraftItemObserver();
        }
    },
    special(8) {
        @Override
        ATitleObserver newInstance() {
            return null;
        }
    },
    levelUp(9, EObserveType.levelUp) {
        @Override
        ATitleObserver newInstance() {
            return new TitleLevelUpObserver();
        }
    },
    contribute(10, EObserveType.contribute) {
        @Override
        ATitleObserver newInstance() {
            return new TitleContributionObserver();
        }
    },
    questComplete(12, EObserveType.questComplete) {
        @Override
        ATitleObserver newInstance() {
            return new TitleQuestCompleteObserver();
        }
    },
    unk13(13) {
        @Override
        ATitleObserver newInstance() {
            return null;
        }
    },
    unk14(14) {
        @Override
        ATitleObserver newInstance() {
            return null;
        }
    },
    unk15(15) {
        @Override
        ATitleObserver newInstance() {
            return null;
        }
    },
    unk16(16) {
        @Override
        ATitleObserver newInstance() {
            return null;
        }
    };

    private byte id;
    private EObserveType observeType;

    ETitleType(final int id) {
        this.id = (byte) id;
    }

    ETitleType(final int id, final EObserveType observeType) {
        this.id = (byte) id;
        this.observeType = observeType;
    }

    public static ETitleType valueOf(final int id) {
        for (final ETitleType classType : values()) {
            if (classType.id == id) {
                return classType;
            }
        }
        throw new IllegalArgumentException("Invalid TitleType id: " + id);
    }

    public byte getId() {
        return this.id;
    }

    public EObserveType getObserveType() {
        return this.observeType;
    }

    abstract ATitleObserver newInstance();

    public ATitleObserver newTitleInstance() {
        return this.newInstance();
    }
}
