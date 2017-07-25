package solvepuzzle;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 *  NOTE: The file_in file has to be adjacent to the src folder in this Eclipse project, eg. not in src/solvepuzzle or inside
 *  src, as Puzzle.java will throw a FileNotFoundException if that happens
 */

public class Puzzle extends JFrame{
	
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
	
	public static boolean isValidCoord(Letter letter){
		return letter.getY() >= 0 && letter.getY() < SIZE 
				&& letter.getX() >= 0 && letter.getX() < SIZE;
	} // serves to make sure we don't tip over the table boundaries
	
	public static boolean searchLetter(Letter[][] table, char[] word_arr, Letter letter, 
			int direction, int word_idx, int start_x, int start_y, String endWord){
		Letter endLetter;
		if(word_idx == word_arr.length - 1){ // base case to make sure we didn't hit the end
			System.out.println("Initial: (" + start_x + "," + start_y + ")");
			return true;
		}
		if(isValidCoord(letter) && word_arr[word_idx] == letter.getLetter()){
			switch(direction){
			case 0: endLetter = left(table, letter); break;
			case 1: endLetter = right(table, letter); break;
			case 2: endLetter = up(table, letter); break;
			case 3: endLetter = down(table, letter); break;
			case 4: endLetter = topLeft(table, letter); break;
			case 5: endLetter = topRight(table, letter); break;
			case 6: endLetter = bottomLeft(table, letter); break;
			default: endLetter = bottomRight(table, letter); break;
			}
			endWord += endLetter.getLetter();
			System.out.println("Direction " + direction + " "+ letter.toString() 
			+ "\n\t" + endLetter.toString() + "\n\t\t" + endWord + " at word_idx " + word_idx);
			return searchLetter(table, word_arr, endLetter, direction, word_idx + 1, 
					start_x, start_y, endWord);
		}
		return false;
	}
	
	public static void searchWord(Letter[][] table, String word){
		char[] letters = word.toCharArray(); // split the word into an arr of letter
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				if(letters[0] == table[i][j].getLetter()){
					//System.out.println("Table[" + i + "][" + j +"] has letter " + letters[0]);
					for(int k = 1; k < 8; k++){
						if(searchLetter(table, letters, table[i][j], k, 0, 
								i, j, Character.toString(letters[0]))) return;
					}
				}
			}
		}
		System.out.println("Word not found in supplied table.");
		return;
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
				table[counter][i] = new Letter(counter,i,line_split[i].charAt(0));
			}
			counter += 1;
		}
		return table;
	}
	
	public static JTextArea displayTable(Letter[][] table, JTextArea textArea){
		for(int i = 0 ; i < SIZE; i++){
			for(int j = 0 ; j < SIZE; j++){
				textArea.append(table[i][j].toStringWords() + "  ");
			}
			textArea.append("\n");
		}
		return textArea;
	}
	
	public static void main(String[] args)throws FileNotFoundException {	
		Letter[][] table = readFile(file_in); // get table from file
		
		// System.out.print("Enter the word you wish to find in " + file_in + ": ");
		// String word = my_scanner.next(); // take in user input
		
		String word = "albany";	
		searchWord(table, word);
		
		JTextArea textArea = new JTextArea(SIZE, SIZE);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		textArea.setEditable(false);
		textArea.setFont(new Font("Serif", Font.BOLD, 16));
		
		Puzzle main_gui = new Puzzle();
		main_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_gui.setSize(500,500);
		main_gui.setVisible(true);
		
	}
}
