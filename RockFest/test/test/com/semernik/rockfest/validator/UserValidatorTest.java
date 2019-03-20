package test.com.semernik.rockfest.validator;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.type.Role;
import com.semernik.rockfest.validator.UserValidator;

public class UserValidatorTest {

	UserValidator validator = UserValidator.getInstance();
	SessionRequestContent emptyAdminContent = new SessionRequestContent();
	SessionRequestContent validAdminContent = new SessionRequestContent();
	SessionRequestContent invalidAdminContent = new SessionRequestContent();
	SessionRequestContent emptyUserContent = new SessionRequestContent();
	SessionRequestContent validUserContent = new SessionRequestContent();
	SessionRequestContent invalidUserContent = new SessionRequestContent();
	SessionRequestContent emptyGuestContent = new SessionRequestContent();
	SessionRequestContent validGuestContent = new SessionRequestContent();
	SessionRequestContent invalidGuestContent = new SessionRequestContent();

	@BeforeClass
	public void initContents(){
		Map<String, String[]> validParameters = new HashMap<>();
		validParameters.put(ParameterName.ID.toString(), new String[] {"1234"});
		validParameters.put(ParameterName.USER_LOGIN.toString(), new String[] {"user login"});
		validParameters.put(ParameterName.USER_EMAIL.toString(), new String[] {"rockfestuser@gmail.com"});
		validParameters.put(ParameterName.PASSWORD.toString(), new String[] {"password"});
		validParameters.put(ParameterName.NEW_PASSWORD.toString(), new String[] {"new_password"});
		validParameters.put(ParameterName.NEW_LOGIN.toString(), new String[] {"new login"});
		validParameters.put(ParameterName.NEW_EMAIL.toString(), new String[] {"rockfestuser@gmail.com"});
		validParameters.put(ParameterName.MELODY_RATING.toString(), new String[] {"7"});
		validParameters.put(ParameterName.TEXT_RATING.toString(), new String[] {"2"});
		validParameters.put(ParameterName.MUSIC_RATING.toString(), new String[] {"10"});
		validParameters.put(ParameterName.VOCAL_RATING.toString(), new String[] {"1"});
		validParameters.put(ParameterName.LOCALE.toString(), new String[] {"ru_RU"});
		validParameters.put(ParameterName.DATE.toString(), new String[] {"1552933027056"});
		validAdminContent.setRequestParameters(validParameters);
		validUserContent.setRequestParameters(validParameters);
		validGuestContent.setRequestParameters(validParameters);
		Map<String, String[]> invalidParameters = new HashMap<>();
		invalidParameters.put(ParameterName.ID.toString(), new String[] {"1234567891234567891"});
		invalidParameters.put(ParameterName.USER_LOGIN.toString(), new String[] {"user_loginuser_loginuser_loginuser_login error!! "});
		invalidParameters.put(ParameterName.USER_EMAIL.toString(), new String[] {"rockfestusergmail.com"});
		invalidParameters.put(ParameterName.PASSWORD.toString(), new String[] {"passwordpasswordpasswordpassword"});
		invalidParameters.put(ParameterName.NEW_PASSWORD.toString(), new String[] {"passwordpasswordpasswordpassword"});
		invalidParameters.put(ParameterName.NEW_LOGIN.toString(), new String[] {"new_loginnew_loginnew_loginnew_loginnew_"
				+ "loginnew_login"});
		invalidParameters.put(ParameterName.NEW_EMAIL.toString(), new String[] {"@gmail.com"});
		invalidParameters.put(ParameterName.MELODY_RATING.toString(), new String[] {"23"});
		invalidParameters.put(ParameterName.TEXT_RATING.toString(), new String[] {"-2"});
		invalidParameters.put(ParameterName.MUSIC_RATING.toString(), new String[] {"rating"});
		invalidParameters.put(ParameterName.VOCAL_RATING.toString(), new String[] {"vocal"});
		invalidParameters.put(ParameterName.LOCALE.toString(), new String[] {"custom locale"});
		invalidParameters.put(ParameterName.DATE.toString(), new String[] {"осень"});
		invalidAdminContent.setRequestParameters(invalidParameters);
		invalidUserContent.setRequestParameters(invalidParameters);
		invalidGuestContent.setRequestParameters(invalidParameters);
		Map<String, Object> adminSessionAttributes = new HashMap<>();
		adminSessionAttributes.put(AttributeName.USER_ID.toString(), "100");
		adminSessionAttributes.put(AttributeName.USER_LOGIN.toString(), "login");
		adminSessionAttributes.put(AttributeName.ROLE.toString(), Role.ADMIN.toString());
		adminSessionAttributes.put(AttributeName.CURRENT_PAGE_ATTRIBUTES.toString(), new HashMap<String, Object>());
		emptyAdminContent.setSessionAttributes(adminSessionAttributes);
		validAdminContent.setSessionAttributes(adminSessionAttributes);
		invalidAdminContent.setSessionAttributes(adminSessionAttributes);
		Map<String, Object> userSessionAttributes = new HashMap<>();
		userSessionAttributes.put(AttributeName.USER_ID.toString(), "100");
		userSessionAttributes.put(AttributeName.USER_LOGIN.toString(), "login");
		userSessionAttributes.put(AttributeName.ROLE.toString(), Role.USER.toString());
		userSessionAttributes.put(AttributeName.CURRENT_PAGE_ATTRIBUTES.toString(), new HashMap<String, Object>());
		emptyUserContent.setSessionAttributes(userSessionAttributes);
		validUserContent.setSessionAttributes(userSessionAttributes);
		invalidUserContent.setSessionAttributes(userSessionAttributes);
		emptyGuestContent.addSessionAttribute(AttributeName.CURRENT_PAGE_ATTRIBUTES.toString(),  new HashMap<String, Object>());
		validGuestContent.addSessionAttribute(AttributeName.CURRENT_PAGE_ATTRIBUTES.toString(),  new HashMap<String, Object>());
		invalidGuestContent.addSessionAttribute(AttributeName.CURRENT_PAGE_ATTRIBUTES.toString(),  new HashMap<String, Object>());
	}

