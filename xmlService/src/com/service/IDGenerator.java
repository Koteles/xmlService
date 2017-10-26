package com.service;


// Singleton class using Lazy Initialization

public class IDGenerator {

	/**

	 * This class generates automatically IDs for the students

	 *

	 */
	
	private static int id = 1;
	
	private static IDGenerator instance;
	
	private IDGenerator() {
		
	}
	
	public static IDGenerator getInstance() {
		if(instance == null) {
			instance = new IDGenerator();
		}
		return instance;
	}
	
	public int getId() {
		return id++;
	}

	public void setId(int id) {
		IDGenerator.id = id;
	}
}
