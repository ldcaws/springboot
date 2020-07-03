package com.ldc.exceptionaspectvalidator.common.response;

/**
 * @description:统一的分页格式类
 * @author: ss
 * @time: 2020/7/3 23:58
 */
public class PageData<T> {

    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private Integer pages;
    private T data;

    public PageData(Integer pageNum, Integer pageSize, Long total, T data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = null;
        this.data = data;
    }

    public PageData(Integer pageNum, Integer pageSize, Long total, Integer pages, T data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
        this.data = data;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
