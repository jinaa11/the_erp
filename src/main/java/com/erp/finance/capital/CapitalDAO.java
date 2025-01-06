package com.erp.finance.capital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.erp.common.rest.RestBusinessException;
import com.erp.common.rest.RestBusinessException.StatusCode;
import com.erp.finance.FinanceVO;
import com.erp.finance.common.DBManager;
import com.erp.finance.common.OracleDBManager;

public class CapitalDAO {
	
	
	public int statusUpdate(String sequence, String updateValue ) {
	    DBManager dbm = OracleDBManager.getInstance();
	    Connection conn = dbm.connect();
	    PreparedStatement pstmt = null;
	    boolean isSuccess = false;

	    try {
	    	String query = "update capital_management set status = ? where capital_management_seq = ?";

	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, updateValue);
	        pstmt.setString(2, sequence);


	        return pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        dbm.close(conn, pstmt, null);
	    }
	    throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
	}
	
	
	
	
	
	
	public ArrayList<CapitalManagementDTO> findAll()  {
    	DBManager dbm = OracleDBManager.getInstance();  	//new OracleDBManager();
		Connection conn = dbm.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CapitalManagementDTO> cList = new ArrayList<CapitalManagementDTO>();
		try {
			String query = "select * from capital_management order by manage_at";
        	
			pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	CapitalManagementDTO vo = new CapitalManagementDTO();
            	vo.setCapitalManagementSeq(rs.getLong("CAPITAL_MANAGEMENT_SEQ"));
            	vo.setManageAt(rs.getDate("MANAGE_AT")); 
            	vo.setSummary(rs.getString("SUMMARY"));
            	vo.setCapitalType(rs.getString("CAPITAL_TYPE"));
            	vo.setCost(rs.getLong("COST"));
            	vo.setPaymentType(rs.getString("PAYMENT_TYPE"));
            	vo.setStatus(rs.getString("STATUS"));
            	vo.setReferenceSeq(rs.getLong("REFERENCE_SEQ"));
            	cList.add(vo);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
				dbm.close(conn, pstmt, rs);
		}
		return cList;
    }
	
	public ArrayList<CapitalManagementDTO> findAllByCapitalType(List<String> capitalTypes, List<String> statuses) {
	    DBManager dbm = OracleDBManager.getInstance();
	    Connection conn = dbm.connect();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    ArrayList<CapitalManagementDTO> cList = new ArrayList<CapitalManagementDTO>();
	    
	    try {
	        StringBuilder query = new StringBuilder("SELECT * FROM capital_management WHERE 1=1");
	        List<Object> params = new ArrayList<>();
	        
	        if (capitalTypes != null && !capitalTypes.isEmpty()) {
	            query.append(" AND capital_type IN (");
	            for (int i = 0; i < capitalTypes.size(); i++) {
	                query.append(i == 0 ? "?" : ",?");
	                params.add(capitalTypes.get(i));
	            }
	            query.append(")");
	        }
	        
	        if (statuses != null && !statuses.isEmpty()) {
	            query.append(" AND status IN (");
	            for (int i = 0; i < statuses.size(); i++) {
	                query.append(i == 0 ? "?" : ",?");
	                params.add(statuses.get(i));
	            }
	            query.append(")");
	        }
	        
	        query.append(" ORDER BY manage_at");
	        
//	       System.out.println("Seleter Find Query = " + query);
	        
	        pstmt = conn.prepareStatement(query.toString());
	        
	        for (int i = 0; i < params.size(); i++) {
	            pstmt.setObject(i + 1, params.get(i));
	        }
	        
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            CapitalManagementDTO vo = new CapitalManagementDTO();
	            vo.setCapitalManagementSeq(rs.getLong("CAPITAL_MANAGEMENT_SEQ"));
            	vo.setManageAt(rs.getDate("MANAGE_AT")); 
            	vo.setSummary(rs.getString("SUMMARY"));
            	vo.setCapitalType(rs.getString("CAPITAL_TYPE"));
            	vo.setCost(rs.getLong("COST"));
            	vo.setPaymentType(rs.getString("PAYMENT_TYPE"));
            	vo.setStatus(rs.getString("STATUS"));
            	vo.setReferenceSeq(rs.getLong("REFERENCE_SEQ"));
            	cList.add(vo);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        dbm.close(conn, pstmt, rs);
	    }
	    return cList;
	}

}
