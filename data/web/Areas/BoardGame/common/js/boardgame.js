//var arrivalPosition 		= 0;	// DB에 저장된 포지션
//var clearBlackMarbleCount 	= 0;	// 완주 카운트
var diceCount = 0;	// 주사위 남은 횟수
var rollableDice = false;	// 주사위 굴릴 수 있는지 여부
var rollDiceValue = 0;	// 주사위 값
var arrivalPosition = 0;	// 주사위 던진 후 최종 포지션
var dicePosition = 0;	// 주사위 한 번 굴린 후의 포지션(서버)
var oneMovePosition = 0;	// 주사위 한 번 굴린 후의 포지션
var moreMovable = false;	// 주사위 굴린 후 한 번 더 움직이냐?
var itemIndex = 0;	// 받게 될 보상 인덱스, 랜덤일 경우 받을 랜덤 인덱스
var todayCount = 0;	// 오늘 주사위 굴린 횟수
var finishCount = 0;	// 완주 횟수
var lastDate = 0;	// 마지막 주사위를 던진 날짜
var overTime = 0;	// 마지막 주사위를 던지고 지난 시간(분)

var serverNo = 0;
var userId = "";
var characterNo = 0;
var characterName = "";
var prizeType = 0;

var leftTime = 0;	// 남은 시간
var overtimeInterval;

var option = {
    speed: 10,
    duration: 3,
    stopImageNumber: -1
}

function confirmLayer(id, elementId, functionName, msg) { // 전달 인자값, 엘레먼트ID, 클릭시 실행할 함수명, 보여줄 메세지
    $(elementId).html('');
    $(elementId).css('display', 'block');
    $(elementId).append('<div class="confirmMsg">' + msg + '</div>');
    $(elementId).append('<div class="confirmYes" onclick="' + functionName + '(' + id + ')">' + $('#WEB_GUILDINFO_ALERT_YES').attr("title") + '</div>');
    $(elementId).append('<div class="confirmNo" onclick="' + functionName + '(0)">' + $('#WEB_GUILDINFO_ALERT_NO').attr("title") + '</div>');
}

function alertLayer(elementId, functionName, msg) { // 전달 인자값, 엘레먼트ID, 클릭시 실행할 함수명, 보여줄 메세지
    $(elementId).html('');
    $(elementId).css('display', 'block');
    $(elementId).append('<div class="alertLayerWrap"><div class="alertMsg">' + msg + '</div><div class="alertOk" onclick="' + functionName + '">' + $('#WEB_GUILDINFO_MEMBER_TOOLTIP_CLOSE').attr("title") + '</div></div>');
}

function alertClose(thisLayer, frmIdName) {
    $(thisLayer).css('display', 'none');
    $('#' + frmIdName).focus();
}

function boardgameAlertClose(thisLayer) {
    $(thisLayer).css('display', 'none');
}


// 한 번에 점프용 애니메이션(스타트 지점 이동 및 백점프)
function gotoSpiritMove(currentPosition) {
    $("#pointer").animate({							// 흑정령 이동 애니메이션
        top: $("#step" + currentPosition).position().top - 74, left: $("#step" + currentPosition).position().left + 16
    }, {duration: 150});

    $("#pointer").animate({
        top: $("#step" + currentPosition).position().top - 46, left: $("#step" + currentPosition).position().left + 3
    }, {
        duration: 150,
        complete: function () { 						// 흑정령 칸 이동이 완료될 때마다 타일 애니메이션 실행
            $("#step" + currentPosition).animate({
                top: $("#step" + currentPosition).position().top + 10
            }, {
                duration: 100,
                complete: function () {
                    $("#step" + currentPosition).animate({
                        top: $("#step" + currentPosition).position().top - 10
                    }, 100)
                }
            });
        }
    });

    oneMovePosition = checkArrivalPosition(currentPosition, false);
}

