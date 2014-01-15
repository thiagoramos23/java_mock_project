package com.thramos.framework.consulta;

public class Order {

	private TipoOrderBy tipoOrderBy;
	private String colunaOrdernadora;
	private String alias;

	public Order(TipoOrderBy tipoOrderBy, String colunaOrdernadora) {
		this.tipoOrderBy = tipoOrderBy;
		this.colunaOrdernadora = colunaOrdernadora;
	}

	public Order(TipoOrderBy tipoOrderBy, String alias, String colunaOrdernadora) {
		this.tipoOrderBy = tipoOrderBy;
		this.colunaOrdernadora = colunaOrdernadora;
		this.alias = alias;
	}

	public static Order asc(String alias, String colunaOrdenadora) {
		return new Order(TipoOrderBy.ASC, alias, colunaOrdenadora);
	}
	
	public static Order desc(String alias, String colunaOrdenadora) {
		return new Order(TipoOrderBy.DESC, alias, colunaOrdenadora);
	}

	public static Order asc(String colunaOrdenadoraComAlias) {
		return new Order(TipoOrderBy.ASC, colunaOrdenadoraComAlias);
	}

	public static Order desc(String colunaOrdenadoraComAlias) {
		return new Order(TipoOrderBy.DESC, colunaOrdenadoraComAlias);
	}
	
	public TipoOrderBy getTipoOrderBy() {
		return tipoOrderBy;
	}
	
	public String getColunaOrdernadora() {
		return colunaOrdernadora;
	}
	
	public String getAlias() {
		return alias;
	}

}
