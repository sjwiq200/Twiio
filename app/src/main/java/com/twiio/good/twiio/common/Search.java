package com.twiio.good.twiio.common;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class Search {
	
	///Field
	private int currentPage;
	private String searchCondition;
	private String searchKeyword;
	private int pageSize;
	private int endRowNum;
	private int startRowNum;
	//////product price//////////////
	private String priceCondition;
	//////product button///////
	private String prodSearchType;
	////////starProduct/////////////
	private int productUserNo;

	
	///Constructor
	public Search() {
	}
	
	
	///Method
	
	
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int paseSize) {
		this.pageSize = paseSize;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	
	//==> Select Query �� ROWNUM ������ �� 
	public int getEndRowNum() {
		return getCurrentPage()*getPageSize();
	}
	//==> Select Query �� ROWNUM ���� ��
	public int getStartRowNum() {
		return (getCurrentPage()-1)*getPageSize()+1;
	}

	public String getPriceCondition() {
		return priceCondition;
	}

	public void setPriceCondition(String priceCondition) {
		this.priceCondition = priceCondition;
	}

	public String getProdSearchType() {
		return prodSearchType;
	}


	public void setProdSearchType(String prodSearchType) {
		this.prodSearchType = prodSearchType;
	}

	public int getProductUserNo() {
		return productUserNo;
	}


	public void setProductUserNo(int productUserNo) {
		this.productUserNo = productUserNo;
	}


	@Override
	public String toString() {
		return "Search [currentPage=" + currentPage + ", searchCondition=" + searchCondition + ", searchKeyword="
				+ searchKeyword + ", pageSize=" + pageSize + ", endRowNum=" + endRowNum + ", startRowNum=" + startRowNum
				+ ", priceCondition=" + priceCondition + ", prodSearchType=" + prodSearchType + ", userNo="
				+ productUserNo + "]";
	}
}