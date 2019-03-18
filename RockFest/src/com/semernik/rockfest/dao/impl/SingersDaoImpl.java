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
import com.semernik.rockfest.dao.SingersDao;
import com.semernik.rockfest.entity.Singer;
import com.semernik.rockfest.entity.Singer.SingerBuilder;


public class SingersDaoImpl implements SingersDao {

	private static Logger logger = LogManager.getLogger();

	private static SingersDaoImpl instance = new SingersDaoImpl();

	public static SingersDaoImpl getInstance(){
		return instance;
	}

	private SingersDaoImpl(){}

	@Override
	public boolean saveSinger(Singer singer) throws DaoException {
		Connection con = null;
		PreparedStatement st = null;
		boolean result = true;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			con.setAutoCommit(false);
			st = con.prepareStatement(Query.SAVE_SINGER.toString());
			st.setLong(1, singer.getSingerId());
			st.setString(2, singer.getTitle());
			st.setString(3, singer.getDescription());
			st.setDate(4, singer.getAddingDate());
			st.setLong(5, singer.getAuthorId());
			int affectedRows = st.executeUpdate();
			if (affectedRows == 0){
				result = false;
			}
			st.close();
			st = con.prepareStatement(Query.SAVE_DESCRIPTION_EDITOR.toString());
			st.setLong(1, singer.getSingerId());
			st.setLong(2, singer.getDescriptionEditorId());
			affectedRows = st.executeUpdate();
			if (affectedRows == 0){
				result = false;
			}
			if (result == true){
				con.commit();
			}
		} catch (SQLException e) {
			result = false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database access failure while connection rollback", e1);
			}
			throw new DaoException("Failed to save singer", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return result;
	}

	@Override
	public Collection<Singer> findAllSingers() throws DaoException {
		Connection con = null;
		Collection<Singer> singers = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_ALL_SINGERS.toString());
			result = st.executeQuery();
			while (result.next()){
				SingerBuilder builder = new SingerBuilder();
				Singer singer = builder.singerId( result.getLong(1))
						.title(result.getString(2))
						.melodyRating(result.getInt(3))
						.textRating(result.getInt(4))
						.musicRating(result.getInt(5))
						.vocalRating(result.getInt(6))
						.votedUsersCount(result.getInt(7))
						.build();
				singers.add(singer);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find singers", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return singers;
	}

	@Override
	public Optional<Singer> findSingerById(long singerId) throws DaoException {
		Connection con = null;
		Singer singer = null;
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_SINGER_BY_ID.toString());
			st.setLong(1, singerId);
			result = st.executeQuery();
			if (result.next()){
				SingerBuilder builder = new SingerBuilder();
				singer = builder.singerId(singerId)
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
			throw new DaoException("Failed to find singer by id", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.ofNullable(singer);
	}

	@Override
	public boolean updateSingerDescription(long singerId, String description, long authorId) throws DaoException {
		Connection con = null;
		PreparedStatement st = null;
		boolean updated = true;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			con.setAutoCommit(false);
			st = con.prepareStatement(Query.UPDATE_SINGER_DESCRIPTION.toString());
			st.setString(1, description);
			st.setLong(2, singerId);
			int affectedRows = st.executeUpdate();
			if (affectedRows == 0){
				updated = false;
			}
			st.close();
			st = con.prepareStatement(Query.UPDATE_DESCRIPTION_EDITOR.toString());
			st.setLong(1, singerId);
			st.setLong(2, authorId);
			affectedRows = st.executeUpdate();
			if (affectedRows == 0){
				updated = false;
			}
			if (updated == true){
				con.commit();
			}
		} catch (SQLException e) {
			updated = false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database access failure while connection rollback", e1);
			}
			throw new DaoException("Failed to update singer description", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return updated;
	}

	@Override
	public boolean changeSingerTitle(long singerId, String newTitle) throws DaoException {
		Connection con = null;
		boolean changed = false;
		PreparedStatement st = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.UPDATE_SINGER_TITLE.toString());
			st.setString(1, newTitle);
			st.setLong(2, singerId);
			int affectedRows = st.executeUpdate();
			if (affectedRows>0){
				changed = true;
			}
		} catch (SQLException e) {
			changed = false;
			throw new DaoException("Fail to change singer title", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return changed;
	}

	private enum Query {

		FIND_ALL_SINGERS ("SELECT singerId, title, melodyRating, textRating, musicRating, vocalRating, votedUsersCount FROM Singers;"),
		FIND_SINGER_BY_ID("SELECT title, description, addingDate, authorId, editorId, "
				+ "(SELECT login FROM Users WHERE userId=authorId) AS author, "
				+ "(SELECT login FROM Users WHERE userId=editorId) AS editor, melodyRating, "
				+ "textRating, musicRating, vocalRating, votedUsersCount FROM Singers JOIN "
				+ "singers_has_descriptionEditors USING(singerId) WHERE singers.singerId=?;"),
		SAVE_SINGER("INSERT INTO Singers (singerId, title, description, "
				+ "addingDate, authorId) VALUES (?,?,?,?,?);"),
		SAVE_DESCRIPTION_EDITOR ("INSERT INTO Singers_has_DescriptionEditors VALUES (?,?)"),
		UPDATE_SINGER_DESCRIPTION("UPDATE Singers SET description=? WHERE singerId=?;"),
		UPDATE_DESCRIPTION_EDITOR("UPDATE Singers_has_DescriptionEditors SET editorId=? WHERE singerId=?;"),
		UPDATE_SINGER_TITLE("UPDATE Singers SET title=? WHERE singerId=?;")
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
