package com.thramos.web.generico;

import static com.thramos.web.constantes.ConstantesUtilWeb.IDENTIFICADOR_MENSAGEM_ALTERADO_COM_SUCESSO;
import static com.thramos.web.constantes.ConstantesUtilWeb.IDENTIFICADOR_MENSAGEM_CADASTRADO_COM_SUCESSO;
import static com.thramos.web.constantes.ConstantesUtilWeb.IDENTIFICADOR_MENSAGEM_EXCLUIDO_COM_SUCESSO;
import static com.thramos.web.constantes.ConstantesUtilWeb.MENSAGEM_ALERTA;
import static com.thramos.web.constantes.ConstantesUtilWeb.MENSAGEM_AVISO;
import static com.thramos.web.constantes.ConstantesUtilWeb.MENSAGEM_ERRO;
import static com.thramos.web.constantes.ConstantesUtilWeb.MENSAGEM_ERRO_FATAL;
import static com.thramos.web.constantes.ConstantesUtilWeb.MENSAGEM_SUCESSO;
import static com.thramos.web.constantes.ConstantesUtilWeb.REGISTRO_ALTERADO_COM_SUCESSO;
import static com.thramos.web.constantes.ConstantesUtilWeb.REGISTRO_CADASTRADO_COM_SUCESSO;
import static com.thramos.web.constantes.ConstantesUtilWeb.REGISTRO_EXCLUIDO_COM_SUCESSO;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.thramos.framework.facade.Facade;
import com.thramos.framework.security.authentication.ProjetoMockStant;
import com.thramos.framework.util.ImageAndTokenUtil;
import com.thramos.framework.util.VerificadorUtil;
import com.thramos.model.autenticacao.Usuario;
import com.thramos.model.empresa.SetadorEmpresa;

public abstract class AbstractBean<E> implements GenericBean<E> {

	public E entidade;
	
	private boolean detalharVisivel = false;
	private boolean cadastrarVisivel = false;
	
	public HashMap<String, String> getMensagens() {
		HashMap<String, String> mensagens = new HashMap<String, String>();
		mensagens.put(IDENTIFICADOR_MENSAGEM_CADASTRADO_COM_SUCESSO, REGISTRO_CADASTRADO_COM_SUCESSO);
		mensagens.put(IDENTIFICADOR_MENSAGEM_ALTERADO_COM_SUCESSO, REGISTRO_ALTERADO_COM_SUCESSO);
		mensagens.put(IDENTIFICADOR_MENSAGEM_EXCLUIDO_COM_SUCESSO, REGISTRO_EXCLUIDO_COM_SUCESSO);
		return mensagens;
	}
	
	protected abstract void limparEntidade();	
	protected abstract E getEntidade();
	protected abstract Facade<E> getFacade();
	
	protected void modificarEntidadeAntesDaOperacaoParaCadastrar(E entidade){}
	protected void modificarEntidadeAntesDaOperacaoParaAlterar(E entidade){}
	protected void modificarEntidadeAntesDeCadastrarEhOuAlterar(E entidade){}
	
	public void setSelecionar(E entidade) {
		setDetalharVisivel(true);
		this.setEntidade(entidade);
	}

	public void setSelecionarAlterar(E entidade) {
		setCadastrarVisivel(true);
		this.setEntidade(entidade);
	}
	
	public void novo() {
		limparEntidade();
		setCadastrarVisivel(true);
	}
	
	public void setEntidade(E entidade) {
		this.entidade = entidade;
	}
	
	@Override
	public void cadastrar(final E entidade) {
		new VerificadorLancamentoException().tratarIhRelancarExcecaoSemLimparEntidade(new CommandBean() {

			public void execute() {
				modificarEntidadeAntesDeCadastrarEhOuAlterar(entidade);
				modificarEntidadeAntesDaOperacaoParaCadastrar(entidade);
				adicionarEmpresaSeNecessario(entidade);
				getFacade().cadastrar(entidade);
				limparEntidade();
				setCadastrarVisivel(false);
				lancarSucesso(getMensagens().get(IDENTIFICADOR_MENSAGEM_CADASTRADO_COM_SUCESSO));
			}

			private void adicionarEmpresaSeNecessario(E entidade) {
				if(entidade instanceof SetadorEmpresa) {
					((SetadorEmpresa) entidade).setEmpresa(getUsuarioLogado().getPessoa().getEmpresa());
				}
			}
		});
	}

