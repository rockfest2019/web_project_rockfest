package com.semernik.rockfest.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import com.semernik.rockfest.connection.ConnectionPool;
import com.semernik.rockfest.dao.CompositionLinksDao;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.entity.Link;



public class CompositionLinksDaoImpl implements CompositionLinksDao {

	private static CompositionLinksDaoImpl instance = new CompositionLinksDaoImpl();

	public static CompositionLinksDaoImpl getInstance(){
		return instance;
	}

	private CompositionLinksDaoImpl(){}

	@Override
	public Collection<Link> findCompositionLinksByCompositionId(long compositionId) throws DaoException {
		Connection con = null;
		Collection<Link> links = new LinkedList<>();
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_COMPOSITION_LINKS.toString());
			st.setLong(1, compositionId);
			result = st.executeQuery();
			while (result.next()){
				long linkId = result.getLong(1);
				String url = result.getString(2);
				Date date = result.getDate(3);
				long authorId = result.getLong(4);
				String authorLogin = result.getString(5);
				Link link = new Link(compositionId, linkId, date, url, authorId, authorLogin);
				links.add(link);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find composition links", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return links;
	}

	@Override
	public boolean saveCompositionLink (Link link) throws DaoException{
		Connection con = null;
		boolean saved = false;
		PreparedStatement st = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.SAVE_COMPOSITION_LINK.toString());
			st.setLong(1, link.getLinkId());
			st.setString(2, link.getUrl());
			st.setDate(3, link.getDate());
			st.setLong(4, link.getAuthorId());
			st.setLong(5, link.getCompositionId());
			int affectedRows = st.executeUpdate();
			if (affectedRows > 0){
				saved = true;
			}
		} catch (SQLException e) {
			saved = false;
			throw new DaoException("Failed to save composition link", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return saved;
	}

	@Override
	public boolean deleteCompositionLink(long linkId) throws DaoException {
		Connection con = null;
		boolean saved = false;
		PreparedStatement st = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.DELETE_COMPOSITION_LINK.toString());
			st.setLong(1, linkId);
			int affectedRows = st.executeUpdate();
			if (affectedRows > 0){
				saved = true;
			}
		} catch (SQLException e) {
			saved = false;
			throw new DaoException("Failed to delete composition link", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return saved;
	}


	private enum Query {

		FIND_COMPOSITION_LINKS ("SELECT linkId, content, date, authorId, login FROM CompositionLinks "
				+ "JOIN Users ON authorId=userId WHERE compositionId=?;"),
		SAVE_COMPOSITION_LINK("INSERT INTO CompositionLinks(linkId, content, date, authorId, compositionId)"
				+ " VALUES(?,?,?,?,?);"),
		DELETE_COMPOSITION_LINK("DELETE FROM CompositionLinks WHERE linkId=?;")

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
