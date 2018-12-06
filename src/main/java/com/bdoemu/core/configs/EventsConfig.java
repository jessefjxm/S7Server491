package com.bdoemu.core.configs;

import com.bdoemu.commons.config.annotation.ConfigComments;
import com.bdoemu.commons.config.annotation.ConfigFile;
import com.bdoemu.commons.config.annotation.ConfigProperty;

@ConfigFile(name = "configs/events.properties")
public class EventsConfig {
    @ConfigComments(comment = {"Attendance eventId(int);enable(bool);startDate(Format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z');period(ms);.", "Default: 1;false;2016-12-24T10:00:00+03:00;2592000000,2;false;2016-12-24T10:00:00+03:00;2592000000"})
    @ConfigProperty(name = "event.attendance.events", value = "1;false;2016-12-24T10:00:00+03:00;2592000000,2;false;2016-12-24T10:00:00+03:00;2592000000")
    public static String[] ATTENDANCE_EVENTS;
    @ConfigComments(comment = {"Enable Auto reward event.", "Default: false"})
    @ConfigProperty(name = "event.auto.reward.enable", value = "false")
    public static boolean AUTO_REWARD_ENABLE;
    @ConfigComments(comment = {"Auto reward each 30 min.", "Default: 30"})
    @ConfigProperty(name = "event.auto.reward.time", value = "30")
    public static long AUTO_REWARD_TIME;
    @ConfigComments(comment = {"Auto reward mail name."})
    @ConfigProperty(name = "event.auto.reward.mail.name", value = "")
    public static String AUTO_REWARD_MAIL_NAME;
    @ConfigComments(comment = {"Auto reward mail subject."})
    @ConfigProperty(name = "event.auto.reward.mail.subject", value = "")
    public static String AUTO_REWARD_MAIL_SUBJECT;
    @ConfigComments(comment = {"Auto reward mail message."})
    @ConfigProperty(name = "event.auto.reward.mail.message", value = "")
    public static String AUTO_REWARD_MAIL_MESSAGE;
    @ConfigComments(comment = {"Auto reward ItemId;EnchantLevel;Count", "Default: 17072;0;1"})
    @ConfigProperty(name = "event.auto.reward.items", value = "17072;0;1")
    public static String[] AUTO_REWARD_ITEMS;
}
