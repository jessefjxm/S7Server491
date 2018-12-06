package com.bdoemu.gameserver.model.ai.executors;

import com.bdoemu.core.network.sendable.SMAiWorldNotifier;
import com.bdoemu.gameserver.model.ai.model.AIHandler;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.eclipsesource.json.JsonObject;

public class AIExecution_InstanceNotifier extends AIExecution {
    private EChatNoticeType _noticeType;
    private String          _sheet;
    private String          _name;

    /**
     * AI Executor constructor.
     * It reads a specific AI Executor attributes from
     * an Black Desert Online AI XML file.
     *
     * @param handler Owner of Executor.
     * @param element AI XML of current executor.
     */
    public AIExecution_InstanceNotifier(AIHandler handler, JsonObject element) {
        super(handler, element);
    }


    @Override
    public void execute(int deltaTime) {
        getAiHandler().getAiScript().getAiOwner().sendBroadcastPacket(
                new SMAiWorldNotifier(getAiHandler().getAiScript().getAiOwner().getName(), _noticeType, _sheet, _name, 0)
        );
    }
}