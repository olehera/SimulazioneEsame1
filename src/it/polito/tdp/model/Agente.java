package it.polito.tdp.model;

public class Agente {
	
	private int id;
	private int posto;
	private boolean libero;

	public Agente(int id, int posto) {
		this.id = id;
		this.posto = posto;
		this.libero = true;
	}

	public int getId() {
		return id;
	}
	
	public int getPosto() {
		return posto;
	}
	
	public void setPosto(int posto) {
		this.posto = posto;
	}

	public boolean isLibero() {
		return libero;
	}

	public void setLibero(boolean libero) {
		this.libero = libero;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agente other = (Agente) obj;
		if (id != other.id)
			return false;
		return true;
	}

}