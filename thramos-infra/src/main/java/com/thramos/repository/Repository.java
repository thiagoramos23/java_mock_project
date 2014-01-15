package com.thramos.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.thramos.framework.consulta.restricao.Restricoes;

public interface Repository<E> {

	EntityManager getEntityManager();
	
	void cadastrar(E entidade);

	void alterar(E entidade);

	void excluir(E entidade);

	List<E> consultarTodos(E entidade);

	List<E> consultarTodos(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao);

	E consultarPorId(E entidade);
	
	E consultarPorId(Integer id);
	
	E consultarEntidade(final E entidade, final List<Restricoes> listaRestricoes);

	List<E> consultarTodosPaginado(E entidade, int first, int pageSize, List<Restricoes> listaRestricoes, String campoOrdenacao);
	
	Integer consultarQuantidadeRegistros(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao);

}