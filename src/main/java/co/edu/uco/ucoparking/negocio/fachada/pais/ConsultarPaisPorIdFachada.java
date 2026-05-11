package co.edu.uco.ucoparking.negocio.fachada.pais;

import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.fachada.FachadaCasoUsoConRetorno;

public interface ConsultarPaisPorIdFachada extends FachadaCasoUsoConRetorno<PaisDTO, PaisDTO> {

	PaisDTO ejecutar(PaisDTO datos);

}
