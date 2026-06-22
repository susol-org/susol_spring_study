package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.Post;
import com.susol.susolstudy.model.entity.PostReadLog;
import com.susol.susolstudy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PostReadLogRepository extends JpaRepository<PostReadLog, Integer> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM PostReadLog p " +
            "WHERE p.user = :user AND p.post = :post " +
            "AND CAST(p.postReadLogDate AS DATE) = :date")
    boolean existsReadLog(@Param("user") User user,
                          @Param("post") Post post,
                          @Param("date") LocalDate now);
}
