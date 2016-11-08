package com.ashokram.jenkins.helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ashokram.jenkins.pojo.ProcessDetailsTO;

public class HomeServletHelper {

    private static final Log log = LogFactory.getLog(HomeServletHelper.class);

    private HttpServletRequest request;

    public HomeServletHelper(HttpServletRequest request) {
        this.request = request;
    }

    public Object getProcessDetails() throws Exception {
        ProcessDetailsTO detailsTO = new ProcessDetailsTO();
        try {
            if (StringUtils.isNotBlank(request.getParameter("jUserId"))) {
                detailsTO.setJenkinsUserName(request.getParameter("jUserId"));
            } else {
                throw new NullPointerException("Jenkins User Name is Empty.");
            }
            if (StringUtils.isNotBlank(request.getParameter("jPassword"))) {
                detailsTO.setPassword(request.getParameter("jPassword"));
            } else {
                throw new NullPointerException("Jenkins Password is Empty.");
            }
            if (StringUtils.isNotBlank(request.getParameter("type"))) {
                detailsTO.setType(request.getParameter("type"));
            } else {
                throw new NullPointerException("Jenkins Type is Empty.");
            }
            if (StringUtils.isNotBlank(request.getParameter("urls"))) {
                String[] urls = StringUtils.split(request.getParameter("urls"), ",");
                Map<String, Object> errorMap = validateURL(urls);
                if (null != errorMap && errorMap.size() > 0) {
                    return errorMap;
                }
                detailsTO.setUrls(new HashSet<String>(Arrays.asList(urls)));
            } else {
                throw new NullPointerException("Should specify valid jenkins URL.");
            }
            if (StringUtils.isNotBlank(request.getParameter("vtype"))) {
                detailsTO.setVisualizationType(request.getParameter("vtype"));
            } else {
                throw new NullPointerException("Given Visualization Type is Empty.");
            }
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            log.error("Process details error.", e);
            throw e;
        }
        return detailsTO;
    }

    private Map<String, Object> validateURL(String[] urls) {
        Map<String, Object> errorMap = new HashMap<String, Object>();
        for (String url : urls) {
            try {
                new URL(url);
            } catch (MalformedURLException e) {
                log.error("Error in the given URL", e);
                errorMap.put(url, e.getStackTrace());
            }
        }
        return errorMap;
    }
}
