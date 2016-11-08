package com.ashokram.jenkins.pojo;

import java.io.Serializable;

public class JenkinsTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String testPackage;

    private String testCase;

    public String getTestPackage() {
        return testPackage;
    }

    public void setTestPackage(String testPackage) {
        this.testPackage = testPackage;
    }

    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

}
