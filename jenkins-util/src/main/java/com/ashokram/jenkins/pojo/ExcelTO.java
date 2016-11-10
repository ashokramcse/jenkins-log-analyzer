package com.ashokram.jenkins.pojo;

import java.io.InputStream;
import java.io.Serializable;

/**
 * The Class ExcelTO.
 *
 * @author Ashok Ram. G
 * @since 1.1
 */
public class ExcelTO implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The file name. */
	private String fileName;

	/** The full name. */
	private String fullName;

	/** The file input stream. */
	private InputStream fileInputStream;

	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the full name.
	 *
	 * @param fullName
	 *            the new full name
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName
	 *            the new file name
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the file input stream.
	 *
	 * @return the file input stream
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	/**
	 * Sets the file input stream.
	 *
	 * @param fileInputStream
	 *            the new file input stream
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

}
