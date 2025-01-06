package com.erp.finance.capital;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/capital")
public class CapitalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CapitalDAO cdao = new CapitalDAO();

		ArrayList<CapitalManagementDTO> alist = cdao.findAll();

//		System.out.println("alist = " + alist);
		request.setAttribute("ALIST", alist);
		request.getRequestDispatcher("/erp/pages/finance/capital_list.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String actionPage = request.getParameter("action");
//		System.out.println("actionPage = " + actionPage);

		if (actionPage == null || actionPage.equals("")) {
			String[] capitalTypes = request.getParameterValues("capitalTypes");
			String[] statuses = request.getParameterValues("statuses");
//			System.out.println("capitalTypes = " + capitalTypes);
//			System.out.println("statuses = " + statuses);

			List<String> capitalTypesList = capitalTypes != null ? Arrays.asList(capitalTypes) : new ArrayList<>();
			List<String> statusesList = statuses != null ? Arrays.asList(statuses) : new ArrayList<>();

//			System.out.println("capitalTypesList = " + capitalTypesList.toString());
//			System.out.println("statusesList = " + statusesList.toString());

			CapitalDAO cdao = new CapitalDAO();
			ArrayList<CapitalManagementDTO> alist = cdao.findAllByCapitalType(capitalTypesList, statusesList);
//			System.out.println("alist = " + alist);
//			System.out.println("aSize = " + alist.size());

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			String jsonResponse = objectMapper.writeValueAsString(alist);

			response.getWriter().write(jsonResponse);

		} else if (actionPage.equals("edit")) {

			String seq = request.getParameter("id");
			String status = request.getParameter("status");
			String[] capitalTypes = request.getParameterValues("capitalTypes");
			String[] statuses = request.getParameterValues("statuses");

			List<String> capitalTypesList = capitalTypes != null ? Arrays.asList(capitalTypes) : new ArrayList<>();
			List<String> statusesList = statuses != null ? Arrays.asList(statuses) : new ArrayList<>();

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			CapitalDAO cdao = new CapitalDAO();
			int row = cdao.statusUpdate(seq,status);
			
			ObjectMapper objectMapper = new ObjectMapper();
			ArrayList<CapitalManagementDTO> alist = cdao.findAllByCapitalType(capitalTypesList, statusesList);

			String jsonResponse = objectMapper.writeValueAsString(alist);

//			System.out.println("seq = " + seq);
//			System.out.println("status = " + status);
//			System.out.println("capitalTypes = " + capitalTypes);
//			System.out.println("statuses = " + statuses);
//			System.out.println("jsonResponse = " + jsonResponse);

			response.getWriter().write(jsonResponse);

		}

	}

}
