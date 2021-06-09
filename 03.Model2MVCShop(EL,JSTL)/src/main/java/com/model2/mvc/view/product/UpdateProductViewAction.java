package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;



public class UpdateProductViewAction extends Action{

	@Override
	public String execute( HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int prodNo=Integer.parseInt(request.getParameter("prodNO"));
		System.out.println(prodNo);
		
		ProductService service=new ProductServiceImpl();
		Product product=service.getProduct(prodNo);
		System.out.println("prodNo"+prodNo);
		request.setAttribute("productVO", product);
		System.out.println("업데이트프로덕트뷰액션 productVO   "+  product);
		return "forward:/product/updateProduct.jsp";
	}
}
