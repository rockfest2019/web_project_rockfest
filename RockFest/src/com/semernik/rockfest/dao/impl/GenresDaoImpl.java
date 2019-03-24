package com.semernik.rockfest.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.connection.ConnectionPool;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.GenresDao;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.entity.Genre.GenreBuilder;


public class GenresDaoImpl implements GenresDao {

	private static Logger logger = LogManager.getLogger();

	private static GenresDaoImpl instance = new GenresDaoImpl();

	public static GenresDaoImpl getInstance(){
		return instance;
	}

	private GenresDaoImpl(){}

	@Override
	public Collection<Genre> findGenresByCompositionId(long compositionId) throws DaoException {
		Connection con = null;
		Collection<Genre> genres = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_COMPOSITION_GENRES.toString());
			st.setLong(1, compositionId);
			result = st.executeQuery();
			while (result.next()){
				GenreBuilder builder = new GenreBuilder();
				Genre genre = builder.genreId(result.getLong(1))
						.title( result.getString(2))
						.build();
				genres.add(genre);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find genres by composition id", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return genres;
	}

	@Override
	public boolean saveGenre(Genre genre) throws DaoException {
		Connection con = null;
		PreparedStatement st = null;
		boolean result = true;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			con.setAutoCommit(false);
			st = con.prepareStatement(Query.SAVE_GENRE.toString());
			st.setLong(1, genre.getGenreId());
			st.setString(2, genre.getTitle());
			st.setString(3, genre.getDescription());
			st.setDate(4, genre.getAddingDate());
			st.setLong(5, genre.getAuthorId());
			int affectedRows = st.executeUpdate();
			if (affectedRows == 0){
				result = false;
			}
			st.close();
			st = con.prepareStatement(Query.SAVE_DESCRIPTION_EDITOR.toString());
			st.setLong(1, genre.getGenreId());
			st.setLong(2, genre.getDescriptionEditorId());
			affectedRows = st.executeUpdate();
			if (affectedRows == 0){
				result = false;
			}
			if (result == true){
				con.commit();
			} else {
				try {
					con.rollback();
				} catch (SQLException e1) {
					logger.error("Database access failure while connection rollback", e1);
				}
			}
		} catch (SQLException e) {
			result = false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database access failure while connection rollback", e1);
			}
			throw new DaoException("Failed to save genre", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return result;
	}

	@Override
	public Collection<Genre> findAllGenres() throws DaoException {
		Connection con = null;
		Collection<Genre> genres = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_ALL_GENRES.toString());
			result = st.executeQuery();
			while (result.next()){
				GenreBuilder builder = new GenreBuilder();
				Genre genre = builder.genreId( result.getLong(1))
						.title(result.getString(2))
						.melodyRating(result.getInt(3))
						.textRating(result.getInt(4))
						.musicRating(result.getInt(5))
						.vocalRating(result.getInt(6))
						.votedUsersCount(result.getInt(7))
						.build();
				genres.add(genre);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find genres", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return genres;
	}

	@Override
	public Collection<Genre> findGenres(int position, int elementsCount) throws DaoException {
		Connection con = null;
		Collection<Genre> genres = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_GENRES.toString());
			st.setInt(1, position);
			st.setInt(2, elementsCount);
			result = st.executeQuery();
			while (result.next()){
				GenreBuilder builder = new GenreBuilder();
				Genre genre = builder.genreId( result.getLong(1))
						.title(result.getString(2))
						.melodyRating(result.getInt(3))
						.textRating(result.getInt(4))
						.musicRating(result.getInt(5))
						.vocalRating(result.getInt(6))
						.votedUsersCount(result.getInt(7))
						.build();
				genres.add(genre);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find genres", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return genres;
	}

