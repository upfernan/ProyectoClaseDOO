package co.edu.uco.ucoparking.controlador.respuesta;

import java.time.LocalDateTime;

import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;

public record RespuestaError(String mensaje, LocalDateTime fecha) {

	public static RespuestaError crear(String mensaje) {

		return new RespuestaError(UtilTexto.aplicarTrim(mensaje), LocalDateTime.now()); // Deberia de ser un util de fecha.

	}

}
