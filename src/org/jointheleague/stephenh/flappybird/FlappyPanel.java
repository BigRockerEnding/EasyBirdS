package org.jointheleague.stephenh.flappybird;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class FlappyPanel extends JPanel implements Runnable, ActionListener {

	public static final int PANEL_WIDTH = 1000;
	public static final int PANEL_HEIGHT = 700;
	private FlappyBird myBird;
	private Pipe pipe, pipe2;
	private Timer flappyTick;
	private static int flappyScore;
	private final Font scoreFont = new Font("Helvetica", Font.BOLD, 24);
	private boolean speedUpHandled = true;
	
	public static void main(String[] args) {
		new FlappyPanel().run();
	}

	private BufferedImage flappingBirdImage;
	private BufferedImage fallingBirdImage;
	private BufferedImage backgroundImage;
	private BufferedImage pipeImage;

	@Override
	public void run() {
		JFrame frame = new JFrame("Stephen's Flappy Bird");
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		try {
			this.intitilizeImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		myBird = new FlappyBird(flappingBirdImage, fallingBirdImage);
		pipe = new Pipe(pipeImage, 0);
		pipe2 = new Pipe(pipeImage, PANEL_WIDTH / 2);
		flappyTick = new Timer(40, myBird);
		flappyTick.addActionListener(this);
		flappyTick.addActionListener(pipe);
		flappyTick.addActionListener(pipe2);
		frame.addKeyListener(myBird);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		flappyTick.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		collisionTest();
		if (flappyScore % 10 == 0) {
			if (speedUpHandled == true) { return;
			} else {
				speedUpHandled = true;
				flappyTick.setDelay(flappyTick.getDelay() - 4);
			}
		} else {
			speedUpHandled = false;
		}
	}
	
	private void collisionTest() {
		Shape birdShape = myBird.getShape();
		Shape pipeShape = pipe.getShape();
		Shape pipe2Shape = pipe2.getShape();
		
		if ((birdShape.intersects((Rectangle2D) pipeShape) || birdShape.intersects((Rectangle2D) pipe2Shape))) {
			flappyTick.stop();
			JOptionPane.showMessageDialog(this, "SMACK! You hit a pipe!\nYour Score: " + flappyScore);
			int restart = JOptionPane.showConfirmDialog(this, "Would you like to play again?", "Replay?", JOptionPane.YES_NO_OPTION);
			if (restart == JOptionPane.YES_OPTION) {
				reset();
			} else {
				System.exit(0);
			}
		} else if (myBird.getYPos() < 0 - myBird.getCurrentImage().getHeight()) {
			flappyTick.stop();
			JOptionPane.showMessageDialog(this, "Oops! That is too high!\nYour Score: " + flappyScore);
			int restart = JOptionPane.showConfirmDialog(this, "Would you like to play again?", "Replay?", JOptionPane.YES_NO_OPTION);
			if (restart == JOptionPane.YES_OPTION) {
				reset();
			} else {
				System.exit(0);
			}
		}
	}

	private void reset() {
		myBird.reset();
		pipe.reset(0);
		pipe2.reset(PANEL_WIDTH / 2);
		flappyTick.setDelay(40);
		flappyTick.start();
		flappyScore = 0;
		repaint();
	}

	private void intitilizeImages() throws IOException {
		flappingBirdImage = ImageIO.read(getClass().getResourceAsStream("images/BirdFlapping.gif"));
		fallingBirdImage = ImageIO.read(getClass().getResourceAsStream("images/BirdFalling.gif"));
		backgroundImage = ImageIO.read(getClass().getResourceAsStream("images/Background.gif"));
		pipeImage = ImageIO.read(getClass().getResourceAsStream("images/Pipe.gif"));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		this.tileBackground(g2);
		myBird.drawSelf(g2);
		pipe.drawSelf(g2);
		pipe2.drawSelf(g2);
		g2.setFont(scoreFont);
		g2.setColor(Color.WHITE);
		g2.drawString("" + flappyScore, PANEL_WIDTH / 2, 50);
	}
	
	private void tileBackground(Graphics2D g2) {
		int width = backgroundImage.getWidth();
		int x = 0;
		while (x < this.getWidth()) {
			g2.drawImage(backgroundImage, null, x, 0);
			x += 2 * width;
		}
		x = -2 * width;
		AffineTransform cached = g2.getTransform();
		g2.scale(-1, 1);
		while (-x < this.getWidth() + width) {
			g2.drawImage(backgroundImage, null, x, 0);
			x += -2 * width;
		}
		g2.setTransform(cached);
	}
	
	public static void incrementScore() {
		flappyScore += 1;
	}
	
}
