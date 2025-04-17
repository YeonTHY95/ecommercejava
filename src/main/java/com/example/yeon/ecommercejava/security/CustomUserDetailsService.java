package com.example.yeon.ecommercejava.security;

import com.example.yeon.ecommercejava.entity.UserEntity;
import com.example.yeon.ecommercejava.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    Logger customUserDetailsServiceLogger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        customUserDetailsServiceLogger.info("Inside loadUserByUsername");
        if (userEntity == null) {
            throw new UsernameNotFoundException("User Not Found in Database");
        }
        return new User(
                userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>()
        );
    }
}
