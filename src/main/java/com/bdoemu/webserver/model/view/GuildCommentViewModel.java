package com.bdoemu.webserver.model.view;

import com.bdoemu.gameserver.model.team.guild.model.GuildComment;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuildCommentViewModel extends BaseViewModel {
    private static URLCodec urlCodec;

    static {
        GuildCommentViewModel.urlCodec = new URLCodec();
    }

    private String comment;
    private String param;
    private long commentNo;
    private int cmtId;
    private List<GuildComment> comments;

    public GuildCommentViewModel(final Map<String, String[]> paramMap) {
        super(paramMap);
        this.comments = new ArrayList<GuildComment>();
        if (paramMap.containsKey("comment") && paramMap.get("comment") != null) {
            this.comment = paramMap.get("comment")[0];
        }
        if (paramMap.containsKey("param") && paramMap.get("param") != null) {
            this.param = paramMap.get("param")[0];
        }
        if (paramMap.containsKey("commentNo") && paramMap.get("commentNo") != null) {
            this.commentNo = Long.parseLong(paramMap.get("commentNo")[0]);
        }
        if (paramMap.containsKey("cmtId") && paramMap.get("cmtId") != null) {
            this.cmtId = Integer.parseInt(paramMap.get("cmtId")[0]);
        }
    }

    public String getComment() {
        return this.comment;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(final String param) {
        try {
            this.param = GuildCommentViewModel.urlCodec.encode(param);
        } catch (EncoderException ex) {
        }
    }

    public long getCommentNo() {
        return this.commentNo;
    }

    public void setCommentNo(final long commentNo) {
        this.commentNo = commentNo;
    }

    public int getCmtId() {
        return this.cmtId;
    }

    public List<GuildComment> getComments() {
        return this.comments;
    }

    public void setComments(final List<GuildComment> comments) {
        this.comments = comments;
    }

    @Override
    public Map<String, Object> toMap() {
        final Map<String, Object> map = super.toMap();
        map.put("comment", this.comment);
        map.put("commentNo", this.commentNo);
        map.put("comments", this.comments);
        map.put("param", this.param);
        return map;
    }
}
