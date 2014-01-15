package com.thramos.web.generico;

import java.util.List;

public interface GenericBean<ENTIDADE> {

	void cadastrar(ENTIDADE entidade);

	void alterar(ENTIDADE entidade);

	void excluir(ENTIDADE entidade);
	
	ENTIDADE consultarPorId();
	
	List<ENTIDADE> consultarTodos();

}