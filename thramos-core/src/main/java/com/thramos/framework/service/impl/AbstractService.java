package com.thramos.framework.service.impl;

import java.util.List;

import com.thramos.framework.consulta.restricao.Restricoes;
import com.thramos.framework.contexto.Alterar;
import com.thramos.framework.contexto.Cadastrar;
import com.thramos.framework.service.Service;
import com.thramos.framework.validador.Validador;
import com.thramos.repository.Repository;

public abstract class AbstractService<E> implements Service<E> {

	private Validador validador;

	protected abstract Repository<E> getRepository();

	public AbstractService(Validador validador) {
		this.validador = validador;
	}

	@Override
	public void cadastrar(E entidade) {
		validarEntidadeNoContextoInformado(entidade, Cadastrar.class);
		regrasNegocioCadastrar(entidade);
		getRepository().cadastrar(entidade);
	}

	@Override
	public void alterar(E entidade) {
		validarEntidadeNoContextoInformado(entidade, Alterar.class);
		regrasNegocioAlterar(entidade);
		getRepository().alterar(entidade);
	}

	@Override
	public void excluir(E entidade) {
		regrasNegocioExcluir(entidade);
		excluirEntidadeOuLancarExcecaoCasoPossuaAssociacao(entidade);
	}

	@Override
	public E consultarPorId(E entidade) {
		return getRepository().consultarPorId(entidade);
	}

	@Override
	public E consultarPorId(Integer id) {
		return getRepository().consultarPorId(id);
	}

	@Override
	public E consultarEntidade(E entidade, List<Restricoes> listaRestricoes) {
		return getRepository().consultarEntidade(entidade, listaRestricoes);
	}
	
	@Override
	public List<E> consultarTodos(E entidade) {
		return getRepository().consultarTodos(entidade);
	}
	
	@Override
	public List<E> consultarTodos(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return getRepository().consultarTodos(entidade, listaRestricoes, campoOrdenacao);
	}
	
	public List<E> consultarTodosPaginado(E entidade, int first, int pageSize, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return getRepository().consultarTodosPaginado(entidade, first, pageSize, listaRestricoes, campoOrdenacao);
	}
	
	@Override
	public Integer consultarQuantidadeRegistros(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return getRepository().consultarQuantidadeRegistros(entidade, listaRestricoes, campoOrdenacao);
	}
	
	protected void validarEntidadeNoContextoInformado(E entidade, Class<?> contexto) {
		validador.validar(entidade, contexto);
	}

	protected void excluirEntidadeOuLancarExcecaoCasoPossuaAssociacao(E entidade) {
		lancarExcecaoCasoEntidadePossuaAssociacao(entidade);
		getRepository().excluir(entidade);
	}
	

	protected void lancarExcecaoCasoEntidadePossuaAssociacao(E entidade) {}

	protected void regrasNegocioCadastrar(E entidade){}
	protected void regrasNegocioAlterar(E entidade){}
	protected void regrasNegocioExcluir(E entidade){}

}