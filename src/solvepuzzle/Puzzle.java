package solvepuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 *  NOTE: The file_in file has to be adjacent to the src folder in this Eclipse project, eg. not in src/solvepuzzle or inside
 *  src, as Puzzle.java will throw a FileNotFoundException if that happens
 */

public class Puzzle {
	
	private static String file_in = "puzzle.csv"; 
	private static Scanner my_scanner = new Scanner(System.in);
	private static Scanner file_scanner;
	private static int SIZE = 20;
	
	public static Letter left(Letter[][] table, Letter current){
		return table[current.getX()][current.getY() - 1];
	}
	
	public static Letter right(Letter[][] table, Letter current){
		return table[current.getX()][current.getY() + 1];
	}
	
	public static Letter up(Letter[][] table, Letter current){
		return table[current.getX() - 1][current.getY()];
	}
	
	public static Letter down(Letter[][] table, Letter current){
		return table[current.getX() + 1][current.getY()];
	}
	
	public static Letter topLeft(Letter[][] table, Letter current){
		return table[current.getX() - 1][current.getY() - 1];
	}
	
	public static Letter topRight(Letter[][] table, Letter current){
		return table[current.getX() - 1][current.getY() + 1];
	}
	
	public static Letter bottomLeft(Letter[][] table, Letter current){
		return table[current.getX() + 1][current.getY() - 1];
	}
	
	public static Letter bottomRight(Letter[][] table, Letter current){
		return table[current.getX() + 1][current.getY() + 1];
	}
	
	public static Letter[][] readFile(String file_in) throws FileNotFoundException{
		Letter[][] table = new Letter[SIZE][SIZE]; // 2D array to hold the table in .csv file
		String[] line_split = new String[SIZE]; // String to hold current line once it's split via commas
		String curr_line; // String to hold current line
		int counter = 0;
		File finp = new File(file_in); // File obj for scanner
		file_scanner = new Scanner(finp);
		while(file_scanner.hasNext()){ // check if there's anything there
			curr_line = file_scanner.next();
			line_split = curr_line.split(",");
			for(int i = 0; i < SIZE; i++){
				table[counter][i] = new Letter(counter,i,line_split[i]);
			}
			counter += 1;
		}
		return table;
	}
	
	public void searchWord(Letter[][] table, String word){
		String[] letters = word.split(""); // split the word into an arr of letter
		return;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// System.out.print("Enter the word you wish to find in " + file_in + ": ");
		// String word = my_scanner.next(); // take in user input

		Letter[][] table = readFile(file_in); // get table from file
		
		for(int i = 0 ; i < SIZE; i++){
			for(int j = 0 ; j < SIZE; j++){
				System.out.print(table[i][j].toStringWords() + "  ");
			}
			System.out.println();
		}
		
		Letter test = table[1][5];
		System.out.println(test.toString() + "\nLeft " + left(table, test).toString()
				+ "\nRight " + right(table, test).toString()
				+ "\nUp " + up(table, test).toString()
				+ "\nDown " + down(table, test).toString()
				+ "\nTop Left " + topLeft(table, test).toString()
				+ "\nTop Right " + topRight(table, test).toString()
				+ "\nBottom Left " + bottomLeft(table, test).toString()
				+ "\nBottom Right " + bottomRight(table, test).toString());
	}

}
