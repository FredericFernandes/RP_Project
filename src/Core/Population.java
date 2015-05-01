package Core;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import loader.LoaderSimulation;


public class Population {
	private int numOfCenters;
	private boolean pMedian = false;
	private ArrayList<String[]> listOfLines;
	private int initialPopSize;
	private HashMap<Integer, ArrayList<Integer>> myPop;
	private int numOfCities;
	//	double[] popFitness;

	public HashMap<Integer, ArrayList<Integer>> getMyPop() {
		return myPop;
	}

	Population(Population pOther)
	{
		this.pMedian = pOther.pMedian;
		this.initialPopSize = pOther.initialPopSize;
		this.numOfCenters = pOther.numOfCenters;
		this.listOfLines = pOther.listOfLines;
		this.myPop = new HashMap<Integer, ArrayList<Integer>>();
		this.numOfCities=pOther.numOfCities;
	}
	public Population (int numOfCities, int numOfCenters, int popSize, ArrayList<String[]> listOfLines){
		/* In this constructor, we RANDOMLY generate a population.  
		 * Each individual (a line of the population matrix) is an affectation of centers to a set of cities.
		 * The case index in the vector is the number of the city.
		 */

		this.listOfLines = listOfLines;
		this.numOfCenters = numOfCenters;
		this.initialPopSize = popSize;
		this.numOfCities=numOfCities;
		Random r = new Random ();

		myPop = new HashMap<Integer, ArrayList<Integer>>();

		for (int i=0 ; i<popSize; i++)
			myPop.put(i, new ArrayList<Integer>(numOfCities));

		if (numOfCenters == -1){
			for (int i=0;i<myPop.size();i++){
				for(int j=0;j<numOfCities;j++){
					myPop.get(i).add(r.nextInt(2));
				}
			}
		}else{
			// Here you treat the case where a certain number of center is required
			pMedian = true;
			for (int i=0;i<myPop.size();i++){
				for(int j=0;j<numOfCities;j++){
					myPop.get(i).add(0);
				}
			}
			for (int i=0;i<myPop.size();i++){
				int j=0;int var = 0; 
				//for(int j=0;j<numOfCenters;j++){

				while(j<numOfCenters){
					var = r.nextInt(numOfCities);
					if(myPop.get(i).get(var) !=1){
						myPop.get(i).set(var,1);
						j++;
					}
				}
				//}

				// If there are less than p centers, the missing centers must be added
			}

		}
	}


	public Population (){
		myPop = new HashMap<Integer, ArrayList<Integer>>();
	}

	//	public void printPop (){
	//		System.out.println("MODE p-median " + pMedian);
	//		for (int i=0;i<this.myPop.size();i++){
	//			System.out.print("[" + i + "] : [ ");
	//			//System.out.println(this.myPop[i].toString());
	//			for (int j=0;j<this.myPop.get(i).size()-1;j++)
	//				System.out.print(this.myPop.get(i).get(j) + " | ");
	//
	//			System.out.println(this.myPop.get(i).get(this.myPop.get(i).size()-1) +" ]");
	//		}
	//	}


	public Population getSolution(){

		HashMap<Integer, HashMap<Integer, ArrayList<Couple>>> mMAP =  this.affectCitiesInPop();
		Population nextIterationPop = this.arrangePop(mMAP);
		System.out.println("BEST SOLUTION FOUND = " + nextIterationPop.myPop.get(0));


		return nextIterationPop;
	}
	public ArrayList<Integer> getAffectationCenters()
	{
		HashMap<Integer, HashMap<Integer, ArrayList<Couple>>> mMAP =  this.affectCitiesInPop();
		Population nextIterationPop = this.arrangePop(mMAP);
		System.out.println("BEST SOL =  " + nextIterationPop.myPop.get(0)); 
		return (nextIterationPop.myPop.get(0));
	}

