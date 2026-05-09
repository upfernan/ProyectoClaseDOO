package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;


import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.RegistrarNuevoPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;

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
	//1. Validacion de datos consistentes: Tip ode dato, longitud, formato, obligatoriedad y rango.
	private void validarIntegridadDatosPais(PaisDominio pais) {
		// validar tipo de dato, longitud, formato, obligatoriedad y rango
		// si no se cumple lanzar una excepcion customizada 
		// datos invalidos excepcion
	}
//2. No debe esxistir otro pais con el mismo nombre 
	private void validarNoExistaOtroPaisConMismoNombre(String nombre) {
		var paisEntidadFiltro = new PaisEntidad.Builder().nombre(nombre).build();
		var resultados = daoFactory.getPaisDAO().consultarPorFiltro(paisEntidadFiltro);
		
		if(UtilObjeto.esNulo(resultados) && resultados.isEmpty()) {
			return; // No existe otro pais con el mismo nombre, se puede continuar
		}
		// si no se cumple lanzar una excepcion customizada 
		//existe pais con mismo nombreexcepcion
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

		PaisEntidad paisEntidad = null;
		daoFactory.getPaisDAO().crear(paisEntidad);

	}
}
