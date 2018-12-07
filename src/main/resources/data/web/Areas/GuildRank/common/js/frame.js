// 0:길드 점수, 1:거점, 2:결전, 3:인원, 4:댓글, 5:길드소개/길드점수
var _items = ['point', 'node', 'duelWar', 'personnel', 'comment', 'guildInfo'];
// 0:채집, 1:낚시, 2:수렵, 3:요리, 4:연금, 5:가공, 6:조련, 7:무역, 8:재배
var _branchs = ['gather', 'fish', 'hunt', 'cook', 'alchemy', 'manufacture', 'mating', 'trade', 'growth'];//, 'wealth', 'combat', 'localWar'
var _territoryName = [];

// 페이지 초기화
function pageInit() {
    $.each(_items, function (key, value) {
        var str = ('WEB_GUILDRANK_' + value + '_caption').toUpperCase();
        $('#top .item .buttonArea').append('<a href="javascript:void(0)" class="btnSearch" attr-key="' + value + '" data-tooltip="' + str + '"><img src="/Areas/GuildRank/common/img/icn_' + value + '.png"/></a>');
    });
    $.each(_branchs, function (key, value) {
        var str = ('WEB_GUILDRANK_' + value + '_caption').toUpperCase();
        $('#top .branch .buttonArea').append('<a href="javascript:void(0)" class="btnSearch" attr-key="' + value + '" data-tooltip="' + str + '"><img src="/Areas/GuildRank/common/img/icn_' + value + '.png"/></a>');
    });


    $('#paging').jPaging({
        'length': 10,
        'maxLength': $('maxLength').val(),
        'current': 1,
        'custom': {'pageFixed': true, 'buttonFixed': true, 'arrowButton': true},
        'onPageChange': function (page) {
            console.log(page);
            $('.scroll').scrollTop(0);

            if ($('#type').val() == 'RANK')
                getGuildRankList($('.item a.active').index(), page);
            else if ($('#type').val() == 'LIFTRANK')
                getGuildLifeRankList($('.branch a.active').index(), page);
            else
                getGuildSearchRankList($('.search.comboBox').attr('data-key'), $('.search.text').val());

        }
    });

    $('.item a')[0].click();

}

// 지역명 저장
function setTerritoryName(key) {
    $.each(key, function (row, val) {
        engine.call('ToClient_GetTerritoryName', parseInt(val));
    });
}

// 지역명 가져오기
function getTerritoryName(key) {
    var str = [];
    $.each(key, function (row, val) {
        str.push(_territoryName[val]);
    });

    if (!pearl.common.isEmpty(str))
        return str.join(',');
    else
        return '';
}

// 항목별 길드순위 리스트
function getGuildRankList(type, page) {
    pearl.common.loading(true);
    $.ajax({
        url: '/GuildRank/Index/GetGuildRankList',
        type: 'POST',
        data: {'rankingType': type, 'page': page, 'pageLength': $('#pageLength').val()},
        async: false,
        beforeSend: function (xhr, settings) {
        },
        complete: function (xhr, status) {
        },
        success: function (result, status, xhr) {
            pearl.common.error(result, function () {
                if (typeof (result) == 'object')
                    rs = result;
                else
                    rs = JSON.parse(result);

                bindList(rs)
            });

        },
        error: function (xhr, status, error) {
            pearl.common.error(xhr.responseText);
            console.log('status : ' + status + ' / error : ' + error);
        }
    });
}

// 분야별 길드순위 리스트
function getGuildLifeRankList(type, page) {
    pearl.common.loading(true);
    $.ajax({
        url: '/GuildRank/Index/GetGuildLifeRankList',
        type: 'POST',
        data: {'rankingType': type, 'page': page, 'pageLength': $('#pageLength').val()},
        async: false,
        beforeSend: function (xhr, settings) {
        },
        complete: function (xhr, status) {
        },
        success: function (result, status, xhr) {
            pearl.common.error(result, function () {
                if (typeof (result) == 'object')
                    rs = result;
                else
                    rs = JSON.parse(result);

                bindList(rs)
            });
        },
        error: function (xhr, status, error) {
            pearl.common.error(xhr.responseText);
            console.log('status : ' + status + ' / error : ' + error);
        }
    });
}

