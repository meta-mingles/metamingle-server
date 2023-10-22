package com.mingles.metamingle.shortform.command.domain.aggregate.entity;

import com.mingles.metamingle.shortform.command.domain.aggregate.vo.MemberNoVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "TBL_SHORT_FORM")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class ShortForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shortFormNo;

    @Column
    private String title;

    @Column
    private String thumbnailUrl;

    @Column
    private String url;

    @Column
    private String description;

    @Column
    private Date date;

    @Column
    private Boolean isInteractive;

    @Embedded
    private MemberNoVO memberNoVO;

    @Builder
    public ShortForm(String title, String thumbnailUrl, String url, String description, Date date,
                     Boolean isInteractive, MemberNoVO memberNoVO) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.url = url;
        this.description = description;
        this.date = date;
        this.isInteractive = isInteractive;
        this.memberNoVO = memberNoVO;
    }

}
