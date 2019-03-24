package com.semernik.rockfest.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import com.semernik.rockfest.connection.ConnectionPool;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.UsersDao;
import com.semernik.rockfest.entity.User;
import com.semernik.rockfest.entity.UserProfile;


public class UsersDaoImpl implements UsersDao{

	private static UsersDaoImpl instance = new UsersDaoImpl();

	public static UsersDaoImpl getInstance(){
		return instance;
	}

	private UsersDaoImpl(){}

	@Override
	public Optional<User> findCompositionAuthorByCompositionId(int compositionId) throws DaoException {
		Connection con = null;
		User author = null;
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_COMPOSITION_AUTHOR.toString());
			st.setInt(1, compositionId);
			result = st.executeQuery();
			result.next();
			int authorId = result.getInt(1);
			String authorLogin = result.getString(2);
			author = new User(authorId, authorLogin);
		} catch (SQLException e) {
			throw new DaoException("Failed to find composition author by composition id", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.ofNullable(author);
	}

	@Override
	public Optional<User> findCompositionYearEditorByCompositionId(int compositionId) throws DaoException {
		Connection con = null;
		User editor = null;
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_COMPOSITION_YEAR_EDITOR.toString());
			st.setInt(1, compositionId);
			result = st.executeQuery();
			result.next();
			int editorId = result.getInt(1);
			String editorLogin = result.getString(2);
			editor = new User(editorId, editorLogin);
		} catch (SQLException e) {
			throw new DaoException("Failed to find composition year editor by composition id", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.ofNullable(editor);
	}

	@Override
	public boolean saveUser(User user) throws DaoException {
		Connection con = null;
		PreparedStatement userSaver = null;
		boolean saved = false;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			userSaver = con.prepareStatement(Query.SAVE_USER.toString());
			userSaver.setLong(1, user.getUserId());
			userSaver.setString(2, user.getLogin());
			userSaver.setString(3, user.getPassword());
			userSaver.setString(4,  user.getEmail());
			int affectedRows = userSaver.executeUpdate();
			if (affectedRows > 0){
				saved = true;
			}
		} catch (SQLException e){
			throw new DaoException("Failed to save user", e);
		} finally {
			closeStatement(userSaver);
			closeConnection(con);
		}
		return saved;
	}

	@Override
	public Collection<User> findAllUsers() throws DaoException {
		Connection con = null;
		Collection<User> users = new LinkedList<>();
		Statement st = null;
		ResultSet result = null;
		try{
			con = ConnectionPool.getInstance().takeConnection();
			st = con.createStatement();
			result = st.executeQuery(Query.FIND_ALL_USERS.toString());
			while(result.next()){
				long userId = result.getLong(1);
				String login = result.getString(2);
				long banExpirationDate = result.getLong(3);
				User user = new User(userId, login, banExpirationDate);
				users.add(user);
			}
		} catch (SQLException e){
			throw new DaoException("Failed to find users", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return users;
	}

	@Override
	public Optional<User> findUserByLogin(String userLogin) throws DaoException {
		Connection con = null;
		User user = null;
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_USER_BY_lOGIN.toString());
			st.setString(1, userLogin);
			result = st.executeQuery();
			if(result.next()){
				long userId = result.getLong(1);
				String userPassword = result.getString(2);
				String userEmail = result.getString(3);
				String userRole = result.getString(4);
				long banExpirationDate = result.getLong(5);
				user = new User(userId, userLogin, userRole, userPassword, userEmail, banExpirationDate);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find user info by login", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.ofNullable(user);
	}

	@Override
	public Optional<User> findUserById(long userId) throws DaoException {
		Connection con = null;
		User user = null;
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_USER_BY_ID.toString());
			st.setLong(1, userId);
			result = st.executeQuery();
			if(result.next()){
				String userLogin = result.getString(1);
				String userPassword = result.getString(2);
				String userEmail = result.getString(3);
				String userRole = result.getString(4);
				long banExpirationDate = result.getLong(5);
				user = new User(userId, userLogin, userRole, userPassword, userEmail, banExpirationDate);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find user info by login", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.ofNullable(user);
	}


	@Override
	public boolean saveNewUserInfo(User user) throws DaoException {
		Connection con = null;
		PreparedStatement st = null;
		boolean saved = false;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.SAVE_USER_INFO.toString());
			st.setString(1, user.getLogin());
			st.setString(2, user.getPassword());
			st.setString(3, user.getEmail());
			st.setLong(4, user.getUserId());
			int affectedRows = st.executeUpdate();
			if (affectedRows > 0){
				saved = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to save user info", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return saved;
	}

	@Override
	public Optional<UserProfile> findUserProfileByUserId(long userId) throws DaoException {
		Connection con = null;
		UserProfile profile = null;
		PreparedStatement st = null;
		ResultSet result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.FIND_USER_PROFILE_BY_USER_ID.toString());
			st.setLong(1, userId);
			result = st.executeQuery();
			if(result.next()){
				String email = result.getString(1);
				int addedCompositionsCount = result.getInt(2);
				int addedGenresCount = result.getInt(3);
				int addedSingersCount = result.getInt(4);
				int assessedCompositionsCount = result.getInt(5);
				profile = new UserProfile(addedCompositionsCount, addedSingersCount, addedGenresCount,
						assessedCompositionsCount, email);
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to find user profile", e);
		} finally {
			closeResultSet(result);
			closeStatement(st);
			closeConnection(con);
		}
		return Optional.ofNullable(profile);
	}

	public boolean saveBanExpirationDate (long userId, long banExpirationDate) throws DaoException{
		Connection con = null;
		PreparedStatement st = null;
		boolean saved = false;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			st = con.prepareStatement(Query.SAVE_BAN_EXPIRATION_DATE.toString());
			st.setLong(1, banExpirationDate);
			st.setLong(2, userId);
			int affectedRows = st.executeUpdate();
			if (affectedRows > 0){
				saved = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to save ban expiration date", e);
		} finally {
			closeStatement(st);
			closeConnection(con);
		}
		return saved;
	}



	private enum Query {

		FIND_COMPOSITION_AUTHOR ("SELECT userId, login from Users JOIN Compositions ON authorId=userId WHERE compositionId=?;"),
		FIND_COMPOSITION_YEAR_EDITOR ("SELECT userId, login from Users JOIN Compositions "
				+ "ON yearEditorId=userId WHERE compositionId=?;"),
		SAVE_USER ("INSERT INTO Users (userId, login, password, email) VALUES (?,?,?,?);"),
		SAVE_USER_PASSWORD ("UPDATE USERS SET password=? WHERE userId=?;"),
		SAVE_USER_INFO ("UPDATE USERS SET login=?, password=?, email=? WHERE userId=?;"),
		FIND_ALL_USERS("SELECT userId, login, banExpirationDate FROM Users;"),
		FIND_USER_BY_lOGIN("SELECT userId, password, email, Roles.title, banExpirationDate FROM Users JOIN Roles USING(roleId) WHERE"
				+ " login=?;"),
		FIND_USER_BY_ID("SELECT login, password, email, Roles.title, banExpirationDate FROM Users JOIN Roles USING(roleId) WHERE userId=?;"),
		FIND_USER_PROFILE_BY_USER_ID("SELECT DISTINCT email, (SELECT COUNT(compositionId) FROM Compositions WHERE "
				+ "Compositions.authorId=userId) AS addedCompositionsCount, (SELECT COUNT(genreId) FROM Genres WHERE "
				+ "Genres.authorId=userId) AS addedGenresCount, (SELECT COUNT(singerId) FROM Singers WHERE Singers.authorId=userId) "
				+ "AS addedSingersCount, (SELECT COUNT(compositionId) FROM singleUserRating WHERE SingleUserRating.userId=users.userId) "
				+ "AS assessedCompositionsCount FROM Users WHERE userId=?;"),
		SAVE_BAN_EXPIRATION_DATE("UPDATE USERS SET banExpirationDate=? WHERE userId=?;")
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
