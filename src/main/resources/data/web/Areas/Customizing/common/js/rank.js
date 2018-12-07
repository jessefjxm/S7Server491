function initPage() {
    common.loading(true);
    // 클래스버튼 바인딩
    var thisClass = _classTypes[customizing.classType];

    $.each(_classNames, function (key, className) {
        $('#searchBox hr').before('<div class="content ' + _classTypes[key] + '" attr-class="' + _classTypes[key] + '" data-tooltip="' + _classNames[key] + '" attr-key="' + key + '"><div class="chk"></div><img src="/Areas/Customizing/common/img/class_' + key + '.png"/></div>');
    });

    var id = ['LastDays_1', 'LastDays_7', 'LastDays_30', 'CumulativeDownloads'];
    var keys = [-1, -7, -30, -365];

    // 검색기간 바인딩 
    $.each(_date, function (key, value) {
        $('.search .radioArea').append(
            '<div>' +
            '<input type="radio" id="' + id[key] + '" class="radio" name="searchTime" attr-date-key="' + keys[key] + '" value="" /><label for="' + id[key] + '"></label>' +
            '<span data-str="' + value + '"></span>' +
            '</div>');
    });

    if (_searchConditions['rank'].class.length > 0) {
        $.each(_searchConditions['rank'].class, function (key, value) {
            common.classSelected($('[attr-key="' + value + '"]'));
        });
    } else {
        common.classSelected($('#rank #searchBox .content.' + thisClass));
    }

    if (_searchConditions['rank'].date.length > 0)
        $('#rank [attr-date-key="' + _searchConditions['rank'].date + '"]').prop('checked', true);
    else
        $('#rank [name="searchTime"]').first().prop('checked', true);

    if ($('#rank #searchBox .content.active').length >= Object.keys(_classNames).length) {
        $('.allSelect').addClass('selected')
        $('.allSelect').attr('data-selector', 'off');
    }

    //getCustomizingByRank();
}

// 클래스 선택/해제
$('#rank').on('click', '#searchBox .content', function (e) {
    var $this = $(this);

    common.classUnSelected($('#rank #searchBox .content'));
    common.classSelected($this);

    if ($('#rank #searchBox .content').length == $('#rank #searchBox .content.active').length) {
        $('#rank #searchBox .allSelect').attr('data-selector', 'off');
        $('.allSelect').addClass('selected')
    } else {
        $('#rank #searchBox .allSelect').attr('data-selector', 'on');
        $('.allSelect').removeClass('selected');
    }

    getCustomizingByRank();

});

// 클래스 전체선택/전체해제
$('#rank').on('click', '#searchBox .allSelect', function (e) {
    var $content = $('#rank #searchBox .content');
    var thisClass = _classTypes[customizing.classType];

    // 전체선택
    if ($(this).attr('data-selector') == 'on') {
        $('.allSelect').addClass('selected');
        $('#rank #searchBox .content').removeClass('active');
        $(this).attr('data-selector', 'off');
        common.classSelected($content);
    }
    // 전체해제
    else {
        $('.allSelect').removeClass('selected')
        //$('.btn.allSelect:not(.' +thisClass+ ')').removeClass('selected')
        $(this).attr('data-selector', 'on');
        common.classUnSelected($content);
        // TODO: 현재 캐릭터의 클래스만 남기고 모두 선택 해제
        $('#rank #searchBox .content.' + thisClass).click();
    }

    getCustomizingByRank();
});

// 기간 선택
$('#rank').on('change', '[name="searchTime"]', function (e) {
    _searchConditions['rank'].date = $(this).attr('attr-date-key');
    getCustomizingByRank();
});

// 커스터마이징 가져오기
function getCustomizingByRank() {
    common.loading(true);
    var searchConditions = _searchConditions.rank;
    var search = {
        'classType': common.isEmpty(searchConditions.class) ? '' : searchConditions.class.join(','),
        'sortType': searchConditions.date
    };
    var custom = common.getCustomizing(searchConditions.url, search);

    common.bindCMBox(custom.customizing);
}