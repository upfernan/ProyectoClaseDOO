package co.edu.uco.ucoparking.transversal.utilitario.excepcion;

public class UcoParkingExcepcion extends RuntimeException {

	private static final long serialVersionUID = -127481128908084318L;

	public UcoParkingExcepcion() {
		super();
	}

	public UcoParkingExcepcion(final String mensaje) {
		super(mensaje);
	}

}
