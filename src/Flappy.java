/**
 *  @author David Katz
 *  @author Daniel Sauer
 */
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
public class Flappy 
{
	//Initializes objects needed to create the Bird
	public int x, y, width, height;
	public boolean alive;
	public double yVel, gravity;
	private int jDelay;
	private double rotate;
	private Image Bird;
	/**
	 * Creates a bird object with specified x and y position & specified sizes 
	 */
	public Flappy() 
	{
		//sets the properties of the bird
		x = 100;
		y = 150;
		yVel = 0;
		width = 45;
		height = 32;
		gravity = 0.5;
		jDelay = 0;
		rotate = 0.0;
		alive = true;
	}
	/**
	 * Updates the y velocity of the bird and jump delay
	 */
	public void act()
	{
		yVel += gravity;
		//Resets the jump delay to when greater than 0
		if (jDelay > 0)
		{
			jDelay--;
		}
		//Sets the vertical velocity and jump delay of the bird after a jump
		if (alive == true && (Menu.keyPress == KeyEvent.VK_SPACE) && jDelay <= 0) 
		{
			Menu.keyPress = 0;
			yVel = -10;
			jDelay = 10;
		}
		y += (int)yVel;
	}
	/**
	 * Sets and updates the image of the bird when in motion
	 * @return r
	 */
	public Picture render() 
	{
		Picture r = new Picture(); //Creates a new picture
		r.x = x;
		r.y = y;
		//Loads and sets the bird's image
		if (Bird == null) 
		{
			Bird = Picture.loadImage("img/bird.png");
		}
		r.image = Bird;
		rotate = (90 * (yVel + 20) / 20) - 90;
		rotate = rotate * Math.PI / 180;
		//Rotates the Bird
		if (rotate > Math.PI / 2)
		{
			rotate = Math.PI / 2;
		}
		r.transform = new AffineTransform(); //Creates a new graphical transformation
		r.transform.translate(x + width / 2, y + height / 2); //Moves the picture
		r.transform.rotate(rotate); //Rotates the picture
		r.transform.translate(-width / 2, -height / 2); //Moves the picture
		return r; //Returns the graphic
	}
}