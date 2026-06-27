package mx.com.qtx.progeven.provs;

import mx.com.qtx.progeven.core.IGestorOrganizacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

@Service
public class GestorOrganizacionCteRest implements IGestorOrganizacion{
	private static Logger bitacora = LoggerFactory.getLogger(GestorOrganizacionCteRest.class);

	private final Environment env;
	private final RestTemplate restTemplate;

    public GestorOrganizacionCteRest(Environment env, RestTemplate restTemplate) {
        this.env = env;
        this.restTemplate = restTemplate;
    }

    public Map<String,String> getMapAreas(){
		Map<String,String> mapAreas = new TreeMap<>();
		String urlAreas = getUrl();
		try {
			AreaDTO[] arrAreas = restTemplate.getForObject(urlAreas, AreaDTO[].class);
			Arrays.asList(arrAreas)
			      .forEach( a -> mapAreas.put(a.getNombre(),a.getCveArea()));
		}
		catch(RestClientResponseException rcrex) {
			bitacora.error("getMapAreas() ha fallado: "+ rcrex.getResponseBodyAsString());
			mapAreas.put("No disponibles", "ND");
		}
		catch(Exception ex) {
			bitacora.error("getMapAreas() ha fallado: "+ ex.getMessage());
			mapAreas.put("No disponibles", "ND");
		}
		return mapAreas;		
	}

	private String getUrl() {
		String url = this.env.getProperty("app.microservices.areas.url");
		return url;
	}
}
