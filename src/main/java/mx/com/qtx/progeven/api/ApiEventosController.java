package mx.com.qtx.progeven.api;

import java.util.Map;

import mx.com.qtx.progeven.core.IPublicadorNotificaciones;
import mx.com.qtx.progeven.core.InvocacionServicioException;
import mx.com.qtx.progeven.core.errores.ErrorAppInvocacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.com.qtx.progeven.core.IGestorTematicas;

@RestController
public class ApiEventosController {
	private static Logger bitacora = LoggerFactory.getLogger(ApiEventosController.class);
	
	@Autowired
	private IGestorTematicas gestorTematicas;
	@Autowired
	private IPublicadorNotificaciones notificador;

	@GetMapping(path = "/eventos/programacion", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getUiProgEventos() {
		bitacora.info("getUiProgEventos()");
		int numPersona = 2;
		
		Map<String, Integer> tematicas = gestorTematicas.getMapTematicas(numPersona);
		bitacora.info("Temáticas de persona num(" + numPersona
				+ "):" + tematicas.keySet());

		// 	Coreografía: enviar eventoProgramado a MessageBroker
		String objJsonEvento = TestUtil.getEventoAleatorioEnJson();
		this.notificador.emitirNotificacion(objJsonEvento);

		return tematicas.keySet().toString() + ", " + objJsonEvento;
		
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


}
