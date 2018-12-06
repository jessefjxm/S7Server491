package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.EtcOptionConfig;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.network.sendable.SMGetDroppedItems;
import com.bdoemu.core.network.sendable.SMTentInformation;
import com.bdoemu.core.network.sendable.SMUnoccupyFixedHouseInstallation;
import com.bdoemu.gameserver.dataholders.*;
import com.bdoemu.gameserver.model.actions.ACollectAction;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.DeadBody;
import com.bdoemu.gameserver.model.creature.DropBag;
import com.bdoemu.gameserver.model.creature.collect.Collect;
import com.bdoemu.gameserver.model.creature.collect.enums.ECollectToolType;
import com.bdoemu.gameserver.model.creature.collect.templates.CollectTemplate;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Knowledge.templates.KnowledgeLearningT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.action.ActionEXPT;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.DecreaseCollectItemEvent;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.VaryEquipItemEnduranceEvent;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.creature.services.DespawnService;
import com.bdoemu.gameserver.model.creature.services.RespawnService;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EDropBagType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.world.region.GameSector;
import com.bdoemu.gameserver.model.world.region.enums.ERegionImgType;
import com.bdoemu.gameserver.model.world.templates.RegionDropT;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;

public class FrameEventCollect extends FrameEvent {
    private static final Logger log = LoggerFactory.getLogger(FrameEventCollect.class);

