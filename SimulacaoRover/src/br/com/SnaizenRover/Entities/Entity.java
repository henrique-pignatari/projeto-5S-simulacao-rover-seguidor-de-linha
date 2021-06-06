package br.com.SnaizenRover.Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Entity {

	protected double x; 
	protected double y;
	protected int width;
	protected int height;
	private BufferedImage sprite;
	
	public Entity(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Entity(double x, double y, int width, int height,BufferedImage sprite) {
		this(x, y, width, height);
		this.sprite = sprite;
	}
	
	public void setX(double newX) {
		this.x = newX;
	}
	
	public void setY(double newY) {
		this.x = newY;
	}	
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, (int)this.getX(), (int)this.getY(), null);
	}
}