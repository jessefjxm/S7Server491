// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network;

import com.bdoemu.commons.network.Connection;
import com.bdoemu.commons.network.IClientFactory;
import com.bdoemu.core.startup.StartupComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Network")
public class GameClientFactory implements IClientFactory<GameClient> {
    private static final Logger log;
    private static final AtomicReference<Object> instance;

    static {
        log = LoggerFactory.getLogger((Class) GameClientFactory.class);
        instance = new AtomicReference<Object>();
    }

    public static GameClientFactory getInstance() {
        Object value = GameClientFactory.instance.get();
        if (value == null) {
            synchronized (GameClientFactory.instance) {
                value = GameClientFactory.instance.get();
                if (value == null) {
                    final GameClientFactory actualValue = new GameClientFactory();
                    value = ((actualValue == null) ? GameClientFactory.instance : actualValue);
                    GameClientFactory.instance.set(value);
                }
            }
        }
        return (GameClientFactory) ((value == GameClientFactory.instance) ? null : value);
    }

    public GameClient createClient(final Connection<GameClient> connection) {
        return new GameClient(connection);
    }
}
