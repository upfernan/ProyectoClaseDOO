package co.edu.uco.ucoparking.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.LoggerFactory;

import co.edu.uco.ucoparking.controlador.respuesta.RespuestaExito;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.fachada.pais.ActualizarPaisFachada;
import co.edu.uco.ucoparking.negocio.fachada.pais.ConsultarPaisPorIdFachada;
import co.edu.uco.ucoparking.negocio.fachada.pais.EliminarPaisFachada;
import co.edu.uco.ucoparking.negocio.fachada.pais.RegistrarNuevoPaisFachada;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.ActualizarPaisFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.ConsultarPaisPorIdFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.EliminarPaisFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.RegistrarNuevoPaisFachadaImpl;

@RestController
@RequestMapping("/api/v1/paises")
public class PaisControlador {
	
	private static final Logger logger = LoggerFactory.getLogger(PaisControlador.class);

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoPais(@RequestBody PaisDTO pais) {
		logger.info("Iniciando registro de nuevo país: {}", pais.getNombre());
		
		RegistrarNuevoPaisFachada fachada = new RegistrarNuevoPaisFachadaImpl();
		fachada.ejecutar(pais);
		logger.info("Registro de nuevo país exitoso: {}", pais.getNombre());

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionPaisExistente(@PathVariable UUID id, @RequestBody PaisDTO pais) {
		logger.info("Iniciando modificacion de pais: {}", pais.getNombre());
		var paisConId = new PaisDTO.Builder()
				.id(id)
				.nombre(pais.getNombre())
				.build();

		ActualizarPaisFachada fachada = new ActualizarPaisFachadaImpl();
		fachada.ejecutar(paisConId);
		logger.info("Modificacion de pais exitosa: {}", pais.getNombre());

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaPaisExistente(@PathVariable UUID id) {
		logger.info("Iniciando eliminación de país con ID: {}", id);
		var pais = new PaisDTO.Builder()
				.id(id)
				.build();

		EliminarPaisFachada fachada = new EliminarPaisFachadaImpl();
		fachada.ejecutar(pais);
		logger.info("Eliminación de país exitosa con ID: {}", id);

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<PaisDTO>> consultarPaisPorId(@PathVariable UUID id) {
		logger.info("Iniciando consulta de país con ID: {}", id);
		var pais = new PaisDTO.Builder()
				.id(id)
				.build();

		ConsultarPaisPorIdFachada fachada = new ConsultarPaisPorIdFachadaImpl();
		var resultado = fachada.ejecutar(pais);
		logger.info("Consulta de país exitosa con ID: {}", id);

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}


}
