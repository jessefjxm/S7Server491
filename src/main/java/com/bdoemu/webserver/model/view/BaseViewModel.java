package com.bdoemu.webserver.model.view;

import java.util.HashMap;
import java.util.Map;

public class BaseViewModel {
    protected Integer userNo;
    protected Long guildNo;
    protected Boolean isMaster;
    protected Boolean isGM;
    protected String certKey;

    public BaseViewModel(final Map<String, String[]> paramMap) {
        if (paramMap.containsKey("userNo") && paramMap.get("userNo") != null) {
            this.userNo = Integer.parseInt(paramMap.get("userNo")[0]);
        }
        if (paramMap.containsKey("guildNo") && paramMap.get("guildNo") != null) {
            this.guildNo = Long.parseLong(paramMap.get("guildNo")[0]);
        }
        if (paramMap.containsKey("isMaster") && paramMap.get("isMaster") != null) {
            final String isMasterText = paramMap.get("isMaster")[0].replace("nil", "false");
            this.isMaster = Boolean.parseBoolean(isMasterText);
        }
        if (paramMap.containsKey("isGM") && paramMap.get("isGM") != null) {
            this.isMaster = Boolean.parseBoolean(paramMap.get("isGM")[0]);
        }
        if (paramMap.containsKey("certKey") && paramMap.get("certKey") != null) {
            this.certKey = paramMap.get("certKey")[0];
        }
    }

    public int getUserNo() {
        return this.userNo;
    }

    public long getGuildNo() {
        return this.guildNo;
    }

    public String getCertKey() {
        return this.certKey;
    }

    public boolean isMaster() {
        return this.isMaster;
    }

    public Map<String, Object> toMap() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("userNo", this.userNo);
        map.put("certKey", this.certKey);
        if (this.guildNo != null) {
            map.put("guildNo", this.guildNo);
        }
        if (this.isMaster != null) {
            map.put("isMaster", this.isMaster);
        }
        if (this.isGM != null) {
            map.put("isGM", this.isGM);
        }
        return map;
    }

    public boolean isValid() {
        return this.userNo != null && this.certKey != null;
    }
}