	@Override
	public void alterar(final E entidade) {
		new VerificadorLancamentoException().tratarIhRelancarExcecaoSemLimparEntidade(new CommandBean() {

			public void execute() {
				modificarEntidadeAntesDeCadastrarEhOuAlterar(entidade);
				modificarEntidadeAntesDaOperacaoParaAlterar(entidade);
				getFacade().alterar(entidade);
				limparEntidade();
				setCadastrarVisivel(false);
				lancarSucesso(getMensagens().get(IDENTIFICADOR_MENSAGEM_ALTERADO_COM_SUCESSO));
			}
		});
	}

	@Override
	public void excluir(final E entidade) {
		new VerificadorLancamentoException().tratarIhRelancarExcecaoSemLimparEntidade(new CommandBean() {

			public void execute() {
				getFacade().excluir(entidade);
				limparEntidade();
				lancarSucesso(getMensagens().get(IDENTIFICADOR_MENSAGEM_EXCLUIDO_COM_SUCESSO));
			}
		});
	}
	
	@Override
	public E consultarPorId() {
		return getFacade().consultarPorId(getEntidade());
	}

	@Override
	public List<E> consultarTodos() {
		return getFacade().consultarTodos(getEntidade());
	}
	
	public void fecharDetalhe() {
		setDetalharVisivel(false);
	}
	
	public Usuario getUsuarioLogado() {
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		ProjetoMockStant userStant = (ProjetoMockStant) authentication.getPrincipal();
		return userStant.getUsuario();
	}
	
	public FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	public HttpServletRequest getRequest() {
		return verificarContexto() ? (HttpServletRequest) getContext().getExternalContext().getRequest() : null; 
	}

	public HttpServletResponse getResponse() {
		return verificarContexto() ? (HttpServletResponse) getContext().getExternalContext().getResponse() : null;
	}
	
	public boolean isDetalharVisivel() {
		return detalharVisivel;
	}
	
	public void setDetalharVisivel(boolean detalharVisivel) {
		this.detalharVisivel = detalharVisivel;
	}
	
	public boolean isCadastrarVisivel() {
		return cadastrarVisivel;
	}
	
	public void setCadastrarVisivel(boolean cadastrarVisivel) {
		this.cadastrarVisivel = cadastrarVisivel;
	}

	public HttpSession getSession() {
		return (HttpSession) getContext().getExternalContext().getSession(false);
	}

	public void redirecionarParaTela(String tela) {
		try {
			getContext().getExternalContext().redirect(getContext().getExternalContext().getRequestContextPath() + tela);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao redirecionar a tela", e);
		}
	}
	
	protected void lancarSucesso(String mensagem) {  
		getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MENSAGEM_SUCESSO, mensagem));  
    }  
	
	protected void lancarInformacao(String mensagem) {  
		getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MENSAGEM_AVISO, mensagem));  
	}  
  
	protected void lancarAlerta(String mensagem) {  
		getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MENSAGEM_ALERTA, mensagem));  
    }  
  
	protected void lancarErro(String mensagem) {
		if(mensagem.contains(";")) {
			String[] mensagens = mensagem.split(";");
			for (String mensagemDeErro : mensagens) {
				getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MENSAGEM_ERRO, mensagemDeErro));  
			}
		} else {
			getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MENSAGEM_ERRO, mensagem));  
		}
    }  

	protected void lancarErroFatal(String mensagem) {  
		getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, MENSAGEM_ERRO_FATAL, mensagem));  
    }
	
	protected void lancarSucessoRedirecionandoTelaComCallBack(String mensagem, String urlCallBack) {
		try {
			getContext().getExternalContext().redirect(urlCallBack);
			getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MENSAGEM_SUCESSO, mensagem));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void uploadImage(InputStream inputStream, String filePath, String fileName) {
		ImageAndTokenUtil.uploadImages(inputStream, filePath, fileName);
	}
	
	private boolean verificarContexto() {
		FacesContext context = getContext();
		return VerificadorUtil.naoEstaNulo(context) && VerificadorUtil.naoEstaNulo(context.getExternalContext());
	}
	
	/*
	 * Classe privada para tratamento de exceção nos beans
	 */
	protected class VerificadorLancamentoException {

		public VerificadorLancamentoException() {}

		public void tratarIhRelancarExcecaoLimpandoEntidade(CommandBean command) {
			try {
				command.execute();
			} catch (Exception e) {
				setarEntidadeParaNuloParaOhContextoDeCadastrar();
				lancarErro(e.getMessage());
			}
		}
		
		public void tratarIhRelancarExcecaoSemLimparEntidade(CommandBean command) {
			try {
				command.execute();
			} catch (Exception e) {
				lancarErro(e.getMessage());
			}
		}

		private void setarEntidadeParaNuloParaOhContextoDeCadastrar() {
			setEntidade(null);
		}
	}
	
	public interface CommandBean {
		
		void execute();
	}

}