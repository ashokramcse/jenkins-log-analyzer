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
 * Servlet implementation class TreeActionServlet
 * 
 * @author Ashok Ram. G
 */
@WebServlet("/treeactionservlet")
public class TreeActionServlet extends BaseHttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * @author Ashok Ram. G
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doProcess(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * @author Ashok Ram. G
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
        String requestParam = request.getParameter("treeactionservletaction");
        if (StringUtils.isNotBlank(requestParam) && requestParam.equalsIgnoreCase("download")) {
            doDownloadProcess(request, response);
        } else if (StringUtils.isNotBlank(requestParam) && requestParam.equalsIgnoreCase("refresh")) {
            doRefreshProcess(request, response);
        }
    }

    protected void doDownloadProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

    protected void doRefreshProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("dntTreeOut.html");
        requestDispatcher.forward(request, response);
    }
}
