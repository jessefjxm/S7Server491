//var _classNames = { '0': '', '4': '', '8': '', '12': '', '16': '', '20': '', '21': '', '24': '', '25': '', '26': '', '28': '', '31': '' };
var _classNames = {};
// 클래스명 하드코딩
var _classTypes = { '0': 'warrior', '4': 'ranger', '8': 'sorceress', '12': 'giant', '16': 'beastmaster', '20': 'blader', '21': 'f-blader', '24': 'valkyrie', '25': 'wizard', '26': 'witch', '28': 'kunoichi', '31': 'ninja' };
// 다국어 키값
var _stringTable = { 'WEB_CUSTOMIZING_CURRENT_CUSTOMIZING_SAVE': '', 'WEB_CUSTOMIZING_MYFOLDER': '', 'WEB_CUSTOMIZING_DOWNLOAD_RANKING': '', 'WEB_CUSTOMIZING_USER_GALLERY': '', 'WEB_CUSTOMIZING_GALLERY': '', 'WEB_CUSTOMIZING_DETAIL': '', 'WEB_CUSTOMIZING_APPLY': '', 'WEB_CUSTOMIZING_COMMENT': '', 'WEB_CUSTOMIZING_DOWNLOAD': '', 'WEB_CUSTOMIZING_LIKE': '', 'WEB_CUSTOMIZING_CONFIRM_CHANGE_CONTENT': '', 'WEB_CUSTOMIZING_YES': '', 'WEB_CUSTOMIZING_NO': '', 'WEB_CUSTOMIZING_CONFIRM_APPLY': '', 'WEB_CUSTOMIZING_ALERT_ALREADY_SIGNALED_PRESS': '', 'WEB_CUSTOMIZING_CONFIRM_LIKE': '', 'WEB_CUSTOMIZING_CONFIRM_MODIFY': '', 'WEB_CUSTOMIZING_CONFIRM_DELETE': '', 'WEB_CUSTOMIZING_CONFIRM_SAVE': '', 'WEB_CUSTOMIZING_CONFIRM_CANCEL': '', 'WEB_CUSTOMIZING_ALL_SELECT': '', 'WEB_CUSTOMIZING_ALL_UNSELECT': '', 'WEB_CUSTOMIZING_TEXT_CLASS': '', 'WEB_CUSTOMIZING_TEXT_ALL': '', 'WEB_CUSTOMIZING_SEARCH_TIME': '', 'WEB_CUSTOMIZING_SEARCH_LATELY_DAY_7': '', 'WEB_CUSTOMIZING_SEARCH_LATELY_DAY_30': '', 'WEB_CUSTOMIZING_SEARCH_CUMULATIVE_DOWNLOAD': '', 'WEB_CUSTOMIZING_TEXT_DOWNLOAD_TOP_50': '', 'WEB_CUSTOMIZING_TEXT_CATEGORY': '', 'WEB_CUSTOMIZING_TEXT_THE_WHOLE': '', 'WEB_CUSTOMIZING_SEARCH_PRETTY_CHIC': '', 'WEB_CUSTOMIZING_SEARCH_INDIVIDUALITY': '', 'WEB_CUSTOMIZING_SEARCH_ENTERTAINER_CELEBRITY': '', 'WEB_CUSTOMIZING_SEARCH_UGLY': '', 'WEB_CUSTOMIZING_SEARCH_ETC': '', 'WEB_CUSTOMIZING_TEXT_TITLE_CODE_SEARCH': '', 'WEB_CUSTOMIZING_SEARCH': '', 'WEB_CUSTOMIZING_GALLERY_SIGN_UP': '', 'WEB_CUSTOMIZING_TEXT_CODE': '', 'WEB_CUSTOMIZING_PREV': '', 'WEB_CUSTOMIZING_CUSTOMIZING_APPLY': '', 'WEB_CUSTOMIZING_CUSTOMIZING_SHARE': '', 'WEB_CUSTOMIZING_MODIFY': '', 'WEB_CUSTOMIZING_DELETE': '', 'WEB_CUSTOMIZING_COMMENT_SAVE': '', 'WEB_CUSTOMIZING_REPORT': '', 'WEB_CUSTOMIZING_TEXT_PLASE_INPUT_TEXT': '', 'WEB_CUSTOMIZING_TEXT_BLIND_COMMENT': '', 'WEB_CUSTOMIZING_TEXT_HELP': '', 'WEB_CUSTOMIZING_SAVE': '', 'WEB_CUSTOMIZING_CANCEL': '', 'WEB_CUSTOMIZING_TEXT_SCREEN_N_CUSTOMIZING_SHARE': '', 'WEB_CUSTOMIZING_SCREENSHOT': '', 'WEB_CUSTOMIZING_TEXT_PAGE_LOAD_FAIL': '', 'WEB_CUSTOMIZING_TEXT_PAGE_LOAD_FAIL_EXECUTE_REFRESH': '', 'WEB_CUSTOMIZING_TEXT_NO_CUSTOMIZING': '', 'WEB_CUSTOMIZING_VIEW_COUNT': '', 'WEB_CUSTOMIZING_TEXT_PLASE_INPUT_TITLE': '', 'WEB_CUSTOMIZING_AGAIN_SCREENSHOT': '', 'WEB_CUSTOMIZING_CONFIRM_REPORT': '', 'WEB_CUSTOMIZING_TEXT_REQUIRED_TITLE': '', 'WEB_CUSTOMIZING_TEXT_REQUIRED_SCREENSHOT': '', 'WEB_CUSTOMIZING_TEXT_LIMIT': '', 'WEB_CUSTOMIZING_CONFIRM_CUSTOMIZING_CHANGE_AGAIN_SCREENSHOT': '', 'WEB_CUSTOMIZING_NOT_SAVED_CUSTOMIZING': '', 'WEB_CUSTOMIZING_USERGALLERY_SIGNUP': '', 'WEB_CUSTOMIZING_USERGALLERY_RELESAE': '', 'WEB_CUSTOMIZING_SIGNUP_FAIL': '', 'WEB_CUSTOMIZING_NOT_SAME_CLASS': '', 'WEB_CUSTOMIZING_HOUR': '', 'WEB_CUSTOMIZING_MINUTE': '', 'WEB_CUSTOMIZING_JUST_NOW': '', 'WEB_CUSTOMIZING_BEFORE': '', 'WEB_CUSTOMIZING_TEXT_REQUIRED_CATEGORY': '', 'WEB_CUSTOMIZING_HIDE': '', 'WEB_CUSTOMIZING_CONFIRM_HIDE': '', 'WEB_CUSTOMIZING_CONFIRM_GALLERY_SAVE': '', 'WEB_CUSTOMIZING_SEARCH_LATELY_DAY_1': '', 'WEB_CUSTOMIZING_CONTENT_PLASE_INPUT_TEXT': '', 'WEB_CUSTOMIZING_CONFIRM_BLIND_NOT_CANCEL': '', 'WEB_CUSTOMIZING_AUTHORRANK': '', 'WEB_CUSTOMIZING_SEARCH_DOWNLOAD_TIME': '', 'WEB_CUSTOMIZING_AUTHOR_DOWNLOAD_RANK': '', 'WEB_CUSTOMIZING_ALERT_AUTHOR_SAVE': '', 'WEB_CUSTOMIZING_CONFIRM_AUTHOR_GALLERY_SEARCH': '', 'WEB_CUSTOMIZING_CONFIRM_USER_GALLERY_SEARCH': '', 'WEB_CUSTOMIZING_SAVE_CLASS': '', 'WEB_CUSTOMIZING_SAVE_GALLERY_USER': '', 'WEB_CUSTOMIZING_AUTHOR': '' };
//var _stringTable = '';
//var deg = 0;
var loading;

