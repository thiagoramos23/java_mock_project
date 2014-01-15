package com.thramos.service.pessoa.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thramos.framework.service.impl.AbstractService;
import com.thramos.framework.validador.Validador;
import com.thramos.model.pessoa.Pessoa;
import com.thramos.repository.Repository;
import com.thramos.repository.pessoa.PessoaRepository;
import com.thramos.service.pessoa.PessoaService;

@Component
public class PessoaServiceImpl extends AbstractService<Pessoa> implements PessoaService{

	private PessoaRepository pessoaRepository;

	@Autowired
	public PessoaServiceImpl(Validador validador, PessoaRepository pessoaRepository) {
		super(validador);
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	protected Repository<Pessoa> getRepository() {
		return this.pessoaRepository;
	}


}
