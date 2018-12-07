// 탭 정보
var tabList = [
    { "title": "home", "keys": "WEB_GUILDINFO_INTRO_TOOLTIP_TAP_GUILDINTRO", "icon": "/Areas/GuildInfo/common/img/content/home.png", "icon_active": "/Areas/GuildInfo/common/img/content/home_active.png", "icon_hover": "/Areas/GuildInfo/common/img/content/home_hover.png", "url": "/GuildInfo/Main/" }
    , { "title": "comment", "keys": "WEB_GUILDINFO_INTRO_TOOLTIP_TAP_COMENT", "icon": "/Areas/GuildInfo/common/img/content/comment.png", "icon_active": "/Areas/GuildInfo/common/img/content/comment_active.png", "icon_hover": "/Areas/GuildInfo/common/img/content/comment_hover.png", "url": "/GuildInfo/Comment/" }
    //, { "title": "member", "icon": "/Areas/GuildInfo/common/img/content/member.png", "icon_active": "/Areas/GuildInfo/common/img/content/member_active.png", "icon_hover": "/Areas/GuildInfo/common/img/content/member_hover.png", "url": "/GuildInfo/Member" }
    //, { "title": "warRecord", "keys": "WEB_GUILDINFO_INTRO_TOOLTIP_TAP_WARHISTORY", "icon": "/Areas/GuildInfo/common/img/content/warRecord.png", "icon_active": "/Areas/GuildInfo/common/img/content/warRecord_active.png", "icon_hover": "/Areas/GuildInfo/common/img/content/warRecord_hover.png", "url": "/GuildInfo/WarRecord" }
]

// 지역이름
var _areaName = {};

// index페이지 초기화
function init(guildInformation) {
    var guildNo = guildInformation.guildNo;

    initTab();
    bindGuild(guildInformation);
    
    pearl.common.pageMove(tabList[0].url, ["guildNo=" + guildNo]);

    var totalCount = getCommentCount(guildNo);
    changeCommentCount(totalCount);
    facebookBind();
}

// 탭 초기화 및 바인딩
function initTab() {
    $.each(tabList, function (row, data) {
        $('.tabList').append('<li id="tab_' + row + '" class="tab_' + data.title + '" data-url="' + data.url + '" data-attr="0" data-tooltip="' + data.keys + '"></li>');

        // TODO: before를 jquery로 제어 가능?
        $('style').append('li.tab_' + data.title + '::before{ content:""; display:inline-block; background:url("' + data.icon + '") no-repeat; width: 22px; height: 20px; }');
        $('style').append('li.tab_' + data.title + ':hover::before{ content:""; display:inline-block; background:url("' + data.icon_hover + '") no-repeat; }');
        $('style').append('li.tab_' + data.title + '.active::before{  content:""; display:inline-block; background:url("' + data.icon_active + '") no-repeat; }');

    });
    $('.tabList > li:first-child').addClass('active');
}

// 길드 정보 바인딩
function bindGuild(data) {
    //console.log(data);
    //$('#gulidLogo').attr('src', pearl.common.isEmpty(data.gulidLogo) ? '' : data.gulidLogo);
    $('#guildName').append(pearl.common.isEmpty(data.guildName) ? '&nbsp;' : data.guildName);
    $('#guildMasterName').append(pearl.common.isEmpty(data.guildMasterName) ? '' : data.guildMasterName);
    $('#guildPoint').append(pearl.common.isEmpty(data.guildPoint) ? '0' : data.guildPoint);

    if (!pearl.common.isEmpty(data.guildRegdate)) {
        var date = new Date(data.guildRegdate);
        var dateFormat = date.getFullYear() + '-' + formatZero(date.getMonth() + 1) + '-' + formatZero(date.getDate());
        $('#guildRegdate').append(pearl.common.isEmpty(data.guildRegdate) ? '' : dateFormat);
    }

    if (!pearl.common.isEmpty(data.guildArea1)) {
        var guildArea1 = data.guildArea1.split(',');

        var territoryKeys = [5, 32, 77, 202, 229];

        $.each(guildArea1, function (key, value) {
            $.each(territoryKeys, function (idx, val) {
                if (value == val) {
                    engine.call('ToClient_GetTerritoryName', parseInt(idx));
                }
            });
        });
    } else {
        $('#guildArea1').append('-');
    }

    if (!pearl.common.isEmpty(data.guildArea2)) {
        
        var guildArea2 = data.guildArea2.split(',');
        $.each(guildArea2, function (key, value) {
            engine.call('ToClient_GetRegionName', parseInt(value));
        });
    } else {
        $('#guildArea2').append('-');
    }

    if (!pearl.common.isEmpty(data.guildCount)) {
        $('#guildCount').append(data.guildCount);
    }

    //TODO: 길드가입버튼 임시삭제
    //TODO: 길드원인지 아닌지??
    //if (data.isMaster != 'Y') {
        //setTimeout(function () {
        //    $('#guildName').after('<div class="guild_join">' + $('#WEB_GUILDINFO_INTRO_TOOLTIP_JOINGUILD').val() + '</div>');
        //});
    //}
}

// facebook
function facebookBind() {
    //TODO: 되는지확인
    setTimeout(function () {
        //var language = pearl.common.getLanguageCode().cultureCode + '_' + pearl.common.getLanguageCode().countryCode;
        var language = 'ko_KR';
        (function (d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/" + language + "/sdk.js#xfbml=1&version=v2.5";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
    }, 500);
}

// 탭 이동
$(document).on('click', '.tabList li', function (e) {
    var mainAdmin = $('#homeDetail').length;
    var $this = $(this);
    var guildNo = $('#guildNo').val();

    if (mainAdmin > 0) {
        pearl.common.messageBox('WEB_GUILDINFO_INTRO_WRITE_ALERT_WRITE_CANCEL', function (rs) {
            if (rs) {
                $('li.active').removeClass('active');
                $this.addClass('active');

                var url = $this.attr('data-url');
                var param = ["guildNo=" + guildNo, "page=1"];

                $('#contents').empty();
                pearl.common.pageMove(url, param);
            }
            else
                return false;
        });
    } else {
        $('li.active').removeClass('active');
        $this.addClass('active');

        var url = $this.attr('data-url');
        var param = ["guildNo=" + guildNo, "page=1"];

        $('#contents').empty();
        pearl.common.pageMove(url, param);
    }
});

