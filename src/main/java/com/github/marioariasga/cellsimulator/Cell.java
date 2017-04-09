package com.github.marioariasga.cellsimulator;
import java.awt.Color;

import com.github.marioariasga.cellsimulator.environment.Environment;


public abstract class Cell {
	protected double x, y;
	protected double angle;
	protected double speed;
	protected double eaten=0;
	protected double odometer=0;
	protected double energy=Constants.INITIAL_ENERGY;
	
	protected Color color=Color.red;
	

	public Cell(double x, double y, double angle, double speed) {
		super();
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.speed = speed;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getAngle() {
		return angle;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	
	void reset() {
		eaten=0;
		odometer=0;
		energy = Constants.INITIAL_ENERGY;
	}
	
	public abstract void move(Environment e);

	public void checkInside(Environment e) {
		// Specular
		if(Constants.CONTAINER_TYPE == Constants.CONTAINER_TYPE_SPECULAR) {
			x = Util.clamp(0, x, e.getWidth());
			y = Util.clamp(0, y, e.getHeight());
		// Periodic
		} else if(Constants.CONTAINER_TYPE == Constants.CONTAINER_TYPE_PERIODIC) {
			x = x<0 ? x+e.getWidth() : x;
			x = x>e.getWidth() ? x-e.getWidth() : x;
			y = y<0 ? y+e.getHeight() : y;
			y = y>e.getHeight() ? y-e.getHeight() : y;
		// Rebound
		} else if(Constants.CONTAINER_TYPE == Constants.CONTAINER_TYPE_REBOUND) {
			if(x<0) {
				x=0;
				angle = Math.PI-angle;
			} else if(x>e.getWidth()) {
				x=e.getWidth();
				angle = Math.PI-angle;
			}
			if(y<0) {
				y=0;
				angle = -angle;
			} else if(y>e.getHeight()) {
				y=e.getHeight();
				angle = -angle;
			}
		}
	}
 
}
