/**
 * 
 */
package pvt.filedetails.utility;

/**
 * This class consists all the enums required in the project
 * 
 * @author Sahil Jain
 *
 */
public class Enums {
	/**
	 * This enum defines the application launch type
	 * 
	 * @author Sahil Jain
	 *
	 */
	public enum ApplicationType {
		GUI, REST_API, CONSOLE
	}

	/**
	 * This enum defines directory related messages
	 * 
	 * @author Sahil Jain
	 *
	 */
	public enum ValidateDirectoryError {
		INVALID_STRING("Entered string is not valid"), DIRECTORY_NOT_FOUND("Input folder does not exist"),
		IS_FILE("Input folder is a file"), VALID_DIRECTORY("Input folder is valid");

		private final String errorMessage;

		ValidateDirectoryError(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		/**
		 * This method returns the error message for the enum
		 * 
		 * @return
		 */
		public String getErrorMessage() {
			return this.errorMessage;
		}
	}

	/**
	 * This enum defines menu item enums
	 * 
	 * @author Sahil Jain
	 *
	 */
	public enum MenuItems {
		VIEW_CONTENT("View Content"), VIEW_ALL_FILES("View All Files"), VIEW_ALL_FOLDERS("View All Folders"),
		PROCESS_OTHER_DIRECTORY("Process Other Directory"), PROCESS_ANOTHER_DIRECTORY("Process Another Directory");

		private final String itemValue;

		MenuItems(String itemValue) {
			this.itemValue = itemValue;
		}

		public String getItemValue() {
			return this.itemValue;
		}

		/**
		 * This method provides enums for given string
		 * 
		 * @param itemValue
		 * @return MenuItems enum
		 */
		public static MenuItems getMenuItemEnum(String itemValue) {
			if (itemValue.equals(VIEW_CONTENT.getItemValue())) {
				return VIEW_CONTENT;
			} else if (itemValue.equals(VIEW_ALL_FILES.getItemValue())) {
				return VIEW_ALL_FILES;
			} else if (itemValue.equals(VIEW_ALL_FOLDERS.getItemValue())) {
				return VIEW_ALL_FOLDERS;
			} else if (itemValue.equals(PROCESS_OTHER_DIRECTORY.getItemValue())) {
				return PROCESS_OTHER_DIRECTORY;
			} else if (itemValue.equals(PROCESS_ANOTHER_DIRECTORY.getItemValue())) {
				return PROCESS_ANOTHER_DIRECTORY;
			} else {
				return null;
			}
		}
	}

	/**
	 * This enum defines processing status
	 * 
	 * @author Sahil Jain
	 *
	 */
	public enum ProcessingStatus {
		PENDING("Not Started"), PROCESSING("In Progress"), COMPLETED("Complete"), FAILED("Error encountered. See Logs");

		private final String processingStatusString;

		ProcessingStatus(String processingStatusString) {
			this.processingStatusString = processingStatusString;
		}

		/**
		 * This method returns processing status string
		 * 
		 * @return Processing status string for the enum
		 */
		public String getProcessingStatus() {
			return this.processingStatusString;
		}
	}

	/**
	 * This enum defines some common dialogue box which are used in the project
	 * 
	 * @author Sahil Jain
	 *
	 */
	public enum DialogueBoxType {
		WARNING, CONFIRMATION, ERROR, SUCCESS
	}

	/**
	 * This enum defines FILE and FOLDER enum
	 * 
	 * @author Sahil Jain
	 *
	 */
	public enum FileFolder {
		FILE, FOLDER
	}
}
