package solvepuzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 *  NOTE: The file_in file has to be adjacent to the src folder in this Eclipse project, eg. not in src/solvepuzzle or inside
 *  src, as Puzzle.java will throw a FileNotFoundException if that happens
 */

public class Puzzle extends JFrame{	
	private static String file_in = "puzzle.csv"; 
	private static Scanner my_scanner = new Scanner(System.in);
	private static Scanner file_scanner;
	private static int SIZE = 20;
		
	public static boolean isValidCoord(Letter letter){
		return letter.getY() >= 0 && letter.getY() < SIZE 
				&& letter.getX() >= 0 && letter.getX() < SIZE;
	} // serves to make sure we don't tip over the table boundaries
	
	public static Letter[] searchLetter(Letter[][] table, char[] word_arr, Letter letter, 
			int direction, int word_idx, String endWord, Letter[] collected_coords){
		Letter endLetter;
		boolean is_begin = false;
		
		if(word_idx == word_arr.length - 1){ // base case to make sure we didn't hit the end
			for(int i = 0; i < endWord.length(); i++){
				System.out.println(collected_coords[i].toString());
			}
			return collected_coords;
		}
		
		if(isValidCoord(letter) && word_arr[word_idx] == letter.getLetter()){
			switch(direction){
			case 0: 
				if(letter.getY() - 1 >= 0) 
					endLetter = table[letter.getX()][letter.getY() - 1]; // left
				else endLetter = letter; break;
			case 1: 
				if(letter.getY() + 1 < SIZE) 
					endLetter = table[letter.getX()][letter.getY() + 1]; // right
				else endLetter = letter; break;
			case 2: 
				if(letter.getX() - 1 >= 0) 
					endLetter = table[letter.getX() - 1][letter.getY()]; // up
				else endLetter = letter; break;
			case 3: 
				if(letter.getX() + 1 < SIZE) 
					endLetter = table[letter.getX() + 1][letter.getY()]; // down
				else endLetter = letter; break;
			case 4: 
				if(letter.getX() - 1 >= 0 && letter.getY() - 1 >= 0) 
					endLetter = table[letter.getX() - 1][letter.getY() - 1]; // top left
				else endLetter = letter; break;
			case 5: 
				if(letter.getX() - 1 >= 0 && letter.getY() + 1 < SIZE) 
					endLetter = table[letter.getX() - 1][letter.getY() + 1]; // top right
				else endLetter = letter; break;
			case 6: 
				if(letter.getX() + 1 < SIZE && letter.getY() - 1 >= 0) 
					endLetter = table[letter.getX() + 1][letter.getY() - 1]; // bottom left
				else endLetter = letter; break;
			default: 
				if(letter.getX() + 1 < SIZE && letter.getY() + 1 < SIZE) 
					endLetter = table[letter.getX() + 1][letter.getY() + 1]; // bottom right
				else endLetter = letter; break;
			}
			endWord += endLetter.getLetter();
			if(word_idx == 0){
				collected_coords[0] = letter;
				collected_coords[1] = endLetter;
				is_begin = true;
			}
			if(!is_begin) collected_coords[word_idx + 1] = endLetter;
			/*
			System.out.println("Direction " + direction + " "+ letter.toString() 
			+ "\n\t" + endLetter.toString() + "\n\t\t" + endWord + " at word_idx " + word_idx);
			*/
			return searchLetter(table, word_arr, endLetter, direction, word_idx + 1, 
					endWord, collected_coords);
		}
		return collected_coords;
	}
	
	public static Letter[] searchWord(Letter[][] table, String word){
		char[] letters = word.toCharArray(); // split the word into an arr of letter
		Letter[] collected_coords = new Letter[word.length()];
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				if(letters[0] == table[i][j].getLetter()){
					//System.out.println("Table[" + i + "][" + j +"] has letter " + letters[0]);
					for(int k = 0; k < 8; k++){
						boolean is_null = false;
						collected_coords = searchLetter(table, letters, table[i][j], k, 0, 
								Character.toString(letters[0]), collected_coords);
						for(int m = 0; m < collected_coords.length; m++){
							if(collected_coords[m] == null) {
								is_null = true;
							}
						}
						if(is_null == false) {
							return collected_coords;
						}
					}
				}
			}
		}
		System.out.println("Input given: " + word + "\nWord not found in supplied table.");
		return null;
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
	
	public static void main(String[] args)throws FileNotFoundException {	
		Letter[][] table = readFile(file_in); // get table from files
		
		String[] words = new String[4];
		
		System.out.print("Enter the word you wish to find in " + file_in + ": ");
		words[0] = my_scanner.next(); // take in user input
		
		for(int i = 1; i < 4; i++){
			System.out.print("Enter the next word you wish to find in " + file_in + ": ");
			words[i] = my_scanner.next(); // take in user input
		}
		JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(20, 200));
        grid.setVisible(true);
        
		Letter[] collected_coords;
		 
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
            	JLabel label = new JLabel("  " + Character.toString(table[i][j].toStringWords()) 
            			+ " ");
            	label.setFont(new Font("Serif", Font.BOLD, 20));
            	
            	for(int m = 0; m < 4; m++){
            		collected_coords = searchWord(table, words[m]);
            		if(collected_coords != null){
            			for(int k = 0; k < collected_coords.length; k++){
                    		if(collected_coords[k].getX() == i && collected_coords[k].getY() == j){
                    			System.out.println("MATCH");
                    			label.setOpaque(true);
                    			if(m == 0) label.setBackground(Color.RED);
                    			if(m == 1) label.setBackground(Color.GREEN);
                    			if(m == 2) label.setBackground(Color.ORANGE);
                    			if(m == 3) label.setBackground(Color.YELLOW);
                    		}
                    	}
                        grid.add(label);
            		}
        		}
            }
            grid.add(new JLabel("\n"));
        }
        
        JFrame frame = new JFrame("Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(grid);
        frame.pack();
        frame.setVisible(true);
        
	}
}
