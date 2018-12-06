// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMRegisterServantAuctionGoodsVer2;
import com.bdoemu.core.network.sendable.SMRegisterServantAuctionGoodsVer2Nak;
import com.bdoemu.gameserver.databaseCollections.ServantAuctionDBCollection;
import com.bdoemu.gameserver.databaseCollections.ServantsDBCollection;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.auction.enums.EAuctionRegisterType;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.ServantEquipments;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantSetTemplate;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.skills.ServantSkill;
import com.bdoemu.gameserver.service.GameTimeService;

public class CMRegisterServantAuctionGoodsVer2 extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private long objectId;
    private long price;
    private boolean isSelfOnly;
    private EAuctionRegisterType auctionRegisterType;

    public CMRegisterServantAuctionGoodsVer2(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.objectId = this.readQ();
        this.auctionRegisterType = EAuctionRegisterType.values()[this.readC()];
        this.isSelfOnly = this.readCB();
        this.price = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
            if (npc == null) {
                return;
            }
            final CreatureFunctionT function = npc.getTemplate().getCreatureFunctionT();
            if (function == null || function.getAuctionKey() == null) {
                return;
            }
            final Servant servant = player.getServantController().getServant(this.objectId);
            if (servant != null && servant.getServantState().isStable() && npc.getRegionId() == servant.getRegionId()) {
                if (!servant.getGameStats().getMp().isFull()) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoServantMpIsLack, this.opCode));
                    return;
                }
                final ServantSetTemplate servantSetTemplate = servant.getServantSetTemplate();
                long calculatedPrice = 0L;
                switch (this.auctionRegisterType) {
                    case ServantMarket: {
                        final ServantEquipments equipments = servant.getEquipments();
                        final ItemPack inventory = servant.getInventory();
                        if ((equipments != null && !equipments.getItemMap().isEmpty()) || (inventory != null && !inventory.getItemMap().isEmpty())) {
                            player.sendPacket(new SMNak(EStringTable.eErrNoRegisterServantFailedByItem, CMChangeServantToReward.class));
                            return;
                        }
                        calculatedPrice = servantSetTemplate.getBasePrice() + servantSetTemplate.getPricePerLevel() * servant.getLevel();
                        final int defaultMatingCount = servantSetTemplate.getMatingCount();
                        int decreasePercentage = 0;
                        if (defaultMatingCount > 0) {
                            final float costPerMatingCount = ServantConfig.VEHICLE_DECREASE_COST_BY_MATING_COUNT / defaultMatingCount / 10000.0f;
                            decreasePercentage = (int) ((defaultMatingCount - servant.getMatingCount()) * costPerMatingCount);
                        }
                        if (servant.isClearedDeathCount()) {
                            decreasePercentage += (int) (ServantConfig.VEHICLE_DECREASE_COST_BY_DEAD_CLEAR / 10000.0f);
                        }
                        if (servant.isClearedMatingCount()) {
                            decreasePercentage += (int) (ServantConfig.VEHICLE_DECREASE_COST_BY_MATING_CLEAR / 10000.0f);
                        }
                        calculatedPrice = calculatedPrice * (100 - decreasePercentage) / 100L;
                        for (final ServantSkill servantSkill : servant.getServantSkillList().getSkills()) {
                            calculatedPrice += servantSkill.getVehicleSkillT().getBasePrice() * (30 * servantSetTemplate.getTier()) / 100L;
                        }
                        final ServantItemMarket servantItemMarket = new ServantItemMarket(servant, player.getAccountId(), calculatedPrice, false);
                        if (AuctionGoodService.getInstance().registerServant(servantItemMarket)) {
                            ServantsDBCollection.getInstance().delete(this.objectId);
                            servant.setServantState(EServantState.RegisterMarket);
                            servant.setAuctionRegisterType(this.auctionRegisterType);
                            ServantAuctionDBCollection.getInstance().save(servantItemMarket);
                            player.sendPacket(new SMRegisterServantAuctionGoodsVer2(servant));
                            break;
                        }
                        break;
                    }
                    case ServantMatingMarket: {
                        if (!servant.getServantSetTemplate().isMale() || servant.getMatingCount() <= 0) {
                            return;
                        }
                        if (player.getAccountData().getMatingAuctionCoolTime() > GameTimeService.getServerTimeInMillis()) {
                            player.sendPacket(new SMRegisterServantAuctionGoodsVer2Nak(player.getAccountData().getMatingAuctionCoolTime() / 1000L));
                            return;
                        }
                        int pricePerTir = 0;
                        switch (servant.getServantSetTemplate().getTier()) {
                            case 1: {
                                pricePerTir = 900;
                                break;
                            }
                            case 2: {
                                pricePerTir = 4860;
                                break;
                            }
                            case 3: {
                                pricePerTir = 14085;
                                break;
                            }
                            case 4: {
                                pricePerTir = 42570;
                                break;
                            }
                            case 5: {
                                pricePerTir = 84690;
                                break;
                            }
                            case 6: {
                                pricePerTir = 1010520;
                                break;
                            }
                            case 7: {
                                pricePerTir = 7007760;
                                break;
                            }
                            case 8: {
                                pricePerTir = 20819880;
                                break;
                            }
                        }
                        calculatedPrice = (this.isSelfOnly ? ServantConfig.VEHICLE_SELF_MATING_PRICE : ((long) (pricePerTir + 360 * servant.getLevel())));
                        final ServantItemMarket servantItemMarket = new ServantItemMarket(servant, player.getAccountId(), calculatedPrice, this.isSelfOnly);
                        if (AuctionGoodService.getInstance().registerMatingServant(servantItemMarket)) {
                            ServantsDBCollection.getInstance().delete(this.objectId);
                            player.getAccountData().setMatingAuctionCoolTime(GameTimeService.getServerTimeInMillis() + ServantConfig.MATING_REGIST_TIME);
                            servant.setServantState(EServantState.RegisterMating);
                            servant.setAuctionRegisterType(this.auctionRegisterType);
                            ServantAuctionDBCollection.getInstance().save(servantItemMarket);
                            player.sendPacket(new SMRegisterServantAuctionGoodsVer2(servant));
                            break;
                        }
                        break;
                    }
                }
            }
        }
    }
}
