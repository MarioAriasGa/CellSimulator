package com.github.marioariasga.cellsimulator;

import com.github.marioariasga.cellsimulator.environment.Environment;

public class CellulaDisc extends Cell {

	public CellulaDisc(double x, double y, double angle, double speed) {
		super(x, y, angle, speed);
	}

	@Override
	public void move(Environment e) {
		double food = e.getFoodAmount(x, y);
		eaten+=food;
	
		if(Util.getRandomBoolean(food)) {
			energy += Constants.ENERGY_PER_FOOD;
			this.speed = this.speed-Constants.SPEED_DEC;
		} else {
			this.speed = this.speed+Constants.SPEED_INC;
		}
		
		this.speed = Util.clamp(Constants.MIN_SPEED, this.speed, Constants.MAX_SPEED);
		odometer+=speed;
		
		energy -= speed*Constants.ENERGY_PER_SPEED;
		energy = Util.clamp(Constants.MIN_ENERGY, this.speed, Constants.MAX_ENERGY);
		
		if(Util.getRandomBoolean(Constants.PROB_ANGLE)) {
			this.angle = this.angle + Util.getRandomUniform(-Math.PI, Math.PI);
		}
//		this.angle = this.angle + Util.getRandomUniform(-Constants.ANGLE_DELTA, Constants.ANGLE_DELTA);

//		this.angle = this.angle + Util.getRandomUniform(-Math.PI/4, Math.PI/4)*food;
		
		this.x = this.x + this.speed * Math.cos(angle);
		this.y = this.y + this.speed * Math.sin(angle);
		
		checkInside(e);
	}

}
