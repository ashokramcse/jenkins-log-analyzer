package com.ashokram.jenkins.pojo;

import java.io.Serializable;
import java.util.Set;

/**
 * The Class ProcessDetailsTO.
 *
 * @author Ashok Ram. G
 * @since 1.1
 */
public class ProcessDetailsTO implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The jenkins user name. */
	private String jenkinsUserName;

	/** The password. */
	private String password;

	/** The type. */
	private String type;

	/** The visualization type. */
	private String visualizationType;

	/** The urls. */
	private Set<String> urls;

	/**
	 * Gets the jenkins user name.
	 *
	 * @return the jenkins user name
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public String getJenkinsUserName() {
		return jenkinsUserName;
	}

	/**
	 * Sets the jenkins user name.
	 *
	 * @param jenkinsUserName
	 *            the new jenkins user name
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setJenkinsUserName(String jenkinsUserName) {
		this.jenkinsUserName = jenkinsUserName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the urls.
	 *
	 * @return the urls
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public Set<String> getUrls() {
		return urls;
	}

	/**
	 * Sets the urls.
	 *
	 * @param urls
	 *            the new urls
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	/**
	 * Gets the visualization type.
	 *
	 * @return the visualization type
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public String getVisualizationType() {
		return visualizationType;
	}

	/**
	 * Sets the visualization type.
	 *
	 * @param visualizationType
	 *            the new visualization type
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setVisualizationType(String visualizationType) {
		this.visualizationType = visualizationType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserName-" + getJenkinsUserName() + " Password-" + getPassword() + " Type-" + getType() + " URL-" + getUrls() + " VisualizationType-" + getVisualizationType();
	}
}
