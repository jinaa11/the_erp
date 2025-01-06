package com.erp.hr.common;

import java.sql.*;

public interface DBManager {
	public Connection connect();
	public void close(Connection conn, PreparedStatement pstmt, ResultSet rs);
	public void close(Connection conn, PreparedStatement pstmt);
	public void close(Connection conn, PreparedStatement pstmt, PreparedStatement pstmt2);
}