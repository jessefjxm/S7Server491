// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.enums;

public enum EContentsEventType {
    Housing(0),
    SkillInitialize(1),
    NpcWorker(2),
    Mail(3),
    GuildMark(4),
    Campfire(5),
    ItemBox(6),
    ChangeServantName(7),
    ChangeServantSkill(8),
    CollectByTool(9),
    CarveSeal(10),
    ResetSeal(11),
    PetRegister(12),
    PetFood(13),
    ClearVested(14),
    Respawn(15),
    OrderToServant(16),
    UseItemToWorker(17),
    ChangeNickName(18),
    ClearServantDeadCount(19),
    ClearServantMatingCount(20),
    ImprintServant(21),
    Roulette(22),
    RemoveKnowledge(23),
    HelpReward(24),
    SummonCharacter(25),
    ReleaseImprintServant(26),
    HelpEndurance(27),
    TransferLifeExperience(28),
    ChangeFormServant(29),
    ItemChange(30),
    CustomizedItemBox(31),
    Stone(32),
    ServantSkillExpTraining(33),
    ConvertEnchantFailCountToItem(34),
    ConvertEnchantFailItemToCount(35),
    ForgetServantSkill(36),
    Rubbing(37),
    ItemExchangeToClass(38),
    AddGuildQuestReward(39),
    SellWarehouseItemToSystem(40),
    PetChangeLook(41),
    LanternAttribute(42),
    Seal(43),
    SkillAwakeningPeriod(44),
    Banquet(45),
    ChannelBattleExpItem(46),
    Warp(47),
    ExchangeBattleAndSkillExp(48),
    SealItemBox(49),
    SealItemBoxKey(50),
    InventoryBag(51);

    private byte id;

    private EContentsEventType(final int id) {
        this.id = (byte) id;
    }

    public static EContentsEventType valueOf(final int id) {
        for (final EContentsEventType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public boolean isCampfire() {
        return this == EContentsEventType.Campfire;
    }

    public boolean isClearServantDeadCount() {
        return this == EContentsEventType.ClearServantDeadCount;
    }

    public boolean isImprintServant() {
        return this == EContentsEventType.ImprintServant;
    }

    public boolean isReleaseImprintServant() {
        return this == EContentsEventType.ReleaseImprintServant;
    }

    public boolean isClearServantMatingCount() {
        return this == EContentsEventType.ClearServantMatingCount;
    }

    public boolean isChangeFormServant() {
        return this == EContentsEventType.ChangeFormServant;
    }

    public boolean isServantSkillExpTraining() {
        return this == EContentsEventType.ServantSkillExpTraining;
    }

    public boolean isChangeServantSkill() {
        return this == EContentsEventType.ChangeServantSkill;
    }

    public boolean isForgetServantSkill() {
        return this == EContentsEventType.ForgetServantSkill;
    }

    public boolean isHelpEndurance() {
        return this == EContentsEventType.HelpEndurance;
    }

    public boolean isOrderToServant() {
        return this == EContentsEventType.OrderToServant;
    }

    public boolean isLantern() {
        return this == EContentsEventType.LanternAttribute;
    }

    public boolean isRespawn() {
        return this == EContentsEventType.Respawn;
    }

    public boolean isCollectByTool() {
        return this == EContentsEventType.CollectByTool;
    }

    public boolean isChangeNickName() {
        return this == EContentsEventType.ChangeNickName;
    }

    public boolean isRubbing() {
        return this == EContentsEventType.Rubbing;
    }

    public boolean isStone() {
        return this == EContentsEventType.Stone;
    }

    public boolean isUseItemToWorker() {
        return this == EContentsEventType.UseItemToWorker;
    }

    public boolean isSkillInitialize() {
        return this == EContentsEventType.SkillInitialize;
    }

    public boolean isPetRegister() {
        return this == EContentsEventType.PetRegister;
    }

    public boolean isPetFood() {
        return this == EContentsEventType.PetFood;
    }

    public boolean isItemBox() {
        return this == EContentsEventType.ItemBox || this == EContentsEventType.CustomizedItemBox || this == EContentsEventType.Roulette;
    }

    public boolean isRoulette() {
        return this == EContentsEventType.Roulette;
    }

    public boolean isSummonCharacter() {
        return this == EContentsEventType.SummonCharacter;
    }

    public boolean isHelpReward() {
        return this == EContentsEventType.HelpReward;
    }

    public boolean isEnchantFail() {
        return this == EContentsEventType.ConvertEnchantFailItemToCount;
    }

    public boolean isItemChange() {
        return this == EContentsEventType.ItemChange;
    }

    public boolean isItemExchangeToClass() {
        return this == EContentsEventType.ItemExchangeToClass;
    }

    public boolean isRemoveKnowledge() {
        return this == EContentsEventType.RemoveKnowledge;
    }

    public boolean isGuildMark() {
        return this == EContentsEventType.GuildMark;
    }

    public boolean isTransferLifeExperience() {
        return this == EContentsEventType.TransferLifeExperience;
    }

    public byte getId() {
        return this.id;
    }
}
