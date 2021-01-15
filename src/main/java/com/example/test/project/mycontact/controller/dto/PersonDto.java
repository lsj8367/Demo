package com.example.test.project.mycontact.controller.dto;

import com.example.test.project.mycontact.domain.dto.Birthday;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PersonDto {
    private String name;
    //private int age; // 0으로 초기화되는 int primitive
    private String hobby;
    private String address;
    private LocalDate birthday;
    private String job;
    private String phoneNumber;
}
