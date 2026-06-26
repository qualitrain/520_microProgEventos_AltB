package mx.com.qtx.progeven.core;

public class NegocioException extends RuntimeException {
    private String regla;

    public NegocioException(String message) {
        super(message);
    }

    public String getRegla() {
        return regla;
    }

    public void setRegla(String regla) {
        this.regla = regla;
    }

    public static NegocioException crearExceptionPersonaRequerida(int numPersona){
        NegocioException nex = new NegocioException("persona no existe [" + numPersona + "]");
        nex.setRegla("El servicio requiere que se especifique una persona que exista previamente.");
        return nex;
    }
}
