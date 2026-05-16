package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.casouso.pais.ActualizarPaisCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.pais.impl.ActualizarPaisCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.negocio.fachada.pais.ActualizarPaisFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class ActualizarPaisFachadaImpl implements ActualizarPaisFachada {

	private DAOFactory daoFactory;
	private ActualizarPaisCasoUso casoUso;

	public ActualizarPaisFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarPaisCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final PaisDTO datos) {
		try {

			daoFactory.iniciarTransaccion();

			PaisDominio dominio = new PaisDominio.Builder()
					.id(datos.getId())
					.nombre(datos.getNombre())
					.build();
			casoUso.ejecutar(dominio);

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

