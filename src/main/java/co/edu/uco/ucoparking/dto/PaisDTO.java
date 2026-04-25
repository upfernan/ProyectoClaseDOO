package co.edu.uco.ucoparking.dto;

import java.util.UUID;

import co.edu.uco.ucoparking.transversal.UtilTexto;

public class PaisDTO {
	
	private UUID id;
	private String nombre;
	
	private PaisDTO(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	private void setId(final UUID id) {
		this.id = id;
	}
	
	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.aplicarTrim(nombre);
	}

	public static class Builder {
		private UUID id;
		private String nombre;
		
		public Builder id(final UUID id) {
			this.id = id;
			return this;
		}
		
		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}
		
		public PaisDTO build() {
			return new PaisDTO(this);
		}
	}	
}