	public HashMap<Integer, ArrayList<Couple>> getOtherAffectation()
	{
		HashMap<Integer, HashMap<Integer, ArrayList<Couple>>> mMAP =  this.affectCitiesInPop();
		//Population nextIterationPop = this.arrangePop(mMAP);

		HashMap<Integer, ArrayList<Integer>> arrangedPop = new HashMap<Integer, ArrayList<Integer>>();
		Population pp = new Population(this);
		ArrayList<Double> fitnessList = this.getFitnessList(mMAP);

		ArrayList<Double> sortedFitnessList = new ArrayList<Double>(fitnessList);

		Collections.sort(sortedFitnessList);
		//System.out.println(" INITIAL Fitness List : " + fitnessList);
		//System.out.println(" SORTED Fitness List : " + sortedFitnessList);

		int[] indexes = new int[sortedFitnessList.size()];
		for (int i = 0; i < this.initialPopSize; i++){
			indexes[i] = fitnessList.indexOf(sortedFitnessList.get(i));
			arrangedPop.put(i, this.myPop.get(indexes[i]));
		}
		//System.out.println("Index list =  " + Arrays.toString(indexes));

		for (int i = 0; i < arrangedPop.size(); i++){
			pp.myPop.put(i, arrangedPop.get(i));
		}
		System.out.println("THE OTHER PART OF THE SOLUTION = " + mMAP.get(indexes[0]));
		return mMAP.get(indexes[0]);

	}


	public ArrayList<Double> getFitnessList (HashMap<Integer, HashMap<Integer, ArrayList<Couple>>> mMAP){
		double fit = 0;
		ArrayList<Double> fitnessList = new ArrayList<Double>();
		ArrayList<Double> costsList =getCostsList();
		// - delete the parameter befoire uncommenting this : HashMap<Integer, HashMap<Integer, ArrayList<Couple>>> mMAP = this.affectCitiesInPop(fl, listOfLines);
		Set mMAPKeySet = mMAP.keySet();
		for(int i=0; i<this.myPop.size(); i++){
			for(int j=0 ; j<this.myPop.get(i).size() ; j++){
				fit += myPop.get(i).get(j) * costsList.get(j);
			}
			//			System.out.println("\n======================================================================================\n");
			//			System.out.println("First part of the fitness fot " +i + " is :"+ fit);

			// First part is correctecly calculated
			Iterator it = mMAPKeySet.iterator();

			for (int j=0; j<mMAP.size(); j++){

				while(it.hasNext()){
					int indivIndex = (int) it.next();
					if(indivIndex == i){
						//System.out.println("Checking for managing costs in the individual " + indivIndex);
						Set insideKeySet = mMAP.get(indivIndex).keySet();

						Iterator it1 = insideKeySet.iterator();
						while(it1.hasNext()){
							int managingCity = (int) it1.next();
							//System.out.println(managingCity + " can manage up to " + mMAP.get(indivIndex).get(managingCity).size() + " in the individual " + indivIndex);
							for(int k=0;k<mMAP.get(indivIndex).get(managingCity).size();k++){
								//System.out.println("Individual "+ i +":  ADD " + mMAP.get(indivIndex).get(managingCity).get(k).getR());
								fit += mMAP.get(indivIndex).get(managingCity).get(k).getR();
							}	
						}
					}

				} 
			}
			fitnessList.add(fit);
			fit = 0;	
		}
		//this.printPop();
		return fitnessList;
	}


