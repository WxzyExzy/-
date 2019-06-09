package cn.zua.smbms.bean;

import java.util.List;


public class Page<T> {
	private Integer pageIndex = 1 ; 	//当前页码
	private Integer totalPageCount = 1 ;//总页数
	private Integer pageSize = 5 ;	    //页面大小，每页的记录数
	private Integer totalCount =0 ;     //总的记录数
	private List<T> pageList ;      	//每页记录的信息集合
	
	
	public Integer getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		if(totalCount > 0){
			this.totalCount = totalCount;
			//计算总页数
			this.totalPageCount = this.totalCount % pageSize == 0 ? 
					(this.totalCount/pageSize) : (this.totalCount/pageSize + 1) ;
		}
		
	}
	public List<T> getPageList() {
		return pageList;
	}
	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	
 }
