package co.edu.uco.ucoparking.datos.dao.sql.factoria.sqlserver;

import java.sql.DriverManager;
import java.sql.SQLException;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.datos.dao.sql.sqlserver.PaisSQLServerDAO;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class SQLServerDAOFactory extends DAOFactory {

	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ucoParkingDB;encrypt=false;trustServerCertificate=true";
	private static final String USUARIO = "sa";
	private static final String CONTRASENA = "UcoParking123!";

	public SQLServerDAOFactory() {
		abrirConexion();
	}

	@Override
	protected void abrirConexion() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
			conexion.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			throw new UcoParkingExcepcion();
		}
	}

	@Override
	public void cerrarConexion() {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
			}
		} catch (SQLException e) {
			throw new UcoParkingExcepcion();
		}
	}

	@Override
	public void iniciarTransaccion() {
		try {
			conexion.setAutoCommit(false);
		} catch (SQLException e) {
			throw new UcoParkingExcepcion();
		}
	}

	@Override
	public void confirmarTransaccion() {
		try {
			conexion.commit();
		} catch (SQLException e) {
			throw new UcoParkingExcepcion();
		}
	}

	@Override
	public void cancelarTransaccion() {
		try {
			conexion.rollback();
		} catch (SQLException e) {
			throw new UcoParkingExcepcion();
		}
	}

	@Override
	public PaisDAO getPaisDAO() {
		return new PaisSQLServerDAO(conexion);
	}

}