// 일반적인 이동 애니메이션
function moveSpiritAnimation(diceValue, currentPosition, isFirstMove) {
    if (diceValue != 0) {
        diceValue = diceValue - 1;
        if (currentPosition == 47 && isFirstMove == false) {
            var targetTop = $(".board").eq(0).position().top;
            var targetLeft = $(".board").eq(0).position().left;
            currentPosition = 0;
        } else if (currentPosition == 50 && isFirstMove == false) {
            var targetTop = $(".board").eq(20).position().top;
            var targetLeft = $(".board").eq(20).position().left;
            currentPosition = 20;
        } else if (currentPosition == 53) {
            var targetTop = $(".board").eq(44).position().top;
            var targetLeft = $(".board").eq(44).position().left;
            currentPosition = 44;
        } else {
            var targetTop = $(".board").eq(currentPosition).next().position().top;
            var targetLeft = $(".board").eq(currentPosition).next().position().left;
            currentPosition++;
        }
        isFirstMove = false;

        $("#pointer").animate({							// 흑정령 이동 애니메이션
            top: targetTop - 74, left: targetLeft + 16
        }, {duration: 150});

        $("#pointer").animate({
            top: targetTop - 46, left: targetLeft + 3
        }, {
            duration: 150,
            complete: function () { 						// 흑정령 칸 이동이 완료될 때마다 타일 애니메이션 실행
                $("#step" + currentPosition).animate({
                    top: $("#step" + currentPosition).position().top + 10
                }, {
                    duration: 100,
                    complete: function () {
                        $("#step" + currentPosition).animate({
                            top: $("#step" + currentPosition).position().top - 10
                        }, 100)
                        $('#moveSound').removeAttr("src");
                        $('#moveSound').attr("src", "/Areas/BoardGame/common/sound/move.ogg");
                        document.getElementById('moveSound').play();
                    }
                });

                checkStartPosition(currentPosition);
                if (currentPosition == 0) {
                    setTimeout(function () {
                        moveSpiritAnimation(diceValue, currentPosition, isFirstMove);			// 재귀적으로 호출
                    }, 3000)
                } else {
                    moveSpiritAnimation(diceValue, currentPosition, isFirstMove);			// 재귀적으로 호출
                }
            }
        });
    } else {
        oneMovePosition = checkArrivalPosition(currentPosition, false);
    }
}

// 랜덤 아이템 보상 받기
function selectIndexByServer() {
    var randomValue = itemIndex;
    return randomValue;
}

function checkStartPosition(position) {
    // 완주 카운트
    if (position == 0) {
        clearBlackMarbleCount = clearBlackMarbleCount + 1;
        timeCheck();
        finishCheck();
        commentCheck(clearBlackMarbleCount);
        blackSpiritImgChange(clearBlackMarbleCount);
        $("#step48").slideUp(500);
        $("#step49").slideUp(500);
        $("#step50").slideUp(500);
        $("#step51").slideUp(500);
        $("#step52").slideUp(500);
        $("#step53").slideUp(500);
    }
}

function blackSpiritImgChange(count) {
    switch (count) {
        case 0:
            $("#pointer").css("background-image", "url(/Areas/BoardGame/common/img/img_boardGamePointer.png)");
            break;
        case 1:
            $("#pointer").css("background-image", "url(/Areas/BoardGame/common/img/img_boardGamePointer1.png)");
            break;
        case 2:
            $("#pointer").css("background-image", "url(/Areas/BoardGame/common/img/img_boardGamePointer2.png)");
            break;
        case 3:
            $("#pointer").css("background-image", "url(/Areas/BoardGame/common/img/img_boardGamePointer3.png)");
            break;
        default:
            $("#pointer").css("background-image", "url(/Areas/BoardGame/common/img/img_boardGamePointer4.png)");
            break;
    }
}

function gotoStartPosition() {
    gotoSpiritMove(0);
    checkStartPosition(0);
    document.getElementById('jumpSound').play();
}
function gotoBack() {
    gotoSpiritMove(6);
    document.getElementById('goBackSound').play();
}
function gotoplus3_7() {
    moveSpiritAnimation(3, 7, false)
}
function gotoplus3_47() {
    moveSpiritAnimation(3, 47, false)
}
function gotoplus2_19() {
    moveSpiritAnimation(2, 19, false)
}
function gotoplus2_42() {
    moveSpiritAnimation(2, 42, false)
}
function gotoplus5_26() {
    moveSpiritAnimation(5, 26, false)
}
function oneMoreDice() {
    rollableDice = true;
    ajaxRequest();
}

