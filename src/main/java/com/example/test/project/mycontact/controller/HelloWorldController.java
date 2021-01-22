package com.example.test.project.mycontact.controller;

import com.example.test.project.mycontact.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

//@Controller
//@ResponseBody
@RestController // << Controller + ResponseBody
public class HelloWorldController {

    @GetMapping(value = "/api/helloWorld")
    public String helloWorld(){
        return "HelloWorld!";
    }
    
    @GetMapping(value = "/api/helloException")
    public String helloException(){ //항상오류를 발생시키는 예외처리
        throw new RuntimeException("Hello RuntimeException");
    }


}
