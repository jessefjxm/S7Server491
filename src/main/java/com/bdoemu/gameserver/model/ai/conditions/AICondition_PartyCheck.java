package com.bdoemu.gameserver.model.ai.conditions;

import com.bdoemu.gameserver.model.ai.executors.AIExecution;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.eclipsesource.json.JsonObject;
import org.w3c.dom.Element;

/**
 * @author H1X4
 */
public class AICondition_PartyCheck extends AICondition {
    private int _type;
    private String _cmp;
    private int _value;

    /**
     * Constructor for AI Condition which is
     * the core mechanic of an executor.
     *
     * @param executor AI Executor that currently owns this object.
     * @param element  AI XML element that will be read from.
     */
    public AICondition_PartyCheck(AIExecution executor, JsonObject element) {
        super(executor, element);

        String type = element.getString("@type", "");
        if (type.equals("isPartyLeader"))
            _type = 1;
        else if (type.equals("isPartyMember"))
            _type = 2;
        else if (type.equals("memberCount")) {
            _type = 3;
            _cmp = element.getString("@cmp", "==");

            if (element.get("@value").isString()) {
                String value = element.getString("@value", "");
                if (value.startsWith("_"))
                    _value = getAiExecutor().getAiHandler().getAiScript().getVariable(value);
                else
                    _value = (int) getAiExecutor().getAiHandler().getAiScript().getAiVariable(value);
            } else
                _value = element.getInt("@value", 0);
        }
        else
            System.out.println("Not implemented PartyCheck: " + type);
    }

    /**
     * All AI Conditions are obliged to inherit this method.
     *
     * @return true if condition succeeded, otherwise false.
     */
    @Override
    public boolean test() {
        Creature creature = getAiExecutor().getAiHandler().getAiScript().getAiOwner();
        @SuppressWarnings("unchecked") IParty<Creature> party = (IParty<Creature>)creature.getParty();
        if (party == null)
            return false;

        switch(_type) {
            case 1: return party.isPartyLeader(creature);
            case 2: return !party.isPartyLeader(creature);
            case 3:
                int partyCount = party.getMembersCount();
                switch(_cmp) {
                    case "==": return partyCount == _value;
                    case ">" : return partyCount > _value;
                    case "<" : return partyCount < _value;
                    case "!=": return partyCount != _value;
                    case ">=": return partyCount >= _value;
                    case "<=": return partyCount <= _value;
                    case "=>": return partyCount >= _value;
                    case "=<": return partyCount <= _value;
                    case "=" : return partyCount == _value;
                    default: return true;
                }
            default: return false;
        }
    }
}
