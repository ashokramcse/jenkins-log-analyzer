package com.ashokram.jenkins.pojo;

import java.io.Serializable;
import java.util.Set;

public class ProcessDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String jenkinsUserName;

    private String password;

    private String type;

    private String visualizationType;

    private Set<String> urls;

    public String getJenkinsUserName() {
        return jenkinsUserName;
    }

    public void setJenkinsUserName(String jenkinsUserName) {
        this.jenkinsUserName = jenkinsUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    public String getVisualizationType() {
        return visualizationType;
    }

    public void setVisualizationType(String visualizationType) {
        this.visualizationType = visualizationType;
    }

    @Override
    public String toString() {
        return "UserName-" + getJenkinsUserName() + " Password-" + getPassword() + " Type-" + getType() + " URL-"
                + getUrls() + " VisualizationType-" + getVisualizationType();
    }
}
