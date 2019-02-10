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
 * from dictionary arraylist using Edit Distance - Wagner–Fischer Algorithm
 * 
 * */

public class WFMatchMethod {
	
	//This main task of this method is to normalize misspell input string to calculate
	//edit distance using Wagner Fischer algorithm and print output
	public void performOperation(ArrayList<String> misspellList, ArrayList<String> correctList,
			ArrayList<String> dictionaryList) {
		// TODO Auto-generated method stub
		
		long startTime = System.nanoTime();
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
					
					//Calculate minimum distance using Wagner-Fischer method
					min = wfGeneratedMatrix( misspellList.get(i) , dictionaryList.get(j) , 
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
				correctedList.add(misspellList.get(i));
				predictedCountList.add(0);
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
		System.out.println("Total time for execution of WF Method in mins= "+totalTime);
	
	}
	
	//Calculate min of three
	int min(int x,int y,int z)
	{
		if (x <= y && x <= z) return x;
		if (y <= x && y <= z) return y;
		else return z;
	}
	
	//WF Matrix generated using two string and their length 
	int wfGeneratedMatrix(String str1, String str2, int m, int n)
	{
		//Create a 2D table of type integer
		int cost[][] = new int[m+1][n+1];
	
		//Iterate d[][] in bottomup manner
		for (int i=0; i<=m; i++){
			for (int j=0; j<=n; j++){
				//1st string is empty then insert all characters of second string
				if (i==0){
					cost[i][j] = j; 
				}
				//2nd string is empty then remove all characters of second string
				else if (j==0){
					cost[i][j] = i; 
				}
				//Last character are same, ignore last char & recursively
				//check for remaining string
				else if (str1.charAt(i-1) == str2.charAt(j-1)){
					cost[i][j] = cost[i-1][j-1];
				}
				//Last character are different, consider all possibilities and find minimum
				else{
					cost[i][j] = 1 + min(cost[i][j-1], // Insert
									cost[i-1][j], // Remove
									cost[i-1][j-1]); // Replace
				}
			}
		}
		return cost[m][n];
	}

}
