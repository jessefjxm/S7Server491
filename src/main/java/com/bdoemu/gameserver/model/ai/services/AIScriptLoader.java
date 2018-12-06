package com.bdoemu.gameserver.model.ai.services;

import com.bdoemu.commons.utils.HashUtil;
import com.bdoemu.gameserver.model.ai.model.AIHandler;
import com.bdoemu.gameserver.model.ai.AIScript;
import com.bdoemu.gameserver.model.ai.model.*;
import com.eclipsesource.json.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * AI Script loader class that loads Black Desert Online compatible *.ai files
 * into memory or on request by the AI Script instance.
 *
 * @author H1X4
 */
public class AIScriptLoader {

    /**
     * This class holds the current object.
     * It is only constructed once and thread-safe initialization.
     *
     * Synchronization is NOT required.
     */
    private static class Holder {
        static final AIScriptLoader INSTANCE = new AIScriptLoader();
    }

    /**
     * Returns a static synchronized on-initialization object.
     *
     * @return AIScript loader service.
     */
    public static AIScriptLoader getInstance() {
        return Holder.INSTANCE;
    }

    public void loadScript(AIScript reference) {
        try {
            JsonObject object = JsonObject.readFrom(readFile(AIServiceProvider.getInstance().getAiPath(
                    reference.getAiOwner().getTemplate().getAiScriptClassName()
            ), StandardCharsets.UTF_8));

            /*if (!object.get("macro_value_define").isNull()) {
                if (object.get("macro_value_define").isObject()) {
                    //
                } else {
                    JsonArray macros = object.get("macro_value_define").asArray();

                    for (JsonValue macro : macros) {
                        JsonObject macroObj = macro.asObject();
                        // need impl
                    }
                }
            }*/

            JsonArray states = object.get("state").asArray();
            for (JsonValue jsonState : states) {
                JsonObject stateObj = jsonState.asObject();
                reference.addState((int) HashUtil.generateHashA(stateObj.getString("@name", "")), new AIState(reference, stateObj));
            }

            JsonValue handlers = object.get("handler");
            if (handlers.isArray()) {
                for (JsonValue handler : handlers.asArray()) {
                    JsonObject handlerObj = handler.asObject();
                    reference.addState((int) HashUtil.generateHashA(handlerObj.getString("@name", "")), new AIHandler(reference, handlerObj));
                }
            } else {
                if (handlers.isNull() && handlers.isObject()) {
                    JsonObject handlerObj = handlers.asObject();
                    reference.addState((int) HashUtil.generateHashA(handlerObj.getString("@name", "")), new AIHandler(reference, handlerObj));
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}