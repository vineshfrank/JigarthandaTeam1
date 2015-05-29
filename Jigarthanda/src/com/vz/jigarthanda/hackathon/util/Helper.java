package com.vz.jigarthanda.hackathon.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vz.jigarthanda.hackathon.object.SentimentsSocre;

public class Helper {

	private static List NEGATIVE = null;
	private static List PUNCH_LIST = null;
	private static LinkedHashMap sentiments = null;
	private static final double INCR = 0.733;
	private static LinkedHashMap previous = null;
	//private static LinkedHashMap positivePhrase = null;

	private static final double SALAR_INCR = 0.293;
	private static final double SALAR_DECR = -0.293;

	public static void initialize() throws IOException {
		NEGATIVE = Arrays.asList("aint", "arent", "cannot", "cant", "couldnt",
				"darent", "didnt", "doesnt", "ain't", "aren't", "can't",
				"couldn't", "daren't", "didn't", "doesn't", "dont", "hadnt",
				"hasnt", "havent", "isnt", "mightnt", "mustnt", "neither",
				"don't", "hadn't", "hasn't", "haven't", "isn't", "mightn't",
				"mustn't", "neednt", "needn't", "never", "none", "nope", "nor",
				"not", "nothing", "nowhere", "oughtnt", "shant", "shouldnt",
				"uhuh", "wasnt", "werent", "oughtn't", "shan't", "shouldn't",
				"uh-uh", "wasn't", "weren't", "without", "wont", "wouldnt",
				"won't", "wouldn't", "rarely", "seldom", "despite");
		PUNCH_LIST = Arrays.asList(".", "!", "?", ",", ";", ":", "-", "'",
				"\"", "!!", "!!!", "??", "???", "?!?", "!?!", "?!?!", "!?!?");
		sentiments = getSentiments();
		previous = new LinkedHashMap<String, Double>();
		previous.put("absolutely", 0.293);
		previous.put("amazingly", 0.293);
		previous.put("awfully", 0.293);
		previous.put("completely", 0.293);
		previous.put("considerably", 0.293);
		previous.put("decidedly", 0.293);
		previous.put("deeply", 0.293);
		previous.put("effing", 0.293);
		previous.put("enormously", 0.293);
		previous.put("entirely", 0.293);
		previous.put("especially", 0.293);
		previous.put("exceptionally", 0.293);
		previous.put("extremely", 0.293);
		previous.put("fabulously", 0.293);
		previous.put("flipping", 0.293);
		previous.put("flippin", 0.293);
		previous.put("fricking", 0.293);
		previous.put("frickin", 0.293);
		previous.put("frigging", 0.293);
		previous.put("friggin", 0.293);
		previous.put("fully", 0.293);
		previous.put("fucking", 0.293);
		previous.put("greatly", 0.293);
		previous.put("hella", 0.293);
		previous.put("highly", 0.293);
		previous.put("hugely", 0.293);
		previous.put("incredibly", 0.293);
		previous.put("intensely", 0.293);
		previous.put("majorly", 0.293);
		previous.put("more", 0.293);
		previous.put("most", 0.293);
		previous.put("particularly", 0.293);
		previous.put("purely", 0.293);
		previous.put("quite", 0.293);
		previous.put("really", 0.293);
		previous.put("remarkably", 0.293);
		previous.put("everything", 0.293);		
		previous.put("so", 0.293);
		previous.put("substantially", 0.293);
		previous.put("thoroughly", 0.293);
		previous.put("totally", 0.293);
		previous.put("tremendously", 0.293);
		previous.put("uber", 0.293);
		previous.put("unbelievably", 0.293);
		previous.put("unusually", 0.293);
		previous.put("utterly", 0.293);
		previous.put("very", 0.293);
		previous.put("almost", -0.293);
		previous.put("barely", -0.293);
		previous.put("hardly", -0.293);
		previous.put("just enough", -0.293);
		previous.put("kind of", -0.293);
		previous.put("kinda", -0.293);
		previous.put("kindof", -0.293);
		previous.put("kind-of", -0.293);
		previous.put("less", -0.293);
		previous.put("little", -0.293);
		previous.put("marginally", -0.293);
		previous.put("occasionally", -0.293);
		previous.put("partly", -0.293);
		previous.put("scarcely", -0.293);
		previous.put("slightly", -0.293);
		previous.put("somewhat", -0.293);
		previous.put("sort of", -0.293);
		previous.put("sorta", -0.293);
		previous.put("sortof", -0.293);
		previous.put("sort-of", -0.293);
		
		
		

	}

	public static LinkedHashMap<String, SentimentsSocre> getSentiments() throws IOException {

		FileReader feedbackReader = new FileReader("C:/hackathon/sentiment.txt");
		BufferedReader feedbackReaderBr = new BufferedReader(feedbackReader);
		String feedback = "";
		LinkedHashMap<String, SentimentsSocre> sentiments = new LinkedHashMap<String, SentimentsSocre>();
		SentimentsSocre sentimentsSocre = null;
		String weight[];
		while ((feedback = feedbackReaderBr.readLine()) != null) {

			sentimentsSocre = new SentimentsSocre();
			weight = feedback.split("\t");
			sentimentsSocre.setScore(Double.parseDouble(weight[1]));
			sentimentsSocre.setWeight(Double.parseDouble(weight[2]));
			sentiments.put(weight[0], sentimentsSocre);
		}
		return sentiments;

	}

