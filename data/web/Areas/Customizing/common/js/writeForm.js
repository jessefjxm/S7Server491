// 현제페이지 초기화
function initPage(data) {
    common.loading(true);
    // 카테고리 바인딩 
    $.each(_category, function (key, value) {
        if (key == 0)
            return;

        $('.search.category .radioArea').append(
            '<div>' +
                '<input type="radio" id="category_' + key + '" class="radio" name="customizingCategoryCode" value="' + key + '" /><label for="category_' + key + '"></label>' +
                '<span data-str="' + value + '"> </span>' +
            '</div>');
    });

    // 등록인 경우
    if (common.isEmpty(data) || Object.keys(data).length < 1) {
        $('#writeForm #classType').val(customizing.classType);
        $('#writeForm .classImage img').attr('src', '/Areas/Customizing/common/img/class_' + customizing.classType + '_active.png');
        $('#writeForm .classImage img').attr('data-tooltip', _classNames[customizing.classType]);
        $('#userNickName').text(customizing.userNickname);
    }
        // 수정인 경우
    else {
        $('#writeForm #classType').val(data.C_classType);
        $('#writeForm .classImage img').attr('src', '/Areas/Customizing/common/img/class_' + data.C_classType + '_active.png');
        $('#writeForm .classImage img').attr('data-tooltip', _classNames[data.C_classType]);
        $('#writeForm #customizingNo').val(data.C_customizingNo);

        common.setToggleSwitch($('.switch'), data.C_isMine == 'N' ? true : false);
        $('#_customizingTitle').val(data.C_customizingTitle);
        $('#userNickName').text(data.C_userNickName);
        $('#category_' + data.C_customizingCategoryCode).click();

        $('#writeForm .imageBox .top a').remove();

        $('#writeForm .imageBox:nth-child(1) .bottom').addClass('hide');
        $('#writeForm .imageBox:nth-child(2) .bottom').addClass('hide');

        if (!common.isEmpty(data.C_customizingImage1)) {
            $('#writeForm .imageBox:nth-child(1) .top').removeClass('hide');
            $('#_customizingImage1').attr('src', data.C_customizingImage1);
            //$('#_customizingImage1').css('background', 'url(' + data.C_customizingImage1 + ') 50% 50% / cover');
        } else {
        }
        if (!common.isEmpty(data.C_customizingImage2)) {
            $('#writeForm .imageBox:nth-child(2) .top').removeClass('hide');
            $('#_customizingImage2').attr('src', data.C_customizingImage2);
            //$('#_customizingImage2').css('background', 'url(' + data.C_customizingImage2 + ') 50% 50% / cover');
        } else {
        }

        $('#_customizingDescription').val(common.isEmpty(data.C_customizingDescription) ? '' : data.C_customizingDescription);

    }
    common.loading(false);
}

// 커스터마이징 저장
function saveCustomizing() {
    if (common.isEmpty($('#writeForm #author').val()))
        return false;
    else if ($('#writeForm #author').val() == '-')
        $('#writeForm #author').val('');


    var customizingNo = $('#customizingNo').val();
    var frm = document.getElementById('writeFrm');
    frm.method = 'POST';
    frm.enctype = 'multipart/form-data';


    // 저장
    if (common.isEmpty(customizingNo)) {
        var url = '/Customizing/MyFolder/SetCustomizingGalleryWrite/';
    }
        // 수정
    else {
        var url = '/Customizing/MyFolder/SetCustomizingGalleryUpdate/';
    }

    $('[name="userNickname"]').val($('#userNickName').text());
    $('[name="isMine"]').val($($(".galleryOnOff")[0]).getChecked() ? 'N' : 'Y');
    $('[name="characterName"]').val(customizing.characterName);
    $('[name="characterNo"]').val(customizing.characterNo);

    var data = new FormData(frm);

    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        async: false,
        cache: false,
        contentType: false,
        processData: false,

        beforeSend: function (xhr, settings) {
        },
        complete: function (xhr, status) {
        },
        success: function (result, status, xhr) {
            if (result.resultCode == '0' || result.resultCode == 0) {
                // 등록
                if (common.isEmpty($('#customizingNo').val())) {
                    var param = [];
                    $('li.active').removeClass('active');
                    $('.tabList li.tab_myfolder').addClass('active');
                    common.pageMove('/Customizing/MyFolder', param);
                }
                    // 수정
                else {
                    var param = ['customizingNo=' + $('#customizingNo').val()];
                    common.pageMove('/Customizing/Detail', param);
                }
            }
            else {
                pearl.common.error(result, function () { });
            }
        },
        error: function (xhr, status, error) {
            pearl.common.error(xhr.responseText);
            console.log('status : ' + status + ' / error : ' + error);
        }
    });
}

