package it.polito.tdp.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento> {
	
	public enum TipoEvento {
		CRIME,
		FREE
	}
	
	private LocalDateTime tempo;
	private TipoEvento tipo;
	private Event event;
	private Agente agente;
	
	public Evento(LocalDateTime tempo, TipoEvento tipo, Event event) {
		this.tempo = tempo;
		this.tipo = tipo;
		this.event = event;
	}
	
	public Evento(LocalDateTime tempo, TipoEvento tipo, Agente agente) {
		this.tempo = tempo;
		this.tipo = tipo;
		this.agente = agente;
	}

	public LocalDateTime getTempo() {
		return tempo;
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	public Event getEvent() {
		return event;
	}
	
	public Agente getAgente() {
		return agente;
	}
	
	@Override
	public int compareTo(Evento ev) {
		return this.tempo.compareTo(ev.tempo);
	}

}