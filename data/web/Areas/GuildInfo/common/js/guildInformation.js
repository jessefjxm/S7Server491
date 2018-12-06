// br태그 변환
function makeBr(data) {
    if (pearl.common.isEmpty(data))
        return '';
    return data.replace(/\n/gi, '<br />');
}

// 날짜/시간 0# 처리
function formatZero(data) {
    return data.toString().length > 1 ? data : '0' + data;
}

///////////////////////////////////////////////////////////////////////////////////////////////////
var _classTypes = new Object();

// 클래스 네임
function getClassTypes(callback) {
    var classNo = [0, 4, 8, 12, 16, 20, 21, 24, 25, 26, 28, 31];
    var classNames = [];
    var classTypes = new Object();
    $.each(classNo, function (row, data) {
        engine.call('ToClient_GetPossibleClassTypeName', data);
    });
    
    engine.on('FromClient_GetPossibleClassTypeName', function (classNo, className) {
        classTypes[classNo] = className;
        setClassType();
    });

    function setClassType() {
        if (classNo.length == Object.keys(classTypes).length) {
            _classTypes = classTypes
            if($.isFunction(callback))
                callback();
            //alert(JSON.stringify(classTypes));
        }
    }
}

// 체크박스 사용여부
function onChecked($this) {
    var _this = $this.parent().parent().children(':first').children();
    if (pearl.common.isEmpty($this.val()))
        _this.removeClass('active');
    else
        _this.addClass('active');
}

// 코멘트 갯수
function getCommentCount(guildNo) {
    var url = '/GuildInfo/CommentCount/';
    var data = { "guildNo": guildNo };
    var rs = 0;

    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        async:false,

        beforeSend: function (xhr, settings) {
        },
        complete: function (xhr, status) {
        },
        success: function (result, status, xhr) {
            pearl.common.error(result, function () {
                rs = result;
            });
        },
        error: function (xhr, status, error) {
            pearl.common.error(xhr.responseText);
            console.log('xhr : ' + JSON.stringify(xhr) + ' / status : ' + status + ' / error : ' + error);
        }
    });
    return rs;
}

// 코멘트 탭의 갯수 변경
function changeCommentCount(totalCount) {
    $('.tab_comment').attr('data-attr', totalCount);
}

///////////////////////////////////////////////////////////////////////////////////////////////////

var paging = window.paging = {
    $this: '',
    settings: [],
    init: false,
    getPage: false
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

        'onPageChange': options.onPageChange
    }

    this.$this.empty();
    this.$this.addClass('paging');

    // 페이지
    this.$this.append('<div class="arrow_first hide"><span data-str="WEB_GUILDINFO_COMENT_NAVI_FIRST"></span></div>');
    this.$this.append('<div class="arrow_left hide"></div>');

    var middle = this.settings.length / 2;

    //if( middle < 1) middle = 1

    if (this.settings.maxLength < 1) {
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

                if (i == this.settings.maxLength)
                    break;
            }
        }
        else {
            for (var i = 1; i <= this.settings.length; i++) {
                if (i == this.settings.current)
                    this.$this.append('<div class="pages arial active" data-page="' + i + '">' + i + '</div>');
                else
                    this.$this.append('<div class="pages arial" data-page="' + i + '">' + i + '</div>');
                if (i == this.settings.length)
                    break;
                if (i == this.settings.maxLength)
                    break;
            }
        }
    }
    this.$this.append('<div class="arrow_right hide"></div>');
    this.$this.append('<div class="arrow_last hide"><span data-str="WEB_GUILDINFO_COMENT_NAVI_LAST"></span></div>');

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
        setPage(this, paging.settings.onPageChange);
    });

    $('.arrow_first').on('click', function () {
        paging.settings.current = 1;
        setPage(this, paging.settings.onPageChange);
    });

    $('.arrow_last').on('click', function () {
        paging.settings.current = paging.settings.maxLength;
        setPage(null, paging.settings.onPageChange);
    });

    $('.arrow_left').on('click', function () {
        paging.settings.current = paging.settings.current - 1;
        setPage(null, paging.settings.onPageChange);
    });

    $('.arrow_right').on('click', function () {
        paging.settings.current = paging.settings.current + 1;
        setPage(null, paging.settings.onPageChange);
    });
}

// 데이터가 빈값이면 기본값 반환
function getDefaultData(defaultData, data) {
    defaultData = defaultData.toString();
    if (defaultData == null || defaultData == '' || defaultData == undefined || parseInt(defaultData) < 0)
        return parseInt(data);
    else
        return parseInt(defaultData);
}

// 해당 페이지로 이동
function setPage($page, callback) {
    var page = $($page).attr('data-page');
    //$('.pages.active').removeClass('active');

    if (page == null || page == '' || page == undefined) {
    } else {

        //$page.addClass('active');
        paging.settings.current = parseInt(page);
    }

    paging.init(paging.settings);

    if ($.isFunction(callback))
        callback(paging.settings.current);
}