// 검색조건 길드리스트
function getGuildSearchRankList(type, text) {
    pearl.common.loading(true);
    $.ajax({
        url: '/GuildRank/Index/GetGuildSearchRankList',
        type: 'POST',
        data: {'searchType': type, 'searchText': text},
        async: false,
        beforeSend: function (xhr, settings) {
        },
        complete: function (xhr, status) {
        },
        success: function (result, status, xhr) {
            pearl.common.error(result, function () {
                if (typeof (result) == 'object')
                    rs = result;
                else
                    rs = JSON.parse(result);

                bindList(rs)
            });
        },
        error: function (xhr, status, error) {
            pearl.common.error(xhr.responseText);
            console.log('status : ' + status + ' / error : ' + error);
        }
    });
}

// 리스트 바인딩
function bindList(data) {
    //console.log(JSON.stringify(data));
    //console.log(data.length);
    //console.log(data);

    $('#container .area .content .table.guildList .tbody').empty();

    var format =
        '<div class="row" attr-guildno="{{C_guildNo}}" attr-rank="{{C_rank}}" attr-caption="">' +
        '<div class="col" attr-data="{{attr_C_rank}}"><span class="arial">{{C_rank}}</span></div>' +
        '<div class="col" attr-data="{{attr_C_guildName}}"><img src="coui://guildmark/{{C_guildNo}}.png"/><span>{{C_guildName}}</span><span class="guildIntroduction" style="display:none">{{C_guildIntroduction}}</span></div>' +
        '<div class="col" attr-data="{{attr_C_masterUserNickname}}"><span data-tooltip="{{C_masterUserNickname}}">{{C_masterUserNickname}}</span></div>' +
        '<div class="col" attr-data="{{attr_C_regionKey1}}"><span>{{C_regionKey1}} <span class="arial" style="color:#8F8F8F">/</span> {{C_spotSiegeCount}}</span></div>' +
        '<div class="col" attr-data="{{attr_C_winCount}}"><span class="arial">{{C_winCount}}</span></div>' +
        '<div class="col" attr-data="{{attr_C_aquiredSkillPoint}}"><span class="arial">{{C_aquiredSkillPoint}}</span></div>' +
        '<div class="col" attr-data="{{attr_C_memberCount}}"><span>{{C_memberCount}}/{{C_variedMemberCount}}</span></div>' +
        '<div class="col" attr-data="{{attr_C_isIntroduction}}"><span>{{C_isIntroduction}}</span></div>' +
        '<div class="col" attr-data="{{attr_C_commentCount}}"><span class="arial">{{C_commentCount}}</span></div>' +
        '</div>';

    $.each(data.guildRankList, function (key, value) {
        var str = format;

        var areas = [value.C_regionKey1, value.C_regionKey2, value.C_regionKey3];

        $.each(value, function (idx, val) {
            var reg = new RegExp('{{' + idx + '}}', 'gi');
            var regAttr = new RegExp('{{attr_' + idx + '}}', 'gi');

            if (idx == 'attr_C_masterUserNickname') {
                val = pearl.common.isEmpty(val) || parseInt(val) == -1 ? '-' : val;
            } else if (idx == 'C_masterUserNickname') {
                val = pearl.common.isEmpty(val) || parseInt(val) == -1 ? '-' : val;
            } else if (idx == 'C_regionKey1') {
                if (!pearl.common.isEmpty(val)) {

                    var territoryKeys = [5, 32, 77, 202, 229];
                    var area1 = val;
                    var area = [];

                    $.each(territoryKeys, function (k, v) {
                        if (v == val)
                            area1 = k;

                        $.each(areas, function (a, b) {
                            if (!pearl.common.isEmpty(b)) {
                                if (b == v)
                                    area.push(k);
                            }
                        });

                    });

                    var areaLength = area.length > 1 ? 'x' + area.length : '';

                    val = '<img src="/Areas/GuildRank/common/img/territorymark_' + area1 + '.png"  data-tooltip="' + getTerritoryName(area) + '"/>' + areaLength;

                } else
                    val = '-';
                val = pearl.common.isEmpty(val) || parseInt(val) <= 0 ? '-' : val;
            } else if (idx == 'C_variedMemberCount') {
                // 클랜 = 0  ~ 15명
                // 소형 = 16 ~ 30명
                // 중형 = 31 ~ 50명
                // 대형 = 51 ~ 75명
                // 초대형 = 76 ~ 100명
                val = parseInt(val) + 15;

                val = pearl.common.isEmpty(val) || val < 1 ? '-' :
                    val <= 15 ? pearl.string.getStringTable('WEB_GUILD_SCALE_SMALLEST') :
                        val <= 30 ? pearl.string.getStringTable('WEB_GUILD_SCALE_SMALL') :
                            val <= 50 ? pearl.string.getStringTable('WEB_GUILD_SCALE_MIDDLE') :
                                val <= 75 ? pearl.string.getStringTable('WEB_GUILD_SCALE_BIG') : pearl.string.getStringTable('WEB_GUILD_SCALE_BIGGEST');

            } else if (idx == 'C_isIntroduction') {
                val = pearl.common.isEmpty(val) || val == '0' ? '-' : 'O';
            } else if (idx == 'C_guildIntroduction') {
                val = pearl.common.isEmpty(val) ? pearl.string.getStringTable('WEB_GUILDRANK_NO_GUILD_INFORMATION') : val.replace(/\n/gi, '<br />');
            }

            str = str.replace(reg, pearl.common.isEmpty(val) ? '0' : val).replace(regAttr, idx);
        });
        $('#container .area .content .table.guildList .tbody').append(str);
    });
    for (i = 1; i < 19 - data.guildRankList.length; i++) {
        $('#container .area .content .table.guildList .tbody').append('<div class="row block"><div class="col"></div><div class="col"></div><div class="col"></div><div class="col"></div><div class="col"></div><div class="col"></div><div class="col"></div><div class="col"></div><div class="col"></div></div>');
    }

    // 항목순위별 버튼 클릭시 데이터 밝게 표현
    var idx = $('.thead .col.active').index();
    $.each($('.tbody .row'), function (key, val) {
        $($(val).find('.col')[idx]).css('color', 'white');
    });

    if ($('.scroll').height() >= $('.scroll .guildList').height())
        $('.guildList .thead .col:last-child').hide();
    else
        $('.guildList .thead .col:last-child').show();

    pearl.common.loading(false);
    $('#paging').setMaxLength(data.totalPage > 30 ? 30 : data.totalPage);
}

