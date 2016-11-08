package com.ashokram.jenkins.aabstract;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseHttpServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * Method to process the request to servlet in common.
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * 
     * @author Ashok Ram. G
     */
    protected void doProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
