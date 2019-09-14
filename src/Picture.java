/**
 *  @author David Katz
 *  @author Daniel Sauer
 */
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
public class Picture 
{
	//Initializes the objects needed for the picture
	public int x;
	public int y;
	public Image image;
	public String imagePath;
	public AffineTransform transform; //Graphics for bird transformation
	private static HashMap<String, Image> cache = new HashMap<String, Image>(); //Sets values for images to be loaded into the game
	public Picture() {}
	/**
	 * Creates a new picture
	 * @param x The x location of the picture
	 * @param y The y location of the picture
	 * @param imagePath 
	 */
	public Picture(int x, int y, String imagePath) 
	{
		this.x = x;
		this.y = y;
		this.image = loadImage(imagePath); //Loads the image
	}   
	/**
	 * Loads the image for the picture
	 * @param path 
	 * @return image Returns the image
	 */
	public static Image loadImage(String path) 
	{
		Image image = null;
		if (cache.containsKey(path)) 
		{
			return cache.get(path);
		}
		try 
		{
			image = ImageIO.read(new File(path));
			if (!cache.containsKey(path)) 
			{
				cache.put(path, image);
			}
		} 
		//If it doesn't work do nothing
		catch (IOException e){}
		return image;
	}
}