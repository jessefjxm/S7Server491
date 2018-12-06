$(function () {

    $('.roulette').find('img').hover(function () {
        //console.log($(this).height());
    });
    var p = {
        startCallback: function () {
            $('#speed, #duration').slider('disable');
            $('#stopImageNumber').spinner('disable');
            $('.start').attr('disabled', 'true');
            $('.stop').removeAttr('disabled');
        },
        slowDownCallback: function () {
            $('.stop').attr('disabled', 'true');
        },
        stopCallback: function ($stopElm) {
            $('#speed, #duration').slider('enable');
            $('#stopImageNumber').spinner('enable');
            $('.start').removeAttr('disabled');
            $('.stop').attr('disabled', 'true');
        }

    }
    var rouletter = $('div.roulette');
    rouletter.roulette(p);
    $('.stop').click(function () {
        var stopImageNumber = $('.stopImageNumber').val();
        if (stopImageNumber == "") {
            stopImageNumber = null;
        }
        rouletter.roulette('stop');
    });
    $('.stop').attr('disabled', 'true');
    $('.start').click(function () {
        rouletter.roulette('start');
    });

    $('#stopImageNumber').spinner({
        spin: function (event, ui) {
            var imageNumber = -1;
            if (ui.value > 4) {
                $(this).spinner("value", -1);
                imageNumber = 0;
                updateStopImageNumber(-1);
                return false;
            } else if (ui.value < -1) {
                $(this).spinner("value", 4);
                imageNumber = 4;
                updateStopImageNumber(4);
                return false;
            }
            updateStopImageNumber(imageNumber);
        }
    });
    $('#stopImageNumber').spinner('value', 0);
//	updateStopImageNumber($('#stopImageNumber').spinner('value'));	

    $('.image_sample').children().click(function () {
        var stopImageNumber = $(this).attr('data-value');
        $('#stopImageNumber').spinner('value', stopImageNumber);
        updateStopImageNumber(stopImageNumber);
    });
});

