package com.mingles.metamingle.quiz.query.domain.repository;

import com.mingles.metamingle.quiz.command.domain.aggregate.entity.QuizRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankQueryRepository extends JpaRepository<QuizRank, Integer> {
}
