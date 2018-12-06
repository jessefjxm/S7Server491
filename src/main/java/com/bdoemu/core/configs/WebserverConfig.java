package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/webserver.properties")
public class WebserverConfig {
    @ConfigComments(comment = {"Webserver enable.", "Default: true"})
    @ConfigProperty(name = "enable", value = "true")
    public static boolean ENABLE;
    @ConfigComments(comment = {"Webserver debug (removing some webclient restrictions).", "Default: false"})
    @ConfigProperty(name = "debug", value = "false")
    public static boolean DEBUG;
    @ConfigComments(comment = {"Webserver port.", "Default: 8089"})
    @ConfigProperty(name = "port", value = "8089")
    public static int PORT;
    @ConfigComments(comment = {"Minimum webserver thread's count.", "Default: 2"})
    @ConfigProperty(name = "thread_count_min", value = "2")
    public static int THREAD_COUNT_MIN;
    @ConfigComments(comment = {"Maximum webserver thread's count.", "Default: 8"})
    @ConfigProperty(name = "thread_count_max", value = "8")
    public static int THREAD_COUNT_MAX;
    @ConfigComments(comment = {"Webserver request timeout.", "Default: 30000"})
    @ConfigProperty(name = "timeout", value = "30000")
    public static int TIMEOUT;
    @ConfigComments(comment = {"Webserver data path.", "Default: data/web/"})
    @ConfigProperty(name = "data_path", value = "data/web/")
    public static String DATA_PATH;
}
