package com.github.marioariasga.cellsimulator.environment;

import com.github.marioariasga.cellsimulator.Util;

public class EnvironmentGauss extends Environment {

	public double getFoodAmount(double x, double y) {
		double meanx = getWidth()*0.5;
		double sigmax = getWidth()*0.3;
		
		double meany = getHeight()*0.5;
		double sigmay = getHeight()*0.3;
		
		double p1 = Util.getZ(x, meanx, sigmax)*2.5;
		double p2 = Util.getZ(y, meany, sigmay)*2.5;
		
		return p1*p2;
	}
}
