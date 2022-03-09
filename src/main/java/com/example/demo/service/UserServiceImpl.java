package com.example.demo.service;

import com.example.demo.dto.Condition;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.WebDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.WebMapper;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final WebMapper webMapper;

    @Override
    public List<UserModel> getAll() {
        return repository.findAll();
    }

    @Override
    public UserDto getById(Long identity) {
        Optional<UserModel> optional = repository.findById(identity);
        return optional.isEmpty() ? null : userMapper.toUserDto(optional.get());
    }

    @Override
    public Page<UserModel> getByValuesPagination(WebDto dto) {
        UserModel model = webMapper.toUserModel(dto);
        Sort sort = getSort(dto);
        if (allFieldsNull(dto))
            return repository.findAll(PageRequest.of(dto.getPage(), dto.getSize(), sort));
        if (dto.getCondition() == Condition.AND)
            return repository.getByValuesAndPagination(model, PageRequest.of(dto.getPage(), dto.getSize(), sort));
        else
            return repository.getByValuesOrPagination(model, PageRequest.of(dto.getPage(), dto.getSize(), sort));
    }

    public Sort getSort(WebDto dto) {
        int[] dtoOrder = getOrders(dto);
        Direction[] dtoBy = getBys(dto);
        Field[] dtoNames = UserDto.class.getDeclaredFields();
        int[] newOrder = new int[dtoNames.length];
        for (int i = 0; i < dtoOrder.length; i++)
            newOrder[dtoOrder[i]] = i;
        List<Order> orderList = new ArrayList<Order>();
        for (int i = 0; i < dtoOrder.length; i++)
            orderList.add(new Order(dtoBy[newOrder[i]], dtoNames[newOrder[i]].getName()));
        return Sort.by(orderList);
    }

    public static int[] getOrders(WebDto dto) {
        return new int[]{dto.getNameOrder(), dto.getBirthdayOrder(), dto.getGenderOrder(),
                dto.getEmailOrder(), dto.getPhoneOrder(), dto.getPasswordOrder()};
    }

    public static Direction[] getBys(WebDto dto) {
        return new Direction[]{dto.getNameBy(), dto.getBirthdayBy(), dto.getGenderBy(),
                dto.getEmailBy(), dto.getPhoneBy(), dto.getPasswordBy()};
    }

    public static boolean allFieldsNull(WebDto dto) {
        return dto.getNameField() == null && dto.getBirthdayField() == null && dto.getGenderField() == null &&
                dto.getEmailField() == null && dto.getPhoneField() == null && dto.getPasswordField() == null;
    }

    /*public Page<UserDto> pageMapper(Page<UserModel> page) {
        return page.map(new Function<UserModel, UserDto>() {
            public UserDto apply(UserModel userModel) {
                return userMapper.toUserDto(userModel);
            }
        });
    }*/

    @Override
    public UserDto save(UserDto dto) {
        try {
            UserModel model = userMapper.toUserModel(dto);
            model = repository.save(model);
            return userMapper.toUserDto(model);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDto update(UserDto dto, Long identity) {
        try {
            Optional<UserModel> optional = repository.findById(identity);
            UserModel model = optional.isEmpty() ? null : optional.get();
            if (model == null) return null;
            UserModel temp = userMapper.toUserModel(dto);
            temp.setIdentity(identity);
            repository.save(temp);
            return userMapper.toUserDto(model);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDto delete(Long identity) {
        Optional<UserModel> optional = repository.findById(identity);
        UserModel model = optional.isEmpty() ? null : optional.get();
        if (model != null) {
            repository.deleteById(identity);
            return userMapper.toUserDto(model);
        } else return null;
    }

}
