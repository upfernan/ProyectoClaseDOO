package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.RegistrarNuevoPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class RegistrarNuevoPaisCasoUsoImpl implements RegistrarNuevoPaisCasoUso {

	private static final Logger logger = LoggerFactory.getLogger(RegistrarNuevoPaisCasoUsoImpl.class);
	private DAOFactory daoFactory;

	public RegistrarNuevoPaisCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final PaisDominio datos) {
		try {
			logger.debug("Entre al metodo ejecutar de RegistrarNuevoPaisCasoUsoImpl...");
			validarIntegridadDatosPais(datos);
			validarNoExistaOtroPaisConMismoNombre(datos.getNombre());
			guardarNuevoPais(datos);
			logger.debug("Sali del metodo ejecutar de RegistrarNuevoPaisCasoUsoImpl exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible registrar el nuevo país. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo ejecutar.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

	// 1. Validar integridad de datos
	private void validarIntegridadDatosPais(final PaisDominio pais) {
		try {
			logger.debug("Entre al metodo validarIntegridadDatosPais de RegistrarNuevoPaisCasoUsoImpl...");
			if (UtilObjeto.esNulo(pais)) {
				throw UcoParkingExcepcion.crear("Los datos del país son obligatorios");
			}
			if (UtilTexto.esNula(pais.getNombre()) || pais.getNombre().trim().isEmpty()) {
				throw UcoParkingExcepcion.crear("El nombre del país es obligatorio");
			}
			if (pais.getNombre().trim().length() < 3 || pais.getNombre().trim().length() > 100) {
				throw UcoParkingExcepcion.crear("El nombre del país debe tener entre 3 y 100 caracteres");
			}
			if (!pais.getNombre().matches("[\\p{L} ]+")) {
				throw UcoParkingExcepcion.crear("El nombre del país solo puede contener letras y espacios");
			}
			logger.debug("Sali del metodo validarIntegridadDatosPais de RegistrarNuevoPaisCasoUsoImpl exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible validar los datos del país. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo validarIntegridadDatosPais.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

	// 2. Validar que no exista otro país con el mismo nombre
	private void validarNoExistaOtroPaisConMismoNombre(final String nombre) {
		try {
			logger.debug("Entre al metodo validarNoExistaOtroPaisConMismoNombre de RegistrarNuevoPaisCasoUsoImpl...");
			var paisEntidadFiltro = new PaisEntidad.Builder().nombre(nombre).build();
			var resultados = daoFactory.getPaisDAO().consultarPorFiltro(paisEntidadFiltro);
			if (!UtilObjeto.esNulo(resultados) && !resultados.isEmpty()) {
				throw UcoParkingExcepcion.crear("Ya existe un país registrado con el nombre: " + nombre);
			}
			logger.debug("Sali del metodo validarNoExistaOtroPaisConMismoNombre de RegistrarNuevoPaisCasoUsoImpl exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible validar si el nombre del país ya existe. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo validarNoExistaOtroPaisConMismoNombre.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

	// 3. Generar ID único para el nuevo país
	private UUID generarIdUnicoNuevoPais() {
		try {
			logger.debug("Entre al metodo generarIdUnicoNuevoPais de RegistrarNuevoPaisCasoUsoImpl...");
			UUID nuevoId;
			boolean existe;
			do {
				nuevoId = UUID.randomUUID();
				var filtro = new PaisEntidad.Builder().id(nuevoId).build();
				var resultados = daoFactory.getPaisDAO().consultarPorFiltro(filtro);
				existe = resultados != null && !resultados.isEmpty();
			} while (existe);
			logger.debug("Sali del metodo generarIdUnicoNuevoPais de RegistrarNuevoPaisCasoUsoImpl exitosamente.");
			return nuevoId;
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible generar el identificador único del país. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo generarIdUnicoNuevoPais.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

	// 4. Guardar el nuevo país
	private void guardarNuevoPais(final PaisDominio pais) {
		try {
			logger.debug("Entre al metodo guardarNuevoPais de RegistrarNuevoPaisCasoUsoImpl...");
			var idNuevoPais = generarIdUnicoNuevoPais();
			var paisEntidad = new PaisEntidad.Builder()
					.id(idNuevoPais)
					.nombre(pais.getNombre())
					.build();
			daoFactory.getPaisDAO().crear(paisEntidad);
			logger.debug("Sali del metodo guardarNuevoPais de RegistrarNuevoPaisCasoUsoImpl exitosamente. ID generado: {}", idNuevoPais);
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible guardar el nuevo país. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo guardarNuevoPais.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

}
