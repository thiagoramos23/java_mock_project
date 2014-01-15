package com.thramos.construtor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;

public class ConstrutorClasses {

	private static final String BASE_PATH = "//Users//thiagoramos//Documents//PROJETOS//STANT//thramos";
	private static final String MODULO_CORE = BASE_PATH + "//thramos-core//src//main//java//";
	private static final String MODULO_INFRA = BASE_PATH + "//thramos-infra//src//main//java//";
	private static final String MODULO_WEB = BASE_PATH + "//thramos-web//src//main//java//";
	private static final String MODULO_WEB_PAGINA = BASE_PATH + "//thramos-web//src//main//webapp//paginas//";
	
	private static final String FACADE_INTERFACE = MODULO_CORE + "com//thramos//construtor//facade//AtividadeFacade.txt";
	private static final String FACADE_CLASS = MODULO_CORE + "com//thramos//construtor//facade//AtividadeFacadeImpl.txt";

	private static final String SERVICE_INTERFACE = MODULO_CORE + "com//thramos//construtor//service//AtividadeService.txt";
	private static final String SERVICE_CLASS = MODULO_CORE + "com//thramos//construtor//service//AtividadeServiceImpl.txt";

	private static final String REPOSITORY_INTERFACE = MODULO_CORE + "com//thramos//construtor//repository//AtividadeRepository.txt";
	private static final String REPOSITORY_CLASS = MODULO_CORE + "com//thramos//construtor//repository//AtividadeRepositoryImpl.txt";

	private static final String WEB_BEAN = MODULO_CORE + "com//thramos//construtor//bean//AtividadeBean.txt";
	private static final String WEB_CONSULTA_INTERFACE = MODULO_CORE + "com//thramos//construtor//consulta//AtividadeConsulta.txt";
	private static final String WEB_CONSULTA_CLASS = MODULO_CORE + "com//thramos//construtor//consulta//AtividadeConsultaImpl.txt";
	
	private static final String WEB_PAGINA = MODULO_CORE + "com//thramos//construtor//pagina//atividade_gerenciador.xhtml";

	public static void main(String[] args) throws IOException {
		String nomeEntidade = JOptionPane.showInputDialog("Digite o nome da entidade que deseja criar");
		int deveCriarPagina = JOptionPane.showOptionDialog(null, "Deve Criar Página", "Pergunta", JOptionPane.YES_NO_OPTION, 0, null, null, JOptionPane.YES_OPTION);
		criarFacade(nomeEntidade);
		criarService(nomeEntidade);
		criarRepository(nomeEntidade);
		criarBean(nomeEntidade);
		criarConsulta(nomeEntidade);
		
		if(deveCriarPagina == 0) {
			criarPagina(nomeEntidade);
		}
	}
	
	private static void criarPagina(String nomeEntidade) throws IOException {
		createPage(nomeEntidade);
	}

	private static void criarFacade(String nomeEntidade) throws IOException {
		createFacade(nomeEntidade, false);
		createFacade(nomeEntidade, true);
	}

	private static void criarService(String nomeEntidade) throws IOException {
		
		createService(nomeEntidade, false);
		createService(nomeEntidade, true);
	}

	private static void criarRepository(String nomeEntidade) throws IOException {
		createRepository(nomeEntidade, false);
		createRepository(nomeEntidade, true);
	}
	
	private static void criarBean(String nomeEntidade) throws IOException {
		createBean(nomeEntidade);
	}
	
	private static void criarConsulta(String nomeEntidade) throws IOException{
		createConsulta(nomeEntidade, false);
		createConsulta(nomeEntidade, true);
	}

	private static void createRepository(String nomeEntidade, boolean isImpl) throws IOException {
		System.out.println("Criando Repository");
		String newFilePath = MODULO_INFRA + "com//thramos//repository//" + StringUtils.uncapitalize(nomeEntidade) 
				+ "//" + (isImpl ? "//impl//" + nomeEntidade + "RepositoryImpl.java" : nomeEntidade +"Repository.java");
		
		criarArquivos((isImpl ? REPOSITORY_CLASS : REPOSITORY_INTERFACE), newFilePath, nomeEntidade, isImpl);
		
		System.out.println("Repository Criado");
	}
	
