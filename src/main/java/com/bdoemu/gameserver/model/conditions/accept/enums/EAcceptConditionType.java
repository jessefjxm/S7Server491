package com.bdoemu.gameserver.model.conditions.accept.enums;

import com.bdoemu.gameserver.model.conditions.accept.*;

import java.util.HashMap;

public enum EAcceptConditionType {
    checkExplore {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckExploreACondition();
        }
    },
    getLifeLevel {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetLifeLevelACondition();
        }
    },
    checkWoman {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckWomanACondition();
        }
    },
    checkClass {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckClassACondition();
        }
    },
    getIntimacy {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetIntimacyACondition();
        }
    },
    getLevel {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetLevelACondition();
        }
    },
    getKnowledge {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetKnowledgeACondition();
        }
    },
    progressQuest {
        @Override
        IAcceptConditionHandler newInstance() {
            return new ProgressQuestACondition();
        }
    },
    clearQuest {
        @Override
        IAcceptConditionHandler newInstance() {
            return new ClearQuestACondition();
        }
    },
    isGuildMember {
        @Override
        IAcceptConditionHandler newInstance() {
            return new IsGuildMemberCondition();
        }
    },
    isTitle {
        @Override
        IAcceptConditionHandler newInstance() {
            return new IsTitleCondition();
        }
    },
    checkHaveTitle {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckHaveTitleCondition();
        }
    },
    checkEquipSlotAndCash {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckEquipSlotAndCashCondition();
        }
    },
    checkTime {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckTimeACondition();
        }
    },
    getItemCount {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetItemCountACondition();
        }
    },
    checkBuff {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckBuffACondition();
        }
    },
    checkTerritory {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckTerritoryACondition();
        }
    },
    checkQuestCondition {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckQuestACondition();
        }
    },
    getSkillLevel {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetSkillLevelACondition();
        }
    },
    getTendency {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetTendencyACondition();
        }
    },
    checkEquipItem {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckEquipItemACondition();
        }
    },
    checkCollectTheme {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckCollectThemeACondition();
        }
    },
    getInventoryFreeCount {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetInventoryFreeCountACondition();
        }
    },
    getWp {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetWpACondition();
        }
    },
    getExplorePoint {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetExplorePointAConditon();
        }
    },
    getAquredExplorePoint {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetExplorePointAConditon();
        }
    },
    checkParty {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckPartyACondition();
        }
    },
    getWeatherFactor {
        @Override
        IAcceptConditionHandler newInstance() {
            return new GetWeatherFactorACondition();
        }
    },
    checkPcRoom {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckPcRoomACondition();
        }
    },
    checkConnectedNode {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckConnectedNodeACondition();
        }
    },
    checkEscort {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckEscortACondition();
        }
    },
    checkPeriod {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckPeriodACondition();
        }
    },
    isContentsGroupOpen {
        @Override
        IAcceptConditionHandler newInstance() {
            return new IsContentsGroupOpenACondition();
        }
    },
    checkCreationIndex {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckCreationIndexACondition();
        }
    },
    checkPosition {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckPositionACondition();
        }
    },
    checkRegionType {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckRegionTypeACondition();
        }
    },
    checkUseItemToTarget {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckUseItemToTargetACondition();
        }
    },
    checkPeriodbyGMT {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckPeriodbyGMTACondition();
        }
    },
    checkSiege {
        @Override
        IAcceptConditionHandler newInstance() {
            return new CheckSiegeACondition();
        }
    },
    progressGuildquest {
        @Override
        IAcceptConditionHandler newInstance() {
            return new ProgressGuildQuestACondition();
        }
    };

    private static final HashMap<String, EAcceptConditionType> map;

    static {
        map = new HashMap<>();
        for (final EAcceptConditionType type : values()) {
            EAcceptConditionType.map.put(type.name().toLowerCase(), type);
        }
    }

    public static EAcceptConditionType getConditionType(final String conditionName) {
        try {
            EAcceptConditionType.map.get(conditionName.toLowerCase());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return EAcceptConditionType.map.get(conditionName.toLowerCase());
    }

    abstract IAcceptConditionHandler newInstance();

    public IAcceptConditionHandler newConditionInstance() {
        return this.newInstance();
    }
}
