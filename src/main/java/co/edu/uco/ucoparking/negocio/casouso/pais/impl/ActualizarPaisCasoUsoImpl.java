package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.ActualizarPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class ActualizarPaisCasoUsoImpl implements ActualizarPaisCasoUso {

	private DAOFactory daoFactory;

	public ActualizarPaisCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final PaisDominio datos) {
		validarIntegridadDatosPais(datos);
		validarExistaPaisConId(datos);
		validarNoExistaOtroPaisConMismoNombre(datos);
		actualizarPais(datos);
	}

	// 1. Validar integridad de datos
	private void validarIntegridadDatosPais(final PaisDominio pais) {
		if (UtilObjeto.esNulo(pais)) {
			throw new UcoParkingExcepcion("Los datos del país son obligatorios");
		}
		if (UtilObjeto.esNulo(pais.getId())) {
			throw new UcoParkingExcepcion("El ID del país es obligatorio para actualizar");
		}
		if (UtilTexto.esNula(pais.getNombre()) || pais.getNombre().trim().isEmpty()) {
			throw new UcoParkingExcepcion("El nombre del país es obligatorio");
		}
		if (pais.getNombre().trim().length() < 3 || pais.getNombre().trim().length() > 100) {
			throw new UcoParkingExcepcion("El nombre del país debe tener entre 3 y 100 caracteres");
		}
		if (!pais.getNombre().matches("[\\p{L} ]+")) {
			throw new UcoParkingExcepcion("El nombre del país solo puede contener letras y espacios");
		}
	}

	// 2. Validar que el país a actualizar exista
	private void validarExistaPaisConId(final PaisDominio pais) {
		var resultado = daoFactory.getPaisDAO().consultarPorId(pais.getId());
		if (UtilObjeto.esNulo(resultado)) {
			throw new UcoParkingExcepcion("No existe un país registrado con el ID proporcionado");
		}
	}

	// 3. Validar que no exista otro país con el mismo nombre
	private void validarNoExistaOtroPaisConMismoNombre(final PaisDominio pais) {
		var filtro = new PaisEntidad.Builder().nombre(pais.getNombre()).build();
		var resultados = daoFactory.getPaisDAO().consultarPorFiltro(filtro);
		if (!UtilObjeto.esNulo(resultados) && !resultados.isEmpty()) {
			boolean esMismoPais = resultados.stream()
					.allMatch(p -> p.getId().equals(pais.getId()));
			if (!esMismoPais) {
				throw new UcoParkingExcepcion("Ya existe otro país registrado con el nombre: " + pais.getNombre());
			}
		}
	}

	// 4. Actualizar el país
	private void actualizarPais(final PaisDominio pais) {
		var paisEntidad = new PaisEntidad.Builder()
				.id(pais.getId())
				.nombre(pais.getNombre())
				.build();
		daoFactory.getPaisDAO().actualizar(pais.getId(), paisEntidad);
	}

}
