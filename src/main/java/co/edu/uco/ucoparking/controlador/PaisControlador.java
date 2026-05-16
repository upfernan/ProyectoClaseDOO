package co.edu.uco.ucoparking.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoPais(@RequestBody PaisDTO pais) {
		RegistrarNuevoPaisFachada fachada = new RegistrarNuevoPaisFachadaImpl();
		fachada.ejecutar(pais);

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionPaisExistente(@PathVariable UUID id, @RequestBody PaisDTO pais) {
		var paisConId = new PaisDTO.Builder()
				.id(id)
				.nombre(pais.getNombre())
				.build();

		ActualizarPaisFachada fachada = new ActualizarPaisFachadaImpl();
		fachada.ejecutar(paisConId);

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaPaisExistente(@PathVariable UUID id) {
		var pais = new PaisDTO.Builder()
				.id(id)
				.build();

		EliminarPaisFachada fachada = new EliminarPaisFachadaImpl();
		fachada.ejecutar(pais);

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<PaisDTO>> consultarPaisPorId(@PathVariable UUID id) {
		var pais = new PaisDTO.Builder()
				.id(id)
				.build();

		ConsultarPaisPorIdFachada fachada = new ConsultarPaisPorIdFachadaImpl();
		var resultado = fachada.ejecutar(pais);

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<PaisDTO>>> consultarPaises() {
		// Simulacion con datos de ejemplo hasta que se implemente la fachada de consultar todos
		var lista = new ArrayList<PaisDTO>();
		lista.add(new PaisDTO.Builder().id(UUID.randomUUID()).nombre("Colombia").build());
		lista.add(new PaisDTO.Builder().id(UUID.randomUUID()).nombre("Brasil").build());

		return new ResponseEntity<>(RespuestaExito.crear("Paises consultados exitosamente.", lista), HttpStatus.OK);
	}

}
