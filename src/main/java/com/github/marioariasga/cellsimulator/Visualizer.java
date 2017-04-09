package com.github.marioariasga.cellsimulator;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.github.marioariasga.cellsimulator.environment.EnvironmentConstant;
import com.github.marioariasga.cellsimulator.environment.EnvironmentGauss;
import com.github.marioariasga.cellsimulator.environment.EnvironmentGradient;
import com.github.marioariasga.cellsimulator.environment.EnvironmentSin;
import com.github.marioariasga.cellsimulator.environment.EnvironmentStep;

public class Visualizer extends Component implements KeyListener /*, MouseListener, ActionListener,MouseMotionListener,MouseWheelListener */{
	private static final long serialVersionUID = -6754436015453195809L;

	protected Frame frame = null;
	private FpsCounter fps = new FpsCounter();
	protected Point windowPos = null;
	protected int windowScreenWidth = (int)Constants.ENV_WIDTH;
	protected int windowsScreenHeight = (int)Constants.ENV_HEIGHT;
	
	private boolean pause=false;
	private boolean fullScreen=false;
	private boolean background=true;
	private boolean histogram=false;
	private boolean info=false;
	private boolean showMean = false;

 	
	Histo foodHisto = new Histo(1.0);
	Histo energyHisto = new Histo(Constants.MAX_ENERGY);
	Histo xHisto = new Histo(Constants.ENV_WIDTH);
	Histo yHisto = new Histo(Constants.ENV_HEIGHT);
	Statistics eatenStat = new Statistics("eaten");
	Statistics odoStat = new Statistics("odo");
	Statistics energyStat = new Statistics("energ");
	Statistics xStat = new Statistics("pos_x");
	Statistics yStat = new Statistics("pos_y");
	
	
	public void refresh() {
		this.repaint();
	}
	
	public void paint(Graphics g1) {
		update(g1);
    }
	
	public void update(Graphics g1) {
		fps.newFrame();
	
		Graphics2D g = (Graphics2D) g1;
		   
    	g.setColor(Color.black);
    	g.fillRect(0,0,3000,3000);
    
    	if(background)
        	g.drawImage(Manager.getInstance().getEnv().getImage(), 0, 0, getWidth(), getHeight(), null);
       

    	
    	if(!pause) {
    		Manager.getInstance().performIteration();
    	}
    	
    	foodHisto.reset();
    	energyHisto.reset();
    	xHisto.reset();
    	yHisto.reset();
    	eatenStat.reset();
    	odoStat.reset();
    	energyStat.reset();
    	xStat.reset();
    	yStat.reset();
    	
    	for(Cell c : Manager.getInstance().getCels()) {		
			foodHisto.process(Manager.getInstance().getEnv().getFoodAmount(c.x, c.y));
    		energyHisto.process(c.energy);
    		xHisto.process(c.x);
    		yHisto.process(c.y);
			
			eatenStat.addValue(c.eaten);
			odoStat.addValue(c.odometer);
			energyStat.addValue(c.energy);
			xStat.addValue(c.x);
			yStat.addValue(c.y);
			
    		paintCell(g,c);
    	}
    	
    	foodHisto.end();
    	energyHisto.end();
    	xHisto.end();
    	yHisto.end();
    	eatenStat.end();
    	odoStat.end();
    	energyStat.end();
    	xStat.end();
    	yStat.end();
    	
    	if(showMean) {
	    	g.setColor(Color.green);
	    	g.drawOval((int)(xStat.getMean()-xStat.getDeviation()/2), (int)(yStat.getMean()-yStat.getDeviation()/2), (int)xStat.getDeviation(), (int)yStat.getDeviation());
	    	g.drawLine(0, (int)yStat.getMean(), getWidth(), (int)yStat.getMean());
	    	g.drawLine((int)xStat.getMean(), 0, (int)xStat.getMean(), getHeight());
    	}
    	
    	
    	if(histogram) {
//    		this.paintHistoHor(g, energyHisto);
    		this.paintHistoVer(g, xHisto);
    	}
    	
    	if(info)
    		paintMetadata(g);
    	

    	this.paintFPS(g);
    	
    	
    	if(!pause) this.repaint();
	}
	
	public void paintCell(Graphics2D g, Cell c) {
		/*g.setColor(c.getColor());
		int sizeEat = (int)(2+c.eaten*8/time);
		int sizeOdo = (int)(2+c.odometer*4/time);
		g.fillOval((int)c.x, (int)c.y, sizeEat, sizeOdo);*/
		
		g.setColor(c.getColor());
		
		int size = (int)(4+c.energy*10/Constants.MAX_ENERGY);
		g.fillOval((int)c.x, (int)c.y, size, size);
	}
	
	public void paintFPS(Graphics2D g) {
		
		String label = "Time: "+Manager.getInstance().getTime()+" FPS: "+fps.getFPS();
		FontMetrics m = g.getFontMetrics();
		int msgwidth = m.stringWidth(label);
		int msgheight = m.getHeight();
		int space = 8;
		
		AffineTransform t = g.getTransform();

		g.translate(10, 0);	

		g.setColor(Color.black);
		g.fillRect(0, getHeight()-msgheight-space, msgwidth+2*space, getHeight());
		g.setColor(Color.white);
		g.drawString(label, 0, getHeight()-space);
	
		g.setTransform(t);
	}
	
