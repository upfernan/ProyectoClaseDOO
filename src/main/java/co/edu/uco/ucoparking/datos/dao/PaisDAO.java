package co.edu.uco.ucoparking.datos.dao;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.ConsultarPorFiltroDAO;
import co.edu.uco.ucoparking.datos.ConsultarPorIdDAO;
import co.edu.uco.ucoparking.datos.ConsultarTodosDAO;
import co.edu.uco.ucoparking.datos.CrearDAO;
import co.edu.uco.ucoparking.datos.EliminarDAO;
import co.edu.uco.ucoparking.datos.ActualizarDAO;
import co.edu.uco.ucoparking.entidad.PaisEntidad;

public interface PaisDAO extends CrearDAO<PaisEntidad>, ConsultarTodosDAO<PaisEntidad>, ConsultarPorIdDAO<PaisEntidad, UUID>, ActualizarDAO<PaisEntidad, UUID>, EliminarDAO<UUID>, ConsultarPorFiltroDAO<PaisEntidad> {

}
