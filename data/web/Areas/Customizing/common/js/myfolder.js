// 현제페이지 초기화
function initPage() {
    common.loading(true);
    // 클래스버튼 바인딩
    var thisClass = _classTypes[customizing.classType];
    $.each(_classNames, function (key, className) {
        $('.classBox .allSelect').before('<div class="content ' + _classTypes[key] + '" attr-class="' + _classTypes[key] + '" data-tooltip="' + _classNames[key] + '" attr-key="' + key + '"><div class="chk"></div><img src="/Areas/Customizing/common/img/class_' + key + '.png"/></div>');
    });

    if (_searchConditions['myfolder'].class.length > 0) {
        $.each(_searchConditions['myfolder'].class, function (key, value) {
            common.classSelected($('[attr-key="' + value + '"]'));
        });
    } else {
        common.classSelected($('#myfolder #searchBox .content'));
    }

    if ($('#myfolder #searchBox .content.active').length >= Object.keys(_classNames).length) {
        $('.btn.allSelect').text(_stringTable.WEB_CUSTOMIZING_ALL_UNSELECT);
        $('.btn.allSelect').addClass('selected')
        $('.btn.allSelect').attr('data-selector', 'off');
    } else {
        $('.btn.allSelect').text(_stringTable.WEB_CUSTOMIZING_ALL_SELECT);
    }

    getCustomizingByMyfolder();
}

// 커스터마이징 가져오기
function getCustomizingByMyfolder() {
    common.loading(true);
    var currentPage = common.getCookie('myfoler');
    if (common.isEmpty(currentPage))
        currentPage = $('#page').val();

    var searchConditions = _searchConditions.myfolder;
    var search = {
        'classType': common.isEmpty(searchConditions.class) ? '' : searchConditions.class.join(','),
        'page': parseInt(currentPage)
    };
    var custom = common.getCustomizing(searchConditions.url, search);

    $('#page').val(custom.page);
    $('#totalPage').val(custom.totalPage);

    common.bindCMBox(custom.customizing);

    paging.init({
        'id': 'paging',
        'length': $('#length').val(),
        'maxLength': $('#totalPage').val(),
        'current': parseInt(currentPage),
        'default': false,
        'onPageChange': function (page) {
            $('#page').val(page);
            common.setCookie('myfoler', page);
            getCustomizingByMyfolder();
            console.log(page);
        }
    });

}

// 클래스 선택/해제
$('#myfolder').on('click', '.classBox .content', function (e) {
    var $this = $(this);
    $('#page').val(1);
    common.setCookie('myfoler', 1);

    common.classUnSelected($('#myfolder .classBox .content'));
    common.classSelected($this);

    if ($('#myfolder .classBox .content').length == $('#myfolder .classBox .content.active').length) {
        $('.btn.allSelect').text(_stringTable.WEB_CUSTOMIZING_ALL_UNSELECT);
        $('.btn.allSelect').addClass('selected')
        $('#myfolder .classBox .allSelect').attr('data-selector', 'off');
    } else {
        $('#myfolder .classBox .allSelect').attr('data-selector', 'on');
        $('.btn.allSelect').text(_stringTable.WEB_CUSTOMIZING_ALL_SELECT);
        $('.btn.allSelect').removeClass('selected');
    }

    getCustomizingByMyfolder();
});

// 클래스 전체선택/전체해제
$('#myfolder').on('click', '.classBox .allSelect', function (e) {
    var thisClass = _classTypes[customizing.classType];
    var $content = $('#myfolder .classBox .content');
    $('#page').val(1);
    common.setCookie('myfoler', 1);

    // 전체선택
    if ($(this).attr('data-selector') == 'on') {
        $('.btn.allSelect').text(_stringTable.WEB_CUSTOMIZING_ALL_UNSELECT);
        $('.btn.allSelect').addClass('selected')
        $('#myfolder .classBox .content').removeClass('active');
        $(this).attr('data-selector', 'off');
        common.classSelected($content);
    }
    // 전체해제
    else {
        $('.btn.allSelect').text(_stringTable.WEB_CUSTOMIZING_ALL_SELECT);
        $('.btn.allSelect').removeClass('selected');
        //$('.btn.allSelect:not(.' +thisClass+ ')').removeClass('selected')
        $(this).attr('data-selector', 'on');
        common.classUnSelected($content);
        $('#myfolder .classBox .content.' + thisClass).click();
    }

    getCustomizingByMyfolder();
});

