package com.thramos.web.bean.pessoa;

import java.io.IOException;
import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import org.glassfish.hk2.runlevel.RunLevelException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.thramos.facade.pessoa.PessoaFacade;
import com.thramos.framework.facade.Facade;
import com.thramos.framework.util.VerificadorUtil;
import com.thramos.model.pessoa.Pessoa;
import com.thramos.model.pessoa.Sexo;
import com.thramos.model.pessoa.TipoPessoa;
import com.thramos.web.constantes.enumerator.Estado;
import com.thramos.web.consulta.pessoa.PessoaConsulta;
import com.thramos.web.generico.AbstractBean;

@Component
@ManagedBean
@Scope("view")
public class PessoaBean extends AbstractBean<Pessoa>{

	public static final String DIRETORIO_FOTO_PESSOA = "pessoa/";
	
	private PessoaFacade pessoaFacade;
	private PessoaConsulta consulta;
	
	private UploadedFile uploadedFile;
	private InputStream inputStream;
	
	@Autowired
	public PessoaBean(PessoaFacade pessoaFacade, PessoaConsulta consulta) {
		this.pessoaFacade = pessoaFacade;
		this.consulta = consulta;
		this.consulta.setEmpresa(getUsuarioLogado().getPessoa().getEmpresa());
		
	}
	
	@Override
	protected void modificarEntidadeAntesDeCadastrarEhOuAlterar(Pessoa entidade) {
		if(VerificadorUtil.naoEstaNulo(uploadedFile)) {
			uploadImage(inputStream, DIRETORIO_FOTO_PESSOA, uploadedFile.getFileName());
		}
	}
	
	public void fecharDetalhe() {
		setDetalharVisivel(false);
		limparEntidade();
	}
 		
	@Override
	protected void limparEntidade() {
		setEntidade(new Pessoa());
	}
	
	@Override
	public Pessoa getEntidade() {
		if(VerificadorUtil.estaNulo(entidade)) {
			entidade = new Pessoa();
		}
		return entidade;
	}
	
	@Override
	protected Facade<Pessoa> getFacade() {
		return this.pessoaFacade;
	}
	
	public PessoaConsulta getConsulta() {
		return consulta;
	}

	public void handleFileUpload(FileUploadEvent event) {		
		try {
			uploadedFile = event.getFile();
			inputStream = uploadedFile.getInputstream();
			getEntidade().setFotoUrl(DIRETORIO_FOTO_PESSOA + uploadedFile.getFileName()); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SelectItem[] getSexoValues() {
		int i = 0;
		SelectItem[] items = new SelectItem[Sexo.values().length];
		for(Sexo sexo : Sexo.values()) {
			items[i++] = new SelectItem(sexo, sexo.getDescricao());
		}
		
		return items;
	}
		
	public SelectItem[] getTipoPessoaValues() {
		int i = 0;
		SelectItem[] items = new SelectItem[TipoPessoa.values().length];
		for(TipoPessoa tipoPessoa : TipoPessoa.values()) {
			items[i++] = new SelectItem(tipoPessoa, tipoPessoa.getValue());
		}
		
		return items;
	}
	
	public SelectItem[] getListaEstados() {
		int i = 0;
		SelectItem[] items = new SelectItem[Estado.values().length];
		for(Estado estado: Estado.values()) {
			items[i++] = new SelectItem(estado, estado.getEstado());
		}
		
		return items;
	}
	
	public StreamedContent getFotoUploaded() {
		try {
			if(VerificadorUtil.naoEstaNulo(uploadedFile)) {
				DefaultStreamedContent content = new DefaultStreamedContent(inputStream, "image/jpeg");
				return content;
			}
			
			return null;
		} catch (Exception e) {
			throw new RunLevelException("Falha ao carregar imagem");
		}
	}
}