var boxFormat = {
    'format':
    '<div class="box" data-no="{{C_customizingNo}}" data-rownum="{{rownum}}">' +
        '<a class="cmApply hide" href="{{C_customizingFile}}"></a>' +
        '<div class="img" style="background: url({{C_customizingImage1}}); background-size: cover; background-position: center;">' +
            '<img src="/Areas/Customizing/common/img/class_{{C_classType}}.png" data-tooltip="{{C_className}}" attr-key="{{C_classType}}">' +
            '<a class="btn detail opacity" data-color="08335f" data-str="WEB_CUSTOMIZING_DETAIL"> </a>' +
            '<a class="btn apply opacity" data-color="0c460d" data-str="WEB_CUSTOMIZING_APPLY"> </a>' +
        '</div>' +
        '<div class="description">' +
            '<div class="gallery_comment">' +
                '<div class="gallery"><span data-str="WEB_CUSTOMIZING_GALLERY"> </span><input type="checkbox" class="switch galleryOnOff" {{C_isMine}}/></div>' +
                '<div class="comment arial"><img src="/Areas/Customizing/common/img/comment.png" data-tooltip="WEB_CUSTOMIZING_COMMENT"/><span>{{C_commentCount}}</span></div>' +
            '</div>' +
            '<div class="name"><span>{{C_customizingTitle}}</span></div>' +
            '<div class="date_apply_like">' +
                '<div class="date arial"><span>{{C_regdate}}</span></div>' +
                '<div class="apply arial"><img src="/Areas/Customizing/common/img/apply.png" data-tooltip="WEB_CUSTOMIZING_DOWNLOAD"/><span>{{C_downloadCount}}</span></div>' +
                '<div class="like arial"><img src="/Areas/Customizing/common/img/like.png" data-tooltip="WEB_CUSTOMIZING_LIKE"/><span>{{C_LikeCount}}</span></div>' +
            '</div>' +
        '</div>' +
    '</div>',
    'searchFormat':
    '<div class="box" data-no="{{C_customizingNo}}" data-rownum="{{rownum}}">' +
        '<a class="cmApply hide" href="{{C_customizingFile}}"></a>' +
        '<div class="img" style="background: url({{C_customizingImage1}}); background-size: cover; background-position: center;">' +
            '<img src="/Areas/Customizing/common/img/class_{{C_classType}}.png" data-tooltip="{{C_className}}" attr-key="{{C_classType}}">' +
            '<div class="caption">' +
                '<span class="arial">{{rownum}}</span>' +
                '<img src="/Areas/Customizing/common/img/icn_caption.png">' +
            '</div>' +
            '<a class="btn detail opacity" data-color="08335f" data-str="WEB_CUSTOMIZING_DETAIL"> </a>' +
            '<a class="btn apply opacity" data-color="0c460d" data-str="WEB_CUSTOMIZING_APPLY"> </a>' +
        '</div>' +
        '<div class="description">' +
            '<div class="name"><span>{{C_customizingTitle}}</span></div>' +
            '<div class="comment_apply_like">' +
                '<div class="comment arial"><img src="/Areas/Customizing/common/img/comment.png" data-tooltip="WEB_CUSTOMIZING_COMMENT"/><span>{{C_commentCount}}</span></div>' +
                '<div class="apply arial"><img src="/Areas/Customizing/common/img/apply.png" data-tooltip="WEB_CUSTOMIZING_DOWNLOAD"/><span>{{C_downloadCount}}</span></div>' +
                '<div class="like arial"><img src="/Areas/Customizing/common/img/like.png" data-tooltip="WEB_CUSTOMIZING_LIKE"/><span>{{C_LikeCount}}</span></div>' +
            '</div>' +
            '<div class="characterName">{{guildmark}}{{C_userNickname}}{{C_characterName}}</div>' +
            '<div class="date arial">{{C_regdate}}</div>' +
        '</div>' +
    '</div>',
    'authorFormat':
    '<div class="box" data-rownum="{{rownum}}">' +
        '<a class="cmApply hide first" href="{{C_customizingFile1}}"></a>' +
        '<a class="cmApply hide last" href="{{C_customizingFile2}}"></a>' +
        '<div class="caption">' +
            '<span class="arial">{{rownum}}</span>' +
            '<img src="/Areas/Customizing/common/img/icn_caption.png">' +
        '</div>' +
        '<div class="title">' +
            '<a href="#" class="userNickname">{{C_userNickname}}</a>' +
            '<div class="apply arial"><img src="/Areas/Customizing/common/img/apply.png" data-tooltip="WEB_CUSTOMIZING_DOWNLOAD"/><span>{{C_downloadCount}}</span></div>' +
            '<div class="hr"></div>' +
            //'<div class="className"><img src="/Areas/Customizing/common/img/icn_class.png" data-tooltip="WEB_CUSTOMIZING_SAVE_CLASS"/><span>{{C_className}}</span>, <span>{{C_className}}</span></div>' +
            //'<div class="userNicknames"><img src="/Areas/Customizing/common/img/icn_userNickname.png" data-tooltip="WEB_CUSTOMIZING_SAVE_GALLERY_USER"/><a href="#">{{C_userNickname}}</a>, <a href="#">{{C_userNickname}}</a>, <a href="#">{{C_userNickname}}</a>, <a href="#">{{C_userNickname}}</a></div>' +
        '</div>' +
        '<div class="img first" data-no="{{C_customizingNo1}}" style="background: url({{C_customizingImage1}}); background-size: cover; background-position: center;float:left;">' +
            '<img src="/Areas/Customizing/common/img/class_{{C_classType1}}.png" data-tooltip="{{C_className1}}" attr-key="{{C_classType1}}">' +
            '<a class="btn detail opacity" data-color="08335f" data-str="WEB_CUSTOMIZING_DETAIL"> </a>' +
            '<a class="btn apply opacity" data-color="0c460d" data-str="WEB_CUSTOMIZING_APPLY"> </a>' +
        '</div>' +
        '<div class="img last{{customizingHide}}" data-no="{{C_customizingNo2}}" style="background: url({{C_customizingImage2}}); background-size: cover; background-position: center;float:right; left:auto; right:-1px;">' +
            '<img src="/Areas/Customizing/common/img/class_{{C_classType2}}.png" data-tooltip="{{C_className2}}" attr-key="{{C_classType2}}">' +
            '<a class="btn detail opacity" data-color="08335f" data-str="WEB_CUSTOMIZING_DETAIL"> </a>' +
            '<a class="btn apply opacity" data-color="0c460d" data-str="WEB_CUSTOMIZING_APPLY"> </a>' +
        '</div>' +
        '<div class="description first">' +
            '<div class="comment_apply_like">' +
                '<div class="comment arial"><img src="/Areas/Customizing/common/img/comment.png" data-tooltip="WEB_CUSTOMIZING_COMMENT"/><span>{{C_commentCount1}}</span></div>' +
                '<div class="apply arial"><img src="/Areas/Customizing/common/img/apply.png" data-tooltip="WEB_CUSTOMIZING_DOWNLOAD"/><span>{{C_downloadCount1}}</span></div>' +
                '<div class="like arial"><img src="/Areas/Customizing/common/img/like.png" data-tooltip="WEB_CUSTOMIZING_LIKE"/><span>{{C_likeCount1}}</span></div>' +
            '</div>' +
        '</div>' +
        '<div class="description last{{customizingHide}}" style="float:right;">' +
            '<div class="comment_apply_like">' +
                '<div class="comment arial"><img src="/Areas/Customizing/common/img/comment.png" data-tooltip="WEB_CUSTOMIZING_COMMENT"/><span>{{C_commentCount2}}</span></div>' +
                '<div class="apply arial"><img src="/Areas/Customizing/common/img/apply.png" data-tooltip="WEB_CUSTOMIZING_DOWNLOAD"/><span>{{C_downloadCount2}}</span></div>' +
                '<div class="like arial"><img src="/Areas/Customizing/common/img/like.png" data-tooltip="WEB_CUSTOMIZING_LIKE"/><span>{{C_likeCount2}}</span></div>' +
            '</div>' +
        '</div>' +
    '</div>'
};
var _searchConditions = {
    'myfolder': { 'url': '/Customizing/MyFolder/GetCustomizingMyFolder/', 'class': [] }
    , 'rank': { 'url': '/Customizing/Rank/GetCustomizingGalleryRank/', 'class': [], 'date': '' }
    , 'gallery': { 'url': '/Customizing/Gallery/GetCustomizingGallery/', 'class': [], 'category': '', 'text': '', 'sort': '1', 'author': '' }
    , 'authorRank': { 'url': '', 'date': '' }
};

