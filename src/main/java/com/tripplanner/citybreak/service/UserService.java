package com.tripplanner.citybreak.service;

import com.tripplanner.citybreak.dto.LoginRequest;
import com.tripplanner.citybreak.dto.LoginResponse;
import com.tripplanner.citybreak.dto.RegisterRequest;
import com.tripplanner.citybreak.dto.UserResponse;
import com.tripplanner.citybreak.entity.User;
import com.tripplanner.citybreak.exception.AuthenticationFailedException;
import com.tripplanner.citybreak.exception.ConflictException;
import com.tripplanner.citybreak.repository.UserRepository;
import com.tripplanner.citybreak.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    public UserResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new ConflictException("Email Already Registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());

        User saved = userRepository.save(user);

        return new UserResponse(saved.getId(),saved.getEmail(),saved.getFullName());
    }

    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new AuthenticationFailedException("Invalid Email or Password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new AuthenticationFailedException("Invalid Email or Password");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        return new LoginResponse(user.getEmail(), user.getFullName(),token);
    }

}
