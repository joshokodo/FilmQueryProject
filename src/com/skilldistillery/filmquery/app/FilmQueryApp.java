package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.FilmQueryUI;

public class FilmQueryApp {

	private DatabaseAccessor db;
	private FilmQueryUI ui;
	private int numberChoice;
	private String textChoice;

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		// app.launchApp();

		app.launchApp();

	}

	public FilmQueryApp() {
		db = new DatabaseAccessorObject();
		ui = new FilmQueryUI();
		numberChoice = 0;
		textChoice = "";
	}

	private void test() throws SQLException {
		Film film = db.getFilmById(1);
		Actor actor = db.getActorById(1);
		System.out.println(film);
		System.out.println(actor);
	}

	public void launchApp() {

		while (true) {

			numberChoice = ui.mainMenuPrompt();

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
					exitProgram();
					break;

				default :
					System.out.println(
							"this shouldn't be happeneing. in default case of launchApp()");
					break;
			}
		}

	}

	private void launchSearchFilmByKeyword() throws SQLException {

		while (true) {

			textChoice = ui.searchFilmByKeywordPrompt();

			if (textChoice.indexOf("-1") >= 0) {
				break;
			}

			List<Film> films = db.getFilmByKeyword(textChoice);
			boolean filmFound = films != null ? true : false;

			if (!filmFound) {
				ui.printFilmNotFound();
				continue;

			} else if (filmFound) {
				
				for (Film film : films) {
					ui.printFilmSimpleDetails(film);

				}
				numberChoice = ui.moreOptionsPrompt();

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

			numberChoice = ui.searchFilmByIdPrompt();

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
				numberChoice = ui.moreOptionsPrompt();

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
		ui.closeKeyboard();
		ui.printExitProgram();
		System.exit(0);

	}

}
