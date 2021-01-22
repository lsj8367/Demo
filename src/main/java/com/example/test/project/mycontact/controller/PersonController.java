package com.example.test.project.mycontact.controller;

import com.example.test.project.mycontact.controller.dto.PersonDto;
import com.example.test.project.mycontact.domain.Person;
import com.example.test.project.mycontact.exception.PersonNotFoundException;
import com.example.test.project.mycontact.exception.RenameIsNotPermittedException;
import com.example.test.project.mycontact.exception.dto.ErrorResponse;
import com.example.test.project.mycontact.repository.PersonRepository;
import com.example.test.project.mycontact.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.util.List;

@RestController //restful api 동작을 하는 controller 명시
@RequestMapping(value = "/api/person")
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public List<Person> getAll(){
        return personService.getAll();
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id){ // ?id=1 이런식이 아니라 /1 이런식으로 호출할수 있게된다.
        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201번 response 신호를 보내줌
    public void postPerson(@RequestBody @Valid PersonDto personDto){
        personService.put(personDto); // person객체 저장
    }

    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto personDto){
        personService.modify(id, personDto);
    }

    @PatchMapping("/{id}") //일부 리소스만 업데이트
    public void modifyPerson(@PathVariable Long id, String name){
        personService.modify(id, name);
    }

    @DeleteMapping("/{id}")
    //public boolean deletePerson(@PathVariable Long id){
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);

        // 방법1.삭제 검증 지워진 내역중 입력받은 id와 같은것이 있는지 확인후 리턴
        //return personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(id));
    }
}
