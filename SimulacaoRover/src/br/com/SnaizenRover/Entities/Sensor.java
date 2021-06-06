package br.com.SnaizenRover.Entities;

import java.awt.Color;
import java.awt.Graphics;

import br.com.SnaizenRover.Pista.Pista;
import br.com.SnaizenRover.Pista.Tile;
import br.com.SnaizenRover.Pista.WhiteTile;

public class Sensor extends Entity{
	
	private Rover rover;
	public String state;
	
	public Sensor(double x, double y, Rover rover) {
		this(x,y,1,1,rover);
	}

	private Sensor(double x, double y, int width, int height, Rover rover) {
		super(x, y, width, height);
		this.rover = rover;
	}
	
	public void changeState(String state) {
		this.state = state;
	}
	
	public void tick(int newX, int newY) {
		this.x = newX;
		this.y = newY;
		
		if(Pista.GetTile((int) x,(int) y) instanceof WhiteTile) {
			changeState("fora");
		}else {
			changeState("dentro");
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)this.x, (int)this.y, this.width, this.height);
	}
}
