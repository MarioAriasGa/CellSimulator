package com.github.marioariasga.cellsimulator;


public class Util {
	// Math
	public static double SQR2PI = Math.sqrt(2*Math.PI);
	
	public static int getRGB(int red,int green,int blue) {
		red = (red&0xFF) << 16;
		green = (green &0xFF) << 8;
		blue = blue & 0xFF;
		return red | green | blue;
	}
	
	public static double clamp(double min, double val, double max) {
		return Math.min(Math.max(val, min),max);
	}
	
	public static double mix(double x, double y, double a) {
		return x+(y-x)*a;
	}
	
	double linearScale(double x, double x1, double x2, double y1, double y2) {
		double m = (y2-y1)/(x2-x1);	
		return y1 + (x-x1) * m;
	}
	
	double gamma(double val, double gamma) {
		return Math.pow(val, 1/gamma);
	}
	
	// String
	public static String fillWithLeadingSpaces(String string, int finalSize) {
        StringBuilder stringBuilder = new StringBuilder();
 
        for(int i=0;i<finalSize-string.length();i++) {
        	stringBuilder.append(' ');
        }
        stringBuilder.append(string);
 
        return stringBuilder.toString();
    }
	
	
	// Random
	static double getRandomUniform() {
		return Math.random();
	}
	
	static double getRandomUniform(double min, double max) {
		return getRandomUniform() * (max - min) + min;
	}
	
	static double getRandomExponential(double fmean)
	{
	     return -fmean*Math.log(getRandomUniform());
	}
	
	static boolean getRandomBoolean(double prob) {
		return getRandomUniform()<prob;
	}

	static double getRandomNormal(float mean, float sigma){
	       double rone = 1.*getRandomUniform();
	       double rtwo = 1.*getRandomUniform();
	       double zone = Math.sqrt(-2*Math.log(rone))*Math.sin(2*Math.PI*rtwo);

	     return (float) zone*sigma+mean;
	}
	
	// Statistics
	public static double getZ(double val, double mean, double deviation) {
		double num = (val-mean)/deviation;
		return Math.exp(  -0.5 * num * num  ) / SQR2PI;
	}
	
	static double getZcomp(double x1, double s1, double n1, double x2, double s2, double n2) {
		return (x1-x2)/Math.sqrt(Math.pow(s1,2)/n1+Math.pow(s2,2)/n2);
	}
	
	/*static double gamma(double val) {
		return val;
	}
	
	static double getT(double val, int n) {
		return gamma((n+1)/2)/ (gamma(n/2)*gamma(0.5)*Math.sqrt(n));
	}*/
	
	
	// Interpolation
	double LinearInterpolate( double y1,double y2, double mu)
	{
		return(y1*(1-mu)+y2*mu);
	}

}
