package com.example.test.project.mycontact.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString //(exclude = "phoneNumber") exclude안에 변수를 넣어주면 해당 변수는 제외
public class Person {

    @Id
    @GeneratedValue //자동생성
    private Long id;

    private String name;

    private int age;

    private String hobby;

    private String bloodType;

    private String address;

    private LocalDate birthday;

    private String job;

    //@ToString.Exclude //상단과 같은 뜻
    private String phoneNumber;
}
