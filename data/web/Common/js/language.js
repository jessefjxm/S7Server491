$(function () {
    if (pearl.common.isEmpty(pearl.common._languageCode)) {
        engine.on('FromClient_GetGameServiceType', function (country) {
            var languages = [
                { 'key': 'PAGST_DEV','languageCode':'ko-KR', 'cultureCode': 'ko', 'countryCode': 'KR', 'help': '개발 버전' }
                , { 'key': 'PAGST_KOR_ALPHA', 'languageCode': 'ko-KR', 'cultureCode': 'ko', 'countryCode': 'KR', 'help': '다음 사내 테스트용' }
                , { 'key': 'PAGST_KOR_REAL', 'languageCode': 'ko-KR', 'cultureCode': 'ko', 'countryCode': 'KR', 'help': '다음 서비스' }
                , { 'key': 'PAGST_KOR_TEST', 'languageCode': 'ko-KR', 'cultureCode': 'ko', 'countryCode': 'KR', 'help': '다음 테스트 서버용?(필요할까?)' }
                , { 'key': 'PAGST_JPN_ALPHA', 'languageCode': 'ja-JP', 'cultureCode': 'ja', 'countryCode': 'JP', 'help': '일본 사내 테스트용' }
                , { 'key': 'PAGST_JPN_REAL', 'languageCode': 'ja-JP', 'cultureCode': 'ja', 'countryCode': 'JP', 'help': '일본 서비스' }
                , { 'key': 'PAGST_RUS_ALPHA', 'languageCode': 'ru-RU', 'cultureCode': 'ru', 'countryCode': 'RU', 'help': '러시아 사내 테스트용' }
                , { 'key': 'PAGST_RUS_REAL', 'languageCode': 'ru-RU', 'cultureCode': 'ru', 'countryCode': 'RU', 'help': '러시아 서비스' }
                , { 'key': 'PAGST_CHI_ALPHA', 'languageCode': 'zh-CN', 'cultureCode': 'zh', 'countryCode': 'CN', 'help': '중국 사내 테스트용' }
                , { 'key': 'PAGST_CHI_REAL', 'languageCode': 'zh-CN', 'cultureCode': 'zh', 'countryCode': 'CN', 'help': '중국 서비스' }
                , { 'key': 'PAGST_NA_ALPHA', 'languageCode': 'en-US', 'cultureCode': 'en', 'countryCode': 'US', 'help': '북미 사내 테스트용' }
                , { 'key': 'PAGST_NA_REAL', 'languageCode': 'en-US', 'cultureCode': 'en', 'countryCode': 'US', 'help': '북미 서비스' }
                , { 'key': 'PAGST_TW_ALPHA', 'languageCode': 'zh-TW', 'cultureCode': 'zh', 'countryCode': 'TW', 'help': '대만' }
                , { 'key': 'PAGST_TW_REAL', 'languageCode': 'zh-TW', 'cultureCode': 'zh', 'countryCode': 'TW', 'help': '대만' }
            ]

            pearl.common._languageCode = languages[country];
        });

        engine.call('ToClient_GetGameServiceType');
    }
});
