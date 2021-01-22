package com.example.test.project.mycontact.controller;

import com.example.test.project.mycontact.configuration.serializer.BirthdaySerializer;
import com.example.test.project.mycontact.controller.dto.PersonDto;
import com.example.test.project.mycontact.domain.Person;
import com.example.test.project.mycontact.domain.dto.Birthday;
import com.example.test.project.mycontact.exception.handler.GlobalExceptionHandler;
import com.example.test.project.mycontact.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@Transactional
public class PersonControllerTest {

//    @Autowired
//    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private MappingJackson2HttpMessageConverter messageConverter;
//
//    @Autowired
//    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach //매 테스트마다 기본적으로 먼저 실행됨
    void beforeEach(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void getAll() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(6))) // $는 리스트 객체여서 총갯수 7개
                .andExpect(jsonPath("$.[0].name").value("martin"))
                .andExpect(jsonPath("$.[1].name").value("david"))
                .andExpect(jsonPath("$.[2].name").value("dennis"))
                .andExpect(jsonPath("$.[3].name").value("sophia"))
                .andExpect(jsonPath("$.[4].name").value("benny"))
                .andExpect(jsonPath("$.[5].name").value("tony"));
                //.andExpect(jsonPath("$.[6].name").value("andrew")); //삭제된값 제거
    }

    @Test
    void getPerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("martin")) //json데이터를 확인하여 검증함
                .andExpect(jsonPath("$.hobby").isEmpty())
                .andExpect(jsonPath("$.address").isEmpty())
                .andExpect(jsonPath("$.birthday").value("1991-08-15"))
                .andExpect(jsonPath("$.job").isEmpty())
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.age").isNumber()) //년도가 변하면 값이 달라짐 그래서 숫자 존재유무만 확인.
                .andExpect(jsonPath("$.birthdayToday").isBoolean());

        //assertThat(result.getName()).isequalTo() 랑 같은의미
    }

    @Test
    void postPerson() throws Exception{
        PersonDto dto = PersonDto.of("martin", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isCreated()); //isok는 200 iscreated는 201

        Person result = personRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0);
        assertAll(
                () -> assertThat(result.getName()).isEqualTo("martin"),
                () -> assertThat(result.getHobby()).isEqualTo("programming"),
                () -> assertThat(result.getAddress()).isEqualTo("판교"),
                () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                () -> assertThat(result.getJob()).isEqualTo("programmer"),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222")
        );
    }

    @Test
    void postPersonIfNameIsNull() throws Exception{
        PersonDto dto = new PersonDto();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value(400))
            .andExpect(jsonPath("$.message").value("이름은 필수값입니다"));
    }

    @Test
    void postPersonIfNameIsEmpty() throws Exception{
        PersonDto dto = new PersonDto();
        dto.setName("");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("이름은 필수값입니다"));
    }

    @Test
    void postPersonIfNameIsBlankString() throws Exception{
        PersonDto dto = new PersonDto();
        dto.setName(" "); //공백은 null이 아니기 때문에 정상 201을 출력해줌 그래서 dto에서 @NotEmpty 대신 @NotBlank를 사용
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("이름은 필수값입니다"));
    }

    @Test
    void modifyPerson() throws Exception{
        PersonDto dto = PersonDto.of("martin", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(dto))) //데이터를 주지않으면 안바꾼것은 null로 바뀐다.
                .andExpect(status().isOk());

        Person result = personRepository.findById(1L).get();

        assertAll( //모든 검정문을 다 확인하여 오류를 잘못된 부분마다 보여줌
                () -> assertThat(result.getName()).isEqualTo("martin"),
                () -> assertThat(result.getHobby()).isEqualTo("programming"),
                () -> assertThat(result.getAddress()).isEqualTo("판교"),
                () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                () -> assertThat(result.getJob()).isEqualTo("programmer"),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222")
        );



    }

    @Test
    void modifyPersonIfNameIsDifferent() throws Exception{
        PersonDto dto = PersonDto.of("james", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto))) //데이터를 주지않으면 안바꾼것은 null로 바뀐다.
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("이름 변경이 허용되지 않습니다."));
    }

    @Test
    void modifyPersonIfPersonNotFound() throws Exception{
        PersonDto dto = PersonDto.of("martin", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/10")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Person Entity가 존재하지 않습니다."));
    }

    @Test
    void modifyName() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", "martinModified"))
                .andExpect(status().isOk());


        assertThat(personRepository.findById(1L).get().getName()).isEqualTo("martinModified");
    }

    @Test
    void deletePerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

                //.andExpect(content().string("true")); //삭제가 true인지 확인 방법1


        //log.info("people deleted : {}", personRepository.findPeopleDeleted()); 방법1
        assertThat(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
    }

    /*
    @Test
    void checkJsonString() throws JsonProcessingException{
        PersonDto dto = new PersonDto();
        dto.setName("martin");
        dto.setBirthday(LocalDate.now());
        dto.setAddress("판교");

        System.out.println(">>> " + toJsonString(dto));
    }*/

    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }
}
