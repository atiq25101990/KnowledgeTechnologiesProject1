/*
 * COMP90049 Knowledge Technologies Project1- Urban Dictionary 
 * Mohammed Atiq Shaikh
 */

package com.comp90049.project1;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * The task of this class to correct the misspell word (misspell arraylist)
 * from dictionary arraylist using Levenshtein Method 
 * 
 * */

public class LDMatchMethod {
	
	//This main task of this method is to normalize misspell input string to calculate
	//levenshtein distance and print output
	public void performOperation(ArrayList<String> misspellList, ArrayList<String> correctList,
			ArrayList<String> dictionaryList) {
		long startTime = System.nanoTime();
		
		// TODO Auto-generated method stub
		ArrayList<String> predictedList = new ArrayList<String>();
		ArrayList<String> correctedList = new ArrayList<String>();
		ArrayList<Integer> predictedCountList = new ArrayList<Integer>();
		Random random = new Random();
		int min=0;
		long totalPredictedCount = 0;
		System.out.println("Misspell Size ="+ misspellList.size());
		System.out.println("Correct Size ="+ correctList.size());
		System.out.println("Dictionary Size ="+ dictionaryList.size());
		
		//Iterating misspell list
		for(int i=0;i<misspellList.size();i++){
			//Iterating dictionary list
			for(int j=0;j<dictionaryList.size();j++){
				
				//Dictionary word should be greater than or equal 
				//to one less size of misspell word
				if(dictionaryList.get(j).trim().length() >= misspellList.get(i).trim().length()-1){
					
					//Calculate minimum distance using levenshtein distance method
					min = levenshteinDist( misspellList.get(i) , dictionaryList.get(j) , 
						misspellList.get(i).length(), dictionaryList.get(j).length());
				}
				
				//Min distance is in range from 0 to 7 then add dictionary word
				//in predicted list
				if(min>=0 && min<=7){
					predictedList.add(dictionaryList.get(j));
				}

			}

			//Check size of predicted list
			if(predictedList.size()>0){
				
					boolean flag = false;	
					//Iterate predicted list
					for(int k=0;k<predictedList.size();k++){
						
						//Checking correct word in the predicted list
						if(correctList.get(i).equals(predictedList.get(k))){
							System.out.println("Match found in the "+predictedList.size()+""
									+ " predicted tokens of predicted list");
							System.out.println("The string "+misspellList.get(i)+" "
									+ "is corrected to "+predictedList.get(k)+" from predicted list");
							correctedList.add(predictedList.get(k));
							flag=true; //If correct word found then flag is true
						}
					}	
					
					//Correct word found then store the predicted list size for that word
					//or else choose random word from predicted list
					if(flag){
						//Taking note of predicted list size for a particular correct word
						predictedCountList.add(predictedList.size());
					}
					else{
						//choosing random word from predicted list since correct word not found
					    int randomIndex = random.nextInt(predictedList.size());				        
					    String randomSelected = predictedList.get(randomIndex);
					    System.out.println("Match not found in the predicted list");
					    System.out.println("The string "+misspellList.get(i)+" is"
					        		+ " randomly corrected with "+randomSelected);
					    correctedList.add(randomSelected);
					    predictedCountList.add(0);
						flag=false;
					}

			}
			else{
				//No predicted word found even in the dictionary
				System.out.println((i+1) + "No correction found for misspelled word : "
									+misspellList.get(i));
				predictedCountList.add(0);
				correctedList.add(misspellList.get(i));
			}
			predictedList.clear();
		}
		
        System.out.println("Misspelled ---- Correct ---- Predicted Count ---- Corrected");
		int correct=0, incorrect=0;
		//Evaluating correct or incorrect count
		for (int i = 0; i < misspellList.size(); i++) {
	        int result = correctList.get(i).compareTo(correctedList.get(i));
	        if(result==0)
	        	correct++;
	        else
	        	incorrect++;
	        totalPredictedCount = totalPredictedCount + predictedCountList.get(i);
	        System.out.println(misspellList.get(i)+" "+correctList.get(i)+" "
	        		+ "" +predictedCountList.get(i) +" "+correctedList.get(i));
		}

		System.out.println("Out of "+misspellList.size()+", "+correct+" are correct "
				+ "and "+incorrect+" incorrect");
		System.out.println("Total Predicted Count = " +totalPredictedCount);
		
		long endTime   = System.nanoTime();
		double totalTime = (double)(endTime - startTime)/1000000000.0;
		totalTime = totalTime/60.0;
		System.out.println("Total time for execution of Levenshtein Method in mins = "+totalTime);
		
	}

	//Levenshtein distance method taking two string and their length
	private int levenshteinDist(String string1, String string2, int length1, int length2) {
		// TODO Auto-generated method stub
        
        int [] costs = new int [string2.length() + 1];
        //Initializing the cost with iterating value
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= string1.length(); i++) {
        	
            costs[0] = i;//diagonal cost 
            int nw = i - 1;
            for (int j = 1; j <= string2.length(); j++) {
            	//calculating min of insertion cost, deletion cost and replacement cost
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                		string1.charAt(i - 1) == string2.charAt(j - 1) ? nw : nw + 1);
                //swapping with the lowest cost
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[string2.length()];
	}

}
