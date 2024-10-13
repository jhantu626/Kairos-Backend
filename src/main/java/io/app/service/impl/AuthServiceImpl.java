package io.app.service.impl;

import io.app.dto.RequestLogin;
import io.app.dto.ResponseToken;
import io.app.dto.UserDto;
import io.app.exceptions.ResourceNotFoundException;
import io.app.models.User;
import io.app.repository.UserRepository;
import io.app.service.AuthService;
import io.app.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseToken register(UserDto userDto) {
        log.info("Register Api called at: [{}]",new Date());
        User user=new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser=repository.save(user);
        String token=jwtService.generateToken(savedUser);
        return ResponseToken.builder()
                .status(true)
                .token(token)
                .build();
    }

    @Override
    public ResponseToken login(RequestLogin requestLogin) {
        log.info("Login Api Called at [{}]",new Date());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestLogin.getEmail(),
                        requestLogin.getPassword()
                )
        );
        User user=repository.findByEmail(requestLogin.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("Invalid Credentials!"));
        boolean isPasswordMatch=passwordEncoder.matches(requestLogin.getPassword(),user.getPassword());
        if(!isPasswordMatch){
            throw new ResourceNotFoundException("Invalid Credentials!");
        }
        String jwtToken=jwtService.generateToken(user);
        return null;
    }
}
