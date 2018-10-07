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
	private static final String ACTORS_BY_FILM_ID_SQL = " SELECT a.first_name, a.last_name, f.title, fa.film_id, fa.actor_id\n"
			+ "  FROM actor a JOIN film_actor fa ON a.id = fa.actor_id\n"
			+ "               JOIN film f ON fa.film_id = f.id\n"
			+ "               where fa.film_id = 3\n" + " ORDER BY f.title;";
	private static final String FILM_BY_ID_SQL = "SELECT * FROM film WHERE film.id = ?";
	private static final String FILM_BY_KEYWORD_SQL = "";
	private static final String FILMS_BY_ACTOR_ID_SQL = " SELECT f.*\n"
			+ "  FROM actor a JOIN film_actor fa ON a.id = fa.actor_id\n"
			+ "               JOIN film f ON fa.film_id = f.id\n"
			+ "               where fa.actor_id = 3\n" + " ORDER BY f.title;"
			+ " ORDER BY f.title;";

	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet results;

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
		results = getQuary(FILM_BY_ID_SQL, filmId);

		if (results.next()) {
			film = getFilmFromResult(results);
		}
		closeConnections();

		return film;
	}

	@Override
	public Actor getActorById(int actorId) throws SQLException {
		Actor actor = null;
		results = getQuary(ACTOR_BY_ID_SQL, actorId);

		if (results.next()) {
			actor = getActorFromResult(results);
		}

		closeConnections();
		return actor;
	}

	@Override
	public List<Actor> getActorsByFilmId(int filmId) throws SQLException {
		List<Actor> cast = new ArrayList();
		results = getQuary(ACTORS_BY_FILM_ID_SQL, filmId);

		while (results.next()) {
			cast.add(getActorFromResult(results));
		}

		closeConnections();
		return cast;
	}

	@Override
	public List<Film> getFilmsByActorId(int actorId) throws SQLException {
		List<Film> films = new ArrayList<>();
		results = getQuary(FILMS_BY_ACTOR_ID_SQL, actorId);

		while (results.next()) {
			films.add(getFilmFromResult(results));
		}
		closeConnections();
		return films;
	}

	@Override
	public Film getFilmByKeyword(String keyword) throws SQLException {
		Film film = null;
		results = getQuary(FILM_BY_KEYWORD_SQL, keyword);

		if (results.next()) {
			film = getFilmFromResult(results);
		}
		closeConnections();

		return film;

	}

	// helper methods
	private Film getFilmFromResult(ResultSet filmResult) throws SQLException {

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

	private ResultSet getQuary(String sql, int id) {

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

		} catch (SQLException sqle) {

		} finally {
			try {
				return stmt.executeQuery();

			} catch (SQLException sqle) {
				System.err.println(sqle);
				System.out.println("In getQuary()");
			}
		}
		return null;
	}

	private ResultSet getQuary(String sql, String keyword) {

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, keyword);

		} catch (SQLException sqle) {

		} finally {
			try {
				return stmt.executeQuery();

			} catch (SQLException sqle) {
				System.err.println(sqle);
				System.out.println("In getQuary()");
			}
		}
		return null;
	}

	private void closeConnections() throws SQLException {
		conn.close();
		stmt.close();
		results.close();
	}

	@Override
	public String getLanguageOfFilm(Film film) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCategoryOfFilm(Film film) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInventoryOfFilm(Film film) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}