	@AfterClass
	public void releaseResources(){
		validAdminContent = null;
		invalidAdminContent = null;
		emptyAdminContent = null;
		validUserContent = null;
		invalidUserContent = null;
		emptyUserContent = null;
		validGuestContent = null;
		invalidGuestContent = null;
		emptyGuestContent = null;
	}

  @Test (dataProvider = "dataForValidAdminCheck")
  public void validAdmin(SessionRequestContent content, boolean expected) {
	  boolean actual = validator.validAdmin(content);
	  Assert.assertEquals(actual, expected);
  }

  @DataProvider(name = "dataForValidAdminCheck")
  public Object[][] dataForValidAdminCheck(){
	  return new Object[][] {
		  {validAdminContent, true},
		  {invalidAdminContent, true},
		  {emptyAdminContent, true},
		  {validUserContent, false},
		  {invalidUserContent, false},
		  {emptyUserContent, false},
		  {validGuestContent, false},
		  {invalidGuestContent, false},
		  {emptyGuestContent, false}
	  };
  }

  @Test (dataProvider = "dataForValidLocaleCheck")
  public void validLocale(SessionRequestContent content, boolean expected) {
	  boolean actual = validator.validLocale(content);
	  Assert.assertEquals(actual, expected);
  }

  @DataProvider(name = "dataForValidLocaleCheck")
  public Object[][] dataForValidLocaleCheck(){
	  return new Object[][] {
		  {validAdminContent, true},
		  {invalidAdminContent, false},
		  {emptyAdminContent, false},
		  {validUserContent, true},
		  {invalidUserContent, false},
		  {emptyUserContent, false},
		  {validGuestContent, true},
		  {invalidGuestContent, false},
		  {emptyGuestContent, false}
	  };
  }

  @Test (dataProvider = "dataForValidLoginCheck")
  public void validLogin(SessionRequestContent content, boolean expected) {
	  boolean actual = validator.validLogin(content);
	  Assert.assertEquals(actual, expected);
  }

  @DataProvider(name = "dataForValidLoginCheck")
  public Object[][] dataForValidLoginCheck(){
	  return new Object[][] {
		  {validAdminContent, true},
		  {invalidAdminContent, false},
		  {emptyAdminContent, false},
		  {validUserContent, true},
		  {invalidUserContent, false},
		  {emptyUserContent, false},
		  {validGuestContent, true},
		  {invalidGuestContent, false},
		  {emptyGuestContent, false}
	  };
  }

