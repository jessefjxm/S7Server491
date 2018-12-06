package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/playerbag.properties")
public class PlayerBagConfig {
    @ConfigComments(comment = {"Guild base warehouse size."})
    @ConfigProperty(name = "guild.warehouse_base_size", value = "1")
    public static int GUILD_WAREHOUSE_BASE_SIZE;
    @ConfigComments(comment = {"Guild maximum warehouse size."})
    @ConfigProperty(name = "guild.warehouse_max_size", value = "1")
    public static int GUILD_WAREHOUSE_MAX_SIZE;
    @ConfigComments(comment = {"Player maximum inventory size."})
    @ConfigProperty(name = "player.inventory_base_size", value = "18")
    public static int INVENTORY_BASE_SIZE;
    @ConfigComments(comment = {"Player maximum inventory size."})
    @ConfigProperty(name = "player.inventory_max_size", value = "194")
    public static int INVENTORY_MAX_SIZE;
    @ConfigComments(comment = {"Player base warehouse size.", "Note: Included reserved slot for silver."})
    @ConfigProperty(name = "player.warehouse_base_size", value = "17")
    public static int WAREHOUSE_BASE_SIZE;
    @ConfigComments(comment = {"Player maximum warehouse size."})
    @ConfigProperty(name = "player.warehouse_max_size", value = "194")
    public static int WAREHOUSE_MAX_SIZE;
    @ConfigComments(comment = {"Player base cash inventory size."})
    @ConfigProperty(name = "player.cash_inventory_base_size", value = "194")
    public static int CASH_INVENTORY_BASE_SIZE;
    @ConfigComments(comment = {"Player maximum cash inventory size."})
    @ConfigProperty(name = "player.cash_maximum_base_size", value = "194")
    public static int CASH_INVENTORY_MAX_SIZE;
}
