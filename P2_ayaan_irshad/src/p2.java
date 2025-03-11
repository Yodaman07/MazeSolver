import java.util.Stack;

public class p2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Maze m = new Maze("src/TEST01.txt", false);
		m.loadMaze();
		m.printMaze();
//		m.solver();
//		System.out.println(m.loadMaze());
	}

}
