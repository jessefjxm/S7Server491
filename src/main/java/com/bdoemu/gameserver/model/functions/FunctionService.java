package com.bdoemu.gameserver.model.functions;

import com.bdoemu.gameserver.model.functions.enums.EFunctionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FunctionService {
    private static final Logger log = LoggerFactory.getLogger(FunctionService.class);
    private static List<String> unimplementedFunctions = new ArrayList<>();

    public static List<IFunctionHandler> getFunctions(final int dialogIndex, final String data) {
        final List<IFunctionHandler> functions = new ArrayList<IFunctionHandler>();
        if (data != null) {
            for (String function : data.split(";")) {
                function = function.trim();
                if (!function.isEmpty()) {
                    final int startIndex = function.indexOf("(");
                    String key = function.substring(0, startIndex);
                    final EFunctionType type = EFunctionType.getFunctionType(key);
                    if (type != null) {
                        final IFunctionHandler functionHandler = type.newFunctionInstance();
                        final int endIndex = function.indexOf(")");
                        key = function.substring(startIndex + 1, endIndex);
                        functionHandler.load(dialogIndex, key);
                        functions.add(functionHandler);
                    } else if (!FunctionService.unimplementedFunctions.contains(key.toLowerCase())) {
                        FunctionService.unimplementedFunctions.add(key.toLowerCase());
                        FunctionService.log.warn("Function [{}] is unimplemented! ({})", (Object) key, (Object) function);
                    }
                }
            }
        }
        return functions;
    }
}
