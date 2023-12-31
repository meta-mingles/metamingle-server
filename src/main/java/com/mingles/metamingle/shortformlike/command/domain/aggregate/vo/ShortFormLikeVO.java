package com.mingles.metamingle.shortformlike.command.domain.aggregate.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortFormLikeVO implements Serializable {

    @Column
    private Long memberNo;

    @Column
    private Long shortFormNo;

    public ShortFormLikeVO(Long memberNo, Long shortFormNo) {
        this.memberNo = memberNo;
        this.shortFormNo = shortFormNo;
    }
}
