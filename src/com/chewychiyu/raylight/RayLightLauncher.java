package com.chewychiyu.raylight;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
@SuppressWarnings("serial")
public class RayLightLauncher extends JFrame{
	
	public static final Dimension windowDim = Toolkit.getDefaultToolkit().getScreenSize();
	public LightPanel lightPanel;
	
	public RayLightLauncher(){
		super("Ray Light");
		frame();
		addElements();
	}
	
	public void addElements(){
		lightPanel = new LightPanel(windowDim);
		this.add(lightPanel);
		this.pack();
	}
	
	public void frame(){
		this.setPreferredSize(windowDim);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){
		new RayLightLauncher();
	}
	
}
