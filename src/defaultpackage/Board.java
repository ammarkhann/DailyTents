package defaultpackage;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
	private static int boardSize;
	private char[][] board;
	private ArrayList<Integer> numbers;
	private char[][] userBoard;
	public int insertion = 0;
	private Integer[] elementsInRow;
	private Integer[] elementsInColumn;
	public static final int NUMBER_OF_TENTS_FOR_SIZE_EIGHT = 12;
	public static final int NUMBER_OF_TENTS_FOR_SIZE_TWELVE = 28;
	public static final int NUMBER_OF_TENTS_FOR_SIZE_SIXTEEN = 51;
	public static final int NUMBER_OF_TENTS_FOR_SIZE_TWENTY = 80;

	public Board(int boardSize) {
		this.boardSize = boardSize;
		board = new char[boardSize][boardSize];
		userBoard = new char[boardSize][boardSize];
		elementsInRow = new Integer[board.length];
		elementsInColumn = new Integer[board.length];
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public char[][] getUserBoard() {
		return userBoard;
	}

	public void setUserBoard(char[][] userBoard) {
		this.userBoard = userBoard;
	}

	public void printBoard() {
		for (int row = 0; row < board.length; row++) {
			System.out.print("|");
			for (int col = 0; col < board.length; col++) {
				System.out.print(board[row][col] + "| ");
			}
			System.out.println();
		}
	}

	public void placeTents() {
		Random rand = new Random();
		int adjacentPlacementCount = 0;
		int low = 0;
		int count = 0;
		int high = boardSize;
		int numberOfIterations = maxIterations();
		for (int i = 0; i < numberOfIterations; i++) {
			count++;
			int row = rand.nextInt(high - low) + low;
			int col = rand.nextInt(high - low) + low;
			if (board[row][col] == 'T') {
				board[row][col] = '\0';
				i--;
			}
			if (count == 60 && boardSize == 8 || adjacentPlacementCount == 20
					&& boardSize == 8 || count == 120 && boardSize == 12
					|| adjacentPlacementCount == 50 && boardSize == 12
					|| count == 175 && boardSize == 16
					|| adjacentPlacementCount == 120 && boardSize == 16
					|| count == 340 && boardSize == 20
					|| adjacentPlacementCount == 250 && boardSize == 20) {
				board = new char[boardSize][boardSize];

				placeTents();
				break;
			}
			board[row][col] = 'T';
			if (isTreeHorizontalAdjacent(row, col)
					|| isTreeVerticalAdjacent(row, col)
					|| isTreeDiagonal(row, col)) {
				board[row][col] = '\0';
				i--;
				adjacentPlacementCount++;
			}
		}
	}

	public boolean isTreeHorizontalAdjacent(int row, int col) {
		if (checkCoordinates(row, col, '1') != 'T')
			return false;
		if (checkCoordinates(row, col + 1, '1') == 'T')
			return true;
		if (checkCoordinates(row, col - 1, '1') == 'T')
			return true;

		return false;
	}

	public boolean isTreeVerticalAdjacent(int row, int col) {
		if (checkCoordinates(row, col, '1') != 'T')
			return false;
		if (checkCoordinates(row + 1, col, '1') == 'T')
			return true;
		if (checkCoordinates(row - 1, col, '1') == 'T')
			return true;

		return false;

	}

	public Integer[] getElementsInRow() {
		return elementsInRow;
	}

	public Integer[] getElementsInColumn() {
		return elementsInColumn;
	}

	public boolean isTreeDiagonal(int row, int col) {
		if (checkCoordinates(row, col, '1') != 'T')
			return false;
		if (checkCoordinates(row + 1, col + 1, '1') == 'T')
			return true;
		if (checkCoordinates(row + 1, col - 1, '1') == 'T')
			return true;
		if (checkCoordinates(row - 1, col + 1, '1') == 'T')
			return true;
		if (checkCoordinates(row - 1, col - 1, '1') == 'T')
			return true;

		return false;

	}

	private char checkCoordinates(int row, int col, char c) {
		if ((0 <= row) && (row < board.length) && (0 <= col)
				&& (col < board[row].length)) {
			return board[row][col];
		}
		return c;
	}

	public void placeTrees() {
		Random rand = new Random();
		int randomMatrixCoordinate = rand.nextInt(3) - 1;
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				if (isHorizontalTreePlacementValid(row, col)) {
					if (randomMatrixCoordinate == 0) {
						col--;
					} else {
						board[row][col + randomMatrixCoordinate] = 'E';
					}
				} else if (isVerticalTreePlacementValid(row, col)) {
					if (randomMatrixCoordinate == 0) {
						col--;
					} else {
						board[row + randomMatrixCoordinate][col] = 'E';
					}
				} else if (checkCellForTent(row, col)
						&& isNextCellEmpty(row, col)) {
					board[row][col + 1] = 'E';
				} else if (checkCellForTent(row, col)
						&& isPrevCellEmpty(row, col)) {
					board[row][col - 1] = 'E';
				}
			}
		}
	}

	public boolean checkCellForTent(int row, int col) {
		if (board[row][col] == 'T') {
			return true;
		}
		return false;
	}

	public boolean isNextCellEmpty(int row, int col) {
		if (col == board.length - 1) {
			return false;
		}
		if (board[row][col + 1] == '\0') {
			return true;
		}
		return false;
	}

	public boolean isPrevCellEmpty(int row, int col) {
		if (col == 0) {
			return false;
		}
		if (board[row][col - 1] == '\0' && col != 0) {
			return true;
		}
		return false;
	}

	public boolean isVerticalNextCellEmpty(int row, int col) {
		if (row == board.length - 1) {
			return false;
		} else if (board[row + 1][col] == '\0') {
			return true;
		}

		return false;
	}

	public boolean isVerticalPrevCellEmpty(int row, int col) {
		if (row == 0) {
			return false;
		} else if (board[row - 1][col] == '\0') {
			return true;
		}

		return false;
	}

	public void printUserBoard() {
		for (int row = 0; row < board.length; row++) {
			 System.out.print(" " + " " + elementsInColumn[row]);
		}
		System.out.println();
		for (int row = 0; row < userBoard.length; row++) {
			 System.out.print(elementsInRow[row]);
			System.out.print("|");
			for (int col = 0; col < userBoard.length; col++) {
				if (board[row][col] == 'E') {
					userBoard[row][col] = 'E';
				} else {
					board[row][col] = '\0';
				}
				System.out.print(userBoard[row][col] + "| ");
			}
			System.out.println();
		}
	}

	public void insert(int row, int col) {
		if (userBoard[row][col] != 'E' || userBoard[row][col] == '\0') {
			userBoard[row][col] = 'T';
			insertion++;
		} else {
			System.out.print("Invalid coordinate entry.");
			System.out.print("Either cell is not empty or is occupied");
		}
	}

	public boolean maxInsertions() {
		if (boardSize == 8 && insertion == NUMBER_OF_TENTS_FOR_SIZE_EIGHT) {
			return true;
		} else if (boardSize == 12
				&& insertion == NUMBER_OF_TENTS_FOR_SIZE_TWELVE) {
			return true;
		} else if (boardSize == 16
				&& insertion == NUMBER_OF_TENTS_FOR_SIZE_SIXTEEN) {
			return true;
		} else if (boardSize == 20
				&& insertion == NUMBER_OF_TENTS_FOR_SIZE_TWENTY) {
			return true;
		}
		return false;

	}

	public int maxIterations() {
		if (boardSize == 8) {
			return NUMBER_OF_TENTS_FOR_SIZE_EIGHT;
		} else if (boardSize == 12) {
			return NUMBER_OF_TENTS_FOR_SIZE_TWELVE;
		} else if (boardSize == 16) {
			return NUMBER_OF_TENTS_FOR_SIZE_SIXTEEN;
		} else if (boardSize == 20) {
			return NUMBER_OF_TENTS_FOR_SIZE_TWENTY;
		}
		return 0;
	}

	public boolean checkUserInsertion(int row, int col) {
		if (checkUserInsertionHorizontally(row, col)
				|| checkUserInsertionVertically(row, col)
				|| checkUserInsertionDiagonally(row, col)) {
			return true;
		}
		return false;
	}

	private char checkCoordinateValidity(int row, int col, char c) {
		if ((0 <= row) && (row < userBoard.length) && (0 <= col)
				&& (col < userBoard[row].length)) {
			return userBoard[row][col];
		}
		return c;
	}

	public boolean checkUserInsertionHorizontally(int row, int col) {
		if (checkCoordinateValidity(row, col + 1, '1') == 'T')
			return true;
		if (checkCoordinateValidity(row, col - 1, '1') == 'T')
			return true;
		return false;

	}

	public boolean checkUserInsertionVertically(int row, int col) {
		if (checkCoordinateValidity(row + 1, col, '1') == 'T')
			return true;
		if (checkCoordinateValidity(row - 1, col, '1') == 'T')
			return true;
		return false;

	}

	public boolean checkUserInsertionDiagonally(int row, int col) {
		if (checkCoordinateValidity(row + 1, col + 1, '1') == 'T')
			return true;
		if (checkCoordinateValidity(row + 1, col - 1, '1') == 'T')
			return true;
		if (checkCoordinateValidity(row - 1, col + 1, '1') == 'T')
			return true;
		if (checkCoordinateValidity(row - 1, col - 1, '1') == 'T')
			return true;
		return false;
	}

	public boolean isHorizontalTreePlacementValid(int row, int col) {
		if (checkCellForTent(row, col)
				&& (isNextCellEmpty(row, col) && isPrevCellEmpty(row, col))) {
			return true;
		}
		return false;
	}

	public boolean isVerticalTreePlacementValid(int row, int col) {
		if (checkCellForTent(row, col)
				&& (isVerticalNextCellEmpty(row, col) && isVerticalPrevCellEmpty(
						row, col))) {
			return true;
		}
		return false;
	}

	public void countNumberOfTentsInRow() {
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == 'T') {
					count++;
				}
			}
			elementsInRow[i] = count;
			count = 0;
		}
	}

	public void countNumberOfTentsInColumn() {
		int count = 0;
		for (int row = 0; row < board[0].length; row++) {
			for (int col = 0; col < board.length; col++) {
				if (board[col][row] == 'T') {
					count++;
				}
			}
			elementsInColumn[row] = count;
			count = 0;
		}
	}

	public int getWinCount() {
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == 'T' && board[i][j] == userBoard[i][j]) {
					count++;
				}
			}
		}
		return count;

	}

	public boolean winCheck() {
		if (getWinCount() == insertion) {
			return true;

		}
		return false;
	}

	public boolean checkCellForTree(int row, int col) {

		if (board[row][col] == 'E') {
			return true;
		}

		return false;
	}
}
