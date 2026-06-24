package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Integer> {
    List<Study> findAllByDeletedAtIsNull();
}


