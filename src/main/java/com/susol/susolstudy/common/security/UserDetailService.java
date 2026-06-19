package com.susol.susolstudy.common.security;

import com.susol.susolstudy.dao.UserRepository;
import com.susol.susolstudy.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User seaarchUser = userRepository.findByUserEmailId(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));

        return new UserDetail(seaarchUser);
    }
}
