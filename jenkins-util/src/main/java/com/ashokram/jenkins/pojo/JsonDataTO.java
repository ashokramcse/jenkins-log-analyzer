
package com.ashokram.jenkins.pojo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("com.altimetrik.jenkins")
public class JsonDataTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("children")
    @Expose
    private List<JsonDataChildrenTO> children = new LinkedList<JsonDataChildrenTO>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JsonDataChildrenTO> getChildren() {
        return children;
    }

    public void setChildren(List<JsonDataChildrenTO> children) {
        this.children = children;
    }

    public void setChild(JsonDataChildrenTO children) {
        this.children.add(children);
    }

    public void addAllChildren(List<JsonDataChildrenTO> children) {
        this.children.addAll(children);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(children).toHashCode();
    }

    @Override
    public boolean equals(Object jsonDataTO) {
        if (jsonDataTO == this) {
            return true;
        }
        if ((jsonDataTO instanceof JsonDataTO) == false) {
            return false;
        }
        JsonDataTO jsonDataTO1 = ((JsonDataTO) jsonDataTO);
        return new EqualsBuilder().append(name, jsonDataTO1.name).append(children, jsonDataTO1.children).isEquals();
    }

    @Override
    public String toString() {
        return "JsonDataTO [name = " + name + ", children = " + children.toString() + "]";
    }

}
