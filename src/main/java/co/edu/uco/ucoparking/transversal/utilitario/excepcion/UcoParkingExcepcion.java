package co.edu.uco.ucoparking.transversal.utilitario.excepcion;

import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;

public final class UcoParkingExcepcion extends RuntimeException {

	private static final long serialVersionUID = -127481128908084318L;

	private final Throwable excepcionRaiz;
	private final String mensajeUsuario;
	private final String mensajeTecnico;

	private UcoParkingExcepcion(final Throwable excepcionRaiz, final String mensajeUsuario, final String mensajeTecnico) {
		super(mensajeTecnico);
		this.excepcionRaiz = (excepcionRaiz != null) ? excepcionRaiz : new Exception();
		this.mensajeUsuario = UtilTexto.aplicarTrim(mensajeUsuario);
		this.mensajeTecnico = UtilTexto.aplicarTrim(mensajeTecnico);
	}

	public static UcoParkingExcepcion crear(final String mensajeUsuario) {
		return new UcoParkingExcepcion(new Exception(), mensajeUsuario, mensajeUsuario);
	}

	public static UcoParkingExcepcion crear(final String mensajeUsuario, final String mensajeTecnico) {
		return new UcoParkingExcepcion(new Exception(), mensajeUsuario, mensajeTecnico);
	}

	public static UcoParkingExcepcion crear(final Throwable excepcionRaiz, final String mensajeUsuario, final String mensajeTecnico) {
		return new UcoParkingExcepcion(excepcionRaiz, mensajeUsuario, mensajeTecnico);
	}

	public Throwable getExcepcionRaiz() {
		return excepcionRaiz;
	}

	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

	public String getMensajeTecnico() {
		return mensajeTecnico;
	}

}
