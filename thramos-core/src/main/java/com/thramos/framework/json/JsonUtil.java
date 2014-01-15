package com.thramos.framework.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	public static String convertListOfObjectsToJsonString(List<?> results) {
		ObjectMapper objectMapper = new HibernateAwareObjectMapper();
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dateFormat);
		try {
			ListEncapsulator listEncapsulator = new ListEncapsulator(results);
			return objectMapper.writeValueAsString(listEncapsulator);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Erro ao converter objeto para JSON!", e);
		}
	}
	
	public static String convertListOfObjectsIncludeLazyLoadedObjectsToJsonString(List<?> results) {
		ObjectMapper objectMapper = new HibernateAwareWithLazyLoadingObjectMapper();
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dateFormat);
		try {
			ListEncapsulator listEncapsulator = new ListEncapsulator(results);
			return objectMapper.writeValueAsString(listEncapsulator);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Erro ao converter objeto para JSON!", e);
		}
	}
	
	public static <E> E convertJSONToObject(String json, Class<E> classeEntidade) {
		ObjectMapper objectMapper = new HibernateAwareObjectMapper();
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		objectMapper.setDateFormat(dateFormat);
		try {
			E entidade = objectMapper.readValue(json, classeEntidade);
			return entidade;
		} catch (JsonParseException e) {
			throw new RuntimeException("Erro de Parse.", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Erro de Mapeamento.", e);
		} catch (IOException e) {
			throw new RuntimeException("Erro de Entrada e/ou Saída.", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <E> List<E> convertJSONToListObject(String json) {
		ObjectMapper objectMapper = new HibernateAwareObjectMapper();
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dateFormat);
		try {
			List<E> lista = objectMapper.readValue(json, List.class);
			return lista;
		} catch (JsonParseException e) {
			throw new RuntimeException("Erro de Parse.", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Erro de Mapeamento.", e);
		} catch (IOException e) {
			throw new RuntimeException("Erro de Entrada e/ou Saída.", e);
		}
	}

	public static <E> String convertObjectToJsonString(E objeto) {
		ObjectMapper objectMapper = new HibernateAwareObjectMapper();
		return convertToJson(objeto, objectMapper);
	}

	public static <E> String convertObjectToJsonStringWithLazyAssociation(E objeto) {
		ObjectMapper objectMapper = new HibernateAwareWithLazyLoadingObjectMapper();
		return convertToJson(objeto, objectMapper);
	}

	public static <E> String convertObjectToJsonStringWithTransientAnnotationEffectDisabled(E objeto) {
		HibernateAwareObjectMapper objectMapper = new HibernateAwareObjectMapper();
		objectMapper.disableTransientAnnotationEffect();
		return convertToJson(objeto, objectMapper);
	}

	private static <E> String convertToJson(E objeto, ObjectMapper objectMapper) {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dateFormat);
		try {
			return objectMapper.writeValueAsString(objeto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Erro ao converter objeto para JSON!", e);
		}
	}
	
}
