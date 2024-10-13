package io.app.controllers;

import io.app.dto.RequestLogin;
import io.app.dto.ResponseToken;
import io.app.dto.UserDto;
import io.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<ResponseToken> register(@RequestBody UserDto userDto){
        log.info("Login Api : [{}]",new Date());
        return new ResponseEntity<>(authService.register(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseToken> register(@RequestBody RequestLogin requestLogin){
        return new ResponseEntity<>(authService.login(requestLogin), HttpStatus.OK);
    }



}
