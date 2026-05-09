package co.edu.uco.ucoparking.datos.dao.sql.factoria.sqlserver;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.datos.dao.sql.sqlserver.PaisSQLServerDAO;

public class SQLServerDAOFactory extends DAOFactory {

	public SQLServerDAOFactory() {
		abrirConexion();
	}

	@Override
	protected void abrirConexion() {
		conexion = null; // Aquí se asigna la conexión obtenida de SQLServer

	}

	@Override
	public void cerrarConexion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void iniciarTransaccion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void confirmarTransaccion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelarTransaccion() {
		// TODO Auto-generated method stub

	}

	@Override
	public PaisDAO getPaisDAO() {
		return new PaisSQLServerDAO(conexion);
	}

}
