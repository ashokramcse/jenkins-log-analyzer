package com.ashokram.jenkins.aabstract;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class BaseHttpServlet.
 *
 * @author Ashok Ram. G
 * @since 1.0
 */
public abstract class BaseHttpServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	/**
	 * Method to process the request to servlet in common.
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
