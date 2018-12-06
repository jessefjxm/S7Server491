package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckPeriodACondition implements IAcceptConditionHandler {
    private static final Logger log = LoggerFactory.getLogger(CheckPeriodACondition.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd-HH:mm");
    private Date startDate;
    private Date endDate;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] dateData = conditionValue.split(",");
        try {
            this.startDate = CheckPeriodACondition.formatter.parse(dateData[0]);
            this.endDate = CheckPeriodACondition.formatter.parse(dateData[1]);
        } catch (ParseException e) {
            CheckPeriodACondition.log.error("Error while parsing date's in condition value.");
        }
    }

    @Override
    public boolean checkCondition(final Player player) {
        final Date now = new Date();
        return now.after(this.startDate) && now.before(this.endDate);
    }
}