function checkArrivalPosition(position, isFirstMove) {
    var string = '';
    if (position == 25) {			// 스타트 지점으로!
//		position = 0;
        setTimeout(gotoStartPosition, 3000);
        document.getElementById('windowOpenSound').play();
    } else if (position == 30) {		// 백점프
//		position = 6;
        setTimeout(gotoBack, 3000);
        document.getElementById('windowOpenSound').play();
    } else if (position == 7) {
        setTimeout(gotoplus3_7, 3000);
        document.getElementById('windowOpenSound').play();
    } else if (position == 47) {
        setTimeout(gotoplus3_47, 3000);
        document.getElementById('windowOpenSound').play();
    } else if (position == 19) {
        setTimeout(gotoplus2_19, 3000);
        document.getElementById('windowOpenSound').play();
    } else if (position == 42) {
        setTimeout(gotoplus2_42, 3000);
        document.getElementById('windowOpenSound').play();
    } else if (position == 26) {
        setTimeout(gotoplus5_26, 3000);
        document.getElementById('windowOpenSound').play();
    } else if (position == 1 || position == 12 || position == 33) {
        setTimeout(oneMoreDice, 3000);
        document.getElementById('windowOpenSound').play();
    } else if (position == 2 || position == 5 || position == 11 || position == 14 || position == 18 || position == 19 || position == 21 || position == 24 || position == 29 || position == 34 || position == 38 || position == 41
        || position == 44 || position == 3 || position == 16 || position == 36 || position == 49 || position == 51 || position == 15 || position == 23 || position == 35 || position == 50 || position == 52) {
        document.getElementById('rewardSound').play();
    } else if (position == 6 || position == 10 || position == 20 || position == 31 || position == 37 || position == 46 || position == 27 || position == 43) {
        document.getElementById('rouletteSound').play();
        roulletPop();
        return position;
    } else if (position == 13 || position == 40 || position == 48 || position == 53) {
        document.getElementById('rewardSound').play();
    } else if (position == 4 || position == 9 || position == 17 || position == 22 || position == 28 || position == 39 || position == 45) {
        document.getElementById('buffSound').play();
    } else if (position == 8 || position == 32) {
        document.getElementById('secretRoadSound').play();
    }
    string = stringArray[position];

    if (position == 6) {
        $("#step48").slideUp(500);
        $("#step49").slideUp(500);
        $("#step50").slideUp(500);
        $("#step51").slideUp(500);
        $("#step52").slideUp(500);
        $("#step53").slideUp(500);
    }

    if (position == 8 || position == 48 || position == 49 || position == 50) {
        $("#step48").slideDown(1000);
        $("#step49").slideDown(1000);
        $("#step50").slideDown(1000);
    } else if (position == 32 || position == 51 || position == 52 || position == 53) {
        $("#step51").slideDown(1000);
        $("#step52").slideDown(1000);
        $("#step53").slideDown(1000);
    }

    if (position != 0) {
        rewardOutput(string);
    }

    if (prizeType == 2) {
        engine.call('ToClient_ApplyBuffForBlackMarble');
    }

    return position;
}

function diceClick() {
    if (rollableDice == false) {
        return;
    }
    if (setFinishCount <= clearBlackMarbleCount) {
        return;
    }

    rollableDice = false

    ajaxRequest();
}

