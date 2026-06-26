package mx.com.qtx.progeven.api;

import java.util.Map;

import mx.com.qtx.progeven.core.InvocacionServicioException;
import mx.com.qtx.progeven.core.errores.ErrorAppInvocacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import mx.com.qtx.progeven.core.IGestorTematicas;

@RestController
public class ApiEventosController {
	private static int numEvento = 400;
	private static Logger bitacora = LoggerFactory.getLogger(ApiEventosController.class); 
	
	@Autowired
	private IGestorTematicas gestorTematicas;

	@GetMapping(path = "/eventos/programacion", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getUiProgEventos() {
		bitacora.info("getUiProgEventos()");
		int numPersona = 2;
		
		Map<String, Integer> tematicas = gestorTematicas.getMapTematicas(numPersona);
		bitacora.info("Temáticas de persona num(" + numPersona
				+ "):" + tematicas.keySet());

		return tematicas.keySet().toString();
		
	}

	@GetMapping(path = "/testTematicas/{numPersona}", produces = MediaType.TEXT_PLAIN_VALUE)
	public String testInvocarMicroGstContactosPersona(@PathVariable Integer numPersona) {
		bitacora.info("testInvocarMicroGstContactosPersona()");

		Map<String, Integer> tematicas = gestorTematicas.getMapTematicas(numPersona);
		bitacora.info("Temáticas de persona num(" + numPersona
				+ "):" + tematicas.keySet());

		return tematicas.keySet().toString();

	}

	@GetMapping(path = "/eventos/tematicas/{numPersona}", produces = MediaType.TEXT_PLAIN_VALUE)
	public String testInvocacionGetorTematicas(@PathVariable int numPersona) {
		bitacora.info("testInvocacionGetorTematicas()");

		Map<String, Integer> tematicas = gestorTematicas.getMapTematicas(numPersona);
		bitacora.info("Temáticas de persona num(" + numPersona
				+ "):" + tematicas.keySet());

		return tematicas.keySet().toString();

	}

	@ExceptionHandler
	public ResponseEntity<ErrorAppInvocacion> manejarErroresNegocio(
			InvocacionServicioException nex){

		ErrorAppInvocacion err = new ErrorAppInvocacion(nex.getCveError(),nex.getDescripcion());
		return new ResponseEntity<ErrorAppInvocacion>(err, HttpStatus.NOT_ACCEPTABLE);

	}

	private String getEventoJSon() {
		JsonObjectBuilder evtoProgJsonBuilder = Json.createObjectBuilder();
		JsonObject evtoJson = evtoProgJsonBuilder
		            .add("numPersonaPropietario", 1201)
		            .add("nombre", "Ramiro López Angulo")
		            .add("objetivo", "Revisar avances proyecto Midas-2020")
		            .add("fechaProg", "2020-07-05 11:15 CST")
		            .add("duracionProgMin", 90)
		            .add("estado", 0)
		            .add("numEvento", numEvento++)
		            .add("participantes",Json.createArrayBuilder()
		            		                 .add(Json.createObjectBuilder()
		            		                		  .add("numParticipante", 1)
		            		                          .add("numEmpleado", 501)
		            		                          .add("nombre","José Miguel Torres Aragón")
		            		                          .add("correoElectronico", "jmtorres@laempresa.com")
		            		                          .add("telefono", "55-11-34-11-22")
		            		                          .build())
		            		                 .add(Json.createObjectBuilder()
		            		                		  .add("numParticipante", 2)
		            		                          .add("numEmpleado", 3421)
		            		                          .add("nombre","Mariana Valdés Forlán")
		            		                          .add("correoElectronico", "mvaldes@laempresa.com")
		            		                          .add("telefono", "77-12-33-91-45")
		            		                          .build())
		            		                 .add(Json.createObjectBuilder()
		            		                		  .add("numParticipante", 3)
		            		                          .add("numEmpleado", 552)
		            		                          .add("nombre","Juan Manuel Tinoco Morales")
		            		                          .add("correoElectronico", "jmtinoco@laempresa.com")
		            		                          .add("telefono", "33-19-99-01-03")
		            		                          .build())
		                                    .build())
		         .build();
		return evtoJson.toString();
	}
}
