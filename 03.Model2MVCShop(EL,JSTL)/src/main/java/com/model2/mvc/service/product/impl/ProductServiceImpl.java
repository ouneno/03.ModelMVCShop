package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.domain.Product;

public class ProductServiceImpl implements ProductService{
	
	// Field
	private ProductDao productDao;
	
	// Constructor
	public ProductServiceImpl() {
		productDao=new ProductDao();
	}
	
	// Method
	
		// 1. ��ǰ�߰�
		public void addProduct(Product product) throws Exception {
			productDao.insertProduct(product);
		}
		
		// 2. ��ǰ�󼼺���
		public Product getProduct(int prodNo) throws Exception {
			return productDao.findProduct(prodNo);
		}	
		
		// 3. ��ǰ�����ȸ
		public Map<String,Object> getProductList(Search search) throws Exception {
			return productDao.getProductList(search);
		}
		
		// 4. ��ǰ��������
		public void updateProduct(Product product) throws Exception {
			productDao.updateProduct(product);
		}
	

}
