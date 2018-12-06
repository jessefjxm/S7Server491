package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.dataholders.ActionEXPData;
import com.bdoemu.gameserver.dataholders.AlchemyData;
import com.bdoemu.gameserver.dataholders.KnowledgeLearningData;
import com.bdoemu.gameserver.dataholders.LifeActionEXPData;
import com.bdoemu.gameserver.model.alchemy.AlchemyRecord;
import com.bdoemu.gameserver.model.alchemy.enums.EAlchemyType;
import com.bdoemu.gameserver.model.alchemy.templates.AlchemyT;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Knowledge.templates.KnowledgeLearningT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.action.ActionEXPT;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;

import java.util.List;

public class StartAlchemyItemEvent extends AItemEvent {
    private int size;
    private long[][] sts;
    private AlchemyT alchemyT;
    private EAlchemyType alchemyType;

    public StartAlchemyItemEvent(final Player player, final EAlchemyType alchemyType, final int size, final long[][] sts) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.size = size;
        this.sts = sts;
        this.alchemyType = alchemyType;
    }

    public AlchemyT getAlchemyT() {
        return this.alchemyT;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (this.addTasks != null && !this.addTasks.isEmpty()) {
            final Item addItem = this.addTasks.peek();
            LifeActionEXPData.getInstance().reward(this.player, addItem.getItemId(), this.alchemyType.isAlchemy() ? ELifeExpType.Alchemy : ELifeExpType.Cook);
            final KnowledgeLearningT knowledgeLearningT = KnowledgeLearningData.getInstance().getTemplate(addItem.getItemId());
            if (knowledgeLearningT != null && Rnd.getChance(knowledgeLearningT.getSelectRate(), 10000)) {
                this.player.getAlchemyRecordStorage().addNewAlchemyRecord(knowledgeLearningT.getKnowledgeIndex(), new AlchemyRecord(this.sts, this.size));
                this.player.getMentalCardHandler().updateMentalCard(knowledgeLearningT.getKnowledgeIndex(), ECardGradeType.C);
            }
            this.player.getObserveController().notifyObserver(EObserveType.gatherItem, addItem.getItemId(), addItem.getEnchantLevel(), addItem.getCount(), player.getObjectId());
            final ActionEXPT actionEXPT = ActionEXPData.getInstance().getExpTemplate(this.player, this.player.getLifeExperienceStorage().getLifeExperience(this.alchemyType.isAlchemy() ? ELifeExpType.Alchemy : ELifeExpType.Cook).getLevel());
            if (actionEXPT != null) {
                this.player.addExp(actionEXPT.getAlchemy());
            }
        }
    }

    @Override
    public boolean canAct() {
        if (this.size <= 0 || this.size >= 9)
            return false;

        // We need to filter out trash items that are inside our cooking utensil.
        for (int i = 0; i < this.size; ++i) {
            final int slotIndex = (int) this.sts[i][0];                        // Slot Index, used
            final long count = this.sts[i][1];
            final Item item = this.playerInventory.getItem(slotIndex);
            if (item == null)
                return false;
            this.sts[i][0] = item.getItemId();
            this.decreaseItem(slotIndex, count, this.playerInventory.getLocationType());
        }
        this.alchemyT = AlchemyData.getInstance().getAlchemyT(this.alchemyType, this.size, this.sts);
        if (this.alchemyT != null) {
            final List<ItemSubGroupT> items = ItemMainGroupService.getItems(this.player, this.alchemyT.getResultDropGroup());
            for (final ItemSubGroupT item2 : items) {
                this.addItem(item2.getItemId(), Rnd.get(item2.getMinCount(), item2.getMaxCount()), item2.getEnchantLevel());
            }
        }
        return super.canAct();
    }
}