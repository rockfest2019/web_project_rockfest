package com.semernik.rockfest.util;

import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;

import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.entity.Comment;
import com.semernik.rockfest.entity.Composition;
import com.semernik.rockfest.entity.Composition.CompositionBuilder;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.entity.Genre.GenreBuilder;
import com.semernik.rockfest.entity.Link;
import com.semernik.rockfest.entity.Singer;
import com.semernik.rockfest.entity.Singer.SingerBuilder;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ParameterName;

public class EntityUtil {


	public static Singer getSingerFromContent(SessionRequestContent content) {
		String singerTitle = content.getParameter(ParameterName.TITLE.toString());
		String description = content.getParameter(ParameterName.DESCRIPTION.toString());
		long authorId = (Long)content.getSessionAttribute(AttributeName.USER_ID.toString());
		long descriptionEditorId = authorId;
		long singerId = GeneratorId.getInstance().generateSingerId();
		Date addingDate = new Date(System.currentTimeMillis());
		SingerBuilder builder = new SingerBuilder();
		Singer singer = builder.singerId(singerId).title(singerTitle).description(description).addingDate(addingDate)
					.authorId(authorId).descriptionEditorId(descriptionEditorId).build();
		return singer;
	}

	public static Genre getGenreFromContent(SessionRequestContent content) {
		String genreTitle = content.getParameter(ParameterName.TITLE.toString());
		String description = content.getParameter(ParameterName.DESCRIPTION.toString());
		long authorId = (Long)content.getSessionAttribute(AttributeName.USER_ID.toString());
		long genreId = GeneratorId.getInstance().generateGenreId();
		Date addingDate = new Date(System.currentTimeMillis());
		GenreBuilder builder = new GenreBuilder();
		Genre genre = builder.genreId(genreId)
				.title(genreTitle)
				.description(description)
				.addingDate(addingDate)
				.authorId(authorId)
				.descriptionEditorId(authorId)
				.build();
		return genre;
	}

	public static Composition getComposititonFromContent(SessionRequestContent content) {
		String title = content.getParameter(ParameterName.TITLE.toString());
		String year = content.getParameter(ParameterName.YEAR.toString());
		long singerId = Long.parseLong(content.getParameter(ParameterName.SINGER_ID.toString()));
		long authorId = (Long) content.getSessionAttribute(AttributeName.USER_ID.toString());
		long compositionId = GeneratorId.getInstance().generateCompositionId();
		Date addingDate = new Date(System.currentTimeMillis());
		Collection <Long> genresIds = findGenresIds(content);
		CompositionBuilder builder = new CompositionBuilder();
		Composition composition = builder.compositionId(compositionId).compositionTitle(title).year(year)
				.singerId(singerId).authorId(authorId).yearEditorId(authorId).genreEditorId(authorId)
				.addingDate(addingDate).genresIds(genresIds).build();
		return composition;
	}


	public static Collection<Long> findGenresIds(SessionRequestContent content) {
		Collection <Long> genresIds = new LinkedList<>();
		String [] strIds = content.getRequestParameters().get(ParameterName.GENRES_IDS.toString());
		if (strIds != null){
			for (String idStr : strIds){
				genresIds.add(Long.parseLong(idStr));
			}
		}
		return genresIds;
	}

	public static Link getCompositionLinkFromContent(SessionRequestContent content) {
		long compositionId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		String url = content.getParameter(ParameterName.URL.toString());
		long authorId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		Date addingDate = new Date(System.currentTimeMillis());
		long linkId = GeneratorId.getInstance().generateCompositionLinkId();
		Link link = new Link(compositionId, linkId, addingDate, url, authorId);
		return link;
	}

	public static Comment getEntityCommentFromContent(SessionRequestContent content) {
		long commentId = GeneratorId.getInstance().generateCompositionCommentId();
		long commentedEntityId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		String commentContent = content.getParameter(ParameterName.COMMENT_CONTENT.toString());
		Date date = new Date(System.currentTimeMillis());
		long authorId = (Long) content.getSessionAttribute(AttributeName.USER_ID.toString());
		Comment comment = new Comment(commentId, commentContent, date, authorId, commentedEntityId);
		return comment;
	}

}
