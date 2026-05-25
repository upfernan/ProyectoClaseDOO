package co.edu.uco.ucoparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.SQLDAO;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class PaisSQLServerDAO extends SQLDAO implements PaisDAO {

	private static final Logger logger = LoggerFactory.getLogger(PaisSQLServerDAO.class);

	public PaisSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final PaisEntidad entidad) {
		try {
			logger.debug("Entre al metodo crear de PaisSQLServerDAO...");
			final String sql = "INSERT INTO pais (id, nombre) VALUES (?, ?)";
			try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
				ps.setString(1, entidad.getId().toString());
				ps.setString(2, entidad.getNombre());
				ps.executeUpdate();
			}
			logger.debug("Sali del metodo crear de PaisSQLServerDAO exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (SQLException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible registrar el país.",
					"Se presento una SQLException al ejecutar INSERT en la tabla pais desde PaisSQLServerDAO.crear.");
		} catch (Exception excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible registrar el país.",
					"Se presento un error no controlado en PaisSQLServerDAO.crear.");
		}
	}

	@Override
	public void actualizar(final UUID id, final PaisEntidad entidad) {
		try {
			logger.debug("Entre al metodo actualizar de PaisSQLServerDAO con ID: {}", id);
			final String sql = "UPDATE pais SET nombre = ? WHERE id = ?";
			try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
				ps.setString(1, entidad.getNombre());
				ps.setString(2, id.toString());
				ps.executeUpdate();
			}
			logger.debug("Sali del metodo actualizar de PaisSQLServerDAO exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (SQLException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible actualizar el país.",
					"Se presento una SQLException al ejecutar UPDATE en la tabla pais desde PaisSQLServerDAO.actualizar.");
		} catch (Exception excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible actualizar el país.",
					"Se presento un error no controlado en PaisSQLServerDAO.actualizar.");
		}
	}

	@Override
	public void eliminar(final UUID id) {
		try {
			logger.debug("Entre al metodo eliminar de PaisSQLServerDAO con ID: {}", id);
			final String sql = "DELETE FROM pais WHERE id = ?";
			try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
				ps.setString(1, id.toString());
				ps.executeUpdate();
			}
			logger.debug("Sali del metodo eliminar de PaisSQLServerDAO exitosamente.");
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (SQLException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible eliminar el país.",
					"Se presento una SQLException al ejecutar DELETE en la tabla pais desde PaisSQLServerDAO.eliminar.");
		} catch (Exception excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible eliminar el país.",
					"Se presento un error no controlado en PaisSQLServerDAO.eliminar.");
		}
	}

	@Override
	public List<PaisEntidad> consultarTodos() {
		try {
			logger.debug("Entre al metodo consultarTodos de PaisSQLServerDAO...");
			final String sql = "SELECT id, nombre FROM pais";
			final List<PaisEntidad> resultados = new ArrayList<>();
			try (PreparedStatement ps = getConexion().prepareStatement(sql);
					ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirPaisEntidad(rs));
				}
			}
			logger.debug("Sali del metodo consultarTodos de PaisSQLServerDAO exitosamente.");
			return resultados;
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (SQLException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible consultar los países.",
					"Se presento una SQLException al ejecutar SELECT en la tabla pais desde PaisSQLServerDAO.consultarTodos.");
		} catch (Exception excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible consultar los países.",
					"Se presento un error no controlado en PaisSQLServerDAO.consultarTodos.");
		}
	}

	@Override
	public PaisEntidad consultarPorId(final UUID id) {
		try {
			logger.debug("Entre al metodo consultarPorId de PaisSQLServerDAO con ID: {}", id);
			final String sql = "SELECT id, nombre FROM pais WHERE id = ?";
			try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
				ps.setString(1, id.toString());
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						logger.debug("Sali del metodo consultarPorId de PaisSQLServerDAO exitosamente.");
						return construirPaisEntidad(rs);
					}
				}
			}
			logger.debug("Sali del metodo consultarPorId de PaisSQLServerDAO: no se encontró registro con el ID proporcionado.");
			return new PaisEntidad.Builder().build();
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (SQLException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible consultar el país.",
					"Se presento una SQLException al ejecutar SELECT por ID en la tabla pais desde PaisSQLServerDAO.consultarPorId.");
		} catch (Exception excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible consultar el país.",
					"Se presento un error no controlado en PaisSQLServerDAO.consultarPorId.");
		}
	}

	@Override
	public List<PaisEntidad> consultarPorFiltro(final PaisEntidad filtro) {
		try {
			logger.debug("Entre al metodo consultarPorFiltro de PaisSQLServerDAO...");
			final StringBuilder sql = new StringBuilder("SELECT id, nombre FROM pais WHERE 1=1");
			final List<Object> parametros = new ArrayList<>();

			if (!UtilObjeto.esNulo(filtro)) {
				if (!UtilTexto.esNula(filtro.getNombre()) && !filtro.getNombre().isEmpty()) {
					sql.append(" AND nombre LIKE ?");
					parametros.add("%" + filtro.getNombre() + "%");
				}
				if (!UtilObjeto.esNulo(filtro.getId())) {
					sql.append(" AND id = ?");
					parametros.add(filtro.getId().toString());
				}
			}

			final List<PaisEntidad> resultados = new ArrayList<>();
			try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
				for (int i = 0; i < parametros.size(); i++) {
					ps.setObject(i + 1, parametros.get(i));
				}
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						resultados.add(construirPaisEntidad(rs));
					}
				}
			}
			logger.debug("Sali del metodo consultarPorFiltro de PaisSQLServerDAO exitosamente.");
			return resultados;
		} catch (UcoParkingExcepcion excepcion) {
			logger.debug(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
			throw excepcion;
		} catch (SQLException excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible consultar los países.",
					"Se presento una SQLException al ejecutar SELECT con filtro en la tabla pais desde PaisSQLServerDAO.consultarPorFiltro.");
		} catch (Exception excepcion) {
			throw UcoParkingExcepcion.crear(excepcion,
					"No fue posible consultar los países.",
					"Se presento un error no controlado en PaisSQLServerDAO.consultarPorFiltro.");
		}
	}

	private PaisEntidad construirPaisEntidad(final ResultSet rs) throws SQLException {
		return new PaisEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombre(rs.getString("nombre"))
				.build();
	}

}
