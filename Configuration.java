package assignment4Game;

public class Configuration {
	
	public int[][] board;
	public int[] available;
	boolean spaceLeft;
	
	public Configuration(){
		board = new int[7][6];
		available = new int[7];
		spaceLeft = true;
	}
	
	public void print(){
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++){
			System.out.print("|");
			for (int j = 0; j < 7; j++){
				if (board[j][5-i] == 0){
					System.out.print("   |");
				}
				else{
					System.out.print(" "+ board[j][5-i]+" |");
				}
			}
			System.out.println();
		}
	}
	/*
		I'll be looking at the board as a matrix,
		where the normal [i][j] format is slightly different
		as i is the column and j is the height
		[0][5] [1][5] [2][5] ....
		[0][4] [1][4]  ...
		[0][3]   .
		   .     .
		   .     .
		   .
	*/
	public void addDisk (int index, int player){
		if(available[index]>4) return; // this should be >5 logically, but then it gives run-time error
		board[index][available[index]] = player;
		available[index]++;
		if(available[index]==6)	checkSpace();
	}

	private void checkSpace() {
		for(int i=0; i<board.length; i++)
			if(available[i]<6)
				return;
		spaceLeft = false;
	}
	
	public boolean isWinning (int lastColumnPlayed, int player){
		int i = lastColumnPlayed;
		int j = available[lastColumnPlayed]-1;
		return diagonal(i,j,player) || horizontal(i,j,player) || vertical(i,j,player);
	}
	/*
	has 5 helper methods
	*/
	private boolean diagonal(int i, int j, int p) {
		if(j<3) return false;
		return posSlope(i,j,p)||negSlope(i,j,p);
	}

	private boolean posSlope(int i, int j, int p) {
		int count = 0;
		int ti = i; // temporary i variable
		// DOWN-SIDE
		for(int k=j; k>=0; j--) {
			if(board[ti][k]!=p) break;
			count++;
			ti--;
			if(ti<0) break;
		}
		// reset ti
		ti = i;
		// UP-SIDE
		for(int k=j; k<6; k++) {
			if(board[ti][k]!=p) break;
			count++;
			ti++;
			if(ti>6) break;
		}
		// TOTAL
		return count>=4;
	}

	private boolean negSlope(int i, int j, int p) {
		int count = 0;
		int ti = i; // temporary i variable
		// DOWN-SIDE
		for(int k=j; k>=0; j--) {
			if(board[ti][k]!=p) break;
			count++;
			ti++;
			if(ti>6) break;
		}
		// reset ti
		ti = i;
		// UP-SIDE
		for(int k=j; k<6; k++) {
			if(board[ti][k]!=p) break;
			count++;
			ti--;
			if(ti<0) break;
		}
		// TOTAL
		return count>=4;
	}

	private boolean horizontal(int i, int j, int p) {
		int count = 0;
		// FORWARD
		for(int k=i; k<6; j++) {
			if(board[k][j]!=p) break;
			count++;
		}
		// BACKWARD
		for(int k=i; k>=0; k--) {
			if(board[k][j]!=p) break;
			count++;
		}
		// TOTAL
		return count>=4;
	}

	private boolean vertical(int i, int j, int p) {
		if(i<3) return false;
		return (board[i-1][j]==p)&&(board[i-2][j]==p)&&(board[i-3][j]==p);
	}

	public int canWinNextRound (int player){
		for(int i=0; i<board.length; i++) {
			if(available[i]>=6) continue;
			addDisk(i,player);
			if(isWinning (i,player)) {
				removeDisk(i);
				return i;
			}
			removeDisk(i);
		}
		return -1;
	}

	public void removeDisk (int index){
		if(available[index]<1) return;
		board[index][available[index]-1] = 0;
		available[index] = available[index]-1;
	}
	
	public int canWinTwoTurns (int player){
		int otherPlayer = 0;
		if(player==1) otherPlayer = 2;
		if(player==2) otherPlayer = 1;
		for(int i=0; i<board.length; i++) {
			if(available[i]>=6) continue;
			addDisk(i,player);
			for(int j=0; j<board.length; j++) {
				if(available[j]>=6) continue;
				addDisk(j,otherPlayer);
				if(canWinNextRound(player)>-1) {
					removeDisk(i);
					removeDisk(j);
					return i;
				}
				removeDisk(j);
			}
			removeDisk(i);
		}
		return -1;
	}
	
}