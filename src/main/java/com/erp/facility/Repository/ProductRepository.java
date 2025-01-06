package com.erp.facility.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;

import com.erp.auth.vo.AuthDTOs.FeaturesResponseDTO;
import com.erp.auth.vo.AuthDTOs.FeaturesResponseDTO.FeatureDetail;
import com.erp.common.database.DBManager;
import com.erp.common.database.StatementProvider;
import com.erp.common.database.impl.OracleDBManager;
import com.erp.common.database.impl.StatementProviderDefaultImpl;
import com.erp.common.rest.RestBusinessException;
import com.erp.common.rest.RestBusinessException.StatusCode;
import com.erp.facility.VO.ProductDTO;
import com.erp.facility.VO.ProductDTO.CreateProductManagementRequestDTO;
import com.erp.facility.VO.ProductDTO.GetProductManagementsResponseDTO;
import com.erp.facility.VO.ProductDTO.GetProductsResponseDTO;

public class ProductRepository{
	
	private static final DBManager db = new OracleDBManager();
	private static final StatementProvider sp = new StatementProviderDefaultImpl();
	
	
	public void createProduct(ProductDTO.CreateProductRequestDTO requestDto) {
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = sp.getPreparedStatement(con, requestDto.convertToSql(),
						requestDto.getAttributeAsObjectArray());) {
			int rows = ps.executeUpdate();
			if (rows == 1)
				con.commit();
			else {
				con.rollback();
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
	
	public GetProductsResponseDTO getProducts() {
		GetProductsResponseDTO response = new GetProductsResponseDTO();
		response.setProducts(new ArrayList<>());
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = con.prepareStatement(
						"SELECT * FROM products");
//					= sp.getPreparedStatement(con, "SELECT fr.*, stb.user_name FROM FEATURE_ROLES fr INNER JOIN (SELECT user_name, user_seq FROM app_users) stb ON(fr.register_user = stb.user_seq)", new Object[0]);
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				response.getProducts()
						.add(ProductDTO.GetProductsResponseDTO.Product.builder()
								.productSeq(rs.getInt("product_seq"))
								.productName(rs.getString("product_name"))
								.productSummary(rs.getString("product_summary"))
								.build());
			}
			return response;
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
	
	public void updateProduct(ProductDTO.UpdateProductRequestDTO requestDto) {
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = sp.getPreparedStatement(con, requestDto.convertToSql(),
						requestDto.getAttributeAsObjectArray());) {
			int rows = ps.executeUpdate();
			if (rows == 1)
				con.commit();
			else {
				con.rollback();
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
	
	public void deleteProduct(int productSeq) {
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = sp.getPreparedStatement(con, "DELETE FROM products WHERE product_seq = ?",
						new Object[] {productSeq});) {
			int rows = ps.executeUpdate();
			if (rows == 1)
				con.commit();
			else {
				con.rollback();
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
	
	public void createProductManagement (CreateProductManagementRequestDTO requestDto) {
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = sp.getPreparedStatement(con, requestDto.convertToSql(),
						requestDto.getAttributeAsObjectArray());) {
			int rows = ps.executeUpdate();
			if (rows != 1) {
				con.rollback();
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
			System.out.println(requestDto);
			try(PreparedStatement ps2 = sp.getPreparedStatement(con, "INSERT INTO capital_management VALUES (capital_management_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, product_management_seq.CURRVAL)", 
					new Object[] {requestDto.getManageAt(), new String("물품 " + requestDto.getManagementType()), "물류", requestDto.getCost(), "카드", "처리 안됨"})){
				System.out.println("여기까지만큼은");
				int rows2 = ps2.executeUpdate();
				if(rows2 != 1) {
					con.rollback();
					throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
				}
			}
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
	
	public GetProductManagementsResponseDTO getProductManagements() {
		GetProductManagementsResponseDTO response = new GetProductManagementsResponseDTO();
		response.setProductManagements(new ArrayList<>());
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = con.prepareStatement(
						"SELECT * FROM product_management");
//					= sp.getPreparedStatement(con, "SELECT fr.*, stb.user_name FROM FEATURE_ROLES fr INNER JOIN (SELECT user_name, user_seq FROM app_users) stb ON(fr.register_user = stb.user_seq)", new Object[0]);
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				response.getProductManagements()
						.add(ProductDTO.GetProductManagementsResponseDTO.ProductManagement.builder()
								.productManagementSeq(rs.getInt("product_management_seq"))
								.managementType(rs.getString("management_type"))
								.cost(rs.getInt("management_cost"))
								.productSeq(rs.getInt("product_seq"))
								.build());
			}
			return response;
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
	
	
	public void updateProductManagement(ProductDTO.UpdateProductManagementRequestDTO requestDto) {
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = sp.getPreparedStatement(con, requestDto.convertToSql(),
						requestDto.getAttributeAsObjectArray());) {
			int rows = ps.executeUpdate();
			if (rows == 1)
				con.commit();
			else {
				con.rollback();
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
	
	public void deleteProductManagement(int productManagementSeq) {
		try (Connection con = db.getConnectionForTransaction();
				PreparedStatement ps = sp.getPreparedStatement(con, "DELETE FROM product_management WHERE product_management_seq = ?",
						new Object[] {productManagementSeq});) {
			int rows = ps.executeUpdate();
			if (rows == 1)
				con.commit();
			else {
				con.rollback();
				throw new RestBusinessException(StatusCode.UNEXPECTED_ERROR);
			}
		} catch (SQLException e) {
			System.out.println(e instanceof SQLIntegrityConstraintViolationException);
			if (e instanceof SQLIntegrityConstraintViolationException)
				throw new RestBusinessException(StatusCode.CONSTRAINT_VIOLATION, e);
			throw new RestBusinessException(StatusCode.DATABASE_UKNOWN_ERROR, e);
		}
	}
}