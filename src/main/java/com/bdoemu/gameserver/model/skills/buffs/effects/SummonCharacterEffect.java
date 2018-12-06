package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.InstanceSummon;
import com.bdoemu.gameserver.model.creature.monster.Monster;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.InstanceSummonService;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.team.party.CreatureParty;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class SummonCharacterEffect extends ABuffEffect {
    private static final Logger log = LoggerFactory.getLogger(SummonCharacterEffect.class);

    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final Integer[] params          = buffTemplate.getParams();
        final int creatureId            = params[0];
        final int type                  = params[1];
        if (params[2] == null)
            return null;
        final int formIndex             = params[2];
        final Integer summonRotation    = params[3];
        final Integer unk4              = params[4];
        final Integer summonTime        = params[7];
        final Integer unk6              = params[9];
        if (owner != null) {
            final Location spawnLocation = owner.getLocation();
            final Creature creature = Creature.newCreature(new SpawnPlacementT(creatureId, formIndex, spawnLocation));
            if (creature != null && creature.getActionStorage() != null) {
                switch (type) {
                    case 0: {
                        if (owner.isPlayer()) {
                            //Creature target = World.getInstance().getObjectById(owner.getActionStorage().getAction().getTargetGameObj());
                            //System.out.println(target);
                            //creature.getAggroList().addCreature(target);
                            //creature.getAggroList().addCreature();
                            //System.out.println(owner.getActionStorage().getActionChartActionT().getMoveDirectionType().toString());
                            //System.out.println(owner.getActionStorage().getActionChartActionT().getActionName());
                            creature.setOwner(owner);
                            World.getInstance().spawn(creature, true, false);
                            owner.getSummonStorage().addSummon(creature);
                            break;
                        }
                        IParty<Creature> party = (IParty<Creature>) owner.getParty();
                        if (party == null) {
                            party = new CreatureParty(owner, 1, 1000);
                            owner.setParty(party);
                        }
                        World.getInstance().spawn(creature, true, false);
                        owner.getSummonStorage().addSummon(creature);
                        party.addMember(creature);
                        break;
                    }
                    case 1: {
                        World.getInstance().spawn(creature, true, false);
                        break;
                    }
                    case 2: {
                        final double distance = owner.getTemplate().getBodySize() + creature.getTemplate().getBodySize() + 200;
                        final double nx = spawnLocation.getX() + spawnLocation.getCos() * distance;
                        final double ny = spawnLocation.getY() + spawnLocation.getSin() * distance;
                        final double angle = Math.atan2((spawnLocation.getY() - ny) / distance, (spawnLocation.getX() - nx) / distance);
                        final double cos = Math.cos(angle);
                        final double sin = Math.sin(angle);
                        creature.getLocation().setLocation(nx, ny, spawnLocation.getZ(), cos, sin);
                        if (creature.isMonster()) {
                            final Monster monster = (Monster) creature;
                            Player player;
                            if (owner.isPlayer()) {
                                player = (Player) owner;
                            } else {
                                IParty<Creature> party2 = (IParty<Creature>) owner.getParty();
                                if (party2 == null) {
                                    party2 = new CreatureParty(owner, 1, 1000);
                                    owner.setParty(party2);
                                }
                                party2.addMember(creature);
                                player = ((Monster) owner).getInstanceSummon().getOwner();
                            }
                            final InstanceSummon summon = new InstanceSummon(monster, player);
                            monster.setInstanceSummon(summon);
                            InstanceSummonService.getInstance().addMonster(summon);
                            break;
                        }
                        break;
                    }
                }
            }
            return null;
        }
        SummonCharacterEffect.log.warn("Trying to spawn creature [{}] from skillId [{}] without owner.", (Object) creatureId, (Object) skillT.getSkillId());
        return null;
    }
}
