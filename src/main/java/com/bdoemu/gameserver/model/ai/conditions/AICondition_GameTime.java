package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.service.GameTimeService;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

/**
 * @author H1X4
 */
public class AICondition_GameTime extends AICondition {
    private int _startHour;
    private int _startMinute;
    private int _endHour;
    private int _endMinute;
    private boolean _checkOpposite;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_GameTime(AIExecution executor, JsonObject element) {
        super(executor, element);
        _startHour      = element.getInt("@starthour", -1);
        _startMinute    = element.getInt("@startmin", -1);
        _endHour        = element.getInt("@endhour", -1);
        _endMinute      = element.getInt("@endmin", -1);
        _checkOpposite  = element.getBoolean("@checkopposite", false);
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        boolean result = !(_startHour != -1 && _startHour <= GameTimeService.getInstance().getGameHour())
                && !(_startMinute != -1 && _startMinute <= GameTimeService.getInstance().getGameMinute())
                && !(_endHour != -1 && _endHour >= GameTimeService.getInstance().getGameHour())
                && !(_endMinute != -1 && _endMinute >= GameTimeService.getInstance().getGameMinute());
        return _checkOpposite != result;
    }
}