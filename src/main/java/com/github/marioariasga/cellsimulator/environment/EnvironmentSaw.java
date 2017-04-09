package com.github.marioariasga.cellsimulator.environment;

public class EnvironmentSaw extends Environment {
	public double getFoodAmount(double x, double y) {
		return (x%400)/400;
	}
}
