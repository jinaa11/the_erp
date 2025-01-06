package com.erp.finance.capital;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/capital")
public class CapitalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CapitalDAO cdao = new CapitalDAO();
		
		ArrayList<CapitalManagementDTO> alist = cdao.findAll();
		
		System.out.println("alist = "+ alist);
		request.setAttribute("ALIST", alist);
		request.getRequestDispatcher("/erp/pages/finance/capital_list.jsp").forward(request, response);
		
		
	}

	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}
