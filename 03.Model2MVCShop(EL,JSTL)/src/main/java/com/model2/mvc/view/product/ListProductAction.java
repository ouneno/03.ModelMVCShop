package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


// ListProductAction : 상품목록보기요청
public class ListProductAction extends Action {

	@Override
	public String execute( HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Search search = new Search();
		
		// menu와 search로 구분하기
		String menu = request.getParameter("menu");
		System.out.println("\nListProductAction....manage or search : " +menu +"\n");
				
		int currentPage = 1; // currentPage 현재페이지
		
		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));	// currentPage를 String값으로 가져오기
		}
	
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));		// seachCondition : seachCondition
		search.setSearchKeyword(request.getParameter("searchKeyword")); 		// searchKeyword : 검색창 입력 값
		
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));	// pageSize : 한 페이지 당 보여지는 게시물 수
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));	// pageUnit : 하단 페이지 번호 화면에 보여지는 수
		search.setPageSize(pageSize);
		
		ProductService productService=new ProductServiceImpl();
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage	= new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
		
		// menu로 들어온 manage or search setAttribute에 담아주기
		request.setAttribute("menu", menu);
			System.out.println("ListProductAction.java [menu] : "+menu);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
}