package main;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Player {
	private int width;
	private int height;
	private Rectangle bound;
	private int midX;
	private int midY;
	
	private double posX;
	private double posY;
	
	private double momentum = 0;
	private double fallSpeed = 0.20;
	private double maxfallSpeed = 10;
	private double jumpStart = -4;
	
	private boolean isJump = false;
	
	public Player(int width, int height, int posX, int posY){
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.posY = posY;
		this.bound = new Rectangle((int)this.posX, (int)this.posY, this.width, this.height);
		this.midX = posX + width/2;
		this.midY = posY + height/2;
	}
	
	public void update(){
		
		if( momentum <= maxfallSpeed){
			momentum += fallSpeed;
		}
		
		if(isJump && momentum >=0){
			momentum = jumpStart;
		}
				
		if(posY >= GamePanel.HEIGHT * 3/4){
			posY = GamePanel.HEIGHT * 3/4;
			if(momentum > 0){
				momentum = 0;
			}
		}
		
		if(posY < 0){
			posY = 0;
		}
		
		posY += momentum;
		midY = (int)posY + height/2; 
		bound.setLocation((int)posX, (int)posY);
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.RED);
		g.fillRect((int)posX, (int)posY, width, height);
		
		//draw the player bound
		g.setColor(Color.WHITE);
		g.drawRect(bound.x, bound.y, bound.width, bound.height);
	}
	
	public boolean isJump(){
		return isJump;
	}
	
	public void setJump(boolean jump){
		isJump = jump;
	}
	
	public int getMidX(){
		return midX;
	}
	
	public int getMidY(){
		return midY;
	}
	
	public Rectangle getBound(){
		return bound;
	}
}