// 순위별 검색 버튼
$(document).on('click', '.buttonArea a', function (e) {
    // 버튼 비활성화
    var parents = $(this).parents('#top');
    parents.find('.buttonArea a.active').find('img').attr('src', '/Areas/GuildRank/common/img/icn_' + parents.find('.buttonArea a.active').attr('attr-key') + '.png');
    parents.find('.buttonArea a.active').removeClass('active');

    // 클릭된 버튼 활성화
    $(this).addClass('active');
    $(this).find('img').attr('src', '/Areas/GuildRank/common/img/icn_' + $(this).attr('attr-key') + '_active.png');

    // 항목순위별 버튼 클릭시 데이터 밝게 표현
    var data = $(this).attr('attr-key');
    $('#container .area .content .table.guildList .thead .col').removeClass('active');
    $('#container .area .content .table.guildList .thead .col[attr-data="' + data + '"]').addClass('active');

    $('.search.text').val('');
    $('#paging').setPage(1);

    $('.area .content').css('height', 'calc(100% - 62px)');
    $('.scroll').css('overflow-y', 'hidden');
    $('#paging').show();
    $('.guildList .tcaption').hide();

    setTimeout(function () {
        var width = ($('#bottom .area').width() - parseInt($('#bottom .area > span').css('width'))) - parseInt($('.searchArea > .search.comboBox').css('width')) - parseInt($('.searchArea > .search.btn').css('width'));
        $('.search.text').css('width', width - 15);
    }, 500);
});
// 콤보박스 클릭시 리스트 보이기/숨기기
$(document).on('click', '#bottom .area > .searchArea .search.comboBox', function (e) {
    e.preventDefault();
    e.stopPropagation();
    $(this).find('ul').toggleClass('hide');
    if (!$(this).find('ul').hasClass('hide')) {
        $(this).find('ul').css('right', $(window).width() - $('.search.comboBox').offset().left - parseInt($('.search.comboBox').css('width')));
    }
})

