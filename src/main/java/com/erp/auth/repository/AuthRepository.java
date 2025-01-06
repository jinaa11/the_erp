package com.erp.auth.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.erp.auth.vo.AuthDTOs.CreateFeatureRoleRequestDTO;
import com.erp.auth.vo.AuthDTOs.CreateRoleRequestDTO;
import com.erp.auth.vo.AuthDTOs.FeaturesResponseDTO;
import com.erp.auth.vo.AuthDTOs.FeaturesResponseDTO.FeatureDetail;
import com.erp.auth.vo.AuthDTOs.GetRolesResponseDTO;
import com.erp.auth.vo.AuthDTOs.GetRolesResponseDTO.roleSummary;
import com.erp.auth.vo.AuthDTOs.LoginRequestDTO;
import com.erp.auth.vo.AuthDTOs.LoginResponseDTO;
import com.erp.auth.vo.AuthDTOs.PutFeatureRequestDTO;
import com.erp.auth.vo.AuthDTOs.RegisterRequestDTO;
import com.erp.common.database.DBManager;
import com.erp.common.database.StatementProvider;
import com.erp.common.database.impl.OracleDBManager;
import com.erp.common.database.impl.StatementProviderDefaultImpl;
import com.erp.common.rest.RestBusinessException;
import com.erp.common.rest.RestBusinessException.StatusCode;
import com.erp.common.security.SecurityContext;
import com.erp.common.security.UserInfo;

public class AuthRepository {

	private static final DBManager db = new OracleDBManager();
	private static final StatementProvider sp = new StatementProviderDefaultImpl();

