package com.vz.jigarthanda.hackathon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import com.vz.jigarthanda.hackathon.util.Helper;

public class FeedbackAnalysis {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{
			
			FileReader feedbackReader = new FileReader("C:/hackathon/feedback.txt");
			FileWriter writer =new FileWriter("C:/hackathon/result.txt");
			BufferedReader feedbackReaderBr =new BufferedReader(feedbackReader);
			BufferedWriter feedbackWriterBr =new BufferedWriter(writer);
			String feedback="";
			Helper helper = new Helper();
			Double weight =0.0;
			String result="";
			String result1="";
		    while ((feedback = feedbackReaderBr.readLine()) != null) {	
		    	if(feedback.trim().length()>0){
		    		System.out.println("========="+feedback.trim());
			    	weight =helper.getSentimentDetails(feedback.trim());
			        //System.out.println(weight);
			    	result="";
			    	result1="";
			    	
			    	if(weight<=-0.30){
			    		result="Negative";
			    	}else if( weight>-0.40 && weight< 0.50){
			    		result="Neutral";
			    	}else{
			    		result="Positive";
			    	}
			    	
	/*		    	if(weight<=-0.05){
			    		result1="NEGATIVE";
			    	}else if( weight>-0.05 && weight< 0.05){
			    		result1="NEUTRAL";
			    	}else{
			    		result1="POSITIVE";
			    	}
			    	*/
			    	Double x=weight/Math.sqrt((weight*weight)+15);
			    	//result+="["+weight+"] ["+result1+"]-->"+x;
			        feedbackWriterBr.write(result);
			        feedbackWriterBr.newLine();		  
			        feedbackWriterBr.flush();
			        //normScore = score/math.sqrt( ((score*score) + alpha) )
			        
			        System.out.println("=====END====");
		    	}
		    }
		    
		    feedbackReaderBr.close();
		    feedbackWriterBr.close();

		}catch(Exception e){
			System.out.println(e);
		}

	}

}
