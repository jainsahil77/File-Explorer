/**
 * 
 */
package pvt.filedetails.utility;

/**
 * @author Sahil Jain
 *
 */
public class Enums {
	public enum ApplicationType {
		GUI, REST_API, CONSOLE
	}

	public enum ValidateDirectoryError {
		INVALID_STRING("Entered string is not valid"), DIRECTORY_NOT_FOUND("Input folder does not exist"),
		IS_FILE("Input folder is a file"), VALID_DIRECTORY("Input folder is valid");

		private final String errorMessage;

		ValidateDirectoryError(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getErrorMessage() {
			return this.errorMessage;
		}
	}

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

	public enum ProcessingStatus {
		PENDING("Not Started"), PROCESSING("In Progress"), COMPLETED("Complete"), FAILED("Error encountered. See Logs");

		private final String processingStatusString;

		ProcessingStatus(String processingStatusString) {
			this.processingStatusString = processingStatusString;
		}

		public String getProcessingStatus() {
			return this.processingStatusString;
		}
	}
}
