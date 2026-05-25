package co.edu.uco.ucoparking.controlador.respuesta;

import java.time.LocalDateTime;

import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;

public record RespuestaExito<T>(String mensaje, LocalDateTime fecha, T datos) {

	public static <T> RespuestaExito<T> crear(String mensaje, T datos) {
		return new RespuestaExito<T>(UtilTexto.aplicarTrim(mensaje), LocalDateTime.now(), datos);
	}

}
