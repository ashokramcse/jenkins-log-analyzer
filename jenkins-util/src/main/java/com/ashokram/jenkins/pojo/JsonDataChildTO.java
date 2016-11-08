
package com.ashokram.jenkins.pojo;

import java.io.Serializable;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("com.altimetrik.jenkins")
public class JsonDataChildTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("size")
    @Expose
    private int size = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(size).toHashCode();
    }

    @Override
    public boolean equals(Object jsonDataChildTO) {
        if (jsonDataChildTO == this) {
            return true;
        }
        if ((jsonDataChildTO instanceof JsonDataChildTO) == false) {
            return false;
        }
        JsonDataChildTO jsonDataChildTO1 = ((JsonDataChildTO) jsonDataChildTO);
        return new EqualsBuilder().append(name, jsonDataChildTO1.name).append(size, jsonDataChildTO1.size).isEquals();
    }

    @Override
    public String toString() {
        return "JsonDataChildTO [name = " + name + ", size = " + size + "]";
    }
}
