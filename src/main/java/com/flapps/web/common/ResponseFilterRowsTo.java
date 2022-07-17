package com.flapps.web.common;

import java.util.List;

public class ResponseFilterRowsTo<F, T> {
    private F filter;
    private PaginatorAndSortDataTo paginator;

    public F getFilter() {
        return filter;
    }

    public void setFilter(F filter) {
        this.filter = filter;
    }

    public PaginatorAndSortDataTo getPaginator() {
        return paginator;
    }

    public void setPaginator(PaginatorAndSortDataTo paginator) {
        this.paginator = paginator;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    private List<T> rows;
}
