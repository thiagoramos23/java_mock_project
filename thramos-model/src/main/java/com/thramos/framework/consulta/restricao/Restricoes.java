package com.thramos.framework.consulta.restricao;


import org.apache.commons.lang.StringUtils;

import com.thramos.framework.consulta.Intervalo;
import com.thramos.framework.consulta.TipoConectivo;
import com.thramos.framework.consulta.TipoOperador;
import com.thramos.framework.util.VerificadorUtil;

public class Restricoes extends AbstractRestricao{

	private TipoOperador tipoOperador;
	private String alias;
	private String nome;
	private Object valor;
	private TipoConectivo tipoConectivo;

	private Restricoes(String nome, TipoOperador tipoOperador, Object valor, TipoConectivo tipoConectivo) {
		this.nome = nome;
		this.tipoOperador = tipoOperador;
		this.tipoConectivo = tipoConectivo;
		this.valor = valor;
	}
	
	private Restricoes(String alias, String nome, TipoOperador tipoOperador, Object valor, TipoConectivo tipoConectivo) {
		this.alias = alias;
		this.nome = nome;
		this.tipoOperador = tipoOperador;
		this.tipoConectivo = tipoConectivo;
		this.valor = valor;
	}
	
	public Restricoes(String nome, TipoOperador tipoOperador, TipoConectivo tipoConectivo) {
		this.nome = nome;
		this.tipoOperador = tipoOperador;
		this.tipoConectivo = tipoConectivo;
	}

	public static Restricoes igual(String nome, Object valor) {
		return criarRestricao(nome, TipoOperador.IGUAL, atribuirLowerCaseCasoSejaString(valor), TipoConectivo.AND);
	}

	
	public static Restricoes igual(String nome, Object valor, TipoConectivo tipoConectivo) {
		return criarRestricao(nome, TipoOperador.IGUAL, atribuirLowerCaseCasoSejaString(valor), tipoConectivo);
	}

	public static Restricoes igualComAlias(String aliasTabela, String nome, Object valor) {
		return criarRestricaoComAlias(aliasTabela, nome, TipoOperador.IGUAL, atribuirLowerCaseCasoSejaString(valor), TipoConectivo.AND);
	}

	public static Restricoes igual(String aliasTabela, String nome, Object valor, TipoConectivo tipoConectivo) {
		return criarRestricaoComAlias(aliasTabela, nome, TipoOperador.IGUAL, atribuirLowerCaseCasoSejaString(valor), tipoConectivo);
	}

	public static Restricoes maiorOuIgualQue(String nome, Object valor) {
		return criarRestricao(nome, TipoOperador.MAIORIGUAL, valor, TipoConectivo.AND);
	}

	public static Restricoes maiorOuIgualQue(String nome, Object valor, TipoConectivo tipoConectivo) {
		return criarRestricao(nome, TipoOperador.MAIORIGUAL, valor, tipoConectivo);
	}
	
	private static Object atribuirLowerCaseCasoSejaString(Object valor) {
		if(valor instanceof String){
			return toLowerCase(valor);
		}
		return valor;
	}
	
	public static Restricoes maiorOuIgualQueComAlias(String aliasTabela, String nome, Object valor) {
		return criarRestricaoComAlias(aliasTabela, nome, TipoOperador.MAIORIGUAL, valor, TipoConectivo.AND);
	}

	public static Restricoes maiorOuIgualQue(String aliasTabela, String nome, Object valor, TipoConectivo tipoConectivo) {
		return criarRestricaoComAlias(aliasTabela, nome, TipoOperador.MAIORIGUAL, valor, tipoConectivo);
	}
	
	public static Restricoes menorOuIgualQue(String nome, Object valor) {
		return criarRestricao(nome, TipoOperador.MENORIGUAL, valor, TipoConectivo.AND);
	}

	public static Restricoes menorOuIgualQue(String nome, Object valor, TipoConectivo tipoConectivo) {
		return criarRestricao(nome, TipoOperador.MENORIGUAL, valor, tipoConectivo);
	}

	public static Restricoes menorOuIgualQueComAlias(String aliasTabela, String nome, Object valor) {
		return criarRestricaoComAlias(aliasTabela, nome, TipoOperador.MENORIGUAL, valor, TipoConectivo.AND);
	}

