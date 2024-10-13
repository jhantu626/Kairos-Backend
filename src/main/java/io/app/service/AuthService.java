package io.app.service;


import io.app.dto.RequestLogin;
import io.app.dto.ResponseToken;
import io.app.dto.UserDto;

public interface AuthService {
    public ResponseToken register(UserDto userDto);
    public ResponseToken login(RequestLogin requestLogin);
}
