
package com.ashokram.jenkins.pojo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class JsonDataTO.
 *
 * @author Ashok Ram. G
 * @since 1.1
 */
@Generated("com.ashokram.jenkins")
public class JsonDataTO implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	@SerializedName("name")
	@Expose
	private String name;

	/** The children. */
	@SerializedName("children")
	@Expose
	private List<JsonDataChildrenTO> children = new LinkedList<JsonDataChildrenTO>();

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
	 * Gets the children.
	 *
	 * @return the children
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public List<JsonDataChildrenTO> getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 *
	 * @param children
	 *            the new children
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setChildren(List<JsonDataChildrenTO> children) {
		this.children = children;
	}

	/**
	 * Sets the child.
	 *
	 * @param children
	 *            the new child
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setChild(JsonDataChildrenTO children) {
		this.children.add(children);
	}

	/**
	 * Adds the all children.
	 *
	 * @param children
	 *            the children
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void addAllChildren(List<JsonDataChildrenTO> children) {
		this.children.addAll(children);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(children).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JsonDataTO [name = " + name + ", children = " + children.toString() + "]";
	}

}