// 커스터마이징 스크린샷 삭제
$('#writeForm').on('click', '.btnDelete', function (e) {
    var $parent = $(this).parent();
    var $img = $(this).find('img');
    common.messageBox('WEB_CUSTOMIZING_CONFIRM_DELETE', function (rs) {
        if (rs) {
            $parent.addClass('hide');
            $img.attr('src', '');
            if ($parent.parent().hasClass('image1')) {
                $('#customizingImage1').val('');
            } else {
                $('#customizingImage2').val('');
            }
        }
    })
});

// 커스터마이징 저장버튼
$('#writeForm').on('click', '.btnSave', function (e) {

    // image 하나 이상 필수
    // cm file 필수
    // validate 체크
    function isValidate() {
        // 제목 필수
        if (common.isEmpty($('#_customizingTitle').val())) {
            common.messageBox('WEB_CUSTOMIZING_TEXT_REQUIRED_TITLE');
            $('#writeForm #customizingFile').val('');
            return false;
        }

        // 스크린샷 1개 이상 필수
        if (common.isEmpty($('#customizingImage1').val()) && common.isEmpty($('#customizingImage2').val())) {
            // 등록일때만 체크
            if (common.isEmpty($('#customizingNo').val())) {
                common.messageBox('WEB_CUSTOMIZING_TEXT_REQUIRED_SCREENSHOT');
                $('#writeForm #customizingFile').val('');
                return false;
            } else {

            }
        }

        // 제목, 내용 글자수 제한
        if ($('#_customizingTitle').val().length > 50) {
            common.messageBox('WEB_CUSTOMIZING_TEXT_LIMIT');
            $('#writeForm #customizingFile').val('');
            return false;
        } else if ($('#_customizingDescription').val().length > 4000) {
            common.messageBox('WEB_CUSTOMIZING_TEXT_LIMIT');
            $('#writeForm #customizingFile').val('');
            return false;
        }

        // 카테고리 선택 필수
        if ($('[name="customizingCategoryCode"]:checked').length <= 0) {
            common.messageBox('WEB_CUSTOMIZING_TEXT_REQUIRED_CATEGORY');
            $('#writeForm #customizingFile').val('');
            return false;
        }

        return true;
    }
    
    if (!isValidate())
        return false;
    common.messageBox('WEB_CUSTOMIZING_CONFIRM_SAVE', function (rs) {
        if (rs) {
            common.messageBox('WEB_CUSTOMIZING_CONFIRM_GALLERY_SAVE', function (is) {
                common.setToggleSwitch($('#writeForm .switch'), is);
                $('#writeForm #customizingFile').click();
            });
        } else {
            $('#writeForm #customizingFile').val('');
        }
    });

});

// 커마 파일이 들어오면 히든필드에 저장
$('#writeForm #customizingFile').on('change', function (e) {
    if (common.isEmpty($(this).val())) {
    } else {
        engine.call('ToClient_GetFilteredText', 'customizingTitle', common.isEmpty($('#writeForm').find('#_customizingTitle').val()) ? '' : $('#writeForm').find('#_customizingTitle').val());
        engine.call('ToClient_GetFilteredText', 'customizingContent', common.isEmpty($('#writeForm').find('#_customizingDescription').val()) ? ' ' : $('#writeForm').find('#_customizingDescription').val());
    }
});

