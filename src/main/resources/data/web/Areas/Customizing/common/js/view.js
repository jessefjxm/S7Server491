function initPage(data) {
    common.loading(true);
    $('#view .classImage img').attr('src', '/Areas/Customizing/common/img/class_' + data.C_classType + '_active.png');
    $('#view .classImage img').attr('data-tooltip', _classNames[data.C_classType]);
    $('#view .classImage').attr('attr-key', data.C_classType);

    $('#view .titleArea .name').append(data.C_customizingTitle);

    var img = '';
    //data['C_guildno'] = '100000000000000446';

    if (!common.isEmpty(data.C_guildno) && data.C_guildno > 0)
        img = '<img class="guildmark" src="coui://guildmark/' + data['C_guildno'] + '.png" style="width:12px;" data-tooltip="' + data.C_guildName + '"/>';

    $('#view .titleArea .userNickname').append(img + data.C_userNickname + (!common.isEmpty(data.C_characterName) ? ' (' + data.C_characterName + ')' : ''));
    $('#view .titleArea .date').text(common.dateFormat(data.C_regdate));
    $('#view .titleArea .view > span').text(data.C_viewCount);
    $('#view .titleArea .comment > span').text(data.C_commentCount);

    if (customizing.userNo == data.C_userNo) {
        $('#view #searchBox .gallery').removeClass('opacity');
        common.setToggleSwitch($('#view .switchArea .switch'), data.C_isMine == 'N' ? true : false);

        // 갤러리 ON/OFF (change이벤트를 전역으로 빼면 로드시에도 호출버려서 최초 바인딩 후 이벤트 걸었음)
        $('#view #searchBox .gallery input:checkbox').on('change', function (e) {
            changGalleryOnOff($(this));
        });

        // 수정, 삭제버튼
        $('.btnModify').removeClass('hide');
        $('.btnDelete').removeClass('hide');
    } else {
        $('#view #searchBox .gallery').addClass('opacity');

        // 수정, 삭제버튼
        $('.btnModify').addClass('hide');
        $('.btnDelete').addClass('hide');
    }
    $('#view .switchArea .code > .customizingNo').text(data.C_customizingNo);

    //$('#view .scroll .imageArea .imageBox #_customizingImage1').attr('src', data.C_customizingImage1);
    //$('#view .scroll .imageArea .imageBox #_customizingImage2').attr('src', data.C_customizingImage2);

    $('#view .scroll .imageArea .imageBox #_customizingImage1').css('background', 'url(' + data.C_customizingImage1 + ')').css('background-size', 'cover').css('background-position', 'center');
    $('#view .scroll .imageArea .imageBox #_customizingImage2').css('background', 'url(' + data.C_customizingImage2 + ')').css('background-size', 'cover').css('background-position', 'center');

    $('#view .scroll .btnArea .count').text(data.C_downloadCount);
    $('#view .scroll .btnArea .author').append(common.isEmpty(data.C_author) ? '<span class="authorUserNickname"> - </span>' : '<a href="#" class="authorUserNickname">' + data.C_author + '</a>');
    $('#view .scroll .descriptionArea > span').append(common.isEmpty(data.C_customizingDescription) ? '' : data.C_customizingDescription.replace(/\n/gi, '<br />'));
    $('#view .scroll .btnArea.like_facebook .like > i').text(data.C_LikeCount);

    $('#customizingNo').val(data.C_customizingNo);
    $('#userNickname').val(customizing.userNickname);
    $('#classType').val(customizing.classType);
    $('#characterName').val(customizing.characterName);
    $('#characterNo').val(customizing.characterNo);

    $('.cmApply').attr('href', data.C_customizingFile);

    $('#isWriter').val(customizing.userNo == data.C_userNo)
    getComment(data.C_customizingNo);

    //GM일 경우만 글 감추기 버튼 보이기
    if (customizing.isGm == true || customizing.isGm == 'true' || customizing.isGm == 'True') {

    } else {
        $('#view .btnHide').addClass('hide');
    }
}

