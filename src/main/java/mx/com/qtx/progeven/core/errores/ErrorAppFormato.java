package mx.com.qtx.progeven.core.errores;


public class ErrorAppFormato extends ErrorApp {

	private String mensaje;
	private String mensajeOriginal;

	public static ErrorAppFormato crearErrorFormatoURI(String mensaje, String mensajeOriginal) {
		ErrorAppFormato error = new ErrorAppFormato(ErrorApp.ERROR_FORMATO);
		error.mensaje = mensaje;
		error.mensajeOriginal = mensajeOriginal;
		return error;
	}

	public ErrorAppFormato(String cveError, String mensaje, String mensajeOriginal) {
		super(cveError);
		this.mensaje = mensaje;
		this.mensajeOriginal = mensajeOriginal;
	}

	public ErrorAppFormato(String cveError) {
		super(cveError);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensajeOriginal() {
		return mensajeOriginal;
	}

	public void setMensajeOriginal(String mensajeOriginal) {
		this.mensajeOriginal = mensajeOriginal;
	}

	@Override
	public String toString() {
		return "ErrorFormato [cveError=" + super.getCveError() + ", mensaje=" + mensaje + ", mensajeOriginal=" + mensajeOriginal
				+ "]";
	}
}