	public static double getScalar(String word, double valence, boolean isUpper) {
		double scalar = 0.0;

		String wordLower = word.toLowerCase();
		if (previous.containsKey(wordLower)) {
			scalar = (double) previous.get(wordLower);
			if (valence < 0) {
				scalar *= -1;
			}
			if (isUpper && word.toUpperCase().equals(wordLower)) {
				if (valence > 0) {
					scalar += SALAR_INCR;
				} else {
					scalar -= SALAR_DECR;
				}
			}
		}
		return scalar;

	}

	public static boolean getNagative(String word) {
		if (NEGATIVE.contains(word)) {
			return true;
		}
		if (word.indexOf("n't") != -1) {
			return false;
		}
		return false;
	}
	
	public static int getCounts(String word,String cha) {
		 
	        Pattern pattern = Pattern.compile(cha);
	        Matcher  matcher = pattern.matcher(word);

	        int count = 0;
	        while (matcher.find())
	            count++;

	       return count;
	}

	public static List<String> getWords(String feedback) throws IOException {

		TextTokenizer textTokenizer = new TextTokenizer();
		feedback = textTokenizer.preprocess(feedback);
		String[] str = textTokenizer.extractKeywords(feedback);
		ArrayList words = new ArrayList();
		for (int i = 0; i < str.length; i++) {
			words.add(str[i]);
		}
		return words;
	}

	public static List<String> getSentimentWords(String feedback)
			throws IOException {
		TextTokenizer textTokenizer = new TextTokenizer();
		String[] str = textTokenizer.extractKeywords(feedback);
		ArrayList words = new ArrayList();
		for (int i = 0; i < str.length; i++) {
			words.add(str[i]);
		}
		return words;
	}