	public HashMap<Integer, HashMap<Integer, ArrayList<Couple>>> affectCitiesInPop(){


		HashMap<Integer, HashMap<Integer, ArrayList<Couple>>> mMAP = new HashMap<Integer, HashMap<Integer, ArrayList<Couple>>>(); 


		mMAP = new HashMap<Integer, HashMap<Integer, ArrayList<Couple>>>(); 
		// mMAP = management Map in All Population
		ArrayList<Integer> obligatoryCities = new ArrayList<Integer>();
		HashMap<Integer, ArrayList<Couple>> affectationMap = getAffectationMap();
		Set affectationMapKeySet = affectationMap.keySet(); // a Set of cities who can manage ; we use them to access the general affectation Map

		for(int i = 0;i<myPop.get(0).size();i++){
			if(!affectationMap.containsKey(i)){
				obligatoryCities.add(i);
			}
		}

		//System.out.println(" Affect cities in Population of size = " +myPop.size());
		//System.out.println("Obligatory Cities are : " + obligatoryCities);


		//			System.out.println("Population before affecting == it shouldn't have a  problem");
		//			this.printPop();
		if(pMedian){
			if(numOfCenters < obligatoryCities.size()){
				System.out.println("It is Impossible to solve this problem with only " + numOfCenters + "!");
			}else{
				for(int i=0;i<myPop.size();i++){ // for every indivdual in the population ; fix obligatoryCities
					for(int j=0; j<myPop.get(i).size(); j++){
						if(obligatoryCities.contains(j))
							myPop.get(i).set(j,1);
					}
				}

				for(int i=0;i<this.myPop.size();i++){ // for every indivdual in the population
					HashMap<Integer, ArrayList<Couple>> mMAPValue = new HashMap<Integer, ArrayList<Couple>>(); // the value part in mMAP 
					int nCI = 0;

					if(!mMAP.containsKey(i)){
						mMAP.put(i, mMAPValue);
					}

					for(int j=0; j<this.myPop.get(i).size(); j++){
						if(obligatoryCities.contains(j) && myPop.get(i).get(j) == 0){
							myPop.get(i).set(j, 1);
						}
						if(myPop.get(i).get(j) == 1)
							nCI++;
					}
					if(nCI>numOfCenters){ // This individual has too many centers
						for(int k=numOfCenters; k<nCI; k++){
							boolean ok = false;
							for(int j=0; j<myPop.get(i).size(); j++){ // for every city in the vector of the individual "i"
								if(!ok && myPop.get(i).get(j) == 1 && !obligatoryCities.contains(j)){
									myPop.get(i).set(j, 0);
									ok = true;
								}

							}
						}

					}
					if(nCI<numOfCenters){ // This individuals doesn't have enough centers
						for(int k=nCI; k<numOfCenters; k++){
							boolean ok = false;
							for(int j=0; j<myPop.get(i).size(); j++){ // for every city in the vector of the individual "i"
								if(!ok && myPop.get(i).get(j) == 0){
									myPop.get(i).set(j, 1);
									ok = true;
								}

							}
						}
					}


				}
			}
		}

		for(int i=0;i<this.myPop.size();i++){ // for every indivdual in the population
			HashMap<Integer, ArrayList<Couple>> mMAPValue = new HashMap<Integer, ArrayList<Couple>>(); // the value part in mMAP 
			int nCI = 0;

			for(int j=0; j<this.myPop.get(i).size(); j++){
				if(myPop.get(i).get(j) == 1)
					nCI++;
			}
			//System.out.println("======================================= " + i +" ===============================================");
			if(!mMAP.containsKey(i)){
				mMAP.put(i, mMAPValue);
			}
			double minCost = -1;
			int managingCity = -1;
			for(int j=0; j<this.myPop.get(i).size(); j++){ // for every city in the vector of the individual "i"

				if(this.myPop.get(i).get(j) == 0){ // only cities with no center
					//System.out.println("The city " + j + " doesn't have a center !");
					Iterator it = affectationMapKeySet.iterator(); // iterate on the list of cities that can manage other cities
					while(it.hasNext()){
						int cc = (int) it.next(); // the city in the affectation Map that can manage
						if(cc != j){
							if(this.myPop.get(i).get(cc) == 1){ // only cities with a center can manage another center
								//System.out.println("Checking if " + cc + " can manage " + j);
								for (int k=0; k<affectationMap.get(cc).size(); k++){ // check in the affectationMap for the city with the lowest management cost for the city "j"
									if (affectationMap.get(cc).get(k).getL() == j){
										//System.out.println(cc + " can manage " + j + " with a cost of " + affectationMap.get(cc).get(k).getR());
										if(managingCity == -1){
											minCost = affectationMap.get(cc).get(k).getR();
											managingCity = cc;
										}else{
											if(affectationMap.get(cc).get(k).getR() < minCost){
												minCost = affectationMap.get(cc).get(k).getR();
												managingCity = cc;
											}
										}
									}
								}

							}
						}
					}

					if(managingCity == -1){
						//System.out.println(j + " can't be managed by none of the existing cities ==> " + "must affect a center to it !");
						this.myPop.get(i).set(j, 1);
						nCI++;
						//System.out.println(" the city (" + j + ") has a center now === PROOF: its valut in the Pop is " + this.myPop.get(i) );
						//this.printPop();
					}else{
						//System.out.println("The minimum cost for managing " + j + " is " + minCost + " by " + managingCity);
						Couple couple = new Couple(j,minCost);
						if(!mMAP.get(i).containsKey(managingCity)){
							mMAP.get(i).put(managingCity, new ArrayList<Couple>());}
						mMAP.get(i).get(managingCity).add(couple);
						minCost = -1;
						managingCity = -1;
					}

				}
			}
			//System.out.println("The hashMap for the individual " + i + " : "+ mMAP.get(i));	
		}
		//this.printPop();	
		return mMAP;
	}
	

