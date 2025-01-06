package com.erp.finance;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.erp.finance.common.PagingUtil;

/**
 * Servlet implementation class ChatMsgServlet
 */
@WebServlet("/finance/*")
public class FinanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FinanceDAO dao = new FinanceDAO();
		String uri = request.getRequestURI();

		// 계정관리
		if (uri.equals("/finance/accounts")) {	
			int currentPage = 1;
		    String currentPageStr = request.getParameter("currentPage");
		    if (currentPageStr != null && !currentPageStr.isEmpty()) {
		        currentPage = Integer.parseInt(currentPageStr);
		    }

		    int blockCount = 10; // 한 페이지당 출력할 레코드 수
		    int blockPage = 10;   // 한 블록당 출력할 페이지 수
		    int totRecord = dao.getAccountsTotalCount(); // 전체 레코드 수 조회

		    PagingUtil pg = new PagingUtil("/finance/accounts", currentPage, totRecord, blockCount, blockPage);

		    // 페이징에 맞는 데이터 조회
		    List<FinanceVO> fList1 = dao.getAccountsByPage(pg.getStartSeq(), pg.getEndSeq());

		    // 데이터 및 페이징 HTML을 request에 추가
		    request.setAttribute("KEY_ACCOUNTS_FLIST", fList1);
		    request.setAttribute("MY_KEY_PAGING_HTML", pg.getPagingHtml().toString());

		    // JSP로 포워드
		    request.getRequestDispatcher("/erp/pages/finance/accounts.jsp").forward(request, response);
		
		    // 전표관리
		} else if (uri.equals("/finance/general_ledger")) {
			List<FinanceVO> fList2 = dao.SlipList();
			request.setAttribute("KEY_SLIP_FLIST", fList2);
			request.getRequestDispatcher("/erp/pages/finance/general_ledger.jsp").forward(request, response);
		
			// 계정별원장
		} else if (uri.equals("/finance/sum_FinanceList")) {
			List<FinanceVO> fList3 = dao.sumFinanceList();
			request.setAttribute("KEY_SUM_FLIST", fList3);
			Map<String, List<FinanceVO>> groupedData = dao.getFinanceDataGroupedByAccountId();
			request.setAttribute("groupedData", groupedData);
			request.getRequestDispatcher("/erp/pages/finance/sum_FinanceList.jsp").forward(request, response);
		
			// 재무상태표
		} else if (uri.equals("/finance/sofp")) {
			List<FinanceVO> fList4 = dao.SoFPList();
			request.setAttribute("KEY_STATE_FLIST", fList4);
			request.getRequestDispatcher("/erp/pages/finance/sofp.jsp").forward(request, response);
		
			// 손익계산서
		} else if (uri.equals("/finance/income_Statement")) {
			List<FinanceVO> fList5 = dao.sumIncomeList();
			request.setAttribute("KEY_INCOME_FLIST", fList5);
			request.getRequestDispatcher("/erp/pages/finance/income_Statement.jsp").forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		FinanceDAO dao = new FinanceDAO();

		// 계정 입력 기능
		if (uri.equals("/finance/add_account")) {
			String accountId = request.getParameter("account_id");
			String accountName = request.getParameter("account_name");
			String accountType = request.getParameter("account_type");
			String parentType = request.getParameter("parent_type");

			FinanceVO accountVO = FinanceVO.builder().account_id(accountId).account_name(accountName)
					.account_type(accountType).parent_type(parentType).build();

			boolean isAdded = dao.addAccount(accountVO);

			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");

			if (isAdded) {
				//System.out.println("응답: success");
				response.getWriter().write("success");
			} else {
				//System.out.println("응답: fail");
				response.getWriter().write("fail");
			}
			return;

		}
//		System.out.println("account_id: " + request.getParameter("account_id"));
//		System.out.println("account_name: " + request.getParameter("account_name"));
//		System.out.println("account_type: " + request.getParameter("account_type"));
//		System.out.println("parent_type: " + request.getParameter("parent_type"));

		// 계정 삭제 기능
		if (uri.equals("/finance/delete_account")) {
		    String accountId = request.getParameter("account_id");

		    response.setContentType("text/plain");
		    response.setCharacterEncoding("UTF-8");

		    if (accountId == null || accountId.isEmpty()) {
		        response.getWriter().write("유효하지 않은 계정 ID입니다.");
		        return;
		    }

		    boolean isDeleted = dao.deleteAccount(accountId);

		    if (isDeleted) {
		        response.getWriter().write("success"); // 삭제 성공
		    } else {
		        response.getWriter().write("fail"); // 삭제 실패
		    }
		    return;
		}
		// 전표 입력 기능
		if (uri.equals("/finance/add_voucher")) {
	        String voucherDate = request.getParameter("voucher_date");
	        String descript = request.getParameter("descript");
	        String debitAccountId = request.getParameter("debit_account_id");
	        String debitAccountName = request.getParameter("debit_account_name");
	        Long debitAmount = Long.parseLong(request.getParameter("debit"));
	        String creditAccountId = request.getParameter("credit_account_id");
	        String creditAccountName = request.getParameter("credit_account_name");
	        Long creditAmount = Long.parseLong(request.getParameter("credit"));

	        FinanceVO debitVO = FinanceVO.builder()
	                .voucher_date(voucherDate)
	                .descript(descript)
	                .account_id(debitAccountId)
	                .account_name(debitAccountName)
	                .debit(debitAmount)
	                .credit(0L)
	                .build();

	        FinanceVO creditVO = FinanceVO.builder()
	                .voucher_date(voucherDate)
	                .descript(descript)
	                .account_id(creditAccountId)
	                .account_name(creditAccountName)
	                .debit(0L)
	                .credit(creditAmount)
	                .build();

	        boolean debitSuccess = dao.addVoucher(debitVO);
	        boolean creditSuccess = dao.addVoucher(creditVO);

	        response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(debitSuccess && creditSuccess ? "success" : "fail");
	        }
		// 전표 삭제 기능
		if (uri.equals("/finance/delete_voucher")) {
	        String voucherDate = request.getParameter("voucher_date");
	        String descript = request.getParameter("descript");
	        String accountId = request.getParameter("account_id");

	        boolean isDeleted = dao.deleteVoucher(voucherDate, descript, accountId);

	        response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(isDeleted ? "success" : "fail");
	        return;
	    }
	}

}
