package com.thramos.framework.consulta;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.persistence.EntityManager;

import com.thramos.framework.consulta.restricao.RestricaoBetWeen;
import com.thramos.framework.consulta.restricao.RestricaoComParametro;
import com.thramos.framework.consulta.restricao.RestricaoComParametroLowerCase;
import com.thramos.framework.consulta.restricao.RestricaoSemParametro;
import com.thramos.framework.consulta.restricao.Restricoes;

@SuppressWarnings("unchecked")
public class ConsultaJPA<E> implements Consulta<E>{

	private EntityManager entityManager;
	private String nomeEntidade;

	private QueryAdapter query;
	private List<Restricoes> listaRestricoes;
	private StringBuilder select = new StringBuilder();
	private Map<TipoOperador, MontadorRestricoes> mapMontadorRestricoes;
	
	private ConsultaJPA(EntityManager entityManager) {
		this.entityManager = entityManager;
		query = new QueryAdapter(entityManager);
		listaRestricoes = new ArrayList<Restricoes>();
		mapMontadorRestricoes = configurarMapTipoRestricoes();
	}
	
	public ConsultaJPA(String sql, EntityManager entityManager) {
		this(entityManager);
		select = new StringBuilder(sql);
	}
	
	public ConsultaJPA(EntityManager entityManager, String nomeEntidade) {
		this(entityManager);
		this.nomeEntidade = nomeEntidade;
		select = new StringBuilder(MessageFormat.format(SELECT_ALL, nomeEntidade));
	}

	public Consulta<E> addInnerJoin(String nomeEntidadeJoin, String alias) {
		select.append(ESPACO)
			.append(INNER_JOIN_FETCH)
			.append(ESPACO)
			.append(ALIAS)
			.append(PONTO)
			.append(nomeEntidadeJoin)
			.append(ESPACO)
			.append(alias);
		return this;
	}
	
	/**
	 * Este método adiciona um inner join na consulta. É esperado que a entidade associada seja uma collection.
	 * Ex: 
	 *   public class Usuario {
	 *      private List<Permissao> permissoes = new ArrayList<Permissao>();
	 *   }
	 *   
	 *   nomeEntidadeJoin = permissoes;
	 *   aliasEntidadePai = alias da entidade usuário. Normalmente é o alias padrão 'o';
	 *   aliasCollection = alias da collection. No caso o alias de permissão.
	 */
	public Consulta<E> addInnerJoinCollection(String nomeEntidadeJoin, String aliasEntidadePai, String aliasCollection) {
		select.append(ESPACO)
		.append(INNER_JOIN_FETCH)
		.append(ESPACO)
		.append(aliasEntidadePai)
		.append(PONTO)
		.append(nomeEntidadeJoin)
		.append(ESPACO)
		.append(aliasCollection);
		return this;
	}
	
	public Consulta<E> addLeftJoin(String nomeEntidadeJoin, String alias) {
		select.append(ESPACO)
			.append(LEFT_JOIN_FETCH)
			.append(ESPACO)
			.append(ALIAS)
			.append(PONTO)
			.append(nomeEntidadeJoin)
			.append(ESPACO)
			.append(alias);
		return this;
	}

	public List<E> consultarTodos() {
		return entityManager.createQuery(select.toString()).getResultList();
	}

	public Consulta<E> addParametroQuery(Restricoes restricoes) {
		addParametro(restricoes);
		return this;
	}

	public Consulta<E> addParametrosQuery(List<Restricoes> listaRestricoes) {
		for (Restricoes restricoes : listaRestricoes) {
			Restricoes restricaoClonada = restricoes.cloneRestricao(); 
			addParametro(restricaoClonada);
		}
		return this;
	}
	
	public Consulta<E> setFirstResult(Integer posicao) {
		query.setFirstResult(posicao);
		return this;
	}

	public Consulta<E> setMaxResults(Integer maximo) {
		query.setMaxResults(maximo);
		return this;
	}

	public Consulta<E> addOrderByColuna(Order orderBy) {
		if(queryPossuiOrderBy()) { 
			addColunaOrderBy(orderBy);
		} else {
			addClausulaOrderByComColunaOrdenadora(orderBy);
		}
		return this;
	}
	
