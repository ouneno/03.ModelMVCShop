package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

// 판매상품관리 - 상품명 - 수정 누르고 나오는 page

public class UpdateProductAction extends Action {

	public String execute( HttpServletRequest request,
						   HttpServletResponse response) throws Exception {
		
	System.out.println("UpdateProductAction.....execute 진입");
				
		Product product=new Product();
		
		product.setProdNo(Integer.parseInt(request.getParameter("prodNo"))); 	// 상품번호
		product.setProdName(request.getParameter("prodName")); 				// 상품명
		product.setProdDetail(request.getParameter("prodDetail"));			// 상품상세정보
		product.setManuDate(request.getParameter("manuDate"));				// 제조일자
		product.setPrice(Integer.parseInt(request.getParameter("price")));	// 가격
		product.setFileName(request.getParameter("fileName"));				// 상품이미지
		
System.out.println("UpdateProductAction 처리완료....");
		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(product);
		
System.out.println("UpdateProductAction service 처리완료....." + product);
		
		request.setAttribute("productVO", product);
		return "forward:/product/updateProduct.jsp";
	}
}