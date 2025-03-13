import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import DataStructures.Queue;

class MapTester {

	@Test
	void testContains() { //test the contains method in the Map Class 
		Map m = new Map("src/TEST01.txt", false);

		Queue<Point> q = new Queue<Point>();
		q.enqueue(new Point(1,2));
		q.enqueue(new Point(3,4));
		q.enqueue(new Point(5,6));
		
//		System.out.println(q.toString());
		String str1 = q.toString();
		assertTrue(m.contains(q, new Point(1,2)) == true); //Edge cases
		String str2 = q.toString();
		assertTrue(str1.equals(str2));
		
		assertTrue(m.contains(q, new Point(3,4)) == true); //normal case
		assertTrue(m.contains(q, new Point(5,6)) == true); //Edge cases
		
		assertTrue(str1.equals(q.toString()));
	}
	
	@Test
	void testRemove() { //test the remove method from the Map Class
		Map m = new Map("src/TEST01.txt", false);

		Queue<Point> q = new Queue<Point>();
		q.enqueue(new Point(1,2));
		q.enqueue(new Point(3,4));
		q.enqueue(new Point(5,6));
		
		m.remove(q, new Point(1,2)); 
//		System.out.println(q.toString()); //middle elmn
		assertTrue(q.toString().equals("[java.awt.Point[x=3,y=4], java.awt.Point[x=5,y=6]]"));
		
		q.dequeue();q.dequeue(); // --------

		q.enqueue(new Point(1,2));
		q.enqueue(new Point(3,4));
		q.enqueue(new Point(5,6));
		
		m.remove(q, new Point(3,4));
//		System.out.println(q.toString()); //edge case
		assertTrue(q.toString().equals("[java.awt.Point[x=1,y=2], java.awt.Point[x=5,y=6]]"));
		
	
		q.dequeue();q.dequeue(); // --------
		
		q.enqueue(new Point(1,2));
		q.enqueue(new Point(3,4));
		q.enqueue(new Point(5,6));
		
		m.remove(q, new Point(5,6));
//		System.out.println(q.toString()); // edge case
		assertTrue(q.toString().equals("[java.awt.Point[x=1,y=2], java.awt.Point[x=3,y=4]]"));
		
		m.remove(q, new Point(1,1));
		assertTrue(q.toString().equals("[java.awt.Point[x=1,y=2], java.awt.Point[x=3,y=4]]"));
	}

}
