package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Obstacle {
	private final int width = 50;
	private int height;
	private Rectangle bound;
	private int midX;
	
	private int posX;
	private int posY;
	
	public Obstacle( int posX, int posY, int height){
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.bound = new Rectangle(this.posX, this.posY, this.width, this.height);
		this.midX = posX + width/2;
	}
	
	public void update(){
		posX--;
		bound.setLocation(posX, posY);
		midX = posX + width/2;
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.GREEN);
		g.fillRect(posX, posY, width, height);
		
		//draw obstacle bound
		g.setColor(Color.WHITE);
		g.drawRect(bound.x, bound.y, bound.width, bound.height);
	}
	
	public int getPosX(){
		return posX;
	}
	
	public Rectangle getBound(){
		return bound;
	}
	
	public int getMidX(){
		return midX;
	}
}
