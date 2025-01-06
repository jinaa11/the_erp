/**
 * 
 */
package com.erp.auth.service;

import com.erp.auth.vo.AuthDTOs.CreateFeatureRoleRequestDTO;
import com.erp.auth.vo.AuthDTOs.CreateRoleRequestDTO;
import com.erp.auth.vo.AuthDTOs.FeaturesResponseDTO;
import com.erp.auth.vo.AuthDTOs.GetRolesResponseDTO;
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
public interface AuthService {
	
	FeaturesResponseDTO getFeatures();
	void putFeature(PutFeatureRequestDTO requestDto);
	void register(RegisterRequestDTO dto);
	UserInfo login(LoginRequestDTO dto);
	void deleteFeature(int featureSeq);
	void createRole(CreateRoleRequestDTO requestDto);
	void createFeatureRole(CreateFeatureRoleRequestDTO requestDto);
	GetRolesResponseDTO getRoles();
}
