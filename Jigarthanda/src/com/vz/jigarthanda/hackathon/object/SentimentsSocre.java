package com.vz.jigarthanda.hackathon.object;

public class SentimentsSocre {

	private double score=0.0;
	private double weight=0.0;
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "SentimentsSocre [score=" + score + ", weight=" + weight + "]";
	}
	

}
