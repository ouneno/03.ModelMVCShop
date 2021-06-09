package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


// ListProductAction : ��ǰ��Ϻ����û
public class ListProductAction extends Action {

	@Override
	public String execute( HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Search search = new Search();
		
		// menu�� search�� �����ϱ�
		String menu = request.getParameter("menu");
		System.out.println("\nListProductAction....manage or search : " +menu +"\n");
				
		int currentPage = 1; // currentPage ����������
		
		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));	// currentPage�� String������ ��������
		}
	
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));		// seachCondition : seachCondition
		search.setSearchKeyword(request.getParameter("searchKeyword")); 		// searchKeyword : �˻�â �Է� ��
		
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));	// pageSize : �� ������ �� �������� �Խù� ��
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));	// pageUnit : �ϴ� ������ ��ȣ ȭ�鿡 �������� ��
		search.setPageSize(pageSize);
		
		ProductService productService=new ProductServiceImpl();
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage	= new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
		
		// menu�� ���� manage or search setAttribute�� ����ֱ�
		request.setAttribute("menu", menu);
			System.out.println("ListProductAction.java [menu] : "+menu);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
}