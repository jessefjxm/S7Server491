// Main페이지 초기화
function initMain(informationData) {
    $('.settings').hide();

    if (informationData.C_isBlind == 'Y') {
        //isGM = 'false';
        //isMaster = 'false';

        if (isGM == 'false') {
            $('#home.container').hide();
            pearl.common.pageMove('/GuildInfo/Blind/', ["guildNo=" + $('#guildNo').val()]);
        }
        if (isMaster == 'false') {
            $('#home.container').hide();
            pearl.common.pageMove('/GuildInfo/Blind/', ["guildNo=" + $('#guildNo').val()]);
        }
        
    }
    if (isMaster == 'true' || isMaster == true)
        $('.settings').show();
    else
        $('.settings').hide();

}

// Main페이지 바인딩
function bindMain(data) {
    if (pearl.common.isEmpty(data.information) && pearl.common.isEmpty(data.image) && pearl.common.isEmpty(data.youtube) && pearl.common.isEmpty(data.facebook)) {
        $('.defaultArea').show();
        $('.informationArea').hide();
        return false;
    } else {
        $('.defaultArea').hide();
        $('.informationArea').show();
        if (!pearl.common.isEmpty(data.information)) {
            $('.information').append(makeBr(data.information));
        } else {
            $('.information').hide();
        }
        if (!pearl.common.isEmpty(data.image)) {
            var timer = parseInt(Math.random() * 1000);
            $('.image').append('<img src="' + data.image + '?t=' + timer + '"/>');
        } else {
            $('.image').hide();
        }
        if (!pearl.common.isEmpty(data.youtube)) {
            $('.youtube').show();
            
            var videoCode = $($(data.youtube.split('/')).last()[0].split('=')).last()[0];
            $('.youtube').append('<div class="video-container"><iframe src="https://youtube.com/embed/' + videoCode + '?rel=0&controls=1&showinfo=0" width="100%" height=100% frameborder="0" ></iframe></div>');
            //$('.youtube').append('<div class="video-container"><iframe src="' + data.youtube + '?rel=0&showinfo=0" width="100%" height=100% frameborder="0" ></iframe></div>');
        } else {
            $('.youtube').hide();
        }
        if (!pearl.common.isEmpty(data.facebook)) {
            $('.facebook').empty();
            $('.facebook').append('<div class="fb-page" data-href="' + data.facebook + '" data-tabs="timeline" data-small-header="true" data-width="500" data-height="500"  data-hide-cover="false" data-show-facepile="false"><div class="fb-xfbml-parse-ignore"><blockquote cite="' + data.facebook + '"></blockquote></div></div>')

            setTimeout(function () {
                FB.XFBML.parse();
            }, 500);
        } else {
            $('.facebook').hide();
        }
    }
}

// 수정화면으로 이동 
$('#home').on('click', '.settings, .btnDetail', function (e) {
    if (isMaster == 'true') {
        pearl.common.messageBox('WEB_GUILDINFO_INTRO_WRITE_ALERT_WRITE', function (rs) {
            if (rs && isMaster) {
                pearl.common.pageMove('/GuildInfo/MainAdmin/', ["guildNo=" + $('#guildNo').val()]);
            }
        });
    } else {
        pearl.common.messageBox('WEB_GUILDINFO_INTRO_WRITEERROR_ALERT');
    }
});
