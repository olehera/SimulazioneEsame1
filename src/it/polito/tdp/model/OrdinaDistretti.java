package it.polito.tdp.model;

import java.util.Comparator;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class OrdinaDistretti implements Comparator<Integer> {

	private Graph<Integer, DefaultWeightedEdge> grafo;
	private int distretto;
	
	public OrdinaDistretti(Graph<Integer, DefaultWeightedEdge> grafo, int distretto) {
		this.grafo = grafo;
		this.distretto = distretto;
	}

	@Override
	public int compare(Integer d1, Integer d2) {
		return (int)(grafo.getEdgeWeight(grafo.getEdge(distretto, d1)) - grafo.getEdgeWeight(grafo.getEdge(distretto, d2)));
	}

}
