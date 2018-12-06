package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;

public class SMNotifyGuildInfo extends SendablePacket<GameClient> {
    private long _guildId;
    private EGuildNotifyType _guildNotifyType;
    private int _actorId;
    private long _objectId;
    private long _accountId;
    private String _guildDstName;
    private String _guildSrcName;
    private String _playerName;
    private long _activity;
    private long _payment;
    private long _penalty;
    private long _invitationTime;
    private int _contractLength;
    private int _param0;
    private int _param1;
    private int _param2;
    private int _param3;


    public SMNotifyGuildInfo(EGuildNotifyType notifyType, Guild guildId, Object... params) {
        // Default initialization:
        _guildNotifyType = notifyType;
        _guildId = guildId.getObjectId();
        _actorId = -1024;
        _objectId = -1;
        _accountId = -1;
        _guildDstName = "";
        _guildSrcName = "";
        _playerName = "";
        _activity = 0;
        _payment = 0;
        _penalty = 0;
        _invitationTime = 0;
        _contractLength = 0;
        _param0 = 0;
        _param1 = 0;
        _param2 = 0;
        _param3 = 0;

        // Notification type initialization:
        switch (notifyType) {
            case LEFT:
                break;                                // not implemented
            case MEMBER_LEFT:
                break;                                // not implemented
            case JOIN:                                        // not implemented
                _accountId = ((Number) params[0]).longValue(); // 214372
                _activity = ((Number) params[1]).longValue(); // 1000
                break;
            case WHO_JOIN:                                        // not implemented
                _accountId = ((Number) params[0]).longValue(); // 126711
                _activity = ((Number) params[1]).longValue(); // 1497775093
                _payment = ((Number) params[2]).longValue(); // 1000
                _penalty = ((Number) params[3]).longValue(); // 500
                _invitationTime = ((Number) params[4]).longValue(); // 1497775093
                _contractLength = ((Number) params[5]).intValue();  // 1
                break;
            case DESTROYED:
                break;                                // not implemented
            case LEFT_HIMSELF:                                        // not implemented
                _guildSrcName = params[0].toString(); // ChaosGames
                _playerName = params[1].toString(); // Huedor
                _activity = ((Number) params[2]).longValue();    // 1
                _payment = ((Number) params[3]).longValue();    // 1000
                _penalty = ((Number) params[4]).longValue();    // 500
                break;
            case MAX_MEMBER_COUNT_INCREASED:
                break;                                // not implemented
            case LOGIN_LOGOUT_MEMBER:
                break;                                // not implemented
            case LOGOUT_ALLIANCE_MEMBER:
                break;                                // not implemented
            case CLAN_TO_GUILD:
                break;
            case MEMBER_LEVEL_UP:
                _accountId = ((Number) params[0]).longValue();
                _activity = ((Number) params[1]).longValue();
                break;
            case MEMBER_ENCHANTED_ITEM:
                break;                                // not implemented
            case DUEL_WILL_BE_END:
                break;                                // not implemented
            case USE_GUILD_FUNDS:
                _objectId = ((Number) params[0]).longValue();
                _guildSrcName = params[1].toString();
                _activity = ((Number) params[2]).longValue();
                _payment = ((Number) params[3]).longValue();
                break;
            case UNK14:
                break;                                // not implemented
            case UNK15:
                break;                                // not implemented
            case CREATE_GUILD:
                _guildSrcName = params[0].toString();
                break;
            case ACCEPT_GUILD_QUEST:
                _activity = ((Number) params[0]).longValue();
                _payment = ((Number) params[1]).longValue(); // 4
                _penalty = ((Number) params[2]).longValue(); // 1
                break;
            case COMPLETE_GUILD_QUEST:
                _objectId = ((Number) params[0]).longValue();
                _activity = ((Number) params[1]).longValue();
                break;
            case UNK19:
                break;
            case UNK20:
                break;
            case DECLARE_GUILD_WAR:                                        // TODO, analize end body.
                _guildDstName = params[0].toString();
                _payment = ((Number) params[1]).longValue();
                _param0 = 1874919624;
                break;
            case RECEIVE_GUILD_WAR:                                        // not implemented
                _guildDstName = params[0].toString(); // ChaosGames
                break;
            case UNK23:
                break;                                // not implemented
            case CHANGE_GUILD_LEADER:
                _accountId = ((Number) params[0]).longValue();
                break;
            case UNK25:
                break;                                // not implemented
            case UNK26:
                break;                                // not implemented
            case PLAYER_INVITED:
                _objectId = ((Number) params[0]).longValue(); // 30000000240012058
                _activity = ((Number) params[1]).longValue(); // 238
                break;
            case UNK28:
                break;                                // not implemented
            case UNK29:
                break;                                // not implemented
            case UNK30:
                break;                                // not implemented
            case UNK31:
                break;                                // not implemented
            case UNK32:
                break;                                // not implemented
            case UNK33:
                break;                                // not implemented
            case UNK34:
                break;                                // not implemented
            case MEMBER_LOGIN:
                break;                                // not implemented
            case MEMBER_ACCOUNT_LOGON:                                        // not implemented
                _accountId = ((Number) params[0]).longValue(); // 214372
                break;
            default:
                break;
        }
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(_guildNotifyType.ordinal());
        buffer.writeQ(_guildId);
        buffer.writeQ(0);
        buffer.writeD(_actorId);
        buffer.writeQ(_objectId);
        buffer.writeQ(_accountId);
        buffer.writeS(_guildDstName, 62);
        buffer.writeS(_guildSrcName, 62);
        buffer.writeS(_playerName, 158);
        buffer.writeQ(_activity);
        buffer.writeQ(_payment);
        buffer.writeQ(_penalty);
        buffer.writeQ(_invitationTime);
        buffer.writeD(_contractLength);
        buffer.writeD(_param0); // channel id, x2 short.
        buffer.writeD(_param1);
        buffer.writeD(_param2);
        buffer.writeD(_param3);
    }
}
