package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.casouso.pais.RegistrarNuevoPaisCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.pais.impl.RegistrarNuevoPaisCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.negocio.fachada.pais.RegistrarNuevoPaisFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class RegistrarNuevoPaisFachadaImpl implements RegistrarNuevoPaisFachada {

	private static final Logger logger = LoggerFactory.getLogger(RegistrarNuevoPaisFachadaImpl.class);

	private DAOFactory daoFactory;
	private RegistrarNuevoPaisCasoUso casoUso;

	public RegistrarNuevoPaisFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarNuevoPaisCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final PaisDTO datos) {
		try {
			logger.debug("Entre al metodo ejecutar de RegistrarNuevoPaisFachadaImpl...");
			daoFactory.iniciarTransaccion();
			var dominio = new PaisDominio.Builder()
					.id(datos.getId())
					.nombre(datos.getNombre())
					.build();
			casoUso.ejecutar(dominio);
			daoFactory.confirmarTransaccion();
			logger.debug("Sali del metodo ejecutar de RegistrarNuevoPaisFachadaImpl exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			var mensajeUsuario = "No fue posible registrar el nuevo país. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo ejecutar.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}
