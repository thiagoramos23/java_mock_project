package com.thramos.facade.pessoa.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thramos.facade.pessoa.PessoaFacade;
import com.thramos.framework.facade.impl.AbstractFacade;
import com.thramos.framework.service.Service;
import com.thramos.model.pessoa.Pessoa;
import com.thramos.service.pessoa.PessoaService;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class PessoaFacadeImpl extends AbstractFacade<Pessoa> implements PessoaFacade{

	private PessoaService pessoaService;

	@Autowired
	public PessoaFacadeImpl(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}
	
	@Override
	protected Service<Pessoa> getService() {
		return this.pessoaService;
	}

}
