package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.WebDto;
import com.example.demo.model.UserModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    public List<UserModel> getAll();

    public UserDto getById(Long identity);

    public Page<UserModel> getByValuesPagination(WebDto dto);

    public UserDto save(UserDto dto);

    public UserDto update(UserDto dto, Long identity);

    public UserDto delete(Long identity);
}