	public FeaturesResponseDTO getFeatures() {
		FeaturesResponseDTO response = new FeaturesResponseDTO();
		response.setDatas(new ArrayList<>());
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = con.prepareStatement(
						"SELECT fr.*, stb.user_name FROM FEATURE_ROLES fr INNER JOIN (SELECT user_name, user_seq FROM app_users) stb ON(fr.register_user = stb.user_seq)");
//					= sp.getPreparedStatement(con, "SELECT fr.*, stb.user_name FROM FEATURE_ROLES fr INNER JOIN (SELECT user_name, user_seq FROM app_users) stb ON(fr.register_user = stb.user_seq)", new Object[0]);
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				/*
				 * FEATURE_SEQ NOT NULL NUMBER URL NOT NULL VARCHAR2(50) METHOD NOT NULL
				 * VARCHAR2(10) REGISTER_DATE NOT NULL DATE REGISTER_USER NOT NULL NUMBER
				 * ROLE_SEQ NOT NULL NUMBER
				 * 
				 * 
				 * 
				 * private String url; private String httpMethod; private Date createAt; private
				 * int roleSeq; private int featureSeq; private String userName;
				 */
				response.getDatas()
						.add(FeatureDetail.builder().url(rs.getString("url")).httpMethod(rs.getString("method"))
								.createAt(rs.getDate("register_date")).roleSeq(rs.getInt("role_seq"))
								.featureSeq(rs.getInt("feature_seq")).userName(rs.getString("user_name")).build());
			}
			return response;
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}

	public void register(RegisterRequestDTO requestDto) {
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = sp.getPreparedStatement(con, requestDto.convertToSql(),
						requestDto.getAttributeAsObjectArray());) {
			int rows = ps.executeUpdate();
			System.out.println(rows);
			if (rows == 1)
				con.commit();
			else {
				con.rollback();
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}

	public UserInfo login(LoginRequestDTO requestDto) {
		try (Connection con = db.getConnection();
				PreparedStatement ps = sp.getPreparedStatement(con, requestDto.converToSql(),
						requestDto.getAttributeAsObjectArray());
				ResultSet rs = ps.executeQuery();) {
			int userSeq = -1;
			String userPassword = null;
			while (rs.next()) {
				userSeq = rs.getInt("user_seq");
				userPassword = rs.getString("user_password");
			}
			if (userSeq == -1 || userPassword == null || !BCrypt.checkpw(requestDto.getUserPassword(), userPassword)) {
				throw new RestBusinessException(StatusCode.INVALID_IP_PASSWORD);
			}
			return UserInfo.builder().userSeq(userSeq).roles(getUserRole(userSeq)).build();
		} catch (SQLException e) {
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}

	private int[] getUserRole(int userSeq) {
		try (Connection con = db.getConnection();
				PreparedStatement ps = sp.getPreparedStatement(con,
						"SELECT role_seq FROM user_roles WHERE user_seq = ?", userSeq);
				ResultSet rs = ps.executeQuery();) {
			List<Integer> roleList = new ArrayList<>();
			while (rs.next()) {
				roleList.add(rs.getInt("role_seq"));
			}
			if (roleList.isEmpty())
				return new int[] { -1 };
			roleList.add(-1);
			return roleList.stream().mapToInt(Integer::intValue).toArray();
		} catch (SQLException e) {
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}

	public void updateFeature(PutFeatureRequestDTO requestDto) {
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = sp.getPreparedStatement(con, requestDto.convertToSql(),
						requestDto.getAttributeAsObjectArray());) {
			int rows = ps.executeUpdate();
			if (rows == 1)
				con.commit();
			else {
				con.rollback();
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}

	public void deleteFeature(int featureSeq) {
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = sp.getPreparedStatement(con, "DELETE FROM feature_roles WHERE feature_seq = ?",
						new Object[] { featureSeq });) {
			int rows = ps.executeUpdate();
			if (rows == 1)
				con.commit();
			else {
				con.rollback();
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}

	}
	
	
	public void createUserRole(CreateRoleRequestDTO requestDto) {
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = sp.getPreparedStatement(con, requestDto.convertToSql(),
						requestDto.getAttributeAsObjectArray());) {
			int rows = ps.executeUpdate();
			if (rows == 1)
				con.commit();
			else {
				con.rollback();
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
	
	public void createFeatureRole(CreateFeatureRoleRequestDTO requestDto) {
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = sp.getPreparedStatement(con, requestDto.convertToSql(),
						requestDto.getAttributeAsObjectArray(SecurityContext.getCurrentUser().getUserSeq()));) {
			int rows = ps.executeUpdate();
			if (rows == 1)
				con.commit();
			else {
				con.rollback();
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
	
	public int getRoleSeq(String url, String httpMethod) {
		try (Connection con = db.getConnection();
				PreparedStatement ps = sp.getPreparedStatement(con,
						"SELECT role_seq FROM feature_roles WHERE url = ? AND method = ?", new Object[] {url, httpMethod});
				ResultSet rs = ps.executeQuery();) {
			int result = -1;
			while (rs.next()) {
				result = rs.getInt("role_seq");
			}
			return result;
		} catch (SQLException e) {
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
	
	public GetRolesResponseDTO getRoles() {
		GetRolesResponseDTO response = new GetRolesResponseDTO();
		response.setRoleSummaries(new ArrayList<>());
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = con.prepareStatement(
						"SELECT * FROM(SELECT role_seq, department_id FROM roles) str LEFT JOIN (SELECT department_id, name FROM department) std USING(department_id)");
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				
				response.getRoleSummaries()
						.add(roleSummary.builder()
								.roleSeq(rs.getInt("role_seq"))
								.departmentName(rs.getString("name"))
								.build());
			}
			return response;
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
	

	/*
	 * 
	 * CREATE TABLE user_roles ( user_role_seq NUMBER NOT NULL PRIMARY KEY, order_id
	 * NUMBER NOT NULL, register_date DATE NOT NULL, role_seq NUMBER NOT NULL,
	 * user_seq NUMBER NOT NULL, CONSTRAINT fk_user_roles_role FOREIGN KEY
	 * (role_seq) REFERENCES roles(role_seq), CONSTRAINT fk_user_roles_user FOREIGN
	 * KEY (user_seq) REFERENCES app_users(user_seq) );
	 *
	 */

}