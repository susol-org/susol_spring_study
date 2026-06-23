package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.UserRepository;
import com.susol.susolstudy.model.dto.FindIdRequestDTO;
import com.susol.susolstudy.model.dto.SignUpRequestDTO;
import com.susol.susolstudy.model.entity.Permission;
import com.susol.susolstudy.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 아이디 중복체크
    public boolean duplicateCheckEmailId(String userEmailId) {
        return userRepository.existsByUserEmailId(userEmailId);
    }

    // 회원가입
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

    // 아이디 찾기
    public String findId(FindIdRequestDTO findIdRequestDTO) {
        Optional<User> findUser = userRepository.findByUserNameAndUserAgeAndUserGender(
            findIdRequestDTO.getUserName(),
            findIdRequestDTO.getUserAge(),
            findIdRequestDTO.getUserGender()
        );

        return findUser.map(User::getUserEmailId).orElse(null);
    }

    public String checkValidateEmailId(String userEmailId) {
        Optional<User> findUser = userRepository.findByUserEmailId(userEmailId);
        return findUser.map(User::getUserEmailId).orElse(null);
    }

    @Transactional
    public void updatePassword(String userEmailId, String password) {
        User user = userRepository.findByUserEmailId(userEmailId)
                            .orElseThrow(() -> new AccessDeniedException("잘못된 접근 방식입니다."));

        String encPassword = passwordEncoder.encode(password);
        user.setUserPassword(encPassword);
    }
}
