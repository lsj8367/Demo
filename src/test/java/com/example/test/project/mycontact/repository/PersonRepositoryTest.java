package com.example.test.project.mycontact.repository;

import com.example.test.project.mycontact.domain.Person;
import com.example.test.project.mycontact.domain.dto.Birthday;
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

    @Test
    void findByBloodType(){
        givenPerson("martin", 10, "A");
        givenPerson("david", 9, "B");
        givenPerson("dennis", 8, "O");
        givenPerson("sophia", 7, "AB");
        givenPerson("benny", 6, "A");
        givenPerson("John", 5, "A");

        List<Person> result = personRepository.findByBloodType("A");

        result.forEach(System.out::println);

    }

    @Test
    void findByBirthDayBetween(){
        givenPerson("martin", 10, "A", LocalDate.of(1991, 8, 15));
        givenPerson("david", 9, "B", LocalDate.of(1992, 7, 10));
        givenPerson("dennis", 8, "O", LocalDate.of(1993, 1, 5));
        givenPerson("sophia", 7, "AB", LocalDate.of(1994, 6, 30));
        givenPerson("benny", 6, "A", LocalDate.of(1995, 8, 30));

        List<Person> result = personRepository.findByMonthOfBirthday(8); //8월 생일자 추출

        result.forEach(System.out::println);
    }

    private void givenPerson(String name, int age, String bloodType){
        givenPerson(name, age, bloodType, null);
    }

    private void givenPerson(String name, int age, String bloodType, LocalDate birthday){
        Person person = new Person(name, age, bloodType);
        person.setBirthday(new Birthday(birthday));
        personRepository.save(person);
    }

}