function fromAjaxDiceRoll() {
    document.getElementById('diceSound').play();

    var diceValue = rollDiceValue;
    var isFirstMove = false;
    var currentPosition = 0;
    if (startPosition == 8) {
        currentPosition = 47;
        isFirstMove = true;
    } else if (startPosition == 32) {
        currentPosition = 50;
        isFirstMove = true;
    } else {
        currentPosition = startPosition;
    }

    var src = "/Areas/BoardGame/common/img/rollingDice_" + diceValue + ".gif";
    $('div.rollingDice1 img').attr("style", "width:517px");
    $('div.rollingDice1 img').attr("src", src);
    $('div.rollingDice1').css("width", "517px");
    $('div.rollingDice1').css("height", "136px");


    var removeDiceAni = setInterval(function () {
        clearInterval(removeDiceAni);
        moveSpiritAnimation(diceValue, currentPosition, isFirstMove);
    }, 1000 * 3);

    setTimeout(function () {
        $('div.rollingDice1').css("width", "0px");			// 툴팁을 살리기 위해 사이즈를 0으로 맞춘다
        $('div.rollingDice1').css("height", "0px");			// 툴팁을 살리기 위해 사이즈를 0으로 맞춘다

        $('div.rollingDice1 img').attr("style", "width:0px"); // 툴팁을 살리기 위해 사이즈를 0으로 맞춘다 ( 추가 )
        $('div.rollingDice1 img').removeAttr("src");

    }, 1000 * 6);

    timeCheck();
    startPosition = arrivalPosition;

    var leftTimeHtml = $("#WEB_PEARLMARBLE_COUNT_TYPE_MESSAGE_3").attr("title").replace(":", ": " + leftTime);
    if (clearBlackMarbleCount >= setFinishCount) {
        leftTimeHtml = $("#WEB_PEARLMARBLE_PLAY_CHECK_MESSAGE_1").attr("title");
    }
    else if (todayCount <= 0) {
        leftTimeHtml = $("#WEB_PEARLMARBLE_PLAY_CHECK_MESSAGE_2").attr("title");
        clearInterval(overtimeInterval);
    }
    else {
        overtimeInterval = setInterval("updateOverTime()", 1000 * 60);
    }
    document.getElementsByClassName("topLeftTime")[0].innerHTML = leftTimeHtml;
}

function showStartArrowAnimation() {
    var src = "/Areas/BoardGame/common/img/gotoStartEffect.gif";
    $('div.gotoStartEffect img').attr("src", src);
    $('div.gotoStartEffect img').attr("style", "");
    $('div.gotoStartEffect').css("width", '533px');
    $('div.gotoStartEffect').css("height", '309px');
}
function hideStartArrowAnimation() {
    //$('div.gotoStartEffect img').removeAttr("src");
    $('div.gotoStartEffect img').attr("style", "width:0px");
    $('div.gotoStartEffect').css("width", '0px');
    $('div.gotoStartEffect').css("height", '0px');
}

function showBackArrowAnimation() {
    var src = "/Areas/BoardGame/common/img/gotoBackEffect.gif";
    $('div.gotoBackEffect img').attr("src", src);
    $('div.gotoBackEffect img').attr("style", "");
    $('div.gotoBackEffect').css("width", '210px');
    $('div.gotoBackEffect').css("height", '310px');
}
function hideBackArrowAnimation() {
    //$('div.gotoBackEffect img').removeAttr("src");
    $('div.gotoBackEffect img').attr("style", "width:0px");
    $('div.gotoBackEffect').css("width", '0px');
    $('div.gotoBackEffect').css("height", '0px');
}

// 서버에 요청
function ajaxRequest() {

    var getNation = pearl.common.getLanguageCode().countryCode;

    $.ajax({
        method: "POST",
        url: "/BoardGame/Game/Update",
        dataType: "json",
        data: {
            serverNo: serverNo
            , userId: userId
            , characterNo: characterNo
            , characterName: characterName
            , blackSoul: $("#PANEL_QUESTLIST_BLACKSOUL").attr("title")
            , mailTitle: $("#WEB_PEARLMARBLE_MAIL_TITLE").attr("title")
            , mailContent: $("#WEB_PEARLMARBLE_MAIL_CONTENTS").attr("title")
            , nationCode: getNation
        }
    })
        .done(function (rs) {

            if (0 < rs.errorCode) {
                errorAlert(rs.errorCode);
                return;
            }

            prizeType = rs.prizeType;
            rollDiceValue = rs.diceValue;
            arrivalPosition = rs.nowPosition;
            dicePosition = rs.orgPosition;
            itemIndex = rs.itemIndex;
            todayCount = rs.todayCount;
            finishCount = rs.finishCount;
            lastDate = rs.lastDate;
            overTime = rs.overTime;
            leftTime = overtimeRemain;

            if (arrivalPosition == dicePosition) {
                moreMovable = false;
            } else {
                moreMovable = true;
            }

            diceCount = todayRemainCount - rs.todayCount;
            fromAjaxDiceRoll();
        });
}

// 랜덤 룰렛 굴리기
function roulletPop() {
    $(".rewardPopLayer").show().delay(1000 * 12).slideUp(50);
    $('div.roulette_container').css('left', '440px');
    $('div.roulette_container').show();
    $('div.roulette_container').delay(1000 * 12).hide(50);
    $('div.roulette').roulette('start');
}

