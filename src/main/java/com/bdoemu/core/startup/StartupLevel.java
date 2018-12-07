package com.bdoemu.core.startup;

import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.commons.utils.versioning.Version;
import com.bdoemu.core.network.GameNetworkThread;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.management.ManagementFactory;

public enum StartupLevel implements IStartupLevel {
	BeforeStart {
		public void invokeDepends() {
			Version.getInstance().init((Class<?>) this.getClass());
		}
	},
	Configure, Threading, Database, Service, Data, World, Network, AfterStart {
		public void invokeDepends() {
			SpawnService.getInstance().spawnAll();
			System.gc();
			System.runFinalization();
			for (final String line : ServerInfoUtils.getMemUsage()) {
				StartupLevel.log.info(line);
			}
			try {
				GameNetworkThread.getInstance().startup();
			} catch (IOException | InterruptedException e) {
				StartupLevel.log.error("Error while starting network thread", e);
			}
			StartupLevel.log.info("Server loaded in {} millisecond(s).",
					ServerInfoUtils.formatNumber(ManagementFactory.getRuntimeMXBean().getUptime()));
		}
	};

	private static final Logger log = LoggerFactory.getLogger(StartupLevel.class);
}
