package co.edu.uco.ucoparking.transversal.utilitario.excepcion;

public class UcoParkingExcepcion extends RuntimeException {

	private static final long serialVersionUID = -127481128908084318L;
	
	private String mensajeUsuario;
	private String mensajeTecnico;
	private Exception excepcionRaiz;

	public UcoParkingExcepcion() {
		super();
	}

	public UcoParkingExcepcion(final String mensaje) {
		super(mensaje);
	}

	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

	public void setMensajeUsuario(String mensajeUsuario) {
		this.mensajeUsuario = mensajeUsuario;
	}

	public String getMensajeTecnico() {
		return mensajeTecnico;
	}

	public void setMensajeTecnico(String mensajeTecnico) {
		this.mensajeTecnico = mensajeTecnico;
	}

	public Exception getExcepcionRaiz() {
		return excepcionRaiz;
	}

	public void setExcepcionRaiz(Exception excepcionRaiz) {
		this.excepcionRaiz = excepcionRaiz;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
