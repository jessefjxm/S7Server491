package com.bdoemu.gameserver.service;

import com.bdoemu.core.configs.OnlineEventConfig;
import com.bdoemu.gameserver.databaseCollections.MasterItemMarketDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.ItemMarket;
import com.bdoemu.gameserver.model.items.MasterItemMarket;
import com.bdoemu.gameserver.scripts.commands.PcCommandHandler;

import java.util.Collection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarketShoppingService extends Thread {
	private static final Logger log = LoggerFactory.getLogger(MarketShoppingService.class);
	private boolean isEventRunning;
	private Player player;

	private static class Holder {
		static final MarketShoppingService INSTANCE = new MarketShoppingService();
	}

	public static MarketShoppingService getInstance() {
		return Holder.INSTANCE;
	}

	private MarketShoppingService() {
		this.isEventRunning = false;
	}

	private MarketShoppingService(Player player) {
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
			new MarketShoppingService(player).start();
			return true;
		} else {
			return false;
		}
	}

	private synchronized void eventThreadAction() {
		log.info("开始了拍卖行扫荡活动.");
		isEventRunning = true;

		try {
			// 1. 开场白
			PcCommandHandler.announce(player, new String[] { "神秘商人正在拍卖行疯狂扫荡珍贵的道具" });
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
						m.buyItem(player, item, item.getCount());
					}
				}
			}
			// 3. 结束语
			Thread.sleep(1000);
			PcCommandHandler.announce(player, new String[] { "神秘商人心满意足地离开了拍卖行，结束了扫荡" });
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		isEventRunning = false;
	}
}
