package org.jointheleague.stephenh.flappybird;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class FlappyBird implements ActionListener, KeyListener {
	boolean flap = false;
	private int xPos = 50;
	private int yPos;
	private long startHop;
	private final BufferedImage flappingImage, fallingImage;
	private BufferedImage currentImage;
	
	public FlappyBird(BufferedImage flappingImage, BufferedImage fallingImage) {
		this.flappingImage = flappingImage;
		this.fallingImage = fallingImage;
		this.currentImage = fallingImage;
	}
	
	public void drawSelf(Graphics2D g) {
		if (flap) {
			g.drawImage(flappingImage, null, xPos, yPos);
		} else {
			g.drawImage(fallingImage, null, xPos, yPos);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (flap && (System.currentTimeMillis() - startHop) < 250) {
			yPos -= 20;
			currentImage = flappingImage;
		} else {
			flap = false;
			yPos += 10;
			currentImage = fallingImage;
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			hop();
		}
	}
	private void hop() {
		flap = true;
		startHop = System.currentTimeMillis();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public Shape getShape() {
		return new Rectangle(xPos, yPos, currentImage.getWidth(), currentImage.getHeight());
	}
	
	public void reset() {
		yPos = 0;
		flap = false;
		currentImage = fallingImage;
	}

	public int getYPos() {
		return yPos;
	}
	
}
