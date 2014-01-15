package com.thramos.web.consulta.pessoa.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thramos.facade.pessoa.PessoaFacade;
import com.thramos.framework.consulta.restricao.Restricoes;
import com.thramos.model.empresa.Empresa;
import com.thramos.model.pessoa.Pessoa;
import com.thramos.web.consulta.pessoa.PessoaConsulta;

@Component
public class PessoaConsultaImpl extends LazyDataModel<Pessoa> implements PessoaConsulta{

	private static final long serialVersionUID = 1L;
	
	private PessoaFacade pessoaFacade;
	private Empresa empresa;
	
	@Autowired
	public PessoaConsultaImpl(PessoaFacade pessoaFacade) {
		this.pessoaFacade = pessoaFacade;
	}

	@Override
	public List<Pessoa> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		Pessoa pessoa = new Pessoa();
		List<Restricoes> listaRestricoes = new ArrayList<Restricoes>();
		listaRestricoes.add(Restricoes.igual("empresa.id", empresa.getId()));
		
		Integer quantidadeRegistros = this.pessoaFacade.consultarQuantidadeRegistros(pessoa, listaRestricoes, pessoa.getCampoOrderBy());
		List<Pessoa> listaPessoa = this.pessoaFacade.consultarTodosPaginado(pessoa, first, pageSize, listaRestricoes, pessoa.getCampoOrderBy());
		setRowCount(quantidadeRegistros);	
		return listaPessoa;
	}
	
	@Override
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}
