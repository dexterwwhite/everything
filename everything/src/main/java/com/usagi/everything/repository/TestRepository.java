package com.usagi.everything.repository;

import java.util.List;

import com.usagi.everything.model.Test;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Test, Long> {

    List<Test> findByName(String name);

    Test findByTestId(long testId);
}