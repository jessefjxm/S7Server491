package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.core.network.sendable.SMSetCharacterTeamAndGuild;
import com.bdoemu.core.network.sendable.SMSetEscort;
import com.bdoemu.core.network.sendable.SMTamingServant;
import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.conditions.accept.CheckEscortACondition;
import com.bdoemu.gameserver.model.conditions.complete.ICompleteConditionHandler;
import com.bdoemu.gameserver.model.conditions.complete.KillMonsterCCondition;
import com.bdoemu.gameserver.model.creature.DeadBody;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.quests.Quest;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.services.RespawnService;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.skills.buffs.ModuleBuffType;
import com.bdoemu.gameserver.utils.PARand;

public class RideOnPrepareAction extends ADefaultAction {
    private Servant servant;

    public RideOnPrepareAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void init() {
        final Player player = (Player) this.owner;
        if (this.servant.getBuffList().hasBuff(ModuleBuffType.BLOCK_DRIVER_SEAT)) {
            this.message = EStringTable.eErrNoRestrictVehicle;
            super.init();
            return;
        }
        if (this.servant.getTemplate().isTaming() && this.servant.getOwner() == null && !this.servant.isTamed()) {
            if (player.getServantController().getTameServant() != this.servant) {
                this.message = EStringTable.eErrNoServantCantTaming;
                super.init();
                return;
            }
            this.servant.getAggroList().clear(true);
            int tamingRate = 0;
            if (this.servant.getTemplate().getVehicleType().isHorse()) {
                tamingRate = ServantConfig.TAMING_RATE;
                if (this.servant.isTamingSugar()) {
                    tamingRate += ServantConfig.TAMING_VALUE;
                }
            } else if (this.servant.getTemplate().getVehicleType().isElephant()) {
                tamingRate = ServantConfig.ELEPHANT_TAMING_RATE;
                if (this.servant.isTamingSugar()) {
                    tamingRate += ServantConfig.ELEPHANT_TAMING_VALUE;
                }
            }
            if (!PARand.PARollChance(tamingRate)) {
                this.message = EStringTable.eErrNoServantMatingField;
                super.init();
                player.getServantController().setTameServant(null);
                this.servant.getAi().HandleFailTaming(this.owner, null);
                return;
            }
            this.servant.setTamed(true);
            this.servant.setOwner(player);
            player.sendBroadcastItSelfPacket(new SMSetCharacterTeamAndGuild(this.servant));
            player.getSummonStorage().addSummon(this.servant);
            player.sendPacket(new SMTamingServant(this.servant));
            this.servant.getAi().HandleTaming(this.owner, null);
            player.getObserveController().notifyObserver(EObserveType.tamingServant, 0, this.servant.getCreatureId());
            RespawnService.getInstance().putBody(new DeadBody(null, this.servant, null));
        }
        if (!this.servant.mount(player, this.ridingSlotType)) {
            this.message = EStringTable.eErrNoVehicleActorAlreadyHaveRider;
        }
        super.init();
        if (CheckEscortACondition.escortList.contains(this.servant.getCreatureId())) {
            for (final Quest quest : player.getPlayerQuestHandler().getProgressQuestList()) {
                for (final ICompleteConditionHandler condition : quest.getTemplate().getCompleteConditions()) {
                    if (condition instanceof KillMonsterCCondition && ((KillMonsterCCondition) condition).getMonsterId() == this.servant.getCreatureId()) {
                        player.setEscort(this.servant);
                        player.sendPacket(new SMSetEscort(this.servant.getGameObjectId()));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean canAct() {
        this.servant = KnowList.getObject(this.owner, ECharKind.Vehicle, this.targetGameObjId);
        return this.servant != null && super.canAct();
    }
}
