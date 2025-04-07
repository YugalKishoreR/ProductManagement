package com.example.ProductManagement.entity;

import java.util.List;

import lombok.Data;

@Data
public class SearchCriteria {
	private List<Criteria> searchCriteriaList;

	public List<Criteria> getSearchCriteriaList() {
		return searchCriteriaList;
	}

	public void setSearchCriteriaList(List<Criteria> searchCriteriaList) {
		this.searchCriteriaList = searchCriteriaList;
	}
	
	
}
