//    openPage: false,                // TODO:테스트 필요 페이지 로드 시작
//    closePage: false,               // TODO:테스트 필요 페이지 로드 완료
//    initButton: false,              // TODO:테스트 필요 버튼 초기화
//    initTooltip: false,             // TODO:테스트 필요 툴팁이벤트 초기화
//    isEmpty: false,                 // TODO:테스트 필요 빈값인 경우 true 반환
//    isObject: false,                // TODO:테스트 필요 array 또는 object인 경우 true 반환
//    messageBox: false,              // TODO:테스트 필요 메세지박스
//    pageMove: false,                // TODO:테스트 필요 페이지 이동
//    loading: false                  // TODO:테스트 필요 로딩레이어
//    setCookie: false,               // TODO:테스트 필요 쿠키 저장
//    getCookie: false,               // TODO:테스트 필요 쿠키 가져오기
//    error: false,                   // TODO:테스트 필요 에러코드/에러페이지 반환
//    getLanguageCode: false,         // TODO:테스트 필요 현재 언어 가져오기

//    dateFormat: false,              // TODO:테스트 필요 Date Format (yyyy-mm-dd)
//    dateTimeFormat: false,          // TODO:테스트 필요 DateTime Format (yyyy-mm-dd hh:mm:ss)
//    setStringTable: false,          // TODO:테스트 필요 다국어 가져오기(페이지 로드시 최초 1회만 실행할것)
//    getStringTable: false,          // TODO:테스트 필요 key값으로 다국어 가져오기
//    stringConvert: false,           // TODO:테스트 필요 다국어처리(key값을 해당 국가 언어로 변경)

var pearl = window.pearl = {};

