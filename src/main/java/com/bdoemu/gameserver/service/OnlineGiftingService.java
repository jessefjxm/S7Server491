package com.bdoemu.gameserver.service;

import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.core.configs.OnlineEventConfig;
import com.bdoemu.core.network.sendable.SMChat;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.scripts.commands.PcCommandHandler;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StartupComponent("Service")
public class OnlineGiftingService extends APeriodicTaskService {
	private static final Logger log = LoggerFactory.getLogger(OnlineGiftingService.class);

	private static class Holder {
		static final OnlineGiftingService INSTANCE = new OnlineGiftingService();
	}

	public static OnlineGiftingService getInstance() {
		return Holder.INSTANCE;
	}

	private OnlineGiftingService() {
		super(48L, TimeUnit.HOURS);
	}

	@Override
	public void run() {
		eventThreadAction();
	}

	private void eventThreadAction() {
		log.info("开始了在线送礼活动.");
		// 奖品id，奖品数量，附魔等级
		List<String> gifts, giftsCount, giftsEnchant;
		// 存储奖品id对应数值，防止随机后打乱
		HashMap<String, String> map;
		// 每组取的随机数量
		int giftsRandNum;

		try {
			// 1. 开场白
			broadcast("今晚的在线活动开始了！");
			Thread.sleep(10000);
			// 2.1 随机发放 武器
			broadcast("现在开始发放 武器");
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
				PcCommandHandler.giftonline(null, new String[] { gifts.get(i), "1", giftsEnchant.get(i) });
			}
			Thread.sleep(5000);
			// 2.2 随机发放 装备
			broadcast("现在开始发放 防具");
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
				PcCommandHandler.giftonline(null, new String[] { gifts.get(i), "1", giftsEnchant.get(i) });
			}
			Thread.sleep(5000);
			// 2.3 随机发放 首饰
			broadcast("现在开始发放 首饰");
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
				PcCommandHandler.giftonline(null, new String[] { gifts.get(i), "1", giftsEnchant.get(i) });
			}
			Thread.sleep(5000);
			// 2.4 随机发放 炼金石
			broadcast("现在开始发放 炼金石");
			gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_ALCHEMIST_STONE);
			Collections.shuffle(gifts);
			giftsRandNum = OnlineEventConfig.ONLINEGIFT_ALCHEMIST_STONE_AMOUNT;
			for (int i = 0; i < giftsRandNum; i++) {
				Thread.sleep(5000);
				PcCommandHandler.giftonline(null, new String[] { gifts.get(i), "1" });
			}
			Thread.sleep(5000);
			// 2.5 随机发放 烹饪材料
			broadcast("现在开始发放 烹饪材料");
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
				PcCommandHandler.giftonline(null, new String[] { gifts.get(i), map.get(gifts.get(i)) });
			}
			Thread.sleep(5000);
			// 2.6 随机发放 炼金材料
			broadcast("现在开始发放 炼金材料");
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
				PcCommandHandler.giftonline(null, new String[] { gifts.get(i), map.get(gifts.get(i)) });
			}
			Thread.sleep(5000);
			// 2.7 随机发放 抽奖材料
			broadcast("然后是激动人心的 抽奖环节");
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
				PcCommandHandler.giftonline(null, new String[] { gifts.get(i), map.get(gifts.get(i)) });
			}
			Thread.sleep(5000);
			// 3. 总人数奖励
			int onlinePlayers = World.getInstance().getPlayers().size();
			if (onlinePlayers >= 100) { // 人太少就跳过吧
				broadcast("今晚的在线人数有 " + onlinePlayers + " 人");
				Thread.sleep(5000);
				// 突破100人
				broadcast("因为突破了 100 人，发放一份额外活动奖品");
				Thread.sleep(5000);
				gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100);
				giftsCount = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100_COUNT);
				for (int i = 0; i < gifts.size(); i++) {
					Thread.sleep(5000);
					PcCommandHandler.giftonline(null, new String[] { gifts.get(i), giftsCount.get(i) });
				}
				// 突破150人
				if (onlinePlayers >= 150) {
					broadcast("因为突破了 150 人，再额外发放一份活动奖品");
					Thread.sleep(5000);
					gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100);
					giftsCount = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100_COUNT);
					for (int i = 0; i < gifts.size(); i++) {
						Thread.sleep(5000);
						PcCommandHandler.giftonline(null, new String[] { gifts.get(i), giftsCount.get(i) });
					}
				}
				// 突破200人
				if (onlinePlayers >= 200) {
					broadcast("因为突破了 200 人，再再额外发放一份活动奖品");
					Thread.sleep(5000);
					gifts = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100);
					giftsCount = Arrays.asList(OnlineEventConfig.ONLINEGIFT_100_COUNT);
					for (int i = 0; i < gifts.size(); i++) {
						Thread.sleep(5000);
						PcCommandHandler.giftonline(null, new String[] { gifts.get(i), giftsCount.get(i) });
					}
				}
				Thread.sleep(5000);
			}
			// 4. 结束语
			broadcast("感谢大家的支持，我们后天不见不散！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void broadcast(String announceText) {
		final SMChat announcePacket = new SMChat(EChatType.Notice, EChatNoticeType.None, announceText);
		World.getInstance().broadcastWorldPacket(announcePacket);
	}
}
