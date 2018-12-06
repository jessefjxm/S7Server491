package com.bdoemu.webserver.model.view;

import java.util.Map;

public class BoardGameViewModel extends BaseViewModel {
    private Integer serverNo;
    private Integer userId;
    private Integer characterNo;
    private String characterName;
    private String blackSoul;
    private String mailTitle;
    private String mailContent;
    private String nationCode;

    public BoardGameViewModel(final Map<String, String[]> paramMap) {
        super(paramMap);
    }
}
