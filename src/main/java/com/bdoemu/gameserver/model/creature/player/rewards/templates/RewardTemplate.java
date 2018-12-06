package com.bdoemu.gameserver.model.creature.player.rewards.templates;

import com.bdoemu.gameserver.dataholders.CardData;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RewardTemplate {
    private ExpRewardT expRewardT;
    private SkillExpRewardT skillExpRewardT;
    private IntimacyRewardT intimacyReward;
    private LifeExpRewardT lifeExpRewardT;
    private int knowledgeReward;
    private List<ItemRewardT> itemRewards;

    public RewardTemplate(final String value) {
        final String[] split;
        final String[] baseRewards = split = value.split(";");
        for (final String baseReward : split) {
            if (!baseReward.trim().isEmpty()) {
                final String pattern = "(.*)\\((.*)\\)";
                final Pattern r = Pattern.compile(pattern);
                final Matcher m = r.matcher(baseReward.trim());
                if (!m.find()) {
                    throw new IllegalArgumentException("RewardTemplate not found matcher: " + baseReward + " all string: " + value);
                }
                final String lowerCase = m.group(1).toLowerCase();
                switch (lowerCase) {
                    case "item": {
                        if (this.itemRewards == null) {
                            this.itemRewards = new ArrayList<ItemRewardT>();
                        }
                        final ItemRewardT itemRewardT = new ItemRewardT(m.group(2).split(","));
                        if (ItemData.getInstance().getItemTemplate(itemRewardT.getItemId()) != null) {
                            this.itemRewards.add(itemRewardT);
                            break;
                        }
                        break;
                    }
                    case "exp": {
                        this.expRewardT = new ExpRewardT(m.group(2));
                        break;
                    }
                    case "skillexp": {
                        this.skillExpRewardT = new SkillExpRewardT(m.group(2));
                        break;
                    }
                    case "intimacy": {
                        this.intimacyReward = new IntimacyRewardT(m.group(2).split(","));
                        break;
                    }
                    case "lifeexp": {
                        this.lifeExpRewardT = new LifeExpRewardT(m.group(2).split(","));
                        break;
                    }
                    case "knowledge": {
                        final Integer knowledgeId = Integer.parseInt(m.group(2));
                        if (CardData.getInstance().getTemplate(knowledgeId) != null) {
                            this.knowledgeReward = knowledgeId;
                            break;
                        }
                        break;
                    }
                    case "coupon"  :
                    case "expgrade": break;
                    default: {
                        throw new IllegalArgumentException("new base reward type: " + m.group(1));
                    }
                }
            }
        }
    }

    public static RewardTemplate parse(final String value) {
        if (value == null) {
            return null;
        }
        return new RewardTemplate(value);
    }

    public void getRewardItems(final int selectedItemIndex, final ConcurrentLinkedQueue<Item> addItemTasks) {
        if (this.itemRewards != null) {
            if (selectedItemIndex >= 0) {
                final ItemRewardT itemRewardT = this.itemRewards.get(selectedItemIndex);
                addItemTasks.add(new Item(itemRewardT.getItemId(), itemRewardT.getCount(), itemRewardT.getEnchantLevel()));
                return;
            }
            for (final ItemRewardT itemRewardT2 : this.itemRewards) {
                addItemTasks.add(new Item(itemRewardT2.getItemId(), itemRewardT2.getCount(), itemRewardT2.getEnchantLevel()));
            }
        }
    }

    public boolean rewardPlayer(final Player player, final int questNpcId, final int npcSession) {
        if (this.expRewardT != null) {
            player.addExp(this.expRewardT.getExp());
        }
        if (this.knowledgeReward > 0) {
            player.getMentalCardHandler().updateMentalCard(this.knowledgeReward, ECardGradeType.C);
        }
        if (this.intimacyReward != null && npcSession > 0 && questNpcId > 0) {
            final int npcId = this.intimacyReward.getNpcId();
            if (npcId == questNpcId && this.intimacyReward.getIntimacy() > 0) {
                player.getIntimacyHandler().updateIntimacy(npcId, npcSession, this.intimacyReward.getIntimacy());
            }
        }
        if (this.skillExpRewardT != null) {
            player.getSkillList().addSkillExp(this.skillExpRewardT.getExp());
        }
        if (this.lifeExpRewardT != null) {
            player.getLifeExperienceStorage().getLifeExperience(this.lifeExpRewardT.getLifeType()).addExp(player, this.lifeExpRewardT.getLifeExp());
        }
        return true;
    }
}
