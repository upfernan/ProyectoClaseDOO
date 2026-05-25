package co.edu.uco.ucoparking.controlador.excepcion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import co.edu.uco.ucoparking.controlador.respuesta.RespuestaError;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

@RestControllerAdvice
public class ManejadorExcepciones {

	private static final Logger logger = LoggerFactory.getLogger(ManejadorExcepciones.class);

	@ExceptionHandler(UcoParkingExcepcion.class)
	public ResponseEntity<RespuestaError> gestionarUcoParkingExcepcion(final UcoParkingExcepcion excepcion) {
		logger.error(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
		return new ResponseEntity<>(RespuestaError.crear(excepcion.getMensajeUsuario()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
	public ResponseEntity<RespuestaError> gestionarDatosInvalidos(final Exception excepcion) {
		var mensajeUsuario = "Los datos enviados no tienen el formato correcto. Por favor verifique e intente de nuevo.";
		var mensajeTecnico = "Se recibió una solicitud con datos mal formados o tipos incorrectos: " + excepcion.getMessage();
		logger.error(mensajeTecnico, excepcion);
		return new ResponseEntity<>(RespuestaError.crear(mensajeUsuario), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RespuestaError> gestionarExcepcion(final Exception excepcion) {
		var mensajeUsuario = "Ha ocurrido un error inesperado. Por favor intente de nuevo y si el problema persiste contacte a un administrador.";
		var mensajeTecnico = "Se ha generado una excepción no controlada en el sistema. Por favor revisar la consola de errores.";
		logger.error(mensajeTecnico, excepcion);
		return new ResponseEntity<>(RespuestaError.crear(mensajeUsuario), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
