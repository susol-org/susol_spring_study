package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByStudy_StudyId(int studyId); //study내 게시판 가져오기

    // 자신이 소속된 study의 post인지 여부까지 한번에 처리
    Optional<Post> findByPostIdAndStudy_StudyId(int postId, int studyId);
}
