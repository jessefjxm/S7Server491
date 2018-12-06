package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.eclipsesource.json.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author H1X4
 */
public class AICondition_TargetCharacterKey extends AICondition {
    private String _cmp;
    private List<String> _value;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_TargetCharacterKey(AIExecution executor, JsonObject element) {
        super(executor, element);

        _cmp = element.getString("@cmp", "==");
        _value = new ArrayList<>();
        if (element.get("@value").isNumber())
            _value.add("" + element.getInt("@value", 0));
        else {
            String[] data = element.getString("@value", "").split("|");
            _value.addAll(Arrays.asList(data));
        }
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        Creature owner = getAiExecutor().getAiHandler().getAiScript().getAiOwner();
        Creature target = owner.getAggroList().getTarget();
        if (target != null) {
            for(String characterKey2 : _value) {
                Integer characterKey = characterKey2.startsWith("_")
                        ? getAiExecutor().getAiHandler().getAiScript().getVariable(characterKey2)
                        : (StringUtils.isNumeric(characterKey2)
                            ? (int) getAiExecutor().getAiHandler().getAiScript().getAiVariable(characterKey2)
                            : Integer.parseInt(characterKey2));
                switch (_cmp) {
                    case "==":
                        return target.getCreatureId() == characterKey;
                    case "!=":
                        return target.getCreatureId() != characterKey;
                    case ">":
                        return target.getCreatureId() > characterKey;
                    case "<":
                        return target.getCreatureId() < characterKey;
                    case ">=":
                        return target.getCreatureId() >= characterKey;
                    case "<=":
                        return target.getCreatureId() <= characterKey;
                    case "=>":
                        return target.getCreatureId() >= characterKey;
                    case "=<":
                        return target.getCreatureId() <= characterKey;
                    default: break;
                }
            }
        }
        return false;
    }
}