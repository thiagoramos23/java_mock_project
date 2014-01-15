package com.thramos.model.pessoa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;

import com.thramos.framework.contexto.Alterar;
import com.thramos.framework.contexto.Cadastrar;
import com.thramos.framework.util.ConstantesModelUtil;
import com.thramos.framework.util.StringUtil;
import com.thramos.framework.util.VerificadorUtil;
import com.thramos.model.empresa.Empresa;
import com.thramos.model.empresa.SetadorEmpresa;
import com.thramos.model.identidade.Identidade;
import com.thramos.model.ordenacao.Ordenacao;

@Entity
@Audited
@Table(name = "pessoas", schema = "teste")
public class Pessoa implements Serializable, Identidade, Ordenacao, SetadorEmpresa{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private Date criadoEm;
	private Date atualizadoEm = new Date();
	private TipoPessoa tipoPessoa;

	private Empresa empresa;
	private String cpf;
	private String rg;
	private Sexo sexo;
	private Endereco endereco = new Endereco();
	private String email;
	private String telefoneCelular;
	private String fotoUrl;
	
	public Pessoa() {}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull(message = "Nome não pode ser nulo.", groups = {Cadastrar.class, Alterar.class})
	@Column(name = "nome", nullable = false, length = 100)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) { 
		this.nome = nome;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "criado_em")
	public Date getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Date criadoEm) {
		if(VerificadorUtil.estaNulo(this.id)) {
			this.criadoEm = new Date();
		}
		this.criadoEm = criadoEm;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "atualizado_em")
	public Date getAtualizadoEm() {
		return atualizadoEm;
	}

	public void setAtualizadoEm(Date atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pessoa", nullable = false)
	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}
	
	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Column(name = "cpf", length = 11)
	@NotNull(message = "Cpf não pode ser nulo.", groups = {Cadastrar.class, Alterar.class})
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = StringUtil.removerMascaraDeCampo(cpf, "./-");;
	}

	@Column(name = "rg", length = 25)
	@NotNull(message = "RG não pode ser nulo.", groups = {Cadastrar.class, Alterar.class})
	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "sexo", nullable = false, length = 1)
	@NotNull(message = "Você deve escolher entre masculino ou feminino. O campo não pode ser nulo.", groups = {Cadastrar.class, Alterar.class})
	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	@Embedded
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Column(name = "email", length = 100)
	@NotNull(message = "O email não pode ser nulo.", groups = {Cadastrar.class, Alterar.class})
	@Email(message = "Este email não é válido.", groups = {Cadastrar.class, Alterar.class})
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "telefone_movel", length = 45)
	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	@Column(name = "foto", length = 500) 
	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	@Override
	@Transient
	public String getCampoOrderBy() {
		return "nome";
	}
	
	@Transient
	public String getFotoUrlEndereco() {
		return ConstantesModelUtil.BASE_PATH_URL + getFotoUrl();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		return result;
	}	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pessoa))
			return false;
		Pessoa other = (Pessoa) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}
	
}
