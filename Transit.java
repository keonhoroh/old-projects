package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Nathan Roh
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {
		trainZero = new TNode(0, null, new TNode(0, null, new TNode(0, null, null)));
		TNode pointer = trainZero.getDown().getDown();

		for (int i = 0; i < locations.length; i++) {
			pointer.setNext(new TNode(locations[i], null, null));
			pointer = pointer.getNext();
		}

		pointer = trainZero.getDown();
		for (int i = 0; i < busStops.length; i++) {
			pointer.setNext(new TNode(busStops[i], null, getNode("walk", busStops[i])));
			pointer = pointer.getNext();
		}

		pointer = trainZero;
		for (int i = 0; i < trainStations.length; i++) {
			pointer.setNext(new TNode(trainStations[i], null, getNode("bus", trainStations[i])));
			pointer = pointer.getNext();
		}
	}

	private TNode getNode (String layer, int location) {
		if (trainZero.getDown().getDown().getDown() == null) {
			TNode pointer = trainZero;
			switch (layer) {
				case "train":
					break;
				case "bus":
					pointer = trainZero.getDown();
					break;
				case "walk":
					pointer = trainZero.getDown().getDown();
					break;
			}
			while (pointer != null) {
				if (pointer.getLocation() == location) {
					return pointer;
				} else {
					pointer = pointer.getNext();
				}
			}
			return null;
		} 
		else {
			TNode pointer = trainZero;
			switch (layer) {
				case "train":
					break;
				case "bus":
					pointer = trainZero.getDown();
					break;
				case "scooter":
					pointer = trainZero.getDown().getDown();
					break;
				case "walk":
					pointer = trainZero.getDown().getDown().getDown();
					break;
			}
			while (pointer != null) {
				if (pointer.getLocation() == location) {
					return pointer;
				} 
				else {
					pointer = pointer.getNext();
				}
			}
			return null;
		}
	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
		if (getNode("train", station) != null) {
			TNode pointer = trainZero;
			while (pointer.getNext() != null) {
				if (pointer.getNext().getLocation() == station) {
					pointer.setNext(pointer.getNext().getNext());
					break;
				} 
				else {
					pointer = pointer.getNext();
				}
			}
		}
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	    if (getNode("walk", busStop) != null && getNode("bus", busStop) == null) {
			TNode pointer = trainZero.getDown();
			while (pointer.getNext() != null && pointer.getLocation() <= busStop) {
				if (pointer.getNext().getLocation() > busStop) {
					TNode newNode = new TNode(busStop, pointer.getNext(), getNode((trainZero.getDown().getDown().getDown() == null)? "walk" : "scooter", busStop));
					pointer.setNext(newNode);
					break;
				} 
				else {
					pointer = pointer.getNext();
				}
			}
		}
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
		ArrayList<TNode> path =  new ArrayList<TNode>();

		TNode pointer = trainZero;
		path.add(pointer);

		do {
			//iterate through nodes in the layer until the the next node is past the destination
			while (pointer.getNext() != null && pointer.getNext().getLocation() <= destination) {
				pointer = pointer.getNext();
				path.add(pointer);
			}
			
			pointer = pointer.getDown(); //move down one layer
			if (pointer != null) {
				path.add(pointer);
			}
		} while (pointer != null);
		
		/*
		//iterate through train stations until the the next station is past the destination
		while (pointer.getNext() != null && pointer.getNext().getLocation() <= destination) {
			pointer = pointer.getNext();
			path.add(pointer);
		}

		pointer = pointer.getDown(); //get off the train and get on the bus
		path.add(pointer);
		//iterate through bus stops until the next stop is past the destination
		while (pointer.getNext() != null && pointer.getNext().getLocation() <= destination) {
			pointer = pointer.getNext();
			path.add(pointer);
		}

		pointer = pointer.getDown(); //get off the bus and walk
		path.add(pointer);
		//iterate through walking locations until the current station is the destination
		while (pointer.getNext() != null && pointer.getNext().getLocation() <= destination) {
			pointer = pointer.getNext();
			path.add(pointer);
		}
		*/

	    return path;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		
		//train layer
		TNode newTrainZero = new TNode(0, null, new TNode(0, null, new TNode(0, null, null)));
		TNode pointer = trainZero;
		TNode newPointer = newTrainZero;
		while (pointer.getNext() != null) {
			pointer = pointer.getNext();
			newPointer.setNext(new TNode(pointer.getLocation()));
			newPointer = newPointer.getNext();
		}
		//bus layer
		pointer = trainZero.getDown();
		newPointer = newTrainZero.getDown();
		while (pointer.getNext() != null) {
			pointer = pointer.getNext();
			newPointer.setNext(new TNode(pointer.getLocation()));
			newPointer = newPointer.getNext();
		}
		//walking layer
		pointer = trainZero.getDown().getDown();
		newPointer = newTrainZero.getDown().getDown();
		while (pointer.getNext() != null) {
			pointer = pointer.getNext();
			newPointer.setNext(new TNode(pointer.getLocation()));
			newPointer = newPointer.getNext();
		}

		//setting down pointers for train level
		TNode trainPointer = newTrainZero;
		TNode busPointer = newTrainZero.getDown();
		
		while (trainPointer != null) {
			while (busPointer.getLocation() < trainPointer.getLocation()) {
				busPointer = busPointer.getNext();
			}
			trainPointer.setDown(busPointer);
			trainPointer = trainPointer.getNext();
		}

		//setting down pointers for bus level
		busPointer = newTrainZero.getDown();
		TNode walkPointer = newTrainZero.getDown().getDown();

		while (busPointer != null) {
			while (walkPointer.getLocation() < busPointer.getLocation()) {
				walkPointer = walkPointer.getNext();
			}
			busPointer.setDown(walkPointer);
			busPointer = busPointer.getNext();
		}

	    return newTrainZero;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {
		TNode scooterZero = new TNode();
		scooterZero.setDown(trainZero.getDown().getDown());
		this.trainZero.getDown().setDown(scooterZero);
		TNode pointer = scooterZero;
		// Populate scooter layer and create downward links to walking layer
		for (int i : scooterStops) {
			pointer.setNext(new TNode(i));
			pointer = pointer.getNext();
			pointer.setDown(getNode("walk", pointer.getLocation()));
		}

		// create downward links from bus layer to scooter layer
		pointer = trainZero.getDown();
		while (pointer != null) {
			pointer.setDown(getNode("scooter", pointer.getLocation()));
			pointer = pointer.getNext();
		}
	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
