import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import DataStructures.Queue;

public class Map {
	
	private String path;
	private boolean coordBased;
	private char[][][] maze; //3d array to hold multiple rooms
	
	private int rows;
	private int cols;
	private int levels;
	
	public Map(String filePath, boolean coordBased) {
		this.path = filePath;
		this.coordBased = coordBased;
	}
	
	
	public void load() throws IncorrectMapFormatException, IncompleteMapException, IllegalMapCharacterException {
		try {
			File f = new File(this.path);
			Scanner s = new Scanner(f);
			
			String[] data = s.nextLine().split(" ");
			try {
				this.rows = Integer.valueOf(data[0]);
				this.cols = Integer.valueOf(data[1]);
				this.levels = Integer.valueOf(data[2]);
				this.maze = new char[this.rows][this.cols][this.levels];
			}catch(Exception e) {
				throw new IncorrectMapFormatException("The first line must contain 3 positive numbers");
			}
//			System.out.println(rows + ":" + cols + ":" + levels);
			
			if (!coordBased) { //loading regular map based mazes
				try {
					for (int l = 0; l < levels; l++) {
						int r = 0;
						while (s.hasNextLine() && r < this.rows) {
							String line = s.nextLine(); 
							for (int i = 0; i < this.cols; i++) {
								if (!validChar(line.charAt(i))) {throw new IllegalMapCharacterException("Your map includes an illegal character. You can only use:.@$W|");}
								this.maze[r][i][l] = line.charAt(i);
							}
							r++;
						}
					}
				}catch(Exception e) {
					throw new IncompleteMapException("You do not have as many columns/rows as the first line said you have. The first line format is rowNum, columnNum, levelNum");
				}
				this.printMaze();
			}else { //loading coordinate based mazes
				while (s.hasNextLine()) {
					char elmn = s.next().toCharArray()[0]; //only 1 char
					int row = s.nextInt();
					int col = s.nextInt();
					int lvl = s.nextInt();
					if (!validChar(elmn)) {throw new IllegalMapCharacterException("Your map includes an illegal character. You can only use:.@$W|");}
					if (!inBounds(new Point(row, col))) {throw new IncompleteMapException("Your coordinates don't fit in the map bound");}
					System.out.println(elmn + " " + row + " " + col + " " + lvl);
					this.maze[row][col][lvl] = elmn;
				}
				
				for (int l = 0; l <this.levels; l++) {
					
					for (int r = 0; r < this.rows; r++) {
						for (int c = 0; c < this.cols; c++) {
							if (this.maze[r][c][l] == '\u0000') {this.maze[r][c][l] = '.';} // \u0000 is ascii null value						
						}
					}
					
				}
				
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void printMaze() {
		if (this.maze == null) {System.out.println("You need to load a maze");}
		
		for (int l = 0; l <this.levels; l++) {
			for (int r = 0; r < this.rows; r++) {
				for (int c = 0; c < this.cols; c++) {
					System.out.print(this.maze[r][c][l]);
					
				}
				System.out.println("");
			}
		}
	}
	
	boolean inBounds(Point p) { //checks to see if a specified point is in the bounds of the maze
//		System.out.println(p);
		// x is rows, y is columns
		return ((p.x < this.getRows()) && (0 <= p.x) && (p.y < this.getCols()) && (0 <= p.y));
	}
	public char[][][] getMaze(){return this.maze;}
	public boolean isCoordBased() { return coordBased; }
	public int getRows() { return rows; }
	public int getCols() { return cols; }
	public int getLevels() { return levels; }
	
	public boolean validChar(char c) {
		char[] valid = {'.','$','W','@','|'};
		for (char a: valid) {
			if (c == a) {
				return true;
			}
		}
		
		return false;
	}
	
}


class IllegalMapCharacterException extends Exception
{
      // Constructor that accepts a message
      public IllegalMapCharacterException(String message)
      {
         super(message);
      }
 }



class IncompleteMapException extends Exception
{
      // Constructor that accepts a message
      public IncompleteMapException(String message)
      {
         super(message);
      }
 }

class IncorrectMapFormatException extends Exception
{
      // Constructor that accepts a message
      public IncorrectMapFormatException(String message)
      {
         super(message);
      }
 }

