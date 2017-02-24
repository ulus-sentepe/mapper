package com.fererlab;

public class Solver {
	

	
	
	public double evaluate(double request, double cacheLatancy, double serverLatency, double videoSize, double cacheSize) {

		if(request == 0){
			return 0;
		}
		
		if(cacheSize < videoSize){
			return -1;
		}
		
		if(cacheLatancy == 0){
			return 0;
		}
		
		if(serverLatency == 0){
			return 0;
		}
		
		if(videoSize == 0){
			return 0;
		}
		
		if(cacheSize == 0){
			return 0;
		}
		
		double result = request * (serverLatency / cacheLatancy) * (videoSize/ cacheSize);

		return result;

	}
	
	
}
