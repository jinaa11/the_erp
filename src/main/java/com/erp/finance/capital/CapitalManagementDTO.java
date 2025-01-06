package com.erp.finance.capital;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CapitalManagementDTO {
	
	private long capitalManagementSeq;
    private Date manageAt;
    private String summary;
    private String capitalType;
    private long cost;
    private String paymentType;
    private String status;
    private long referenceSeq;
    
    
    
    // 내가 선택한 물건/시설 중에서 가장 나중에 입력된 값 가져오는 코드 
    public static String findMaxCapital() {
        return "SELECT * FROM ("
               + "    SELECT * "
               + "    FROM capital_management "
               + "    WHERE capital_type = ? "
               + "    AND reference_seq = ? "
               + "    ORDER BY capital_management_seq DESC"
               + ") "
               + "WHERE ROWNUM = 1";
    }

    // Insert Query
    public static String getInsertCapitalManagementQuery() {
        return "INSERT INTO capital_management "
             + "(capital_management_seq, manage_at, summary, capital_type, cost, payment_type, status, reference_seq) "
             + "VALUES (capital_management_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
    }
  
    // findALl Query
    public static String findAllCapital() {
        return "select * from capital_management where capital_type = ?"
        		+ " and eference_seq = ? "
        		+ "order by capital_management_seq desc";
        
    }
    
    
    
    public static CapitalManagementDTO fromResultSet(ResultSet rs) throws SQLException {
        return new CapitalManagementDTO(
            rs.getLong("capital_management_seq"),
            rs.getTimestamp("manage_at"),
            rs.getString("summary"),
            rs.getString("capital_type"),
            rs.getLong("cost"),
            rs.getString("payment_type"),
            rs.getString("status"),
            rs.getLong("reference_seq")
        );
    }

    
    
    public Object[] getAttributeAsObjectArray() {
        return new Object[] {
            this.manageAt,
            this.summary,
            this.capitalType,
            this.cost,
            this.paymentType,
            this.status,
            this.referenceSeq
        };
    }

    
}


