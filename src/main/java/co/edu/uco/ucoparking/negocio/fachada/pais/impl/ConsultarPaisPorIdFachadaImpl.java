package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.casouso.pais.ConsultarPaisPorIdCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.pais.impl.ConsultarPaisPorIdCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.ConsultarPaisPorIdFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class ConsultarPaisPorIdFachadaImpl implements ConsultarPaisPorIdFachada {

	private static final Logger logger = LoggerFactory.getLogger(ConsultarPaisPorIdFachadaImpl.class);

	private DAOFactory daoFactory;
	private ConsultarPaisPorIdCasoUso casoUso;

	public ConsultarPaisPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarPaisPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public PaisDTO ejecutar(final PaisDTO datos) {
		try {
			logger.debug("Entre al metodo ejecutar de ConsultarPaisPorIdFachadaImpl...");
			var dominio = casoUso.ejecutar(datos.getId());
			var resultado = new PaisDTO.Builder()
					.id(dominio.getId())
					.nombre(dominio.getNombre())
					.build();
			logger.debug("Sali del metodo ejecutar de ConsultarPaisPorIdFachadaImpl exitosamente.");
			return resultado;
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = "No fue posible consultar el país. Por favor intente de nuevo.";
			var mensajeTecnico = "Se presento un flujo no controlado dentro de la clase " + this.getClass().getName() + " en el metodo ejecutar.";
			logger.debug(mensajeTecnico, excepcion);
			throw UcoParkingExcepcion.crear(excepcion, mensajeUsuario, mensajeTecnico);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}
