package com.bdoemu.gameserver.model.functions.enums;

import com.bdoemu.gameserver.model.functions.*;

import java.util.HashMap;

public enum EFunctionType {
    escortComplete {
        @Override
        IFunctionHandler newInstance() {
            return new EscortCompleteFunction();
        }
    },
    executeHandler {
        @Override
        IFunctionHandler newInstance() {
            return new ExecuteHandlerFunction();
        }
    },
    restoreItemByPoint {
        @Override
        IFunctionHandler newInstance() {
            return new RestoreItemByPointFunction();
        }
    },
    buyItemByPoint {
        @Override
        IFunctionHandler newInstance() {
            return new BuyItemByPointFunction();
        }
    },
    exchangeItemToGroup {
        @Override
        IFunctionHandler newInstance() {
            return new ExchangeItemToGroupFunction();
        }
    },
    exchangeItem {
        @Override
        IFunctionHandler newInstance() {
            return new ExchangeItemFunction();
        }
    },
    exchangeSilver {
        @Override
        IFunctionHandler newInstance() {
            return new ExchangeSilverFunction();
        }
    },
    showCutScene {
        @Override
        IFunctionHandler newInstance() {
            return new ShowCutSceneFunction();
        }
    },
    pushItem {
        @Override
        IFunctionHandler newInstance() {
            return new PushItemFunction();
        }
    },
    removeItem {
        @Override
        IFunctionHandler newInstance() {
            return new RemoveItemFunction();
        }
    },
    detectPlayer {
        @Override
        IFunctionHandler newInstance() {
            return new DetectPlayerFunction();
        }
    },
    executeSearch {
        @Override
        IFunctionHandler newInstance() {
            return new ExecuteSearchFunction();
        }
    },
    pushKnowledge {
        @Override
        IFunctionHandler newInstance() {
            return new PushKnowledgeFunction();
        }
    },
    askKnowledge {
        @Override
        IFunctionHandler newInstance() {
            return new AskKnowledgeFunction();
        }
    };

    private static final HashMap<String, EFunctionType> map;

    static {
        map = new HashMap<>();
        for (final EFunctionType type : values()) {
            EFunctionType.map.put(type.name().toLowerCase(), type);
        }
    }

    public static EFunctionType getFunctionType(final String functionName) {
        return EFunctionType.map.get(functionName.toLowerCase());
    }

    abstract IFunctionHandler newInstance();

    public IFunctionHandler newFunctionInstance() {
        return this.newInstance();
    }
}
