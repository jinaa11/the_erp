package com.erp.facility.VO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceDTO {

	private long maintenanceId;
    private long facilityId;
    private String workStatus;
   

    public static String finOneMaxMaintenance() {
    	return "SELECT * FROM ("
    			+ "    SELECT * "
    			+ "    FROM facility_maintenance "
    			+ "    WHERE facility_id = ? "
    			+ "    ORDER BY maintenance_id DESC"
    			+ ") "
    			+ "WHERE ROWNUM = 1"
    			+ "";
    }
    public static String findAllMaintenance() {
        return "select * from facility_maintenance where facility_id = ? order by maintenance_id desc";
    }
    
    
    public static String getInsertMaintenanceQuery() {
        return "INSERT INTO facility_maintenance "
             + "(maintenance_id, facility_id, work_status) "
             + "VALUES (maintenance_seq.NEXTVAL, ?, ?)";
    }

    
//    public static String getInsertMaintenanceQuery() {
//    	return "INSERT INTO facility_maintenance "
//    			+ "(maintenance_id, facility_id, working_date, work_detail, work_status, work_cost) "
//    			+ "VALUES (maintenance_seq.NEXTVAL, ?, ?, ?, ?, ?)";
//    }
    
    public static MaintenanceDTO fromResultSet(ResultSet rs) throws SQLException {
        return new MaintenanceDTO(
            rs.getLong("maintenance_id"),
            rs.getLong("facility_id"),
            rs.getString("work_status")
        );
    }


    
    public Object[] getAttributeAsObjectArray() {
        return new Object[] {
            this.facilityId,
            this.workStatus,
        };
    }
}
