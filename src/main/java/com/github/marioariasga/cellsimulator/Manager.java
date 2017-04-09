package com.github.marioariasga.cellsimulator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.github.marioariasga.cellsimulator.environment.Environment;
import com.github.marioariasga.cellsimulator.environment.EnvironmentGradient;


public class Manager {
	
	private static Manager inst = null;
	
	public static Manager getInstance() {
		if(inst==null) {
			inst = new Manager();
		}
		return inst;
	}
	

	List<Cell> cels = new ArrayList<Cell>(Constants.NUM_CELLS);
	Environment env;
	long time=1;
	
	public Manager() {
		env = new EnvironmentGradient();
//		env = new EnvironmentStep();
//		env = new EnvironmentGauss();
//		env = new EnvironmentConstant();
		
		resetAll();
	}
	
	void reset() {
		time=1;
		for(Cell c : cels) {
			c.reset();
		}
	}
	
	void resetAll() {
		time=1;
		cels.clear();

		for(int i=0;i<Constants.NUM_CELLS;i++) {
			double x = Util.getRandomUniform(0.0, env.getWidth());
			double y = Util.getRandomUniform(0.0, env.getHeight());
			double angle = Util.getRandomUniform(0.0, Math.PI*2);
			double speed = Util.getRandomUniform(Constants.MIN_SPEED, Constants.MAX_SPEED);
			Cell c = new CellulaDisc(x,y,angle,speed);
			c.setColor(Color.red);
			cels.add(c);
		}
		/*
		for(int i=Constants.NUM_PARTICLES/2;i<Constants.NUM_PARTICLES;i++) {
			double x = Util.getRandomUniform(0, env.getWidth());
			double y = Util.getRandomUniform(0.0, env.getHeight());
			double angle = Util.getRandomUniform(0.0, Math.PI*2);
			double speed = Util.getRandomUniform(Constants.MIN_SPEED, Constants.MAX_SPEED);
			Cellula c = new CellulaDisc(x,y,angle,speed);
			c.setColor(Color.green);
			cels.add(c);
		}*/
	}
	
	public void performIteration() {
		time++;
    	for(Cell c : getCels()) {
    		c.move(getEnv());
    	}
	}
	
	public void performIterations(int n) {
		if(n<0) return;
		
    	for(int i=0;i<n;i++) {
    		performIteration();
    	}
	}
	
	public List<Cell> getCels() {
		return cels;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}
	public Environment getEnv() {
		return env;
	}
	public long getTime() {
		return time;
	}
	
	public static void main(String[] args) {
		Manager.getInstance();
		new Visualizer().createWindow();
	}
}
