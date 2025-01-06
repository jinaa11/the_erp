package com.erp.facility.Service.impl;

import java.util.List;

import com.erp.facility.Repository.MaintenanceRepository;
import com.erp.facility.Service.MaintenanceService;
import com.erp.facility.VO.MaintenanceDTO;
import com.erp.facility.dto.CommomGetDTO;
import com.erp.finance.capital.CapitalManagementDTO;


public class MaintenanceServiceImpl implements MaintenanceService{

	private static final MaintenanceRepository maintenanceRepository = new MaintenanceRepository();
	
	
	@Override
	public  int save(CommomGetDTO commomGetDTO) {

		MaintenanceDTO maintenanceDTO = new MaintenanceDTO();
		CapitalManagementDTO capitalManagement = new CapitalManagementDTO();
		
		maintenanceDTO.setFacilityId(commomGetDTO.getReferenceSeq());
		maintenanceDTO.setWorkStatus(commomGetDTO.getWorkStatus());
	
		maintenanceRepository.maintSave(maintenanceDTO);
		
		MaintenanceDTO mSeq = maintenanceRepository.findOneMm(commomGetDTO.getReferenceSeq());
		
		capitalManagement.setManageAt(commomGetDTO.getManageAt());
		capitalManagement.setSummary(commomGetDTO.getSummary());
		capitalManagement.setCapitalType(commomGetDTO.getCapitalType());
		capitalManagement.setCost(commomGetDTO.getCost());
		capitalManagement.setPaymentType(commomGetDTO.getPaymentType());
		capitalManagement.setStatus(commomGetDTO.getStatus());
		capitalManagement.setReferenceSeq(mSeq.getMaintenanceId());
		
		maintenanceRepository.capitaSave(capitalManagement);
	
		return 1;
	
	}

	@Override
	public List<CommomGetDTO> findALl(String seq) {
		return maintenanceRepository.findMaintenance(seq);
	}
	
	

	@Override
	public int update(MaintenanceDTO maintenanceDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CommomGetDTO findOneMax(String seq) {
		return maintenanceRepository.findOneMax(seq);
	}

	@Override
	public MaintenanceDTO findOneMaxm(String seq) {
		// TODO Auto-generated method stub
		return null;
	}

}
