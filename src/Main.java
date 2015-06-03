import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


public class Main {
	
	public static int[][]distances;
	
	/**
	 * Main entry point of GA
	 * 
	 * @param args
	 */
	
	public static void main (String args []){
		
		int numberOfCities;

		
		/*Obtain the number of cities from user input*/
		//Scanner scan = new Scanner(System.in);
			//numberOfCities = scan.nextInt();
			//scan.close();
		
		/* 
		 * Mock table input
		 * Randomly generate the distances between cities.
		 * Store distances in 2D array
		 */
		//distances = generateMap(numberOfCities);
		
		/*
		 * Table 1 Example data
		 * 
		 * 
		 */
		
		numberOfCities = 11;
		
		ArrayList<int[]> table = new ArrayList<int[]>();
		table.add(new int[]{0,9});
		table.add(new int[]{1,5});
		table.add(new int[]{2,7});
		table.add(new int[]{4,4});
		table.add(new int[]{5,9});
		table.add(new int[]{9,7});
		table.add(new int[]{7,5});
		table.add(new int[]{7,1});
		table.add(new int[]{3,1});
		table.add(new int[]{5,0});
		table.add(new int[]{0,0});
		
		distances = generateMapFromTable(table);
		
		
		
		/*
		 * Print 2D of distances
		 */
		String map = printDistanceMatrix(numberOfCities, distances);
		System.out.println("Distances : \n" + map);
		
		/*
		 * Pass the routine 
		 *   #cities, distances, #tours, #generations , crossover %, mutation %
		 */
		ArrayList<ArrayList<Integer>> bestTour = runGA(numberOfCities, distances,10,10,0.7,0.1);
		/*End runGA routine*/
		
		
		String[][] mapWithPaths = new String[table.size()][table.size()];

		int[] citiesAtPositions = orderTour(bestTour);
		System.out.println();
		System.out.println("Position(gene value) : index in solution");
		for(int k = 0; k < citiesAtPositions.length; k++){
			System.out.println("index " + k + " value: " + citiesAtPositions[k]);
		}
		
		//index is city number
		for(int i = 0; i < citiesAtPositions.length-1; i++){
			int indexFrom = citiesAtPositions[i];
			int indexTo = citiesAtPositions[i+1];
			
			int[] cityFrom = table.get(indexFrom);
			int[] cityTo = table.get(indexTo);
			
			int xFrom = cityFrom[0];
			int yFrom = cityFrom[1];
			int xTo = cityTo[0];
			int yTo = cityTo[1];
			
			String cityLabel = "X";
			switch(indexFrom){
				case 0: cityLabel = "A"; break;
				case 1: cityLabel = "B"; break;
				case 2: cityLabel = "C"; break;
				case 3: cityLabel = "D"; break;
				case 4: cityLabel = "E"; break;
				case 5: cityLabel = "F"; break;
				case 6: cityLabel = "G"; break;
				case 7: cityLabel = "H"; break;
				case 8: cityLabel = "I"; break;
				case 9: cityLabel = "J"; break;
				case 10: cityLabel = "K"; break;
			}			
	
			int horizontal = xFrom - xTo;
			int temp = xFrom;
			for(int h = 1; h <= Math.abs(horizontal); h++){
				if(horizontal < 0){
					mapWithPaths[xFrom+h][yFrom] = "|";
					temp = xFrom + h;
				}else{
					mapWithPaths[xFrom-h][yFrom] = "|";
					temp = xFrom - h;
				}
			}
			
			int vertical = yFrom - yTo;
			for(int h = 1; h <= Math.abs(vertical); h++){
				if(vertical < 0){
					mapWithPaths[temp][yFrom+h] = "-";
				}else{
					mapWithPaths[temp][yFrom-h] = "-";
				}
			}
			
			mapWithPaths[xFrom][yFrom] = cityLabel;
			
			System.out.println();
			printMapFromTable(mapWithPaths);			
		}
		//Print final map
		printMapFromTable(mapWithPaths);
	}//end main 
	
	public static void printMapFromTable(String[][] mapWithPaths){
		//Print mapWithPaths
				System.out.println("\n");
				for (int i = 0; i < mapWithPaths.length; i++){
					for(int j = 0; j < mapWithPaths.length; j++){
						if(mapWithPaths[i][j] == null)
							System.out.print("0 ");
						else
							System.out.print(mapWithPaths[i][j] + " ");
					}
					System.out.println();
				}
	}
	
	
	/**
	 * generateMap 
	 * 
	 * @param table List of 2d vectors
	 * @return 2d representation of cities connection and distances between connections
	 */
	
