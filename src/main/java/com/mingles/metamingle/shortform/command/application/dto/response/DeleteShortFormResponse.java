package com.mingles.metamingle.shortform.command.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteShortFormResponse {
    private Long shortFormNo;

    public DeleteShortFormResponse(Long shortFormNo) {
        this.shortFormNo = shortFormNo;
    }
}
