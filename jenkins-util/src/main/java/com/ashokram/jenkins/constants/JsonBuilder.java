package com.ashokram.jenkins.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class JsonBuilder.
 *
 * @author Ashok Ram. G
 * @since 1.0
 */
public class JsonBuilder
{

	/**
	 * Gets the size.
	 *
	 * @return the size
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	public int getSize() {
		int size = 0;
		do {
			size = (int) (Math.random() * (999) + Math.random());
		} while (size == 0);
		return size;
	}

	/**
	 * Clean json.
	 *
	 * @param json
	 *            the json
	 * @return the string
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	public String cleanJson(String json) {
		do {
			json = json.replaceAll(",\"size\":0", StringUtils.EMPTY);
		} while (json.contains(",\"size\":0"));
		return json;
	}
}
