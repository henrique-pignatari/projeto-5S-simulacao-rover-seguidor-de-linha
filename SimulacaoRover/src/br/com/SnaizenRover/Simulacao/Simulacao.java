package br.com.SnaizenRover.Simulacao;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import br.com.SnaizenRover.Entities.Entity;
import br.com.SnaizenRover.Entities.Rover;
import br.com.SnaizenRover.Graficos.Spritesheet;
import br.com.SnaizenRover.Pista.Pista;
import br.com.SnaizenRover.Pista.StartTile;
import br.com.SnaizenRover.Pista.Tile;

public class Simulacao extends Canvas implements Runnable, KeyListener{
	
	public static JFrame frame;
	private final int WIDTH = 480;
	private final int HEIGHT = 270;
	private final int SCALE  = 2;
	
	private Thread thread;
	private boolean isRunning = true;
	
	private Rover rover;
	private boolean manualControl = true;
	
	private BufferedImage image;
	
	public List<Entity> entities;
	public Spritesheet spritesheet;
	
	public static Pista pista;
	
	public Simulacao() {
		addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		InitFrame();
		
		pista = new Pista("/pista.png");
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		rover = new Rover(Pista.startX,Pista.startY);
		
		entities = new ArrayList<Entity>();
		entities.add(rover);
		//spritesheet = new Spritesheet("/spritesheet.png");
		
	}
	
	public void InitFrame() {
		frame = new JFrame("Simulação");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Simulacao simulacao = new Simulacao();
		simulacao.start();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(new Color(19,19,19));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		pista.render(g);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
	}	
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = Math.pow(10, 9)/amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning) {
			long now = System.nanoTime();			
			delta += (now - lastTime)/ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				delta--;
				
				frames++;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer = System.currentTimeMillis();
			}
		}
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(manualControl = true) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				rover.right = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				rover.left = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				rover.up = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				rover.down = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(manualControl == true) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				rover.right = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				rover.left = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				rover.up = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				rover.down = false;
			}
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
}