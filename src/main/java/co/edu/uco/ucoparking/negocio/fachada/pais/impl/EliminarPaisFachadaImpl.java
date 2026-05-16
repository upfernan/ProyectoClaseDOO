package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.casouso.pais.EliminarPaisCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.pais.impl.EliminarPaisCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.EliminarPaisFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class EliminarPaisFachadaImpl implements EliminarPaisFachada {

	private DAOFactory daoFactory;
	private EliminarPaisCasoUso casoUso;

	public EliminarPaisFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new EliminarPaisCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final PaisDTO datos) {
		try {

			daoFactory.iniciarTransaccion();

			casoUso.ejecutar(datos.getId());

			daoFactory.confirmarTransaccion();

		} catch (UcoParkingExcepcion exception) {

			daoFactory.cancelarTransaccion();
			throw exception;

		} catch (Exception exception) {

			daoFactory.cancelarTransaccion();
			throw new UcoParkingExcepcion();

		} finally {

			daoFactory.cerrarConexion();

		}
	}

	

}
