package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 300;
	public static final int HEIGHT = 400;
	public static final int SCALE = 2;
	
	private Thread gameThread;
	private boolean isRunning = false;
	private int FPS = 60;
	
	public static boolean gameOver = false;
	private int obspace = 80;
	private int score;
	
	private BufferedImage image;
	private Graphics2D g2;
	
	private Player player;
	
	public ArrayList<Obstacle> obs;
	
	//testing
	
	//testing
	
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	//testing
	
	public void addNotify(){
		super.addNotify();
		start();
	}
	
	//testing
	
	public void start(){
		if(gameThread == null){
			gameThread = new Thread(this);
			gameThread.start();
		}
		isRunning = true;
	}
	
	public void init(){
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) image.getGraphics();
		
		obs = new ArrayList<Obstacle>();
		
		player = new Player(20, 20, 50, 80);
		addKeyListener(this);
		
		score = 0;
		
	}
	
	public void reset(){
		
		g2.clearRect(0, 0, WIDTH, HEIGHT);
		
		obs = new ArrayList<Obstacle>();
		
		player = new Player(20, 20, 50, 80);
		
		score = 0;
		
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerUpdate = 1000000000.0/FPS;
		double updateOffsetTime = 0;
		int frames = 0;
		int updates = 0;
		long secondTimer = System.currentTimeMillis();
		
		init();
		
		while(isRunning){
			long now = System.nanoTime();
			updateOffsetTime += (now - lastTime) / nsPerUpdate;
			lastTime = now;
			boolean shouldDraw = false;   //false
			
			while(updateOffsetTime >= 1){
				update();
				updates ++;
				updateOffsetTime -= 1;
				shouldDraw = true;
			}
			
			
			try{
				Thread.sleep(2);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			
			if(shouldDraw){
				draw();
			    //repaint();
				frames ++;
			}
			
			
			if(System.currentTimeMillis() - secondTimer >= 1000){
				secondTimer += 1000;
				System.out.println("Update: " + updates + " , Frames: "+ frames);
				updates = 0;
				frames = 0;
				//testing
				if(secondTimer/1000 % 3 == 1){  //obstacle spawner
					Random R = new Random();
					int r = R.nextInt(HEIGHT*3/4 - obspace);
					obs.add(new Obstacle(WIDTH, 0, r));
					obs.add(new Obstacle(WIDTH, r + obspace, HEIGHT*3/4 - r - obspace));
				}
				//testing
			}
			
			
		}
		
	}
	
	public void update(){
		player.update();
		
		if(!gameOver){
			for(int i =0; i<obs.size(); i++){
				obs.get(i).update();
				if(player.getBound().intersects(obs.get(i).getBound())){
					gameOver = true;
				}
				
				if(!gameOver && i%2 == 0){
					if(player.getMidX() == obs.get(i).getMidX()){
						score++;
					}
				}
				
				if(obs.get(i).getPosX() < 0 - 100){
					obs.remove(i);
					i--;
				}
			}
		}
		
		if(player.getMidY() > HEIGHT * 3/4){
			gameOver = true;
		}
	}
	
	public void draw(){
		///add here, use g2 to draw
		g2.setColor(Color.GRAY);
		g2.fillRect(0,0, WIDTH, HEIGHT);
		
		g2.setColor(Color.BLACK);
		g2.drawLine(0, HEIGHT * 3/4, WIDTH, HEIGHT * 3/4);
		
		for(Obstacle o : obs){
			
			o.draw(g2);
			
			// help to test the score condition
			//g2.setColor(Color.WHITE);
			//g2.drawLine(o.getMidPoint(), 0, o.getMidPoint(), HEIGHT);
			
		}
		
		player.draw(g2);
		
		//testing player position 
		//g2.drawLine(player.getMidX(), 0, player.getMidX(), HEIGHT);
		//g2.drawLine(0, player.getMidY(), WIDTH,  player.getMidY());
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("arail", Font.BOLD, 20));
		g2.drawString(score+"", WIDTH/2, HEIGHT*1/8);
		
		if(gameOver){	//testing
			g2.setColor(Color.BLACK);
			g2.drawString("Game Over", 100, 100);
			g2.drawString("press space to restart", 50, 150);
		}
		
		
		//////////////// 
		
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		g.dispose();
		g2.clearRect(0, 0, WIDTH, HEIGHT);
		
	}
	
	/*
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);
        draw();
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        g.dispose();
    }
	*/
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(player.getMidY() > HEIGHT * 3/4){
			if(gameOver){
				gameOver = false;
				reset();
			
				return;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(!gameOver){
				player.setJump(true);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			player.setJump(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}

}