	@Override
	public Optional<Genre> findGenreById(long genreId) throws DaoException {
		Connection con = null;
		Genre genre = null;
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_GENRE_BY_ID.toString());
			st.setLong(1, genreId);
			result = st.executeQuery();
			if (result.next()){
				GenreBuilder builder = new GenreBuilder();
				genre = builder.genreId(genreId)
						.title(result.getString(1))
						.description(result.getString(2))
						.addingDate(result.getDate(3))
						.authorId(result.getLong(4))
						.descriptionEditorId(result.getLong(5))
						.authorTitle(result.getString(6))
						.descriptionEditorTitle(result.getString(7))
						.melodyRating(result.getInt(8))
						.textRating(result.getInt(9))
						.musicRating(result.getInt(10))
						.vocalRating(result.getInt(11))
						.votedUsersCount(result.getInt(12))
						.build();
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find genres", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.ofNullable(genre);
	}

	@Override
	public boolean updateGenreDescription(long genreId, String description, long authorId) throws DaoException {
		Connection con = null;
		PreparedStatement st = null;
		boolean updated = true;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			con.setAutoCommit(false);
			st = con.prepareStatement(Query.UPDATE_GENRE_DESCRIPTION.toString());
			st.setString(1, description);
			st.setLong(2, genreId);
			int affectedRows = st.executeUpdate();
			if (affectedRows == 0){
				updated = false;
			}
			st.close();
			st = con.prepareStatement(Query.UPDATE_DESCRIPTION_EDITOR.toString());
			st.setLong(1, genreId);
			st.setLong(2, authorId);
			affectedRows = st.executeUpdate();
			if (affectedRows == 0){
				updated = false;
			}
			if (updated == true){
				con.commit();
			} else {
				try {
					con.rollback();
				} catch (SQLException e1) {
					logger.error("Database access failure while connection rollback", e1);
				}
			}
		} catch (SQLException e) {
			updated = false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database access failure while connection rollback", e1);
			}
			throw new DaoException("Failed to update genre description", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return updated;
	}

	@Override
	public boolean changeGenreTitle(long genreId, String newTitle) throws DaoException {
		Connection con = null;
		boolean changed = false;
		PreparedStatement st = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.UPDATE_GENRE_TITLE.toString());
			st.setString(1, newTitle);
			st.setLong(2, genreId);
			int affectedRows = st.executeUpdate();
			if (affectedRows>0){
				changed = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to change genre title", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return changed;
	}

	private enum Query {

		FIND_COMPOSITION_GENRES ("SELECT genreId, title FROM Genres JOIN compositions_has_genres "
				+ "USING (genreId) WHERE compositionId=?;"),
		FIND_ALL_GENRES ("SELECT genreId, title, melodyRating, textRating, musicRating, vocalRating, votedUsersCount FROM Genres;"),
		FIND_GENRES ("SELECT genreId, title, melodyRating, textRating, musicRating, vocalRating, votedUsersCount FROM Genres"
				+ " LIMIT ?, ?;"),
		FIND_GENRE_BY_ID("SELECT title, description, addingDate, authorId, editorId, "
				+ "(SELECT login FROM Users WHERE userId=authorId) AS author, "
				+ "(SELECT login FROM Users WHERE userId=editorId) AS editor, melodyRating, "
				+ "textRating, musicRating, vocalRating, votedUsersCount FROM Genres JOIN "
				+ "Genres_has_DescriptionEditors Using(genreId) WHERE genreId=?;"),
		SAVE_GENRE("INSERT INTO Genres (genreId, title, description, "
				+ "addingDate, authorId) VALUES (?,?,?,?,?);"),
		SAVE_DESCRIPTION_EDITOR ("INSERT INTO Genres_has_DescriptionEditors VALUES (?,?)"),
		UPDATE_GENRE_DESCRIPTION("UPDATE Genres SET description=? WHERE genreId=?;"),
		UPDATE_DESCRIPTION_EDITOR("UPDATE Genres_has_DescriptionEditors SET editorId=? WHERE genreId=?;"),
		UPDATE_GENRE_TITLE("UPDATE Genres SET title=? WHERE genreId=?;")
		;

		private String query;

		Query (String query){
			this.query = query;
		}

		@Override
		public String toString(){
			return query;
		}

	}


}