	@Override
	public Integer getQuantidadeRegistro() {
		addParametrosNaQueryJpa(listaRestricoes);
		select = PATTERN_WHERE.matcher(select).find() ? substituirSelectPorCount(select.toString()) : new StringBuilder(MessageFormat.format(SELECT_COUNT_ALL, this.nomeEntidade)); 
		Long quantidadeRegistros = query.comSelect(select).getSingleResult();
		limparListaDeParametros(listaRestricoes);
		return quantidadeRegistros.intValue();
	}

	public List<E> resultList(){
		addParametrosNaQueryJpa(listaRestricoes);
		List<E> listaResultado = query.comSelect(select).getResultList();
		limparListaDeParametros(listaRestricoes);
		return listaResultado;
	}
	
	public E singleResult() {
		addParametrosNaQueryJpa(listaRestricoes);
		Object resultado = query.comSelect(select).getSingleResult();
		limparListaDeParametros(listaRestricoes);
		return (E) resultado;
	}

	private boolean queryPossuiOrderBy() {
		Matcher matcher = PATTERN_ORDER.matcher(select);
		return matcher.find();
	}

	private Consulta<E> addParametro(Restricoes restricoes) {
		if(queryPossuiClausulaWhere()) {	
			select.append(ESPACO).append(restricoes.getTipoConectivo());
			adicionaRestricoes(restricoes);
		} else {
			adicionaClausulaWhere();
			adicionaRestricoes(restricoes);
		}
		addParametroNaListaDeRestricoes(restricoes);
		return this;
	}

	private boolean queryPossuiClausulaWhere() {
		Matcher matcher = PATTERN_WHERE.matcher(select);
		return matcher.find();
	}
	
	private void adicionaClausulaWhere() {
		select.append(ESPACO)
			.append(WHERE);
	}

	private void addClausulaOrderByComColunaOrdenadora(Order orderBy) {
		select.append(ESPACO)
		.append(ORDER)
		.append(ESPACO)
		.append(BY)
		.append(ESPACO)
		.append(retornarAliasCorretoOrderBy(orderBy))
		.append(PONTO)
		.append(orderBy.getColunaOrdernadora())
		.append(ESPACO)
		.append(orderBy.getTipoOrderBy());
	}

	private void addColunaOrderBy(Order orderBy) {
		select.append(VIRGULA)
			.append(ESPACO)
			.append(retornarAliasCorretoOrderBy(orderBy))
			.append(PONTO)
			.append(orderBy.getColunaOrdernadora())
			.append(ESPACO)
			.append(orderBy.getTipoOrderBy());
	}

	private void adicionaRestricoes(Restricoes restricoes)	 {
		String restricao = mapMontadorRestricoes.get(restricoes.getTipoOperador()).montarRestricao(restricoes);
		select.append(restricao);
	}
	
	private String retornarAliasCorretoOrderBy(Order order) {
		return order.getAlias() != null ? order.getAlias() : ALIAS;
	}

	private void addParametroNaListaDeRestricoes(Restricoes restricoes) {
		listaRestricoes.add(restricoes);
	}

	private void addParametrosNaQueryJpa(List<Restricoes> listaRestricoes) {
		query.setParameters(listaRestricoes);
	}

	private void limparListaDeParametros(List<Restricoes> listaRestricoes) {
		listaRestricoes.clear();
	}
	
	private Map<TipoOperador, MontadorRestricoes> configurarMapTipoRestricoes() {
		Map<TipoOperador, MontadorRestricoes> mapRestricoes = new HashMap<TipoOperador, MontadorRestricoes>();
		mapRestricoes.put(TipoOperador.BETWEEN, new RestricaoBetWeen());
		mapRestricoes.put(TipoOperador.ISNULL, new RestricaoSemParametro());
		mapRestricoes.put(TipoOperador.ISNOTNULL, new RestricaoSemParametro());
		mapRestricoes.put(TipoOperador.DIFERENTEDE, new RestricaoComParametro());
		mapRestricoes.put(TipoOperador.IGUAL, new RestricaoComParametroLowerCase());
		mapRestricoes.put(TipoOperador.LIKE, new RestricaoComParametroLowerCase());
		mapRestricoes.put(TipoOperador.MAIORIGUAL, new RestricaoComParametro());
		mapRestricoes.put(TipoOperador.MENORIGUAL, new RestricaoComParametro());
		return mapRestricoes;
	}
	
	private StringBuilder substituirSelectPorCount(String sql) {
		sql = sql.replaceFirst(ALIAS, "count(o)");
		return new StringBuilder(sql);
	}

}

