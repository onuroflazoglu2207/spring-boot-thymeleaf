package com.example.demo.dto;

import com.example.demo.model.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;
    Gender gender;
    String email;
    String phone;
    String password;
}
