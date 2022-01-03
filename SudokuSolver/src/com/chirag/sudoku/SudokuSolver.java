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
		sudoku[0][0] = 8;
		sudoku[0][2] = 5;
		sudoku[0][4] = 1;
		sudoku[0][8] = 7;

		sudoku[1][2] = 1;
		sudoku[1][3] = 9;
		sudoku[1][4] = 5;

		sudoku[2][5] = 3;
		sudoku[2][7] = 4;
		sudoku[2][8] = 1;

		sudoku[3][2] = 6;
		sudoku[3][3] = 3;
		sudoku[3][5] = 5;
		sudoku[3][7] = 9;

		sudoku[4][0] = 9;
		sudoku[4][1] = 5;
		sudoku[4][4] = 5;
		sudoku[4][7] = 7;
		sudoku[4][8] = 4;

		sudoku[5][1] = 1;
		sudoku[5][3] = 4;
		sudoku[5][5] = 9;
		sudoku[5][6] = 8;

		sudoku[6][0] = 5;
		sudoku[6][1] = 2;
		sudoku[6][3] = 7;

		sudoku[7][4] = 9;
		sudoku[7][5] = 8;
		sudoku[7][6] = 7;

		sudoku[8][0] = 7;
		sudoku[8][4] = 4;
		sudoku[8][6] = 6;
		sudoku[8][8] = 5;
	}

	private void solveSudoku() {
		int count = 1;
		boolean foundUpdate = true;

		while (foundUpdate) {
			System.out.println("Count : " + count++);
			foundUpdate = false;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
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
						} else {

							possibleNumbersMap.put(possibilityIndex, possibility);
						}
					}
				}
			}

		}

		System.out.println(possibleNumbersMap);
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
		if (raw == 6 && col == 4) {
			System.out.println("Break");
		}
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
				if ((rawBoxIndex + i) != raw && (colBoxIndex + j) != col) {
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