  @Test (dataProvider = "dataForValidLoginParametersCheck")
  public void validLoginParameters(SessionRequestContent content, boolean expected) {
	  boolean actual = validator.validLoginParameters(content);
	  Assert.assertEquals(actual, expected);
  }

  @DataProvider(name = "dataForValidLoginParametersCheck")
  public Object[][] dataForValidLoginParametersCheck(){
	  return new Object[][] {
		  {validAdminContent, false},
		  {invalidAdminContent, false},
		  {emptyAdminContent, false},
		  {validUserContent, false},
		  {invalidUserContent, false},
		  {emptyUserContent, false},
		  {validGuestContent, true},
		  {invalidGuestContent, false},
		  {emptyGuestContent, false}
	  };
  }

  @Test (dataProvider = "dataForValidNewEmailCheck")
  public void validNewEmail(SessionRequestContent content, boolean expected) {
	  boolean actual = validator.validNewEmail(content);
	  Assert.assertEquals(actual, expected);
  }

  @DataProvider(name = "dataForValidNewEmailCheck")
  public Object[][] dataForValidNewEmailCheck(){
	  return new Object[][] {
		  {validAdminContent, true},
		  {invalidAdminContent, false},
		  {emptyAdminContent, false},
		  {validUserContent, true},
		  {invalidUserContent, false},
		  {emptyUserContent, false},
		  {validGuestContent, false},
		  {invalidGuestContent, false},
		  {emptyGuestContent, false}
	  };
  }

  @Test (dataProvider = "dataForValidNewLoginCheck")
  public void validNewLogin(SessionRequestContent content, boolean expected) {
	  boolean actual = validator.validNewLogin(content);
	  Assert.assertEquals(actual, expected);
  }

  @DataProvider(name = "dataForValidNewLoginCheck")
  public Object[][] dataForValidNewLoginCheck(){
	  return new Object[][] {
		  {validAdminContent, true},
		  {invalidAdminContent, false},
		  {emptyAdminContent, false},
		  {validUserContent, true},
		  {invalidUserContent, false},
		  {emptyUserContent, false},
		  {validGuestContent, false},
		  {invalidGuestContent, false},
		  {emptyGuestContent, false}
	  };
  }

  @Test (dataProvider = "dataForValidNewPasswordCheck")
  public void validNewPassword(SessionRequestContent content, boolean expected) {
	  boolean actual = validator.validNewPassword(content);
	  Assert.assertEquals(actual, expected);
  }

  @DataProvider(name = "dataForValidNewPasswordCheck")
  public Object[][] dataForValidNewPasswordCheck(){
	  return new Object[][] {
		  {validAdminContent, true},
		  {invalidAdminContent, false},
		  {emptyAdminContent, false},
		  {validUserContent, true},
		  {invalidUserContent, false},
		  {emptyUserContent, false},
		  {validGuestContent, false},
		  {invalidGuestContent, false},
		  {emptyGuestContent, false}
	  };
  }

  @Test (dataProvider = "dataForValidRegistrationParametersCheck")
  public void validRegistrationParameters(SessionRequestContent content, boolean expected) {
	  boolean actual = validator.validRegistrationParameters(content);
	  Assert.assertEquals(actual, expected);
  }

  @DataProvider(name = "dataForValidRegistrationParametersCheck")
  public Object[][] dataForValidRegistrationParametersCheck(){
	  return new Object[][] {
		  {validAdminContent, true},
		  {invalidAdminContent, false},
		  {emptyAdminContent, false},
		  {validUserContent, true},
		  {invalidUserContent, false},
		  {emptyUserContent, false},
		  {validGuestContent, true},
		  {invalidGuestContent, false},
		  {emptyGuestContent, false}
	  };
  }

  @Test (dataProvider = "dataForValidUserCheck")
  public void validUser(SessionRequestContent content, boolean expected) {
	  boolean actual = validator.validUser(content);
	  Assert.assertEquals(actual, expected);
  }

  @DataProvider(name = "dataForValidUserCheck")
  public Object[][] dataForValidUserCheck(){
	  return new Object[][] {
		  {validAdminContent, true},
		  {invalidAdminContent, true},
		  {emptyAdminContent, true},
		  {validUserContent, true},
		  {invalidUserContent, true},
		  {emptyUserContent, true},
		  {validGuestContent, false},
		  {invalidGuestContent, false},
		  {emptyGuestContent, false}
	  };
  }

}
