import java.awt.Point;
import java.util.Stack;

import DataStructures.Queue;

public class p2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map m = new Map("src/TEST02.txt", false);
		m.load();
		m.printMaze();
		m.solver();
	
	}

}
