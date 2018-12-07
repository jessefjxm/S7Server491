package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/bossinfo.properties")
public class BossInfoConfig {
	@ConfigComments(comment = { "要跟踪的BOSS的ID", "格式：ID;ID ……以此类推" })
	@ConfigProperty(name = "bossinfo.id", value = "1001;1002;1003")
	public static String[] BOSSINFO_ID;
	@ConfigComments(comment = { "要跟踪的BOSS的刷新间隔，用于确定第一次刷新时间，单位是小时", "必须和上面的BOSS ID一一对应", "格式：小时;小时 ……以此类推" })
	@ConfigProperty(name = "bossinfo.interval", value = "4;2;6")
	public static String[] BOSSINFO_INTERVAL;

	@ConfigComments(comment = { "BOSS刷新时间的记录文件的位置", "格式：目录" })
	@ConfigProperty(name = "bossinfo.path", value = "output/boss.timer")
	public static String BOSSINFO_PATH;
}