    public FrameEventCollect(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    private static void doReward(final Player player, final int dropId, final EDropBagType dropBagType, final EItemStorageLocation location, final int slotIndex) {
        int rate = RateConfig.COLLECTION_DROP_RATE;
        final int knowledgeHighAcquireStatValue = player.getGameStats().getDropRateCollectStat().getIntValue();
        if (knowledgeHighAcquireStatValue > 0) {
            rate += knowledgeHighAcquireStatValue / 10000;
        }
        final DropBag dropBag = ItemMainGroupService.getDropBag(dropId, player, -1024, 0, dropBagType, rate);
        if (dropBag != null) {
            for (final Item item : dropBag.getDropMap().values()) {
                LifeActionEXPData.getInstance().reward(player, item.getItemId(), (dropBagType == EDropBagType.CollectTentInstallation) ? ELifeExpType.Farming : ELifeExpType.Gather);
                player.getObserveController().notifyObserver(EObserveType.gatherItem, item.getItemId(), item.getEnchantLevel(), item.getCount(), player.getObjectId());
            }
            if (dropBagType == EDropBagType.CollectWithInventory) {
                if (!player.getPlayerBag().onEvent(new DecreaseCollectItemEvent(player, location, slotIndex, dropBag.getDropMap().values()))) {
                    return;
                }
            } else {
                player.getPlayerBag().setDropBag(dropBag);
                player.sendPacket(new SMGetDroppedItems(-1024, dropBag));
            }
            final ActionEXPT actionEXPT = ActionEXPData.getInstance().getExpTemplate(player, player.getLifeExperienceStorage().getLifeExperience(ELifeExpType.Gather).getLevel());
            if (actionEXPT != null) {
                player.addExp(actionEXPT.getCollect());
            }
        }
    }

    private static int getReward(final Player player, final ACollectAction collectAction, final Item useItem, final ECollectToolType collectToolType) {
        int dropId = -1;
        switch (collectAction.getCollectType()) {
            case 6: {
                final GameSector gameSector = World.getInstance().getWorldMap().getGameSectorBySubXY(collectAction.getSubSectorX(), collectAction.getSubSectorY());
                if (gameSector != null) {
                    final Collect collectSpawn = gameSector.getCreature0(collectAction.getResourceIndex(), ECharKind.Collect);
                    if (collectSpawn != null) {
                        final KnowledgeLearningT knowledgeLearningT = KnowledgeLearningData.getInstance().getTemplate(collectSpawn.getCreatureId());
                        if (knowledgeLearningT != null && Rnd.getChance(knowledgeLearningT.getSelectRate(), 10000)) {
                            player.getMentalCardHandler().updateMentalCard(knowledgeLearningT.getKnowledgeIndex(), ECardGradeType.C);
                        }
                        if (World.getInstance().deSpawn(collectSpawn, ERemoveActorType.Collect)) {
                            RespawnService.getInstance().putCollect(collectSpawn);
                        }
                        final Integer _dropId = collectSpawn.getCollectTemplate().getDropsByToolTypes().get(collectToolType);
                        if (_dropId == null) {
                            return -1;
                        }
                        dropId = _dropId;
                    }
                    break;
                }
                break;
            }
            case 8: {
                final HouseHold tent = player.getHouseholdController().getHouseHold(collectAction.getHouseHoldObjId());
                if (tent != null) {
                    final HouseInstallation houseInstallation = tent.getHouseInstallation(collectAction.getHouseInstallationObjId());
                    if (houseInstallation != null) {
                        switch (collectAction.getCollectTentType()) {
                            case 0: {
                                if (tent.removeHouseInstallation(houseInstallation)) {
                                    tent.refreshLifeTime();
                                    tent.sendBroadcastPacket(new SMUnoccupyFixedHouseInstallation(tent, houseInstallation));
                                    dropId = houseInstallation.getObjectTemplate().getProductRecipe();
                                    break;
                                }
                                break;
                            }
                            case 1: {
                                if (tent.removeHouseInstallation(houseInstallation)) {
                                    tent.refreshLifeTime();
                                    tent.sendBroadcastPacket(new SMUnoccupyFixedHouseInstallation(tent, houseInstallation));
                                    dropId = houseInstallation.getObjectTemplate().getSeedRecipe();
                                    break;
                                }
                                break;
                            }
                            case 2: {
                                if (houseInstallation.isPruning()) {
                                    houseInstallation.setPruning(false);
                                    dropId = EtcOptionConfig.PRUNING;
                                    break;
                                }
                                break;
                            }
                            case 3: {
                                if (houseInstallation.isCatchBug()) {
                                    houseInstallation.setCatchBug(false);
                                    dropId = EtcOptionConfig.CATCHBUG;
                                    break;
                                }
                                break;
                            }
                        }
                        tent.sendBroadcastPacket(new SMTentInformation(Collections.singleton(tent)));
                    }
                    break;
                }
                break;
            }
            case 9: {
                final DeadBody body = KnowList.getObject(player, ECharKind.Deadbody, collectAction.getSessionId());
                if (body == null) {
                    break;
                }
                if ((body.getDropBag() != null && !body.getDropBag().isEmpty()) || !body.hasCollections() || !DespawnService.getInstance().despawnBody(body)) {
                    return -1;
                }
                final CollectTemplate collectTemplate = CollectData.getInstance().getTemplate(body.getTemplate().getCollectDropId());
                final Integer _dropId2 = collectTemplate.getDropsByToolTypes().get(collectToolType);
                if (_dropId2 == null) {
                    return -1;
                }
                dropId = _dropId2;
                break;
            }
            case 11: {
                final int toolId = useItem.getTemplate().getContentsEventParam1();
                if (toolId == 0) {
                    final int rgb = RegionData.getInstance().getRegionMask(ERegionImgType.Water, player.getLocation().getX(), player.getLocation().getY());
                    final RegionDropT waterT = WaterData.getInstance().getTemplate(rgb);
                    dropId = waterT.getDropId();
                    break;
                }
                if (toolId == 1) {
                    final int rgb = RegionData.getInstance().getRegionMask(ERegionImgType.Underground, player.getLocation().getX(), player.getLocation().getY());
                    final RegionDropT dropTemplate = UndergroundData.getInstance().getTemplate(rgb);
                    dropId = dropTemplate.getDropId();
                    break;
                }
                break;
            }
            default:
                log.error("Not implemented collection type: " + collectAction.getCollectType());
                break;
        }
        return dropId;
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        final Player player = (Player) action.getOwner();
        final ACollectAction collectAction = (ACollectAction) action;
        EItemStorageLocation location = null;
        int slotIndex = 0;
        ECollectToolType collectToolType = ECollectToolType.BareHands;
        long useTime = 5000L;
        EDropBagType droppedItemsType = EDropBagType.CollectWithEquipment;
        final String actionName = action.getActionName();
        switch (actionName) {
            case "Submerge_Collect_HAND":
            case "COLLECT_BY_HAND": {
                if (collectAction.getCollectType() != 6 && collectAction.getCollectType() != 8) {
                    return false;
                }
                if (collectAction.getCollectType() == 8) {
                    droppedItemsType = EDropBagType.CollectTentInstallation;
                    break;
                }
                break;
            }
            case "COLLECT_UNDERGROUND":
            case "COLLECT_WATER": {
                if (collectAction.getCollectType() != 11) {
                    return false;
                }
                location = EItemStorageLocation.valueOf(collectAction.getStorageType());
                slotIndex = collectAction.getSlotIndex();
                break;
            }
            case "Submerge_Collect_HOE":
            case "COLLECT_BY_TWEEZERS":
            case "COLLECT_BY_AXE":
            case "COLLECT_BY_PICKAX":
            case "COLLECT_BY_PRAY":
            case "COLLECT_BY_HOE": {
                if (collectAction.getCollectType() != 6) {
                    return false;
                }
                location = EItemStorageLocation.Equipments;
                slotIndex = EEquipSlot.subTool.getId();
                break;
            }
            case "COLLECT_BY_TANNINGKNIFE":
            case "COLLECT_BY_RAZOR": {
                if (collectAction.getCollectType() != 9) {
                    return false;
                }
                location = EItemStorageLocation.Equipments;
                slotIndex = EEquipSlot.subTool.getId();
                break;
            }
            case "COLLECT_BY_SYRINGE": {
                if (collectAction.getCollectType() != 9 && collectAction.getCollectType() != 6) {
                    return false;
                }
                location = EItemStorageLocation.Equipments;
                slotIndex = EEquipSlot.subTool.getId();
                break;
            }
            case "COLLECT_BY_BOTTLE": {
                return false;
            }
            default: {
                log.error("Not implemented collection event: " + actionName + ", collection type: " + collectAction.getCollectType());
                return false;
            }
        }
        Item useItem = null;
        if (location != null) {
            final ItemPack itemPack = player.getPlayerBag().getItemPack(location);
            if (itemPack != null) {
                useItem = itemPack.getItem(slotIndex);
                if (useItem != null) {
                    if (location.isEquipments()) {
                        collectToolType = useItem.getTemplate().getCollectToolType();
                        if (!collectToolType.isBareHands()) {
                            useTime = useItem.getTemplate().getCollectTime();
                        }
                    } else {
                        droppedItemsType = EDropBagType.CollectWithInventory;
                        useTime = useItem.getTemplate().getContentsEventParam2();
                    }
                }
            }
        }
        useTime -= useTime * (player.getGameStats().getCollectionLuck().getCollectionRate() / 10000) / 100L;
        if (System.currentTimeMillis() + 1000L < action.getStartTime() + useTime) {
            return false;
        }
        final int rewardId = getReward(player, collectAction, useItem, collectToolType);
        if (rewardId < 0 || !player.addWp(-EtcOptionConfig.COLLECT_CONSUME_WP)) {
            return false;
        }
        if (!collectToolType.isBareHands() && !player.getPlayerBag().onEvent(new VaryEquipItemEnduranceEvent(player, EEquipSlot.subTool.getId(), -1))) {
            return false;
        }
        doReward(player, rewardId, droppedItemsType, location, slotIndex);
        return true;
    }
}
