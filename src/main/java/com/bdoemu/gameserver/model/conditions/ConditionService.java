package com.bdoemu.gameserver.model.conditions;

import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;
import com.bdoemu.gameserver.model.conditions.accept.enums.EAcceptConditionType;
import com.bdoemu.gameserver.model.conditions.complete.ICompleteConditionHandler;
import com.bdoemu.gameserver.model.conditions.complete.enums.ECompleteConditionType;
import com.bdoemu.gameserver.model.creature.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionService {
    private static final Logger log;
    private static List<String> unimplementedConditions;

    static {
        log = LoggerFactory.getLogger((Class) ConditionService.class);
        ConditionService.unimplementedConditions = new ArrayList<String>();
    }

    public static boolean checkCondition(final IAcceptConditionHandler[][] handlers, final Player player) {
        if (handlers == null) {
            return true;
        }
        boolean result = false;
        for (final IAcceptConditionHandler[] _handlers : handlers) {
            result = true;
            for (final IAcceptConditionHandler handler : _handlers) {
                if (handler != null) {
                    if (!handler.checkCondition(player)) {
                        result = false;
                        break;
                    }
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }

    public static IAcceptConditionHandler[][] getAcceptConditions(String data) {
        if (data != null && data.trim().startsWith(";"))
            data = data.trim().substring(1);
        if (data != null && data.trim().contains("\n"))
            data = data.replace("\n", "");
        if (data != null && data.trim().equals("0")) {
            return new IAcceptConditionHandler[1][1];
        }
        if (data == null || data.isEmpty() || data.trim().equals("0")) {
            return null;
        }
        final String[] orList = data.split("<or>");
        final IAcceptConditionHandler[][] handlers = new IAcceptConditionHandler[orList.length][];
        for (int i = 0; i < orList.length; ++i) {
            final String[] handlerList = orList[i].trim().split(";");
            handlers[i] = new IAcceptConditionHandler[handlerList.length];
            for (int y = 0; y < handlerList.length; ++y) {
                String subData = handlerList[y].trim();
                Pattern pattern = Pattern.compile("\\!|\\*");
                Matcher matcher = pattern.matcher(subData);
                boolean hasExclamation = false;
                String conditionName = null;
                String conditionValue = null;
                String operator = null;
                String operatorValue = null;
                if (matcher.find() && matcher.group(0).equals("!")) {
                    hasExclamation = true;
                    subData = subData.substring(1, subData.length());
                }
                pattern = Pattern.compile("(.*)\\((.*)\\)");
                matcher = pattern.matcher(subData);
                if (matcher.find()) {
                    conditionName = matcher.group(1);
                    conditionValue = matcher.group(2);
                }
                pattern = Pattern.compile("(.*)(\\<|\\>|\\=)(.*)");
                matcher = pattern.matcher(subData);
                if (matcher.find()) {
                    operator = matcher.group(2);
                    operatorValue = matcher.group(3);
                }

                final EAcceptConditionType type = EAcceptConditionType.getConditionType(conditionName);
                if (type != null) {
                    final IAcceptConditionHandler handler = type.newConditionInstance();
                    handler.load(conditionValue, operator, operatorValue, hasExclamation);
                    handlers[i][y] = handler;
                } else if (conditionName != null && !ConditionService.unimplementedConditions.contains(conditionName.toLowerCase())) {
                    ConditionService.unimplementedConditions.add(conditionName.toLowerCase());
                    ConditionService.log.warn("Accept condition [{}] is unimplemented! ({})", (Object) conditionName, (Object) conditionValue);
                }
            }
        }
        return handlers;
    }

    public static List<ICompleteConditionHandler> getCompleteConditions(final String data) {
        final List<ICompleteConditionHandler> conditions = new ArrayList<ICompleteConditionHandler>();
        for (String condition : data.split(";")) {
            condition = condition.trim().replace("\n", "");
            if (!condition.isEmpty()) {
                final int startIndex = condition.indexOf("(");
                String key = condition.substring(0, startIndex);
                final ECompleteConditionType type = ECompleteConditionType.getConditionType(key);
                if (type != null) {
                    final ICompleteConditionHandler conditionHandler = type.newConditionInstance();
                    final int endIndex = condition.indexOf(")");
                    try {
                        if (endIndex == -1)
                            key = condition.substring(startIndex + 1);
                        else
                            key = condition.substring(startIndex + 1, endIndex); // meet(40001);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    conditionHandler.load(key.split(","));
                    conditions.add(conditionHandler);
                } else if (!ConditionService.unimplementedConditions.contains(key.toLowerCase())) {
                    ConditionService.unimplementedConditions.add(key.toLowerCase());
                    ConditionService.log.warn("Complete condition [{}] is unimplemented! (data: {})", (Object) key, (Object) condition);
                }
            }
        }
        return conditions;
    }
}
