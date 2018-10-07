package com.skilldistillery.filmquery.database;

import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.FilmQueryUI;

public class Test {

	private static FilmQueryUI ui = new FilmQueryUI();
	private static int numberChoice;
	private static String textChoice;
	
	
	public static void main(String[] args) {
		Test t = new Test();
		t.launchApp();
	}

	public void launchApp() {

		while (true) {

			numberChoice = ui.mainMenuPrompt();

			if (numberChoice == 1) {
				launchSearchFilmById();
			} else if (numberChoice == 2) {
				launchSearchFilmByKeyword();
			} else if (numberChoice == 3) {
				exitProgram();
			}

		}

	}

	private void launchSearchFilmByKeyword() {
		while (true) {
			textChoice = ui.searchFilmByKeywordPrompt();

			if(textChoice.indexOf("-1") >=0) {
				break;
			}
			// look up film and print or can't be found

			boolean filmFound = true;

			if (filmFound) {
				// print film

				System.out.println("Movie found!");

				numberChoice = ui.moreOptionsPrompt();

				if (numberChoice == 1) {
//					ui.printFilmAllDetails(film);
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

	private void launchSearchFilmById() {
		while (true) {

			numberChoice = ui.searchFilmByIdPrompt();
		
			// cancel's search and returns user to main menu
			if (numberChoice == -1) {
				break;
			}

			// user num to get film

			boolean filmFound = numberChoice >= 1 && numberChoice <= 3
					? true
					: false;

			if (!filmFound) {
				ui.printFilmNotFound();
				continue;
			} else if (filmFound) {
				
				List<Actor> cast = new ArrayList<>();
				cast.add(new Actor(1, "johnny", "bravo", null));
				cast.add(new Actor(1, "mojo", "jojo", null));
				cast.add(new Actor(1, "brad", "pitt", null));
				cast.add(new Actor(1, "stephanie", "girlfaces", null));
				String description = "This is a long description to test out the UI methods are working properly. if the UI class is working properly, than this will display nicely in a bordered UI. just a little randomness for extra measure";
				Film film = new Film(1, "balls", description, 2013, 1, 1, 1, 1, 1, "PG-13", "Director commentary", cast);
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
