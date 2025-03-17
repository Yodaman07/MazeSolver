package DataStructures;

import java.util.ArrayList;

public class Stack<E> {
	
	//data storage used behind the scenes is an ArrayList
	
	private ArrayList<E> data;
	
	//constructor
	public Stack() {
		//create the ArrayList!!!!!!!! - Common AP Exam error :(
		data = new ArrayList<E>();
	}
	
	
	//add the element to the stack
	public void push (E element) {
		data.add(element); //Adds to the end aka the "top"
	}
	
	//pop from the Stack (removes and returns)
	//@return null if empty
	public E pop() {
		if (data.size() == 0) {return null;}
		return data.remove(data.size()-1);	
	}
	
	//return the top of the stack WITHOUT removing from the data structure
	public E peek() {
		if (data.size()==0){return null;}
		return data.get(data.size()-1);
	}
	
	//return the size of the stack
	public int size() {
		return data.size();
	}
	
	public String toString() {
		//return a String representation of the data
		return data.toString();		
	}
	
	public boolean empty() {
		if (data.size() == 0) {
			return true;
		}
		return false;
	}
	
}
