# Traveling Sales Person

The traveling sales person is a classic optimization problem.
Given a set of cities and a set of values corresponding to each element in the cartesian product of the set of cities and itself.
Each value represents the distance between the two elements of the corresponding pair.

A genetic algorithm will be used to select several solutions from a pool of solutions, and by using a fitness function determine which solution provided the best result.
The solution itself is a list of cities, in which the order of the list corresponds to the sequence in which the cities were visited.
The fitness function determines a given solution's total distance traveled by taking the sum of the distances of each pair of successive elements of the solution's list.

The solution whose fitness function produces the smallest value is the best fit solution, or one that produces that shortest distance between n cities in which no city was traveled to more than once and every city was traveled to.


## Input

* The number of cities to visit (determines the GA chromosome length)
* Intercity distances (can be stored in a 2D array for efficient access)
* The population size (number of tours in each GA generation)
* The maximum number of GA generations
* The GA crossover probability
* The GA mutation probability

## Output

* The tour (list of cities in the order they are visited) with the shortest length 
* The length of the shortest tour
* The generation number for the best tour found


## Requirments

* Genetic Algorithm
* Java
