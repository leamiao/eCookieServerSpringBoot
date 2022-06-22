package com.memd.ecookie.specification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.memd.ecookie.common.SearchParam;
import com.memd.ecookie.common.SortParam;
import com.memd.ecookie.util.EntityUtil;

public class DefaultSpecification<T> implements Specification<T> {
	private static final long serialVersionUID = -7922436008148225975L;
	
	protected String searchTermPropertyName;
	protected String searchTerm;
	protected List<SortParam> sortParams;
	protected List<SearchParam> searchParams;
	protected Set<String> specialHandledSearchSet = new HashSet<>();
	protected Set<String> specialHandledSortSet = new HashSet<>();
	
	protected List<Predicate> conditions = new ArrayList<>();
	protected List<Order> orders = new ArrayList<>();
	
	public DefaultSpecification() {
	}
	
	public DefaultSpecification(String searchTermPropertyName, String searchTerm,
			List<SortParam> sortParams, List<SearchParam> searchParams) {
		this.searchTermPropertyName = searchTermPropertyName;
		this.searchTerm = searchTerm;
		this.sortParams = sortParams;
		this.searchParams = searchParams;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		collectSearchTermPredicate(root, query, criteriaBuilder, conditions);
		collectSearchParamsPredicates(root, query, criteriaBuilder, conditions);
		
		if (!orders.isEmpty()) {
			query.orderBy(orders);
		}
		
		return criteriaBuilder.and(conditions.toArray(new Predicate[conditions.size()]));
	}

	public void addExtraPredicate(Predicate predicate) {
		if (predicate != null) {
			conditions.add(predicate);
		}
	}

	public void addExtraOrder(Order order) {
		if (order != null) {
			orders.add(order);
		}
	}

	public String getSearchTermPropertyName() {
		return searchTermPropertyName;
	}

	public void setSearchTermPropertyName(String searchTermPropertyName) {
		this.searchTermPropertyName = searchTermPropertyName;
	}

	public Set<String> getSpecialHandledSearchSet() {
		return specialHandledSearchSet;
	}


	public Set<String> getSpecialHandledSortSet() {
		return specialHandledSortSet;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public List<SortParam> getSortParams() {
		return sortParams;
	}

	public void setSortParams(List<SortParam> sortParams) {
		this.sortParams = sortParams;
	}

	public List<SearchParam> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(List<SearchParam> searchParams) {
		this.searchParams = searchParams;
	}

	protected void collectSearchParamsPredicates(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> conditions) {		
   	    List<Predicate> conditions1 = EntityUtil.buildManyPredicates(criteriaBuilder, root, searchParams, specialHandledSearchSet);
   	    if (conditions1 != null) {
   	    	conditions.addAll(conditions1);
   	    }
		
	}

	protected void collectSearchTermPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder,
			List<Predicate> conditions) {
		if (StringUtils.isAnyBlank(searchTermPropertyName, searchTerm)) {
			return;
		}
		
		conditions.add(criteriaBuilder.like(root.get(searchTermPropertyName), "%"+searchTerm+"%"));
	}
}
