package co.edu.uco.ucoparking.negocio.fachada.pais;

import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.fachada.FachadaSinRetorno;

public interface RegistrarNuevoPaisFachada extends FachadaSinRetorno<PaisDTO> {

	void ejecutar(PaisDTO datos);

}
