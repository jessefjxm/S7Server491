package com.bdoemu.gameserver.model.ai.services;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.configs.AIConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.ai.AIScript;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.worldInstance.World;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * This is an AI Service Provider. It provides paths to
 * the actual AI script and manages unused memory, creates new AI script readers.
 *
 * @author H1X4
 */
@StartupComponent("Service")
public class AIServiceProvider {

    /**
     * This class holds the current object.
     * It is only constructed once and thread-safe initialization.
     *
     * Synchronization is NOT required.
     */
    private static class Holder {
        static final AIServiceProvider INSTANCE = new AIServiceProvider();
    }

    /**
     * Returns a static synchronized on-initialization object.
     *
     * @return AIScript loader service.
     */
    public static AIServiceProvider getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * List for current AI scripts. Map<\ScriptClassName, ScriptPath>
     */
    private Map<String, String> _aiScripts = null;

    /**
     * Event loop that manages and executes all events within a pool.
     */
    private ExecutorService _aiEventLoop = null;

    /**
     * In order to compensate the delay between all AI, we need to
     * add a compensation such as DeltaTime.
     */
    private long _loopStartTime = 0;

    /**
     * Logger to debug all issues with our AI Service
     */
    private static final Logger log = LoggerFactory.getLogger(AIServiceProvider.class);

    /**
     * Constructor for AI Service provider.
     * Constructs base variables and initializes multiple map instances.
     */
    private AIServiceProvider() {
        _aiScripts = new HashMap<>();
        switch(AIConfig.TREAD_POOL_TYPE) {
            case 1: _aiEventLoop = Executors.newCachedThreadPool(); break;
            default:
            case 2: _aiEventLoop = Executors.newFixedThreadPool(AIConfig.THREAD_POOL_SIZE); break;
            case 3: _aiEventLoop = Executors.newWorkStealingPool(AIConfig.THREAD_POOL_SIZE); break;
            case 4: _aiEventLoop = Executors.newScheduledThreadPool(AIConfig.THREAD_POOL_SIZE); break;
        }
        loadAiScripts();

        if (!AIConfig.USE_LEGACY_AI) {
            ThreadPool.getInstance().scheduleGeneralAtFixedRate(this::GameLoop, 0, (long) ((1 / AIConfig.EVENT_LOOP_FPS) * 1000), TimeUnit.MILLISECONDS);
            log.info("Initialized AI event loop with {} FPS, {} threads and {} scripts.", AIConfig.EVENT_LOOP_FPS, AIConfig.THREAD_POOL_SIZE, _aiScripts.size());
        }
    }

    /**
     * Every tick (1 / FPS) this function is being called in order to update all
     * AI states.
     *
     * @apiNote Please note that DeltaTime is passed using loopStartTime.
     * @implNote There might be an issue where a game loop is being called every (1 / FPS) ms
     *              which basically calls the same task work multiple times. Investigation needed.
     */
    private void GameLoop() {
        try {
            //System.out.println("AIServiceProvider#update()");
            _loopStartTime = System.currentTimeMillis();

            for (Creature creature : World.getInstance().getObjects()) {
                AIScript ai = creature.getAiScript();

                if (ai != null)
                    _aiEventLoop.submit(ai::update);
            }

            //_aiEventLoop.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch(Exception e) {
            //
        }
    }

    /**
     * Returns a delta time which is a compensation
     * for each update task that is running slow or
     * out of synchronization with the thread pool.
     *
     * @return Time elapsed since loop started.
     */
    public int getDeltaTime() {
        return (int) (System.currentTimeMillis() - _loopStartTime);
    }

    /**
     * Creates a map of AI Scripts with their class name and file name.
     */
    private void loadAiScripts() {
        Collection<File> files = FileUtils.listFiles(new File("data/ai"), new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
        for (File file : files)
            _aiScripts.put(FilenameUtils.getBaseName(file.getAbsolutePath()), file.getAbsolutePath());
    }

    /**
     * Returns an AI class path, not found = null.
     *
     * @param aiClassName AI Class name from Character_Table
     * @return Path to AI script
     */
    public String getAiPath(String aiClassName) {
        return _aiScripts.get(aiClassName);
    }
}