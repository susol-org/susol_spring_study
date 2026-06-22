package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostFileRepository extends JpaRepository<PostFile, Integer> {
    Optional<PostFile> findByPostRenamedFileName(String renamedFileName);
}
