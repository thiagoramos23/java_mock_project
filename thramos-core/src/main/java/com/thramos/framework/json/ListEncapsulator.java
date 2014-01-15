package com.thramos.framework.json;

import java.util.List;

public class ListEncapsulator {

	private List<?> results;

	public ListEncapsulator(List<?> results) {
		this.results = results;
	}
	
	public List<?> getResults() {
		return results;
	}
	
	public void setResults(List<?> results) {
		this.results = results;
	}
}
