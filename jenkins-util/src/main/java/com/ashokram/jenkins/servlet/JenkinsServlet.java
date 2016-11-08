package com.ashokram.jenkins.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ashokram.jenkins.aabstract.BaseHttpServlet;
import com.ashokram.jenkins.helper.JenkinsServletHelper;

/**
 * Servlet implementation class JenkinsServlet
 */
@WebServlet("/jenkinsservlet")
public class JenkinsServlet extends BaseHttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(JenkinsServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doProcess(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doProcess(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.altimetrik.jenkins.aabstract.BaseHttpServlet#doProcess(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JenkinsServletHelper jenkinsServletHelper = new JenkinsServletHelper(request);
        try {
            Object result = jenkinsServletHelper.processJenkins();
            if (result instanceof Map) {
                request.setAttribute("errorMap", result);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("errorservlet");
                requestDispatcher.forward(request, response);
            } else if (result instanceof String) {
                if (String.valueOf(result).equalsIgnoreCase("excel")) {

                } else if (String.valueOf(result).equalsIgnoreCase("json")) {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("dntTreeOut.html");
                    requestDispatcher.forward(request, response);
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

}