// 코멘트 가져오기
function getComment(customizingNo) {
    var url = '/Customizing/Detail/GetCustomizingGalleryComment/';
    var data = {'customizingNo': customizingNo, 'page': parseInt($('#page').val())};

    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        async: false,

        beforeSend: function (xhr, settings) {
        },
        complete: function (xhr, status) {
            common.loading(false);
        },
        success: function (result, status, xhr) {
            pearl.common.error(result, function () {
                if (result.comment.length > 0)
                    bindCommet(result.comment);
                else
                    bindCommet();

                $('#page').val(result.page);
                $('#totalPage').val(result.totalPage);
            });
        },
        error: function (xhr, status, error) {
            pearl.common.error(xhr.responseText);
            console.log('status : ' + status + ' / error : ' + error);
        }
    });

    paging.init({
        'id': 'paging', 'length': $('#length').val(), 'maxLength': $('#totalPage').val(), 'current': $('#page').val(),
        'onPageChange': function (page) {
            $('#page').val(page);
            getComment($('#customizingNo').val());
            console.log(page);
            common.setCookie('view', page);
        }
    });

}

// 코멘트 바인딩
function bindCommet(commentData) {
    console.log(commentData);

    $('#view .scroll .commentArea .table').empty();

    var format =
        '<div class="row{{C_isBlind}}" attr-comment-no="{{C_customizingCommentNo}}">' +
        '<div class="th">{{C_userNickname}}</div>' +
        '<div class="td comment" data-blind="' + common.getStringTable('WEB_CUSTOMIZING_TEXT_BLIND_COMMENT') + '">' +
        '<span class="content">{{C_customizingComment}}</span>' +
        '<span class="date">{{C_CommentRegdate}}</span>' +
        '{{C_userNo}}' +
        '</div>' +
        '<div class="td">' +
        '<a class="btnReport{{C_commentReportCount}}" data-tooltip="WEB_CUSTOMIZING_REPORT"><span data-str="WEB_CUSTOMIZING_REPORT"> </span></a>' +
        '</div>' +
        '</div>';

    $('#view .scroll .commentArea .table .row:not(.row:last-child)').remove();

    var writeFormat =
        '<div class="row">' +
        '<div class="th">{{C_userNickname}}</div>' +
        '<div class="td comment">' +
        '<textarea class="scroll" data-str-placeholder="WEB_CUSTOMIZING_TEXT_PLASE_INPUT_TEXT" maxlength="500"></textarea>' +
        '</div>' +
        '<div class="td">' +
        '<a class="btn save" data-color="3d382d" data-str="WEB_CUSTOMIZING_COMMENT_SAVE"> </a>' +
        '</div>' +
        '</div>';


    // GM이거나 작성자인 경우 텍스트 변경
    var isWriter = $('#isWriter').val();
    if (customizing.isGm == true || customizing.isGm == 'true' || customizing.isGm == 'True' || isWriter == true || isWriter == 'true')
        format = format.replace(/WEB_CUSTOMIZING_REPORT/gi, 'WEB_CUSTOMIZING_HIDE');

    // 코멘트 입력창 바인딩
    writeFormat = writeFormat.replace('{{C_userNickname}}', customizing.userNickname + (!common.isEmpty(customizing.characterName) ? ' (' + customizing.characterName + ')' : ''));
    $('#view .scroll .commentArea .table').append(writeFormat);

    // 코멘트 데이터 바인딩
    var str = '';
    $.each(commentData, function (row, data) {
        if (common.isEmpty(data))
            return;

        var formatData = format;

        $.each(data, function (key, value) {
            if (key == 'C_userNo') {
                if (parseInt(customizing.userNo) == value) {
                    formatData = formatData.replace('{{' + key + '}}', '<a class="modify" data-tooltip="WEB_CUSTOMIZING_MODIFY"></a><a class="remove" data-tooltip="WEB_CUSTOMIZING_DELETE"></a>');
                } else
                    formatData = formatData.replace('{{' + key + '}}', '');
            } else if (key == 'C_CommentRegdate') {
                var reg = new Date(common.dateTimeFormat(value));
                var today = new Date();

                if (reg.getDate() == today.getDate()) {
                    var all = today - reg;
                    var hour = parseInt(all / 1000 / 60 / 60);
                    var min = parseInt((all / 1000 / 60) - (hour * 60));
                    var sec = parseInt((all / 1000) - (hour * 60 * 60) - (min * 60));

                    var str = '';
                    if (hour > 0)
                        str = hour + _stringTable.WEB_CUSTOMIZING_HOUR + ' ';
                    if (min > 0)
                        str += min + _stringTable.WEB_CUSTOMIZING_MINUTE + ' ';
                    else {
                        if (hour <= 0)
                            str += _stringTable.WEB_CUSTOMIZING_JUST_NOW + ' ';
                    }

                    str += _stringTable.WEB_CUSTOMIZING_BEFORE;

                    //(hour > 0 ? hour + _stringTable.WEB_CUSTOMIZING_HOUR + ' ' : '') + (min > 0 ? _stringTable.WEB_CUSTOMIZING_MINUTE : _stringTable.WEB_CUSTOMIZING_JUST_NOW) + ' ' + _stringTable.WEB_CUSTOMIZING_BEFORE


                    formatData = formatData.replace('{{' + key + '}}', str);

                } else
                    formatData = formatData.replace('{{' + key + '}}', common.dateTimeFormat(value));
            } else if (key == 'C_userNickname')
                formatData = formatData.replace('{{' + key + '}}', value + (!common.isEmpty(data.C_characterName) ? ' (' + data.C_characterName + ')' : ''));
            else if (key == 'C_customizingComment') {
                formatData = formatData.replace('{{' + key + '}}', value.replace(/\n/gi, '<br />'));
                //formatData = formatData.replace('{{' + key + '}}', common.decodeHtml(value));

            }
            else if (key == 'C_isBlind') {
                formatData = formatData.replace(/{{C_isBlind}}/gi, data['C_isBlind'] == 'Y' ? ' blind' : '');
            }
            else if (key == 'C_commentReportCount') {
                // 만약 내가 GM이나 작성자이고 신고버튼 비활성화에 블라인드가 안되어 있다면 신고버튼 활성화
                var blind = '';
                if (data['C_isBlind'] == 'Y')
                    blind = ' blind';
                else {
                    if (customizing.isGm == true || customizing.isGm == 'true' || customizing.isGm == 'True' || isWriter == true || isWriter == 'true')
                        blind = '';
                    else {
                        blind = parseInt(data['C_commentReportCount']) > 0 || parseInt(data['C_isBlind']) > 'Y' ? ' blind' : '';
                    }
                }
                formatData = formatData.replace('{{' + key + '}}', blind);
                //formatData = formatData.replace('{{' + key + '}}', parseInt(data['C_commentReportCount']) > 0 || parseInt(data['C_isBlind']) > 0 ? ' blind' : '');
            }
            else
                formatData = formatData.replace('{{' + key + '}}', value);
        });
        str += formatData;
    });

    $('#view .scroll .commentArea .table').prepend(str);

    // 내가 등록한 코멘트인 경우 신고버튼 없앰
    $.each(commentData, function (row, data) {
        if (data.C_userNo == customizing.userNo)
            $('[attr-comment-no="' + data.C_customizingCommentNo + '"] .btnReport').css('display', 'none');
    });

    var $str = $('[data-str], [data-str-placeholder]');
    $.each($str, function (key, value) {
        common.stringConvert(value);
    });

}

