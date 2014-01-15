package com.thramos.model.pessoa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import com.thramos.framework.contexto.Alterar;
import com.thramos.framework.contexto.Cadastrar;
import com.thramos.framework.util.StringUtil;

@Embeddable
public class Endereco implements Serializable{

	private static final long serialVersionUID = 1L;

	private String cep;
	private String endereco;
	private String bairro;
	private String cidade;
	private String estado;
	
	@Column(name = "cep", length = 8)
	@NotNull(message = "O cep não pode ser nulo.", groups = {Cadastrar.class, Alterar.class})
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = StringUtil.removerMascaraDeCampo(cep, "./-");;
	}

	@Column(name = "endereco", length = 150)
	@NotNull(message = "A rua/avenida não pode ser nula.", groups = {Cadastrar.class, Alterar.class})
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Column(name = "bairro", length = 45)
	@NotNull(message = "O bairro não pode ser nulo.", groups = {Cadastrar.class, Alterar.class})
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@Column(name = "cidade", length = 100)
	@NotNull(message = "A cidade não pode ser nula.", groups = {Cadastrar.class, Alterar.class})
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Column(name = "estado", length = 2)
	@NotNull(message = "O estado não pode ser nulo.", groups = {Cadastrar.class, Alterar.class})
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
