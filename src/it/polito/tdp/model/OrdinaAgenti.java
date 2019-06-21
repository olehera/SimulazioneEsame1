package it.polito.tdp.model;

import java.util.Comparator;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class OrdinaAgenti implements Comparator<Agente> {
	
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private int distretto;
	
	public OrdinaAgenti(Graph<Integer, DefaultWeightedEdge> grafo, int distretto) {
		this.grafo = grafo;
		this.distretto = distretto;
	}

	/**
	 *  Ordina in base alla distanza crescente, quindi prima gli agenti che sono più vicini al distretto
	 */
	@Override
	public int compare(Agente a1, Agente a2) {
		if (a1.getPosto() == distretto)
			return -1;
		
		if (a2.getPosto() == distretto)
			return +1;
		
		return (int)(grafo.getEdgeWeight(grafo.getEdge(distretto, a1.getPosto())) - grafo.getEdgeWeight(grafo.getEdge(distretto, a2.getPosto())));
	}

}