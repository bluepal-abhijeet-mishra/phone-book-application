package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserRequestDto;

public interface UserService {
    UserDto registerUser(UserRequestDto userRequestDto);
}
