package com.usagi.everything.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Test {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long testId;
    private String name;
    private Boolean isCute;

    protected Test() {}

    public Test(String name, Boolean isCute) {
        this.name = name;
        this.isCute = isCute;
    }

    public Long getTestId() {
        return testId;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsCute() {
        return isCute;
    }
}