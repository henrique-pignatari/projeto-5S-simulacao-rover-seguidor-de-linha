package br.com.SnaizenRover.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Rover extends Entity{

	public boolean right, left, up, down, autoPilot = true;
	private double speed;
	private double angle;
	private double prevAngle;
	private Sensor sensor1, sensor2;
	
	private int sensorDist = 5;
	private double sensorAngle = 90;
	private int dir = 1;
	
	public Rover(double initX, double initY) {
		this(initX,initY,10,90);
	}
	
	public Rover(double initX, double initY, double angle) {
		this(initX,initY,10,angle);
	}
	
	public Rover(double initX, double initY, int radius, double angle) {
		super(initX-(radius/2),initY-(radius/2),radius,radius);
		this.prevAngle =  angle;
		this.angle = angle;
		this.speed = 1;
		this.sensor1 = new Sensor((int)((this.x+this.width/2) + (this.width/2 * Math.cos(Math.toRadians(angle)))),(int) ((this.y+this.height/2) - (this.height/2 * Math.sin(Math.toRadians(angle)))),this);
		this.sensor2 = new Sensor((int)((this.x+this.width/2) + (this.width/2 * Math.cos(Math.toRadians(angle)))),(int) ((this.y+this.height/2) - (this.height/2 * Math.sin(Math.toRadians(angle)))) + sensorDist,this);
	}
	
	public void moveForward() {		
		this.x += Math.cos(Math.toRadians(angle)) * speed;
		this.y -= Math.sin(Math.toRadians(angle)) * speed;
	}
	
	public void moveBack() {
		this.x -= Math.cos(Math.toRadians(angle)) * speed;
		this.y += Math.sin(Math.toRadians(angle)) * speed;
	}
	
	public void rotateRight(double amount) {
		this.angle -= amount;
	}
	
	public void rotateLeft(double amount) {
		this.angle += amount;
	}
	
	public void rotateTo(double angle) {
		this.angle = angle;
	}
	
	public void autoMoveRover() {
		
		if(sensor1.state != "fora") {
			if(sensor2.state != "fora") {
				moveForward();
			}
		}
		
		if(sensor2.state == "fora") {
			sensorAngle += (5*dir);
			
			if(sensorAngle >= (this.angle + 30) || sensorAngle <= (this.angle - 30)) {
				dir *= -1;
			}
		}
		
		if(sensor2.state == "dentro") {
			rotateTo(sensorAngle);
			moveForward();
		}
	}
	
	public void tick() {
		sensor1.tick((int)((this.x+this.width/2) + (this.width/2 * Math.cos(Math.toRadians(angle)))),(int) ((this.y+this.height/2) - (this.height/2 * Math.sin(Math.toRadians(angle)))));
		sensor2.tick((int)((this.x+this.width/2) + (((this.width/2) + sensorDist) * Math.cos(Math.toRadians(sensorAngle)))),(int) ((this.y+this.height/2) - (((this.height/2) + sensorDist)* Math.sin(Math.toRadians(sensorAngle)))));
		
		if(autoPilot == false) {
			if(right && !left) rotateRight(60/60.0);
			
			else if(left && !right) rotateLeft(60/60.0);
			
			else if(up && !down) moveForward();
			
			else if(down && !up) moveBack();
		
			return;
		}
		
		autoMoveRover();
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.CYAN);
		g.fillOval((int)this.x, (int)this.y, this.getWidth(), this.getHeight());
		sensor1.render(g);
		sensor2.render(g);
	}
}