package com.mingles.metamingle.shortform.query.domain.repository;

import com.mingles.metamingle.shortform.command.domain.aggregate.entity.ShortForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShortFormQueryRepository extends JpaRepository<ShortForm, Long> {

    List<ShortForm> findAllByOrderByShortFormNoDesc();

    ShortForm findShortFormByShortFormNo(Long shortFormNo);

    List<ShortForm> findByShortFormNoIn(List<Long> shortFormNos);

}
