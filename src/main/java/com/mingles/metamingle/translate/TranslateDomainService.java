package com.mingles.metamingle.translate;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TranslateDomainService {

    //자음/모음만 있을 경우 번역 x
    public void checkKorean(String text) {

        boolean notWord = Pattern.matches(".*[가-힣]+.*", text);

        if(!notWord) {
            throw new IllegalArgumentException("자음/모음만 있는 경우 번역이 불가능합니다.");
        }
    }

    public boolean detectSourceLanguageIsEnglish(String text) {
        String regex = "^[a-zA-Z\\s\\p{P}]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        System.out.println("matcher.matches() = " + matcher.matches());
        return matcher.matches();
    }
}
