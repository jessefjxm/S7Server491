var tabList = [
    { 'key': 'myfolder', 'strKey': 'WEB_CUSTOMIZING_MYFOLDER', 'icon': '/Areas/Customizing/common/img/icn_myfolder.png', 'url': '/Customizing/MyFolder' },
    { 'key': 'gallery', 'strKey': 'WEB_CUSTOMIZING_USER_GALLERY', 'icon': '/Areas/Customizing/common/img/icn_gallery.png', 'url': '/Customizing/Gallery' },
    { 'key': 'rank', 'strKey': 'WEB_CUSTOMIZING_DOWNLOAD_RANKING', 'icon': '/Areas/Customizing/common/img/icn_rank.png', 'url': '/Customizing/Rank' },
    { 'key': 'authorRank', 'strKey': 'WEB_CUSTOMIZING_AUTHORRANK', 'icon': '/Areas/Customizing/common/img/icn_authorRank.png', 'url': '/Customizing/Rank/authorRank' }
];

var _category = ['WEB_CUSTOMIZING_TEXT_THE_WHOLE', 'WEB_CUSTOMIZING_SEARCH_PRETTY_CHIC', 'WEB_CUSTOMIZING_SEARCH_INDIVIDUALITY', 'WEB_CUSTOMIZING_SEARCH_ENTERTAINER_CELEBRITY', 'WEB_CUSTOMIZING_SEARCH_UGLY', 'WEB_CUSTOMIZING_SEARCH_ETC'];
var _date = ['WEB_CUSTOMIZING_SEARCH_LATELY_DAY_1', 'WEB_CUSTOMIZING_SEARCH_LATELY_DAY_7', 'WEB_CUSTOMIZING_SEARCH_LATELY_DAY_30', 'WEB_CUSTOMIZING_SEARCH_CUMULATIVE_DOWNLOAD'];

function initPage() {
    // 포커스
    $(document).on('focus', 'input, textarea', function () {
        engine.call("ToClient_SetInputMode", true);
    });

    // 포커스
    $(document).on('blur', 'input, textarea', function () {
        engine.call("ToClient_SetInputMode", false);
    });

    //customizing.className = _classNames[customizing.classType];

    // 전체 쿠키 삭제 후 기본값 쿠키저장
    $.each(document.cookie.split(';'), function (row, data) {
        var day = new Date();
        day.setDate(day.getDate() - 1);
        //console.log(data.split('=')[0]);
        document.cookie = data.split('=')[0] + '=; path=/; expires=' + day.toGMTString() + ';';
    })
    common.setCookie('myfoler',1);
    common.setCookie('gallery', 1);
}

// 탭 초기화 및 바인딩
function initTab() {
    $.each(tabList, function (row, data) {
        $('.tabList').append('<li class="tab_' + data.key + '" data-url="' + data.url + '" data-tooltip="' + data.strKey + '" data-tooltip-position="bottom"><div><img src="' + data.icon + '"><span data-str="' + data.strKey + '"></span></div>');
    });

    $('.tabList > li:first-child').addClass('active');
}

// 탭 이동
$(document).on('click', '.tabList li', function (e) {
    var $this = $(this);
    var $writeForm = $('#writeForm').length;

    var url = $this.attr('data-url');
    var param = [];

    if ($writeForm > 0) {
        common.messageBox('WEB_CUSTOMIZING_CONFIRM_CHANGE_CONTENT', function (rs) {
            if (rs) {
                $('li.active').removeClass('active');
                $this.addClass('active');
                common.pageMove(url, param);
            } else {
                return false;
            }

        });
    } else {
        $('li.active').removeClass('active');
        $this.addClass('active');
        common.pageMove(url, param);
    }

    // 검색조건 초기화
    //_searchConditions['gallery'].text = '';
    //_searchConditions.gallery.sort = '1';
});

// 커스터마이징 등록페이지 이동
$(document).on('click', '.btn_write', function () {
    var $writeForm = $('#writeForm').length;
    
    if ($writeForm > 0) {
        common.messageBox('WEB_CUSTOMIZING_CONFIRM_CHANGE_CONTENT', function (rs) {
            if (rs) {
                common.pageMove('/Customizing/MyFolder/CustomizingGalleryWrite/');
            } else {
                
            }
        });
    } else {
        common.pageMove('/Customizing/MyFolder/CustomizingGalleryWrite/');
    }
    
});
