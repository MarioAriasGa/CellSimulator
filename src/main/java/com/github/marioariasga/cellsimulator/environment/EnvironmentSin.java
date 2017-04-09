package com.github.marioariasga.cellsimulator.environment;

public class EnvironmentSin extends Environment {
	public double getFoodAmount(double x, double y) {
		double v1 = (Math.sin(x*5*Math.PI/getWidth())+1)*0.5;
//		double v2 = (Math.sin(y*4*Math.PI/getHeight())+1)*0.5;
//		return v1*v2;
		return v1;
	}
}
