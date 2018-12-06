package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.chat.enums.EChatResponseType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.weather.services.WeatherService;


/**
 * @author H1X4
 */
@CommandHandler(prefix = "weather")
public class WeatherCommandHandler extends AbstractCommandHandler {
    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        if (player.isWeatherEnabled()) {
            return new Object[] { EChatResponseType.Rejected, "Weather is already enabled." };
        } else {
            player.setWeather(true);
            WeatherService.getInstance().enableWeatherCommand(player);
        }
        return new Object[]{EChatResponseType.Accepted, "Weather has been enabled."};
    }
}