// 선택된 아이템의 텍스트 바인딩
$(document).on('click', '#bottom .area > .searchArea .search.comboBox li', function (e) {
    $('#bottom .area > .searchArea .search.comboBox > span').text($(this).text());
    $('#bottom .area > .searchArea .search.comboBox').attr('data-key', $(this).attr('data-key'));

    var width = ($('#bottom .area').width() - parseInt($('#bottom .area > span').css('width'))) - parseInt($('.searchArea > .search.comboBox').css('width')) - parseInt($('.searchArea > .search.btn').css('width'));
    $('.search.text').css('width', width - 15);

    //if ($(this).attr('data-str') == 'WEB_GUILDRANK_GUILDNAME')
    //    $('#search').attr('placeholder', pearl.string.getStringTable('WEB_GUILDRANK_ENTER_GUILD_NAME'));
    //else
    //    $('#search').attr('placeholder', pearl.string.getStringTable('WEB_GUILDRANK_ENTER_GUILDMASTER_NICKNAME'));
})

// 콤보박스 이외의 배경 클릭시 숨기기
$(document).on('click', function (e) {
    $('#bottom .area > .searchArea .search.comboBox ul').addClass('hide');
});

// 길드정보 팝업 열기
$(document).on('mouseover', '#container .area .content .table.guildList .tbody .row [attr-data="C_guildName"] span', function (e) {
    var guildName = $(this).text();
    var guildMasterNickname = $(this).parents('.row').find('[attr-data="C_masterUserNickname"] span').text();
    var guildDescription = $(this).next().html();
    var guildNo = $(this).parents('.row').attr('attr-guildno');

    $('.popup').find('img').attr('src', 'coui://guildmark/' + guildNo + '.png');
    $('.popup').find('.guildName').text(guildName);
    $('.popup').find('.guildMaster').text(guildMasterNickname);
    $('.popup').find('.guildDescription').html(pearl.common.isEmpty(guildDescription) ? pearl.string.getStringTable('WEB_GUILDRANK_NO_GUILD_INFORMATION') : pearl.string.getStringTable(guildDescription));

    var position = $(this).offset();
    var top = position.top;

    if (top + parseInt($('.popup').css('height')) > $('body').height())
        top = $('body').height() - parseInt($('.popup').css('height')) - 10;

    $('.popup').css('top', top).css('left', position.left + $(this).width() + 20);

    $('.popup').removeClass('hide');
});

// 길드정보 팝업 닫기
$(document).on('mouseout', '#container .area .content .table.guildList .tbody .row [attr-data="C_guildName"] span, .popup', function (e) {
    $('.popup').addClass('hide');
});

// 콤보박스 이외의 배경 클릭시 숨기기
$(document).on('mouseover', '.popup', function (e) {
    $('.popup').removeClass('hide');
});

// 항목순위 버튼
$(document).on('click', '.item a', function (e) {
    $('.scroll').scrollTop(0);
    $('#type').val('RANK');
    getGuildRankList($(this).index(), 1);
});

// 분야순위 버튼
$(document).on('click', '.branch a', function (e) {
    $('.scroll').scrollTop(0);
    $('#type').val('LIFTRANK');
    getGuildLifeRankList($(this).index(), 1);
});

// 검색 버튼
$(document).on('click', '.btn.search', function (e) {
    if (pearl.common.isEmpty($('.search.text').val()) || $('.search.text').val().length < 2) {
        pearl.common.messageBox('WEB_GUILDRANK_SEARCHTEXT_MINIMUM_TWO_LENGTH');
    } else {
        $('.area .content').css('height', 'calc(100% - 6px)');
        $('#paging').hide();
        $('.scroll').css('overflow-y', 'auto');

        $('#paging').setPage(1);

        $('.guildList .tcaption').text($('.search.comboBox span').text() + ' [' + $('.search.text').val() + ']' + pearl.string.getStringTable('WEB_GUILDRANK_SEARCH_RESULT'));
        $('.guildList .tcaption').show();

        $('.buttonArea a.active').find('img').attr('src', '/Areas/GuildRank/common/img/icn_' + $('.buttonArea a.active').attr('attr-key') + '.png');
        $('.buttonArea a.active').removeClass('active');

        $('.scroll').scrollTop(0);
        $('#type').val('SEARCHRANK');

        $('#container .area .content .table.guildList .thead .col').removeClass('active');

        getGuildSearchRankList($('.search.comboBox').attr('data-key'), $('.search.text').val());
    }
})

// 길드명 클릭시 길드페이지 이동
$(document).on('click', '#container .area .content .table.guildList .tbody .row', function (e) {
    var guildNo = $(this).attr('attr-guildno');
    //console.log(guildNo);

    if ($.isNumeric(guildNo))
        engine.call('ToClient_DoLua', 'FGlobal_GuildWebInfoForGuildRank_Open("' + guildNo + '")');

});
