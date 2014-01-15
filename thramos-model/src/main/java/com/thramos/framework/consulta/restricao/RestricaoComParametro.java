package com.thramos.framework.consulta.restricao;

import static com.thramos.framework.consulta.Consulta.*;

public class RestricaoComParametro extends AbstractRestricao{

	@Override
	public void montarRetricaoParticular(Restricoes restricoes) {
		restricoes.seAtributoCompostoRetiraPonto();
		getSelect().append(DOIS_PONTOS)
		   .append(restricoes.getNome());
	}

}
