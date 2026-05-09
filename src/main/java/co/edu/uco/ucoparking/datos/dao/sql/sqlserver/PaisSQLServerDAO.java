package co.edu.uco.ucoparking.datos.dao.sql.sqlserver;

import java.util.List;
import java.util.UUID;
import java.sql.Connection;
import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.datos.dao.sql.SQLDAO;

public class PaisSQLServerDAO extends SQLDAO implements PaisDAO {

	public PaisSQLServerDAO(Connection conexion) {
		super(conexion);
	}
	@Override
	public void crear(PaisEntidad entidad) {
		// Implementar lógica de inserción en la base de datos
	}

	

	@Override
	public List<PaisEntidad> consultarTodos() {
		// Implementar consulta y mapeo a PaisEntidad
		return null;
	}

	@Override
	public PaisEntidad consultarPorId(UUID id) {
		// Implementar consulta por id
		return null;
	}

	@Override
	public void actualizar(UUID id, PaisEntidad entidad) {
		// Implementar actualización
	}

	@Override
	public void eliminar(UUID id) {
		// Implementar eliminación por id
	}

	@Override
	public List<PaisEntidad> consultarPorFiltro(PaisEntidad filtro) {
		// Implementar consulta por filtro
		return null;
	}

		
	}