function rewardOutput(rewardName) {
    $("span.rewardText").slideDown(200).delay(2000).slideUp(50);
    document.getElementsByClassName("rewardText")[0].innerHTML = '<h2>' + rewardName + '</h2>';
}

function timeCheck() {
    var clearCount = '<br/><h4>' + ($("#WEB_PEARLMARBLE_COUNT_TYPE_MESSAGE_1").attr("title")).replace(":", ": " + clearBlackMarbleCount) + '</h4><p>' + ($("#WEB_PEARLMARBLE_COUNT_TYPE_MESSAGE_2").attr("title")).replace(":", ": " + diceCount) + '</p>';
    document.getElementsByClassName("topLayer")[0].innerHTML = clearCount;
    diceEffect(false);
}

function diceEffect(effectOn) {
    $('#diceBg img').attr('img', '');
//	$('#diceEffect #diceEffectImg').attr("src", "");

    if (effectOn == true) {
        rollableDice = true;
    } else if (effectOn == false) {
        rollableDice = false;
    }

    if (rollableDice == true) {
        $('#diceBg #diceBgImg').attr("src", "/Areas/BoardGame/common/img/dice_backgroundBg.png");
        $('#diceIcon img').attr("src", "/Areas/BoardGame/common/img/diceIconEffect.gif");
        $('#diceEffect').remove();
        $('#diceEffect').append("<img src=\"/Areas/BoardGame/common/img/diceEffect.gif\">");
        $('#diceIconMask img').attr("src", "/Areas/BoardGame/common/img/blank.png");
    } else {
        $('#diceBg #diceBgImg').attr("src", "/Areas/BoardGame/common/img/dice_backgroundBgGray.png");
        $('#diceIconMask img').attr("src", "/Areas/BoardGame/common/img/diceIconMask.png");
        $('#diceIconMask').fadeOut(200, function () {
            $('#diceIconMask img').attr("src", "");
            $('#diceIconMask').fadeIn();
        });
        $('#diceEffect').remove();
        $('#diceIcon #diceIconImg').attr("src", "/Areas/BoardGame/common/img/diceIcon.png");
        $("#diceIcon").animate({
            top: $("#diceIcon").position().top + 10
        }, {
            duration: 100,
            complete: function () {
                $("#diceIcon").animate({
                    top: $("#diceIcon").position().top - 10
                }, 100);
            }
        });
    }
}

function diceEffectOff() {
    $('#diceBg #diceBgImg').attr("src", "/Areas/BoardGame/common/img/dice_backgroundBgGray.png");
    $('#diceIconMask img').attr("src", "");
    $('#diceIcon #diceIconImg').attr("src", "/Areas/BoardGame/common/img/diceIcon.png");
}

function finishCheck() {
    var clearCount = '<h1><b>' + $("#WEB_PEARLMARBLE_COUNT_TYPE_MESSAGE_1").attr("title").replace(":", ": " + clearBlackMarbleCount) + '</b></h1>';
    document.getElementsByClassName("finishText")[0].innerHTML = clearCount;
    $("span.finishText").show(200).delay(3000).hide(100);
    rewardOutput(stringArray[0]);
}

function commentCheck(count) {
    var str = '';
    $('#blackSpiritSound').removeAttr("src");
    switch (count) {
        case 1:
            str = $("#WEB_PEARLMARBLE_BLACK_SPIRIT_TALK_1").attr("title");
            $('#blackSpiritSound').attr("src", "/Areas/BoardGame/common/sound/blackSpirit01.ogg");
            break;
        case 2:
            str = $("#WEB_PEARLMARBLE_BLACK_SPIRIT_TALK_2").attr("title");
            $('#blackSpiritSound').attr("src", "/Areas/BoardGame/common/sound/blackSpirit02.ogg");
            break;
        case 3:
            str = $("#WEB_PEARLMARBLE_BLACK_SPIRIT_TALK_3").attr("title");
            $('#blackSpiritSound').attr("src", "/Areas/BoardGame/common/sound/blackSpirit03.ogg");
            break;
        case 4:
            str = $("#WEB_PEARLMARBLE_BLACK_SPIRIT_TALK_4").attr("title");
            $('#blackSpiritSound').attr("src", "/Areas/BoardGame/common/sound/blackSpirit04.ogg");
            break;
        default:
            str = '';
            $('#blackSpiritSound').attr("src", "/Areas/BoardGame/common/sound/blackSpirit06.ogg");
            break;
    }
    document.getElementById('blackSpiritSound').play();

    if (count < 5) {
        document.getElementsByClassName("blackSpiritComment")[0].innerHTML = str;
        $(".blackSpiritComment").show(100).delay(2800).hide(100);
    }
}

