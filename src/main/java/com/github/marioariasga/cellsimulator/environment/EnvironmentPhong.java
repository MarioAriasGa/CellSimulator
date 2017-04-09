package com.github.marioariasga.cellsimulator.environment;

import com.github.marioariasga.cellsimulator.Util;

public class EnvironmentPhong extends Environment {
	public double getFoodAmount(double x, double y) {
		return Util.clamp(0, 1-(int)(Math.sqrt(Math.pow(x-getWidth()/2,2)+Math.pow(y-getHeight()/2,2))/2), 1);
	}
}
