package com.thramos.model.empresa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thramos.framework.util.StringUtil;
import com.thramos.model.identidade.Identidade;
import com.thramos.model.ordenacao.Ordenacao;
import com.thramos.model.pessoa.Pessoa;

@Entity
@Audited
@Table(name = "empresas", schema = "teste")
public class Empresa implements Serializable, Identidade, Ordenacao{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nomeFantasia;
	private String razaoSocial;
	private String cnpj;
	private String telefone;
		
	private Set<Pessoa> pessoas = new HashSet<Pessoa>();
	
	public Empresa() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nome_fantasia") 
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	@Column(name = "razao_social", nullable = false)
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Column(name = "cnpj", nullable = false)
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = StringUtil.removerMascaraDeCampo(cnpj, "./-");
	}

	@Column(name = "telefone")
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = StringUtil.removerMascaraDeCampo(this.telefone, "()-");
	}

	
	@JsonIgnore
	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
	public Set<Pessoa> getPessoas() {
		return pessoas;
	}
	
	public void setPessoas(Set<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	@Override
	@Transient
	public String getCampoOrderBy() {
		return "nomeFantasia";
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
		if (!(obj instanceof Empresa))
			return false;
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
