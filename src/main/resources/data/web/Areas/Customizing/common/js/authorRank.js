function initPage() {
    common.loading(true);
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


    if (_searchConditions['authorRank'].date.length > 0)
        $('#authorRank [attr-date-key="' + _searchConditions['authorRank'].date + '"]').prop('checked', true);
    else
        $('#authorRank [name="searchTime"]').first().prop('checked', true);

    //getCustomizingByAuthorRank();
}

// 커스터마이징 가져오기
function getCustomizingByAuthorRank() {
    common.loading(true);
    // TODO: 테스트
    //var searchConditions = _searchConditions.rank;
    //var search = { 'classType': '0,4,8', 'sortType': '3' };

    var search = { 'sortType': _searchConditions['authorRank'].date };
    var custom = common.getCustomizing('/Customizing/Rank/GetCustomizingGalleryAuthorRank', search);
    var customizing_author = {};

    $.each(custom.customizing, function (row, data) {
        if (customizing_author[data.C_ranking] == undefined)
            customizing_author[data.C_ranking] = [data];
        else
            customizing_author[data.C_ranking].push(data);
    });

    bindCMBox(customizing_author);
}

// 커스터마이징 박스 바인딩
function bindCMBox(bindData) {

    $('.cmArea .CMBox').empty();
    $('.cmArea .nobox').remove();
    var str = '';
    
    if (Object.keys(bindData).length <= 0) {
        $('.cmArea').append('<div class="nobox"><span data-str="WEB_CUSTOMIZING_NOT_SAVED_CUSTOMIZING"></span></div>');
    } else {

        $.each(bindData, function (row, data) {
            if (common.isEmpty(data))
                return;

            var formatData = boxFormat[$('.cmArea').attr('data-formatType')];

            if (data.length < 2)
                formatData = formatData.replace(/{{customizingHide}}/gi, ' visibility');
            else
                formatData = formatData.replace(/{{customizingHide}}/gi, '');

            $.each(data, function (key, value) {
                // 가문정보 바인딩
                formatData = formatData.replace(/{{rownum}}/gi, value['C_ranking']);
                formatData = formatData.replace(/{{C_userNickname}}/gi, value['C_author']);
                formatData = formatData.replace(/{{C_downloadCount}}/gi, value['downTotal']);


                // cmbox 바인딩
                if (value['Tnum'] == 1 || value['Tnum'] == '1') {
                    if (!common.isEmpty(value['C_customizingImage1']))
                        formatData = formatData.replace(/{{C_customizingImage1}}/gi, value['C_customizingImage1']);
                    else 
                        formatData = formatData.replace(/{{C_customizingImage1}}/gi, value['C_customizingImage2']);

                    $.each(value, function (idx, val) {
                        var re = new RegExp('{{' + idx + '1}}', 'g');

                        if (idx == 'C_classType')
                            formatData = formatData.replace(/{{C_className1}}/gi, _classNames[val]);
                        formatData = formatData.replace(re, val);
                    });
                } else if (value['Tnum'] == 2 || value['Tnum'] == '2') {
                    if (!common.isEmpty(value['C_customizingImage1']))
                        formatData = formatData.replace(/{{C_customizingImage2}}/gi, value['C_customizingImage1']);
                    else
                        formatData = formatData.replace(/{{C_customizingImage2}}/gi, value['C_customizingImage2']);

                    $.each(value, function (idx, val) {
                        var re = new RegExp('{{' + idx + '2}}', 'g');

                        if (idx == 'C_classType')
                            formatData = formatData.replace(/{{C_className2}}/gi, _classNames[val]);
                        formatData = formatData.replace(re, val);
                    });
                }
            });

            str += formatData;

        });
        $('.cmArea .CMBox').append(str);

        common.initButton();

        // 뷰페이지 이동
        $('.CMBox .detail, .CMBox .img').on('click', function () {
            var $ele = '';
            if ($(this).hasClass('detail'))
                $ele = $(this).parents('.img');
            else
                $ele = $(this);

            var customizingNo = $ele.attr('data-no');

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
                var data = { 'customizingNo': $(this).parent('.img').attr('data-no'), 'classType': customizing.classType };

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
                                else 
                                    pearl.common.error(result, function () { });
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
        $(document).on('mouseover', '.img', function (e) {
            $(this).find('.btn').removeClass('opacity');
        });

        // 커스터마이징박스 마우스아웃
        $(document).on('mouseout', '.img', function (e) {
            $('.img .btn').addClass('opacity');
        });

    }
    var $str = $('[data-str], [data-str-placeholder]');
    $.each($str, function (key, value) {
        common.stringConvert(value);
        if (key + 1 == $str.length)
            common.loading(false);
    });
}

// 모험가갤러리에서 제작자 기준 검색
$('#authorRank').on('click', '.cmArea .CMBox .box .title a', function (e) {
    var str = '';
    var $this = $(this);

    if ($(this).hasClass('userNickname'))
        str = 'WEB_CUSTOMIZING_CONFIRM_AUTHOR_GALLERY_SEARCH';
    else
        str = 'WEB_CUSTOMIZING_CONFIRM_USER_GALLERY_SEARCH';

    common.messageBox(str, function (rs) {
        if (rs) {
            _searchConditions.gallery.text = '';
            _searchConditions.gallery.class = Object.keys(_classNames);
            _searchConditions.gallery.category = '0';
            _searchConditions.gallery.author = $this.text();

            $('.tab_gallery').click();
        }
    });
});

// 기간 선택
$('#authorRank').on('change', '[name="searchTime"]', function (e) {
    _searchConditions['authorRank'].date = $(this).attr('attr-date-key');
    getCustomizingByAuthorRank();
});