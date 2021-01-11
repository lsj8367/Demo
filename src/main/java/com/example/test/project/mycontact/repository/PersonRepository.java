package com.example.test.project.mycontact.repository;

import com.example.test.project.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    //JpaRepository를 상속받아줌으로써 orm사용 가능 제네릭엔 entity와 id가 들어온다.
}
