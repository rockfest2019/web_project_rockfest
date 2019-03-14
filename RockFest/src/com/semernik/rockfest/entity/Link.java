package com.semernik.rockfest.entity;

import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Link.
 */
public class Link {

	/** The composition id. */
	private long compositionId;

	/** The link id. */
	private long linkId;

	/** The date. */
	private Date date;

	/** The url. */
	private String url;

	/** The author id. */
	private long authorId;

	/** The author login. */
	private String authorLogin;

	/**
	 * Instantiates a new link.
	 *
	 * @param compositionId the composition id
	 * @param linkId the link id
	 * @param date the date
	 * @param url the url
	 * @param authorId the author id
	 * @param authorLogin the author login
	 */
	public Link(long compositionId, long linkId, Date date, String url, long authorId, String authorLogin){
		this.compositionId = compositionId;
		this.linkId = linkId;
		this.date = date;
		this.url = url;
		this.authorId = authorId;
		this.authorLogin = authorLogin;
	}

	/**
	 * Instantiates a new link.
	 *
	 * @param compositionId the composition id
	 * @param linkId the link id
	 * @param date the date
	 * @param url the url
	 * @param authorId the author id
	 */
	public Link(long compositionId, long linkId, Date date, String url, long authorId){
		this.compositionId = compositionId;
		this.linkId = linkId;
		this.date = date;
		this.url = url;
		this.authorId = authorId;
	}

	/**
	 * Gets the composition id.
	 *
	 * @return the composition id
	 */
	public long getCompositionId() {
		return compositionId;
	}

	/**
	 * Gets the link id.
	 *
	 * @return the link id
	 */
	public long getLinkId() {
		return linkId;
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
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
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



}