	public Population arrangePop( HashMap<Integer, HashMap<Integer, ArrayList<Couple>>> mMAP){

		HashMap<Integer, ArrayList<Integer>> arrangedPop = new HashMap<Integer, ArrayList<Integer>>();
		Population pp = new Population(this);

		//System.out.println("ARRANGE POP_____SIZE : " + this.myPop.size());
		ArrayList<Double> fitnessList = this.getFitnessList(mMAP);

		ArrayList<Double> sortedFitnessList = new ArrayList<Double>(fitnessList);

		Collections.sort(sortedFitnessList);
		//System.out.println(" INITIAL Fitness List : " + fitnessList);
		//System.out.println(" SORTED Fitness List : " + sortedFitnessList);

		int[] indexes = new int[sortedFitnessList.size()];
		for (int i = 0; i < this.initialPopSize; i++){
			indexes[i] = fitnessList.indexOf(sortedFitnessList.get(i));
			arrangedPop.put(i, this.myPop.get(indexes[i]));
		}
		//System.out.println("Index list =  " + Arrays.toString(indexes));

		for (int i = 0; i < arrangedPop.size(); i++){
			pp.myPop.put(i, arrangedPop.get(i));
		}
		//System.out.println("BEST SOL (individual " + indexes[0] + ") =  " + pp.myPop.get(0) + " With a cost of : " + sortedFitnessList.get(0)); 

		//System.out.println("THE OTHER PART OF THE SOLUTION = " + mMAP.get(indexes[0]));
		//pp.printPop();
		//System.out.println("After arange Pop : size = "+pp.myPop.size() + "== initialPopSize = " + this.initialPopSize);
		return pp;


	}


	public Population selectionBest (double percentage, ArrayList<Double> fitnessList){
		Population selectedPop = new Population(this);

		ArrayList<Double> sortedFitnessList = new ArrayList<Double>(fitnessList);
		Collections.sort(sortedFitnessList);
		//System.out.println("Fitness list : " + fitnessList);
		//System.out.println("Sorted fitness list : " + sortedFitnessList);
		int[] indexes = new int[(int) Math.round (sortedFitnessList.size()*percentage)];
		for (int i = 0; i < indexes.length; i++){
			indexes[i] = fitnessList.indexOf(sortedFitnessList.get(i));
			selectedPop.myPop.put(i, this.myPop.get(indexes[i]));
		}

		//System.out.println("Indexes are : " + Arrays.toString(indexes) + "\n");
		//		for (int i = 0; i < indexes.length ; i++)
		//			System.out.println(fitnessList.get(indexes[i]));
		//System.out.println("Selected Pop contains " + selectedPop.myPop.size() + " individuals from initial " + this.myPop.size());
		return selectedPop;
	}


