package com.skilldistillery.filmquery.entities;

import com.skilldistillery.myutils.consoleUI.AbstractUI;

public class FilmQueryUI extends AbstractUI {

	private final static String MAIN_MENU_HEADER_1 = "Welcome to the Film Query App.";
	private final static String MAIN_MENU_HEADER_2 = "Choose an option below.";

	private final static String MAIN_MENU_OPTION_1 = "1. Look up film by it's ID Number";
	private final static String MAIN_MENU_OPTION_2 = "2. Look up film by keywords";
	private final static String MAIN_MENU_OPTION_3 = "3. Add film to database ";
	private final static String MAIN_MENU_OPTION_4 = "4. Exit the app ";

	private final static String FIND_BY_ID_HEADER_1 = "Enter an ID number and I'll try to find the film.";
	private final static String CANCEL_HEADER_1 = "(Enter -1 to cancel)";

	private final static String FILM_NOT_FOUND_MESSAGE_1 = "Sorry, film could not be found.";

	private final static String MORE_OPTIONS_HEADER_1 = "What would you like to do now?";
	private final static String MORE_OPTIONS_OPTION_1 = "1. View all details";
	private final static String MORE_OPTIONS_OPTION_2 = "2. search again";
	private final static String MORE_OPTIONS_OPTION_3 = "3. return to main menu";
	private final static String MORE_OPTIONS_OPTION_4 = "4. exit program";
	
	private final static String ENTER_TITLE_PROMPT = "Enter film title";
	private final static String FILM_ADDED_MESSAGE = "Film added to database with id ";
	private final static String NO_CAST_INFO_MESSAGE = "No Cast Info found at this time";

	private final static String FIND_BY_KEYWORD_HEADER_1 = "Enter a keyword and I'll try to find all films related to it.";

	private final static String EXIT_PROGRAM_MESSAGE_1 = "Thanks for using the Film Query app. Goodbye!";

	public FilmQueryUI() {
		super();
	}

	// main methods
	public void mainMenuPrompt() {
		clearLines();
		addTopOfUI();
		addHeaders(MAIN_MENU_HEADER_1, MAIN_MENU_HEADER_2);
		addOptions(MAIN_MENU_OPTION_1, MAIN_MENU_OPTION_2, MAIN_MENU_OPTION_3, MAIN_MENU_OPTION_4);
		addBottomOfUI();
		printUI();
	}

	public void moreOptionsPrompt() {
		clearLines();
		addTopOfUI();
		addHeaders(MORE_OPTIONS_HEADER_1);
		addOptions(MORE_OPTIONS_OPTION_1, MORE_OPTIONS_OPTION_2, MORE_OPTIONS_OPTION_3, MORE_OPTIONS_OPTION_4);
		addBottomOfUI();
		printUI();
	}

	public void searchFilmByIdPrompt() {
		clearLines();
		setAsBasicMessageUI(FIND_BY_ID_HEADER_1, CANCEL_HEADER_1);
		printUI();
	}

	public void searchFilmByKeywordPrompt() {
		clearLines();
		setAsBasicMessageUI(FIND_BY_KEYWORD_HEADER_1, CANCEL_HEADER_1);
		printUI();
	}

	public void printFilmNotFound() {
		clearLines();
		setAsBasicMessageUI(FILM_NOT_FOUND_MESSAGE_1);
		this.printUI();
	}

	public void printExitProgram() {
		clearLines();
		setAsBasicMessageUI(EXIT_PROGRAM_MESSAGE_1);
		this.printUI();
	}

	public void printFilmSimpleDetails(Film film) {
		clearLines();
		setAsFilmSimpleDetailsUI(film);
		this.printUI();
	}

	public void printFilmAllDetails(Film film) {
		clearLines();
		setAsFilmAllDetailsUI(film);
		this.printUI();
	}
	
	public void enterTitlePrompt() {
		clearLines();
		setAsBasicMessageUI(ENTER_TITLE_PROMPT, CANCEL_HEADER_1);
		printUI();
	}
	public void printFilmAddedMessage(Film film) {
		clearLines();
		setAsBasicMessageUI(FILM_ADDED_MESSAGE + film.getId());
		printUI();
	}

	// specially made UI

	private void setAsFilmSimpleDetailsUI(Film film) {
		
		addTopOfUI();
		addHeaders(film.getTitle(), "Release Year:" + film.getReleaseYear(), "Rated " + film.getRating(),
				"Language: " + film.getLanguage());
		addBottomOfUI();
		addSpace();
		addHeaders("Description", film.getDescription());
		addBottomOfUI();
		addSpace();
		addCenterAlignedText("Cast");
		addSpace();
		if(film.getCast() != null && film.getCast().size() > 0) {
			for (int i = 0; i < film.getCast().size(); i++) {
				addLeftAlignedText(film.getCast().get(i).getFullName());
				addSpace();
			}
			
		}
		else {
			addCenterAlignedText(NO_CAST_INFO_MESSAGE);
			addSpace();
		}
		addBorder();
	}

	private void setAsFilmAllDetailsUI(Film film) {
		setAsFilmSimpleDetailsUI(film);
		addSpace();
		addCenterAlignedText("Extra Details");
		addSpace();
		addLeftAlignedText("Length: " + film.getLength());
		addSpace();
		addLeftAlignedText("Special Features: " + film.getSpecialFeatures());
		addSpace();
		addBorder();
		addSpace();
		addCenterAlignedText("Rental Details");
		addSpace();
		addLeftAlignedText("Rental Rate(price): $" + film.getRentalRate());
		addSpace();
		addLeftAlignedText("Rental Duration: " + film.getRentalDuration());
		addSpace();
		addLeftAlignedText("Replacement Cost: $" + film.getReplacementCost());
		addSpace();
		addBorder();

	}

}
