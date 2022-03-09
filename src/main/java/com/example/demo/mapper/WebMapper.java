package com.example.demo.mapper;

import com.example.demo.dto.WebDto;
import com.example.demo.model.UserModel;
import org.mapstruct.*;

import java.util.List;

@Mapper
public interface WebMapper {

    @Mappings({@Mapping(source = "name", target = "nameField"),
            @Mapping(source = "birthday", target = "birthdayField"),
            @Mapping(source = "gender", target = "genderField"),
            @Mapping(source = "email", target = "emailField"),
            @Mapping(source = "phone", target = "phoneField"),
            @Mapping(source = "password", target = "passwordField")})
    WebDto toWebDto(UserModel user);

    List<WebDto> toWebDtos(List<UserModel> users);

    @Mappings({@Mapping(target = "identity", ignore = true),
            @Mapping(source = "nameField", target = "name"),
            @Mapping(source = "birthdayField", target = "birthday"),
            @Mapping(source = "genderField", target = "gender"),
            @Mapping(source = "emailField", target = "email"),
            @Mapping(source = "phoneField", target = "phone"),
            @Mapping(source = "passwordField", target = "password")})
    UserModel toUserModel(WebDto dto);

    List<UserModel> toUserModels(List<WebDto> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WebDto getDto(WebDto oldDto, @MappingTarget WebDto newDto);
}
