package br.com.SnaizenRover.Pista;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Tile {
	private int x;
	private int y;
	public static Color color;
	
	public Tile(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public void render(Graphics g) {
		g.setColor(this.color);
		g.fillRect(x, y, 1, 1);
	}
}