	public Population crossOver (int crossOverIndex){
		if(crossOverIndex < 1 || (crossOverIndex > (myPop.get(0).size()-2)))
			System.out.println("Corssover Impossible ===> position out of Bound");

		int index = myPop.size();
		//System.out.println(" size before crossover " + index);
		Population offSprings = new Population(this);

		HashMap<Integer, ArrayList<Integer>> offSpringsMap = new HashMap<Integer, ArrayList<Integer>>();


		ArrayList<Integer> p1 = new ArrayList<Integer>();
		ArrayList<Integer> p2 = new ArrayList<Integer>();
		ArrayList<Integer> c1;
		ArrayList<Integer> c2;

		if(myPop.size() % 2 == 0){
			for (int i = 0; i < myPop.size()-1; i=i+2){
				c1 = new ArrayList<Integer>();
				c2 = new ArrayList<Integer>();
				p1 = myPop.get(i);
				p2 = myPop.get(i+1);				
				for (int j = 0; j<crossOverIndex; j++){
					c1.add(p1.get(j));
					c2.add(p2.get(j));
				}
				for (int j = crossOverIndex; j<p1.size(); j++){
					c2.add(p1.get(j));
					c1.add(p2.get(j));
				}
				offSpringsMap.put(index+i, c1);
				offSpringsMap.put(index+i+1, c2);
				//				System.out.println((index+i) + " "+ offSpringsMap.get(index+i));
				//				System.out.println((index+i+1) + " "+ offSpringsMap.get(index+i+1));
			}
		}else{
			for (int i = 0; i < myPop.size()-2; i++){
				c1 = new ArrayList<Integer>();
				c2 = new ArrayList<Integer>();
				p1 = myPop.get(i);
				p2 = myPop.get(i+1);
				for (int j = 0; j<crossOverIndex; j++){
					c1.add(p1.get(j));
					c2.add(p2.get(j));
				}
				for (int j = crossOverIndex; j<p1.size(); j++){
					c2.add(p1.get(j));
					c1.add(p2.get(j));
				}
				offSpringsMap.put(index, c1);
				offSpringsMap.put(index+1, c2);
			}

		}

		if(pMedian){
			for(int i=0;i<offSpringsMap.size(); i++){
				ArrayList<Integer> a = new ArrayList<Integer>();
				for(int j=0; j<offSpringsMap.get(index+i).size();j++){
					if(offSpringsMap.get(index+i).get(j) == 1)
						a.add(j);
				}

				if(a.size()>this.numOfCenters){
					// adapt the number of centers in this individual to "p"
					//System.out.println("The individual : " + (index+i) + offSpringsMap.get(index+i) + a.size() +" ==>"+ "has too many Centers");

					for(int j=numOfCenters;j<a.size();j++){
						Random r = new Random();
						int k = r.nextInt(a.size());
						while(offSpringsMap.get(index+i).get(a.get(k)) ==0){
							k = r.nextInt(a.size());
						}
						offSpringsMap.get(index+i).set(a.get(k), 0);

					}
					//System.out.println("The individual : " + (index+i) + offSpringsMap.get(index+i) + " Fixed");

				}
				//System.out.println((index+i) + " number of ones = " + a.size());
				if(a.size()<this.numOfCenters){
					// adapt the number of centers in this individual to "p"
					//System.out.println("The individual " +(index+i) + offSpringsMap.get(index+i) + a.size()  + " has less Centers");
					for(int j=a.size();j<this.numOfCenters;j++){
						Random r = new Random();
						int k = r.nextInt(offSpringsMap.get(index+i).size());
						while(offSpringsMap.get(index+i).get(k) ==1){
							k = r.nextInt(offSpringsMap.get(index+i).size());
						}
						offSpringsMap.get(index+i).set(k, 1);
					}
					//System.out.println("The individual " + (index+i) + offSpringsMap.get(index+i) + " after fixing ");
				}

			}
		}


		offSprings.myPop.putAll(myPop);
		offSprings.myPop.putAll(offSpringsMap);
		//System.out.println("OffSprings Size :  " + offSprings.myPop.size());


		return offSprings;
	}


