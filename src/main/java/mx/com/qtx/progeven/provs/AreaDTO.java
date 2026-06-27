package mx.com.qtx.progeven.provs;

public class AreaDTO {
	private String cveArea;
	private String nombre;
	private String nomCorto;
	
	public AreaDTO(String cveArea, String nombre, String nomCorto) {
		super();
		this.cveArea = cveArea;
		this.nombre = nombre;
		this.nomCorto = nomCorto;
	}

	public AreaDTO() {
		super();
	}

	public String getCveArea() {
		return cveArea;
	}

	public void setCveArea(String cveArea) {
		this.cveArea = cveArea;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNomCorto() {
		return nomCorto;
	}

	public void setNomCorto(String nomCorto) {
		this.nomCorto = nomCorto;
	}

	@Override
	public String toString() {
		return "AreaDTO [cveArea=" + cveArea + ", nombre=" + nombre + ", nomCorto=" + nomCorto + "]";
	}

}
