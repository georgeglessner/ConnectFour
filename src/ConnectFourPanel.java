import javax.swing.*;


import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/********************************************************************
 *ConnectFourPanel Sets up game board, displays board and player tokens.
 ********************************************************************/
public class ConnectFourPanel extends JPanel {

	//Variables for labels, buttons, icons, and menu items
	private JLabel[][] board;
	private JButton[] selection;
	private JLabel score[];
	private ConnectFourGame game;
	private ImageIcon[] icon;
	private JMenuBar menu;
	private JMenu fileMenu;
	private JMenuItem quitItem, resetItem;

	/*****************************************************************
	 * Constructor for ConnectFourPanel
	 *****************************************************************/
	public ConnectFourPanel() {

		// Constructor
		game = new ConnectFourGame();
		icon = new ImageIcon[game.getNumPlayers() + 1];
		JPanel bottom = new JPanel();
		JPanel center = new JPanel();
		JPanel panel = new JPanel();

		// Set panel to boarder layout
		panel.setLayout(new BorderLayout());

		// Set the icon to given player
		for (int i = 0; i < game.getNumPlayers() + 1; i++) {
			icon[i] = new ImageIcon( i + ".png");
		}

		// Create a button listener
		ButtonListener listener = new ButtonListener();

		
		// Set a center layout for the game
		center.setLayout(new GridLayout(game.getBoardSize() + 1, 
				game.getBoardSize()));
		board = new JLabel[game.getBoardSize()][game.getBoardSize()];

		// Create the select buttons at top of game
		selection = new JButton[game.getBoardSize()];
		for (int col = 0; col < game.getBoardSize(); col++) {
			selection[col] = new JButton("Select");
			selection[col].addActionListener(listener);
			center.add(selection[col]);
		}

		// Create game board
		for (int row = 0; row < game.getBoardSize(); row++)
			for (int col = 0; col < game.getBoardSize(); col++) {
				board[row][col] = new JLabel(icon[0]);
				center.add(board[row][col]);
			}

		// Create scoreboard
		score = new JLabel[game.getNumPlayers()];
		for (int numPlayers = 1; numPlayers <= game.getNumPlayers(); 
				numPlayers++) {
			score[numPlayers - 1] = new JLabel("Player " + numPlayers + 
					" wins: " + game.getScore((numPlayers)));
			bottom.add(score[numPlayers - 1]);
		}
		
		//Set up menu bar
		menu = new JMenuBar();
		quitItem = new JMenuItem("Quit");
		resetItem = new JMenuItem("Reset");
		fileMenu = new JMenu("File");
		fileMenu.add(quitItem);
		fileMenu.add(resetItem);
		quitItem.addActionListener(listener);
		resetItem.addActionListener(listener);
		menu.add(fileMenu);
		

		// add panels
		panel.add(center, BorderLayout.CENTER);
		panel.add(bottom, BorderLayout.SOUTH);

		//Set up JFrame
		JFrame frame = new JFrame("Connect Four");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setJMenuBar(menu);
		frame.setSize(game.getBoardSize(), game.getBoardSize());
		frame.setVisible(true);
		frame.pack();
		
		
	}

	/*****************************************************************
	 * Button Listener class, implements action listener
	 *****************************************************************/
	private class ButtonListener implements ActionListener {

		// Keeps track of current temporary row
		int tempRow;

		// Checks to see if quit button is selected, exits game
		public void actionPerformed(ActionEvent e) {

			// Check to see if quit item is pressed
			if (quitItem == e.getSource()) {
				System.exit(0);
			}

			// Check to see if reset button is pressed
			if (resetItem == e.getSource()) {
				game.reset();
				for (int row = 0; row < game.getBoardSize(); row++) {
					for (int col = 0; col < game.getBoardSize(); col++){
						board[row][col].setIcon(icon[0]);
					}
				}
			}

			// Goes through the board, sets tempRow to the given value
			// the selectCol class gives it.
			for (int col = 0; col < game.getBoardSize(); col++) {
				if (selection[col] == e.getSource()) {
					tempRow = game.selectCol(col);

					// Sets the board location to the given players icon
					if (tempRow >= 0) {
						board[tempRow][col].setIcon(icon
								[game. getPlayerTurn()]);
						game.playerTurn();
					}

					// Resets game after winner
					if (game.isNewGame() == true) {
						for (int r = 0; r < game.getBoardSize(); r++) {
							for (int c = 0; c < game.getBoardSize(); c++){
								board[r][c].setIcon(icon[0]);
							}
						}

						// reset game, set new game to false
						game.reset();
						game.setNewGame(false);

						// set player score accordingly
						for (int numPlayers = 1; numPlayers 
								<= game.getNumPlayers(); numPlayers++){
							score[numPlayers - 1]
									.setText("Player " + numPlayers + 
											" wins: " + 
										game.getScore((numPlayers)));
						}
					}

				}

			}

		}

	}

	/*****************************************************************
	 * Main class
	 *****************************************************************/
	public static void main(String[] args) {

		// Create new game panel
		new ConnectFourPanel();

	}
}
