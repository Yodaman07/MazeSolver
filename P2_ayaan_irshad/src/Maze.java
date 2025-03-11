import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
	
	private String path;
	private boolean coordBased;
	private String maze;
	
	public Maze(String filePath, boolean coordBased) {
		this.path = filePath;
		this.coordBased = coordBased;
	}
	
	
	public String loadMaze() {
		String m = "";
		try {
			File f = new File(this.path);
			Scanner s = new Scanner(f);
			while (s.hasNext()) {
				m += s.nextLine() + "\n";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.maze = m;
		return m;
	}
	
}
