package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.casouso.pais.EliminarPaisCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.pais.impl.EliminarPaisCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.EliminarPaisFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class EliminarPaisFachadaImpl implements EliminarPaisFachada {

	private static final Logger logger = LoggerFactory.getLogger(EliminarPaisFachadaImpl.class);

	private DAOFactory daoFactory;
	private EliminarPaisCasoUso casoUso;

	public EliminarPaisFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new EliminarPaisCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final PaisDTO datos) {
		try {
			logger.debug("Entre al metodo ejecutar de EliminarPaisFachadaImpl...");
			daoFactory.iniciarTransaccion();
			casoUso.ejecutar(datos.getId());
			daoFactory.confirmarTransaccion();
			logger.debug("Sali del metodo ejecutar de EliminarPaisFachadaImpl exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			var mensajeUsuario = "No fue posible eliminar el país. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo ejecutar.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}
