package com.erp.facility.Service;

import static com.erp.facility.VO.ProductDTO.*;


public interface ProductService{
	void createProduct(CreateProductRequestDTO requestDto);
	GetProductsResponseDTO getProducts();
	void updateProduct(UpdateProductRequestDTO requestDTO);
	void deleteProduct(int productSeq);
	
	void createProductManagement(CreateProductManagementRequestDTO requestDto);
	GetProductManagementsResponseDTO getProductManagement();
	void updateProductManagement(UpdateProductManagementRequestDTO requestDto);
	void deleteProductManagement(int productManagementSeq);
}