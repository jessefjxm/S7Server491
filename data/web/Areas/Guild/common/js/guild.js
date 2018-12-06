function initReply(frm){
	if(frm.comment.value==""){
		guildLayerReset();
		alertLayer('.alertLayer', 'alertClose(\'.alertLayer\',\'commentArea\');', $('#WEB_GUILDINFO_FORM_TEXT').attr("title"));
		return false;
	}

	if(frm.comment.value.length<2){
		guildLayerReset();
		alertLayer('.alertLayer', 'alertClose(\'.alertLayer\',\'commentArea\');', $('#WEB_GUILD_COMMENT_LENTH_OVER_TWO').attr("title"));
		return false;
	}
}

function commentDelete(cmtId){
	guildLayerReset();
	confirmLayer(cmtId, '.confirmLayer', 'commentRealDelete', $('#WEB_GUILDINFO_INTRO_WRITE_ALERT_COMENT_DEL').attr("title"));
}

function commentRealDelete(cmtId){
    $('.confirmLayer').css('display', 'none');
    if (cmtId == 0) {
	    return false;
	}else{
        //alert(certKey);
        location.replace('/Guild/DeleteGuildComment?userNo=' + userNo + '&cmtId=' + cmtId + '&certKey=' + certKey + '&guildNo=' + guildNo + '&isMaster=' + isMaster);
	}	
}

function commentShow(no){
	$("#cmt_"+no).css('display','block');
}

function guildLayerReset(){
	$('.confirmLayer').css('display','none');
	$('.alertLayer').css('display','none');
	$('.layerComment').css('display','none');
}

engine.on('FromClient_PAGetString', function (stringCategory, stringName, stringMessage) {
    $('#' + stringName).attr("title", stringMessage);
    $('[name="' + stringName + '"]').before(stringMessage);
    $('[name="' + stringName + '"]').remove();
});

var strTextArray = [
        'WEB_GUILDINFO_COMENT_NAVI_FIRST',
        'WEB_GUILDINFO_COMENT_NAVI_NEXT',
        'WEB_GUILD_COMMENT_WRITE',
        'WEB_GUILDINFO_FORM_TEXT_LIMIT',
        'WEB_GUILDINFO_FORM_TEXT',
        'WEB_GUILD_COMMENT_LENTH_OVER_TWO',
        'WEB_GUILDINFO_INTRO_WRITE_ALERT_COMENT_DEL',
        'WEB_GUILDINFO_ALERT_YES',
        'WEB_GUILDINFO_ALERT_NO',
        'WEB_GUILDINFO_MEMBER_TOOLTIP_CLOSE'
];

$.each(strTextArray, function (index, value) {
    engine.call('ToClient_PAGetString', 'WEB', value);
});

$(document).ready(function () {
    engine.call("ToClient_SetInputMode", false);

    $("#commentArea").focus(function () {
        engine.call("ToClient_SetInputMode", true);
    });

    $("#commentArea").blur(function () {
        engine.call("ToClient_SetInputMode", false);
    });

    $(".layerComment").click(function () {
        guildLayerReset();
        var cmtId = $(this).attr('id');
        $("#" + cmtId).css('display', 'none');
    });

    $(".alertOk").click(function () {
        var thisLayer = $(this).attr('id');
        //$("#"+cmtId).css('display','none');
    });
});

$(document).on('click', '#inputButton', function (e) {
    if ($('#commentArea').val().length <= 0) {
        guildLayerReset();
        alertLayer('.alertLayer', 'alertClose(\'.alertLayer\',\'commentArea\');', $('#WEB_GUILDINFO_FORM_TEXT').attr("title"));
        return false;
    } else if ($('#commentArea').val().length < 2) {
        guildLayerReset();
        alertLayer('.alertLayer', 'alertClose(\'.alertLayer\',\'commentArea\');', $('#WEB_GUILD_COMMENT_LENTH_OVER_TWO').attr("title"));
        return false;
    } else {
        $('#guildCommentsFrm')[0].submit();
    }
});