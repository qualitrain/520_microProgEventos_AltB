package mx.com.qtx.progeven.core.errores;

public class ErrorAppNegocio extends ErrorApp {
    public static final int SE_REQUIERE_PERSONA_EXISTENTE = 10001;

    private String error;
    private String regla;

    public ErrorAppNegocio(String error, String regla) {
        super(ErrorApp.ERROR_NEGOCIO);
        this.error = error;
        this.regla = regla;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRegla() {
        return regla;
    }

    public void setRegla(String regla) {
        this.regla = regla;
    }

    @Override
    public String toString() {
        return "ErrorNegocio{" +
                " cveError='" + super.getCveError() + '\'' +
                " error='" + error + '\'' +
                ", regla='" + regla + '\'' +
                '}';
    }

    public static ErrorAppNegocio crearErrorNegocio(String msj, String regla){
        return new ErrorAppNegocio(msj,regla);
    }
}
