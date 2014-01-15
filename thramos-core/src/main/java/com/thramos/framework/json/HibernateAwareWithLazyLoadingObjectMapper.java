package com.thramos.framework.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;

public class HibernateAwareWithLazyLoadingObjectMapper extends ObjectMapper{

	private static final long serialVersionUID = 1L;

	public HibernateAwareWithLazyLoadingObjectMapper() {
		Hibernate4Module hibernate4Module = new Hibernate4Module();
		hibernate4Module.enable(Feature.FORCE_LAZY_LOADING);
		registerModule(hibernate4Module);
	}
}
