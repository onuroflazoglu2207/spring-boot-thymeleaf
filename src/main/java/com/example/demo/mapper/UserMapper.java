package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.model.UserModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDto toUserDto(UserModel user);

    List<UserDto> toUserDtos(List<UserModel> users);

    UserModel toUserModel(UserDto dto);

    List<UserModel> toUserModels(List<UserDto> dtos);
}
