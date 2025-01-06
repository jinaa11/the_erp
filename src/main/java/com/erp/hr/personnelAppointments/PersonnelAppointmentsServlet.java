package com.erp.hr.personnelAppointments;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.erp.facility.common.DtoConverter;
import com.erp.hr.common.PagingUtil;
import com.erp.hr.employeeCard.HrVO;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet("/PAServlet")

public class PersonnelAppointmentsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//인코딩
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String pageGubun = request.getParameter("pageGubun");
		PersonnelAppointmentsDAO padao = new PersonnelAppointmentsDAO();
   	
//   		System.out.println("페이지 구분자는 : -" + pageGubun + "-");
   		
   		
   		//	 -------------------------------------------------------- 인사발령 전체 목록보기 (페이징)
   		if (pageGubun == null || pageGubun.equals("")) {
   		
   			int currentPage = 1;
   			
   			String currentPageStr = request.getParameter("currentPage");
   			
   			if(currentPageStr != null && !currentPageStr.equals("")) {
   				currentPage = Integer.parseInt(currentPageStr);
   				//현재페이지 값 세팅
   			}
   			//총개수는 DB에 가서 가져와
   			int totRecord = padao.totalSelect().size();
   			
   			//내맘대로 보여줄개수 설정
   			int blockCount = 10;
   			int blockPage = 5;
   		
   			PagingUtil pg = new PagingUtil("/PAServlet", currentPage, totRecord, blockCount, blockPage);

   			ArrayList<PaVO> palist = padao.pagingSelect(pg.getStartSeq(), pg.getEndSeq());
   			
   			request.setAttribute( "MY_KEY_PAGING_HTML", pg.getPagingHtml().toString() );
   			//----------------------------------------------------------------------------
   			request.setAttribute("MY_KEY_PALIST", palist);
   			
   			//목록보기 페이지로 포워딩
   			request.getRequestDispatcher("/erp/hr/personnelAppointments/personnelAppointments_list.jsp").forward(request, response);

   		//e.of.null이나공백
   			
   		//   -------------------------------------------------------------- 해당 인사발령내역 상세보기 T			
   		
   		} else if (pageGubun.equals("T001")) {	//셀렉트
   			
   			int pa_seq = 1;
   			
   			// pa_seq 받기
   			String pa_seqStr = request.getParameter("pa_seq");
   			if (pa_seqStr != null && !pa_seqStr.equals(""))  {
   				pa_seq = Integer.parseInt(pa_seqStr);
   				
   				PaVO pavo = padao.detailSelect(pa_seq);
   				request.setAttribute("MY_KEY_PAVO", pavo);		// 해당 인사발령 상세내용
   				
   				// 인사카드 상세페이지로 포워딩
   	   			request.getRequestDispatcher("/erp/hr/personnelAppointments/personnelAppointments_detail.jsp").forward(request, response);
   	   			
   			} else {
   				response.sendRedirect("/404.html");
   			}
		
   		} //e.of.T001
	} //e.of.doGet
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	


//		System.out.println("POST 메서드 호출됨");

		//페이지 구분자부터 받기
   		String pageGubun = request.getParameter("pageGubun");
   		

   		PersonnelAppointmentsDAO padao = new PersonnelAppointmentsDAO();
//   		System.out.println("페이지 구분자는 : -" + pageGubun + "-");
   		

   		if (pageGubun == null) {
   			response.sendRedirect("/500.html");
   			
   		//e.of.null
   			
   		// ----------------------------------------------------------- 인사발령 등록 I

   		} else if (pageGubun.equals("I001")) {
   			
   			BufferedReader reader = request.getReader();
   			StringBuilder jsonData = new StringBuilder();
   		    String line;
   		    while ( (line = reader.readLine()) != null ) {
   		           jsonData.append(line);
   		    }
   		    
   		    //값이 제대로 들어왔나 확인
   		    String jsonString = jsonData.toString();
//   		    System.out.println("들어온 값: " + jsonString);
   			
   			ObjectMapper om = new ObjectMapper();
   			
//   			ObjectMapper가 필드를 자동으로 Date 타입으로 변환하도록 추가적인 설정
//   			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//   			om.setDateFormat(sdf);
   			
//			JSON 데이터를 Java 객체로 변환
			PaVO pvo = om.reader().readValue(jsonString, PaVO.class);
			System.out.println("Parsed PaVO: " + pvo);


   			int res = padao.insert(pvo);
   			System.out.println("DB에 입력완료 : " + res + "건");
   			

<<<<<<< HEAD
=======
   			
//   			System.out.println("Parsed PaVO: " + pvo);
   			
   			int res = padao.insert(pvo);
   			
>>>>>>> 12b35d4147d58a2f9613f9ed90989813afcbd4f4
   			// 응답 타입을 JSON 객체로 설정
   			response.setContentType("application/json; charset=UTF-8");

   			//정상적으로 DB에 입력 된 경우
			if (res > 0) {
				response.getWriter().write("{\"status\":1, \"message\":\"인사발령 등록 성공\"}");
			} else {
	        	response.getWriter().write("{\"status\":0, \"message\":\"인사발령 등록 실패\"}");
   			}


	//e.of.I001
			
   	// -------------------------------------------------------- 인사발령 선택 삭제 D001

		} else if (pageGubun.equals("D001")) {
//			request.setCharacterEncoding("UTF-8");
			
	        // 전송된 배열 데이터(valueArr) 받기
	        String[] valueArr = request.getParameterValues("valueArr");
	        int size = valueArr.length;
	        
//	        System.out.println(
//	        		"삭제할 데이터: " + Arrays.toString(valueArr)
//	        		); // valueArr 값 잘 들어왔는지 확인 로그
//	        
	        
	        boolean allDeleted = true; // 모두삭제 성공 여부 플래그
	        int res = 0;	//삭제건수
	        
	        if (valueArr != null && valueArr.length > 0) {
		        for(int i=0; i < size; i++) {
		        	// 삭제 처리
		        	res = padao.delete(valueArr[i]);
		        	
		        	// 삭제 결과 확인
//		        	System.out.println("삭제시도: " + valueArr[i] +"번 항목"+ ", 삭제결과: " + res);
		        	 
		        	
	                if (res <= 0) { // 하나라도 삭제 실패 시
	                    allDeleted = false;
	                    break;		//for 루프를 바로 종료
	                }
		        }
		        
		        // 응답 타입을 JSON 객체로 설정
		        response.setContentType("application/json; charset=UTF-8");
		        
		        // 모든 삭제가 이루어진 후 1 반환, 실패시 0 반환
		        if (allDeleted) {
		        	response.getWriter().write("{\"status\":1, \"message\":\"모두 삭제 성공\"}");
		        
		        } else {
		        	response.getWriter().write("{\"status\":0, \"message\":\"삭제 실패\"}");
		        }
	        
	        } else { // valueArr가 null이거나 비어있다면 에러 페이지로 이동
	        	response.sendRedirect("/500.html");
		    }
		} //e.of.D001
			
//		 ----------------------------------------------------------- 그외
		else {
			response.sendRedirect("/500.html");
		}
		
		
		
		
		
	} //e.of.doPost
	
}
