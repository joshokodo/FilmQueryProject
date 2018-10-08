package com.skilldistillery.myutils.consoleUI;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUI extends AbstractUserInput {

	private final int PADDING = 4;
	private final int DEFAULT_SPACING = 5;
	private final char DEFAULT_BORDER_LINING = '*';
	private final char DEFAULT_WARNING_BORDER_LINING = '!';
	private final int DEFAULT_WRAP_AROUND_LIMIT = 50;

	// fields
	private List<String> lines;
	private char borderLining;
	private int spacing;
	private int wrapAroundLimit = 50;

	// constructors
	public AbstractUI() {
		lines = new ArrayList<>();
		borderLining = DEFAULT_BORDER_LINING;
		spacing = DEFAULT_SPACING;
		wrapAroundLimit = DEFAULT_WRAP_AROUND_LIMIT;
	}

	public AbstractUI(char borderLining) {
		super();
		this.borderLining = borderLining;
	}

	public AbstractUI(char borderLining, int wrapAroundLimit) {
		super();
		this.borderLining = borderLining;
		this.wrapAroundLimit = wrapAroundLimit;
	}

	// lines manipulator methods

	public void addLeftAlignedText(String text) {

		lines.add("L" + text);
	}
	public void addRightAlignedText(String text) {
		lines.add("R" + text);
	}
	public void addCenterAlignedText(String text) {
		lines.add("C" + text);
	}
	public void addSpace() {
		lines.add("S");
	}
	public void addBorder() {
		lines.add("B");
	}

	public void clearLines() {
		lines.clear();
	}

	public void addTopOfUI() {
		addBorder();
		addSpace();
	}

	public void addBottomOfUI() {
		addSpace();
		addBorder();
	}
	public void addHeaders(String... headers) {
		for (String header : headers) {
			addCenterAlignedText(header);
		}
		addSpace();
	}
	public void addOptions(String... options) {
		for (String option : options) {
			addLeftAlignedText(option);
			addSpace();
		}
	}

	public void setAsBasicMenuUI(String[] headers, String[] options) {
		addTopOfUI();
		addHeaders(headers);
		addOptions(options);
		addBottomOfUI();
	}
	public void setAsBasicMessageUI(String... message) {
		addTopOfUI();
		addHeaders(message);
		addBottomOfUI();
	}
	// other methods

	public void printUI() {
		System.out.println(this.constructUI());
	}

	// // helper methods
	private String constructUI() {

		spacing = getLongestLength() + PADDING;

		StringBuilder menu = new StringBuilder();

		for (String line : lines) {

			char lineType = line.charAt(0);

			switch (lineType) {

				case 'B' :
					menu.append(buildBorderLine());
					break;

				case 'S' :
					menu.append(buildSpaceLine());
					break;

				case 'L' :
					if (line.substring(1).length() >= wrapAroundLimit) {
						for (String text : breakUpText(line.substring(1))) {
							menu.append(buildLeftAlign(text));
						}
					} else {
						menu.append(buildLeftAlign(line.substring(1)));
					}
					break;

				case 'R' :
					if (line.substring(1).length() >= wrapAroundLimit) {
						for (String text : breakUpText(line.substring(1))) {
							menu.append(buildRightAlign(text));
						}
					} else {
						menu.append(buildRightAlign(line.substring(1)));
					}
					break;

				case 'C' :
					if (line.substring(1).length() >= wrapAroundLimit) {
						for (String text : breakUpText(line.substring(1))) {
							menu.append(buildCenterAlign(text));
						}
					} else {
						menu.append(buildCenterAlign(line.substring(1)));
					}
					break;
				default :
					System.out.println(
							"In ConstructUI() line didnt have proper char identifier at index 0");
					break;
			}
		}

		return menu.toString();
	}

	private String buildLeftAlign(String line) {
		String format = "%c %-" + (spacing - 1) + "s%c%n";
		return String.format(format, borderLining, line, borderLining);
	}

	private String buildRightAlign(String line) {
		String format = "%c%" + (spacing - 1) + "s %c%n";
		return String.format(format, borderLining, line, borderLining);
	}
	private String buildBorderLine() {

		StringBuilder borderLine = new StringBuilder(
				"" + borderLining + borderLining);
		for (int i = 0; i < spacing; i++) {
			borderLine.append(borderLining);
		}
		borderLine.append("\n");
		return borderLine.toString();
	}

	private String buildSpaceLine() {
		StringBuilder spaceLine = new StringBuilder();
		spaceLine.append(borderLining);
		for (int i = 0; i < spacing; i++) {
			spaceLine.append(" ");
		}
		spaceLine.append(borderLining + "\n");
		return spaceLine.toString();
	}

	private String buildCenterAlign(String line) {

		// calculates the remaining space when putting header in the line
		// should always be at least 4;
		int remainingSpace = (spacing - line.length());

		int leftSpace = 1 + remainingSpace / 2;
		int rightSpace = 1 + remainingSpace / 2;

		if (remainingSpace % 2 != 0) {

			rightSpace++;
		}

		String format = "%-" + leftSpace + "c%s%" + rightSpace + "c%n";
		return String.format(format, borderLining, line, borderLining);
	}

	private boolean isTextLine(String line) {
		return line.charAt(0) == 'L' || line.charAt(0) == 'R'
				|| line.charAt(0) == 'C';
	}

	private int getLongestLength() {
		int longest = DEFAULT_SPACING;

		for (String line : lines) {
			if (isTextLine(line)) {
				longest = longest > line.substring(1).length()
						? longest
						: line.substring(1).length();
			}
		}

		return longest >= wrapAroundLimit ? wrapAroundLimit : longest;
	}

	private List<String> breakUpText(String text) {
		List<String> brokenUpText = new ArrayList<>();
		String[] textTokens = text.split("\\s+");
		StringBuilder newText = new StringBuilder();
		boolean overLimit = false;

		for (String token : textTokens) {

			overLimit = newText.length() + token.length() >= wrapAroundLimit;

			if (overLimit) {
				brokenUpText.add(newText.toString());
				newText.delete(0, newText.length());
				continue;
			} else {
				newText.append(" " + token);
			}

		}
		// adds any remaining text to the list
		brokenUpText.add(newText.toString());

		return brokenUpText;
	}

	// setters and getters for relevant fields
	public char getBorderLining() {
		return borderLining;
	}

	public void setBorderLining(char borderLining) {
		this.borderLining = borderLining;
	}

	public int getWrapAroundLimit() {
		return wrapAroundLimit;
	}

	public void setWrapAroundLimit(int wrapAroundLimit) {
		this.wrapAroundLimit = wrapAroundLimit;
	}

}
