package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.service.MarketShoppingService;
import com.bdoemu.gameserver.service.OnlineGiftingService;

import org.apache.commons.lang3.text.StrBuilder;

@CommandHandler(prefix = "event", accessLevel = EAccessLevel.ADMIN)
public class EventCommandHandler extends AbstractCommandHandler {
	private static StrBuilder helpBuilder;

	static {
		(EventCommandHandler.helpBuilder = new StrBuilder()).appendln("可用指令:");
		EventCommandHandler.helpBuilder.appendln("event onlinegift - 在线送礼活动");
		EventCommandHandler.helpBuilder.appendln("event marketshopping - 扫荡拍卖行");
		EventCommandHandler.helpBuilder.appendln("event [更多脑洞？]");
	}

	@CommandHandlerMethod
	public static Object[] index(final Player player, final String... params) {
		return AbstractCommandHandler.getAcceptResult(EventCommandHandler.helpBuilder.toString());
	}

	@CommandHandlerMethod
	public static Object[] onlinegift(final Player player, final String... params) {
		return OnlineGiftingService.getInstance().startEvent(player)
				? AbstractCommandHandler.getAcceptResult("在线送礼活动 已顺利完成.")
				: AbstractCommandHandler.getAcceptResult("在线送礼活动 已在进行中！无法开始.");
	}

	@CommandHandlerMethod
	public static Object[] marketshopping(final Player player, final String... params) {
		return MarketShoppingService.getInstance().startEvent(player)
				? AbstractCommandHandler.getAcceptResult("扫荡拍卖行 已顺利完成.")
				: AbstractCommandHandler.getAcceptResult("扫荡拍卖行 已在进行中！无法开始.");
	}
}
