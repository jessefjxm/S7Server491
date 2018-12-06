// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network;

import com.bdoemu.commons.network.handler.XmlPacketHandlerFactory;
import com.bdoemu.core.startup.StartupComponent;

import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Network")
public class GamePacketFactory extends XmlPacketHandlerFactory<GameClient> {
    private static final AtomicReference<Object> instance;

    static {
        instance = new AtomicReference<Object>();
    }

    public static GamePacketFactory getInstance() {
        Object value = GamePacketFactory.instance.get();
        if (value == null) {
            synchronized (GamePacketFactory.instance) {
                value = GamePacketFactory.instance.get();
                if (value == null) {
                    final GamePacketFactory actualValue = new GamePacketFactory();
                    value = ((actualValue == null) ? GamePacketFactory.instance : actualValue);
                    GamePacketFactory.instance.set(value);
                }
            }
        }
        return (GamePacketFactory) ((value == GamePacketFactory.instance) ? null : value);
    }
}
