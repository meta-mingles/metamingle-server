package com.mingles.metamingle.scenario.command.domain.service;

import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

@Service
public class ScenarioCommandDomainService {

    public void validateScriptLanguage(String scriptContent) {

        boolean isKorean = Pattern.matches("^[\\sㄱ-ㅎ가-힣a-zA-Z0-9\\p{Punct}]*$", scriptContent);

        if(!isKorean) {
            throw new IllegalArgumentException("한국어/영어가 아닌 언어는 사용이 불가능합니다.");
        }

    }

    public void validateScriptLength(String scriptContent) {

        int length = scriptContent.getBytes(Charset.defaultCharset()).length;

        if(length > 1000) {
            throw new IllegalArgumentException("글자수는 1000자를 넘길 수 없습니다.");
        } else if (length == 0 || scriptContent.trim().isEmpty()) {
            throw new IllegalArgumentException("글자수는 0자 이상이어야 합니다.");
        }

    }

}