package com.erp.facility.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommomGetDTO {
	
	
	private long capitalManagementSeq;
    private Date manageAt;
    private String capitalType;
    private long cost;
    private String paymentType;
    private String workStatus;
    private String summary;
    private String status;
    private long referenceSeq;
	private long maintenanceId;
    
    public static String findAllCapital() {
        return "select * from capital_management where capital_type = ?"
        		+ " and eference_seq = ? "
        		+ "order by capital_management_seq desc";
        
    }
    
    
    
    public static String findAllMaintenance() {
        return "select c.*, f.* "
        		+ "from facility_maintenance f, capital_management c "
        		+ "where capital_type = '시설관리' and f.facility_id = ? and f.maintenance_id = c.REFERENCE_SEQ "       		
        		+ "order by CAPITAL_MANAGEMENT_SEQ desc";
    }
    
    
    public static String findMaxCapital() {
        return "SELECT * FROM ("
        		+ "    SELECT c.*, f.*"
        		+ "    FROM facility_maintenance f, capital_management c"
        		+ "    WHERE capital_type = '시설관리' "
        		+ "    AND f.facility_id = ? "
        		+ "    AND f.maintenance_id = c.REFERENCE_SEQ"
        		+ "    ORDER BY CAPITAL_MANAGEMENT_SEQ DESC"
        		+ ") "
        		+ "WHERE ROWNUM = 1";
        		
    }
    
    
    public static CommomGetDTO fromResultSet(ResultSet rs) throws SQLException {
        return new CommomGetDTO(
            rs.getLong("capital_management_seq"),
            rs.getDate("manage_at"),
            rs.getString("capital_type"),
            rs.getLong("cost"),
            rs.getString("payment_type"),
            rs.getString("work_status"),
            rs.getString("summary"),
            rs.getString("status"),
            rs.getLong("reference_seq"),
            rs.getLong("maintenance_id")
        );
    }

}
