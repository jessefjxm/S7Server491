package com.bdoemu.webserver.model.view;

import java.util.Map;

public class BeautyAlbumViewModel extends BaseViewModel {
    private String userNickname;
    private int classType;
    private boolean isCustomizationMode;

    public BeautyAlbumViewModel(final Map<String, String[]> paramMap) {
        super(paramMap);
    }
}
