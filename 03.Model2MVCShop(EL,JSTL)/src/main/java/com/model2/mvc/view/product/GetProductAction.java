package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;

import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

// GetProductAction : 상품상세보기요청 - 구매가 나와야 함
public class GetProductAction extends Action{

	@Override
	public String execute( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		
		String menu = request.getParameter("menu");
		System.out.println("GetProductAction...[menu] : " +menu);
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));

		ProductService productservice = new ProductServiceImpl();
		Product product = productservice.getProduct(prodNo);
		
		request.setAttribute("product", product);
		
		if(menu.equals("manage")) {						// search일 경우 updateProductView.jsp
			return "forward:/product/updateProductView.jsp";
		}else {
			return "forward:/product/getProduct.jsp";	// search가 아닐 경우 getProduct.jsp
		}
		
	}
}
