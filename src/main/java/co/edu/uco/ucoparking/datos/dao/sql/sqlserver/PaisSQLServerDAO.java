package co.edu.uco.ucoparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.SQLDAO;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class PaisSQLServerDAO extends SQLDAO implements PaisDAO {

	public PaisSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final PaisEntidad entidad) {
		final String sql = "INSERT INTO pais (id, nombre) VALUES (?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNombre());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new UcoParkingExcepcion();
		}
	}

	@Override
	public void actualizar(final UUID id, final PaisEntidad entidad) {
		final String sql = "UPDATE pais SET nombre = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombre());
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new UcoParkingExcepcion();
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM pais WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new UcoParkingExcepcion();
		}
	}

	@Override
	public List<PaisEntidad> consultarTodos() {
		final String sql = "SELECT id, nombre FROM pais";
		final List<PaisEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				resultados.add(construirPaisEntidad(rs));
			}
		} catch (SQLException e) {
			throw new UcoParkingExcepcion();
		}
		return resultados;
	}

	@Override
	public PaisEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT id, nombre FROM pais WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirPaisEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw new UcoParkingExcepcion();
		}
		return null;
	}

	@Override
	public List<PaisEntidad> consultarPorFiltro(final PaisEntidad filtro) {
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
		} catch (SQLException e) {
			throw new UcoParkingExcepcion();
		}
		return resultados;
	}

	private PaisEntidad construirPaisEntidad(final ResultSet rs) throws SQLException {
		return new PaisEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombre(rs.getString("nombre"))
				.build();
	}
}
