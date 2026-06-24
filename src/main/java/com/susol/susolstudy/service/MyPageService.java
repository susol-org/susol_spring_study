package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.UserRepository;
import com.susol.susolstudy.model.dto.UserResponseDTO;
import com.susol.susolstudy.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final UserRepository userRepository;

    public UserResponseDTO getUserInfo(String userEmailId) {
        User searchUser = userRepository.findByUserEmailId(userEmailId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));

        return UserResponseDTO.entityOf(searchUser);
    }
}
