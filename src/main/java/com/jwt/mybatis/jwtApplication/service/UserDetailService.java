package com.jwt.mybatis.jwtApplication.service;

import com.jwt.mybatis.jwtApplication.entity.UserEntity;
import com.jwt.mybatis.jwtApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findUserByUsername(username);

        if (userData != null) {
            Collection<String> AutorityListOfUser = List.of(userData.getRole().name());
            return new User(username, new BCryptPasswordEncoder().encode(userData.getPassword()), AutorityListOfUser.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        } else {
            throw new UsernameNotFoundException("user not found with username: " + username);
        }

    }

}