function updateOverTime() {
    leftTime = leftTime - 1;
    if (leftTime < 0) {
        leftTime = 0;
    }

    if ((leftTime == 0) && (0 < todayCount) && (clearBlackMarbleCount < setFinishCount)) {
        rollableDice = true;
        diceEffect(true);
    }

    var leftTimeHtml = $("#WEB_PEARLMARBLE_COUNT_TYPE_MESSAGE_3").attr("title").replace(":", ": " + leftTime);
    if (clearBlackMarbleCount >= setFinishCount) {
        leftTimeHtml = $("#WEB_PEARLMARBLE_PLAY_CHECK_MESSAGE_1").attr("title");
    }
    else if (todayCount <= 0) {
        leftTimeHtml = $("#WEB_PEARLMARBLE_PLAY_CHECK_MESSAGE_2").attr("title");
        clearInterval(overtimeInterval);
    } else if (leftTime == 0) {
        leftTimeHtml = $("#WEB_PEARLMARBLE_PLAY_CHECK_MESSAGE_3").attr("title");
        clearInterval(overtimeInterval);
    }
    document.getElementsByClassName("topLeftTime")[0].innerHTML = leftTimeHtml;
}

// Error 알림
function errorAlert(errorCode) {
    switch (errorCode) {
        case 11:	// 5회 완주 완료 했을 경우
            //alert('더 이상 게임을 할 수 없습니다.');
            alertLayer('.alertLayer', 'boardgameAlertClose(\'.alertLayer\');', $("#WEB_PEARLMARBLE_CAN_NOT_MESSAGE_1").attr("title"));
            rollableDice = false;
            break;
        case 12:	// 하루에 3회 이상 게임을 할 경우
            //alert('하루 3번만 가능합니다.');
            alertLayer('.alertLayer', 'boardgameAlertClose(\'.alertLayer\');', $("#WEB_PEARLMARBLE_CAN_NOT_MESSAGE_2").attr("title"));
            rollableDice = false;
            break;
        case 13:	// 게임 후 한 시간 안에 다시 플레이 할 경우
            //alert('잠시 후에 게임이 가능합니다.');
            alertLayer('.alertLayer', 'boardgameAlertClose(\'.alertLayer\');', $("#WEB_PEARLMARBLE_CAN_NOT_MESSAGE_3").attr("title"));
            rollableDice = false;
            break;
        case 14:	// 날짜가 지난 후에 플레이 할 경우
            //alert('게임에 참여가능한 날짜가 아닙니다.');
            alertLayer('.alertLayer', 'boardgameAlertClose(\'.alertLayer\');', $("#WEB_PEARLMARBLE_CAN_NOT_MESSAGE_4").attr("title"));
            rollableDice = false;
            break;
        case 101:		// 로그 기록에 실패 했을 경우
        case 102:
        case 201:		// 게임 기록에 실패 했을 경우
        case 202:
            //alert('주사위를 다시 굴려주세요.');
            alertLayer('.alertLayer', 'boardgameAlertClose(\'.alertLayer\');', $("#WEB_PEARLMARBLE_CAN_NOT_MESSAGE_5").attr("title"));
            rollableDice = false;
            break;
        case 301:	// 메일 보내다가 실패 할 경우
        case 302:
        case 401:
        case 402:
        case 411:
        case 412:
            //alert('메일을 보내다가 실패 하였습니다.');
            alertLayer('.alertLayer', 'boardgameAlertClose(\'.alertLayer\');', $("#WEB_PEARLMARBLE_FAIL_MESSAGE_1").attr("title"));
            break;
        case 501:
        case 502:
            //alert('버프를 적용하다가 실패 하였습니다.');
            alertLayer('.alertLayer', 'boardgameAlertClose(\'.alertLayer\');', $("#WEB_PEARLMARBLE_FAIL_MESSAGE_2").attr("title"));
            break;
        default:
            //alert('주사위를 다시 던져주시기 바랍니다.');
            alertLayer('.alertLayer', 'boardgameAlertClose(\'.alertLayer\');', $("#WEB_PEARLMARBLE_CAN_NOT_MESSAGE_5").attr("title"));
            break;
    }
}


