/**
 *  @author David Katz
 *  @author Daniel Sauer
 */
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
public class Highscore 
{
	//Initializes objects needed for high scores
	private static final File highscore = new File("FLAPPY_BIRD_HIGHSCORES.txt"); 
	private static int[] scores = new int[5];
	private static String[] names = new String[5];
	private static Font f = new Font("Times New Roman", Font.PLAIN, 20);
	/**
	 * Creates the new high score object
	 */
	public Highscore()
	{
		if (!highscore.exists())
		{
			try
			{
				//Creates the array to store the high scores
				highscore.createNewFile();
				names[0] = "Dimitri";
				names[1] = "Vasya";
				names[2] = "Slavik";
				names[3] = "Vladim";
				names[4] = "Andrey";
				//Sets the scores
				for (int i = 0; i < 5; i++)
				{
					scores[i] = 50-i*10;
				}
				writeScore();
			}
			//If it doesn't work, return the message
			catch(IOException e)
			{
				System.out.println("Couldn't create the file.");
			}
		}
		UIManager.put("OptionPane.background", Color.GRAY); //Make the background of the pane gray
		UIManager.put("OptionPane.messageForeground", Color.CYAN); //Make the color of the message cyan
		UIManager.put("OptionPane.font", f); //Sets the font of the message
		UIManager.put("Panel.background",Color.GRAY); // Sets the color of the background to gray
	}
	/**
	 * Writes the names of the players and their scores in the array
	 */
	private static void writeScore()
	{
		try
		{
			//Writes the names of the players and their scores
			FileWriter f = new FileWriter(highscore);
			BufferedWriter b = new BufferedWriter(f);
			b.write(names[0]);
			b.newLine();
			b.write(names[1]);
			b.newLine();
			b.write(names[2]);
			b.newLine();
			b.write(names[3]);
			b.newLine();
			b.write(names[4]);
			b.newLine();
			b.write(Integer.toString(scores[0]));
			b.newLine();
			b.write(Integer.toString(scores[1]));
			b.newLine();
			b.write(Integer.toString(scores[2]));
			b.newLine();
			b.write(Integer.toString(scores[3]));
			b.newLine();
			b.write(Integer.toString(scores[4]));
			b.close();
			f.close();
		}
		//If it doesn't work, return the message
		catch (IOException ex)
		{
			System.out.println("Couldn't write to the file.");
		}
	}
	/**
	 * Gets the scores of the players, compares them to and replaces high scores if greater
	 * @param score Sets the score
	 */
	public void set(int score)
	{
		read();
		String name = null;
		String tempName = new String();
		int tempScore;
		//Prompts player for their name to send to high score list if it is greater
		while (name == null || name.length()<1)
		{
			name = JOptionPane.showInputDialog(null, "You might have gotten a high score! Enter your name:", "Game Over", JOptionPane.PLAIN_MESSAGE);
			if (name == null) return;
		}
		name = name.trim();
		//Compares the player's score to high scores
		if (score>scores[3])
		{
			for (int i = 3; i>=0; i--)
			{
				if (score>scores[i])
				{
					tempName = names[i];
					names[i] = name;
					names[i+1] = tempName;
					tempScore = scores[i];
					scores[i] = score;
					scores[i+1] = tempScore;
				}
			}
		}
		else if (score>scores[4])
		{
			names[4] = name;
			scores[4] = score;
		}
		writeScore();
	}
	/**
	 * Reads the current high scores and the players they belong to
	 */
	private void read()
	{
		try
		{
			FileReader f = new FileReader(highscore);
			BufferedReader b = new BufferedReader(f);
			names[0] = b.readLine();
			names[1] = b.readLine();
			names[2]= b.readLine();
			names[3] = b.readLine();
			names[4] = b.readLine();
			scores[0] = Integer.parseInt(b.readLine());
			scores[1] = Integer.parseInt(b.readLine());
			scores[2] = Integer.parseInt(b.readLine());
			scores[3] = Integer.parseInt(b.readLine());
			scores[4] = Integer.parseInt(b.readLine());
			b.close();
			f.close();
		}
		//If it doesn't work, return the message
		catch (IOException ex)
		{
			System.out.println("Couldn't read the file.");
		}
	}
	/**
	 * For displaying the high scores
	 */
	public void display()
	{
		read(); //Calls readScore to get the scores
		String[] columns = {"Name", "Score"}; //Makes the array to display the scores
		//Sets the data to be displayed
		String[][] data = 
			{
					{names[0], Integer.toString(scores[0])},
					{names[1], Integer.toString(scores[1])},
					{names[2], Integer.toString(scores[2])},
					{names[3], Integer.toString(scores[3])},
					{names[4], Integer.toString(scores[4])}
			};
		JTable table = new JTable(data, columns); //Makes a table to display the array with the data
		table.setBackground(Color.GRAY); //Sets the background of the table to gray
		table.setRowHeight(20); //Sets the height of the rows in the table
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setFont(f); //Sets the font in the table
		table.setForeground(Color.CYAN); //Sets the color of the foreground to cyan
		table.setEnabled(false);
		JOptionPane.showMessageDialog(null, table, "High Scores", JOptionPane.PLAIN_MESSAGE); //Sets the description of the table
	}
	/**
	 * A score getter
	 * @return scores
	 */
	public int[] get()
	{
		return scores;
	}
	/**
	 * Creates a new high score
	 * @param args
	 */
	public static void main (String[] args)
	{
		new Highscore();
	}
}