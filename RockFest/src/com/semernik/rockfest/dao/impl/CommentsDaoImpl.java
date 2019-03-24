package com.semernik.rockfest.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import com.semernik.rockfest.connection.ConnectionPool;
import com.semernik.rockfest.dao.CommentsDao;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.entity.Comment;


public class CommentsDaoImpl implements CommentsDao {

	private static CommentsDaoImpl instance = new CommentsDaoImpl();

	public static CommentsDaoImpl getInstance(){
		return instance;
	}

	private CommentsDaoImpl(){}

	@Override
	public Collection<Comment> findCompositionCommentsByCompositionId(long compositionId) throws DaoException{
		Connection con = null;
		Collection<Comment> comments = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_COMPOSITION_COMMENTS.toString());
			st.setLong(1, compositionId);
			result = st.executeQuery();
			while (result.next()){
				long commentId = result.getLong(1);
				String content = result.getString(2);
				Date date = result.getDate(3);
				long authorId = result.getLong(4);
				String authorLogin = result.getString(5);
				comments.add(new Comment(commentId, content, date, authorId, authorLogin, compositionId));
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to find comments by composition id", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return comments;
	}

	@Override
	public Collection<Comment> findSingerCommentsBySingerId(long singerId) throws DaoException {
		Connection con = null;
		Collection<Comment> comments = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_SINGER_COMMENTS.toString());
			st.setLong(1, singerId);
			result = st.executeQuery();
			while (result.next()){
				long commentId = result.getLong(1);
				String content = result.getString(2);
				Date date = result.getDate(3);
				long authorId = result.getLong(4);
				String authorLogin = result.getString(5);
				comments.add(new Comment(commentId, content, date, authorId, authorLogin, singerId));
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to find comments by singer id", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return comments;
	}

	@Override
	public Collection<Comment> findGenreCommentsByGenreId(long genreId) throws DaoException {
		Connection con = null;
		Collection<Comment> comments = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_GENRE_COMMENTS.toString());
			st.setLong(1, genreId);
			result = st.executeQuery();
			while (result.next()){
				long commentId = result.getLong(1);
				String content = result.getString(2);
				Date date = result.getDate(3);
				long authorId = result.getLong(4);
				String authorLogin = result.getString(5);
				comments.add(new Comment(commentId, content, date, authorId, authorLogin, genreId));
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to find comments by genre id", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return comments;
	}

	@Override
	public boolean saveCompositionComment(Comment comment) throws DaoException {
		Connection con = null;
		boolean result = false;
		PreparedStatement st = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.SAVE_COMPOSITION_COMMENT.toString());
			st.setLong(1, comment.getCommentId());
			st.setString(2, comment.getContent());
			st.setDate(3, comment.getDate());
			st.setLong(4, comment.getAuthorId());
			st.setLong(5, comment.getCommentedEntityId());
			int affectedRows = st.executeUpdate();
			if (affectedRows>0){
				result = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to save composition comment", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return result;
	}

	@Override
	public boolean saveSingerComment(Comment comment) throws DaoException {
		Connection con = null;
		boolean result = false;
		PreparedStatement st = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.SAVE_SINGER_COMMENT.toString());
			st.setLong(1, comment.getCommentId());
			st.setString(2, comment.getContent());
			st.setDate(3, comment.getDate());
			st.setLong(4, comment.getAuthorId());
			st.setLong(5, comment.getCommentedEntityId());
			int affectedRows = st.executeUpdate();
			if (affectedRows>0){
				result = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to save singer comment", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return result;
	}

	@Override
	public boolean saveGenreComment(Comment comment) throws DaoException {
		Connection con = null;
		boolean result = false;
		PreparedStatement st = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.SAVE_GENRE_COMMENT.toString());
			st.setLong(1, comment.getCommentId());
			st.setString(2, comment.getContent());
			st.setDate(3, comment.getDate());
			st.setLong(4, comment.getAuthorId());
			st.setLong(5, comment.getCommentedEntityId());
			int affectedRows = st.executeUpdate();
			if (affectedRows>0){
				result = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to save genre comment", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return result;
	}

	@Override
	public boolean deleteCompositionComment(long commentId) throws DaoException {
		Connection con = null;
		boolean result = false;
		PreparedStatement st = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.DELETE_COMPOSITION_COMMENT.toString());
			st.setLong(1, commentId);
			int affectedRows = st.executeUpdate();
			if (affectedRows>0){
				result = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to delete composition comment", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return result;
	}

	@Override
	public boolean deleteSingerComment(long commentId) throws DaoException {
		Connection con = null;
		boolean deleted = false;
		PreparedStatement st = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.DELETE_SINGER_COMMENT.toString());
			st.setLong(1, commentId);
			int affectedRows = st.executeUpdate();
			if (affectedRows>0){
				deleted = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to delete singer comment", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return deleted;
	}

	@Override
	public boolean deleteGenreComment(long commentId) throws DaoException {
		Connection con = null;
		boolean result = false;
		PreparedStatement st = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.DELETE_GENRE_COMMENT.toString());
			st.setLong(1, commentId);
			int affectedRows = st.executeUpdate();
			if (affectedRows>0){
				result = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to delete genre comment", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return result;
	}


	private enum Query {

		FIND_COMPOSITION_COMMENTS ("SELECT commentId, content, date, userId, login FROM CompositionsComments "
				+ "JOIN Users USING (userId) WHERE compositionId=?;"),
		FIND_SINGER_COMMENTS ("SELECT commentId, content, date, userId, login FROM SingersComments "
				+ "JOIN Users USING (userId) WHERE singerId=?;"),
		FIND_GENRE_COMMENTS ("SELECT commentId, content, date, userId, login FROM GenresComments "
				+ "JOIN Users USING (userId) WHERE genreId=?;"),
		SAVE_COMPOSITION_COMMENT ("INSERT INTO CompositionsComments (commentId, content, date, userId, compositionId) "
				+ "VALUES (?, ?, ?, ?,?);"),
		SAVE_SINGER_COMMENT ("INSERT INTO SingersComments (commentId, content, date, userId, singerId) "
				+ "VALUES (?, ?, ?, ?,?);"),
		SAVE_GENRE_COMMENT ("INSERT INTO GenresComments (commentId, content, date, userId, genreId) "
				+ "VALUES (?, ?, ?, ?,?);"),
		DELETE_COMPOSITION_COMMENT ("DELETE FROM CompositionsComments WHERE commentId=?;"),
		DELETE_SINGER_COMMENT ("DELETE FROM SingersComments WHERE commentId=?;"),
		DELETE_GENRE_COMMENT ("DELETE FROM GenresComments WHERE commentId=?;")
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
