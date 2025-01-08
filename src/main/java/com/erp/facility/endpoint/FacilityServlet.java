package com.erp.facility.endpoint;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.erp.common.rest.RestBusinessException;
import com.erp.common.rest.RestBusinessException.StatusCode;
import com.erp.facility.Service.FacilityService;
import com.erp.facility.Service.MaintenanceService;
import com.erp.facility.Service.ManagerService;
import com.erp.facility.Service.impl.FacilityServiceImpl;
import com.erp.facility.Service.impl.MaintenanceServiceImpl;
import com.erp.facility.Service.impl.ManagerServiceImpl;
import com.erp.facility.VO.FacilityDTO;

import com.erp.facility.VO.ManagerDTO;
import com.erp.facility.dto.CommomGetDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.erp.facility.common.DtoConverter.convertToDto;

@WebServlet("/facility/*")
public class FacilityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final FacilityService facilityService = new FacilityServiceImpl();
	private final ManagerService managerService = new ManagerServiceImpl();
	private final MaintenanceService maintenanceService = new MaintenanceServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String actionPage = request.getParameter("actiop");
		String pathInfo = request.getPathInfo();

//		System.out.println("pathInfo = " + pathInfo);

		// 아무것도 없다면 list로 보내기
		if (pathInfo == null) {
			
			
			response.sendRedirect("/facility/list");
			
			
			// 시설관리 리스트 보기
		} else if (pathInfo.equals("/list")) {

			List<FacilityDTO> allList = facilityService.findAll();
			

			
			request.setAttribute("Fac_LIST", allList);
			request.getRequestDispatcher("/erp/ga/facility/facility_main.jsp").forward(request, response);

		} else if ("/details".equals(pathInfo)) {
			

			// 상세목록 보기
			String seqStr = request.getParameter("facilityId");

			if (seqStr != null) {
				int seq = Integer.parseInt(seqStr);

				FacilityDTO facility = facilityService.findDetailsFacility(seq);
				ManagerDTO manager = managerService.findDetailsManager(seq);
				
				List<CommomGetDTO> mList = maintenanceService.findALl(seqStr);
				
//				System.out.println("mListmList = " + mList);

				request.setAttribute("FACILITY", facility);
				request.setAttribute("MANAGER", manager);
				request.setAttribute("MAINT", mList);
				
				
				request.getRequestDispatcher("/erp/ga/facility/facility_details.jsp").forward(request, response);

			} else {
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
			
			
		} else if (pathInfo.equals("/create")) {

			// 시설 타입 테이블호출
			List<String> fType = facilityService.facilityType();
		
			request.setAttribute("F_TYPE", fType);
			request.getRequestDispatcher("/erp/ga/facility/facility_form.jsp").forward(request, response);

		} else if ("/modify".equals(pathInfo)) {

			// 상세목록 보기
			String seqStr = request.getParameter("facilityId");

			if (seqStr != null) {
				int seq = Integer.parseInt(seqStr);

				FacilityDTO facility = facilityService.findDetailsFacility(seq);
				ManagerDTO manager = managerService.findDetailsManager(seq);
				// 관리중인 시설이 없는 관리자 리스트
				List<ManagerDTO> nullManager = managerService.findFacilityIsNull();
				request.setAttribute("EMPTY_MANAGER", nullManager);
				
				request.setAttribute("FACILITY", facility);
				request.setAttribute("MANAGER", manager);
				request.getRequestDispatcher("/erp/ga/facility/facility_modify.jsp").forward(request, response);

			} else {
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}

		} else if ("/add-manager".equals(pathInfo)) {

			// 매니저 추가
			request.getRequestDispatcher("/erp/ga/facility/manager_form.jsp").forward(request, response);

		} else {

			// 예외 발생 아무것도 없다면
			throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
		}

//		System.out.println("Facility Servlet Get End ");
	}
	
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String actionPage = request.getParameter("actiop");

//		System.out.println("actiop = " + actionPage);

		if (actionPage == null) {
			System.out.println("NULL 입니다. ");
			response.sendRedirect("/facility/list");

		} else if (actionPage.equals("save")) {

			// 타입 컨버트, 자동으로 필드 매핑
			FacilityDTO facility = convertToDto(request, FacilityDTO.class);
			facilityService.save(facility);

//			System.out.println("facilityDTO : 저장완료");
			response.sendRedirect("/facility/list");

		} else if (actionPage.equals("status")) {

			String status = request.getParameter("value");

			List<FacilityDTO> flist = facilityService.findTypeSelectList(status);

			// JSON으로 보내기
			ObjectMapper mapper = new ObjectMapper();
			PrintWriter out = response.getWriter();
			mapper.writeValue(out, flist);

		} else if (actionPage.equals("manager-search")) {
			response.setContentType("application/json");

			String managerSeq = request.getParameter("name");
			ObjectMapper mapper = new ObjectMapper();
			PrintWriter out = response.getWriter();

			// 매니저 추가를 위해 유저 중복검사
			boolean checkUser = managerService.checkUser(managerSeq);
			mapper.writeValue(out, checkUser);

		} else if (actionPage.equals("manager-add")) {

			String managerSeq = request.getParameter("name");			
			int save = managerService.save(managerSeq);
			response.sendRedirect("/facility/list");

			
		} else if (actionPage.equals("modify")) {
			
			String facilityId = request.getParameter("facilityId");					
			String userSeq = request.getParameter("workName");		
			String currentManagerId = request.getParameter("currentManagerId");
		
			FacilityDTO facility = convertToDto(request, FacilityDTO.class);
						
			facilityService.updateFacility(facility);
			managerService.update(userSeq, facilityId, currentManagerId);
			
			
			response.sendRedirect("/facility/details?actiop=details&facilityId="+facilityId);

		} else if (actionPage.equals("maintenance")) {
			
//			System.out.println("maintenance = " + actionPage);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    PrintWriter out = response.getWriter();
		    ObjectMapper mapper = new ObjectMapper();
		    CommomGetDTO commomGetDTO = mapper.readValue(request.getReader(), CommomGetDTO.class);	 
		    
		    
//			System.out.println("maintenance = " + commomGetDTO);
			
		    try {
		        // JSON 요청 데이터를 DTO로 변환
	        		        
		        // 저장 처리
		        int result = maintenanceService.save(commomGetDTO);		       	        
		        String fSeq = String.valueOf(commomGetDTO.getReferenceSeq());
		        
		        // 저장 후 해당 시설의 전체 유지보수 목록 조회
		        List<CommomGetDTO> mList = maintenanceService.findALl(fSeq);
		        CommomGetDTO dto = maintenanceService.findOneMax(fSeq);
//		        System.out.println("dto = " + dto);
//		        System.out.println("mList = " + mList);
				
		        Map<String, Object> responseData = new HashMap<>();
//		        System.out.println("dto : "+ dto);	  		        
//		        System.out.println("dto.getWorkingDate().toString() : "+ dto.getWorkingDate().toString());	  		        
		        // 응답 데이터 구성
		        responseData.put("success", result == 1);
		        responseData.put("maintenance", dto!=null ? dto : commomGetDTO);	               
		        responseData.put("maintenanceList", mList);

		        
		        
		        // JSON 응답 전송
		        mapper.writeValue(out, responseData);
		        
		        System.out.println(dto);
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		        Map<String, Object> errorResponse = new HashMap<>();
		        errorResponse.put("success", false);
		        mapper.writeValue(out, errorResponse);
		    }
		
	}
		
		
		

//		System.out.println("Facility Servlet Post End");
	}

}