	public static int[][] generateMapFromTable(ArrayList<int[]> table){
		int n = table.size();
		//n being the number of cities
		int[][] distances = new int[n][n];
		//Iterate matrix
		for(int i = 0; i < n; i ++){
			//Loop through half the array since it is symmetric
			for( int j = i + 1; j < n; j++){
				//Populate distances between cities
				int[] fromCity = table.get(i);
				int[] toCity = table.get(j);
				distances[i][j] = getDistance(fromCity, toCity);
				//Copy values
				distances[j][i] = distances[i][j];
			}
		}
		
		return distances;
		
	}
	
	/**
	 * Compute distance between two cities
	 * @param from city (x,y)
	 * @param to city (x,y)
	 * @return distance
	 */
	
	public static int getDistance(int[] from, int[] to){
		return Math.abs(from[0] - to[0]) + Math.abs(from[1]-to[1]);
	}
	
	
	/**
	 * generateMap 
	 * 
	 * @param n number of cities
	 * @return 2d representation of cities connection and distances between connections
	 */
	
	public static int[][] generateMap(int n){
		//n being the number of cities
		int[][] distances = new int[n][n];
		//Iterate matrix
		for(int i = 0; i < n; i ++){
			//Loop through half the array since it is symmetric
			for( int j = i + 1; j < n; j++){
				//Populate distances between cities
				distances[i][j] = (int) (100 * Math.random() + 1);
				//Copy values
				distances[j][i] = distances[i][j];
			}
		}
		
		return distances;
		
	}
	
	/**
	 * Return number of bits required to represent base ten integer
	 * @param n base ten number
	 * @return length of bit string
	 */
	
	public static int geneLength(int n){
		int length = 1; 
		int total = 2;
		while(total < n){
			total *= 2;
			length += 1;
		}
		return length;
	}
	
	/**
	 * computes the base, b, expansion of an integer n.
	 * 
	 * @param n Integer.
	 * @param b base b.
	 * @return list of digits in base that represent an integer n.
	 */

	public static ArrayList<Integer> getBaseExpansionForInteger(int n, int base) {

		ArrayList<Integer> array = new ArrayList<Integer>();

		if (base > 1) {
			int q = n;
			int k = 0;
			while (q != 0) {
				array.add(k, (q % base));
				q = q / base;
				k = k + 1;
			}
		}
		Collections.reverse(array);
		return array;
	}
	
