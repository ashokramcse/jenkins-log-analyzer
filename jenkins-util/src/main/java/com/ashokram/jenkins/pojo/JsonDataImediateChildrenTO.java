
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
public class JsonDataImediateChildrenTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("children")
    @Expose
    private List<JsonDataChildTO> children = new LinkedList<JsonDataChildTO>();

    @SerializedName("size")
    @Expose
    private int size = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JsonDataChildTO> getChildren() {
        return children;
    }

    public void setChildren(List<JsonDataChildTO> children) {
        this.children = children;
    }

    public void setChild(JsonDataChildTO children) {
        this.children.add(children);
    }

    public void addAllChildren(List<JsonDataChildTO> children) {
        this.children.addAll(children);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(children).append(size).toHashCode();
    }

    @Override
    public boolean equals(Object jsonDataImediateChildrenTO) {
        if (jsonDataImediateChildrenTO == this) {
            return true;
        }
        if ((jsonDataImediateChildrenTO instanceof JsonDataImediateChildrenTO) == false) {
            return false;
        }
        JsonDataImediateChildrenTO jsonDataImediateChildrenTO1 = ((JsonDataImediateChildrenTO) jsonDataImediateChildrenTO);
        return new EqualsBuilder().append(name, jsonDataImediateChildrenTO1.name)
                .append(children, jsonDataImediateChildrenTO1.children).append(size, jsonDataImediateChildrenTO1.size)
                .isEquals();
    }

    @Override
    public String toString() {
        return "JsonDataImediateChildrenTO [name = " + name + ", size = " + size + ", children = " + children.toString()
                + "]";
    }

}
