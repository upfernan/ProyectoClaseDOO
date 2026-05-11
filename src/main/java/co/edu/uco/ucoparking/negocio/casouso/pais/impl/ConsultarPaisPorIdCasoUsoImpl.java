package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.ConsultarPaisPorIdCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class ConsultarPaisPorIdCasoUsoImpl implements ConsultarPaisPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarPaisPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public PaisDominio ejecutar(final UUID id) {
		validarIntegridadId(id);
		return consultarPais(id);
	}

	// 1. Validar que el ID no sea nulo
	private void validarIntegridadId(final UUID id) {
		if (UtilObjeto.esNulo(id)) {
			throw new UcoParkingExcepcion("El ID del país es obligatorio para consultar");
		}
	}

	// 2. Consultar y retornar el país
	private PaisDominio consultarPais(final UUID id) {
		PaisEntidad entidad = daoFactory.getPaisDAO().consultarPorId(id);
		if (UtilObjeto.esNulo(entidad)) {
			throw new UcoParkingExcepcion("No existe un país registrado con el ID proporcionado");
		}
		return new PaisDominio.Builder()
				.id(entidad.getId())
				.nombre(entidad.getNombre())
				.build();
	}

}
