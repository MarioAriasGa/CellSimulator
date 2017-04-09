package com.github.marioariasga.cellsimulator;

import com.github.marioariasga.cellsimulator.environment.Environment;

public class CellCont extends Cell {
	public CellCont(double x, double y, double angle, double speed) {
		super(x,y,angle,speed);
	}
	
	@Override
	public void move(Environment e) {
		double food = e.getFoodAmount(x, y);
		eaten+=food;
		
		energy += food*Constants.ENERGY_PER_FOOD;
		
		this.speed = (1-food) * (Constants.MAX_SPEED-Constants.MIN_SPEED) + Constants.MIN_SPEED;
		odometer+=speed;
		
		energy -= speed*Constants.ENERGY_PER_SPEED;
		energy = Util.clamp(Constants.MIN_ENERGY, energy, Constants.MAX_ENERGY);
		
		this.angle = this.angle + Util.getRandomUniform(-Constants.ANGLE_DELTA, Constants.ANGLE_DELTA);

		this.x = this.x + this.speed * Math.cos(angle);
		this.y = this.y + this.speed * Math.sin(angle);
		
		checkInside(e);
	}
}
