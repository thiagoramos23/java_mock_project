package com.thramos.web.converter.pessoa;

import com.thramos.model.pessoa.Pessoa;
import com.thramos.web.framework.converter.ConversorGeneric;

public class PessoaConversor extends ConversorGeneric{

	protected static final String key = "jsf.EntityConverter.Pessoa";
	
	@Override
	protected String getKey() {
		return key;
	}

	@Override
	protected String getID(Object value) {
		if(value == null) {
			return EMPTY;
		}
		return ((Pessoa) value).getId().toString();
	}
}