	public void paintHistoHor(Graphics2D g, Histo h) {
		int width = 18;
		int sep = 2;
		
		AffineTransform t = g.getTransform();

		g.translate(5, 5);

		
		for(int i=0;i<h.getNumBands();i++) {
			int val = h.getValue(i);
			g.setColor(Color.blue);
			
			g.fillRect(0, i*width, val/2, width-sep);
			
			g.setColor(Color.white);
			g.drawString(Integer.toString(val), 5, 12+i*width);
		}
		
		g.setTransform(t);
	}
	
	public void paintHistoVer(Graphics2D g, Histo h) {
		int width = 28;
		int sep = 2;
		
		AffineTransform t = g.getTransform();
		g.translate(10, getHeight()-30);
		
		for(int i=0;i<h.getNumBands();i++) {
			int val = h.getValue(i);
			g.setColor(Color.blue);		
			g.fillRect(i*width, -val, width-sep, val);
			
			g.setColor(Color.white);
			g.drawString(Integer.toString(val), i*width+sep, -5);
		}
		
		g.setTransform(t);
	}
	
	public void paintMetadata(Graphics2D g) {
		List<String> metaStrings = new ArrayList<String>();
		
		
		AffineTransform t = g.getTransform();

		g.translate(getWidth()-470, getHeight()-150);	
		
		metaStrings.add(odoStat.toString());
		metaStrings.add(eatenStat.toString());
		metaStrings.add(energyStat.toString());
		metaStrings.add(xStat.toString());
		metaStrings.add(yStat.toString());
		
		FontMetrics m = g.getFontMetrics();
		int fontHeight = m.getHeight();
		int lineWidth=0;

		// Find background size
		for (int i = 0; i < metaStrings.size(); i++) {
			String current = metaStrings.get(i);
			int thisWidth = m.stringWidth(current);
			if(thisWidth>lineWidth)
				lineWidth = thisWidth;
		}

		// draw background
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));

		g2d.setColor(Color.black);
		g2d.fillRoundRect(fontHeight, fontHeight*2, lineWidth+2*fontHeight, (metaStrings.size()+2)*fontHeight, 20, 20);

		g2d.setComposite(AlphaComposite.SrcOver);
		g2d.setColor(Color.white);
		g2d.drawRoundRect(fontHeight-1, fontHeight*2-1, lineWidth+2*fontHeight+1, (metaStrings.size()+2)*fontHeight+1, 20, 20);

		// draw strings

		int posx = 2*fontHeight;
		int posy = 3*fontHeight;

		g2d.drawString("Info:", posx, posy);
		g2d.drawLine(2*fontHeight, posy+3, 2*fontHeight+lineWidth, posy+3);

		posy+=fontHeight;

		for (int i = 0; i < metaStrings.size(); i++) {
			g2d.drawString(metaStrings.get(i), posx, posy);
			posy+=fontHeight;
		}
		

		g.setTransform(t);

	}
	
	
	public void createWindow() {	
        frame = new JFrame("Cell Simulator");

		// Exitig program on mouse click
		frame.addKeyListener(this);
/*		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.addMouseWheelListener(this);*/
        
        frame.add("Center", this);
        frame.pack();

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(windowScreenWidth,windowsScreenHeight);
    }
	
	public void toogleFullScreen() {
		setFullScreen(!fullScreen);
	}
	
	public boolean isFullScreen() {
		return fullScreen;
	}

	public void setFullScreen(boolean fs) {
		if(frame==null) return;
		if( !fullScreen && fs ) {
			fullScreen = true;
	
			windowScreenWidth = frame.getWidth();
			windowsScreenHeight = frame.getHeight();
			windowPos = frame.getLocationOnScreen();
			
			frame.dispose();
			frame.setUndecorated(true);
			
			DisplayMode mode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
			
			frame.setLocation(0, 0);
			frame.setSize(mode.getWidth(), mode.getHeight());
			
			// switching to fullscreen mode
			if(!System.getProperty("os.name").contains("Windows")) {
				GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().setFullScreenWindow(frame);
			}
	
			
			frame.setVisible(true);
			frame.repaint();
		} else if(fullScreen && !fs){
			fullScreen = false;
			
			if(!System.getProperty("os.name").contains("Windows")) {
				GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().setFullScreenWindow(null);
			}
			
			frame.dispose();
			frame.setLocation(windowPos);
			frame.setSize(windowScreenWidth, windowsScreenHeight);
	
			frame.setUndecorated(false);
			frame.setVisible(true);
			frame.repaint();
		}
	}

	public void keyPressed(KeyEvent event) {
		char character = Character.toLowerCase(event.getKeyChar());
		int code = event.getKeyCode();
		
//		System.out.println("KEYPRESS: "+code+" = "+character);
		
		
		if(code==27) {
			System.exit(0);
		} else if (character==' ') {
			pause = !pause;
		} else if(character=='b') {
			background = !background;
		} else if(character=='f') {
			// f
			this.toogleFullScreen();
		} else if(character=='h') {
			histogram = !histogram;
		} else if(character=='1') {
			Manager.getInstance().setEnv(new EnvironmentConstant());
		} else if(character=='2') {
			Manager.getInstance().setEnv(new EnvironmentStep());
		} else if(character=='3') {
			Manager.getInstance().setEnv(new EnvironmentGradient());
		} else if(character=='4') {
			Manager.getInstance().setEnv(new EnvironmentSin());
		} else if(character=='5') {
			Manager.getInstance().setEnv(new EnvironmentGauss());
		} else if(character=='r') {
			Manager.getInstance().reset();
		} else if(character=='t') {
			Manager.getInstance().resetAll();
		} else if(character=='+') {
			Manager.getInstance().performIterations(100);
		} else if(character=='i') {
			info = !info;
		} else if(character=='m') {
			showMean = !showMean;
		}
		
		this.repaint();
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
