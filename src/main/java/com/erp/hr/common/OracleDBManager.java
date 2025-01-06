package com.erp.hr.common;

import java.sql.*;
import java.io.*;
import java.util.Properties;

public class OracleDBManager implements DBManager {

	private String DB_URL;
	private String DB_ID;
	private String DB_PW;
	
	// 싱글톤 변수 선언
	private static OracleDBManager instance;
	
	// 프라이빗 기본 생성자
	private OracleDBManager() {}
	
	//싱글톤 변수 가져갈 수 있는 메소드 getter
	public static synchronized OracleDBManager getInstance() {
		if (instance == null) {
			instance = new OracleDBManager();
		}
		return instance;
	}
	
//---------------------------------------------------------------------------	
	
	@Override
	public Connection connect() {
		System.out.println("연결완료");
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//------------- DB 설정 읽어오기 -----------------------
			Properties props = new Properties();
			props.load(OracleDBManager.class.getClassLoader().getResourceAsStream("mydb.properties"));
			DB_URL = props.getProperty("oracle.url");
			DB_ID  = props.getProperty("oracle.id");
			DB_PW  = props.getProperty("oracle.pw");
			System.err.println("properties 로드........" + DB_URL);
			
			conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
			
			if(conn != null)
				System.out.println("오라클 연결성공");
			else
				System.out.println("오라클 연결실패");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}

	
//---------------------------------------------------------------------------	
	
	@Override
	public void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs != null) 		rs.close();
			if(pstmt != null)   pstmt.close();
			if(conn != null)    conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void close(Connection conn, PreparedStatement pstmt) {
		try {
			if(pstmt != null)   pstmt.close();
			if(conn != null)    conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close(Connection conn, PreparedStatement pstmt, PreparedStatement pstmt2) {
		try {
			if(pstmt2 != null)   pstmt.close();
			if(pstmt != null)   pstmt.close();
			if(conn != null)    conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

}
