package com.example.test.project.mycontact.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
//@Getter
//@Setter
//@ToString //(exclude = "phoneNumber") exclude안에 변수를 넣어주면 해당 변수는 제외
@NoArgsConstructor // 기본 생성자 선언
@AllArgsConstructor //전체 변수를 포함한 생성자 생성// RequiredArgsConstruct는 특정변수만 포함하여 생성 가능.
@RequiredArgsConstructor
//@EqualsAndHashCode // 기존의 것과 새로 생성된 객체의 주소를 맞춰줌
@Data
public class Person {

    @Id
    @GeneratedValue //자동생성
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int age;

    private String hobby;

    @NonNull
    private String bloodType;

    private String address;

    private LocalDate birthday;

    private String job;

    @ToString.Exclude //상단과 같은 뜻
    private String phoneNumber;

    /*
    public boolean equals(Object object){
        if(object == null){
            return false;
        }

        Person person = (Person)object;

        if(!person.getName().equals(this.getName())){
            return false;
        }

        if(person.getAge() != this.getAge()){
            return false;
        }
        return true;
    }

    public int hashCode(){
        return(name + age).hashCode();
    }
     */

    private boolean block; //차단

    private String blockReason; //차단이유

    private LocalDate blockStartDate;

    private LocalDate blockEndDate;
}