// 갤러리 ON/OFF
function changGalleryOnOff($this) {
    $('#view').before('<div id="checkLoadingBox"></div>');
    setTimeout(function () {
        var url = '/Customizing/Frame/SetCustomizingGalleryType';
        var data = {'customizingNo': $('#customizingNo').val(), 'isMine': $($this).is(':checked') ? 'N' : 'Y'};
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
}

// 코멘트 저장
function saveComment(str) {
    var $parent = $('#write').parents('.row');

    // 수정
    if ($parent.hasClass('modify')) {

        $parent.removeClass('modify');

        var url = '/Customizing/Detail/SetCustomizingGalleryCommentUpdate/';
        $('#customizingComment').val(str);
        $('#customizingCommentNo').val($parent.attr('attr-comment-no'));

        var frm = $('#viewForm');
        var data = frm.serializeArray();

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
                    getComment($('#customizingNo').val());
                });
            },
            error: function (xhr, status, error) {
                pearl.common.error(xhr.responseText);
                console.log('status : ' + status + ' / error : ' + error);
            }
        });

    }
    // 저장
    else {
        var url = '/Customizing/Detail/SetCustomizingGalleryCommentWrite/';
        $('#customizingComment').val(str);

        var frm = $('#viewForm');
        var data = frm.serializeArray();

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
                    getComment($('#customizingNo').val());
                });
            },
            error: function (xhr, status, error) {
                pearl.common.error(xhr.responseText);
                console.log('status : ' + status + ' / error : ' + error);
            }
        });
    }
}

