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
import com.semernik.rockfest.dao.CompositionsDao;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.entity.Composition;
import com.semernik.rockfest.entity.Composition.CompositionBuilder;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.entity.Genre.GenreBuilder;
import com.semernik.rockfest.entity.Link;

public class CompositionsDaoImpl implements CompositionsDao{

	private static Logger logger = LogManager.getLogger();

	private static CompositionsDaoImpl instance = new CompositionsDaoImpl();

	public static CompositionsDaoImpl getInstance(){
		return instance;
	}

	private CompositionsDaoImpl(){}


	@Override
	public Optional<Composition> findCompositionById(long compositionId) throws DaoException {
		Connection con = null;
		Composition composition = null;
		Collection<Genre> genres = new LinkedList<>();
		Collection<Link> links = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_COMPOSITION_BY_ID.toString());
			st.setLong(1, compositionId);
			result = st.executeQuery();
			result.next();
			CompositionBuilder builder = new CompositionBuilder();
			builder.compositionTitle(result.getString(ColumnName.COMPOSITION_TITLE.toString()))
					.compositionId(compositionId)
					.year(result.getString(ColumnName.YEAR.toString()))
					.addingDate(result.getDate(ColumnName.COMPOSITION_ADDING_DATE.toString()))
					.melodyRating(result.getInt(ColumnName.MELODY_RATING.toString()))
					.textRating(result.getInt(ColumnName.TEXT_RATING.toString()))
					.musicRating(result.getInt(ColumnName.MUSIC_RATING.toString()))
					.vocalRating(result.getInt(ColumnName.VOCAL_RATING.toString()))
					.votedUsersCount(result.getInt(ColumnName.VOTED_USERS_COUNT.toString()))
					.authorId(result.getLong(ColumnName.AUTHOR_ID.toString()))
					.yearEditorId(result.getLong(ColumnName.YEAR_EDITOR_ID.toString()))
					.genreEditorId(result.getLong(ColumnName.GENRE_EDITOR_ID.toString()))
					.author(result.getString(ColumnName.AUTHOR.toString()))
					.yearEditor(result.getString(ColumnName.YEAR_EDITOR.toString()))
					.genreEditor(result.getString(ColumnName.COMPOSITION_TITLE.toString()))
					.singerId(result.getLong(ColumnName.SINGER_ID.toString()))
					.singerTitle(result.getString(ColumnName.SINGER.toString()));
			st = con.prepareStatement(Query.FIND_COMPOSITION_GENRES_BY_ID.toString());
			st.setLong(1, compositionId);
			result = st.executeQuery();
			while (result.next()){
				GenreBuilder genreBuilder = new GenreBuilder();
				Genre genre = genreBuilder.genreId(result.getLong(1))
						.title( result.getString(2))
						.build();
				genres.add(genre);
			}
			composition = builder.genres(genres).build();
		} catch (SQLException e) {
			composition = null;
			throw new DaoException("Failed to find composition by id", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.ofNullable(composition);
	}

	@Override
	public boolean saveComposition(Composition composition) throws DaoException {
		Connection con = null;
		PreparedStatement saver = null;
		boolean saved = true;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			con.setAutoCommit(false);
			saver = con.prepareStatement(Query.SAVE_COMPOSITION.toString());
			saver.setLong(1, composition.getCompositionId());
			saver.setString(2, composition.getTitle());
			saver.setLong(3, composition.getSingerId());
			saver.setString(4, composition.getYear());
			saver.setDate(5, composition.getAddingDate());
			saver.setLong(6, composition.getAuthorId());
			int affectedRows = saver.executeUpdate();
			if (affectedRows == 0){
				saved = false;
			}
			saver.close();
			Collection<Long> genresIds = composition.getGenresIds();
			saver = con.prepareStatement(Query.SAVE_COMPOSITION_GENRE.toString());
			saver.setLong(1, composition.getCompositionId());
			for (Long genreId : genresIds){
				saver.setLong(2, genreId);
				saver.addBatch();
			}
			int [] affectedRowsArray = saver.executeBatch();
			for (int affected : affectedRowsArray){
				if (affected == 0){
					saved = false;
				}
			}
			saver = con.prepareStatement(Query.SAVE_YEAR_EDITOR.toString());
			saver.setLong(1, composition.getCompositionId());
			saver.setLong(2, composition.getYearEditorId());
			affectedRows = saver.executeUpdate();
			if (affectedRows == 0){
				saved = false;
			}
			saver = con.prepareStatement(Query.SAVE_GENRE_EDITOR.toString());
			saver.setLong(1, composition.getCompositionId());
			saver.setLong(2, composition.getGenreEditorId());
			affectedRows = saver.executeUpdate();
			if (affectedRows == 0){
				saved = false;
			}
			if (saved == true){
				con.commit();
			} else {
				try {
					con.rollback();
				} catch (SQLException e1) {
					logger.error("Database access failure while connection rollback", e1);
				}
			}
		} catch (SQLException e) {
			saved = false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database access failure while connection rollback", e1);
			}
			throw new DaoException("Fail to save composition", e);
		} finally {
			closeStatement(saver);
			closeConnection(con);
		}
		return saved;
	}

	@Override
	public Collection<Composition> findAllCompositions() throws DaoException {
		Connection con = null;
		Collection<Composition> compositions = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_ALL_COMPOSITIONS.toString());
			result = st.executeQuery();
			while (result.next()){
				CompositionBuilder builder = new CompositionBuilder();
				Composition composition = builder.compositionId(result.getLong(1))
						.compositionTitle(result.getString(2))
						.melodyRating(result.getInt(3))
						.textRating(result.getInt(4))
						.musicRating(result.getInt(5))
						.vocalRating(result.getInt(6))
						.votedUsersCount(result.getInt(7))
						.build();
				compositions.add(composition);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find compositions", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return compositions;
	}

	@Override
	public Collection<Composition> findSingerCompositions(long singerId) throws DaoException {
		Connection con = null;
		Collection<Composition> compositions = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_SINGER_COMPOSITIONS.toString());
			st.setLong(1, singerId);
			result = st.executeQuery();
			while (result.next()){
				CompositionBuilder builder = new CompositionBuilder();
				Composition composition = builder.compositionId(result.getLong(1))
						.compositionTitle(result.getString(2))
						.melodyRating(result.getInt(3))
						.textRating(result.getInt(4))
						.musicRating(result.getInt(5))
						.vocalRating(result.getInt(6))
						.votedUsersCount(result.getInt(7))
						.build();
				compositions.add(composition);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find compositions", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return compositions;
	}

	@Override
	public Collection<Composition> findGenreCompositions(long singerId) throws DaoException {
		Connection con = null;
		Collection<Composition> compositions = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_GENRE_COMPOSITIONS.toString());
			st.setLong(1, singerId);
			result = st.executeQuery();
			while (result.next()){
				CompositionBuilder builder = new CompositionBuilder();
				Composition composition = builder.compositionId(result.getLong(1))
						.compositionTitle(result.getString(2))
						.melodyRating(result.getInt(3))
						.textRating(result.getInt(4))
						.musicRating(result.getInt(5))
						.vocalRating(result.getInt(6))
						.votedUsersCount(result.getInt(7))
						.build();
				compositions.add(composition);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find compositions", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return compositions;
	}

	@Override
	public boolean changeCompositionGenres(long compositionId, Collection<Long> genresIds, long authorId) throws DaoException {
		Connection con = null;
		PreparedStatement saver = null;
		boolean changed = true;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			con.setAutoCommit(false);
			saver = con.prepareStatement(Query.DELETE_COMPOSITION_GENRES.toString());
			saver.setLong(1, compositionId);
			int affectedRows = saver.executeUpdate();
			if (affectedRows == 0){
				changed = false;
			}
			saver.close();
			saver = con.prepareStatement(Query.SAVE_COMPOSITION_GENRE.toString());
			saver.setLong(1, compositionId);
			for (Long genreId : genresIds){
				saver.setLong(2, genreId);
				saver.addBatch();
			}
			int [] affectedRowsArray = saver.executeBatch();
			for (int affected : affectedRowsArray){
				if (affected == 0){
					changed = false;
				}
			}
			saver = con.prepareStatement(Query.UPDATE_GENRE_EDITOR.toString());
			saver.setLong(1, authorId);
			saver.setLong(2, compositionId);
			affectedRows = saver.executeUpdate();
			if (affectedRows == 0){
				changed = false;
			}
			if (changed == true){
				con.commit();
			} else {
				try {
					con.rollback();
				} catch (SQLException e1) {
					logger.error("Database access failure while connection rollback", e1);
				}
			}
		} catch (SQLException e) {
			changed = false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database access failure while connection rollback", e1);
			}
			throw new DaoException("Fail to save composition", e);
		} finally {
			closeStatement(saver);
			closeConnection(con);
		}
		return changed;
	}

	@Override
	public boolean changeCompositionYear(long compositionId, String year, long authorId) throws DaoException {
		Connection con = null;
		PreparedStatement saver = null;
		boolean changed = true;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			con.setAutoCommit(false);
			saver = con.prepareStatement(Query.UPDATE_COMPOSITION_YEAR.toString());
			saver.setString(1, year);
			saver.setLong(2, compositionId);
			int affectedRows = saver.executeUpdate();
			if (affectedRows == 0){
				changed = false;
			}
			saver.close();
			saver = con.prepareStatement(Query.UPDATE_YEAR_EDITOR.toString());
			saver.setLong(1, authorId);
			saver.setLong(2, compositionId);
			affectedRows = saver.executeUpdate();
			if (affectedRows == 0){
				changed = false;
			}
			if (changed == true){
				con.commit();
			} else {
				try {
					con.rollback();
				} catch (SQLException e1) {
					logger.error("Database access failure while connection rollback", e1);
				}
			}
		} catch (SQLException e) {
			changed = false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database access failure while connection rollback", e1);
			}
			throw new DaoException("Fail to update composition year", e);
		} finally {
			closeStatement(saver);
			closeConnection(con);
		}
		return changed;
	}

	@Override
	public boolean changeCompositionTitle(long compositionId, String newTitle) throws DaoException {
		Connection con = null;
		boolean changed = false;
		PreparedStatement st = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.UPDATE_COMPOSITION_TITLE.toString());
			st.setString(1, newTitle);
			st.setLong(2, compositionId);
			int affectedRows = st.executeUpdate();
			if (affectedRows>0){
				changed = true;
			}
		} catch (SQLException e) {
			changed = false;
			throw new DaoException("Fail to change composition title", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return changed;
	}



	private enum Query {

		FIND_COMPOSITION_BY_ID ("SELECT compositions.title, year, compositions.addingDate, compositions.melodyRating, "
				+ "compositions.textRating, compositions.musicRating, compositions.vocalRating, "
				+ "compositions.votedUsersCount, compositions.authorId, yearEditorId, genreEditorId, "
				+ "(SELECT login FROM Users WHERE userId=compositions.authorId) AS author, "
				+ "(SELECT login FROM Users WHERE userId=yearEditorId) AS yearEditor, "
				+ "(SELECT login FROM Users WHERE userId=genreEditorId) AS genreEditor,"
				+ "compositions.singerId, singers.title FROM Compositions "
				+ "JOIN singers USING (singerId) JOIN Compositions_has_YearEditor USING (compositionId) "
				+ "JOIN Compositions_has_GenresEditor USING (compositionId) WHERE compositions.compositionId=?;"),
		FIND_COMPOSITION_GENRES_BY_ID("SELECT genreId, title FROM Genres JOIN compositions_has_genres "
				+ "USING (genreId) WHERE compositionId=?;"),
		FIND_COMPOSITION_LINKS ("SELECT linkId, content, date, authorId, login FROM CompositionLinks "
				+ "JOIN Users ON authorId=userId WHERE compositionId=?;"),
		FIND_ALL_COMPOSITIONS ("SELECT compositionId, title, melodyRating, textRating, musicRating, vocalRating, votedUsersCount "
				+ "FROM compositions;"),
		FIND_SINGER_COMPOSITIONS ("SELECT compositionId, title, melodyRating, textRating, musicRating, vocalRating, votedUsersCount "
				+ "FROM compositions WHERE singerId=?;"),
		FIND_GENRE_COMPOSITIONS ("SELECT compositionId, title, melodyRating, textRating, musicRating, vocalRating, votedUsersCount "
				+ "FROM Compositions JOIN Compositions_has_Genres USING (compositionId) where genreId=?;"),
		SAVE_COMPOSITION("INSERT INTO Compositions (compositionId, title, singerId, year, "
				+ "addingDate, authorId) VALUES (?,?,?,?,?,?);"),
		SAVE_YEAR_EDITOR ("INSERT INTO Compositions_has_YearEditor(compositionId, yearEditorId) VALUES (?,?);"),
		UPDATE_COMPOSITION_YEAR("UPDATE Compositions SET year=? WHERE compositionId=?;"),
		UPDATE_YEAR_EDITOR ("UPDATE Compositions_has_YearEditor SET yearEditorId=? WHERE compositionId=?;"),
		SAVE_GENRE_EDITOR ("INSERT INTO Compositions_has_GenresEditor(compositionId, genreEditorId) VALUES (?,?);"),
		UPDATE_GENRE_EDITOR ("UPDATE Compositions_has_GenresEditor SET genreEditorId=? WHERE compositionId=?;"),
		SAVE_COMPOSITION_GENRE("INSERT INTO Compositions_has_Genres(compositionId, genreId) VALUES (?,?);"),
		UPDATE_COMPOSITION_TITLE("UPDATE Compositions SET title=? WHERE compositionId=?;"),
		DELETE_COMPOSITION_GENRES ("DELETE FROM Compositions_has_Genres WHERE compositionId=?;")
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


	private enum ColumnName {
		COMPOSITION_TITLE ("compositions.title"),
		YEAR("year"),
		COMPOSITION_ADDING_DATE("compositions.addingDate"),
		MELODY_RATING("compositions.melodyRating"),
		TEXT_RATING("compositions.textRating"),
		MUSIC_RATING("compositions.musicRating"),
		VOCAL_RATING("compositions.vocalRating"),
		VOTED_USERS_COUNT("compositions.votedUsersCount"),
		AUTHOR_ID("authorId"),
		YEAR_EDITOR_ID("yearEditorId"),
		GENRE_EDITOR_ID("genreEditorId"),
		AUTHOR("author"),
		YEAR_EDITOR("yearEditor"),
		GENRE_EDITOR("genreEditor"),
		SINGER_ID("compositions.singerId"),
		SINGER("singers.title"),
		TITLE("title"),
		GENRE_ID("genreId"),
		;

		private String name;

		ColumnName(String name){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}


}
