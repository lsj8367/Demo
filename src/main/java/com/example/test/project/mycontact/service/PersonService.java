package com.example.test.project.mycontact.service;

import com.example.test.project.mycontact.controller.dto.PersonDto;
import com.example.test.project.mycontact.domain.Person;
import com.example.test.project.mycontact.domain.dto.Birthday;
import com.example.test.project.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    /*
    public List<Person> getPeopleExcludeBlocks(){ //차단한사람 제외 전체 사람 출력
        List<Person> people = personRepository.findAll();
//        List<Block> blocks = blockRepository.findAll();
//        List<String> blockNames = blocks.stream().map(Block::getName).collect(Collectors.toList()); //Block에 있는 name값만 다 가져옴


        //blockNames에 이름이 포함되어있지 않은것만 골라서 List로 변환함.
        //return people.stream().filter(person -> person.getBlock() == null).collect(Collectors.toList()); //filter는 조건에 맞는것만 return해줌
        return personRepository.findByBlockIsNull();
    }
     */

    public List<Person> getPeopleByName(String name){
//        List<Person> people = personRepository.findAll();

//        return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
        return personRepository.findByName(name);
    }

//    @Transactional 어노테이션
//    transaction begin, commit을 자동 수행해준다.
//    예외를 발생시키면, rollback 처리를 자동 수행해준다.
    @Transactional(readOnly = true) //select문만 있기때문에 readOnly = true
    public Person getPerson(Long id){
//        Person person = personRepository.findById(id).get();

        Person person = personRepository.findById(id).orElse(null); //get을 기본으로 하는데 값이 없다면 null을 리턴

        log.info("person : {}", person);

        return person;
    }

    @Transactional
    public void put(PersonDto personDto){
        Person person = new Person();
        person.set(personDto);
        person.setName(personDto.getName());

        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto){
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        if(!person.getName().equals(personDto.getName())){
            throw new RuntimeException("이름이 다릅니다.");
        }

        person.set(personDto); //여기서 값을 안준것은 그냥 유지할수 있게 해주었다.

        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, String name){
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다"));

        person.setName(name);

        personRepository.save(person);
    }

    @Transactional
    public void delete(Long id){
//        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다"));
//        personRepository.delete(person);
        //personRepository.deleteById(id); //위와 같은 기능
        //바로 삭제하면 문제가 생길수있다.
        
        //그래서 update형식으로 deleted 옵션을 넣어줌
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다"));
        
        person.setDeleted(true);
        
        personRepository.save(person);
    }
}
