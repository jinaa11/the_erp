package com.erp.auth.vo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.erp.common.constant.CommonCode.*;

public class AuthDTOs {

	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	@Setter
	public static class RegisterRequestDTO {
		private String userId;
		private String userPassword;
		private String email;
		private String address;
		private int phoneNumber;
		private String userName;
		private Gender gender;
		private Date birth;

		public String convertToSql() {
			return "INSERT INTO app_users(user_seq, user_id, user_password, email, address, phone_number, register_date, user_status, hire_date, user_name, modify_date, gender, birth) VALUES(app_users_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		}

		public Object[] getAttributeAsObjectArray() {
			return new Object[] { this.userId, BCrypt.hashpw(this.userPassword, BCrypt.gensalt()), this.email,
					this.address, this.phoneNumber, new Date(), UserStatus.ACTIVE.getNum(), new Date(), this.userName,
					new Date(), this.gender.getNum(), this.birth };
		}
	}

	@NoArgsConstructor
	@Builder
	@Setter
	@Getter
	@AllArgsConstructor
	public static class LoginRequestDTO {
		private String userId;
		private String userPassword;

		public String converToSql() {
			return "SELECT user_seq, user_password FROM app_users WHERE user_id = ?";
		}

		public Object[] getAttributeAsObjectArray() {
			return new Object[] { this.userId };
		}
	}

	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	@Getter
	@Setter
	public static class LoginResponseDTO {
		private int userSeq;
		private int[] roles;
	}

	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	@Getter
	@Setter
	public static class FeaturesResponseDTO {
		List<FeatureDetail> datas;

		@NoArgsConstructor
		@Builder
		@AllArgsConstructor
		@Getter
		@Setter
		public static class FeatureDetail {
			private String url;
			private String httpMethod;
			private Date createAt;
			private int roleSeq;
			private int featureSeq;
			private String userName;
		}
	}

	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	@Getter
	@Setter
	public static class PutFeatureRequestDTO {
		private String url;
		private String httpMethod;
		private int roleSeq;
		private int featureSeq;
		
				
		public String convertToSql() {
			return "UPDATE FEATURE_ROLES SET url = ?, method = ?, role_seq = ? WHERE feature_seq = ?";
		}
		
		public Object[] getAttributeAsObjectArray() {
			return new Object[] {this.url, this.httpMethod, this.roleSeq, this.featureSeq};
		}
	}
	
	
	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	@Getter
	@Setter
	public static class CreateRoleRequestDTO {
		private int departmentId;
				
		public String convertToSql() {
			return "INSERT INTO roles VALUES(roles_seq.NEXTVAL, 1, ?, ?)";
		}
		
		public Object[] getAttributeAsObjectArray() {
			return new Object[] {new Date(), this.departmentId};
		}
	}
	
	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	@Getter
	@Setter
	public static class CreateFeatureRoleRequestDTO {
		private String url;
		private String httpMethod;
		private int featureRoleContraintRoleSeq;
		
		public String convertToSql() {
			return "INSERT INTO feature_roles VALUES(feature_roles_seq.NEXTVAL, ?, ?, ?, ?, ?)";
		}
		
		public Object[] getAttributeAsObjectArray(int userSeq) {
			return new Object[] {this.url, this.httpMethod, new Date(), userSeq, this.featureRoleContraintRoleSeq};
		}
	}
	
	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	@Getter
	@Setter
	public static class GetRolesResponseDTO{
		List<roleSummary> roleSummaries; 
		
		@NoArgsConstructor
		@Builder
		@AllArgsConstructor
		@Getter
		@Setter
		public static class roleSummary{
			private int roleSeq;
			private String departmentName;
		}
	}

}