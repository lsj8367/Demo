package com.example.test.project.mycontact.service;

import com.example.test.project.mycontact.controller.dto.PersonDto;
import com.example.test.project.mycontact.domain.Person;
import com.example.test.project.mycontact.domain.dto.Birthday;
import com.example.test.project.mycontact.exception.PersonNotFoundException;
import com.example.test.project.mycontact.exception.RenameIsNotPermittedException;
import com.example.test.project.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Test
    void getAll(){
        when(personRepository.findAll())
                .thenReturn(Lists.newArrayList(new Person("martin"), new Person("dennis"), new Person("tony")));

        List<Person> result = personService.getAll();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("dennis");
        assertThat(result.get(2).getName()).isEqualTo("tony");
    }

    @Test
    void getPeopleByName(){
        when(personRepository.findByName("martin")) //만약 있다면 가정..
                .thenReturn(Lists.newArrayList(new Person("martin"))); //return 해줌

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }

    @Test
    void getPerson(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("martin");
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(1L)).thenReturn(Optional.empty()); //null인 경우 << optional로 매핑이 되어있음.

        Person person = personService.getPerson(1L);

        assertThat(person).isNull();
    }

    @Test
    void put(){
        //mock에 대한 것을 지정해주지 않음 return값이나 exception이 지정되어있지 않으면 문제가 되지않음.
        personService.put(mockPersonDto());

        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeInserted())); //personrepository에 대해 체크 + 실행이 되었는지, Person클래스에 대한 값이 들어왔는지 확인
        //save 가 1번 실행이 되었는지 확인
    }

    @Test
    void modifyPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty()); //없는경우

        assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));

    }

    @Test
    void modifyNameIsDifferent(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("tony")));

        assertThrows(RenameIsNotPermittedException.class, () -> personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modify(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L, mockPersonDto());

        //verify(personRepository, times(1)).save(any(Person.class));
        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeUpdated()));
    }
    @Test
    void modifyByNameIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.modify(1L, "daniel"));
    }

    @Test
    void modifyByName(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L, "daniel");

        verify(personRepository, times(1)).save(argThat(new IsNameWillBeUpdated()));
    }

    @Test
    void deleteIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.delete(1L));
    }

    @Test
    void delete(){
        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("martin")));

        personService.delete(1L);

        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeDeleted()));
    }

    private PersonDto mockPersonDto(){
        return PersonDto.of("martin", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");
    }

    private static class IsPersonWillBeInserted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(), "martin")
                    && equals(person.getHobby(),"programming")
                    && equals(person.getAddress(), "판교")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(), "programmer")
                    && equals(person.getPhoneNumber(), "010-1111-2222");
        }

        private boolean equals(Object actual, Object expected){
            return expected.equals(actual);
        }
    }

    private static class IsPersonWillBeUpdated implements ArgumentMatcher<Person> {
        @Override
        public boolean matches(Person person) {
            return equals(person.getName(), "martin")
                    && equals(person.getHobby(),"programming")
                    && equals(person.getAddress(), "판교")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(), "programmer")
                    && equals(person.getPhoneNumber(), "010-1111-2222");
        }
        private boolean equals(Object actual, Object expected){
            return expected.equals(actual);
        }
    }

    private static class IsNameWillBeUpdated implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.getName().equals("daniel");
        }
    }

    private static class IsPersonWillBeDeleted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.isDeleted();
        }
    }
}