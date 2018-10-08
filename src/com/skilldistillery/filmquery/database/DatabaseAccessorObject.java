package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private static final String USERNAME = "student";
	private static final String PASSWORD = "student";
	private static final String ACTOR_BY_ID_SQL = "SELECT * FROM actor WHERE actor.id = ?";

	private static final String ACTORS_BY_FILM_ID_SQL = " SELECT a.*, f.*"
			+ "  FROM actor a JOIN film_actor fa ON a.id = fa.actor_id\n"
			+ "  JOIN film f ON fa.film_id = f.id\n"
			+ "  WHERE fa.film_id = ?;";

	private static final String FILM_BY_ID_SQL = "SELECT f.*, l.name FROM film f "
			+ "JOIN language l on f.language_id = l.id  " + "WHERE f.id = ?";
	private static final String FILM_BY_KEYWORD_SQL = "select distinct f.*, l.* from film f "
			+ "join language l on f.language_id = l.id "
			+ "join film_actor fa on fa.film_id = f.id "
			+ "join actor a on a.id = fa.actor_id "
			+ "where f.title like ? or f.description like ?";
	private static final String FILMS_BY_ACTOR_ID_SQL = " SELECT f.*, a.*, l.*"
			+ "  FROM actor a JOIN film_actor fa ON a.id = fa.actor_id\n"
			+ "  JOIN film f ON fa.film_id = f.id\n"
			+ "	 JOIN language l ON f.language_id = l.id\n"
			+ "  WHERE fa.actor_id = ?\n";

	// static initializer
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("In static initializer");
		}
	}

	@Override
	public Film getFilmById(int filmId) throws SQLException {
		Film film = null;

		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		PreparedStatement stmt = conn.prepareStatement(FILM_BY_ID_SQL);
		stmt.setInt(1, filmId);

		ResultSet results = stmt.executeQuery();

		if (results.next()) {
			film = new Film();

			film.setId(results.getInt("id"));
			film.setTitle(results.getString("title"));
			film.setDescription(results.getString("description"));
			film.setReleaseYear(results.getInt("release_year"));
			film.setLanguageId(results.getInt("language_id"));
			film.setRentalDuration(results.getInt("rental_duration"));
			film.setRentalRate(results.getDouble("rental_rate"));
			film.setLength(results.getInt("length"));
			film.setReplacementCost(results.getDouble("replacement_cost"));
			film.setRating(results.getString("rating"));
			film.setSpecialFeatures(results.getString("special_features"));
			film.setCast(getActorsByFilmId(film.getId()));
			film.setLanguage(results.getString("name"));
		}
		conn.close();
		stmt.close();
		results.close();

		return film;
	}

	@Override
	public Actor getActorById(int actorId) throws SQLException {
		Actor actor = null;
		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		PreparedStatement stmt = conn.prepareStatement(ACTOR_BY_ID_SQL);
		stmt.setInt(1, actorId);

		ResultSet results = stmt.executeQuery();

		if (results.next()) {
			actor = new Actor();

			actor.setfName(results.getString("first_name"));
			actor.setlName(results.getString("last_name"));
			actor.setId(results.getInt("id"));
			actor.setFilms(getFilmsByActorId(actor.getId()));

			return actor;
		}

		conn.close();
		stmt.close();
		results.close();
		return actor;
	}

	@Override
	public List<Actor> getActorsByFilmId(int filmId) throws SQLException {
		List<Actor> cast = new ArrayList();
		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		PreparedStatement stmt = conn.prepareStatement(ACTORS_BY_FILM_ID_SQL);
		stmt.setInt(1, filmId);

		ResultSet results = stmt.executeQuery();

		while (results.next()) {
			Actor actor = new Actor();

			actor.setfName(results.getString("first_name"));
			actor.setlName(results.getString("last_name"));
			actor.setId(results.getInt("id"));
			cast.add(actor);
		}

		conn.close();
		stmt.close();
		results.close();
		return cast;
	}

	@Override
	public List<Film> getFilmsByActorId(int actorId) throws SQLException {
		List<Film> films = new ArrayList<>();
		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		PreparedStatement stmt = conn.prepareStatement(FILMS_BY_ACTOR_ID_SQL);
		stmt.setInt(1, actorId);

		ResultSet results = stmt.executeQuery();

		while (results.next()) {

			Film film = new Film();

			film.setId(results.getInt("id"));
			film.setTitle(results.getString("title"));
			film.setDescription(results.getString("description"));
			film.setReleaseYear(results.getInt("release_year"));
			film.setLanguageId(results.getInt("language_id"));
			film.setRentalDuration(results.getInt("rental_duration"));
			film.setRentalRate(results.getDouble("rental_rate"));
			film.setLength(results.getInt("length"));
			film.setReplacementCost(results.getDouble("replacement_cost"));
			film.setRating(results.getString("rating"));
			film.setSpecialFeatures(results.getString("special_features"));
			film.setCast(getActorsByFilmId(film.getId()));
			film.setLanguage(results.getString("name"));
			films.add(film);;
		}
		conn.close();
		stmt.close();
		results.close();
		return films;
	}

	@Override
	public List<Film> getFilmByKeyword(String keyword) throws SQLException {
		List<Film> films = new ArrayList<>();

		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		PreparedStatement stmt = conn.prepareStatement(FILM_BY_KEYWORD_SQL);
		stmt.setString(1, "%" + keyword + "%");
		stmt.setString(2, "%" + keyword + "%");

		ResultSet results = stmt.executeQuery();

		while (results.next()) {
			Film film = new Film();

			film.setId(results.getInt("id"));
			film.setTitle(results.getString("title"));
			film.setDescription(results.getString("description"));
			film.setReleaseYear(results.getInt("release_year"));
			film.setLanguageId(results.getInt("language_id"));
			film.setRentalDuration(results.getInt("rental_duration"));
			film.setRentalRate(results.getDouble("rental_rate"));
			film.setLength(results.getInt("length"));
			film.setReplacementCost(results.getDouble("replacement_cost"));
			film.setRating(results.getString("rating"));
			film.setSpecialFeatures(results.getString("special_features"));
			film.setCast(getActorsByFilmId(film.getId()));
			film.setLanguage(results.getString("name"));
			films.add(film);;
		}
		conn.close();
		stmt.close();
		results.close();
		return films;

	}

}