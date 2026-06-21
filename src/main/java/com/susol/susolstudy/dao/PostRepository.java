package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByStudy_StudyId(int studyId);
}