// 코멘트 수정버튼
$('#view .scroll .commentArea').on('click', 'a.modify', function (e) {
    var $parent = $(this).parents('.row');

    // 코멘트 취소
    var $other = $('#view .scroll .commentArea').find('.row.modify').not($parent);
    $other.removeClass('modify');
    $other.find('textarea, a.btn.save').remove();

    // 코멘트 수정 textarea show
    $parent.addClass('modify');

    $parent.children('.td.comment').append('<textarea class="scroll" placeholder="' + common.getStringTable('WEB_CUSTOMIZING_TEXT_PLASE_INPUT_TEXT') + '">' + $parent.children().children('.content').html().replace(/<br>/gi, '\n') + '</textarea>');
    $parent.children('.td:not(.comment)').append('<a class="btn save" data-color="3d382d" style="background-color: rgb(61, 56, 45);">' + common.getStringTable('WEB_CUSTOMIZING_COMMENT_SAVE') + '</a>');
});

// 코멘트 저장
$('#view .scroll .commentArea').on('click', 'a.save', function (e) {
    var $parent = $(this).parents('.row');
    common.messageBox('WEB_CUSTOMIZING_CONFIRM_SAVE', function (rs) {

        if (rs) {
            if (common.isEmpty($parent.children('.td.comment').children('textarea').val())) {
                common.messageBox('WEB_CUSTOMIZING_TEXT_PLASE_INPUT_TEXT');
                return false;
            }

            $parent.children('.td.comment').attr('id', 'write');
            engine.call('ToClient_GetFilteredText', 'comment', $parent.children('.td.comment').children('textarea').val());

        }
    });

});

// 코멘트 삭제
$('#view .scroll .commentArea').on('click', 'a.remove', function (e) {
    var $parent = $(this).parents('.row');

    common.messageBox('WEB_CUSTOMIZING_CONFIRM_DELETE', function (rs) {
        if (rs) {
            $('#customizingCommentNo').val($parent.attr('attr-comment-no'));

            var frm = $('#viewForm');
            var url = '/Customizing/Detail/SetCustomizingGalleryCommentDelete/';
            var data = frm.serializeArray();

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
                        getComment($('#customizingNo').val());
                    });
                },
                error: function (xhr, status, error) {
                    pearl.common.error(xhr.responseText);
                    console.log('status : ' + status + ' / error : ' + error);
                }
            });
        }
    });
});

// 이전페이지로 돌아가기
$('#view').on('click', '.btnBack', function (e) {
    if ($('.tabList li.active').length)
        $('.tabList li.active').click();
});

// 적용버튼
$('#view').on('click', '.apply', function (e) {
    if (customizing.isCustomizationMode == 'false' || !customizing.isCustomizationMode) {
        common.messageBox('WEB_CUSTOMIZING_SIGNUP_FAIL');
        return false;
    } else {
        var url = '/Customizing/Frame/SetCustomizingGalleryDownload/';
        var data = {'customizingNo': $('#customizingNo').val(), 'classType': parseInt(customizing.classType)};

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
                            $('#view .scroll .btnArea .count').text(result.totalCount);
                            location.href = $('#view .cmApply').attr('href');
                        }
                        else if (result.resultCode == -20003)
                            common.messageBox('WEB_CUSTOMIZING_NOT_SAME_CLASS');
                        else if (result.resultCode == -20000)
                            location.href = $('#view .cmApply').attr('href');
                        else
                            pearl.common.error(result, function () {
                            });
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

// 신고버튼
$('#view').on('click', '.btnReport', function (e) {
    var $this = $(this);

    if ($this.hasClass('blind')) {
        // 블라인드가 이미 처리되어있음
        return false;
    }

    var str = 'WEB_CUSTOMIZING_CONFIRM_REPORT';

    // GM이거나 작성자인 경우 텍스트 변경
    var isWriter = $('#isWriter').val();
    if (customizing.isGm == true || customizing.isGm == 'true' || customizing.isGm == 'True' || isWriter == true || isWriter == 'true')
        str = 'WEB_CUSTOMIZING_CONFIRM_HIDE';

    common.messageBox(str, function (rs) {
        if (rs) {
            var url = '/Customizing/Detail/SetCustomizingGalleryCommentReport/';
            var data = {
                'customizingNo': parseInt($('#customizingNo').val()),
                'customizingCommentNo': parseInt($this.parents('.row').attr('attr-comment-no')),
                'isGm': customizing.isGm
            };

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
                        getComment($('#customizingNo').val());
                    });
                },
                error: function (xhr, status, error) {
                    pearl.common.error(xhr.responseText);
                    console.log('status : ' + status + ' / error : ' + error);
                }
            });
        }
    });
});

