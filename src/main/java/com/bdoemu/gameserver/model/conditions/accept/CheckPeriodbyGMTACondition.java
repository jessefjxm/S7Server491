package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class CheckPeriodbyGMTACondition implements IAcceptConditionHandler {
    private static final Logger log = LoggerFactory.getLogger(CheckPeriodbyGMTACondition.class);
    private static SimpleDateFormat format;

    static {
        (CheckPeriodbyGMTACondition.format = new SimpleDateFormat("yyyy/MM/dd-HH:mm")).setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private boolean hasExclamation;
    private long start;
    private long end;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        this.hasExclamation = hasExclamation;
        final String[] conditions = conditionValue.split(",");
        try {
            this.start = CheckPeriodbyGMTACondition.format.parse(conditions[1]).getTime();
            this.end = CheckPeriodbyGMTACondition.format.parse(conditions[2]).getTime();
        } catch (Exception e) {
            CheckPeriodbyGMTACondition.log.error("Error while parsing CheckPeriodbyGMTACondition time: {}", (Object) e, (Object) conditionValue);
        }
    }

    @Override
    public boolean checkCondition(final Player player) {
        final long currentTime = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
        final boolean isInRange = currentTime >= this.start && currentTime <= this.end;
        return this.hasExclamation != isInRange;
    }
}
