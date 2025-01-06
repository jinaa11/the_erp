package com.erp.facility.endpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.erp.auth.vo.AuthDTOs.RegisterRequestDTO;
import com.erp.common.rest.RestBusinessException;
import com.erp.common.rest.RestResponse;
import com.erp.facility.Service.ProductService;
import com.erp.facility.Service.impl.ProductServiceImpl;
import com.erp.facility.VO.ProductDTO;
import com.erp.facility.VO.ProductDTO.CreateProductManagementRequestDTO;
import com.erp.facility.VO.ProductDTO.UpdateProductManagementRequestDTO;
import com.erp.facility.VO.ProductDTO.UpdateProductRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.erp.common.rest.RestBusinessException.StatusCode;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/api/v1/product/*")
public class ProductServlet extends HttpServlet {
       
	private static final ProductService productService = new ProductServiceImpl(); 
	private static final ObjectMapper om = new ObjectMapper();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		RestResponse<?> restResponse = null;
		switch (request.getRequestURI()) {
		case "/api/v1/product/products": {
			restResponse = RestResponse.builder().message("OK").resonseDate(new Date()).data(productService.getProducts())
					.build();
			response.setStatus(HttpServletResponse.SC_OK);
			break;
		}
		case "/api/v1/product/product_managements": {
			restResponse = RestResponse.builder().message("OK").resonseDate(new Date()).data(productService.getProductManagement())
					.build();
			response.setStatus(HttpServletResponse.SC_OK);
			break;
		}
		default:
			throw new RestBusinessException(StatusCode.BAD_REQUEST);
		}
		response.getWriter().write(om.writer().writeValueAsString(restResponse));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!request.getContentType().equals("application/json"))
			throw new RestBusinessException(StatusCode.BAD_REQUEST);
		response.setContentType("application/json");
		StringBuilder jsonData = new StringBuilder();
		String line;
		try (BufferedReader reader = request.getReader()) {
			while ((line = reader.readLine()) != null) {
				jsonData.append(line);
			}
		}
		String jsonString = jsonData.toString();
		RestResponse<?> restResponse;
		switch (request.getRequestURI()) {
		case "/api/v1/product/product": {
			productService.createProduct(om.reader().readValue(jsonString, ProductDTO.CreateProductRequestDTO.class));
			response.setStatus(HttpServletResponse.SC_CREATED);
			restResponse = RestResponse.builder().data(new Date()).message("CREATED").build();
			break;
		}
		case "/api/v1/product/product_management": {
			productService.createProductManagement(om.reader().readValue(jsonString, CreateProductManagementRequestDTO.class));
			response.setStatus(HttpServletResponse.SC_CREATED);
			restResponse = RestResponse.builder().data(new Date()).message("CREATED").build();
			break;
		}
		default:
			throw new RestBusinessException(StatusCode.BAD_REQUEST);
		}
		response.getWriter().write(om.writer().writeValueAsString(restResponse));
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		RestResponse<?> restResponse = null;
		switch (request.getRequestURI()) {
		case "/api/v1/product/product": {
			productService.deleteProduct(Integer.parseInt(request.getParameter("productSeq")));
			restResponse = RestResponse.builder().data(new Date()).message("OK").build();
			response.setStatus(HttpServletResponse.SC_OK);
			break;
		}
		case "/api/v1/product/product_management": {
			productService.deleteProduct(Integer.parseInt(request.getParameter("productManagementSeq")));
			restResponse = RestResponse.builder().data(new Date()).message("OK").build();
			response.setStatus(HttpServletResponse.SC_OK);
			break;
		}
		default:
			throw new RestBusinessException(StatusCode.BAD_REQUEST);
		}
		response.getWriter().write(om.writer().writeValueAsString(restResponse));
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		StringBuilder jsonData = new StringBuilder();
		String line;
		try (BufferedReader reader = request.getReader()) {
			while ((line = reader.readLine()) != null) {
				jsonData.append(line);
			}
		}
		String jsonString = jsonData.toString();
		RestResponse<?> restResponse;
		switch (request.getRequestURI()) {
		case "/api/v1/product/product": {
			productService.updateProduct(om.reader().readValue(jsonString, UpdateProductRequestDTO.class));
			response.setStatus(HttpServletResponse.SC_OK);
			restResponse = RestResponse.builder().data(new Date()).message("OK").build();
			break;
		}
		case "/api/v1/product/product_management": {
			productService.updateProductManagement(om.reader().readValue(jsonString, UpdateProductManagementRequestDTO.class));
			response.setStatus(HttpServletResponse.SC_OK);
			restResponse = RestResponse.builder().data(new Date()).message("OK").build();
			break;
		}
		default:
			throw new RestBusinessException(StatusCode.BAD_REQUEST);
		}
		response.getWriter().write(om.writer().writeValueAsString(restResponse));
	}

}
