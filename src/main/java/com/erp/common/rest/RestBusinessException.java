package com.erp.common.rest;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
public class RestBusinessException extends RuntimeException {
	private StatusCode statusCode;

	public RestBusinessException(){
		super();
	}
	
	public RestBusinessException(StatusCode statusCode){
		super(statusCode.getDesc());
		this.statusCode = statusCode;
	}
	
	public RestBusinessException(StatusCode statusCode, Exception e){
		super(e);
		this.statusCode = statusCode;
	}
	
	
	
	@AllArgsConstructor
	@Getter
	public static enum StatusCode{
		BAD_REQUEST("잘못된 요청입니다.", 400)
		, CONSTRAINT_VIOLATION("중복된 값을 입력하셨습니다.", 400)
		, DATABASE_UKNOWN_ERROR("데이터베이스에서 알 수 없는 오류가 발생했습니다.", 400)
		, INVALID_IP_PASSWORD("아이디 또는 암호가 올바르지않습니다", 400)
		, DTO_CONVERT_ERROR("DTO 파싱중 오류가 발생하였습니", 400)
		, UNEXPECTED_ERROR("예상치 못한 오류", 400)
		, USER_NOT_EXISTS("없는 사용자입니다.", 400)
		, UNAUTHORIZED("로그인이 필요합니다.", 401)
		, FORBIDDEN("권한이 부족합니다.", 403)
		
		;
		
		private final String desc;
		private final int statusCode;
	}

	public RestResponse<String> convertToRestResponse() {
		return new RestResponse.RestResponseBuilder()
				.resonseDate(new Date())
				.message(this.statusCode.getDesc())
				.data("error")
				.build();
	}

	
	
}