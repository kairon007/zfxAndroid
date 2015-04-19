package com.zifei.corebeau.search.bean;

import java.util.List;

public class PageBean<T> {
	private List<T> list; // 要返回的某一页得记录
	private int currentPage; // 当前页
	private int pageSize; // 每页记录数
	private boolean isLastPage;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isFirstPageOrNot() {
		return currentPage == 1;
	}

	public boolean isHasPreviousPageOrNot() {
		return currentPage != 1;
	}

	public static int countTotalPage(final int pageSize, final int allRow) {
		int totalPage = (allRow % pageSize == 0 ? allRow / pageSize : allRow
				/ pageSize + 1);
		return totalPage;
	}

	public static int countOffset(final int pageSize, final int currentPage) {
		final int offset = pageSize * (currentPage - 1);
		return offset;
	}

	public static int countCurrentPage(int page) {
		final int curPage = (page == 0 ? 1 : page);
		return curPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

}
