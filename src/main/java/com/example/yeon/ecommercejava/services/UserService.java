package com.example.yeon.ecommercejava.services;

import com.example.yeon.ecommercejava.controller.UserController;
import com.example.yeon.ecommercejava.dto.LoginDTO;
import com.example.yeon.ecommercejava.dto.UserDTO;
import com.example.yeon.ecommercejava.entity.UserEntity;
import com.example.yeon.ecommercejava.repository.UserRepository;
import com.example.yeon.ecommercejava.security.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    Logger userLogger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public String verifyUser (LoginDTO loginDTO) {
        userLogger.info("Inside UserService verifyUser");
        String password = loginDTO.getPassword();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword()));
        userLogger.info("Authentication returned by AuthenticationManager is " + authentication);
        if (authentication.isAuthenticated()) {
            userLogger.info("verifyUser Successfully");
            return jwtService.generateAccessToken(loginDTO.getUsername());
        }

        return "Failed JWT Authentication" ;
    }

    public Long getUserId(LoginDTO loginDTO){
        try {
            userLogger.info("Inside UserService's getUserID");
            UserEntity userEntity = userRepository.findByUsername(loginDTO.getUsername());
            userLogger.info("After getting UserEntity for getUserID");
            return userEntity.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String signup(UserDTO signUpDTO){
        try {
            UserEntity registeredUser = new UserEntity();
            // Register new User
            registeredUser.setAge(signUpDTO.getAge());
            registeredUser.setRole(signUpDTO.getRole());
            registeredUser.setSex(signUpDTO.getSex());
            registeredUser.setPhoneNumber(signUpDTO.getPhoneNumber());
            registeredUser.setUsername(signUpDTO.getUsername());
            registeredUser.setPassword(passwordEncoder.encode(signUpDTO.getUsername()));
            userRepository.save(registeredUser);
            return "User Signup Successfully";
        }
        catch ( DataIntegrityViolationException e) {
            return "Username already exists";
        }
        catch (Exception e) {
            System.out.println("Exception in UserService Signup is " +  e.getLocalizedMessage());
            return "Something wrong with the input";
        }



    }

    public UserEntity getUserEntity(Long userId) {
        try {
            UserEntity userEntity = userRepository.findById(userId).orElseThrow();
            return userEntity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
