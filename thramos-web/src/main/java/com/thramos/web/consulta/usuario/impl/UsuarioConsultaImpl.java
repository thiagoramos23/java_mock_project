package com.thramos.web.consulta.usuario.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thramos.facade.usuario.UsuarioFacade;
import com.thramos.framework.consulta.restricao.Restricoes;
import com.thramos.model.autenticacao.Usuario;
import com.thramos.model.empresa.Empresa;
import com.thramos.web.consulta.usuario.UsuarioConsulta;

@Component
public class UsuarioConsultaImpl extends LazyDataModel<Usuario> implements UsuarioConsulta{

	private static final long serialVersionUID = 1L;
	
	private UsuarioFacade usuarioFacade;
	private Empresa empresa;
	
	@Autowired
	public UsuarioConsultaImpl(UsuarioFacade usuarioFacade) {
		this.usuarioFacade = usuarioFacade;
	}

	@Override
	public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		Usuario usuario = new Usuario();
		
		List<Restricoes> listaRestricoes = new ArrayList<Restricoes>();
		listaRestricoes.add(Restricoes.igual("pessoa.empresa.id", this.empresa.getId()));
		
		Integer quantidadeRegistros = this.usuarioFacade.consultarQuantidadeRegistros(usuario, listaRestricoes, usuario.getCampoOrderBy());
		List<Usuario> listaUsuario = this.usuarioFacade.consultarTodosPaginado(usuario, first, pageSize, listaRestricoes, usuario.getCampoOrderBy());
		setRowCount(quantidadeRegistros);	
		return listaUsuario;
	}

	@Override
	public void setEmpresaUsuarioLogado(Empresa empresa) {
		this.empresa = empresa;
	}
}
