package com.example.test.project.mycontact.repository;

import com.example.test.project.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    //JpaRepository를 상속받아줌으로써 orm사용 가능 제네릭엔 entity와 id가 들어온다.
    List<Person> findByName(String name);

    List<Person> findByBlockIsNull(); //차단이 되지 않은경우의 사람들

    List<Person> findByBloodType(String bloodType); //여기를 단일대상으로 하게되면 2개 이상은 에러를 발생시킴
    
    List<Person> findByBirthdayBetween(LocalDate startDate, LocalDate endDate); // 날짜 두개로 생일구하기
}
