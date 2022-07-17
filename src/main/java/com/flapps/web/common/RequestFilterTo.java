package com.flapps.web.common;

public class RequestFilterTo<T> {
    private PaginatorAndSortDataTo paginator = new PaginatorAndSortDataTo();

    public PaginatorAndSortDataTo getPaginator() {
        return paginator;
    }

    public void setPaginator(PaginatorAndSortDataTo paginator) {
        this.paginator = paginator;
    }

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }

    private T filter;
}
