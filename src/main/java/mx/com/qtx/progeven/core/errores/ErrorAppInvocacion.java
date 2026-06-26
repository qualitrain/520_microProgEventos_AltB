package mx.com.qtx.progeven.core.errores;

public class ErrorAppInvocacion extends ErrorApp {
    private String descripcion;

    public ErrorAppInvocacion(String cveError, String descripcion) {
        super(cveError);
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
