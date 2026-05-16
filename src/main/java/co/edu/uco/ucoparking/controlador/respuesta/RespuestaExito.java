package co.edu.uco.ucoparking.controlador.respuesta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;

public record RespuestaExito<T>(String mensaje, LocalDateTime fecha, T datos) {

	public static <T> RespuestaExito<T> crear(String mensaje, T datos) {

		return new RespuestaExito<T>(UtilTexto.aplicarTrim(mensaje), LocalDateTime.now(), datos); // Deberia de ser un
																									// util de fecha.

	}

	public static Object crear(String string, LocalDate now, ArrayList<PaisDTO> lista) {
		// TODO Auto-generated method stub
		return null;
	}

}
