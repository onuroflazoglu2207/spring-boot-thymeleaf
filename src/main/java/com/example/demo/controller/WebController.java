package com.example.demo.controller;

import com.example.demo.dto.Condition;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.WebDto;
import com.example.demo.mapper.WebMapper;
import com.example.demo.model.Gender;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class WebController {

    public static final String defaultGender = "NULL";
    public static final Direction defaultDirection = Direction.ASC;
    public static final Condition defaultCondition = Condition.AND;
    public static final Integer startSize = 3, finishSize = 9;
    public static final Integer defaultPage = 0;

    private final UserController controller;
    private final WebMapper webMapper;

    private WebDto getAllDefaultDto = setDefaultWebDtoOrder(WebDto.builder().nameBy(defaultDirection).
            birthdayBy(defaultDirection).genderBy(defaultDirection).emailBy(defaultDirection).phoneBy(defaultDirection).
            passwordBy(defaultDirection).condition(defaultCondition).size(startSize).page(defaultPage).build());
    public UserDto saveDefaultDto = UserDto.builder().build();

    @GetMapping("/web/v1/users/getAll")
    public ModelAndView getAll(@ModelAttribute WebDto dto) {
        ModelAndView modelAndView = new ModelAndView("getAll");
        getAllDefaultDto = webMapper.getDto(checkFieldBlank(dto), getAllDefaultDto);
        if (checkOrderDuplicate(getAllDefaultDto)) getAllDefaultDto = setDefaultWebDtoOrder(getAllDefaultDto);
        Page<UserModel> page = controller.getByValuesPagination(getAllDefaultDto);
        modelAndView.addObject("pageDto", page);
        modelAndView.addObject("listDto", page.getContent());
        modelAndView.addObject("webDto", getAllDefaultDto);
        return modelAndView;
    }

    public static WebDto checkFieldBlank(WebDto dto) {
        if (dto.getNameField() != null && dto.getNameField().isBlank()) dto.setNameField(null);
        if (dto.getGenderField() != null && dto.getGenderField().equals(defaultGender)) dto.setGenderField(null);
        if (dto.getEmailField() != null && dto.getEmailField().isBlank()) dto.setEmailField(null);
        if (dto.getPhoneField() != null && dto.getPhoneField().isBlank()) dto.setPhoneField(null);
        if (dto.getPasswordField() != null && dto.getPasswordField().isBlank()) dto.setPasswordField(null);
        return dto;
    }

    public WebDto setDefaultWebDtoOrder(WebDto dto) {
        int i = 0;
        return dto.withNameOrder(i++).withBirthdayOrder(i++).withGenderOrder(i++).
                withEmailOrder(i++).withPhoneOrder(i++).withPasswordOrder(i++);
    }

    public boolean checkOrderDuplicate(WebDto dto) {
        int[] dtoOrder = UserServiceImpl.getOrders(dto);
        Arrays.sort(dtoOrder);
        return !Arrays.equals(dtoOrder, userDtoFieldCountArray());
    }

    public static int[] sizeCountArray() {
        return IntStream.range(startSize, finishSize).map(i -> (i + 1)).toArray();
    }

    public static String[] userDtoField() {
        return Arrays.stream(UserDto.class.getDeclaredFields()).map(Field::getName).toArray(String[]::new);
    }

    public static int[] userDtoFieldCountArray() {
        return IntStream.range(0, userDtoFieldCount()).map(i -> i).toArray();
    }

    public static int userDtoFieldCount() {
        return UserDto.class.getDeclaredFields().length;
    }

    public static Direction[] DirectionField() {
        return Direction.values();
    }

    public static Condition[] conditionField() {
        return Condition.values();
    }

    public static Gender[] genderFieldNoNull() {
        return Gender.values();
    }

    public static String[] genderFieldWithNull() {
        return new String[]{defaultGender, Gender.MALE.name(), Gender.FEMALE.name()};
    }


    @GetMapping("/web/v1/users/save")
    public ModelAndView save(@RequestParam(required = false) String message) {
        ModelAndView modelAndView = new ModelAndView("save");
        modelAndView.addObject("dto", saveDefaultDto);
        if (message != null) modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping("/web/v1/users/saver")
    public RedirectView saver(@ModelAttribute UserDto dto, RedirectAttributes att) {
        saveDefaultDto = dto;
        att.addAttribute("message", "User is" + (controller.save(dto) == null ? " not" : "") + " added!");
        return new RedirectView("/web/v1/users/save");
    }

    @GetMapping("/web/v1/users/updateOrDelete/{identity}")
    public ModelAndView updateOrDelete(@PathVariable Long identity, @RequestParam(required = false) String message) {
        ModelAndView modelAndView = new ModelAndView("updateOrDelete");
        modelAndView.addObject("identity", identity);
        UserDto dto = controller.getById(identity);
        modelAndView.addObject("dto", dto);
        modelAndView.addObject("isHave", dto != null);
        if (message != null) modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping("/web/v1/users/updateOrDeleter/{identity}")
    public RedirectView updateOrDeleter(@PathVariable Long identity, @RequestParam String submit,
                                        @ModelAttribute UserDto dto, RedirectAttributes att) {
        String message = null;
        if (submit.equals("Delete"))
            message = "User is" + (controller.delete(identity) == null ? " not" : "") + " deleted!";
        else if (submit.equals("Update"))
            message = "User is" + (controller.update(dto, identity) == null ? " not" : "") + " updated!";
        if (message != null) att.addAttribute("message", message);
        return new RedirectView("/web/v1/users/updateOrDelete/" + identity);
    }

}

