package com.thramos.restservice.resources.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thramos.framework.consulta.restricao.Restricoes;
import com.thramos.framework.json.JsonUtil;
import com.thramos.model.pessoa.Pessoa;
import com.thramos.service.pessoa.PessoaService;

@Component
@Path("/pessoas")
@Transactional
@Produces(MediaType.APPLICATION_JSON)
public class PessoaResourceImpl {

	@Autowired PessoaService pessoaService;
	
	@GET
	@Path("listar/{idEmpresa}")
	public Response listarPessoas(@PathParam("idEmpresa")Integer idEmpresa) {
		List<Restricoes> listaRestricoes = new ArrayList<Restricoes>();
		listaRestricoes.add(Restricoes.igual("empresa.id", idEmpresa));
		
		List<Pessoa> listaPessoas = pessoaService.consultarTodos(new Pessoa(), listaRestricoes, null);
		String jsonObject = JsonUtil.convertListOfObjectsToJsonString(listaPessoas);
		return Response.ok(jsonObject, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	@GET
	@Path("listar/{idEmpresa}/{descricao}")
	public Response listarPessoasPorNome(@PathParam("idEmpresa")Integer idEmpresa, @PathParam("descricao")String descricao) {
		List<Restricoes> listaRestricoes = new ArrayList<Restricoes>();
		listaRestricoes.add(Restricoes.igual("empresa.id", idEmpresa));
		listaRestricoes.add(Restricoes.like("nome", descricao));
		
		List<Pessoa> listaPessoas = pessoaService.consultarTodos(new Pessoa(), listaRestricoes, new Pessoa().getCampoOrderBy());
		String jsonObject = JsonUtil.convertListOfObjectsToJsonString(listaPessoas);
		return Response.ok(jsonObject, MediaType.APPLICATION_JSON_TYPE).build();
	}

}
