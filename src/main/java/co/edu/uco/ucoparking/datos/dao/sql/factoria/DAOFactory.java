package co.edu.uco.ucoparking.datos.dao.sql.factoria;

import java.sql.Connection;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.factoria.sqlserver.SQLServerDAOFactory;

public abstract class DAOFactory {

	protected Connection conexion;
	private static TipoFactoriaEnum FACTORIA_ACTUAL = TipoFactoriaEnum.SQLSERVER;

	public static DAOFactory getFactory() {
		switch (FACTORIA_ACTUAL) {
		case SQLSERVER:
			return new SQLServerDAOFactory();
		default:
			// No dejar esta. Deberia ser una excepción personalizada
			throw new IllegalArgumentException("Tipo de factoría no soportada: " + FACTORIA_ACTUAL);

		}

	}

	protected abstract void abrirConexion();

	public abstract void cerrarConexion();

	public abstract void iniciarTransaccion();

	public abstract void confirmarTransaccion();

	public abstract void cancelarTransaccion();

	public abstract PaisDAO getPaisDAO();

}
