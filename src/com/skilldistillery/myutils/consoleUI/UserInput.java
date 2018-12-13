package com.skilldistillery.myutils.consoleUI;

import java.util.Scanner;

public class UserInput {
	final Scanner KEYBOARD = new Scanner(System.in);
	final String INVALID_INPUT_PROMPT = "Not a valid input. Please try again.";
	final String OUT_OF_RANGE_PROMPT = "Input not within specific range. Please try again";

	// scanner input methods
	public int getIntInputFromUser() {
		int num = -1;

		while (true) {
			String stringChoice = KEYBOARD.next();
			KEYBOARD.nextLine(); // to get rid of any trailing texts

			try {
				num = Integer.parseInt(stringChoice);
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_PROMPT);
				continue;
			}
			break;
		}
		return num;
	}

	public int getIntInputFromUser(int min, int max) {
		int num = -1;

		while (true) {
			String stringChoice = KEYBOARD.next();
			KEYBOARD.nextLine(); // to get rid of any trailing texts

			try {
				num = Integer.parseInt(stringChoice);

				boolean withinRange = num >= min && num <= max;
				if (!withinRange) {
					System.out.println(OUT_OF_RANGE_PROMPT);
					continue;
				}

			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_PROMPT);
				continue;
			}
			break;
		}
		return num;
	}

	public String getStringInputFromUser() {
		return KEYBOARD.nextLine();
	}

	// prompts user for string input between length min and max
	public String getStringInputFromUser(int min, int max) {
		String returnValue = null;
		int length = 0;
		boolean withinRange = false;

		do {
			returnValue = KEYBOARD.nextLine();
			length = returnValue.length();
			withinRange = length >= min && length <= max;

			if (!withinRange) {
				System.out.println(OUT_OF_RANGE_PROMPT);
			}

		} while (!withinRange);

		return returnValue;
	}

	public double getDoubleInputFromUser() {
		double num = -1;

		while (true) {
			String stringChoice = KEYBOARD.next();
			KEYBOARD.nextLine(); // to get rid of any trailing texts

			try {
				num = Double.parseDouble(stringChoice);
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_PROMPT);
				continue;
			}
			break;
		}
		return num;
	}
	
	public double getDoubleInputFromUser(double min, double max) {
		double num = -1;
		
		while (true) {
			String stringChoice = KEYBOARD.next();
			KEYBOARD.nextLine(); // to get rid of any trailing texts

			try {
				num = Double.parseDouble(stringChoice);
				boolean withinRange = num >= min && num <= max;
				
				if (!withinRange) {
					System.out.println(OUT_OF_RANGE_PROMPT);
					continue;
				}
				
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_PROMPT);
				continue;
			}
			break;
		}
		return num;
	}

	public void closeKeyboard() {
		KEYBOARD.close();
	}

}
