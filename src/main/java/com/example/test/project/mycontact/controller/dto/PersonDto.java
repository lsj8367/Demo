package com.example.test.project.mycontact.controller.dto;

import com.example.test.project.mycontact.domain.dto.Birthday;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PersonDto {
    @NotBlank(message = "이름은 필수값입니다") // 오류에 대한 메세지 empty는 공백이어도 정상을 나타냄
    private String name;
    //private int age; // 0으로 초기화되는 int primitive
    private String hobby;
    private String address;
    private LocalDate birthday;
    private String job;
    private String phoneNumber;
}
