
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
 * The Class JsonDataChildrenTO.
 *
 * @author Ashok Ram. G
 * @since 1.1
 */
@Generated("com.ashokram.jenkins")
public class JsonDataChildrenTO implements Serializable
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
	private List<JsonDataImediateChildrenTO> children = new LinkedList<JsonDataImediateChildrenTO>();

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
	public List<JsonDataImediateChildrenTO> getChildren() {
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
	public void setChildren(List<JsonDataImediateChildrenTO> children) {
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
	public void setChild(JsonDataImediateChildrenTO children) {
		this.children.add(children);
	}

	/**
	 * Adds the children.
	 *
	 * @param children
	 *            the children
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void addChildren(List<JsonDataImediateChildrenTO> children) {
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
	public boolean equals(Object jsonDataChildrenTO) {
		if (jsonDataChildrenTO == this) {
			return true;
		}
		if ((jsonDataChildrenTO instanceof JsonDataChildrenTO) == false) {
			return false;
		}
		JsonDataChildrenTO jsonDataChildrenTO1 = ((JsonDataChildrenTO) jsonDataChildrenTO);
		return new EqualsBuilder().append(name, jsonDataChildrenTO1.name).append(children, jsonDataChildrenTO1.children).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JsonDataChildrenTO [name = " + name + ", children = " + children.toString() + "]";
	}
}
