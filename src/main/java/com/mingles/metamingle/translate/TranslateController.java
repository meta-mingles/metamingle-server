package com.mingles.metamingle.translate;


import com.mingles.metamingle.global.infra.AiInfraService;
import com.mingles.metamingle.translate.dto.TranslationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TranslateController {

    private final TranslateDomainService translateDomainService;
    private final AiInfraService aiInfraService;

    @PostMapping(value = "/translate")
    public String translateChat(@RequestBody TranslationRequest request) {

        translateDomainService.checkKorean(request.getText());

        if(translateDomainService.detectSourceLanguageIsEnglish(request.getText())) {
            return aiInfraService.translateTextToKorean(request.getText());
        } else return aiInfraService.translateTextToEnglish(request.getText());

    }
}
