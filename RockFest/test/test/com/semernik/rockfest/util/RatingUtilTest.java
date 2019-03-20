package test.com.semernik.rockfest.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.semernik.rockfest.util.RatingUtil;

public class RatingUtilTest {

  @Test (dataProvider = "dataForFindAjaxCommandCheck")
  public void findAjaxCommand(String entityType, String expectedCommand) {
	  RatingUtil instance = RatingUtil.getInstance();
	  String actualCommand = instance.findAjaxCommand(entityType);
	  Assert.assertEquals(actualCommand, expectedCommand);
  }

  @DataProvider (name = "dataForFindAjaxCommandCheck")
  public Object[][] dataForFindAjaxCommandCheck() {
	  return new Object[][]{
		  {"genre", "genres_ratings_ajax"},
		  {"singer", "singers_ratings_ajax"},
		  {"composition", "compositions_ratings_ajax"}
	  };
  }

  @Test (dataProvider = "dataForFindEntityCommandCheck")
  public void findEntityCommand(String entityType, String expectedCommand) {
	  RatingUtil instance = RatingUtil.getInstance();
	  String actualCommand = instance.findEntityCommand(entityType);
	  Assert.assertEquals(actualCommand, expectedCommand);
  }

  @DataProvider (name = "dataForFindEntityCommandCheck")
  public Object[][] dataForFindEntityCommandCheck() {
	  return new Object[][]{
		  {"genre", "find_genre"},
		  {"singer", "find_singer"},
		  {"composition", "find_composition"}
	  };
  }

  @Test (dataProvider = "dataForFindRatingCommandCheck")
  public void findRatingCommand(String entityType, String expectedCommand) {
	  RatingUtil instance = RatingUtil.getInstance();
	  String actualCommand = instance.findRatingCommand(entityType);
	  Assert.assertEquals(actualCommand, expectedCommand);
  }

  @DataProvider (name = "dataForFindRatingCommandCheck")
  public Object[][] dataForFindRatingCommandCheck() {
	  return new Object[][]{
		  {"genre", "find_genres_ratings"},
		  {"singer", "find_singers_ratings"},
		  {"composition", "find_compositions_ratings"}
	  };
  }


}
