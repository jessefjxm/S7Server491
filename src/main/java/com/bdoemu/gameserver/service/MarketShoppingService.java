package com.bdoemu.gameserver.service;

import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.core.configs.OnlineEventConfig;
import com.bdoemu.core.network.sendable.SMChat;
import com.bdoemu.core.network.sendable.SMListItemHeaderAtItemMarket;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.databaseCollections.MasterItemMarketDBCollection;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.model.creature.player.mail.services.MailService;
import com.bdoemu.gameserver.model.items.ItemMarket;
import com.bdoemu.gameserver.model.items.MasterItemMarket;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StartupComponent("Service")
public class MarketShoppingService extends APeriodicTaskService {
	private static final Logger log = LoggerFactory.getLogger(MarketShoppingService.class);

	private static class Holder {
		static final MarketShoppingService INSTANCE = new MarketShoppingService();
	}

	public static MarketShoppingService getInstance() {
		return Holder.INSTANCE;
	}

	private MarketShoppingService() {
		super(24L, TimeUnit.HOURS);
	}

	@Override
	public void run() {
		eventThreadAction();
	}

	private void eventThreadAction() {
		log.info("开始了拍卖行扫荡活动.");
		try {
			// 1. 开场白
			broadcast("神秘商人正在拍卖行疯狂扫荡珍贵的道具");
			Thread.sleep(1000);
			// 2 扫荡
			HashMap<Integer, Integer> map = new HashMap<>();
			for (int i = 0; i < OnlineEventConfig.MARKETSHOPPING_ID.length; i++) {
				map.put(Integer.parseInt(OnlineEventConfig.MARKETSHOPPING_ID[i]),
						Integer.parseInt(OnlineEventConfig.MARKETSHOPPING_PRICE[i]));
			}
			Collection<MasterItemMarket> markets = MasterItemMarketDBCollection.getInstance().load().values();
			for (MasterItemMarket m : markets) {
				// 忽略普通物品
				if (!map.containsKey(m.getItemId())) {
					continue;
				}
				int price = map.get(m.getItemId());
				for (ItemMarket item : m.getItems()) {
					if (item.getItemPrice() <= price) {
						Thread.sleep(100);
						m.buyItem(null, item, item.getCount());
						Thread.sleep(100);
						// 以下两行提取自
						// /src/main/java/com/bdoemu/gameserver/model/creature/player/itemPack/events/BuyItemAtItemMarketEvent.java
						World.getInstance().broadcastWorldPacket(
								new SMListItemHeaderAtItemMarket(Collections.singletonList(m), EPacketTaskType.Update));
						MailService.getInstance().sendMail(item.getAccountId(), 0L, "{3183609639|3119282326}",
								"{3183609639|990597375}", "{3183609639|199595621|643213191|ItemInfo:Name_"
										+ item.getItemId() + "|2207029135|" + item.getCount() + "}");
					}
				}
			}
			// 3. 结束语
			Thread.sleep(1000);
			broadcast("神秘商人心满意足地离开了拍卖行，结束了扫荡");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void broadcast(String announceText) {
		final SMChat announcePacket = new SMChat(EChatType.Notice, EChatNoticeType.None, announceText);
		World.getInstance().broadcastWorldPacket(announcePacket);
	}
}