// 좋아요버튼
$('#view').on('click', '.like', function (e) {
    var url = '/Customizing/Detail/iLike/';
    var data = {'customizingNo': $('#customizingNo').val()};
    var $this = $(this);

    common.messageBox('WEB_CUSTOMIZING_CONFIRM_LIKE', function (rs) {
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
                    if (result.resultCode == 0 || parseInt(result.resultCode) == '0')
                        $this.find('i').text(result.totalCount);
                    else if (result.resultCode == -20001)
                        common.messageBox('WEB_CUSTOMIZING_ALERT_ALREADY_SIGNALED_PRESS');
                    else
                        pearl.common.error(result, function () {
                        });
                },
                error: function (xhr, status, error) {
                    pearl.common.error(xhr.responseText);
                    console.log('status : ' + status + ' / error : ' + error);
                }
            });
        }
        else
            return false;
    });

});

// 페이스북버튼
$('#view').on('click', '.facebook', function (e) {
    // TODO: 테스트
    ToClient_NewOuterWindow('http://www.naver.com', false);
    //ToClient_NewOuterWindow('http://www.naver.com', false);
});

// gm 블라인드 버튼
$('#view .scroll .modify_delete').on('click', '.btnHide', function (e) {
    common.messageBox('WEB_CUSTOMIZING_CONFIRM_BLIND_NOT_CANCEL', function (rs) {
        if (rs) {
            var url = '/Customizing/Detail/SetGalleryBlind/';
            var data = {'customizingNo': parseInt($('#customizingNo').val()), 'isGm': customizing.isGm};

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
                        $('#view .btnBack').click();
                    });
                },
                error: function (xhr, status, error) {
                    pearl.common.error(xhr.responseText);
                    console.log('status : ' + status + ' / error : ' + error);
                }
            });
        }
    });

});

// 수정버튼
$('#view .scroll .modify_delete').on('click', '.btnModify', function (e) {
    common.messageBox('WEB_CUSTOMIZING_CONFIRM_MODIFY', function (rs) {
        if (rs) {
            var code = parseInt($('#customizingNo').val());
            var param = ['customizingNo=' + code];
            common.pageMove('/Customizing/MyFolder/CustomizingGalleryWrite/', param);
        }
    });

});

// 삭제버튼
$('#view .scroll .modify_delete').on('click', '.btnDelete', function (e) {
    common.messageBox('WEB_CUSTOMIZING_CONFIRM_DELETE', function (rs) {
        if (rs) {
            //var code = parseInt($('#customizingNo').val());
            var frm = $('#viewForm');
            var url = '/Customizing/MyFolder/SetCustomizingGalleryDelete/';
            var data = frm.serializeArray();

            $.ajax({
                url: url,
                data: data,
                type: 'POST',
                beforeSend: function (xhr, settings) {
                },
                complete: function (xhr, status) {
                },
                success: function (result, status, xhr) {
                    pearl.common.error(result, function () {
                        if ($('.tabList li.active').length)
                            $('.tabList li.active').click();
                    });
                },
                error: function (xhr, status, error) {
                    pearl.common.error(xhr.responseText);
                    console.log('status : ' + status + ' / error : ' + error);
                }
            });
        }
    });

});

// 텍스트 최대 크기 이상 입력시 경고창
$('#view').on('keyup', 'textarea, input[type="text"]', function (e) {
    var maxLength = parseInt($(this).attr('maxlength'));
    if (e.keyCode == 13)
        return false;

    if ($(this).val().length > maxLength) {
        e.preventDefault();
        common.messageBox('WEB_CUSTOMIZING_TEXT_LIMIT');
        return false;
    }
});

// 모험가갤러리에서 제작자 기준 검색
$('#view').on('click', '.authorUserNickname', function (e) {
    var str = 'WEB_CUSTOMIZING_CONFIRM_AUTHOR_GALLERY_SEARCH';
    var $this = $(this);

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