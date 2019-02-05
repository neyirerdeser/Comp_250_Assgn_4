package assignment4Game;

import java.io.*;

public class Game {
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;
		
		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;
		
		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}
	
	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
		boolean invalidSpace = true;
		System.out.print("Player "+player+", chose a column to play in: ");
		while(invalidSpace) {
			try {
					int column = Integer.parseInt(keyboard.readLine());
					if(column<0 || column>6) {
						System.out.println("the column "+column+" does not exist, please choose between 0-6: ");
						continue;
					}
					if(c.available[column]>5) {
						System.out.print("the column "+column+" is already full, please choose another one: ");
						continue;
					}
					invalidSpace = false;
					c.addDisk(column,player);
					return column;
			} catch(IOException e) {System.out.println("problem occoured");}
		}
		return -1; // test return
	}
	
	public static int firstMovePlayer1 (){
		return 3;
	}
	
	public static int movePlayer1 (int columnPlayed2, Configuration c){
		int moveToWin = c.canWinNextRound(1);
		if(moveToWin>-1) c.addDisk(moveToWin,1);
		else {
			moveToWin = c.canWinTwoTurns(1);
			if(moveToWin>-1) c.addDisk(moveToWin,1);
			else {
				for(int i=0; i<13; i++) {
					int difference = 0;
					if(i%2==0) difference = i/2;
					else difference = -((i+1)/2);
					moveToWin = columnPlayed2+difference;
					if(moveToWin>-1 || moveToWin<7) {
						c.addDisk(moveToWin,1);
						break;
					}
				}
			}
		}
		return moveToWin; // DON'T FORGET TO CHANGE THE RETURN
	}
	
}
