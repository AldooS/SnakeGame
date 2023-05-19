//I got some help ofc importing these java packages but the main idea was mine, Thanks Aldo.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 300; // Width of the game window
	private static final int HEIGHT = 300; // Height of the game window
	private static final int DOT_SIZE = 10; // Size of each snake segment
	private static final int DELAY = 90; // Delay between each game tick (in milliseconds)
	
	private ArrayList<Point> snake; // List of snake segments
	private Point fruit; // Position of the fruit
	private int direction; // Direction of the snake's movement
	private boolean gameOver; // Whether the game is over or not
	private Timer timer; // Timer for game ticks
        private int score; // Player's score
        private Image backgroundImage;
        private JLabel timerLabel;  

        

	
	public SnakeGame() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		initGame();
                initTimer();
		timer = new Timer(DELAY, this);
		timer.start();
	}
        private void initTimer() {
            timerLabel = new JLabel("Time: 0");
            timerLabel.setForeground(Color.WHITE);
            timerLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            add(timerLabel, BorderLayout.NORTH);

            Timer timer = new Timer(1000, new ActionListener() {
            public int time = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                timerLabel.setText("Time: " + time);
            }
        });
        timer.start();
    }
        
	
	private void initGame() {
                backgroundImage = Toolkit.getDefaultToolkit().createImage("resources/grass_texture.jpg");
		snake = new ArrayList<Point>();
		snake.add(new Point(WIDTH/2, HEIGHT/2));
		direction = KeyEvent.VK_UP;
		spawnFruit();
		gameOver = false;
	}
	
	private void spawnFruit() {
		Random rand = new Random();
		int x = rand.nextInt(WIDTH/DOT_SIZE) * DOT_SIZE;
		int y = rand.nextInt(HEIGHT/DOT_SIZE) * DOT_SIZE;
                fruit = new Point(x, y);
                }
	
        private void move() {
        Point head = snake.get(0);
        Point newHead = new Point(head.x, head.y);
        switch (direction) {
            case KeyEvent.VK_UP:
                newHead.y -= DOT_SIZE;
                if (newHead.y < 0) {
                    newHead.y = HEIGHT - DOT_SIZE;
                }
                break;
            case KeyEvent.VK_DOWN:
                newHead.y += DOT_SIZE;
                if (newHead.y >= HEIGHT) {
                    newHead.y = 0;
                }
                break;
            case KeyEvent.VK_LEFT:
                newHead.x -= DOT_SIZE;
                if (newHead.x < 0) {
                    newHead.x = WIDTH - DOT_SIZE;
                }
                break;
            case KeyEvent.VK_RIGHT:
                newHead.x += DOT_SIZE;
                if (newHead.x >= WIDTH) {
                    newHead.x = 0;
                }
                break;
        }
        if (newHead.equals(fruit)) {
            snake.add(0, newHead);
            spawnFruit();
            score++;
        } else if (snake.contains(newHead)) {
            gameOver = true;
            
        } else {
            snake.remove(snake.size() - 1);
            snake.add(0, newHead);
        }
    }


	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, this);
		if(gameOver) {
			g.setColor(Color.RED);
			g.drawString("GAME OVER", WIDTH/2-40, HEIGHT/2);
                        g.drawString("Score: " + score, WIDTH/2-30, HEIGHT/2+20);
		}
		else {
                    for(int i = 0; i < snake.size(); i++) {
                        Point p = snake.get(i);
                        if(i == 0) {
                            g.setColor(Color.BLUE); // Set head color to blue
                        }
                        else {
                            g.setColor(Color.GREEN); // Set body color to green
                        }
                        g.fillOval(p.x, p.y, DOT_SIZE, DOT_SIZE);
                    }
                    g.setColor(Color.RED);
                    g.fillOval(fruit.x, fruit.y, DOT_SIZE, DOT_SIZE);
               }
                
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!gameOver) {
			move();
			repaint();
		}else{
                    timer.stop();
                }
	}

        @Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
                switch(key) {
	case KeyEvent.VK_UP:
	case KeyEvent.VK_DOWN:
	case KeyEvent.VK_LEFT:
	case KeyEvent.VK_RIGHT:
		if(Math.abs(key-direction) != 2) {
			direction = key;
		}
		break;
	case KeyEvent.VK_ENTER:
		if(gameOver) {
			initGame();
		}
		break;
	}
}

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}

public static void main(String[] args) {
	JFrame frame = new JFrame("Snake Game");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setResizable(false);
	frame.add(new SnakeGame());
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }
}