pearl.common = {
    _languageCode: '',

    openPage: function (arrString) {
        engine.call("ToClient_SetInputMode", false);

        $.ajaxSetup({ cache: false });

        pearl.string.setStringTable(arrString);
        pearl.common.initButton();
    },
    closePage: function () {
        //pearl.string.stringConvert();
        pearl.common.initTooltip();
    },

    initButton: function () {
        var $btns = $('.btn');
        $.each($btns, function (row, btn) {
            $(btn).css('background-color', '#' + $(btn).attr('data-color'));
        });
    },
    initTooltip: function () {
        // 툴팁 마우스오버
        $(document).on('mouseover', '[data-tooltip]', function (e) {
            var tooltip = pearl.string.getStringTable($(this).attr('data-tooltip'));

            // 툴팁 생성
            if ($('#tooltipArea').length <= 0)
                $('body').append('<div id="tooltipArea"><span class="tooltip">' + tooltip + '</span></div>');

            // 툴팁 크기
            var tooltipWidth = parseInt($('#tooltipArea .tooltip').css('width')) + parseInt($('#tooltipArea .tooltip').css('padding-left')) + parseInt($('#tooltipArea .tooltip').css('padding-right'));
            var tooltipHeight = parseInt($('#tooltipArea .tooltip').css('height')) + parseInt($('#tooltipArea .tooltip').css('padding-top')) + parseInt($('#tooltipArea .tooltip').css('padding-bottom'));

            var thisWidth = parseInt($(this).css('width'));
            var thisHeight = parseInt($(this).css('height'));

            // 툴팁 위치 조정
            var left = $(this).offset().left;
            var right = $('body').width() - ($(this).offset().left + $(this).outerWidth());
            var top = $(this).offset().top - tooltipHeight - 10;
            var bottom = $(this).offset().top + thisHeight + 10;

            if (top < 0)
                top = $(this).offset().top + thisHeight + 10;
            if (bottom + tooltipHeight > $('body').height())
                bottom = $('body').height() - thisHeight - tooltipHeight - 10;

            if (left + tooltipWidth > $('body').width())
                left = $('body').width() - tooltipWidth - 10;
            if (right + tooltipWidth > $('body').width())
                right = tooltipWidth - 10;

            if (!pearl.common.isEmpty($(this).attr('data-tooltip-position'))) {
                var position = $(this).attr('data-tooltip-position');

                if (position == 'right')
                    $('#tooltipArea .tooltip').css('right', right).css('top', top);
                else if (position == 'bottom')
                    $('#tooltipArea .tooltip').css('left', left).css('top', bottom);
                else if (position == 'rightBottom')
                    $('#tooltipArea .tooltip').css('right', right).css('top', bottom);
                else
                    $('#tooltipArea .tooltip').css('left', left).css('top', top);
            } else {
                $('#tooltipArea .tooltip').css('left', left).css('top', top);
            }

        });

        // 툴팁 마우스아웃
        $(document).on('mouseout', '[data-tooltip]', function (e) {
            $('#tooltipArea').remove();
        });

        // 툴팁에 마우스오버할 경우 툴팁 숨김 
        //$(document).on('mouseover', '[data-tooltip] .tooltip', function (e) {
        //    $(this).find('.tooltip').addClass('hide');
        //    e.stopPropagation();
        //    return false;
        //});
    },

    isEmpty: function (data) {
        if (typeof (data) == 'string')
            data = data.trim();

        return data == null || data == '' || data == undefined;
    },
    isObject: function (data) {

        if ($.type(data) == 'object') {
            return true;
        } else {
            try {
                data = $.parseJSON(data);
            } catch (err) {
                return false;
            }

            if ($.type(data) == 'object')
                return true;
            else
                return false;
        }
    },
    isEmptyObject: function (data) {
        if (pearl.common.isObject(data)) {

            try {
                data = $.parseJSON(data);
            } catch (err) {
            }

            if (Object.keys(data).length < 1)
                return true;
            else
                return false;
        } else {

            return false;
        }

    },
    
    messageBox: function (str, callback) {
        if ($('#messageBox').length > 0)
            $(this).remove();

        var appendStr =
            '<div id="messageBox">' +
                '<div class="messageBox_content">' +
                    '<div class="messageBox_str"><span>' + pearl.string.getStringTable(str) + '</span></div>' +
                    '<div class="messageBox_button">' +
                        '<div class="btn messageBox_yes"data-color="3d382d"> ' + pearl.string.getStringTable('WEB_MESSAGE_YES') + ' </div>' +
                        '<div class="btn messageBox_no"data-color="3d382d"> ' + pearl.string.getStringTable('WEB_MESSAGE_NO') + ' </div>' +
                    '</div>' +
                '</div>' +
                '<div class="messageBox_bg hide"></div>' +
            '</div>';

        $('body').append(appendStr);
        $('#messageBox').show(function () {
            engine.call("ToClient_SetLuaInputHold", true);
        });
        $('.messageBox_bg').show();

        // 팝업 뜨면 포커스 없앤 후 예/아니오 버튼 클릭시 포커스줌
        var focusElement = $(document.activeElement);
        focusElement.blur();

        if (!$.isFunction(callback)) {
            $('.messageBox_yes').addClass('messageBox_default').text(pearl.string.getStringTable('WEB_MESSAGE_DONE'));
            $('.messageBox_no').addClass('hide');
        }
        var $btns = $('#messageBox .btn');
        $.each($btns, function (row, btn) {
            $(btn).css('background-color', '#' + $(btn).attr('data-color'));
        });

        // 예
        $('.messageBox_yes').on('click', function (e) {
            focusElement.focus();
            $('.messageBox_bg').fadeOut(10, function () {
                $(this).remove();
            });
            $('#messageBox').fadeOut(10, function () {
                $(this).remove();
            });

            if ($.isFunction(callback))
                callback(true);
            engine.call("ToClient_SetLuaInputHold", false);
        });

        // 아니오
        $('.messageBox_no').on('click', function (e) {
            focusElement.focus();
            $('.messageBox_bg').fadeOut(10, function () {
                this.remove();
            });
            $('#messageBox').fadeOut(10, function () {
                this.remove();
            });
            if ($.isFunction(callback))
                callback(false);
            engine.call("ToClient_SetLuaInputHold", false);
        });
    },
    pageMove: function (url, params) {
        var param = params != null && params != undefined ? '?' + params.join('&') : '';
        var fullUrl = url + param;

        $('#contents').empty();

        $("#contents").load(fullUrl, function (response, status, xhr) {
            pearl.common.error(response);

            //if (status == "error") {
            //    var msg = "load error";
            //    $("#main").html(msg + xhr.status + " | " + xhr.statusText + "|" + response);
            //}
        });
    },
    loading: function (is) {
        var loading = '<div id="loading"><div class="layer"><div class="spin"></div></div></div>';
        if (is)
            $('body').append(loading);
        else
            $('#loading').remove();
    },

    setCookie: function (cookieName, cookieValue) {
        // 없으면 생성
        if (pearl.common.isEmpty($.getCookie(cookieName))) {
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
    },
    getCookie: function (cookieName) {
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
    },

    error: function (result, callback) {
        if (parseInt(result) < 0) {
            console.log('errorCode:' + result);

            if (result == '-8888' || result == '-10001')
                location.href = '/ErrorPage';
            else if (result == '-90007' || result == '-90008')
                pearl.common.messageBox(result);
            else if(!pearl.common.isObject(result))
                location.href = '/ErrorPage';
            else
                pearl.common.messageBox(result);
        } else {
            if ($.isFunction(callback))
                callback();
        }
            
    },
    getLanguageCode: function () {
        return pearl.common._languageCode;
    },

    setHiddenField: function (obj) {
        $.each(obj, function (key, val) {
            if ($('#' + key).length > 0)
                $('#' + key).val(val);
        });

    },
    getHiddenField: function (id) {
        var obj = {};

        $.each(id, function (key, val) {
            if ($('#' + val).length > 0)
                obj[val] = $('#' + val).val();
        });
    }
}

pearl.string = {
    _stringTable: { 'WEB_MESSAGE_YES': '', 'WEB_MESSAGE_NO': '', 'WEB_MESSAGE_DONE': '' },
    dateFormat: function (date) {
        function formatZero(data) {
            return data.toString().length > 1 ? data : '0' + data;
        }

        if ((new Date(date)).toDateString() == 'Invalid Date')
            date = new Date(parseInt(date.substr(6)));
        else
            date = new Date(date);

        return date.getFullYear() + '-' + formatZero(date.getMonth() + 1) + '-' + formatZero(date.getDate());
    },
    dateTimeFormat: function (date) {
        function formatZero(data) {
            return data.toString().length > 1 ? data : '0' + data;
        }

        if ((new Date(date)).toDateString() == 'Invalid Date')
            date = new Date(parseInt(date.substr(6)));
        else
            date = new Date(date);

        return date.getFullYear() + '-' + formatZero(date.getMonth() + 1) + '-' + formatZero(date.getDate()) + ' ' + formatZero(date.getHours()) + ':' + formatZero(date.getMinutes()) + ':' + formatZero(date.getSeconds());
    },
    htmlDecoding: function (data) {
        if (!pearl.common.isEmpty(data))
            return data.replace(/&#39;/gi, "'").replace(/&lt;/gi, "<").replace(/&gt;/gi, ">").replace(/&quot;/gi, "\\").replace(/&amp;/gi, "&");
        else
            return data;
    },
    htmlEncoding: function (data) {
        if (!pearl.common.isEmpty(data))
            return data.replace(/'/gi, "&#39;").replace(/</gi, "&lt;").replace(/>/gi, "&gt;").replace(/\\/gi, "&quot;").replace(/&/gi, "&amp;");
        else
            return data;
    },

    setStringTable: function (arrString) {
        $.each(pearl.string._stringTable, function (key, val) {
            if (pearl.common.isEmpty(val))
                return false;
        });

        pearl.string._stringTable = $.extend(pearl.string._stringTable, arrString);

        engine.on('FromClient_PAGetString', function (stringCategory, stringName, stringMessage) {
            //console.log('stringCategory : ' + stringCategory + ' / stringName : ' + stringName + ' / stringMessage : ' + stringMessage);
            pearl.string._stringTable[stringName] = stringMessage;
            pearl.string.stringConvert();
        });

        $.each(pearl.string._stringTable, function (key, value) {
            engine.call('ToClient_PAGetString', 'WEB', key);
        });
    },
    getStringTable: function (key) {
        // value가 없을경우 key를 반환
        if (pearl.common.isEmpty(pearl.string._stringTable[key]))
            return key;
        else
            return pearl.string.htmlDecoding(pearl.string._stringTable[key]);
    },
    stringConvert: function (el, callback) {
        var $str;
        if (pearl.common.isEmpty(el))
            $str = $('[data-str], [data-str-placeholder]');
        else
            $str = $(el);

        $.each($str, function (key, value) {
            var $el = $(value);
            if (!pearl.common.isEmpty($el.attr('data-str'))) {
                var key = $el.attr('data-str');
                $('[data-str="' + key + '"]').html(pearl.string.getStringTable(key));
            } else if (!pearl.common.isEmpty($el.attr('data-str-placeholder'))) {
                var key = $el.attr('data-str-placeholder');
                $('[data-str-placeholder="' + key + '"]').attr('placeholder', pearl.string.getStringTable(key));
            }
        });

        if ($.isFunction(callback))
            callback();
    }
}

$(function () {
    $('head').append('<scr' + 'ipt src ="/Common/js/language.js"></scr' + 'ipt>');

    // 단축키 해제
    //engine.call("ToClient_SetLuaInputHold", false);

    // 뒤로가기
    $(document).on('keydown', function (e) {
        //console.log(e.keyCode);

        if (e.target.nodeName != 'TEXTAREA' && e.target.nodeName != 'INPUT') {
            // 뒤로가기(Backspace)
            if (e.keyCode == 8) {
                //if ($('#writeForm').length > 0)
                //    $('#writeForm .btnCancel').click();
                //else if ($('#view').length > 0)
                //    $('#view .btnBack').click();
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
            if (e.keyCode == 13) {
                $('.messageBox_yes').click();
                // 검색(Enter)
                if (e.target.id == 'search')
                    $('.btn.search').click();
            }
        }
    });

    // 포커스일 경우 input모드
    $(document).on('focus', 'input, textarea', function () {
        engine.call("ToClient_SetInputMode", true);
    });

    // 포커스해제될 경우 input모드 해제
    $(document).on('blur', 'input, textarea', function () {
        engine.call("ToClient_SetInputMode", false);
    });

});


