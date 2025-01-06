package com.erp.facility.common;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.erp.common.rest.RestBusinessException;
import com.erp.common.rest.RestBusinessException.StatusCode;

/**
 * Description : Query To DTO Converter<br>
 * Date : 2024. 12. 29.<br>
 * History :<br>
 * - 작성자 : 최영범 , 날짜 : 2024. 12. 29.<br>
 * 설명 : HTTP 요청의 쿼리 파라미터를 DTO 객체로 자동 변환<br>
 * 
 * 반드시!!! 쿼라파리미터의 Key의 이름과 DTO변수의 이름이 같아야 한다!!!!
 * 
 * @param request HTTP 요청 객체
 * @param dtoClass 변환할 DTO 클래스 타입
 * @param excludeParams 변환 제외할 필드명들 (가변 인자)
 * @return <T> 변환된 DTO 객체
 * @throws RuntimeException DTO 변환 중 발생하는 모든 예외
 * 
 * 기능:<br>
 * - 리플렉션을 사용한 동적 필드 매핑<br>
 * - 자동 타입 변환 (String → int, long, Date 등)<br>
 * - null 값 처리<br>
 * - 제외 필드 지정 가능<br>
 * 
 * 지원하는 타입:<br>
 * - String<br>
 * - Integer/int<br>
 * - Long/long<br>
 * - Date (형식: yyyy-MM-dd)<br>
 * 
 * 사용 예시:<br>
 * FacilityDTO dto = DtoConverter.convertToDto(request, FacilityDTO.class, "action");<br>
 * 
 * @author 최영범  
 * @version 1.0
 */


public class DtoConverter {
	
	// 매핑 
	public static <T> T convertToDto(HttpServletRequest request, Class<T> dtoClass, String... excludeParams) {
	    try {
	        T dto = dtoClass.getDeclaredConstructor().newInstance();
	        
	        // 제외할 객체 
	        Set<String> excludeSet = new HashSet<>(Arrays.asList(excludeParams));	        
	        for (Field field : dtoClass.getDeclaredFields()) {
	        	
	        	// private 접근 가능
	            field.setAccessible(true);
	            
	            // 예외로 매핑 하지 않을 값이 있다면 
	            String fieldName = field.getName();	            
	            if (excludeSet.contains(fieldName)) continue;
	            
	            String paramValue = request.getParameter(fieldName);
	            
	            if (paramValue != null && !paramValue.isEmpty()) {
	                Object convertedValue = convertValue(paramValue, field.getType());
	                field.set(dto, convertedValue);
	            }
	        }        
	        return dto;
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        throw new RestBusinessException(StatusCode.DTO_CONVERT_ERROR);
	        
	    }
	}
	
	// 타입 파싱 
	private static Object convertValue(String value, Class<?> targetType) {
	    try {
	        if (targetType == String.class) return value;
	        if (targetType == int.class || targetType == Integer.class)	        	
	        	return Integer.parseInt(value.trim());	      
	        
	        if (targetType == long.class || targetType == Long.class) 	        	
	        	return Long.parseLong(value.trim());
	        
	        if (targetType == Date.class) 
	        	return new SimpleDateFormat("yyyy-MM-dd").parse(value); 
	        
	        throw new RestBusinessException(StatusCode.DTO_CONVERT_ERROR);
	    } catch (Exception e) {
	    	 throw new RestBusinessException(StatusCode.DTO_CONVERT_ERROR);
	    }
	}

}
