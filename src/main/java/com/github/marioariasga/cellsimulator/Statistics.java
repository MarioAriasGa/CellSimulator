package com.github.marioariasga.cellsimulator;
import java.text.DecimalFormat;


public class Statistics {
	double min=Double.MAX_VALUE;
	double max=-Double.MAX_VALUE;
	double mean=0;
	double sumx=0;
	double sumx2=0;
	double sumx3=0;
	double deviation=0;
	double fisher=0;
	int number = 0;
	String name;
	
	Statistics(String name) {
		this.name = name;
	}
	
	void addValue(double val) {
		min = Math.min(min, val);
		max = Math.max(max, val);
		sumx += val;
		sumx2 += val * val;
		sumx3 += val * val * val;
		mean += val;
		deviation += val * val;
		//fisher += val * val * val;
		number++;
	}
	
	void reset() {
		min=Double.MAX_VALUE;
		max=-Double.MAX_VALUE;
		mean=0;
		deviation=0;
		sumx=0;
		sumx2=0;
		sumx3=0;
		fisher=0;
		number = 0;	
	}
	
	void end() {
		mean = sumx / number;
    	
    	deviation = Math.sqrt(sumx2/number - mean * mean);
    	   
    	fisher = sumx3 - 3*sumx2*mean +3*sumx*mean*mean - number * mean * mean * mean;
    	fisher = fisher/(number*deviation*deviation*deviation);
    	/*fisher = fisher/ number * mean * mean * mean;
    	fisher = fisher / deviation*deviation*deviation;*/
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getMean() {
		return mean;
	}

	public double getDeviation() {
		return deviation;
	}

	public int getNumber() {
		return number;
	}
	
	private static DecimalFormat fm = null;
	
	private String fmt(double v) {
		if(fm==null) {
			fm= new DecimalFormat("0.00");
		}
		return Util.fillWithLeadingSpaces(fm.format(v), 8);
	}
	
	public String toString() {
		return Util.fillWithLeadingSpaces(name,8)+": "+ fmt(min)+" - "+fmt(mean)+" - "+fmt(max)+ "  / "+fmt(deviation)+" / "+fmt(fisher);
	}
	
	public static void main(String[] args) {
//		int list[] = {3,7,7,19};
//		int list[] = {1,2,3,4,3,2,1};
		int list[] = {10,11,12,13,14,15,16,17,18,29};
		
		Statistics st = new Statistics("test");
		
		st.reset();	
		for(int i=0;i<list.length;i++) { 
			st.addValue(list[i]);
		}
		st.end();
		double mean = st.getMean();
		double dev = st.getDeviation();
		
		double val=0;
		for(int i=0;i<list.length;i++) {
			double tmp = Math.pow(list[i] - mean,3);
			val+=tmp;
		}
		val = val/list.length;
		val = val/Math.pow(dev,3);
		
		System.out.println(st.toString());
		System.out.println(val);
	}
}
