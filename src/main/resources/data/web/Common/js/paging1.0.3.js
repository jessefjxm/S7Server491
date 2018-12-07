/* 
 paging ver 1.0.3 - 2016-06-24 
 jQuery paging 

    1.0.1 페이지가 없는데 length페이지 갯수대로 나오는 현상 처리 
    1.0.2 주석 추가 
    1.0.3 화살표버튼 이미지 변경 
          buttonFixed : 화살표버튼 항상 보이기/유동적으로 보이기 ,  
          arrowButton : 화살표버튼 숨김 사용/미사용,  
          pageFixed : 페이지버튼 length 고정/중앙 고정 
*/
$(function (e) {
    var settings = { length: '', current: '', maxLength: '', custom: { 'pageFixed': false, 'buttonFixed': true, 'arrowButton': true }, onPageChange: '' };
    var getDefaultData = function (defaultData, data) {
        if (defaultData == null || defaultData == '' || defaultData == undefined)
            return parseInt(data);
        else
            return parseInt(defaultData);
    }, isDefaultData = function (defaultData, data) {
        if (defaultData.toString() == null || defaultData.toString() == '' || defaultData.toString() == undefined)
            return data;
        else
            return defaultData;
    }, drawPage = function (startNum, endNum, currentNum) {
        var str = '';
        for (var i = startNum; i < endNum + 1; i++) {
            if (i == currentNum)
                str += '<div class="pages arial active" data-page="' + i + '">' + i + '</div>';
            else
                str += '<div class="pages arial" data-page="' + i + '">' + i + '</div>';
        }
        return str;
    }

    $.fn.jPaging = function (option) {
        settings = option;
        $(this).empty().addClass('paging');

        $(this).setPage('', option.callback);

        $(document).on('click', '.paging > div', function (e) {
            $(this).setPage('', settings.onPageChange);
        });
    }
    $.fn.setPage = function (pageNum, callback) {
        var selectedPage = $.isNumeric(pageNum) ? pageNum : $(this).attr('data-page');
        var current = 1;
        var $page = $(this).hasClass('paging') ? $(this) : $(this).parents('.paging');

        if (selectedPage == null || selectedPage == '' || selectedPage == undefined) {

        } else {
            settings.current = parseInt(selectedPage);
        }

        var pagingStr = '';

        if (settings['custom'].pageFixed == true) {
            var startNum = settings.current % settings.length == 1 ? settings.current : (parseInt((settings.current - 1) / settings.length) * settings.length) + 1;
            var endNum = startNum + settings.length > settings.maxLength ? settings.maxLength : startNum + settings.length - 1;
            pagingStr = drawPage(startNum, endNum, settings.current);

        } else {
            var middle = parseInt(settings.length / 2);
            var startNum = parseInt(settings.current) - middle;
            var endNum = parseInt(settings.current) + middle > parseInt(settings.maxLength) ? settings.maxLength : parseInt(settings.current) + middle;

            if (settings.current > middle)
                pagingStr = drawPage(startNum, endNum, settings.current);
            else
                pagingStr = drawPage(1, settings.length, settings.current);
        }

        if (settings['custom'].arrowButton == true) {
            if (settings['custom'].buttonFixed == true) {
                var prev = (settings.current - 1) < 1 ? 1 : (settings.current - 1);
                var next = (settings.current + 1) > settings.maxLength ? settings.maxLength : (settings.current + 1);
                var prevDisabled = settings.current < 2 ? ' disabled' : '';
                var nextDisabled = settings.current >= settings.maxLength ? ' disabled' : '';

                pagingStr =
                    '<div class="arrow_first' + prevDisabled + '" data-page="1"></div><div class="arrow_left' + prevDisabled + '" data-page="' + prev + '"></div>' +
                    pagingStr +
                    '<div class="arrow_right' + nextDisabled + '" data-page="' + next + '"></div><div class="arrow_last' + nextDisabled + '" data-page="' + settings.maxLength + '"></div>';
            } else {
                var prev = (settings.current - 1) < 1 ? 1 : (settings.current - 1);
                var next = (settings.current + 1) > settings.maxLength ? settings.maxLength : (settings.current + 1);
                var prevHide = settings.current < 2 ? ' hide' : '';
                var nextHide = settings.current >= settings.maxLength ? ' hide' : '';

                pagingStr =
                    '<div class="arrow_first' + prevHide + '" data-page="1"></div><div class="arrow_left' + prevHide + '" data-page="' + prev + '"></div>' +
                    pagingStr +
                    '<div class="arrow_right' + nextHide + '" data-page="' + next + '"></div><div class="arrow_last' + nextHide + '" data-page="' + settings.maxLength + '"></div>';
            }

        } else {
            pagingStr = '<div class="arrow_first hide"></div><div class="arrow_left hide"></div>' + pagingStr + '<div class="arrow_right hide"></div><div class="arrow_last hide"></div>';
        }

        $page.empty().append(pagingStr);

        if ($.isFunction(callback))
            callback(settings.current);

    }
    $.fn.getPage = function () {
        return $(this).children('.pages.active').attr('data-page');
    }
    $.fn.setMaxLength = function (number) {
        settings.maxLength = number;
        $(this).setPage(settings.current);
    }
    $.fn.getMaxLength = function () {
        return settings.maxLength;
    }
});