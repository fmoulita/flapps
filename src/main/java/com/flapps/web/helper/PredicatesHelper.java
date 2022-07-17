package com.flapps.web.helper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;

public class PredicatesHelper {
    private static class PredicateHolder {
        Predicate predicate;
        PredicatesHelper predicatesHelper;
        Boolean predicatesAsAnd;

        public PredicateHolder(Predicate predicate) {
            this.predicate = predicate;
            this.predicatesAsAnd = null;
        }

        public PredicateHolder(PredicatesHelper predicatesHelper, boolean predicatesAsAnd) {
            this.predicatesHelper = predicatesHelper;
            this.predicatesAsAnd = predicatesAsAnd;
        }

        public boolean hasPredicate() {
            if (this.predicate != null) return true;
            return !this.predicatesHelper.predicates.isEmpty();
        }

        public Predicate getPredicate(CriteriaBuilder cb) {
            if (!hasPredicate()) return null;
            if (this.predicate != null) return this.predicate;
            if (this.predicatesHelper.predicates.size() == 1) return this.predicatesHelper.predicates.get(0).getPredicate(cb);
            if (this.predicatesAsAnd) return cb.and(this.predicatesHelper.toPredicates(cb));
            return cb.or(this.predicatesHelper.toPredicates(cb));
        }
    }

    final private List<PredicateHolder> predicates = new LinkedList<>();

    public PredicatesHelper add(Predicate predicate) {
        if (predicate != null) {
            predicates.add(new PredicateHolder(predicate));
        }
        return this;
    }

    public PredicatesHelper or() {
        PredicateHolder predicateHolder = new PredicateHolder(new PredicatesHelper(), false);
        predicates.add(predicateHolder);
        return predicateHolder.predicatesHelper;
    }

    public PredicatesHelper and() {
        PredicateHolder predicateHolder = new PredicateHolder(new PredicatesHelper(), true);
        predicates.add(predicateHolder);
        return predicateHolder.predicatesHelper;
    }

    public Predicate[] toPredicates(CriteriaBuilder cb) {
        int calcCount = 0;
        for (PredicateHolder ph : this.predicates) {
            if (ph.hasPredicate()) calcCount++;
        }

        Predicate[] ret = new Predicate[calcCount];
        int i = 0;

        for (PredicateHolder ph : this.predicates) {
            if (!ph.hasPredicate()) continue;
            ret[i++] = ph.getPredicate(cb);
        }

        return ret;
    }

    public boolean isEmpty() {
        return predicates.isEmpty();
    }

    public int size() {
        return predicates.size();
    }
}
