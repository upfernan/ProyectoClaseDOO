package co.edu.uco.ucoparking.datos.dao.sql.factoria.sqlserver;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.datos.dao.sql.sqlserver.PaisSQLServerDAO;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class SQLServerDAOFactory extends DAOFactory {

	private static final Logger logger = LoggerFactory.getLogger(SQLServerDAOFactory.class);

	private static final String URL = "jdbc:sqlserver://localhost:1434;databaseName=ucoParkingDB;encrypt=false;trustServerCertificate=true";
	private static final String USUARIO = "sa";
	private static final String CONTRASENA = "UcoParking123!";

	public SQLServerDAOFactory() {
		abrirConexion();
	}

	@Override
	protected void abrirConexion() {
		try {
			logger.debug("Entre al metodo abrirConexion de SQLServerDAOFactory...");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
			conexion.setAutoCommit(false);
			logger.debug("Sali del metodo abrirConexion de SQLServerDAOFactory exitosamente.");
		} catch (ClassNotFoundException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible establecer la conexión con la base de datos.",
					"No se encontró el driver de SQL Server en SQLServerDAOFactory.abrirConexion.");
		} catch (SQLException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible establecer la conexión con la base de datos.",
					"Se presento una SQLException al intentar conectar en SQLServerDAOFactory.abrirConexion.");
		}
	}

	@Override
	public void cerrarConexion() {
		try {
			logger.debug("Entre al metodo cerrarConexion de SQLServerDAOFactory...");
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
			}
			logger.debug("Sali del metodo cerrarConexion de SQLServerDAOFactory exitosamente.");
		} catch (SQLException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible cerrar la conexión con la base de datos.",
					"Se presento una SQLException al cerrar la conexión en SQLServerDAOFactory.cerrarConexion.");
		}
	}

	@Override
	public void iniciarTransaccion() {
		try {
			logger.debug("Entre al metodo iniciarTransaccion de SQLServerDAOFactory...");
			conexion.setAutoCommit(false);
			logger.debug("Sali del metodo iniciarTransaccion de SQLServerDAOFactory exitosamente.");
		} catch (SQLException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible iniciar la transacción.",
					"Se presento una SQLException al iniciar la transacción en SQLServerDAOFactory.iniciarTransaccion.");
		}
	}

	@Override
	public void confirmarTransaccion() {
		try {
			logger.debug("Entre al metodo confirmarTransaccion de SQLServerDAOFactory...");
			conexion.commit();
			logger.debug("Sali del metodo confirmarTransaccion de SQLServerDAOFactory exitosamente.");
		} catch (SQLException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible confirmar la transacción.",
					"Se presento una SQLException al hacer commit en SQLServerDAOFactory.confirmarTransaccion.");
		}
	}

	@Override
	public void cancelarTransaccion() {
		try {
			logger.debug("Entre al metodo cancelarTransaccion de SQLServerDAOFactory...");
			conexion.rollback();
			logger.debug("Sali del metodo cancelarTransaccion de SQLServerDAOFactory exitosamente.");
		} catch (SQLException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible cancelar la transacción.",
					"Se presento una SQLException al hacer rollback en SQLServerDAOFactory.cancelarTransaccion.");
		}
	}

	@Override
	public PaisDAO getPaisDAO() {
		return new PaisSQLServerDAO(conexion);
	}

}
