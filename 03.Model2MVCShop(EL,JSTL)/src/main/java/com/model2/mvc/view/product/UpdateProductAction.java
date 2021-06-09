package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

// �ǸŻ�ǰ���� - ��ǰ�� - ���� ������ ������ page

public class UpdateProductAction extends Action {

	public String execute( HttpServletRequest request,
						   HttpServletResponse response) throws Exception {
		
	System.out.println("UpdateProductAction.....execute ����");
				
		Product product=new Product();
		
		product.setProdNo(Integer.parseInt(request.getParameter("prodNo"))); 	// ��ǰ��ȣ
		product.setProdName(request.getParameter("prodName")); 				// ��ǰ��
		product.setProdDetail(request.getParameter("prodDetail"));			// ��ǰ������
		product.setManuDate(request.getParameter("manuDate"));				// ��������
		product.setPrice(Integer.parseInt(request.getParameter("price")));	// ����
		product.setFileName(request.getParameter("fileName"));				// ��ǰ�̹���
		
System.out.println("UpdateProductAction ó���Ϸ�....");
		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(product);
		
System.out.println("UpdateProductAction service ó���Ϸ�....." + product);
		
		request.setAttribute("productVO", product);
		return "forward:/product/updateProduct.jsp";
	}
}