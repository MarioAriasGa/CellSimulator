package com.github.marioariasga.cellsimulator.environment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.marioariasga.cellsimulator.Constants;
import com.github.marioariasga.cellsimulator.Util;

public abstract class Environment {
	public BufferedImage img = null;

	public double eat(double x, double y) {
		return getFoodAmount(x, y);
		//1- C/C0
	}
	
	public abstract double getFoodAmount(double x, double y);

	public double getWidth() {
		return Constants.ENV_WIDTH;
	}
	
	
	public double getHeight() {
		return Constants.ENV_HEIGHT;
	}

	public BufferedImage getImage() {
		if(img==null) {
			img = new BufferedImage((int)getWidth(),(int)getHeight(), BufferedImage.TYPE_INT_RGB);
			for(int x=0;x<img.getWidth();x++) {
				for(int y=0;y<img.getHeight();y++) {
					double food = getFoodAmount(x, y);
					
					food = Util.clamp(0, food, 1);
					
					byte am = (byte)(food*255);
		
					img.setRGB(x, y, Util.getRGB(am, am, am) );
				}
			}
			/*try {
				ImageIO.write(img, "png", new File("/Users/mck/Desktop/"+this.getClass().getName()+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		return img;
	}
}
