function confirmLayer(id,elementId,functionName,msg){ // 전달 인자값, 엘레먼트ID, 클릭시 실행할 함수명, 보여줄 메세지
	$(elementId).html('');
	$(elementId).css('display','block');
	$(elementId).append('<div class="confirmMsg">'+msg+'</div>');
	$(elementId).append('<div class="confirmYes" onclick="' + functionName + '(' + id + ')">' + $('#WEB_GUILDINFO_ALERT_YES').attr("title") + '</div>');
	$(elementId).append('<div class="confirmNo" onclick="' + functionName + '(0)">' + $('#WEB_GUILDINFO_ALERT_NO').attr("title") + '</div>');
}

function alertLayer(elementId,functionName,msg){ // 전달 인자값, 엘레먼트ID, 클릭시 실행할 함수명, 보여줄 메세지
	$(elementId).html('');
	$(elementId).css('display','block');
	$(elementId).append('<div class="alertLayerWrap"><div class="alertMsg">' + msg + '</div><div class="alertOk" onclick="' + functionName + '">' + $('#WEB_GUILDINFO_MEMBER_TOOLTIP_CLOSE').attr("title") + '</div></div>');
}

function alertClose(thisLayer, frmIdName){
	$(thisLayer).css('display','none');
	$('#'+frmIdName).focus();
}

function boardgameAlertClose( thisLayer ) {
	$(thisLayer).css('display','none');
}

var getNation = pearl.common.getLanguageCode().countryCode;
