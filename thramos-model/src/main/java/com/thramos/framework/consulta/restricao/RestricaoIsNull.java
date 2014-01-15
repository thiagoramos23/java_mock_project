package com.thramos.framework.consulta.restricao;



public class RestricaoIsNull extends AbstractRestricao  {
	@Override
	public void montarRetricaoParticular(Restricoes restricoes) {
		getSelect().append(restricoes.getOperador());
	}
}
