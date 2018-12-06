package com.bdoemu.gameserver.model.creature.player.fitness;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMSetCharacterPublicPoints;
import com.bdoemu.core.network.sendable.SMSetCharacterRelatedPoints;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.fitness.enums.EFitnessType;
import com.bdoemu.gameserver.model.creature.player.fitness.templates.FitnessTemplate;
import com.bdoemu.gameserver.model.stats.containers.PlayerGameStats;
import com.bdoemu.gameserver.model.stats.elements.FitnessElement;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class FitnessHandler extends JSONable {
    private final Fitness[] fitnessList;
    private FitnessElement stamina;
    private FitnessElement weight;
    private FitnessElement hp;
    private FitnessElement mp;

    public FitnessHandler(final Player player, final BasicDBObject basicDBObject) {
        this.fitnessList = new Fitness[EFitnessType.values().length];
        int addStamina = 0;
        int addWeight = 0;
        int addHp = 0;
        int addMp = 0;
        final BasicDBList fitnessListDB = (BasicDBList) basicDBObject.get("fitnessList");
        for (int i = 0; i < fitnessListDB.size(); ++i) {
            final BasicDBObject fitnessDB = (BasicDBObject) fitnessListDB.get(i);
            final Fitness fitness = new Fitness(fitnessDB);
            final FitnessTemplate t = fitness.getTemplate();
            addStamina += t.getAddStamina();
            addWeight += t.getAddWeight();
            addHp += t.getAddHp();
            addMp += t.getAddMp();
            this.fitnessList[fitness.getFitnessType().ordinal()] = fitness;
        }
        this.stamina = new FitnessElement(addStamina);
        this.weight = new FitnessElement(addWeight);
        this.hp = new FitnessElement(addHp);
        this.mp = new FitnessElement(addMp);
        this.setStats(player);
    }

    public FitnessHandler(final Player player) {
        this.fitnessList = new Fitness[EFitnessType.values().length];
        for (int index = 0; index < this.fitnessList.length; ++index) {
            this.fitnessList[index] = new Fitness(EFitnessType.valueOf(index));
        }
        this.stamina = new FitnessElement(0.0f);
        this.weight = new FitnessElement(0.0f);
        this.hp = new FitnessElement(0.0f);
        this.mp = new FitnessElement(0.0f);
        this.setStats(player);
    }

    private final void setStats(final Player player) {
        final PlayerGameStats stats = player.getGameStats();
        stats.getHp().addElement(this.hp);
        stats.getMp().addElement(this.mp);
        stats.getWeight().addElement(this.weight);
        stats.getStamina().addElement(this.stamina);
    }

    public void addExp(final Player player, final EFitnessType type, final int exp) {
        this.fitnessList[type.ordinal()].addExp(player, exp);
    }

    public Fitness[] getFitness() {
        return this.fitnessList;
    }

    public synchronized void updateStats(final Player player, final int addStamina, final int addWeight, final int addHp, final int addMp) {
        final PlayerGameStats stats = player.getGameStats();
        stats.getStamina().increaseElement(this.stamina, addStamina);
        stats.getWeight().increaseElement(this.weight, addWeight);
        stats.getHp().increaseElement(this.hp, addHp);
        stats.getMp().increaseElement(this.mp, addMp);
        if (addHp > 0) {
            player.sendBroadcastPacket(new SMSetCharacterPublicPoints(player, 0.0f));
            final IParty<Player> party = player.getParty();
            if (party != null) {
                party.sendBroadcastPacket(new SMSetCharacterPublicPoints(player, 0.0f));
            }
        }
        if (addMp > 0) {
            final IParty<Player> party = player.getParty();
            if (party != null) {
                party.sendBroadcastPacket(new SMSetCharacterRelatedPoints(player, 0));
            }
        }
    }

    public FitnessElement getHp() {
        return this.hp;
    }

    public FitnessElement getMp() {
        return this.mp;
    }

    public FitnessElement getStamina() {
        return this.stamina;
    }

    public FitnessElement getWeight() {
        return this.weight;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList fitnessDB = new BasicDBList();
        for (final Fitness fitness : this.fitnessList) {
            fitnessDB.add(fitness.toDBObject());
        }
        builder.append("fitnessList", fitnessDB);
        return builder.get();
    }
}
