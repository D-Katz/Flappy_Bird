/**
 *  @author David Katz
 *  @author Daniel Sauer
 */
import java.awt.Color;
import java.awt.Font;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener
{
	//Creates and initializes all the objects to create the menu
	final public static int WIDTH = 500, HEIGHT = 500;
	final static JButton START =  new JButton("START");
	final static JButton RULES =  new JButton("RULES");
	final static JButton EXIT = new JButton("EXIT");
	final static JButton INSANE = new JButton("INSANE");
	final static JButton HIGHSCORE = new JButton("HIGHSCORES");
	final public static JFrame MENUFRAME = new JFrame("Flappy Bird Menu");
	final public static JFrame GAMEFRAME = new JFrame("Flappy Bird");
	public static Game FlappyBird = new Game();
	public Highscore highScore = new Highscore();
	public static int keyPress;
	/**
	 * Creates a new menu object
	 */
	public Menu() 
	{
		START.setFont(new Font("Times New Roman", Font.BOLD, 15)); //Sets the font of the start button
		START.setForeground(Color.CYAN); //Sets the foreground of the start button
		START.setBounds(40,10,100,100); //Sets the location of the start button
		START.addActionListener(this);//Adds an action listener to the button
		EXIT.setFont(new Font("Times New Roman", Font.BOLD, 15));
		EXIT.setForeground(Color.RED); //Sets the foreground of the exit button
		EXIT.setBounds(40,350,100,100); //Sets the location of the exit button
		EXIT.addActionListener(this);//Adds an action listener to the button
		RULES.setFont(new Font("Times New Roman", Font.BOLD, 15));
		RULES.setForeground(Color.MAGENTA); //Sets the foreground of the rules button
		RULES.setBounds(350,350,100,100); //Sets the location of the rules button
		RULES.addActionListener(this);//Adds an action listener to the button
		INSANE.setFont(new Font("Times New Roman", Font.BOLD, 15));
		INSANE.setForeground(Color.BLACK); //Sets the foreground of the insane mode button
		INSANE.setBounds(350,10,100,100); //Sets the location of the insane mode button
		INSANE.addActionListener(this);//Adds an action listener to the button
		HIGHSCORE.setFont(new Font("Times New Roman", Font.BOLD, 10));
		HIGHSCORE.setForeground(Color.GRAY); //Sets the foreground of the high scores button
		HIGHSCORE.setBounds(175,400,150,50); //Sets the location of the high score button
		HIGHSCORE.addActionListener(this);//Adds an action listener to the button
		//Loads the images for the Menu
		try {
			BufferedImage image=ImageIO.read(getClass().getResourceAsStream("menu.png"));
			ImageIcon icon=new ImageIcon(image);
			MENUFRAME.setContentPane(new JLabel(icon));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}   
		MENUFRAME.setLayout(null); //Sets the layout of the menu frame
		MENUFRAME.setSize(HEIGHT,WIDTH); //Sets the size of the menu frame
		MENUFRAME.setResizable(false); //Makes it so the window cannot be resized
		//Adds the buttons
		MENUFRAME.add(START); 
		MENUFRAME.add(EXIT);
		MENUFRAME.add(RULES);
		MENUFRAME.add(INSANE);
		MENUFRAME.add(HIGHSCORE);
		MENUFRAME.setVisible(true); //Makes the window visible
		MENUFRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MENUFRAME.setLocationRelativeTo(null);
		//Sets the attributes of the game window
		GAMEFRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GAMEFRAME.setSize(WIDTH, HEIGHT);
		GAMEFRAME.setLocationRelativeTo(null);
		GAMEFRAME.addKeyListener(new KeyListener() //adds a key listener 
				{
			public void keyPressed(KeyEvent e) 
			{
				keyPress=e.getKeyCode();
			}
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
				});	
	}
	/**
	 * Makes actions for each button
	 */
	public void actionPerformed(ActionEvent e) 
	{
		//Sets the action for the start button
		if (e.getSource() == START) 
		{	
			MENUFRAME.setVisible(false);			
			GAMEFRAME.setVisible(true);
			GAMEFRAME.setResizable(false);
			GAMEFRAME.add(FlappyBird);
			Game.threadSleeper=25;
		}
		//Sets the action for the rules button
		else if (e.getSource() == RULES) 
		{
			//Displays the rules of the game
			JOptionPane.showMessageDialog(this, "HOW TO PLAY:"
					+ "\nTO START THE GAME PRESS SPACE"
					+ "\nGET YOUR BIRD TO FLY THROUGH THE PIPES BY PRESSING SPACE TO MAKE IT FLAP!"
					+ "\nEVERY PIPE YOU GO THROUGH YOU GET A POINT!"
					+ "\nTOUCH A PIPE OR THE GROUND AND YOU DIE!"
					+ "\nGET THE HIGHEST SCORE AMONGST YOUR FRIENDS!"
					+ "\nPRESS P TO PAUSE"
					+ "\nWHEN YOU DIE PRESS R TO RESTART"
					+ "\nPRESS ESC TO GO BACK TO THE MENU");
		} 
		//Sets the action for the insane mode button
		else if (e.getSource() == INSANE) 
		{
			MENUFRAME.setVisible(false);			
			GAMEFRAME.setVisible(true);
			GAMEFRAME.setResizable(false);
			GAMEFRAME.add(FlappyBird);
			Game.threadSleeper=10;
		}
		//Sets the action for the exit button
		else if (e.getSource() == EXIT) 
		{
			System.exit(0);
		}
		//Sets the action for the high scores button
		else if (e.getSource() == HIGHSCORE) 
		{
			highScore.display(); // opens high scores pop up
		}
	}
	/**
	 * Creates a new Menu
	 * @param args
	 */
	public static void main(String[] args) 
	{
		new Menu();
	}
}