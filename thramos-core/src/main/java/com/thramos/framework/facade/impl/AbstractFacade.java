package com.thramos.framework.facade.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.thramos.framework.consulta.restricao.Restricoes;
import com.thramos.framework.facade.Facade;
import com.thramos.framework.service.Service;

@Transactional
public abstract class AbstractFacade<E> implements Facade<E> {

	protected abstract Service<E> getService();

	@Override
	public void cadastrar(E entidade) {
		getService().cadastrar(entidade);
	}

	@Override
	public void alterar(E entidade) {
		getService().alterar(entidade);
	}

	@Override
	public void excluir(E entidade) {
		getService().excluir(entidade);
	}

	@Override
	public E consultarPorId(E entidade) {
		return getService().consultarPorId(entidade);
	}

	@Override
	public E consultarPorId(Integer id) {
		return getService().consultarPorId(id);
	}
	
	@Override
	public E consultarEntidade(E entidade, List<Restricoes> listaRestricoes) {
		return getService().consultarEntidade(entidade, listaRestricoes);
	}
	
	@Override
	public List<E> consultarTodos(E entidade) {
		return getService().consultarTodos(entidade);
	}
	
	@Override
	public List<E> consultarTodos(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return getService().consultarTodos(entidade, listaRestricoes, campoOrdenacao);
	}
	
	@Override
	public List<E> consultarTodosPaginado(E entidade, int first, int pageSize, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return getService().consultarTodosPaginado(entidade, first, pageSize, listaRestricoes, campoOrdenacao);
	}
	
	@Override
	public Integer consultarQuantidadeRegistros(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return getService().consultarQuantidadeRegistros(entidade, listaRestricoes, campoOrdenacao);
	}
}