package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.UserRepository;
import com.susol.susolstudy.model.dto.SignUpRequestDTO;
import com.susol.susolstudy.model.entity.Permission;
import com.susol.susolstudy.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public boolean duplicateCheckEmailId(String userEmailId) {
        return userRepository.existsByUserEmailId(userEmailId);
    }

    public void signUp(SignUpRequestDTO signUpDTO) {
        String password = passwordEncoder.encode(signUpDTO.getUserPassword());
        User signUpUser = User.builder()
                            .userEmailId(signUpDTO.getUserEmailId())
                            .userPassword(password)
                            .userName(signUpDTO.getUserName())
                            .userAge(signUpDTO.getUserAge())
                            .userGender(signUpDTO.getGender())
                            .userShortBio(signUpDTO.getUserShortBio())
                            .userPermission(Permission.USER)
                            .build();

        userRepository.save(signUpUser);
    }
}
