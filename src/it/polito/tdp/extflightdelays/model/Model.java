package it.polito.tdp.extflightdelays.model;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.mysql.jdbc.BestResponseTimeBalanceStrategy;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	ExtFlightDelaysDAO dao;
	List<Airport> vertex;
	HashMap<Integer, Airport> idMap;
	SimpleWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	ArrayList<Airport> best;
	List<Adiacenza> adiacenze;
	
	public Model() {
		
		idMap = new HashMap<Integer, Airport>();
		dao = new ExtFlightDelaysDAO();
		
	}

	public void creaGrafo(int num) {
		// TODO Auto-generated method stub
		idMap = new HashMap<Integer, Airport>();
		grafo = new SimpleWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		dao.loadAllAirports(num,idMap);
		vertex = new ArrayList<Airport>(dao.getAirports(num,idMap));
		adiacenze = new ArrayList<Adiacenza>(dao.getArco(idMap));
		
		Graphs.addAllVertices(grafo, vertex);
		
		for(Adiacenza a : adiacenze) {
			
			if(grafo.getEdge(idMap.get(a.getA1()), idMap.get(a.getA2())) == null)
				Graphs.addEdge(grafo, idMap.get(a.getA1()), idMap.get(a.getA2()), a.getPeso());
			else {
				
				DefaultWeightedEdge edge = grafo.getEdge(idMap.get(a.getA1()), idMap.get(a.getA2()));
				double peso = grafo.getEdgeWeight(edge) + a.getPeso();
				grafo.setEdgeWeight(edge, peso);
			}
			
		}
		
		System.out.println("#vertici : "+grafo.vertexSet().size());
		System.out.println("#archi : "+grafo.edgeSet().size());
	}

	public List<Airport> getVertex() {
		// TODO Auto-generated method stub
		return this.vertex;
	}

	public List<String> getConnessi(Airport airport) {
		// TODO Auto-generated method stub
		List<String> result = new ArrayList<String>();
		List<DefaultWeightedEdge> edges = new ArrayList<DefaultWeightedEdge>();
		Airport now = null;
		
		for(Airport airport2 : Graphs.neighborListOf(grafo, airport)) {
			
			edges.add(grafo.getEdge(airport, airport2));
			
		}
		
		Collections.sort(edges, new Comparator<DefaultWeightedEdge>() {

			@Override
			public int compare(DefaultWeightedEdge o1, DefaultWeightedEdge o2) {
				// TODO Auto-generated method stub
				return -(int)(grafo.getEdgeWeight(o1) - grafo.getEdgeWeight(o2));
			}
		});
		
		for(DefaultWeightedEdge edge : edges) {
			
			if(grafo.getEdgeTarget(edge).equals(airport)) {
				now =  grafo.getEdgeSource(edge);
			} else now = grafo.getEdgeTarget(edge);
			
			result.add(now.toString()+" con peso: "+grafo.getEdgeWeight(edge));
				
		}
		
		return result;
	}

	public List<Airport> getSequenza(Airport partenza, Airport destinazione, int tratte) {
		// TODO Auto-generated method stub
		best = new ArrayList<Airport>();
		List<Airport> parziale = new ArrayList<Airport>();
		
		parziale.add(partenza);
		best.add(partenza);
		
		cerca(parziale,tratte,0,destinazione);
		
		if(!best.contains(destinazione))
				best.add(destinazione);
		return best;
	}

	private void cerca(List<Airport> parziale, int tratte, int L, Airport destinazione) {
		// TODO Auto-generated method stub

		if(conta(parziale) > conta(best)){
				
			best = new ArrayList<Airport>(parziale);
			System.out.println("NEW BEST");
			return;
				
		}
		
		if(L==(tratte - 1))
			return;
		
		Airport last = parziale.get(parziale.size()-1);
		
		List<Airport> neighbors = Graphs.neighborListOf(grafo, last);

		for(Airport airport : neighbors) {
				
			if(!parziale.contains(airport)) {
				System.out.println("AGGIUNGO");
				parziale.add(airport);
				System.out.println("CERCO");
				cerca(parziale, tratte, L+1, destinazione);
				System.out.println("RIMUOVO");
				parziale.remove(parziale.size()-1);
			} 
		}
	}

	public int conta(List<Airport> parziale) {
		// TODO Auto-generated method stub
		int tot = 0;
		
		for(int i = 0; i < parziale.size()-1; i++) {
			
			DefaultWeightedEdge edge = grafo.getEdge(parziale.get(i),parziale.get(i+1));
			
			if(edge != null)
				tot += grafo.getEdgeWeight(edge);
		}
			
		return tot;
	}

}