	public static Restricoes menorOuIgualQue(String aliasTabela, String nome, Object valor, TipoConectivo tipoConectivo) {
		return criarRestricaoComAlias(aliasTabela, nome, TipoOperador.MENORIGUAL, valor, tipoConectivo);
	}

	public static Restricoes like(String nome, Object valor) {
		return criarRestricao(nome, TipoOperador.LIKE, adicionarPorcentagemAoValorPesquisado(atribuirLowerCaseCasoSejaString(valor)), TipoConectivo.AND);
	}

	public static Restricoes like(String nome, Object valor, TipoConectivo tipoConectivo) {
		return criarRestricao(nome, TipoOperador.LIKE, adicionarPorcentagemAoValorPesquisado(atribuirLowerCaseCasoSejaString(valor)), tipoConectivo);
	}

	public static Restricoes likeComAlias(String aliasTabela, String nome, Object valor) {
		return criarRestricaoComAlias(aliasTabela, nome, TipoOperador.LIKE, adicionarPorcentagemAoValorPesquisado(atribuirLowerCaseCasoSejaString(valor)), TipoConectivo.AND);
	}

	public static Restricoes like(String aliasTabela, String nome, Object valor, TipoConectivo tipoConectivo) {
		return criarRestricaoComAlias(aliasTabela, nome, TipoOperador.LIKE, adicionarPorcentagemAoValorPesquisado(atribuirLowerCaseCasoSejaString(valor)), tipoConectivo);
	}
	
	public static Restricoes diferenteDe(String nome, Object valor) {
		return criarRestricao(nome, TipoOperador.DIFERENTEDE, valor, TipoConectivo.AND);
	}
	
	public static Restricoes between(String nome, Object valorInicial, Object valorFinal) {
		return criarRestricao(nome, TipoOperador.BETWEEN, new Intervalo(valorInicial, valorFinal), TipoConectivo.AND);
	}
	
	public static Restricoes isNull(String nome) {
		return criarRestricao(nome, TipoOperador.ISNULL, TipoConectivo.AND);
	}
	
	public static Restricoes isNotNull(String nome) {
		return criarRestricao(nome, TipoOperador.ISNOTNULL, TipoConectivo.AND);
	}
	
	public String getNome() {
		return nome;
	}
	
	public Object getValor() {
		return valor;
	}

	public String getOperador() {
		return tipoOperador.getOperador();
	}
	
	public TipoOperador getTipoOperador(){
		return this.tipoOperador;
	}

	public String getAlias() {
		return alias;
	}

	public TipoConectivo getTipoConectivo() {
		return tipoConectivo;
	}
	
	public boolean isOperadorEhBetween() {
		return getOperador().equals(TipoOperador.BETWEEN.toString());
	}
	
	public boolean isOperadorNaoEhIsNullNemIsNotNull() {
		return !(TipoOperador.ISNULL.equals(getTipoOperador()) || TipoOperador.ISNOTNULL.equals(getTipoOperador()));
	}
	
	private static Restricoes criarRestricao(String nome, TipoOperador tipoOperador, Object valor, TipoConectivo tipoConectivo){
		return new Restricoes(nome, tipoOperador, valor, tipoConectivo);
	}

	private static Restricoes criarRestricaoComAlias(String aliasTabela, String nome, TipoOperador tipoOperador, Object valor, TipoConectivo tipoConectivo) {
		return new Restricoes(aliasTabela, nome, tipoOperador, valor, tipoConectivo);
	}
	
	private static Restricoes criarRestricao(String nome, TipoOperador tipoOperador,TipoConectivo tipoConectivo) {
		return new Restricoes(nome,tipoOperador, tipoConectivo);
	}
		
	private static String adicionarPorcentagemAoValorPesquisado(Object valor) {
		return "%" + valor.toString() + "%";
	}
	
	private static String toLowerCase(Object valor) {
		if(VerificadorUtil.naoEstaNulo(valor)) {
			return StringUtils.lowerCase(valor.toString());
		}
		
		return null;
	}
	
	public Restricoes seAtributoCompostoRetiraPonto() {
		
		if(StringUtils.contains(this.nome, ".")) {
			this.nome = StringUtils.remove(this.nome, ".");
		}
		return this;
	}
	
	public Restricoes cloneRestricao() {
		return new Restricoes(this.getAlias(), this.getNome(), this.getTipoOperador(), this.getValor(), this.getTipoConectivo());
	}
}
