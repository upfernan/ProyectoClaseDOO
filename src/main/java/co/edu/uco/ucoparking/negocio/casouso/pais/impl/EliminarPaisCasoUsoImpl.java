package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.negocio.casouso.pais.EliminarPaisCasoUso;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class EliminarPaisCasoUsoImpl implements EliminarPaisCasoUso {

	private DAOFactory daoFactory;

	public EliminarPaisCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final UUID id) {
		validarIntegridadId(id);
		validarExistaPaisConId(id);
		eliminarPais(id);
	}

	// 1. Validar que el ID no sea nulo
	private void validarIntegridadId(final UUID id) {
		if (UtilObjeto.esNulo(id)) {
			throw new UcoParkingExcepcion("El ID del país es obligatorio para eliminar");
		}
	}

	// 2. Validar que el país exista
	private void validarExistaPaisConId(final UUID id) {
		var resultado = daoFactory.getPaisDAO().consultarPorId(id);
		if (UtilObjeto.esNulo(resultado)) {
			throw new UcoParkingExcepcion("No existe un país registrado con el ID proporcionado");
		}
	}

	// 3. Eliminar el país
	private void eliminarPais(final UUID id) {
		daoFactory.getPaisDAO().eliminar(id);
	}

}
