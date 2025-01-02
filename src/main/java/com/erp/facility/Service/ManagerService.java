package com.erp.facility.Service;

import java.util.List;

import com.erp.facility.VO.ManagerDTO;

/**
 * Description : Facility에 관한 서비스 모음 <br>
 * Date :  2025. 1. 1.<br>
 * History :<br>
 * - 작성자 :최영범, 날짜 : 2025. 1. 1. 설명 : Manager에 관한 서비스 모음<br>
 *
 * @author 최영범
 * @version 1.0
 */

public interface ManagerService {
	
	ManagerDTO findDetailsManager(int seq);
	List<ManagerDTO> findAll();
	int save(ManagerDTO managerDTO);

}
