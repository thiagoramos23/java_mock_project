package com.thramos.model.autenticacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.thramos.model.identidade.Identidade;

@Entity
@Audited
@Table(name = "acao", schema = "teste")
public class Acao implements Serializable, Identidade{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descricao;
	
	public Acao() {}
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "descricao", length = 200, nullable = false)
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}