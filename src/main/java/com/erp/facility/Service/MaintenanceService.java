package com.erp.facility.Service;

import java.util.List;

import com.erp.facility.VO.MaintenanceDTO;
import com.erp.facility.dto.CommomGetDTO;


public interface MaintenanceService {
	
//	int save(MaintenanceDTO maintenanceDTO);

	public List<CommomGetDTO> findALl(String seq);	
	int update(MaintenanceDTO maintenanceDTO);
	MaintenanceDTO findOneMaxm(String seq);
	CommomGetDTO findOneMax(String seq);
	int save(CommomGetDTO commomGetDTO);
	
		

}
