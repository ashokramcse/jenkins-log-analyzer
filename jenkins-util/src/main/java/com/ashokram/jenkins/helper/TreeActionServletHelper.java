package com.ashokram.jenkins.helper;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ashokram.jenkins.manager.JenkinsExcelManager;
import com.ashokram.jenkins.pojo.ExcelTO;

/**
 * The Class TreeActionServletHelper.
 *
 * @author Ashok Ram. G
 * @since 1.1
 */
public class TreeActionServletHelper
{

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(TreeActionServletHelper.class);

	/** The request. */
	private HttpServletRequest request;

	/**
	 * Instantiates a new tree action servlet helper.
	 *
	 * @param request
	 *            the request
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public TreeActionServletHelper(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Gets the excel TO.
	 *
	 * @return the excel TO
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	public ExcelTO getExcelTO() {
		JenkinsExcelManager jenkinsExcelManager = new JenkinsExcelManager(request);
		try {
			return jenkinsExcelManager.getExcelTO();
		} catch (Exception e) {
			log.error("Error in getting Excel stream.", e);
		}
		return null;
	}
}