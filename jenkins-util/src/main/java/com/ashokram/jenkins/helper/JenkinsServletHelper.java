package com.ashokram.jenkins.helper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ashokram.jenkins.constants.JsonBuilder;
import com.ashokram.jenkins.manager.JenkinsExcelManager;
import com.ashokram.jenkins.manager.JenkinsManager;
import com.ashokram.jenkins.pojo.JsonDataTO;
import com.ashokram.jenkins.pojo.ProcessDetailsTO;
import com.google.gson.Gson;

/**
 * The Class JenkinsServletHelper.
 *
 * @author Ashok Ram. G
 * @since 1.0
 */
public class JenkinsServletHelper
{

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(JenkinsServletHelper.class);

	/** The request. */
	private HttpServletRequest request;

	/**
	 * Instantiates a new jenkins servlet helper.
	 *
	 * @param request
	 *            the request
	 */
	public JenkinsServletHelper(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Process jenkins.
	 *
	 * @return the object
	 * @throws Exception
	 *             the exception
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	public Object processJenkins() throws Exception {
		ProcessDetailsTO processDetailsTO = (ProcessDetailsTO) request.getAttribute("processDetailsTO");
		JenkinsManager jenkinsManager = new JenkinsManager();
		Map<String, Object> errorMap = validateJenkinsURL(processDetailsTO);
		if (null != errorMap && errorMap.size() > 0) {
			return errorMap;
		}
		for (String url : processDetailsTO.getUrls()) {
			jenkinsManager.getJenkinsDetails(processDetailsTO.getJenkinsUserName(), processDetailsTO.getPassword(), url, processDetailsTO.getType());
		}
		if (processDetailsTO.getVisualizationType().equals("excel")) {
			prepareExcel();
			return "excel";
		} else {
			prepareJson();
			return "json";
		}
	}

	/**
	 * Validate jenkins URL.
	 *
	 * @param processDetailsTO
	 *            the process details TO
	 * @return the map
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private Map<String, Object> validateJenkinsURL(ProcessDetailsTO processDetailsTO) {
		JenkinsManager jenkinsManager = new JenkinsManager();
		Map<String, Object> errorMap = new HashMap<String, Object>();
		for (String url : processDetailsTO.getUrls()) {
			errorMap.putAll(jenkinsManager.validateAndProcess(processDetailsTO.getType(), url, processDetailsTO.getJenkinsUserName(), processDetailsTO.getPassword()));
		}
		return errorMap;
	}

	/**
	 * Prepare json.
	 * 
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private void prepareJson() {
		JenkinsManager jenkinsManager = new JenkinsManager();
		JsonDataTO jsonDataTO = jenkinsManager.getJsonData();
		String json = new Gson().toJson(jsonDataTO);
		JsonBuilder jsonBuilder = new JsonBuilder();
		json = jsonBuilder.cleanJson(json);
		try {
			writeJson(json);
		} catch (Exception e) {
			log.error("Error in writing json into file.", e);
		}
	}

	/**
	 * Prepare excel.
	 * 
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private void prepareExcel() {
		JenkinsExcelManager excelManager = new JenkinsExcelManager(request);
		try {
			excelManager.writeExcelFile();
		} catch (Exception e) {
			log.error("Error in writing Excel file.", e);
		}
	}

	/**
	 * Write json.
	 *
	 * @param json
	 *            the json
	 * @throws Exception
	 *             the exception
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private void writeJson(String json) throws Exception {
		String path = this.request.getServletContext().getRealPath("WEB-INF/../");
		FileUtils.writeStringToFile(new File(path.concat("testcase.json")), json);
		Thread.sleep(60000);
	}
}