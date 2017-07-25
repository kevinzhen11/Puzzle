package solvepuzzle;

public class Letter {
	private int x, y;
	private String letter;
	
	public Letter(int x, int y, String letter){
		this.x = x;
		this.y = y;
		this.letter = new String(letter);
	}
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public String getLetter() { return this.letter;} 
	
	public String toString(){
		return this.letter + ": (" + this.x + "," + this.y + ")";
	}
	
	public String toStringCoords(){
		return "(" + this.x + "," + this.y + ")";
	}
	
	public String toStringWords(){
		return this.letter;
	}
	
}
