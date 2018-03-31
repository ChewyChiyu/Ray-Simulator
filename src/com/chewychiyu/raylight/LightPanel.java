package com.chewychiyu.raylight;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LightPanel extends JPanel{
	
	private Dimension dim;
	private LightNode lightNode;
	private Graphics2D graphics;
	private Point mouse = new Point();
	
	private ArrayList<Line2D> blockers = new ArrayList<Line2D>();
	private final BasicStroke MAX_BLOCKER_SIZE = new BasicStroke(10);
	private final int MAX_BLOCKER_COUNT = 7;
	
	public LightPanel(Dimension dim){
		this.dim = dim;
		panel();
		addElements();
		mouse();
	}
	
	public void mouse(){
		MouseAdapter ma = new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				mouse = e.getPoint();
				lightNode.translate(mouse, blockers);
				repaint();
			}
		};
		this.addMouseListener(ma);
		this.addMouseMotionListener(ma);
	}
	
	public void addElements(){
		
		for(int index = 0; index < MAX_BLOCKER_COUNT; index++){
			float randX1 = (float) (Math.random()*dim.getWidth());
			float randY1 = (float) (Math.random()*dim.getHeight());
			float randX2 = (float) (Math.random()*dim.getWidth());
			float randY2 = (float) (Math.random()*dim.getHeight());
			blockers.add(new Line2D.Float(randX1,randY1,randX1+randX2,randY1+randY2));
		}
		
		
		
		
		lightNode = new LightNode(new Point(0,0), blockers);
		
	}
	
	public void panel(){
		this.setPreferredSize(dim);
		this.setBackground(Color.BLACK);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		graphics = (Graphics2D) g;
		graphics.setColor(Color.WHITE);
		lightNode.draw(graphics);
		graphics.setColor(Color.BLUE);
		drawBlockers(graphics);
	}
	
	public void drawBlockers(Graphics2D g2d){
		g2d.setStroke(MAX_BLOCKER_SIZE);
		for(int index = 0; index < blockers.size(); index++){
			g2d.draw(blockers.get(index));
		}
	}

}
