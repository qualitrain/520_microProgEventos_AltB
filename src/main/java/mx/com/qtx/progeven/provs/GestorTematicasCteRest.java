package mx.com.qtx.progeven.provs;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import mx.com.qtx.progeven.core.InvocacionServicioException;
import mx.com.qtx.progeven.core.errores.*;
import mx.com.qtx.progeven.core.errores.ErrorApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import mx.com.qtx.progeven.core.IGestorTematicas;

@Service
public class GestorTematicasCteRest implements IGestorTematicas{
	private static Logger bitacora = LoggerFactory.getLogger(GestorTematicasCteRest.class);

	private final Environment env;
	private final RestTemplate restTemplate;

	public GestorTematicasCteRest(Environment env, RestTemplate restTemplate) {
		this.env = env;
		this.restTemplate = restTemplate;
	}

	public Map<String,Integer> getMapTematicas(int numPersona){
		bitacora.info("getMapTematicas(" + numPersona + ")");

		String url = getUrl();
		// String url = getUrlErronea();
		Map<String,Integer> mapTematicas = new TreeMap<>();
		try {
			TematicaDTO[] arrTematicas = restTemplate.getForObject(url, TematicaDTO[].class, numPersona);
			Arrays.asList(arrTematicas)
			      .forEach( t -> mapTematicas.put(t.getNombre(),t.getIdTematica()));
		}
		catch(RestClientResponseException rcrex) {
			manejarErrorLlamadoMicroGstContactosPersona(numPersona, rcrex, mapTematicas);
		}
		catch(Exception ex) {
			bitacora.error("getMapTematicas("+numPersona+") ha fallado: "+ ex.getMessage());
			mapTematicas.put("Temáticas no disponibles", -1);
		}
		return mapTematicas;
	}

	private static void manejarErrorLlamadoMicroGstContactosPersona(int numPersona, RestClientResponseException rcrex,
																	Map<String, Integer> mapTematicas) {
		bitacora.error("getMapTematicas("+ numPersona +") ha fallado: "+ rcrex.getResponseBodyAsString());
		String cveError = getCveError(rcrex);
		switch(cveError){
			case ErrorApp.ERROR_NEGOCIO -> {
				ErrorAppNegocio errNegocio = rcrex.getResponseBodyAs(ErrorAppNegocio.class);
				bitacora.error(errNegocio.toString());
				throw new InvocacionServicioException("Invocacion errónea",errNegocio.getCveError(),errNegocio.getError() + ". " + errNegocio.getRegla());
			}
			case ErrorApp.ERROR_FORMATO -> {
				ErrorAppFormato errorFormato = rcrex.getResponseBodyAs(ErrorAppFormato.class);
				bitacora.error(errorFormato.toString());
			}
		}

		mapTematicas.put("Tematicas No disponibles", -1);
	}

	private static String getCveError(RestClientResponseException rcrex) {
		JsonReader reader = Json.createReader(new StringReader(rcrex.getResponseBodyAsString()));
		JsonObject errorJson = reader.readObject();
        return ( (JsonString)errorJson.get("cveError") ).getString();
	}

	private String getUrl() {
		String url = this.env.getProperty("app.microservices.tematicas.url");
		return url;
	}

	private String getUrlErronea() {
		String url = this.env.getProperty("app.microservices.tematicas.url.erronea");
		return url;
	}

}
