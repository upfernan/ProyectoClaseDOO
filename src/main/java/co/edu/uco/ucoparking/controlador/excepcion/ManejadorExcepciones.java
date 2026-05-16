package co.edu.uco.ucoparking.controlador.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.edu.uco.ucoparking.controlador.respuesta.RespuestaError;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

@RestControllerAdvice
public class ManejadorExcepciones {

	@ExceptionHandler(UcoParkingExcepcion.class)
	public ResponseEntity<RespuestaError> gestionarUcoParkingExcepcion(UcoParkingExcepcion excepcion) {
		System.err.println(excepcion.getMensajeTecnico());
		excepcion.getExcepcionRaiz().printStackTrace();

		return new ResponseEntity<>(RespuestaError.crear(excepcion.getMensajeUsuario()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RespuestaError> gestionarUcoParkingExcepcion(Exception excepcion) {
		System.err.println("Excepción no controlada...");
		excepcion.printStackTrace();

		return new ResponseEntity<>(RespuestaError.crear(
				"Ha ocurrido un error inesperado. Por favor intentelo de nuevo y si el problema persiste contacte a un administrador."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
