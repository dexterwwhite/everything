package com.usagi.everything.service;

import com.usagi.everything.repository.TestRepository;
import com.usagi.everything.model.Test;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<Test> getTestsByName(String name) {
        return testRepository.findByName(name);
    }

    public Test getTestById(long id) {
        return testRepository.findByTestId(id);
    }

    public Test addTest(String name, boolean isCute) {
        Test newBoi = new Test(name, isCute);
        return testRepository.save(newBoi);
    }
}