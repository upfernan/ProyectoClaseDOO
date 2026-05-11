package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;


import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.RegistrarNuevoPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public class RegistrarNuevoPaisCasoUsoImpl implements RegistrarNuevoPaisCasoUso {
	
	private DAOFactory daoFactory;
	
	public RegistrarNuevoPaisCasoUsoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	public void ejecutar(PaisDominio datos) {
		validarIntegridadDatosPais(datos);
		validarNoExistaOtroPaisConMismoNombre(datos.getNombre());
		guardarNuevoPais(datos);
		
	}
	//1. Validacion de datos consistentes: tipo de dato, longitud, formato, obligatoriedad y rango.
	private void validarIntegridadDatosPais(final PaisDominio pais) {
		if (UtilObjeto.esNulo(pais)) {
			throw new UcoParkingExcepcion("Los datos del país son obligatorios");
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
//2. No debe esxistir otro pais con el mismo nombre 
	private void validarNoExistaOtroPaisConMismoNombre(final String nombre) {
		var paisEntidadFiltro = new PaisEntidad.Builder().nombre(nombre).build();
		var resultados = daoFactory.getPaisDAO().consultarPorFiltro(paisEntidadFiltro);
		
		if(UtilObjeto.esNulo(resultados) || resultados.isEmpty()) {
			return; // No existe otro pais con el mismo nombre, se puede continuar
		}
		throw new UcoParkingExcepcion("Ya existe un país registrado con el nombre: " + nombre);
	}
	//3. No debe existir otro pais con el mismo ID 
	private UUID generarIdUnicoNuevoPais() {
		// Aqui la logica para garantizar que se geenre un id que no existe 
		return UUID.randomUUID();
	}
	
//4. Guardar informacion del neuvo pais 
	private void guardarNuevoPais(PaisDominio pais) {
		// logica para guardar el nuevo pais

		var idNuevoPais = generarIdUnicoNuevoPais();

		PaisEntidad paisEntidad = new PaisEntidad.Builder()
				.id(idNuevoPais)
				.nombre(pais.getNombre())
				.build();
		daoFactory.getPaisDAO().crear(paisEntidad);

	}
}
