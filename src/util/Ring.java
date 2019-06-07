package util;

import java.util.ArrayList;

public class Ring<T> extends ArrayList<T>{
	private static final long serialVersionUID = 1L;

	private int currentId = 0;
	
	public T current() {
		try {
			return get(currentId);
		}catch(Exception e) {
			return null;
		}
	}
	
	public T next() {
		try {
			if(currentId < size()-1) {
				currentId++;
			}else {
				currentId = 0;
			}
			return current();
		}catch(Exception e) {
			return null;
		}
	}
	
	public T prev() {
		try {
			if(currentId > 0) {
				currentId--;
			}else {
				currentId = size()-1;
			}
			return current();
		}catch(Exception e) {
			return null;
		}
	}
}
