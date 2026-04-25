package co.edu.uco.ucoparking.negocio.assembler.dto.impl;

import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.UtilObjeto;

public final class PaisDTOAssembler implements DTOAssembler<PaisDominio, PaisDTO> {

	// Singleton
	private static DTOAssembler<PaisDominio, PaisDTO> INSTANCE;
	
	private PaisDTOAssembler() {
		super();
	}
	
	public synchronized static final DTOAssembler<PaisDominio, PaisDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new PaisDTOAssembler();
		}
		
		return INSTANCE;
	}
	
	// PaisDTO a PaisDominio
	@Override
	public PaisDominio ensamblarDominio(final PaisDTO dto) {
		var paisAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new PaisDTO.Builder().build());
		return new PaisDominio.Builder()
				.id(paisAEnsamblar.getId())
				.nombre(paisAEnsamblar.getNombre())
				.build();
	}

	// PaisDominio a PaisDTO
	@Override
	public PaisDTO ensamblarDTO(final PaisDominio dominio) {
		var paisAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PaisDominio.Builder().build());
		return new PaisDTO.Builder()
				.id(paisAEnsamblar.getId())
				.nombre(paisAEnsamblar.getNombre())
				.build();
	}
	
	public static void main(String[] args) {
		var miPaisDTO = new PaisDTO.Builder()
				.id(null)
				.build();
		
		var miPaisDominio = PaisDTOAssembler.getInstance().ensamblarDominio(miPaisDTO);
	}
	
}
