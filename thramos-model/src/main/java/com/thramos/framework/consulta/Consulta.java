package com.thramos.framework.consulta;

import java.util.List;
import java.util.regex.Pattern;

import com.thramos.framework.consulta.restricao.Restricoes;

public interface Consulta<E> {

	int INDICE_PRIMEIRO_TYPE_ARGUMENT = 0;
	
	String SELECT_ALL = "select o from {0} o";
	String SELECT_COUNT_ALL = "select count(o) from {0} o";
	String WHERE = "WHERE";
	String ORDER = "ORDER";
	String BY = "BY";
	String ESPACO = " ";
	String ALIAS = "o";
	String PONTO = ".";
	String VIRGULA = ",";
	String DOIS_PONTOS = ":";
	String FINAL = "Final";
	String INICIAL = "Inicial";
	String AND = "AND";
	String INNER_JOIN_FETCH = "INNER JOIN FETCH";
	String LEFT_JOIN_FETCH = "LEFT JOIN FETCH";
	Pattern PATTERN_SELECAO = Pattern.compile("^select (\\w+(\\.\\w+)*)\\s+from", Pattern.CASE_INSENSITIVE);
	Pattern PATTERN_FROM = Pattern.compile("(^|\\s)(from)\\s", Pattern.CASE_INSENSITIVE);
	Pattern PATTERN_WHERE = Pattern.compile("\\s(where)\\s", Pattern.CASE_INSENSITIVE);
	Pattern PATTERN_ORDER = Pattern.compile("\\s(order)(\\s)+by\\s", Pattern.CASE_INSENSITIVE);
	Pattern PATTERN_ORDER_COLUMN = Pattern.compile("^\\w+(\\.\\w+)*$");

	Consulta<E> addInnerJoin(String nomeEntidadeJoin, String alias);
	
	Consulta<E> addInnerJoinCollection(String nomeEntidadeJoin, String aliasCollection, String aliasEntidade);
	
	Consulta<E> addLeftJoin(String nomeEntidadeJoin, String alias);
	
	Consulta<E> addParametroQuery(Restricoes restricoes);
	
	Consulta<E> addParametrosQuery(List<Restricoes> listaRestricoes);
	
	Consulta<E> setFirstResult(Integer posicao);
	
	Consulta<E> setMaxResults(Integer maximo);
	
	List<E> consultarTodos();
	
	List<E> resultList();
	
	E singleResult();

	Consulta<E> addOrderByColuna(Order colunaOrdenadora);
	
	Integer getQuantidadeRegistro();
}