	private static void createService(String nomeEntidade, boolean isImpl) throws IOException {
		System.out.println("Criando Service");
		
		String newFilePath = MODULO_CORE + "com//thramos//service//" + StringUtils.uncapitalize(nomeEntidade) 
				+ "//" + (isImpl ? "//impl//" + nomeEntidade + "ServiceImpl.java" : nomeEntidade + "Service.java");
		
		criarArquivos((isImpl ? SERVICE_CLASS : SERVICE_INTERFACE), newFilePath, nomeEntidade, isImpl);
		
		System.out.println("Service Criado");
	}
	
	private static void createFacade(String nomeEntidade, boolean isImpl) throws IOException {
		System.out.println("Criando Facade");
		
		String newFilePath = MODULO_CORE + "com//thramos//facade//" + StringUtils.uncapitalize(nomeEntidade) 
				+ "//" + (isImpl ? "impl//" + nomeEntidade + "FacadeImpl.java" :  nomeEntidade + "Facade.java");
		criarArquivos(isImpl ? FACADE_CLASS : FACADE_INTERFACE, newFilePath, nomeEntidade, isImpl);
		
		System.out.println("Facade Criado");
	}

	private static void createBean(String nomeEntidade) throws IOException {
		System.out.println("Criando Bean");
		
		String newFilePath = MODULO_WEB + "com//thramos//web//bean//" + StringUtils.uncapitalize(nomeEntidade) 
				+ "//" + nomeEntidade + "Bean.java";
		criarArquivos(WEB_BEAN, newFilePath, nomeEntidade, false);
		
		System.out.println("Bean Criado");
	}

	private static void createConsulta(String nomeEntidade, boolean isImpl) throws IOException {
		System.out.println("Criando Consulta");
		
		String newFilePath = MODULO_WEB + "com//thramos//web//consulta//" + StringUtils.uncapitalize(nomeEntidade) 
				+ "//" + (isImpl ? "impl//" + nomeEntidade + "ConsultaImpl.java" :  nomeEntidade + "Consulta.java");
		criarArquivos((isImpl ? WEB_CONSULTA_CLASS : WEB_CONSULTA_INTERFACE), newFilePath, nomeEntidade, false);
		
		System.out.println("Consulta Criado");
	}

	private static void createPage(String nomeEntidade) throws IOException {
		System.out.println("Criando Pagina");
		
		String newFilePath = MODULO_WEB_PAGINA + StringUtils.uncapitalize(nomeEntidade) + "_gerenciador.xhtml";
		criarArquivos(WEB_PAGINA, newFilePath, nomeEntidade, false);
		
		System.out.println("Pagina Criada");
	}


	private static void criarArquivos(String oldFilePath, String newFilePath, String nomeEntidade, boolean isImpl) throws IOException {
		String nomeEntidadeUncapitalized = StringUtils.uncapitalize(nomeEntidade);
		
		File oldFile = new File(oldFilePath);
		File newFile = new File(newFilePath);
		newFile.getParentFile().mkdir();

		BufferedReader br = null;
		BufferedWriter bw = null;
		br = new BufferedReader(new FileReader(oldFile));
		bw = new BufferedWriter(new FileWriter(newFile));
		String line;
		while ((line = br.readLine()) != null) {
			if (line.contains("atividade")) {
				line = line.replace("atividade", nomeEntidadeUncapitalized); 
			}

			if(line.contains("Atividade")) {
				line = line.replace("Atividade", nomeEntidade);
			}
			
			if(line.contains("construtor.")) {
				line = line.replace("construtor.", "").replace(";", "");
				line = line + "." + nomeEntidadeUncapitalized + (isImpl ? ".impl;" : ";") ;
			}

			bw.write(line + "\n");
		}
		
		br.close();
		bw.close();
	}
}
