package com.erp.facility.endpoint;

import static com.erp.facility.common.DtoConverter.convertToDto;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.erp.facility.Service.InspectionService;
import com.erp.facility.Service.MaintenanceService;

import com.erp.facility.Service.impl.InspectionServiceImpl;
import com.erp.facility.Service.impl.MaintenanceServiceImpl;
import com.erp.facility.VO.InspectionDTO;
import com.erp.facility.VO.MaintenanceDTO;


//@WebServlet("/maintenance")
public class MaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	private final MaintenanceService maintenanceService = new MaintenanceServiceImpl();

   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionPage = request.getParameter("actionp");
		if(actionPage == null) {
			
			// QueryParam 을 DTO로 변
//			MaintenanceDTO maintenanceDTO = convertToDto(request, MaintenanceDTO.class);  
////			System.out.println("inspectionDTO = " + inspectionDTO);
//			List<MaintenanceDTO> mlist = maintenanceService.findALl();
//			System.out.println("mlist = " + mlist.size());
//			
//			mlist.forEach(i -> System.out.println(i)); 
//			
//			request.setAttribute("MAI_LIST", mlist );
//			request.setAttribute("gift", "QWER" );
//			
//			request.getRequestDispatcher("/facility/maintenance_detail.jsp").forward(request, response);
		}
			
			
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionPage = request.getParameter("actionp");
//		if(actionPage.equals("save")) {
//			
//			// QueryParam 을 DTO로 변
//			MaintenanceDTO maintenanceDTO = convertToDto(request, MaintenanceDTO.class);  
//			System.out.println("maintenanceDTO = " + maintenanceDTO);
//			maintenanceService.save(maintenanceDTO);
//			
//		} 
		 if(actionPage.equals("modify")) {
			MaintenanceDTO maintenanceDTO = convertToDto(request, MaintenanceDTO.class);  
			System.out.println("inspectionDTO = " + maintenanceDTO);
			int result = maintenanceService.update(maintenanceDTO);

		}	
	}

}
