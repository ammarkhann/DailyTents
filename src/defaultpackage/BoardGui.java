package defaultpackage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class BoardGui extends JPanel {
	private JButton[][] button;
	private JButton topButtons;
	private JButton sideButtons;
	private JComboBox<String> dimensions = new JComboBox<String>();
	private JComboBox<String> gameOptions = new JComboBox<String>();
	private Board board;
	private BoardController[][] boardController;
	private int boardSize;
	private JPanel rowPanel, colPanel, gridPanel;
	JPanel menuPanel;
	JPanel gamePanel;
	JLabel welcome = new JLabel("Welcome to daiy tents puzzle game");
	JLabel enterDimensions = new JLabel(
			"Select from one of the following dimensions to start");
	JPanel container;
	CardLayout cardLayout;

	public BoardGui() {

		initializeCardLayout();
		initializeContainerPanel();
		populateDimensionsBar();
		populateGameOptions();
		setLayout(new BorderLayout());
		initializeMenuPanel();
		initializeGamePanel();
		addPanelsToContainer();
		setMenuView();
		
		add(container);

	}

	public void initializeCardLayout() {
		cardLayout = new CardLayout();
	}

	public void initializeContainerPanel() {
		container = new JPanel();
		container.setLayout(cardLayout);
	}

	public void populateDimensionsBar() {
		dimensions.insertItemAt("8x8", 0);
		dimensions.insertItemAt("12x12", 1);
		dimensions.insertItemAt("16x16", 2);
		dimensions.insertItemAt("20x20", 3);

		dimensions.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JComboBox comboBox = (JComboBox) e.getSource();
				int index = comboBox.getSelectedIndex();
				String boardDimension = (String) comboBox.getSelectedItem();
				int charIndex = boardDimension.indexOf('x');
				boardDimension = boardDimension.substring(0, charIndex);
				Integer size = Integer.parseInt(boardDimension);
				boardSize = size;
				cardLayout.show(container, "2");
				try {
					startGame(boardSize);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}


			
		});

	}

	public void populateGameOptions() {
		gameOptions.insertItemAt("Restart Game", 0);
		gameOptions.insertItemAt("Change Dimensions", 1);
		gameOptions.insertItemAt("Exit", 2);
		gameOptions.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
					JComboBox comboBox = (JComboBox) e.getSource();
					int index = comboBox.getSelectedIndex();
					if (index == 0) {
						
		
						
					} else if (index == 1) {
						cardLayout.show(container, "1");
						initializeGamePanel();
						addPanelsToContainer();
						setMenuView();
						add(container);
					} else if (index == 2) {
						System.exit(0);
					}
				}
		
		
		});
	}

	public void initializeMenuPanel() {
		menuPanel = new JPanel();
		menuPanel.setLayout(new BorderLayout());
		welcome.setHorizontalAlignment(JLabel.CENTER);
		menuPanel.add(welcome, BorderLayout.NORTH);
		enterDimensions.setHorizontalAlignment(JLabel.CENTER);
		menuPanel.add(enterDimensions, BorderLayout.CENTER);
		dimensions.setPrototypeDisplayValue("Hello World");
		menuPanel.add(dimensions, BorderLayout.SOUTH);
	}

	public void initializeGamePanel() {
		gamePanel = new JPanel();
	}

	public void addPanelsToContainer() {
		container.add(menuPanel, "1");
		container.add(gamePanel, "2");
	}

	public void setMenuView() {
		cardLayout.show(container, "1");
	}

	public void startGame(int boardSize) throws IOException {
		initializeButtons(boardSize);
		initializeBoardController(boardSize);
		initializeBoard(boardSize);
		gamePanel.setLayout(new BorderLayout());
		initializeRowPanel();
		addInGameMenu();
		setTentNumberInCols();
		initializeGridPanel();
		setButtonsOnGrid();
		displayTrees();
		initializeColPanel();
		setTentNumberInRows();
		initGamePanel();
	}

	private void initializeButtons(int boardSize) {
		button = new JButton[boardSize][boardSize];
	}

	private void initializeBoardController(int boardSize) {
		boardController = new BoardController[boardSize][boardSize];
	}

	private void initializeBoard(int boardSize) {
		board = new Board(boardSize);
		board.placeTents();
		board.placeTrees();
		board.countNumberOfTentsInRow();
		board.countNumberOfTentsInColumn();
	}

	private void initializeRowPanel() {
		rowPanel = new JPanel();
		rowPanel.setLayout(new GridLayout(1, board.getBoardSize()));
	}

	private void addInGameMenu() {
		rowPanel.add(gameOptions);
	}

	private void setTentNumberInCols() {
		Integer[] colArray = board.getElementsInColumn();
		for (int i = 0; i < board.getBoardSize(); i++) {
			topButtons = new JButton(colArray[i].toString());
			rowPanel.add(topButtons);
		}
	}

	private void initializeGridPanel() {
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(board.getBoardSize(), board
				.getBoardSize()));
	}

	private void setButtonsOnGrid() {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[i][j] = new JButton();
				button[i][j].setBackground(Color.GREEN);
				gridPanel.add(button[i][j]);
				boardController[i][j] = new BoardController(this, i, j);
				button[i][j].addActionListener(boardController[i][j]);
			}
		}
	}

	public void displayTrees() throws IOException {
		Image img = ImageIO.read(getClass().getResource("/images/tree.gif"));
		for (int i = 0; i < board.getBoardSize(); i++) {
			for (int j = 0; j < board.getBoardSize(); j++) {
				if (board.checkCellForTree(i, j)) {
					button[i][j].setIcon(new ImageIcon(img));
					button[i][j].setBackground(Color.white);
					button[i][j].setFocusable(false);
				}
			}
		}
	}

	private void initializeColPanel() {
		colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(board.getBoardSize(), 1));
	}

	private void setTentNumberInRows() {
		Integer[] rowArray = board.getElementsInRow();
		for (int i = 0; i < board.getBoardSize(); i++) {
			sideButtons = new JButton(rowArray[i].toString());
			colPanel.add(sideButtons);
		}
	}

	private void initGamePanel() {
		
		gamePanel.add(rowPanel, BorderLayout.NORTH);
		gamePanel.add(gridPanel, BorderLayout.CENTER);
		gamePanel.add(colPanel, BorderLayout.WEST);
	}

	public JButton[][] getButton() {
		return button;
	}

	public void setButton(JButton[][] button) {
		this.button = button;
	}

	public boolean pressedOnButton(int x, int y) {
		if (!(board.checkUserInsertion(x, y))) {
			board.insert(x, y);
			button[x][y].removeActionListener(boardController[x][y]);
			return true;
		}
		return false;
	}

	public boolean winCheck() {
		if (board.maxInsertions() && board.winCheck()) {
			return true;
		}
		return false;
	}

	public boolean lossCheck() {
		if (board.maxInsertions() && !(board.winCheck())) {
			return true;
		}
		return false;
	}

	public void showErrorMessageDialog() {
		JOptionPane.showMessageDialog(null,
				"Tents can not be entered adjacently");
	}

	public void showWinDialog() {
		JOptionPane.showMessageDialog(null, "You Won, Congratulations!");
	}

	public void showFailDialog() {
		JOptionPane.showMessageDialog(null,
				"You lost, maximum insertions reached!");
	}
}