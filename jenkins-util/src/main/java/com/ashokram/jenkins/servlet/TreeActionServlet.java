package com.ashokram.jenkins.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.ashokram.jenkins.aabstract.BaseHttpServlet;
import com.ashokram.jenkins.helper.TreeActionServletHelper;
import com.ashokram.jenkins.pojo.ExcelTO;

/**
 * The Class TreeActionServlet.
 *
 * @author Ashok Ram. G
 * @since 1.1
 */
@WebServlet("/treeactionservlet")
public class TreeActionServlet extends BaseHttpServlet
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
	 * Do Post.
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
		String requestParam = request.getParameter("treeactionservletaction");
		if (StringUtils.isNotBlank(requestParam) && requestParam.equalsIgnoreCase("download")) {
			doDownloadProcess(request, response);
		} else if (StringUtils.isNotBlank(requestParam) && requestParam.equalsIgnoreCase("refresh")) {
			doRefreshProcess(request, response);
		}
	}

	/**
	 * Do download process.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	protected void doDownloadProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TreeActionServletHelper treeActionServletHelper = new TreeActionServletHelper(request);
		ExcelTO excelTO = treeActionServletHelper.getExcelTO();
		FileInputStream fileInputStream = (FileInputStream) excelTO.getFileInputStream();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String filename = excelTO.getFileName();
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.close();

		FileUtils.deleteQuietly(new File(excelTO.getFullName()));
	}

	/**
	 * Do refresh process.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @author Ashok Ram. G
	 * @since 1.1
	 */
	protected void doRefreshProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("dntTreeOut.html");
		requestDispatcher.forward(request, response);
	}
}
