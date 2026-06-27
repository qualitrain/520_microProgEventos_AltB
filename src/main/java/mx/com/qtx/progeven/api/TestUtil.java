package mx.com.qtx.progeven.api;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import org.jspecify.annotations.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TestUtil {
    private static final Random random = new Random(42);
    private static int numEvento = 400;

    // Catálogo fijo de personas (numEmpleado -> datos completos)
    private static final Map<Integer, Persona> catalogoPersonas = new LinkedHashMap<>();

    static {
        catalogoPersonas.put(501, new Persona(501, "José Miguel Torres Aragón",
                "jmtorres@laempresa.com", "55-11-34-11-22"));
        catalogoPersonas.put(3421, new Persona(3421, "Mariana Valdés Forlán",
                "mvaldes@laempresa.com", "77-12-33-91-45"));
        catalogoPersonas.put(552, new Persona(552, "Juan Manuel Tinoco Morales",
                "jmtinoco@laempresa.com", "33-19-99-01-03"));
        catalogoPersonas.put(1201, new Persona(1201, "Ramiro López Angulo",
                "rlopez@laempresa.com", "55-22-33-44-55"));
        catalogoPersonas.put(789, new Persona(789, "Laura Jiménez García",
                "ljimenez@laempresa.com", "55-98-76-54-32"));
        catalogoPersonas.put(1555, new Persona(1555, "Carlos Mendoza Ruiz",
                "cmendoza@laempresa.com", "81-44-55-66-77"));
        catalogoPersonas.put(234, new Persona(234, "Ana Patricia Solís",
                "asolis@laempresa.com", "33-77-88-99-00"));
        catalogoPersonas.put(1103, new Persona(1103, "Fernando Rivas Cortés",
                "frivas@laempresa.com", "55-44-33-22-11"));
        catalogoPersonas.put(2876, new Persona(2876, "Gabriela Núñez Prado",
                "gnunez@laempresa.com", "33-12-34-56-78"));
        catalogoPersonas.put(401, new Persona(401, "Roberto Delgado Mejía",
                "rdelgado@laempresa.com", "81-99-88-77-66"));
    }

    // Catálogo de motivos/objetivos de reunión
    private static final List<String> objetivos = List.of(
            "Revisar avances proyecto Midas-2020",
            "Planificación del sprint quincenal",
            "Retrospectiva mensual del equipo",
            "Sesión de lluvia de ideas para nueva funcionalidad",
            "Revisión de arquitectura del sistema",
            "Capacitación sobre nuevas tecnologías",
            "Junta de seguimiento de incidencias",
            "Presentación de resultados del trimestre",
            "Definición de métricas de rendimiento",
            "Coordinación interdepartamental"
    );

    // Duraciones posibles en minutos
    private static final int[] duraciones = {30, 45, 60, 90, 120};

    private record Persona(int numEmpleado, String nombre, String correoElectronico, String telefono) {
    }

    private static int getIndiceAleatorio(int tamaño) {
        return random.nextInt(tamaño);
    }

    private static int getNumParticipantesAleatorio() {
        return random.nextInt(3) + 2; // 2, 3 o 4
    }

    private static String getObjetivoAleatorio() {
        return objetivos.get(random.nextInt(objetivos.size()));
    }

    private static long getLongAleatorio(long limite) {
        return (long) (random.nextDouble() * limite);
    }

    private static int getHoraAleatoria() {
        return random.nextInt(8) + 9; // 9 a 16
    }

    private static int getMinutoAleatorio() {
        return random.nextInt(60); // 0 a 59
    }

    private static int getDuracionAleatoria() {
        return duraciones[random.nextInt(duraciones.length)];
    }

    public static String getEventoAleatorioEnJson() {
        List<Integer> clavesPersonas = new ArrayList<>(catalogoPersonas.keySet());
        // 1. Seleccionar propietario aleatorio
        int clavePropietario = clavesPersonas.get(getIndiceAleatorio(clavesPersonas.size()));
        Persona propietario = catalogoPersonas.get(clavePropietario);

        // 2. Seleccionar 2-4 participantes distintos al propietario
        List<Integer> idsInvitados = getInvitadosEvento(clavesPersonas, clavePropietario);

        // 3. Seleccionar objetivo aleatorio
        String objetivo = getObjetivoAleatorio();

        // 4. Generar fecha aleatoria entre 2020-01-01 y 2026-12-31
        final int[] paramsFecInicial = {2020,1,1,9,0};
        final int[] paramsFecFinal = {2026,12,31,17,0};
        LocalDateTime fechaAleatoria = getFechaAleatoriaEntre(paramsFecInicial, paramsFecFinal);
        String fechaProg = fechaAleatoria.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " CST";

        // 5. Duración aleatoria
        int duracion = getDuracionAleatoria();

        // 6. Construir JSON
        JsonObject evtoJson = getEventoEnJson(idsInvitados, propietario, objetivo, fechaProg, duracion);

        return evtoJson.toString();
    }

    private static JsonObject getEventoEnJson(List<Integer> idsInvitados, Persona propietario, String objetivo, String fechaProg, int duracion) {
        JsonObjectBuilder evtoProgJsonBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrParticipantes = Json.createArrayBuilder();
        int numParticipante = 1;
        for (int claveEmpleado : idsInvitados) {
            Persona p = catalogoPersonas.get(claveEmpleado);
            arrParticipantes.add(Json.createObjectBuilder()
                    .add("numParticipante", numParticipante++)
                    .add("numEmpleado", p.numEmpleado())
                    .add("nombre", p.nombre())
                    .add("correoElectronico", p.correoElectronico())
                    .add("telefono", p.telefono())
                    .build());
        }

        JsonObject evtoJson = evtoProgJsonBuilder
                .add("numPersonaPropietario", propietario.numEmpleado())
                .add("nombre", propietario.nombre())
                .add("objetivo", objetivo)
                .add("fechaProg", fechaProg)
                .add("duracionProgMin", duracion)
                .add("estado", 0)
                .add("numEvento", numEvento++)
                .add("participantes", arrParticipantes)
                .build();
        return evtoJson;
    }

    private static @NonNull LocalDateTime getFechaAleatoriaEntre(int[] paramsFecInicial, int[] paramsFecFinal) {
        LocalDateTime fechaInicio = LocalDateTime.of(paramsFecInicial[0], paramsFecInicial[1], paramsFecInicial[2], paramsFecInicial[3], paramsFecInicial[4]);
        LocalDateTime fechaFin = LocalDateTime.of(paramsFecFinal[0], paramsFecFinal[1], paramsFecFinal[2], paramsFecFinal[3], paramsFecFinal[4]);
        long segundosRango = java.time.Duration.between(fechaInicio, fechaFin).getSeconds();
        LocalDateTime fechaAleatoria = fechaInicio.plusSeconds(getLongAleatorio(segundosRango + 1));
        // Ajustar hora entre 9:00 y 17:00
        int hora = getHoraAleatoria();
        int minuto = getMinutoAleatorio();
        fechaAleatoria = fechaAleatoria.withHour(hora).withMinute(minuto).withSecond(0).withNano(0);
        return fechaAleatoria;
    }

    private static @NonNull List<Integer> getInvitadosEvento(List<Integer> clavesPersonas, int clavePropietario) {
        List<Integer> candidatos = new ArrayList<>(clavesPersonas);
        candidatos.remove(Integer.valueOf(clavePropietario));
        int numParticipantes = getNumParticipantesAleatorio();
        List<Integer> seleccionados = new ArrayList<>();
        for (int i = 0; i < numParticipantes && !candidatos.isEmpty(); i++) {
            int idx = getIndiceAleatorio(candidatos.size());
            seleccionados.add(candidatos.remove(idx));
        }
        return seleccionados;
    }
}