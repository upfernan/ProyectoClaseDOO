package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.casouso.pais.ConsultarPaisPorIdCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.pais.impl.ConsultarPaisPorIdCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.negocio.fachada.pais.ConsultarPaisPorIdFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class ConsultarPaisPorIdFachadaImpl implements ConsultarPaisPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarPaisPorIdCasoUso casoUso;

	public ConsultarPaisPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarPaisPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public PaisDTO ejecutar(final PaisDTO datos) {
		try {

			PaisDominio dominio = casoUso.ejecutar(datos.getId());

			return new PaisDTO.Builder()
					.id(dominio.getId())
					.nombre(dominio.getNombre())
					.build();

		} catch (UcoParkingExcepcion exception) {

			throw exception;

		} catch (Exception exception) {

			throw new UcoParkingExcepcion();

		} finally {

			daoFactory.cerrarConexion();

		}
	}


	}

