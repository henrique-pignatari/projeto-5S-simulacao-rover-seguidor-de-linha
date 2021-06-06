package br.com.SnaizenRover.Pista;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pista {
	
	private BufferedImage map;
	private static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static int startX, startY;
	
	public Pista(String path) {
		try {
			map = ImageIO.read(getClass().getResource(path));
			
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			
			int[] pixels =  new int[WIDTH * HEIGHT];
			
			tiles = new Tile[WIDTH * HEIGHT];
			
			map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
			                                         
			for(int xx = 0; xx < WIDTH; xx++) {
				for(int yy = 0; yy < HEIGHT; yy++) {
					int pixelAtual = xx + (yy*WIDTH);
					
					if(pixels[pixelAtual] == 0xFF000000) {
						//Tile IN
						tiles[xx + (yy * WIDTH)] = new BlackTile(xx, yy);
					}else if(pixels[pixelAtual] == 0xFFFFFFFF) {
						//Tile OUT
						tiles[xx + (yy * WIDTH)] = new WhiteTile(xx, yy);
						
					}else if(pixels[pixelAtual] == 0xFF00FF00) {
						//Pixel Start
						tiles[xx + (yy * WIDTH)] = new StartTile(xx, yy);
						startX = xx;
						startY = yy;
						
					}else if(pixels[pixelAtual] == 0xFFFF0000) {
						//Pixel final
						tiles[xx + (yy * WIDTH)] = new EndTile(xx, yy);
					}else {
						throw new Exception("Erro ao pegar os tiles, Tile invalido");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static Tile GetTile(int x, int y) {
		return tiles[x + (y*WIDTH)];
	}
	
	public void render(Graphics g) {
		g.drawImage(map, 0, 0, null);
	}
}