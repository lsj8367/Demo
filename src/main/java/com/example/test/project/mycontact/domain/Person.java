package com.example.test.project.mycontact.domain;

import com.example.test.project.mycontact.controller.dto.PersonDto;
import com.example.test.project.mycontact.domain.dto.Birthday;
import lombok.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동생성
    private Long id;

    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    @Min(1)
    private Integer age;

    private String hobby;

    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String bloodType;

    private String address;

    @Embedded
    @Valid
    private Birthday birthday; //BirthDay 클래스 참조

    private String job;

    @ToString.Exclude //상단과 같은 뜻
    private String phoneNumber;

    //person에 대해 동작을 수행해도 영속성 결합으로 같이 변경 삭제 추가등이 된다.  optional = false는 inner join 특성을 갖고있다.
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) //{CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    @ToString.Exclude
    private Block block;

    public void set(PersonDto personDto){ //데이터 수정작업 하지 않은것은 유지하게 만드는 메소드
        if (personDto.getAge() != 0){
            this.setAge(personDto.getAge());
        }

        if(!ObjectUtils.isEmpty(personDto.getHobby())){
            this.setHobby(personDto.getHobby());
        }

        if(!ObjectUtils.isEmpty(personDto.getBloodType())){
            this.setBloodType(personDto.getBloodType());
        }

        if(!ObjectUtils.isEmpty(personDto.getAddress())){
            this.setAddress(personDto.getAddress());
        }

        if(!ObjectUtils.isEmpty(personDto.getJob())){
            this.setJob(personDto.getJob());
        }

        if(!ObjectUtils.isEmpty(personDto.getPhoneNumber())){
            this.setPhoneNumber(personDto.getPhoneNumber());
        }
    }
}
