package it.polito.tdp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.model.Evento.TipoEvento;

public class Simulatore {
	
	private PriorityQueue<Evento> queue;
	
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private Random rand;
	
	private List<Agente> agenti;
	
	private int eventiMalGestiti;

	public void init(int numAgenti, int centrale, List<Event> eventi, Graph<Integer, DefaultWeightedEdge> grafo) {
		
		queue = new PriorityQueue<>();
		rand = new Random();
		
		eventiMalGestiti = 0;
		this.grafo = grafo;
		
		agenti = new ArrayList<>();
		for (int i=1; i<=numAgenti; i++)
			agenti.add(new Agente(i, centrale));
		
		this.queue.clear();
				
		for (Event e: eventi) 
			queue.add(new Evento(e.getReported_date(), TipoEvento.CRIME, e));
		
	}

	public void run() {
		
		while ( !queue.isEmpty() ) {     
			Evento ev = queue.poll();
			
			switch (ev.getTipo()) {
			
			case CRIME:
				
				Agente a = scegliAgente(ev.getEvent().getDistrict_id());
				
				if (a != null ) {
					double tempoImpiegato = distanza(a.getPosto(), ev.getEvent().getDistrict_id()) / 60;   // velocità 60 km/h
					
					if (tempoImpiegato > 0.25)     // 15 min 
						eventiMalGestiti++;
					
					a.setLibero(false);
					a.setPosto(ev.getEvent().getDistrict_id());
						
					if (ev.getEvent().getOffense_category_id().equals("all-other-crimes"))
						tempoImpiegato += (rand.nextInt(2)+1);
					else 
						tempoImpiegato += 2;
					
					queue.add(new Evento(ev.getTempo().plusMinutes((long)(tempoImpiegato*60)), TipoEvento.FREE, a));
				} else {
					eventiMalGestiti++;
				}
				
				break;
				
			case FREE:
				ev.getAgente().setLibero(true);
				
				break;
			}
		}
		
	}
	
	public Agente scegliAgente(int distretto) {
		List<Agente> agentiLiberi = new ArrayList<Agente>();
		
		for (Agente a: agenti) 
			if (a.isLibero())
				agentiLiberi.add(a);
		
		Collections.sort(agentiLiberi, new OrdinaAgenti(grafo, distretto));
		
		if (agentiLiberi.size() > 0)
			return agentiLiberi.get(0);
		else 
			return null;
	}
	
	public double distanza(int d1, int d2) {
		if (d1 != d2)
			return grafo.getEdgeWeight(grafo.getEdge(d1, d2));
		else
			return 0;
	}

	public int getEventiMalGestiti() {
		return eventiMalGestiti;
	}

}