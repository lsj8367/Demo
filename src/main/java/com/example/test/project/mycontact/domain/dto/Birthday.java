package com.example.test.project.mycontact.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Embeddable //entity에 속해있는 dto를 명시
@NoArgsConstructor
@Data
public class Birthday {
    private Integer yearOfBirthday;

    @Min(1) //최소값
    @Max(12) //최대값
    private Integer monthOfBirthday; // int는 null허용 x

    @Min(1)
    @Max(31)
    private Integer dayOfBirthday;

    public Birthday(LocalDate birthday){
        this.yearOfBirthday = birthday.getYear();
        this.monthOfBirthday = birthday.getMonthValue();
        this.dayOfBirthday = birthday.getDayOfMonth();
    }
}
