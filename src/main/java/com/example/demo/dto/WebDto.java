package com.example.demo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebDto {

    String nameField;
    Integer nameOrder;
    Direction nameBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdayField;
    Integer birthdayOrder;
    Direction birthdayBy;

    String genderField;
    Integer genderOrder;
    Direction genderBy;

    String emailField;
    Integer emailOrder;
    Direction emailBy;

    String phoneField;
    Integer phoneOrder;
    Direction phoneBy;

    String passwordField;
    Integer passwordOrder;
    Direction passwordBy;

    Condition condition;
    Integer page;
    Integer size;
}