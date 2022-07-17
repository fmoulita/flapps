package com.flapps.web.common;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class ResponseRowsUtil {

    public static class QueryConfig {
        final private boolean forCount;

        public QueryConfig(boolean forCount) {
            this.forCount = forCount;
        }

        private Predicate[] where;
        private List<Order> orders;
        private List<Selection<?>> multiselect;
        private Expression<?>[] groupBy;

        private boolean distinct = false;

        static private <T> List<T> addValues(List<T> vals, T... v) {
            if (vals == null) {
                vals = new LinkedList<T>();
            }
            ;
            Collections.addAll(vals, v);
            return vals;
        }

        public void where(Predicate... where) {
            this.where = where;
        }

        public void orderBy(Order... orders) {
            this.orders = addValues(this.orders, orders);
        }

        public void orderBy(List<Order> orders) {
            this.orders = orders;
        }

        public void multiselect(Selection<?>... multiselect) {
            this.multiselect = addValues(this.multiselect, multiselect);
        }

        public boolean isForCount() {
            return forCount;
        }

        public Predicate[] getWhere() {
            return where;
        }

        public void setWhere(Predicate[] where) {
            this.where = where;
        }

        public List<Order> getOrders() {
            return orders;
        }

        public void setOrders(List<Order> orders) {
            this.orders = orders;
        }

        public List<Selection<?>> getMultiselect() {
            return multiselect;
        }

        public void setMultiselect(List<Selection<?>> multiselect) {
            this.multiselect = multiselect;
        }

        public Expression<?>[] getGroupBy() {
            return groupBy;
        }

        public void setGroupBy(Expression<?>[] groupBy) {
            this.groupBy = groupBy;
        }

        public boolean isDistinct() {
            return distinct;
        }

        public void setDistinct(boolean distinct) {
            this.distinct = distinct;
        }

        public void groupBy(Expression<?>... groupBy) {
            this.groupBy = groupBy;
        }
    }

    @FunctionalInterface
    public static interface DoQueryConfig<FORM_TYPE, DTO_TYPE, Tuple> {
        public Function<Tuple, DTO_TYPE> confifQuery(CriteriaBuilder cb, CriteriaQuery<?> q, Root<FORM_TYPE> r, QueryConfig conf);
    }

    protected static <DB, F, T> Long getTotalCount(EntityManager em, Class<DB> clsFrom, DoQueryConfig<DB, T, ?> config) {
        // Count ...
        Long totalValue = 0L;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<DB> root = cq.from(clsFrom);
        QueryConfig conf = new QueryConfig(true);
        config.confifQuery(cb, cq, root, conf);
        if (conf.getWhere() != null) {
            cq.where(conf.getWhere());
        }
        if (conf.getGroupBy() != null) {
            totalValue = em.createQuery(cq).getSingleResult();
        } else {
            cq.select(cb.count(root));
            totalValue = em.createQuery(cq).getSingleResult();
        }

        if (totalValue == null) totalValue = 0L;
        return totalValue;
    }

    private static PaginatorAndSortDataTo createPaginator(RequestFilterTo<?> filter, Integer totalCount) {

        //paginator calculators ..
        PaginatorAndSortDataTo p = new PaginatorAndSortDataTo();

        p.setTotalCounts(totalCount);

        if (filter.getPaginator().getMaxResults() > 0) {
            // pages
            p.setTotalPages(BigDecimal.valueOf(totalCount).divide(BigDecimal.valueOf(filter.getPaginator().getMaxResults()), BigDecimal.ROUND_UP, 0).intValue());

            // current page
            p.setPage(filter.getPaginator().getPage());
            if (p.getPage() > p.getTotalPages()) {
                p.setPage(p.getTotalPages());
            }
        } else {
            p.setTotalPages(1);
            p.setPage(1);
        }

        return p;
    }

    public static <F, T> ResponseFilterRowsTo<F, T> createResponse(RequestFilterTo<F> filter, Integer totalCount, List<T> rows) {
        ResponseFilterRowsTo<F, T> result = new ResponseFilterRowsTo<>();

        result.setFilter(filter.getFilter());

        //paginator calculators ..
        result.setPaginator(createPaginator(filter, totalCount));

        //data
        result.setRows(rows);

        return result;
    }

    public static <DB, F, T> ResponseFilterRowsTo<F, T> doQueryResponse(EntityManager em, Class<DB> clsFrom, RequestFilterTo<F> filter, DoQueryConfig<DB, T, DB> config) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Count ...
        Long totalValue = getTotalCount(em, clsFrom, config);

        // Select ...
        CriteriaQuery<DB> cq = cb.createQuery(clsFrom);
        Root<DB> root = cq.from(clsFrom);
        QueryConfig conf = new QueryConfig(false);
        Function<DB, T> trans = config.confifQuery(cb, cq, root, conf);
        if (conf.getWhere() != null) {
            cq.where(conf.getWhere());
        }
        if (conf.getMultiselect() != null) {
            cq.multiselect(conf.getMultiselect());
        }
        if (conf.getOrders() != null) {
            cq.orderBy(conf.getOrders());
        }
        if (conf.getGroupBy() != null) {
            cq.groupBy(conf.getGroupBy());
        }
        if (conf.isDistinct()) {
            cq.distinct(true);
        }

        List<DB> lst = em.createQuery(cq).setFirstResult((filter.getPaginator().getPage() - 1) * filter.getPaginator().getMaxResults()).setMaxResults(filter.getPaginator().getMaxResults()).getResultList();
        List<T> rows = new ArrayList<T>(filter.getPaginator().getMaxResults());
        for (DB t : lst) {
            rows.add(trans.apply(t));
        }

        return createResponse(filter, totalValue.intValue(), rows);
    }
}
