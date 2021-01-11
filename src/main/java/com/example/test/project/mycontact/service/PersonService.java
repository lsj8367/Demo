package com.example.test.project.mycontact.service;

import com.example.test.project.mycontact.domain.Block;
import com.example.test.project.mycontact.domain.Person;
import com.example.test.project.mycontact.repository.BlockRepository;
import com.example.test.project.mycontact.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    BlockRepository blockRepository;

    public List<Person> getPeopleExcludeBlocks(){ //차단한사람 제외 전체 사람 출력
        List<Person> people = personRepository.findAll();
        List<Block> blocks = blockRepository.findAll();
        List<String> blockNames = blocks.stream().map(Block::getName).collect(Collectors.toList()); //Block에 있는 name값만 다 가져옴

        //blockNames에 이름이 포함되어있지 않은것만 골라서 List로 변환함.
        return people.stream().filter(person -> !blockNames.contains(person.getName())).collect(Collectors.toList()); //filter는 조건에 맞는것만 return해줌
    }
}