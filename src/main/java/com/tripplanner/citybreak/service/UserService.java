package com.tripplanner.citybreak.service;

import com.tripplanner.citybreak.dto.LoginRequest;
import com.tripplanner.citybreak.dto.LoginResponse;
import com.tripplanner.citybreak.dto.RegisterRequest;
import com.tripplanner.citybreak.dto.UserResponse;
import com.tripplanner.citybreak.entity.User;
import com.tripplanner.citybreak.exception.ConflictException;
import com.tripplanner.citybreak.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
                () -> new ConflictException("Invalid Email or Password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new ConflictException("Invalid Email or Password");
        }

        return new LoginResponse(user.getEmail(), user.getFullName());
    }

}
