package solvepuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 *  NOTE: The file_in file has to be adjacent to the src folder in this Eclipse project, as in not in src/solvepuzzle or inside
 *  src, as Puzzle.java will throw a FileNotFoundException
 */

public class Puzzle {
	
	public static String file_in = "puzzle.csv"; 
	public static Scanner my_scanner = new Scanner(System.in);
	private static Scanner file_scanner;
	static int SIZE = 20;
	
	public static String[][] readFile(String file_in) throws FileNotFoundException{
		String[][] table = new String[SIZE][SIZE]; // 2D array to hold the table in .csv file
		String[] line_split = new String[SIZE]; // String to hold current line once it's split via commas
		String curr_line; // String to hold current line
		int counter = 0;
		File finp = new File(file_in); // File obj for scanner
		file_scanner = new Scanner(finp);
		while(file_scanner.hasNext()){ // check if there's anything there
			curr_line = file_scanner.next();
			line_split = curr_line.split(",");
			for(int i = 0; i < SIZE; i++){
				table[counter][i] = line_split[i];
			}
			counter += 1;
		}
		return table;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.print("Enter the word you wish to find in " + file_in + ": ");
		String word = my_scanner.next(); // take in user input

		String[][] table = readFile(file_in); // get table from file
		
	}

}
