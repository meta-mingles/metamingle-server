package com.mingles.metamingle.script.command.application.dto.response;

import com.mingles.metamingle.script.command.domain.aggregate.entity.Script;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ScriptCommandResponse {

    Long scriptNo;
    Long memberNo;
    Long shortFormNo;
    LocalDate uploadDate;

    public static ScriptCommandResponse from(Script script) {
        return new ScriptCommandResponse(
                script.getScriptNo(),
                script.getScriptMemberNoVO().getMemberNo(),
                script.getShortFormNoVO().getShortFormNo(),
                script.getUploadDate()
        );
    }

}