
import java.awt.*;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*********************************************************************
 * Connect Four Game
 * 
 * @author George Glessner
 * @version 10.7.2015
 *********************************************************************/
public class ConnectFourGame {

	/**Instance variable for 2D array board */
	private int[][] board;
	
	/**Instance variable for 1D array of score keeping */
	private int[] score;
	
	/**Instance variables for values of different parameters for game*/
	private int numPlayers, connectSize, boardSize, pStart, tempStart;
	
	/**Instance variable to set newGame boolean to false initially */
	private boolean newGame = false;

	/*****************************************************************
	 * Constructor for ConnectFourGame
	 *****************************************************************/
	public ConnectFourGame() {
		
		// Prompts for number of players, board size, connection size,
		// player who starts. Set default values if invalid number
		try {
			String players = JOptionPane.showInputDialog("Enter number "
					+ "of players: ");
			numPlayers = Integer.parseInt(players);
		} 
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valid numbers are "
					+ "1-10 \nPlayers set to 2.");
			numPlayers = 2;
		}
		if (numPlayers < 1 || numPlayers > 10) {
			JOptionPane.showMessageDialog(null, "Valid numbers are "
					+ "1-10 \nPlayers set to 2.");
			numPlayers = 2;
		}

		try {
			String board = JOptionPane.showInputDialog("Enter board "
					+ "size:");
			boardSize = Integer.parseInt(board);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valid board sizes are "
					+ "4-20 \nBoard set to 10.");
			boardSize = 10;
		}
		if (boardSize < 4 || boardSize > 20) {
			JOptionPane.showMessageDialog(null, "Valid board sizes are "
					+ "4-20\nBoard set to 10.");
			boardSize = 10;
		}

		try {
			String connect = JOptionPane.showInputDialog("Enter "
					+ "connection size: ");
			connectSize = Integer.parseInt(connect);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valid connection sizes"
					+ " are 2-" + boardSize + "\nConnection set to 4.");
			connectSize = 4;
		}
		if (connectSize < 2 || connectSize > boardSize) {
			JOptionPane.showMessageDialog(null, "Valid connection sizes"
					+ " are 2-" + boardSize + "\nConnection set to 4.");
			connectSize = 4;
		}

		try {
			String start = JOptionPane.showInputDialog("Enter player who"
					+ " starts: ");
			pStart = Integer.parseInt(start);
			tempStart = Integer.parseInt(start);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valid player # who will"
					+ "start. (1-" + numPlayers + ")\nStarting player "
							+ "set to 1.");
			pStart = 1;
			tempStart = 1;
		}
		if (pStart < 1 || pStart > numPlayers) {
			JOptionPane.showMessageDialog(null, "Valid numbers are 1-" 
		+ numPlayers + "\nStarting player set to 1.");
			pStart = 1;
			tempStart = 1;
		}
		

		// Create new board given board size
		board = new int[boardSize][boardSize];

		// Set score array to size of number of players
		score = new int[numPlayers];
	}

	/*****************************************************************
	 * Get number of players
	 * 
	 * @return numPlayers number of players
	 *****************************************************************/
	public int getNumPlayers() {
		return numPlayers;
	}

	/*****************************************************************
	 * Get board size
	 * 
	 * @return boardSize size of board (LxW)
	 *****************************************************************/
	public int getBoardSize() {
		return boardSize;
	}

	/*****************************************************************
	 * Get number of connection
	 * 
	 * @return connectSize length of connection size needed to win
	 *****************************************************************/
	public int getNumConnections() {
		return connectSize;
	}

	/*****************************************************************
	 * Boolean is new game
	 * 
	 * @return newGame true or false depending on if new game is needed
	 *****************************************************************/
	public boolean isNewGame() {
		return newGame;
	}

	/*****************************************************************
	 * Set new game
	 * 
	 * @param newGame set the boolean to true or false
	 *****************************************************************/
	public void setNewGame(boolean newGame) {
		this.newGame = newGame;
	}

	/*****************************************************************
	 * Get player start
	 * 
	 * @return pStart player who starts the game
	 *****************************************************************/
	public int getpStart() {
		return pStart;
	}

	/*****************************************************************
	 * Set player start
	 * 
	 * @param pStart Set the player who starts the game
	 *****************************************************************/
	public void setpStart(int pStart) {
		this.pStart = pStart;
	}

	/*****************************************************************
	 * Score Increment increments the score of player i.
	 * 
	 * @param i value of player
	 *****************************************************************/
	public void scoreInc(int i) {
		score[i - 1]++;
	}

	/*****************************************************************
	 * Get score
	 * 
	 * @param i number of player
	 * @return score score of player i
	 *****************************************************************/
	public int getScore(int i) {
		return score[i - 1];
	}

	/*****************************************************************
	 * Select which row token falls to.
	 * 
	 * @param col column number that was selected
	 * @return tempRow row that token falls to.
	 *****************************************************************/
	public int selectCol(int col) {

		// Variable for row number
		int tempRow = 0;

		// Check to see if the column is full
		if (board[0][col] != 0) {
			return -1;
		}
		// Check to see if the column is empty
		for (int row = 0; row < boardSize; row++) {
			if (board[row][col] == 0) {
				tempRow = row;
			}
		}

		// If not empty, check to see what row the token falls to
		if (tempRow >= 0) {
			board[tempRow][col] = getPlayerTurn();
			getGameStatus();
			return tempRow;
		} else {
			return tempRow;
		}

	}

	/*****************************************************************
	 * Check game status of the current game
	 * 
	 * @return pStart, -1 returns player who wins or -1 if no one wins
	 *****************************************************************/
	public int getGameStatus() {

		// Boolean for winner
		boolean winner = false;

		//Variable for winning connections
		int win = 0;
		
		// Check for horizontal connection
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {

				// Variable for winning connection
				win = 0;
				for (int connect = 0; connect < connectSize; connect++){
					if (board[row][(col + connect) % boardSize] == 
							pStart) {
						win++;
					} else {
						break;
					}
					if (win == connectSize) {
						winner = true;
						break;
					}

				}

				// Check for vertical connection
				win = 0;
				for (int connect = 0; connect < connectSize; connect++){
					if (board[(row + connect) % boardSize][(col)] == 
							pStart) {
						win++;
					} else {
						break;
					}
					if (win == connectSize) {
						winner = true;
						break;
					}
				}

				// Check for diagonal connection
				win = 0;
				for (int connect = 0; connect < connectSize; connect++){
					if (row + connect >= boardSize || col - connect < 0){
						break;
					} else if (board[row + connect][col - connect] == 
							pStart) {
						win++;
					} else {
						break;
					}
					if (win == connectSize) {
						winner = true;
						break;
					}

				}

				// Check for diagonal connection
				win = 0;
				for (int connect = 0; connect < connectSize; connect++){
					if (row + connect >= boardSize || col + connect >= 
					boardSize) {
						break;
					} else if (board[row + connect][col + connect] ==
							pStart) {
						win++;
					} else {
						break;
					}
					if (win == connectSize) {
						winner = true;
						break;
					}

				}
				
				// If winner, prompt user who won, increment score
				// Set new game to true
				if (winner) {
					String Message = "Player " + pStart + " won!" + "\nClick OK to restart game.";
					JOptionPane.showMessageDialog(null, Message);
					col = boardSize;
					row = boardSize;
					scoreInc(pStart);
					newGame = true;
					return pStart;

				}

			}
		}

		// Check for cats game
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				if (board[row][col] == 0) {
					return -1;
				}
			}
		}

		// If Cats game, warn player, reset game
		String message = "Cats Game \nGame will restart.";
		JOptionPane.showMessageDialog(null, message);
		newGame = true;
		return pStart;

	}

	/*****************************************************************
	 * Keeps track of player turn throughout the game
	 *****************************************************************/
	public void playerTurn() {

		//Sets player turn accordingly
		if (pStart == numPlayers) {
			pStart = 1;
		} else {
			pStart++;
		}

	}

	/*****************************************************************
	 * Get player turn
	 * 
	 * @return pTurn current players turn
	 *****************************************************************/
	public int getPlayerTurn() {
		return pStart;
	}

	/*****************************************************************
	 * Resets the game, sets starting player to original
	 *****************************************************************/
	public void reset() {

		// Goes through the board sets all values back to 0
		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++)
				board[r][c] = 0;

		// Resets the player who starts back to original
		setpStart(tempStart);

	}

	/*****************************************************************
	 * Displays board
	 * 
	 * @return board displays the board
	 *****************************************************************/
	public int[][] getBoard() {
		return board;
	}

}
