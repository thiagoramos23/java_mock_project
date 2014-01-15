package com.thramos.repository.pessoa.impl;

import org.springframework.stereotype.Component;

import com.thramos.model.pessoa.Pessoa;
import com.thramos.repository.AbstractRepository;
import com.thramos.repository.pessoa.PessoaRepository;

@Component
@SuppressWarnings("unchecked")
public class PessoaRepositoryImpl extends AbstractRepository<Pessoa> implements PessoaRepository{

}
