package com.ashokram.jenkins.helper;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ashokram.jenkins.manager.JenkinsExcelManager;
import com.ashokram.jenkins.pojo.ExcelTO;

public class TreeActionServletHelper {

    private static final Log log = LogFactory.getLog(TreeActionServletHelper.class);

    private HttpServletRequest request;

    public TreeActionServletHelper(HttpServletRequest request) {
        this.request = request;
    }

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