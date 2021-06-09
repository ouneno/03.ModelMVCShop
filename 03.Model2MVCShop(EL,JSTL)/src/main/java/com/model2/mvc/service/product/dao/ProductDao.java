package com.model2.mvc.service.product.dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDao {

	// Field

	// Constructor
	public ProductDao() {
	}

	// Method

	// 1. InsertProduct ��ǰ�߰�
	public void insertProduct(Product product) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = " INSERT " + 
					 " INTO PRODUCT " + 
					 " VALUES (seq_product_prod_no.NEXTVAL,?,?,?,?,?,sysdate) ";

		PreparedStatement pStmt = con.prepareStatement(sql);

		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate().replaceAll("[\\s\\-()]", ""));
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.executeUpdate();

		pStmt.close();
		con.close();
	}

	// 2. FindProduct ��ǰã��
	public Product findProduct(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product WHERE prod_no = ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product product = null;
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO")); // ��ǰ��ȣ
			product.setProdName(rs.getString("PROD_NAME")); // ��ǰ��
			product.setProdDetail(rs.getString("PROD_DETAIL")); // ��ǰ������
			product.setManuDate(rs.getString("MANUFACTURE_DAY")); // ��ǰ��������
			product.setPrice(rs.getInt("PRICE")); // ��ǰ����
			product.setFileName(rs.getString("IMAGE_FILE")); // �̹��������̸�
			product.setRegDate(rs.getDate("REG_DATE")); // ��ǰ�������

		}
		con.close();
		return product;
	}

	// 3. map(key,value) ���� �̿��Ͽ� ��ǰ�˻�
	public Map<String, Object> getProductList(Search search) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product";

		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0")) {
				sql += " WHERE prod_no LIKE '%" + search.getSearchKeyword() + "%' ";
			} else if (search.getSearchCondition().equals("1")) {
				sql += " WHERE prod_name LIKE '%" + search.getSearchKeyword() + "%' ";
			}
		}
		sql += " ORDER BY prod_no ";

		int totalCount = this.getTotalCount(sql);

		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		List<Product> list = new ArrayList<Product>();

		while (rs.next()) {
			Product product = new Product();

			product.setProdNo(rs.getInt("PROD_NO")); // No
			product.setProdName(rs.getString("PROD_NAME")); // ��ǰ��
			product.setPrice(rs.getInt("PRICE")); // ����
			product.setRegDate(rs.getDate("REG_DATE")); // �����
			
			list.add(product);
		}
		map.put("totalCount", new Integer(totalCount));
		map.put("list", list);
		
		rs.close();
		pStmt.close();
		con.close();

		return map;
	}

	// 4. ��ǰ����
	public void updateProduct(Product product) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product " + "SET prod_name=?, prod_detail=?," + "manufacture_day=?, price=?, reg_date=?"
				+ "WHERE prod_no=?";

		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, product.getProdName()); // ��ǰ��
		pStmt.setString(2, product.getProdDetail()); // ��ǰ������
		pStmt.setString(3, product.getManuDate()); // ��ǰ��������
		pStmt.setInt(4, product.getPrice()); // ��ǰ����
		pStmt.setDate(5, product.getRegDate()); // ��ǰ�������
		pStmt.setInt(6, product.getProdNo()); // ��ǰ��ȣ
		pStmt.executeUpdate();

		pStmt.close();
		con.close();
	} // end updateProduct

	// 5. �Խ��� Page ó���� ���� ��ü Row(totalCount) return
	private int getTotalCount(String sql) throws Exception {

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	} // end getTotalCount

	// 6. �Խ��� currentPage Row �� return
	private String makeCurrentPageSql(String sql, Search search) {
		sql = "SELECT * FROM ( SELECT inner_table.* ,  ROWNUM AS row_seq " + " 	FROM (	" + sql + " ) inner_table "
				+ "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) " + "WHERE row_seq BETWEEN "
				+ ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		return sql;

	} // end 6. makeCurrentPageSql

} // end class