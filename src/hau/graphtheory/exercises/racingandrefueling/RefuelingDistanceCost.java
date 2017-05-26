package hau.graphtheory.exercises.racingandrefueling;

import java.util.List;

import princeton.graphtheory.graphlib.Edge;
import princeton.graphtheory.graphlib.EdgeWeightedGraph;
import princeton.graphtheory.stdlib.StdOut;

public class RefuelingDistanceCost {

	//Minimum distance costs for every refueling
	//Contains the n(# of refueling)-lower total distances from every
	//different city to a gas station
	//
	//We use double data type although we know that weights we have 
	//as input are integers b/c the implementation we use for weighted
	//undirected graphs uses double data type for weights. 
	//private double[] distanceCost;
	private OrderedSortedMinArray<Double> distanceCost; 
	private double refuelingDistance;  
	
	public double getRefuelingDistance() {
		return refuelingDistance;
	}

	public RefuelingDistanceCost(EdgeWeightedGraph G, List<Integer> routeCities, List<Integer> gasStationsCities, int numberOfRefuelings) {
		
		//Do not forget to check if #r<G(V)
		
		//n closest distances from all routeCities to All Gas Stations 
		distanceCost = new OrderedSortedMinArray<Double>(numberOfRefuelings);
		
		//for every city of the route
		for (Integer routeCity : routeCities ) {
			
			if (routeCity==routeCities.get(0) || routeCity==routeCities.get(routeCities.size()-1)){
				continue;
			}
			//Distance from routeCity to closest GasStation
			Double minAllGasStationsDistance = Double.POSITIVE_INFINITY;
			 
			for (Integer gasStation : gasStationsCities) {
				
				//calculate shortest path distance 
				//routeCity -> gasStation			
				Double minGasStationDistance = new DijkstraUndirectedPtPSP(G, routeCity, gasStation).distTo(gasStation);
				
				System.out.println("City:" + Integer.valueOf(routeCity+1) + " gasStation:" +  Integer.valueOf(gasStation+1) + " Distance:" + minGasStationDistance);
				//if distance of shortest path is less than the 
				//minimum distance of all the other shortest paths keep it
				if ( minAllGasStationsDistance > minGasStationDistance ) {
					minAllGasStationsDistance = minGasStationDistance;
				}
				
			}
			// and insert it to the n-minimum shortest path array  
			distanceCost.insert(minAllGasStationsDistance);
		}
		//distanceCost.printAll();
		while (!distanceCost.isEmpty()) {
			refuelingDistance += distanceCost.delMin();
		}
		System.out.println("Refueling Cost:" + refuelingDistance);
	} 
}
