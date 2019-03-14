package com.semernik.rockfest.type;

import java.util.Comparator;
import java.util.function.BiFunction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.entity.EntityRating;

// TODO: Auto-generated Javadoc
/**
 * The Enum RatingsComparator.
 */
public enum RatingsComparator implements Comparator<EntityRating>{

	/** The general. */
	GENERAL((a,b) -> Double.compare(b.getRating(), a.getRating())),

	/** The melody. */
	MELODY((a,b) -> Double.compare(b.getMelodyRating(), a.getMelodyRating())),

	/** The text. */
	TEXT((a,b) -> Double.compare(b.getTextRating(), a.getTextRating())),

	/** The music. */
	MUSIC((a,b) -> Double.compare(b.getMusicRating(), a.getMusicRating())),

	/** The vocal. */
	VOCAL((a,b) -> Double.compare(b.getVocalRating(), a.getVocalRating())),
	;

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The comparator. */
	private BiFunction<EntityRating, EntityRating, Integer> comparator;

	/**
	 * Instantiates a new ratings comparator.
	 *
	 * @param comparator the comparator
	 */
	RatingsComparator(BiFunction<EntityRating, EntityRating, Integer> comparator){
		this.comparator = comparator;
	}


	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(EntityRating first, EntityRating second) {
		return comparator.apply(first, second);
	}


	/**
	 * Gets the single instance of RatingsComparator.
	 *
	 * @param name the name
	 * @return single instance of RatingsComparator
	 */
	public static RatingsComparator getInstance(String name) {
		RatingsComparator comparator = RatingsComparator.GENERAL;
		try {
			comparator = RatingsComparator.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e) {
			logger.error("Invalid comparator name " + name, e);
		}
		return comparator;
	}

}
