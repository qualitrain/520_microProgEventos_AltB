package mx.com.qtx.progeven.core;

public class InvocacionServicioException extends RuntimeException{
    private String cveError;
    private String descripcion;

    public InvocacionServicioException(String message, String cveError, String descripcion) {
        super(message);
        this.cveError = cveError;
        this.descripcion = descripcion;
    }

    public String getCveError() {
        return cveError;
    }

    public void setCveError(String cveError) {
        this.cveError = cveError;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
