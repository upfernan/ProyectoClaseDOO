package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.casouso.pais.RegistrarNuevoPaisCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.pais.impl.RegistrarNuevoPaisCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.negocio.fachada.pais.RegistrarNuevoPaisFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class RegistrarNuevoPaisFachadaImpl implements RegistrarNuevoPaisFachada {

	private DAOFactory daoFactory;
	private RegistrarNuevoPaisCasoUso casoUso;

	public RegistrarNuevoPaisFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarNuevoPaisCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(PaisDTO datos) {
		try {

			daoFactory.iniciarTransaccion();

			PaisDominio dominio = null; // Mapper
			casoUso.ejecutar(dominio);

			daoFactory.confirmarTransaccion();

		} catch (UcoParkingExcepcion exception) {

			daoFactory.cancelarTransaccion();
			throw exception;

		} catch (Exception exception) {

			daoFactory.cancelarTransaccion();
			// Cuidado: No se puede botar la excepcion raiz ( root exception )
			throw new UcoParkingExcepcion();
		} finally {

			daoFactory.cerrarConexion();

		}
	}

	public static void main(String[] args) {

		try {
			var pais = new PaisDTO.Builder().nombre("Colombia").build();

			RegistrarNuevoPaisFachada fachada = new RegistrarNuevoPaisFachadaImpl();

			fachada.ejecutar(pais);
			
			System.out.println("Soy un mago. Todo funcionó.");
		} catch (Exception e) {
			System.err.println("No funcionó. A revisar!!!!!");
			e.printStackTrace();
		}

	}

}


