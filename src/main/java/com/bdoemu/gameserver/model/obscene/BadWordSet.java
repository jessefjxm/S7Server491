package com.bdoemu.gameserver.model.obscene;

import java.util.regex.Pattern;

public class BadWordSet {
    private final Pattern _include;
    private final Pattern _exclude;

    public BadWordSet(final Pattern include, final Pattern exclude) {
        this._include = include;
        this._exclude = exclude;
    }

    public Pattern getInclude() {
        return this._include;
    }

    public Pattern getExclude() {
        return this._exclude;
    }
}
