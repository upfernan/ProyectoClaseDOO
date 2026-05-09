package co.edu.uco.ucoparking.transversal.utilitario;

public final class UtilObjeto {

	private UtilObjeto() {
		super();
	}
	
	// <O> es un objeto generalizado, es decir, puede ser cualquier tipo de objeto que se le pase como argumento al método esNulo.
	public static <O> boolean esNulo(final O objeto) {
		return objeto == null;
	}
	
	public static <O> O obtenerValorDefecto(final O objeto, final O valorDefecto) {
		return esNulo(objeto) ? valorDefecto : objeto;
	}
	
}
