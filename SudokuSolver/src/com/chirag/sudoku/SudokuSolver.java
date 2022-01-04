package com.chirag.sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class SudokuSolver {

	private static final int size = 9;

	private static int[][] sudoku = new int[size][size];

	private final Map<Integer, List<Integer>> possibleNumbersMap = new HashMap<>();

	public static void main(String[] args) {
		generateSudoku();
		SudokuSolver obj = new SudokuSolver();
		obj.printSudoku();
		System.out.println("=======================Resolve=============");
		obj.solveSudoku();
		obj.printSudoku();

	}

	private static void generateSudoku() {
		sudoku[0][0] = 3;
		sudoku[0][2] = 6;
		sudoku[0][3] = 7;
		sudoku[0][4] = 2;
		sudoku[0][5] = 9;
		sudoku[0][6] = 8;
		sudoku[0][8] = 4;

		sudoku[1][1] = 8;
		sudoku[1][3] = 6;
		sudoku[1][5] = 4;
		sudoku[1][7] = 5;
		// sudoku[1][7] = 8;

		// sudoku[2][2] = 5;
		/*
		 * sudoku[2][7] = 4; sudoku[2][8] = 1;
		 */

		sudoku[3][0] = 1;
		// sudoku[3][7] = 9;
		sudoku[3][8] = 3;
		/* sudoku[3][7] = 9; */

		sudoku[4][0] = 8;
		sudoku[4][2] = 2;
		sudoku[4][4] = 3;
		sudoku[4][6] = 7;
		sudoku[4][8] = 6;

		sudoku[5][0] = 5;
		// sudoku[5][7] = 6;
		sudoku[5][8] = 2;
		/* sudoku[5][6] = 8; */

		// sudoku[6][2] = 1;
		/*
		 * sudoku[6][1] = 2; sudoku[6][3] = 7;
		 */

		// sudoku[7][0] = 4;
		sudoku[7][1] = 2;
		sudoku[7][3] = 5;
		sudoku[7][5] = 7;
		sudoku[7][7] = 3;

		sudoku[8][0] = 6;
		sudoku[8][2] = 1;
		sudoku[8][3] = 8;
		sudoku[8][4] = 4;
		sudoku[8][5] = 3;
		sudoku[8][6] = 2;
		sudoku[8][8] = 5;

	}

	private void solveSudoku() {
		int count = 1;
		boolean guessValue = false;

		do {
			guessValue = false;
			System.out.println("Count : " + count++);
			String insertedData = "";
			boolean foundUpdate = false;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (i == 4 && j == 4) {
						System.out.println("Beak");
					}
					List<Integer> possibility = new ArrayList<>();
					int possibilityIndex = generateIndex(i, j);
					if (sudoku[i][j] == 0) {
						for (int num = 1; num < 10; num++) {
							if (validateNumber(num, i, j)) {
								possibility.add(num);
							}
						}

						if (possibility.size() > 1) {
							validateNumberPossibility(possibility, i, j);
						}
						if (possibility.size() == 1) {
							foundUpdate = true;
							sudoku[i][j] = possibility.get(0);
							possibleNumbersMap.remove(possibilityIndex);
							insertedData = insertedData + i + ":" + j + "=" + possibility.get(0) + ", ";
						} else {

							possibleNumbersMap.put(possibilityIndex, possibility);
						}
					}
				}
			}
			System.out.println(insertedData);

			if (!foundUpdate) {
				System.out.println("------------Update not found : ");
				guesOneValue();
				guessValue = true;
			}
		} while (guessValue || !possibleNumbersMap.isEmpty());

		System.out.println(possibleNumbersMap);
	}

	private void guesOneValue() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (sudoku[i][j] == 0) {
					int guessIndex = generateIndex(i, j);
					List<Integer> list = possibleNumbersMap.get(guessIndex);
					if (list != null) {
						sudoku[i][j] = list.get(0);
						System.out.println(" value Guess " + i + ":" + j + "=" + list.get(0));
						possibleNumbersMap.clear();
					}

				}
			}
		}
	}

	private int generateIndex(int raw, int col) {
		return (raw * 10) + col;
	}

	private boolean validateNumber(int number, int raw, int col) {
		boolean isValid = true;
		for (int i = 0; i < size; i++) {
			if (sudoku[i][col] == number || sudoku[raw][i] == number) {
				isValid = false;
				break;
			}
		}
		if (isValid) {
			int rawBoxIndex = (raw / 3) * 3;
			int colBoxIndex = (col / 3) * 3;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (sudoku[rawBoxIndex + i][colBoxIndex + j] == number) {
						isValid = false;
						break;
					}
				}

			}
		}
		return isValid;
	}

	private void validateNumberPossibility(List<Integer> possibleNumbers, int raw, int col) {

		// Check raw wise
		List<Integer> uniqueuPossibleNumbers = new CopyOnWriteArrayList<>(possibleNumbers);
		for (int i = 0; i < size; i++) {
			if (uniqueuPossibleNumbers.isEmpty()) {
				break;
			}
			if (sudoku[i][col] == 0 && i != raw) {
				List<Integer> list = possibleNumbersMap.get(generateIndex(i, col));
				if (list == null) {
					return;
				}
				for (Integer possibleNum : uniqueuPossibleNumbers) {
					if (list.contains(possibleNum)) {
						uniqueuPossibleNumbers.remove(uniqueuPossibleNumbers.indexOf(possibleNum));
					}
				}
			}
		}
		if (uniqueuPossibleNumbers.size() == 1) {
			possibleNumbers.clear();
			possibleNumbers.add(uniqueuPossibleNumbers.get(0));
			return;
		}

		// Check columns wise
		uniqueuPossibleNumbers = new CopyOnWriteArrayList<>(possibleNumbers);
		for (int i = 0; i < size; i++) {
			if (uniqueuPossibleNumbers.isEmpty()) {
				break;
			}

			if (sudoku[raw][i] == 0 && i != col) {
				List<Integer> list = possibleNumbersMap.get(generateIndex(raw, i));
				if (list == null) {
					return;
				}
				for (Integer possibleNum : uniqueuPossibleNumbers) {
					if (list.contains(possibleNum)) {
						uniqueuPossibleNumbers.remove(uniqueuPossibleNumbers.indexOf(possibleNum));
					}
				}
			}
		}
		if (uniqueuPossibleNumbers.size() == 1) {
			possibleNumbers.clear();
			possibleNumbers.add(uniqueuPossibleNumbers.get(0));
			return;
		}

		// Check Block wise

		uniqueuPossibleNumbers = new CopyOnWriteArrayList<>(possibleNumbers);
		int rawBoxIndex = (raw / 3) * 3;
		int colBoxIndex = (col / 3) * 3;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (uniqueuPossibleNumbers.isEmpty()) {
					break;
				}
				if ((rawBoxIndex + i) != raw || (colBoxIndex + j) != col) {
					List<Integer> list = possibleNumbersMap.get(generateIndex(rawBoxIndex + i, colBoxIndex + j));
					if (list == null) {
						return;
					}
					for (Integer possibleNum : uniqueuPossibleNumbers) {
						if (list.contains(possibleNum)) {
							uniqueuPossibleNumbers.remove(uniqueuPossibleNumbers.indexOf(possibleNum));
						}
					}
				}
			}

		}
		if (uniqueuPossibleNumbers.size() == 1) {
			possibleNumbers.clear();
			possibleNumbers.add(uniqueuPossibleNumbers.get(0));
			return;
		}

	}

	private void printSudoku() {
		boolean isResolved = true;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int data = sudoku[i][j];
				if (data == 0) {
					isResolved = false;
				}
				System.out.print(data + " ");
			}
			System.out.println();
		}
	}

}
