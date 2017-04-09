package com.github.marioariasga.cellsimulator;

public class Constants {
	public static int NUM_CELLS = 2000;

	public static double ENV_WIDTH = 1280.0;
	public static double ENV_HEIGHT = 800.0;

	public static double SPEED_INC = 0.1;
	public static double SPEED_DEC = 0.1;
	public static double MIN_SPEED = 0.5;
	public static double MAX_SPEED = 10;

	public static double PROB_ANGLE = 0.05;
	public static double ANGLE_DELTA = Math.PI/12;
	
	public static double INITIAL_ENERGY = 25000.0;
	public static double MIN_ENERGY = 0.0;
	public static double MAX_ENERGY = 50000.0;
	
	public static double ENERGY_PER_FOOD = 10;
	public static double ENERGY_PER_SPEED = 10;
			
	public static int CONTAINER_TYPE_SPECULAR = 0;
	public static int CONTAINER_TYPE_PERIODIC = 1;
	public static int CONTAINER_TYPE_REBOUND = 2;
	public static int CONTAINER_TYPE = CONTAINER_TYPE_REBOUND;
	
	public static int ENVIRONMENT_TYPE_CONSTANT = 0;
	public static int ENVIRONMENT_TYPE_GAUSS= 1;
	public static int ENVIRONMENT_TYPE_STEP = 2;
	public static int ENVIRONMENT_TYPE_GRADIENT = 3;
	
	public static int ENVIRONMENT_TYPE = ENVIRONMENT_TYPE_GAUSS;
}
