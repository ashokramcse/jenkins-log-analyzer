
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
 * The Class JsonDataImediateChildrenTO.
 *
 * @author Ashok Ram. G
 * @since 1.1
 */
@Generated("com.ashokram.jenkins")
public class JsonDataImediateChildrenTO implements Serializable
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
	private List<JsonDataChildTO> children = new LinkedList<JsonDataChildTO>();

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
	 * Gets the children.
	 *
	 * @return the children
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public List<JsonDataChildTO> getChildren() {
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
	public void setChildren(List<JsonDataChildTO> children) {
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
	public void setChild(JsonDataChildTO children) {
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
	public void addAllChildren(List<JsonDataChildTO> children) {
		this.children.addAll(children);
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
		return new HashCodeBuilder().append(name).append(children).append(size).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object jsonDataImediateChildrenTO) {
		if (jsonDataImediateChildrenTO == this) {
			return true;
		}
		if ((jsonDataImediateChildrenTO instanceof JsonDataImediateChildrenTO) == false) {
			return false;
		}
		JsonDataImediateChildrenTO jsonDataImediateChildrenTO1 = ((JsonDataImediateChildrenTO) jsonDataImediateChildrenTO);
		return new EqualsBuilder().append(name, jsonDataImediateChildrenTO1.name).append(children, jsonDataImediateChildrenTO1.children).append(size, jsonDataImediateChildrenTO1.size).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JsonDataImediateChildrenTO [name = " + name + ", size = " + size + ", children = " + children.toString() + "]";
	}

}
