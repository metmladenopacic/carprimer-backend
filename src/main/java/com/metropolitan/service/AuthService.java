package com.metropolitan.service;


import com.metropolitan.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
