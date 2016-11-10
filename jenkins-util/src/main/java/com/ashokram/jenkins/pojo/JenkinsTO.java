package com.ashokram.jenkins.pojo;

import java.io.Serializable;

// TODO: This TO is unusable now. Need to get all details for the jobs in JenkinsTO object.
/**
 * The Class JenkinsTO.
 *
 * @author Ashok Ram. G
 */
public class JenkinsTO implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The test package. */
	private String testPackage;

	/** The test case. */
	private String testCase;

	/**
	 * Gets the test package.
	 *
	 * @return the test package
	 * @author Ashok Ram. G
	 */
	public String getTestPackage() {
		return testPackage;
	}

	/**
	 * Sets the test package.
	 *
	 * @param testPackage
	 *            the new test package
	 * @author Ashok Ram. G
	 */
	public void setTestPackage(String testPackage) {
		this.testPackage = testPackage;
	}

	/**
	 * Gets the test case.
	 *
	 * @return the test case
	 * @author Ashok Ram. G
	 */
	public String getTestCase() {
		return testCase;
	}

	/**
	 * Sets the test case.
	 *
	 * @param testCase
	 *            the new test case
	 * @author Ashok Ram. G
	 */
	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

}