// 제작자이름이 들어오면 저장
$('#writeForm #author').on('change', function (e) {
    saveCustomizing();
});

// 커스터마이징 취소버튼
$('#writeForm').on('click', '.btnCancel', function (e) {
    common.messageBox('WEB_CUSTOMIZING_CONFIRM_CHANGE_CONTENT', function (rs) {
        if (rs) {
            var customizingNo = $('#customizingNo').val();
            var param = [];
            if ($.isNumeric(customizingNo)) {
                param = ['customizingNo=' + customizingNo];
                common.pageMove('/Customizing/Detail', param);
            } else {
                $('li.active').removeClass('active');
                $('.tabList li.tab_myfolder').addClass('active');
                common.pageMove('/Customizing/MyFolder', param);
            }
            //var param = [];
            //$('li.active').removeClass('active');
            //$('.tabList li.tab_myfolder').addClass('active');
            //common.pageMove('/Customizing/MyFolder', param);
        }
    })
});

// 커스터마이징 다시찍기버튼
$('#writeForm').on('click', '.btnReScreenshot', function (e) {
    var $parent = $(this).parent();
    var $img = $parent.find('img');

    common.messageBox('WEB_CUSTOMIZING_CONFIRM_CUSTOMIZING_CHANGE_AGAIN_SCREENSHOT', function (rs) {
        if (rs) {
            $parent.addClass('hide');
            $img.attr('src', '');
            if ($parent.parent().hasClass('image1')) {
                $('#customizingImage1').val('');
                $('#customizingImage1').click();
            } else {
                $('#customizingImage2').val('');
                $('#customizingImage2').click();
            }

        }
    })
});

// 스크린샷1 사진찍기버튼
$('#writeForm .image1 .btnScreenshot').on('click', function (e) {
    $('#customizingImage1').click();
    document.body.focus();
});

// 스크린샷2 사진찍기버튼
$('#writeForm .image2 .btnScreenshot').on('click', function (e) {
    $('#customizingImage2').click();
    document.body.focus();
});

// 스크린샷 사진 찍은 후 이미지 바인딩
$('#writeForm #customizingImage1').on('change', function (e) {
    var reader = new FileReader();

    $('#writeForm .imageArea .image1 .top').removeClass('hide');

    reader.onload = function (e) {
        $('#writeForm .imageArea .image1 .top #_customizingImage1').attr('src', e.target.result)
    }
    reader.readAsDataURL(document.getElementById('customizingImage1').files[0]);
});

// 스크린샷 사진 찍은 후 이미지 바인딩
$('#writeForm #customizingImage2').on('change', function (e) {
    var reader = new FileReader();

    $('#writeForm .imageArea .image2 .top').removeClass('hide');

    reader.onload = function (e) {
        $('#writeForm .imageArea .image2 .top #_customizingImage2').attr('src', e.target.result)
    }
    reader.readAsDataURL(document.getElementById('customizingImage2').files[0]);
});

// 텍스트 최대 크기 이상 입력시 경고창
$('#writeForm').on('keyup', 'textarea, input[type="text"]', function (e) {
    var maxLength = parseInt($(this).attr('maxlength'));
    if (e.keyCode == 13)
        return false;

    if ($(this).val().length > maxLength) {
        e.preventDefault();
        common.messageBox('WEB_CUSTOMIZING_TEXT_LIMIT');
        return false;
    }
});

// 갤러리 ON/OFF 시 메세지박스
$('#writeForm input:checkbox').on('change', function (e) {
    //$('#myfolder').before('<div id="checkLoadingBox"></div>');
    //var $this = $(this);
    //setTimeout(function (e) {
    //    if ($this.is(':checked'))
    //        common.messageBox('WEB_CUSTOMIZING_USERGALLERY_SIGNUP');
    //    else
    //        common.messageBox('WEB_CUSTOMIZING_USERGALLERY_RELESAE');
    //    $('#checkLoadingBox').remove();
    //},500);
});