package com.github.marioariasga.cellsimulator;


public class Histo {
	int data[];
	int numBands;
	double maxvalue;
	double minvalue;
	double maxVal; // Para intervalos
	double total;
	int number;
	double average;
	double deviation;
	double fisher;
	
	public Histo(double maxVal) {
		reset();
		this.maxVal = maxVal;
	}

	public void reset() {
		numBands = 15;
		data = new int[numBands];
		
		number=0;
		minvalue=Double.MAX_VALUE;
		maxvalue=-Double.MAX_VALUE;
		total=0;
		average=0;
		deviation=0;
		fisher=0;
	}
	
	public void process(double val) {
		number++;
		minvalue = Math.min(minvalue, val);
		maxvalue = Math.max(maxvalue, val);
		total+=val;
		average+=val;
		deviation += val * val;
		fisher += val * val * val;
		
		int pos = (int)(val*(numBands-1)/maxVal);
		if(pos>=0 && pos<data.length) {
			data[pos]++;
		} else {
//			System.out.println("Overflow: "+pos);
		 }
	}
	
	public void end() {
		average = average/number;
		deviation = deviation/number - average * average;
		deviation = Math.sqrt(deviation);
		
		fisher = fisher/number - average * average * average;
		fisher = fisher/ deviation*deviation*deviation;

	}

	public void dump() {
		/*System.out.println("Average: "+average);
		System.out.println("Deviation: "+deviation);
		
		System.out.println("\nHISTO");
		for(int i=0;i<data.length;i++) {
			System.out.println(i+" "+data[i]);
		}*/
	}
	
	public int getNumBands() {
		return numBands;
	}

	public double getMaxvalue() {
		return maxvalue;
	}

	public double getMinvalue() {
		return minvalue;
	}
	
	public double getAverage() {
		return average;
	}
	
	public double getDeviation() {
		return deviation;
	}
	
	public double getFisher() {
		return fisher;
	}
	
	public int getValue(int num) {
		return data[num];
	}
}
