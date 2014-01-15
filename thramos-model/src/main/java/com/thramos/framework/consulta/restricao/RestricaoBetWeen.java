package com.thramos.framework.consulta.restricao;

import static com.thramos.framework.consulta.Consulta.*;

import com.thramos.framework.consulta.TipoConectivo;

public class RestricaoBetWeen extends AbstractRestricao{
	
	@Override
	public void montarRetricaoParticular(Restricoes restricoes) {
		restricoes.seAtributoCompostoRetiraPonto();
		getSelect().append(DOIS_PONTOS)
		.append(restricoes.getNome()+INICIAL)
        .append(ESPACO)
        .append(TipoConectivo.AND.toString())
        .append(ESPACO)
        .append(DOIS_PONTOS)
		.append(restricoes.getNome()+FINAL);
	}
}
