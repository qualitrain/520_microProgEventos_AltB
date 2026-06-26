package mx.com.qtx.progeven.core.errores;

public class ErrorApp {
    public static final String ERROR_FORMATO = "FMT_INV_01";
    public static final String ERROR_NEGOCIO = "REGLA_NEGOCIO";

    private String cveError;

    public ErrorApp(String cveError) {
        this.cveError = cveError;
    }

    public String getCveError() {
        return cveError;
    }

    public void setCveError(String cveError) {
        this.cveError = cveError;
    }

    @Override
    public String toString() {
        return "Error{" +
                "cveError='" + cveError + '\'' +
                '}';
    }
}
