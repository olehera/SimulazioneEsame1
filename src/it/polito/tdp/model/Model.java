package it.polito.tdp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;

public class Model {
	
	private List<Integer> distretti;
	private List<Integer> anni;
	private EventsDao dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private Simulatore sim;
	
	public Model() {
		dao = new EventsDao();
		anni = new ArrayList<Integer>(dao.listAllYears());
		distretti = new ArrayList<Integer>(dao.listAllDistricts());
	}
	
	public void creaGrafo(int anno) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, distretti);
		
		for (Integer partenza: distretti)
		for (Integer arrivo: distretti)
			if (!grafo.containsEdge(partenza, arrivo) && partenza != arrivo) {

				double peso = LatLngTool.distance(dao.centroDistretto(anno, partenza), dao.centroDistretto(anno, arrivo), LengthUnit.KILOMETER);
					
				Graphs.addEdge(grafo, partenza, arrivo, peso);
			}
		
	}

	public List<Integer> getDistretti() {
		return distretti;
	}

	public Graph<Integer, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List<Integer> getAnni() {
		return anni;
	}
	
	public List<Integer> getGiorni(int mese, int anno) {
		return dao.listOfDays(mese, anno);
	}
	
	public List<Integer> trovaAdiacenti(int distretto) {
		
		List<Integer> distretti = Graphs.neighborListOf(grafo, distretto);
		
		Collections.sort(distretti, new OrdinaDistretti(grafo, distretto));
		
		return distretti;
	}
	
	public int simula(int n, int anno, LocalDate data) {
		
		sim = new Simulatore();
		sim.init(n, dao.centralePolizia(anno), dao.listEvents(data), grafo);
		sim.run();
		
		return sim.getEventiMalGestiti();
	}
	
}