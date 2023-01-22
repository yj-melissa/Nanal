package com.dbd.nanal.service;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserRoleEntity;
import com.dbd.nanal.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Optional<UserEntity> loginUser = this.userRepository.findByUserId(loginId);
        System.out.println(loginUser);
        if (loginUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없음");
        }
        UserEntity user = loginUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(loginId)) {
            authorities.add(new SimpleGrantedAuthority(UserRoleEntity.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRoleEntity.USER.getValue()));
        }
        return new User(user.getUserId(), user.getUserPassword(), authorities);
    }

}
