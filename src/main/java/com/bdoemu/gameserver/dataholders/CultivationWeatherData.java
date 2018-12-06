package com.bdoemu.gameserver.dataholders;

import com.bdoemu.commons.reload.IReloadable;
import com.bdoemu.commons.reload.Reloadable;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.weather.CultivationWeatherT;
import com.bdoemu.gameserver.model.weather.enums.EWeatherKind;
import com.bdoemu.gameserver.model.weather.services.WeatherService;
import com.bdoemu.gameserver.service.database.SQLiteDatabaseFactory;
import org.apache.commons.lang3.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Reloadable(name = "cultivationweather", group = "sqlite")
@StartupComponent("Data")
public class CultivationWeatherData implements IReloadable {
    private static class Holder {
        static final CultivationWeatherData INSTANCE = new CultivationWeatherData();
    }
    private static final Logger log = LoggerFactory.getLogger(CultivationWeatherData.class);
    private Map<Integer, Map<EWeatherKind, List<CultivationWeatherT>>> table;

    private CultivationWeatherData() {
        this.table = new HashMap<>();
        this.load();
    }

    public static CultivationWeatherData getInstance() {
        return Holder.INSTANCE;
    }

    public void load() {
        ServerInfoUtils.printSection("CultivationWeatherData Loading");
        try (final Connection con = SQLiteDatabaseFactory.getInstance().getCon();
             final Statement statement = con.createStatement();
             final ResultSet rs = statement.executeQuery("SELECT * FROM 'CultivationWeather_Table'")) {
            while (rs.next()) {
                final int exchangeKey = rs.getInt("ExchangeKey");
                final EWeatherKind weatherKind = EWeatherKind.values()[rs.getInt("WeatherKind")];
                final Range<Integer> weatherRange = Range.between(rs.getInt("WeatherStartRate"), rs.getInt("WeatherEndRate"));
                final Integer productSpeedRate = rs.getInt("ProductSpeedRate");
                final Map<EWeatherKind, List<CultivationWeatherT>> weatherMap = this.table.computeIfAbsent(exchangeKey, item -> new HashMap<>());
                final List<CultivationWeatherT> cultivationList = weatherMap.computeIfAbsent(weatherKind, item -> new ArrayList<>());
                cultivationList.add(new CultivationWeatherT(weatherRange, productSpeedRate));
            }
            CultivationWeatherData.log.info("Loaded {} CultivationWeatherData's", this.table.values().stream().mapToInt(item -> item.values().size()).sum());
        } catch (SQLException ex) {
            CultivationWeatherData.log.error("Failed load CultivationWeatherT", ex);
        }
    }

    public int getProductSpeedRate(final int productId) {
        int result = 0;
        if (this.table.containsKey(productId)) {
            for (final Map.Entry<EWeatherKind, List<CultivationWeatherT>> entry : this.table.get(productId).entrySet()) {
                final int weatherValue = WeatherService.getInstance().getWeatherKindValue(entry.getKey());
                for (final CultivationWeatherT cultivationWeatherT : entry.getValue()) {
                    if (cultivationWeatherT.getRange().contains(weatherValue)) {
                        result += cultivationWeatherT.getProductSpeedRate();
                        break;
                    }
                }
            }
        }
        return result;
    }

    public void reload() {
        this.load();
    }
}
