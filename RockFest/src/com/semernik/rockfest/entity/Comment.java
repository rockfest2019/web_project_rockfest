package com.semernik.rockfest.entity;

import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Comment.
 */
public class Comment {

	/** The comment id. */
	private long commentId;

	/** The commented entity id. */
	private long commentedEntityId;

	/** The content. */
	private String content;

	/** The date. */
	private Date date;

	/** The author id. */
	private long authorId;

	/** The author login. */
	private String authorLogin;

	/**
	 * Instantiates a new comment.
	 *
	 * @param commentId the comment id
	 * @param content the content
	 * @param date the date
	 * @param authorId the author id
	 * @param authorLogin the author login
	 * @param commentedEntityId the commented entity id
	 */
	public Comment(long commentId, String content, Date date, long authorId, String authorLogin, long commentedEntityId){
		this.commentId = commentId;
		this.content = content;
		this.date = date;
		this.authorId = authorId;
		this.authorLogin = authorLogin;
		this.commentedEntityId = commentedEntityId;
	}

	/**
	 * Instantiates a new comment.
	 *
	 * @param commentId the comment id
	 * @param content the content
	 * @param date the date
	 * @param authorId the author id
	 * @param commentedEntityId the commented entity id
	 */
	public Comment(long commentId, String content, Date date, long authorId, long commentedEntityId){
		this.commentId = commentId;
		this.content = content;
		this.date = date;
		this.authorId = authorId;
		this.commentedEntityId = commentedEntityId;
	}

	/**
	 * Gets the comment id.
	 *
	 * @return the comment id
	 */
	public long getCommentId() {
		return commentId;
	}


	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}


	/**
	 * Gets the author id.
	 *
	 * @return the author id
	 */
	public long getAuthorId() {
		return authorId;
	}


	/**
	 * Gets the author login.
	 *
	 * @return the author login
	 */
	public String getAuthorLogin() {
		return authorLogin;
	}


	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Gets the commented entity id.
	 *
	 * @return the commented entity id
	 */
	public long getCommentedEntityId() {
		return commentedEntityId;
	}



}