var common = window.common = {
    isEmpty: false,                 // 빈값인 경우 true 반환
    pageMove: false,                // 페이지 이동
    initToggleSwitch: false,        // 토글스위치 초기화
    setToggleSwitch: false,         // 토글스위치 값 삽입
    classSelected: false,           // 클래스 선택
    classUnSelected: false,         // 클래스 해제
    bindCMBox: false,               // CM박스 바인딩
    openPage: false,                // 페이지 로드 시작
    closePage: false,               // 페이지 로드 완료
    initButton: false,              // 버튼 초기화
    messageBox: false,              // 메세지박스
    initTooltip: false,             // 툴팁이벤트 초기화
    tooltip: false,                 // 툴팁
    getClassName: false,            // 클래스명 가져오기
    dateFormat: false,              // Date Format (yyyy-mm-dd)
    dateTimeFormat: false,          // DateTime Format (yyyy-mm-dd hh:mm:ss)
    stringConvert: false,           // 다국어처리
    setCookie: false,               // 쿠키 저장
    getCookie: false                // 쿠키 가져오기
}
common.isEmpty = function (data) {
    if (typeof (data) == 'string')
        data = data.trim();
    return data == null || data == '' || data == undefined;
}
common.pageMove = function (url, params) {
    var param = params != null && params != undefined && params.length >= 1 ? '?' + params.join('&') : '';
    var fullUrl = url + param;

    $('#main').empty();
    
    $("#main").load(fullUrl, function (response, status, xhr) {
        pearl.common.error(response);

        //if (status == "error") {
        //    var msg = "load error";
        //    $("#main").html(msg + xhr.status + " | " + xhr.statusText + "|" + response);
        //} else {
        //}
    });
}
common.initToggleSwitch = function () {
    $(".switch").toggleSwitch({
        'width': '57px',
        'height': '20px',
        'offLabel': '',
        'onLabel': '',
        //'onToggle': function (checked, caller) { },   // 토글 된 후 호출
        'onReady': function (checked, caller) {
            //if (!common.isEmpty(caller))
            //    common.setToggleSwitch(caller);
        }
    });
}
common.setToggleSwitch = function (caller, is) {
    //$($(".galleryOnOff")[0]).toggleCheckedState(true)         // 토글 on
    //$($(".galleryOnOff")[0]).toggleCheckedState(false)        // 토글 off
    //$($(".galleryOnOff")[0]).getChecked()                     // 토글 checked 반환

    //var no = $(caller).parents('.box').attr('data-no');
    //$.each(testData, function (key, value) {
    //    if (value._customizingNo == no)
    //        $(caller).toggleCheckedState(value._isMine == "1" ? true : false);
    //});
    $(caller).toggleCheckedState(is);
}
common.classSelected = function ($this) {
    var page = $('.container').attr('id');

    $.each($this, function (row, data) {
        $(data).find('img').attr('src', '/Areas/Customizing/common/img/class_' + $(data).attr('attr-key') + '_active.png');
        $(data).addClass('active');
    });

    _searchConditions[page].class = [];
    $.each($('#searchBox .content.active'), function (key, value) {
        _searchConditions[page].class.push($(value).attr('attr-key'));
    });

}
common.classUnSelected = function ($this) {
    var page = $('.container').attr('id');

    $.each($this, function (row, data) {
        $(data).find('img').attr('src', '/Areas/Customizing/common/img/class_' + $(data).attr('attr-key') + '.png');
        $(data).removeClass('active');
    });

    _searchConditions[page].class = [];
    $.each($('#searchBox .content.active'), function (key, value) {
        _searchConditions[page].class.push($(value).attr('attr-key'));
    });
}
common.getCustomizing = function (url, data) {
    // 커스터마이징 가져오기
    common.loading(true);

    var rs = '';
    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        async: false,
        beforeSend: function (xhr, settings) {
        },
        complete: function (xhr, status) {
        },
        success: function (result, status, xhr) {
            pearl.common.error(result, function () {
                //console.log(result);
                if (typeof (result) == 'object')
                    rs = result;
                else
                    rs = JSON.parse(result);
            });
        },
        error: function (xhr, status, error) {
            pearl.common.error(xhr.responseText);
            console.log('status : ' + status + ' / error : ' + error);
        }
    });

    return rs;
}
common.bindCMBox = function (bindData) {
    $('.scroll').scrollTop(0);
    setTimeout(function () {
        $('.cmArea .CMBox').empty();
        $('.cmArea .nobox').remove();
        var str = '';


        if (common.isEmpty(bindData) || bindData.length <= 0) {
            $('.cmArea').append('<div class="nobox"><span data-str="WEB_CUSTOMIZING_NOT_SAVED_CUSTOMIZING"></span></div>');
            common.loading(false);
        } else {
            $.each(bindData, function (row, data) {
                if (common.isEmpty(data))
                    return;
                var formatData = boxFormat[$('.cmArea').attr('data-formatType')];

                //var _guildMark = "coui://guildmark/100000000000000446.png";

                if (common.isEmpty(data['C_guildno']))
                    formatData = formatData.replace(/{{guildmark}}/gi, '');
                else {
                    if (data['C_guildno'] > 0)
                        formatData = formatData.replace(/{{guildmark}}/gi, '<img class="guildmark" src="coui://guildmark/' + data['C_guildno'] + '.png" data-tooltip="' + data.C_guildName + '"/>');
                    else
                        formatData = formatData.replace(/{{guildmark}}/gi, '');
                }

                formatData = formatData.replace(/{{C_isMine}}/gi, data['C_isMine'] == 'N' ? 'checked' : '');
                formatData = formatData.replace(/{{C_className}}/gi, _classNames[data['C_classType']]);

                if (common.isEmpty(data['C_customizingImage1'])) {
                    formatData = formatData.replace(/C_customizingImage1/gi, 'C_customizingImage2');
                }

                $.each(data, function (key, value) {
                    var re = new RegExp('{{' + key + '}}', 'g');

                    if (key == 'C_regdate')
                        value = common.dateFormat(value);
                    else if (key == 'C_characterName')
                        value = !common.isEmpty(value) ? '(' + value + ')' : '';

                    formatData = formatData.replace(re, value);
                });
                str += formatData;

            });

            $('.cmArea .CMBox').append(str);

            common.initButton();
            common.initToggleSwitch();

            // 뷰페이지 이동
            $('.CMBox .detail, .CMBox .img').on('click', function () {
                var $parent = $(this).parents('.box');
                var customizingNo = $parent.attr('data-no');

                var param = ['customizingNo=' + customizingNo];
                common.pageMove('/Customizing/Detail', param);
            });

            // 적용버튼
            $('.CMBox .apply ').on('click', function (e) {
                e.stopPropagation();

                if (customizing.isCustomizationMode == 'false' || !customizing.isCustomizationMode) {
                    common.messageBox('WEB_CUSTOMIZING_SIGNUP_FAIL');
                    return false;
                } else {
                    var $parent = $(this).parents('.box');
                    var url = '/Customizing/Frame/SetCustomizingGalleryDownload/';
                    var data = { 'customizingNo': $parent.attr('data-no'), 'classType': customizing.classType };

                    common.messageBox('WEB_CUSTOMIZING_CONFIRM_APPLY', function (rs) {
                        if (rs) {
                            $.ajax({
                                url: url,
                                type: 'POST',
                                data: data,
                                async: false,
                                beforeSend: function (xhr, settings) {
                                },
                                complete: function (xhr, status) {
                                },
                                success: function (result, status, xhr) {
                                    if (result.resultCode == 0) {
                                        $parent.find('.apply span').text(result.totalCount);
                                        location.href = $parent.find('.cmApply').attr('href');
                                    }
                                    else if (result.resultCode == -20003)
                                        common.messageBox('WEB_CUSTOMIZING_NOT_SAME_CLASS');
                                    else if (result.resultCode == -20000)
                                        location.href = $parent.find('.cmApply').attr('href');
                                    else {
                                        pearl.common.error(result, function () {});
                                    }
                                },
                                error: function (xhr, status, error) {
                                    pearl.common.error(xhr.responseText);
                                    console.log('status : ' + status + ' / error : ' + error);
                                }
                            });
                        }
                    });
                }
            });

            // 커스터마이징박스 마우스오버
            $('.CMBox').on('mouseover', '.img', function (e) {
                $(this).find('.btn').removeClass('opacity');
            });

            // 커스터마이징박스 마우스아웃
            $('.CMBox').on('mouseout', '.img', function (e) {
                $('.img .btn').addClass('opacity');
            });

            // 갤러리 ON/OFF
            $('.cmArea .CMBox input:checkbox').on('change', function (e) {
                var $this = $(this);
                $('#myfolder').before('<div id="checkLoadingBox"></div>');
                setTimeout(function () {
                    var url = '/Customizing/Frame/SetCustomizingGalleryType';
                    var data = { 'customizingNo': $this.parents('.box').attr('data-no'), 'isMine': $this.is(':checked') ? 'N' : 'Y' };
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: data,
                        async: false,
                        beforeSend: function (xhr, settings) {
                        },
                        complete: function (xhr, status) {
                        },
                        success: function (result, status, xhr) {
                            pearl.common.error(result, function () {
                                if (data.isMine == 'N')
                                    common.messageBox('WEB_CUSTOMIZING_USERGALLERY_SIGNUP');
                                else
                                    common.messageBox('WEB_CUSTOMIZING_USERGALLERY_RELESAE');
                            });
                        },
                        error: function (xhr, status, error) {
                            pearl.common.error(xhr.responseText);
                            console.log('status : ' + status + ' / error : ' + error);
                        }
                    });
                    $('#checkLoadingBox').remove();
                }, 500);
            });
        }
        var $str = $('[data-str], [data-str-placeholder]');
        $.each($str, function (key, value) {
            common.stringConvert(value);
            if (key + 1 == $str.length)
                common.loading(false);
        });
    }, 0);
}
common.openPage = function () {
    //common.initButton();
    //$('.container_top').on('mouseover', function () {
    //    $('.scroll').css('overflow-y', 'auto');
    //});
    //$('.container_top').on('mouseout', function () {
    //    $('.scroll').css('overflow-y', 'hidden');
    //    $('#rank.container ').css('padding', '15px');

    //});
}
common.closePage = function () {
    common.initButton();
    common.initTooltip();
    $('img').on('load', function (e) {
        //common.tooltip();
    });

    var $str = $('[data-str], [data-str-placeholder]');
    $.each($str, function (key, value) {
        common.stringConvert(value);
    });
}
common.initButton = function () {
    var $btns = $('.btn');
    $.each($btns, function (row, btn) {
        $(btn).css('background-color', '#' + $(btn).attr('data-color'));
    });
}
common.messageBox = function (str, callback) {
    if ($('.messageBox').length > 0) {
        $(this).remove();
    }

    var appendStr =
        '<div class="messageBox">' +
            '<div class="messageBox_content">' +
                '<div class="messageBox_str"><span>' + common.getStringTable(str) + '</span></div>' +
                '<div class="messageBox_button">' +
                    '<div class="btn messageBox_yes"data-color="3d382d"> ' + common.getStringTable('WEB_CUSTOMIZING_YES') + ' </div>' +
                    '<div class="btn messageBox_no"data-color="3d382d"> ' + common.getStringTable('WEB_CUSTOMIZING_NO') + ' </div>' +
                '</div>' +
            '</div>' +
            '<div class="messageBox_bg hide"></div>' +
        '</div>';

    $('.container_top').append(appendStr);
    $('.messageBox').show(function () {
        engine.call("ToClient_SetLuaInputHold", true);
    });
    $('.messageBox_bg').show();

    

    if (!$.isFunction(callback)) {
        $('.messageBox_yes').addClass('messageBox_default');
        $('.messageBox_no').addClass('hide');
    }

    var $btns = $('.messageBox .btn');
    $.each($btns, function (row, btn) {
        $(btn).css('background-color', '#' + $(btn).attr('data-color'));
    });

    // 예
    $('.messageBox_yes').on('click', function (e) {
        $('.messageBox_bg').fadeOut(10, function () {
            $(this).remove();
        });
        $('.messageBox').fadeOut(10, function () {
            $(this).remove();
        });

        if ($.isFunction(callback))
            callback(true);
        engine.call("ToClient_SetLuaInputHold", false);
    });

    // 아니오
    $('.messageBox_no').on('click', function (e) {
        $('.messageBox_bg').fadeOut(10, function () {
            this.remove();
        });
        $('.messageBox').fadeOut(10, function () {
            this.remove();
        });
        if ($.isFunction(callback))
            callback(false);
        engine.call("ToClient_SetLuaInputHold", false);
    });

}
common.initTooltip = function () {
    // 툴팁 마우스오버
    $(document).on('mouseover', '[data-tooltip]', function (e) {

        var left = $(this).offset().left;
        var right = $(window).width() - ($(this).offset().left + $(this).outerWidth());
        var top = $(this).offset().top;
        var bottom = top + $(this).height();

        var tooltip = $(this).attr('data-tooltip');
        
        // 툴팁 생성
        if ($('.tooltip#' + tooltip).length <= 0) {
            var str = common.isEmpty(common.getStringTable(tooltip)) ? tooltip : common.getStringTable(tooltip);
            if (!common.isEmpty($(this).attr('data-tooltip-position'))) {
                var position = $(this).attr('data-tooltip-position');
                
                if (position == 'right')
                    $('.tooltipArea').append('<span class="tooltip" id="' + tooltip + '" style="right:' + right + 'px; top:' + (top - 30) + 'px;">' + str + '</span>');
                else if (position == 'bottom')
                    $('.tooltipArea').append('<span class="tooltip" id="' + tooltip + '" style="left:' + (left + 10) + 'px; top:' + (bottom + 10) + 'px;">' + str + '</span>');
                else if (position == 'rightBottom')
                    $('.tooltipArea').append('<span class="tooltip" id="' + tooltip + '" style="right:' + right + 'px; top:' + (bottom + 10) + 'px;">' + str + '</span>');
                else
                    $('.tooltipArea').append('<span class="tooltip" id="' + tooltip + '" style="left:' + left + 'px; top:' + (top - 30) + 'px;">' + str + '</span>');
            } else {
                $('.tooltipArea').append('<span class="tooltip" id="' + tooltip + '" style="left:' + left + 'px; top:' + (top - 30) + 'px;">' + str + '</span>');
            }
        }
            // 툴팁 위치 조정
        else {
            if (!common.isEmpty($(this).attr('data-tooltip-position'))) {
                var position = $(this).attr('data-tooltip-position');
                
                if (position == 'right')
                    $('#' + tooltip).css('right', right).css('top', top - 30 + 'px');
                else if (position == 'bottom')
                    $('#' + tooltip).css('left', left + 10).css('top', bottom + 10 + 'px');
                else if (position == 'rightBottom')
                    $('#' + tooltip).css('right', right).css('top', bottom + 10 + 'px');
                else
                    $('#' + tooltip).css('left', left).css('top', top - 30 + 'px');
            }

            //$(this).find('.tooltip').removeClass('hide');
        }
    });

    // 툴팁 마우스아웃
    $(document).on('mouseout', '[data-tooltip]', function (e) {
        $('.tooltipArea').empty();
    });

    $('[data-tooltip] *').on('mouseover', function (e) {
        //e.stopPropagation();
    });

    // 툴팁에 마우스오버할 경우 툴팁 숨김 
    //$(document).on('mouseover', '[data-tooltip] .tooltip', function (e) {
    //    $(this).find('.tooltip').addClass('hide');
    //    e.stopPropagation();
    //    return false;
    //});

}
common.tooltip = function () {
    var $tooltips = $('[data-tooltip]');

    $.each($tooltips, function (row, data) {
        var left = $(data).offset().left;
        var top = $(data).offset().top;

        var tooltip = $(data).attr('data-tooltip');

        // 툴팁 생성
        if ($('#' + tooltip).length <= 0)
            $('.tooltipArea').append('<span class="tooltip" id="' + tooltip + '" style="left:' + left + 'px; top:' + (top - 30) + 'px;">' + tooltip + '</span>');
            // 툴팁 위치 조정
        else
            $('#' + tooltip).css('left', left).css('top', top - 30 + 'px');
    });
}
common.getClassName = function () {
    engine.call('ToClient_GetOpenClassTypeInfo');
}
common.dateFormat = function (date) {
    function formatZero(data) {
        return data.toString().length > 1 ? data : '0' + data;
    }
    if ((new Date(date)).toDateString() == 'Invalid Date')
        date = new Date(parseInt(date.substr(6)));
    else
        date = new Date(date);

    return date.getFullYear() + '-' + formatZero(date.getMonth() + 1) + '-' + formatZero(date.getDate());
}
common.dateTimeFormat = function (date) {
    function formatZero(data) {
        return data.toString().length > 1 ? data : '0' + data;
    }

    date = new Date(parseInt(date.substr(6)))

    return date.getFullYear() + '-' + formatZero(date.getMonth() + 1) + '-' + formatZero(date.getDate()) + ' ' + formatZero(date.getHours()) + ':' + formatZero(date.getMinutes()) + ':' + formatZero(date.getSeconds());
}
common.stringConvert = function (el) {
    var $el = $(el);
    
    if (!common.isEmpty($(el).attr('data-str'))) {
        var key = $(el).attr('data-str');
        $('[data-str="' + key + '"]').text(common.getStringTable(key));
    } else if (!common.isEmpty($(el).attr('data-str-placeholder'))) {
        var key = $(el).attr('data-str-placeholder');
        $('[data-str-placeholder="' + key + '"]').attr('placeholder', common.getStringTable(key));
    }


    // 탭 초기화 후 글자가 길 경우 스타일 조정
    $.each($('.tabList li span'), function (row, data) {
        if ($(data).width() > 155) {
            $('.tabList li img').css('display', 'none');
            $('.tabList li span').addClass('over');
        } else {
            if ($('.tabList li img').css('display') != 'none') {
                $('.tabList li img').css('display', 'initial');
                $('.tabList li span').removeClass('over');
            }
        }
    });

}
common.getStringTable = function (key) {
    if (common.isEmpty(_stringTable[key]))
        return key;
    else 
        return _stringTable[key];
}
common.setCookie = function (cookieName,cookieValue) {
    // 없으면 생성
    if (common.isEmpty(common.getCookie(cookieName))) {
        var cDay = 1; // 만료일
        var expire = new Date();
        expire.setDate(expire.getDate() + cDay);
        cookies = cookieName + '=' + escape(cookieValue) + '; path=/ '; // 한글 깨짐을 막기위해 escape(cookieValue) 사용
        cookies += ';expires=' + expire.toGMTString() + ';';
        document.cookie = cookies;
    }
    // 있으면 수정
    else {
        document.cookie = cookieName + '=' + cookieValue;
    }
}
common.getCookie = function (cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cValue = '';
    if (start != -1) {
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if (end == -1) end = cookieData.length;
        cValue = cookieData.substring(start, end);
    }
    return unescape(cValue);
}
common.loading = function (is) {
    //clearInterval(loading);
    if (is) {
        $('#loading').removeClass('hide');
        //loading = setInterval(function (e) {
        //    $('.spin').css('-webkit-transform', 'rotate(' + deg + 'deg)');
        //    deg++;
        //}, 0.1);
    } else {
        $('#loading').addClass('hide');
        //clearInterval(loading);
        //deg = 0;
    }
}

