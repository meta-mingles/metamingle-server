package com.mingles.metamingle.interativemovie.command.domain.aggregate.entity;

import com.mingles.metamingle.interativemovie.command.domain.aggregate.vo.MemberNoVO;
import com.mingles.metamingle.interativemovie.command.domain.aggregate.vo.ShortFormNoVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "TBL_INTERACTIVE_MOVIE")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class InteractiveMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interactiveMovieNo;

    @Column
    private String title;

    @Column
    private String url;

    @Column
    private Date date;

    @Column
    private int sequence;

    @Column
    private int interactiveMovieLength;

    @Embedded
    private ShortFormNoVO shortFormNoVO;

    @Embedded
    private MemberNoVO memberNoVO;

    @Builder
    public InteractiveMovie(String title, String url, Date date, int order, int interactiveMovieLength, ShortFormNoVO shortFormNoVO, MemberNoVO memberNoVO) {
        this.title = title;
        this.url = url;
        this.date = date;
        this.sequence = order;
        this.interactiveMovieLength = interactiveMovieLength;
        this.shortFormNoVO = shortFormNoVO;
        this.memberNoVO = memberNoVO;
    }

}
