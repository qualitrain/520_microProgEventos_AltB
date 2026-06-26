package mx.com.qtx.progeven.provs;

public class TematicaDTO {
	private String nomCorto;
	private String nombre;
//	private int numPersona;
	private int idTematica;
	
	public TematicaDTO() {
		super();
	}
	
	public String getNomCorto() {
		return nomCorto;
	}
	public void setNomCorto(String nomCorto) {
		this.nomCorto = nomCorto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
//	public int getNumPersona() {
//		return numPersona;
//	}
//	public void setNumPersona(int numPersona) {
//		this.numPersona = numPersona;
//	}
	public int getIdTematica() {
		return idTematica;
	}
	public void setIdTematica(int idTematica) {
		this.idTematica = idTematica;
	}

	@Override
	public String toString() {
		return "TematicaDTO [nomCorto=" + nomCorto + ", nombre=" + nombre 
//				+ ", numPersona=" + numPersona
				+ ", idTematica=" + idTematica + "]";
	}

}
