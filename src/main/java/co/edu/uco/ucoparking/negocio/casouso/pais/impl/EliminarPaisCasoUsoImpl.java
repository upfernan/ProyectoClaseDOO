package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.negocio.casouso.pais.EliminarPaisCasoUso;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class EliminarPaisCasoUsoImpl implements EliminarPaisCasoUso {

	private DAOFactory daoFactory;
	private static final Logger logger = LoggerFactory.getLogger(EliminarPaisCasoUsoImpl.class);

	public EliminarPaisCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final UUID id) {
		try {
			logger.debug("Entre al metodo ejecutar de EliminarPaisCasoUsoImpl...");
			validarIntegridadId(id);
			validarExistaPaisConId(id);
			eliminarPais(id);
			logger.debug("Sali del metodo ejecutar de EliminarPaisCasoUsoImpl exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible eliminar el país. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo ejecutar.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

	// 1. Validar que el ID no sea nulo
	private void validarIntegridadId(final UUID id) {
		try {
			logger.debug("Entre al metodo validarIntegridadId de EliminarPaisCasoUsoImpl...");
			if (UtilObjeto.esNulo(id)) {
				throw UcoParkingExcepcion.crear("El ID del país es obligatorio para eliminar");
			}
			logger.debug("Sali del metodo validarIntegridadId de EliminarPaisCasoUsoImpl exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible validar el identificador del país. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo validarIntegridadId.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

	// 2. Validar que el país exista
	private void validarExistaPaisConId(final UUID id) {
		try {
			logger.debug("Entre al metodo validarExistaPaisConId de EliminarPaisCasoUsoImpl...");
			var resultado = daoFactory.getPaisDAO().consultarPorId(id);
			if (UtilObjeto.esNulo(resultado) || UtilObjeto.esNulo(resultado.getId())) {
				throw UcoParkingExcepcion.crear("No existe un país registrado con el ID proporcionado");
			}
			logger.debug("Sali del metodo validarExistaPaisConId de EliminarPaisCasoUsoImpl exitosamente.");
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

	// 3. Eliminar el país
	private void eliminarPais(final UUID id) {
		try {
			logger.debug("Entre al metodo eliminarPais de EliminarPaisCasoUsoImpl...");
			daoFactory.getPaisDAO().eliminar(id);
			logger.debug("Sali del metodo eliminarPais de EliminarPaisCasoUsoImpl exitosamente. ID eliminado: {}", id);
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible eliminar el país de la base de datos. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo eliminarPais.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

}
