package com.bdoemu.gameserver.model.actions;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.network.sendable.SMFishingTime;
import com.bdoemu.gameserver.dataholders.*;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.DropBag;
import com.bdoemu.gameserver.model.creature.FloatFish;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.encyclopedia.templates.EncyclopediaT;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.creature.player.fishing.services.FishingService;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.VaryEquipItemEnduranceEvent;
import com.bdoemu.gameserver.model.fishing.templates.FishingT;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EDropBagType;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemMainGroupT;
import com.bdoemu.gameserver.model.world.region.enums.ERegionImgType;
import com.bdoemu.gameserver.service.GameTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public abstract class AFishingAction extends AAction {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(AFishingAction.class);
    }

    private int wpCount;

    public AFishingAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void read(final ByteBuffer buff, final Player owner) {
        super.read(buff, owner);
        this.wpCount = this.readH(buff);
        this.oldX = this.readF(buff);
        this.oldY = this.readF(buff);
        this.oldZ = this.readF(buff);
        this.targetGameObjId = this.readD(buff);
        this.skipAll(buff);
    }

    @Override
    public boolean canAct() {
        final Player player = (Player) this.owner;
        final Item item = player.getPlayerBag().getEquipments().getItem(EEquipSlot.rightHand.getId());
        if (item == null || item.getEndurance() <= 0 || !item.getTemplate().getEquipType().isFishingRod()) {
            return false;
        }
        final int regionRgb = RegionData.getInstance().getRegionMask(ERegionImgType.Fishing, player.getLocation().getX(), player.getLocation().getY());
        final FishingT template = FishingData.getInstance().getTemplate(regionRgb);
        if (template == null) {
            return false;
        }
        if (this.wpCount > 0) {
            final int currentWp = player.getCurrentWp();
            if (currentWp < this.wpCount) {
                this.wpCount = currentWp;
            }
            if (!player.addWp(-this.wpCount)) {
                return false;
            }
        }
        if (Rnd.getChance(50) && !player.getPlayerBag().onEvent(new VaryEquipItemEnduranceEvent(player, EEquipSlot.rightHand.getId(), -1))) {
            return false;
        }
        final FloatFish floatFish = FishingService.getInstance().getFloatFish(player);
        int dropId = 0;
        if (floatFish != null) {
            dropId = floatFish.getTemplate().getDropId();
            FishingService.getInstance().onFloatFishCatch(floatFish);
        }
        if (dropId == 0) {
            final FishingT fishingT = FishingData.getInstance().getTemplate(RegionData.getInstance().getRegionMask(ERegionImgType.Fishing, player.getLocation().getX(), player.getLocation().getY()));
            dropId = fishingT.calculateDrop();
        }
        if (dropId == 0) {
            final FishingT fishingT = FishingData.getInstance().getTemplate(RegionData.getInstance().getRegionMask(ERegionImgType.Fishing, player.getLocation().getX(), player.getLocation().getY()));
            dropId = fishingT.getDropId();
        }
        if (dropId == 0) {
            return false;
        }
        int waitTime = ProductToolPropertyData.getInstance().getAutoFishingTime(item);
        final int reduceAutoFishingTime = player.getGameStats().getAutoFishingTimeReduce().getIntMaxValue();
        if (reduceAutoFishingTime > 0) {
            waitTime -= waitTime * (reduceAutoFishingTime / 10000) / 100;
        }
        final long startTime = GameTimeService.getServerTimeInMillis() + Rnd.get(template.getMinWaitTime(), template.getMaxWaitTime());
        final long endTime = startTime + waitTime;
        final ItemMainGroupT itemMainGroupT = ItemMainGroupData.getInstance().getTemplate(dropId);
        if (itemMainGroupT == null) {
            AFishingAction.log.warn("AFishingAction itemMainGroup null for dropId {}", dropId);
            return false;
        }
        final DropBag dropBag = ItemMainGroupService.getDropBag(dropId, player, -1024, 0, EDropBagType.Fishing, RateConfig.FISHING_DROP_RATE);
        int fishGrade = 0;
        if (dropBag != null) {
            dropBag.setValidityTime(startTime);
            player.getPlayerBag().setDropBag(dropBag);
            final EncyclopediaT encyclopediaT = EncyclopediaData.getInstance().getTemplate(dropBag.getDropMap().get(0).getTemplate().getItemId());
            if (encyclopediaT != null)
                fishGrade = encyclopediaT.getRareness() / 3;
        }
        player.sendPacket(new SMFishingTime(startTime, endTime, fishGrade));
        return this.subType == 4;
    }
}