// 페이징
var paging = window.paging = {
    $this: '',                     // 페이징 컨테이너
    settings: [],                  // 옵션값
    init: false,                   // 초기화
    setPage:false,                 // 페이지값 삽입
    getPage: false                 // 현재페이지
}
paging.init = function (options) {
    this.$this = $('#' + options.id);
    this.settings = {
        'first': '',                                                                // 첫 페이지 이동 버튼       // 코딩X
        'last': '',                                                                 // 마지막 페이지 이동 버튼   // 코딩X
        'next': '',                                                                 // 다음 페이지 이동 버튼     // 코딩X
        'prev': '',                                                                 // 이전 페이지 이동 버튼     // 코딩X

        'id': options.id,
        'length': getDefaultData(options.length, 10),                               // 페이지를 표시할 개수. 기본값 10
        'maxLength': getDefaultData(options.maxLength, 1),                          // 전체 페이지 개수. 기본값 1
        'current': getDefaultData(options.current, 1),                              // 현제 페이지. 기본값 1
        'default': options.default,                                                 // true: 전체페이지 2 이하일 경우에도 모두 표시. false: 전체페이지 2 이상일 경우 표시

        'onPageChange': options.onPageChange
    }

    this.$this.empty();
    this.$this.addClass('paging');

    // 페이지
    this.$this.append('<div class="arrow_first hide"></div>');
    this.$this.append('<div class="arrow_left hide"></div>');

    var middle = this.settings.length / 2;

    if (this.settings.maxLength <= 1) {
        if (this.settings.default)
            this.$this.append('<div class="pages arial active" data-page="' + 1 + '">' + 1 + '</div>');

    } else {
        if (this.settings.current > middle) {
            for (var i = this.settings.current - middle; i <= this.settings.maxLength; i++) {
                if (i == this.settings.current + this.settings.length - middle)
                    break;
                if (i == this.settings.current)
                    this.$this.append('<div class="pages arial active" data-page="' + i + '">' + i + '</div>');
                else
                    this.$this.append('<div class="pages arial" data-page="' + i + '">' + i + '</div>');
            }
        }
        else {
            for (var i = 1; i <= this.settings.length; i++) {
                if (i == this.settings.current)
                    this.$this.append('<div class="pages arial active" data-page="' + i + '">' + i + '</div>');
                else
                    this.$this.append('<div class="pages arial" data-page="' + i + '">' + i + '</div>');

                if (i == this.settings.maxLength)
                    break;
            }
        }
    }

    this.$this.append('<div class="arrow_right hide"></div>');
    this.$this.append('<div class="arrow_last hide"></div>');

    if (this.settings.maxLength > 1) {
        $('.arrow_left').removeClass('hide');
        $('.arrow_right').removeClass('hide');

        if (this.settings.maxLength > 2) {
            $('.arrow_first').removeClass('hide');
            $('.arrow_last').removeClass('hide');
        }

        if (this.settings.current == 1) {
            $('.arrow_left').addClass('hide');
            $('.arrow_first').addClass('hide');
        }
        else if (this.settings.current == this.settings.maxLength) {
            $('.arrow_right').addClass('hide');
            $('.arrow_last').addClass('hide');
        }
    }

    $('.pages').on('click', function () {
        common.loading(true);
        paging.setPage(this, paging.settings.onPageChange);
    });

    $('.arrow_first').on('click', function () {
        common.loading(true);
        paging.settings.current = 1;
        paging.setPage(this, paging.settings.onPageChange);
    });

    $('.arrow_last').on('click', function () {
        common.loading(true);
        paging.settings.current = paging.settings.maxLength;
        paging.setPage(null, paging.settings.onPageChange);
    });

    $('.arrow_left').on('click', function () {
        common.loading(true);
        paging.settings.current = paging.settings.current - 1;
        paging.setPage(null, paging.settings.onPageChange);
    });

    $('.arrow_right').on('click', function () {
        common.loading(true);
        paging.settings.current = paging.settings.current + 1;
        paging.setPage(null, paging.settings.onPageChange);
    });
}
paging.setPage = function ($page, callback) {
    var page = $($page).attr('data-page');

    if (common.isEmpty(page)) {
    } else {
        paging.settings.current = parseInt(page);
    }

    paging.init(paging.settings);

    if ($.isFunction(callback))
        callback(paging.settings.current);
}
paging.getPage = function () {
    return paging.settings.current;
}