	public Population randomMutation (double prob){
		// probability is between 0 and 1
		//System.out.println(" Mutation procces may occur on  " + this.myPop.size() + " individuals ");
		Population newPop = new Population(this);

		for (int i = 0; i < this.myPop.size(); i++){
			newPop.myPop.put(i, myPop.get(i));
			Random r = new Random();
			double pb = r.nextInt(100)/100;
			if (pb<=prob){
				r = new Random();
				//System.out.println(newPop.myPop.get(i).size());
				int mutationIndex = (int) r.nextInt(newPop.myPop.get(i).size());
				if(mutationIndex<0) 
					mutationIndex = 0;
				if(mutationIndex>=newPop.myPop.get(i).size()) 
					mutationIndex = newPop.myPop.get(i).size();
				//System.out.println("About to do a flip on the " + mutationIndex + "position of the individual number " + i + " " + newPop.myPop.get(i));
				newPop.myPop.get(i).set(mutationIndex, 1 - newPop.myPop.get(i).get(mutationIndex)); 
				//System.out.println("After Mutation : "  + newPop.myPop.get(i));
			}
		}
		if(this.pMedian){
			for(int i=0;i<newPop.myPop.size(); i++){
				ArrayList<Integer> a = new ArrayList<Integer>();
				for(int j=0; j<newPop.myPop.get(i).size();j++){
					if(newPop.myPop.get(i).get(j) == 1)
						a.add(j);
				}

				if(a.size()>this.numOfCenters){
					// adapt the number of centers in this individual to "p"
					//System.out.println("The individual : " + (i) + newPop.myPop.get(i) + a.size() +" ==>"+ "has too many Centers" + "delete" + (a.size()-numOfCenters));

					for(int j=numOfCenters;j<a.size();j++){
						Random r = new Random();
						int k = r.nextInt(a.size());
						while(newPop.myPop.get(i).get(a.get(k)) ==0){
							k = r.nextInt(a.size());
						}
						newPop.myPop.get(i).set(a.get(k), 0);

					}
					//System.out.println("The individual : " + (i) + newPop.myPop.get(i) + " Fixed");

				}
				//System.out.println((i) + " number of ones = " + a.size());
				if(a.size()<this.numOfCenters){
					// adapt the number of centers in this individual to "p"
					//System.out.println("The individual " +(i) + newPop.myPop.get(i) + a.size()  + " has less Centers");
					for(int j=a.size();j<this.numOfCenters;j++){
						Random r = new Random();
						int k = r.nextInt(newPop.myPop.get(i).size());
						while(newPop.myPop.get(i).get(k) ==1){
							k = r.nextInt(newPop.myPop.get(i).size());
						}
						newPop.myPop.get(i).set(k, 1);
					}
					//System.out.println("The individual " + (i) + newPop.myPop.get(i) + " after fixing ");
				}

			}
		}
		//System.out.println("New Pop after mutation : size = " + newPop.myPop.size());
		return newPop;
	}

	public int size()
	{
		return myPop.size();
	}


	public HashMap<Integer, ArrayList<Couple>> getAffectationMap (){
		// The couple in this case is : This city that can BE MANAGED by the city in the key argument and the cost of this management

		HashMap<Integer, ArrayList<Couple>> affectationMap = new HashMap<Integer, ArrayList<Couple>>();

		int numOfCities = Integer.valueOf(listOfLines.get(0)[0]);
		int startingIndex = numOfCities + 1;
		Couple couple;
		for(int i=startingIndex; i<listOfLines.size()-1; i++){
			//			System.out.print(myList.get(i)[0] + " ");
			//			System.out.print(myList.get(i)[1] + " ");
			//			System.out.print(myList.get(i)[2] + "\n");
			if(!affectationMap.containsKey(Integer.valueOf(listOfLines.get(i)[0])) && Integer.valueOf(listOfLines.get(i)[0]) != -1 ){
				affectationMap.put(Integer.valueOf(listOfLines.get(i)[0]), new ArrayList<Couple>());
			}
			couple = new Couple(Integer.valueOf(listOfLines.get(i)[1]), Double.valueOf(listOfLines.get(i)[2]));
			affectationMap.get(Integer.valueOf(listOfLines.get(i)[0])).add(couple);
		}


		return affectationMap;

	}
	public ArrayList<Double> getCostsList(){
		ArrayList<Double> installationCostsList = new ArrayList<Double>(); 
		for(int i=1; i<=numOfCities; i++){
			installationCostsList.add(Double.valueOf(listOfLines.get(i)[2]));
		}
		return installationCostsList;

	}
	
	public double getResult()
	{
		HashMap<Integer, HashMap<Integer, ArrayList<Couple>>> mMAP =  this.affectCitiesInPop();
		ArrayList<Double> fitnessList = this.getFitnessList(mMAP);
		ArrayList<Double> sortedFitnessList = new ArrayList<Double>(fitnessList);
		Collections.sort(sortedFitnessList);
		return  sortedFitnessList.get(0); 
		
		
	}
}

