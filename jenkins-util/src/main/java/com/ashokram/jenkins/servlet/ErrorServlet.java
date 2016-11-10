package com.ashokram.jenkins.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ashokram.jenkins.aabstract.BaseHttpServlet;

/**
 * Servlet implementation class ErrorServlet.
 *
 * @author Ashok Ram. G
 * @since 1.1
 */
@WebServlet("/errorservlet")
public class ErrorServlet extends BaseHttpServlet
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

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
	 * @since 1.1
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
	 * @since 1.1
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ashokram.jenkins.aabstract.BaseHttpServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer errorHTML = new StringBuffer();
		errorHTML.append("<!DOCTYPE html><html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><meta name=\"viewport\"content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\"><title>Jenkins Util</title><!-- Latest compiled and minified CSS --><link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\"><!-- Optional theme --><link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\"><!-- Latest compiled and minified JavaScript --><scriptsrc=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js\"></script><scriptsrc=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script><scriptsrc=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.5.3/css/bootstrapValidator.css\"></script></head><body><div class=\"container\"><div class=\"page-header\"><h1>Jenkins Util</h1></div><div class=\"panel panel-default\"><div class=\"panel-heading\"><h3 class=\"panel-title\">Jenkins Error Details</h3></div><div class=\"panel-body\">");
		errorHTML.append("<div class=\"table-responsive\">");
		errorHTML.append("<table class=\"table table-hover table-bordered\">");
		errorHTML.append("<thead><tr><th>S.No</th><th>URL</th><th>Exception</th></tr></thead>");
		Map<String, Object> errorMap = (Map<String, Object>) request.getAttribute("errorMap");
		int sNo = 0;
		for (Map.Entry<String, Object> error : errorMap.entrySet()) {
			StackTraceElement[] stackTraceElements = (StackTraceElement[]) error.getValue();
			StringBuffer errorstackTrace = new StringBuffer();
			for (StackTraceElement stackTraceElement : stackTraceElements) {
				errorstackTrace.append(stackTraceElement.toString());
				errorstackTrace.append("\n");
			}
			errorHTML.append("<tr><th scope=\"row\">" + ++sNo + "</th><td>" + error.getKey() + "</td><td>" + errorstackTrace.toString() + "</td></tr>");
		}
		errorHTML.append("<tbody></tbody></table></div></div></div></div></body></html>");
		response.getWriter().append(errorHTML.toString());
	}
}
