package com.semernik.rockfest.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.semernik.rockfest.connection.ConnectionPool;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.IdsDao;
import com.semernik.rockfest.entity.LastIdsContainer;



public class IdsDaoImpl implements IdsDao {

	private static IdsDaoImpl instance = new IdsDaoImpl();

	public static IdsDaoImpl getInstance(){
		return instance;
	}

	private IdsDaoImpl(){}

	@Override
	public Optional<LastIdsContainer> findLastIds() throws DaoException {
		Connection con = null;
		Statement st = null;
		LastIdsContainer container = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.createStatement();
			container = new LastIdsContainer();
			result = st.executeQuery(Query.FIND_LAST_USER_ID.toString());
			if (result.next()){
				container.setUserId(result.getLong(1));
			}
			result = st.executeQuery(Query.FIND_LAST_COMPOSITION_ID.toString());
			if (result.next()){
				container.setCompositionId(result.getLong(1));
			}
			result = st.executeQuery(Query.FIND_LAST_SINGER_ID.toString());
			if (result.next()){
				container.setSingerId(result.getLong(1));
			}
			result = st.executeQuery(Query.FIND_LAST_GENRE_ID.toString());
			if (result.next()){
				container.setGenreId(result.getLong(1));
			}
			result = st.executeQuery(Query.FIND_LAST_COMPOSITION_COMMENT_ID.toString());
			if (result.next()){
				container.setCompositionCommentId(result.getLong(1));
			}
			result = st.executeQuery(Query.FIND_LAST_SINGER_COMMENT_ID.toString());
			if (result.next()){
				container.setSingerCommentId(result.getLong(1));
			}
			result = st.executeQuery(Query.FIND_LAST_GENRE_COMMENT_ID.toString());
			if (result.next()){
				container.setGenreCommentId(result.getLong(1));
			}
			result = st.executeQuery(Query.FIND_LAST_COMPOSITION_LINK_ID.toString());
			if (result.next()){
				container.setCompositionLinkId(result.getLong(1));
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find last ids", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.of(container);
	}


	private enum Query {

			FIND_LAST_USER_ID ("SELECT MAX(userId) FROM Users;"),
			FIND_LAST_COMPOSITION_ID ("SELECT MAX(compositionId) FROM Compositions;"),
			FIND_LAST_SINGER_ID ("SELECT MAX(singerId) FROM Singers;"),
			FIND_LAST_GENRE_ID ("SELECT MAX(genreId) FROM Genres;"),
			FIND_LAST_COMPOSITION_COMMENT_ID ("SELECT MAX(commentId) FROM CompositionsComments;"),
			FIND_LAST_SINGER_COMMENT_ID ("SELECT MAX(commentId) FROM SingersComments;"),
			FIND_LAST_GENRE_COMMENT_ID ("SELECT MAX(commentId) FROM GenresComments;"),
			FIND_LAST_COMPOSITION_LINK_ID ("SELECT MAX(linkId) FROM CompositionLinks;"),
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
