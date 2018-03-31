package com.chewychiyu.raylight;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class LightNode {

	public ArrayList<Line2D> rays = new ArrayList<Line2D>();
	public Point anchor;

	public static final int MAX_RAY_LEN = 3000;
	public static final double MAX_RAY_THETA = Math.PI/70;
	public static final BasicStroke MAX_RAY_GIRTH = new BasicStroke(1);

	public LightNode(Point anchor, ArrayList<Line2D> blockers){
		this.anchor = anchor;
		spawnRays(blockers);
	}

	public void translate(Point pos, ArrayList<Line2D> blockers){
		anchor = pos;
		spawnRays(blockers);
	}

	public void spawnRays(ArrayList<Line2D> blockers){
		rays.clear();
		for(float theta = 0; theta < Math.PI*2; theta+=MAX_RAY_THETA){
			Point2D initial = new Point2D.Float(anchor.x,anchor.y);
			float xFin = (float) ((MAX_RAY_LEN)*Math.cos(theta) + initial.getX());
			float yFin = (float) ((MAX_RAY_LEN)*Math.sin(theta) + initial.getY());
			Point2D finish = new Point2D.Float(xFin,yFin);
			Line2D ray = new Line2D.Double(initial,finish);
			for(int index = 0; index < blockers.size(); index++){
				Line2D blocker = blockers.get(index);
				if(blocker.intersectsLine(ray)){
					double blockerM = findSlope(blocker);
					double blockerB = findB(blocker);
					double rayM = findSlope(ray);
					//vertical ray , slope = undefined
					if(rayM==Double.NEGATIVE_INFINITY || rayM==Double.POSITIVE_INFINITY){
						ray.setLine(ray.getX1(), ray.getY1(), ray.getX2()+1, ray.getY2()); //manual shift adjust
						rayM = findSlope(ray);
					}
					double rayB = findB(ray);
					double x = (blockerB - rayB) / (rayM - blockerM);
					double y = rayM * x + rayB;
					ray.setLine(ray.getX1(), ray.getY1(), x, y);
				}
			}			
			rays.add(ray);
		}
	}

	public double findB(Line2D line){
		return -findSlope(line)*line.getX1()+line.getY1();
	}

	public double findSlope(Line2D line){
		double deltaX = line.getX2()-line.getX1();
		double deltaY = line.getY2()-line.getY1();
		return deltaY/deltaX;
	}

	public void draw(Graphics2D g2d){
		g2d.setStroke(MAX_RAY_GIRTH);
		for(int index = 0; index < rays.size(); index++){
			Line2D ray = rays.get(index);
			g2d.draw(ray);
		}
	}

}
