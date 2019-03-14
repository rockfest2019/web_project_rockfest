package com.semernik.rockfest.command;

import com.semernik.rockfest.container.ErrorMessagesContainer;
import com.semernik.rockfest.container.PagesNamesContainer;
import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.PageType;
import com.semernik.rockfest.type.SendingMethod;
import com.semernik.rockfest.validator.Validator;

// TODO: Auto-generated Javadoc
/**
 * The Class Command.
 */
public class Command {

	/** The validator. */
	private Validator validator;

	/** The invoker. */
	private LogicInvoker invoker;

	/** The result page type. */
	private PageType resultPageType;

	/** The method. */
	private SendingMethod method;

	/** The Constant INVALID_PARAMETERS. */
	private final static String INVALID_PARAMETERS = "invalidParameters";

	/** The Constant OK. */
	private final static String OK = "ok";

	/** The Constant ERROR. */
	private final static String ERROR = "error";

	/** The Constant DELIMITER. */
	private final static String DELIMITER = "_";


	/**
	 * Instantiates a new command.
	 *
	 * @param validator the validator
	 * @param invoker the invoker
	 * @param pageType the page type
	 * @param method the method
	 */
	public Command (Validator validator, LogicInvoker invoker, PageType pageType, SendingMethod method){
		this.validator = validator;
		this.invoker = invoker;
		this.resultPageType = pageType;
		this.method = method;
	}

	/**
	 * Instantiates a new command.
	 *
	 * @param validator the validator
	 * @param invoker the invoker
	 * @param pageType the page type
	 */
	public Command (Validator validator, LogicInvoker invoker, PageType pageType){
		this(validator, invoker, pageType, SendingMethod.FORWARD);
	}

	/**
	 * Instantiates a new command.
	 *
	 * @param invoker the invoker
	 * @param pageType the page type
	 * @param method the method
	 */
	public Command (LogicInvoker invoker, PageType pageType, SendingMethod method){
		this(DefaultValidator.getInstance(), invoker, pageType, method);
	}

	/**
	 * Instantiates a new command.
	 *
	 * @param validator the validator
	 * @param invoker the invoker
	 * @param method the method
	 */
	public Command (Validator validator, LogicInvoker invoker,  SendingMethod method){
		this(validator, invoker, PageType.ERROR, method);
	}

	/**
	 * Instantiates a new command.
	 *
	 * @param pageType the page type
	 * @param method the method
	 */
	public Command (PageType pageType, SendingMethod method){
		this(DefaultLogicInvoker.getInstance(), pageType, method);
	}

	/**
	 * Instantiates a new command.
	 *
	 * @param invoker the invoker
	 * @param pageType the page type
	 */
	public Command (LogicInvoker invoker, PageType pageType){
		this(invoker, pageType, SendingMethod.FORWARD);
	}

	/**
	 * Instantiates a new command.
	 *
	 * @param invoker the invoker
	 * @param method the method
	 */
	public Command (LogicInvoker invoker,  SendingMethod method){
		this(invoker, PageType.ERROR, method);
	}

	/**
	 * Execute.
	 *
	 * @param content the content
	 * @return the string
	 */
	public String execute(SessionRequestContent content){
		String preffix = resultPageType.toString();
		String suffix;
		if (!validator.isValid(content)){
			suffix = INVALID_PARAMETERS;
		} else if (invoker.invoke(content)){
			suffix = OK;
		} else {
			String errorMessage = ErrorMessagesContainer.findMessage(preffix);
			content.getRequestAttributes().put(AttributeName.ERROR_MESSAGE.toString(), errorMessage);
			suffix = ERROR;
		}
		System.out.println(preffix + DELIMITER + suffix);
		String pageName = null;
		if (method != SendingMethod.AJAX){
			pageName = PagesNamesContainer.findPage(preffix + DELIMITER + suffix);
		}
		content.setSendingMethod(method);
		if (content.isUsingCurrentPage()){
			pageName = content.getCurrentPage();
		} else {
			content.setCurrentPage(pageName);
		}
		return pageName;
	}

	/**
	 * The Class DefaultValidator.
	 */
	private static class DefaultValidator implements Validator {

		/** The instance. */
		private static DefaultValidator instance = new DefaultValidator();

		/**
		 * Gets the single instance of DefaultValidator.
		 *
		 * @return single instance of DefaultValidator
		 */
		public static DefaultValidator getInstance(){
			return instance;
		}

		/**
		 * Instantiates a new default validator.
		 */
		private DefaultValidator() {}

		/* (non-Javadoc)
		 * @see com.semernik.rockFest.validator.Validator#isValid(com.semernik.rockFest.controller.SessionRequestContent)
		 */
		@Override
		public boolean isValid(SessionRequestContent content) {
			return true;
		}

	}

	/**
	 * The Class DefaultLogicInvoker.
	 */
	private static class DefaultLogicInvoker implements LogicInvoker {

		/** The instance. */
		private static DefaultLogicInvoker instance = new DefaultLogicInvoker();

		/**
		 * Gets the single instance of DefaultLogicInvoker.
		 *
		 * @return single instance of DefaultLogicInvoker
		 */
		public static DefaultLogicInvoker getInstance(){
			return instance;
		}

		/**
		 * Instantiates a new default logic invoker.
		 */
		private DefaultLogicInvoker() {}

		/* (non-Javadoc)
		 * @see com.semernik.rockFest.command.LogicInvoker#invoke(com.semernik.rockFest.controller.SessionRequestContent)
		 */
		@Override
		public boolean invoke(SessionRequestContent content) {
			return true;
		}
	}

}
