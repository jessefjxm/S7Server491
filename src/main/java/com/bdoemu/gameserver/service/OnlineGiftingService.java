package com.bdoemu.gameserver.service;

import com.bdoemu.core.configs.OnlineEventConfig;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.scripts.commands.PcCommandHandler;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnlineGiftingService extends Thread {
	private static final Logger log = LoggerFactory.getLogger(OnlineGiftingService.class);
	private boolean isEventRunning;
	private Player player;

	private static class Holder {
		static final OnlineGiftingService INSTANCE = new OnlineGiftingService();
	}

	public static OnlineGiftingService getInstance() {
		return Holder.INSTANCE;
	}

	private OnlineGiftingService() {
		this.isEventRunning = false;
	}

	private OnlineGiftingService(Player player) {
		this.isEventRunning = false;
		this.player = player;
	}

	@Override
	public void run() {
		eventThreadAction();
	}

	public boolean isEventRunning() {
		return isEventRunning;
	}

	public boolean startEvent(Player player) {
		if (player != null && !isEventRunning) {
			// 建立一个活动线程
			new OnlineGiftingService(player).start();
			return true;
		} else {
			return false;
		}
	}

	private synchronized void eventThreadAction() {
		log.info("开始了在线送礼活动.");
		isEventRunning = true;
		// 奖品id，奖品数量，附魔等级
		List<String> gifts, giftsCount, giftsEnchant;
		// 存储奖品id对应数值，防止随机后打乱
		HashMap<String, String> map;
		// 每组取的随机数量
		int giftsRandNum;

		try {
			// 1. 开场白
			PcCommandHandler.announce(player, new String[] { "今晚的在线活动开始了！" });
			Thread.sleep(10000);
			// 2.1 随机发放 武器
			PcCommandHandler.announce(player, new String[] { "现在开始发放 武器" });
			gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_WEAPON);
			giftsEnchant = Arrays.asList(OnlineEventConfig.ONLINEGIFT_WEAPON_ENCHANT);
			map = new HashMap<>();
			for (int i = 0; i < gifts.size(); i++) {
				map.put(gifts.get(i), giftsEnchant.get(i));
			}
			Collections.shuffle(gifts);
			giftsRandNum = OnlineEventConfig.ONLINEGIFT_WEAPON_AMOUNT;
			for (int i = 0; i < giftsRandNum; i++) {
				Thread.sleep(5000);
				PcCommandHandler.giftonline(player, new String[] { gifts.get(i), "1", giftsEnchant.get(i) });
			}
			Thread.sleep(5000);
			// 2.2 随机发放 装备
			PcCommandHandler.announce(player, new String[] { "现在开始发放 防具" });
			gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_ARMOR);
			giftsEnchant = Arrays.asList(OnlineEventConfig.ONLINEGIFT_ARMOR_ENCHANT);
			map = new HashMap<>();
			for (int i = 0; i < gifts.size(); i++) {
				map.put(gifts.get(i), giftsEnchant.get(i));
			}
			Collections.shuffle(gifts);
			giftsRandNum = OnlineEventConfig.ONLINEGIFT_ARMOR_AMOUNT;
			for (int i = 0; i < giftsRandNum; i++) {
				Thread.sleep(5000);
				PcCommandHandler.giftonline(player, new String[] { gifts.get(i), "1", giftsEnchant.get(i) });
			}
			Thread.sleep(5000);
			// 2.3 随机发放 首饰
			PcCommandHandler.announce(player, new String[] { "现在开始发放 首饰" });
			gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_ACCESSORY);
			giftsEnchant = Arrays.asList(OnlineEventConfig.ONLINEGIFT_ACCESSORY_ENCHANT);
			map = new HashMap<>();
			for (int i = 0; i < gifts.size(); i++) {
				map.put(gifts.get(i), giftsEnchant.get(i));
			}
			Collections.shuffle(gifts);
			giftsRandNum = OnlineEventConfig.ONLINEGIFT_ACCESSORY_AMOUNT;
			for (int i = 0; i < giftsRandNum; i++) {
				Thread.sleep(5000);
				PcCommandHandler.giftonline(player, new String[] { gifts.get(i), "1", giftsEnchant.get(i) });
			}
			Thread.sleep(5000);
			// 2.4 随机发放 炼金石
			PcCommandHandler.announce(player, new String[] { "现在开始发放 炼金石" });
			gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_ALCHEMIST_STONE);
			Collections.shuffle(gifts);
			giftsRandNum = OnlineEventConfig.ONLINEGIFT_ALCHEMIST_STONE_AMOUNT;
			for (int i = 0; i < giftsRandNum; i++) {
				Thread.sleep(5000);
				PcCommandHandler.giftonline(player, new String[] { gifts.get(i), "1" });
			}
			Thread.sleep(5000);
			// 2.5 随机发放 烹饪材料
			PcCommandHandler.announce(player, new String[] { "现在开始发放 烹饪材料" });
			gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_INGREDIENT);
			giftsCount = Arrays.asList(OnlineEventConfig.ONLINEGIFT_INGREDIENT_COUNT);
			map = new HashMap<>();
			for (int i = 0; i < gifts.size(); i++) {
				map.put(gifts.get(i), giftsCount.get(i));
			}
			Collections.shuffle(gifts);
			giftsRandNum = OnlineEventConfig.ONLINEGIFT_INGREDIENT_AMOUNT;
			for (int i = 0; i < giftsRandNum; i++) {
				Thread.sleep(5000);
				PcCommandHandler.giftonline(player, new String[] { gifts.get(i), map.get(gifts.get(i)) });
			}
			Thread.sleep(5000);
			// 2.6 随机发放 炼金材料
			PcCommandHandler.announce(player, new String[] { "现在开始发放 炼金材料" });
			gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_ALCHEMY_MATERIAL);
			giftsCount = Arrays.asList(OnlineEventConfig.ONLINEGIFT_ALCHEMY_MATERIAL_COUNT);
			map = new HashMap<>();
			for (int i = 0; i < gifts.size(); i++) {
				map.put(gifts.get(i), giftsCount.get(i));
			}
			Collections.shuffle(gifts);
			giftsRandNum = OnlineEventConfig.ONLINEGIFT_ALCHEMY_MATERIAL_AMOUNT;
			for (int i = 0; i < giftsRandNum; i++) {
				Thread.sleep(5000);
				PcCommandHandler.giftonline(player, new String[] { gifts.get(i), map.get(gifts.get(i)) });
			}
			Thread.sleep(5000);
			// 2.7 随机发放 抽奖材料
			PcCommandHandler.announce(player, new String[] { "然后是激动人心的 抽奖环节" });
			gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_LOTTERY);
			giftsCount = Arrays.asList(OnlineEventConfig.ONLINEGIFT_LOTTERY_COUNT);
			map = new HashMap<>();
			for (int i = 0; i < gifts.size(); i++) {
				map.put(gifts.get(i), giftsCount.get(i));
			}
			Collections.shuffle(gifts);
			giftsRandNum = OnlineEventConfig.ONLINEGIFT_LOTTERY_AMOUNT;
			for (int i = 0; i < giftsRandNum; i++) {
				Thread.sleep(5000);
				PcCommandHandler.giftonline(player, new String[] { gifts.get(i), map.get(gifts.get(i)) });
			}
			Thread.sleep(5000);
			// 3. 总人数奖励
			int onlinePlayers = World.getInstance().getPlayers().size();
			if (onlinePlayers >= 100) { // 人太少就跳过吧
				PcCommandHandler.announce(player, new String[] { "今晚的在线人数有 " + onlinePlayers + " 人" });
				Thread.sleep(5000);
				// 突破100人
				PcCommandHandler.announce(player, new String[] { "因为突破了 100 人，发放一份额外活动奖品" });
				Thread.sleep(5000);
				gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100);
				giftsCount = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100_COUNT);
				for (int i = 0; i < gifts.size(); i++) {
					Thread.sleep(5000);
					PcCommandHandler.giftonline(player, new String[] { gifts.get(i), giftsCount.get(i) });
				}
				// 突破150人
				if (onlinePlayers >= 150) {
					PcCommandHandler.announce(player, new String[] { "因为突破了 150 人，再额外发放一份活动奖品" });
					Thread.sleep(5000);
					gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100);
					giftsCount = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100_COUNT);
					for (int i = 0; i < gifts.size(); i++) {
						Thread.sleep(5000);
						PcCommandHandler.giftonline(player, new String[] { gifts.get(i), giftsCount.get(i) });
					}
				}
				// 突破200人
				if (onlinePlayers >= 200) {
					PcCommandHandler.announce(player, new String[] { "因为突破了 200 人，再再额外发放一份活动奖品" });
					Thread.sleep(5000);
					gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100);
					giftsCount = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100_COUNT);
					for (int i = 0; i < gifts.size(); i++) {
						Thread.sleep(5000);
						PcCommandHandler.giftonline(player, new String[] { gifts.get(i), giftsCount.get(i) });
					}
				}
				Thread.sleep(5000);
			}
			// 4. 结束语
			PcCommandHandler.announce(player, new String[] { "感谢大家的支持，我们后天不见不散！" });
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		isEventRunning = false;
	}
}
