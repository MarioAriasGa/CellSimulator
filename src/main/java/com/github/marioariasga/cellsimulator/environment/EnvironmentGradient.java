package com.github.marioariasga.cellsimulator.environment;

public class EnvironmentGradient extends Environment {

	public double getFoodAmount(double x, double y) {
		return x/getWidth();
		
	}

}
