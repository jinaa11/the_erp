package com.erp.facility.Service.impl;

import com.erp.facility.Repository.ProductRepository;
import com.erp.facility.Service.ProductService;
import com.erp.facility.VO.ProductDTO.CreateProductManagementRequestDTO;
import com.erp.facility.VO.ProductDTO.CreateProductRequestDTO;
import com.erp.facility.VO.ProductDTO.GetProductManagementsResponseDTO;
import com.erp.facility.VO.ProductDTO.UpdateProductManagementRequestDTO;
import com.erp.facility.VO.ProductDTO.UpdateProductRequestDTO;

import static com.erp.facility.VO.ProductDTO.*;

public class ProductServiceImpl implements ProductService{
	
	private static final ProductRepository productRepository = new ProductRepository();
	@Override
	public void createProduct(CreateProductRequestDTO requestDto) {
		productRepository.createProduct(requestDto);
	}
	
	@Override
	public GetProductsResponseDTO getProducts() {
		return productRepository.getProducts();
	}

	@Override
	public void updateProduct(UpdateProductRequestDTO requestDTO) {
		productRepository.updateProduct(requestDTO);
	}

	@Override
	public void deleteProduct(int productSeq) {
		productRepository.deleteProductManagement(productSeq);
	}

	@Override
	public void createProductManagement(CreateProductManagementRequestDTO requestDto) {
		productRepository.createProductManagement(requestDto);
		
	}

	@Override
	public GetProductManagementsResponseDTO getProductManagement() {
		return productRepository.getProductManagements();
	}

	@Override
	public void updateProductManagement(UpdateProductManagementRequestDTO requestDto) {
		productRepository.updateProductManagement(requestDto);
	}

	@Override
	public void deleteProductManagement(int productManagementSeq) {
		productRepository.deleteProductManagement(productManagementSeq);
	}
	
}