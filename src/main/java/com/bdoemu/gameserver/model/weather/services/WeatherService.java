package com.bdoemu.gameserver.model.weather.services;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMChangeWeathers;
import com.bdoemu.core.network.sendable.SMInitializeRegionWeatherData;
import com.bdoemu.core.network.sendable.SMInitializeWeatherData;
import com.bdoemu.core.network.sendable.SMUpdateWeatherManager;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.weather.enums.EWeatherKind;
import com.bdoemu.gameserver.service.GameTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Service")
public class WeatherService extends APeriodicTaskService {
    private static class Holder {
        static final WeatherService INSTANCE = new WeatherService();
    }

    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) WeatherService.class);
        instance = new AtomicReference<Object>();
    }

    private List<float[]> _weatherData;
    private List<Long> _cultivationWeatherData; // TODO
    private long _lastWeatherUpdate;

    private WeatherService() {
        super(30L, TimeUnit.SECONDS);
        _lastWeatherUpdate = GameTimeService.getServerTimeInMillis();
        _weatherData = new ArrayList<>();
        for (int i = 0; i < 1/*1356 * 48 + 1332*/; ++i) {
            int rand = ThreadLocalRandom.current().nextInt(0, 100);

            _weatherData.add(new float[]{
                    0.1f, //0.3f + ThreadLocalRandom.current().nextInt(1, 1000001) / 250000000.0f, 													// Rain Rate
                    0.1f, //rand > 90 ? 1f : (rand > 60 ? 0.6f : 0.3f), //0.035f + ThreadLocalRandom.current().nextInt(1, 101) / 100000.0f, 					// Cloud Rate
                    0.1f, //ThreadLocalRandom.current().nextInt(0, 100) / 100f //ThreadLocalRandom.current().nextInt(0, 250000) > 50000 ? 0.1f : 0.0f	// Rain Amount
            });
        }
    }

    public static WeatherService getInstance() {
        return Holder.INSTANCE;
    }

    public int getWeatherKindValue(final EWeatherKind weatherKind) {
        return Rnd.get(0, 100);
    }

    public void run() {
        _lastWeatherUpdate = GameTimeService.getServerTimeInMillis();
        //final SMUpdateWeatherManager packet = new SMUpdateWeatherManager();
        //for (final Player player : World.getInstance().getPlayers()) {
        //    player.sendPacket(packet);
        //}
    }

    public void onLogin(final Player player) {
        // disabled for now
    }

    public void enableWeatherCommand(Player player) {
        player.sendPacketNoFlush(new SMInitializeRegionWeatherData());
        final ListSplitter<float[]> splitter = new ListSplitter<>(_weatherData, SMInitializeWeatherData.MAXIMUM);
        while (splitter.hasNext())
            player.sendPacketNoFlush(new SMInitializeWeatherData(this._lastWeatherUpdate, splitter.getNext()));
        player.sendPacketNoFlush(new SMChangeWeathers(0L, 0L));
        player.sendPacketNoFlush(new SMUpdateWeatherManager());
    }
}
