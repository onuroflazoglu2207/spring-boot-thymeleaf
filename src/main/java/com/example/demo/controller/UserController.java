package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.WebDto;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl service;

    @GetMapping("/getAll")
    public List<UserModel> getAll() {
        return service.getAll();
    }

    @GetMapping("/getById/{identity}")
    public UserDto getById(@PathVariable Long identity) {
        return service.getById(identity);
    }

    @GetMapping("/getByPage")
    public Page<UserModel> getByValuesPagination(@RequestBody WebDto dto) {
        return service.getByValuesPagination(dto);
    }

    @PostMapping("/save")
    public UserDto save(@RequestBody UserDto dto) {
        return service.save(dto);
    }

    @PutMapping("/update/{identity}")
    public UserDto update(@RequestBody UserDto dto, @PathVariable Long identity) {
        return service.update(dto, identity);
    }

    @DeleteMapping("/delete/{identity}")
    public UserDto delete(@PathVariable Long identity) {
        return service.delete(identity);
    }

}