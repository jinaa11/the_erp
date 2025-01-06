package com.erp.hr.personnelAppointments;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	
public class PaVO {
		private int    pah_seq;				//시퀀스
		private Date   pa_date;				//발령 일자
		private int    user_seq;			//사원번호
		private String user_name;			//이름
		private Date   birth;				//생년월일
		private String before_dept;			//이전 부서
		private String before_position;		//이전 직급
		private int assigned_dept;			//발령 부서
		private String assigned_position;	//발령 직급
		private int    assignment_type;		//발령구분
		private String notes;				//설명
}
