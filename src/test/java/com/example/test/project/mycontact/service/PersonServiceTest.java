package com.example.test.project.mycontact.service;

import com.example.test.project.mycontact.domain.Block;
import com.example.test.project.mycontact.domain.Person;
import com.example.test.project.mycontact.repository.BlockRepository;
import com.example.test.project.mycontact.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Test
    void getPeopleExcludeBlocks(){
        givenPeople();
        //givenBlocks();

        List<Person> result = personService.getPeopleExcludeBlocks();

//        System.out.println(result);
        result.forEach(System.out::println);
    }

    @Test
    void getPeopleByName(){
        givenPeople();

        List<Person> result = personService.getPeopleByName("martin"); //martin이라는 이름을 가진 데이터 가져옴

        result.forEach(System.out::println);
    }


    @Test
    void cascadeTest(){
        givenPeople();

        List<Person> result = personRepository.findAll();

        result.forEach(System.out::println);

        Person person = result.get(3);
        person.getBlock().setStartDate(LocalDate.now());
        person.getBlock().setEndDate(LocalDate.now());

        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);

//        personRepository.delete(person);
//        personRepository.findAll().forEach(System.out::println);
//        blockRepository.findAll().forEach(System.out::println);

        person.setBlock(null); //4번째 block을 null로 설정
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println); //여기는 null로 설정되었지만
        blockRepository.findAll().forEach(System.out::println); //여기에서는 값이 남아있다.(orphanremoval = true) 설정해주면 같이 지워진다.
    }

    @Test
    void getPerson(){
        givenPeople();

        Person person = personService.getPerson(3L);

        System.out.println(person);
    }


//    private void givenBlocks() {
//        givenBlock("martin");
//    }
//
//    private Block givenBlock(String name) {
//        return blockRepository.save(new Block(name));
//    }

    private void givenPeople() {
        givenPerson("martin", 10, "A");
        givenPerson("david", 9, "B");
        givenBlockPerson("dennis", 7, "O");
        givenBlockPerson("martin", 11, "AB");
    }

    private void givenBlockPerson(String name, int age, String bloodType){
        Person blockPerson = new Person(name, age, bloodType); // blockperson을 생성
        //blockPerson.setBlock(givenBlock(name)); //이름값만 들고가서 block객체 생성하여 저장
        blockPerson.setBlock(new Block(name));

        personRepository.save(blockPerson); // blockperson person데이터에 저장
    }

    private void givenPerson(String name, int age, String bloodType) {
        personRepository.save(new Person(name, age, bloodType)); //들어온 그대로 person데이터 저장
    }

}