	/**
	 * getAllGenes
	 * 
	 * @param geneLength
	 * @return set of all possible genes
	 */
	
	
	public static ArrayList<ArrayList<Integer>> getAllGenes(int n, int geneLength){
		
		ArrayList<ArrayList<Integer>> genes = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < n; i++){
			ArrayList<Integer> gene = getBaseExpansionForInteger(i, 2);
			
			while(gene.size() < geneLength){
				gene.add(0, new Integer(0));
			}
			
			genes.add(gene);
		}
		return genes;
	}
	
	/**
	 * getIntegerFromGene
	 * 
	 * @param gene represented by a bit string
	 * @return integer value of gene
	 */
	public static int getIntegerFromGene(ArrayList<Integer> gene){
		int value = 0;
		
		int length = gene.size()-1;
		for(int i = 0; i < gene.size(); i++){
			value += gene.get(i) * Math.pow(2, length);
			length--;
		}
		return value;
	}
	
	/**
	 * runGA
	 * 
	 * @param n number of cities
	 * @param distances 2D array of distances between cities
	 * @param p population size
	 * @param g number of generations
	 * @param cp Crossover probability
	 * @param mp Mutation probability
	 */
	
	public static ArrayList<ArrayList<Integer>> runGA(int n, int[][] distances, int p, int g, double cp, double mp){
		//Determine gene, bit string length
		int geneLength = geneLength(n);
		//Store every unique gene given n cities in genes list
		ArrayList<ArrayList<Integer>> genes = getAllGenes(n, geneLength);
		//Print every unique gene given n cities
		System.out.print("All genes: \n" + printGenes(genes));
		//Populate the population pool with tours
		ArrayList<ArrayList<ArrayList<Integer>>> tours = generatePopulationPool(n, p, genes);
		//Print every tour in the initial pool and its fitness
		System.out.println("Initial population pool");
		printToursWithFitness(tours);
		
		
		//Determine which tour of the pool was most fit and store it
		ArrayList<ArrayList<ArrayList<Integer>>> bestTours = new ArrayList<ArrayList<ArrayList<Integer>>>();
		bestTours.add(getBestTour(tours));
		
		//For each generation create a new population pool
		// cp % chance of crossover from bestTours
		// mp % chance of mutation from bestTours
		for(int i = 0; i < g; i++){
			//Generate new population
			tours = generatePopulationPool(n, p, genes);
			
			double r = Math.random();
			//Account for chance that mutation or cross will occur
			if(r < mp){
				//mutation()
			}
			else if(r < cp){
				//crossover()
			}
			//Add best tour of pool in this generation
			bestTours.add(getBestTour(tours));
		}
		//Print every tour in the most fit pool and its fitness
		System.out.println("Most Fit Solutions after " + g + " generations.");
		printToursWithFitness(bestTours);
		
		System.out.print("Best Tour \n " + printTour(getBestTour(bestTours)));
		return getBestTour(bestTours);
	}
	
	public static ArrayList<ArrayList<Integer>> getBestTour(ArrayList<ArrayList<ArrayList<Integer>>> tours){
		ArrayList<ArrayList<Integer>> bestTour = tours.get(0);
		int max = fitness(bestTour);
		for(ArrayList<ArrayList<Integer>> tour : tours){
			if(fitness(tour) < max){
				max = fitness(tour);
				bestTour = tour;
			}
		}
		return bestTour;
	}
	
	
	/**
	 * Generate population pool of tours
	 * @param n number of cities
	 * @param p number of tours
	 * @param genes set of all genes
	 * @return set of tours
	 */
	public static ArrayList<ArrayList<ArrayList<Integer>>> generatePopulationPool(int n, int p, ArrayList<ArrayList<Integer>> genes){
		//Store each tour in a list of tours to create a population pool
		ArrayList<ArrayList<ArrayList<Integer>>> tours = new ArrayList<ArrayList<ArrayList<Integer>>>();
		//Generate p tours
		for(int i = 0 ; i < p; i++){
			//Create list of n numbers
			ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
			for(int k = 0; k < n; k++){
				randomNumbers.add(k);
			}
			//Shuffle the list
			Collections.shuffle(randomNumbers);
			//Combine genes to generate tour
			ArrayList<ArrayList<Integer>> tour = new ArrayList<ArrayList<Integer>>();
			for(int j = 0; j < n; j++){
				int randomNumber = randomNumbers.get(j);
				tour.add(genes.get(randomNumber));
			}
			//Add tour to list of tours
			tours.add(tour);
		}
		return tours;
	}
	
	/**
	 * getOrderOfCitiesInPath index to array is city and value at index is position
	 * @param tour
	 * @return order of cities in path
	 */
	public static int[] getOrderOfCitiesInPath (ArrayList<ArrayList<Integer>> tour){
		//Get an indexed list of the position in the path a city comes from the best tour
				int[] orderInPath = new int[tour.size()];
				for(int i =0; i < orderInPath.length ; i++){
					ArrayList<Integer> gene = tour.get(i);
					int positionInPath = getIntegerFromGene(gene);
					orderInPath[i] = positionInPath;
				}
				return orderInPath;
	}
	
	
	/**
	 * orderTour generate array with index is position in path and value at index is city 
	 * @param tour
	 * @return ordered tour
	 */
	public static int[] orderTour(ArrayList<ArrayList<Integer>> tour){
		int[] orderInPath = getOrderOfCitiesInPath(tour);
		
		int[] reversed = new int[orderInPath.length];
		for(int i = 0; i < tour.size(); i++){
			reversed[orderInPath[i]] = i;
		}
		return reversed;
	}
	
	/**
	 * Determine fitness of tour by determining the total distance traveled
	 * @param tour
	 * @return return distance in tour
	 */

	
	public static int fitness(ArrayList<ArrayList<Integer>> tour){
		//Access cities by order in path
		int[] tourOrder = orderTour(tour);
		int fitness = 0;
		//i corresponds to the city 
		for(int i = 0; i < tour.size()-1; i++){	
			fitness += distances[tourOrder[i]][tourOrder[i+1]];
		}
		return fitness;
	}
	
	/**
	 * Prints every possible gene given n cities and their integer value
	 * @param genes
	 */
	public static String printGenes(ArrayList<ArrayList<Integer>> genes){
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < genes.size(); i++){
			sb.append(getIntegerFromGene(genes.get(i)) + " : " + genes.get(i) + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	public static String printTour(ArrayList<ArrayList<Integer>> tour){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < tour.size(); i ++){
			sb.append(tour.get(i));
			}
		sb.append(" fitness: " + fitness(tour));
		return sb.toString();
	}
	
	/**
	 * Prints the given generations tours and their fitness
	 * @param tours
	 */
	public static void printToursWithFitness(ArrayList<ArrayList<ArrayList<Integer>>> tours){
		for(ArrayList<ArrayList<Integer>> tour : tours){
			System.out.println(printTour(tour));
			System.out.println();
		}
	}
	
	
	
	/**
	 * toString 
	 * 
	 * @param n number of cities
	 * @param d Cartesian product of set of cities with itself in which the elements of cross product are distance between cities opposed to pair of cities.
	 * @return Textual representation of 2d map
	 * 
	 */
	
	public static String printDistanceMatrix(int n, int[][] d){
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				int distance = d[i][j];
				//Format so digits line up
				if(distance < 10){
					sb.append("  ");
				}else if(distance < 100){
					sb.append(" ");
				}
				sb.append(distance + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	

}

	

//[000]

