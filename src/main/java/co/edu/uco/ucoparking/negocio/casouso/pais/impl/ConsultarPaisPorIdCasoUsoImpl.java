package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.negocio.casouso.pais.ConsultarPaisPorIdCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class ConsultarPaisPorIdCasoUsoImpl implements ConsultarPaisPorIdCasoUso {

	private static final Logger logger = LoggerFactory.getLogger(ConsultarPaisPorIdCasoUsoImpl.class);
	private DAOFactory daoFactory;

	public ConsultarPaisPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public PaisDominio ejecutar(final UUID id) {
		try {
			logger.debug("Entre al metodo ejecutar de ConsultarPaisPorIdCasoUsoImpl...");
			validarIntegridadId(id);
			var resultado = consultarPais(id);
			logger.debug("Sali del metodo ejecutar de ConsultarPaisPorIdCasoUsoImpl exitosamente.");
			return resultado;
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible consultar el país. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo ejecutar.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

	// 1. Validar que el ID no sea nulo
	private void validarIntegridadId(final UUID id) {
		try {
			logger.debug("Entre al metodo validarIntegridadId de ConsultarPaisPorIdCasoUsoImpl...");
			if (UtilObjeto.esNulo(id)) {
				throw UcoParkingExcepcion.crear("El ID del país es obligatorio para consultar");
			}
			logger.debug("Sali del metodo validarIntegridadId de ConsultarPaisPorIdCasoUsoImpl exitosamente.");
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

	// 2. Consultar y retornar el país
	private PaisDominio consultarPais(final UUID id) {
		try {
			logger.debug("Entre al metodo consultarPais de ConsultarPaisPorIdCasoUsoImpl...");
			var entidad = daoFactory.getPaisDAO().consultarPorId(id);
			if (UtilObjeto.esNulo(entidad) || UtilObjeto.esNulo(entidad.getId())) {
				throw UcoParkingExcepcion.crear("No existe un país registrado con el ID proporcionado");
			}
			logger.debug("Sali del metodo consultarPais de ConsultarPaisPorIdCasoUsoImpl exitosamente.");
			return new PaisDominio.Builder()
					.id(entidad.getId())
					.nombre(entidad.getNombre())
					.build();
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible consultar el país en la base de datos. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo consultarPais.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
	}

}
