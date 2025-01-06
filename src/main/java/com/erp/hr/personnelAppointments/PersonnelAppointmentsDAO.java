package com.erp.hr.personnelAppointments;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
//import java.util.Date;

import com.erp.hr.common.DBManager;
import com.erp.hr.common.OracleDBManager;
import com.erp.hr.employeeCard.HrVO;


public class PersonnelAppointmentsDAO {

	// 전체 인사발령 내역 선택 ============================================================
	public ArrayList<PaVO> totalSelect() {
		
		ArrayList<PaVO> palist = new ArrayList<PaVO>();
		
		DBManager dbm = OracleDBManager.getInstance();
		Connection conn = dbm.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "select p.*, a.user_name as user_name, a.birth as birth\r\n"
					+ "from personnel_appointments_history p, app_users a\r\n"
					+ "where p.user_seq = a.user_seq(+)\r\n"
					+ "order by pah_seq desc";
			
			pstmt =  conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
					PaVO pvo = new PaVO();
						pvo.setPah_seq(rs.getInt("pah_seq"));
						pvo.setPa_date(rs.getDate("pa_date"));
						pvo.setUser_seq(rs.getInt("user_seq"));
						pvo.setUser_name(rs.getString("user_name"));
						pvo.setBirth(rs.getDate("birth"));
						pvo.setBefore_dept(rs.getString("before_dept"));
						pvo.setBefore_position(rs.getString("before_position"));
						pvo.setAssigned_dept(rs.getInt("assigned_dept"));
						pvo.setAssigned_position(rs.getString("assigned_position"));
						pvo.setAssignment_type(rs.getInt("assignment_type"));
						pvo.setNotes(rs.getString("notes"));
					palist.add(pvo);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbm.close(conn, pstmt, rs);
		}
		return palist;
	}


	// 인사발령 내역 페이징 선택 ============================================================
	public ArrayList<PaVO> pagingSelect(int startSeq, int endSeq) {
		
		ArrayList<PaVO> palist = new ArrayList<PaVO>();
		
		DBManager dbm = OracleDBManager.getInstance();
		Connection conn = dbm.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select p.* , a.user_name as user_name, a.birth as birth\r\n"
					+ "from (select personnel_appointments_history.* ,\r\n"
					+ "        ROW_NUMBER() OVER (ORDER BY pah_seq DESC) AS rnum\r\n"
					+ "        from personnel_appointments_history) p, app_users a\r\n"
					+ "where p.user_seq = a.user_seq(+)\r\n"
					+ "and p.rnum between ? and ?\r\n"
					+ "order by pah_seq desc";
			pstmt =  conn.prepareStatement(sql);
			pstmt.setInt(1, startSeq);
			pstmt.setInt(2, endSeq);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PaVO pvo = new PaVO();
					pvo.setPah_seq(rs.getInt("pah_seq"));
					pvo.setPa_date(rs.getDate("pa_date"));
					pvo.setUser_seq(rs.getInt("user_seq"));
					pvo.setUser_name(rs.getString("user_name"));
					pvo.setBirth(rs.getDate("birth"));
					pvo.setBefore_dept(rs.getString("before_dept"));
					pvo.setBefore_position(rs.getString("before_position"));
					pvo.setAssigned_dept(rs.getInt("assigned_dept"));
					pvo.setAssigned_position(rs.getString("assigned_position"));
					pvo.setAssignment_type(rs.getInt("assignment_type"));
					pvo.setNotes(rs.getString("notes"));
				palist.add(pvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbm.close(conn, pstmt, rs);
		}
		
		return palist;
	}

	
	// 인사발령 내역 한개 선택 ============================================================
	public PaVO detailSelect(int pah_seq) {
		
		DBManager dbm = OracleDBManager.getInstance();
		Connection conn = dbm.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		PaVO pvo = new PaVO();
		
		try {
			String sql = "select p.*, a.user_name as user_name, a.birth as birth\r\n"
					+ "from personnel_appointments_history p, app_users a\r\n"
					+ "where p.user_seq = a.user_seq(+) and pah_seq = ?\r\n"
					+ "order by pah_seq desc";
					
			pstmt =  conn.prepareStatement(sql);
			pstmt.setInt(1, pah_seq);
			
			rs = pstmt.executeQuery();
			rs.next();	//한건만 있을때
			
			pvo.setPah_seq(rs.getInt("pah_seq"));
			pvo.setPa_date(rs.getDate("pa_date"));
			pvo.setUser_seq(rs.getInt("user_seq"));
			pvo.setUser_name(rs.getString("user_name"));
			pvo.setBirth(rs.getDate("birth"));
			pvo.setBefore_dept(rs.getString("before_dept"));
			pvo.setBefore_position(rs.getString("before_position"));
			pvo.setAssigned_dept(rs.getInt("assigned_dept"));
			pvo.setAssigned_position(rs.getString("assigned_position"));
			pvo.setAssignment_type(rs.getInt("assignment_type"));
			pvo.setNotes(rs.getString("notes"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbm.close(conn, pstmt, rs);
		}
		return pvo;
	}


	// 인사발령 내역 등록 ============================================================
	public int insert(PaVO pvo) {

		DBManager dbm  = OracleDBManager.getInstance();
		Connection conn = dbm.connect();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		
		int rows = 0;
		
		try {   
			// 인사발령 내역 삽입 SQL
			String sql = "INSERT INTO personnel_appointments_history (\r\n"
					+ "    pah_seq,\r\n"
					+ "    pa_date,\r\n"
					+ "    user_seq,\r\n"
					+ "    before_dept,\r\n"
					+ "    before_position,\r\n"
					+ "    assigned_dept,\r\n"
					+ "    assigned_position,\r\n"
					+ "    assignment_type,\r\n"
					+ "    notes\r\n"
					+ ") VALUES (PA_HISTORY_SEQ.NEXTVAL,?,?,?,?,?,?,?,?)";
			
			// 사용자 정보 업데이트 SQL
			String sql2 = "UPDATE app_users\r\n"
					+ "SET department_id = ? ,\r\n"
					+ "	position = ? ,\r\n"
					+ "	modify_date = SYSDATE\r\n"
					+ "WHERE user_seq = ?";
			
			System.out.println("pvo dao = " +pvo);
			
			// 첫 번째 PreparedStatement 설정
			pstmt =  conn.prepareStatement(sql);
			
				pstmt.setDate(1, pvo.getPa_date());
				pstmt.setInt(2, pvo.getUser_seq());
				pstmt.setString(3, pvo.getBefore_dept());
				pstmt.setString(4, pvo.getBefore_position());
				pstmt.setInt(5, pvo.getAssigned_dept());
				pstmt.setString(6, pvo.getAssigned_position());
				pstmt.setInt(7, pvo.getAssignment_type());
				pstmt.setString(8, pvo.getNotes());
			
			// 두 번째 PreparedStatement 설정
			pstmt2 =  conn.prepareStatement(sql2);
			
				pstmt2.setInt(1, pvo.getAssigned_dept());
				pstmt2.setString(2, pvo.getAssigned_position());
				pstmt2.setInt(3, pvo.getUser_seq());
				
	        // 트랜잭션 시작
	        conn.setAutoCommit(false);

	        // 첫 번째 쿼리 실행
	        rows = pstmt.executeUpdate();

	        // 두 번째 쿼리 실행
	        pstmt2.executeUpdate();

	        // 트랜잭션 커밋
	        conn.commit();
	        
	    } catch (SQLException e) {
	        // 예외 발생 시 롤백
	        if (conn != null) {
	            try {
	                conn.rollback();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        e.printStackTrace();
	    } finally {
	        // 리소스 해제
	        dbm.close(conn, pstmt, pstmt2);
	    }
	    return rows;
	}



//	// 인사발령 내역 삭제 ============================================================
//	public int delete(int pah_seq) {
//
//		DBManager dbm  = OracleDBManager.getInstance();
//		Connection conn = dbm.connect();
//		PreparedStatement pstmt = null;
//		int rows = 0;
//		try {
//			String sql = "delete from personnel_appointments_history where pah_seq = ?";
//			pstmt =  conn.prepareStatement(sql);
//			pstmt.setInt(1, pah_seq);
//			
//			rows = pstmt.executeUpdate();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			dbm.close(conn, pstmt);
//		}
//		return rows;
//	}
//	
	// 인사발령 내역 선택 삭제 ============================================================
	public int delete(String pah_seq) {

		DBManager dbm  = OracleDBManager.getInstance();
		Connection conn = dbm.connect();
		PreparedStatement pstmt = null;
		int rows = 0;
		try {
			String sql = "delete from personnel_appointments_history where pah_seq = ?";
			pstmt =  conn.prepareStatement(sql);
			pstmt.setString(1, pah_seq);
			
			rows = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbm.close(conn, pstmt);
		}
		return rows;
	}
	
	
	
	
//	// DAO 테스트	
//	public static void main(String[] args) {
//  	
//		PersonnelAppointmentsDAO padao = new PersonnelAppointmentsDAO();

//      // 1. 인사발령 목록 페이징 조회 테스트

//      }

//      // 2. 인사발령 내역 전체 조회 테스트
//      System.out.println("=== 인사카드 전체 조회 ===");
//      ArrayList<PaVO> fullList = padao.totalSelect();
//      for (PaVO pvo : fullList) {
//          System.out.println("인사발령내역 전체 조회 - " + pvo.toString());
//      }

//      // 3. 특정 인사발령 내역 조회 테스트
//      System.out.println("=== 특정 인사발령 조회 ===");
//      int pah_seq = 1; 	// 테스트할 user_seq 값
//      PaVO singleInfo = padao.detailSelect(pah_seq);
//      System.out.println(singleInfo.toString());

//    	4. 인사발령 등록 테스트
//		System.out.println("=== 인사카드 등록 테스트 ===");
//		PersonnelAppointmentsDAO paDAO = new PersonnelAppointmentsDAO();  // PaDAO 객체 생성
//        PaVO pvo = new PaVO();  // PaVO 객체 생성
//
//        // PaVO 객체에 테스트 데이터를 세팅
//        pvo.setPa_date(java.sql.Date.valueOf("2025-05-01"));  // 예시 날짜
//        pvo.setUser_seq(160);  			// 예시 user_seq
//        pvo.setBefore_dept("인사팀");  	// 예시 이전 부서
//        pvo.setBefore_position("대리");  // 예시 이전 직급
//        pvo.setAssigned_dept("인사팀");  // 예시 배정 부서
//        pvo.setAssigned_position("과장");  // 예시 배정 직급
//        pvo.setAssignment_type(2);  // 예시 배정 유형
//        pvo.setNotes("또승진이에요");  // 설명
//
//        // PaDAO의 insert 메소드 실행
//        int rows = paDAO.insert(pvo);
//        
//        // 결과 확인
//        if (rows > 0) {
//            System.out.println("인사발령 내역 등록 성공!");
//        } else {
//            System.out.println("인사발령 내역 등록 실패!");
//        }


//      // 5. 인사발령 삭제 테스트
//      System.out.println("=== 인사카드 삭제 테스트 ===");
//      int deletePah_seq = 4; // 삭제할 user_seq 값
//      int deleteResult = padao.delete(deletePah_seq);
//      System.out.println("Delete 결과: " + deleteResult);
      
//  }	

}
