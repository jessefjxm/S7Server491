package com.bdoemu.gameserver.dataholders.xml;

import com.bdoemu.commons.model.enums.EServiceResourceType;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.commons.xml.XmlDataHolderRoot;
import com.bdoemu.commons.xml.factory.NodeForEach;
import com.bdoemu.commons.xml.models.XmlDataHolder;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.gameserver.model.obscene.BadWordSet;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@XmlDataHolderRoot("obsceneData")
public class ObsceneFilterData extends XmlDataHolder {
    private static final Map<String, String> equivalents = new HashMap<>();
    private static final List<BadWordSet> badWordSet = new ArrayList<>();
    private static final List<String> badWordsCache = new ArrayList<>();

    public static ObsceneFilterData getInstance() {
        return Holder.INSTANCE;
    }

    public void loadData(final NodeList nList) {
        new NodeForEach(nList.item(0).getChildNodes()) {
            protected boolean read(final Node nNode) {
                if (!nNode.getNodeName().equalsIgnoreCase("obscene")) {
                    return true;
                }
                final EServiceResourceType locale = EServiceResourceType.valueOf(nNode.getAttributes().getNamedItem("locale").getNodeValue());
                if (locale != ServerConfig.GAME_RESOURCE_TYPE) {
                    return true;
                }
                ServerInfoUtils.printSection("ObsceneFilterData Loading");
                new NodeForEach(nNode.getChildNodes()) {
                    protected boolean read(final Node nNode) {
                        final String nodeName = nNode.getNodeName();
                        switch (nodeName) {
                            case "equivalents": {
                                new NodeForEach(nNode.getChildNodes()) {
                                    protected boolean read(final Node nNode) {
                                        if (nNode.getNodeName().equalsIgnoreCase("equivalent")) {
                                            final String find = nNode.getChildNodes().item(1).getTextContent();
                                            final String replace = nNode.getChildNodes().item(3).getTextContent();
                                            ObsceneFilterData.equivalents.put(find, replace);
                                        }
                                        return true;
                                    }
                                };
                                break;
                            }
                            case "badWords": {
                                new NodeForEach(nNode.getChildNodes()) {
                                    protected boolean read(final Node nNode) {
                                        if (nNode.getNodeName().equalsIgnoreCase("badWordSet")) {
                                            String include = nNode.getTextContent().trim();
                                            if (include.contains("\n")) {
                                                include = nNode.getChildNodes().item(1).getTextContent();
                                                final String exclude = nNode.getChildNodes().item(3).getTextContent();
                                                final BadWordSet badWord = new BadWordSet(Pattern.compile(include), Pattern.compile(exclude));
                                                ObsceneFilterData.badWordSet.add(badWord);
                                            } else {
                                                final BadWordSet badWord2 = new BadWordSet(Pattern.compile(include), null);
                                                ObsceneFilterData.badWordSet.add(badWord2);
                                            }
                                        }
                                        return true;
                                    }
                                };
                                break;
                            }
                        }
                        return true;
                    }
                };
                return true;
            }
        };
    }

    public String filterWord(final String inputString) {
        String outputString = inputString;
        for (final String word : inputString.split(" ")) {
            if (this.isObsceneWord(word)) {
                outputString = outputString.replace(word, StringUtils.repeat("*", word.length()));
            }
        }
        return outputString;
    }

    public boolean isObsceneWord(String word) {
        word = this.replaceWithEquivalents(word);
        if (ObsceneFilterData.badWordsCache.contains(word)) {
            return true;
        }
        for (final BadWordSet bw : ObsceneFilterData.badWordSet) {
            if (bw.getInclude().matcher(word).matches() && (bw.getExclude() == null || !bw.getExclude().matcher(word).matches())) {
                ObsceneFilterData.badWordsCache.add(word);
                return true;
            }
        }
        return false;
    }

    private String replaceWithEquivalents(String word) {
        for (final Map.Entry<String, String> entry : ObsceneFilterData.equivalents.entrySet()) {
            if (word.contains(entry.getKey())) {
                word = word.replace(entry.getKey(), entry.getValue());
            }
        }
        return word;
    }

    private static class Holder {
        static final ObsceneFilterData INSTANCE = new ObsceneFilterData();
    }
}
