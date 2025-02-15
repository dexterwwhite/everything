package com.usagi.everything.controller;

import com.usagi.everything.model.Test;
import com.usagi.everything.service.TestService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.status(HttpStatus.OK).body("Test endpoint!");
    }

    @GetMapping("/csrfToken")
    public CsrfToken csrfToken(CsrfToken token) {
        return token;
    }

    @GetMapping("/getTestByName/{name}")
    @ResponseBody
    public List<Test> getTestsByName(@PathVariable String name) {
        return testService.getTestsByName(name);
    }
    
    @PostMapping("/addTest")
    @ResponseBody
    public Test addTest(@RequestParam String name, @RequestParam boolean cute) {
        return testService.addTest(name, cute);
    }
}