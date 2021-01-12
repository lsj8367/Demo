package com.example.test.project.mycontact.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PersonControllerTest {

    @Autowired
    private PersonController personController;

    private MockMvc mockMvc;

    @Test
    void getPerson() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1").characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void postPerson() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content("{\n" +
                                "    \"name\" : \"martin2\",\n" +
                                "    \"age\" : 20,\n" +
                                "    \"bloodType\" : \"A\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isCreated()); //isok는 200 iscreated는 201
    }

    @Test
    void modifyPerson() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content("{\n" +
                        "    \"name\" : \"martin\",\n" +
                        "    \"age\" : 20,\n" +
                        "    \"bloodType\" : \"A\"\n" +
                        "}")) //데이터를 주지않으면 안바꾼것은 null로 바뀐다.
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void modifyName() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .param("name", "martin22"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
