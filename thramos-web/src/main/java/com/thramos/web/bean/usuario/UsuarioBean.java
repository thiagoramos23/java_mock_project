package com.thramos.web.bean.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.thramos.facade.pessoa.PessoaFacade;
import com.thramos.facade.usuario.UsuarioFacade;
import com.thramos.framework.consulta.restricao.Restricoes;
import com.thramos.framework.facade.Facade;
import com.thramos.framework.util.VerificadorUtil;
import com.thramos.model.autenticacao.Usuario;
import com.thramos.model.pessoa.Pessoa;
import com.thramos.model.pessoa.SimNao;
import com.thramos.web.consulta.usuario.UsuarioConsulta;
import com.thramos.web.converter.pessoa.PessoaConversor;
import com.thramos.web.converter.simnaoparaboolean.SimNaoParaBooleanConversor;
import com.thramos.web.framework.combomodel.ItemCombo;
import com.thramos.web.generico.AbstractBean;

@Component
@ManagedBean
@Scope("view")
public class UsuarioBean extends AbstractBean<Usuario>{

	private UsuarioFacade usuarioFacade;
	private PessoaFacade pessoaFacade;
	private UsuarioConsulta consulta;
	
	private PessoaConversor pessoaConversor;
	private SimNaoParaBooleanConversor conversorSimNao;
	
	private boolean ativo;
	
	@Autowired
	public UsuarioBean(UsuarioFacade usuarioFacade, PessoaFacade pessoaFacade, UsuarioConsulta consulta) {
		this.usuarioFacade = usuarioFacade;
		this.pessoaFacade = pessoaFacade;
		this.consulta = consulta;
		this.pessoaConversor = new PessoaConversor();
		this.conversorSimNao = new SimNaoParaBooleanConversor();
		this.consulta.setEmpresaUsuarioLogado(getUsuarioLogado().getPessoa().getEmpresa());
	}
		
	public void fecharDetalhe() {
		setDetalharVisivel(false);
		limparEntidade();
	}
		
	@Override
	protected void limparEntidade() {
		setEntidade(new Usuario());
	}
	
	@Override
	public Usuario getEntidade() {
		if(VerificadorUtil.estaNulo(entidade)) {
			entidade = new Usuario();
		}
		return entidade;
	}
	
	@Override
	protected Facade<Usuario> getFacade() {
		return this.usuarioFacade;
	}
	
	public UsuarioConsulta getConsulta() {
		return consulta;
	}
	
	public List<ItemCombo> getListaPessoas() {
		List<ItemCombo> listaItemCombo = new ArrayList<ItemCombo>();
		Pessoa pessoaConsulta = new Pessoa();
		
		List<Restricoes> listaRestricoes = new ArrayList<Restricoes>();
		listaRestricoes.add(Restricoes.igual("empresa.id", getUsuarioLogado().getPessoa().getEmpresa().getId()));
		
		List<Pessoa> listaPessoas = this.pessoaFacade.consultarTodos(pessoaConsulta, listaRestricoes, pessoaConsulta.getCampoOrderBy());
		
		for (Pessoa pessoa : listaPessoas) {
				listaItemCombo.add(new ItemCombo(pessoa.getNome(), pessoa));
		}
		return listaItemCombo;
	}
	
	public PessoaConversor getPessoaConversor() {
		return pessoaConversor;
	}
	
	public SimNaoParaBooleanConversor getConversorSimNao() {
		return conversorSimNao;
	}

	public void setAtivo(boolean ativo) {
		entidade.setAtivo(ativo ? SimNao.SIM : SimNao.NAO);
		this.ativo = ativo;
	}
	
	public boolean isAtivo() {
		return SimNao.SIM.equals(entidade.getAtivo()) ? true : false;
	}
}