	public static List<String> removeSingleWord(List words) throws IOException {
		List wordsList = new ArrayList();
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).toString().trim().length() > 1) {
				wordsList.add(words.get(i));
			}
		}
		return wordsList;
	}

	public static boolean isUpper(List words) throws IOException {
		int upperCount = 0;
		for (int i = 0; i < words.size(); i++) {
			//System.out.println("------ uppere "+words.get(i));
			if (words.get(i).toString().toUpperCase().equals(words.get(i))) {
				//System.out.println("------ CON ");
				upperCount++;
			}
		}
		int diffCount = 0;
		diffCount = words.size() - upperCount;
		if (diffCount > 0 && diffCount < words.size()) {
			return true;
		} else {
			return false;
		}

	}
	
	public static Double getWeight(ArrayList<Double> score) throws IOException {
		Double weight=0.0;
		for(Double d:score){
			weight+=d;
		}
		return weight;
	}
	
	

	public static Double getSentimentDetails(String feedback) throws IOException {
		
		ArrayList<Double> score = new ArrayList<Double>();
		Double result=0.0;
		try{
			//System.out.println(feedback);
			initialize();
			List words = getWords(feedback);
			List wordsOnly = new ArrayList();
			List sentimentWords = getSentimentWords(feedback);
			wordsOnly = removeSingleWord(words);
			String prefix = "";
			String sufix = "";
			String content="";
			int iteractionCount=0;
			for (int i = 0; i < wordsOnly.size(); i++) {

				for (int j = 0; j < PUNCH_LIST.size(); j++) {
					prefix = PUNCH_LIST.get(j) + "" + wordsOnly.get(i);
					sufix = wordsOnly.get(i) + "" + PUNCH_LIST.get(j);
					content=(String) sentimentWords.get(i);
					int index = content.indexOf(prefix);
					iteractionCount=0;
					while (index >= 0 && iteractionCount<150) {
						//System.out.println("1====bbb==");
						sentimentWords.set(i, wordsOnly.get(i));
						index = sentimentWords.indexOf(prefix);
						iteractionCount++;
					}
					iteractionCount=0;
					content=(String) sentimentWords.get(i);
					index = content.indexOf(sufix);
					while (index >= 0 && iteractionCount<150) {

						sentimentWords.set(i, wordsOnly.get(i));
						//System.out.println("after--"+wordsOnly.get(i));
						index = sentimentWords.indexOf(sufix);
						iteractionCount++;
					}
	
				}
	
			}
	
			boolean isUpper = isUpper(sentimentWords);
			
			//System.out.println("isUpper     ======>"+isUpper);
	
			SentimentsSocre sSource = null;
			//System.out.println("isUpper     ======>"+sentimentWords.size());
			for (int i = 0; i < sentimentWords.size(); i++) {
				//System.out.println(" i "+i);
				Double value = 0.0;
				if (i < sentimentWords.size() - 1 && previous.containsKey(sentimentWords.get(i))) {
					score.add(value);
					continue;
				}
				//System.out.println(" i 66"+sentimentWords.get(i).toString().toLowerCase());
				if (sentiments.containsKey(sentimentWords.get(i).toString().toLowerCase())) {
					System.out.println("sentimentWords   "+sentimentWords.get(i));
					sSource = (SentimentsSocre) sentiments.get(sentimentWords.get(i).toString().toLowerCase());
					//System.out.println(sSource.getScore() + " --- " + sentimentWords.get(i));
	
					value = sSource.getScore();
	
					if (isUpper && sentimentWords.get(i).toString().toUpperCase().equals(sentimentWords.get(i))) {
						if (value > 0) {
							value += INCR;
						} else {
							value -= INCR;
						}
					}
					
					double scalar = -0.74;
					//System.out.println("value       "+value);
					if (i > 0 && !sentiments.containsKey(sentimentWords.get(i - 1).toString().toLowerCase())) {
						value += getScalar(sentimentWords.get(i - 1).toString(), value, isUpper);
						if (getNagative(sentimentWords.get(i - 1).toString())) {
							value = value * scalar;
						}
					}
	
					if (i > 1 && !sentiments.containsKey(sentimentWords.get(i - 2) .toString().toLowerCase())) {
						double value1 = getScalar(sentimentWords.get(i - 2).toString(), value, isUpper);
						
						//System.out.println("value1      --->"+value1);
						if (value1 != 0) {
							value1 = value1 * 0.95;
						}
						value = value1 + value;
						if (sentimentWords.get(i - 2).toString().equals("never")&& (sentimentWords.get(i - 1).toString() .equals("so") || sentimentWords.get(i - 1) .toString().equals("this"))) {
							value *= 1.5;
						} else if (getNagative(sentimentWords.get(i - 2).toString())) {
							value = value * scalar;
						}
					}
	
					if (i > 2 && !sentiments.containsKey(sentimentWords.get(i - 3).toString().toLowerCase())) {
						double value1 = getScalar(sentimentWords.get(i - 3) .toString(), value, isUpper);
						if (value1 != 0) {
							value1 = value1 * 0.95;
						}
						value = value1 + value;
						if (sentimentWords.get(i - 3).toString().equals("never") && ((sentimentWords.get(i - 2).toString() .equals("so") || sentimentWords.get(i - 2)       
										.toString().equals("this")) || (sentimentWords
										.get(i - 1).toString().equals("so") || sentimentWords
										.get(i - 1).toString().equals("this")))) {
							value *= 1.5;
						} else if (getNagative(sentimentWords.get(i - 3).toString())) {
							value = value * scalar;
						}
					}
	
					score.add(value);
					
					
					System.out.println("Scores >"+score);
					System.out.println("Weight >"+getWeight(score));
					
	
				}else{
					score.add(0.0);
				}
			}
			
			try{
				
				
				
				int intextBut=0;
				if(getCounts(feedback, "But")>0 || getCounts(feedback, "BUT")>0 || getCounts(feedback, "but")>0){
					intextBut=(sentimentWords.indexOf("But")!=-1)?sentimentWords.indexOf("But"):(sentimentWords.indexOf("BUT")!=-1?sentimentWords.indexOf("BUT"):sentimentWords.indexOf("but"));
					for (int bu=0; bu<sentimentWords.size() ; bu++ ){
						if(bu < intextBut){
							score.set(bu, score.get(bu)*0.5);
						}else if (bu > intextBut) {
							score.set(bu, score.get(bu)*1.5);
						}					
					}
				}
				result=getWeight(score);
				
				int countMatched=getCounts(feedback, "!");
				double x = 0;
				if (countMatched>=2) {
					x = 2 * 0.292;
					if (result < 0) {
						result -= x;
					} else {
						result += x;
					}
				}
	
				countMatched=getCounts(feedback, "\\?");
				if (countMatched>=2) {
					x = 2 * 0.292;
					if (result < 0) {
						result -= x;
					} else {
						result += x;
					}
				}
				


	/*			for(int po =0 ; po< positivePhrase.size() ; po++){					
					if(feedback.indexOf(positivePhrase.get(po).toString())!= -1){
						x +=0.5;						
					}
				}*/
				result+=x;
	
			}catch(Exception e1){
				System.out.println(e1);
			}
	
			System.out.println("score          "+score);
			sentimentWords = removeSingleWord(sentimentWords);
			System.out.println(" Words Only " + wordsOnly.toString());
			System.out.println(" Words emotion " + sentimentWords.toString());
		}catch(Exception e){
			System.out.println(e);
		}
		System.out.println("result          "+result);
		return result;
	}

/*	public static void main(String[] args) throws IOException {

		// getSentimentDetails("");
		getSentimentDetails("I won't say that the  sucks and is stuck and not movie is astounding and I good wouldn't claim that the movie is too banal either:)");
	}*/
}
