package com.memd.ecookie.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.memd.ecookie.common.Constants;
import com.memd.ecookie.common.DateUtil;
import com.memd.ecookie.common.NumberUtil;
import com.memd.ecookie.common.SearchParam;
import com.memd.ecookie.common.SortParam;
import com.memd.ecookie.entity.ProductEntity;

public class EntityUtil {
    public static final String START_NODE = "Start";

    public static ProductEntity getUserEntity(Set<ProductEntity> set, String name) {
        if (set == null || name == null) {
            return null;
        }

        for (ProductEntity entity : set) {
            if (name.equals(entity.getName())) {
                return entity;
            }
        }

        return null;
    }

    public static String getStringValueFromJsonNodeChild(JsonNode jsonNode, String childName) {
        if (jsonNode == null || childName == null) {
            return null;
        }

        JsonNode childNode = jsonNode.get(childName);
        if (childNode != null) {
            return childNode.asText();
        }

        return null;
    }

    public static SortParam findSortParamByProperty(List<SortParam> sortParams, String name) {
        if (sortParams == null || name == null) {
            return null;
        }

        for (SortParam sortParam : sortParams) {
            if (sortParam == null) {
                continue;
            }

            if (name.equals(sortParam.getProperty())) {
                return sortParam;
            }
        }

        return null;
    }

    public static SearchParam findSearchParamByProperty(List<SearchParam> searchParams, String name) {
        if (searchParams == null || name == null) {
            return null;
        }

        for (SearchParam searchParam : searchParams) {
            if (searchParam == null) {
                continue;
            }

            if (name.equals(searchParam.getProperty())) {
                return searchParam;
            }
        }

        return null;
    }

    public static <T> List<Predicate> buildManyPredicates(CriteriaBuilder cb, Root<T> entity,
            List<SearchParam> searchParams, Set<String> used) {
        List<Predicate> results = new ArrayList<>();
        if (searchParams != null) {
            for (SearchParam searchParam : searchParams) {
                Predicate predicate = buildPredicate(cb, entity, searchParam, used);
                if (predicate != null) {
                    results.add(predicate);
                }
            }
        }

        return results;
    }

    public static <T> Predicate buildPredicate(CriteriaBuilder cb, Root<T> root, SearchParam searchParam,
            Set<String> used) {
        if (cb == null || root == null || searchParam == null) {
            return null;
        }

        Object value = searchParam.getValue();
        if (value == null) {
            return null;
        }

        String property = searchParam.getProperty();
        if (StringUtils.isBlank(property) || (used != null && used.contains(property))) {
            return null;
        }

        String operator = searchParam.getOperator();
        if (Constants.IN.equalsIgnoreCase(operator)) {
            return root.<String>get(property).in(value);
        } else if (Constants.LIKE.equalsIgnoreCase(operator)) {
            return cb.like(root.get(property), "%" + value + "%");
        } else if (Constants.EQ.equalsIgnoreCase(operator)) {
            if (value instanceof Number) {
                return cb.equal(root.get(property), value);
            }
            Date date = DateUtil.getDateBySearchFormat(value);
            if (date == null) {
                return cb.equal(root.get(property), value);
            } else {
                Date[] dates = DateUtil.getDateRange(date);
                return cb.between(root.get(property), dates[0], dates[1]);
            }
        } else if (Constants.LT.equalsIgnoreCase(operator)) {
            if (value instanceof Number) {
                return cb.lt(root.get(property), NumberUtil.getNumber(value));
            }
            Date date = DateUtil.getDateBySearchFormat(value);
            if (date == null) {
                return cb.lt(root.get(property), NumberUtil.getDouble(value));
            } else {
                return cb.lessThan(root.get(property), DateUtil.truncate(date));
            }
        } else if (Constants.GT.equalsIgnoreCase(operator)) {
            if (value instanceof Number) {
                return cb.gt(root.get(property), NumberUtil.getNumber(value));
            }
            Date date = DateUtil.getDateBySearchFormat(value);
            if (date == null) {
                return cb.gt(root.get(property), NumberUtil.getDouble(value));
            } else {
                return cb.greaterThanOrEqualTo(root.get(property), DateUtil.truncateToNextDay(date));
            }
        }

        return null;
    }

    public static Object getFirstObject(Object... objects) {
        if (objects == null) {
            return null;
        }

        for (Object obj : objects) {
            if (obj != null) {
                return obj;
            }
        }

        return null;
    }
}
