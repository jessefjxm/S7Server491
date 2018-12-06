package com.bdoemu.gameserver.model.ai.deprecated;

import com.bdoemu.core.configs.AIConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.ai.AIScript;
import com.bdoemu.gameserver.model.creature.Creature;
import org.atteo.classindex.ClassIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@StartupComponent("Service")
//@Deprecated
public class AIService {
    private static class Holder {
        static final AIService INSTANCE = new AIService();
    }
    private static final Logger log = LoggerFactory.getLogger(AIService.class);
    private final HashMap<String, Class<?>> ais;

    private AIService() {
        this.ais = new HashMap<>();
        for (final Class<?> clazz : ClassIndex.getAnnotated(IAIName.class)) {
            this.ais.put(clazz.getAnnotation(IAIName.class).value(), clazz);
        }

        log.info("Loaded {} AI classes.", this.ais.size());
        log.info("Created event loop with {} threads.", AIConfig.THREAD_POOL_SIZE);
    }

    public static AIService getInstance() {
        return Holder.INSTANCE;
    }

    public CreatureAI newAIHandler(final Creature actor, final String aiName) {
        if (!AIConfig.USE_LEGACY_AI)
            actor.setAiScript(new AIScript(actor));

        final Class<?> c = this.ais.get(aiName.toLowerCase());
        if (c != null) {
            try {
                return (CreatureAI) c.getDeclaredConstructor(Creature.class, Map.class).newInstance(actor, actor.getActionStorage().getAiParameters());
            } catch (Exception e) {
                AIService.log.error("Error while creating new instance of AI : " + aiName, e);
            }
        }
        return null;
    }
}
