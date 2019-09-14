/**
 *  @author David Katz
 *  @author Daniel Sauer
 */
import java.awt.Image;
public class Pipe 
{
	//Initializes the objects needed for the pipes
	public int x, y, width=66, height=400, speed=3, margin=2; 
	public String position;
	private Image pipe;
	/**
	 * Sets the orientation of the pipes
	 * @param orientation
	 */
	public Pipe(String orientation) 
	{
		this.position = orientation; //Sets the orientation
		reset();
	}
	/**
	 * Updates the pipe
	 */
	public void update() 
	{
		x -= speed; //Sets a new value of x with speed subtracted
	}
	/**
	 * Checks if there is a collision
	 * @param xv The x location of the pipe
	 * @param yv The y location of the pipe
	 * @param widthv Width of the pipe
	 * @param heightv Height of the pipe
	 * @return
	 */
	public boolean collides(int xv, int yv, int widthv, int heightv) 
	{
		if (xv + widthv - margin > x && xv + margin < x + width) 
		{
			if (position.equals("south") && yv < y + height) 
			{
				return true;
			} 
			else if (position.equals("north") && yv + heightv > y) 
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * To reset the orientation of the pipe
	 */
	public void reset() 
	{     
		x = Menu.WIDTH + 2; //Sets a new value of x to 2 greater than the width of the menu
		if (position.equals("south")) 
		{
			y = -(int)(Math.random() * 120) - height / 2; //Sets a new y value if the pipe is facing downwards
		}
	}
	/**
	 * Sets and updates the image of the pipe when in motion
	 * @return r
	 */
	public Picture getRender() 
	{
		Picture r = new Picture(height, height, position); //Creates a new picture r
		r.x = x;
		r.y = y;
		if (pipe == null) //If there is no image, get the image
		{
			pipe = Picture.loadImage("img/pipe-" + position + ".png"); //Loads the image of the pipe
		}
		r.image = pipe; //Set the image to r
		return r; //Return the picture
	}
}