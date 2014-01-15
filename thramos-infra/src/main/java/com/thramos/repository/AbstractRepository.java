package com.thramos.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.thramos.framework.consulta.ConsultaJPA;
import com.thramos.framework.consulta.Order;
import com.thramos.framework.consulta.restricao.Restricoes;
import com.thramos.framework.util.VerificadorUtil;
import com.thramos.model.identidade.Identidade;
import com.thramos.model.ordenacao.Ordenacao;
import com.thramos.repository.command.CommandSingleResult;
import com.thramos.repository.command.ValidadorSingleResult;

public abstract class AbstractRepository<E> implements Repository<E> {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public void cadastrar(E entidade) {
		if(VerificadorUtil.estaNulo(((Identidade)entidade).getId())){
			getEntityManager().persist(entidade);
		} else {
			getEntityManager().merge(entidade);
		}
	}

	@Override
	public void alterar(E entidade) {
		getEntityManager().merge(entidade);
	}

	@Override
	public void excluir(E entidade) {
		E itemParaExcluir = consultarPorId(entidade);
		getEntityManager().remove(itemParaExcluir);
		getEntityManager().flush();
	}

	@SuppressWarnings("unchecked")
	public E consultarPorId(E entidade) {
		return (E) getEntityManager().find(entidade.getClass(), ((Identidade) entidade).getId());
	}

	public E consultarPorId(Integer id) {
		return (E) getEntityManager().find(getClassePeloTipoParametrizado(), id);
	}
	
	@Override
	public List<E> consultarTodos(E entidade) {
		return new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName()).resultList();
	}
	
	@Override
	public E consultarEntidade(final E entidade, final List<Restricoes> listaRestricoes) {
		return new ValidadorSingleResult<E>().getSingleResult(new CommandSingleResult<E>() {
			@Override
			public E execute() {
				return new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName())
				.addParametrosQuery(listaRestricoes)
				.singleResult();
			}
		});
	}
	
	@Override
	public List<E> consultarTodos(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		List<E> resultList = new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName())
				.addParametrosQuery(listaRestricoes)
				.addOrderByColuna(Order.asc(getOrdenacao(campoOrdenacao, entidade)))
				.resultList();
		return resultList;
	}
	
	public List<E> consultarTodosPaginado(E entidade, int first, int pageSize, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName())
			.addParametrosQuery(listaRestricoes)
			.setFirstResult(first)
			.setMaxResults(pageSize)
			.addOrderByColuna(Order.asc(getOrdenacao(campoOrdenacao, entidade)))
			.resultList();
	}

	public Integer consultarQuantidadeRegistros(E entidade, List<Restricoes> listaRestricoes, String campoOrdenacao) {
		return new ConsultaJPA<E>(getEntityManager(), entidade.getClass().getSimpleName())
			.addParametrosQuery(listaRestricoes)
			.setFirstResult(0)
			.setMaxResults(Integer.MAX_VALUE)
			.getQuantidadeRegistro();
	}

	@SuppressWarnings("unchecked")
	private Class<E> getClassePeloTipoParametrizado() {
		Class<E> classe = (Class<E>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return classe;
	}

	private String getOrdenacao(String campoOrdenacao, E entidade) {
		return VerificadorUtil.estaNuloOuVazio(campoOrdenacao) ? ((Ordenacao)entidade).getCampoOrderBy() : campoOrdenacao;
	}

}