package com.bdoemu.webserver.model.view;

import java.util.Map;

public class GuildInfoViewModel extends BaseViewModel {
    private String userNickname;
    private String guildLogo;
    private String guildName;
    private String guildMasterName;
    private int guildCount;
    private long guildRegdate;
    private String guildArea1;
    private String guildArea2;
    private int guildPoint;
    private int level;
    private long characterNo;
    private String characterName;
    private int classType;
    private String territoryKey;
    private String regionKey;

    public GuildInfoViewModel(final Map<String, String[]> paramMap) {
        super(paramMap);
    }
}
