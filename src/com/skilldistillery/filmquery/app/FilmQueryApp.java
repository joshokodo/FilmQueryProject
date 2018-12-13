package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.FilmQueryUI;
import com.skilldistillery.myutils.consoleUI.UserInput;

public class FilmQueryApp {

	private DatabaseAccessor db;
	private FilmQueryUI ui;
	private UserInput input;
	private int numberChoice;
	private String textChoice;

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launchApp();

	}

	public FilmQueryApp() {
		db = new DatabaseAccessorObject();
		ui = new FilmQueryUI();
		input = new UserInput();
		numberChoice = 0;
		textChoice = "";
	}

	public void launchApp() {

		while (true) {

			ui.mainMenuPrompt();
			numberChoice = input.getIntInputFromUser(1, 4);
			switch (numberChoice) {

				case 1 :
					try {
						launchSearchFilmById();
					} catch (SQLException e) {
						System.err.println("Error with finding film by Id");
						e.printStackTrace();
						exitProgram();
					}
					break;

				case 2 :
					try {
						launchSearchFilmByKeyword();
					} catch (SQLException e) {
						System.err
								.println("Error with finding film by Keyword");
						e.printStackTrace();
						exitProgram();
					}
					break;

					
				case 3 :
					launchAddFilm();
					break;
					
				case 4 :
					exitProgram();
					break;

				default :
					System.out.println(
							"this shouldn't be happeneing. in default case of launchApp()");
					break;
			}
		}

	}

	private void launchAddFilm() {
		while(true) {
			
			ui.enterTitlePrompt();
			String inputStr = input.getStringInputFromUser();
			
			if(inputStr.indexOf("-1") >= 0) {
				break;
			}
			Film newFilm = new Film();
			newFilm.setTitle(inputStr);
			newFilm.setDescription("A new summer blockbuster");
			newFilm.setLanguageId(1);
			newFilm.setRentalDuration(5);
			newFilm.setRentalRate(4.99);
			newFilm.setReplacementCost(50.00);
			
			db.addFilm(newFilm);
			ui.printFilmSimpleDetails(newFilm);
			ui.printFilmAddedMessage(newFilm);
			
			break;
		}
	}

	private void launchSearchFilmByKeyword() throws SQLException {

		while (true) {
			
			ui.searchFilmByKeywordPrompt();
			textChoice = input.getStringInputFromUser();

			if (textChoice.indexOf("-1") >= 0) {
				break;
			}

			List<Film> films = db.getFilmByKeyword(textChoice);
			boolean filmFound = films.size() > 0 ? true : false;
			System.out.println(filmFound);
			if (!filmFound) {
				ui.printFilmNotFound();
				continue;

			} else if (filmFound) {
				
				for (Film film : films) {
					ui.printFilmSimpleDetails(film);

				}
				ui.moreOptionsPrompt();
				numberChoice = input.getIntInputFromUser(1, 4);

				if (numberChoice == 1) {
					for (Film film : films) {
						ui.printFilmAllDetails(film);
					}

				} else if (numberChoice == 2) {
					continue;

				} else if (numberChoice == 3) {
					break;

				} else if (numberChoice == 4) {
					exitProgram();

				}
			}
		}
		numberChoice = 0;
	}

	private void launchSearchFilmById() throws SQLException {

		while (true) {
			ui.searchFilmByIdPrompt();
			numberChoice = input.getIntInputFromUser();

			// cancel's search and returns user to main menu
			if (numberChoice == -1) {
				break;
			}

			Film film = db.getFilmById(numberChoice);
			boolean filmFound = film != null ? true : false;

			if (!filmFound) {
				ui.printFilmNotFound();
				continue;

			} else if (filmFound) {

				ui.printFilmSimpleDetails(film);
				ui.moreOptionsPrompt();
				numberChoice = input.getIntInputFromUser(1, 4);

				if (numberChoice == 1) {
					ui.printFilmAllDetails(film);

				} else if (numberChoice == 2) {
					continue;

				} else if (numberChoice == 3) {
					break;

				} else if (numberChoice == 4) {
					exitProgram();

				}
			}

		}
		numberChoice = 0;
	}

	private void exitProgram() {
		// close all connections
		input.closeKeyboard();
		ui.printExitProgram();
		System.exit(0);

	}

}
