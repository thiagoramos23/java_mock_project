package com.thramos.model.autenticacao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thramos.framework.contexto.Alterar;
import com.thramos.framework.contexto.Cadastrar;
import com.thramos.model.empresa.Empresa;
import com.thramos.model.identidade.Identidade;
import com.thramos.model.ordenacao.Ordenacao;
import com.thramos.model.pessoa.Pessoa;
import com.thramos.model.pessoa.SimNao;

@Entity
@Audited
@Table(name = "usuarios", schema = "teste")
public class Usuario implements Serializable, Identidade, Ordenacao{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Pessoa pessoa;
	private String login;
	private String senha;
	private Date ultimoAcesso;
	private SimNao ativo = SimNao.SIM;
	private String tokenAcesso;
	private Set<UsuarioPermissao> permissoes = new HashSet<UsuarioPermissao>();
	
	public Usuario() {}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "pessoa_id", nullable = false)
	@NotNull(message = "Pessoa não pode ser nula.", groups = {Cadastrar.class, Alterar.class})
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Column(name= "login", length = 45)
	@NotNull(message = "Login deve ser preenchido.", groups = {Cadastrar.class, Alterar.class})
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name= "senha", length = 300)
	@NotAudited
	@JsonIgnore
	@NotNull(message = "Senha deve ser preenchida.", groups = {Cadastrar.class, Alterar.class})
	public String getSenha() {
		return senha;
	}	

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "ultimo_acesso", nullable = false)
	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "ativo", nullable = false)
	public SimNao getAtivo() {
		return ativo;
	}

	public void setAtivo(SimNao ativo) {
		this.ativo = ativo;
	}
	
	@Column(name = "token_acesso", length = 1000)
	public String getTokenAcesso() {
		return tokenAcesso;
	}
	
	public void setTokenAcesso(String tokenAcesso) {
		this.tokenAcesso = tokenAcesso;
	}
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	public Set<UsuarioPermissao> getPermissoes() {
		return permissoes;
	}
	
	public void setPermissoes(Set<UsuarioPermissao> permissoes) {
		this.permissoes = permissoes;
	}
	
	@Override
	@Transient
	public String getCampoOrderBy() {
		return "login";
	}
	
	@Transient
	public Empresa getEmpresa() {
		return this.getPessoa().getEmpresa();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
