package com.semernik.rockfest.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import com.semernik.rockfest.connection.ConnectionPool;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.SearchDao;
import com.semernik.rockfest.entity.Composition;
import com.semernik.rockfest.entity.Composition.CompositionBuilder;
import com.semernik.rockfest.entity.Entity;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.entity.Genre.GenreBuilder;
import com.semernik.rockfest.entity.Singer;
import com.semernik.rockfest.entity.Singer.SingerBuilder;
import com.semernik.rockfest.type.EntityType;

public class SearchDaoImpl implements SearchDao {

	private static SearchDaoImpl instance = new SearchDaoImpl();
	private static final String PERCENT_MARK = "%";

	public static SearchDaoImpl getInstance(){
		return instance;
	}

	private SearchDaoImpl(){}

	@Override
	public Collection<Entity> entitiesSearch(String pattern) throws DaoException {
		Connection con = null;
		Collection<Entity> entities = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.COMPOSITIONS_SEARCH.toString());
			pattern = preparePattern(pattern);
			st.setString(1, pattern);
			result = st.executeQuery();
			Entity entity = null;
			long id = 0;
			String title = null;
			EntityType entityType = EntityType.COMPOSITION;
			while (result.next()){
				id = result.getLong(1);
				title = result.getString(2);
				entity = new Entity(entityType, title, id);
				entities.add(entity);
			}
			st = con.prepareStatement(Query.GENRES_SEARCH.toString());
			st.setString(1, pattern);
			result = st.executeQuery();
			entityType = EntityType.GENRE;
			while (result.next()){
				id = result.getLong(1);
				title = result.getString(2);
				entity = new Entity(entityType, title, id);
				entities.add(entity);
			}
			st = con.prepareStatement(Query.SINGERS_SEARCH.toString());
			st.setString(1, pattern);
			result = st.executeQuery();
			entityType = EntityType.SINGER;
			while (result.next()){
				id = result.getLong(1);
				title = result.getString(2);
				entity = new Entity(entityType, title, id);
				entities.add(entity);
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to find compositions by pattern", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return entities;
	}

	@Override
	public Collection<Composition> compositionsSearch(String pattern) throws DaoException {
		Connection con = null;
		Collection<Composition> compositions = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.COMPOSITIONS_SEARCH.toString());
			pattern = preparePattern(pattern);
			st.setString(1, pattern);
			result = st.executeQuery();
			Composition composition = null;
			CompositionBuilder builder = null;
			while (result.next()){
				builder = new CompositionBuilder();
				composition = builder.compositionId(result.getLong(1)).compositionTitle(result.getString(2)).build();
				compositions.add(composition);
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to find compositions by pattern", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return compositions;
	}

	private String preparePattern(String pattern) {
		return PERCENT_MARK + pattern + PERCENT_MARK;
	}


	@Override
	public Collection<Genre> genresSearch(String pattern) throws DaoException {
		Connection con = null;
		Collection<Genre> genres = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.GENRES_SEARCH.toString());
			pattern = preparePattern(pattern);
			st.setString(1, pattern);
			result = st.executeQuery();
			Genre genre = null;
			GenreBuilder builder = null;
			while (result.next()){
				builder = new GenreBuilder();
				genre = builder.genreId(result.getLong(1)).title(result.getString(2)).build();
				genres.add(genre);
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to find compositions by pattern", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return genres;
	}

	@Override
	public Collection<Singer> singersSearch(String pattern) throws DaoException {
		Connection con = null;
		Collection<Singer> singers = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.SINGERS_SEARCH.toString());
			pattern = preparePattern(pattern);
			st.setString(1, pattern);
			result = st.executeQuery();
			Singer singer = null;
			SingerBuilder builder = null;
			while (result.next()){
				builder = new SingerBuilder();
				singer = builder.singerId(result.getLong(1)).title(result.getString(2)).build();
				singers.add(singer);
			}
		} catch (SQLException e) {
			throw new DaoException("Fail to find compositions by pattern", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return singers;
	}

	private enum Query {

		COMPOSITIONS_SEARCH ("SELECT compositionId, title FROM Compositions WHERE title LIKE ?;"),
		GENRES_SEARCH ("SELECT genreId, title FROM Genres WHERE title LIKE ?;"),
		SINGERS_SEARCH ("SELECT singerId, title FROM Singers WHERE title LIKE ?;"),

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
