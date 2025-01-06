package com.erp.facility.VO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class ProductDTO {
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CreateProductRequestDTO {
		private String productName;
		private String productSummary;
		
		public String convertToSql() {
			return "INSERT INTO products values(product_seq.NEXTVAL, ?, ?)";
		}
		
		public Object[] getAttributeAsObjectArray() {
			return new Object[] {this.productName, this.productSummary};
		}
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class GetProductsResponseDTO{
		List<Product> products;
		@Getter
		@Setter
		@NoArgsConstructor
		@AllArgsConstructor
		@Builder
		public static class Product{
			private int productSeq;
			private String productName;
			private String productSummary;
		}
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UpdateProductRequestDTO {
		private int productSeq;
		private String productName;
		private String productSummary;
		
		public String convertToSql() {
			return "UPDATE products SET products = ?, product_summary = ? WHERE product_seq = ?";
		}
		
		public Object[] getAttributeAsObjectArray() {
			return new Object[] {this.productName, this.productSummary, this.productSeq};
		}
	}
	
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@ToString
	public static class CreateProductManagementRequestDTO {
		private String managementType;
		private int cost;
		private int productSeq;
		
		public String convertToSql() {
			return "INSERT INTO product_management values(product_management_seq.NEXTVAL, ?, ?, ?)";
		}
		
		public Object[] getAttributeAsObjectArray() {
			return new Object[] {this.managementType, this.cost, this.productSeq};
		}
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class GetProductManagementsResponseDTO{
		List<ProductManagement> productManagements;
		@Getter
		@Setter
		@NoArgsConstructor
		@AllArgsConstructor
		@Builder
		public static class ProductManagement{
			private int productManagementSeq;
			private String managementType;
			private int cost;
			private int productSeq;
		}
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UpdateProductManagementRequestDTO {
		private int productManagementSeq;
		private String managementType;
		private int cost;
		private int productSeq;
		
		public String convertToSql() {
			return "UPDATE product_management SET management_type = ?, management_cost = ?, product_seq = ? WHERE product_management_seq = ?";
		}
		
		public Object[] getAttributeAsObjectArray() {
			return new Object[] {this.managementType, this.cost, this.productSeq, this.productManagementSeq};
		}
	}
}