engine.call('ToClient_RequestSelfplayerInfo2').then(function (returnValue) {
    userId = returnValue._userNickName;
});

engine.on('FromClient_ResponseSelfplayerInfo', function (playServerNo, playUserId, playCharacterNo, playCharacterName) {
    serverNo = playServerNo;
    //userId					= playUserId;
    characterNo = playCharacterNo;
    characterName = playCharacterName;
});

// string table key
var strTextArray = [
    'WEB_PEARLMARBLE_COUNT_TYPE_MESSAGE_1',
    'WEB_PEARLMARBLE_COUNT_TYPE_MESSAGE_2',
    'WEB_PEARLMARBLE_COUNT_TYPE_MESSAGE_3',
    'WEB_PEARLMARBLE_PLAY_CHECK_MESSAGE_1',
    'WEB_PEARLMARBLE_PLAY_CHECK_MESSAGE_2',
    'WEB_PEARLMARBLE_PLAY_CHECK_MESSAGE_3',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_1',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_2',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_3',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_4',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_5',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_6',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_7',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_8',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_9',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_10',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_11',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_12',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_13',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_14',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_15',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_16',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_17',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_18',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_19',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_20',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_21',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_22',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_23',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_24',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_25',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_26',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_27',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_28',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_29',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_30',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_31',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_32',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_33',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_34',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_35',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_36',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_37',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_38',
    'WEB_PEARLMARBLE_GAME_STEP_MESSAGE_39',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_1',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_2',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_3',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_4',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_5',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_6',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_7',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_8',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_9',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_10',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_11',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_12',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_13',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_14',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_15',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_16',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_17',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_18',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_19',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_20',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_21',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_22',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_23',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_24',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_25',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_26',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_27',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_28',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_29',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_30',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_31',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_32',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_33',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_34',
    'WEB_PEARLMARBLE_GAME_ROULETTE_MESSAGE_35',
    'WEB_PEARLMARBLE_BLACK_SPIRIT_TALK_1',
    'WEB_PEARLMARBLE_BLACK_SPIRIT_TALK_2',
    'WEB_PEARLMARBLE_BLACK_SPIRIT_TALK_3',
    'WEB_PEARLMARBLE_BLACK_SPIRIT_TALK_4',
    'WEB_PEARLMARBLE_CAN_NOT_MESSAGE_1',
    'WEB_PEARLMARBLE_CAN_NOT_MESSAGE_2',
    'WEB_PEARLMARBLE_CAN_NOT_MESSAGE_3',
    'WEB_PEARLMARBLE_CAN_NOT_MESSAGE_4',
    'WEB_PEARLMARBLE_CAN_NOT_MESSAGE_5',
    'WEB_PEARLMARBLE_FAIL_MESSAGE_1',
    'WEB_PEARLMARBLE_FAIL_MESSAGE_2',
    'WEB_PEARLMARBLE_MAIL_TITLE',
    'WEB_PEARLMARBLE_MAIL_CONTENTS',
    'WEB_PEARLMARBLE_WARNING_MESSAGE_1',
    'WEB_PEARLMARBLE_WARNING_MESSAGE_2',
    'WEB_PEARLMARBLE_WARNING_MESSAGE_3',
    'WEB_PEARLMARBLE_WARNING_MESSAGE_4',
    'WEB_PEARLMARBLE_WARNING_MESSAGE_5',
    'WEB_GUILDINFO_MEMBER_TOOLTIP_CLOSE',
    'WEB_GUILDINFO_ALERT_YES',
    'WEB_GUILDINFO_ALERT_NO',
    'WEB_PEARLMARBLE_GAME_DESCRIPTION',
    'WEB_PEARLMARBLE_MEMORY_FRAGMENTS'
];