package com.example.test.project.mycontact.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloWorldControllerTest {

    @Autowired
    private HelloWorldController helloWorldController;
    
    //mockmvc 테스트 : 매핑까지 일치시켜 조건으로 출력하기위한 테스트
    private MockMvc mockMvc;
    
    
    @Test
    void helloWorld(){
        //System.out.println("test");
        System.out.println(helloWorldController.helloWorld());

        Assertions.assertEquals(helloWorldController.helloWorld(), "HelloWorld!");
        //여기서는 mapping을 무시하고 테스트하여 값만 알아낼수 있음.
    }

    @Test
    void mockMvcTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloWorld") //주소 매칭
        ).andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk()) //예측 상태가 OK인지 확인( 200이 잘 뜨는지)
        .andExpect(MockMvcResultMatchers.content().string("HelloWorld!")); //HelloWorld!가 맞는지 다르다면 오류 발생

    }



}