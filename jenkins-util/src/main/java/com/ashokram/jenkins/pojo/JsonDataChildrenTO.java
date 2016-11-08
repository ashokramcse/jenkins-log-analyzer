
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
public class JsonDataChildrenTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("children")
    @Expose
    private List<JsonDataImediateChildrenTO> children = new LinkedList<JsonDataImediateChildrenTO>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JsonDataImediateChildrenTO> getChildren() {
        return children;
    }

    public void setChildren(List<JsonDataImediateChildrenTO> children) {
        this.children = children;
    }

    public void setChild(JsonDataImediateChildrenTO children) {
        this.children.add(children);
    }

    public void addChildren(List<JsonDataImediateChildrenTO> children) {
        this.children.addAll(children);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(children).toHashCode();
    }

    @Override
    public boolean equals(Object jsonDataChildrenTO) {
        if (jsonDataChildrenTO == this) {
            return true;
        }
        if ((jsonDataChildrenTO instanceof JsonDataChildrenTO) == false) {
            return false;
        }
        JsonDataChildrenTO jsonDataChildrenTO1 = ((JsonDataChildrenTO) jsonDataChildrenTO);
        return new EqualsBuilder().append(name, jsonDataChildrenTO1.name).append(children, jsonDataChildrenTO1.children)
                .isEquals();
    }

    @Override
    public String toString() {
        return "JsonDataChildrenTO [name = " + name + ", children = " + children.toString() + "]";
    }
}
