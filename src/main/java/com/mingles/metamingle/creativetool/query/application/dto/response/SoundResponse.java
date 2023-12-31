package com.mingles.metamingle.creativetool.query.application.dto.response;

import com.mingles.metamingle.creativetool.document.Image;
import com.mingles.metamingle.creativetool.document.Sound;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SoundResponse {

    private String title;
    private String soundUrl;

    public SoundResponse(Sound sound) {
        this.title = sound.getPrompt();
        this.soundUrl = sound.getSoundUrl();
    }
}
