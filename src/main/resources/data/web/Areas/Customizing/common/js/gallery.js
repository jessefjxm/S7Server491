function initPage() {
    common.loading(true);
    // 현재 클래스
    var thisClass = _classTypes[customizing.classType];

    // 클래스 바인딩
    $.each(_classNames, function (key, className) {
        $('#searchBox hr').before('<div class="content ' + _classTypes[key] + '" attr-class="' + _classTypes[key] + '" data-tooltip="' + _classNames[key] + '" attr-key="' + key + '"><div class="chk"></div><img src="/Areas/Customizing/common/img/class_' + key + '.png"/></div>');
    });

    // 검색기간 바인딩 
    $.each(_category, function (key, value) {
        $('.search .category').append('<a id="category_' + key + '" attr-category-key="' + key + '" data-str="' + value + '"> </a>');

        if (key + 1 < _category.length)
            $('.search .category').append('<span>|</span>');
    });

    // 이전에 검색한 기록이 있으면 이전 검색 선택, 없으면 현재 기본값 선택
    if (_searchConditions['gallery'].class.length > 0) {
        $.each(_searchConditions['gallery'].class, function (key, value) {
            common.classSelected($('[attr-key="' + value + '"]'));
        });
    } else {
        common.classSelected($('#gallery #searchBox .content.' + thisClass));
    }
    if (_searchConditions['gallery'].category.length > 0) {
        $('#gallery .search .category a').removeClass('active');
        $('#gallery .category #category_' + _searchConditions['gallery'].category).addClass('active');
        _searchConditions['gallery'].category = $('#gallery .category #category_' + _searchConditions['gallery'].category).attr('attr-category-key');

    } else {
        $('#gallery .search .category a').removeClass('active');
        $('#gallery .category a').first().addClass('active');
        _searchConditions['gallery'].category = $('#gallery .category a').first().attr('attr-category-key');
    }

    if (_searchConditions['gallery'].text.length > 0)
        $('#gallery #SearchText').val(_searchConditions['gallery'].text);

    // 모두 버튼 on/off 여부
    if ($('#gallery #searchBox .content.active').length >= Object.keys(_classNames).length) {
        $('.allSelect').addClass('selected')
        $('.allSelect').attr('data-selector', 'off');
    }

    getCustomizingByGallery();

    $('#author').val(_searchConditions.gallery.author);
}

$('.tabList li').on('click', function (e) {
    var str = $('#gallery #author').val();
    if (!common.isEmpty(str))
        _searchConditions.gallery.author = '';
})

// 클래스 선택/해제
$('#gallery').on('click', '#searchBox .content', function (e) {
    var $this = $(this);
    $('#page').val(1);
    common.setCookie('gallery', 1);

    common.classUnSelected($('#gallery #searchBox .content.active'));
    common.classSelected($this);

    if ($('#gallery #searchBox .content').length == $('#gallery #searchBox .content.active').length) {
        $('#gallery #searchBox .allSelect').attr('data-selector', 'off');
        $('.allSelect').addClass('selected')
    } else {
        $('#gallery #searchBox .allSelect').attr('data-selector', 'on');
        $('.allSelect').removeClass('selected')
    }
    _searchConditions.gallery.author = '';

    getCustomizingByGallery();

});

// 클래스 전체선택/전체해제
$('#gallery').on('click', '#searchBox .allSelect', function (e) {
    var $content = $('#gallery #searchBox .content');
    var thisClass = _classTypes[customizing.classType];
    $('#page').val(1);
    common.setCookie('gallery', 1);

    // 전체선택
    if ($(this).attr('data-selector') == 'on') {
        $('.allSelect').addClass('selected')
        $('#gallery #searchBox .content').removeClass('active');
        $(this).attr('data-selector', 'off');
        common.classSelected($content);
    }
    // 전체해제
    else {
        $('.allSelect').removeClass('selected')
        //$('.btn.allSelect:not(.' +thisClass+ ')').removeClass('selected')
        $(this).attr('data-selector', 'on');
        common.classUnSelected($content);
        $('#gallery #searchBox .content.' + thisClass).click();
    }
    _searchConditions.gallery.author = '';

    getCustomizingByGallery();

    //$('#gallery #searchBox .content').click();
});

// 카테고리 선택
$('#gallery').on('click', '.search .category a', function () {
    $('#gallery .search .category a').removeClass('active');
    $(this).addClass('active');
    $('#page').val(1);
    common.setCookie('gallery', 1);

    _searchConditions.gallery.category = $(this).attr('attr-category-key');
    _searchConditions.gallery.author = '';
    getCustomizingByGallery();
});

// 검색
$('#gallery').on('click', '.btnSearch', function (e) {
    common.setCookie('gallery', 1);
    _searchConditions['gallery'].text = $('#gallery #SearchText').val();
    getCustomizingByGallery();
});

$('#gallery').on('click', '#searchBox a, #searchBox .content, #searchBox .search .category > a, .searchLayer', function (e) {
    e.preventDefault();
    e.stopPropagation();
});

// 검색레이어
$('#gallery').on('click', '.btnSearchShow', function (e) {
    $('#gallery .searchLayer').toggleClass('hide');
});

// 검색레이어 숨기기
$('#gallery, #gallery .btnClose').on('click', function (e) {
    $('#gallery .searchLayer').addClass('hide');
});

// 커스터마이징 가져오기
function getCustomizingByGallery() {
    common.loading(true);
    var currentPage = common.getCookie('gallery');
    if (common.isEmpty(currentPage))
        currentPage = $('#page').val();

    _searchConditions['gallery'].text = $('#gallery #SearchText').val();

    var searchConditions = _searchConditions.gallery;
    console.log(searchConditions);

    var search = {
        'classType': common.isEmpty(searchConditions.class) ? '' : searchConditions.class.join(',')
        , 'categoryCode': searchConditions.category
        , 'searchText': searchConditions.text
        , 'page': parseInt(currentPage)
        //, 'orderType': common.isEmpty(searchConditions.sort) ? '1' : searchConditions.sort
    };
    var url = searchConditions.url;

    if (common.isEmpty(_searchConditions.gallery.text)) {
        if (!common.isEmpty(_searchConditions.gallery.author)) {
            search.author = _searchConditions.gallery.author
            search.searchText = '';
            url = '/Customizing/Gallery/GetCustomizingAuthorGallery';
        }
    } else {
        _searchConditions.gallery.author = '';
    }

    var custom = common.getCustomizing(url, search);

    $('#page').val(custom.page);
    $('#totalPage').val(custom.totalPage);

    common.bindCMBox(custom.customizing);

    paging.init({
        'id': 'paging',
        'length': $('#length').val(),
        'maxLength': $('#totalPage').val(),
        'current': parseInt(currentPage),
        'onPageChange': function (page) {
            $('#page').val(page);
            common.setCookie('gallery', page);
            getCustomizingByGallery();
            console.log(page);
        }
    });
}