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
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet results;

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
		String sql = "SELECT * FROM film WHERE film.id = ?";

		results = getQuary(sql, 1, filmId);

		if (results.next()) {
			film = getFilmFromResult(results);
		}
		conn.close();
		stmt.close();
		results.close();

		return film;
	}

	@Override
	public Actor getActorById(int actorId) throws SQLException {
		Actor actor = null;
		String sql = "SELECT * FROM actor WHERE actor.id = ?";
		results = getQuary(sql, 1, actorId);

		if (results.next()) {
			actor = getActorFromResult(results);
		}
		conn.close();
		stmt.close();
		results.close();
		return actor;
	}

	@Override
	public List<Actor> getActorsByFilmId(int filmId) throws SQLException {
		List<Actor> cast = new ArrayList();

		String sql = "SELECT id, first_name, last_name FROM actor JOIN film_actor ON actor.id = film_actor.film_id "
				+ " WHERE film_id = ?";
		results = getQuary(sql, 1, filmId);

		while (results.next()) {
			cast.add(getActorFromResult(results));
		}

		return cast;
	}

	@Override
	public List<Film> getFilmsByActorId(int actorId) throws SQLException {
		List<Film> films = new ArrayList<>();

		String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
		sql += " rental_rate, length, replacement_cost, rating, special_features "
				+ " FROM film JOIN film_actor ON film.id = film_actor.film_id "
				+ " WHERE actor_id = ?";

		results = getQuary(sql, 1, actorId);

		while (results.next()) {
			films.add(getFilmFromResult(results));
		}

		return films;
	}

	public Film getFilmFromResult(ResultSet filmResult) throws SQLException {

		if (filmResult == null) {
			return null;
		}

		Film film = new Film();

		film.setId(filmResult.getInt("id"));
		film.setTitle(filmResult.getString("title"));
		film.setDescription(filmResult.getString("description"));
		film.setReleaseYear(filmResult.getInt("release_year"));
		film.setLanguageId(filmResult.getInt("language_id"));
		film.setRentalDuration(filmResult.getInt("rental_duration"));
		film.setRentalRate(filmResult.getDouble("rental_rate"));
		film.setLength(filmResult.getInt("length"));
		film.setReplacementCost(filmResult.getDouble("replacement_cost"));
		film.setRating(filmResult.getString("rating"));
		film.setSpecialFeatures(filmResult.getString("special_features"));
		film.setCast(getActorsByFilmId(film.getId()));

		return film;
	}

	public Actor getActorFromResult(ResultSet actorResult) throws SQLException {

		if (actorResult == null) {
			return null;
		}

		Actor actor = new Actor();

		actor.setfName(actorResult.getString("first_name"));
		actor.setlName(actorResult.getString("last_name"));
		actor.setId(actorResult.getInt("id"));
		actor.setFilms(getFilmsByActorId(actor.getId()));

		return actor;
	}

	public ResultSet getQuary(String sql, int numOfClauses, int id) {

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(numOfClauses, id);

		} catch (SQLException sqle) {

		} finally {
			try {

				// if (stmt != null) {
				// stmt.close();
				// }
				// if (conn != null) {
				// conn.close();
				// }
				return stmt.executeQuery();

			} catch (SQLException sqle) {
				System.err.println(sqle);
				System.out.println("In getQuary()");
			}
		}
		return null;
	}

}
