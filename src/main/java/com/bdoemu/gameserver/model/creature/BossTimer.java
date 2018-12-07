package com.bdoemu.gameserver.model.creature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.bdoemu.core.configs.BossInfoConfig;

public class BossTimer {
	private static HashMap<Integer, Long> timer = new HashMap<>();

	public static void UpdateAction(long time) {
		// 服务器刚启动，自己算吧
		for (int i = 0; i < BossInfoConfig.BOSSINFO_ID.length; i++) {
			int id = Integer.parseInt(BossInfoConfig.BOSSINFO_ID[i]);
			int interval = Integer.parseInt(BossInfoConfig.BOSSINFO_INTERVAL[i]);
			timer.put(id, time + interval);
		}
	}

	public static void UpdateAction(int i, long time) {
		// 我们只关心BOSS
		if(!timer.containsKey(i))
			return;
		// BOSS挂了，准备复活
		timer.put(i, time);
	}

	public static void output() {
		String dir = BossInfoConfig.BOSSINFO_PATH;
		File writeName = new File(dir);
		try {
			FileWriter writer = new FileWriter(writeName);
			BufferedWriter out = new BufferedWriter(writer);
			for (int i : timer.keySet()) {
				out.write(i + "," + timer.get(i));
				out.write("\r\n");
			}
			out.flush(); // 把缓存区内容压入文件
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