// 데이터가 빈값이면 기본값 반환
function getDefaultData(defaultData, data) {
    if (defaultData == 0 || defaultData == null || defaultData == '' || defaultData == undefined)
        return parseInt(data);
    else
        return parseInt(defaultData);
}

// 다국어처리
function setStringTable() {
    engine.on('FromClient_PAGetString', function (stringCategory, stringName, stringMessage) {
        //console.log('stringCategory : ' + stringCategory + ' / stringName : ' + stringName + ' / stringMessage : ' + stringMessage);
        _stringTable[stringName] = stringMessage;

    });

    $.each(_stringTable, function (key, value) {
        engine.call('ToClient_PAGetString', 'WEB', key);
    });
}

// 뒤로가기
$(document).on('keydown', function (e) {
    console.log(e.keyCode);

    if (e.target.nodeName != 'TEXTAREA' && e.target.nodeName != 'INPUT') {
        // 뒤로가기(Backspace)
        if (e.keyCode == 8) {
            if ($('#writeForm').length > 0)
                $('#writeForm .btnCancel').click();
            else if ($('#view').length > 0)
                $('#view .btnBack').click();
        }
        // 메세지박스 예(Enter/Spacebar)
        else if (e.keyCode == 13 || e.keyCode == 32) {
            $('.messageBox_yes').click();
        }
        // 메세지박스 아니오(Esc)
        else if (e.keyCode == 27) {
            $('.messageBox_no').click();
        }
    } else {
        // 검색(Enter)
        if (e.keyCode == 13) {
            $('.messageBox_yes').click();
            if (e.target.id == 'SearchText')
                $('#gallery .btnSearch').click();
        }
    }
});
