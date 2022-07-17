package com.flapps.web.common;

import jdk.javadoc.internal.doclets.toolkit.PropertyUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;

abstract public class RequestFilterUtil {

    static private <T> void doParam(HttpServletRequest request, String name, Consumer<T> consumer, Function<String, T> conv) {
        try {
            String value = request.getParameter(name);
            if (value == null) return;
            consumer.accept(conv.apply(value));
        } catch (Exception e) {
        }
    }

    static private void doParam(HttpServletRequest request, String name, Consumer<String> consumer) {
        try {
            String value = request.getParameter(name);
            if (value == null) return;
            consumer.accept(value);
        } catch (Exception e) {
        }
    }

    public static <T> RequestFilterTo<T> parseQuery(Class<T> cls, HttpServletRequest request) {
        return parseQuery(null, cls, request);
    }

    public static <T> RequestFilterTo<T> parseQuery(RequestFilterTo<T> req, Class<T> cls, HttpServletRequest request) {
        if (req == null) req = new RequestFilterTo<T>();

        PaginatorAndSortDataTo paginator = new PaginatorAndSortDataTo();
        req.setPaginator(paginator);

        doParam(request, "page", paginator::setPage, Integer::parseInt);
        doParam(request, "maxResults", paginator::setMaxResults, Integer::parseInt);
        doParam(request, "sortBy", paginator::setSortBy);
        doParam(request, "sortAsc", paginator::setSortAsc, Boolean::parseBoolean);

        if (paginator.getPage() == 0) paginator.setPage(1);

        return req;
    }

    public static Object parseObject(String strVal, Class<?> type) {

        if (Date.class.equals(type)) {
            if (NumberUtils.isNumber(strVal)) {
                return new Date(NumberUtils.toLong(strVal));
            }
        }

        return null;
    }
}
