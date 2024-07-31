package com.sparta.fltpleprojectbackend.trainer.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.fltpleprojectbackend.owner.entity.QOwner;
import com.sparta.fltpleprojectbackend.store.entity.QStore;
import com.sparta.fltpleprojectbackend.trainer.entity.QTrainer;
import com.sparta.fltpleprojectbackend.trainer.entity.Trainer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TrainerRepositoryQueryImpl implements TrainerRepositoryQuery {

  private final JPAQueryFactory queryFactory;


  @Override
  public List<Trainer> findAllTrainersByOwnerId(Long ownerId) {

    QTrainer trainer = QTrainer.trainer;
    QStore store = QStore.store;
    QOwner owner = QOwner.owner;

    return queryFactory.selectFrom(trainer)
      .join(trainer.store, store).fetchJoin()
      .join(store.owner, owner).fetchJoin()
      .where(owner.id.eq(ownerId))
      .fetch();
  }
}