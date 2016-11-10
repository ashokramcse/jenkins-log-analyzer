
package com.ashokram.jenkins.pojo;

import java.io.Serializable;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class JsonDataChildTO.
 *
 * @author Ashok Ram. G
 * @since 1.1
 */
@Generated("com.ashokram.jenkins")
public class JsonDataChildTO implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	@SerializedName("name")
	@Expose
	private String name;

	/** The size. */
	@SerializedName("size")
	@Expose
	private int size = 0;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size
	 *            the new size
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(size).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JsonDataChildTO [name = " + name + ", size = " + size + "]";
	}
}
