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
import com.ashokram.jenkins.constants.JenkinsStaticStore;
import com.ashokram.jenkins.helper.HomeServletHelper;
import com.ashokram.jenkins.pojo.ProcessDetailsTO;

/**
 * Servlet implementation class Home.
 *
 * @author Ashok Ram. G
 * @since 1.0
 */
@WebServlet("/home")
public class Home extends BaseHttpServlet
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(Home.class);

	/**
	 * Do get.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * Do post.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ashokram.jenkins.aabstract.BaseHttpServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HomeServletHelper homeServletHelper = new HomeServletHelper(request);
		try {
			clearCache();
			Object details = homeServletHelper.getProcessDetails();
			RequestDispatcher requestDispatcher = null;
			if (details instanceof ProcessDetailsTO) {
				ProcessDetailsTO processDetailsTO = (ProcessDetailsTO) homeServletHelper.getProcessDetails();
				request.setAttribute("processDetailsTO", processDetailsTO);
				requestDispatcher = request.getRequestDispatcher("jenkinsservlet");
				requestDispatcher.forward(request, response);
			} else if (details instanceof Map) {
				request.setAttribute("errorMap", details);
				requestDispatcher = request.getRequestDispatcher("errorservlet");
				requestDispatcher.forward(request, response);
			}
		} catch (Exception e) {
			log.error("Error in processing data.", e);
		}
	}

	/**
	 * Clear cache.
	 * 
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private void clearCache() {
		JenkinsStaticStore.clearCache();
	}

}