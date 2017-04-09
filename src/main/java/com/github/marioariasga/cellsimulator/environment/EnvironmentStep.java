package com.github.marioariasga.cellsimulator.environment;

public class EnvironmentStep extends Environment {
	public double getFoodAmount(double x, double y) {
		return x<getWidth()/2 ? 0.8 : 0.2;
	}
}
