package org.jointheleague.stephenh.flappybird;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Pipe implements ActionListener {
	private int xPos, yPos;
	private final BufferedImage image;
	private static final Random RNG = new Random();
	
	public Pipe(BufferedImage image, int start) {
		this.image = image;
		xPos = FlappyPanel.PANEL_WIDTH + start;
		yPos = RNG.nextInt(FlappyPanel.PANEL_HEIGHT - 200) + 100;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		xPos -= 20;
		if (xPos + image.getWidth() < 0) {
			xPos = FlappyPanel.PANEL_WIDTH;
			yPos = RNG.nextInt(FlappyPanel.PANEL_HEIGHT - 200) + 100;
			FlappyPanel.incrementScore();
		}
	}
	
	public void drawSelf(Graphics2D g) {
		g.drawImage(image, null, xPos, yPos);
	}
	
	public Shape getShape() {
		return new Rectangle(xPos, yPos, image.getWidth(), image.getHeight());
	}
	
	public void reset(int newStart) {
		xPos = FlappyPanel.PANEL_WIDTH + newStart;
		yPos = RNG.nextInt(FlappyPanel.PANEL_HEIGHT - 200) + 100;
	}
	
}
