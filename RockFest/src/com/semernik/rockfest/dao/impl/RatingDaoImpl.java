package com.semernik.rockfest.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.connection.ConnectionPool;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.RatingDao;
import com.semernik.rockfest.entity.EntityRating;


public class RatingDaoImpl implements RatingDao {

	private static Logger logger = LogManager.getLogger();

	private static RatingDaoImpl instance = new RatingDaoImpl();

	public static RatingDaoImpl getInstance(){
		return instance;
	}

	private RatingDaoImpl(){}

	@Override
	public boolean saveUserRating(EntityRating rating) throws DaoException {
		Connection con = null;
		boolean result = true;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			con.setAutoCommit(false);
			result =( executeQuery(con, rating, Query.SAVE_USER_RATING)
					&& executeQuery(con, rating, Query.UPDATE_COMPOSITION_RATING)
					&& executeQuery(con, rating, Query.UPDATE_SINGER_RATING)
					&& executeQuery(con, rating, Query.UPDATE_GENRES_RATING));
			if (result == true){
				con.commit();
			} else {
				con.rollback();
			}
		} catch (SQLException e){
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database access failure while connection rollback", e1);
			}
			result = false;
			throw new DaoException("Failed to save user rating", e);
		} finally {
			closeConnection(con);
		}
		return result;
	}

	private boolean executeQuery(Connection con, EntityRating rating, Query query) throws SQLException{
		PreparedStatement st = null;
		int affectedRows = 0;
		try {
			st = con.prepareStatement(query.toString());
			st.setDouble(1, rating.getMelodyRating());
			st.setDouble(2, rating.getTextRating());
			st.setDouble(3, rating.getMusicRating());
			st.setDouble(4, rating.getVocalRating());
			st.setLong(5, rating.getEntityId());
			if (query == Query.SAVE_USER_RATING){
				st.setLong(6, rating.getUserId());
			}
			affectedRows = st.executeUpdate();
		} finally {
			closeStatement(st);
		}
		return ( affectedRows > 0 );
	}

	@Override
	public Collection<EntityRating> findUserRatingsByUserId(long userId) throws DaoException {
		Connection con = null;
		Collection<EntityRating> ratings = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_SINGLE_USER_RATINGS.toString());
			st.setLong(1, userId);
			result = st.executeQuery();
			while(result.next()){
				long compositionId = result.getLong(1);
				String compositionTitle = result.getString(2);
				int melodyRating = result.getInt(3);
				int textRating = result.getInt(4);
				int musicRating = result.getInt(5);
				int vocalRating = result.getInt(6);
				EntityRating rating = new EntityRating(userId, compositionId, melodyRating,
						textRating, musicRating, vocalRating);
				rating.setEntityTitle(compositionTitle);
				ratings.add(rating);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find user ratings", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return ratings;
	}

	@Override
	public Optional<EntityRating> findCompositionUserRating(long compositionId, long userId) throws DaoException {
		Connection con = null;
		EntityRating rating = null;
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_COMPOSITION_USER_RATING.toString());
			st.setLong(1, userId);
			st.setLong(2, compositionId);
			result = st.executeQuery();
			if (result.next()){
				int melodyRating = result.getInt(1);
				int textRating = result.getInt(2);
				int musicRating = result.getInt(3);
				int vocalRating = result.getInt(4);
				rating = new EntityRating(userId, compositionId, melodyRating,
						textRating, musicRating, vocalRating);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find composition user rating", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.ofNullable(rating);
	}

	@Override
	public Optional<EntityRating> findGenreUserRating(long genreId, long userId) throws DaoException {
		Connection con = null;
		EntityRating rating = null;
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_GENRE_USER_RATING.toString());
			st.setLong(1, userId);
			st.setLong(2, genreId);
			result = st.executeQuery();
			if (result.next()){
				double melodyRating = result.getDouble(1);
				double textRating = result.getDouble(2);
				double musicRating = result.getDouble(3);
				double vocalRating = result.getDouble(4);
				rating = new EntityRating(melodyRating,
						textRating, musicRating, vocalRating);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find genre user rating", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.ofNullable(rating);
	}

	@Override
	public Optional<EntityRating> findSingerUserRating(long singerId, long userId) throws DaoException {
		Connection con = null;
		EntityRating rating = null;
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_SINGER_USER_RATING.toString());
			st.setLong(1, userId);
			st.setLong(2, singerId);
			result = st.executeQuery();
			if (result.next()){
				double melodyRating = result.getDouble(1);
				double textRating = result.getDouble(2);
				double musicRating = result.getDouble(3);
				double vocalRating = result.getDouble(4);
				rating = new EntityRating(melodyRating,
						textRating, musicRating, vocalRating);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find singer user rating", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.ofNullable(rating);
	}

	@Override
	public List<EntityRating> findUserCompositionsRating(long userId) throws DaoException {
		Connection con = null;
		List<EntityRating> ratings = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try{
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_COMPOSITIONS_USER_RATINGS.toString());
			st.setLong(1, userId);
			result = st.executeQuery();
			while(result.next()){
				long singerId = result.getLong(1);
				String title = result.getString(2);
				double melody = result.getDouble(3);
				double text = result.getDouble(4);
				double music = result.getDouble(5);
				double vocal = result.getDouble(6);
				EntityRating rating = new EntityRating(title, singerId, melody, text, music, vocal);
				ratings.add(rating);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find user compositions ratings", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return ratings;
	}

	@Override
	public List<EntityRating> findUserSingersRating(long userId) throws DaoException {
		Connection con = null;
		List<EntityRating> ratings = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try{
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_SINGERS_USER_RATINGS.toString());
			st.setLong(1, userId);
			result = st.executeQuery();
			while(result.next()){
				long singerId = result.getLong(1);
				String title = result.getString(2);
				double melody = result.getDouble(3);
				double text = result.getDouble(4);
				double music = result.getDouble(5);
				double vocal = result.getDouble(6);
				EntityRating rating = new EntityRating(title, singerId, melody, text, music, vocal);
				ratings.add(rating);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find user singers ratings", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return ratings;
	}

	@Override
	public List<EntityRating> findUserGenresRating(long userId) throws DaoException {
		Connection con = null;
		List<EntityRating> ratings = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try{
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_GENRES_USER_RATINGS.toString());
			st.setLong(1, userId);
			result = st.executeQuery();
			while(result.next()){
				long singerId = result.getLong(1);
				String title = result.getString(2);
				double melody = result.getDouble(3);
				double text = result.getDouble(4);
				double music = result.getDouble(5);
				double vocal = result.getDouble(6);
				EntityRating rating = new EntityRating(title, singerId, melody, text, music, vocal);
				ratings.add(rating);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find user genres ratings", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return ratings;
	}


	private enum Query {

		SAVE_USER_RATING ("INSERT INTO SingleUserRating(melodyRating, textRating, musicRating, "
				+ "vocalRating, compositionId, userId) VALUES (?,?,?,?,?,?);"),
		UPDATE_COMPOSITION_RATING ("UPDATE Compositions SET melodyRating=melodyRating+?, textRating=textRating+?,"
				+ " musicRating=musicRating+?, vocalRating=vocalRating+?, votedUsersCount=votedUsersCount+1"
				+ " WHERE compositionId=?;"),
		UPDATE_SINGER_RATING ("UPDATE Singers SET melodyRating=melodyRating+?, textRating=textRating+?,"
				+ " musicRating=musicRating+?, vocalRating=vocalRating+?, votedUsersCount=votedUsersCount+1"
				+ " WHERE singerId=(SELECT singerId FROM Compositions WHERE compositionId=?);"),
		UPDATE_GENRES_RATING ("UPDATE Genres SET melodyRating=melodyRating+?, textRating=textRating+?,"
				+ " musicRating=musicRating+?, vocalRating=vocalRating+?, votedUsersCount=votedUsersCount+1"
				+ " WHERE genreId IN (SELECT genreId FROM Compositions_has_genres WHERE compositionId=?);"),
		FIND_SINGLE_USER_RATINGS ("SELECT compositionId, title, SingleUserRating.melodyRating, "
				+ "SingleUserRating.textRating, SingleUserRating.musicRating, SingleUserRating.vocalRating "
				+ "FROM SingleUserRating JOIN Compositions USING (compositionId) WHERE userId=?;"),
		FIND_COMPOSITION_USER_RATING ("SELECT melodyRating, textRating, musicRating, vocalRating "
				+ "FROM SingleUserRating WHERE userId=? AND compositionId=?;"),
		FIND_GENRE_USER_RATING ("SELECT avg(melodyRating) as melody, avg(textRating) as text, avg(musicRating) as music, "
				+ "avg(vocalRating) FROM SingleUserRating WHERE userId=? AND compositionId IN "
				+ "(SELECT compositionId FROM Compositions_has_Genres JOIN Genres USING (genreId) WHERE genreId=?);"),
		FIND_SINGER_USER_RATING ("SELECT avg(melodyRating) as melody, avg(textRating) as text, avg(musicRating) as music, "
				+ "avg(vocalRating) FROM SingleUserRating WHERE userId=? AND compositionId IN "
				+ "(SELECT compositionId FROM compositions WHERE singerId=?);"),


		FIND_SINGERS_RATING ("SELECT singerId, title, melodyRating, textRating, musicRating, vocalRating, votedUsersCount FROM Singers"
				+ " WHERE votedUsersCount>0;"),
		FIND_GENRES_RATING ("SELECT genreId, title, melodyRating, textRating, musicRating, vocalRating, votedUsersCount FROM Genres"
				+ " WHERE votedUsersCount>0"),
		FIND_COMPOSITIONS_USER_RATINGS ("SELECT compositionId, title, SingleUserRating.melodyRating, SingleUserRating.textRating, "
				+ "SingleUserRating.musicRating, SingleUserRating.vocalRating FROM SingleUserRating JOIN Compositions"
				+ " USING(compositionId) WHERE userId=? GROUP BY compositionId;"),
		FIND_COMPOSITIONS_USER_RATINGS_ORDER_BY_GENERAL_RATING ("SELECT compositionId, title, SingleUserRating.melodyRating, "
				+ "SingleUserRating.textRating, SingleUserRating.musicRating, SingleUserRating.vocalRating FROM SingleUserRating "
				+ "JOIN Compositions USING(compositionId) WHERE userId=? GROUP BY compositionId ORDER BY "
				+ "(SingleUserRating.melodyRating + SingleUserRating.textRating + SingleUserRating.musicRating + "
				+ "SingleUserRating.vocalRating) DESC LIMIT ?, ?;"),
		FIND_COMPOSITIONS_USER_RATINGS_ORDER_BY_MELODY_RATING ("SELECT compositionId, title, SingleUserRating.melodyRating, "
				+ "SingleUserRating.textRating, SingleUserRating.musicRating, SingleUserRating.vocalRating FROM SingleUserRating "
				+ "JOIN Compositions USING(compositionId) WHERE userId=? GROUP BY compositionId ORDER BY "
				+ "SingleUserRating.melodyRating DESC LIMIT ?, ?;"),
		FIND_COMPOSITIONS_USER_RATINGS_ORDER_BY_TEXT_RATING ("SELECT compositionId, title, SingleUserRating.melodyRating, "
				+ "SingleUserRating.textRating, SingleUserRating.musicRating, SingleUserRating.vocalRating FROM SingleUserRating "
				+ "JOIN Compositions USING(compositionId) WHERE userId=? GROUP BY compositionId ORDER BY "
				+ "SingleUserRating.textRating DESC LIMIT ?, ?;"),
		FIND_COMPOSITIONS_USER_RATINGS_ORDER_BY_MUSIC_RATING ("SELECT compositionId, title, SingleUserRating.melodyRating, "
				+ "SingleUserRating.textRating, SingleUserRating.musicRating, SingleUserRating.vocalRating FROM SingleUserRating "
				+ "JOIN Compositions USING(compositionId) WHERE userId=? GROUP BY compositionId ORDER BY "
				+ "SingleUserRating.musicRating DESC LIMIT ?, ?;"),
		FIND_COMPOSITIONS_USER_RATINGS_ORDER_BY_VOCAL_RATING ("SELECT compositionId, title, SingleUserRating.melodyRating, "
				+ "SingleUserRating.textRating, SingleUserRating.musicRating, SingleUserRating.vocalRating FROM SingleUserRating "
				+ "JOIN Compositions USING(compositionId) WHERE userId=? GROUP BY compositionId ORDER BY "
				+ "SingleUserRating.vocalRating DESC LIMIT ?, ?;"),
		FIND_GENRES_USER_RATINGS ("SELECT Genres.genreId, Genres.title, avg(SingleUserRating.melodyRating) as melody, "
				+ "avg(SingleUserRating.textRating) as text, avg(SingleUserRating.musicRating) as music, "
				+ "avg(SingleUserRating.vocalRating) as vocal FROM SingleUserRating  JOIN Compositions_has_Genres "
				+ "USING (compositionId) JOIN Genres USING (genreId) WHERE userId=? AND compositionId IN "
				+ "(SELECT compositionId FROM Compositions_has_Genres JOIN Genres USING (genreId)) GROUP BY genreId;"),
		FIND_SINGERS_USER_RATINGS ("SELECT Singers.singerId, Singers.title, avg(SingleUserRating.melodyRating) as melody, "
				+ "avg(SingleUserRating.textRating) as text, avg(SingleUserRating.musicRating) as music, "
				+ "avg(SingleUserRating.vocalRating) as vocal FROM SingleUserRating JOIN Compositions USING(compositionId) JOIN "
				+ "Singers USING(SingerId) WHERE userId=? AND compositionId IN (SELECT compositionId FROM compositions) GROUP BY singerId;"),

		FIND_COMPOSITIONS_RATING ("SELECT compositionId, title, melodyRating, textRating, musicRating, vocalRating, votedUsersCount "
				+ "FROM Compositions WHERE votedUsersCount>0;"),
		FIND_COMPOSITIONS_RATING_PART_ORDER_BY_GENERAL_RATING ("SELECT compositionId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Compositions WHERE votedUsersCount>0 ORDER BY ((melodyRating+textRating + "
				+ "musicRating + vocalRating) / (4 * votedUsersCount)) DESC LIMIT ?, ?;"),
		FIND_COMPOSITIONS_RATING_PART_ORDER_BY_MELODY_RATING ("SELECT compositionId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Compositions WHERE votedUsersCount>0 ORDER BY (melodyRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
		FIND_COMPOSITIONS_RATING_PART_ORDER_BY_TEXT_RATING ("SELECT compositionId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Compositions WHERE votedUsersCount>0 ORDER BY (textRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
		FIND_COMPOSITIONS_RATING_PART_ORDER_BY_MUSIC_RATING ("SELECT compositionId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Compositions WHERE votedUsersCount>0 ORDER BY (musicRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
		FIND_COMPOSITIONS_RATING_PART_ORDER_BY_VOCAL_RATING ("SELECT compositionId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Compositions WHERE votedUsersCount>0 ORDER BY (vocalRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
		FIND_SINGERS_RATING_PART_ORDER_BY_GENERAL_RATING ("SELECT singerId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Singers WHERE votedUsersCount>0 ORDER BY ((melodyRating+textRating + "
				+ "musicRating + vocalRating) / (4 * votedUsersCount)) DESC LIMIT ?, ?;"),
		FIND_SINGERS_RATING_PART_ORDER_BY_MELODY_RATING ("SELECT singerId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Singers WHERE votedUsersCount>0 ORDER BY (melodyRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
		FIND_SINGERS_RATING_PART_ORDER_BY_TEXT_RATING ("SELECT singerId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Singers WHERE votedUsersCount>0 ORDER BY (textRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
		FIND_SINGERS_RATING_PART_ORDER_BY_MUSIC_RATING ("SELECT singerId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Singers WHERE votedUsersCount>0 ORDER BY (musicRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
		FIND_SINGERS_RATING_PART_ORDER_BY_VOCAL_RATING ("SELECT singerId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Singers WHERE votedUsersCount>0 ORDER BY (vocalRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
		FIND_GENRES_RATING_PART_ORDER_BY_GENERAL_RATING ("SELECT genreId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Genres WHERE votedUsersCount>0 ORDER BY ((melodyRating+textRating + "
				+ "musicRating + vocalRating) / (4 * votedUsersCount)) DESC LIMIT ?, ?;"),
		FIND_GENRES_RATING_PART_ORDER_BY_MELODY_RATING ("SELECT genreId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Genres WHERE votedUsersCount>0 ORDER BY (melodyRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
		FIND_GENRES_RATING_PART_ORDER_BY_TEXT_RATING ("SELECT genreId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Genres WHERE votedUsersCount>0 ORDER BY (textRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
		FIND_GENRES_RATING_PART_ORDER_BY_MUSIC_RATING ("SELECT genreId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Genres WHERE votedUsersCount>0 ORDER BY (musicRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
		FIND_GENRES_RATING_PART_ORDER_BY_VOCAL_RATING ("SELECT genreId, title, melodyRating, textRating, musicRating, "
				+ "vocalRating, votedUsersCount FROM Genres WHERE votedUsersCount>0 ORDER BY (vocalRating / votedUsersCount) "
				+ "DESC LIMIT ?, ?;"),
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


	@Override
	public List<EntityRating> findCompositionsRatingPartOrderedByGeneralRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_COMPOSITIONS_RATING_PART_ORDER_BY_GENERAL_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findCompositionsRatingPartOrderedByMelodyRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_COMPOSITIONS_RATING_PART_ORDER_BY_MELODY_RATING.toString(), positionFrom, elementsCount);	}

	@Override
	public List<EntityRating> findCompositionsRatingPartOrderedByTextRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_COMPOSITIONS_RATING_PART_ORDER_BY_TEXT_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findCompositionsRatingPartOrderedByMusicRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_COMPOSITIONS_RATING_PART_ORDER_BY_MUSIC_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findCompositionsRatingPartOrderedByVocalRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_COMPOSITIONS_RATING_PART_ORDER_BY_VOCAL_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findSingersRatingPartOrderedByGeneralRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_SINGERS_RATING_PART_ORDER_BY_GENERAL_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findSingersRatingPartOrderedByMelodyRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_SINGERS_RATING_PART_ORDER_BY_MELODY_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findSingersRatingPartOrderedByTextRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_SINGERS_RATING_PART_ORDER_BY_TEXT_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findSingersRatingPartOrderedByMusicRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_SINGERS_RATING_PART_ORDER_BY_MUSIC_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findSingersRatingPartOrderedByVocalRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_SINGERS_RATING_PART_ORDER_BY_VOCAL_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findGenresRatingPartOrderedByGeneralRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_GENRES_RATING_PART_ORDER_BY_GENERAL_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findGenresRatingPartOrderedByMelodyRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_GENRES_RATING_PART_ORDER_BY_MELODY_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findGenresRatingPartOrderedByTextRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_GENRES_RATING_PART_ORDER_BY_TEXT_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findGenresRatingPartOrderedByMusicRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_GENRES_RATING_PART_ORDER_BY_MUSIC_RATING.toString(), positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findGenresRatingPartOrderedByVocalRating(int positionFrom, int elementsCount)
			throws DaoException {
		return findEntityRatingPart(Query.FIND_GENRES_RATING_PART_ORDER_BY_VOCAL_RATING.toString(), positionFrom, elementsCount);
	}


	private List<EntityRating> findEntityRatingPart(String query, int positionFrom, int elementsCount) throws DaoException {
		Connection con = null;
		List<EntityRating> ratings = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try{
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(query);
			st.setInt(1, positionFrom);
			st.setInt(2, elementsCount);
			result = st.executeQuery();
			while(result.next()){
				long entityId = result.getLong(1);
				String title = result.getString(2);
				double melody = result.getDouble(3);
				double text = result.getDouble(4);
				double music = result.getDouble(5);
				double vocal = result.getDouble(6);
				int votedUsersCount = result.getInt(7);
				EntityRating rating = new EntityRating(title, entityId, melody, text, music, vocal, votedUsersCount);
				ratings.add(rating);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find ratings", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return ratings;
	}

	@Override
	public List<EntityRating> findCompositionsUserRatingPartOrderedByGeneralRating(long userId, int positionFrom,
			int elementsCount) throws DaoException {
		return findEntityUserRatingPart(Query.FIND_COMPOSITIONS_USER_RATINGS_ORDER_BY_GENERAL_RATING.toString(), userId,
				positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findCompositionsUserRatingPartOrderedByMelodyRating(long userId, int positionFrom,
			int elementsCount) throws DaoException {
		return findEntityUserRatingPart(Query.FIND_COMPOSITIONS_USER_RATINGS_ORDER_BY_MELODY_RATING.toString(), userId,
				positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findCompositionsUserRatingPartOrderedByTextRating(long userId, int positionFrom,
			int elementsCount) throws DaoException {
		return findEntityUserRatingPart(Query.FIND_COMPOSITIONS_USER_RATINGS_ORDER_BY_TEXT_RATING.toString(), userId,
				positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findCompositionsUserRatingPartOrderedByMusicRating(long userId, int positionFrom,
			int elementsCount) throws DaoException {
		return findEntityUserRatingPart(Query.FIND_COMPOSITIONS_USER_RATINGS_ORDER_BY_MUSIC_RATING.toString(), userId,
				positionFrom, elementsCount);
	}

	@Override
	public List<EntityRating> findCompositionsUserRatingPartOrderedByVocalRating(long userId, int positionFrom,
			int elementsCount) throws DaoException {
		return findEntityUserRatingPart(Query.FIND_COMPOSITIONS_USER_RATINGS_ORDER_BY_VOCAL_RATING.toString(), userId,
				positionFrom, elementsCount);
	}


	public List<EntityRating> findEntityUserRatingPart(String query, long userId, int positionFrom, int elementsCount)
			throws DaoException {
		Connection con = null;
		List<EntityRating> ratings = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try{
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(query);
			st.setLong(1, userId);
			st.setInt(2, positionFrom);
			st.setInt(2, elementsCount);
			result = st.executeQuery();
			while(result.next()){
				long singerId = result.getLong(1);
				String title = result.getString(2);
				double melody = result.getDouble(3);
				double text = result.getDouble(4);
				double music = result.getDouble(5);
				double vocal = result.getDouble(6);
				EntityRating rating = new EntityRating(title, singerId, melody, text, music, vocal);
				ratings.add(rating);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find entities user ratings", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return ratings;
	}


}
