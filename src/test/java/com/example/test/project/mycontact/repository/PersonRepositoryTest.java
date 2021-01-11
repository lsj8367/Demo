package com.example.test.project.mycontact.repository;

import com.example.test.project.mycontact.domain.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        Person person = new Person();

        person.setName("martin");
        person.setAge(10);
        person.setBloodType("B");
        personRepository.save(person);

        System.out.println(personRepository.findAll());

        List<Person> people = personRepository.findAll();

        Assertions.assertThat(people.size()).isEqualTo(1);
        Assertions.assertThat(people.get(0).getName()).isEqualTo("martin");
        Assertions.assertThat(people.get(0).getAge()).isEqualTo(10);

        Assertions.assertThat(people.get(0).getBloodType()).isEqualTo("B");
    }

    @Test
    void hashCodeAndEquals(){
        Person person1 = new Person("martin", 10, "A");
        Person person2 = new Person("martin", 10, "A");

        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());//주소가 같게 나온다
        System.out.println(person2.hashCode());//주소가 같게 나온다

        Map<Person, Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());

        System.out.println(map);
        System.out.println(map.get(person2)); //person1로 넣었는데 주소가같은 person2로 가져와도 가져올수 있게됨.


    }

}