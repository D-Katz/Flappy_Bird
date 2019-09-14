/**
 *  @author David Katz
 *  @author Daniel Sauer
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable 
{
	//Initializes all game objects
	public static final int PIPE_DELAY = 100;
	private static Boolean paused;
	public static Boolean gameOver, start;
	public static int threadSleeper;
	public int score;
	private int pause, restart, pipe;
	private Flappy flappy;
	private ArrayList<Pipe> pipes;
	/**
	 * Creates the game
	 */
	public Game() 
	{
		restart();
		new Thread(this).start();
	}
	/**
	 * Updates the state of the game and runs the game
	 */
	public void update() 
	{
		start(); //Calls the method to watch for the start of the game
		if (!start) //If not started, game does not update
		{
			return;
		}
		pause(); //Calls the method to watch for if the game is paused
		reset(); //Calls the method to watch for if the game is reset
		if (paused) //If paused, game no longer updates
		{
			return;
		}
		flappy.act(); //Updates the bird
		if (gameOver) //If the game is over it does not update
		{
			return;
		}
		move(); //Calls method to move pipes
		collision(); //Checks for collision
		repaint();
		return;         
	}
	/**
	 * Sets the function of the game restarting
	 */
	public void restart() 
	{
		paused = false; //Unpauses
		start = false; //Makes sure the game doesn't start
		gameOver = false; //Game is not over
		score = 0; //Resets the score
		pause = 0; //Resets pause delay
		restart = 0; //Resets restart delay
		pipe = 0; //Resets pipe delay
		flappy = new Flappy(); //Created a new bird
		pipes = new ArrayList<Pipe>(); //Makes new pipes
	}

	/**
	 * Method for adding pictures and text to the game
	 */
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D gp = (Graphics2D) g; //Creates new 2D graphics for the bird
		for (Picture r : getRenders())
			if (r.transform != null)
			{
				gp.drawImage(r.image, r.transform, null);
			}
			else
			{
				gp.drawImage(r.image, r.x, r.y, null);
			}
		gp.setColor(Color.BLACK); //Sets the color to black
		gp.setFont(new Font("TimesRoman", Font.PLAIN, 25)); //Sets the font to TimesRoman size 20
		gp.drawString(Integer.toString(score), 10, 460);
		if(!start) //Creates the text for when the game hasn't started yet
		{
			gp.setFont(new Font("TimesRoman", Font.PLAIN, 20)); //Sets the font to TimesRoman size 20
			gp.drawString("Press SPACE to start", 150, 460); //Sets the text and location of the start string
		}
		else if (gameOver) //Creates text for when the game ends
		{
			gp.setFont(new Font("TimesRoman", Font.PLAIN, 20)); //Sets the font to TimesRoman size 20
			gp.drawString("Press R to restart or ESC for Main Menu", 100, 460); //Sets the text and location of the game over string
		}
	}
	/**
	 * Checks if the bird collides with a pipe
	 */
	private void collision() 
	{
		for (Pipe pipe : pipes)
		{
			if (pipe.collides(flappy.x, flappy.y, flappy.width, flappy.height)) //If the bird collides with a pipe
			{
				gameOver = true; //Ends the game
				flappy.alive = false; //Sets the state of the bird to not alive
				Highscore h = new Highscore();
				if (score > h.get()[4]) //If the current score is greater than the high score stored
				{
					h.set(score); //Sets the current score to be a new high score
				}
				h.display(); //Displays the current high scores
			} 
			else if (pipe.x == flappy.x && pipe.position.equalsIgnoreCase("south")) //If the bird does not collide and passes through the pipes
			{
				score++; //Increments the current score
			}
		}
		if (flappy.y + flappy.height > Menu.HEIGHT - 80) //If the bird hits the bottom of the window/the floor
		{
			gameOver = true; //Ends the game
			Highscore h = new Highscore();
			if (score > h.get()[4]) //If the current score is greater than the high score stored
			{
				h.set(score); //Sets the current score to be a new high score
			}
			h.display(); //Displays the current high scores
			flappy.y = Menu.HEIGHT - 80 - flappy.height; 
		}
	}
	/**
	 * Creates an array list to display the foreground and background pictures in game
	 * @return pictures
	 */
	public ArrayList<Picture> getRenders() 
	{
		ArrayList<Picture> pictures = new ArrayList<Picture>();
		pictures.add(new Picture(0, 0, "img/background.png"));
		for (Pipe pipe : pipes)
			pictures.add(pipe.getRender());
		pictures.add(new Picture(0, 0, "img/foreground.png"));
		pictures.add(flappy.render());
		return pictures;
	}
	/**
	 * Checks for when the player starts the game
	 */
	private void start() 
	{
		if (!start && Menu.keyPress == KeyEvent.VK_SPACE) //Starts the game when space is pressed
		{
			Menu.keyPress = 0;
			start = true;
		}
	}
	/**
	 * Checks for when the player pauses the game
	 */
	private void pause() 
	{
		if (pause > 0) //Reduces pause delay when greater than 0
			pause--; 
		if (Menu.keyPress == KeyEvent.VK_P && pause <= 0) //Pauses the game when p is pressed and when the delay is less than or equal to 0
		{
			Menu.keyPress = 0;
			paused = !paused;
			pause = 10; //Sets the pause delay to 10 when paused
		}
	}
	/**
	 * Moves the pipes in the game
	 */
	private void move() 
	{
		pipe--;

		if (pipe < 0) 
		{
			pipe = PIPE_DELAY; //Sets the pipe delay to 100
			Pipe northPipe = null;
			Pipe southPipe = null;
			// Look for pipes off the screen
			for (Pipe pipe : pipes) 
			{
				if (pipe.x - pipe.width < 0) 
				{
					if (northPipe == null) 
					{
						northPipe = pipe; //Sets the north pipe to pipe when not existent
					} 
					else if (southPipe == null) 
					{
						southPipe = pipe; //Sets the south pipe to pipe when not existent
						break;
					}
				}
			}
			if (northPipe == null) //Adds a north pipe if not already existing
			{
				Pipe pipe = new Pipe("north");
				pipes.add(pipe);
				northPipe = pipe;
			} 
			else 
			{
				northPipe.reset();
			}
			if (southPipe == null) //Adds a south pipe if not already existing
			{
				Pipe pipe = new Pipe("south");
				pipes.add(pipe);
				southPipe = pipe;
			} 
			else 
			{
				southPipe.reset();
			}
			northPipe.y = southPipe.y + southPipe.height + 175;
		}
		for (Pipe pipe : pipes) 
		{
			pipe.update(); //Updates the pipe
		}
	}
	/**
	 * Checks for when the player resets the game
	 */
	private void reset() 
	{
		if (restart > 0) //Reduces restart delay when greater than 0
			restart--;
		if (Menu.keyPress==KeyEvent.VK_R && restart <= 0) //Resets the game when r is pressed and when the delay is less than or equal to 0 
		{
			Menu.keyPress = 0;
			restart();
			restart = 10; //Sets the restart delay to 10 when paused
			return;
		}
		if (Menu.keyPress == KeyEvent.VK_ESCAPE && restart <= 0) //Resets the game when esc is pressed and when the delay is less than or equal to 0 
		{
			Menu.keyPress = 0; 
			Menu.MENUFRAME.setVisible(true);                        
			Menu.GAMEFRAME.setVisible(false);
			return;
		}
	}
	/**
	 * When the game is running
	 */
	public void run() 
	{
		try 
		{
			while (true) 
			{
				update(); //Update the game
				Thread.sleep(threadSleeper);
			}
		}
		catch (Exception e)
		{
		}
	}
}
