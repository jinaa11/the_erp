/**
 * 
 */
package com.erp.auth.service.impl;

import com.erp.auth.repository.AuthRepository;
import com.erp.auth.service.AuthService;
import com.erp.auth.vo.AuthDTOs.CreateFeatureRoleRequestDTO;
import com.erp.auth.vo.AuthDTOs.CreateRoleRequestDTO;
import com.erp.auth.vo.AuthDTOs.FeaturesResponseDTO;
import com.erp.auth.vo.AuthDTOs.LoginRequestDTO;
import com.erp.auth.vo.AuthDTOs.LoginResponseDTO;
import com.erp.auth.vo.AuthDTOs.PutFeatureRequestDTO;
import com.erp.auth.vo.AuthDTOs.RegisterRequestDTO;
import com.erp.common.rest.RestResponse;
import com.erp.common.security.UserInfo;

/**
 * Description : 클래스에 대한 설명을 입력해주세요.<br>
 * Date : 2024. 12. 26.<br>
 * History :<br>
 * - 작성자 : Kosta, 날짜 : 2024. 12. 26., 설명 : 최초작성<br>
 *
 * @author Kosta
 * @version 1.0
 */
public class AuthServiceImpl implements AuthService{
	private static final AuthRepository authRepository = new AuthRepository();
	
	@Override
	public void register(RegisterRequestDTO dto) {
		authRepository.register(dto);
	}

	@Override
	public UserInfo login(LoginRequestDTO dto) {
		return authRepository.login(dto);
	}

	@Override
	public FeaturesResponseDTO getFeatures() {
		return authRepository.getFeatures();
	}

	@Override
	public void putFeature(PutFeatureRequestDTO requestDto) {
		authRepository.updateFeature(requestDto);
	}

	@Override
	public void deleteFeature(int featureSeq) {
		authRepository.deleteFeature(featureSeq);		
	}

	@Override
	public void createRole(CreateRoleRequestDTO requestDto) {
		authRepository.createUserRole(requestDto);
	}

	@Override
	public void createFeatureRole(CreateFeatureRoleRequestDTO requestDto) {
		authRepository.createFeatureRole(requestDto);
	}
	
}
