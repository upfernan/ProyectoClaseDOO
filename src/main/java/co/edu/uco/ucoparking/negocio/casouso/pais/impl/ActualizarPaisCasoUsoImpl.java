package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.ActualizarPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class ActualizarPaisCasoUsoImpl implements ActualizarPaisCasoUso {

	private DAOFactory daoFactory;
	private static final Logger logger = LoggerFactory.getLogger(ActualizarPaisCasoUsoImpl.class);

	public ActualizarPaisCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final PaisDominio datos) {
		try {
			logger.debug("Entre al metodo ejecutar de ActualizarPaisCasoUsoImpl...");
			validarIntegridadDatosPais(datos);
			validarExistaPaisConId(datos);
			validarNoExistaOtroPaisConMismoNombre(datos);
			actualizarPais(datos);
			logger.debug("Sali del metodo ejecutar de ActualizarPaisCasoUsoImpl exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible actualizar el país. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo ejecutar.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

	// 1. Validar integridad de datos
	private void validarIntegridadDatosPais(final PaisDominio pais) {
		try {
			logger.debug("Entre al metodo validarIntegridadDatosPais de ActualizarPaisCasoUsoImpl...");
			if (UtilObjeto.esNulo(pais)) {
				throw UcoParkingExcepcion.crear("Los datos del país son obligatorios");
			}
			if (UtilObjeto.esNulo(pais.getId())) {
				throw UcoParkingExcepcion.crear("El ID del país es obligatorio para actualizar");
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
			logger.debug("Sali del metodo validarIntegridadDatosPais de ActualizarPaisCasoUsoImpl exitosamente.");
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

	// 2. Validar que el país a actualizar exista
	private void validarExistaPaisConId(final PaisDominio pais) {
		try {
			logger.debug("Entre al metodo validarExistaPaisConId de ActualizarPaisCasoUsoImpl...");
			var resultado = daoFactory.getPaisDAO().consultarPorId(pais.getId());
			if (UtilObjeto.esNulo(resultado) || UtilObjeto.esNulo(resultado.getId())) {
				throw UcoParkingExcepcion.crear("No existe un país registrado con el ID proporcionado");
			}
			logger.debug("Sali del metodo validarExistaPaisConId de ActualizarPaisCasoUsoImpl exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible verificar si el país existe. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo validarExistaPaisConId.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

	// 3. Validar que no exista otro país con el mismo nombre
	private void validarNoExistaOtroPaisConMismoNombre(final PaisDominio pais) {
		try {
			logger.debug("Entre al metodo validarNoExistaOtroPaisConMismoNombre de ActualizarPaisCasoUsoImpl...");
			var filtro = new PaisEntidad.Builder().nombre(pais.getNombre()).build();
			var resultados = daoFactory.getPaisDAO().consultarPorFiltro(filtro);
			if (!UtilObjeto.esNulo(resultados) && !resultados.isEmpty()) {
				boolean esMismoPais = resultados.stream().allMatch(p -> p.getId().equals(pais.getId()));
				if (!esMismoPais) {
					throw UcoParkingExcepcion.crear("Ya existe otro país registrado con el nombre: " + pais.getNombre());
				}
			}
			logger.debug("Sali del metodo validarNoExistaOtroPaisConMismoNombre de ActualizarPaisCasoUsoImpl exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible verificar si el nombre del país ya existe. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo validarNoExistaOtroPaisConMismoNombre.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

	// 4. Actualizar el país
	private void actualizarPais(final PaisDominio pais) {
		try {
			logger.debug("Entre al metodo actualizarPais de ActualizarPaisCasoUsoImpl...");
			var paisEntidad = new PaisEntidad.Builder()
					.id(pais.getId())
					.nombre(pais.getNombre())
					.build();
			daoFactory.getPaisDAO().actualizar(pais.getId(), paisEntidad);
			logger.debug("Sali del metodo actualizarPais de ActualizarPaisCasoUsoImpl exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible actualizar el país en la base de datos. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo actualizarPais.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

}
